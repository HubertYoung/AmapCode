package com.amap.bundle.drivecommon.navi.navidata;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.text.TextUtils;
import com.alipay.zoloz.toyger.bean.Config;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.model.NavigationResult;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.guide.model.NaviStatisticsInfo;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.jni.drive.ajx.TracePoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.json.JSONObject;

public class NavigationDataResult extends AbstractNavigationDataResult implements ICarRouteResult {
    private static final String DIGITAL_EYE_USE_PAY = "digital_eye_use_pay";
    private static final String DISPLAY = "display";
    private static final String GAS_ACTIVITY = "gas_activity";
    private static final String HINT_LOGIN = "hint_login";
    private static final String RANK = "rank";
    private static final String RESULT = "result";
    private static final String SHARE_URL = "share_url";
    private static final String TIP_TEXT = "tip_text";
    private static final String USE_PAY = "use_pay";
    private static final long serialVersionUID = 707329;
    private int account = 0;
    public int alreadyPassbyViaPointCount;
    private transient CalcRouteResult calcRouteResult;
    private String carPlate;
    private ArrayList<GeoPoint> currentList = new ArrayList<>();
    private String currentNaviId;
    private ArrayList<GeoPoint> deviationPoints = new ArrayList<>();
    private int displayAdWidget = 0;
    private int errorCode;
    private GeoPoint firstPoint;
    private POI fromPOI = null;
    private String gasActivities;
    private int hintLogin = 0;
    private transient Intent intent;
    private boolean isParticipation;
    private GeoPoint lastPoint;
    private boolean mIsMultiRoute = false;
    private POI mOriginalFromPoi = null;
    private POI mSharedEndPOI = null;
    private ArrayList<TracePoint> mTracePointList;
    private String method;
    private ArrayList<POI> midPOIs;
    private transient NaviStatisticsInfo naviStaticInfo;
    private ArrayList<GeoPoint> orgLinePoints = new ArrayList<>();
    private ArrayList<POI> originMidPOIs;
    private ArrayList<ArrayList<GeoPoint>> passedPoints = new ArrayList<>();
    private ArrayList<POI> passedViaPoints;
    private POI quitPOI = null;
    private double rank = -1.0d;
    private String rankTimeStamp = "";
    private int recommendFlag;
    private String roadCameraUsePay;
    private String routeNaviId;
    private LinkedHashSet routeNaviIdAllContainer;
    public String share_url;
    private POI sharedFromPOI = null;
    private ArrayList<POI> sharedMidPOIs;
    private ArrayList<GeoPoint> sharedMidPoints;
    private POI sharedToPOI = null;
    private String tid;
    private String timeStamp = "";
    private String tip_text;
    private POI toPOI = null;
    private String viaCityCodeList;

    public String genMethodStr(int i) {
        return null;
    }

    public byte[] getBackUpTbtData() {
        return null;
    }

    public Class<?> getClassType() {
        return null;
    }

    public NavigationPath getFocusNavigationPath() {
        return null;
    }

    public String getFromCityCode() {
        return null;
    }

    public POI getMainPoi() {
        return null;
    }

    public NavigationResult getNaviResultData() {
        return null;
    }

    public NavigationPath getNavigationPath(int i) {
        return null;
    }

    public int getStationsCount() {
        return 0;
    }

    public String getToCityCode() {
        return null;
    }

    public boolean hasData() {
        return false;
    }

    public boolean isLongDisResult() {
        return false;
    }

    public boolean isM_bNative() {
        return false;
    }

    public boolean isSceneResult() {
        return false;
    }

    public boolean isServiceAreaMode() {
        return false;
    }

    public boolean isSuggestOnfoot() {
        return false;
    }

    public boolean isViaCityMode() {
        return false;
    }

    public boolean isViaRoadMode() {
        return false;
    }

    public boolean needSaveHistory() {
        return false;
    }

    public boolean parseTBTData(byte[] bArr) {
        return false;
    }

    public void reset() {
    }

    public void restoreData() {
    }

    public void saveData() {
    }

    public void setM_bNative(boolean z) {
    }

    public void setMainPoi(POI poi) {
    }

    public void setNaviResultData(POI poi, POI poi2, NavigationResult navigationResult, String str) {
    }

    public void setSceneResult(boolean z) {
    }

    public void setServiceAreaMode(boolean z) {
    }

    public void setStationCount(int i) {
    }

    public void setSuggestOnfoot(boolean z) {
    }

    public void setViaCityMode(boolean z) {
    }

    public void setViaRoadMode(boolean z) {
    }

    public void setTraceListReference(ArrayList<TracePoint> arrayList) {
        this.mTracePointList = arrayList;
    }

    public int parseData(byte[] bArr, int i, int i2) {
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
            if (bno.a) {
                ku a = ku.a();
                StringBuilder sb = new StringBuilder("[rank]");
                sb.append(jSONObject.toString());
                a.c("Rank", sb.toString());
            }
            boolean z = jSONObject.getBoolean("result");
            if (jSONObject.has(HINT_LOGIN)) {
                this.hintLogin = jSONObject.getInt(HINT_LOGIN);
            }
            if (z) {
                this.rankTimeStamp = jSONObject.getString("timestamp");
                int indexOf = this.rankTimeStamp.indexOf(".");
                if (indexOf > 0) {
                    this.timeStamp = this.rankTimeStamp.substring(0, indexOf);
                }
                setRank(jSONObject.getDouble(RANK));
                if (jSONObject.has(GAS_ACTIVITY)) {
                    setGasActivities(jSONObject.optString(GAS_ACTIVITY));
                }
                if (jSONObject.has(USE_PAY)) {
                    JSONObject optJSONObject = jSONObject.optJSONObject(USE_PAY);
                    if (optJSONObject != null) {
                        if (optJSONObject.has("result")) {
                            setIsParticipation(optJSONObject.getBoolean("result"));
                        }
                        if (optJSONObject.has(TIP_TEXT)) {
                            setTipText(optJSONObject.getString(TIP_TEXT));
                        }
                        if (optJSONObject.has(SHARE_URL)) {
                            this.share_url = optJSONObject.getString(SHARE_URL);
                            if (this.share_url != null) {
                                setShareUrl(this.share_url);
                            }
                        }
                        if (optJSONObject.has(DISPLAY)) {
                            setFocusRouteIndex(optJSONObject.optInt(DISPLAY));
                        }
                    }
                }
                if (jSONObject.has(DIGITAL_EYE_USE_PAY)) {
                    setRoadCameraUsePay(jSONObject.getString(DIGITAL_EYE_USE_PAY));
                }
            }
            return 0;
        } catch (Exception unused) {
            return 1;
        }
    }

    public String getShareSinaWeiboBody() {
        if (this.naviStaticInfo == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_from_string));
        sb2.append(this.fromPOI.getName());
        sb.append(sb2.toString());
        if (this.midPOIs != null && this.midPOIs.size() > 0) {
            Iterator<POI> it = this.midPOIs.iterator();
            while (it.hasNext()) {
                POI next = it.next();
                if (next != null) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_pass_string));
                    sb3.append(next.getName());
                    sb.append(sb3.toString());
                }
            }
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_arrive));
        sb4.append(this.toPOI.getName());
        sb.append(sb4.toString());
        sb.append(",");
        StringBuilder sb5 = new StringBuilder();
        sb5.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_using_time));
        sb5.append(getRestTime(this.naviStaticInfo.drivenTime));
        sb.append(sb5.toString());
        sb.append(",");
        StringBuilder sb6 = new StringBuilder();
        sb6.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_average_speed));
        sb6.append(this.naviStaticInfo.averageSpeed);
        sb6.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_speed_unit));
        sb.append(sb6.toString());
        sb.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_at_app_name));
        return sb.toString();
    }

    public Bitmap getThumbnailsBitmap(String str) {
        return BitmapFactory.decodeFile(str);
    }

    public Bitmap getCompressBitmap(String str) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateInSampleSize(options, 320, Config.HQ_IMAGE_WIDTH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    public String getNaviSharePicPath(String str) {
        return getNaviShareFilePath(str);
    }

    public void onNewRouteStart() {
        if (this.currentList == null) {
            this.currentList = new ArrayList<>();
        } else if (this.currentList.size() < 2) {
            this.currentList.clear();
        } else {
            this.passedPoints.add(this.currentList);
            this.currentList = new ArrayList<>();
        }
    }

    public void setGasActivities(String str) {
        this.gasActivities = str;
    }

    public void setRank(double d) {
        this.rank = d;
    }

    public double getRank() {
        return this.rank;
    }

    public String getKey() {
        return this.tid;
    }

    public void setKey(String str) {
        this.tid = str;
    }

    public int getFocusRouteIndex() {
        return this.displayAdWidget;
    }

    public void setFocusRouteIndex(int i) {
        this.displayAdWidget = i;
    }

    public int getFocusStationIndex() {
        return this.account;
    }

    public void setFocusStationIndex(int i) {
        this.account = i;
    }

    public void setGotoNaviDlgIndex(int i) {
        this.errorCode = i;
    }

    public int getGotoNaviDlgIndex() {
        return this.errorCode;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public String getMethod() {
        return this.method;
    }

    public void setShareUrl(String str) {
        this.share_url = str;
    }

    public String getShareUrl() {
        return this.share_url;
    }

    public ArrayList<POI> getPassedViaPoints() {
        if (this.passedViaPoints == null) {
            this.passedViaPoints = new ArrayList<>();
        }
        return this.passedViaPoints;
    }

    public void setArgIntent(Intent intent2) {
        this.intent = intent2;
    }

    public Intent getArgIntent() {
        return this.intent;
    }

    public void setRecommendFlag(int i) {
        this.recommendFlag = i;
    }

    public int getRecommendFlag() {
        return this.recommendFlag;
    }

    public void setCarPlate(String str) {
        this.carPlate = str;
    }

    public String getCarPlate() {
        return this.carPlate;
    }

    public void setTipText(String str) {
        this.tip_text = str;
    }

    public void setIsParticipation(boolean z) {
        this.isParticipation = z;
    }

    public void setNaviId(String str) {
        this.currentNaviId = str;
    }

    public String getNaviId() {
        return this.currentNaviId;
    }

    public void setRoadCameraUsePay(String str) {
        this.roadCameraUsePay = str;
    }

    public int getSpeed() {
        if (this.naviStaticInfo != null) {
            return this.naviStaticInfo.averageSpeed;
        }
        return 0;
    }

    public int getDuration() {
        if (this.naviStaticInfo != null) {
            return this.naviStaticInfo.drivenTime / 60;
        }
        return 0;
    }

    public int getDistance() {
        if (this.naviStaticInfo != null) {
            return this.naviStaticInfo.drivenDist;
        }
        return 0;
    }

    public POI getFromPOI() {
        return this.fromPOI;
    }

    public void setFromPOI(POI poi) {
        this.fromPOI = null;
        if (poi != null) {
            this.fromPOI = poi.clone();
        }
        if (this.mOriginalFromPoi == null && isLegalPOI(poi)) {
            this.mOriginalFromPoi = poi.clone();
            if (TextUtils.isEmpty(this.mOriginalFromPoi.getName())) {
                this.mOriginalFromPoi.setName(AMapAppGlobal.getApplication().getResources().getString(R.string.my_location));
            }
        }
    }

    public POI getShareFromPOI() {
        if (this.sharedFromPOI == null) {
            this.sharedFromPOI = this.fromPOI.clone();
        }
        return this.sharedFromPOI;
    }

    public POI getToPOI() {
        return this.toPOI;
    }

    public void setToPOI(POI poi) {
        this.toPOI = poi;
        if (this.mSharedEndPOI == null) {
            this.mSharedEndPOI = poi;
        }
    }

    public POI getShareToPOI() {
        if (this.sharedToPOI == null && this.toPOI != null) {
            this.sharedToPOI = this.toPOI.clone();
        }
        return this.sharedToPOI;
    }

    public void setSharedToPOI(POI poi) {
        this.sharedToPOI = poi;
    }

    public boolean hasMidPos() {
        return this.midPOIs != null && this.midPOIs.size() > 0;
    }

    public ArrayList<POI> getMidPOI() {
        return this.midPOIs;
    }

    public ArrayList<POI> getMidPOIs() {
        return this.midPOIs;
    }

    public void setMidPOIs(ArrayList<POI> arrayList) {
        this.midPOIs = arrayList;
        setSharedMidPOIs();
    }

    public ArrayList<POI> getShareMidPOI() {
        return getShareMidPOIs();
    }

    public ArrayList<POI> getShareMidPOIs() {
        if (this.sharedMidPOIs == null) {
            this.sharedMidPOIs = new ArrayList<>();
        } else {
            this.sharedMidPOIs.clear();
        }
        if (this.midPOIs != null && this.midPOIs.size() > 0) {
            Iterator<POI> it = this.midPOIs.iterator();
            while (it.hasNext()) {
                this.sharedMidPOIs.add(it.next().clone());
            }
        }
        return this.sharedMidPOIs;
    }

    private void setSharedMidPOIs() {
        if (this.sharedMidPOIs == null) {
            this.sharedMidPOIs = new ArrayList<>();
            if (this.midPOIs != null && this.midPOIs.size() > 0) {
                Iterator<POI> it = this.midPOIs.iterator();
                while (it.hasNext()) {
                    this.sharedMidPOIs.add(it.next().clone());
                }
            }
        }
    }

    public void setOriginMidPOIs(List<so> list) {
        if (this.originMidPOIs == null) {
            this.originMidPOIs = new ArrayList<>();
        }
        this.originMidPOIs.clear();
        if (list != null && list.size() > 0) {
            for (so next : list) {
                if (next != null) {
                    this.originMidPOIs.add(next.a);
                }
            }
        }
    }

    public ArrayList<POI> getOriginMidPOIs() {
        return this.originMidPOIs;
    }

    public GeoPoint getShareStartPos() {
        return this.firstPoint;
    }

    public void setShareStartPos(GeoPoint geoPoint) {
        if (geoPoint != null) {
            this.firstPoint = geoPoint.clone();
        }
    }

    public GeoPoint getShareEndPos() {
        return this.lastPoint;
    }

    public void setShareEndPos(GeoPoint geoPoint) {
        if (geoPoint != null) {
            this.lastPoint = geoPoint.clone();
        }
    }

    public ArrayList<GeoPoint> getDeviationPoints() {
        return this.deviationPoints;
    }

    public void addDeviationPoint(GeoPoint geoPoint) {
        if (geoPoint != null) {
            if (this.deviationPoints == null) {
                this.deviationPoints = new ArrayList<>();
            }
            this.deviationPoints.add(geoPoint.clone());
        }
    }

    public POI getOriginalFromPoi() {
        return this.mOriginalFromPoi;
    }

    public ArrayList<GeoPoint> getOrgLinePoints() {
        return this.orgLinePoints;
    }

    public void setOrgRouteLine(ArrayList<GeoPoint> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            if (this.orgLinePoints == null) {
                this.orgLinePoints = new ArrayList<>();
            }
            if (this.orgLinePoints.size() <= 0) {
                this.orgLinePoints.addAll(arrayList);
            }
        }
    }

    public ArrayList<GeoPoint> getShareMidPos() {
        return getShareMidPoses();
    }

    public ArrayList<GeoPoint> getShareMidPoses() {
        if (this.sharedMidPoints == null) {
            this.sharedMidPoints = new ArrayList<>();
            ArrayList<POI> shareMidPOIs = getShareMidPOIs();
            if (shareMidPOIs != null && shareMidPOIs.size() > 0) {
                Iterator<POI> it = shareMidPOIs.iterator();
                while (it.hasNext()) {
                    this.sharedMidPoints.add(it.next().getPoint());
                }
            }
        }
        return this.sharedMidPoints;
    }

    public void setShareMidPos(ArrayList<GeoPoint> arrayList) {
        this.sharedMidPoints = arrayList;
    }

    public ArrayList<ArrayList<GeoPoint>> getPassedPoints() {
        return this.passedPoints;
    }

    public void addPassedPoint(GeoPoint geoPoint) {
        if (geoPoint != null && this.currentList != null) {
            this.currentList.add(geoPoint);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0073 A[SYNTHETIC, Splitter:B:40:0x0073] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x007f A[SYNTHETIC, Splitter:B:47:0x007f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveBitmap(android.graphics.Bitmap r4, java.lang.String r5) throws java.io.IOException {
        /*
            r3 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x0019
            boolean r1 = r0.delete()
            if (r1 != 0) goto L_0x0019
            return
        L_0x0019:
            boolean r1 = r0.createNewFile()
            if (r1 != 0) goto L_0x0020
            return
        L_0x0020:
            r1 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x006d }
            r2.<init>(r5)     // Catch:{ Exception -> 0x006d }
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            r1 = 100
            r4.compress(r5, r1, r2)     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            java.lang.String r4 = r0.getParent()     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            r0.<init>()     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            r0.append(r4)     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            java.lang.String r4 = "/.nomedia"
            r0.append(r4)     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            java.lang.String r4 = r0.toString()     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            r5.<init>(r4)     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            boolean r4 = r5.exists()     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            if (r4 != 0) goto L_0x005c
            boolean r4 = r5.createNewFile()     // Catch:{ Exception -> 0x0067, all -> 0x0065 }
            if (r4 != 0) goto L_0x005c
            r2.close()     // Catch:{ IOException -> 0x0057 }
            return
        L_0x0057:
            r4 = move-exception
            defpackage.kf.a(r4)
            return
        L_0x005c:
            r2.close()     // Catch:{ IOException -> 0x0060 }
            return
        L_0x0060:
            r4 = move-exception
            defpackage.kf.a(r4)
            return
        L_0x0065:
            r4 = move-exception
            goto L_0x007d
        L_0x0067:
            r4 = move-exception
            r1 = r2
            goto L_0x006e
        L_0x006a:
            r4 = move-exception
            r2 = r1
            goto L_0x007d
        L_0x006d:
            r4 = move-exception
        L_0x006e:
            defpackage.kf.a(r4)     // Catch:{ all -> 0x006a }
            if (r1 == 0) goto L_0x007c
            r1.close()     // Catch:{ IOException -> 0x0077 }
            goto L_0x007c
        L_0x0077:
            r4 = move-exception
            defpackage.kf.a(r4)
            return
        L_0x007c:
            return
        L_0x007d:
            if (r2 == 0) goto L_0x0087
            r2.close()     // Catch:{ IOException -> 0x0083 }
            goto L_0x0087
        L_0x0083:
            r5 = move-exception
            defpackage.kf.a(r5)
        L_0x0087:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult.saveBitmap(android.graphics.Bitmap, java.lang.String):void");
    }

    /* access modifiers changed from: protected */
    public String getNaviShareFilePath(String str) {
        String str2 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder sb = new StringBuilder("autonavi");
            sb.append(File.separator);
            sb.append("navishare");
            File file = new File(externalStorageDirectory, sb.toString());
            if (!file.exists() && !file.mkdir()) {
                return null;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/");
            sb2.append(str);
            str2 = sb2.toString();
        }
        return str2;
    }

    private String getRestTime(int i) {
        int i2 = (i + 30) / 60;
        if (i2 >= 60) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2 / 60);
            sb.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_hour_str));
            String sb2 = sb.toString();
            int i3 = i2 % 60;
            if (i3 <= 0) {
                return sb2;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(i3);
            sb3.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_minute_str));
            return sb3.toString();
        } else if (i2 == 0) {
            return AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_less_than_one_minute);
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(i2);
            sb4.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_minute));
            return sb4.toString();
        }
    }

    private int calculateInSampleSize(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int round = Math.round(((float) i3) / ((float) i2));
        int round2 = Math.round(((float) i4) / ((float) i));
        return round > round2 ? round : round2;
    }

    public void checkValidFromPOI() {
        String string = AMapAppGlobal.getApplication().getString(R.string.autonavi_data_result_select_point_from_map);
        if (this.fromPOI == null || this.fromPOI.getPoint().x == 0 || this.fromPOI.getPoint().y == 0) {
            if (this.passedPoints != null && this.passedPoints.size() > 0 && this.passedPoints.get(0).size() > 0) {
                this.fromPOI = POIFactory.createPOI(string, (GeoPoint) this.passedPoints.get(0).get(0));
            }
        } else if (TextUtils.isEmpty(this.fromPOI.getName())) {
            this.fromPOI.setName(string);
        }
    }

    public CalcRouteResult getCalcRouteResult() {
        return this.calcRouteResult;
    }

    public void setCalcRouteResult(CalcRouteResult calcRouteResult2) {
        this.calcRouteResult = calcRouteResult2;
    }

    public CalcRouteScene getCalcRouteScene() {
        return CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN;
    }

    public boolean isMultiRoute() {
        return this.mIsMultiRoute;
    }

    public void setIsMultiRoute(boolean z) {
        this.mIsMultiRoute = z;
    }

    public String getViaCityCodeList() {
        return this.viaCityCodeList;
    }

    public void setViaCityCodeList(String str) {
        this.viaCityCodeList = str;
    }

    public boolean isLegalPOI(POI poi) {
        return poi != null && poi.getPoint() != null && poi.getPoint().x > 0 && poi.getPoint().y > 0;
    }

    public void setRouteNaviId(String str) {
        this.routeNaviId = str;
    }

    public void setRouteNaviIdAllContainer(LinkedHashSet linkedHashSet) {
        this.routeNaviIdAllContainer = linkedHashSet;
    }

    public LinkedHashSet getRouteNaviIdAllContainer() {
        return this.routeNaviIdAllContainer;
    }
}
