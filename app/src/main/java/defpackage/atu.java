package defpackage;

import android.text.Html;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.amap.bundle.statistics.util.LogUtil;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import org.json.JSONException;
import org.json.JSONObject;

@BundleInterface(att.class)
/* renamed from: atu reason: default package */
/* compiled from: CloudSyncDialog */
public class atu extends esi implements att {
    public boolean a = false;
    /* access modifiers changed from: private */
    public bid b;
    private a c;
    private final String e = LogConstant.PAGE_MORE;
    private final String f = "B051";

    public final void a(bid bid) {
        this.b = bid;
        this.c = new a(this.b.getActivity());
        this.c.a(R.string.sync_dialog_title);
        this.c.b((CharSequence) Html.fromHtml(this.b.getContext().getString(R.string.sync_dialog_message)));
        this.c.a(R.string.sync_dialog_positive_button, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                bin.a.m(true);
                atu.a(true);
                atu.this.b.dismissViewLayer(alertView);
            }
        });
        this.c.b(R.string.sync_dialog_negative_button, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                bin.a.m(false);
                atu.a(false);
                atu.this.b.dismissViewLayer(alertView);
            }
        });
        this.c.b = new a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        this.c.c = new a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
    }

    public final void a() {
        this.c.a(this.a);
        AlertView a2 = this.c.a();
        this.b.showViewLayer(a2);
        a2.startAnimation();
    }

    public static void a(boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", z ? "sync" : BQCCameraParam.VALUE_NO);
            LogUtil.actionLogV2(LogConstant.PAGE_MORE, "B051", jSONObject);
        } catch (JSONException unused) {
        }
    }
}
