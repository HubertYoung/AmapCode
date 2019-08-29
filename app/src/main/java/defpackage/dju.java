package defpackage;

import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dju reason: default package */
/* compiled from: TrafficTopBoardParser */
public final class dju {
    private djv a = new djv();

    public final djv a(JSONObject jSONObject) {
        try {
            JSONObject optJSONObject = jSONObject.optJSONObject("traffic").optJSONObject(ModuleLongLinkService.CALLBACK_KEY_RESPONSE);
            JSONObject optJSONObject2 = optJSONObject.optJSONObject("curcity");
            JSONObject optJSONObject3 = optJSONObject.optJSONObject("config");
            JSONArray optJSONArray = optJSONObject.optJSONArray("data");
            if (optJSONObject3 != null) {
                this.a.b = optJSONObject3.optString("md5");
                JSONArray optJSONArray2 = optJSONObject3.optJSONArray("citylist");
                JSONArray optJSONArray3 = optJSONObject3.optJSONArray("labltlist");
                djv djv = this.a;
                ArrayList<a> arrayList = new ArrayList<>();
                int length = optJSONArray2.length();
                for (int i = 0; i < length; i++) {
                    a aVar = new a();
                    JSONObject jSONObject2 = optJSONArray2.getJSONObject(i);
                    aVar.a = jSONObject2.optString("code");
                    aVar.b = jSONObject2.optString("name");
                    aVar.c = jSONObject2.optString("sharetxt");
                    aVar.d = jSONObject2.optString(SocialConstants.PARAM_SHARE_URL);
                    arrayList.add(aVar);
                }
                djv.f = arrayList;
                this.a.g = a(optJSONArray3);
            }
            if (optJSONObject2 != null) {
                djv djv2 = this.a;
                a aVar2 = new a();
                aVar2.c = optJSONObject2.optString("sharetxt");
                aVar2.a = optJSONObject2.optString("code");
                aVar2.b = optJSONObject2.optString("name");
                aVar2.d = optJSONObject2.optString(SocialConstants.PARAM_SHARE_URL);
                djv2.e = aVar2;
            }
            if (optJSONArray != null) {
                this.a.h = b(optJSONArray);
            }
            this.a.a = optJSONObject.optString("status");
            if (optJSONObject.has("errormsg")) {
                this.a.d = optJSONObject.optString("errormsg");
            }
            if (optJSONObject.has("sharetxt")) {
                this.a.c = optJSONObject.optString("sharetxt");
            }
            return this.a;
        } catch (JSONException e) {
            kf.a((Throwable) e);
            return this.a;
        } catch (Exception e2) {
            kf.a((Throwable) e2);
            return this.a;
        }
    }

    private static ArrayList<d> a(JSONArray jSONArray) throws JSONException {
        ArrayList<d> arrayList = new ArrayList<>();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            d dVar = new d();
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            dVar.a = jSONObject.optString("index");
            dVar.b = jSONObject.optString("name");
            arrayList.add(dVar);
        }
        return arrayList;
    }

    private static ArrayList<b> b(JSONArray jSONArray) throws JSONException {
        int length = jSONArray.length();
        ArrayList<b> arrayList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            b bVar = new b();
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            bVar.a = jSONObject.optString("index");
            bVar.b = c(jSONObject.getJSONArray("list"));
            if (bVar.b.size() > 0) {
                arrayList.add(bVar);
            }
        }
        return arrayList;
    }

    private static ArrayList<c> c(JSONArray jSONArray) throws JSONException {
        int length = jSONArray.length();
        ArrayList<c> arrayList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            c cVar = new c();
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            cVar.a = jSONObject.optString(BQCCameraParam.VALUE_NO);
            cVar.b = jSONObject.optString("road");
            cVar.c = jSONObject.optString("segment");
            cVar.d = jSONObject.optDouble("index");
            cVar.e = jSONObject.optString("ishow");
            cVar.f = jSONObject.optString("mile");
            cVar.g = jSONObject.optString("ptime");
            cVar.h = jSONObject.optString("dtime");
            cVar.i = jSONObject.optString("speed");
            cVar.j = jSONObject.optString("cars");
            cVar.k = jSONObject.optString("accident");
            cVar.l = jSONObject.optString("rclose");
            arrayList.add(cVar);
        }
        return arrayList;
    }
}
