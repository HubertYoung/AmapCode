package org.aspectj.runtime.internal.cflowstack;

public interface ThreadCounter {
    void dec();

    void inc();

    boolean isNotZero();

    void removeThreadCounter();
}
