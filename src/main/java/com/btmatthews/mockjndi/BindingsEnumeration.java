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

package com.btmatthews.mockjndi;

import javax.naming.Binding;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class BindingsEnumeration implements NamingEnumeration<Binding> {

    private final ListIterator<MockBinding> iterator;

    public BindingsEnumeration(final Collection<MockBinding> bindings) {
        iterator = new LinkedList<MockBinding>(bindings).listIterator();
    }

    @Override
    public Binding next() throws NamingException {
        return nextElement();
    }

    @Override
    public boolean hasMore() throws NamingException {
        return hasMoreElements();
    }

    @Override
    public void close() throws NamingException {
    }

    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    @Override
    public Binding nextElement() {
        final MockBinding binding = iterator.next();
        return new Binding(binding.getName(), binding.getBoundObject());
    }
}
