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

package com.btmatthews.mockjndi.core;

import com.btmatthews.mockjndi.core.object.ObjectBinding;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.naming.NameParser;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Stack;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public final class ConfigParserHandler extends DefaultHandler {
    private final NameParser nameParser;
    private final Map<String, MockContext> contexts = new HashMap<String, MockContext>();
    private final Stack<MockContext> contextStack = new Stack<MockContext>();
    private final Map<String, MockBindingFactory> factories = new HashMap<String, MockBindingFactory>();
    private MockBinding binding;

    public ConfigParserHandler(final MockContext root, final NameParser nameParser, final ClassLoader classLoader) {
        this.nameParser = nameParser;
        this.contextStack.push(root);
        final ServiceLoader<MockBindingFactory> factoryLoader = ServiceLoader.load(MockBindingFactory.class, classLoader);
        for (final MockBindingFactory factory : factoryLoader) {
            factories.put(factory.getType(), factory);
        }
    }

    @Override
    public void startElement(final String uri,
                             final String localName,
                             final String qName,
                             final Attributes attributes)
            throws SAXException {
        if (qName.equals("root")) {
        } else if (qName.equals("context")) {
            final String name = attributes.getValue("name");
            final MockContext context = new MockContext("", name, nameParser); // TODO full name
            contextStack.peek().addMockContext(name, context);
            contextStack.push(context);
        } else if (qName.equals("binding")) {
            final String name = attributes.getValue("name");
            final String type = attributes.getValue("type");
            final MockBindingFactory factory = factories.get(type);
            if (factory == null) {
                try {
                    final Class typeClass = Class.forName(type);
                    binding = new ObjectBinding(name, typeClass);
                } catch (final ClassNotFoundException e) {
                    // TODO
                }
            } else {
                binding = factory.createBinding(name);
            }
        } else if (qName.equals("property")) {
            final String name = attributes.getValue("name");
            final String value = attributes.getValue("value");
            if (binding == null) {
                // TODO
            } else if (name == null || value == null || name.length() == 0) {
                // TODO
            } else {
                binding.setValue(name, value);
            }
        }
    }

    @Override
    public void endElement(final String uri,
                           final String localName,
                           final String qName)
            throws SAXException {
        if (qName.equals("root")) {
        } else if (qName.equals("context")) {
            contextStack.pop();
        } else if (qName.equals("binding")) {
            if (binding == null) {
                // TODO Error - binding cannot be null!
            } else if (binding.isValid()) {
                contextStack.peek().addBinding(binding);
                binding = null;
            } else {
                // TODO Error - User configured binding incorrectly
            }
        } else if (qName.equals("property")) {
        }
    }
}
