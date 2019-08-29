package com.mpaas.nebula.plugin;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.model.H5Refer;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5StartPagePlugin extends H5SimplePlugin {
    public static final String TAG = "H5StartPagePlugin";
    private String a = "startH5App";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(this.a);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!this.a.equals(event.getAction())) {
            return false;
        }
        a(event);
        return true;
    }

    private static void a(H5Event event) {
        JSONObject callParam = event.getParam();
        H5CoreNode target = event.getTarget();
        if (!(target instanceof H5Page)) {
            H5Log.w(TAG, "invalid target!");
            return;
        }
        H5Page h5Page = (H5Page) target;
        Bundle bundle = h5Page.getParams();
        String currentUrl = h5Page.getUrl();
        if (TextUtils.isEmpty(H5Refer.referUrl)) {
            H5Refer.referUrl = LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_REFVIEWID);
        } else if (!TextUtils.equals(currentUrl, H5Refer.referUrl)) {
            H5Refer.referUrl = currentUrl;
        }
        H5Log.d("H5LoggerPlugins", "start AppId , g5PageData : " + h5Page.getPageData().hashCode() + " , public ReferUrl :  " + H5Refer.referUrl);
        if (!H5Utils.getBoolean(bundle, (String) H5Param.LONG_TRANSPARENT, false) || H5Utils.getBoolean(bundle, (String) H5Param.LONG_FULLSCREEN, false)) {
            if (h5Page != null) {
                H5Log.d(TAG, "sendToWeb page event pagePause");
                h5Page.getBridge().sendToWeb("pagePause", null, null);
            }
            Bundle oldParams = new Bundle();
            H5Utils.toBundle(oldParams, callParam);
            String url = H5Utils.getString(callParam, (String) "url", (String) null);
            if (TextUtils.isEmpty(H5Utils.getString(callParam, (String) "appId", (String) null))) {
                H5Log.e((String) TAG, (String) "can't get appId parameter!");
                return;
            }
            H5Log.d(TAG, "startH5App url " + url);
            oldParams.putString(H5Param.REFERER, currentUrl);
            if (h5Page != null && h5Page.getContext() != null && h5Page.getContext().getContext() != null) {
                H5Bundle h5Bundle = new H5Bundle();
                h5Bundle.setParams(oldParams);
                ((H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName())).startPage(LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp(), h5Bundle);
                return;
            }
            return;
        }
        H5Log.d(TAG, "can not pushwindow in a transparent window");
    }
}
