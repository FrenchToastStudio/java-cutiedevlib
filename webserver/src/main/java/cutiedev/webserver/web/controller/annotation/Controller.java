package cutiedev.webserver.web.controller.annotation;

import cutiedev.webserver.web.data.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Controller
{
    String path();
    DataType defaultReturnType() default DataType.HTML;
}
