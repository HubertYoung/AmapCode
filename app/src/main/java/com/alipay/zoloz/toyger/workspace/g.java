package com.alipay.zoloz.toyger.workspace;

import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.zoloz.toyger.workspace.alert.AlertType;

/* compiled from: ToygerWorkspace */
final class g implements Runnable {
    final /* synthetic */ ToygerWorkspace a;

    g(ToygerWorkspace toygerWorkspace) {
        this.a = toygerWorkspace;
    }

    public final void run() {
        BioLog.d("zolozTime", "sendresponse liveness fail!");
        this.a.responseWithCode(this.a.mAlertHelper.getAlertReturnCode(AlertType.ALERT_REMOTE_COMMAND_FAIL_RETRY));
    }
}
