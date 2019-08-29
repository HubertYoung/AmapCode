package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;
import java.util.ArrayList;
import java.util.List;

public final class a implements IDiffDevOAuth {
    /* access modifiers changed from: private */
    public List<OAuthListener> c = new ArrayList();
    /* access modifiers changed from: private */
    public d d;
    private OAuthListener e = new b(this);
    /* access modifiers changed from: private */
    public Handler handler = null;

    public final void addListener(OAuthListener oAuthListener) {
        if (!this.c.contains(oAuthListener)) {
            this.c.add(oAuthListener);
        }
    }

    public final boolean auth(String str, String str2, String str3, String str4, String str5, OAuthListener oAuthListener) {
        Log.i("MicroMsg.SDK.DiffDevOAuth", "start auth, appId = ".concat(String.valueOf(str)));
        if (str == null || str.length() <= 0 || str2 == null || str2.length() <= 0) {
            Log.d("MicroMsg.SDK.DiffDevOAuth", String.format("auth fail, invalid argument, appId = %s, scope = %s", new Object[]{str, str2}));
            return false;
        }
        if (this.handler == null) {
            this.handler = new Handler(Looper.getMainLooper());
        }
        addListener(oAuthListener);
        if (this.d != null) {
            Log.d("MicroMsg.SDK.DiffDevOAuth", "auth, already running, no need to start auth again");
            return true;
        }
        d dVar = new d(str, str2, str3, str4, str5, this.e);
        this.d = dVar;
        d dVar2 = this.d;
        if (VERSION.SDK_INT >= 11) {
            dVar2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            return true;
        }
        dVar2.execute(new Void[0]);
        return true;
    }

    public final void detach() {
        Log.i("MicroMsg.SDK.DiffDevOAuth", "detach");
        this.c.clear();
        stopAuth();
    }

    public final void removeAllListeners() {
        this.c.clear();
    }

    public final void removeListener(OAuthListener oAuthListener) {
        this.c.remove(oAuthListener);
    }

    public final boolean stopAuth() {
        boolean z;
        Log.i("MicroMsg.SDK.DiffDevOAuth", "stopAuth");
        try {
            z = this.d == null ? true : this.d.a();
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("stopAuth fail, ex = ");
            sb.append(e2.getMessage());
            Log.w("MicroMsg.SDK.DiffDevOAuth", sb.toString());
            z = false;
        }
        this.d = null;
        return z;
    }
}
