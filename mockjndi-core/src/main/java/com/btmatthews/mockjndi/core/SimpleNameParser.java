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

import javax.naming.CompoundName;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.Properties;

/**
 * The {@link NameParser} for MockJNDI.
 *
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public class SimpleNameParser implements NameParser {

    /**
     * The environment properties that will be used to when constructing
     * new {@link CompoundName} objects.
     */
    private Properties properties = new Properties();

    /**
     * Initialise the {@link NameParser} for MockJNDI taking a copy of the
     * environment properties.
     *
     * @param properties The environment properties that will be used when constructing
     *                   new {@link CompoundName} objects.
     */
    SimpleNameParser(final Hashtable<?, ?> properties) {
        this.properties.putAll(properties);
    }

    /**
     * Parses a name into its components.
     *
     * @param name The name string to be parsed.
     * @return A {@link CompoundName} containing the parsed name.
     * @throws NamingException If a naming exception was encountered.
     */
    @Override
    public Name parse(final String name) throws NamingException {
        if (name == null) {
            return new CompoundName("", properties);
        } else if (name.startsWith("java:")) {
            return new CompoundName(name.substring(5), properties);
        } else {
            return new CompoundName(name, properties);
        }
    }
}
