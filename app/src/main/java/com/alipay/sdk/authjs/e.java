package com.alipay.sdk.authjs;

import android.widget.Toast;
import com.alipay.sdk.authjs.a.C0005a;
import java.util.Timer;
import org.json.JSONException;
import org.json.JSONObject;

final class e implements Runnable {
    final /* synthetic */ a a;
    final /* synthetic */ d b;

    e(d dVar, a aVar) {
        this.b = dVar;
        this.a = aVar;
    }

    public final void run() {
        d dVar = this.b;
        a aVar = this.a;
        if (aVar != null && "toast".equals(aVar.k)) {
            JSONObject jSONObject = aVar.m;
            String optString = jSONObject.optString("content");
            int i = 1;
            if (jSONObject.optInt("duration") < 2500) {
                i = 0;
            }
            Toast.makeText(dVar.b, optString, i).show();
            new Timer().schedule(new f(dVar, aVar), (long) i);
        }
        int i2 = C0005a.a;
        if (i2 != C0005a.a) {
            try {
                this.b.a(this.a.i, i2);
            } catch (JSONException unused) {
            }
        }
    }
}
