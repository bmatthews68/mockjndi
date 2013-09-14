/*
 * Copyright 2013 Brian Matthews
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.btmatthews.mockjndi.transaction;

import com.btmatthews.mockjndi.core.MockBinding;
import com.btmatthews.mockjndi.core.MockBindingFactory;

import javax.transaction.UserTransaction;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class UserTransactionBindingFactory implements MockBindingFactory {
    @Override
    public String getType() {
        return UserTransaction.class.getName();
    }

    @Override
    public MockBinding createBinding(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
