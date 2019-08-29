package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ctt reason: default package */
/* compiled from: VoiceVolumeListenerFinishAction */
public class ctt extends vz {
    private JsAdapter a;
    private b b = new b() {
        public final void run() {
            ctw.a().e();
        }

        public final void reject() {
            ctw.a().e();
        }
    };

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        this.a = a();
        if (this.a != null) {
            kj.a(this.a.mPageContext.getActivity(), new String[]{"android.permission.RECORD_AUDIO"}, this.b);
        }
    }
}
