package com.alipay.multimedia.adjuster.adapter;

import android.net.Uri;
import android.text.TextUtils;
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

public class APMTfsAdapter implements ICdnAdapter {
    private static final String TAG = "APMTfsAdapter";
    private static final Log logger = Log.getLogger((String) TAG);
    private static Pattern mCdnRuleRegex;
    private static Pattern mCdnWHRuleRegex;
    private static Pattern mNewCdnWHRuleRegex;

    public boolean canExecAdapterForUrl(String originUrl) {
        if (TextUtils.isEmpty(originUrl)) {
            return false;
        }
        Uri uri = Uri.parse(originUrl);
        if (TextUtils.isEmpty(uri.getHost()) || AliCdnUtils.checkUrlHostWithExact(uri.getHost(), ConfigMgr.getInc().getCdnConfigItem().tfsCdnDomainExactBlackList) || AliCdnUtils.checkUrlWithFuzzy(originUrl, ConfigMgr.getInc().getCdnConfigItem().tfsCdnDomainFuzzyBlackList) || !AliCdnUtils.checkUrlWithFuzzy(originUrl, ConfigMgr.getInc().getCdnConfigItem().tfsCdnDomainList)) {
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
        String urlSuffix = originUrl;
        int index = originUrl.lastIndexOf("/");
        if (index > 0 && index < originUrl.length()) {
            urlSuffix = originUrl.substring(index + 1, originUrl.length());
        }
        String cdnSuffix = null;
        int index2 = urlSuffix.indexOf("_");
        if (index2 > 0 && index2 < urlSuffix.length()) {
            cdnSuffix = urlSuffix.substring(index2, urlSuffix.length());
        }
        if (!TextUtils.isEmpty(cdnSuffix)) {
            if (cdnSuffix.contains("q90") || cdnSuffix.contains("Q90")) {
                urlInfo.quality = 90;
            } else if (cdnSuffix.contains("q75") || cdnSuffix.contains("Q75")) {
                urlInfo.quality = 75;
            } else if (cdnSuffix.contains("q60") || cdnSuffix.contains("Q60")) {
                urlInfo.quality = 60;
            } else if (cdnSuffix.contains("q50") || cdnSuffix.contains("Q50")) {
                urlInfo.quality = 50;
            } else if (cdnSuffix.contains("q30") || cdnSuffix.contains("Q30")) {
                urlInfo.quality = 30;
            }
            if (ConfigMgr.getInc().getCdnConfigItem().useOldCdnParseImageSizeRegex()) {
                Matcher matcher = parseTfsRule(cdnSuffix);
                if (matcher != null && matcher.matches() && matcher.groupCount() == 5) {
                    int width = Integer.parseInt(matcher.group(2));
                    int height = Integer.parseInt(matcher.group(3));
                    if (width > 0 && height > 0) {
                        urlInfo.originSize = new Size(width, height);
                        if (TextUtils.isEmpty(matcher.group(0))) {
                            return urlInfo;
                        }
                        int baseIndex = originUrl.indexOf(matcher.group(0));
                        if (originUrl.length() <= baseIndex) {
                            return urlInfo;
                        }
                        urlInfo.baseUrl = originUrl.substring(0, baseIndex);
                        return urlInfo;
                    }
                }
                Matcher matcher2 = parseTfsWHRule(cdnSuffix);
                if (matcher2 != null && matcher2.matches() && matcher2.groupCount() == 3) {
                    int width2 = Integer.parseInt(matcher2.group(1));
                    int height2 = Integer.parseInt(matcher2.group(2));
                    if (width2 > 0 && height2 > 0) {
                        urlInfo.originSize = new Size(width2, height2);
                    }
                    urlInfo.baseUrl = originUrl.substring(0, originUrl.length() - matcher2.group(0).length());
                    return urlInfo;
                }
            } else {
                Matcher matcher3 = parseNewTfsRule(originUrl);
                if (matcher3 != null && matcher3.matches() && matcher3.groupCount() > 4) {
                    int width3 = 0;
                    int height3 = 0;
                    if (!TextUtils.isEmpty(matcher3.group(3))) {
                        width3 = Integer.parseInt(matcher3.group(3));
                    }
                    if (!TextUtils.isEmpty(matcher3.group(4))) {
                        height3 = Integer.parseInt(matcher3.group(4));
                    }
                    if (width3 > 0 || height3 > 0) {
                        urlInfo.originSize = new Size(width3, height3);
                        if (TextUtils.isEmpty(matcher3.group(1))) {
                            return urlInfo;
                        }
                        urlInfo.baseUrl = matcher3.group(1);
                        return urlInfo;
                    }
                }
            }
        }
        int baseIndex2 = originUrl.indexOf(".png");
        if (baseIndex2 <= 0 || originUrl.length() <= baseIndex2 + 4) {
            int baseIndex3 = originUrl.indexOf(".jpg");
            if (baseIndex3 <= 0 || originUrl.length() <= baseIndex3 + 4) {
                return urlInfo;
            }
            urlInfo.baseUrl = originUrl.substring(0, baseIndex3 + 4);
            return urlInfo;
        }
        urlInfo.baseUrl = originUrl.substring(0, baseIndex2 + 4);
        return urlInfo;
    }

    public String adapterCdnZoomResult(String originUrl, Format format, CutType cutType, int quality, int imageWidth, int imageHeight, int sharpValue, Map extras) {
        String zoom;
        if (imageWidth == 0 && imageHeight == 0) {
            zoom = "_";
        } else {
            zoom = String.format("_%dx%d", new Object[]{Integer.valueOf(imageWidth), Integer.valueOf(imageHeight)});
        }
        if (!(CutType.CUT_TYPE_CROP != cutType || imageWidth == 0 || imageHeight == 0)) {
            zoom = zoom + "xz";
        }
        if (quality > 0 && quality <= 100) {
            zoom = zoom + String.format("q%d", new Object[]{Integer.valueOf(quality)});
        }
        if (sharpValue > 0) {
            zoom = zoom + String.format("s%d", new Object[]{Integer.valueOf(sharpValue)});
        }
        if (Format.FORMAT_HEIC == format) {
            return zoom + "_.heic";
        }
        if (Format.FORMAT_WEBP == format) {
            return zoom + ".jpg_.webp";
        }
        return zoom + ".jpg";
    }

    public boolean hasNotSupportCdnRule(String originUrl, Size size, CutType cutType) {
        if (TextUtils.isEmpty(originUrl) || size == null || cutType == null) {
            return false;
        }
        Uri uri = Uri.parse(originUrl);
        if (TextUtils.isEmpty(uri.getPath())) {
            return false;
        }
        if (CutType.CUT_TYPE_CROP == cutType && ((double) Math.abs((size.mWidth / size.mHeight) - 1)) > 0.1d) {
            return true;
        }
        String urlPath = uri.getPath();
        int index = urlPath.indexOf("_");
        if (index <= 0 || index >= urlPath.length()) {
            return false;
        }
        String urlPath2 = urlPath.substring(index, urlPath.length());
        if (urlPath2.indexOf("xz") > 0 || urlPath2.indexOf("co0") > 0) {
            return true;
        }
        if (urlPath2.matches("cy(\\d+)i|cx(\\d+)i")) {
            return true;
        }
        return false;
    }

    public Size adjustImageSize(String originUrl, Size imageSize, Size displaySize, CutType cutType) {
        Size finalImageSize;
        if (imageSize == null) {
            return imageSize;
        }
        int finalWidth = imageSize.mWidth;
        int finalHeight = imageSize.mHeight;
        if (imageSize.mHeight >= 10000) {
            int newLength = imageSize.mWidth;
            if (displaySize != null && displaySize.mWidth > 0) {
                newLength = (int) scaleSideLengthForOriginalLength((float) displaySize.mWidth);
            }
            finalImageSize = new Size(adaptWidthOf10000HeightWithImageWidth(newLength), finalHeight);
        } else if (imageSize.mWidth >= 10000) {
            int newLength2 = imageSize.mHeight;
            if (displaySize != null && displaySize.mHeight > 0) {
                newLength2 = (int) scaleSideLengthForOriginalLength((float) displaySize.mHeight);
            }
            finalImageSize = new Size(finalWidth, adaptHeightOf10000WidthWithImageHeight(newLength2));
        } else {
            finalImageSize = adaptImageSizeWithImageSize(imageSize, cutType);
        }
        return finalImageSize;
    }

    private float scaleSideLengthForOriginalLength(float originalLength) {
        float scale = (float) AliCdnUtils.getScreenScale();
        if (scale > 2.0f) {
            return originalLength * 3.0f;
        }
        if (scale > 1.0f) {
            return originalLength * 2.0f;
        }
        return originalLength;
    }

    public int adaptWidthOf10000HeightWithImageWidth(int imageWidth) {
        int width = imageWidth;
        int preWidth = 0;
        int[] widthOf10000HeightArray = ConfigMgr.getInc().getCdnConfigItem().cdnWidthListOf10000Height;
        int length = widthOf10000HeightArray.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int cdnWidth = widthOf10000HeightArray[i];
            if (cdnWidth < imageWidth) {
                preWidth = cdnWidth;
                i++;
            } else if (cdnWidth - imageWidth > imageWidth - preWidth) {
                width = preWidth;
            } else {
                width = cdnWidth;
            }
        }
        if (width < widthOf10000HeightArray[0]) {
            width = widthOf10000HeightArray[0];
        }
        if (width > widthOf10000HeightArray[widthOf10000HeightArray.length - 1]) {
            return widthOf10000HeightArray[widthOf10000HeightArray.length - 1];
        }
        return width;
    }

    public int adaptHeightOf10000WidthWithImageHeight(int imageHeight) {
        int height = imageHeight;
        int preHeight = 0;
        int[] heightOf10000WidthArray = ConfigMgr.getInc().getCdnConfigItem().cdnHeightListOf10000Width;
        int length = heightOf10000WidthArray.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int cdnHeight = heightOf10000WidthArray[i];
            if (cdnHeight < imageHeight) {
                preHeight = cdnHeight;
                i++;
            } else if (cdnHeight - imageHeight > imageHeight - preHeight) {
                height = preHeight;
            } else {
                height = cdnHeight;
            }
        }
        if (height < heightOf10000WidthArray[0]) {
            height = heightOf10000WidthArray[0];
        }
        if (height > heightOf10000WidthArray[heightOf10000WidthArray.length - 1]) {
            return heightOf10000WidthArray[heightOf10000WidthArray.length - 1];
        }
        return height;
    }

    public Size adaptImageSizeWithImageSize(Size imageSize, CutType cutType) {
        int[] cdnSizes;
        int currentImageSize = Math.max(imageSize.mWidth, imageSize.mHeight);
        int preSize = 0;
        if (CutType.CUT_TYPE_SCALE == cutType || CutType.CUT_TYPE_CROP != cutType) {
            cdnSizes = ConfigMgr.getInc().getCdnConfigItem().cdnImageSizeList;
        } else {
            cdnSizes = ConfigMgr.getInc().getCdnConfigItem().cdnXZImageSizeList;
        }
        int length = cdnSizes.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int cdnsize = cdnSizes[i];
            if (cdnsize < currentImageSize) {
                preSize = cdnsize;
                i++;
            } else if (((float) currentImageSize) >= ((float) preSize) * 1.0f) {
                currentImageSize = cdnsize;
            } else {
                currentImageSize = preSize;
            }
        }
        if (currentImageSize < cdnSizes[0]) {
            currentImageSize = cdnSizes[0];
        }
        if (currentImageSize > cdnSizes[cdnSizes.length - 1]) {
            currentImageSize = cdnSizes[cdnSizes.length - 1];
        }
        return new Size(currentImageSize, currentImageSize);
    }

    private static Matcher parseTfsRule(String cdnPart) {
        try {
            if (mCdnRuleRegex == null) {
                mCdnRuleRegex = Pattern.compile(ConfigConst.TFS_ZOOM_REGEX);
            }
            return mCdnRuleRegex.matcher(cdnPart);
        } catch (Exception e) {
            logger.e(e, "parseTfsRule exp!", new Object[0]);
            return null;
        }
    }

    private static Matcher parseTfsWHRule(String cdnPart) {
        try {
            if (mCdnWHRuleRegex == null) {
                mCdnWHRuleRegex = Pattern.compile(ConfigConst.TFS_ZOOM_WH_REGEX);
            }
            return mCdnWHRuleRegex.matcher(cdnPart);
        } catch (Exception e) {
            logger.e(e, "parseTfsWHRule exp!", new Object[0]);
            return null;
        }
    }

    private static Matcher parseNewTfsRule(String cdnPart) {
        try {
            if (mNewCdnWHRuleRegex == null) {
                mNewCdnWHRuleRegex = Pattern.compile(ConfigMgr.getInc().getCdnConfigItem().tfsCdnParseImageSizeRegex);
            }
            return mNewCdnWHRuleRegex.matcher(cdnPart);
        } catch (Exception e) {
            logger.e(e, "parseNewTfsRule exp!", new Object[0]);
            return null;
        }
    }
}
