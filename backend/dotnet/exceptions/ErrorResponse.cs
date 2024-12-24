namespace dotnet.exceptions;

public class ErrorResponse
{
    private int code;
    private string message;
    
    public ErrorResponse(int code, string message)
    {
        this.code = code;
        this.message = message;
    }
    
    public string GetMessage()
    {
        return message;
    }
    
    public int GetCode()
    {
        return code;
    }

}