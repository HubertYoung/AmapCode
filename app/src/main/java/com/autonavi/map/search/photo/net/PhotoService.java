package com.autonavi.map.search.photo.net;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.comment.CommentRequestHolder;
import com.autonavi.minimap.comment.param.ImgUploadRequest;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PhotoService {
    private static final String a = "PhotoService";

    static class InnerCommentServiceCallback implements AosResponseCallback<AosByteResponse> {
        /* access modifiers changed from: private */
        public cag<caa> a;

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            final caa caa;
            AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
            if (aosByteResponse == null || aosByteResponse.getResult() == null) {
                onFailure(null, new AosResponseException((String) ""));
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(new String((byte[]) aosByteResponse.getResult()));
                caa = new caa();
                try {
                    caa.a = jSONObject.optBoolean("result");
                    caa.b = jSONObject.optInt("code");
                    caa.c = jSONObject.optString("message");
                    caa.d = jSONObject.optString("timestamp");
                    caa.e = jSONObject.optString("version");
                    caa.f = jSONObject.optInt("img_count");
                    JSONArray optJSONArray = jSONObject.optJSONArray("img");
                    if (optJSONArray != null) {
                        ArrayList<cal> arrayList = new ArrayList<>();
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            try {
                                JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                                if (jSONObject2 != null) {
                                    cal cal = new cal();
                                    cal.a = i + 1;
                                    cal.b = "";
                                    cal.c = "";
                                    cal.e = jSONObject2.optString("img_url", "");
                                    arrayList.add(cal);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        caa.g = arrayList;
                    }
                } catch (JSONException unused) {
                }
            } catch (JSONException unused2) {
                caa = null;
            }
            aho.a(new Runnable() {
                public final void run() {
                    if (InnerCommentServiceCallback.this.a != null) {
                        InnerCommentServiceCallback.this.a.a(caa);
                    }
                }
            });
        }

        InnerCommentServiceCallback(cag<caa> cag) {
            this.a = cag;
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            if (this.a != null) {
                this.a.a();
            }
        }
    }

    public static void a(ImgUploadRequest imgUploadRequest, cag<caa> cag) {
        CommentRequestHolder.getInstance().sendImgUpload(imgUploadRequest, new InnerCommentServiceCallback(cag));
    }
}
