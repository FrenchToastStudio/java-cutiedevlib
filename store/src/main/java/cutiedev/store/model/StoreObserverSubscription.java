package cutiedev.store.model;

@FunctionalInterface
public interface StoreObserverSubscription<T>
{
    void accept(T var1);
}
