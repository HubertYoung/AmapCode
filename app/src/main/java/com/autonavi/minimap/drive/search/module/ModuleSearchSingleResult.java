package com.autonavi.minimap.drive.search.module;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("fetchpoi")
public class ModuleSearchSingleResult extends AbstractModule {
    public static final String MODULE_NAME = "fetchpoi";

    public ModuleSearchSingleResult(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    @com.autonavi.minimap.ajx3.modules.AjxMethod("selectPOI")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void selectPOI(java.lang.String r6, final com.autonavi.minimap.ajx3.core.JsFunctionCallback r7) {
        /*
            r5 = this;
            java.lang.String r0 = ""
            boolean r1 = android.text.TextUtils.isEmpty(r6)
            r2 = 1
            if (r1 != 0) goto L_0x0027
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0023 }
            r1.<init>(r6)     // Catch:{ JSONException -> 0x0023 }
            java.lang.String r6 = "placeholder"
            java.lang.String r6 = r1.optString(r6)     // Catch:{ JSONException -> 0x0023 }
            java.lang.String r0 = "isShowMappoint"
            boolean r0 = r1.optBoolean(r0, r2)     // Catch:{ JSONException -> 0x001e }
            r4 = r0
            r0 = r6
            r6 = r4
            goto L_0x0028
        L_0x001e:
            r0 = move-exception
            r4 = r0
            r0 = r6
            r6 = r4
            goto L_0x0024
        L_0x0023:
            r6 = move-exception
        L_0x0024:
            r6.printStackTrace()
        L_0x0027:
            r6 = 1
        L_0x0028:
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle
            r1.<init>()
            java.lang.String r3 = "search_for"
            r1.putInt(r3, r2)
            java.lang.String r2 = "hint"
            r1.putString(r2, r0)
            java.lang.String r0 = "keyword"
            java.lang.String r2 = ""
            r1.putString(r0, r2)
            java.lang.String r0 = "isHideMyPosition"
            r2 = 0
            r1.putBoolean(r0, r2)
            java.lang.String r0 = "selectedfor"
            com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r3 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.FROM_POI
            r1.putObject(r0, r3)
            java.lang.String r0 = "from_page"
            r3 = 12400(0x3070, float:1.7376E-41)
            r1.putInt(r0, r3)
            java.lang.String r0 = "SUPER_ID"
            java.lang.String r3 = "r"
            r1.putString(r0, r3)
            java.lang.String r0 = "auto_search"
            r1.putBoolean(r0, r2)
            java.lang.String r0 = com.autonavi.minimap.drive.search.fragment.SearchCallbackFragment.a
            r1.putBoolean(r0, r6)
            java.lang.String r6 = "callback"
            com.autonavi.minimap.drive.search.module.ModuleSearchSingleResult$1 r0 = new com.autonavi.minimap.drive.search.module.ModuleSearchSingleResult$1
            r0.<init>(r7)
            r1.putObject(r6, r0)
            bid r6 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r6 == 0) goto L_0x007d
            java.lang.String r7 = "search.fragment.SearchCallbackFragment"
            r0 = 1001(0x3e9, float:1.403E-42)
            r6.startPageForResult(r7, r1, r0)
        L_0x007d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.search.module.ModuleSearchSingleResult.selectPOI(java.lang.String, com.autonavi.minimap.ajx3.core.JsFunctionCallback):void");
    }
}
