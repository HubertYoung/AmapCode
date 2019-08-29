package defpackage;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3DialogPage;

/* renamed from: ddc reason: default package */
/* compiled from: SharedPassphraseManager */
public final class ddc {
    public ddg a;
    private final int b;
    private final int c;

    /* renamed from: ddc$a */
    /* compiled from: SharedPassphraseManager */
    public static class a {
        public static ddc a = new ddc(0);
    }

    /* synthetic */ ddc(byte b2) {
        this();
    }

    private ddc() {
        this.b = 5000;
        this.c = 1;
        this.a = new ddg(AMapAppGlobal.getApplication());
    }

    /* access modifiers changed from: 0000 */
    public final void a(final int i) {
        aho.a(new Runnable() {
            public final void run() {
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null && pageContext.isAlive() && pageContext.getContext() != null) {
                    dda dda = new dda(pageContext, i);
                    dda.onCreate(null);
                    dda.a = false;
                    dda.show();
                    if (pageContext != null) {
                        Context context = pageContext.getContext();
                        if (context != null) {
                            View contentView = pageContext.getContentView();
                            if (contentView != null) {
                                IBinder windowToken = contentView.getWindowToken();
                                if (windowToken != null) {
                                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
                                    if (inputMethodManager != null && inputMethodManager.isActive()) {
                                        inputMethodManager.hideSoftInputFromWindow(windowToken, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public final void a(final j jVar) {
        aho.a(new Runnable() {
            public final void run() {
                if (jVar != null) {
                    try {
                        dcs dcs = jVar.j;
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put((String) "shortMessage", (Object) dcs.a);
                        jSONObject.put((String) "backgroundColor", (Object) dcs.b);
                        jSONObject.put((String) "buttonColor", (Object) dcs.c);
                        jSONObject.put((String) "fontColor", (Object) dcs.d);
                        jSONObject.put((String) "activityId", (Object) dcs.e);
                        jSONObject.put((String) "title", (Object) jVar.b);
                        jSONObject.put((String) "message", (Object) jVar.f);
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putString("url", "path://amap_bundle_share/src/pages/BizPassphraseCopiedPage.page.js");
                        pageBundle.putString("jsData", jSONObject.toJSONString());
                        AMapPageUtil.getPageContext().startPage(Ajx3DialogPage.class, pageBundle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x001f  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean a() {
        /*
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r1 = 0
            if (r0 == 0) goto L_0x001a
            boolean r2 = r0 instanceof com.autonavi.minimap.ajx3.Ajx3Page
            if (r2 == 0) goto L_0x001a
            com.autonavi.minimap.ajx3.Ajx3Page r0 = (com.autonavi.minimap.ajx3.Ajx3Page) r0
            java.lang.String r0 = r0.getAjx3Url()
            if (r0 == 0) goto L_0x001a
            java.lang.String r2 = "path://amap_bundle_taxi/src/taxi_order/pages/TaxiRouteOverPage.page.js"
            boolean r0 = r0.equalsIgnoreCase(r2)
            goto L_0x001b
        L_0x001a:
            r0 = 0
        L_0x001b:
            if (r0 == 0) goto L_0x001f
            r0 = 1
            return r0
        L_0x001f:
            com.autonavi.minimap.ajx3.Ajx r0 = com.autonavi.minimap.ajx3.Ajx.getInstance()
            java.lang.String r2 = "taxi_business_state"
            com.autonavi.minimap.ajx3.core.MemoryStorageRef r0 = r0.getMemoryStorage(r2)
            java.lang.String r2 = "state"
            java.lang.Object r0 = r0.getItem(r2)
            java.lang.String r0 = (java.lang.String) r0
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x0053
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x004f }
            r2.<init>(r0)     // Catch:{ JSONException -> 0x004f }
            java.lang.String r0 = "taxi"
            org.json.JSONObject r0 = r2.getJSONObject(r0)     // Catch:{ JSONException -> 0x004f }
            java.lang.String r2 = "polling"
            java.lang.String r0 = r0.getString(r2)     // Catch:{ JSONException -> 0x004f }
            java.lang.String r2 = "1"
            boolean r0 = r2.equals(r0)     // Catch:{ JSONException -> 0x004f }
            goto L_0x0054
        L_0x004f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0053:
            r0 = 0
        L_0x0054:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ddc.a():boolean");
    }
}
