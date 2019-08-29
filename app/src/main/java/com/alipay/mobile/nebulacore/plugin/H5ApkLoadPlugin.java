package com.alipay.mobile.nebulacore.plugin;

import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5DomainUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class H5ApkLoadPlugin extends H5SimplePlugin {
    public static final String TAG = "H5ApkLoadPlugin";
    private H5Page a;

    public void onInitialize(H5CoreNode coreNode) {
        this.a = (H5Page) coreNode;
    }

    public void onRelease() {
        this.a = null;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_SHOULD_LOAD_URL);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (!CommonEvents.H5_PAGE_SHOULD_LOAD_URL.equals(event.getAction())) {
            return false;
        }
        String url = H5Utils.getString(event.getParam(), (String) "url");
        String path = H5UrlHelper.getPath(url);
        if (TextUtils.isEmpty(path) || !path.toLowerCase().endsWith(".apk")) {
            return false;
        }
        long delta = System.currentTimeMillis() - this.a.getLastTouch();
        H5Log.d(TAG, "apk file url " + url);
        int historySize = H5Utils.getInt(event.getParam(), (String) "historySize", 0);
        H5Log.d(TAG, "historySize " + historySize + " delta " + delta);
        if (delta < 400) {
            H5Log.d(TAG, "user trigger apk download delta " + delta);
            return false;
        } else if (historySize <= 0) {
            H5Log.d(TAG, "skip first apk download url");
            return false;
        } else {
            H5Service h5Service = H5ServiceUtils.getH5Service();
            if (h5Service != null && h5Service.isAliDomain(url)) {
                return false;
            }
            if (H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(url), H5Environment.getConfig("h5_302_download_apk_whiteList"))) {
                return false;
            }
            a(url);
            return true;
        }
    }

    private void a(String url) {
        boolean isNeed = true;
        String needLoadSecPage = H5Environment.getConfigWithProcessCache("h5_loadSecPage");
        if (!TextUtils.isEmpty(needLoadSecPage) && BQCCameraParam.VALUE_NO.equalsIgnoreCase(needLoadSecPage)) {
            isNeed = false;
        }
        if (isNeed) {
            try {
                this.a.loadUrl("https://ds.alipay.com/error/securityLink.htm?url=" + URLEncoder.encode(url, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                H5Log.d(TAG, "Error: Unsupported encoding exception on " + url);
            }
        }
    }
}
