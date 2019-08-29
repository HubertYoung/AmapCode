package com.xiaomi.metoknlp.devicediscover;

import android.os.AsyncTask;
import com.xiaomi.metoknlp.a.b;
import org.json.JSONException;
import org.json.JSONObject;

class k extends AsyncTask {
    final /* synthetic */ n a;
    private boolean b;

    private k(n nVar) {
        this.a = nVar;
        this.b = true;
    }

    /* synthetic */ k(n nVar, b bVar) {
        this(nVar);
    }

    private String b(String str) {
        String a2 = b.a(str, null);
        if (a2 != null) {
            try {
                return new JSONObject(a2).getString("real-ip");
            } catch (JSONException unused) {
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String doInBackground(String... strArr) {
        if (this.b) {
            try {
                return b(strArr[0]);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(String str) {
        if (this.b) {
            this.a.j.sendMessage(this.a.j.obtainMessage(3, str));
        }
    }

    /* access modifiers changed from: protected */
    public void onCancelled() {
        this.b = false;
    }
}
