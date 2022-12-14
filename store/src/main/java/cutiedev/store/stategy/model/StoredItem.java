package cutiedev.store.stategy.model;

public class StoredItem
{
    private String name;
    private StoringSecurity security;
    private Object object;

    // TODO add a way to change the default security level
    public StoredItem(String name, Object object)
    {
        this(name,  object, StoringSecurity.Low);
    }

    public StoredItem(String name, Object object, StoringSecurity security)
    {
        this.name =name;
        this.security = security;
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public Object getObject() {
        return object;
    }
}
