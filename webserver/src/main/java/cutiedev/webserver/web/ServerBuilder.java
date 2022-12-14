package cutiedev.webserver.web;

import cutiedev.webserver.web.controller.Controller;
import cutiedev.webserver.web.controller.ServerController;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

public class ServerBuilder
{

    private int port;
    private String controllerPackage;
    List<ServerController> controllers;

    public ServerBuilder(String controllerPackage)
    {
    }

    public ServerBuilder port(String controllerPackage)
    {
        return this;
    }

    public void port(short port)
    {

    }

    public WebServer build()
    {
        isValid();
        return new WebServer(this);
    }

    protected int getPort() {
        return port;
    }

    private void isValid()
    {
        // TODO : check if valid port was entered

        // TODO : check if at least one controller exists
    }

    private void validateControllers(List<String> errors)
    {
        if(controllerPackage == null || controllerPackage.trim().isEmpty()) {
            errors.add("Path is not Set");
            return;
        }
        setController(errors);
    }

    private void setController(List<String> errors)
    {
        this.controllers = new ArrayList<>();

        Reflections reflections = new Reflections(controllerPackage);

        Set<Class<? extends Object>> allClasses =
                reflections.get(SubTypes.of(TypesAnnotated.with(Controller.class)).asClass());
        if(allClasses == null || allClasses.isEmpty()) {
            errors.add("No classes ");
            return;
        }
        for(Class<? extends Object> aClass:allClasses)
        {
            if(aClass.isAssignableFrom(Controller.class))
            {
                this.controllers.add(new ServerController((Class<Controller>) aClass));
            }
        }
    }
}
