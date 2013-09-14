package com.btmatthews.mockjndi.transaction;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.btmatthews.mockjndi.core.AbstractBinding;

import javax.transaction.SystemException;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public final class TransactionManagerBinding extends AbstractBinding {

    public TransactionManagerBinding(final String name) {
        super(name);
    }

    @Override
    protected Object createBoundObject() {
        final UserTransactionManager transactionManager = new UserTransactionManager();
        try {
            transactionManager.init();
            return transactionManager;
        } catch (final SystemException e) {
            return null;
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void setValue(final String name, final String value) {
    }
}
