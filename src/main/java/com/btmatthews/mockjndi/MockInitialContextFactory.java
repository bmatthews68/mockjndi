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

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.naming.Context;
import javax.naming.NameParser;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class MockInitialContextFactory extends DefaultHandler implements InitialContextFactory {
    @Override
    public Context getInitialContext(final Hashtable<?, ?> environment) throws NamingException {
        final InputStream inputStream;
        try {
            final String configPath = (String) environment.get("com.btmatthews.mockjndi.config");
            if (configPath.startsWith("classpath:")) {
                inputStream = getClass().getResourceAsStream("/" + configPath.substring(10));
            } else if (configPath.startsWith("file://")) {
                inputStream = new FileInputStream(new File(configPath.substring(7)));
            } else {
                inputStream = new FileInputStream(new File(configPath));
            }
            if (inputStream != null) {
                try {
                    return loadInitialContext(inputStream);
                } finally {
                    inputStream.close();
                }
            }
        } catch (final IOException e) {
        } catch (final SAXException e) {
        } catch (final ParserConfigurationException e) {
        }
        return null;
    }

    private Context loadInitialContext(final InputStream inputStream)
            throws IOException, ParserConfigurationException, SAXException {
        final NameParser nameParser = new SimpleNameParser();
        final MockContext initialContext = new MockContext("", "", nameParser);
        final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        final SAXParser parser = parserFactory.newSAXParser();
        parser.parse(inputStream, new ConfigParserHandler(initialContext, nameParser));
        return initialContext;
    }
}
