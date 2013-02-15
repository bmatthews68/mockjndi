package com.btmatthews.mockjndi;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class ObjectBinding extends AbstractBinding {

    private Object object;

    public ObjectBinding(final String name,
                         final Object object) {
        super(name);
        this.object = object;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void setValue(final String name,
                         final String value) {
    }

    @Override
    public Object getBoundObject() {
        return object;
    }
}
