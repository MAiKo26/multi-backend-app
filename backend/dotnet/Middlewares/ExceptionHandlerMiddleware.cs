namespace dotnet.exceptions;

using Microsoft.AspNetCore.Http;
using System.Text.Json;

public class ExceptionHandlerMiddleware
{
    private readonly RequestDelegate _next;

    public ExceptionHandlerMiddleware(RequestDelegate next)
    {
        _next = next;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        try
        {
            await _next(context);
        }
        catch (CustomException ex)
        {
            await HandleExceptionAsync(context, ex.StatusCode, ex.Message);
        }
        catch (Exception)
        {
            await HandleExceptionAsync(context, 500, "Internal server error");
        }
    }

    private static Task HandleExceptionAsync(HttpContext context, int statusCode, string message)
    {
        context.Response.ContentType = "application/json";
        context.Response.StatusCode = statusCode;

        var response = new
        {
            status = statusCode,
            message,
            timestamp = DateTime.UtcNow,
            path = context.Request.Path
        };

        return context.Response.WriteAsync(JsonSerializer.Serialize(response));
    }
}

public static class ExceptionHandlerMiddlewareExtensions
{
    public static void AddExceptionHandlerMiddleware(this IServiceCollection services)
    {
        services.AddScoped<ExceptionHandlerMiddleware>();
    }
}
