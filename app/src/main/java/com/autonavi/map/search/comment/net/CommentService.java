package com.autonavi.map.search.comment.net;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.comment.CommentRequestHolder;
import com.autonavi.minimap.comment.param.CommentCreateRequest;
import com.autonavi.minimap.comment.param.CommentDeleteRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentService {
    private static final String a = "CommentService";

    static class InnerCommentServiceCallback implements AosResponseCallback<AosByteResponse> {
        /* access modifiers changed from: private */
        public cag<bwp> a;

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            final bwp bwp;
            AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
            if (aosByteResponse != null && aosByteResponse.getResult() != null) {
                try {
                    JSONObject jSONObject = new JSONObject(new String((byte[]) aosByteResponse.getResult()));
                    bwp = new bwp();
                    try {
                        bwp.a = jSONObject.getBoolean("result");
                        bwp.b = jSONObject.getInt("code");
                        bwp.c = jSONObject.getString("message");
                        bwp.d = jSONObject.getString("timestamp");
                        bwp.e = jSONObject.getString("version");
                        if (bwp.a && jSONObject.has("comment_id")) {
                            bwp.f = jSONObject.getLong("comment_id");
                        }
                    } catch (JSONException unused) {
                    }
                } catch (JSONException unused2) {
                    bwp = null;
                }
                aho.a(new Runnable() {
                    public final void run() {
                        if (InnerCommentServiceCallback.this.a != null) {
                            InnerCommentServiceCallback.this.a.a(bwp);
                        }
                    }
                });
            }
        }

        InnerCommentServiceCallback(cag<bwp> cag) {
            this.a = cag;
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            aho.a(new Runnable() {
                public final void run() {
                    if (InnerCommentServiceCallback.this.a != null) {
                        InnerCommentServiceCallback.this.a.a();
                    }
                }
            });
        }
    }

    public static void a(CommentCreateRequest commentCreateRequest, cag<bwp> cag) {
        CommentRequestHolder.getInstance().sendCommentCreate(commentCreateRequest, new InnerCommentServiceCallback(cag));
    }

    public static void a(CommentDeleteRequest commentDeleteRequest) {
        CommentRequestHolder.getInstance().sendCommentDelete(commentDeleteRequest, new InnerCommentServiceCallback(null));
    }
}
