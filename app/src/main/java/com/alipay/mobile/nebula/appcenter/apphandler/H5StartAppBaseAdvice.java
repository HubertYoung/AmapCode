package com.alipay.mobile.nebula.appcenter.apphandler;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.data.H5Trace;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5KeepAliveUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;

public abstract class H5StartAppBaseAdvice implements Advice {
    private static final String TAG = "H5StartAppAdvice";

    public abstract boolean canHandler(String str);

    public void onCallBefore(String pointCut, Object targetPoint, Object[] args) {
    }

    public Pair<Boolean, Object> onCallAround(String pointCut, Object targetPoint, Object[] args) {
        return null;
    }

    public void onCallAfter(String pointCut, Object targetPoint, Object[] args) {
    }

    public void onExecutionBefore(String pointCut, Object thisPoint, Object[] args) {
    }

    public Pair<Boolean, Object> onExecutionAround(String pointCut, Object thisPoint, Object[] args) {
        if (!TextUtils.equals(pointCut, PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_DOSTARTAPP)) {
            return null;
        }
        if (args != null) {
            try {
                if (args.length >= 3) {
                    String sourceAppId = "";
                    String targetAppId = "";
                    Bundle param = null;
                    Bundle sceneParam = null;
                    if (args[0] instanceof String) {
                        sourceAppId = args[0];
                    }
                    if (args[1] instanceof String) {
                        targetAppId = args[1];
                    }
                    if (args[2] instanceof Bundle) {
                        param = args[2];
                    }
                    if (args.length > 3 && (args[3] instanceof Bundle)) {
                        sceneParam = args[3];
                    }
                    boolean hasCheck = false;
                    if (H5AppHandler.hasCheckParam(param)) {
                        hasCheck = true;
                    }
                    if (!hasCheck && !TextUtils.isEmpty(targetAppId)) {
                        String longUrl = H5Utils.getString(param, (String) "url");
                        String url = H5Utils.getString(param, (String) H5Param.URL);
                        if ((!TextUtils.isEmpty(longUrl) && longUrl.startsWith("javascript:")) || (!TextUtils.isEmpty(url) && url.startsWith("javascript:"))) {
                            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                            if (h5ConfigProvider != null) {
                                JSONArray jsonArray = h5ConfigProvider.getConfigJSONArray("h5_enableInterceptJavascriptInScheme");
                                if (jsonArray == null || !jsonArray.contains(targetAppId)) {
                                    H5Log.d(TAG, "H5StartAppBaseAdvice intercept javascript success");
                                    return new Pair<>(Boolean.valueOf(true), null);
                                }
                            } else {
                                H5Log.d(TAG, "H5StartAppBaseAdvice intercept javascript success");
                                return new Pair<>(Boolean.valueOf(true), null);
                            }
                        }
                        if (canHandler(targetAppId)) {
                            if (H5KeepAliveUtil.handleRestartInMain(targetAppId, param)) {
                                return new Pair<>(Boolean.valueOf(true), null);
                            }
                            H5Trace.event("Advice.startApp", null, "appId", targetAppId);
                            H5Log.d(TAG, "sourceAppId " + sourceAppId + " targetAppId:" + targetAppId + " param:" + param);
                            final H5StartAppInfo h5StartAppInfo = new H5StartAppInfo();
                            h5StartAppInfo.params = new Bundle(param);
                            h5StartAppInfo.sourceAppId = sourceAppId;
                            h5StartAppInfo.targetAppId = targetAppId;
                            h5StartAppInfo.sceneParams = sceneParam;
                            H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
                                public void run() {
                                    try {
                                        H5Trace.sessionBegin("Advice.syncApp", null, "appId");
                                        H5AppHandler.syncApp(h5StartAppInfo);
                                    } catch (Exception e) {
                                        H5Log.e((String) H5StartAppBaseAdvice.TAG, (Throwable) e);
                                    } finally {
                                        r2 = "Advice.syncApp";
                                        r4 = "appId";
                                        H5Trace.sessionEnd(r2, null, r4);
                                    }
                                }
                            });
                            return new Pair<>(Boolean.valueOf(true), null);
                        } else if (H5InstallAppAdvice.enableUseDevMode(param)) {
                            H5Log.d(TAG, "sourceAppId " + sourceAppId + " targetAppId:" + targetAppId + " param:" + param);
                            final H5StartAppInfo h5StartAppInfo2 = new H5StartAppInfo();
                            if (param == null) {
                                param = new Bundle();
                            }
                            h5StartAppInfo2.params = new Bundle(param);
                            h5StartAppInfo2.sourceAppId = sourceAppId;
                            h5StartAppInfo2.targetAppId = targetAppId;
                            h5StartAppInfo2.sceneParams = sceneParam;
                            H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
                                public void run() {
                                    try {
                                        H5AppHandler.syncApp(h5StartAppInfo2);
                                    } catch (Exception e) {
                                        H5Log.e((String) H5StartAppBaseAdvice.TAG, (Throwable) e);
                                    }
                                }
                            });
                            return new Pair<>(Boolean.valueOf(true), null);
                        }
                    }
                }
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
        return null;
    }

    public void onExecutionAfter(String pointCut, Object thisPoint, Object[] args) {
    }
}
