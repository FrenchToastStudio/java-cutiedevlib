package cutiedev.store;

import cutiedev.store.action.StoreAction;
import cutiedev.store.effect.StoreEffect;
import cutiedev.store.effect.StoreEffects;
import cutiedev.store.model.IStoreState;
import cutiedev.store.model.Subject;
import cutiedev.store.model.SubscriptionObject;
import cutiedev.store.reducer.StoreReducer;
import cutiedev.store.selector.Selector;
import cutiedev.store.stategy.IStoreItemStrategy;
import cutiedev.tools.ObjectToolBox;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DevStore {

    private static DevStore instance;
    private IStoreState state;
    private IStoreItemStrategy storeItemStrategy;
    private List<SubscriptionObject> subscriptionObjects;
    private List<StoreReducer> reducers;
    private List<StoreEffects> effects;


    public DevStore(StoreBuilder builder) {
        this.storeItemStrategy = builder.getStoreItemStrategy();
        this.state = builder.getStoreState();
        this.reducers = builder.getReducer();
        this.effects = builder.getEffects();
        setReducers();

        instance = this;
    }

    public void execute(StoreAction action) {
        executeEffect(action);
        notifyStoreIfItemChanged();
        //updateReducers(action);
    }

    public Subject select(Class<Selector> selectorClass) {
        Selector selector = null;
        try {
            selector = selectorClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        for (StoreReducer storeReducer : this.reducers) {
            if (storeReducer.getStateType().equals(selector.getPropertyName())) {
                return storeReducer.getSubscription();
            }
        }

        //TODO: Handle Exception
        return null;
    }

    public static DevStore getInstance() {
        return instance;
    }
    private void executeEffect(StoreAction action)
    {
        for (StoreEffects effect: effects)
        {
            for (StoreEffect innerEffect :effect.getStoreEffects())
            {
                if (innerEffect.getType().equals(action.getType()))
                {
                    this.state = newState();
                    innerEffect.launchEffect(this.state);
                }
            }
        }
    }

    private void notifyStoreIfItemChanged()
    {
        Field[] fields = state.getClass().getFields();

        for (Field field : fields) {

            String name = field.getName();
            for (StoreReducer reducer: reducers)
            {

                SubscriptionObject subscription = reducer.getSubscription();
                if(subscription.getName().equals(name) )
                {
                    try {
                        Object o = field.get(state);
                        if(!subscription.getObject().equals(o))
                        {
                            subscription.setObject(o);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
        }
    }

    private void setReducers()
    {
        if(subscriptionObjects == null)
        {
            subscriptionObjects = new ArrayList<>();
        }
        for (StoreReducer reducer: reducers)
        {
            SubscriptionObject test =reducer.getSubscription();
            subscriptionObjects.add(test);
        }
    }

    private IStoreState newState()
    {
        IStoreState storeState;
        ObjectToolBox objectToolBox = new ObjectToolBox();
        try {
            storeState = state.getClass().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        Field[] fields = storeState.getClass().getFields();
        for (Field field: fields)
        {
            for (SubscriptionObject subscription: subscriptionObjects)
            {
                if(field.getName().equals(subscription.getName()))
                {
                    Object o = subscription.getObject();
                    try {
                        field.set(storeState, objectToolBox.cloneAllField(o));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return storeState;
    }
}
