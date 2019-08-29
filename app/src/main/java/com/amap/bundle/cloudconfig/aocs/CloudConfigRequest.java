package com.amap.bundle.cloudconfig.aocs;

import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.aocs.AocsRequestHolder;
import com.autonavi.minimap.aocs.param.UpdatableRequest;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class CloudConfigRequest {
    boolean a = false;
    a b;
    private UpdatableRequest c = null;
    private List<String> d = null;
    private String e;

    public enum ResultType {
        RESULT_SUCCESS(GenBusCodeService.CODE_SUCESS, 0),
        RESULT_NO_NETWORK("NO_NETWORK", 1),
        RESULT_NETWORK_ERROR("NETWORK_ERROR", 2),
        RESULT_SERVER_ERROR("SERVER_ERROR", 3),
        RESULT_PARSE_FAIL("PARSE_FAIL", 5),
        RESULT_CANCELED("CANCELED", 8);
        
        /* access modifiers changed from: private */
        public int mCode;
        /* access modifiers changed from: private */
        public String mMsg;

        private ResultType(String str, int i) {
            this.mMsg = str;
            this.mCode = i;
        }

        public final String getMsg() {
            return this.mMsg;
        }

        public final int getCode() {
            return this.mCode;
        }
    }

    public interface a {
        void a(int i, List<String> list);

        boolean a(ArrayList<lq> arrayList);
    }

    public interface b {
        String a(String str);
    }

    public CloudConfigRequest(String str, List<String> list) {
        this.e = str;
        this.d = list;
    }

    public final void a(b bVar, a aVar) {
        this.b = aVar;
        a(this.e, bVar);
    }

    public final void a() {
        if (this.c != null) {
            this.c.cancel();
            this.c = null;
        }
        a(ResultType.RESULT_CANCELED);
    }

    private void a(String str, b bVar) {
        defpackage.lo.a b2 = lo.b();
        if (b2 != null) {
            GeoPoint a2 = b2.a();
            String str2 = "";
            String str3 = "";
            if (a2 != null) {
                str2 = String.valueOf(a2.getLongitude());
                str3 = String.valueOf(a2.getLatitude());
            } else {
                GeoPoint b3 = b2.b();
                if (b3 != null) {
                    str2 = String.valueOf(b3.getLongitude());
                    str3 = String.valueOf(b3.getLatitude());
                }
            }
            UpdatableRequest updatableRequest = new UpdatableRequest();
            updatableRequest.b = str;
            updatableRequest.c = a(bVar);
            updatableRequest.e = str3;
            updatableRequest.d = str2;
            this.c = updatableRequest;
            if (bno.a) {
                StringBuilder sb = new StringBuilder("CloudConfigRequest update_mode:");
                sb.append(updatableRequest.b);
                sb.append(",module:");
                sb.append(updatableRequest.c);
                AMapLog.debug("paas.cloudconfig", "CloudConfigRequest", sb.toString());
            }
            AocsRequestHolder.getInstance().sendUpdatable(updatableRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
                public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                    AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                    if (!CloudConfigRequest.this.a) {
                        try {
                            final JSONObject aosByteResponseToJSONObject = AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
                            if (bno.a) {
                                StringBuilder sb = new StringBuilder("[ConfigTask] getConfig return ");
                                sb.append(aosByteResponseToJSONObject.toString());
                                AMapLog.d("CloudConfigRequest", sb.toString(), true);
                            }
                            aho.a(new Runnable() {
                                public final void run() {
                                    CloudConfigRequest cloudConfigRequest = CloudConfigRequest.this;
                                    JSONObject jSONObject = aosByteResponseToJSONObject;
                                    try {
                                        int i = jSONObject.getInt("code");
                                        if (i == 1) {
                                            ArrayList arrayList = new ArrayList();
                                            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                                            JSONArray names = jSONObject2.names();
                                            boolean z = false;
                                            if (names != null) {
                                                for (int i2 = 0; i2 < names.length(); i2++) {
                                                    String string = names.getString(i2);
                                                    lq lqVar = new lq(string, jSONObject2.optJSONObject(string));
                                                    arrayList.add(lqVar);
                                                    if (bno.a) {
                                                        AMapLog.debug("paas.cloudconfig", "CloudConfigRequest", lqVar.toString());
                                                    }
                                                }
                                            }
                                            if (cloudConfigRequest.b != null && arrayList.size() > 0) {
                                                z = cloudConfigRequest.b.a(arrayList);
                                            }
                                            cloudConfigRequest.a(z ? ResultType.RESULT_SUCCESS : ResultType.RESULT_PARSE_FAIL);
                                            return;
                                        }
                                        if (bno.a) {
                                            AMapLog.d("CloudConfigRequest", "[ConfigTask] return errorCode = ".concat(String.valueOf(i)), true);
                                        }
                                        cloudConfigRequest.a(ResultType.RESULT_SERVER_ERROR);
                                    } catch (Exception e) {
                                        if (bno.a) {
                                            StringBuilder sb = new StringBuilder("[ConfigTask] parseResult error. ");
                                            sb.append(e.toString());
                                            AMapLog.d("CloudConfigRequest", sb.toString(), true);
                                        }
                                        cloudConfigRequest.a(ResultType.RESULT_PARSE_FAIL);
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            a(e);
                        }
                    }
                }

                public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                    a(aosResponseException);
                }

                private void a(Exception exc) {
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder("[ConfigTask] ");
                        sb.append(exc.toString());
                        AMapLog.d("ConfigTask", sb.toString(), true);
                    }
                    aho.a(new Runnable() {
                        public final void run() {
                            CloudConfigRequest.this.a(ResultType.RESULT_NETWORK_ERROR);
                        }
                    });
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(ResultType resultType) {
        if (!this.a) {
            if (this.b != null) {
                this.b.a(resultType.getCode(), this.d);
            }
            if (bno.a) {
                StringBuilder sb = new StringBuilder("[ConfigTask] result code = ");
                sb.append(resultType.mCode);
                sb.append(", ");
                sb.append(resultType.mMsg);
                AMapLog.d("CloudConfigRequest", sb.toString(), true);
            }
            this.c = null;
            this.b = null;
            this.a = true;
        }
    }

    private String a(b bVar) {
        int size = this.d != null ? this.d.size() : 0;
        JSONObject jSONObject = new JSONObject();
        for (int i = 0; i < size; i++) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                String str = this.d.get(i);
                jSONObject2.put("version", bVar.a(str));
                jSONObject.put(str, jSONObject2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return jSONObject.toString();
    }
}
