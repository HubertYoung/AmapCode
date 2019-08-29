package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.map.widget.ProgressDlg;
import org.json.JSONObject;

/* renamed from: bly reason: default package */
/* compiled from: ToggleLoadingAction */
public class bly extends vz {
    public ProgressDlg a = null;

    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a2 = a();
        if (a2 != null) {
            String optString = jSONObject.optString("text");
            if (jSONObject.optInt("type") == 1) {
                if (this.a == null) {
                    this.a = new ProgressDlg(a2.mPageContext.getActivity());
                }
                this.a.setMessage(optString);
                this.a.show();
                return;
            }
            if (this.a != null) {
                this.a.dismiss();
                this.a = null;
            }
        }
    }
}
