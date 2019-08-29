package com.alipay.multimedia.adjuster.adapter;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.multimedia.adjuster.config.ConfigConst;
import com.alipay.multimedia.adjuster.config.ConfigMgr;
import com.alipay.multimedia.adjuster.data.APMImageInfo.CutType;
import com.alipay.multimedia.adjuster.data.APMImageInfo.Format;
import com.alipay.multimedia.adjuster.data.UrlInfo;
import com.alipay.multimedia.adjuster.data.UrlInfo.Size;
import com.alipay.multimedia.adjuster.utils.AliCdnUtils;
import com.alipay.multimedia.adjuster.utils.Log;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APMOssAdapter implements ICdnAdapter {
    private static final String TAG = "APMOssAdapter";
    private static final Log logger = Log.getLogger((String) TAG);
    private static Pattern mCdnRuleRegex;

    public boolean canExecAdapterForUrl(String originUrl) {
        if (TextUtils.isEmpty(originUrl)) {
            return false;
        }
        Uri uri = Uri.parse(originUrl);
        if (TextUtils.isEmpty(uri.getHost()) || originUrl.contains("_") || AliCdnUtils.checkUrlHostWithExact(uri.getHost(), ConfigMgr.getInc().getCdnConfigItem().ossCdnDomainExactBlackList) || AliCdnUtils.checkUrlWithFuzzy(originUrl, ConfigMgr.getInc().getCdnConfigItem().ossCdnDomainFuzzyBlackList) || !AliCdnUtils.checkUrlWithFuzzy(originUrl, ConfigMgr.getInc().getCdnConfigItem().ossCdnDomainList)) {
            return false;
        }
        return true;
    }

    public UrlInfo getBaseUrlAndImageSize(String originUrl) {
        if (TextUtils.isEmpty(originUrl)) {
            return null;
        }
        UrlInfo urlInfo = new UrlInfo();
        urlInfo.baseUrl = originUrl;
        int index = originUrl.lastIndexOf(AUScreenAdaptTool.PREFIX_ID);
        if (index > 0) {
            urlInfo.baseUrl = originUrl.substring(0, index);
            return parseOssRule(urlInfo, originUrl.substring(index, originUrl.length()));
        }
        int baseIndex = originUrl.indexOf(".png");
        if (baseIndex <= 0 || originUrl.length() <= baseIndex + 4) {
            int baseIndex2 = originUrl.indexOf(".jpg");
            if (baseIndex2 <= 0 || originUrl.length() <= baseIndex2 + 4) {
                return urlInfo;
            }
            urlInfo.baseUrl = originUrl.substring(0, baseIndex2 + 4);
            return urlInfo;
        }
        urlInfo.baseUrl = originUrl.substring(0, baseIndex + 4);
        return urlInfo;
    }

    public String adapterCdnZoomResult(String originUrl, Format format, CutType cutType, int quality, int imageWidth, int imageHeight, int sharpValue, Map extras) {
        String zoom;
        if (imageWidth == 0 && imageHeight == 0) {
            zoom = AUScreenAdaptTool.PREFIX_ID;
        } else {
            zoom = String.format("@%dw_%dh_1l", new Object[]{Integer.valueOf(imageWidth), Integer.valueOf(imageHeight)});
        }
        if (CutType.CUT_TYPE_CROP == cutType) {
            zoom = zoom + "_1e_1c";
        }
        if (quality > 0 && quality < 100) {
            zoom = zoom + String.format("_%dq", new Object[]{Integer.valueOf(quality)});
        }
        if (sharpValue > 0) {
            zoom = zoom + String.format("_%ds", new Object[]{Integer.valueOf(sharpValue)});
        }
        if (Format.FORMAT_WEBP == format) {
            return zoom + ".webp";
        }
        if (originUrl.contains(".png")) {
            return zoom + DjangoConstant.SUFFIX_SRC;
        }
        return zoom;
    }

    public boolean hasNotSupportCdnRule(String originUrl, Size size, CutType cutType) {
        if (TextUtils.isEmpty(originUrl)) {
            return false;
        }
        String urlPath = Uri.parse(originUrl).getPath();
        int index = urlPath.indexOf(AUScreenAdaptTool.PREFIX_ID);
        if (index > 0) {
            urlPath = urlPath.substring(index, urlPath.length());
        }
        if (urlPath.contains("_2e") || urlPath.contains("_1e_1c_") || urlPath.contains("-") || (urlPath.contains(MergeUtil.SEPARATOR_KV) && !urlPath.contains("_1l_"))) {
            return true;
        }
        return false;
    }

    public Size adjustImageSize(String originUrl, Size imageSize, Size displaySize, CutType cutType) {
        return imageSize;
    }

    private static UrlInfo parseOssRule(UrlInfo urlInfo, String cdnPart) {
        try {
            if (mCdnRuleRegex == null) {
                mCdnRuleRegex = Pattern.compile(ConfigConst.OSS_ZOOM_REGEX);
            }
            Matcher matcher = mCdnRuleRegex.matcher(cdnPart);
            if (matcher.matches() && matcher.groupCount() == 5) {
                String widthStr = matcher.group(1);
                if (widthStr == null) {
                    widthStr = matcher.group(2);
                }
                String heightStr = matcher.group(3);
                String quality = matcher.group(4);
                int width = 0;
                if (!TextUtils.isEmpty(widthStr)) {
                    width = Integer.parseInt(widthStr);
                }
                int height = 0;
                if (!TextUtils.isEmpty(heightStr)) {
                    height = Integer.parseInt(heightStr);
                }
                urlInfo.originSize = new Size(width, height);
                if (!TextUtils.isEmpty(quality)) {
                    urlInfo.quality = Integer.parseInt(quality);
                }
            }
        } catch (Exception e) {
            logger.e(e, "parseOssRule exp!", new Object[0]);
        }
        return urlInfo;
    }
}
