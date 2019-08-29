package org.aspectj.runtime.internal;

import org.aspectj.lang.ProceedingJoinPoint;

public abstract class AroundClosure {
    protected int bitflags = 1048576;
    protected Object[] preInitializationState;
    protected Object[] state;

    public abstract Object run(Object[] objArr);

    public AroundClosure() {
    }

    public AroundClosure(Object[] state2) {
        this.state = state2;
    }

    public int getFlags() {
        return this.bitflags;
    }

    public Object[] getState() {
        return this.state;
    }

    public Object[] getPreInitializationState() {
        return this.preInitializationState;
    }

    public ProceedingJoinPoint linkClosureAndJoinPoint() {
        ProceedingJoinPoint jp = (ProceedingJoinPoint) this.state[this.state.length - 1];
        jp.set$AroundClosure(this);
        return jp;
    }

    public ProceedingJoinPoint linkClosureAndJoinPoint(int flags) {
        ProceedingJoinPoint jp = (ProceedingJoinPoint) this.state[this.state.length - 1];
        jp.set$AroundClosure(this);
        this.bitflags = flags;
        return jp;
    }
}
