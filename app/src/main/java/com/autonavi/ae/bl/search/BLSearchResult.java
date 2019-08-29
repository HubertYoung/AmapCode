package com.autonavi.ae.bl.search;

import com.autonavi.jni.ae.bl.Parcel;
import com.autonavi.jni.ae.bl.Parcelable;

public class BLSearchResult implements Parcelable {
    public static final int SEARCH_STATUS_ABORTED = 2;
    public static final int SEARCH_STATUS_ENOUGH = 8;
    public static final int SEARCH_STATUS_ERRDATA = 6;
    public static final int SEARCH_STATUS_FAILED = -1;
    public static final int SEARCH_STATUS_IDEXISTED = 5;
    public static final int SEARCH_STATUS_IDNOTFOUND = 4;
    public static final int SEARCH_STATUS_NODATA = 1;
    public static final int SEARCH_STATUS_NOMEMORY = 3;
    public static final int SEARCH_STATUS_SUCCEED = 0;
    public static final int SEARCH_STATUS_WRITEFILEERR = 7;
    public int count;
    public Poi[] pois;
    public SearchMatchInfo[] searchMatchInfos;
    public int searchStatus;
    public String statusMsg;

    public static class Poi {
        public String address;
        public int adminCode;
        public String alias;
        public int categoryCode;
        public String categoryName;
        public int locatedCoordLat;
        public int locatedCoordLon;
        public int matchPosition;
        public String name;
        public int navigationalCoordLat;
        public int navigationalCoordLon;
        public String pid;
        public String telephone;
    }

    public static class SearchMatchInfo {
        public int distance;
        public int isSuggestedCategory;
        public int matchPosition;
        public int matchValue;
        public int priority;
    }

    public boolean writeToParcel(Parcel parcel) {
        return false;
    }

    public boolean readFromParcel(Parcel parcel) {
        parcel.reset();
        this.searchStatus = parcel.readInt();
        this.count = parcel.readInt();
        if (this.count > 0) {
            this.pois = new Poi[this.count];
            for (int i = 0; i < this.count; i++) {
                Poi poi = new Poi();
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
                this.pois[i] = poi;
            }
        }
        return true;
    }
}
