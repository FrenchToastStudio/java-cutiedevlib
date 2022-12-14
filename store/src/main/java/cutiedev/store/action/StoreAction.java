package cutiedev.store.action;

/**
 * Represent an action that a user can take. Will be loaded autmatically when create as a property of StoreActionContext
 */
public class StoreAction
{

    private String type;
    private Class selector;

    public StoreAction(String type, Class selector)
    {
        this.type =type;
        this.selector = selector;
    }

    public String getType() {
        return type;
    }
}
