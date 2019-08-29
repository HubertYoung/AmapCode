package com.autonavi.minimap.ajx3;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.loader.AjxPathLoader;
import com.autonavi.minimap.ajx3.util.FileUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;

@SuppressFBWarnings({"NP_LOAD_OF_KNOWN_NULL_VALUE", "RCN_REDUNDANT_NULLCHECK_OF_NULL_VALUE"})
public class Ajx3Path {
    public static final String BASEMAP_MINE = "path://amap_bundle_mine/src/pages/MinePage.page.js";
    public static final String CAR_OWNER_ADD_PATH = "path://amap_bundle_carowner/src/car_owner/CarAddViewController.page.js";
    public static final String CAR_OWNER_BRAND_PATH = "path://amap_bundle_carowner/src/car_owner/CarBrandSelectController.page.js";
    public static final String CAR_OWNER_EDIT_PATH = "path://amap_bundle_carowner/src/car_owner/CarEditViewController.page.js";
    public static final String CAR_OWNER_HOMEPAGE_PATH = "path://amap_bundle_carowner/src/car_owner/CarHomeViewController.page.js";
    public static final String CAR_OWNER_LIST_PATH = "path://amap_bundle_carowner/src/car_owner/CarListViewController.page.js";
    public static final String CAR_OWNER_SCAN_GUIDE_PATH = "path://amap_bundle_carowner/src/car_owner/CarScanGuideViewController.page.js";
    public static final String CAR_OWNER_SCAN_RESULT_PATH = "path://amap_bundle_carowner/src/car_owner/CarLicenceController.page.js";
    public static final String CAR_SELECT_AJXPAGE = "path://amap_bundle_drive/src/car/select_page/CarSelectViewController.page.js";
    public static final String DRIVE_NEWS_PATH = "path://amap_bundle_basemap/src/pages/DriveNews.page.js";
    public static final String FEEDBACK_DETAIL_PATH = "amap_bundle_basemap_feedback/src/user_center_old/detail.jsx.js";
    public static final String FEEDBACK_LOCATION_PATH = "path://amap_bundle_basemap_feedback/src/location/FeedbackLocation.page.js";
    public static final String FEED_PATH = "path://amap_bundle_nearby/src/pages/BizNearbyIndex.page.js";
    private static final int LEN_SCHEMA_SPLIT = 3;
    public static final String MSG_BOX_PATH = "path://amap_bundle_messagebox/src/MessageBoxPage.page.js";
    public static final String OFFLINE_PATH = "path://amap_bundle_offline/src/homePage/HomePage.jsx.js";
    public static final String POI_PATH = "path://amap_bundle_poi/src/poi.jsx.js";
    public static final String SETTING_FONT_SIZE_PATH = "path://amap_bundle_setting/src/pages/BizSettingMapFontSizePage.page.js";
    public static final String TRIP_MODE_SETTING = "path://amap_bundle_setting/src/pages/TripModeSetting.page.js";

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0051 A[SYNTHETIC, Splitter:B:13:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0030  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.minimap.ajx3.loading.LoadingConfig getLoadingConfig(android.content.Context r3, java.lang.String r4) {
        /*
            com.autonavi.minimap.ajx3.Ajx r0 = com.autonavi.minimap.ajx3.Ajx.getInstance()
            boolean r0 = r0.getPerformanceLogEnabled()
            r1 = 0
            if (r0 == 0) goto L_0x002d
            java.lang.String r3 = getAjxUrlFromSDCard(r3, r4)
            if (r3 == 0) goto L_0x002d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r3)
            java.lang.String r3 = ".config"
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            boolean r0 = defpackage.ahd.b(r3)
            if (r0 == 0) goto L_0x002d
            byte[] r3 = defpackage.ahd.d(r3)
            goto L_0x002e
        L_0x002d:
            r3 = r1
        L_0x002e:
            if (r3 != 0) goto L_0x004f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r4 = getAjxUrl(r4)
            r0.append(r4)
            java.lang.String r4 = ".config"
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            boolean r0 = com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo.isFileExists(r4)
            if (r0 == 0) goto L_0x004f
            byte[] r3 = com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo.getFileDataByPath(r4)
        L_0x004f:
            if (r3 == 0) goto L_0x00eb
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x00e7 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x00e7 }
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x00e7 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x00e7 }
            com.autonavi.minimap.ajx3.loading.LoadingConfig r4 = new com.autonavi.minimap.ajx3.loading.LoadingConfig     // Catch:{ Exception -> 0x00e7 }
            r4.<init>()     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r0 = "locationMode"
            int r0 = r3.optInt(r0)     // Catch:{ Exception -> 0x00e7 }
            r4.locationMode = r0     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r0 = "spm"
            java.lang.String r0 = r3.optString(r0, r1)     // Catch:{ Exception -> 0x00e7 }
            r4.spm = r0     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r0 = "launchMode"
            java.lang.String r0 = r3.optString(r0, r1)     // Catch:{ Exception -> 0x00e7 }
            r4.launchMode = r0     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r0 = "loadding"
            org.json.JSONObject r0 = r3.optJSONObject(r0)     // Catch:{ Exception -> 0x00e7 }
            if (r0 == 0) goto L_0x00c2
            java.lang.String r2 = "needLoading"
            boolean r2 = r0.optBoolean(r2)     // Catch:{ Exception -> 0x00e7 }
            r4.needLoading = r2     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r2 = "barType"
            java.lang.String r2 = r0.optString(r2)     // Catch:{ Exception -> 0x00e7 }
            r4.barType = r2     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r2 = "title"
            java.lang.String r2 = r0.optString(r2)     // Catch:{ Exception -> 0x00e7 }
            r4.title = r2     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r2 = "bgcolor"
            java.lang.String r2 = r0.optString(r2)     // Catch:{ Exception -> 0x00e7 }
            r4.bgcolor = r2     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r2 = "bgOpacity"
            int r2 = r0.optInt(r2)     // Catch:{ Exception -> 0x00e7 }
            r4.bgOpacity = r2     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r2 = "hasLogo"
            boolean r2 = r0.optBoolean(r2)     // Catch:{ Exception -> 0x00e7 }
            r4.hasLogo = r2     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r2 = "AMap"
            org.json.JSONObject r0 = r0.optJSONObject(r2)     // Catch:{ Exception -> 0x00e7 }
            if (r0 == 0) goto L_0x00c2
            java.lang.String r2 = "controlOpacity"
            int r0 = r0.optInt(r2)     // Catch:{ Exception -> 0x00e7 }
            r4.amapControlOpacity = r0     // Catch:{ Exception -> 0x00e7 }
        L_0x00c2:
            java.lang.String r0 = "showMap"
            boolean r0 = r3.optBoolean(r0)     // Catch:{ Exception -> 0x00e7 }
            r4.showMap = r0     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r0 = "recoveryMapState"
            boolean r0 = r3.optBoolean(r0)     // Catch:{ Exception -> 0x00e7 }
            r4.recoveryMapState = r0     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r0 = "showGpsCenter"
            boolean r0 = r3.optBoolean(r0)     // Catch:{ Exception -> 0x00e7 }
            r4.showGpsCenter = r0     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r0 = "overlay"
            java.lang.String r2 = ""
            java.lang.String r3 = r3.optString(r0, r2)     // Catch:{ Exception -> 0x00e7 }
            r4.overlayStatus = r3     // Catch:{ Exception -> 0x00e7 }
            return r4
        L_0x00e7:
            r3 = move-exception
            r3.printStackTrace()
        L_0x00eb:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.Ajx3Path.getLoadingConfig(android.content.Context, java.lang.String):com.autonavi.minimap.ajx3.loading.LoadingConfig");
    }

    public static String getAjxUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String scheme = Uri.parse(str).getScheme();
        return !TextUtils.isEmpty(scheme) ? str.substring(scheme.length() + LEN_SCHEMA_SPLIT) : str;
    }

    private static String getAjxUrlFromSDCard(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith(AjxPathLoader.DOMAIN)) {
            StringBuilder sb = new StringBuilder();
            sb.append(FileUtil.getExternalFilesDir(context).getAbsolutePath());
            sb.append(File.separator);
            sb.append("js/");
            return str.replace(AjxPathLoader.DOMAIN, sb.toString());
        } else if (str.startsWith("file://")) {
            return str.replace("file://", "");
        } else {
            return null;
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.widget.ui.TitleBar creatTitleBar(android.content.Context r17, com.autonavi.minimap.ajx3.loading.LoadingConfig r18) {
        /*
            r1 = r17
            r2 = r18
            r3 = 0
            if (r2 != 0) goto L_0x0008
            return r3
        L_0x0008:
            java.lang.String r4 = r2.barType     // Catch:{ Exception -> 0x0243 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0243 }
            if (r4 != 0) goto L_0x0241
            java.lang.String r4 = r2.barType     // Catch:{ Exception -> 0x0243 }
            int r6 = r4.hashCode()     // Catch:{ Exception -> 0x0243 }
            r7 = 7
            r8 = 9
            r9 = 10
            r10 = 12
            r11 = 13
            r12 = 17
            r13 = 18
            r14 = 19
            r15 = 20
            r5 = 8
            r3 = 0
            switch(r6) {
                case -2135432420: goto L_0x012f;
                case -2135432418: goto L_0x0123;
                case -2135432417: goto L_0x0117;
                case -2135432296: goto L_0x010b;
                case -1870000329: goto L_0x0100;
                case -1870000328: goto L_0x00f5;
                case -1870000327: goto L_0x00ea;
                case -1870000267: goto L_0x00df;
                case -1870000236: goto L_0x00d4;
                case -1870000235: goto L_0x00c8;
                case -1870000234: goto L_0x00bb;
                case -1870000233: goto L_0x00ae;
                case -1870000231: goto L_0x00a1;
                case -1870000228: goto L_0x0094;
                case -1870000205: goto L_0x0087;
                case -1870000204: goto L_0x007a;
                case -1870000202: goto L_0x006d;
                case -1870000201: goto L_0x0060;
                case -1773784283: goto L_0x0053;
                case -1307248582: goto L_0x0047;
                case -1307248581: goto L_0x003b;
                case -1307248580: goto L_0x002f;
                default: goto L_0x002d;
            }     // Catch:{ Exception -> 0x0243 }
        L_0x002d:
            goto L_0x013b
        L_0x002f:
            java.lang.String r6 = "title_c"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 5
            goto L_0x013c
        L_0x003b:
            java.lang.String r6 = "title_b"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 4
            goto L_0x013c
        L_0x0047:
            java.lang.String r6 = "title_a"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 0
            goto L_0x013c
        L_0x0053:
            java.lang.String r6 = "title_feed"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 21
            goto L_0x013c
        L_0x0060:
            java.lang.String r6 = "title_e5"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 20
            goto L_0x013c
        L_0x006d:
            java.lang.String r6 = "title_e4"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 19
            goto L_0x013c
        L_0x007a:
            java.lang.String r6 = "title_e2"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 18
            goto L_0x013c
        L_0x0087:
            java.lang.String r6 = "title_e1"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 17
            goto L_0x013c
        L_0x0094:
            java.lang.String r6 = "title_d9"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 13
            goto L_0x013c
        L_0x00a1:
            java.lang.String r6 = "title_d6"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 12
            goto L_0x013c
        L_0x00ae:
            java.lang.String r6 = "title_d4"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 10
            goto L_0x013c
        L_0x00bb:
            java.lang.String r6 = "title_d3"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 9
            goto L_0x013c
        L_0x00c8:
            java.lang.String r6 = "title_d2"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 8
            goto L_0x013c
        L_0x00d4:
            java.lang.String r6 = "title_d1"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 7
            goto L_0x013c
        L_0x00df:
            java.lang.String r6 = "title_c1"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 6
            goto L_0x013c
        L_0x00ea:
            java.lang.String r6 = "title_a3"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 3
            goto L_0x013c
        L_0x00f5:
            java.lang.String r6 = "title_a2"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 2
            goto L_0x013c
        L_0x0100:
            java.lang.String r6 = "title_a1"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 1
            goto L_0x013c
        L_0x010b:
            java.lang.String r6 = "title_d3n"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 11
            goto L_0x013c
        L_0x0117:
            java.lang.String r6 = "title_d13"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 16
            goto L_0x013c
        L_0x0123:
            java.lang.String r6 = "title_d12"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 15
            goto L_0x013c
        L_0x012f:
            java.lang.String r6 = "title_d10"
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0243 }
            if (r4 == 0) goto L_0x013b
            r4 = 14
            goto L_0x013c
        L_0x013b:
            r4 = -1
        L_0x013c:
            switch(r4) {
                case 0: goto L_0x023a;
                case 1: goto L_0x01fa;
                case 2: goto L_0x01f3;
                case 3: goto L_0x01ec;
                case 4: goto L_0x01e0;
                case 5: goto L_0x01d9;
                case 6: goto L_0x01d2;
                case 7: goto L_0x01cb;
                case 8: goto L_0x01c4;
                case 9: goto L_0x01bd;
                case 10: goto L_0x01b4;
                case 11: goto L_0x01ad;
                case 12: goto L_0x01a3;
                case 13: goto L_0x019c;
                case 14: goto L_0x0193;
                case 15: goto L_0x018a;
                case 16: goto L_0x017c;
                case 17: goto L_0x0175;
                case 18: goto L_0x016e;
                case 19: goto L_0x0167;
                case 20: goto L_0x0160;
                case 21: goto L_0x0142;
                default: goto L_0x013f;
            }     // Catch:{ Exception -> 0x0243 }
        L_0x013f:
            r3 = 0
            goto L_0x0240
        L_0x0142:
            com.autonavi.widget.ui.TitleBar r2 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r4 = 4096(0x1000, float:5.74E-42)
            r2.<init>(r1, r4)     // Catch:{ Exception -> 0x0243 }
            int r1 = com.autonavi.minimap.R.drawable.icon_a15_selector     // Catch:{ Exception -> 0x0243 }
            r2.setBackImg(r1)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r1 = "#4287ff"
            int r1 = android.graphics.Color.parseColor(r1)     // Catch:{ Exception -> 0x0243 }
            r2.setBackgroundColor(r1)     // Catch:{ Exception -> 0x0243 }
            android.widget.EditText r1 = r2.getEditText()     // Catch:{ Exception -> 0x0243 }
            r1.setEnabled(r3)     // Catch:{ Exception -> 0x0243 }
            goto L_0x023f
        L_0x0160:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r3.<init>(r1, r15)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x0167:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r3.<init>(r1, r14)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x016e:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r3.<init>(r1, r13)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x0175:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r3.<init>(r1, r12)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x017c:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r4 = 16
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r1 = r2.title     // Catch:{ Exception -> 0x0243 }
            r3.setTitle(r1)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x018a:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r2 = 15
            r3.<init>(r1, r2)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x0193:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r2 = 14
            r3.<init>(r1, r2)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x019c:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r3.<init>(r1, r11)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01a3:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r3.<init>(r1, r10)     // Catch:{ Exception -> 0x0243 }
            r3.setActionImgVisibility(r5)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01ad:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r3.<init>(r1, r9)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01b4:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r2 = 11
            r3.<init>(r1, r2)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01bd:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r3.<init>(r1, r8)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01c4:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r3.<init>(r1, r5)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01cb:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r3.<init>(r1, r7)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01d2:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r2 = 6
            r3.<init>(r1, r2)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01d9:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r2 = 5
            r3.<init>(r1, r2)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01e0:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r4 = 4
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0243 }
            java.lang.String r1 = r2.title     // Catch:{ Exception -> 0x0243 }
            r3.setTitle(r1)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01ec:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r2 = 3
            r3.<init>(r1, r2)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01f3:
            com.autonavi.widget.ui.TitleBar r3 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r2 = 2
            r3.<init>(r1, r2)     // Catch:{ Exception -> 0x0243 }
            goto L_0x0240
        L_0x01fa:
            com.autonavi.widget.ui.TitleBar r4 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r6 = 1
            r4.<init>(r1, r6)     // Catch:{ Exception -> 0x0243 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Exception -> 0x0243 }
            r1.<init>()     // Catch:{ Exception -> 0x0243 }
            java.lang.String r6 = r2.title     // Catch:{ Exception -> 0x0243 }
            java.lang.String r7 = ";"
            boolean r6 = r6.contains(r7)     // Catch:{ Exception -> 0x0243 }
            if (r6 == 0) goto L_0x0228
            java.lang.String r2 = r2.title     // Catch:{ Exception -> 0x0243 }
            java.lang.String r6 = ";"
            java.lang.String[] r2 = r2.split(r6)     // Catch:{ Exception -> 0x0243 }
            int r6 = r2.length     // Catch:{ Exception -> 0x0243 }
            r7 = 0
        L_0x0219:
            if (r7 >= r6) goto L_0x0232
            r8 = r2[r7]     // Catch:{ Exception -> 0x0243 }
            com.autonavi.widget.ui.TitleBar$b r9 = new com.autonavi.widget.ui.TitleBar$b     // Catch:{ Exception -> 0x0243 }
            r9.<init>(r8)     // Catch:{ Exception -> 0x0243 }
            r1.add(r9)     // Catch:{ Exception -> 0x0243 }
            int r7 = r7 + 1
            goto L_0x0219
        L_0x0228:
            com.autonavi.widget.ui.TitleBar$b r6 = new com.autonavi.widget.ui.TitleBar$b     // Catch:{ Exception -> 0x0243 }
            java.lang.String r2 = r2.title     // Catch:{ Exception -> 0x0243 }
            r6.<init>(r2)     // Catch:{ Exception -> 0x0243 }
            r1.add(r6)     // Catch:{ Exception -> 0x0243 }
        L_0x0232:
            r4.addTabs(r1, r3)     // Catch:{ Exception -> 0x0243 }
            r4.setActionImgVisibility(r5)     // Catch:{ Exception -> 0x0243 }
            r3 = r4
            goto L_0x0240
        L_0x023a:
            com.autonavi.widget.ui.TitleBar r2 = new com.autonavi.widget.ui.TitleBar     // Catch:{ Exception -> 0x0243 }
            r2.<init>(r1, r3)     // Catch:{ Exception -> 0x0243 }
        L_0x023f:
            r3 = r2
        L_0x0240:
            return r3
        L_0x0241:
            r1 = r3
            goto L_0x0249
        L_0x0243:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            r1 = 0
        L_0x0249:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.Ajx3Path.creatTitleBar(android.content.Context, com.autonavi.minimap.ajx3.loading.LoadingConfig):com.autonavi.widget.ui.TitleBar");
    }
}
