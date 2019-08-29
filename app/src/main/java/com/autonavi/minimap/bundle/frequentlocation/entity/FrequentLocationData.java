package com.autonavi.minimap.bundle.frequentlocation.entity;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.bedstone.model.FrequentLocationDBInfo;
import com.autonavi.minimap.bedstone.model.FrequentLocationInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class FrequentLocationData {
    public int a = 1000;
    public boolean b = false;
    public String c;
    private String d;
    private FrequentLocationDBInfo e;
    private POI f;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private FrequentLocationData(String str) {
        this.d = str;
    }

    public static FrequentLocationData a(@NonNull FrequentLocationDBInfo frequentLocationDBInfo) {
        FrequentLocationData frequentLocationData = new FrequentLocationData(frequentLocationDBInfo.name);
        frequentLocationData.e = frequentLocationDBInfo;
        return frequentLocationData;
    }

    public static FrequentLocationData a(@NonNull POI poi, String str) {
        FrequentLocationData frequentLocationData = new FrequentLocationData(poi.getName());
        frequentLocationData.f = poi;
        frequentLocationData.c = str;
        return frequentLocationData;
    }

    public static FrequentLocationData a() {
        return new FrequentLocationData("");
    }

    @NonNull
    public final POI b() {
        if (this.f != null) {
            return this.f;
        }
        if (this.e == null) {
            return POIFactory.createPOI();
        }
        if (this.a == 1002 || this.a == 1001) {
            return c(this.e);
        }
        return b(this.e);
    }

    public final String c() {
        return !TextUtils.isEmpty(this.d) ? this.d : "";
    }

    private static POI b(@NonNull FrequentLocationDBInfo frequentLocationDBInfo) {
        POI a2 = frequentLocationDBInfo.FrequentLocation != null ? a(frequentLocationDBInfo.FrequentLocation) : null;
        if (a2 == null) {
            a2 = POIFactory.createPOI();
        }
        if (a2 != null) {
            a2.setId(frequentLocationDBInfo.poiid);
            a2.setName(frequentLocationDBInfo.name);
            a2.setPoint(new GeoPoint(frequentLocationDBInfo.x, frequentLocationDBInfo.y));
        }
        return a2;
    }

    public static POI a(FrequentLocationInfo frequentLocationInfo) {
        if (frequentLocationInfo == null) {
            return null;
        }
        POI createPOI = POIFactory.createPOI();
        createPOI.setId(frequentLocationInfo.poiid);
        createPOI.setName(frequentLocationInfo.name);
        createPOI.setPoint(new GeoPoint(frequentLocationInfo.x, frequentLocationInfo.y));
        createPOI.setCityCode(frequentLocationInfo.cityCode);
        createPOI.setType(frequentLocationInfo.poiType);
        createPOI.setEntranceList(frequentLocationInfo.entranceList);
        createPOI.setEndPoiExtension(frequentLocationInfo.endPoiExtension);
        createPOI.setTransparent(frequentLocationInfo.transparent);
        return createPOI;
    }

    private static FavoritePOI c(@NonNull FrequentLocationDBInfo frequentLocationDBInfo) {
        FavoritePOI b2 = frequentLocationDBInfo.FrequentLocation != null ? b(frequentLocationDBInfo.FrequentLocation) : null;
        if (b2 == null) {
            b2 = (FavoritePOI) POIFactory.createPOI().as(FavoritePOI.class);
        }
        if (b2 != null) {
            b2.setId(frequentLocationDBInfo.poiid);
            b2.setName(frequentLocationDBInfo.name);
            b2.setPoint(new GeoPoint(frequentLocationDBInfo.x, frequentLocationDBInfo.y));
        }
        return b2;
    }

    private static FavoritePOI b(FrequentLocationInfo frequentLocationInfo) {
        if (frequentLocationInfo == null) {
            return null;
        }
        FavoritePOI favoritePOI = (FavoritePOI) POIFactory.createPOI().as(FavoritePOI.class);
        favoritePOI.setId(frequentLocationInfo.poiid);
        favoritePOI.setName(frequentLocationInfo.name);
        favoritePOI.setPoint(new GeoPoint(frequentLocationInfo.x, frequentLocationInfo.y));
        favoritePOI.setCityCode(frequentLocationInfo.cityCode);
        favoritePOI.setType(frequentLocationInfo.poiType);
        favoritePOI.setEntranceList(frequentLocationInfo.entranceList);
        favoritePOI.setChildType(frequentLocationInfo.childType);
        favoritePOI.setFnona(frequentLocationInfo.fnona);
        favoritePOI.setTowardsAngle(frequentLocationInfo.towardsAngle);
        favoritePOI.setEndPoiExtension(frequentLocationInfo.endPoiExtension);
        favoritePOI.setTransparent(frequentLocationInfo.transparent);
        return favoritePOI;
    }
}
