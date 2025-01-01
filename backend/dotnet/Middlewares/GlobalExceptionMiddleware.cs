using dotnet.exceptions;
using Newtonsoft.Json;

public class GlobalExceptionMiddleware
{
    private readonly RequestDelegate _next;

    public GlobalExceptionMiddleware(RequestDelegate next)
    {
        _next = next;
    }

    public async Task InvokeAsync(HttpContext context)
    {
        
            await _next(context);
        }
        catch (NotFoundResponse ex)
        {
            await HandleExceptionAsync(context, ex);
        }
        catch (Exception ex)
        {
            await HandleExceptionAsync(context, ex, 500);
        }
    }

    private Task HandleExceptionAsync(HttpContext context, Exception exception, int statusCode = 400)
    {
        context.Response.ContentType = "application/json";
        context.Response.StatusCode = (exception is NotFoundResponse notFoundEx)
            ? notFoundEx.StatusCode
            : statusCode;

        var response = new
        {
            message = exception.Message,
            statusCode = context.Response.StatusCode
        };

        return context.Response.WriteAsync(JsonConvert.SerializeObject(response));
    }
}