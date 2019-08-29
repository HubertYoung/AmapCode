package com.ali.auth.third.accountlink;

import android.content.Intent;
import android.os.AsyncTask;
import com.ali.auth.third.accountlink.a.a;
import com.ali.auth.third.accountlink.ui.BindResultActivity;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.ui.context.CallbackContext;

class h extends AsyncTask<Object, Void, IbbParams> {
    final /* synthetic */ LoginCallback a;
    final /* synthetic */ g b;

    h(g gVar, LoginCallback loginCallback) {
        this.b = gVar;
        this.a = loginCallback;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public IbbParams doInBackground(Object... objArr) {
        SDKLogger.d("bind", "dispatchBindEvent getBindIbb");
        return this.b.c.getBindIbb();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(IbbParams ibbParams) {
        super.onPostExecute(ibbParams);
        if (ibbParams != null) {
            StringBuilder sb = new StringBuilder("dispatchBindEvent ibbParams.code=");
            sb.append(ibbParams.code);
            sb.append(" ibbParams.trustLoginToken=");
            sb.append(ibbParams.trustLoginToken);
            sb.append(" ibbParams.ibb=");
            sb.append(ibbParams.ibb);
            SDKLogger.d("bind", sb.toString());
            if (ibbParams.code == AccountLinkType.COOPERATION_TB_BIND) {
                a.f = true;
                a.a.a(AccountLinkType.COOPERATION_TB_BIND, ibbParams == null ? "" : ibbParams.ibb, this.a);
            } else if (ibbParams.code == AccountLinkType.COOPERATION_TB_LOGIN) {
                a.a.a(AccountLinkType.COOPERATION_TB_LOGIN, ibbParams == null ? "" : ibbParams.ibb, this.a);
            } else if (ibbParams.code == AccountLinkType.COOPERATION_TB_TRUST_LOGIN) {
                SDKLogger.d("bind", "handleTrustLoginEvent");
                CallbackContext.loginCallback = this.a;
                Intent intent = new Intent();
                intent.setClass(KernelContext.context, BindResultActivity.class);
                intent.putExtra("token", ibbParams.trustLoginToken);
                intent.putExtra("ibb", ibbParams.ibb);
                intent.putExtra("type", 3);
                intent.setFlags(268435456);
                KernelContext.context.startActivity(intent);
            } else {
                a.a.a(ibbParams.code, (String) "", this.a);
            }
        } else {
            a.a.a(AccountLinkType.COOPERATION_TB_LOGIN, (String) "", this.a);
        }
    }
}
