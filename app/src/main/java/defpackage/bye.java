package defpackage;

import android.text.TextUtils;
import com.alibaba.appmonitor.offline.TempEvent;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.feedback.ajx.ModuleFeedBack;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.search.imagepreview.page.ImageDetailPage;
import com.autonavi.map.search.imagepreview.page.ImageGridNodePage;
import com.autonavi.map.search.imagepreview.page.ImageGridNodeTabPage;
import com.autonavi.map.search.imagepreview.page.ImagePreviewPage;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bye reason: default package */
/* compiled from: ImagePreviewAction */
public class bye extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        PageBundle pageBundle = new PageBundle();
        try {
            boolean has = jSONObject.has("tagList");
            String optString = jSONObject.optString(TempEvent.TAG_MODULE);
            if (!TextUtils.isEmpty(optString)) {
                JSONObject optJSONObject = jSONObject.optJSONObject("poiInfo");
                if (optJSONObject == null) {
                    pageBundle.putBoolean("show_btn", false);
                } else {
                    can.a().b = optJSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, "");
                    can.a().c = optJSONObject.optString(LocationParams.PARA_FLP_AUTONAVI_LON, "");
                    can.a().d = optJSONObject.optString("lat", "");
                }
                can.a().e = jSONObject.optString("uploadTips", "");
                can.a().f = jSONObject.optString("link", "");
                pageBundle.putBoolean("show_btn", jSONObject.optInt("showUploadButton", 0) == 1);
                String optString2 = jSONObject.optString("type", "");
                pageBundle.putString("type", optString2);
                if (has) {
                    int optInt = jSONObject.optInt("tag", 0);
                    pageBundle.putInt("default_tag", optInt);
                    ArrayList arrayList = new ArrayList();
                    JSONArray optJSONArray = jSONObject.optJSONArray("tagList");
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                        if ("restaurant".equals(optJSONObject2.getString("tag_name"))) {
                            arrayList.add(0, a(optJSONObject2));
                        } else if ("menu".equals(optJSONObject2.getString("tag_name"))) {
                            arrayList.add(1, a(optJSONObject2));
                        }
                    }
                    pageBundle.putObject("key_tab_data", arrayList);
                    pageBundle.putObject("data", arrayList.get(optInt));
                    pageBundle.putBoolean("key_from_idq_plus", true);
                    pageBundle.putObject("POI", (POI) a().getBundle().getObject("POI"));
                    if (optString.equals("single")) {
                        pageBundle.putBoolean("fromJS", true);
                        pageBundle.putInt("jsindex", jSONObject.optInt("index", 0));
                        a().mPageContext.startPage(ImageDetailPage.class, pageBundle);
                        return;
                    }
                    if (optString.equals("list")) {
                        a().mPageContext.startPage(ImageGridNodeTabPage.class, pageBundle);
                        pageBundle.putInt("jsindex", -1);
                    }
                    return;
                }
                pageBundle.putObject("data", a(jSONObject));
                if (optString2.equals("feeddetail")) {
                    pageBundle.putBoolean("fromJS", true);
                    pageBundle.putInt("jsindex", jSONObject.optInt("index", 0));
                    a().mPageContext.startPage(ImagePreviewPage.class, pageBundle);
                } else if (optString.equals("single")) {
                    pageBundle.putBoolean("fromJS", true);
                    pageBundle.putInt("jsindex", jSONObject.optInt("index", 0));
                    a().mPageContext.startPage(ImageDetailPage.class, pageBundle);
                } else {
                    if (optString.equals("list")) {
                        a().mPageContext.startPage(ImageGridNodePage.class, pageBundle);
                        pageBundle.putInt("jsindex", -1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<cal> a(JSONObject jSONObject) {
        ArrayList<cal> arrayList = new ArrayList<>();
        if (!(jSONObject == null || jSONObject.optJSONArray("list") == null)) {
            JSONArray optJSONArray = jSONObject.optJSONArray("list");
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    try {
                        JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                        if (jSONObject2 != null) {
                            cal cal = new cal();
                            cal.a = i + 1;
                            cal.b = jSONObject2.optString("title", "");
                            cal.c = jSONObject2.optString("message", "");
                            cal.d = jSONObject2.optString(ModuleFeedBack.RECOMMEND, "");
                            cal.e = jSONObject2.optString("url", "");
                            cal.f = jSONObject2.optString("source");
                            arrayList.add(cal);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return arrayList;
    }
}
