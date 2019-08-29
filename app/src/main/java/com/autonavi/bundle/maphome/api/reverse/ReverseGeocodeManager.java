package com.autonavi.bundle.maphome.api.reverse;

import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.xidea.el.impl.ReflectUtil;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class ReverseGeocodeManager {
    private static awe a = ((awe) ank.a(awe.class));

    public static a getReverseGeocodeResult(GeoPoint geoPoint, Callback<ReverseGeocodeResponser> callback) {
        return getReverseGeocodeResult(geoPoint, 5, callback);
    }

    public static a getReverseGeocodeResult(GeoPoint geoPoint, int i, Callback<ReverseGeocodeResponser> callback) {
        return a.a(geoPoint, i, callback);
    }

    public static <T> a get(final Callback<T> callback, final GeoPoint geoPoint) {
        if (callback != null && geoPoint != null) {
            return getReverseGeocodeResult(geoPoint, new Callback<ReverseGeocodeResponser>() {
                public final void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
                    if (reverseGeocodeResponser == null) {
                        callback.callback(null);
                        return;
                    }
                    Class<? extends Object> a2 = ReflectUtil.a(ReflectUtil.a((Type) callback.getClass(), Callback.class));
                    POI createPOI = POIFactory.createPOI(reverseGeocodeResponser.getPosition(), geoPoint);
                    createPOI.setAdCode(reverseGeocodeResponser.getAdCode());
                    createPOI.setAddr(reverseGeocodeResponser.getDesc());
                    createPOI.setCityCode(reverseGeocodeResponser.getAreaCode());
                    createPOI.setCityName(reverseGeocodeResponser.getCity());
                    if (a2.equals(String.class)) {
                        callback.callback(reverseGeocodeResponser.getDesc());
                    } else if (POI.class.isAssignableFrom(a2)) {
                        callback.callback(createPOI);
                    } else if (List.class.isAssignableFrom(a2)) {
                        ArrayList<POI> poiList = reverseGeocodeResponser.getPoiList();
                        if (poiList == null) {
                            poiList = new ArrayList<>();
                        }
                        poiList.add(0, createPOI);
                        callback.callback(poiList);
                    } else {
                        StringBuilder sb = new StringBuilder("ParameterizedType ");
                        sb.append(a2.getName());
                        sb.append(" is not support in reverseGeocode~!");
                        throw new IllegalArgumentException(sb.toString());
                    }
                }

                public final void error(Throwable th, boolean z) {
                    if (callback != null) {
                        callback.error(th, z);
                    }
                }
            });
        }
        throw new IllegalArgumentException("Callback or GeoPoint should not bue null");
    }
}
