package cutiedev.store.model;

public class SubscriptionObject extends Subject
{
    private String name;
    private Object object;

    public SubscriptionObject(String name, Object obj)
    {
        this.name  = name;
        this.object = obj;
    }
    public SubscriptionObject(Object obj)
    {
        this.object = obj;
    }

    @Override
    public Object getState() {
        return getObject();
    }

    public String getName() {
        return name;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
        notifyObserverOfChange();
    }
}
