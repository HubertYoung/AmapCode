package defpackage;

/* renamed from: dob reason: default package */
/* compiled from: TriggerFeatureAction */
public class dob extends vz {
    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v17, types: [java.io.Serializable] */
    /* JADX WARNING: type inference failed for: r3v19, types: [com.autonavi.common.model.POI] */
    /* JADX WARNING: type inference failed for: r3v22 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(org.json.JSONObject r8, final defpackage.wa r9) {
        /*
            r7 = this;
            com.amap.bundle.jsadapter.JsAdapter r0 = r7.a()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.String r1 = "feature"
            java.lang.String r1 = r8.optString(r1)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r2 = "nearbyShop"
            boolean r2 = r2.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r2 != 0) goto L_0x03dc
            java.lang.String r2 = "reportMenzhi"
            boolean r2 = r2.equals(r1)     // Catch:{ Exception -> 0x03dd }
            r3 = 0
            if (r2 == 0) goto L_0x0049
            com.autonavi.common.PageBundle r8 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x03dd }
            r8.<init>()     // Catch:{ Exception -> 0x03dd }
            com.amap.bundle.jsadapter.JsAdapter r9 = r7.a()     // Catch:{ Exception -> 0x03dd }
            com.autonavi.common.PageBundle r9 = r9.getBundle()     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x0036
            java.lang.String r1 = "POI"
            java.lang.Object r9 = r9.getObject(r1)     // Catch:{ Exception -> 0x03dd }
            r3 = r9
            com.autonavi.common.model.POI r3 = (com.autonavi.common.model.POI) r3     // Catch:{ Exception -> 0x03dd }
        L_0x0036:
            if (r3 == 0) goto L_0x003d
            java.lang.String r9 = "POI"
            r8.putSerializable(r9, r3)     // Catch:{ Exception -> 0x03dd }
        L_0x003d:
            bid r9 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x0048
            bid r9 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            java.lang.String r0 = "amap.basemap.action.feedback_door_address_upload_page"
            r9.startPage(r0, r8)     // Catch:{ Exception -> 0x03dd }
        L_0x0048:
            return
        L_0x0049:
            java.lang.String r2 = "groupbuyHome"
            boolean r2 = r2.equals(r1)     // Catch:{ Exception -> 0x03dd }
            r4 = 0
            if (r2 == 0) goto L_0x0081
            java.lang.String r9 = "poiInfo"
            org.json.JSONObject r8 = r8.optJSONObject(r9)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x03dd }
            com.autonavi.common.model.POI r8 = defpackage.kv.a(r8)     // Catch:{ Exception -> 0x03dd }
            com.autonavi.common.PageBundle r9 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x03dd }
            r9.<init>()     // Catch:{ Exception -> 0x03dd }
            java.lang.String r1 = "POI"
            r9.putObject(r1, r8)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = "isGeoCode"
            r9.putBoolean(r8, r4)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = "isGPSPoint"
            r9.putBoolean(r8, r4)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = "isMarkPoint"
            r9.putBoolean(r8, r4)     // Catch:{ Exception -> 0x03dd }
            bid r8 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            java.lang.String r0 = "amap.life.action.GroupBuyH5HomePageFragment"
            r8.startPage(r0, r9)     // Catch:{ Exception -> 0x03dd }
            return
        L_0x0081:
            java.lang.String r2 = "tuangouList"
            boolean r2 = r2.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r2 == 0) goto L_0x00fc
            java.util.HashMap r9 = new java.util.HashMap     // Catch:{ Exception -> 0x03dd }
            r9.<init>()     // Catch:{ Exception -> 0x03dd }
            java.lang.String r1 = "params"
            org.json.JSONObject r1 = r8.optJSONObject(r1)     // Catch:{ Exception -> 0x03dd }
            if (r1 == 0) goto L_0x00c0
            int r2 = r1.length()     // Catch:{ Exception -> 0x03dd }
            if (r2 <= 0) goto L_0x00c0
            int r2 = r1.length()     // Catch:{ Exception -> 0x03dd }
        L_0x00a1:
            if (r4 >= r2) goto L_0x00c0
            java.util.Iterator r3 = r1.keys()     // Catch:{ Exception -> 0x03dd }
        L_0x00a7:
            boolean r5 = r3.hasNext()     // Catch:{ Exception -> 0x03dd }
            if (r5 == 0) goto L_0x00bd
            java.lang.Object r5 = r3.next()     // Catch:{ Exception -> 0x03dd }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x03dd }
            java.lang.Object r6 = r1.get(r5)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x03dd }
            r9.put(r5, r6)     // Catch:{ Exception -> 0x03dd }
            goto L_0x00a7
        L_0x00bd:
            int r4 = r4 + 1
            goto L_0x00a1
        L_0x00c0:
            java.lang.String r1 = "poiInfo"
            org.json.JSONObject r8 = r8.optJSONObject(r1)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x03dd }
            com.autonavi.common.model.POI r8 = defpackage.kv.a(r8)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r1 = r8.getId()     // Catch:{ Exception -> 0x03dd }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x03dd }
            if (r1 != 0) goto L_0x00fb
            java.lang.Class<com.autonavi.minimap.life.inter.IOpenLifeFragment> r1 = com.autonavi.minimap.life.inter.IOpenLifeFragment.class
            java.lang.Object r1 = defpackage.ank.a(r1)     // Catch:{ Exception -> 0x03dd }
            com.autonavi.minimap.life.inter.IOpenLifeFragment r1 = (com.autonavi.minimap.life.inter.IOpenLifeFragment) r1     // Catch:{ Exception -> 0x03dd }
            if (r1 == 0) goto L_0x00fb
            com.autonavi.common.PageBundle r2 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x03dd }
            r2.<init>()     // Catch:{ Exception -> 0x03dd }
            java.lang.String r3 = "poiId"
            java.lang.String r8 = r8.getId()     // Catch:{ Exception -> 0x03dd }
            r2.putString(r3, r8)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = "params"
            r2.putObject(r8, r9)     // Catch:{ Exception -> 0x03dd }
            bid r8 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            r9 = 7
            r1.a(r8, r9, r2)     // Catch:{ Exception -> 0x03dd }
        L_0x00fb:
            return
        L_0x00fc:
            java.lang.String r2 = "easyDriving"
            boolean r2 = r2.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r2 == 0) goto L_0x0116
            esb r8 = defpackage.esb.a.a     // Catch:{ Exception -> 0x03dd }
            java.lang.Class<ddq> r9 = defpackage.ddq.class
            esc r8 = r8.a(r9)     // Catch:{ Exception -> 0x03dd }
            ddq r8 = (defpackage.ddq) r8     // Catch:{ Exception -> 0x03dd }
            if (r8 == 0) goto L_0x0115
            r8.b()     // Catch:{ Exception -> 0x03dd }
        L_0x0115:
            return
        L_0x0116:
            java.lang.String r2 = "takeTaxi"
            boolean r2 = r2.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r2 == 0) goto L_0x0125
            java.lang.Class<djl> r8 = defpackage.djl.class
            defpackage.ank.a(r8)     // Catch:{ Exception -> 0x03dd }
            return
        L_0x0125:
            java.lang.String r2 = "P&G"
            boolean r2 = r2.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r2 == 0) goto L_0x013f
            esb r8 = defpackage.esb.a.a     // Catch:{ Exception -> 0x03dd }
            java.lang.Class<ddq> r9 = defpackage.ddq.class
            esc r8 = r8.a(r9)     // Catch:{ Exception -> 0x03dd }
            ddq r8 = (defpackage.ddq) r8     // Catch:{ Exception -> 0x03dd }
            if (r8 == 0) goto L_0x013e
            r8.c()     // Catch:{ Exception -> 0x03dd }
        L_0x013e:
            return
        L_0x013f:
            java.lang.String r2 = "dialogGoback"
            boolean r2 = r2.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r2 == 0) goto L_0x0157
            vy r8 = r0.mViewLayer     // Catch:{ Exception -> 0x03dd }
            if (r8 == 0) goto L_0x0151
            vy r8 = r0.mViewLayer     // Catch:{ Exception -> 0x03dd }
            r8.a()     // Catch:{ Exception -> 0x03dd }
            return
        L_0x0151:
            bid r8 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            r8.finish()     // Catch:{ Exception -> 0x03dd }
            return
        L_0x0157:
            java.lang.String r2 = "orderlist"
            boolean r2 = r2.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r2 == 0) goto L_0x017b
            com.autonavi.common.PageBundle r8 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x03dd }
            r8.<init>()     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = "url"
            java.lang.String r1 = "path://amap_bundle_order_home/src/pages/OrderHome.page.js"
            r8.putString(r9, r1)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = "pageId"
            java.lang.String r1 = "OrderHome"
            r8.putString(r9, r1)     // Catch:{ Exception -> 0x03dd }
            bid r9 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r0 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r9.startPage(r0, r8)     // Catch:{ Exception -> 0x03dd }
            return
        L_0x017b:
            java.lang.String r2 = "clearHistoryQuery"
            boolean r2 = r2.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r2 == 0) goto L_0x01ae
            com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage$Builder r8 = new com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage$Builder     // Catch:{ Exception -> 0x03dd }
            bid r1 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            android.app.Activity r1 = r1.getActivity()     // Catch:{ Exception -> 0x03dd }
            r8.<init>(r1)     // Catch:{ Exception -> 0x03dd }
            int r1 = com.autonavi.minimap.R.string.del_cache     // Catch:{ Exception -> 0x03dd }
            com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage$Builder r8 = r8.setTitle(r1)     // Catch:{ Exception -> 0x03dd }
            int r1 = com.autonavi.minimap.R.string.del_now     // Catch:{ Exception -> 0x03dd }
            dob$2 r2 = new dob$2     // Catch:{ Exception -> 0x03dd }
            r2.<init>(r9, r0)     // Catch:{ Exception -> 0x03dd }
            com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage$Builder r8 = r8.setPositiveButton(r1, r2)     // Catch:{ Exception -> 0x03dd }
            int r1 = com.autonavi.minimap.R.string.cancel     // Catch:{ Exception -> 0x03dd }
            dob$1 r2 = new dob$1     // Catch:{ Exception -> 0x03dd }
            r2.<init>(r9, r0)     // Catch:{ Exception -> 0x03dd }
            com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage$Builder r8 = r8.setNegativeButton(r1, r2)     // Catch:{ Exception -> 0x03dd }
            com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.startAlertDialogPage(r8)     // Catch:{ Exception -> 0x03dd }
            return
        L_0x01ae:
            java.lang.String r9 = "qianbao"
            boolean r9 = r9.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x01c4
            java.lang.Class<cpq> r8 = defpackage.cpq.class
            java.lang.Object r8 = defpackage.ank.a(r8)     // Catch:{ Exception -> 0x03dd }
            cpq r8 = (defpackage.cpq) r8     // Catch:{ Exception -> 0x03dd }
            if (r8 == 0) goto L_0x01c3
            r8.a()     // Catch:{ Exception -> 0x03dd }
        L_0x01c3:
            return
        L_0x01c4:
            java.lang.String r9 = "trainInquiries"
            boolean r9 = r9.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x01d5
            bid r8 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = "amap.life.action.TrainSearchFragment"
            r8.startPage(r9, r3)     // Catch:{ Exception -> 0x03dd }
            return
        L_0x01d5:
            java.lang.String r9 = "subway"
            boolean r9 = r9.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x023e
            java.lang.String r9 = "poiInfo"
            org.json.JSONObject r8 = r8.optJSONObject(r9)     // Catch:{ Exception -> 0x03dd }
            if (r8 == 0) goto L_0x023d
            java.lang.String r9 = "lon"
            java.lang.String r9 = r8.optString(r9)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r1 = "lat"
            java.lang.String r1 = r8.optString(r1)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r2 = "cityCode"
            java.lang.String r2 = r8.optString(r2)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r3 = "poiid"
            java.lang.String r8 = r8.optString(r3)     // Catch:{ Exception -> 0x03dd }
            boolean r3 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x03dd }
            if (r3 != 0) goto L_0x023d
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x03dd }
            if (r3 != 0) goto L_0x023d
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x03dd }
            if (r3 != 0) goto L_0x023d
            boolean r3 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Exception -> 0x03dd }
            if (r3 != 0) goto L_0x023d
            esb r3 = defpackage.esb.a.a     // Catch:{ Exception -> 0x03dd }
            java.lang.Class<bdk> r4 = defpackage.bdk.class
            esc r3 = r3.a(r4)     // Catch:{ Exception -> 0x03dd }
            bdk r3 = (defpackage.bdk) r3     // Catch:{ Exception -> 0x03dd }
            if (r3 == 0) goto L_0x023d
            bid r0 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x03dd }
            r4.<init>()     // Catch:{ Exception -> 0x03dd }
            r4.append(r9)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = ","
            r4.append(r9)     // Catch:{ Exception -> 0x03dd }
            r4.append(r1)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = r4.toString()     // Catch:{ Exception -> 0x03dd }
            r3.a(r0, r2, r8, r9)     // Catch:{ Exception -> 0x03dd }
        L_0x023d:
            return
        L_0x023e:
            java.lang.String r9 = "shoppingList"
            boolean r9 = r9.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x02c4
            com.amap.bundle.jsadapter.JsAdapter r9 = r7.a()     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x02c3
            java.lang.String r0 = "params"
            org.json.JSONObject r0 = r8.optJSONObject(r0)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r1 = "poiInfo"
            org.json.JSONObject r8 = r8.optJSONObject(r1)     // Catch:{ Exception -> 0x03dd }
            if (r8 != 0) goto L_0x025f
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ Exception -> 0x03dd }
            r8.<init>()     // Catch:{ Exception -> 0x03dd }
        L_0x025f:
            java.lang.String r1 = "poiid"
            java.lang.String r2 = ""
            java.lang.String r1 = r8.optString(r1, r2)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r2 = "name"
            java.lang.String r3 = ""
            java.lang.String r8 = r8.optString(r2, r3)     // Catch:{ Exception -> 0x03dd }
            if (r0 != 0) goto L_0x0276
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x03dd }
            r0.<init>()     // Catch:{ Exception -> 0x03dd }
        L_0x0276:
            java.lang.String r2 = "fl_name"
            java.lang.String r3 = ""
            java.lang.String r2 = r0.optString(r2, r3)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r3 = "main_type"
            java.lang.String r4 = ""
            java.lang.String r3 = r0.optString(r3, r4)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r4 = "condition"
            java.lang.String r5 = ""
            java.lang.String r0 = r0.optString(r4, r5)     // Catch:{ Exception -> 0x03dd }
            java.lang.Class<com.autonavi.minimap.life.inter.IOpenLifeFragment> r4 = com.autonavi.minimap.life.inter.IOpenLifeFragment.class
            java.lang.Object r4 = defpackage.ank.a(r4)     // Catch:{ Exception -> 0x03dd }
            com.autonavi.minimap.life.inter.IOpenLifeFragment r4 = (com.autonavi.minimap.life.inter.IOpenLifeFragment) r4     // Catch:{ Exception -> 0x03dd }
            if (r4 == 0) goto L_0x02c3
            com.autonavi.common.PageBundle r5 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x03dd }
            r5.<init>()     // Catch:{ Exception -> 0x03dd }
            java.lang.String r6 = "poiId"
            r5.putString(r6, r1)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r1 = "poiName"
            r5.putString(r1, r8)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = "floor"
            r5.putString(r8, r2)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = "showIndoorMap"
            r1 = 1
            r5.putBoolean(r8, r1)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = "classify"
            r5.putString(r8, r3)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = "prefercial"
            r5.putString(r8, r0)     // Catch:{ Exception -> 0x03dd }
            bid r8 = r9.mPageContext     // Catch:{ Exception -> 0x03dd }
            r9 = 23
            r4.a(r8, r9, r5)     // Catch:{ Exception -> 0x03dd }
        L_0x02c3:
            return
        L_0x02c4:
            java.lang.String r9 = "feedbackList"
            boolean r9 = r9.equals(r1)     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x02f3
            bid r8 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()     // Catch:{ Exception -> 0x03dd }
            if (r8 == 0) goto L_0x02f2
            android.content.Context r9 = r8.getContext()     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x02f2
            android.content.res.Resources r9 = r9.getResources()     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x02f2
            int r0 = com.autonavi.minimap.R.string.schema_uri_feedback     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = r9.getString(r0)     // Catch:{ Exception -> 0x03dd }
            android.net.Uri r9 = android.net.Uri.parse(r9)     // Catch:{ Exception -> 0x03dd }
            android.content.Intent r0 = new android.content.Intent     // Catch:{ Exception -> 0x03dd }
            java.lang.String r1 = "android.intent.action.VIEW"
            r0.<init>(r1, r9)     // Catch:{ Exception -> 0x03dd }
            r8.startScheme(r0)     // Catch:{ Exception -> 0x03dd }
        L_0x02f2:
            return
        L_0x02f3:
            java.lang.String r9 = "location"
            boolean r9 = r1.equals(r9)     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x030c
            java.lang.String r9 = "params"
            org.json.JSONObject r8 = r8.optJSONObject(r9)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = "id"
            r8.optString(r9)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = "adcode"
            r8.optString(r9)     // Catch:{ Exception -> 0x03dd }
            return
        L_0x030c:
            java.lang.String r9 = "userPhoto"
            boolean r9 = r1.equals(r9)     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x0333
            java.lang.String r9 = "params"
            org.json.JSONObject r8 = r8.optJSONObject(r9)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = "id"
            java.lang.String r8 = r8.optString(r9)     // Catch:{ Exception -> 0x03dd }
            com.autonavi.common.PageBundle r9 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x03dd }
            r9.<init>()     // Catch:{ Exception -> 0x03dd }
            java.lang.String r1 = "REAL_SCENE_USER_ID"
            r9.putString(r1, r8)     // Catch:{ Exception -> 0x03dd }
            bid r8 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            java.lang.String r0 = "amap.life.action.RealSceneUserInfoFragment"
            r8.startPage(r0, r9)     // Catch:{ Exception -> 0x03dd }
            return
        L_0x0333:
            java.lang.String r9 = "photoDetail"
            boolean r9 = r1.equals(r9)     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x0397
            if (r0 == 0) goto L_0x0359
            bid r9 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x0359
            android.content.Context r9 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getContext()     // Catch:{ Exception -> 0x03dd }
            boolean r9 = defpackage.aaw.c(r9)     // Catch:{ Exception -> 0x03dd }
            if (r9 != 0) goto L_0x0359
            int r8 = com.autonavi.minimap.R.string.error_check_network_and_retry     // Catch:{ Exception -> 0x03dd }
            android.app.Application r9 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = r9.getString(r8)     // Catch:{ Exception -> 0x03dd }
            com.amap.bundle.utils.ui.ToastHelper.showToast(r8)     // Catch:{ Exception -> 0x03dd }
            return
        L_0x0359:
            java.lang.String r9 = "params"
            org.json.JSONObject r8 = r8.optJSONObject(r9)     // Catch:{ Exception -> 0x03dd }
            if (r8 != 0) goto L_0x0362
            return
        L_0x0362:
            java.lang.String r9 = "list"
            org.json.JSONArray r9 = r8.optJSONArray(r9)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r1 = "index"
            int r1 = r8.optInt(r1)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r2 = "id"
            java.lang.String r8 = r8.optString(r2)     // Catch:{ Exception -> 0x03dd }
            if (r9 != 0) goto L_0x0377
            goto L_0x037b
        L_0x0377:
            java.lang.String r3 = r9.toString()     // Catch:{ Exception -> 0x03dd }
        L_0x037b:
            com.autonavi.common.PageBundle r9 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x03dd }
            r9.<init>()     // Catch:{ Exception -> 0x03dd }
            java.lang.String r2 = "image_id"
            r9.putString(r2, r8)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = "image_index"
            r9.putInt(r8, r1)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r8 = "image_list"
            r9.putString(r8, r3)     // Catch:{ Exception -> 0x03dd }
            bid r8 = r0.mPageContext     // Catch:{ Exception -> 0x03dd }
            java.lang.String r0 = "amap.life.action.RealSceneDetailSingleFragment"
            r8.startPage(r0, r9)     // Catch:{ Exception -> 0x03dd }
            return
        L_0x0397:
            java.lang.String r9 = "addPhoto"
            boolean r9 = r1.equals(r9)     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x03b0
            java.lang.String r9 = "params"
            org.json.JSONObject r8 = r8.optJSONObject(r9)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = "activity_id"
            r8.optString(r9)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = "activity_name"
            r8.optString(r9)     // Catch:{ Exception -> 0x03dd }
            return
        L_0x03b0:
            java.lang.String r9 = "a-navFavorites"
            boolean r9 = r1.equals(r9)     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x03dc
            java.lang.String r9 = "params"
            org.json.JSONObject r8 = r8.optJSONObject(r9)     // Catch:{ Exception -> 0x03dd }
            java.lang.String r9 = "account"
            java.lang.String r8 = r8.optString(r9)     // Catch:{ Exception -> 0x03dd }
            esb r9 = defpackage.esb.a.a     // Catch:{ Exception -> 0x03dd }
            java.lang.Class<auz> r0 = defpackage.auz.class
            esc r9 = r9.a(r0)     // Catch:{ Exception -> 0x03dd }
            auz r9 = (defpackage.auz) r9     // Catch:{ Exception -> 0x03dd }
            if (r9 == 0) goto L_0x03d5
            r9.a(r8)     // Catch:{ Exception -> 0x03dd }
        L_0x03d5:
            bid r8 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()     // Catch:{ Exception -> 0x03dd }
            r8.finish()     // Catch:{ Exception -> 0x03dd }
        L_0x03dc:
            return
        L_0x03dd:
            r8 = move-exception
            defpackage.kf.a(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dob.a(org.json.JSONObject, wa):void");
    }
}
