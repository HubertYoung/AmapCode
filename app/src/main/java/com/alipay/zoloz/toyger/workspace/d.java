package com.alipay.zoloz.toyger.workspace;

import com.alipay.mobile.security.bio.task.ActionFrame;

/* compiled from: ToygerWorkspace */
final class d implements Runnable {
    final /* synthetic */ ActionFrame a;
    final /* synthetic */ ToygerWorkspace b;

    d(ToygerWorkspace toygerWorkspace, ActionFrame actionFrame) {
        this.b = toygerWorkspace;
        this.a = actionFrame;
    }

    public final void run() {
        this.b.onDoAction(this.a);
    }
}
