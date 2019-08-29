package defpackage;

import android.os.Handler;
import android.os.Message;
import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONObject;

/* renamed from: dnz reason: default package */
/* compiled from: RemoveCacheItemAction */
public class dnz extends vz {
    private Handler a;

    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a2 = a();
        if (a2 != null) {
            int optInt = jSONObject.optInt("itemId");
            String str = "failure";
            if (this.a != null) {
                Message message = new Message();
                message.obj = Integer.valueOf(optInt);
                this.a.sendMessage(message);
                str = "success";
                this.a = null;
            }
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("_action", "removeCacheItem");
                jSONObject2.put("message", str);
                a2.callJs(waVar.a, jSONObject.toString());
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
