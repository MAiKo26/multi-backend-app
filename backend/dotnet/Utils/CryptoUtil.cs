using System.Security.Cryptography;

namespace dotnet.Utils;

public static class CryptoUtil
{
    public static string GenerateRandomToken(int size = 32)
    {
        var tokenBytes = new byte[size];
        RandomNumberGenerator.Fill(tokenBytes);
        return Convert.ToBase64String(tokenBytes);
    }
}