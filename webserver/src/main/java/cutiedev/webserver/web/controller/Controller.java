package cutiedev.webserver.web.controller;

import cutiedev.webserver.web.response.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public abstract class Controller
{
    public final IActionResult respond(Object value, Short statusCode)
    {
        return new ObjectResult(value, statusCode);
    }
    public final IActionResult ok(Object value)
    {
        return new OkResult(value);
    }
    public final IActionResult bad(Object value)
    {
        return new BadResult(value);
    }
    public final IActionResult serverError()
    {
        return new ServerErrorResult("Error in server");
    }
    public final IActionResult serverError(Object value)
    {
        return new ServerErrorResult(value);
    }
}
