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

import javax.naming.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public final class MockContext implements Context {

    private final String fullName;
    private final String name;
    private final NameParser nameParser;
    private final Map<String, MockContext> subContexts = new HashMap<String, MockContext>();
    private final Map<String, MockBinding> bindings = new HashMap<String, MockBinding>();
    private final Hashtable<String, Object> environment = new Hashtable<String, Object>();

    MockContext(final String fullName,
                final String name,
                final NameParser nameParser) {
        this.fullName = fullName;
        this.name = name;
        this.nameParser = nameParser;
    }

    boolean addMockContext(final String name, final MockContext context) {
        if (subContexts.containsKey(name) || bindings.containsKey(name)) {
            return false;
        } else {
            subContexts.put(name, context);
            return true;
        }
    }

    boolean addBinding(final MockBinding binding) {
        if (subContexts.containsKey(name) || bindings.containsKey(name)) {
            return false;
        } else {
            bindings.put(binding.getName(), binding);
            return true;
        }
    }

    @Override
    public Object lookup(final Name name) throws NamingException {
        if (name.size() == 0) {
            try {
                return this.clone();
            } catch (final CloneNotSupportedException e) {
                throw new NamingException();
            }
        }

        final String prefix = name.get(0);
        MockContext context = subContexts.get(prefix);
        if (context == null) {
            final MockBinding binding = bindings.get(prefix);
            if (binding == null) {
                throw new NameNotFoundException();
            } else {
                if (name.size() > 1) {
                    throw new NotContextException();
                } else {
                    return binding.getBoundObject();
                }
            }
        } else {
            if (name.size() > 1) {
                return context.lookup(name.getSuffix(1));
            } else {
                return context;
            }
        }
    }

    @Override
    public Object lookup(String name) throws NamingException {
        return lookup(nameParser.parse(name));
    }

    @Override
    public void bind(Name name, Object obj) throws NamingException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void bind(final String name,
                     final Object obj)
            throws NamingException {
        bind(nameParser.parse(name), obj);
    }

    @Override
    public void rebind(final Name name,
                       final Object obj)
            throws NamingException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void rebind(final String name,
                       final Object obj)
            throws NamingException {
        rebind(nameParser.parse(name), obj);
    }

    @Override
    public void unbind(final Name name)
            throws NamingException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void unbind(String name)
            throws NamingException {
        unbind(nameParser.parse(name));
    }

    @Override
    public void rename(final Name oldName,
                       final Name newName)
            throws NamingException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void rename(final String oldName,
                       final String newName)
            throws NamingException {
        rename(nameParser.parse(oldName), nameParser.parse(newName));
    }

    @Override
    public NamingEnumeration<NameClassPair> list(final Name name)
            throws NamingException {
        return visitContext(name, new MockContextVisitor<NamingEnumeration<NameClassPair>>() {
            @Override
            public NamingEnumeration<NameClassPair> empty() throws NamingException {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public NamingEnumeration<NameClassPair> visit(final MockContext context) throws NamingException {
                return context.list(name.getSuffix(1));
            }
        });
    }

    @Override
    public NamingEnumeration<NameClassPair> list(final String name)
            throws NamingException {
        return list(nameParser.parse(name));
    }

    @Override
    public NamingEnumeration<Binding> listBindings(final Name name)
            throws NamingException {
        return visitContext(name, new MockContextVisitor<NamingEnumeration<Binding>>() {
            @Override
            public NamingEnumeration<Binding> empty() throws NamingException {
                return new BindingsEnumeration(bindings.values());
            }

            @Override
            public NamingEnumeration<Binding> visit(final MockContext context) throws NamingException {
                return context.listBindings(name.getSuffix(1));
            }
        });
    }

    @Override
    public NamingEnumeration<Binding> listBindings(final String name)
            throws NamingException {
        return listBindings(nameParser.parse(name));
    }

    @Override
    public void destroySubcontext(final Name name)
            throws NamingException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroySubcontext(final String name)
            throws NamingException {
        destroySubcontext(nameParser.parse(name));
    }

    @Override
    public Context createSubcontext(final Name name)
            throws NamingException {
        switch (name.size()) {
            case 0:
                throw new NamingException();
            case 1: {
                if (bindings.containsValue(name.get(0)) || subContexts.containsValue(name.get(0))) {
                    throw new NameAlreadyBoundException();
                }
                final MockContext newContext = new MockContext(composeName(fullName, name.get(0)), name.get(0), nameParser);
                subContexts.put(name.get(0), newContext);
                return newContext;
            }
            default:
                if (bindings.containsKey(name.get(0)) || !subContexts.containsKey(name.get(0))) {
                    throw new NamingException();
                }
                return subContexts.get(name.get(0)).createSubcontext(name.getSuffix(1));
        }
    }

    @Override
    public Context createSubcontext(final String name)
            throws NamingException {
        return createSubcontext(nameParser.parse(name));
    }

    @Override
    public Object lookupLink(final Name name)
            throws NamingException {
        return lookup(name);
    }

    @Override
    public Object lookupLink(final String name)
            throws NamingException {
        return lookupLink(nameParser.parse(name));
    }

    @Override
    public NameParser getNameParser(final Name name)
            throws NamingException {
        return visitContext(name, new MockContextVisitor<NameParser>() {
            @Override
            public NameParser empty() throws NamingException {
                return nameParser;
            }

            @Override
            public NameParser visit(final MockContext context) throws NamingException {
                return context.getNameParser(name.getSuffix(1));
            }
        });
    }

    @Override
    public NameParser getNameParser(final String name)
            throws NamingException {
        return getNameParser(nameParser.parse(name));
    }

    @Override
    public Name composeName(final Name name,
                            final Name prefix)
            throws NamingException {
        return ((Name) prefix.clone()).addAll(name);
    }

    @Override
    public String composeName(final String name,
                              final String prefix)
            throws NamingException {
        return composeName(nameParser.parse(name), nameParser.parse(prefix)).toString();
    }

    @Override
    public Object addToEnvironment(final String propName,
                                   final Object propVal)
            throws NamingException {
        return environment.put(propName, propVal);
    }

    @Override
    public Object removeFromEnvironment(final String propName)
            throws NamingException {
        return environment.remove(propName);
    }

    @Override
    public Hashtable<?, ?> getEnvironment() throws
            NamingException {
        return environment;
    }

    @Override
    public void close() throws NamingException {
    }

    @Override
    public String getNameInNamespace() throws NamingException {
        return fullName;
    }

    private <T> T visitContext(final Name name, final MockContextVisitor<T> visitor) throws NamingException {
        if (name == null || name.isEmpty()) {
            return visitor.empty();
        }
        final String key = name.get(0);
        final MockContext subContext = subContexts.get(key);
        if (subContext == null) {
            if (bindings.containsKey(key)) {
                throw new NotContextException();
            } else {
                throw new NameNotFoundException();
            }
        }
        return visitor.visit(subContext);
    }
}
