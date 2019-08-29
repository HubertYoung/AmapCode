package com.autonavi.miniapp.plugin.feedback;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.plugins.utils.GeneralUtils;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.nebula.util.H5Log;
import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.miniapp.plugin.BasePlugin;
import com.tencent.open.SocialConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedbackPlugin extends BasePlugin {
    private static final String PARAM_DIU = "diu";
    private static final String PARAM_TID = "tid";
    private static final Pattern PATTERN = Pattern.compile("https://resource/(.*)\\.image");
    private static final String SEND_AOS_DATA = "sendAosData";
    private static final List<String> SIGN_PARAMS;
    private static final String TAG = "FeedbackPlugin";
    private AosResponseCallback<AosStringResponse> mCallback = new AosResponseCallback<AosStringResponse>() {
        public void onSuccess(AosStringResponse aosStringResponse) {
            H5Log.d(FeedbackPlugin.TAG, "feedback success.");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "data", (Object) aosStringResponse.getResponseBodyString());
            FeedbackPlugin.this.mContext.sendBridgeResult(jSONObject);
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            StringBuilder sb = new StringBuilder("feedback error msg: ");
            sb.append(aosResponseException.getMessage());
            H5Log.d(FeedbackPlugin.TAG, sb.toString());
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "error", (Object) Integer.valueOf(1));
            jSONObject.put((String) "errorMessage", (Object) aosResponseException.getMessage());
            FeedbackPlugin.this.mContext.sendBridgeResult(jSONObject);
        }
    };
    /* access modifiers changed from: private */
    public H5BridgeContext mContext = null;

    static {
        ArrayList arrayList = new ArrayList();
        SIGN_PARAMS = arrayList;
        arrayList.add("type");
        SIGN_PARAMS.add("description");
    }

    public void onPrepare(H5EventFilter h5EventFilter) {
        h5EventFilter.addAction(SEND_AOS_DATA);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        int i = 0;
        if (!SEND_AOS_DATA.equals(h5Event.getAction())) {
            H5Log.d(TAG, "unknown action for feedback plugin.");
            return false;
        }
        JSONObject param = h5Event.getParam();
        if (param == null) {
            H5Log.w(TAG, "empty param for feedback.");
            h5BridgeContext.sendError(h5Event, Error.INVALID_PARAM);
            return true;
        }
        String string = param.getString("url");
        JSONObject jSONObject = param.getJSONObject("data");
        if (TextUtils.isEmpty(string) || jSONObject == null) {
            h5BridgeContext.sendError(h5Event, Error.INVALID_PARAM);
            return true;
        }
        this.mContext = h5BridgeContext;
        JSONArray jSONArray = jSONObject.getJSONArray(SocialConstants.PARAM_AVATAR_URI);
        jSONObject.remove(SocialConstants.PARAM_AVATAR_URI);
        Map<String, String> transToMap = transToMap(jSONObject);
        AosMultipartRequest aosMultipartRequest = new AosMultipartRequest();
        aosMultipartRequest.setUrl(string);
        aosMultipartRequest.addReqParams(transToMap);
        aosMultipartRequest.addSignParams(SIGN_PARAMS);
        if (jSONArray != null) {
            int size = jSONArray.size();
            while (i < size) {
                String concat = i != 0 ? SocialConstants.PARAM_AVATAR_URI.concat(String.valueOf(i)) : SocialConstants.PARAM_AVATAR_URI;
                File fileByLocalUrl = getFileByLocalUrl(jSONArray.getString(i));
                if (fileByLocalUrl != null) {
                    aosMultipartRequest.a(concat, fileByLocalUrl);
                }
                i++;
            }
        }
        in.a().a((AosRequest) aosMultipartRequest, this.mCallback);
        return true;
    }

    private Map<String, String> transToMap(JSONObject jSONObject) {
        HashMap hashMap = new HashMap();
        for (String next : jSONObject.keySet()) {
            hashMap.put(next, jSONObject.getString(next));
        }
        hashMap.put("diu", NetworkParam.getDiu());
        hashMap.put("tid", NetworkParam.getTaobaoID());
        return hashMap;
    }

    private File getFileByLocalUrl(String str) {
        H5Log.d(TAG, "url: ".concat(String.valueOf(str)));
        Matcher matcher = PATTERN.matcher(str);
        if (matcher.matches()) {
            String group = matcher.group(1);
            String convertLocalIdToPath = GeneralUtils.convertLocalIdToPath(group);
            if (!TextUtils.isEmpty(convertLocalIdToPath)) {
                H5Log.d(TAG, "file path: ".concat(String.valueOf(convertLocalIdToPath)));
                return new File(convertLocalIdToPath.replace("file:///", ""));
            }
            H5Log.d(TAG, "convert to empty path. localId: ".concat(String.valueOf(group)));
        }
        return null;
    }

    public String getScope() {
        return SCOPE_PAGE;
    }

    public String getEvents() {
        return getEvents(Arrays.asList(new String[]{SEND_AOS_DATA}));
    }
}
