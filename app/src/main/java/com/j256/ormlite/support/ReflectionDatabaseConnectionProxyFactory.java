package com.j256.ormlite.support;

import com.j256.ormlite.misc.SqlExceptionUtil;
import java.lang.reflect.Constructor;

public class ReflectionDatabaseConnectionProxyFactory implements DatabaseConnectionProxyFactory {
    private final Constructor<? extends DatabaseConnection> constructor;
    private final Class<? extends DatabaseConnection> proxyClass;

    public ReflectionDatabaseConnectionProxyFactory(Class<? extends DatabaseConnection> proxyClass2) {
        this.proxyClass = proxyClass2;
        try {
            this.constructor = proxyClass2.getConstructor(new Class[]{DatabaseConnection.class});
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not find constructor with DatabaseConnection argument in " + proxyClass2);
        }
    }

    public DatabaseConnection createProxy(DatabaseConnection realConnection) {
        try {
            return (DatabaseConnection) this.constructor.newInstance(new Object[]{realConnection});
        } catch (Exception e) {
            throw SqlExceptionUtil.create("Could not create a new instance of " + this.proxyClass, e);
        }
    }
}
