package com.alipay.mobile.nebulacore.tabbar;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.mobile.nebula.data.H5CustomHttpResponse;
import com.alipay.mobile.nebula.provider.H5FallbackStreamProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5TabbarUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabObserver.H5SessionTabListener;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.HashMap;
import java.util.Map;

public class H5SessionTabInfoParser {

    public interface H5SessionTabInfoListener {
        void onGetAsyncData(JSONObject jSONObject);

        void onGetSyncData(JSONObject jSONObject);

        void onShowDefaultTab();
    }

    public static void getOfflineData(H5SessionImpl h5Session, final H5SessionTabInfoListener h5SessionTabInfoListener, final String appId) {
        if (h5Session != null && h5Session.getH5SessionTabObserver() != null) {
            String dataStr = h5Session.getH5SessionTabObserver().getData(new H5SessionTabListener() {
                public final void onDataParsed(String data) {
                    H5Log.d("H5SessionTabInfoParser", "##tabbar## getOfflineData in callback data " + data);
                    if (!TextUtils.equals(data, "stupid")) {
                        if (data.startsWith("http")) {
                            H5SessionTabInfoParser.getOnlineData(data, h5SessionTabInfoListener, appId);
                            return;
                        }
                        JSONObject dataObj = H5Utils.parseObject(data);
                        if (h5SessionTabInfoListener != null) {
                            h5SessionTabInfoListener.onGetAsyncData(dataObj);
                        }
                    } else if (h5SessionTabInfoListener != null) {
                        h5SessionTabInfoListener.onGetAsyncData(null);
                    }
                }
            });
            H5Log.d("H5SessionTabInfoParser", "##tabbar## getOfflineData in uithread dataStr " + dataStr);
            if (!TextUtils.isEmpty(dataStr) && !TextUtils.equals(dataStr, "stupid")) {
                a(h5SessionTabInfoListener, dataStr, appId);
            } else if (TextUtils.isEmpty(dataStr) || !TextUtils.equals(dataStr, "stupid")) {
                try {
                    byte[] tabbarJSON = H5TabbarUtils.getTabDataByAppId(appId);
                    String dataStrRetry = null;
                    if (tabbarJSON != null) {
                        dataStrRetry = new String(tabbarJSON);
                    }
                    H5Log.d("H5SessionTabInfoParser", "##tabbar## getOfflineData else retry " + dataStrRetry);
                    if (!TextUtils.isEmpty(dataStrRetry) && !TextUtils.equals(dataStrRetry, "stupid")) {
                        a(h5SessionTabInfoListener, dataStrRetry, appId);
                    } else if (!TextUtils.isEmpty(dataStrRetry) && TextUtils.equals(dataStrRetry, "stupid")) {
                        a(h5SessionTabInfoListener);
                    } else if (h5SessionTabInfoListener != null) {
                        H5Log.d("H5SessionTabInfoParser", "##tabbar## getOfflineData onShowDefaultTab()");
                        h5SessionTabInfoListener.onShowDefaultTab();
                    }
                } catch (Exception e) {
                    H5Log.e("H5SessionTabInfoParser", "catch exception ", e);
                }
            } else {
                a(h5SessionTabInfoListener);
            }
        }
    }

    private static void a(H5SessionTabInfoListener h5SessionTabInfoListener) {
        if (h5SessionTabInfoListener != null) {
            H5Log.d("H5SessionTabInfoParser", "##tabbar## getOfflineData onGetSyncData(null)");
            h5SessionTabInfoListener.onGetSyncData(null);
        }
    }

    private static void a(H5SessionTabInfoListener h5SessionTabInfoListener, String dataStr, String appId) {
        if (dataStr.startsWith("http")) {
            getOnlineData(dataStr, h5SessionTabInfoListener, appId);
            return;
        }
        JSONObject dataObj = H5Utils.parseObject(dataStr);
        if (h5SessionTabInfoListener != null) {
            H5Log.d("H5SessionTabInfoParser", "##tabbar## getOfflineData onGetSyncData(dataObj)");
            h5SessionTabInfoListener.onGetSyncData(dataObj);
        }
    }

    public static void getOnlineData(final String url, final H5SessionTabInfoListener h5SessionTabInfoListener, final String appId) {
        if (h5SessionTabInfoListener != null) {
            H5Log.d("H5SessionTabInfoParser", "##tabbar## getOnlineData onShowDefaultTab()");
            h5SessionTabInfoListener.onShowDefaultTab();
        }
        H5Utils.getExecutor("RPC").execute(new Runnable() {
            public final void run() {
                H5FallbackStreamProvider fallbackStreamProvider = (H5FallbackStreamProvider) H5Utils.getProvider(H5FallbackStreamProvider.class.getName());
                if (fallbackStreamProvider == null) {
                    H5Log.d("H5SessionTabInfoParser", "##tabbar## getOnlineData fallbackStreamProvider == null return");
                    return;
                }
                long time = System.currentTimeMillis();
                JSONObject dataObj = null;
                try {
                    Map headers = new HashMap();
                    headers.put(H5AppHttpRequest.HEADER_ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8,UC/163");
                    headers.put("Accept-Encoding", "gzip, deflate");
                    headers.put("Accept-Language", "zh-CN,en-US;q=0.8");
                    H5CustomHttpResponse res = fallbackStreamProvider.httpRequest(url, "GET", headers, null, 5000, false);
                    if (!(res == null || res.getResStream() == null)) {
                        byte[] data = H5Utils.readBytes(res.getResStream());
                        H5TabbarUtils.setTabData(appId, data);
                        String dataStr = new String(data);
                        H5Log.d("H5SessionTabInfoParser", "##tabbar## getOnlineData from entry onPrepareData " + dataStr + Token.SEPARATOR + (System.currentTimeMillis() - time));
                        dataObj = JSON.parseObject(dataStr);
                    }
                    if (h5SessionTabInfoListener != null) {
                        h5SessionTabInfoListener.onGetAsyncData(dataObj);
                    }
                } catch (Exception e) {
                    H5Log.e((String) "H5SessionTabInfoParser", (Throwable) e);
                    if (h5SessionTabInfoListener != null) {
                        h5SessionTabInfoListener.onGetAsyncData(null);
                    }
                } catch (Throwable th) {
                    if (h5SessionTabInfoListener != null) {
                        h5SessionTabInfoListener.onGetAsyncData(null);
                    }
                    throw th;
                }
            }
        });
    }
}
