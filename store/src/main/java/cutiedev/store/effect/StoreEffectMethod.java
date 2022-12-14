package cutiedev.store.effect;

@FunctionalInterface
public interface StoreEffectMethod<T>
{
    void effect(T var1);
}
