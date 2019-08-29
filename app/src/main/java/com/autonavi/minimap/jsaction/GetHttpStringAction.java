package com.autonavi.minimap.jsaction;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.widget.ProgressDlg;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class GetHttpStringAction extends vz {
    private ProgressDlg a;

    class HttpStringCallback implements AosResponseCallbackOnUi<AosStringResponse> {
        private final wa b;

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            GetHttpStringAction.this.b();
            JSONObject jSONObject = new JSONObject();
            String str = (String) ((AosStringResponse) aosResponse).getResult();
            try {
                jSONObject.put("_action", this.b.b);
                jSONObject.put("content", str);
                JsAdapter a2 = GetHttpStringAction.this.a();
                if (a2 != null) {
                    a2.callJs(this.b.a, jSONObject.toString());
                }
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }

        public HttpStringCallback(wa waVar) {
            this.b = waVar;
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            GetHttpStringAction.this.b();
        }
    }

    private static String a(JSONObject jSONObject, String str) {
        try {
            if (jSONObject.has(str)) {
                return jSONObject.getString(str);
            }
            return "";
        } catch (Exception e) {
            kf.a((Throwable) e);
            return "";
        }
    }

    public final void b() {
        if (this.a != null) {
            this.a.dismiss();
            this.a = null;
        }
    }

    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a2 = a();
        if (a2 != null) {
            String a3 = a(jSONObject, (String) "url");
            String a4 = a(jSONObject, (String) "progress");
            if (!a3.equals("")) {
                final AosGetRequest aosGetRequest = new AosGetRequest();
                aosGetRequest.setUrl(a3);
                aosGetRequest.setWithoutSign(true);
                aosGetRequest.setCommonParamStrategy(-1);
                ArrayList arrayList = new ArrayList();
                arrayList.add(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT);
                aosGetRequest.setDisabledCommonParams(arrayList);
                yq.a();
                yq.a((AosRequest) aosGetRequest, (AosResponseCallback<T>) new HttpStringCallback<T>(waVar));
                if (a4 != null && !"".equals(a4)) {
                    a2.mPageContext.getActivity();
                    this.a = new ProgressDlg(AMapAppGlobal.getTopActivity(), a4);
                    this.a.setOnCancelListener(new OnCancelListener() {
                        public final void onCancel(DialogInterface dialogInterface) {
                            if (aosGetRequest != null) {
                                aosGetRequest.cancel();
                            }
                        }
                    });
                    this.a.show();
                }
            }
        }
    }
}
