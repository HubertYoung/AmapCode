package com.alipay.mobile.tinyappcommon.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.a.a.a;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.taobao.accs.common.Constants;

public class H5TinyAppLogUtil {
    public static final String TAG = "H5TinyAppLogHelper";
    public static final String TINY_APP_STANDARD_CATEGORY = "category";
    public static final String TINY_APP_STANDARD_DESCRIPTION = "description";
    public static final String TINY_APP_STANDARD_EXTRA = "autoExtra";
    public static final String TINY_APP_STANDARD_EXTRA_APPID = "appId";
    public static final String TINY_APP_STANDARD_EXTRA_APPVERSION = "appVersion";
    public static final String TINY_APP_STANDARD_EXTRA_APPXVERSION = "appxVersion";
    public static final String TINY_APP_STANDARD_EXTRA_CLIENTVERSION = "clientVersion";
    public static final String TINY_APP_STANDARD_EXTRA_PLATFORM = "platform";
    public static final String TINY_APP_STANDARD_EXTRA_SYSTEMINFO = "systemInfo";
    public static final String TINY_APP_STANDARD_EXTRA_TIMESTAMP = "timestamp";
    public static final String TINY_APP_STANDARD_EXTRA_URL = "url";
    public static final String TINY_APP_STANDARD_EXTRA_USERID = "userId";
    public static final String TINY_APP_STANDARD_LOG = "tinyAppStandardLog";
    public static final String TINY_APP_STANDARD_LOGID = "logId";
    public static final String TINY_APP_STANDARD_MESSAGE = "message";
    public static final String TINY_APP_STANDARD_OUTPUT = "output";

    public static String buildStandardLogInfo(Activity activity, H5Page h5Page, JSONObject eventParams) {
        String standardLog = null;
        try {
            return appendExtraInfo(activity, h5Page, buildBasicLogInfo(H5Utils.getString(eventParams, (String) "category"), H5Utils.getString(eventParams, (String) TINY_APP_STANDARD_LOGID), H5Utils.getString(eventParams, (String) TINY_APP_STANDARD_OUTPUT), H5Utils.getString(eventParams, (String) "description"), H5Utils.getString(eventParams, (String) "message"))).toJSONString();
        } catch (Throwable throwable) {
            H5Log.w(TAG, "getCreateScenario", throwable);
            return standardLog;
        }
    }

    public static JSONObject buildBasicLogInfo(String category, String logId, String output, String description, String msg) {
        JSONObject obj = new JSONObject();
        obj.put((String) "category", (Object) category);
        obj.put((String) TINY_APP_STANDARD_LOGID, (Object) logId);
        obj.put((String) TINY_APP_STANDARD_OUTPUT, (Object) output);
        obj.put((String) "description", (Object) description);
        obj.put((String) "message", (Object) msg);
        return obj;
    }

    public static JSONObject appendExtraInfo(Activity activity, H5Page h5Page, JSONObject obj) {
        JSONObject extraInfo = new JSONObject();
        extraInfo.put((String) "timestamp", (Object) Long.valueOf(System.currentTimeMillis()));
        extraInfo.put((String) "userId", (Object) H5Utils.getUserId());
        extraInfo.put((String) TINY_APP_STANDARD_EXTRA_CLIENTVERSION, (Object) H5Utils.getVersion());
        extraInfo.put((String) "platform", (Object) a.a);
        if (h5Page != null) {
            Bundle pageParams = h5Page.getParams();
            extraInfo.put((String) "appId", (Object) H5Utils.getString(pageParams, (String) "appId"));
            extraInfo.put((String) "url", (Object) H5Utils.getString(pageParams, (String) "url"));
            extraInfo.put((String) "appVersion", (Object) H5Utils.getString(pageParams, (String) "appVersion"));
            extraInfo.put((String) TINY_APP_STANDARD_EXTRA_APPXVERSION, (Object) H5Utils.getCurrentAvailableAppxVersion());
            JSONObject systemInfo = new JSONObject();
            DisplayMetrics displayMetrics = H5Utils.getContext().getResources().getDisplayMetrics();
            if (displayMetrics != null) {
                float density = displayMetrics.density;
                int width = Math.round(((float) displayMetrics.widthPixels) / density);
                systemInfo.put((String) "pixelRatio", (Object) Float.valueOf(density));
                systemInfo.put((String) "windowWidth", (Object) Integer.valueOf(width));
                systemInfo.put((String) "screenWidth", (Object) Integer.valueOf(displayMetrics.widthPixels));
                systemInfo.put((String) "screenHeight", (Object) Integer.valueOf(displayMetrics.heightPixels));
                systemInfo.put((String) "windowHeight", (Object) Integer.valueOf(getHeight(activity, h5Page, density, displayMetrics)));
            }
            systemInfo.put((String) "brand", (Object) Build.BRAND);
            systemInfo.put((String) "system", (Object) VERSION.RELEASE);
            systemInfo.put((String) "apiLevel", (Object) Integer.valueOf(VERSION.SDK_INT));
            systemInfo.put((String) Constants.KEY_MODEL, (Object) Build.MANUFACTURER + Token.SEPARATOR + Build.MODEL);
            systemInfo.put((String) "storage", (Object) getInternalMemorySize(H5Utils.getContext()));
            extraInfo.put((String) TINY_APP_STANDARD_EXTRA_SYSTEMINFO, (Object) systemInfo);
        }
        if (obj != null) {
            obj.put((String) TINY_APP_STANDARD_EXTRA, (Object) extraInfo);
        }
        return obj;
    }

    public static String checkMsgIsValid(JSONObject params) {
        String category = H5Utils.getString(params, (String) "category");
        if (TextUtils.isEmpty(category)) {
            return "log format is error, category is empty.";
        }
        if (TextUtils.isEmpty(H5Utils.getString(params, (String) TINY_APP_STANDARD_LOGID))) {
            return "log format is error, logId is empty.";
        }
        if (TextUtils.isEmpty(H5Utils.getString(params, (String) TINY_APP_STANDARD_OUTPUT))) {
            return "log format is error, output is empty.";
        }
        if (ActionConstant.EXCEPTION_VIEW_TYPE_WARN.equals(category) && TextUtils.isEmpty(H5Utils.getString(params, (String) "description"))) {
            return "log format is error, warn log needs description key.";
        }
        if (!"error".equals(category)) {
            return null;
        }
        if (TextUtils.isEmpty(H5Utils.getString(params, (String) "message")) || TextUtils.isEmpty(H5Utils.getString(params, (String) "description"))) {
            return "log format is error, error log needs description and message keys.";
        }
        return null;
    }

    public static int getHeight(Activity activity, H5Page h5Page, float density, DisplayMetrics displayMetrics) {
        int height = displayMetrics != null ? Math.round(((float) (displayMetrics.heightPixels - getTitleAndStatusBarHeight(activity))) / density) : 0;
        boolean getHeightFromWebView = true;
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider == null) {
            return height;
        }
        String getHeightWebview = provider.getConfig("h5_getWebViewHeight");
        if (!TextUtils.isEmpty(getHeightWebview) && BQCCameraParam.VALUE_NO.equalsIgnoreCase(getHeightWebview)) {
            getHeightFromWebView = false;
        }
        if (getHeightFromWebView) {
            int heightOfWebView = Math.round(((float) h5Page.getWebView().getView().getHeight()) / density);
            if (heightOfWebView > 0) {
                height = heightOfWebView;
            }
        }
        return height;
    }

    private static int getTitleAndStatusBarHeight(Activity activity) {
        try {
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            return ((int) activity.getResources().getDimension(R.dimen.h5_title_height)) + frame.top;
        } catch (Throwable e) {
            H5Log.e((String) TAG, "getTitleAndStatusBarHeight...e=" + e);
            return H5DimensionUtil.dip2px(H5Utils.getContext(), 1.0f) * 73;
        }
    }

    public static String getInternalMemorySize(Context context) {
        if (VERSION.SDK_INT < 18) {
            return "";
        }
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return Formatter.formatFileSize(context, statFs.getBlockCountLong() * statFs.getBlockSizeLong());
        } catch (Throwable e) {
            H5Log.e(TAG, "getInternalMemorySize...", e);
            return "";
        }
    }
}
