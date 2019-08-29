package com.ali.auth.third.core.task;

class d implements Runnable {
    final /* synthetic */ a a;

    d(a aVar) {
        this.a = aVar;
    }

    public void run() {
        if (this.a.a != null) {
            this.a.a.onSuccess();
        }
    }
}
