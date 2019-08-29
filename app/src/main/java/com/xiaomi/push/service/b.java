package com.xiaomi.push.service;

class b implements Runnable {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void run() {
        String a2 = this.a.b(this.a.j);
        synchronized (this.a.i) {
            this.a.l = a2;
            this.a.o = true;
        }
        synchronized (this.a.g) {
            this.a.g.notifyAll();
        }
    }
}
