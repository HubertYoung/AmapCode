package com.ali.auth.third.accountlink;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.ali.auth.third.accountlink.a.a;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.service.UserTrackerService;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.login.LoginComponent;
import com.ali.auth.third.login.RequestCode;
import com.ali.auth.third.login.UTConstants;
import com.taobao.applink.util.TBAppLinkUtil;

class f extends AsyncTask<Object, Void, String> {
    final /* synthetic */ String a;
    final /* synthetic */ int b;
    final /* synthetic */ Activity c;
    final /* synthetic */ String d;
    final /* synthetic */ a e;

    f(a aVar, String str, int i, Activity activity, String str2) {
        this.e = aVar;
        this.a = str;
        this.b = i;
        this.c = activity;
        this.d = str2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String doInBackground(Object... objArr) {
        SDKLogger.d("login", "showBind doInBackground");
        return LoginComponent.INSTANCE.generateTopAppLinkToken(this.a);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(String str) {
        UserTrackerService userTrackerService;
        String str2;
        SDKLogger.d("login", "showBind onPostExecute signResult = ".concat(String.valueOf(str)));
        if (!TextUtils.isEmpty(str)) {
            Intent intent = new Intent();
            intent.setAction(TBAppLinkUtil.ACTION_CUSTOM);
            StringBuilder sb = new StringBuilder("tbopen://m.taobao.com/getway/oauth?&appkey=");
            sb.append(KernelContext.getAppKey());
            sb.append("&pluginName=taobao.oauth.code.create&apkSign=");
            sb.append(this.a);
            sb.append("&BaiChuanIBB4Bind=");
            sb.append(this.b);
            sb.append("&sign=");
            sb.append(str);
            intent.setData(Uri.parse(sb.toString()));
            if (this.c.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                int i = RequestCode.OPEN_TAOBAO;
                a.f = false;
                if (this.b == AccountLinkType.COOPERATION_TB_BIND) {
                    a.f = true;
                    userTrackerService = (UserTrackerService) KernelContext.getService(UserTrackerService.class);
                    str2 = UTConstants.E_COOOPERATION_BIND;
                } else if (this.b == AccountLinkType.COOPERATION_TB_LOGIN) {
                    userTrackerService = (UserTrackerService) KernelContext.getService(UserTrackerService.class);
                    str2 = UTConstants.E_COOOPERATION_LOGIN;
                } else if (this.b == AccountLinkType.COOPERATION_TB_TRUST_LOGIN) {
                    userTrackerService = (UserTrackerService) KernelContext.getService(UserTrackerService.class);
                    str2 = UTConstants.E_COOOPERATION_TRUST;
                } else {
                    if (this.b == AccountLinkType.COOPERATION_SYSTEM_ERROR) {
                        userTrackerService = (UserTrackerService) KernelContext.getService(UserTrackerService.class);
                        str2 = UTConstants.E_COOOPERATION_ERROR;
                    }
                    this.c.startActivityForResult(intent, RequestCode.OPEN_TAOBAO);
                    return;
                }
                userTrackerService.send(str2, null);
                this.c.startActivityForResult(intent, RequestCode.OPEN_TAOBAO);
                return;
            }
            this.e.b(this.c, this.b, this.d);
            return;
        }
        this.e.b(this.c, this.b, this.d);
    }
}
