package org.aspectj.runtime.internal;

import java.util.Stack;
import org.aspectj.lang.NoAspectBoundException;
import org.aspectj.runtime.CFlow;
import org.aspectj.runtime.internal.cflowstack.ThreadStack;
import org.aspectj.runtime.internal.cflowstack.ThreadStackFactory;
import org.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl;
import org.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl11;

public class CFlowStack {
    private static ThreadStackFactory a;
    private ThreadStack b = a.getNewThreadStack();

    static {
        d();
    }

    private Stack a() {
        return this.b.getThreadStack();
    }

    public void push(Object obj) {
        a().push(obj);
    }

    public void pushInstance(Object obj) {
        a().push(new CFlow(obj));
    }

    public void push(Object[] obj) {
        a().push(new CFlowPlusState(obj));
    }

    public void pop() {
        Stack s = a();
        s.pop();
        if (s.isEmpty()) {
            this.b.removeThreadStack();
        }
    }

    public Object peek() {
        Stack stack = a();
        if (!stack.isEmpty()) {
            return stack.peek();
        }
        throw new NoAspectBoundException();
    }

    public Object get(int index) {
        CFlow cf = peekCFlow();
        if (cf == null) {
            return null;
        }
        return cf.get(index);
    }

    public Object peekInstance() {
        CFlow cf = peekCFlow();
        if (cf != null) {
            return cf.getAspect();
        }
        throw new NoAspectBoundException();
    }

    public CFlow peekCFlow() {
        Stack stack = a();
        if (stack.isEmpty()) {
            return null;
        }
        return (CFlow) stack.peek();
    }

    public CFlow peekTopCFlow() {
        Stack stack = a();
        if (stack.isEmpty()) {
            return null;
        }
        return (CFlow) stack.elementAt(0);
    }

    public boolean isValid() {
        return !a().isEmpty();
    }

    private static ThreadStackFactory b() {
        return new ThreadStackFactoryImpl();
    }

    private static ThreadStackFactory c() {
        return new ThreadStackFactoryImpl11();
    }

    private static void d() {
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
            a = b();
        } else {
            a = c();
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
