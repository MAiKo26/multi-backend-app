# .NET Backend

A C# ASP.NET Core REST API.

## Prerequisites

- **.NET SDK** 9.0
- **Database**: PostgreSQL
- **IDE**: VS Code with C# extension

## Quick Start (CLI - VS Code Integrated Terminal)

```bash
cd dotnet

# Restore dependencies
dotnet restore

# Create environment file
copy .env.example .env
# Edit .env with your database credentials

# Run the application
dotnet run
```

The API will be available at `http://localhost:5000`

## Available .NET Commands

| Command | Description |
|---------|-------------|
| `dotnet run` | Start development server |
| `dotnet build` | Build the project |
| `dotnet test` | Run tests |
| `dotnet watch run` | Start with hot reload |
| `dotnet publish` | Create production build |

## Installing Packages

```bash
# Add a NuGet package
dotnet add package <PackageName>

# Example
dotnet add package AutoMapper
```

After adding, restore with:
```bash
dotnet restore
```

## Environment Variables

Create an `appsettings.json` in the root:

```json
{
  "ConnectionStrings": {
    "DefaultConnection": "Host=localhost;Database=mydb;Username=postgres;Password=yourpassword"
  },
  "Jwt": {
    "Secret": "your-secret-key",
    "Issuer": "your-issuer",
    "Audience": "your-audience"
  },
  "Server": {
    "Port": 5000
  }
}
```

Or set environment variables:
```powershell
$env:ASPNETCORE_ENVIRONMENT="Development"
$env:ConnectionStrings__DefaultConnection="..."
```

## Project Structure

```
dotnet/
├── Program.cs                # Entry point
├── Controllers/              # API endpoints
├── Services/                 # Business logic
├── Models/                   # Entities
├── Data/                     # DbContext
├── Middleware/               # Custom middleware
├── DTOs/                     # Data transfer objects
├── dotnet.csproj
└── appsettings.json
```

## VS Code Extensions Recommended

- C# Dev Kit
- .NET Install Tool