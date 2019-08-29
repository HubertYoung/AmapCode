package com.autonavi.miniapp.plugin.lbs;

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
public class MiniappReverseGeocodeResponser extends AbstractAOSParser {
    private String adcode = null;
    private String areacode;
    private String city = "";
    private String country = null;
    private String district;
    private String districtadcode;
    private String hn;
    private ArrayList<POI> list = new ArrayList<>();
    private String mCityAdCode = null;
    private String mDesc = null;
    private String mPos;
    private String province = "";
    private String street;

    public void setPoiList(ArrayList<POI> arrayList) {
        this.list = arrayList;
    }

    public void setDesc(String str) {
        this.mDesc = str;
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        try {
            JSONObject parseHeader = parseHeader(bArr);
            if (parseHeader != null) {
                this.mPos = parseHeader.optString("pos");
                this.province = parseHeader.optString("province");
                this.city = parseHeader.optString("city");
                this.adcode = parseHeader.optString(AutoJsonUtils.JSON_ADCODE);
                this.district = parseHeader.optString("district");
                this.mDesc = parseHeader.optString("desc");
                this.mCityAdCode = parseHeader.optString("cityadcode");
                this.areacode = parseHeader.optString("areacode");
                this.country = parseHeader.optString("country");
                this.districtadcode = parseHeader.optString("districtadcode");
                this.hn = parseHeader.optString("hn");
                if (parseHeader.has("road_list")) {
                    JSONArray jSONArray = parseHeader.getJSONArray("road_list");
                    if (jSONArray != null && jSONArray.length() > 0) {
                        this.street = jSONArray.optJSONObject(0).optString("name");
                    }
                }
                if (parseHeader.has("poi_list")) {
                    JSONArray jSONArray2 = parseHeader.getJSONArray("poi_list");
                    if (jSONArray2 != null) {
                        int length = jSONArray2.length();
                        if (length > 0) {
                            for (int i = 0; i < length; i++) {
                                JSONObject jSONObject = jSONArray2.getJSONObject(i);
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
                                    JSONArray jSONArray3 = jSONObject.getJSONArray("entrances");
                                    if (jSONArray3 != null && jSONArray3.length() > 0) {
                                        ArrayList arrayList2 = new ArrayList();
                                        for (int i2 = 0; i2 < jSONArray3.length(); i2++) {
                                            JSONObject jSONObject2 = jSONArray3.getJSONObject(i2);
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
                                this.list.add(favoritePOI);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    public String getCityAdCode() {
        return this.mCityAdCode;
    }

    public String getDesc() {
        return this.mDesc;
    }

    public String getPos() {
        if (this.list == null || this.list.size() == 0) {
            return getPosition();
        }
        return this.list.get(0).getName();
    }

    public ArrayList<POI> getPoiList() {
        return this.list;
    }

    public String getShortDesc() {
        if (TextUtils.isEmpty(this.mDesc)) {
            return "";
        }
        return this.mDesc.replace(this.province, "").replace(this.city, "");
    }

    public String getDetailDesc() {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(this.mDesc)) {
            return "";
        }
        sb.append(this.mDesc.replace(this.province, "").replace(this.city, ""));
        if (this.list != null && this.list.size() > 0) {
            POI poi = this.list.get(0);
            if (poi != null) {
                sb.append(poi.getName());
            }
        }
        return sb.toString();
    }

    public String getAdCode() {
        return this.adcode;
    }

    public String getAreaCode() {
        return this.areacode;
    }

    public String getPosition() {
        return this.mPos;
    }

    public String getDistrict() {
        return this.district;
    }

    public String getStreetNumber() {
        return this.hn;
    }

    public String getErrorDesc(int i) {
        return this.errorMessage;
    }

    public String getCity() {
        return this.city;
    }

    public String getProvince() {
        return this.province;
    }

    public String getDistrictadcode() {
        return this.districtadcode;
    }

    public String getStreet() {
        return this.street;
    }

    public String getCountry() {
        return this.country;
    }
}
