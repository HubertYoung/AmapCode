package com.ali.auth.third.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.trace.SDKLogger;
import com.taobao.applink.util.TBAppLinkUtil;

class a extends AsyncTask<Object, Void, String> {
    final /* synthetic */ String a;
    final /* synthetic */ Activity b;
    final /* synthetic */ LoginComponent c;

    a(LoginComponent loginComponent, String str, Activity activity) {
        this.c = loginComponent;
        this.a = str;
        this.b = activity;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String doInBackground(Object... objArr) {
        SDKLogger.d("login", "showLogin doInBackground");
        return TextUtils.isEmpty(this.a) ? "" : this.c.generateTopAppLinkToken(this.a);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(String str) {
        SDKLogger.d("login", "showLogin onPostExecute signResult = ".concat(String.valueOf(str)));
        if (!TextUtils.isEmpty(str)) {
            Intent intent = new Intent();
            intent.setAction(TBAppLinkUtil.ACTION_CUSTOM);
            StringBuilder sb = new StringBuilder("tbopen://m.taobao.com/getway/oauth?&appkey=");
            sb.append(KernelContext.getAppKey());
            sb.append("&pluginName=taobao.oauth.code.create&apkSign=");
            sb.append(this.a);
            sb.append("&sign=");
            sb.append(str);
            intent.setData(Uri.parse(sb.toString()));
            if (this.b.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                this.b.startActivityForResult(intent, RequestCode.OPEN_TAOBAO);
            } else {
                this.c.showH5Login(this.b);
            }
        } else {
            this.c.showH5Login(this.b);
        }
    }
}
