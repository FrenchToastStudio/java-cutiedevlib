package cutiedev.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ObjectToolBox<T>
{

    public Object cloneAllField(Object object)
    {
        if (object instanceof Collection<?>)
        {
            return parseListObject(object);
        }

        return parseSimpleObject(object);
    }

    @SuppressWarnings("unchecked")
    private Object parseListObject(Object object)
    {

        ArrayList<Object> actualList = (ArrayList<Object>) object;
        ArrayList<Object> newList = new ArrayList<>();
        for (Object objectInList:actualList)
        {
            newList.add(cloneAllField(objectInList));
        }

        return newList;
    }


    private Object parseSimpleObject(Object object)
    {
        Object newObject;
        try {
            newObject = object.getClass().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field: fields)
        {
            field.setAccessible(true);
            if(field.getType() == Object.class)
            {
                try {
                    field.set(newObject, cloneAllField(object));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                try {
                    field.set(newObject, field.get(object));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            field.setAccessible(false);
        }

        return newObject;
    }

    public T getFieldOfType(Class<T> classTofind, Object objectToInspect, boolean instantiateIfNull)
    {

        for (Field field:objectToInspect.getClass().getDeclaredFields())
        {
            return parseField(field, classTofind, objectToInspect, instantiateIfNull);
        }

        // TODO : handle not found exception
        return null;
    }

    public List<T> getAllFieldOfType(Class<T> classTofind, Object objectToInspect, boolean instantiateIfNull)
    {
        List<T> objects = new ArrayList<>();

        for (Field field:objectToInspect.getClass().getDeclaredFields())
        {
            T object = parseField(field, classTofind, objectToInspect, instantiateIfNull);
            if(object != null)
                objects.add(object);
        }

        return objects;
    }
    @SuppressWarnings("unchecked")
    private T parseField(Field field, Class<T> classTofind, Object objectToInspect, boolean instantiateIfNull)
    {
        Class fieldClass = field.getType();
        boolean test =  classTofind.isAssignableFrom(fieldClass);
        if(!test)
            return null;

        try {
            Object object = field.get(objectToInspect);
            if(object == null && instantiateIfNull)
            {
                T newObject = (T) fieldClass.newInstance();
                field.set(objectToInspect, newObject);
                return newObject;
            }

            return (T) object;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
