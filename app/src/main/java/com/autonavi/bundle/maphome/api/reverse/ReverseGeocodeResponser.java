package com.autonavi.bundle.maphome.api.reverse;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class ReverseGeocodeResponser extends AbstractAOSParser {
    private String a = null;
    private ArrayList<POI> b = new ArrayList<>();
    private String c = null;
    private String d = "";
    private String e = "";
    private String f = null;
    private String g;
    private String h;
    private String i;

    public void setPoiList(ArrayList<POI> arrayList) {
        this.b = arrayList;
    }

    public void setDesc(String str) {
        this.c = str;
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        try {
            JSONObject parseHeader = parseHeader(bArr);
            if (parseHeader != null) {
                this.h = parseHeader.optString("pos");
                this.d = parseHeader.optString("province");
                this.e = parseHeader.optString("city");
                this.f = parseHeader.optString(AutoJsonUtils.JSON_ADCODE);
                this.i = parseHeader.optString("district");
                this.c = parseHeader.optString("desc");
                this.a = parseHeader.optString("cityadcode");
                this.g = parseHeader.optString("areacode");
                if (parseHeader.has("poi_list")) {
                    JSONArray jSONArray = parseHeader.getJSONArray("poi_list");
                    if (jSONArray != null) {
                        int length = jSONArray.length();
                        if (length > 0) {
                            for (int i2 = 0; i2 < length; i2++) {
                                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                                POI createPOI = POIFactory.createPOI(jSONObject.getString("name"), new GeoPoint(jSONObject.getDouble("longitude"), jSONObject.getDouble("latitude")));
                                createPOI.setId(jSONObject.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
                                createPOI.setAddr(jSONObject.getString("address"));
                                createPOI.setEndPoiExtension(jSONObject.optString("end_poi_extension"));
                                createPOI.setTransparent(jSONObject.optString(H5Param.LONG_TRANSPARENT));
                                double optDouble = jSONObject.optDouble("x_entr");
                                double optDouble2 = jSONObject.optDouble("y_entr");
                                if (!Double.isNaN(optDouble) && !Double.isNaN(optDouble2)) {
                                    GeoPoint geoPoint = new GeoPoint(optDouble, optDouble2);
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(geoPoint);
                                    createPOI.setEntranceList(arrayList);
                                }
                                if (jSONObject.has("entrances")) {
                                    JSONArray jSONArray2 = jSONObject.getJSONArray("entrances");
                                    if (jSONArray2 != null && jSONArray2.length() > 0) {
                                        ArrayList arrayList2 = new ArrayList();
                                        for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                                            JSONObject jSONObject2 = jSONArray2.getJSONObject(i3);
                                            double optDouble3 = jSONObject2.optDouble("longitude");
                                            double optDouble4 = jSONObject2.optDouble("latitude");
                                            if (!Double.isNaN(optDouble4) && !Double.isNaN(optDouble3)) {
                                                arrayList2.add(new GeoPoint(optDouble3, optDouble4));
                                            }
                                        }
                                        createPOI.setEntranceList(arrayList2);
                                    }
                                }
                                FavoritePOI favoritePOI = (FavoritePOI) createPOI.as(FavoritePOI.class);
                                favoritePOI.setNewType(jSONObject.optString("typecode", ""));
                                this.b.add(favoritePOI);
                            }
                        }
                    }
                }
            }
        } catch (Exception e2) {
            kf.a((Throwable) e2);
        }
    }

    public String getCityAdCode() {
        return this.a;
    }

    public String getDesc() {
        return this.c;
    }

    public String getPos() {
        if (this.b == null || this.b.size() == 0) {
            return getPosition();
        }
        return this.b.get(0).getName();
    }

    public ArrayList<POI> getPoiList() {
        return this.b;
    }

    public String getShortDesc() {
        if (TextUtils.isEmpty(this.c)) {
            return "";
        }
        return this.c.replace(this.d, "").replace(this.e, "");
    }

    public String getDetailDesc() {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(this.c)) {
            return "";
        }
        sb.append(this.c.replace(this.d, "").replace(this.e, ""));
        if (this.b != null && this.b.size() > 0) {
            POI poi = this.b.get(0);
            if (poi != null) {
                sb.append(poi.getName());
            }
        }
        return sb.toString();
    }

    public String getAdCode() {
        return this.f;
    }

    public String getAreaCode() {
        return this.g;
    }

    public String getPosition() {
        return this.h;
    }

    public String getDistrict() {
        return this.i;
    }

    public String getErrorDesc(int i2) {
        return this.errorMessage;
    }

    public String getCity() {
        return this.e;
    }

    public String getProvince() {
        return this.d;
    }
}
