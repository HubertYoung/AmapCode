package com.autonavi.ae.bl.search;

import com.autonavi.jni.ae.bl.Parcel;
import com.autonavi.jni.ae.bl.Parcelable;

public class BLSearchCondition implements Parcelable {
    public static final int LANGUAGE_TYPE_ENGLISH = 1;
    public static final int LANGUAGE_TYPE_MAX = 3;
    public static final int LANGUAGE_TYPE_SIMPLE_CHINESE = 0;
    public static final int LANGUAGE_TYPE_TRADITIONAL_CHINESE = 2;
    public static final int SEARCH_FORM_SEARCH = 2;
    public static final int SEARCH_FORM_SUGGEST = 1;
    public static final int SEARCH_TYPE_AROUND = 2;
    public static final int SEARCH_TYPE_FTS = 1;
    public static final int SEARCH_TYPE_NEAREST = 4;
    public static final int SEARCH_TYPE_ONROUTE = 3;
    public static final int SEARCH_TYPE_SELECT = 5;
    public int aroundRadius = 20000;
    public RoadId[] guideRoads;
    public String keyword;
    public int languageType = 0;
    public int lat;
    public int locatedAdminCode;
    public int lon;
    public int resultMaxCount = 200;
    public int searchForm = 2;
    public int searchType;

    public static class RoadId {
        public int roadId;
        public int tileId;
        public int urId;
    }

    public boolean readFromParcel(Parcel parcel) {
        return false;
    }

    public boolean writeToParcel(Parcel parcel) {
        parcel.reset();
        parcel.writeInt(this.languageType);
        parcel.writeInt(this.searchType);
        parcel.writeInt(this.searchForm);
        parcel.writeInt(this.resultMaxCount);
        parcel.writeInt(this.locatedAdminCode);
        parcel.writeInt(this.aroundRadius);
        parcel.writeInt(this.lon);
        parcel.writeInt(this.lat);
        parcel.writeString(this.keyword);
        if (this.guideRoads == null) {
            parcel.writeInt(0);
            return true;
        }
        parcel.writeInt(this.guideRoads.length);
        for (int i = 0; i < this.guideRoads.length; i++) {
            parcel.writeInt(this.guideRoads[i].urId);
            parcel.writeInt(this.guideRoads[i].tileId);
            parcel.writeInt(this.guideRoads[i].roadId);
        }
        return true;
    }
}
