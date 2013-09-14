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

package com.btmatthews.mockjndi.core.object;

import com.btmatthews.mockjndi.core.AbstractBinding;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public final class ObjectBinding extends AbstractBinding {

    private final Class type;
    private final Map<String, String> properties = new HashMap<String, String>();

    public ObjectBinding(final String name, final Class type) {
        super(name);
        this.type = type;
    }

    public ObjectBinding(final String name, final Object boundObject) {
        super(name, boundObject);
        this.type = boundObject.getClass();
    }

    @Override
    public boolean isValid() {
        return type != null && !type.isInterface();
    }

    @Override
    public void setValue(final String name,
                         final String value) {
        properties.put(name, value);
    }

    @Override
    public Object createBoundObject() {

        try {
            final Object object = type.newInstance();
            BeanUtils.populate(object, properties);
            return object;
         } catch (InstantiationException e) {
            // Error creating object
            return null;
        } catch (IllegalAccessException e) {
            // Error creating object
            return null;
        } catch (InvocationTargetException e) {
            // Error setting properties
            return null;
        }
    }
}
