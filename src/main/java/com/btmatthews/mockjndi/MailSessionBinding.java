package com.btmatthews.mockjndi;

import javax.mail.Session;
import java.util.Properties;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class MailSessionBinding extends AbstractBinding {

    private Session session;
    private Properties properties = new Properties();

    public MailSessionBinding(final String name) {
        super(name);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void setValue(final String name, final String value) {
        properties.put(name, value);
    }

    @Override
    public Object getBoundObject() {
        if (session == null) {
            session = Session.getInstance(properties);
        }
        return session;
    }
}
