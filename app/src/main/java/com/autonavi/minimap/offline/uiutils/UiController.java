package com.autonavi.minimap.offline.uiutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.offline.WorkThreadManager;
import com.autonavi.minimap.offline.WorkThreadManager.OfflineTask;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.minimap.offline.storage.StorageService;

public class UiController {
    public static final String KEY_ALONGWAY_CITIES = "alongway_cities";
    public static final String KEY_ALONGWAY_MODE = "alongway_mode";
    public static final String KEY_CITY_DOWNLOAD_ID = "start_download_city";
    public static final String KEY_FORM_SEARCH__DOWNLOAD_MAP = "download_currentcity_map";
    public static final String KEY_FormSearch_DownloadMap = "download_currentcity_map";
    public static final String OFFLiNE_DOWNOAD_CURRENT_CITY_MAP = "current_city_map";
    public static final String OFFLiNE_DOWNOAD_CURRENT_CITY_NAVI = "current_city_navi";
    public static final String PLUGIN_DOWNLOAD_START = "startDownloadPlugin";
    public static final String SHOW_ENLARGEMENT_DOWNLOAD = "showEnlargementDownload";
    public static final String SHOW_MAP_DOWNLOAD = "showMapDownload";
    public static final String SHOW_MAP_POI_ROUTE_DOWNLOAD = "showPoiRouteDownload";
    /* access modifiers changed from: private */
    public static StorageService mStorageService;

    public static void deal(bid bid, Intent intent) {
        if (bid != null && intent != null) {
            if (FileUtil.getIsClearDataOperation()) {
                FileUtil.setClearDataOperation(false);
                resetPath(bid, intent);
                return;
            }
            gotoFragment(intent, bid);
        }
    }

    private static void resetPath(final bid bid, final Intent intent) {
        if (bid != null) {
            WorkThreadManager.start(new OfflineTask() {
                public final Object doBackground() throws Exception {
                    String str;
                    Activity activity = DoNotUseTool.getActivity();
                    if (activity == null || activity.isFinishing()) {
                        return null;
                    }
                    final dsj dsj = new dsj(bid, intent);
                    UiController.mStorageService = new StorageService(dsj);
                    String b2 = PathManager.a().b();
                    PathManager.a();
                    DirType dirType = DirType.OFFLINE;
                    if (!TextUtils.isEmpty(b2)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(b2);
                        sb.append(FilePathHelper.APP_FOLDER);
                        str = PathManager.b(dirType, sb.toString());
                    } else {
                        str = "";
                    }
                    if (TextUtils.isEmpty(str)) {
                        return null;
                    }
                    activity.runOnUiThread(new Runnable() {
                        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0033, code lost:
                            if (r0.b.isShowing() == false) goto L_0x0035;
                         */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public final void run() {
                            /*
                                r3 = this;
                                dsj r0 = r2
                                bid r1 = r0.a
                                if (r1 == 0) goto L_0x003c
                                android.app.Activity r1 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getActivity()
                                if (r1 == 0) goto L_0x003b
                                boolean r2 = r1.isFinishing()
                                if (r2 == 0) goto L_0x0013
                                goto L_0x003b
                            L_0x0013:
                                com.autonavi.map.widget.ProgressDlg r2 = r0.b
                                if (r2 != 0) goto L_0x002d
                                com.autonavi.map.widget.ProgressDlg r2 = new com.autonavi.map.widget.ProgressDlg
                                r2.<init>(r1)
                                r0.b = r2
                                com.autonavi.map.widget.ProgressDlg r1 = r0.b
                                java.lang.String r2 = "正在切换目录..."
                                r1.setMessage(r2)
                                com.autonavi.map.widget.ProgressDlg r1 = r0.b
                                r2 = 0
                                r1.setCancelable(r2)
                                goto L_0x0035
                            L_0x002d:
                                com.autonavi.map.widget.ProgressDlg r1 = r0.b
                                boolean r1 = r1.isShowing()
                                if (r1 != 0) goto L_0x003c
                            L_0x0035:
                                com.autonavi.map.widget.ProgressDlg r0 = r0.b     // Catch:{ Exception -> 0x003c }
                                r0.show()     // Catch:{ Exception -> 0x003c }
                                return
                            L_0x003b:
                                return
                            L_0x003c:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.uiutils.UiController.AnonymousClass1.AnonymousClass1.run():void");
                        }
                    });
                    UiController.mStorageService.doStorageChange("", str);
                    return null;
                }
            });
        }
    }

    public static void gotoFragment(Intent intent, bid bid) {
        if (intent != null && bid != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                bid pageContext = AMapPageUtil.getPageContext();
                if (extras.containsKey("showMapDownload")) {
                    startCityDataFragment(new PageBundle(intent), pageContext);
                    return;
                } else if (intent.getBooleanExtra("showEnlargementDownload", false)) {
                    startCityDataFragment(new PageBundle(intent), pageContext);
                    return;
                } else if (intent.getBooleanExtra("showPoiRouteDownload", false)) {
                    startCityDataFragment(new PageBundle(intent), pageContext);
                    return;
                }
            }
            bid.finish();
        }
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v5, types: [lj] */
    /* JADX WARNING: type inference failed for: r1v9, types: [lj] */
    /* JADX WARNING: type inference failed for: r1v12, types: [com.autonavi.minimap.offlinesdk.model.CityInfo] */
    /* JADX WARNING: type inference failed for: r1v15, types: [com.autonavi.minimap.offlinesdk.model.CityInfo] */
    /* JADX WARNING: type inference failed for: r1v16 */
    /* JADX WARNING: type inference failed for: r1v17 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], com.autonavi.minimap.offlinesdk.model.CityInfo, lj]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], lj, com.autonavi.minimap.offlinesdk.model.CityInfo]
      mth insns count: 98
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void startCityDataFragment(com.autonavi.common.PageBundle r7, defpackage.bid r8) {
        /*
            java.lang.String r0 = "start_download_city"
            boolean r0 = r7.containsKey(r0)     // Catch:{ Exception -> 0x0183 }
            r1 = 0
            if (r0 == 0) goto L_0x0078
            java.lang.String r0 = "start_download_city"
            java.lang.Object r0 = r7.get(r0)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0183 }
            boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0183 }
            if (r2 != 0) goto L_0x014a
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0183 }
            r2.<init>()     // Catch:{ Exception -> 0x0183 }
            java.lang.String r3 = "source"
            java.lang.String r4 = "extra"
            r2.put(r3, r4)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r3 = "code"
            java.lang.String r4 = "3"
            r2.put(r3, r4)     // Catch:{ Exception -> 0x0183 }
            org.json.JSONArray r3 = new org.json.JSONArray     // Catch:{ Exception -> 0x0183 }
            r3.<init>()     // Catch:{ Exception -> 0x0183 }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0183 }
            r4.<init>()     // Catch:{ Exception -> 0x0183 }
            java.lang.String r5 = "adCode"
            java.lang.String r6 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x0183 }
            r4.put(r5, r6)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r5 = "types"
            java.lang.String r6 = "0"
            r4.put(r5, r6)     // Catch:{ Exception -> 0x0183 }
            r3.put(r4)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r4 = "data"
            r2.put(r4, r3)     // Catch:{ Exception -> 0x0183 }
            com.autonavi.minimap.offline.IOfflineNativeSdk r3 = com.autonavi.minimap.offline.OfflineNativeSdk.getInstance()     // Catch:{ Exception -> 0x0183 }
            com.autonavi.minimap.offlinesdk.ICityManager r3 = r3.getCityManager()     // Catch:{ Exception -> 0x0183 }
            if (r3 == 0) goto L_0x0064
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0183 }
            com.autonavi.minimap.offlinesdk.model.CityInfo r1 = r3.getCityByAdcode(r0)     // Catch:{ Exception -> 0x0183 }
        L_0x0064:
            if (r1 == 0) goto L_0x006d
            java.lang.String r0 = "cityName"
            java.lang.String r1 = r1.name     // Catch:{ Exception -> 0x0183 }
            r2.put(r0, r1)     // Catch:{ Exception -> 0x0183 }
        L_0x006d:
            java.lang.String r0 = "jsData"
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x0183 }
            r7.putString(r0, r1)     // Catch:{ Exception -> 0x0183 }
            goto L_0x014a
        L_0x0078:
            java.lang.String r0 = "download_currentcity_map"
            boolean r0 = r7.containsKey(r0)     // Catch:{ Exception -> 0x0183 }
            if (r0 == 0) goto L_0x00d8
            com.autonavi.minimap.offlinesdk.model.CityInfo r0 = com.autonavi.minimap.offline.utils.CityHelper.getCurrentMapViewCity()     // Catch:{ Exception -> 0x0183 }
            if (r0 == 0) goto L_0x014a
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0183 }
            r1.<init>()     // Catch:{ Exception -> 0x0183 }
            java.lang.String r2 = "source"
            java.lang.String r3 = "extra"
            r1.put(r2, r3)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r2 = "code"
            java.lang.String r3 = "3"
            r1.put(r2, r3)     // Catch:{ Exception -> 0x0183 }
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ Exception -> 0x0183 }
            r2.<init>()     // Catch:{ Exception -> 0x0183 }
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0183 }
            r3.<init>()     // Catch:{ Exception -> 0x0183 }
            java.lang.String r4 = "adCode"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0183 }
            r5.<init>()     // Catch:{ Exception -> 0x0183 }
            int r6 = r0.cityId     // Catch:{ Exception -> 0x0183 }
            r5.append(r6)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0183 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r4 = "types"
            java.lang.String r5 = "0"
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0183 }
            r2.put(r3)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r3 = "data"
            r1.put(r3, r2)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r2 = "cityName"
            java.lang.String r0 = r0.name     // Catch:{ Exception -> 0x0183 }
            r1.put(r2, r0)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r0 = "jsData"
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0183 }
            r7.putString(r0, r1)     // Catch:{ Exception -> 0x0183 }
            goto L_0x014a
        L_0x00d8:
            java.lang.String r0 = "current_city_navi"
            boolean r0 = r7.containsKey(r0)     // Catch:{ Exception -> 0x0183 }
            if (r0 == 0) goto L_0x014a
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x0183 }
            com.autonavi.common.model.GeoPoint r0 = r0.getLatestPosition()     // Catch:{ Exception -> 0x0183 }
            if (r0 == 0) goto L_0x00f6
            li r1 = defpackage.li.a()     // Catch:{ Exception -> 0x0183 }
            int r2 = r0.x     // Catch:{ Exception -> 0x0183 }
            int r0 = r0.y     // Catch:{ Exception -> 0x0183 }
            lj r1 = r1.b(r2, r0)     // Catch:{ Exception -> 0x0183 }
        L_0x00f6:
            if (r1 == 0) goto L_0x014a
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0183 }
            r0.<init>()     // Catch:{ Exception -> 0x0183 }
            java.lang.String r2 = "source"
            java.lang.String r3 = "setting"
            r0.put(r2, r3)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r2 = "code"
            java.lang.String r3 = "2"
            r0.put(r2, r3)     // Catch:{ Exception -> 0x0183 }
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ Exception -> 0x0183 }
            r2.<init>()     // Catch:{ Exception -> 0x0183 }
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0183 }
            r3.<init>()     // Catch:{ Exception -> 0x0183 }
            java.lang.String r4 = "adCode"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0183 }
            r5.<init>()     // Catch:{ Exception -> 0x0183 }
            int r6 = r1.j     // Catch:{ Exception -> 0x0183 }
            r5.append(r6)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0183 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r4 = "types"
            java.lang.String r5 = "2"
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0183 }
            r2.put(r3)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r3 = "data"
            r0.put(r3, r2)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r2 = "cityName"
            java.lang.String r1 = r1.a     // Catch:{ Exception -> 0x0183 }
            r0.put(r2, r1)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r1 = "jsData"
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0183 }
            r7.putString(r1, r0)     // Catch:{ Exception -> 0x0183 }
        L_0x014a:
            boolean r0 = r8 instanceof com.autonavi.minimap.ajx3.Ajx3Page     // Catch:{ Exception -> 0x0183 }
            if (r0 == 0) goto L_0x0164
            r0 = r8
            com.autonavi.minimap.ajx3.Ajx3Page r0 = (com.autonavi.minimap.ajx3.Ajx3Page) r0     // Catch:{ Exception -> 0x0183 }
            java.lang.String r0 = r0.getAjx3Url()     // Catch:{ Exception -> 0x0183 }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0183 }
            if (r1 != 0) goto L_0x0164
            java.lang.String r1 = "path://amap_bundle_offline/src/"
            boolean r0 = r0.startsWith(r1)     // Catch:{ Exception -> 0x0183 }
            if (r0 == 0) goto L_0x0164
            return
        L_0x0164:
            java.lang.String r0 = "url"
            java.lang.String r1 = "path://amap_bundle_offline/src/homePage/HomePage.tsx.js"
            r7.putString(r0, r1)     // Catch:{ Exception -> 0x0183 }
            java.lang.String r0 = "startTime"
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0183 }
            r7.putLong(r0, r1)     // Catch:{ Exception -> 0x0183 }
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r0 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r8.startPage(r0, r7)     // Catch:{ Exception -> 0x0183 }
            r7 = 0
            com.autonavi.minimap.offline.utils.OfflineSpUtil.setOfflineDataUpdateShow(r7)     // Catch:{ Exception -> 0x0183 }
            com.autonavi.minimap.offline.utils.OfflineSpUtil.setAe8RedNeedShowSp(r7)     // Catch:{ Exception -> 0x0183 }
            return
        L_0x0183:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.uiutils.UiController.startCityDataFragment(com.autonavi.common.PageBundle, bid):void");
    }
}
