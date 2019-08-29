package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.model.GeoPoint;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: djf reason: default package */
/* compiled from: AosIllegalparkingResponsor */
public final class djf extends AbstractAOSParser {
    public djg a;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        int i;
        int i2;
        JSONArray jSONArray;
        int i3;
        int i4;
        JSONArray jSONArray2;
        int i5;
        int i6;
        JSONArray jSONArray3;
        int i7;
        parseHeader(bArr);
        int i8 = 1;
        if (this.errorCode == 1) {
            JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
            this.a = new djg();
            this.a.h = jSONObject.optInt("total");
            this.a.b = jSONObject.optString("update_message");
            this.a.c = jSONObject.optInt("result_code");
            JSONArray optJSONArray = jSONObject.optJSONArray("poi_list");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                int length = optJSONArray.length();
                int i9 = 0;
                while (i9 < length) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i9);
                    if (optJSONObject != null) {
                        div div = new div();
                        div.a = optJSONObject.optString("id");
                        div.d = optJSONObject.optDouble("latitude");
                        div.c = optJSONObject.optDouble("longitude");
                        div.b = optJSONObject.optString("name");
                        div.e = optJSONObject.optString("citycode");
                        div.f = optJSONObject.optString("typecode");
                        div.g = optJSONObject.optInt("danger_level");
                        div.j = optJSONObject.optString("last_time");
                        div.h = optJSONObject.optString("street_type");
                        div.i = optJSONObject.optString("data_source");
                        div.o = optJSONObject.optString("high_frequency_time");
                        div.k = optJSONObject.optInt("total_d_no");
                        div.l = optJSONObject.optString("violation_type");
                        div.m = optJSONObject.optString("details");
                        div.n = optJSONObject.optJSONArray("data_source_index");
                        String optString = optJSONObject.optString("shape");
                        String optString2 = optJSONObject.optString("shape_ext");
                        try {
                            int parseInt = Integer.parseInt(div.h);
                            if (parseInt >= 0 && parseInt <= i8 && div.g >= 0) {
                                int i10 = 2;
                                if (div.g <= 2) {
                                    if (parseInt == i8) {
                                        div.g = 3;
                                    }
                                    if (TextUtils.isEmpty(optString2) || !optString2.contains(MergeUtil.SEPARATOR_KV)) {
                                        jSONArray = optJSONArray;
                                        i2 = length;
                                        i = i9;
                                        if (!TextUtils.isEmpty(optString) && !optString2.contains(MergeUtil.SEPARATOR_KV)) {
                                            String[] split = optString.split(",");
                                            if (split != null) {
                                                int length2 = split.length / 2;
                                                div.p = new ArrayList();
                                                for (int i11 = 0; i11 < length2; i11++) {
                                                    try {
                                                        int i12 = i11 * 2;
                                                        div.p.add(new GeoPoint(Double.parseDouble(split[i12]), Double.parseDouble(split[i12 + 1])));
                                                    } catch (NumberFormatException e) {
                                                        kf.a((Throwable) e);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        String[] split2 = optString2.split("\\|");
                                        div.q = new ArrayList<>();
                                        int i13 = 0;
                                        while (i13 < split2.length) {
                                            String[] split3 = split2[i13].split(",");
                                            if (split3 != null) {
                                                int length3 = split3.length / i10;
                                                ArrayList arrayList = new ArrayList();
                                                int i14 = 0;
                                                while (i14 < length3) {
                                                    try {
                                                        int i15 = i14 * 2;
                                                        i7 = length3;
                                                        try {
                                                            jSONArray3 = optJSONArray;
                                                            try {
                                                                i6 = length;
                                                                i5 = i9;
                                                                try {
                                                                    arrayList.add(new GeoPoint(Double.parseDouble(split3[i15]), Double.parseDouble(split3[i15 + 1])));
                                                                } catch (NumberFormatException e2) {
                                                                    e = e2;
                                                                }
                                                            } catch (NumberFormatException e3) {
                                                                e = e3;
                                                                i6 = length;
                                                                i5 = i9;
                                                                kf.a((Throwable) e);
                                                                i14++;
                                                                length3 = i7;
                                                                optJSONArray = jSONArray3;
                                                                length = i6;
                                                                i9 = i5;
                                                            }
                                                        } catch (NumberFormatException e4) {
                                                            e = e4;
                                                            jSONArray3 = optJSONArray;
                                                            i6 = length;
                                                            i5 = i9;
                                                            kf.a((Throwable) e);
                                                            i14++;
                                                            length3 = i7;
                                                            optJSONArray = jSONArray3;
                                                            length = i6;
                                                            i9 = i5;
                                                        }
                                                    } catch (NumberFormatException e5) {
                                                        e = e5;
                                                        jSONArray3 = optJSONArray;
                                                        i6 = length;
                                                        i5 = i9;
                                                        i7 = length3;
                                                        kf.a((Throwable) e);
                                                        i14++;
                                                        length3 = i7;
                                                        optJSONArray = jSONArray3;
                                                        length = i6;
                                                        i9 = i5;
                                                    }
                                                    i14++;
                                                    length3 = i7;
                                                    optJSONArray = jSONArray3;
                                                    length = i6;
                                                    i9 = i5;
                                                }
                                                jSONArray2 = optJSONArray;
                                                i4 = length;
                                                i3 = i9;
                                                div.q.add(arrayList);
                                            } else {
                                                jSONArray2 = optJSONArray;
                                                i4 = length;
                                                i3 = i9;
                                            }
                                            i13++;
                                            optJSONArray = jSONArray2;
                                            length = i4;
                                            i9 = i3;
                                            i10 = 2;
                                        }
                                        jSONArray = optJSONArray;
                                        i2 = length;
                                        i = i9;
                                    }
                                    JSONArray optJSONArray2 = optJSONObject.optJSONArray("time_list");
                                    if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                                        int length4 = optJSONArray2.length();
                                        div.r = new ArrayList();
                                        for (int i16 = 0; i16 < length4; i16++) {
                                            a aVar = new a();
                                            JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i16);
                                            aVar.a = optJSONObject2.optString("time");
                                            aVar.b = optJSONObject2.optString("total");
                                            div.r.add(aVar);
                                        }
                                    }
                                    this.a.a.add(div);
                                    i9 = i + 1;
                                    optJSONArray = jSONArray;
                                    length = i2;
                                    i8 = 1;
                                }
                            }
                        } catch (NumberFormatException e6) {
                            jSONArray = optJSONArray;
                            i2 = length;
                            i = i9;
                            e6.printStackTrace();
                        }
                    }
                    jSONArray = optJSONArray;
                    i2 = length;
                    i = i9;
                    i9 = i + 1;
                    optJSONArray = jSONArray;
                    length = i2;
                    i8 = 1;
                }
            }
            JSONArray optJSONArray3 = jSONObject.optJSONArray("data_source");
            if (optJSONArray3 != null && optJSONArray3.length() > 0) {
                for (int i17 = 0; i17 < optJSONArray3.length(); i17++) {
                    JSONObject optJSONObject3 = optJSONArray3.optJSONObject(i17);
                    String optString3 = optJSONObject3.optString("name");
                    String optString4 = optJSONObject3.optString("url");
                    if (!TextUtils.isEmpty(optString3) && !TextUtils.isEmpty(optString4)) {
                        this.a.d.add(optString3);
                        this.a.e.add(optString4);
                    }
                }
            }
            JSONArray optJSONArray4 = jSONObject.optJSONArray("data_source_list");
            if (optJSONArray4 != null && optJSONArray4.length() > 0) {
                for (int i18 = 0; i18 < optJSONArray4.length(); i18++) {
                    JSONObject optJSONObject4 = optJSONArray4.optJSONObject(i18);
                    String optString5 = optJSONObject4.optString("name");
                    String optString6 = optJSONObject4.optString("url");
                    if (!TextUtils.isEmpty(optString5) && !TextUtils.isEmpty(optString6)) {
                        this.a.f.add(optString5);
                        this.a.g.add(optString6);
                    }
                }
            }
        }
    }
}
