package org.aspectj.runtime.internal.cflowstack;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class ThreadCounterImpl11 implements ThreadCounter {
    private Hashtable a = new Hashtable();
    private Thread b;
    private Counter c;
    private int d = 0;

    class Counter {
        protected int value = 0;

        Counter() {
        }
    }

    private synchronized Counter a() {
        if (Thread.currentThread() != this.b) {
            this.b = Thread.currentThread();
            this.c = (Counter) this.a.get(this.b);
            if (this.c == null) {
                this.c = new Counter();
                this.a.put(this.b, this.c);
            }
            this.d++;
            if (this.d > Math.max(100, 20000 / Math.max(1, this.a.size()))) {
                List<Thread> dead_stacks = new ArrayList<>();
                Enumeration e = this.a.keys();
                while (e.hasMoreElements()) {
                    Thread t = (Thread) e.nextElement();
                    if (!t.isAlive()) {
                        dead_stacks.add(t);
                    }
                }
                for (Thread t2 : dead_stacks) {
                    this.a.remove(t2);
                }
                this.d = 0;
            }
        }
        return this.c;
    }

    public void inc() {
        Counter a2 = a();
        a2.value++;
    }

    public void dec() {
        Counter a2 = a();
        a2.value--;
    }

    public boolean isNotZero() {
        return a().value != 0;
    }

    public void removeThreadCounter() {
    }
}
