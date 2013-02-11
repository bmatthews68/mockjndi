package com.btmatthews.mockjndi;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public abstract class AbstractBinding implements MockBinding {

    private String name;

    protected AbstractBinding(final String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }
}
