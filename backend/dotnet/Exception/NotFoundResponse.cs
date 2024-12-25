namespace dotnet.exceptions
{
    public class NotFoundResponse : Exception
    {
        public int StatusCode { get; }

        public NotFoundResponse(string message) : base(message)
        {
            StatusCode = 404;
        }
    }
}