package com.amap.bundle.drive.ajx.tools;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.model.NavigationResult;
import com.amap.bundle.drivecommon.navi.navidata.TmcColor;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.OfflineMsgCode;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.route.RouteService;
import com.autonavi.jni.ae.route.model.LightBarItem;
import com.autonavi.jni.ae.route.model.TmcRoutePath;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class DriveRouteHomeCompanyManager {
    private static final String FILE_NAME_COMPANY_PRE;
    private static final String FILE_NAME_HOME_PRE;
    private static final String FILE_NAME_SUFFIX = ".png";
    public static final String MY_LOCATION_DES = "我的位置";
    private static final String SP_COMPANY = "company";
    private static final String SP_HOME = "home";
    private static final String SP_NAME = "routecar_tmc_last";
    private static final String TAG = "DriveRouteHomeCompanyUtil";
    private static final int TMC_REQUEST_FAIL = 2;
    private static final int TMC_REQUEST_SUCCESS = 1;
    private static DriveRouteHomeCompanyManager mInstance = new DriveRouteHomeCompanyManager();
    final Callback<tc> innerCallback = new Callback<tc>() {
        public void callback(final tc tcVar) {
            ahn.b().execute(new Runnable() {
                public void run() {
                    DriveRouteHomeCompanyManager.this.onTmcRequestSuccess(tcVar);
                }
            });
        }

        public void error(Throwable th, boolean z) {
            DriveRouteHomeCompanyManager.this.onTmcRequesetFailed();
        }
    };
    private JsFunctionCallback mJsFunctionCallback;
    private AosRequest mTmcRequest;
    private int mTrafficBarHeight = agn.a(AMapPageUtil.getAppContext(), 6.0f);
    private int mTrafficBarWidth = agn.a(AMapPageUtil.getAppContext(), 280.0f);
    private int mergeTmcBarWidth = 0;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/Android/data/com.autonavi.minimap/files/js/tmc_home");
        FILE_NAME_HOME_PRE = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory());
        sb2.append("/Android/data/com.autonavi.minimap/files/js/tmc_company");
        FILE_NAME_COMPANY_PRE = sb2.toString();
    }

    private DriveRouteHomeCompanyManager() {
    }

    public static DriveRouteHomeCompanyManager getInstace() {
        return mInstance;
    }

    public String getCompanyPOI() {
        POI pOICompany = DriveUtil.getPOICompany();
        if (pOICompany == null) {
            return bny.c;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SP_COMPANY, bnx.b(pOICompany));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tj.a(TAG, String.valueOf(jSONObject));
        return jSONObject.toString();
    }

    public String getHomePOI() {
        POI pOIHome = DriveUtil.getPOIHome();
        if (pOIHome == null) {
            return bny.c;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SP_HOME, bnx.b(pOIHome));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tj.a(TAG, String.valueOf(jSONObject));
        return jSONObject.toString();
    }

    public String getHomeCompanyPOI() {
        POI pOIHome = DriveUtil.getPOIHome();
        POI pOICompany = DriveUtil.getPOICompany();
        JSONObject jSONObject = new JSONObject();
        if (pOIHome != null) {
            try {
                jSONObject.put(SP_HOME, bnx.b(pOIHome));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (pOICompany != null) {
            jSONObject.put(SP_COMPANY, bnx.b(pOICompany));
        }
        return jSONObject.toString();
    }

    public void requestTMCAndSavePic(int i, String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            this.mJsFunctionCallback = jsFunctionCallback;
        }
        POI curLocation = getCurLocation();
        if (curLocation == null) {
            tj.b(TAG, "getLatestPosition fail, do not request");
            return;
        }
        POI pOICompany = DriveUtil.getPOICompany();
        ta taVar = null;
        ta taVar2 = (pOICompany == null || !str.contains(SP_COMPANY)) ? null : new ta(curLocation, (ISearchPoiData) pOICompany.as(ISearchPoiData.class), CalcRouteScene.SCENE_COMPANY_TMC);
        POI pOIHome = DriveUtil.getPOIHome();
        if (pOIHome != null && str.contains(SP_HOME)) {
            taVar = new ta(curLocation, (ISearchPoiData) pOIHome.as(ISearchPoiData.class), CalcRouteScene.SCENE_HOME_TMC);
        }
        if (this.mTmcRequest != null) {
            in.a().a(this.mTmcRequest);
        }
        if (taVar != null && taVar2 != null) {
            this.mTmcRequest = om.a(i, this.innerCallback, taVar, taVar2);
        } else if (taVar2 != null && taVar == null) {
            this.mTmcRequest = om.a(i, this.innerCallback, taVar2);
        } else if (taVar == null || taVar2 != null) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback("");
            }
        } else {
            this.mTmcRequest = om.a(i, this.innerCallback, taVar);
        }
    }

    /* access modifiers changed from: private */
    public void onTmcRequesetFailed() {
        this.mJsFunctionCallback = null;
    }

    /* access modifiers changed from: private */
    public void onTmcRequestSuccess(tc tcVar) {
        List<ICarRouteResult> c = tcVar.c();
        if (c == null || c.size() <= 0) {
            onTmcRequesetFailed();
        } else if (c.size() == 1) {
            handleSingleResult(tcVar, c, c.get(0));
        } else {
            ICarRouteResult iCarRouteResult = c.get(0);
            ICarRouteResult iCarRouteResult2 = c.get(1);
            if (iCarRouteResult == null && iCarRouteResult2 == null) {
                onTmcRequesetFailed();
            } else if (iCarRouteResult == null && iCarRouteResult2 != null) {
                handleSingleResult(tcVar, c, iCarRouteResult2);
            } else if (iCarRouteResult == null || iCarRouteResult2 != null) {
                if (!(iCarRouteResult == null || iCarRouteResult2 == null)) {
                    tn.a().a(iCarRouteResult.getCalcRouteScene(), iCarRouteResult.getCalcRouteResult());
                    tn.a().a(iCarRouteResult2.getCalcRouteScene(), iCarRouteResult2.getCalcRouteResult());
                    NavigationResult naviResultData = iCarRouteResult.getNaviResultData();
                    NavigationPath focusNavigationPath = iCarRouteResult.getFocusNavigationPath();
                    NavigationResult naviResultData2 = iCarRouteResult.getNaviResultData();
                    NavigationPath focusNavigationPath2 = iCarRouteResult2.getFocusNavigationPath();
                    if ((naviResultData == null || naviResultData.mPaths == null || naviResultData.mPathNum <= 0 || focusNavigationPath == null) && (naviResultData2 == null || naviResultData2.mPaths == null || naviResultData2.mPathNum <= 0 || focusNavigationPath2 == null)) {
                        onTmcRequesetFailed();
                    } else if (tcVar.a() || tcVar.errorCode == OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getnCode()) {
                        if (naviResultData == null && naviResultData2 == null) {
                            onTmcRequesetFailed();
                            return;
                        }
                        handleTmcRequsetData(c);
                    }
                }
            } else {
                handleSingleResult(tcVar, c, iCarRouteResult);
            }
        }
    }

    private void handleSingleResult(tc tcVar, List<ICarRouteResult> list, ICarRouteResult iCarRouteResult) {
        if (iCarRouteResult != null) {
            tn.a().a(iCarRouteResult.getCalcRouteScene(), iCarRouteResult.getCalcRouteResult());
            NavigationResult naviResultData = iCarRouteResult.getNaviResultData();
            NavigationPath focusNavigationPath = iCarRouteResult.getFocusNavigationPath();
            if (naviResultData == null || naviResultData.mPaths == null || naviResultData.mPathNum <= 0 || focusNavigationPath == null) {
                onTmcRequesetFailed();
                return;
            }
            if (tcVar.a() || tcVar.errorCode == OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getnCode()) {
                if (iCarRouteResult.getNaviResultData() == null) {
                    onTmcRequesetFailed();
                    return;
                }
                handleTmcRequsetData(list);
            }
            return;
        }
        onTmcRequesetFailed();
    }

    private void handleTmcRequsetData(List<ICarRouteResult> list) {
        int i;
        boolean z;
        int i2;
        boolean z2;
        StringBuilder sb = new StringBuilder();
        sb.append(FILE_NAME_HOME_PRE);
        sb.append(System.currentTimeMillis());
        sb.append(".png");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(FILE_NAME_COMPANY_PRE);
        sb3.append(System.currentTimeMillis());
        sb3.append(".png");
        String sb4 = sb3.toString();
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences(SP_NAME, 0);
        if (list == null || list.size() <= 0) {
            z2 = false;
            i2 = 0;
            z = false;
            i = 0;
        } else {
            z2 = false;
            i2 = 0;
            z = false;
            i = 0;
            for (IRouteResultData next : list) {
                if (next != null) {
                    ICarRouteResult iCarRouteResult = (ICarRouteResult) next;
                    if (iCarRouteResult.getNaviResultData() != null) {
                        iCarRouteResult.setFocusRouteIndex(0);
                        NavigationPath focusNavigationPath = iCarRouteResult.getFocusNavigationPath();
                        if (focusNavigationPath != null) {
                            if (CalcRouteScene.SCENE_HOME_TMC == iCarRouteResult.getCalcRouteScene()) {
                                deleteFile(sharedPreferences.getString(SP_HOME, ""));
                                z2 = loadCarRouteResult(iCarRouteResult, sb2);
                                if (z2) {
                                    sharedPreferences.edit().putString(SP_HOME, sb2).apply();
                                }
                                i2 = focusNavigationPath.mCostTime;
                            } else if (CalcRouteScene.SCENE_COMPANY_TMC == iCarRouteResult.getCalcRouteScene()) {
                                deleteFile(sharedPreferences.getString(SP_COMPANY, ""));
                                z = loadCarRouteResult(iCarRouteResult, sb4);
                                if (z) {
                                    sharedPreferences.edit().putString(SP_COMPANY, sb4).apply();
                                }
                                i = focusNavigationPath.mCostTime;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
        JSONObject jSONObject = new JSONObject();
        if (z2) {
            try {
                jSONObject.put(SP_HOME, "file:/".concat(String.valueOf(sb2)));
                jSONObject.put("home_time", i2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (z) {
            jSONObject.put(SP_COMPANY, "file:/".concat(String.valueOf(sb4)));
            jSONObject.put("company_time", i);
        }
        if (this.mJsFunctionCallback != null) {
            this.mJsFunctionCallback.callback(jSONObject.toString());
        }
        this.mJsFunctionCallback = null;
    }

    private boolean loadCarRouteResult(ICarRouteResult iCarRouteResult, String str) {
        if (iCarRouteResult == null) {
            return false;
        }
        int i = iCarRouteResult.getFocusNavigationPath().mPathlength;
        LightBarItem[] decodeRouteTmcBar = RouteService.decodeRouteTmcBar(iCarRouteResult.getBackUpTbtData(), new TmcRoutePath());
        if (decodeRouteTmcBar == null || i <= 0) {
            return false;
        }
        Bitmap createTmcBar = createTmcBar(decodeRouteTmcBar, i);
        if (createTmcBar == null) {
            return false;
        }
        return storeBitmap(createTmcBar, str);
    }

    public Bitmap createTmcBar(LightBarItem[] lightBarItemArr, int i) {
        TmcColor tmcColor;
        this.mergeTmcBarWidth = 0;
        ArrayList arrayList = new ArrayList();
        if (lightBarItemArr == null) {
            return null;
        }
        int length = lightBarItemArr.length - 1;
        int i2 = 0;
        for (int i3 = 0; i3 <= length; i3++) {
            int i4 = lightBarItemArr[i3].length;
            i2 += i4;
            int i5 = (i4 * this.mTrafficBarWidth) / i;
            if (i5 <= 0) {
                i5 = 1;
            }
            switch (lightBarItemArr[i3].status) {
                case 0:
                    tmcColor = TmcColor.UNKNOWN;
                    break;
                case 1:
                    tmcColor = TmcColor.UNBLOCK;
                    break;
                case 2:
                    tmcColor = TmcColor.SLOW;
                    break;
                case 3:
                    tmcColor = TmcColor.BLOCK;
                    break;
                case 4:
                    tmcColor = TmcColor.GRIDLOCKED;
                    break;
                default:
                    tmcColor = TmcColor.UNKNOWN;
                    break;
            }
            arrayList.add(dependColorCreateBitmap(i5, this.mTrafficBarHeight, tmcColor));
        }
        if (i2 < i) {
            int i6 = ((i - i2) * this.mTrafficBarWidth) / i;
            if (i6 <= 0) {
                i6 = 1;
            }
            arrayList.add(dependColorCreateBitmap(i6, this.mTrafficBarHeight, TmcColor.UNBLOCK));
        }
        if (this.mergeTmcBarWidth < this.mTrafficBarWidth) {
            int i7 = this.mTrafficBarWidth - this.mergeTmcBarWidth;
            if (i7 <= 0) {
                i7 = 1;
            }
            arrayList.add(dependColorCreateBitmap(i7, this.mTrafficBarHeight, TmcColor.UNBLOCK));
        }
        return mergeBitmap(arrayList);
    }

    private Bitmap mergeBitmap(ArrayList<Bitmap> arrayList) {
        Bitmap createBitmap = Bitmap.createBitmap(this.mTrafficBarWidth, arrayList.get(0).getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        int size = arrayList.size() - 1;
        int i = 0;
        for (int i2 = 0; i2 <= size; i2++) {
            Bitmap bitmap = arrayList.get(i2);
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, (float) i, 0.0f, null);
                i += bitmap.getWidth();
                bitmap.recycle();
            }
        }
        return i > this.mTrafficBarWidth ? Bitmap.createScaledBitmap(createBitmap, (int) (((double) createBitmap.getWidth()) * 0.9d), createBitmap.getHeight(), true) : createBitmap;
    }

    private Bitmap dependColorCreateBitmap(int i, int i2, TmcColor tmcColor) {
        this.mergeTmcBarWidth += i;
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        new Canvas(createBitmap).drawARGB(255, tmcColor.R(), tmcColor.G(), tmcColor.B());
        return createBitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0070 A[SYNTHETIC, Splitter:B:30:0x0070] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0081 A[SYNTHETIC, Splitter:B:39:0x0081] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean storeBitmap(android.graphics.Bitmap r4, java.lang.String r5) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            java.io.File r5 = r0.getParentFile()
            boolean r5 = r5.exists()
            r1 = 0
            if (r5 != 0) goto L_0x0039
            java.io.File r5 = r0.getParentFile()
            boolean r5 = r5.mkdirs()
            if (r5 != 0) goto L_0x0039
            java.io.File r5 = r0.getParentFile()
            boolean r5 = r5.exists()
            if (r5 == 0) goto L_0x0038
            java.io.File r5 = r0.getParentFile()
            boolean r5 = r5.canRead()
            if (r5 == 0) goto L_0x0038
            java.io.File r5 = r0.getParentFile()
            boolean r5 = r5.canWrite()
            if (r5 != 0) goto L_0x0039
        L_0x0038:
            return r1
        L_0x0039:
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream
            r5.<init>()
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.PNG
            r3 = 100
            r4.compress(r2, r3, r5)
            r4 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0068, all -> 0x0064 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0068, all -> 0x0064 }
            byte[] r4 = r5.toByteArray()     // Catch:{ Exception -> 0x0062 }
            r2.write(r4)     // Catch:{ Exception -> 0x0062 }
            r2.flush()     // Catch:{ Exception -> 0x0062 }
            r2.close()     // Catch:{ IOException -> 0x005c }
            r5.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x0060
        L_0x005c:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0060:
            r4 = 1
            return r4
        L_0x0062:
            r4 = move-exception
            goto L_0x006b
        L_0x0064:
            r0 = move-exception
            r2 = r4
            r4 = r0
            goto L_0x007f
        L_0x0068:
            r0 = move-exception
            r2 = r4
            r4 = r0
        L_0x006b:
            r4.printStackTrace()     // Catch:{ all -> 0x007e }
            if (r2 == 0) goto L_0x0076
            r2.close()     // Catch:{ IOException -> 0x0074 }
            goto L_0x0076
        L_0x0074:
            r4 = move-exception
            goto L_0x007a
        L_0x0076:
            r5.close()     // Catch:{ IOException -> 0x0074 }
            goto L_0x007d
        L_0x007a:
            r4.printStackTrace()
        L_0x007d:
            return r1
        L_0x007e:
            r4 = move-exception
        L_0x007f:
            if (r2 == 0) goto L_0x0087
            r2.close()     // Catch:{ IOException -> 0x0085 }
            goto L_0x0087
        L_0x0085:
            r5 = move-exception
            goto L_0x008b
        L_0x0087:
            r5.close()     // Catch:{ IOException -> 0x0085 }
            goto L_0x008e
        L_0x008b:
            r5.printStackTrace()
        L_0x008e:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.ajx.tools.DriveRouteHomeCompanyManager.storeBitmap(android.graphics.Bitmap, java.lang.String):boolean");
    }

    public void startSetHome(bid bid) {
        POI pOIHome = DriveUtil.getPOIHome();
        startSearchCallBackFragment(bid, pOIHome != null ? pOIHome.getName() : null, 1004, bid.getContext().getString(R.string.commute_set_home_hint));
    }

    public void startSetCompany(bid bid) {
        POI pOICompany = DriveUtil.getPOICompany();
        startSearchCallBackFragment(bid, pOICompany != null ? pOICompany.getName() : null, 1005, bid.getContext().getString(R.string.act_fromto_company_input_hint));
    }

    private void deleteFile(String str) {
        if (!TextUtils.isEmpty(str)) {
            File file = new File(str);
            if (file.exists()) {
                ahd.a(file);
            }
        }
    }

    private POI getCurLocation() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition == null) {
            return null;
        }
        POI createPOI = POIFactory.createPOI();
        createPOI.setName("我的位置");
        createPOI.setPoint(latestPosition);
        return createPOI;
    }

    private void startSearchCallBackFragment(bid bid, String str, int i, String str2) {
        auz auz = (auz) a.a.a(auz.class);
        if (auz != null) {
            PageBundle pageBundle = new PageBundle();
            if (TextUtils.isEmpty(str) || !str.equalsIgnoreCase("我的位置")) {
                pageBundle.putString(TrafficUtil.KEYWORD, str);
            } else {
                pageBundle.putString(TrafficUtil.KEYWORD, "");
            }
            pageBundle.putString("search_hint", str2);
            if (i == 1004 || i == 1005) {
                pageBundle.putString("SUPER_ID", "j");
            }
            if (i == 1004) {
                pageBundle.putString("address", bid.getContext().getString(R.string.home));
            } else if (i == 1005) {
                pageBundle.putString("address", bid.getContext().getString(R.string.company));
            }
            auz.a(pageBundle, i);
        }
    }
}
