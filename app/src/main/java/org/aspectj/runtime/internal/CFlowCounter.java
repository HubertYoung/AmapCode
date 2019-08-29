package org.aspectj.runtime.internal;

import org.aspectj.runtime.internal.cflowstack.ThreadCounter;
import org.aspectj.runtime.internal.cflowstack.ThreadStackFactory;
import org.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl;
import org.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl11;

public class CFlowCounter {
    private static ThreadStackFactory a;
    private ThreadCounter b = a.getNewThreadCounter();

    static {
        c();
    }

    public void inc() {
        this.b.inc();
    }

    public void dec() {
        this.b.dec();
        if (!this.b.isNotZero()) {
            this.b.removeThreadCounter();
        }
    }

    public boolean isValid() {
        return this.b.isNotZero();
    }

    private static ThreadStackFactory a() {
        return new ThreadStackFactoryImpl();
    }

    private static ThreadStackFactory b() {
        return new ThreadStackFactoryImpl11();
    }

    private static void c() {
        boolean useThreadLocalImplementation = true;
        String override = a("aspectj.runtime.cflowstack.usethreadlocal", "unspecified");
        if (override.equals("unspecified")) {
            if (System.getProperty("java.class.version", "0.0").compareTo("46.0") < 0) {
                useThreadLocalImplementation = false;
            }
        } else if (!override.equals("yes") && !override.equals("true")) {
            useThreadLocalImplementation = false;
        }
        if (useThreadLocalImplementation) {
            a = a();
        } else {
            a = b();
        }
    }

    private static String a(String aPropertyName, String aDefaultValue) {
        try {
            return System.getProperty(aPropertyName, aDefaultValue);
        } catch (SecurityException e) {
            return aDefaultValue;
        }
    }

    public static String getThreadStackFactoryClassName() {
        return a.getClass().getName();
    }
}
