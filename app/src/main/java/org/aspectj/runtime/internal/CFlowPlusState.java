package org.aspectj.runtime.internal;

import org.aspectj.runtime.CFlow;

public class CFlowPlusState extends CFlow {
    private Object[] a;

    public CFlowPlusState(Object[] state) {
        this.a = state;
    }

    public CFlowPlusState(Object[] state, Object _aspect) {
        super(_aspect);
        this.a = state;
    }

    public Object get(int index) {
        return this.a[index];
    }
}
