package com.autonavi.minimap.route.bus.inter.impl;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.BusPathSection.IrregularTime;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.poi.param.BusBaseRequest;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.model.BusSubwayInfo;
import com.autonavi.minimap.route.bus.model.BusSubwayStation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BusLineSearchResultImpl implements IBusLineSearchResult {
    public static final int SHOW_NUM_ONCE = 10;
    private static final long serialVersionUID = 4291439016749324225L;
    private String lineId;
    private long mAdCode;
    private ArrayList<Bus> mBusList = null;
    private String mCityCode;
    private int mCurPage = 1;
    private int mFocusBuslineIndex = 0;
    private int mFocusChildStaionIndex = -1;
    private int mFocusPoiIndex = 0;
    private int mFocusStationIndex = -1;
    private boolean mIsReset;
    private String mKey;
    private int mResultType = -1;
    private String mSearchName;
    private ArrayList<POI> mStationList = null;
    private int mTotalBusCount = 0;
    private int mTotalPoiPage = 1;
    private int mTotalPoiSize = 0;
    private BusBaseRequest mWrapper;

    public String getCurrentCity() {
        return null;
    }

    public String getMsgId() {
        return null;
    }

    public Bundle parse(Uri uri) {
        return null;
    }

    public void restoreData() {
    }

    public void saveData() {
    }

    public void setSearchPage(int i) {
    }

    public Class<BusLineSearchResultImpl> getClassType() {
        return BusLineSearchResultImpl.class;
    }

    public void reset() {
        this.mIsReset = true;
        this.mSearchName = null;
        this.mCityCode = null;
        this.mTotalPoiPage = 1;
        this.mTotalPoiSize = 0;
        this.mCurPage = 1;
        if (this.mBusList != null) {
            this.mBusList.clear();
        }
        if (this.mStationList != null) {
            this.mStationList.clear();
        }
        this.mResultType = -1;
        this.mFocusStationIndex = 0;
        this.mFocusChildStaionIndex = -1;
        this.mFocusBuslineIndex = 0;
        this.mFocusPoiIndex = 0;
    }

    public boolean hasData() {
        return hasBuslineData() || hasStationData();
    }

    public boolean hasStationData() {
        return this.mStationList != null && this.mStationList.size() > 0;
    }

    public boolean hasBuslineData() {
        return this.mBusList != null && this.mBusList.size() > 0;
    }

    public void setFocusStationIndex(int i) {
        this.mFocusStationIndex = i;
    }

    public void setFocusBusLineIndex(int i) {
        this.mFocusBuslineIndex = i;
    }

    public Bus getBusLine(int i) {
        if (this.mBusList == null || this.mBusList.size() == 0) {
            return null;
        }
        int size = this.mBusList.size();
        if (i < 0 || i > size - 1) {
            return null;
        }
        return this.mBusList.get(i);
    }

    public Bus[] getBusLineArray(int i) {
        if (this.mBusList == null || this.mBusList.size() == 0 || i <= 0 || i > this.mCurPage) {
            return null;
        }
        int i2 = (i - 1) * 10;
        if (i2 >= this.mBusList.size()) {
            return null;
        }
        int i3 = (i2 + 10) - 1;
        int min = Math.min(this.mTotalPoiSize - 1, this.mBusList.size() - 1);
        if (i3 > min) {
            i3 = min;
        }
        int i4 = (i3 - i2) + 1;
        Bus[] busArr = new Bus[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            busArr[i5] = this.mBusList.get(i2 + i5);
        }
        return busArr;
    }

    public ArrayList<Bus> getBuslines() {
        return this.mBusList;
    }

    public int getFocusBusLineIndex() {
        return this.mFocusBuslineIndex;
    }

    public ArrayList<POI> getPoiList(int i) {
        if (this.mStationList == null || this.mStationList.size() == 0 || i <= 0 || i > this.mTotalPoiPage) {
            return null;
        }
        int i2 = (i - 1) * 10;
        int i3 = (i2 + 10) - 1;
        if (i3 > this.mStationList.size() - 1) {
            i3 = this.mStationList.size() - 1;
        }
        int i4 = (i3 - i2) + 1;
        ArrayList<POI> arrayList = new ArrayList<>();
        for (int i5 = 0; i5 < i4; i5++) {
            arrayList.add(this.mStationList.get(i2 + i5));
        }
        return arrayList;
    }

    public int getFocusedPoiIndex() {
        return this.mFocusPoiIndex;
    }

    public void setFocusedPoiIndex(int i) {
        this.mFocusPoiIndex = i;
    }

    public POI getFocusedPoi() {
        if (this.mStationList != null && this.mFocusPoiIndex >= 0 && this.mFocusPoiIndex < this.mStationList.size()) {
            return this.mStationList.get(this.mFocusPoiIndex);
        }
        return null;
    }

    public String getSearchKeyword() {
        return this.mSearchName;
    }

    public void setSearchKeyword(String str) {
        this.mSearchName = str;
    }

    public int getCurPoiPage() {
        return this.mCurPage;
    }

    public void setCurPoiPage(int i) {
        this.mCurPage = i;
    }

    public void setTotalPoiSize(int i) {
        this.mTotalPoiSize = i;
    }

    public int getTotalPoiSize() {
        return this.mTotalPoiSize;
    }

    public int getTotalPoiPage() {
        return this.mTotalPoiPage;
    }

    public boolean isReset() {
        return this.mIsReset;
    }

    public void setReset(boolean z) {
        this.mIsReset = z;
    }

    public void resetAll() {
        reset();
    }

    public String getCityCode() {
        return this.mCityCode;
    }

    public void setCityCode(String str) {
        this.mCityCode = str;
    }

    public void setAdCode(long j) {
        this.mAdCode = j;
    }

    public long getAdCode() {
        return this.mAdCode;
    }

    public boolean parse(JSONObject jSONObject) {
        try {
            int i = jSONObject.getInt("total");
            this.mTotalBusCount = jSONObject.getInt("busline_count");
            this.mResultType = 0;
            this.mTotalPoiSize = 0;
            if (jSONObject.has("busline_list") && this.mTotalBusCount > 0) {
                this.mResultType = 3;
                this.mTotalPoiSize = this.mTotalBusCount;
                parseBusline(jSONObject);
            }
            if (jSONObject.has("bus_list")) {
                this.mResultType = 1;
                this.mTotalPoiSize = i;
                parseStation(jSONObject);
            }
            if (this.mTotalPoiSize == 0) {
                this.mTotalPoiSize = i + this.mTotalBusCount;
            }
            this.mTotalPoiPage = ((this.mTotalPoiSize + 10) - 1) / 10;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addBusLineArray(Bus[] busArr, boolean z) {
        if (this.mBusList == null) {
            this.mBusList = new ArrayList<>();
        }
        if (z) {
            this.mBusList.clear();
        }
        if (busArr != null) {
            for (Bus add : busArr) {
                this.mBusList.add(add);
            }
        }
    }

    public void addBusLineArray(List<Bus> list, boolean z) {
        if (this.mBusList == null) {
            this.mBusList = new ArrayList<>();
        }
        if (z) {
            this.mBusList.clear();
        }
        this.mBusList.addAll(list);
    }

    public void addBusLine(Bus bus, boolean z) {
        if (this.mBusList == null) {
            this.mBusList = new ArrayList<>();
        }
        if (z) {
            this.mBusList.clear();
        }
        this.mBusList.add(bus);
    }

    public void addStationArray(ArrayList<POI> arrayList, boolean z) {
        if (this.mStationList == null) {
            this.mStationList = new ArrayList<>();
        }
        if (z) {
            this.mStationList.clear();
        }
        this.mStationList.addAll(arrayList);
    }

    public int getResultType() {
        return this.mResultType;
    }

    public int getFocusStationIndex() {
        return this.mFocusStationIndex;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:52|53) */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r1[r3].startTime = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        r1[r3].endTime = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r1[r3].stationStartTime = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        r1[r3].stationEndTime = -1;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x00a0 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:36:0x00bc */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x00da */
    /* JADX WARNING: Missing exception handler attribute for start block: B:52:0x00f8 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseBusline(org.json.JSONObject r15) throws org.json.JSONException {
        /*
            r14 = this;
            java.lang.String r0 = "busline_list"
            org.json.JSONArray r15 = r15.getJSONArray(r0)     // Catch:{ Exception -> 0x03eb }
            if (r15 != 0) goto L_0x0009
            return
        L_0x0009:
            int r0 = r15.length()     // Catch:{ Exception -> 0x03eb }
            com.autonavi.minimap.route.bus.model.Bus[] r1 = new com.autonavi.minimap.route.bus.model.Bus[r0]     // Catch:{ Exception -> 0x03eb }
            r2 = 0
            r3 = 0
        L_0x0011:
            if (r3 >= r0) goto L_0x03e7
            org.json.JSONObject r4 = r15.getJSONObject(r3)     // Catch:{ Exception -> 0x03eb }
            com.autonavi.minimap.route.bus.model.Bus r5 = new com.autonavi.minimap.route.bus.model.Bus     // Catch:{ Exception -> 0x03eb }
            r5.<init>()     // Catch:{ Exception -> 0x03eb }
            r1[r3] = r5     // Catch:{ Exception -> 0x03eb }
            java.lang.String r5 = "id"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x0030
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r6 = "id"
            java.lang.String r6 = r4.getString(r6)     // Catch:{ Exception -> 0x03eb }
            r5.id = r6     // Catch:{ Exception -> 0x03eb }
        L_0x0030:
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r6 = "type"
            int r6 = r4.optInt(r6)     // Catch:{ Exception -> 0x03eb }
            r5.type = r6     // Catch:{ Exception -> 0x03eb }
            java.lang.String r5 = "name"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x004d
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r6 = "name"
            java.lang.String r6 = r4.getString(r6)     // Catch:{ Exception -> 0x03eb }
            r5.name = r6     // Catch:{ Exception -> 0x03eb }
        L_0x004d:
            java.lang.String r5 = "front_name"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x005f
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r6 = "front_name"
            java.lang.String r6 = r4.getString(r6)     // Catch:{ Exception -> 0x03eb }
            r5.startName = r6     // Catch:{ Exception -> 0x03eb }
        L_0x005f:
            java.lang.String r5 = "terminal_name"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x0073
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r6 = "terminal_name"
            java.lang.String r6 = r4.getString(r6)     // Catch:{ Exception -> 0x03eb }
            r5.endName = r6     // Catch:{ Exception -> 0x03eb }
        L_0x0073:
            java.lang.String r5 = "color"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x0085
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r6 = "color"
            java.lang.String r6 = r4.getString(r6)     // Catch:{ Exception -> 0x03eb }
            r5.color = r6     // Catch:{ Exception -> 0x03eb }
        L_0x0085:
            r5 = -1
            java.lang.String r6 = "start_time"
            boolean r6 = r4.has(r6)     // Catch:{ JSONException -> 0x00a0 }
            if (r6 == 0) goto L_0x009b
            r6 = r1[r3]     // Catch:{ JSONException -> 0x00a0 }
            java.lang.String r7 = "start_time"
            int r7 = r4.getInt(r7)     // Catch:{ JSONException -> 0x00a0 }
            r6.startTime = r7     // Catch:{ JSONException -> 0x00a0 }
            goto L_0x00a4
        L_0x009b:
            r6 = r1[r3]     // Catch:{ JSONException -> 0x00a0 }
            r6.startTime = r5     // Catch:{ JSONException -> 0x00a0 }
            goto L_0x00a4
        L_0x00a0:
            r6 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            r6.startTime = r5     // Catch:{ Exception -> 0x03eb }
        L_0x00a4:
            java.lang.String r6 = "end_time"
            boolean r6 = r4.has(r6)     // Catch:{ JSONException -> 0x00bc }
            if (r6 == 0) goto L_0x00b7
            r6 = r1[r3]     // Catch:{ JSONException -> 0x00bc }
            java.lang.String r7 = "end_time"
            int r7 = r4.getInt(r7)     // Catch:{ JSONException -> 0x00bc }
            r6.endTime = r7     // Catch:{ JSONException -> 0x00bc }
            goto L_0x00c0
        L_0x00b7:
            r6 = r1[r3]     // Catch:{ JSONException -> 0x00bc }
            r6.endTime = r5     // Catch:{ JSONException -> 0x00bc }
            goto L_0x00c0
        L_0x00bc:
            r6 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            r6.endTime = r5     // Catch:{ Exception -> 0x03eb }
        L_0x00c0:
            java.lang.String r6 = "stationStartTime"
            boolean r6 = r4.has(r6)     // Catch:{ JSONException -> 0x00da }
            if (r6 == 0) goto L_0x00d5
            r6 = r1[r3]     // Catch:{ JSONException -> 0x00da }
            java.lang.String r7 = "stationStartTime"
            int r7 = r4.getInt(r7)     // Catch:{ JSONException -> 0x00da }
            r6.stationStartTime = r7     // Catch:{ JSONException -> 0x00da }
            goto L_0x00de
        L_0x00d5:
            r6 = r1[r3]     // Catch:{ JSONException -> 0x00da }
            r6.stationStartTime = r5     // Catch:{ JSONException -> 0x00da }
            goto L_0x00de
        L_0x00da:
            r6 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            r6.stationStartTime = r5     // Catch:{ Exception -> 0x03eb }
        L_0x00de:
            java.lang.String r6 = "stationEndTime"
            boolean r6 = r4.has(r6)     // Catch:{ JSONException -> 0x00f8 }
            if (r6 == 0) goto L_0x00f3
            r6 = r1[r3]     // Catch:{ JSONException -> 0x00f8 }
            java.lang.String r7 = "stationEndTime"
            int r7 = r4.getInt(r7)     // Catch:{ JSONException -> 0x00f8 }
            r6.stationEndTime = r7     // Catch:{ JSONException -> 0x00f8 }
            goto L_0x00fc
        L_0x00f3:
            r6 = r1[r3]     // Catch:{ JSONException -> 0x00f8 }
            r6.stationEndTime = r5     // Catch:{ JSONException -> 0x00f8 }
            goto L_0x00fc
        L_0x00f8:
            r6 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            r6.stationEndTime = r5     // Catch:{ Exception -> 0x03eb }
        L_0x00fc:
            java.lang.String r5 = "key_name"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x010e
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r6 = "key_name"
            java.lang.String r6 = r4.getString(r6)     // Catch:{ Exception -> 0x03eb }
            r5.key_name = r6     // Catch:{ Exception -> 0x03eb }
        L_0x010e:
            java.lang.String r5 = "areacode"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x011e
            java.lang.String r5 = "areacode"
            java.lang.String r5 = r4.getString(r5)     // Catch:{ Exception -> 0x03eb }
            r14.mCityCode = r5     // Catch:{ Exception -> 0x03eb }
        L_0x011e:
            java.lang.String r5 = "status"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            r6 = 1
            if (r5 == 0) goto L_0x0133
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r7 = "status"
            int r7 = r4.optInt(r7, r6)     // Catch:{ Exception -> 0x03eb }
            r5.status = r7     // Catch:{ Exception -> 0x03eb }
        L_0x0133:
            java.lang.String r5 = "description"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x0145
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r7 = "description"
            java.lang.String r7 = r4.optString(r7)     // Catch:{ Exception -> 0x03eb }
            r5.description = r7     // Catch:{ Exception -> 0x03eb }
        L_0x0145:
            java.lang.String r5 = "emergency"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x016d
            java.lang.String r5 = "emergency"
            org.json.JSONObject r5 = r4.getJSONObject(r5)     // Catch:{ Exception -> 0x03eb }
            com.autonavi.minimap.route.bus.model.Bus$a r7 = new com.autonavi.minimap.route.bus.model.Bus$a     // Catch:{ Exception -> 0x03eb }
            r7.<init>()     // Catch:{ Exception -> 0x03eb }
            java.lang.String r8 = "state"
            int r8 = r5.optInt(r8)     // Catch:{ Exception -> 0x03eb }
            r7.a = r8     // Catch:{ Exception -> 0x03eb }
            java.lang.String r8 = "description"
            java.lang.String r5 = r5.optString(r8)     // Catch:{ Exception -> 0x03eb }
            r7.b = r5     // Catch:{ Exception -> 0x03eb }
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            r5.emergency = r7     // Catch:{ Exception -> 0x03eb }
        L_0x016d:
            java.lang.String r5 = "interval"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x0183
            java.lang.String r5 = "interval"
            java.lang.String r5 = r4.optString(r5)     // Catch:{ Exception -> 0x03eb }
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r5 = getInterval4String(r5)     // Catch:{ Exception -> 0x03eb }
            r7.interval = r5     // Catch:{ Exception -> 0x03eb }
        L_0x0183:
            java.lang.String r5 = "direc"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x0195
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r7 = "direc"
            java.lang.String r7 = r4.optString(r7)     // Catch:{ Exception -> 0x03eb }
            r5.returnId = r7     // Catch:{ Exception -> 0x03eb }
        L_0x0195:
            java.lang.String r5 = "basic_price"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x01a7
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r7 = "basic_price"
            java.lang.String r7 = r4.optString(r7)     // Catch:{ Exception -> 0x03eb }
            r5.basic_price = r7     // Catch:{ Exception -> 0x03eb }
        L_0x01a7:
            java.lang.String r5 = "total_price"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x01bb
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r7 = "total_price"
            java.lang.String r7 = r4.optString(r7)     // Catch:{ Exception -> 0x03eb }
            r5.total_price = r7     // Catch:{ Exception -> 0x03eb }
        L_0x01bb:
            java.lang.String r5 = "basic_price_air"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x01cd
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r7 = "basic_price_air"
            java.lang.String r7 = r4.optString(r7)     // Catch:{ Exception -> 0x03eb }
            r5.basic_price_air = r7     // Catch:{ Exception -> 0x03eb }
        L_0x01cd:
            java.lang.String r5 = "total_price_air"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x01e1
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r7 = "total_price_air"
            java.lang.String r7 = r4.optString(r7)     // Catch:{ Exception -> 0x03eb }
            r5.total_price_air = r7     // Catch:{ Exception -> 0x03eb }
        L_0x01e1:
            java.lang.String r5 = "length"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x0215
            java.lang.String r5 = "length"
            java.lang.String r5 = r4.getString(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x020b
            java.lang.String r7 = r5.trim()     // Catch:{ Exception -> 0x03eb }
            java.lang.String r8 = ""
            boolean r7 = r7.equals(r8)     // Catch:{ Exception -> 0x03eb }
            if (r7 != 0) goto L_0x020b
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            double r8 = java.lang.Double.parseDouble(r5)     // Catch:{ Exception -> 0x03eb }
            int r8 = (int) r8     // Catch:{ Exception -> 0x03eb }
            r7.length = r8     // Catch:{ Exception -> 0x03eb }
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            r7.otherLen = r5     // Catch:{ Exception -> 0x03eb }
            goto L_0x0215
        L_0x020b:
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            r5.length = r2     // Catch:{ Exception -> 0x03eb }
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r7 = "0.0"
            r5.otherLen = r7     // Catch:{ Exception -> 0x03eb }
        L_0x0215:
            java.lang.String r5 = "is_realtime"
            int r5 = r4.optInt(r5)     // Catch:{ Exception -> 0x03eb }
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            if (r5 != r6) goto L_0x0221
            r5 = 1
            goto L_0x0222
        L_0x0221:
            r5 = 0
        L_0x0222:
            r7.isRealTime = r5     // Catch:{ Exception -> 0x03eb }
            java.lang.String r5 = "irregular_time"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x023a
            r5 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String r7 = "irregular_time"
            java.lang.String r7 = defpackage.axr.e(r4, r7)     // Catch:{ Exception -> 0x03eb }
            com.autonavi.bundle.routecommon.entity.BusPathSection$IrregularTime r7 = getIrregularTime(r7)     // Catch:{ Exception -> 0x03eb }
            r5.irregulartime = r7     // Catch:{ Exception -> 0x03eb }
        L_0x023a:
            java.lang.String r5 = "xs"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x02c2
            java.lang.String r5 = "ys"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x02c2
            java.lang.String r5 = "xs"
            java.lang.String r5 = r4.getString(r5)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r7 = "ys"
            java.lang.String r7 = r4.getString(r7)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x02c2
            java.lang.String r8 = r5.trim()     // Catch:{ Exception -> 0x03eb }
            java.lang.String r9 = ""
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x03eb }
            if (r8 != 0) goto L_0x02c2
            if (r7 == 0) goto L_0x02c2
            java.lang.String r8 = r7.trim()     // Catch:{ Exception -> 0x03eb }
            java.lang.String r9 = ""
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x03eb }
            if (r8 != 0) goto L_0x02c2
            java.lang.String r8 = ","
            java.lang.String[] r5 = r5.split(r8)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r8 = ","
            java.lang.String[] r7 = r7.split(r8)     // Catch:{ Exception -> 0x03eb }
            int r8 = r5.length     // Catch:{ Exception -> 0x03eb }
            r9 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int r10 = r5.length     // Catch:{ Exception -> 0x03eb }
            int[] r10 = new int[r10]     // Catch:{ Exception -> 0x03eb }
            r9.coordX = r10     // Catch:{ Exception -> 0x03eb }
            r9 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int r10 = r5.length     // Catch:{ Exception -> 0x03eb }
            int[] r10 = new int[r10]     // Catch:{ Exception -> 0x03eb }
            r9.coordY = r10     // Catch:{ Exception -> 0x03eb }
            r9 = 0
        L_0x0292:
            if (r9 >= r8) goto L_0x02c2
            int r10 = r7.length     // Catch:{ Exception -> 0x03eb }
            if (r9 >= r10) goto L_0x02bf
            r10 = r5[r9]     // Catch:{ Exception -> 0x03eb }
            java.lang.Double r10 = java.lang.Double.valueOf(r10)     // Catch:{ Exception -> 0x03eb }
            double r10 = r10.doubleValue()     // Catch:{ Exception -> 0x03eb }
            r12 = r7[r9]     // Catch:{ Exception -> 0x03eb }
            java.lang.Double r12 = java.lang.Double.valueOf(r12)     // Catch:{ Exception -> 0x03eb }
            double r12 = r12.doubleValue()     // Catch:{ Exception -> 0x03eb }
            android.graphics.Point r10 = defpackage.cfg.a(r12, r10)     // Catch:{ Exception -> 0x03eb }
            r11 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int[] r11 = r11.coordX     // Catch:{ Exception -> 0x03eb }
            int r12 = r10.x     // Catch:{ Exception -> 0x03eb }
            r11[r9] = r12     // Catch:{ Exception -> 0x03eb }
            r11 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int[] r11 = r11.coordY     // Catch:{ Exception -> 0x03eb }
            int r10 = r10.y     // Catch:{ Exception -> 0x03eb }
            r11[r9] = r10     // Catch:{ Exception -> 0x03eb }
        L_0x02bf:
            int r9 = r9 + 1
            goto L_0x0292
        L_0x02c2:
            java.lang.String r5 = "stations"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x03eb }
            if (r5 == 0) goto L_0x03e3
            java.lang.String r5 = "stations"
            org.json.JSONArray r4 = r4.getJSONArray(r5)     // Catch:{ Exception -> 0x03eb }
            if (r4 == 0) goto L_0x03e3
            int r5 = r4.length()     // Catch:{ Exception -> 0x03eb }
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String[] r8 = new java.lang.String[r5]     // Catch:{ Exception -> 0x03eb }
            r7.stations = r8     // Catch:{ Exception -> 0x03eb }
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int[] r8 = new int[r5]     // Catch:{ Exception -> 0x03eb }
            r7.stationX = r8     // Catch:{ Exception -> 0x03eb }
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int[] r8 = new int[r5]     // Catch:{ Exception -> 0x03eb }
            r7.stationY = r8     // Catch:{ Exception -> 0x03eb }
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String[] r8 = new java.lang.String[r5]     // Catch:{ Exception -> 0x03eb }
            r7.stationIds = r8     // Catch:{ Exception -> 0x03eb }
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int[] r8 = new int[r5]     // Catch:{ Exception -> 0x03eb }
            r7.stationstatus = r8     // Catch:{ Exception -> 0x03eb }
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String[] r8 = new java.lang.String[r5]     // Catch:{ Exception -> 0x03eb }
            r7.stationpoiid1 = r8     // Catch:{ Exception -> 0x03eb }
            r7 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String[] r8 = new java.lang.String[r5]     // Catch:{ Exception -> 0x03eb }
            r7.stationpoiid2 = r8     // Catch:{ Exception -> 0x03eb }
            r7 = 0
        L_0x0303:
            if (r7 >= r5) goto L_0x03e3
            org.json.JSONObject r8 = r4.getJSONObject(r7)     // Catch:{ Exception -> 0x03eb }
            r9 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String[] r9 = r9.stations     // Catch:{ Exception -> 0x03eb }
            java.lang.String r10 = "name"
            java.lang.String r10 = r8.getString(r10)     // Catch:{ Exception -> 0x03eb }
            r9[r7] = r10     // Catch:{ Exception -> 0x03eb }
            r9 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x03eb }
            r10.<init>()     // Catch:{ Exception -> 0x03eb }
            java.lang.String r11 = "code"
            long r11 = r8.getLong(r11)     // Catch:{ Exception -> 0x03eb }
            r10.append(r11)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x03eb }
            r9.areacode = r10     // Catch:{ Exception -> 0x03eb }
            java.lang.String r9 = "xy_coords"
            java.lang.String r9 = r8.getString(r9)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r10 = ";"
            java.lang.String[] r9 = r9.split(r10)     // Catch:{ Exception -> 0x03eb }
            int r10 = r9.length     // Catch:{ Exception -> 0x03eb }
            r11 = 2
            if (r10 != r11) goto L_0x0364
            r10 = r9[r2]     // Catch:{ Exception -> 0x03eb }
            java.lang.Double r10 = java.lang.Double.valueOf(r10)     // Catch:{ Exception -> 0x03eb }
            double r10 = r10.doubleValue()     // Catch:{ Exception -> 0x03eb }
            r9 = r9[r6]     // Catch:{ Exception -> 0x03eb }
            java.lang.Double r9 = java.lang.Double.valueOf(r9)     // Catch:{ Exception -> 0x03eb }
            double r12 = r9.doubleValue()     // Catch:{ Exception -> 0x03eb }
            android.graphics.Point r9 = defpackage.cfg.a(r12, r10)     // Catch:{ Exception -> 0x03eb }
            r10 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int[] r10 = r10.stationX     // Catch:{ Exception -> 0x03eb }
            int r11 = r9.x     // Catch:{ Exception -> 0x03eb }
            r10[r7] = r11     // Catch:{ Exception -> 0x03eb }
            r10 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int[] r10 = r10.stationY     // Catch:{ Exception -> 0x03eb }
            int r9 = r9.y     // Catch:{ Exception -> 0x03eb }
            r10[r7] = r9     // Catch:{ Exception -> 0x03eb }
        L_0x0364:
            java.lang.String r9 = "name"
            java.lang.String r9 = r8.getString(r9)     // Catch:{ Exception -> 0x03eb }
            r10 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String[] r10 = r10.stations     // Catch:{ Exception -> 0x03eb }
            r10[r7] = r9     // Catch:{ Exception -> 0x03eb }
            java.lang.String r10 = "station_id"
            java.lang.String r10 = r8.getString(r10)     // Catch:{ Exception -> 0x03eb }
            r11 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String[] r11 = r11.stationIds     // Catch:{ Exception -> 0x03eb }
            r11[r7] = r10     // Catch:{ Exception -> 0x03eb }
            java.lang.String r11 = "code"
            long r11 = r8.getLong(r11)     // Catch:{ Exception -> 0x03eb }
            r14.mAdCode = r11     // Catch:{ Exception -> 0x03eb }
            java.lang.String r11 = "subways"
            boolean r11 = r8.has(r11)     // Catch:{ Exception -> 0x03eb }
            if (r11 == 0) goto L_0x039a
            java.lang.String r11 = "subways"
            org.json.JSONArray r11 = r8.getJSONArray(r11)     // Catch:{ Exception -> 0x03eb }
            r12 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            parseSubways(r12, r11, r9, r10)     // Catch:{ Exception -> 0x03eb }
        L_0x039a:
            java.lang.String r9 = "status"
            boolean r9 = r8.has(r9)     // Catch:{ Exception -> 0x03eb }
            if (r9 == 0) goto L_0x03b1
            r9 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int[] r9 = r9.stationstatus     // Catch:{ Exception -> 0x03eb }
            java.lang.String r10 = "status"
            int r10 = r8.getInt(r10)     // Catch:{ Exception -> 0x03eb }
            r9[r7] = r10     // Catch:{ Exception -> 0x03eb }
            goto L_0x03b7
        L_0x03b1:
            r9 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            int[] r9 = r9.stationstatus     // Catch:{ Exception -> 0x03eb }
            r9[r7] = r6     // Catch:{ Exception -> 0x03eb }
        L_0x03b7:
            java.lang.String r9 = "poiid1"
            boolean r9 = r8.has(r9)     // Catch:{ Exception -> 0x03eb }
            if (r9 == 0) goto L_0x03cb
            r9 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String[] r9 = r9.stationpoiid1     // Catch:{ Exception -> 0x03eb }
            java.lang.String r10 = "poiid1"
            java.lang.String r10 = r8.getString(r10)     // Catch:{ Exception -> 0x03eb }
            r9[r7] = r10     // Catch:{ Exception -> 0x03eb }
        L_0x03cb:
            java.lang.String r9 = "poiid2"
            boolean r9 = r8.has(r9)     // Catch:{ Exception -> 0x03eb }
            if (r9 == 0) goto L_0x03df
            r9 = r1[r3]     // Catch:{ Exception -> 0x03eb }
            java.lang.String[] r9 = r9.stationpoiid2     // Catch:{ Exception -> 0x03eb }
            java.lang.String r10 = "poiid2"
            java.lang.String r8 = r8.getString(r10)     // Catch:{ Exception -> 0x03eb }
            r9[r7] = r8     // Catch:{ Exception -> 0x03eb }
        L_0x03df:
            int r7 = r7 + 1
            goto L_0x0303
        L_0x03e3:
            int r3 = r3 + 1
            goto L_0x0011
        L_0x03e7:
            r14.addBusLineArray(r1, r2)     // Catch:{ Exception -> 0x03eb }
            return
        L_0x03eb:
            r15 = move-exception
            defpackage.kf.a(r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.inter.impl.BusLineSearchResultImpl.parseBusline(org.json.JSONObject):void");
    }

    public static IrregularTime getIrregularTime(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            IrregularTime irregularTime = new IrregularTime();
            irregularTime.normalday = axr.e(jSONObject, "normalday");
            irregularTime.workday = axr.e(jSONObject, "workday");
            irregularTime.holiday = axr.e(jSONObject, "holiday");
            return irregularTime;
        } catch (JSONException unused) {
            return null;
        }
    }

    public static void parseSubways(Bus bus, JSONArray jSONArray, String str, String str2) throws JSONException {
        if (jSONArray != null && jSONArray.length() != 0 && bus != null) {
            if (bus.subwayInfo == null) {
                bus.subwayInfo = new BusSubwayInfo();
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                BusSubwayStation busSubwayStation = new BusSubwayStation();
                busSubwayStation.busStationId = str2;
                busSubwayStation.busStationName = str;
                busSubwayStation.subwayName = jSONObject.getString("line_key");
                StringBuilder sb = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                sb.append(jSONObject.getString("color"));
                busSubwayStation.subwayColor = sb.toString();
                bus.subwayInfo.subwayList.add(busSubwayStation);
            }
        }
    }

    private void parseStation(JSONObject jSONObject) throws JSONException {
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("bus_list");
            if (jSONArray != null) {
                int length = jSONArray.length();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < length; i++) {
                    POI createPOI = POIFactory.createPOI();
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    if (jSONObject2.has("id")) {
                        createPOI.setId(jSONObject2.getString("id"));
                    }
                    if (jSONObject2.has("name")) {
                        createPOI.setName(jSONObject2.getString("name"));
                    }
                    if (jSONObject2.has("address")) {
                        createPOI.setAddr(jSONObject2.getString("address"));
                    }
                    if (jSONObject2.has("businfo_line_keys")) {
                        String[] split = jSONObject2.getString("businfo_line_keys").split(";|\\|");
                        StringBuffer stringBuffer = new StringBuffer();
                        HashMap hashMap = new HashMap();
                        for (int i2 = 0; i2 < split.length; i2++) {
                            if (!hashMap.containsKey(split[i2])) {
                                hashMap.put(split[i2], split[i2]);
                                if (i2 != 0) {
                                    stringBuffer.append("/");
                                }
                                stringBuffer.append(split[i2]);
                            }
                        }
                        hashMap.clear();
                        if (createPOI.getPoiExtra() != null) {
                            createPOI.getPoiExtra().put("station_lines", stringBuffer.toString());
                        }
                    }
                    double d = 0.0d;
                    double d2 = jSONObject2.has(DictionaryKeys.CTRLXY_X) ? jSONObject2.getDouble(DictionaryKeys.CTRLXY_X) : 0.0d;
                    if (jSONObject2.has(DictionaryKeys.CTRLXY_Y)) {
                        d = jSONObject2.getDouble(DictionaryKeys.CTRLXY_Y);
                    }
                    if (createPOI.getPoint() != null) {
                        createPOI.getPoint().setLonLat(d2, d);
                    }
                    if (jSONObject2.has("areacode")) {
                        createPOI.setAdCode(jSONObject2.getString("areacode"));
                    }
                    if (jSONObject2.has("businfo_lineids") && createPOI.getPoiExtra() != null) {
                        createPOI.getPoiExtra().put("businfo_lineids", (String) jSONObject2.get("businfo_lineids"));
                    }
                    arrayList.add(createPOI);
                }
                addStationArray(arrayList, false);
            }
        } catch (JSONException e) {
            kf.a((Throwable) e);
        }
    }

    public static String getInterval4String(String str) {
        if (!TextUtils.isEmpty(str) && str.length() < 4) {
            int parseInt = Integer.parseInt(str);
            int i = parseInt / 60;
            int i2 = parseInt % 60;
            Resources resources = AMapAppGlobal.getApplication().getResources();
            if (str.length() == 0) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            if (i > 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(i);
                sb2.append(resources.getString(R.string.hour));
                sb.append(sb2.toString());
            }
            if (i2 > 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(i2);
                sb3.append(resources.getString(R.string.minute));
                sb.append(sb3.toString());
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(resources.getString(R.string.train_starting_interval));
            sb4.append(":");
            sb4.append(resources.getString(R.string.approx));
            sb4.append(sb.toString());
            sb4.append("/");
            sb4.append(resources.getString(R.string.one_trip));
            return sb4.toString();
        } else if (str.length() != 4) {
            return "";
        } else {
            String substring = str.substring(0, 2);
            String substring2 = str.substring(2, 4);
            int parseInt2 = Integer.parseInt(substring);
            int parseInt3 = Integer.parseInt(substring2);
            StringBuilder sb5 = new StringBuilder();
            Resources resources2 = AMapAppGlobal.getApplication().getResources();
            if (parseInt2 != 0) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(parseInt2);
                sb6.append(resources2.getString(R.string.hour));
                sb5.append(sb6.toString());
            }
            if (parseInt3 != 0) {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(parseInt3);
                sb7.append(resources2.getString(R.string.minute));
                sb5.append(sb7.toString());
            }
            sb5.length();
            String sb8 = sb5.toString();
            if (sb8.length() == 0) {
                return sb8;
            }
            StringBuilder sb9 = new StringBuilder();
            sb9.append(resources2.getString(R.string.train_starting_interval));
            sb9.append(":");
            sb9.append(resources2.getString(R.string.approx));
            sb9.append(sb8);
            sb9.append("/");
            sb9.append(resources2.getString(R.string.one_trip));
            return sb9.toString();
        }
    }

    public String getKey() {
        return this.mKey;
    }

    public void setKey(String str) {
        this.mKey = str;
    }

    public String getLineID() {
        return this.lineId;
    }

    public void setFocusChildIndex(int i) {
        this.mFocusChildStaionIndex = i;
    }

    public int getFocusChildIndex() {
        return this.mFocusChildStaionIndex;
    }

    public void setBusRequest(BusBaseRequest busBaseRequest) {
        this.mWrapper = busBaseRequest;
    }

    public BusBaseRequest getBusReqest() {
        return this.mWrapper;
    }
}
