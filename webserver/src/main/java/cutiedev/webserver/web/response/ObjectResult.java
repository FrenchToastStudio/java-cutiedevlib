package cutiedev.webserver.web.response;

import cutiedev.webserver.web.data.strategy.IBuildDataStrategy;

public class ObjectResult implements IActionResult
{
    private Object value;
    private IBuildDataStrategy buildDataStrategy;
    private short httpCode;

    public ObjectResult(Object value, short httpCode)
    {
        this.value = value;
        this.httpCode = httpCode;
    }

}
