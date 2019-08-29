package defpackage;

import android.graphics.Point;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.ae.search.model.GADAREAEXTRAINFO;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bl.search.PoilistPassageway;
import com.autonavi.bl.search.PoilistPoiInfo;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* renamed from: aer reason: default package */
/* compiled from: JavaInfoliteParser */
public final class aer {

    /* renamed from: aer$a */
    /* compiled from: JavaInfoliteParser */
    public static class a {
        public InfoliteParam a;

        public final List<POI> a(List<PoilistPoiInfo> list) {
            ArrayList arrayList = new ArrayList();
            if (list == null || list.size() == 0) {
                return arrayList;
            }
            for (PoilistPoiInfo a2 : list) {
                SearchPoi searchPoi = (SearchPoi) POIFactory.createPOI(SearchPoi.class);
                a(a2, searchPoi);
                arrayList.add(searchPoi);
            }
            return arrayList;
        }

        private void a(PoilistPoiInfo poilistPoiInfo, ISearchPoiData iSearchPoiData) {
            iSearchPoiData.setId(poilistPoiInfo.id);
            iSearchPoiData.setType(poilistPoiInfo.typecode);
            StringBuilder sb = new StringBuilder();
            sb.append(poilistPoiInfo.adcode);
            iSearchPoiData.setAdCode(sb.toString());
            iSearchPoiData.setName(poilistPoiInfo.name);
            iSearchPoiData.setPhone(poilistPoiInfo.tel);
            iSearchPoiData.setAddr(poilistPoiInfo.address);
            adz adz = (adz) defpackage.esb.a.a.a(adz.class);
            if (TextUtils.isEmpty(poilistPoiInfo.address) && poilistPoiInfo.adcode > 0 && adz != null) {
                GADAREAEXTRAINFO a2 = adz.a(poilistPoiInfo.adcode);
                if (a2 != null) {
                    String townName = a2.getTownName();
                    String cityName = a2.getCityName();
                    String provName = a2.getProvName();
                    if (!TextUtils.isEmpty(townName)) {
                        iSearchPoiData.setAddr(townName);
                    } else if (!TextUtils.isEmpty(cityName)) {
                        iSearchPoiData.setAddr(cityName);
                    } else if (!TextUtils.isEmpty(provName)) {
                        iSearchPoiData.setAddr(provName);
                    }
                }
            }
            double d = poilistPoiInfo.longitude;
            double d2 = poilistPoiInfo.latitude;
            if (d > 0.0d && d2 > 0.0d) {
                iSearchPoiData.getPoint().setLonLat(d, d2);
            }
            iSearchPoiData.getPoiExtra().put("SrcType", "nativepoi");
            GeoPoint geoPoint = null;
            if (this.a != null) {
                iSearchPoiData.getPoiExtra().put("query_type", this.a.query_type);
                if ("RQBXY".equals(this.a.query_type)) {
                    geoPoint = new GeoPoint(this.a.longitude, this.a.latitude);
                }
            }
            iSearchPoiData.setDistance(aeq.a((SearchPoi) iSearchPoiData, poilistPoiInfo.distance, geoPoint));
            if (poilistPoiInfo.entrances != null && poilistPoiInfo.entrances.size() > 0) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < poilistPoiInfo.entrances.size(); i++) {
                    PoilistPassageway poilistPassageway = poilistPoiInfo.entrances.get(i);
                    double d3 = poilistPassageway.longitude;
                    double d4 = poilistPassageway.latitude;
                    if (d3 > 0.0d && d4 > 0.0d) {
                        Point a3 = bcz.a(d4, d3);
                        arrayList.add(new GeoPoint(a3.x, a3.y));
                    }
                }
                iSearchPoiData.setEntranceList(arrayList);
            }
        }

        public static void b(List<POI> list) {
            for (POI next : list) {
                if (next != null && TextUtils.isEmpty(next.getAddr())) {
                    try {
                        String adCode = next.getAdCode();
                        adz adz = (adz) defpackage.esb.a.a.a(adz.class);
                        if (!TextUtils.isEmpty(adCode) && TextUtils.isDigitsOnly(adCode) && adz != null) {
                            GADAREAEXTRAINFO a2 = adz.a(Integer.parseInt(adCode));
                            if (a2 != null) {
                                String townName = a2.getTownName();
                                String cityName = a2.getCityName();
                                String provName = a2.getProvName();
                                if (!TextUtils.isEmpty(townName)) {
                                    next.setAddr(townName);
                                } else if (!TextUtils.isEmpty(cityName)) {
                                    next.setAddr(cityName);
                                } else if (!TextUtils.isEmpty(provName)) {
                                    next.setAddr(provName);
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /* renamed from: aer$b */
    /* compiled from: JavaInfoliteParser */
    public static class b {
        public static aud a(JSONObject jSONObject) {
            try {
                aud aud = new aud();
                aep.a(jSONObject, aud);
                aud.c.isOnLine = true;
                return aud;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
