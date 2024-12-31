namespace dotnet.Middlewares;

using Microsoft.AspNetCore.Http;
using System.Threading.Tasks;
using System.Collections.Concurrent;

public class RateLimitMiddleware
{
    private readonly RequestDelegate _next;
    private static readonly ConcurrentDictionary<string, RateLimiter> _rateLimiters = new();

    public RateLimitMiddleware(RequestDelegate next)
    {
        _next = next;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        var key = context.Connection.RemoteIpAddress?.ToString() ?? "unknown";
        var limiter = _rateLimiters.GetOrAdd(key, _ => new RateLimiter(100, TimeSpan.FromMinutes(1)));

        if (!limiter.AllowRequest())
        {
            context.Response.StatusCode = StatusCodes.Status429TooManyRequests;
            await context.Response.WriteAsync("Too many requests, please try again later.");
            return;
        }

        await _next(context);
    }
}

public class RateLimiter
{
    private readonly int _limit;
    private readonly TimeSpan _timeWindow;
    private int _requestCount;
    private DateTime _windowStart;

    public RateLimiter(int limit, TimeSpan timeWindow)
    {
        _limit = limit;
        _timeWindow = timeWindow;
        _windowStart = DateTime.UtcNow;
    }

    public bool AllowRequest()
    {
        if (DateTime.UtcNow - _windowStart > _timeWindow)
        {
            _requestCount = 0;
            _windowStart = DateTime.UtcNow;
        }

        if (_requestCount >= _limit) return false;

        _requestCount++;
        return true;
    }
}
