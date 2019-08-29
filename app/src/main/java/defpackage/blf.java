package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.framework.service.common.SchemeService;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: blf reason: default package */
/* compiled from: LoadSchemaAction */
public class blf extends vz {
    private static final String a = "blf";

    public final void a(JSONObject jSONObject, wa waVar) {
        final JsAdapter a2 = a();
        if (a2 != null) {
            String optString = jSONObject.optString("url");
            String optString2 = jSONObject.optString("target");
            String str = a;
            StringBuilder sb = new StringBuilder("url:");
            sb.append(optString);
            sb.append(",target:");
            sb.append(optString2);
            AMapLog.i(str, sb.toString());
            int i = 1;
            if (!TextUtils.isEmpty(optString)) {
                Uri parse = Uri.parse(optString);
                Intent intent = new Intent();
                intent.putExtra("owner", "js");
                intent.setData(parse);
                List<ResolveInfo> queryIntentActivities = a2.mPageContext.getContext().getPackageManager().queryIntentActivities(intent, 131072);
                if (queryIntentActivities != null && queryIntentActivities.size() > 0) {
                    if (SchemeService.SCHEME_REVEAL.equalsIgnoreCase(parse.getScheme())) {
                        intent.addFlags(268435456);
                        a2.mPageContext.startActivity(intent);
                    } else {
                        a2.mPageContext.startScheme(intent);
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("status", i);
                    jSONObject2.put("_action", waVar.b);
                    a2.callJs(waVar.a, jSONObject2.toString());
                } else if (BehavorReporter.PROVIDE_BY_ALIPAY.equalsIgnoreCase(optString2)) {
                    Activity activity = a2.mPageContext.getActivity();
                    if (activity != null) {
                        a aVar = new a(a2.mPageContext.getActivity());
                        aVar.a((CharSequence) activity.getResources().getString(R.string.alipay_down_alert));
                        aVar.a(R.string.download, (a) new a() {
                            public final void onClick(AlertView alertView, int i) {
                                a2.mPageContext.dismissViewLayer(alertView);
                                ddq ddq = (ddq) a.a.a(ddq.class);
                                if (ddq != null) {
                                    ddq.d();
                                }
                                LogManager.actionLogV2(LogConstant.POI_DETAIL_PAGE_ID, "B002");
                            }
                        });
                        aVar.b(R.string.Cancel, (a) new a() {
                            public final void onClick(AlertView alertView, int i) {
                                a2.mPageContext.dismissViewLayer(alertView);
                                LogManager.actionLogV2(LogConstant.POI_DETAIL_PAGE_ID, "B003");
                            }
                        });
                        aVar.a(true);
                        AlertView a3 = aVar.a();
                        a2.mPageContext.showViewLayer(a3);
                        a3.startAnimation();
                        LogManager.actionLogV2(LogConstant.POI_DETAIL_PAGE_ID, "B001");
                    }
                }
            }
            i = 0;
            try {
                JSONObject jSONObject22 = new JSONObject();
                jSONObject22.put("status", i);
                jSONObject22.put("_action", waVar.b);
                a2.callJs(waVar.a, jSONObject22.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
