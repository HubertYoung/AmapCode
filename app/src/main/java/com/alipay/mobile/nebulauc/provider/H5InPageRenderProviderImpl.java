package com.alipay.mobile.nebulauc.provider;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import com.alipay.mobile.nebula.provider.H5InPageRenderProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.impl.setup.UcOtherBizSetup;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class H5InPageRenderProviderImpl implements H5InPageRenderProvider {
    private static final String TAG = "H5InPageRenderProviderImpl";
    private final Set<String> enableTypes = new HashSet<String>() {
        {
            add("map");
            add("video");
            add(WalletTinyappUtils.CONST_SCOPE_CAMERA);
            add("canvas");
            add("input");
        }
    };
    private boolean hasGetConfig = false;
    private OnConfigChangeListener listener = new OnConfigChangeListener() {
        public void onChange(String value) {
            H5InPageRenderProviderImpl.this.applyConfig(value);
        }
    };

    public void addInPageRender(String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = H5UrlHelper.parseUrl(url);
            if (uri != null) {
                tryInitConfig();
                synchronized (this.enableTypes) {
                    UcOtherBizSetup.enableInPageRender(uri.getHost(), this.enableTypes);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void applyConfig(String value) {
        if (!TextUtils.isEmpty(value)) {
            H5Log.d(TAG, "h5_ucInPageRenderTypeList " + value);
            String[] types = value.split(MergeUtil.SEPARATOR_KV);
            synchronized (this.enableTypes) {
                this.enableTypes.clear();
                this.enableTypes.addAll(Arrays.asList(types));
            }
        }
    }

    private void tryInitConfig() {
        if (!this.hasGetConfig) {
            this.hasGetConfig = true;
            H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (configProvider != null) {
                applyConfig(configProvider.getConfigWithNotifyChange("h5_ucInPageRenderTypeList", this.listener));
            }
        }
    }
}
