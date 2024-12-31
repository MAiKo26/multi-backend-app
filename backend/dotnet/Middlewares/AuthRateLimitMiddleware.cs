namespace dotnet.Middlewares;

using Microsoft.AspNetCore.Http;
using System.Threading.Tasks;

public class AuthRateLimitMiddleware
{
    private readonly RequestDelegate _next;
    private readonly RateLimiter _rateLimiter = new(5, TimeSpan.FromMinutes(5));

    public AuthRateLimitMiddleware(RequestDelegate next)
    {
        _next = next;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        if (context.Request.Path.StartsWithSegments("/auth"))
        {
            if (!_rateLimiter.AllowRequest())
            {
                context.Response.StatusCode = StatusCodes.Status429TooManyRequests;
                await context.Response.WriteAsync("Too many login attempts, please try again later.");
                return;
            }
        }

        await _next(context);
    }
}
