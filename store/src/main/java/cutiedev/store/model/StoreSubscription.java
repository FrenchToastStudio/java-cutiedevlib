package cutiedev.store.model;

import java.util.function.Consumer;

@FunctionalInterface
public interface StoreSubscription<T>
{
    void notify(T t);
    default Consumer<T> andThen(Consumer<? super T> after) {
        throw new RuntimeException();
    }
}
