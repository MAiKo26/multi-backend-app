using System.Text;
using System.Text.Json;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.IdentityModel.Tokens;

namespace dotnet.Configuration;

public static class SecurityConfig
{
    public static void ConfigureServices(IServiceCollection services)
    {
        services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
            .AddJwtBearer(options =>
            {
                options.TokenValidationParameters = new TokenValidationParameters
                {
                    ValidateIssuer = true,
                    ValidateAudience = true,
                    ValidateLifetime = true,
                    ValidateIssuerSigningKey = true,
                    ValidIssuer = "YourIssuer",
                    ValidAudience = "YourAudience",
                    IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("YourSecretKey"))
                };

                options.Events = new JwtBearerEvents
                {
                    OnAuthenticationFailed = context =>
                    {
                        context.Response.StatusCode = StatusCodes.Status401Unauthorized;
                        context.Response.ContentType = "application/json";

                        var errorDetails = new Dictionary<string, object>
                        {
                            { "status", StatusCodes.Status401Unauthorized },
                            { "message", "Not Authenticated: " + context.Exception.Message },
                            { "timestamp", DateTime.UtcNow },
                            { "path", context.Request.Path }
                        };

                        return context.Response.WriteAsync(JsonSerializer.Serialize(errorDetails));
                    },
                    OnChallenge = context =>
                    {
                        context.Response.StatusCode = StatusCodes.Status403Forbidden;
                        context.Response.ContentType = "application/json";

                        var errorDetails = new Dictionary<string, object>
                        {
                            { "status", StatusCodes.Status403Forbidden },
                            { "message", "Not Authorized: " + context.ErrorDescription },
                            { "timestamp", DateTime.UtcNow },
                            { "path", context.Request.Path }
                        };

                        return context.Response.WriteAsync(JsonSerializer.Serialize(errorDetails));
                    }
                };
            });

        services.AddAuthorization(options =>
        {
            options.DefaultPolicy = new AuthorizationPolicyBuilder()
                .RequireAuthenticatedUser()
                .Build();

            options.AddPolicy("UserPolicy", policy => policy.RequireRole("user", "admin"));
        });
    }

    public static void Configure(IApplicationBuilder app)
    {
        app.UseAuthentication();
        app.UseAuthorization();

        app.UseEndpoints(endpoints => { endpoints.MapControllers().RequireAuthorization("UserPolicy"); });
    }
}