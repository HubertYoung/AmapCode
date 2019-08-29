package defpackage;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.constants.AppLinkConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: asx reason: default package */
/* compiled from: PoiSelectorController */
public final class asx {
    public String a;
    public asv b;
    public bid c;

    /* renamed from: asx$a */
    /* compiled from: PoiSelectorController */
    public static class a {
        public final bid a;
        public String b;
        public int c;
        public int d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String i;
        public String j;
        public int k;
        public String l;
        public String m;
        public int n;
        public String o;
        public String p;
        public int q;
        public asv r;

        public a(bid bid) {
            this.a = bid;
        }

        public final String a() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("token", this.d);
                jSONObject.put("source", this.b);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.putOpt(AppLinkConstants.REQUESTCODE, Integer.valueOf(this.c));
                jSONObject.putOpt("channelArgs", jSONObject2);
                if (!TextUtils.isEmpty(this.e)) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("poiName", this.e);
                    jSONObject3.put("title", this.f);
                    jSONObject3.put("index", 0);
                    jSONObject.put("startPoi", jSONObject3);
                }
                if (!TextUtils.isEmpty(this.g)) {
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("poiName", this.g);
                    jSONObject4.put("title", this.h);
                    jSONObject4.put("index", 0);
                    jSONObject.put("endPoi", jSONObject4);
                }
                a(jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            return jSONObject.toString();
        }

        private void a(JSONObject jSONObject) {
            String[] strArr = {this.i, this.l, this.o};
            String[] strArr2 = {this.j, this.m, this.p};
            int[] iArr = {this.k, this.n, this.q};
            JSONArray jSONArray = new JSONArray();
            int i2 = 0;
            for (int i3 = 0; i3 < 3; i3++) {
                if (!TextUtils.isEmpty(strArr[i3])) {
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("poiName", strArr[i3]);
                        jSONObject2.put("title", strArr2[i3]);
                        jSONObject2.put("index", iArr[i3]);
                        jSONArray.put(i2, jSONObject2);
                        i2++;
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            try {
                jSONObject.put("midPoi", jSONArray);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
    }

    public asx(bid bid) {
        this.c = bid;
    }
}
