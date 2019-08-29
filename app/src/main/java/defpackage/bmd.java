package defpackage;

import android.app.Activity;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.j;
import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bmd reason: default package */
/* compiled from: ExecAlipay */
public class bmd extends vz implements ans<bmb> {
    private String a = null;
    private JsAdapter b = null;
    private wa c = null;
    private bmc d = null;

    public final /* synthetic */ void a(Object obj) {
        bmb bmb = (bmb) obj;
        String str = bmb.b;
        String str2 = bmb.d;
        if (this.b != null && this.c != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("result", this.a);
                jSONObject.put(j.a, str2);
                jSONObject.put("memo", str);
                jSONObject.put("_action", this.c.b);
                this.b.callJs(this.c.a, jSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        this.b = a();
        this.c = waVar;
        if (this.b != null) {
            this.a = jSONObject.optString("orderStr");
            if (!TextUtils.isEmpty(this.a)) {
                if (this.d == null) {
                    this.d = new bmc(this.b.mPageContext.getActivity(), this);
                }
                ahm.a(new Runnable(this.a) {
                    final /* synthetic */ String a;

                    {
                        this.a = r2;
                    }

                    public final void run() {
                        if (bmc.this.b != null && bmc.this.b.get() != null) {
                            bmc.a(bmc.this, new PayTask((Activity) bmc.this.b.get()).pay(this.a, true));
                        }
                    }
                });
            }
        }
    }
}
