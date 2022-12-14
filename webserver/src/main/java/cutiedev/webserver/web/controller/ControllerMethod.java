package cutiedev.webserver.web.controller;

import cutiedev.webserver.web.model.HTTPMethod;

import java.lang.reflect.Method;

public class ControllerMethod
{
    private HTTPMethod HTTPMethod;
    private Method method;
    private String path;

    public ControllerMethod(HTTPMethod HTTPMethod, Method method)
    {
        try {
            path = this.method.getName();
        }
        catch(NullPointerException exception)
        {

        }
        this.HTTPMethod = HTTPMethod;
        this.method = method;
    }
    public ControllerMethod(HTTPMethod HTTPMethod, Method method, String path) {
        this.HTTPMethod = HTTPMethod;
        this.method = method;
    }
}
