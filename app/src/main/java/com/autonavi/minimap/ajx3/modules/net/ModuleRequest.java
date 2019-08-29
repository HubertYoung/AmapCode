package com.autonavi.minimap.ajx3.modules.net;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.ae.ajx.tbt.CAjxBLBinaryCenter;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.inspect.OnRequestOpListener;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.FileUtil;
import com.autonavi.server.aos.serverkey;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("XMLHttpRequest")
public final class ModuleRequest extends AbstractModule {
    private static final String HEADER_PROTOCOL_BUFFERS_KEY = "g-rprotocol";
    private static final String PB_COMMON_RECOMMEND_SUFFIX = "/ws/valueadded/deepinfo/common_recommend";
    private static final String PB_DSP_REVIEW_LIST_SUFFIX = "/ws/shield/dsp/valueadded/deepinfo/review_list";
    private static final String PB_ERROR_MESSAGE = "{\"code\" : -1001, \"message\" :\"pb error\"}";
    private static final String PB_FAVORITE_PRICE_SUFFIX = "/ws/valueadded/discount/favourable_price";
    private static final String PB_MEDIA_RECOMMEND_SUFFIX = "/ws/mapapi/poi/media/recommand";
    private static final String PB_POIDETAIL_SUFFIX = "/ws/shield/dsp/h5/poi/detail/v2";
    private static final String PB_POILITE_SUFFIX = "/ws/valueadded/deepinfo/poilite";
    private static final String PB_RECOMMEND_SEARCH_SUFFIX = "/ws/mapapi/recommend/recommend_search";
    private static final String PB_SCENIC_AREA_INFO_DETAIL_SUFFIX = "/ws/shield/maps/valueadded/scenic_ggc/info_detail";
    private static final int READY_STATE_CONNECTED = 1;
    private static final int READY_STATE_FINISHED = 4;
    private static final int READY_STATE_RECEIVED = 3;
    private static final int READY_STATE_RECEIVEING = 2;
    private static final int READY_STATE_UNINIT = 0;
    private static final String TAG = "ModuleRequest";
    /* access modifiers changed from: private */
    public static OnRequestOpListener onRequestOpListener;
    /* access modifiers changed from: private */
    public final Map<String, RequestInfo> mRequestRecords = new HashMap();

    static class AjxBinaryCallback implements AosResponseCallbackOnUi<AosByteResponse> {
        private JsFunctionCallback callback;
        private final String reqID;
        private WeakReference<ModuleRequest> service;

        AjxBinaryCallback(ModuleRequest moduleRequest, @NonNull String str) {
            this.service = new WeakReference<>(moduleRequest);
            this.reqID = str;
        }

        /* access modifiers changed from: 0000 */
        public void setCallback(JsFunctionCallback jsFunctionCallback) {
            this.callback = jsFunctionCallback;
        }

        public void onSuccess(AosByteResponse aosByteResponse) {
            ModuleRequest moduleRequest = (ModuleRequest) this.service.get();
            if (moduleRequest != null) {
                long j = -1;
                if (aosByteResponse.getResponseBodyData() != null && aosByteResponse.getResponseBodyData().length > 0) {
                    j = (long) CAjxBLBinaryCenter.addBinaryDataS(aosByteResponse.getResponseBodyData());
                }
                long j2 = j;
                moduleRequest.notifyJs(this.callback, aosByteResponse.getStatusCode(), 4, j2, 0, "");
                moduleRequest.mRequestRecords.remove(this.reqID);
            }
            this.callback = null;
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            ModuleRequest moduleRequest = (ModuleRequest) this.service.get();
            if (moduleRequest != null) {
                ModuleRequest moduleRequest2 = moduleRequest;
                moduleRequest2.notifyJs(this.callback, moduleRequest.getStatusCode(aosRequest, aosResponseException), 4, -1, moduleRequest.getErrorCode(aosResponseException), moduleRequest.getErrorMsg(aosResponseException));
                moduleRequest.mRequestRecords.remove(this.reqID);
            }
            this.callback = null;
        }
    }

    static class AjxCallback implements AosResponseCallbackOnUi<AosStringResponse> {
        private JsFunctionCallback callback;
        private final String reqID;
        private WeakReference<ModuleRequest> service;

        AjxCallback(ModuleRequest moduleRequest, @NonNull String str) {
            this.service = new WeakReference<>(moduleRequest);
            this.reqID = str;
        }

        /* access modifiers changed from: 0000 */
        public void setCallback(JsFunctionCallback jsFunctionCallback) {
            this.callback = jsFunctionCallback;
        }

        public void onSuccess(AosStringResponse aosStringResponse) {
            String str;
            String access$300;
            JSONObject jSONObject;
            StringBuilder sb;
            ModuleRequest moduleRequest = (ModuleRequest) this.service.get();
            if (!(moduleRequest == null || aosStringResponse == null)) {
                Iterator<Entry<String, List<String>>> it = aosStringResponse.getHeaders().entrySet().iterator();
                HashMap hashMap = new HashMap();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Entry next = it.next();
                    if (next.getKey() != null) {
                        List list = (List) next.getValue();
                        if (list == null || list.size() <= 0) {
                            sb = null;
                        } else {
                            sb = null;
                            for (int i = 0; i < list.size(); i++) {
                                String str2 = (String) list.get(i);
                                if (str2 != null) {
                                    if (sb == null) {
                                        sb = new StringBuilder(str2);
                                    } else {
                                        sb.append(", ");
                                        sb.append(str2);
                                    }
                                }
                            }
                        }
                        hashMap.put(next.getKey(), sb != null ? sb.toString() : null);
                    }
                }
                StringBuilder sb2 = new StringBuilder("onSuccess:");
                sb2.append(aosStringResponse.getResponseBodyString());
                AMapLog.e("ajx", sb2.toString());
                JSONObject jSONObject2 = new JSONObject();
                Map<String, List<String>> headers = aosStringResponse.getHeaders();
                for (Entry next2 : headers.entrySet()) {
                    String str3 = (String) next2.getKey();
                    if (str3 != null) {
                        String str4 = "";
                        StringBuffer stringBuffer = new StringBuffer();
                        for (String append : (List) next2.getValue()) {
                            stringBuffer.append(append);
                            stringBuffer.append(",");
                        }
                        if (stringBuffer.length() > 0) {
                            str4 = stringBuffer.substring(0, stringBuffer.length() - 1);
                        }
                        try {
                            jSONObject2.put(str3, str4);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                RequestInfo requestInfo = (RequestInfo) moduleRequest.mRequestRecords.get(this.reqID);
                if (ModuleRequest.onRequestOpListener != null) {
                    if (requestInfo == null) {
                        access$300 = "";
                    } else {
                        access$300 = requestInfo.mUrlFromAjx;
                    }
                    String str5 = access$300;
                    if (requestInfo == null) {
                        jSONObject = null;
                    } else {
                        jSONObject = requestInfo.getHeadersAsJSONObj();
                    }
                    ModuleRequest.onRequestOpListener.onResponseReceived(this.reqID, str5, 200, "success", jSONObject, jSONObject2, "XHR", aosStringResponse.getResponseBodyString());
                }
                if (headers.get(ModuleRequest.HEADER_PROTOCOL_BUFFERS_KEY) != null) {
                    String access$3002 = requestInfo != null ? requestInfo.mUrlFromAjx : "";
                    try {
                        if (access$3002.contains(ModuleRequest.PB_POILITE_SUFFIX)) {
                            ce a = ce.a(aosStringResponse.getResponseBodyData());
                            JSONObject jSONObject3 = new JSONObject();
                            if (!TextUtils.isEmpty(a.a)) {
                                jSONObject3.put("code", a.a);
                            }
                            if (!TextUtils.isEmpty(a.b)) {
                                jSONObject3.put("timestamp", a.b);
                            }
                            if (!TextUtils.isEmpty(a.c)) {
                                jSONObject3.put("version", a.c);
                            }
                            if (!TextUtils.isEmpty(a.d)) {
                                jSONObject3.put("result", a.d);
                            }
                            if (a.e != null) {
                                jSONObject3.put("poiinfo", d.a(a.e));
                            }
                            if (!TextUtils.isEmpty(a.f)) {
                                jSONObject3.put("message", a.f);
                            }
                            str = jSONObject3.toString();
                        } else if (access$3002.contains(ModuleRequest.PB_POIDETAIL_SUFFIX)) {
                            be a2 = be.a(aosStringResponse.getResponseBodyData());
                            JSONObject jSONObject4 = new JSONObject();
                            jSONObject4.put("result", a2.a);
                            jSONObject4.put("code", a2.b);
                            if (!TextUtils.isEmpty(a2.c)) {
                                jSONObject4.put("message", a2.c);
                            }
                            if (!TextUtils.isEmpty(a2.e)) {
                                jSONObject4.put("version", a2.e);
                            }
                            if (a2.f != null) {
                                bf bfVar = a2.f;
                                JSONObject jSONObject5 = new JSONObject();
                                if (bfVar.a != null) {
                                    jSONObject5.put(ShowRouteActionProcessor.SEARCH_TYPE_BUS, b.a(bfVar.a));
                                }
                                if (bfVar.b != null) {
                                    jSONObject5.put("common_recommend", b.a(bfVar.b));
                                }
                                if (bfVar.c != null) {
                                    ao aoVar = bfVar.c;
                                    JSONObject jSONObject6 = new JSONObject();
                                    if (!TextUtils.isEmpty(aoVar.a)) {
                                        jSONObject6.put("result", aoVar.a);
                                    }
                                    if (!TextUtils.isEmpty(aoVar.b)) {
                                        jSONObject6.put("code", aoVar.b);
                                    }
                                    if (!TextUtils.isEmpty(aoVar.c)) {
                                        jSONObject6.put("message", aoVar.c);
                                    }
                                    if (!TextUtils.isEmpty(aoVar.d)) {
                                        jSONObject6.put("timestamp", aoVar.d);
                                    }
                                    if (!TextUtils.isEmpty(aoVar.e)) {
                                        jSONObject6.put("version", aoVar.e);
                                    }
                                    if (!TextUtils.isEmpty(aoVar.f)) {
                                        jSONObject6.put("modules_data_template", aoVar.f);
                                    }
                                    if (aoVar.g != null) {
                                        jSONObject6.put("modules_data", b.a(aoVar.g));
                                    }
                                    jSONObject5.put("deepinfo_infotemplate", jSONObject6);
                                }
                                if (bfVar.d != null) {
                                    jSONObject5.put("depart_info", b.a(bfVar.d));
                                }
                                if (bfVar.e != null) {
                                    jSONObject5.put("doctor_info", b.a(bfVar.e));
                                }
                                if (bfVar.f != null) {
                                    jSONObject5.put("education_scores", b.a(bfVar.f));
                                }
                                if (bfVar.g != null) {
                                    jSONObject5.put("favourable_price", b.a(bfVar.g));
                                }
                                if (bfVar.h != null) {
                                    ap apVar = bfVar.h;
                                    JSONObject jSONObject7 = new JSONObject();
                                    if (!TextUtils.isEmpty(apVar.a)) {
                                        jSONObject7.put("result", apVar.a);
                                    }
                                    if (!TextUtils.isEmpty(apVar.b)) {
                                        jSONObject7.put("code", apVar.b);
                                    }
                                    if (!TextUtils.isEmpty(apVar.c)) {
                                        jSONObject7.put("message", apVar.c);
                                    }
                                    if (!TextUtils.isEmpty(apVar.d)) {
                                        jSONObject7.put("timestamp", apVar.d);
                                    }
                                    if (!TextUtils.isEmpty(apVar.e)) {
                                        jSONObject7.put("version", apVar.e);
                                    }
                                    if (apVar.f != null) {
                                        aq aqVar = apVar.f;
                                        JSONObject jSONObject8 = new JSONObject();
                                        if (!TextUtils.isEmpty(aqVar.a)) {
                                            jSONObject8.put("data_type", aqVar.a);
                                        }
                                        if (!TextUtils.isEmpty(aqVar.b)) {
                                            jSONObject8.put("open_url", aqVar.b);
                                        }
                                        if (!TextUtils.isEmpty(aqVar.c)) {
                                            jSONObject8.put("message_url", aqVar.c);
                                        }
                                        jSONObject7.put("data", jSONObject8);
                                    }
                                    jSONObject5.put("lbp_check", jSONObject7);
                                }
                                if (bfVar.i != null) {
                                    jSONObject5.put("media_recommand", b.a(bfVar.i));
                                }
                                if (bfVar.j != null) {
                                    jSONObject5.put("poibusline", b.a(bfVar.j));
                                }
                                if (bfVar.k != null) {
                                    jSONObject5.put("relate_recommend", b.a(bfVar.k));
                                }
                                if (bfVar.l != null) {
                                    jSONObject5.put("review_list", b.a(bfVar.l));
                                }
                                if (bfVar.m != null) {
                                    jSONObject5.put("shop_activity", b.a(bfVar.m));
                                }
                                if (bfVar.n != null) {
                                    jSONObject5.put("spaces_list", b.a(bfVar.n));
                                }
                                if (bfVar.o != null) {
                                    jSONObject5.put("transbay_list", b.a(bfVar.o));
                                }
                                if (bfVar.p != null) {
                                    au auVar = bfVar.p;
                                    JSONObject jSONObject9 = new JSONObject();
                                    if (!TextUtils.isEmpty(auVar.a)) {
                                        jSONObject9.put("result", auVar.a);
                                    }
                                    if (!TextUtils.isEmpty(auVar.b)) {
                                        jSONObject9.put("code", auVar.b);
                                    }
                                    if (!TextUtils.isEmpty(auVar.c)) {
                                        jSONObject9.put("message", auVar.c);
                                    }
                                    if (!TextUtils.isEmpty(auVar.d)) {
                                        jSONObject9.put("timestamp", auVar.d);
                                    }
                                    if (!TextUtils.isEmpty(auVar.e)) {
                                        jSONObject9.put("version", auVar.e);
                                    }
                                    if (!TextUtils.isEmpty(auVar.f)) {
                                        jSONObject9.put("temperature", auVar.f);
                                    }
                                    if (!TextUtils.isEmpty(auVar.g)) {
                                        jSONObject9.put("image_url", auVar.g);
                                    }
                                    if (!TextUtils.isEmpty(auVar.h)) {
                                        jSONObject9.put("weather_icon_num", auVar.h);
                                    }
                                    if (!TextUtils.isEmpty(auVar.i)) {
                                        jSONObject9.put("weather_condition", auVar.i);
                                    }
                                    if (!TextUtils.isEmpty(auVar.j)) {
                                        jSONObject9.put("aqi_quality_level", auVar.j);
                                    }
                                    jSONObject5.put("weather", jSONObject9);
                                }
                                jSONObject4.put("data", jSONObject5);
                            }
                            str = jSONObject4.toString();
                        } else if (access$3002.contains(ModuleRequest.PB_DSP_REVIEW_LIST_SUFFIX)) {
                            str = b.a(bk.a(aosStringResponse.getResponseBodyData())).toString();
                        } else if (access$3002.contains(ModuleRequest.PB_MEDIA_RECOMMEND_SUFFIX)) {
                            str = b.a(at.a(aosStringResponse.getResponseBodyData())).toString();
                        } else if (access$3002.contains(ModuleRequest.PB_COMMON_RECOMMEND_SUFFIX)) {
                            str = b.a(g.a(aosStringResponse.getResponseBodyData())).toString();
                        } else if (access$3002.contains(ModuleRequest.PB_RECOMMEND_SEARCH_SUFFIX)) {
                            d a3 = d.a(aosStringResponse.getResponseBodyData());
                            JSONObject jSONObject10 = new JSONObject();
                            if (!TextUtils.isEmpty(a3.a)) {
                                jSONObject10.put("code", a3.a);
                            }
                            if (!TextUtils.isEmpty(a3.b)) {
                                jSONObject10.put("timestamp", a3.b);
                            }
                            if (!TextUtils.isEmpty(a3.c)) {
                                jSONObject10.put("version", a3.c);
                            }
                            if (!TextUtils.isEmpty(a3.d)) {
                                jSONObject10.put("result", a3.d);
                            }
                            if (!TextUtils.isEmpty(a3.e)) {
                                jSONObject10.put("message", a3.e);
                            }
                            if (a3.f != null) {
                                a aVar = a3.f;
                                JSONObject jSONObject11 = new JSONObject();
                                if (aVar.a != null) {
                                    b bVar = aVar.a;
                                    JSONObject jSONObject12 = new JSONObject();
                                    jSONObject12.put("status", bVar.a);
                                    if (bVar.b != null) {
                                        jSONObject12.put("toplist_detail", f.a(bVar.b));
                                    }
                                    if (!TextUtils.isEmpty(bVar.c)) {
                                        jSONObject12.put("back_args", bVar.c);
                                    }
                                    if (!TextUtils.isEmpty(bVar.d)) {
                                        jSONObject12.put("testid", bVar.d);
                                    }
                                    if (!TextUtils.isEmpty(bVar.e)) {
                                        jSONObject12.put("recommend_pos_id", bVar.e);
                                    }
                                    jSONObject11.put("detail_result", jSONObject12);
                                }
                                jSONObject10.put("data", jSONObject11);
                            }
                            str = jSONObject10.toString();
                        } else {
                            str = access$3002.contains(ModuleRequest.PB_FAVORITE_PRICE_SUFFIX) ? b.a(u.a(aosStringResponse.getResponseBodyData())).toString() : access$3002.contains(ModuleRequest.PB_SCENIC_AREA_INFO_DETAIL_SUFFIX) ? h.a(p.a(aosStringResponse.getResponseBodyData())).toString() : ModuleRequest.PB_ERROR_MESSAGE;
                        }
                    } catch (Exception unused) {
                        str = ModuleRequest.PB_ERROR_MESSAGE;
                    }
                } else {
                    str = aosStringResponse.getResponseBodyString();
                }
                moduleRequest.notifyJs(this.callback, aosStringResponse.getStatusCode(), 4, str, new JSONObject(hashMap).toString(), 0, "");
                if (ModuleRequest.onRequestOpListener != null) {
                    ModuleRequest.onRequestOpListener.onLoadingFinished(this.reqID, "XHR");
                }
                moduleRequest.mRequestRecords.remove(this.reqID);
            }
            this.callback = null;
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            String access$300;
            AosResponseException aosResponseException2 = aosResponseException;
            ModuleRequest moduleRequest = (ModuleRequest) this.service.get();
            if (moduleRequest != null) {
                int access$700 = moduleRequest.getStatusCode(aosRequest, aosResponseException2);
                int access$800 = moduleRequest.getErrorCode(aosResponseException2);
                String access$900 = moduleRequest.getErrorMsg(aosResponseException2);
                if (ModuleRequest.onRequestOpListener != null) {
                    RequestInfo requestInfo = (RequestInfo) moduleRequest.mRequestRecords.get(this.reqID);
                    if (requestInfo == null) {
                        access$300 = "";
                    } else {
                        access$300 = requestInfo.mUrlFromAjx;
                    }
                    ModuleRequest.onRequestOpListener.onResponseReceived(this.reqID, access$300, -1, "failure", null, null, "XHR", null);
                }
                moduleRequest.notifyJs(this.callback, access$700, 4, null, null, access$800, access$900);
                if (ModuleRequest.onRequestOpListener != null) {
                    ModuleRequest.onRequestOpListener.onLoadingFinished(this.reqID, "XHR");
                }
                moduleRequest.mRequestRecords.remove(this.reqID);
            }
            this.callback = null;
        }
    }

    static class RequestInfo {
        /* access modifiers changed from: private */
        public AjxCallback mAjxCallback;
        private boolean mAosParamsInBody = false;
        private String mBodyStr;
        private boolean mBodyTransfer;
        private Map<String, String> mBusinessParamMap = new HashMap();
        private Map<String, String> mHeaderMap = new HashMap();
        /* access modifiers changed from: private */
        public int mMethod = 0;
        private boolean mMock = false;
        private boolean mNeedAosParams = false;
        private boolean mNeedEncrypt;
        /* access modifiers changed from: private */
        public AosRequest mRequest;
        private int mRetryTimes = 0;
        private List<String> mSignList = new ArrayList();
        private int mTimeout = 10000;
        private Map<String, String> mUploadFiles;
        private String mUrl;
        /* access modifiers changed from: private */
        public String mUrlFromAjx;

        RequestInfo() {
        }

        /* access modifiers changed from: 0000 */
        public void setTimeout(int i) {
            this.mTimeout = i;
        }

        /* access modifiers changed from: 0000 */
        public void setRetryTimes(int i) {
            this.mRetryTimes = i;
        }

        /* access modifiers changed from: 0000 */
        public void setUrlFromAjx(String str) {
            this.mUrlFromAjx = str;
            this.mUrl = AjxUrlParser.ajxUrlToAosUrl(this.mUrlFromAjx);
            if (!TextUtils.isEmpty(this.mUrlFromAjx)) {
                String[] split = this.mUrlFromAjx.split("\\?", 2);
                if (split.length == 2) {
                    for (String split2 : split[1].split("&")) {
                        String[] split3 = split2.split("=");
                        if (split3.length == 2) {
                            this.mBusinessParamMap.put(split3[0], Uri.decode(split3[1]));
                        }
                        if (split3.length == 1) {
                            this.mBusinessParamMap.put(split3[0], "");
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void setMethodType(int i) {
            this.mMethod = i;
        }

        /* access modifiers changed from: 0000 */
        public void putHeader(String str, String str2) {
            this.mHeaderMap.put(str, str2);
        }

        /* access modifiers changed from: 0000 */
        public void setAosParamsInBody(boolean z) {
            this.mAosParamsInBody = z;
        }

        /* access modifiers changed from: 0000 */
        public void setMock(boolean z) {
            this.mMock = z;
        }

        /* access modifiers changed from: 0000 */
        public void putBody(boolean z, String str) {
            String[] split;
            if (!TextUtils.isEmpty(str)) {
                this.mBodyTransfer = z;
                if (!z) {
                    this.mBodyStr = str;
                } else if (str.contains("?") || str.contains("&")) {
                    for (String str2 : str.split("&")) {
                        int indexOf = str2.indexOf(61);
                        int length = str2.length();
                        if (indexOf >= 0 && indexOf <= length - 1) {
                            putParams(str2.substring(0, indexOf), Uri.decode(str2.substring(indexOf + 1)));
                        }
                    }
                } else {
                    try {
                        if (str.contains("=")) {
                            String[] split2 = str.split("=");
                            if (split2.length == 2) {
                                putParams(split2[0], split2[1]);
                                return;
                            } else if (split2.length == 1) {
                                putParams(split2[0], Token.SEPARATOR);
                                return;
                            }
                        }
                        JSONObject jSONObject = new JSONObject(str);
                        Iterator<String> keys = jSONObject.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            if (!TextUtils.isEmpty(next)) {
                                putParams(next, jSONObject.optString(next));
                            }
                        }
                    } catch (JSONException e) {
                        StringBuilder sb = new StringBuilder("parse body error:");
                        sb.append(e.getMessage());
                        AMapLog.e(ModuleRequest.TAG, sb.toString());
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public JSONObject getHeadersAsJSONObj() {
            HashMap hashMap = new HashMap();
            for (Entry next : this.mHeaderMap.entrySet()) {
                if (next.getKey() != null) {
                    hashMap.put(next.getKey(), next.getValue());
                }
            }
            return new JSONObject(hashMap);
        }

        /* access modifiers changed from: 0000 */
        public String getHeaders() {
            return getHeadersAsJSONObj().toString();
        }

        /* access modifiers changed from: 0000 */
        public void setNeedAosParams(boolean z) {
            this.mNeedAosParams = z;
        }

        /* access modifiers changed from: 0000 */
        public void setNeedEncrypt(boolean z) {
            this.mNeedEncrypt = z;
        }

        /* access modifiers changed from: 0000 */
        public void setSigns(List<String> list) {
            this.mSignList.clear();
            if (list != null) {
                this.mSignList.addAll(list);
            }
        }

        /* access modifiers changed from: 0000 */
        public void putParams(String str, String str2) {
            this.mBusinessParamMap.put(str, str2);
        }

        /* access modifiers changed from: 0000 */
        public void setUploadFile(JSONObject jSONObject) {
            if (this.mUploadFiles == null) {
                this.mUploadFiles = new HashMap();
            } else {
                this.mUploadFiles.clear();
            }
            try {
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String obj = keys.next().toString();
                    String optString = jSONObject.optString(obj);
                    if (optString.startsWith("file://")) {
                        optString = optString.substring(optString.indexOf("file://") + 7);
                    }
                    if (!TextUtils.isEmpty(obj) && !TextUtils.isEmpty(optString) && FileUtil.checkFileInvalid(optString)) {
                        this.mUploadFiles.put(obj, optString);
                    }
                }
            } catch (Exception unused) {
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean isValid() {
            return !TextUtils.isEmpty(this.mUrl);
        }

        /* access modifiers changed from: 0000 */
        public AosRequest createAosRequest() {
            if (this.mMethod != 1) {
                this.mRequest = new AosGetRequest();
            } else if (this.mUploadFiles == null || this.mUploadFiles.isEmpty()) {
                AosPostRequest aosPostRequest = new AosPostRequest();
                if (!TextUtils.isEmpty(this.mBodyStr)) {
                    String str = this.mBodyStr;
                    if (this.mNeedEncrypt) {
                        str = serverkey.amapEncodeV2(str);
                    }
                    try {
                        aosPostRequest.setBody(str.getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                this.mRequest = aosPostRequest;
            } else {
                AosMultipartRequest aosMultipartRequest = new AosMultipartRequest();
                for (Entry next : this.mUploadFiles.entrySet()) {
                    aosMultipartRequest.a((String) next.getKey(), new File((String) next.getValue()));
                }
                this.mRequest = aosMultipartRequest;
            }
            this.mRequest.setUrl(this.mUrl);
            if (!this.mHeaderMap.isEmpty()) {
                for (Entry next2 : this.mHeaderMap.entrySet()) {
                    this.mRequest.addHeader((String) next2.getKey(), (String) next2.getValue());
                }
            }
            if (!this.mBusinessParamMap.isEmpty()) {
                this.mRequest.addReqParams(this.mBusinessParamMap);
            }
            this.mRequest.setRetryTimes(this.mRetryTimes);
            this.mRequest.setTimeout(this.mTimeout);
            this.mRequest.addSignParams(this.mSignList);
            int i = 2;
            this.mRequest.setEncryptStrategy(this.mNeedEncrypt ? 2 : -1);
            if (this.mNeedAosParams) {
                this.mRequest.setCommonParamStrategy(0);
            } else {
                this.mRequest.setCommonParamStrategy(-1);
            }
            if (this.mRequest instanceof AosPostRequest) {
                AosPostRequest aosPostRequest2 = (AosPostRequest) this.mRequest;
                if (!this.mAosParamsInBody) {
                    i = 1;
                }
                aosPostRequest2.setCommonParamFormatStrategy(i);
            } else if (this.mRequest instanceof AosMultipartRequest) {
                this.mRequest.setEncryptStrategy(2);
                ((AosMultipartRequest) this.mRequest).b();
                ((AosMultipartRequest) this.mRequest).a();
            }
            if (!this.mNeedEncrypt || this.mMock) {
                this.mRequest.setEncryptStrategy(-1);
                if (this.mRequest instanceof AosPostRequest) {
                    ((AosPostRequest) this.mRequest).setCommonParamFormatStrategy(1);
                    ((AosPostRequest) this.mRequest).setReqParamFormatStrategy(1);
                }
            }
            return this.mRequest;
        }

        private String getAjxVersion() {
            String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
            if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
                return allAjxLatestPatchVersion;
            }
            return AjxFileInfo.getAllAjxFileBaseVersion();
        }
    }

    public ModuleRequest(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("fetch")
    public final void fetch(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && jsFunctionCallback != null) {
            RequestInfo optionsToRequestInfo = optionsToRequestInfo(str2, str);
            if (optionsToRequestInfo != null && optionsToRequestInfo.isValid()) {
                AjxCallback ajxCallback = new AjxCallback(this, str);
                ajxCallback.setCallback(jsFunctionCallback);
                optionsToRequestInfo.mAjxCallback = ajxCallback;
                AosRequest createAosRequest = optionsToRequestInfo.createAosRequest();
                yq.a();
                yq.a(createAosRequest, (AosResponseCallback<T>) ajxCallback);
                this.mRequestRecords.put(str, optionsToRequestInfo);
            }
        }
    }

    @AjxMethod("binaryFetch")
    public final void binaryFetch(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && jsFunctionCallback != null) {
            RequestInfo optionsToRequestInfo = optionsToRequestInfo(str2, str);
            if (optionsToRequestInfo != null && optionsToRequestInfo.isValid()) {
                AjxBinaryCallback ajxBinaryCallback = new AjxBinaryCallback(this, str);
                ajxBinaryCallback.setCallback(jsFunctionCallback);
                AosRequest createAosRequest = optionsToRequestInfo.createAosRequest();
                createAosRequest.setOutput(3);
                yq.a();
                yq.a(createAosRequest, (AosResponseCallback<T>) ajxBinaryCallback);
                this.mRequestRecords.put(str, optionsToRequestInfo);
            }
        }
    }

    @AjxMethod("destroyBinary")
    public final void destroyBinary(long j) {
        CAjxBLBinaryCenter.removeBinaryDataS((int) j);
    }

    @AjxMethod("getRequestHeader")
    public final void getRequestHeader(@NonNull String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str)) {
            jsFunctionCallback.callback(this.mRequestRecords.get(str).getHeaders());
        }
    }

    @AjxMethod("abort")
    public final void abort(@NonNull String str) {
        if (!TextUtils.isEmpty(str)) {
            RequestInfo remove = this.mRequestRecords.remove(str);
            if (remove != null) {
                AosRequest access$100 = remove.mRequest;
                if (access$100 != null) {
                    yq.a();
                    yq.a(access$100);
                }
            }
        }
    }

    private RequestInfo optionsToRequestInfo(@NonNull String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setUrlFromAjx(jSONObject.optString("url"));
            try {
                JSONObject optJSONObject = jSONObject.optJSONObject("data");
                if (optJSONObject != null) {
                    JSONObject optJSONObject2 = optJSONObject.optJSONObject("uploadFiles");
                    if (optJSONObject2 != null) {
                        requestInfo.setUploadFile(optJSONObject2);
                    }
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("parse data error:");
                sb.append(e.getMessage());
                AMapLog.e(TAG, sb.toString());
            }
            requestInfo.setMethodType(TextUtils.equals(jSONObject.optString("method").toLowerCase(), "post") ? 1 : 0);
            JSONObject optJSONObject3 = jSONObject.optJSONObject("aosSign");
            if (optJSONObject3 != null) {
                setAosSign(requestInfo, optJSONObject3);
            }
            JSONObject optJSONObject4 = jSONObject.optJSONObject("headers");
            if (optJSONObject4 != null) {
                setHeaders(requestInfo, optJSONObject4);
            }
            int optInt = jSONObject.optInt("timeout", -1);
            if (optInt != -1) {
                setTimeout(requestInfo, optInt);
            }
            setRetryTimes(requestInfo, jSONObject.optInt("retryTimes", 0));
            if (optJSONObject3 != null) {
                optJSONObject3.optBoolean("mock");
            }
            boolean optBoolean = jSONObject.optBoolean("bodytransfer", true);
            String optString = jSONObject.optString(Constants.BODY);
            if (optString != null) {
                requestInfo.putBody(optBoolean, optString);
            }
            if (onRequestOpListener != null) {
                String str3 = requestInfo.mMethod == 0 ? "GET" : "POST";
                String access$300 = requestInfo.mUrlFromAjx;
                requestInfo.getHeadersAsJSONObj();
                onRequestOpListener.onRequestWillBeSend(str2, access$300, str3, optJSONObject4, optString, "XHR");
            }
            return requestInfo;
        } catch (JSONException unused) {
            return null;
        }
    }

    private void setAosSign(@NonNull RequestInfo requestInfo, @NonNull JSONObject jSONObject) {
        requestInfo.setNeedAosParams(jSONObject.optBoolean("aos_params"));
        requestInfo.setNeedEncrypt(jSONObject.optBoolean("ent"));
        requestInfo.setAosParamsInBody(jSONObject.optBoolean("aos_params_inbody", false));
        JSONArray optJSONArray = jSONObject.optJSONArray("sign");
        if (optJSONArray != null && optJSONArray.length() > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                String optString = optJSONArray.optString(i);
                if (!TextUtils.isEmpty(optString)) {
                    arrayList.add(optString);
                }
            }
            requestInfo.setSigns(arrayList);
        }
    }

    private void setHeaders(@NonNull RequestInfo requestInfo, @NonNull JSONObject jSONObject) {
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            requestInfo.putHeader(next, jSONObject.optString(next));
        }
    }

    private void setTimeout(@NonNull RequestInfo requestInfo, int i) {
        requestInfo.setTimeout(i);
    }

    private void setRetryTimes(@NonNull RequestInfo requestInfo, int i) {
        requestInfo.setRetryTimes(i);
    }

    private RequestInfo tryGetRequest(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mRequestRecords.get(str);
    }

    private RequestInfo tryRemoveRequest(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mRequestRecords.remove(str);
    }

    /* access modifiers changed from: private */
    public void notifyJs(JsFunctionCallback jsFunctionCallback, int i, int i2, String str, String str2, int i3, String str3) {
        if (jsFunctionCallback != null) {
            if (TextUtils.isEmpty(str)) {
                str = "";
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = "";
            }
            jsFunctionCallback.callback(Integer.valueOf(i), Integer.valueOf(i2), str, str2, Integer.valueOf(i3), str3);
        }
    }

    /* access modifiers changed from: private */
    public void notifyJs(JsFunctionCallback jsFunctionCallback, int i, int i2, long j, int i3, String str) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Integer.valueOf(i), Integer.valueOf(i2), Long.valueOf(j), Integer.valueOf(i3), str);
        }
    }

    /* access modifiers changed from: private */
    public int getStatusCode(AosRequest aosRequest, AosResponseException aosResponseException) {
        int i = aosRequest.getHttpRequest().requestStatistics.e;
        if (aosResponseException == null) {
            return i;
        }
        if (12 == aosResponseException.errorCode || 11 == aosResponseException.errorCode) {
            return -2;
        }
        return aosResponseException.response != null ? aosResponseException.response.getStatusCode() : i;
    }

    /* access modifiers changed from: private */
    public int getErrorCode(AosResponseException aosResponseException) {
        if (aosResponseException != null) {
            return aosResponseException.errorCode;
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public String getErrorMsg(AosResponseException aosResponseException) {
        StringBuilder sb = new StringBuilder();
        if (aosResponseException != null) {
            if (aosResponseException.exception != null) {
                sb.append("Exception: ");
                sb.append(aosResponseException.exception);
                sb.append(" | ");
            }
            if (aosResponseException.getMessage() != null) {
                sb.append("Message : ");
                sb.append(aosResponseException.getMessage());
            }
        }
        return sb.toString();
    }

    public static String formatMockData(List<? extends abg> list, String str) {
        JSONObject jSONObject = new JSONObject();
        for (abg abg : list) {
            try {
                jSONObject.put(abg.a, abg.b);
            } catch (JSONException unused) {
            }
        }
        return jSONObject.toString();
    }

    public static void setOnRequestOpListener(OnRequestOpListener onRequestOpListener2) {
        onRequestOpListener = onRequestOpListener2;
    }
}
