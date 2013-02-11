package com.btmatthews.mockjndi;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.naming.Context;
import javax.naming.NameParser;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public final class ConfigParserHandler extends DefaultHandler {
    private final NameParser nameParser;
    private final Map<String, MockContext> contexts = new HashMap<String, MockContext>();
    private final Stack<MockContext> contextStack = new Stack<MockContext>();
    private MockBinding binding;

    public ConfigParserHandler(final MockContext root, final NameParser nameParser) {
        this.nameParser = nameParser;
        this.contextStack.push(root);
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
            if ("javax.sql.DataSource".equals(type)) {
                binding = new DataSourceBinding(name);
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
                // TODO Error - User configured binding incorrectly
            } else {
                contextStack.peek().addBinding(binding);
                binding = null;
            }
        } else if (qName.equals("property")) {
        }
    }
}