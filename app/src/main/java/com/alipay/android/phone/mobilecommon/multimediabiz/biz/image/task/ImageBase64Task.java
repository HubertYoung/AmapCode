package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.util.Arrays;

public class ImageBase64Task extends ImageTask {
    private static final Logger a = Logger.getLogger((String) "ImageBase64Task");

    public ImageBase64Task(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
    }

    public Object call() {
        byte[] data;
        byte[] sourceData = CommonUtils.base64Decode(PathUtils.extractBase64(this.loadReq.source));
        if (sourceData == null || sourceData.length == 0) {
            notifyError(RETCODE.PARAM_ERROR, "base64 decode error, src: " + this.loadReq.source, null);
            return null;
        }
        ImageInfo info = ImageInfo.getImageInfo(sourceData);
        CutScaleType type = this.options.getCutScaleType();
        int width = this.options.getWidth().intValue();
        int height = this.options.getHeight().intValue();
        Bitmap bitmap = null;
        FalconFacade facade = FalconFacade.get();
        if (CutScaleType.CENTER_CROP.equals(type)) {
            try {
                bitmap = facade.cutImage(sourceData, width, height, this.options.getScale().floatValue());
                a.p("CENTER_CROP, width: " + bitmap.getWidth() + ", height: " + bitmap.getHeight() + ", req: " + this.loadReq.cacheKey, new Object[0]);
            } catch (Exception e) {
                a.e(e, "fromLocal err, info: " + this.loadReq, new Object[0]);
            }
        } else if (CutScaleType.AUTO_CUT_EXACTLY.equals(type)) {
            try {
                int maxLen = Math.max(width, height);
                int minLen = Math.min(width, height);
                bitmap = facade.cutImage(sourceData, maxLen, minLen, ((float) minLen) / ((float) maxLen));
                a.p("AUTO_CUT_EXACTLY cutImage exactly, width: " + bitmap.getWidth() + ", height: " + bitmap.getHeight() + ", req: " + this.loadReq.cacheKey, new Object[0]);
            } catch (Exception e2) {
                a.e(e2, "fromLocal err, info: " + this.loadReq, new Object[0]);
            }
        } else {
            int[] fitSize = getFitSize(new Size(info.correctWidth, info.correctHeight), width, height);
            a.d("from local fitSize: " + Arrays.toString(fitSize), new Object[0]);
            bitmap = facade.cutImageKeepRatio(sourceData, fitSize[0], fitSize[1]);
        }
        if (!ImageUtils.checkBitmap(bitmap)) {
            return bitmap;
        }
        this.loadReq.downloadRsp.loadFrom = 2;
        if (this.loadReq.options.isCacheInMem()) {
            getCacheLoader().put(this.loadReq.cacheKey, null, bitmap, this.loadReq.options.getBusinessId(), this.loadReq.getExpiredTime());
            data = null;
        } else {
            data = ImageUtils.bitmap2Bytes(bitmap, ImageFileType.detectImageDataType(sourceData) == 0);
            getCacheLoader().putDiskCache(this.loadReq.cacheKey, data, this.loadReq.options.getBusinessId(), null, this.loadReq.getExpiredTime());
        }
        if (this.loadReq.options.isWithImageDataInCallback()) {
            if (data == null) {
                data = ImageUtils.bitmap2Bytes(bitmap, ImageFileType.detectImageDataType(sourceData) == 0);
            }
            notifySuccessWithImageData(this.loadReq, data);
            return bitmap;
        }
        display(bitmap, this.loadReq, this.viewWrapper);
        return bitmap;
    }
}
