package cutiedev.webserver.web.response;

public class BadResult extends ObjectResult{

    public BadResult(Object value) {
        super(value, HttpCode.BAD_REQUEST);
    }

}
