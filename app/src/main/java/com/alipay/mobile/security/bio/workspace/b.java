package com.alipay.mobile.security.bio.workspace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alipay.mobile.security.bio.config.Constant;
import com.alipay.mobile.security.bio.utils.BioLog;

/* compiled from: BioFragmentContainer */
final class b extends BroadcastReceiver {
    final /* synthetic */ BioFragmentContainer a;

    b(BioFragmentContainer bioFragmentContainer) {
        this.a = bioFragmentContainer;
    }

    public final void onReceive(Context context, Intent intent) {
        if (Constant.BIOLOGY_FLAG_AUTOCLOSE.equals(intent.getAction())) {
            this.a.verifyCallBackEvent();
            BioLog.i("verifyCallBackEvent rev");
            this.a.commandFinished();
        }
        this.a.onReceiveAction(intent);
    }
}
