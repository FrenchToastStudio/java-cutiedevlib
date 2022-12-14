package cutiedev.webserver.web.response;

public class ServerErrorResult extends ObjectResult
{
    public ServerErrorResult(Object value) {
        super(value, HttpCode.INTERNAL_SERVER_ERROR);
    }
}
