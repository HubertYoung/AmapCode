package com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.adapter;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.UriFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.UriFactory.Request;
import java.util.HashMap;
import java.util.Map;

public class ImageRequestAdapter {
    public static String buildUrl(ImageLoadReq req, Bundle extraConfig) {
        Map params = new HashMap();
        Request request = new Request(1);
        request.params = params;
        params.put("zoom", ZoomHelper.getZoom(req));
        if (extraConfig != null) {
            request.preferHttps = extraConfig.getBoolean("https", false);
            if (extraConfig.containsKey("publicDomain")) {
                request.isPublic = extraConfig.getBoolean("publicDomain", false);
            }
        }
        return UriFactory.buildGetUrl(req.path, request);
    }
}
