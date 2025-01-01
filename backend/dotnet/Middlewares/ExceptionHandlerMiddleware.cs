using System.Security.Authentication;
using System.Text.Json;

namespace dotnet.exceptions;

public class ExceptionHandlerMiddleware
{
    private readonly RequestDelegate _next;
    private readonly ILogger<ExceptionHandlerMiddleware> _logger;

    public ExceptionHandlerMiddleware(RequestDelegate next, ILogger<ExceptionHandlerMiddleware> logger)
    {
        _next = next;
        _logger = logger;
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
        catch (AuthenticationException ex)
        {
            await HandleAuthenticationExceptionAsync(context, ex);
        }
        catch (UnauthorizedAccessException ex)
        {
            await HandleAccessDeniedExceptionAsync(context, ex);
        }
        catch (NotFoundResponse ex)
        {
            await HandleExceptionAsync(context, StatusCodes.Status404NotFound, "Endpoint not found");
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "An unhandled exception occurred");
            await HandleExceptionAsync(context, StatusCodes.Status500InternalServerError, "Internal server error");
        }
    }

    private static Task HandleExceptionAsync(HttpContext context, int statusCode, string message)
    {
        context.Response.ContentType = "application/json";
        context.Response.StatusCode = statusCode;

        var errorResponse = new ErrorResponse(statusCode, message);
        return context.Response.WriteAsync(JsonSerializer.Serialize(errorResponse));
    }

    private static Task HandleAuthenticationExceptionAsync(HttpContext context, AuthenticationException ex)
    {
        context.Response.ContentType = "application/json";
        context.Response.StatusCode = StatusCodes.Status401Unauthorized;

        var response = new
        {
            timestamp = DateTime.UtcNow,
            status = StatusCodes.Status401Unauthorized,
            message = $"Not Authenticated: {ex.Message}",
            path = context.Request.Path
        };

        return context.Response.WriteAsync(JsonSerializer.Serialize(response));
    }

    private static Task HandleAccessDeniedExceptionAsync(HttpContext context, UnauthorizedAccessException ex)
    {
        context.Response.ContentType = "application/json";
        context.Response.StatusCode = StatusCodes.Status403Forbidden;

        var response = new
        {
            timestamp = DateTime.UtcNow,
            status = StatusCodes.Status403Forbidden,
            message = $"Not Authorized: {ex.Message}",
            path = context.Request.Path
        };

        return context.Response.WriteAsync(JsonSerializer.Serialize(response));
    }
}