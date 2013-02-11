package com.btmatthews.mockjndi;

import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class SimpleNameParser implements NameParser {

    @Override
    public Name parse(final String name) throws NamingException {
        if (name.startsWith("java:")) {
            return new CompositeName(name.substring(5));
        } else {
            return new CompositeName(name);
        }
    }
}
