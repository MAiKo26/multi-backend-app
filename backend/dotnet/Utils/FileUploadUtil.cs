namespace dotnet.Utils;

using Microsoft.AspNetCore.Http;
using System;
using System.IO;
using System.Threading.Tasks;

public class FileUploadUtil
{
    private readonly string _uploadDirectory;

    public FileUploadUtil(string uploadDirectory)
    {
        _uploadDirectory = uploadDirectory;
    }

    public async Task<string> UploadFileAsync(IFormFile file)
    {
        if (file == null || file.Length == 0)
        {
            throw new ArgumentException("No file uploaded.");
        }

        var fileExtension = Path.GetExtension(file.FileName);
        var uniqueFileName = $"{DateTime.Now:yyyyMMddHHmmssfff}-{Path.GetFileNameWithoutExtension(file.FileName)}.webp"; // For example .webp

        var filePath = Path.Combine(_uploadDirectory, uniqueFileName);

        // Save file to disk
        using (var fileStream = new FileStream(filePath, FileMode.Create))
        {
            await file.CopyToAsync(fileStream);
        }

        return uniqueFileName;
    }
}
