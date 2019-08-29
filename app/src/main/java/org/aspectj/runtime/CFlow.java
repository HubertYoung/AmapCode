package org.aspectj.runtime;

public class CFlow {
    private Object a;

    public CFlow() {
        this(null);
    }

    public CFlow(Object _aspect) {
        this.a = _aspect;
    }

    public Object getAspect() {
        return this.a;
    }

    public void setAspect(Object _aspect) {
        this.a = _aspect;
    }

    public Object get(int index) {
        return null;
    }
}
