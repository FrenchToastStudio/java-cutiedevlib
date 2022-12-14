package cutiedev.webserver.web.controller;

import cutiedev.webserver.web.controller.annotation.method.DELETE;
import cutiedev.webserver.web.controller.annotation.method.GET;
import cutiedev.webserver.web.controller.annotation.method.POST;
import cutiedev.webserver.web.controller.annotation.method.PUT;
import cutiedev.webserver.web.data.DataType;
import cutiedev.webserver.web.model.HTTPMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ServerController
{
    private List<ControllerMethod> methodList;
    private Controller controller;
    private String fullPath;
    private String controllerPath;
    private DataType defaultDataType;

    public ServerController(Class<Controller> controller)
    {
        Class[] parameterType = null;
        try {
            this.controller = controller.getConstructor(parameterType).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        setMethods();
    }

    public String getConrollerPath()
    {
        return this.controllerPath;
    }

    private void setControllerInfo()
    {
        Class<? extends Controller> controllerClass = this.controller.getClass();
        if(controllerClass.isAnnotationPresent(cutiedev.webserver.web.controller.annotation.Controller.class))
        {
            cutiedev.webserver.web.controller.annotation.Controller annotation = controller.getClass().getAnnotation(cutiedev.webserver.web.controller.annotation.Controller.class);
            setPath(annotation.path());
            this.defaultDataType = annotation.defaultReturnType();
        }
    }
    private void setPath(String path)
    {
        String controllerName = this.controller.getClass().getName();

        if(path == null || path.trim().isEmpty())
        {
            throw  new RuntimeException("Path is not set in controller: " + controllerName);
        }

        this.fullPath = path;
        String controllerPath = new String(path);
        if(controllerPath.contains("{controller}"))
        {


            String controllerSimpleName = controllerName.replace("Controller", "");
            controllerPath = controllerPath.replace("{controller}",controllerSimpleName);
        }
        if(path.contains("{method}"))
        {
            int position = controllerPath.indexOf("{method}");
            controllerPath = controllerPath.replace("{method}", "");
            if(controllerPath.length() >= position)
                throw new RuntimeException("Cannot add path after the {method}");
        }
        this.controllerPath = controllerPath;
    }

    private void setMethods()
    {
        this. methodList = new ArrayList<>();
        for (final Method method : this.controller.getClass().getDeclaredMethods()) {
            ControllerMethod controllerMethod = null;

            if (method.isAnnotationPresent(POST.class))
            {
                POST annotation = method.getAnnotation(POST.class);
                controllerMethod = new ControllerMethod(HTTPMethod.POST, method, method.getName());
            } else if (method.isAnnotationPresent(GET.class))
            {
                GET annotation = method.getAnnotation(GET.class);
                controllerMethod = new ControllerMethod(HTTPMethod.GET, method, method.getName());
            }
            else if (method.isAnnotationPresent(DELETE.class))
            {
                DELETE annotation = method.getAnnotation(DELETE.class);
                controllerMethod = new ControllerMethod(HTTPMethod.DELETE, method, method.getName());
            }
            else if (method.isAnnotationPresent(PUT.class))
            {
                PUT annotation = method.getAnnotation(PUT.class);
                controllerMethod = new ControllerMethod(HTTPMethod.PUT, method, method.getName());
            }

            if(controllerMethod != null)
                this.methodList.add(controllerMethod);
        }
    }
}
