package com.autonavi.common.js.action;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AosrequestAction extends vz {

    public static class JsAosListener implements AosResponseCallbackOnUi<AosByteResponse> {
        public int a = 0;
        String b;
        private final wa c;
        private JSONObject d;
        private String e = null;
        private String f = null;
        private int g = 0;
        private int h = 0;
        /* access modifiers changed from: private */
        public ProgressDlg i;
        private WeakReference<bid> j;
        private final wm k;

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
            a();
            bid bid = this.j != null ? (bid) this.j.get() : null;
            if (bid != null && bid.isAlive()) {
                this.a = 1;
                this.d = new JSONObject();
                bkn bkn = new bkn();
                try {
                    bkn.parser((byte[]) aosByteResponse.getResult());
                } catch (UnsupportedEncodingException e2) {
                    AMapLog.d("AosrequestAction", e2.getLocalizedMessage());
                } catch (JSONException e3) {
                    AMapLog.d("AosrequestAction", e3.getLocalizedMessage());
                }
                this.b = bkn.a;
                try {
                    this.d.put("_action", this.c.b);
                    this.d.put("content", this.b);
                } catch (JSONException e4) {
                    kf.a((Throwable) e4);
                }
                a(this.c.a, this.d.toString());
                if (bkn.errorCode == 1) {
                    if (this.e != null && !"".equals(this.e)) {
                        ToastHelper.showToast(this.e);
                        if (this.g > 0) {
                            this.k.a(-this.g);
                        }
                    }
                } else if (this.h == 1) {
                    ToastHelper.showToast(bkn.errorMessage);
                } else if (!TextUtils.isEmpty(this.f)) {
                    ToastHelper.showToast(this.f);
                }
            }
        }

        public JsAosListener(wa waVar, String str, String str2, int i2, int i3, wm wmVar, bid bid) {
            this.c = waVar;
            this.j = new WeakReference<>(bid);
            this.e = str;
            this.f = str2;
            this.g = i2;
            this.h = i3;
            this.k = wmVar;
        }

        public final void a(String str, final AosRequest aosRequest) {
            if (this.i == null) {
                this.i = new ProgressDlg(AMapAppGlobal.getTopActivity(), str);
            }
            this.i.setCanceledOnTouchOutside(false);
            this.i.setOnCancelListener(new OnCancelListener() {
                public final void onCancel(DialogInterface dialogInterface) {
                    if (aosRequest != null) {
                        yq.a();
                        yq.a(aosRequest);
                    }
                }
            });
            this.i.getLoadingView().setOnCloseClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (aosRequest != null && JsAosListener.this.i != null) {
                        yq.a();
                        yq.a(aosRequest);
                        JsAosListener.this.i.dismiss();
                    }
                }
            });
            this.i.show();
        }

        private void a() {
            if (this.i != null) {
                this.i.dismiss();
            }
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            a();
            this.a = -1;
            JSONObject jSONObject = new JSONObject();
            this.d = new JSONObject();
            try {
                if (!NetworkReachability.b()) {
                    jSONObject.put("code", "-1");
                } else {
                    jSONObject.put("code", "-2");
                }
                this.d.put("_action", this.c.b);
                this.d.put("content", jSONObject.toString());
                a(this.c.a, this.d.toString());
            } catch (Exception unused) {
            }
            a();
        }

        private void a(String str, String str2) {
            this.k.a(str, str2);
        }
    }

    public final void a(JSONObject jSONObject, wa waVar) {
        int i;
        String str;
        String str2;
        String str3;
        int i2;
        JSONArray jSONArray;
        JSONObject jSONObject2;
        JSONObject jSONObject3 = jSONObject;
        JsAdapter a = a();
        if (a != null && a.mPageContext != null && a.mPageContext.isAlive()) {
            try {
                String optString = jSONObject3.optString("urlPrefix");
                if (optString != null) {
                    if (!"".equals(optString)) {
                        if (!optString.contains("?")) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(optString);
                            sb.append("?");
                            optString = sb.toString();
                        }
                        String upperCase = jSONObject3.optString("method").toUpperCase(Locale.US);
                        jSONObject3.optInt(DriveUtil.SCHEME_PARAM_ENCRYPT);
                        String optString2 = jSONObject3.optString("progress");
                        int optInt = jSONObject3.optInt("goback");
                        JSONObject optJSONObject = jSONObject3.optJSONObject("alert");
                        if (optJSONObject != null) {
                            String optString3 = optJSONObject.optString("success");
                            String optString4 = optJSONObject.optString(UploadDataResult.FAIL_MSG);
                            i = optJSONObject.optInt("admin");
                            str2 = optString3;
                            str = optString4;
                        } else {
                            str2 = null;
                            str = null;
                            i = 0;
                        }
                        JSONArray optJSONArray = jSONObject3.optJSONArray("params");
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(new abg("appkey", "21799508"));
                        if (optJSONArray != null && optJSONArray.length() > 0) {
                            int length = optJSONArray.length();
                            int i3 = 0;
                            while (i3 < length) {
                                JSONObject optJSONObject2 = optJSONArray.optJSONObject(i3);
                                if (optJSONObject2 != null) {
                                    jSONArray = optJSONArray;
                                    int optInt2 = optJSONObject2.optInt("sign");
                                    i2 = length;
                                    Iterator<String> keys = optJSONObject2.keys();
                                    while (keys.hasNext()) {
                                        Iterator<String> it = keys;
                                        String next = keys.next();
                                        String str4 = optString;
                                        if (!"sign".equals(next)) {
                                            jSONObject2 = optJSONObject2;
                                            abg abg = new abg(next, optJSONObject2.optString(next));
                                            if (optInt2 == 1) {
                                                arrayList.add(next);
                                            }
                                            arrayList2.add(abg);
                                        } else {
                                            jSONObject2 = optJSONObject2;
                                        }
                                        keys = it;
                                        optString = str4;
                                        optJSONObject2 = jSONObject2;
                                    }
                                    str3 = optString;
                                } else {
                                    str3 = optString;
                                    jSONArray = optJSONArray;
                                    i2 = length;
                                }
                                i3++;
                                optJSONArray = jSONArray;
                                length = i2;
                                optString = str3;
                            }
                        }
                        String str5 = optString;
                        ArrayList arrayList3 = arrayList2;
                        ArrayList arrayList4 = arrayList;
                        JsAosListener jsAosListener = new JsAosListener(waVar, str2, str, optInt, i, a.mBaseWebView, a.mPageContext);
                        boolean z = jSONObject3.optInt("shield") == 1;
                        if ("GET".equals(upperCase)) {
                            a(str5, (List<String>) arrayList4, (List<abg>) arrayList3, optString2, jsAosListener);
                            return;
                        }
                        String str6 = str5;
                        if ("POST".equals(upperCase)) {
                            if (arrayList4.isEmpty() && z) {
                                arrayList4.add(LocationParams.PARA_COMMON_DIU);
                                arrayList4.add(LocationParams.PARA_COMMON_DIV);
                            }
                            a(str6, (List<String>) arrayList4, arrayList3, optString2, jsAosListener);
                        }
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    private static AosRequest a(String str, List<String> list, ArrayList<abg> arrayList, String str2, JsAosListener jsAosListener) {
        AosPostRequest aosPostRequest = new AosPostRequest();
        aosPostRequest.setUrl(str);
        Iterator<abg> it = arrayList.iterator();
        while (it.hasNext()) {
            abg next = it.next();
            aosPostRequest.addReqParam(next.a, next.b);
        }
        for (String addSignParam : list) {
            aosPostRequest.addSignParam(addSignParam);
        }
        if (str.contains("channel=")) {
            for (abg next2 : new aaz(str).a()) {
                if ("channel".equals(next2.a)) {
                    aosPostRequest.addCustomCommonParam("channel", next2.b);
                }
            }
        }
        yq.a();
        yq.a((AosRequest) aosPostRequest, (AosResponseCallback<T>) jsAosListener);
        if (str2 != null && !"".equals(str2)) {
            jsAosListener.a(str2, (AosRequest) aosPostRequest);
        }
        return aosPostRequest;
    }

    private static AosRequest a(String str, List<String> list, List<abg> list2, String str2, JsAosListener jsAosListener) {
        AosGetRequest aosGetRequest = new AosGetRequest();
        aosGetRequest.setUrl(str);
        for (abg next : list2) {
            aosGetRequest.addReqParam(next.a, next.b);
        }
        for (String addSignParam : list) {
            aosGetRequest.addSignParam(addSignParam);
        }
        yq.a();
        yq.a((AosRequest) aosGetRequest, (AosResponseCallback<T>) jsAosListener);
        if (str2 != null && !"".equals(str2) && jsAosListener.a == 0) {
            jsAosListener.a(str2, (AosRequest) aosGetRequest);
        }
        return aosGetRequest;
    }
}
