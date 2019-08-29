package com.xiaomi.slim;

class g extends Thread {
    final /* synthetic */ f a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    g(f fVar, String str) {
        // this.a = fVar;
        super(str);
    }

    public void run() {
        try {
            this.a.x.a();
        } catch (Exception e) {
            this.a.c(9, e);
        }
    }
}
