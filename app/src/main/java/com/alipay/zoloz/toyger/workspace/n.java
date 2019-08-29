package com.alipay.zoloz.toyger.workspace;

import com.alipay.zoloz.toyger.workspace.alert.AlertType;

/* compiled from: ToygerWorkspace */
final class n implements Runnable {
    final /* synthetic */ m a;

    n(m mVar) {
        this.a = mVar;
    }

    public final void run() {
        this.a.a.mAlertHelper.alert(AlertType.ALERT_TIMEOUT);
    }
}
