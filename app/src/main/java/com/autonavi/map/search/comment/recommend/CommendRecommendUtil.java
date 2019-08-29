package com.autonavi.map.search.comment.recommend;

import android.text.TextUtils;
import com.alipay.mobile.beehive.audio.Constants;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class CommendRecommendUtil {
    private AosResponseCallback<AosByteResponse> a;

    @Path(builder = AosURLBuilder.class, host = "aos_ugc_comment_url", sign = {"diu"}, url = "ws/ugc/comment/user_foot_mini")
    public static class CommentRecommendParam implements ParamEntity {
        private String poi_id;

        public CommentRecommendParam(String str) {
            this.poi_id = str;
        }
    }

    public interface a<T> {
        void a(T t);
    }

    public static class b {
        public boolean a;
        public int b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public List<c> h;

        public final void a(JSONObject jSONObject) {
            this.a = jSONObject.optBoolean("result");
            this.b = jSONObject.optInt("code");
            this.c = jSONObject.optString("message");
            this.d = jSONObject.optString("timestamp");
            this.e = jSONObject.optString("version");
            this.f = jSONObject.optString("visited_num");
            this.g = jSONObject.optString("tag");
            JSONArray optJSONArray = jSONObject.optJSONArray("list");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                this.h = new ArrayList();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        c cVar = new c();
                        if (optJSONObject != null) {
                            cVar.a = optJSONObject.optString("name");
                            cVar.b = optJSONObject.optInt("gold_type");
                            cVar.c = optJSONObject.optInt("gold_num");
                            cVar.d = optJSONObject.optString("action_uri");
                            cVar.e = optJSONObject.optString("poi_id");
                            cVar.f = optJSONObject.optString(Constants.KEY_AUDIO_BUSINESS_ID);
                            cVar.g = optJSONObject.optString("gold_text");
                            cVar.h = optJSONObject.optString("pw_text");
                        }
                        this.h.add(cVar);
                    }
                }
            }
        }
    }

    public static class c {
        public String a;
        public int b;
        public int c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
    }

    public final void a(AosRequest aosRequest, final a<b> aVar) {
        this.a = new AosResponseCallback<AosByteResponse>() {
            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            }

            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                if (aosByteResponse.getResult() != null) {
                    String str = new String((byte[]) aosByteResponse.getResult());
                    if (!TextUtils.isEmpty(str)) {
                        try {
                            JSONObject jSONObject = new JSONObject(str);
                            final b bVar = new b();
                            bVar.a(jSONObject);
                            if (bVar.a && aVar != null) {
                                aho.a(new Runnable() {
                                    public final void run() {
                                        aVar.a(bVar);
                                    }
                                });
                            }
                        } catch (JSONException unused) {
                        }
                    }
                }
            }
        };
        yq.a();
        yq.a(aosRequest, this.a);
    }
}
