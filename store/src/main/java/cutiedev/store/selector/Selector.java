package cutiedev.store.selector;

import cutiedev.store.annotation.StoreField;

public abstract class Selector implements ISelector
{
    public String getPropertyName(Object o)
    {
        Class aClass = o.getClass();
        StoreField field = (StoreField) aClass.getAnnotation(StoreField.class);
        if(field != null)
            return field.value();
        // TODO: handle this exception
        return null;
    }
}
