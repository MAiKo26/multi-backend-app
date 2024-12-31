using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using Microsoft.IdentityModel.Tokens;

namespace dotnet.Utils;

public class JwtUtil
{
    private readonly string
        _secretKey = "YourVeryLongAndComplexSecretKeyHerePleaseMakeItLongEnough"; // Secret key for signing JWT

    public string GenerateToken(string email)
    {
        var claims = new List<Claim>
        {
            new Claim(ClaimTypes.Name, email)
        };

        var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_secretKey));
        var credentials = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

        var token = new JwtSecurityToken(
            issuer: "yourIssuer", // Can be any string
            audience: "yourAudience", // Can be any string
            claims: claims,
            expires: DateTime.Now.AddHours(1),
            signingCredentials: credentials
        );

        return new JwtSecurityTokenHandler().WriteToken(token);
    }

    public bool ValidateToken(string token)
    {
        try
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.UTF8.GetBytes(_secretKey);

            tokenHandler.ValidateToken(token, new TokenValidationParameters
            {
                ValidateIssuer = false,
                ValidateAudience = false,
                ValidateLifetime = true,
                IssuerSigningKey = new SymmetricSecurityKey(key)
            }, out var validatedToken);

            return validatedToken != null;
        }
        catch
        {
            return false;
        }
    }

    public string ExtractEmail(string token)
    {
        var tokenHandler = new JwtSecurityTokenHandler();
        var jwtToken = tokenHandler.ReadToken(token) as JwtSecurityToken;
        return jwtToken?.Subject;
    }
}