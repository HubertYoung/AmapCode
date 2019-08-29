package com.alipay.multimedia.adjuster.api;

import com.alipay.multimedia.adjuster.api.data.IConfig;
import com.alipay.multimedia.adjuster.api.data.ITraceId;
import com.alipay.multimedia.adjuster.data.APMImageInfo.CutType;
import com.alipay.multimedia.adjuster.data.APMImageInfo.Format;
import com.alipay.multimedia.adjuster.data.UrlInfo.Size;
import com.alipay.multimedia.adjuster.mgr.APMCdnManager;
import com.alipay.multimedia.adjuster.utils.Log;
import com.alipay.multimedia.adjuster.utils.Logger;
import java.util.ArrayList;

public class APMAdjuster {
    public static void registerConfig(IConfig config) {
        APMCdnManager.getIns().registerConfig(config);
    }

    public static void registerITraceId(ITraceId traceIdBuilder) {
        APMCdnManager.getIns().registerITraceId(traceIdBuilder);
    }

    public static void registerLogger(Logger logger) {
        Log.setLogger(logger);
    }

    public static ArrayList<String> buildHighAvailabilityUrls(String url, String bizType) {
        return APMCdnManager.getIns().buildHighAvailabilityUrls(url, bizType);
    }

    public static boolean canExecAdapterForUrl(String originUrl) {
        return APMCdnManager.getIns().canExecAdapterForUrl(originUrl);
    }

    public static String parseImageUrlForAliCdn(String originUrl, Format format, Size imageSize, Size viewSize, CutType cutType) {
        return APMCdnManager.getIns().parseImageUrlForAliCdn(originUrl, format, imageSize, viewSize, cutType);
    }
}
