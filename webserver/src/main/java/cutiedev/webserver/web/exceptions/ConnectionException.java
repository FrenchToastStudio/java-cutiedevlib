package cutiedev.webserver.web.exceptions;

public class ConnectionException extends Exception
{

    public ConnectionException(String message)
    {
        super("Connection: " + message);
    }
}
