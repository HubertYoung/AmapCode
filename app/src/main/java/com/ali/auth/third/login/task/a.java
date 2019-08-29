package com.ali.auth.third.login.task;

class a implements Runnable {
    final /* synthetic */ AbsLoginByCodeTask a;

    a(AbsLoginByCodeTask absLoginByCodeTask) {
        this.a = absLoginByCodeTask;
    }

    public void run() {
        this.a.doWhenResultOk();
    }
}
