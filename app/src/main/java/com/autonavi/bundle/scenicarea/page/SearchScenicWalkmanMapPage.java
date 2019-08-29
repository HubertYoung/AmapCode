package com.autonavi.bundle.scenicarea.page;

import android.content.Context;
import android.support.annotation.Nullable;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;

@PageAction("search_scenicarea_walkman_map")
public class SearchScenicWalkmanMapPage extends Ajx3Page implements launchModeSingleTask {
    @Nullable
    public String getAjx3Url() {
        return "path://amap_bundle_scenic_area/src/walkman/pages/BizScenicWalkmanMapPage.page.js";
    }

    public String getLaunchMode() {
        return super.getLaunchMode();
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
    }

    public Ajx3PagePresenter createPresenter() {
        return super.createPresenter();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0021  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0026  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void newIntent(com.autonavi.common.PageBundle r4) {
        /*
            r3 = this;
            if (r4 == 0) goto L_0x0031
            java.lang.String r0 = "jsData"
            java.lang.String r0 = r4.getString(r0)
            r1 = 0
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x001a }
            r2.<init>(r0)     // Catch:{ JSONException -> 0x001a }
            java.lang.String r0 = "show_type"
            java.lang.String r1 = "schema"
            r2.put(r0, r1)     // Catch:{ JSONException -> 0x0018 }
            goto L_0x001f
        L_0x0018:
            r0 = move-exception
            goto L_0x001c
        L_0x001a:
            r0 = move-exception
            r2 = r1
        L_0x001c:
            r0.printStackTrace()
        L_0x001f:
            if (r2 == 0) goto L_0x0026
            java.lang.String r0 = r2.toString()
            goto L_0x0028
        L_0x0026:
            java.lang.String r0 = ""
        L_0x0028:
            com.autonavi.minimap.ajx3.AjxPageStateInvoker r1 = r3.ajxPageStateInvoker
            if (r1 == 0) goto L_0x0031
            com.autonavi.minimap.ajx3.AjxPageStateInvoker r1 = r3.ajxPageStateInvoker
            r1.setResumeData(r0)
        L_0x0031:
            super.newIntent(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.scenicarea.page.SearchScenicWalkmanMapPage.newIntent(com.autonavi.common.PageBundle):void");
    }
}
