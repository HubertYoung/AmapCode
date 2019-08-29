package com.xiaomi.push.mpcd;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.xiaomi.push.mpcd.job.f;
import com.xiaomi.xmpush.thrift.d;
import com.xiaomi.xmpush.thrift.k;

public class a implements ActivityLifecycleCallbacks {
    private String a = "";
    private String b;
    private Context c;

    public a(Context context, String str) {
        this.c = context;
        this.a = str;
    }

    private void a(String str) {
        k kVar = new k();
        kVar.a(str);
        kVar.a(System.currentTimeMillis());
        kVar.a(d.ActivityActiveTimeStamp);
        f.a(this.c, kVar);
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
        String localClassName = activity.getLocalClassName();
        if (!TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(localClassName)) {
            this.b = "";
            if (TextUtils.isEmpty(this.b) || TextUtils.equals(this.b, localClassName)) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.c.getPackageName());
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(localClassName);
                sb.append(":");
                sb.append(this.a);
                sb.append(",");
                sb.append(String.valueOf(System.currentTimeMillis() / 1000));
                a(sb.toString());
                this.a = "";
                this.b = "";
                return;
            }
            this.a = "";
        }
    }

    public void onActivityResumed(Activity activity) {
        if (TextUtils.isEmpty(this.b)) {
            this.b = activity.getLocalClassName();
        }
        this.a = String.valueOf(System.currentTimeMillis() / 1000);
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }
}
