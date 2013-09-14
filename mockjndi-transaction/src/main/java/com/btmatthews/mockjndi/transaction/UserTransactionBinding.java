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

import com.btmatthews.mockjndi.core.AbstractBinding;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class UserTransactionBinding extends AbstractBinding {

    public UserTransactionBinding(final String name) {
        super(name);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void setValue(String name, String value) {
    }

    @Override
    public Object createBoundObject() {
        return null;
    }
}
