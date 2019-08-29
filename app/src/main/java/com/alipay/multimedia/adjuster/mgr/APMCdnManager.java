package com.alipay.multimedia.adjuster.mgr;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.multimedia.adjuster.adapter.APMOssAdapter;
import com.alipay.multimedia.adjuster.adapter.APMTfsAdapter;
import com.alipay.multimedia.adjuster.adapter.ICdnAdapter;
import com.alipay.multimedia.adjuster.api.data.IConfig;
import com.alipay.multimedia.adjuster.api.data.ITraceId;
import com.alipay.multimedia.adjuster.builder.UriBuilder;
import com.alipay.multimedia.adjuster.config.ConfigMgr;
import com.alipay.multimedia.adjuster.config.HostItem;
import com.alipay.multimedia.adjuster.data.APMImageInfo.CutType;
import com.alipay.multimedia.adjuster.data.APMImageInfo.Format;
import com.alipay.multimedia.adjuster.data.UrlInfo;
import com.alipay.multimedia.adjuster.data.UrlInfo.Size;
import com.alipay.multimedia.adjuster.utils.AliCdnUtils;
import com.alipay.multimedia.adjuster.utils.Log;
import java.util.ArrayList;

public class APMCdnManager {
    private static final Log logger = Log.getLogger((String) "APMCdnManager");
    private static APMCdnManager mInstance;
    private APMOssAdapter mOssAdapter;
    private APMTfsAdapter mTfsAdapter;

    private APMCdnManager() {
        this.mOssAdapter = null;
        this.mTfsAdapter = null;
        this.mOssAdapter = new APMOssAdapter();
        this.mTfsAdapter = new APMTfsAdapter();
    }

    public static APMCdnManager getIns() {
        if (mInstance == null) {
            synchronized (APMCdnManager.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new APMCdnManager();
                    }
                }
            }
        }
        return mInstance;
    }

    public void registerConfig(IConfig config) {
        ConfigMgr.getInc().registerConfig(config);
    }

    public void registerITraceId(ITraceId traceIdBuilder) {
        UriBuilder.registerITraceId(traceIdBuilder);
    }

    private ICdnAdapter getAdapter(String originUrl) {
        if (TextUtils.isEmpty(originUrl)) {
            return null;
        }
        if (this.mOssAdapter.canExecAdapterForUrl(originUrl)) {
            return this.mOssAdapter;
        }
        if (this.mTfsAdapter.canExecAdapterForUrl(originUrl)) {
            return this.mTfsAdapter;
        }
        return null;
    }

    public boolean canExecAdapterForUrl(String originUrl) {
        return getAdapter(originUrl) != null;
    }

    private boolean supportCdnRuleForURL(String url, Size imageSize, CutType cutType, ICdnAdapter adapter) {
        if (adapter == null || adapter.hasNotSupportCdnRule(url, imageSize, cutType) || url.contains("%")) {
            return false;
        }
        return true;
    }

    public String parseImageUrlForAliCdn(String originUrl, Format format, Size imageSize, Size viewSize, CutType cutType) {
        String str;
        String str2;
        String destUrl = originUrl;
        try {
            ICdnAdapter adapter = getAdapter(originUrl);
            if (!supportCdnRuleForURL(originUrl, imageSize, cutType, adapter)) {
                return originUrl;
            }
            Uri uri = Uri.parse(originUrl);
            String urlPath = uri.getPath();
            int urlPathCutLength = 0;
            if (!uri.getHost().contains("ossgw.alicdn.com")) {
                if (urlPath.endsWith("_sum.jpg")) {
                    urlPathCutLength = 8;
                    urlPath = urlPath.substring(0, urlPath.length() - 8);
                } else if (urlPath.endsWith("_m.jpg") || urlPath.endsWith("_b.jpg")) {
                    urlPathCutLength = 6;
                    urlPath = urlPath.substring(0, urlPath.length() - 6);
                }
            }
            Object[] objArr = new Object[4];
            if (!TextUtils.isEmpty(uri.getScheme())) {
                str = String.format("%s:", new Object[]{uri.getScheme()});
            } else {
                str = "";
            }
            objArr[0] = str;
            objArr[1] = uri.getHost();
            if (uri.getPort() > 0) {
                str2 = String.format(":%d", new Object[]{Integer.valueOf(uri.getPort())});
            } else {
                str2 = "";
            }
            objArr[2] = str2;
            objArr[3] = urlPath;
            String urlString = String.format("%s//%s%s%s", objArr);
            String absolutedUrlString = AliCdnUtils.getUrlDecoderString(destUrl, "UTF8");
            String urlSuffix = "";
            if (absolutedUrlString.length() > urlString.length() + urlPathCutLength) {
                urlSuffix = absolutedUrlString.substring(urlString.length() + urlPathCutLength, absolutedUrlString.length());
            }
            if (urlPathCutLength > 0) {
                destUrl = urlString;
            }
            UrlInfo baseUrlInfo = adapter.getBaseUrlAndImageSize(urlString);
            Size urlImageSize = null;
            if (baseUrlInfo != null) {
                String baseUrl = baseUrlInfo.baseUrl;
                if (TextUtils.isEmpty(baseUrl)) {
                    return destUrl;
                }
                urlString = baseUrl;
                urlImageSize = baseUrlInfo.originSize;
            }
            Size finalImageSize = imageSize;
            if (imageSize != null && imageSize.mWidth > 0 && imageSize.mHeight > 0) {
                finalImageSize = adapter.adjustImageSize(destUrl, imageSize, new Size(0, 0), cutType);
            } else if (urlImageSize != null && urlImageSize.mWidth > 0 && urlImageSize.mHeight > 0) {
                finalImageSize = adapter.adjustImageSize(destUrl, urlImageSize, viewSize, cutType);
            } else if (viewSize != null && viewSize.mWidth > 0 && viewSize.mHeight > 0) {
                Size size = new Size(viewSize.mWidth * AliCdnUtils.getScreenScale(), viewSize.mHeight * AliCdnUtils.getScreenScale());
                finalImageSize = adapter.adjustImageSize(destUrl, size, new Size(0, 0), cutType);
            }
            int quality = ConfigMgr.getInc().getCdnConfigItem().mQuality;
            int sharp = ConfigMgr.getInc().getCdnConfigItem().mSharpValue;
            Format destFormat = format;
            if (!ConfigMgr.getInc().getCdnConfigItem().isSupportWebp() && destFormat == Format.FORMAT_WEBP) {
                destFormat = null;
            }
            String cdnZoom = adapter.adapterCdnZoomResult(originUrl, destFormat, cutType, quality, finalImageSize.mWidth, finalImageSize.mHeight, sharp, null);
            if (!TextUtils.isEmpty(cdnZoom)) {
                urlString = urlString + cdnZoom;
            }
            String urlString2 = urlString + urlSuffix;
            if (AliCdnUtils.checkUrl(urlString2)) {
                return urlString2;
            }
            return originUrl;
        } catch (Exception e) {
            logger.e(e, "parseImageUrlForAliCdn exp", new Object[0]);
            return originUrl;
        }
    }

    public ArrayList<String> buildHighAvailabilityUrls(String url, String bizType) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        ArrayList urls = new ArrayList();
        try {
            HostItem item = checkHighAvailability(url, bizType);
            if (item == null || !item.checkPercent()) {
                urls.add(url);
                return urls;
            }
            String cdnUrl = url;
            if (item.checkGraySwitch(AliCdnUtils.generateRandom(0, 100))) {
                cdnUrl = UriBuilder.buildAftsFilecCdnUrl(url, bizType);
            }
            String masterUrl = UriBuilder.buildAftsFileMasterUrl(url, bizType);
            if (!TextUtils.isEmpty(cdnUrl)) {
                urls.add(cdnUrl);
            }
            if (TextUtils.isEmpty(masterUrl)) {
                return urls;
            }
            urls.add(masterUrl);
            return urls;
        } catch (Exception e) {
            urls.clear();
            urls.add(url);
            logger.e(e, "buildHighAvailabilityUrls exp", new Object[0]);
            return urls;
        }
    }

    private HostItem checkHighAvailability(String url, String bizType) {
        return ConfigMgr.getInc().getCdnConfigItem().checkHighAvailability(url, bizType);
    }
}
