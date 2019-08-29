package com.amap.bundle.drivecommon.model;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.route.utils.CarRouteParser;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.route.RouteService;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class RouteCarResultData implements ICarRouteResult {
    private static final int LONG_DISTANCE_STANDARD = 100000;
    private static final ReentrantLock mLock = new ReentrantLock();
    NavigationResult a;
    private CalcRouteResult calcRouteResult;
    private int errorCode = 0;
    private int gotoNaviDlgIndex = 0;
    private boolean isServiceAreaMode = false;
    private boolean isViaCityMode = false;
    private boolean isViaRoadMode = false;
    private byte[] mBackUpData;
    private transient CalcRouteScene mCalcRouteScene = CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN;
    private String mCarPlate;
    private int mFocusRouteIndex = 0;
    private int mFocusStationIndex = 0;
    private POI mFromPoi = null;
    private transient Intent mIntent;
    private boolean mIsCarSceneResult;
    private boolean mIsMultiRoute = false;
    private String mKey;
    private POI mMainPoi;
    private String mMethod_;
    private ArrayList<POI> mMidPois;
    private int mRecommendFlag = -1;
    private GeoPoint mShareEndGp;
    private ArrayList<GeoPoint> mShareMidGps;
    private ArrayList<POI> mShareMidPois;
    private GeoPoint mShareStartGp = null;
    private int mStationsCount = 0;
    private POI mToPoi = null;
    private boolean m_bNative = false;
    private POI share_from_poi = null;
    private POI share_to_poi = null;
    private boolean suggestOnFoot = false;

    public void restoreData() {
    }

    public void saveData() {
    }

    public RouteCarResultData(CalcRouteScene calcRouteScene) {
        this.mCalcRouteScene = calcRouteScene;
    }

    public Class<RouteCarResultData> getClassType() {
        return RouteCarResultData.class;
    }

    public boolean hasData() {
        if (this.a == null || this.a.mPaths == null || this.a.mPaths.length == 0 || this.mFromPoi == null || this.mToPoi == null || this.a.mPaths[0] == null || this.a.mPaths[0].mSections == null || this.a.mPaths[0].mSections.length == 0) {
            return false;
        }
        return true;
    }

    public void setNaviResultData(POI poi, POI poi2, NavigationResult navigationResult, String str) {
        this.mFromPoi = poi;
        this.mToPoi = poi2;
        this.a = navigationResult;
        this.mMethod_ = str;
    }

    public NavigationResult getNaviResultData() {
        return this.a;
    }

    public void reset() {
        this.a = null;
        this.mFromPoi = null;
        this.mToPoi = null;
        this.mMidPois = null;
        this.mMethod_ = "1";
        this.mBackUpData = null;
        this.mFocusStationIndex = 0;
        this.mFocusRouteIndex = 0;
        this.mStationsCount = 0;
    }

    public synchronized int parseData(byte[] bArr, int i, int i2) {
        return parseDataEx21Version(bArr, i, i2);
    }

    public boolean parseTBTData(byte[] bArr) {
        parsePathDataEx(bArr);
        this.m_bNative = false;
        return true;
    }

    public String getFromCityCode() {
        if (this.mFromPoi != null) {
            return this.mFromPoi.getCityCode();
        }
        return null;
    }

    public String getToCityCode() {
        if (this.mToPoi != null) {
            return this.mToPoi.getCityCode();
        }
        return null;
    }

    public int getFocusRouteIndex() {
        return this.mFocusRouteIndex;
    }

    public void setFocusRouteIndex(int i) {
        this.mFocusRouteIndex = i;
    }

    public int getFocusStationIndex() {
        return this.mFocusStationIndex;
    }

    public void setFocusStationIndex(int i) {
        this.mFocusStationIndex = i;
    }

    public int getStationsCount() {
        return this.mStationsCount;
    }

    public void setStationCount(int i) {
        this.mStationsCount = i;
    }

    public void setFromPOI(POI poi) {
        this.mFromPoi = poi;
    }

    public POI getFromPOI() {
        return this.mFromPoi;
    }

    public void setToPOI(POI poi) {
        this.mToPoi = poi;
    }

    public POI getToPOI() {
        return this.mToPoi;
    }

    public POI getShareFromPOI() {
        if (this.share_from_poi == null) {
            this.share_from_poi = this.mFromPoi.clone();
        }
        return this.share_from_poi;
    }

    public POI getShareToPOI() {
        if (this.share_to_poi == null) {
            this.share_to_poi = this.mToPoi.clone();
        }
        return this.share_to_poi;
    }

    public void setShareMidPos(ArrayList<GeoPoint> arrayList) {
        this.mShareMidGps = arrayList;
    }

    public void setShareStartPos(GeoPoint geoPoint) {
        this.mShareStartGp = geoPoint;
    }

    public void setShareEndPos(GeoPoint geoPoint) {
        this.mShareEndGp = geoPoint;
    }

    public ArrayList<GeoPoint> getShareMidPoses() {
        if (this.mShareMidGps == null) {
            ArrayList<POI> shareMidPOIs = getShareMidPOIs();
            if (shareMidPOIs != null && shareMidPOIs.size() > 0) {
                this.mShareMidGps = new ArrayList<>();
                Iterator<POI> it = shareMidPOIs.iterator();
                while (it.hasNext()) {
                    this.mShareMidGps.add(it.next().getPoint());
                }
            }
        }
        return this.mShareMidGps;
    }

    public GeoPoint getShareStartPos() {
        POI shareFromPOI = getShareFromPOI();
        if (this.mShareStartGp == null && shareFromPOI != null) {
            this.mShareStartGp = shareFromPOI.getPoint();
        }
        return this.mShareStartGp;
    }

    public GeoPoint getShareEndPos() {
        POI shareToPOI = getShareToPOI();
        if (this.mShareEndGp == null && shareToPOI != null) {
            this.mShareEndGp = shareToPOI.getPoint();
        }
        return this.mShareEndGp;
    }

    public void setMethod(String str) {
        this.mMethod_ = str;
    }

    public boolean needSaveHistory() {
        return !this.mIsCarSceneResult && this.mMainPoi == null && !(this.mToPoi != null && (this.mToPoi.getPoiExtra().get("sub_poi_name") != null || this.mToPoi.getPoiExtra().get("main_poi") != null));
    }

    public String getMethod() {
        if (TextUtils.isEmpty(this.mMethod_)) {
            return "";
        }
        return this.mMethod_;
    }

    public boolean hasMidPos() {
        return this.mMidPois != null && this.mMidPois.size() > 0;
    }

    @Nullable
    public NavigationPath getFocusNavigationPath() {
        return getNavigationPath(this.mFocusRouteIndex);
    }

    @Nullable
    public NavigationPath getNavigationPath(int i) {
        if (this.a == null) {
            return null;
        }
        NavigationPath[] navigationPathArr = this.a.mPaths;
        if (i < 0 || navigationPathArr == null || i >= navigationPathArr.length || navigationPathArr.length == 0) {
            return null;
        }
        return navigationPathArr[i];
    }

    public byte[] getBackUpTbtData() {
        return this.mBackUpData;
    }

    private int parseDataEx21Version(byte[] bArr, int i, int i2) {
        int[] parseTaxiCost = parseTaxiCost(bArr, i2);
        if (parsePathDataEx(bArr) && parseTaxiCost != null && parseTaxiCost.length == this.a.mPathNum) {
            for (int i3 = 0; i3 < this.a.mPathNum; i3++) {
                this.a.mPaths[i3].mTaxiFee = parseTaxiCost[i3];
            }
        }
        this.m_bNative = false;
        return this.errorCode;
    }

    private int[] parseTaxiCost(byte[] bArr, int i) {
        int length = bArr.length - i;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, i, bArr2, 0, length);
        if ((bArr2[0] & 255) + ((bArr2[1] & 255) << 8) == 100) {
            byte[] bArr3 = new byte[8];
            System.arraycopy(bArr2, 2, bArr3, 0, 8);
            try {
                String str = new String(bArr2, 10, (int) ahg.b(bArr3), "UTF-16LE");
                if (str.length() > 0) {
                    String[] split = str.split(",");
                    int[] iArr = new int[split.length];
                    for (int i2 = 0; i2 < split.length; i2++) {
                        iArr[i2] = Integer.parseInt(split[i2]);
                    }
                    return iArr;
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
                return null;
            }
        }
        return null;
    }

    private synchronized boolean parsePathDataEx(byte[] bArr) {
        try {
            mLock.lock();
            this.errorCode = 0;
            CalcRouteResult decodeRouteData = RouteService.decodeRouteData(bArr);
            if (decodeRouteData == null) {
                mLock.unlock();
                return false;
            }
            this.errorCode = decodeRouteData.getRouteReqState();
            if (this.errorCode != 1) {
                mLock.unlock();
                return false;
            } else if (decodeRouteData.getPathCount() <= 0) {
                mLock.unlock();
                return false;
            } else {
                backUpPushTbtData(bArr);
                CarRouteParser.parseCalcRouteResult(this, decodeRouteData);
                mLock.unlock();
                return true;
            }
        } catch (Throwable th) {
            mLock.unlock();
            throw th;
        }
    }

    private void backUpPushTbtData(byte[] bArr) {
        if (bArr != null) {
            this.mBackUpData = bArr;
        } else {
            this.mBackUpData = null;
        }
    }

    public void setGotoNaviDlgIndex(int i) {
        this.gotoNaviDlgIndex = i;
    }

    public int getGotoNaviDlgIndex() {
        return this.gotoNaviDlgIndex;
    }

    public String getKey() {
        return this.mKey;
    }

    public void setKey(String str) {
        this.mKey = str;
    }

    public boolean isM_bNative() {
        return this.m_bNative;
    }

    public void setM_bNative(boolean z) {
        this.m_bNative = z;
    }

    public String genMethodStr(int i) {
        NavigationPath navigationPath = getNavigationPath(i);
        return navigationPath != null ? navigationPath.mTagName : "";
    }

    public boolean isSuggestOnfoot() {
        return this.suggestOnFoot;
    }

    public void setSuggestOnfoot(boolean z) {
        this.suggestOnFoot = z;
    }

    public static String toDBC(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == 12288) {
                charArray[i] = ' ';
            } else if (charArray[i] > 65280 && charArray[i] < 65375) {
                charArray[i] = (char) (charArray[i] - 65248);
            }
        }
        return new String(charArray);
    }

    public void setArgIntent(Intent intent) {
        this.mIntent = intent;
    }

    public Intent getArgIntent() {
        return this.mIntent;
    }

    public void setRecommendFlag(int i) {
        this.mRecommendFlag = i;
    }

    public int getRecommendFlag() {
        return this.mRecommendFlag;
    }

    public ArrayList<POI> getMidPOIs() {
        return this.mMidPois;
    }

    public void setMidPOIs(ArrayList<POI> arrayList) {
        this.mMidPois = arrayList;
    }

    public ArrayList<POI> getShareMidPOIs() {
        if (this.mShareMidPois == null) {
            this.mShareMidPois = new ArrayList<>();
            if (this.mMidPois != null && this.mMidPois.size() > 0) {
                Iterator<POI> it = this.mMidPois.iterator();
                while (it.hasNext()) {
                    POI next = it.next();
                    if (next != null) {
                        this.mShareMidPois.add(next.clone());
                    }
                }
            }
        }
        return this.mShareMidPois;
    }

    public void setCarPlate(String str) {
        this.mCarPlate = str;
    }

    public String getCarPlate() {
        return this.mCarPlate;
    }

    public boolean isSceneResult() {
        return this.mIsCarSceneResult;
    }

    public void setSceneResult(boolean z) {
        this.mIsCarSceneResult = z;
    }

    public POI getMainPoi() {
        return this.mMainPoi;
    }

    public void setMainPoi(POI poi) {
        this.mMainPoi = poi;
    }

    public boolean isLongDisResult() {
        return (this.mFromPoi == null || this.mToPoi == null || cfe.a(this.mFromPoi.getPoint(), this.mToPoi.getPoint()) <= 100000.0f) ? false : true;
    }

    public boolean isViaRoadMode() {
        return this.isViaRoadMode;
    }

    public void setViaRoadMode(boolean z) {
        this.isViaRoadMode = z;
        if (z) {
            this.isViaCityMode = false;
            this.isServiceAreaMode = false;
        }
    }

    public boolean isViaCityMode() {
        return this.isViaCityMode;
    }

    public boolean isServiceAreaMode() {
        return this.isServiceAreaMode;
    }

    public void setServiceAreaMode(boolean z) {
        this.isServiceAreaMode = z;
        if (z) {
            this.isViaRoadMode = false;
            this.isViaCityMode = false;
        }
    }

    public void setViaCityMode(boolean z) {
        this.isViaCityMode = z;
        if (z) {
            this.isViaRoadMode = false;
            this.isServiceAreaMode = false;
        }
    }

    public CalcRouteResult getCalcRouteResult() {
        return this.calcRouteResult;
    }

    public void setCalcRouteResult(CalcRouteResult calcRouteResult2) {
        this.calcRouteResult = calcRouteResult2;
    }

    public CalcRouteScene getCalcRouteScene() {
        return this.mCalcRouteScene;
    }

    public boolean isMultiRoute() {
        return this.mIsMultiRoute;
    }

    public void setIsMultiRoute(boolean z) {
        this.mIsMultiRoute = z;
    }
}
