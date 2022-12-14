package cutiedev.webserver.web.exceptions;

public class ConnectionClosedException extends ConnectionException{

    public ConnectionClosedException()
    {
        this(true);
    }

    public ConnectionClosedException(boolean byClient)
    {
        super("closed by " + (byClient ? "client": "server"));
    }
}
