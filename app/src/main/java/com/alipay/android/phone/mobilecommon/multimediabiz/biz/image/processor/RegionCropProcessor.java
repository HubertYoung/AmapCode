package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.processor;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.ImageInfo;
import java.io.File;

public class RegionCropProcessor {
    public static Bitmap processRegionCrop(ImageLoadReq loadReq, File sourceFile, CutScaleType scaleType) {
        int cropMode;
        DisplayImageOptions options = loadReq.options;
        ImageInfo info = ImageInfo.getImageInfo(sourceFile.getAbsolutePath());
        float width = (float) options.getWidth().intValue();
        float height = (float) options.getHeight().intValue();
        int[] targetSize = {(int) width, (int) height};
        if (!ConfigManager.getInstance().supportRegionCrop() || info == null || info.width > 32768 || info.height > 32768) {
            try {
                int maxLen = Math.max(targetSize[0], targetSize[1]);
                int minLen = Math.min(targetSize[0], targetSize[1]);
                Bitmap bitmap = FalconFacade.get().cutImage(sourceFile, maxLen, minLen, ((float) minLen) / ((float) maxLen));
                Logger.D("RegionCrop", "processRegionCrop centerCrop, width: " + bitmap.getWidth() + ", height: " + bitmap.getHeight() + ", req: " + loadReq.cacheKey, new Object[0]);
                return bitmap;
            } catch (Exception e) {
                Logger.E((String) "RegionCrop", (Throwable) e, "processRegionCrop centerCrop err, info: " + loadReq, new Object[0]);
            }
        } else {
            if (((float) info.correctWidth) / ((float) info.correctHeight) > width / height) {
                cropMode = a(scaleType);
            } else {
                cropMode = b(scaleType);
            }
            try {
                return FalconFacade.get().cutImage(sourceFile, null, targetSize[0], targetSize[1], cropMode);
            } catch (Exception e2) {
                Logger.E((String) "RegionCrop", (Throwable) e2, "processRegionCrop error, req: " + loadReq, new Object[0]);
            }
        }
        return null;
    }

    public static Bitmap processRegionCrop(ImageLoadReq loadReq, byte[] fileData, CutScaleType scaleType) {
        int cropMode;
        DisplayImageOptions options = loadReq.options;
        ImageInfo info = ImageInfo.getImageInfo(fileData);
        float width = (float) options.getWidth().intValue();
        float height = (float) options.getHeight().intValue();
        int[] targetSize = {(int) width, (int) height};
        if (!ConfigManager.getInstance().supportRegionCrop() || info == null || info.width > 32768 || info.height > 32768) {
            try {
                int maxLen = Math.max(targetSize[0], targetSize[1]);
                int minLen = Math.min(targetSize[0], targetSize[1]);
                Bitmap bitmap = FalconFacade.get().cutImage(fileData, maxLen, minLen, ((float) minLen) / ((float) maxLen));
                Logger.D("RegionCrop", "processRegionCrop centerCrop, width: " + bitmap.getWidth() + ", height: " + bitmap.getHeight() + ", req: " + loadReq.cacheKey, new Object[0]);
                return bitmap;
            } catch (Exception e) {
                Logger.E((String) "RegionCrop", (Throwable) e, "processRegionCrop centerCrop err, info: " + loadReq, new Object[0]);
            }
        } else {
            if (((float) info.correctWidth) / ((float) info.correctHeight) > width / height) {
                cropMode = a(scaleType);
            } else {
                cropMode = b(scaleType);
            }
            try {
                return FalconFacade.get().cutDataImage(fileData, null, targetSize[0], targetSize[1], cropMode);
            } catch (Exception e2) {
                Logger.E((String) "RegionCrop", (Throwable) e2, "processRegionCrop error, req: " + loadReq, new Object[0]);
            }
        }
        return null;
    }

    private static int a(CutScaleType scaleType) {
        CutScaleType regionCropType = c(scaleType);
        if (CompareUtils.in(regionCropType, CutScaleType.REGION_CROP_LEFT_TOP, CutScaleType.REGION_CROP_LEFT_CENTER, CutScaleType.REGION_CROP_LEFT_BOTTOM)) {
            return 3;
        }
        if (CompareUtils.in(regionCropType, CutScaleType.REGION_CROP_CENTER_TOP, CutScaleType.REGION_CROP_CENTER_CENTER, CutScaleType.REGION_CROP_CENTER_BOTTOM)) {
            return 0;
        }
        if (CompareUtils.in(regionCropType, CutScaleType.REGION_CROP_RIGHT_TOP, CutScaleType.REGION_CROP_RIGHT_CENTER, CutScaleType.REGION_CROP_RIGHT_BOTTOM)) {
            return 4;
        }
        return 0;
    }

    private static int b(CutScaleType scaleType) {
        CutScaleType regionCropType = c(scaleType);
        if (CompareUtils.in(regionCropType, CutScaleType.REGION_CROP_LEFT_TOP, CutScaleType.REGION_CROP_CENTER_TOP, CutScaleType.REGION_CROP_RIGHT_TOP)) {
            return 1;
        }
        if (CompareUtils.in(regionCropType, CutScaleType.REGION_CROP_LEFT_CENTER, CutScaleType.REGION_CROP_CENTER_CENTER, CutScaleType.REGION_CROP_RIGHT_CENTER)) {
            return 0;
        }
        if (CompareUtils.in(regionCropType, CutScaleType.REGION_CROP_LEFT_BOTTOM, CutScaleType.REGION_CROP_CENTER_BOTTOM, CutScaleType.REGION_CROP_RIGHT_BOTTOM)) {
            return 2;
        }
        return 0;
    }

    private static CutScaleType c(CutScaleType scaleType) {
        if (!scaleType.isSmartCrop()) {
            if (!CompareUtils.in(scaleType, CutScaleType.AUTO_CUT_EXACTLY, CutScaleType.CENTER_CROP)) {
                return scaleType;
            }
        }
        return CutScaleType.REGION_CROP_CENTER_TOP;
    }
}
