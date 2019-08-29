package com.alipay.mobile.nebulacore.plugin;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.util.PingUtil;
import com.alipay.mobile.nebulacore.util.PingUtil.PingResult;
import java.text.DecimalFormat;
import java.util.HashMap;

public class H5NetworkAnalysisPlugin extends H5SimplePlugin {
    public static final String TAG = "H5NetworkAnalysisPlugin";
    /* access modifiers changed from: private */
    public static final HashMap<String, JSONObject> a = new HashMap<>();

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("ping");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (!"ping".equals(event.getAction())) {
            return false;
        }
        JSONObject param = event.getParam();
        if (param != null) {
            final String host = H5Utils.getString(param, (String) "host");
            final int numOfRound = H5Utils.getInt(param, (String) "numOfRound", 3);
            boolean useCached = H5Utils.getBoolean(param, (String) "useCached", true);
            final JSONObject resultJson = new JSONObject();
            if (TextUtils.isEmpty(host) || numOfRound <= 0) {
                H5Log.e((String) TAG, "illegal arguments(host:" + host + " numOfRound:" + numOfRound + ")");
                bridgeContext.sendError(event, Error.INVALID_PARAM);
                return true;
            } else if (!useCached || a.get(host) == null) {
                final H5BridgeContext h5BridgeContext = bridgeContext;
                H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                    public void run() {
                        PingResult result = null;
                        try {
                            result = PingUtil.ping(host, numOfRound);
                        } catch (Exception globalException) {
                            H5Log.e(H5NetworkAnalysisPlugin.TAG, "exception detail.", globalException);
                        }
                        H5NetworkAnalysisPlugin.b(result, resultJson, h5BridgeContext);
                        if (result != null) {
                            H5NetworkAnalysisPlugin.a.put(host, resultJson);
                        }
                    }
                });
            } else {
                bridgeContext.sendBridgeResult(a.get(host));
                return true;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void b(PingResult result, JSONObject resultJson, H5BridgeContext bridgeContext) {
        if (result != null) {
            resultJson.put((String) "avgConsumedTimeMs", (Object) Float.valueOf(result.avgConsumedTimeMs));
            float loss = 0.0f;
            try {
                if (result.loss != null && result.loss.endsWith("%")) {
                    loss = Float.parseFloat(result.loss.substring(0, result.loss.length() - 1)) / 100.0f;
                }
            } catch (NumberFormatException e) {
                H5Log.e(TAG, "exception detail", e);
            }
            resultJson.put((String) "loss", (Object) new DecimalFormat("##0.00").format((double) loss));
            if (result.timePerRound != null && result.timePerRound.length > 0) {
                JSONArray timePerRound = new JSONArray();
                for (Float time : result.timePerRound) {
                    timePerRound.add(time);
                }
                Context context = H5Environment.getContext();
                resultJson.put((String) "timePerRound", (Object) timePerRound);
                resultJson.put((String) "networkType", (Object) H5Utils.getNetworkType(context));
                resultJson.put((String) "timeStamp", (Object) Long.valueOf(System.currentTimeMillis()));
            }
        } else {
            resultJson.put((String) "error", (Object) Integer.valueOf(Error.UNKNOWN_ERROR.ordinal()));
        }
        bridgeContext.sendBridgeResult(resultJson);
    }
}
