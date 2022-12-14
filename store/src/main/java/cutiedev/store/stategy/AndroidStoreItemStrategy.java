package cutiedev.store.stategy;

import cutiedev.store.stategy.model.StoredItem;
import cutiedev.store.stategy.model.StoringSecurity;

import java.util.ArrayList;
import java.util.List;

public abstract class AndroidStoreItemStrategy implements IStoreItemStrategy
{

    protected List<StoredItem> storedItems;

    public void StoreItem()
    {

    }

    @Override
    public void store(String name, Object obj)
    {
        store(new StoredItem(name, obj));
    }

    @Override
    public void store(String name, Object obj, StoringSecurity security)
    {
        store(new StoredItem(name, obj, security));
    }

    public Object get(String name)
    {
        for (StoredItem item: storedItems)
        {
            if(item.getName().equals(name))
                return item.getObject();
        }

        // TODO : Trow an exception of type object not found
        return null;
    }

    protected void store(StoredItem storedItem)
    {
        if(this.storedItems == null)
        {
            this.storedItems = new ArrayList<>();
        }

        storedItems.add(storedItem);
    }
}
