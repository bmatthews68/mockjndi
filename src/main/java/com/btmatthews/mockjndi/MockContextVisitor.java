package com.btmatthews.mockjndi;

import javax.naming.NamingException;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public interface MockContextVisitor<T> {

    T empty() throws NamingException;

    T visit(MockContext context) throws NamingException;
}
