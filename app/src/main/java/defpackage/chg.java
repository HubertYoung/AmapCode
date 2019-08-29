package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: chg reason: default package */
/* compiled from: Parser */
public final class chg {
    public static ArrayList<chi> a(@NonNull JSONObject jSONObject) {
        ArrayList<chi> arrayList = new ArrayList<>();
        if (jSONObject == null) {
            return arrayList;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("ad");
        if (optJSONArray == null || optJSONArray.length() <= 0) {
            return arrayList;
        }
        String optString = jSONObject.optString("session_id");
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                JSONArray optJSONArray2 = optJSONObject.optJSONArray("creative");
                if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i2);
                        if (optJSONObject2 != null) {
                            chi chi = new chi();
                            String optString2 = optJSONObject2.optString("cid");
                            if (!TextUtils.isEmpty(optString2)) {
                                chi.e = optString2;
                                String optString3 = optJSONObject2.optString(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME);
                                if (!TextUtils.isEmpty(optString3)) {
                                    chi.b = Integer.parseInt(optString3);
                                    String optString4 = optJSONObject2.optString(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME);
                                    if (!TextUtils.isEmpty(optString4)) {
                                        chi.c = Integer.parseInt(optString4);
                                        JSONObject optJSONObject3 = optJSONObject2.optJSONObject("media");
                                        if (optJSONObject3 != null) {
                                            String optString5 = optJSONObject3.optString("creative_type");
                                            if (!TextUtils.isEmpty(optString5)) {
                                                chi.f = optString5;
                                                String optString6 = optJSONObject3.optString("display_time");
                                                if (!TextUtils.isEmpty(optString6)) {
                                                    chi.k = (long) (Integer.parseInt(optString6) * 1000);
                                                    String optString7 = optJSONObject3.optString("click_type");
                                                    if (!TextUtils.isEmpty(optString7)) {
                                                        chi.p = optString7;
                                                        String optString8 = optJSONObject3.optString("click_url");
                                                        if (!TextUtils.isEmpty(optString7)) {
                                                            chi.q = optString8;
                                                        }
                                                        String optString9 = optJSONObject3.optString("click_url_type");
                                                        if ("0".equals(optString9)) {
                                                            chi.r = false;
                                                        } else if ("1".equals(optString9)) {
                                                            chi.r = true;
                                                        }
                                                        String optString10 = optJSONObject3.optString("is_ad");
                                                        if ("0".equals(optString10)) {
                                                            chi.a = false;
                                                        } else if ("1".equals(optString10)) {
                                                            chi.a = true;
                                                        }
                                                        String optString11 = optJSONObject3.optString("display_type");
                                                        if (!TextUtils.isEmpty(optString11)) {
                                                            chi.l = optString11;
                                                        }
                                                        String optString12 = optJSONObject3.optString("logo_type");
                                                        if (!TextUtils.isEmpty(optString12)) {
                                                            chi.m = optString12;
                                                        }
                                                        String optString13 = optJSONObject3.optString("button_type");
                                                        if ("0".equals(optString13)) {
                                                            chi.n = false;
                                                        } else if ("1".equals(optString13)) {
                                                            chi.n = true;
                                                        }
                                                        String optString14 = optJSONObject3.optString("button_text");
                                                        if (!TextUtils.isEmpty(optString14)) {
                                                            chi.o = optString14;
                                                        }
                                                        String optString15 = optJSONObject3.optString("static_img_url");
                                                        if (!TextUtils.isEmpty(optString15)) {
                                                            chi.j = optString15;
                                                        }
                                                        String optString16 = optJSONObject3.optString("dynamic_img_url");
                                                        if (!TextUtils.isEmpty(optString16)) {
                                                            chi.i = optString16;
                                                        }
                                                        String optString17 = optJSONObject3.optString("video_url");
                                                        if (!TextUtils.isEmpty(optString17)) {
                                                            chi.g = optString17;
                                                        }
                                                        if ("0".equals(optJSONObject3.optString("display_sound"))) {
                                                            chi.h = false;
                                                        } else {
                                                            chi.h = true;
                                                        }
                                                        String optString18 = optJSONObject3.optString("padding");
                                                        if (!TextUtils.isEmpty(optString18)) {
                                                            chi.s = optString18;
                                                        }
                                                        ArrayList<String> arrayList2 = new ArrayList<>();
                                                        JSONArray optJSONArray3 = optJSONObject2.optJSONArray("impression");
                                                        if (optJSONArray3 != null && optJSONArray3.length() > 0) {
                                                            for (int i3 = 0; i3 < optJSONArray3.length(); i3++) {
                                                                String optString19 = optJSONArray3.optString(i3);
                                                                if (!TextUtils.isEmpty(optString19)) {
                                                                    arrayList2.add(optString19);
                                                                }
                                                            }
                                                            chi.t = arrayList2;
                                                        }
                                                        ArrayList<String> arrayList3 = new ArrayList<>();
                                                        JSONArray optJSONArray4 = optJSONObject2.optJSONArray("click");
                                                        if (optJSONArray4 != null && optJSONArray4.length() > 0) {
                                                            for (int i4 = 0; i4 < optJSONArray4.length(); i4++) {
                                                                String optString20 = optJSONArray4.optString(i4);
                                                                if (!TextUtils.isEmpty(optString20)) {
                                                                    arrayList3.add(optString20);
                                                                }
                                                            }
                                                            chi.u = arrayList3;
                                                        }
                                                        ArrayList<String> arrayList4 = new ArrayList<>();
                                                        JSONArray optJSONArray5 = optJSONObject2.optJSONArray(LogConstant.SPLASH_SCREEN_DOWNLOADED);
                                                        if (optJSONArray5 != null && optJSONArray5.length() > 0) {
                                                            for (int i5 = 0; i5 < optJSONArray4.length(); i5++) {
                                                                String optString21 = optJSONArray5.optString(i5);
                                                                if (!TextUtils.isEmpty(optString21)) {
                                                                    arrayList4.add(optString21);
                                                                }
                                                            }
                                                            chi.v = arrayList4;
                                                        }
                                                        chi.d = optString;
                                                        arrayList.add(chi);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }
}
