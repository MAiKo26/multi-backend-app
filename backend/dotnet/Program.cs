using System.IdentityModel.Tokens.Jwt;
using System.IO;
using Microsoft.Extensions.FileProviders;
using dotnet.Configuration;
using dotnet.Middlewares;
using dotnet.Data;
using dotnet.exceptions;
using dotnet.Interfaces;
using dotnet.Mappers;
using dotnet.Services;
using dotnet.Utils;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddAutoMapper(cfg => cfg.AddMaps(typeof(UserMapper).Assembly));
builder.Services.AddControllers();
builder.Services.Configure<EmailConfig>(builder.Configuration.GetSection("EmailSettings"));


builder.Services.AddSingleton<IHttpContextAccessor, HttpContextAccessor>();
builder.Services.AddScoped<HashPasswordService>();
builder.Services.AddScoped<JwtSecurityTokenHandler>();
builder.Services.AddScoped<IEmailSenderService, EmailSenderService>();


builder.Services.AddScoped<IUserService, UserService>();
builder.Services.AddScoped<IProfileService, ProfileService>();

builder.Services.AddScoped<IActivityHistoryService, ActivityHistoryService>();
builder.Services.AddScoped<IAuthService, AuthService>();
builder.Services.AddScoped<IProjectService, ProjectService>();
builder.Services.AddScoped<ITaskService, TaskService>();
builder.Services.AddScoped<ITeamService, TeamService>();
builder.Services.AddScoped<IChatService, ChatService>();
builder.Services.AddScoped<JwtUtil>();

builder.Services.AddSingleton<FileUploadUtil>(new FileUploadUtil("C:/Users/MSI/Workstation/multi-backend-app/backend/uploads"));
builder.Services.AddTransient<FileUploadService>();


builder.Services.AddDbContext<DataContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddCors(options =>
{
    options.AddPolicy("CorsPolicy", policy =>
    {
        policy.WithOrigins("http://localhost:5173")
              .AllowAnyMethod()
              .AllowAnyHeader()
              .AllowCredentials();
    });
});
builder.Services.AddOpenApi(options =>
{
    options.OpenApiVersion = Microsoft.OpenApi.OpenApiSpecVersion.OpenApi3_0;
});

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
    app.UseSwaggerUI(options =>
    {
        options.DocumentTitle = "Dotnet API";
        options.SwaggerEndpoint("/openapi/v1.json", "Dotnet API V1");
    });
}

app.UseHttpsRedirection();

app.UseMiddleware<ExceptionHandlerMiddleware>();
app.UseWebSockets();
app.UseMiddleware<WebSocketMiddleware>();

app.UseAuthentication();

app.UseAuthorization();

app.UseCors("CorsPolicy");

app.UseStaticFiles();
app.UseStaticFiles(new StaticFileOptions
{
    FileProvider = new PhysicalFileProvider(
        Path.Combine(builder.Environment.ContentRootPath, "..", "..", "backend", "uploads")),
    RequestPath = "/uploads"
});

app.MapControllers();

app.Run();