package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.interf.ISmartCutProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.processor.RegionCropProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.ImageInfo;
import java.io.File;

public class DefaultSmartCutProcessor implements ISmartCutProcessor {
    private static final Logger a = Logger.getLogger((String) "DefaultSmartCutProcessor");

    public Bitmap process(String path, ImageLoadReq req) {
        if (path == null) {
            return null;
        }
        boolean isEncrypt = false;
        byte[] fileData = null;
        File file = new File(path);
        if (req.isEncryptRequest()) {
            fileData = AESUtils.decryptFile(req.options.fileKey, file);
        }
        if (fileData != null) {
            isEncrypt = true;
        }
        ImageInfo info = isEncrypt ? ImageInfo.getImageInfo(fileData) : ImageInfo.getImageInfo(path);
        return req.options.secondaryCutScaleType != null ? isEncrypt ? processBySecondaryCutScaleType(fileData, info, req) : processBySecondaryCutScaleType(path, info, req) : isEncrypt ? processByDefault(fileData, info, req) : processByDefault(path, info, req);
    }

    /* access modifiers changed from: protected */
    public Bitmap processByDefault(String path, ImageInfo info, ImageLoadReq loadReq) {
        if (ConfigManager.getInstance().getCommonConfigItem().djangoConf.enableCalcScaleSize == 1) {
            return RegionCropProcessor.processRegionCrop(loadReq, new File(path), CutScaleType.REGION_CROP_CENTER_TOP);
        }
        DisplayImageOptions options = loadReq.options;
        float width = (float) loadReq.options.getWidth().intValue();
        float height = (float) loadReq.options.getHeight().intValue();
        boolean landscape = ((float) info.correctWidth) / ((float) info.correctHeight) > width / height;
        int cropMode = landscape ? 0 : 1;
        try {
            return FalconFacade.get().cutImage(new File(path), null, options.getWidth().intValue(), options.getHeight().intValue(), cropMode);
        } catch (Exception e) {
            a.e(e, "processByDefault error, path: " + path + ", info: " + info + ", landscape: " + landscape + ", cropMode: " + cropMode + ", width: " + width + ", height: " + height, new Object[0]);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Bitmap processByDefault(byte[] fileData, ImageInfo info, ImageLoadReq loadReq) {
        boolean landscape;
        int i;
        int cropMode = 1;
        boolean z = 0;
        if (ConfigManager.getInstance().getCommonConfigItem().djangoConf.enableCalcScaleSize == 1) {
            return RegionCropProcessor.processRegionCrop(loadReq, fileData, CutScaleType.REGION_CROP_CENTER_TOP);
        }
        DisplayImageOptions options = loadReq.options;
        float width = (float) loadReq.options.getWidth().intValue();
        float height = (float) loadReq.options.getHeight().intValue();
        if (((float) info.correctWidth) / ((float) info.correctHeight) > width / height) {
            landscape = 1;
        } else {
            landscape = z;
        }
        if (landscape != 0) {
            cropMode = z;
        }
        try {
            return FalconFacade.get().cutImage(fileData, options.getWidth().intValue(), options.getHeight().intValue(), (float) cropMode);
        } catch (Exception e) {
            Logger logger = a;
            StringBuilder sb = new StringBuilder("processByDefault error, fileData size: ");
            if (fileData != null) {
                i = fileData.length;
            } else {
                i = z;
            }
            logger.e(e, sb.append(i).append(", info: ").append(info).append(", landscape: ").append(landscape).append(", cropMode: ").append(cropMode).append(", width: ").append(width).append(", height: ").append(height).toString(), new Object[z]);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Bitmap processBySecondaryCutScaleType(String path, ImageInfo info, ImageLoadReq loadReq) {
        Bitmap bitmap = null;
        CutScaleType cutScaleType = loadReq.options.secondaryCutScaleType;
        if (!cutScaleType.isRegionCrop()) {
            if (!CompareUtils.in(cutScaleType, CutScaleType.CENTER_CROP, CutScaleType.AUTO_CUT_EXACTLY)) {
                int[] fitSize = ZoomHelper.getFitSize(AppUtils.getApplicationContext(), new Size(info.correctWidth, info.correctHeight), loadReq.options.getWidth().intValue(), loadReq.options.getHeight().intValue());
                try {
                    return FalconFacade.get().cutImageKeepRatio(new File(path), fitSize[0], fitSize[1]);
                } catch (Throwable e) {
                    a.e(e, "processBySecondaryCutScaleType err, info: " + loadReq, new Object[0]);
                    return bitmap;
                }
            }
        }
        return RegionCropProcessor.processRegionCrop(loadReq, new File(path), cutScaleType);
    }

    /* access modifiers changed from: protected */
    public Bitmap processBySecondaryCutScaleType(byte[] fileData, ImageInfo info, ImageLoadReq loadReq) {
        Bitmap bitmap = null;
        CutScaleType cutScaleType = loadReq.options.secondaryCutScaleType;
        if (!cutScaleType.isRegionCrop()) {
            if (!CompareUtils.in(cutScaleType, CutScaleType.CENTER_CROP, CutScaleType.AUTO_CUT_EXACTLY)) {
                int[] fitSize = ZoomHelper.getFitSize(AppUtils.getApplicationContext(), new Size(info.correctWidth, info.correctHeight), loadReq.options.getWidth().intValue(), loadReq.options.getHeight().intValue());
                try {
                    return FalconFacade.get().cutImageKeepRatio(fileData, fitSize[0], fitSize[1]);
                } catch (Throwable e) {
                    a.e(e, "processBySecondaryCutScaleType err, info: " + loadReq, new Object[0]);
                    return bitmap;
                }
            }
        }
        return RegionCropProcessor.processRegionCrop(loadReq, fileData, cutScaleType);
    }

    public Bitmap process(byte[] fileData, ImageLoadReq req) {
        if (fileData == null) {
            return null;
        }
        ImageInfo info = ImageInfo.getImageInfo(fileData);
        if (req.options.secondaryCutScaleType != null) {
            return processBySecondaryCutScaleType(fileData, info, req);
        }
        return processByDefault(fileData, info, req);
    }
}
