package cutiedev.webserver.web.response;

public class OkResult extends ObjectResult {
    public OkResult(Object value)
    {
        super(value, HttpCode.OK);
    }
}
