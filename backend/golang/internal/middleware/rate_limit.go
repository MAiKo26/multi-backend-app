package middleware

import (
	"net/http"
	"sync"
	"time"

	"github.com/gin-gonic/gin"
)

type tokenBucket struct {
	tokens     float64
	maxTokens  float64
	refillRate float64
	lastRefill time.Time
	mu         sync.Mutex
}

func newTokenBucket(maxTokens, refillRate float64) *tokenBucket {
	return &tokenBucket{
		tokens:     maxTokens,
		maxTokens:  maxTokens,
		refillRate: refillRate,
		lastRefill: time.Now(),
	}
}

func (tb *tokenBucket) allow() bool {
	tb.mu.Lock()
	defer tb.mu.Unlock()

	now := time.Now()
	elapsed := now.Sub(tb.lastRefill).Seconds()
	tb.tokens += elapsed * tb.refillRate
	if tb.tokens > tb.maxTokens {
		tb.tokens = tb.maxTokens
	}
	tb.lastRefill = now

	if tb.tokens >= 1 {
		tb.tokens--
		return true
	}
	return false
}

type RateLimiter struct {
	clients map[string]*tokenBucket
	mu      sync.RWMutex
	max     float64
	rate    float64
}

func NewRateLimiter(maxTokens, refillRate float64) *RateLimiter {
	return &RateLimiter{
		clients: make(map[string]*tokenBucket),
		max:     maxTokens,
		rate:    refillRate,
	}
}

func (rl *RateLimiter) getBucket(ip string) *tokenBucket {
	rl.mu.RLock()
	bucket, exists := rl.clients[ip]
	rl.mu.RUnlock()

	if exists {
		return bucket
	}

	rl.mu.Lock()
	defer rl.mu.Unlock()

	bucket = newTokenBucket(rl.max, rl.rate)
	rl.clients[ip] = bucket
	return bucket
}

func RateLimitMiddleware(maxTokens, refillRate float64) gin.HandlerFunc {
	limiter := NewRateLimiter(maxTokens, refillRate)

	return func(c *gin.Context) {
		ip := c.ClientIP()
		bucket := limiter.getBucket(ip)

		if !bucket.allow() {
			c.JSON(http.StatusTooManyRequests, gin.H{"error": "rate limit exceeded"})
			c.Abort()
			return
		}

		c.Next()
	}
}
