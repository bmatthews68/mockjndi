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
package com.btmatthews.mockjndi.core.objectfactory;

import com.btmatthews.mockjndi.core.AbstractBinding;

import javax.naming.spi.ObjectFactory;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public final class ObjectFactoryBinding extends AbstractBinding {

    private Class<ObjectFactory> objectFactoryClass;

    private ObjectFactory objectFactory;

    public ObjectFactoryBinding(final String name) {
        super(name);
    }

    public ObjectFactoryBinding(final String name, final ObjectFactory objectFactory) {
        super(name);
        this.objectFactory = objectFactory;
    }

    @Override
    public boolean isValid() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setValue(final String name,
                         final String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object createBoundObject() {
        //final ObjectFactory objectFactory = objectFactoryClass.newInstance();
        //return objectFactory.getObjectInstance(null, name, context, environment);
        return null;
    }
}
