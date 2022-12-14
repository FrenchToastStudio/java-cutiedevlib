package cutiedev.store.reducer;

import cutiedev.store.model.SubscriptionObject;
import cutiedev.store.selector.Selector;

/**
 * Represents the state of an item in the store.
 */
public class StoreReducer
{
    private String actionType;
    private String stateType;
    private SubscriptionObject subscription;

    /**
     *
     * @param actionType the action on which this selector will react to
     * @param selector the object that is represented by the selector
     */
    public StoreReducer(String actionType, Class selector)
    {
        this("", actionType, selector, new SubscriptionObject(null));
    }

    /**
     *
     * @param action the action on which this selector will react to
     * @param selector the item this selector is associated to
     * @param initialState initialState of the object
     */
    public StoreReducer(String stateFieldName, String action, Class selector,Object initialState)
    {
        this.actionType = action;
        this.subscription = new SubscriptionObject(stateFieldName,initialState);

        // TODO: handle exception better
        try {
            this.stateType = ((Selector)selector.newInstance()).getPropertyName();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public String getActionType()
    {
        return this.actionType;
    }

    public SubscriptionObject getSubscription() {
        return subscription;
    }

    public String getStateType()
    {
        return this.stateType;
    }

    public void notifyObserver()
    {
        subscription.notifyObserverOfChange();
    }
}
