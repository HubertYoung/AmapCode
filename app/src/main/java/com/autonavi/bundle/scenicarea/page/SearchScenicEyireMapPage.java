package com.autonavi.bundle.scenicarea.page;

import android.content.Context;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.bundle.scenicarea.ajx.ModuleSearchScenic;
import com.autonavi.bundle.scenicarea.ajx.ModuleSearchScenic.a;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTop;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.util.Iterator;

@PageAction("search_scenicarea_eyire")
public class SearchScenicEyireMapPage extends Ajx3Page implements bbc, a, launchModeSingleTop {
    private ModuleSearchScenic a;
    private bba b;
    private bbb c;

    public final void a() {
    }

    public final void a(String str) {
    }

    public final void b(String str) {
    }

    public void onPageAppear() {
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.a = (ModuleSearchScenic) this.mAjxView.getJsModule(ModuleSearchScenic.MODULE_NAME);
        if (this.a != null) {
            this.a.setUiListener(this);
        }
    }

    public Ajx3PagePresenter createPresenter() {
        return new bbd(this);
    }

    public void destroy() {
        this.c.a(false);
        d();
        super.destroy();
    }

    public void onPageCover() {
        this.c.a(false);
        d();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0063  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String c() {
        /*
            r5 = this;
            com.autonavi.common.PageBundle r0 = r5.getArguments()
            java.lang.String r1 = "jsData"
            java.lang.String r0 = r0.getString(r1)
            r1 = 0
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0057 }
            r2.<init>(r0)     // Catch:{ JSONException -> 0x0057 }
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x0055 }
            com.autonavi.common.model.GeoPoint r0 = r0.getLatestPosition()     // Catch:{ JSONException -> 0x0055 }
            int r0 = r0.getAdCode()     // Catch:{ JSONException -> 0x0055 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0055 }
            r1.<init>()     // Catch:{ JSONException -> 0x0055 }
            com.autonavi.sdk.location.LocationInstrument r3 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x0055 }
            com.autonavi.common.model.GeoPoint r3 = r3.getLatestPosition()     // Catch:{ JSONException -> 0x0055 }
            double r3 = r3.getLongitude()     // Catch:{ JSONException -> 0x0055 }
            r1.append(r3)     // Catch:{ JSONException -> 0x0055 }
            java.lang.String r3 = ","
            r1.append(r3)     // Catch:{ JSONException -> 0x0055 }
            com.autonavi.sdk.location.LocationInstrument r3 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x0055 }
            com.autonavi.common.model.GeoPoint r3 = r3.getLatestPosition()     // Catch:{ JSONException -> 0x0055 }
            double r3 = r3.getLatitude()     // Catch:{ JSONException -> 0x0055 }
            r1.append(r3)     // Catch:{ JSONException -> 0x0055 }
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x0055 }
            java.lang.String r3 = "user_adcode"
            r2.put(r3, r0)     // Catch:{ JSONException -> 0x0055 }
            java.lang.String r0 = "user_loc"
            r2.put(r0, r1)     // Catch:{ JSONException -> 0x0055 }
            goto L_0x005c
        L_0x0055:
            r0 = move-exception
            goto L_0x0059
        L_0x0057:
            r0 = move-exception
            r2 = r1
        L_0x0059:
            r0.printStackTrace()
        L_0x005c:
            if (r2 == 0) goto L_0x0063
            java.lang.String r0 = r2.toString()
            return r0
        L_0x0063:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.scenicarea.page.SearchScenicEyireMapPage.c():java.lang.String");
    }

    private void d() {
        PlaySoundUtils.getInstance().clear();
        PlaySoundUtils.getInstance().removeSoundPlayListener(this.b);
    }

    public final void b() {
        startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00a0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(com.autonavi.common.PageBundle r6) {
        /*
            r5 = this;
            if (r6 != 0) goto L_0x0003
            return
        L_0x0003:
            java.lang.String r0 = "jsData"
            java.lang.String r6 = r6.getString(r0)
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x008e }
            r1.<init>(r6)     // Catch:{ JSONException -> 0x008e }
            com.autonavi.sdk.location.LocationInstrument r6 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x008c }
            com.autonavi.common.model.GeoPoint r6 = r6.getLatestPosition()     // Catch:{ JSONException -> 0x008c }
            int r6 = r6.getAdCode()     // Catch:{ JSONException -> 0x008c }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x008c }
            r0.<init>()     // Catch:{ JSONException -> 0x008c }
            com.autonavi.sdk.location.LocationInstrument r2 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x008c }
            com.autonavi.common.model.GeoPoint r2 = r2.getLatestPosition()     // Catch:{ JSONException -> 0x008c }
            double r2 = r2.getLongitude()     // Catch:{ JSONException -> 0x008c }
            r0.append(r2)     // Catch:{ JSONException -> 0x008c }
            java.lang.String r2 = ","
            r0.append(r2)     // Catch:{ JSONException -> 0x008c }
            com.autonavi.sdk.location.LocationInstrument r2 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x008c }
            com.autonavi.common.model.GeoPoint r2 = r2.getLatestPosition()     // Catch:{ JSONException -> 0x008c }
            double r2 = r2.getLatitude()     // Catch:{ JSONException -> 0x008c }
            r0.append(r2)     // Catch:{ JSONException -> 0x008c }
            java.lang.String r0 = r0.toString()     // Catch:{ JSONException -> 0x008c }
            java.lang.String r2 = "user_adcode"
            r1.put(r2, r6)     // Catch:{ JSONException -> 0x008c }
            java.lang.String r6 = "user_loc"
            r1.put(r6, r0)     // Catch:{ JSONException -> 0x008c }
            java.lang.String r6 = "show_type"
            java.lang.String r0 = "schema"
            r1.put(r6, r0)     // Catch:{ JSONException -> 0x008c }
            bty r6 = r5.getMapView()     // Catch:{ JSONException -> 0x008c }
            if (r6 == 0) goto L_0x0093
            bty r6 = r5.getMapView()     // Catch:{ JSONException -> 0x008c }
            com.autonavi.common.model.GeoPoint r6 = r6.o()     // Catch:{ JSONException -> 0x008c }
            java.lang.String r0 = "center"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x008c }
            r2.<init>()     // Catch:{ JSONException -> 0x008c }
            double r3 = r6.getLongitude()     // Catch:{ JSONException -> 0x008c }
            r2.append(r3)     // Catch:{ JSONException -> 0x008c }
            java.lang.String r3 = ","
            r2.append(r3)     // Catch:{ JSONException -> 0x008c }
            double r3 = r6.getLatitude()     // Catch:{ JSONException -> 0x008c }
            r2.append(r3)     // Catch:{ JSONException -> 0x008c }
            java.lang.String r6 = r2.toString()     // Catch:{ JSONException -> 0x008c }
            r1.put(r0, r6)     // Catch:{ JSONException -> 0x008c }
            goto L_0x0093
        L_0x008c:
            r6 = move-exception
            goto L_0x0090
        L_0x008e:
            r6 = move-exception
            r1 = r0
        L_0x0090:
            r6.printStackTrace()
        L_0x0093:
            if (r1 == 0) goto L_0x009a
            java.lang.String r6 = r1.toString()
            goto L_0x009c
        L_0x009a:
            java.lang.String r6 = ""
        L_0x009c:
            com.autonavi.minimap.ajx3.AjxPageStateInvoker r0 = r5.ajxPageStateInvoker
            if (r0 == 0) goto L_0x00a5
            com.autonavi.minimap.ajx3.AjxPageStateInvoker r0 = r5.ajxPageStateInvoker
            r0.setResumeData(r6)
        L_0x00a5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.scenicarea.page.SearchScenicEyireMapPage.a(com.autonavi.common.PageBundle):void");
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments == null) {
            arguments = new PageBundle();
            setArguments(arguments);
        }
        arguments.putString("url", "path://amap_bundle_scenic_area/src/pages/EyrieMapPage.page.js");
        arguments.putString("jsData", c());
        this.c = new bbb(this);
        this.c.b();
        super.onCreate(context);
    }

    public final String c(String str) {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            try {
                Iterator<LayerItem> it = awo.i().iterator();
                while (it.hasNext()) {
                    LayerItem next = it.next();
                    if (next.getLayer_id() == Integer.parseInt(str)) {
                        return next.getData();
                    }
                }
            } catch (NumberFormatException unused) {
            }
        }
        return "";
    }
}
