package com.btmatthews.mockjndi;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class ObjectFactoryBinding extends AbstractBinding {

    private Class<ObjectFactory> objectFactoryClass;

    private ObjectFactory objectFactory;

    public ObjectFactoryBinding(final String name) {
        super(name);
    }

    public ObjectFactoryBinding(final String name, final ObjectFactory objectFactory) {
        super(name);
        this.objectFactory = objectFactory;
    }

    @Override
    public boolean isValid() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setValue(final String name,
                         final String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getBoundObject(/*final Name name,
                                 final Context context,
                                 final Hashtable<?, ?> environment*/) {
        //final ObjectFactory objectFactory = objectFactoryClass.newInstance();
        //return objectFactory.getObjectInstance(null, name, context, environment);
        return null;
    }
}
