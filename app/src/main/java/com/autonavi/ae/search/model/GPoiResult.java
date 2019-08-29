package com.autonavi.ae.search.model;

import android.graphics.PointF;
import com.autonavi.ae.bl.search.BLSearchResult.Poi;
import com.autonavi.ae.search.log.GLog;
import com.autonavi.jni.ae.bl.Parcel;
import java.util.ArrayList;
import java.util.List;

public class GPoiResult {
    private static String TAG = "wmh";
    private List<GPoiBase> pPoi = new ArrayList();
    private int sNumberOfItemGet;
    private int uFlag;

    private GPoiResult(int i, int i2) {
        this.sNumberOfItemGet = i;
        this.uFlag = i2;
        if (GLog.isLogShow()) {
            GLog.v(TAG, "new GPoiResult(int sNumberOfTotalItem, int sIndex, int sNumberOfItemGet,int uFlag)");
        }
    }

    public GPoiResult(Parcel parcel) {
        parcel.reset();
        parcel.readInt();
        this.sNumberOfItemGet = parcel.readInt();
        if (this.sNumberOfItemGet > 0) {
            Poi poi = new Poi();
            for (int i = 0; i < this.sNumberOfItemGet; i++) {
                poi.pid = parcel.readString();
                poi.adminCode = parcel.readInt();
                poi.categoryCode = parcel.readInt();
                poi.locatedCoordLat = parcel.readInt();
                poi.locatedCoordLon = parcel.readInt();
                poi.navigationalCoordLat = parcel.readInt();
                poi.navigationalCoordLon = parcel.readInt();
                poi.name = parcel.readString();
                poi.alias = parcel.readString();
                poi.address = parcel.readString();
                poi.telephone = parcel.readString();
                poi.categoryName = parcel.readString();
                poi.matchPosition = parcel.readInt();
                GPoiBean gPoiBean = new GPoiBean(poi.pid, poi.name, poi.address, poi.telephone, poi.adminCode, poi.categoryCode, poi.categoryName, new PointF(((float) poi.locatedCoordLon) / 1000000.0f, ((float) poi.locatedCoordLat) / 1000000.0f), new PointF(((float) poi.navigationalCoordLon) / 1000000.0f, ((float) poi.navigationalCoordLat) / 1000000.0f), poi.matchPosition);
                addPoiObj(gPoiBean);
            }
        }
    }

    public int getNumberOfItemGet() {
        return this.sNumberOfItemGet;
    }

    private int addPoiObj(GPoiBase gPoiBase) {
        if (gPoiBase == null) {
            return -1;
        }
        this.pPoi.add(gPoiBase);
        return 1;
    }

    public List<GPoiBase> getPoiList() {
        return this.pPoi;
    }
}
