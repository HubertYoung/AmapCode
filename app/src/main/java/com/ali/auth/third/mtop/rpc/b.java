package com.ali.auth.third.mtop.rpc;

import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.taobao.tao.remotebusiness.login.onLoginListener;

class b implements LoginCallback {
    final /* synthetic */ onLoginListener a;
    final /* synthetic */ MtopRemoteLoginImpl b;

    b(MtopRemoteLoginImpl mtopRemoteLoginImpl, onLoginListener onloginlistener) {
        this.b = mtopRemoteLoginImpl;
        this.a = onloginlistener;
    }

    public void onFailure(int i, String str) {
        if (this.a != null) {
            if (i == 10003) {
                this.a.onLoginCancel();
                return;
            }
            this.a.onLoginFail();
        }
    }

    public void onSuccess(Session session) {
        if (this.a != null) {
            this.a.onLoginSuccess();
        }
    }
}
