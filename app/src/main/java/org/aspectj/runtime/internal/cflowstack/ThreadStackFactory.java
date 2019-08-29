package org.aspectj.runtime.internal.cflowstack;

public interface ThreadStackFactory {
    ThreadCounter getNewThreadCounter();

    ThreadStack getNewThreadStack();
}
