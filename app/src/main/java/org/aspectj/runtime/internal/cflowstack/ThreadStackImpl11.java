package org.aspectj.runtime.internal.cflowstack;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

public class ThreadStackImpl11 implements ThreadStack {
    private Hashtable a = new Hashtable();
    private Thread b;
    private Stack c;
    private int d = 0;

    public synchronized Stack getThreadStack() {
        if (Thread.currentThread() != this.b) {
            this.b = Thread.currentThread();
            this.c = (Stack) this.a.get(this.b);
            if (this.c == null) {
                this.c = new Stack();
                this.a.put(this.b, this.c);
            }
            this.d++;
            if (this.d > Math.max(100, 20000 / Math.max(1, this.a.size()))) {
                Stack dead_stacks = new Stack();
                Enumeration e = this.a.keys();
                while (e.hasMoreElements()) {
                    Thread t = (Thread) e.nextElement();
                    if (!t.isAlive()) {
                        dead_stacks.push(t);
                    }
                }
                Enumeration e2 = dead_stacks.elements();
                while (e2.hasMoreElements()) {
                    this.a.remove((Thread) e2.nextElement());
                }
                this.d = 0;
            }
        }
        return this.c;
    }

    public void removeThreadStack() {
    }
}
