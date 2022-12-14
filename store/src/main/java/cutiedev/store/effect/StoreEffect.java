package cutiedev.store.effect;

import cutiedev.store.model.IStoreState;

public abstract class StoreEffect
{
    private String type;

    public StoreEffect(String type)
    {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public abstract void launchEffect(IStoreState appState);
}
