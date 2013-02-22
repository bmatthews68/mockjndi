package com.btmatthews.mockjndi;

import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Hashtable;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class TestJNDILookup {

    private Context rootContext;

    @Before
    public void setUp() throws Exception {
        final Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.btmatthews.mockjndi.MockInitialContextFactory");
        environment.put("com.btmatthews.mockjndi.config", "classpath:mockjndi.xml");
        rootContext = new InitialContext(environment);
    }

    @Test
    public void lookupContext() throws NamingException {
        final Object obj = rootContext.lookup("java:comp");
        assertTrue(obj instanceof Context);
    }

    @Test
    public void lookupDataSource() throws NamingException {
        final Object obj = rootContext.lookup("java:comp/env/jdbc/myDS");
        assertTrue(obj instanceof DataSource);
    }

    @Test(expected = NameNotFoundException.class)
    public void lookupNonExistingRoot() throws NamingException {
        rootContext.lookup("java:root/datasource");
    }

    @Test(expected = NameNotFoundException.class)
    public void lookupNameWithNonExistingRoot() throws NamingException {
        rootContext.lookup("java:root/datasource");
    }

    @Test(expected = NameNotFoundException.class)
    public void lookupNameWithExistingRoot() throws NamingException {
        rootContext.lookup("java:comp/env/jdbc/yourDS");
    }
}
