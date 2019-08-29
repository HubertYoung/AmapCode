package com.alipay.mobile.nebulauc.impl.network;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.uc.webview.export.internal.interfaces.INetworkDecider;
import java.util.ArrayList;
import java.util.List;

public class AlipayNetworkDecider implements INetworkDecider {
    public static final String TAG = "AlipayNetworkDecider";
    private List<String> alipayNetworkBlackList;

    public AlipayNetworkDecider() {
        JSONArray array = H5ConfigUtil.getConfigJSONArray("h5_alipayNetworkBlackList");
        if (array != null) {
            this.alipayNetworkBlackList = new ArrayList();
            int size = array.size();
            for (int i = 0; i < size; i++) {
                String value = array.getString(i);
                if (!TextUtils.isEmpty(value)) {
                    this.alipayNetworkBlackList.add(value);
                }
            }
        }
    }

    public int chooseNetwork(String url) {
        if (TextUtils.isEmpty(url)) {
            return 0;
        }
        if (!H5Utils.isDebug() || !H5DevConfig.getBooleanConfig("h5_disable_alipay_network", false)) {
            if (this.alipayNetworkBlackList != null) {
                for (String blackListItem : this.alipayNetworkBlackList) {
                    if (url.startsWith(blackListItem)) {
                        H5Log.d(TAG, "disable alipay network by online config! " + url);
                        return 0;
                    }
                }
            }
            Uri uri = H5UrlHelper.parseUrl(url);
            if (uri == null) {
                H5Log.d(TAG, "uri is null, use alinetwork");
                return 1;
            }
            String scheme = uri.getScheme();
            if (TextUtils.isEmpty(scheme) || !scheme.toLowerCase().startsWith("ws")) {
                H5Log.d(TAG, "use alinetwork");
                return 1;
            }
            H5Log.d(TAG, "use ucnetwork to handle websocket");
            return 0;
        }
        H5Log.d(TAG, "disable alipay network by dev config! " + url);
        return 0;
    }
}
