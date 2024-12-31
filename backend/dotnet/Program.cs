using dotnet.Configuration;
using dotnet.Data;
using dotnet.exceptions;
using dotnet.Interfaces;
using dotnet.Mappers;
using dotnet.Middlewares;
using dotnet.Services;
using dotnet.Utils;
using Microsoft.EntityFrameworkCore;
using Microsoft.OpenApi.Models;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddAutoMapper(typeof(UserMapper).Assembly);
builder.Services.AddControllers();
builder.Services.Configure<EmailConfig>(builder.Configuration.GetSection("EmailSettings"));
builder.Services.AddScoped<IActivityHistoryService, ActivityHistoryService>();
builder.Services.AddScoped<IAuthService, AuthService>();
builder.Services.AddScoped<IEmailSenderService, EmailSenderService>();
builder.Services.AddScoped<IProfileService, ProfileService>();
builder.Services.AddScoped<IProjectService, ProjectService>();
builder.Services.AddScoped<ITaskService, TaskService>();
builder.Services.AddScoped<ITeamService, TeamService>();
builder.Services.AddScoped<IUserService, UserService>();
builder.Services.AddSingleton<FileUploadUtil>(new FileUploadUtil("C:/path/to/your/upload/directory"));
builder.Services.AddTransient<FileUploadService>();



builder.Services.AddDbContext<DataContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
{
    c.SwaggerDoc("v1", new OpenApiInfo
    {
        Title = "Dotnet Api",
        Version = "v1",
        Contact = new OpenApiContact
        {
            Name = "Mohamed Aziz Karoui",
            Email = "contact.mohamedazizkaroui@gmail.com"
        }
    });
});

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI(c =>
    {
        c.SwaggerEndpoint("/swagger/v1/swagger.json", "My API V1");
        c.RoutePrefix = "swagger-ui";
    });
}

app.UseHttpsRedirection();

app.UseMiddleware<GlobalExceptionMiddleware>();

app.UseMiddleware<ExceptionHandlerMiddleware>();

app.UseAuthentication();

app.UseAuthorization();

app.UseCors("CorsPolicy");

app.UseStaticFiles();

app.MapControllers();

app.Run();