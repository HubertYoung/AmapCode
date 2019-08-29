package defpackage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback.a;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import org.json.JSONObject;

/* renamed from: blk reason: default package */
/* compiled from: PromptMessageAction */
public class blk extends vz {
    private static ProgressDlg a;

    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a2 = a();
        if (a2 != null) {
            String optString = jSONObject.optString("message");
            int optInt = jSONObject.optInt("type");
            if (optInt == 0) {
                ToastHelper.showLongToast(optString);
                return;
            }
            if (optInt == 1) {
                if (a2.mPageContext != null && AMapPageUtil.getPageContext() == a2.mPageContext) {
                    ProgressDlg progressDlg = new ProgressDlg(AMapAppGlobal.getTopActivity(), optString);
                    a = progressDlg;
                    progressDlg.setOnCancelListener(new OnCancelListener() {
                        final /* synthetic */ a a = null;

                        public final void onCancel(DialogInterface dialogInterface) {
                            if (this.a != null) {
                                this.a.cancel();
                            }
                        }
                    });
                    a.show();
                }
            } else if (optInt == 3) {
                if (a2.mPageContext != null && AMapPageUtil.getPageContext() == a2.mPageContext) {
                    a2.showTimeToast(optString);
                }
            } else if (optInt == -1) {
                if (a != null) {
                    a.dismiss();
                    a = null;
                }
                a2.closeTimeToast();
            }
        }
    }
}
