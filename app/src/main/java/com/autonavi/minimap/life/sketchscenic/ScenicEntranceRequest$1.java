package com.autonavi.minimap.life.sketchscenic;

import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.life.api.entity.ScenicGuideEntity;
import com.autonavi.bundle.life.api.entity.ScenicPlayEntity;
import com.autonavi.bundle.life.api.entity.ScenicSpeakEntity;
import com.autonavi.map.search.data.DateEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class ScenicEntranceRequest$1 implements AosResponseCallback {
    final /* synthetic */ a a;
    final /* synthetic */ dqs b;

    public ScenicEntranceRequest$1(dqs dqs, a aVar) {
        this.b = dqs;
        this.a = aVar;
    }

    public void onSuccess(AosResponse aosResponse) {
        final ScenicSpeakEntity scenicSpeakEntity;
        try {
            JSONObject jSONObject = new JSONObject(aosResponse.getResponseBodyString());
            if (bno.a) {
                StringBuilder sb = new StringBuilder("native response: ");
                sb.append(jSONObject.toString());
                AMapLog.debug("infoservice.scenic", "hanyang", sb.toString());
            }
            if ("1".equals(jSONObject.optString("code"))) {
                try {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                    JSONObject jSONObject3 = jSONObject2.getJSONObject(DateEntity.DATETYPE_VIEWPOINT);
                    final ScenicEntranceEntity scenicEntranceEntity = new ScenicEntranceEntity();
                    scenicEntranceEntity.b = jSONObject3.optString(ActionConstant.SCHEMA);
                    scenicEntranceEntity.a = jSONObject3.optInt("showScenicEntrance");
                    scenicEntranceEntity.c = jSONObject3.optString("text");
                    scenicEntranceEntity.d = jSONObject3.optInt("effective_level");
                    JSONObject optJSONObject = jSONObject2.optJSONObject("scenic_guide");
                    final ScenicGuideEntity a2 = dqs.a(optJSONObject);
                    if (optJSONObject == null) {
                        scenicSpeakEntity = null;
                    } else {
                        ScenicSpeakEntity scenicSpeakEntity2 = new ScenicSpeakEntity();
                        JSONObject optJSONObject2 = optJSONObject.optJSONObject("voice_guide");
                        if (optJSONObject2 != null) {
                            scenicSpeakEntity2.a = optJSONObject2.optString("name");
                            scenicSpeakEntity2.b = optJSONObject2.optString("icon_type");
                            scenicSpeakEntity2.c = optJSONObject2.optString("widget_type");
                            scenicSpeakEntity2.d = optJSONObject2.optString(ActionConstant.SCHEMA);
                            scenicSpeakEntity2.e = optJSONObject2.optString("operation_schema");
                            scenicSpeakEntity2.f = optJSONObject2.optString("operation_text");
                            scenicSpeakEntity2.g = optJSONObject2.optInt("operation_duration");
                        }
                        scenicSpeakEntity = scenicSpeakEntity2;
                    }
                    final ScenicPlayEntity b2 = dqs.b(optJSONObject);
                    AnonymousClass1 r2 = new Runnable() {
                        public final void run() {
                            if (ScenicEntranceRequest$1.this.a != null) {
                                ScenicEntranceRequest$1.this.a.a(scenicEntranceEntity, a2, scenicSpeakEntity, b2);
                            }
                        }
                    };
                    aho.a(r2);
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (this.a != null) {
                        this.a.a(null, null, null, null);
                    }
                }
            }
        } catch (Exception unused) {
            if (this.a != null) {
                this.a.a(null, null, null, null);
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                if (ScenicEntranceRequest$1.this.a != null) {
                    ScenicEntranceRequest$1.this.a.a(null, null, null, null);
                }
            }
        });
        if (bno.a) {
            StringBuilder sb = new StringBuilder("native e.getMessage(): ");
            sb.append(aosResponseException.getMessage());
            AMapLog.debug("infoservice.scenic", "hanyang", sb.toString());
        }
    }
}
