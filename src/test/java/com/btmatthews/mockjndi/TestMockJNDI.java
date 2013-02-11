package com.btmatthews.mockjndi;

import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.Hashtable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class TestMockJNDI {

    @Test
    public void createInitialContext() throws Exception {
        final Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.btmatthews.mockjndi.MockInitialContextFactory");
        environment.put("com.btmatthews.mockjndi.config", "classpath:mockjndi.xml");
        new InitialContext(environment);
    }


    @Test
    public void lookupDataSource() throws Exception {
        final Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.btmatthews.mockjndi.MockInitialContextFactory");
        environment.put("com.btmatthews.mockjndi.config", "classpath:mockjndi.xml");
        final Object dataSource = new InitialContext(environment).lookup("java:comp/env/jdbc/myDS");
        assertNotNull(dataSource);
        assertTrue(dataSource instanceof DataSource);
    }
}
