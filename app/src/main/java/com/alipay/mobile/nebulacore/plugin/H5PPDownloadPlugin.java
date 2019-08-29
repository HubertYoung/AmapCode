package com.alipay.mobile.nebulacore.plugin;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.provider.H5ChannelProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.util.H5PPQueryThread;
import com.alipay.mobile.nebulacore.util.H5PPQueryThread.OnGetQueryResult;
import com.alipay.mobile.nebulacore.view.H5Toast;
import java.util.concurrent.ThreadPoolExecutor;

public class H5PPDownloadPlugin extends H5SimplePlugin {
    public static final String TAG = "H5PPDownloadPlugin";
    /* access modifiers changed from: private */
    public H5Page a = null;
    private String b = "ppdownload";

    public void onInitialize(H5CoreNode coreNode) {
        this.a = (H5Page) coreNode;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(this.b);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (this.b.equals(event.getAction())) {
            a(event);
        }
        return true;
    }

    private void a(H5Event event) {
        JSONObject params = event.getParam();
        final String url = H5Utils.getString(params, (String) "url");
        final String fileName = H5Utils.getString(params, (String) "fileName");
        final String userAgent = H5Utils.getString(params, (String) "userAgent");
        final String contentDisposition = H5Utils.getString(params, (String) "contentDisposition");
        final String mimeType = H5Utils.getString(params, (String) "mimeType");
        final long contentLength = H5Utils.getLong(params, (String) "contentLength");
        H5ChannelProvider h5ChannelProvider = (H5ChannelProvider) Nebula.getProviderManager().getProvider(H5ChannelProvider.class.getName());
        boolean channel = false;
        if (h5ChannelProvider != null) {
            String channelId = h5ChannelProvider.getChannelId();
            channel = "5136".equals(channelId);
            H5Log.d(TAG, "channelId is " + channelId + ", isGooglePlayChannel " + channelId);
        }
        final boolean isGooglePlayChannel = channel;
        H5Log.d(TAG, "isGooglePlayChannel " + isGooglePlayChannel);
        OnGetQueryResult onGetQueryResult = new OnGetQueryResult() {
            public void onQueryResult(String detailUrl, String packageName) {
                if (isGooglePlayChannel) {
                    if (!TextUtils.isEmpty(packageName)) {
                        try {
                            H5Utils.startExtActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
                        } catch (Exception e2) {
                            H5Utils.runOnMain(new Runnable() {
                                public void run() {
                                    H5Toast.showToast(H5Environment.getContext(), H5Environment.getResources().getString(R.string.h5_googleplaynotinstall), 0);
                                }
                            });
                        }
                    } else {
                        H5Log.d(H5PPDownloadPlugin.TAG, "googleplaychannel query packagename empty");
                    }
                    H5Log.d(H5PPDownloadPlugin.TAG, "download whitelistapk but googleplay channel return");
                } else if (!TextUtils.isEmpty(detailUrl)) {
                    H5PPDownloadPlugin.this.a.loadUrl(detailUrl);
                    JSONObject params = new JSONObject();
                    params.put((String) "type", (Object) "apk");
                    params.put((String) "origin", (Object) url);
                    params.put((String) "detailUrl", (Object) detailUrl);
                    H5PPDownloadPlugin.this.a.sendEvent(CommonEvents.H5_PAGE_JUMP_PP, params);
                } else {
                    H5PPDownloadPlugin.this.a(url, fileName, userAgent, contentDisposition, mimeType, contentLength);
                }
            }
        };
        ThreadPoolExecutor executor = H5Utils.getExecutor(H5ThreadType.URGENT);
        H5PPQueryThread h5PPQueryThread = H5PPQueryThread.getInstance();
        h5PPQueryThread.setParams(url, 1, onGetQueryResult, this.a);
        executor.execute(h5PPQueryThread);
    }

    /* access modifiers changed from: private */
    public void a(String url, String fileName, String userAgent, String contentDisposition, String mimeType, long contentLength) {
        JSONObject params = new JSONObject();
        params.put((String) "url", (Object) url);
        params.put((String) "fileName", (Object) fileName);
        params.put((String) "userAgent", (Object) userAgent);
        params.put((String) "contentDisposition", (Object) contentDisposition);
        params.put((String) "mimeType", (Object) mimeType);
        params.put((String) "contentLength", (Object) Long.valueOf(contentLength));
        this.a.sendEvent(CommonEvents.H5_PAGE_DOWNLOAD, params);
    }
}
