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

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author <a href="mailto:brian@btmatthews.com">Brian Matthews</a>
 * @since 1.0.0
 */
public final class DataSourceBinding extends AbstractBinding {

    private BasicDataSource dataSource;
    private String driver;
    private String url;
    private String user;
    private String password;

    public DataSourceBinding(final String name) {
        super(name);
    }

    @Override
    public boolean isValid() {
        return driver != null && url != null;
    }

    @Override
    public void setValue(final String name, final String value) {
        if ("driver".equals(name)) {
            driver = value;
        } else if ("url".equals(name)) {
            url = value;
        } else if ("user".equals(name)) {
            user = value;
        } else if ("password".equals(name)) {
            password = value;
        }
    }

    @Override
    public Object getBoundObject() {
        if (dataSource == null && isValid()) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(driver);
            dataSource.setUrl(url);
            if (user != null) {
                dataSource.setUsername(user);
                if (password != null) {
                    dataSource.setPassword(password);
                }
            }
        }
        return dataSource;
    }
}
