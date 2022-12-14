package cutiedev.store.facade;

import cutiedev.store.model.Subject;

public interface IStoreFacade
{
    Subject select(Class selector);
}
