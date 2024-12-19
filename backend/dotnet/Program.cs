using dotnet.Configuration;
using dotnet.Data;
using dotnet.Interfaces;
using dotnet.Mappers;
using dotnet.Services;
using Microsoft.EntityFrameworkCore;
using Microsoft.OpenApi.Models;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddAutoMapper(typeof(UserMapper).Assembly);
builder.Services.AddControllers();
builder.Services.Configure<EmailConfig>(builder.Configuration.GetSection("EmailSettings"));

builder.Services.AddScoped<IUserService, UserService>();
builder.Services.AddScoped<IAuthService, AuthService>();
builder.Services.AddScoped<IEmailSenderService, EmailSenderService>();

builder.Services.AddDbContext<DataContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

// Register Swagger generator
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

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    // Enable Swagger in development mode
    app.UseSwagger();
    app.UseSwaggerUI(c =>
    {
        c.SwaggerEndpoint("/swagger/v1/swagger.json", "My API V1");
        c.RoutePrefix = "swagger-ui"; // Swagger UI at root URL
    });
}

app.UseHttpsRedirection();

app.UseAuthentication(); // if you have authentication configured
app.UseAuthorization();

app.MapControllers();

app.Run();