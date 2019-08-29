package com.autonavi.sync;

public class GirfSyncJni {
    private long mShadow;

    public static native String getVersion();

    public static native void turnOffDebug();

    public static native void turnOnDebug();

    /* access modifiers changed from: protected */
    public native int beginTransactionForChangeData();

    /* access modifiers changed from: protected */
    public native int clearJsonData(String str, String str2, int i);

    /* access modifiers changed from: protected */
    public native int confirmMerge(String str, boolean z);

    /* access modifiers changed from: protected */
    public native int confirmMerge(boolean z);

    /* access modifiers changed from: protected */
    public native int doSync();

    /* access modifiers changed from: protected */
    public native int endTransactionForChangeData();

    /* access modifiers changed from: protected */
    public native String getCars(int i);

    /* access modifiers changed from: protected */
    public native String getCityCodes();

    /* access modifiers changed from: protected */
    public native String getCityNames();

    /* access modifiers changed from: protected */
    public native String getClassifications();

    /* access modifiers changed from: protected */
    public native String getCompanyList();

    /* access modifiers changed from: protected */
    public native String getCompanyListSorted();

    /* access modifiers changed from: protected */
    public native String getCustomLabels();

    /* access modifiers changed from: protected */
    public native int getDataItemCountByType(String str);

    /* access modifiers changed from: protected */
    public native String getHomeList();

    /* access modifiers changed from: protected */
    public native String getHomeListSorted();

    /* access modifiers changed from: protected */
    public native String getIdsByType(String str);

    /* access modifiers changed from: protected */
    public native String getJsonData(String str, String str2);

    /* access modifiers changed from: protected */
    public native String getJsonDataByPoiid(String str);

    /* access modifiers changed from: protected */
    public native String getJsonDatas(String str);

    /* access modifiers changed from: protected */
    public native String getJsondatasByRegEx(String str, String str2);

    /* access modifiers changed from: protected */
    public native String getNaviSearchHistory(String str, int i);

    /* access modifiers changed from: protected */
    public native String getPath(String str, String str2, int i);

    /* access modifiers changed from: protected */
    public native int[] getPoiIdsByCityCode(String str);

    /* access modifiers changed from: protected */
    public native int[] getPoiIdsByCityName(String str);

    /* access modifiers changed from: protected */
    public native int[] getPoiIdsByClassification(String str);

    /* access modifiers changed from: protected */
    public native int[] getPoiIdsByLabel(String str);

    /* access modifiers changed from: protected */
    public native int[] getPoiIdsInScreen();

    /* access modifiers changed from: protected */
    public native String getSearchHistory(int i);

    /* access modifiers changed from: protected */
    public native int[] getSnapshotIdsByType(long j, String str, int i);

    /* access modifiers changed from: protected */
    public native String getSnaptshotItemById(long j, String str, int i);

    /* access modifiers changed from: protected */
    public native int getTotalDistance(String str);

    /* access modifiers changed from: protected */
    public native int getTotalDuration(String str);

    /* access modifiers changed from: protected */
    public native boolean isSyncing();

    /* access modifiers changed from: protected */
    public native int setGuestLogin();

    /* access modifiers changed from: protected */
    public native int setGuestLoginWithoutSync();

    /* access modifiers changed from: protected */
    public native int setJsonData(String str, String str2, String str3, int i);

    /* access modifiers changed from: protected */
    public native int setJsonDataForUser(String str, String str2, String str3, String str4);

    /* access modifiers changed from: protected */
    public native int setUserLogin(String str);

    /* access modifiers changed from: protected */
    public native int setUserLoginWithoutSync(String str);

    /* access modifiers changed from: protected */
    public int[] getSnapshotIdsByType(String str, int i) {
        return getSnapshotIdsByType(this.mShadow, str, i);
    }

    /* access modifiers changed from: protected */
    public String getSnaptshotItemById(String str, int i) {
        return getSnaptshotItemById(this.mShadow, str, i);
    }
}
