using dotnet.Utils;

namespace dotnet.Services;

public class FileUploadService
{
    private readonly FileUploadUtil _fileUploadUtil;

    public FileUploadService(FileUploadUtil fileUploadUtil)
    {
        _fileUploadUtil = fileUploadUtil;
    }

    public async Task<string> UploadFileAsync(IFormFile file)
    {
        // Call the utility to handle file saving and return file name
        return await _fileUploadUtil.UploadFileAsync(file);
    }
}
