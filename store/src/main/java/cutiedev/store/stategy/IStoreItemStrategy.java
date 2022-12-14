package cutiedev.store.stategy;

import cutiedev.store.stategy.model.StoringSecurity;

public interface IStoreItemStrategy
{

    void store(String name, Object obj);
    void store(String name, Object obj, StoringSecurity security);
}
