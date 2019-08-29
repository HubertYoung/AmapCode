package defpackage;

import android.os.Handler;
import android.os.Looper;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bgk reason: default package */
/* compiled from: TipModel */
public final class bgk extends bgd {
    private Handler a = new Handler(Looper.getMainLooper());

    public final String a() {
        return "tip";
    }

    public final boolean a(final bgb bgb, final bfb bfb) {
        this.a.post(new Runnable() {
            public final void run() {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("tip", bgb.h);
                    jSONObject.put("result", jSONObject2);
                    bfb.a(bgb.a, 10000, jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    String str = bgb == null ? "" : bgb.f;
                    bfq bfq = b.a;
                    StringBuilder sb = new StringBuilder("error");
                    sb.append(e.getMessage());
                    sb.append(" taskId=");
                    sb.append(str);
                    bfp.a(bfq, 1, sb.toString());
                }
            }
        });
        return true;
    }
}
