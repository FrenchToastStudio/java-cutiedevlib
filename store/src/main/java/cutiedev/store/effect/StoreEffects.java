package cutiedev.store.effect;

import java.util.List;

public abstract class StoreEffects
{
    private List<StoreEffect> storeEffects;

    protected void setStoreEffects(List<StoreEffect> effects)
    {
        this.storeEffects = effects;
    }

    public List<StoreEffect> getStoreEffects() {
        return storeEffects;
    }
}
