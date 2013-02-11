MockJNDI
========

MockJNDI implements the Java Naming and Directory Interface (JNDI) as a client library for use in creating unit and
integration tests that have a dependency on JDNI.

<?xml version="1.0" encoding="UTF-8"?>
<root>
    <context name="comp">
        <context name="env">
            <context name="jdbc">
                <binding name="myDS" type="javax.sql.DataSource">
                    <property name="url" value="jdbc:h2:tcp://localhost/mem:mydb" />
                    <property name="driver" path="org.h2.Driver" />
                    <property name="user" value="sa" />
                    <property name="password" value="" />
                </binding>
            </context>
        </context>
    </context>
</root>