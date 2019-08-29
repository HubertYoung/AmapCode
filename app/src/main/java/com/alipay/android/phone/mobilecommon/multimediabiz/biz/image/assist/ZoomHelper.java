package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ZoomHelper {
    public static final int MAX_SUPER_SIZE = 16000;
    public static String OSS_WH_PATTERN = "([0-9]+w_[0-9]+h)|([0-9]+h_[0-9]+w)";
    public static final String TAG = "ZoomHelper";
    public static String TFS_WH_PATTERN = "^[0-9]+x[0-9]+";
    private static String a = "((^[1-9]\\d*)|(^0))x(([1-9]\\d*)|0)([qQ]([1-9]\\d?|100))?(_\\.webp|\\.jpg|\\.png|\\.gif|\\.webp|\\.src|\\.ico|\\.bmp|\\.wbmp|\\.tiff|\\.tif|\\.jpeg|\\.jfif|\\.jpe)?$";
    private static String b = "((^[1-9]\\d*)|(^0))x(([1-9]\\d*)|0)(xc)?([qQ]([1-9]\\d?|100))?(_\\.webp|\\.jpg|\\.png|\\.gif|\\.webp|\\.src|\\.ico|\\.bmp|\\.wbmp|\\.tiff|\\.tif|\\.jpeg|\\.jfif|\\.jpe)?$";
    private static String c = "((^[1-9]\\d*)|(^0))x(([1-9]\\d*)|0)(xz)?([qQ]([1-9]\\d?|100))?(_\\.webp|\\.jpg|\\.png|\\.gif|\\.webp|\\.src|\\.ico|\\.bmp|\\.wbmp|\\.tiff|\\.tif|\\.jpeg|\\.jfif|\\.jpe)?$";
    private static final Map<CutScaleType, String> d;

    static {
        HashMap hashMap = new HashMap();
        d = hashMap;
        hashMap.put(CutScaleType.REGION_CROP_LEFT_TOP, "4-2");
        d.put(CutScaleType.REGION_CROP_CENTER_TOP, "5-2");
        d.put(CutScaleType.REGION_CROP_RIGHT_TOP, "6-2");
        d.put(CutScaleType.REGION_CROP_LEFT_CENTER, "4-5");
        d.put(CutScaleType.REGION_CROP_CENTER_CENTER, "5-5");
        d.put(CutScaleType.REGION_CROP_RIGHT_CENTER, "6-5");
        d.put(CutScaleType.REGION_CROP_LEFT_BOTTOM, "4-8");
        d.put(CutScaleType.REGION_CROP_CENTER_BOTTOM, "5-8");
        d.put(CutScaleType.REGION_CROP_RIGHT_BOTTOM, "6-8");
        d.put(CutScaleType.SMART_CROP, "5-2");
    }

    public static String getZoom(ImageLoadReq req) {
        String zoomVal;
        if (req.urlToDjango) {
            zoomVal = req.getAdjustedZoom();
        } else {
            zoomVal = a(req, req.options.getCutScaleType());
            if (req.options.isUsingSourceType() && (!req.options.getCutScaleType().isSmartCrop() || !ConfigManager.getInstance().supportSmartCrop())) {
                zoomVal = zoomVal + DjangoConstant.SUFFIX_SRC;
            }
        }
        Logger.D(TAG, "getZoom final zoomVal: " + zoomVal, new Object[0]);
        return zoomVal;
    }

    public static String getSecondaryZoom(ImageLoadReq req) {
        if (req.options.secondaryCutScaleType == null) {
            return null;
        }
        String zoomVal = a(req, req.options.secondaryCutScaleType);
        if (req.options.isUsingSourceType() && (!req.options.getCutScaleType().isSmartCrop() || !ConfigManager.getInstance().supportSmartCrop())) {
            zoomVal = zoomVal + DjangoConstant.SUFFIX_SRC;
        }
        Logger.D(TAG, "getZoom final zoomVal: " + zoomVal, new Object[0]);
        return zoomVal;
    }

    public static boolean imageProgressiveSupport(DisplayImageOptions options) {
        return options != null && options.isProgressive() && ConfigManager.getInstance().progressiveConfigSwitch() && CommonUtils.progressiveMobileNetwork() && !options.isDetectedGif();
    }

    private static String a(ImageLoadReq req, CutScaleType cutScaleType) {
        String zoomVal;
        DisplayImageOptions options = req.options;
        boolean progressiveSupport = imageProgressiveSupport(options);
        String progressive = progressiveSupport ? DjangoConstant.PROGRESSIVE_KEY : "";
        int width = options.getWidth().intValue();
        int height = options.getHeight().intValue();
        Size size = options.getOriginalSize();
        if (width == 0 && height == 0) {
            return formatOssZoomExtra(req, progressiveSupport, String.format("%dw_%dh_1l%s", new Object[]{Integer.valueOf(1280), Integer.valueOf(1280), progressive}));
        } else if ((width == -1 && height == -1) || (width == Integer.MAX_VALUE && height == Integer.MAX_VALUE)) {
            return "original";
        } else {
            if (width > 16000 || height > 16000) {
                width = 16000;
                height = 16000;
            }
            if (CutScaleType.CENTER_CROP.equals(cutScaleType) && size != null) {
                int[] newWH = options.getScale().floatValue() == 0.5f ? size.getWidth() > size.getHeight() ? new int[]{375, 187} : new int[]{187, 375} : FalconFacade.get().calculateCutImageRect(size.getWidth(), size.getHeight(), Math.max(width, height), options.getScale().floatValue(), null);
                zoomVal = String.format("%dw_%dh_1e_1c%s", new Object[]{Integer.valueOf(newWH[0]), Integer.valueOf(newWH[1]), progressive});
            } else if (CutScaleType.AUTO_CUT_EXACTLY.equals(cutScaleType)) {
                Size s = new Size(width, height);
                if (ConfigManager.getInstance().djangoConf().isImgCutPreSet()) {
                    s = PathUtils.getDjangoNearestCutImageSize(s);
                }
                zoomVal = String.format("%dw_%dh_1e_1c%s", new Object[]{Integer.valueOf(s.getWidth()), Integer.valueOf(s.getHeight()), progressive});
            } else if (cutScaleType.isRegionCrop() || (!ConfigManager.getInstance().supportSmartCrop() && cutScaleType.isSmartCrop())) {
                if (ConfigManager.getInstance().supportRegionCrop()) {
                    zoomVal = String.format("%dw_%dh_1e%s|%dx%d-%src", new Object[]{Integer.valueOf(width), Integer.valueOf(height), progressive, Integer.valueOf(width), Integer.valueOf(height), a(cutScaleType)});
                } else {
                    zoomVal = String.format("%dw_%dh_1e_1c%s", new Object[]{Integer.valueOf(width), Integer.valueOf(height), progressive});
                }
            } else if (cutScaleType.isSmartCrop()) {
                zoomVal = String.format((!req.isEnableSaliency() || req.getTransportWay() != 2) ? "%sw_%sh%s_zn" : "%sw_%sh%s_zx", new Object[]{Integer.valueOf(width), Integer.valueOf(height), progressive});
            } else {
                zoomVal = String.format("%dw_%dh_1l%s", new Object[]{Integer.valueOf(width), Integer.valueOf(height), progressive});
            }
            String zoomVal2 = formatOssZoomExtra(req, progressiveSupport, zoomVal);
            Logger.D(TAG, "getOssZoom width: %s, height: %s, cutType: %s, size: %s, zoomVal: %s", Integer.valueOf(width), Integer.valueOf(height), cutScaleType, size, zoomVal2);
            return zoomVal2;
        }
    }

    public static String formatOssZoomExtra(ImageLoadReq req, boolean progressiveSupport, String zoomVal) {
        StringBuilder append;
        String str;
        if (req.options.getQuality() != -1 && req.options.getQuality() > 0 && req.options.getQuality() < 100) {
            zoomVal = req.options.getQuality() + "q_" + zoomVal;
        } else if (!ConfigManager.getInstance().closeDefaultQualitySwitch()) {
            zoomVal = "80q_" + zoomVal;
        }
        if (!a() || progressiveSupport) {
            if (!progressiveSupport) {
                return zoomVal;
            }
            append = new StringBuilder().append(zoomVal);
            if (a(req)) {
                str = String.format("%s%s", new Object[]{"_df", ".jpg"});
            } else {
                str = ".jpg";
            }
        } else if (a(req)) {
            String gifMark = "";
            if (req.options.isDetectedGif()) {
                gifMark = "_mf";
            } else if (req.options.isUsingSourceType()) {
                gifMark = "_sf";
            }
            return a(req, zoomVal, gifMark);
        } else if (req.options.isDetectedGif() || req.options.isUsingSourceType()) {
            return zoomVal;
        } else {
            if (ConfigManager.getInstance().isEnableHevc() && req.getTransportWay() == 2 && !req.isEncryptRequest()) {
                return zoomVal + DjangoConstant.SUFFIX_AHP + ImageFileType.getHevcVer();
            }
            if (!ConfigManager.getInstance().getAftsLinkConf().checkWebpFormat()) {
                return zoomVal;
            }
            append = new StringBuilder().append(zoomVal);
            str = ".webp";
        }
        return append.append(str).toString();
    }

    private static String a(ImageLoadReq req, String zoomVal, String gifMark) {
        boolean enanbleWebpFormt = ConfigManager.getInstance().getAftsLinkConf().checkWebpFormat();
        if (ConfigManager.getInstance().isEnableHevc() && !req.isEncryptRequest()) {
            return zoomVal + String.format("%s%s%s%d%s", new Object[]{gifMark, "_df", DjangoConstant.SUFFIX_AHP, Integer.valueOf(ImageFileType.getHevcVer()), enanbleWebpFormt ? ".webp" : ""});
        } else if (!enanbleWebpFormt) {
            return zoomVal;
        } else {
            return zoomVal + String.format("%s%s%s", new Object[]{gifMark, "_df", ".webp"});
        }
    }

    private static boolean a(ImageLoadReq req) {
        return req.bAftsLink && ConfigManager.getInstance().getAftsLinkConf().isUseAftsDynamicFormat() && !req.isEnableSaliency() && !req.options.cutScaleType.isSmartCrop();
    }

    private static String a(CutScaleType cutScaleType) {
        return d.get(cutScaleType);
    }

    public static int[] getFitSize(Context context, Size srcSize, int width, int height) {
        int[] defaultWh = ImageUtils.getScaleScreenRect(context, 1.0f);
        int[] screenRect = ImageUtils.getScaleScreenRect(context, 1.2f);
        float defaultRatio = ((float) defaultWh[0]) / ((float) defaultWh[1]);
        if (defaultWh[0] < defaultWh[1]) {
            if (defaultWh[1] > 1280) {
                defaultWh[1] = 1280;
                defaultWh[0] = (int) (((float) defaultWh[1]) * defaultRatio);
            }
        } else if (defaultWh[0] > 1280) {
            defaultWh[0] = 1280;
            defaultWh[1] = (int) (((float) defaultWh[0]) / defaultRatio);
        }
        if (width == 0 || height == 0) {
            Logger.D(TAG, "getFitSize big width: %s, height: %s, srcSize: %s, outSize: %s", Integer.valueOf(width), Integer.valueOf(height), srcSize, Arrays.toString(defaultWh));
            return defaultWh;
        } else if (width == Integer.MAX_VALUE || height == Integer.MAX_VALUE) {
            if (srcSize == null || a(srcSize.getWidth(), srcSize.getHeight())) {
                defaultWh = a(context, srcSize, srcSize != null ? Math.min(Math.max(srcSize.getWidth(), srcSize.getHeight()), 16000) : 16000, 1.1f);
            } else {
                int min = Math.min(Math.max(Math.max(screenRect[0], screenRect[1]), 2000), Math.max(srcSize.getWidth(), srcSize.getHeight()));
                defaultWh[1] = min;
                defaultWh[0] = min;
            }
            Logger.D(TAG, "getFitSize Original width: %s, height: %s, srcSize: %s, outSize: %s", Integer.valueOf(width), Integer.valueOf(height), srcSize, Arrays.toString(defaultWh));
            return defaultWh;
        } else {
            boolean superThumb = width > screenRect[0] || height > screenRect[1];
            int maxSide = Math.max(width, height);
            if (srcSize != null && !a(srcSize.getWidth(), srcSize.getHeight()) && superThumb) {
                int min2 = Math.min(2000, maxSide);
                defaultWh[1] = min2;
                defaultWh[0] = min2;
            } else if (superThumb) {
                defaultWh = a(context, srcSize, Math.min(maxSide, 16000), 0.9f);
            } else {
                defaultWh[0] = width;
                defaultWh[1] = height;
            }
            Logger.D(TAG, "getFitSize thumb width: %s, height: %s, srcSize: %s, outSize: %s", Integer.valueOf(width), Integer.valueOf(height), srcSize, Arrays.toString(defaultWh));
            return defaultWh;
        }
    }

    private static boolean a(int width, int height) {
        if (width <= 0 || height <= 0) {
            return true;
        }
        float scale_ori = ((float) width) / ((float) height);
        if (scale_ori < 0.5f || scale_ori > 2.0f) {
            return true;
        }
        return false;
    }

    private static int[] a(Context context, Size srcSize, int maxSide, float screenScale) {
        int maxSide2;
        int[] outSize = {maxSide, maxSide};
        if (!(srcSize == null || context == null)) {
            int[] screenSize = ImageUtils.getScaleScreenRect(context, screenScale);
            float scale = 1.0f;
            if (srcSize.getWidth() < srcSize.getHeight()) {
                if (srcSize.getWidth() > screenSize[0]) {
                    scale = ((float) screenSize[0]) / ((float) srcSize.getWidth());
                }
                maxSide2 = (int) Math.min((float) maxSide, ((float) srcSize.getHeight()) * scale);
            } else {
                if (srcSize.getHeight() > screenSize[1]) {
                    scale = ((float) screenSize[1]) / ((float) srcSize.getHeight());
                }
                maxSide2 = (int) Math.min((float) maxSide, ((float) srcSize.getWidth()) * scale);
            }
            int max = Math.max(maxSide2, SecExceptionCode.SEC_ERROR_SIMULATORDETECT);
            outSize[1] = max;
            outSize[0] = max;
        }
        return outSize;
    }

    public static boolean isTfsFormatZoom(String zoom) {
        if (TextUtils.isEmpty(zoom)) {
            return false;
        }
        if (Pattern.compile(a).matcher(zoom).matches()) {
            return true;
        }
        if (Pattern.compile(b).matcher(zoom).matches()) {
            return true;
        }
        if (Pattern.compile(c).matcher(zoom).matches()) {
            return true;
        }
        return false;
    }

    private static boolean a() {
        if (!ConfigManager.getInstance().openWebpGraySwitch() && CommonUtils.isWifiNetwork()) {
            return false;
        }
        return true;
    }
}
