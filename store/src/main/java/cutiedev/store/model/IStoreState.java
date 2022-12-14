package cutiedev.store.model;

public abstract class IStoreState implements Cloneable {
    @Override
    public IStoreState clone() {
        try {
            return (IStoreState) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
