package defpackage;

import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ctu reason: default package */
/* compiled from: VoiceVolumeListenerRunAction */
public class ctu extends vz implements ctx<Integer, Integer> {
    /* access modifiers changed from: private */
    public JsAdapter a;
    private wa b;
    /* access modifiers changed from: private */
    public int c;
    private b d = new b() {
        public final void run() {
            ctw.a().e = ctu.this.a.mPageContext;
            ctw.a().c = ctu.this;
            ctw.a().b = ctu.this.c;
            ctw.a().b();
        }

        public final void reject() {
            AMapLog.i(ctw.d, "AudioRecord申请权限被拒绝");
            ctu.this.a(-1, 0);
        }
    };

    public final /* synthetic */ void a(Object obj, Object obj2) {
        a(((Integer) obj).intValue(), ((Integer) obj2).intValue());
    }

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        this.a = a();
        this.b = waVar;
        if (this.a != null && !ctw.a().a) {
            this.c = jSONObject.optInt(H5SensorPlugin.PARAM_INTERVAL);
            kj.a(this.a.mPageContext.getActivity(), new String[]{"android.permission.RECORD_AUDIO"}, this.d);
        }
    }

    /* access modifiers changed from: private */
    public void a(int i, int i2) {
        if (this.a != null && this.b != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("content", b(i, i2));
                jSONObject.put("_action", this.b.b);
                this.a.callJs(this.b.a, jSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static JSONObject b(int i, int i2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", i);
            jSONObject.put("volume", i2);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
