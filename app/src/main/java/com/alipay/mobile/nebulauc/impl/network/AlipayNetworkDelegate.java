package com.alipay.mobile.nebulauc.impl.network;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.uc.webview.export.internal.interfaces.INetworkDelegate;
import com.uc.webview.export.internal.interfaces.IRequestData;
import com.uc.webview.export.internal.interfaces.IResponseData;
import java.util.Iterator;
import java.util.Map;

public class AlipayNetworkDelegate implements INetworkDelegate {
    private static String TAG = "AlipayNetworkDelegate";

    public IRequestData onSendRequest(IRequestData iRequestData) {
        String url;
        String url2 = iRequestData.getUrl();
        H5Log.d(TAG, "onSendRequest " + url2);
        if (shouldUseWebPImage(url2)) {
            int index = url2.indexOf("?");
            if (index < 0) {
                url = url2 + "_.webp";
            } else {
                url = url2.substring(0, index) + "_.webp" + url2.substring(index);
            }
            iRequestData.setUrl(url);
            H5Log.d(TAG, "will load webp Image " + url);
        }
        Map headers = iRequestData.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            Iterator<String> it = headers.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String key = it.next();
                H5Log.d(TAG, "key " + key + Token.SEPARATOR + headers.get(key));
                if ("Accept-Language".equalsIgnoreCase(key)) {
                    headers.put(key, AlipayNetworkLanUtils.getAcceptLanguageValue());
                    break;
                }
            }
            iRequestData.setHeaders(headers);
        }
        return iRequestData;
    }

    public IResponseData onReceiveResponse(IResponseData iResponseData) {
        H5Log.d(TAG, "onReceiveResponse");
        return iResponseData;
    }

    private boolean shouldUseWebPImage(String url) {
        return url != null && shouldUseWebPImage(Uri.parse(url));
    }

    private boolean isValidImageType(String fileName, JSONArray imageTypes) {
        if (TextUtils.isEmpty(fileName) || imageTypes == null) {
            return false;
        }
        for (int index = 0; index < imageTypes.size(); index++) {
            if (fileName.endsWith(imageTypes.getString(index))) {
                return true;
            }
        }
        return false;
    }

    private boolean shouldUseWebPImage(Uri uri) {
        JSONObject object = H5ConfigUtil.getConfigJSONObject(H5Utils.KEY_H5_CDN_WEBP_CONFIG);
        JSONArray cdnDomains = H5Utils.getJSONArray(object, "cdnDomains", null);
        if (isValidImageType(uri.getPath(), H5Utils.getJSONArray(object, "imageTypes", null)) && cdnDomains != null && cdnDomains.size() > 0) {
            String host = uri.getHost();
            for (int index = 0; index < cdnDomains.size(); index++) {
                String domain = cdnDomains.getString(index);
                if (!TextUtils.isEmpty(domain) && domain.equals(host)) {
                    return true;
                }
            }
            H5Log.d(TAG, "not use webp image, beauseof " + host + " not in whitelist");
        }
        return false;
    }
}
