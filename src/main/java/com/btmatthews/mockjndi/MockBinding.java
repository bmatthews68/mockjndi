package com.btmatthews.mockjndi;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public interface MockBinding {

    String getName();

    boolean isValid();

    void setValue(String name, String value);

    Object getBoundObject();
}
