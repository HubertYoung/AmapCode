package org.aspectj.runtime.internal.cflowstack;

import java.util.Stack;

public interface ThreadStack {
    Stack getThreadStack();

    void removeThreadStack();
}
