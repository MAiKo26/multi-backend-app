using dotnet.Services;
using Microsoft.AspNetCore.Mvc;

namespace dotnet.Controllers;

[ApiController]
[Route("upload")]
public class FileUploadController : ControllerBase
{
    private readonly FileUploadService _fileUploadService;

    public FileUploadController(FileUploadService fileUploadService)
    {
        _fileUploadService = fileUploadService;
    }

    [HttpPost("avatar")]
    public async Task<IActionResult> UploadAvatar(IFormFile file)
    {
        try
        {
            var fileName = await _fileUploadService.UploadFileAsync(file);
            return Ok(new { fileName = fileName, path = $"/uploads/{fileName}" });
        }
        catch (ArgumentException ex)
        {
            return BadRequest(new { message = ex.Message });
        }
    }
}