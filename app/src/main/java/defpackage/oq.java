package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: oq reason: default package */
/* compiled from: CarSceneSearchParser */
public final class oq extends AbstractAOSParser {
    public oo a;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        int i;
        int i2;
        JSONObject parseHeader = parseHeader(bArr);
        if (parseHeader != null) {
            this.a = new oo();
            JSONArray optJSONArray = parseHeader.optJSONArray("naviinfo");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(0);
                if (optJSONObject != null && optJSONObject.has("children")) {
                    JSONArray optJSONArray2 = optJSONObject.optJSONArray("children");
                    if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                        int length = optJSONArray2.length();
                        int i3 = 0;
                        while (i3 < length) {
                            JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i3);
                            ISearchPoiData iSearchPoiData = (ISearchPoiData) POIFactory.createPOI(ISearchPoiData.class);
                            if (optJSONObject2.has("shortname")) {
                                iSearchPoiData.setName(optJSONObject2.optString("shortname"));
                            } else {
                                iSearchPoiData.setName(optJSONObject2.optString("name"));
                            }
                            if (optJSONObject2.has(LocationInstrument.LOCATION_EXTRAS_KEY_POIID)) {
                                iSearchPoiData.setId(optJSONObject2.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
                            }
                            if (optJSONObject2.has("end_poi_extension")) {
                                iSearchPoiData.setEndPoiExtension(optJSONObject2.optString("end_poi_extension"));
                            }
                            if (optJSONObject2.has(H5Param.LONG_TRANSPARENT)) {
                                iSearchPoiData.setTransparent(optJSONObject2.optString(H5Param.LONG_TRANSPARENT));
                            }
                            iSearchPoiData.setType(optJSONObject2.optString("new_type"));
                            iSearchPoiData.getPoint().setLonLat(optJSONObject2.optDouble(DictionaryKeys.CTRLXY_X), optJSONObject2.optDouble(DictionaryKeys.CTRLXY_Y));
                            String[] split = optJSONObject2.optString("geometry").split(",");
                            if (split != null) {
                                int length2 = split.length / 2;
                                ArrayList arrayList = new ArrayList(length2);
                                int i4 = 0;
                                while (i4 < length2) {
                                    try {
                                        int i5 = i4 * 2;
                                        i2 = length;
                                        try {
                                            arrayList.add(new GeoPoint(Double.parseDouble(split[i5]), Double.parseDouble(split[i5 + 1])));
                                        } catch (NumberFormatException e) {
                                            e = e;
                                        }
                                    } catch (NumberFormatException e2) {
                                        e = e2;
                                        i2 = length;
                                        kf.a((Throwable) e);
                                        i4++;
                                        length = i2;
                                    }
                                    i4++;
                                    length = i2;
                                }
                                i = length;
                                iSearchPoiData.setEntranceList(arrayList);
                            } else {
                                i = length;
                            }
                            String optString = optJSONObject2.optString("rel_type");
                            if (!TextUtils.isEmpty(optString)) {
                                if (optString.equalsIgnoreCase("103")) {
                                    this.a.a.add(iSearchPoiData);
                                } else if (optString.equalsIgnoreCase("104")) {
                                    this.a.b.add(iSearchPoiData);
                                } else if (optString.equalsIgnoreCase("305")) {
                                    this.a.c.add(iSearchPoiData);
                                } else if (optString.equalsIgnoreCase("303")) {
                                    this.a.d.add(iSearchPoiData);
                                } else if (optString.equalsIgnoreCase("105")) {
                                    this.a.e.add(iSearchPoiData);
                                } else if (optString.equalsIgnoreCase("106")) {
                                    this.a.f.add(iSearchPoiData);
                                }
                            }
                            i3++;
                            length = i;
                        }
                    }
                }
            }
        }
    }
}
