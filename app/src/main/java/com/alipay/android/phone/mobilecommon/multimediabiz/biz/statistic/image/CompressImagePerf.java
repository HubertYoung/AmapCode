package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.BaseStatistics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP1;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP2;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP3;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SPExt;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.ST;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.encode.EncodeOptions;
import com.alipay.multimedia.img.encode.EncodeResult;
import java.io.File;
import java.util.Map;

@ST(caseId = "UC-MM-C31", seedId = "CompressImagePerf")
public class CompressImagePerf extends BaseStatistics {
    @SPExt(name = "dh")
    public int destHeight = -1;
    @SPExt(name = "dw")
    public int destWidth = -1;
    @SPExt(name = "h")
    public int height;
    @SPExt(name = "cl")
    public int level;
    @SP1
    public int retCode;
    @SP2
    public long size;
    @SP3
    public long totalTime;
    @SPExt(name = "w")
    public int width;

    public static CompressImagePerf createFrom(long start, File file, EncodeOptions options, EncodeResult result) {
        return a(System.currentTimeMillis() - start, ImageInfo.getImageInfo(file.getAbsolutePath()), options, result);
    }

    public static CompressImagePerf createFrom(long start, byte[] data, EncodeOptions options, EncodeResult result) {
        return a(System.currentTimeMillis() - start, ImageInfo.getImageInfo(data), options, result);
    }

    public static CompressImagePerf createFrom(long start, Bitmap bitmap, EncodeOptions options, EncodeResult result) {
        long costTime = System.currentTimeMillis() - start;
        ImageInfo info = new ImageInfo();
        if (bitmap != null) {
            int width2 = bitmap.getWidth();
            info.correctWidth = width2;
            info.width = width2;
            int height2 = bitmap.getHeight();
            info.correctHeight = height2;
            info.height = height2;
        }
        return a(costTime, info, options, result);
    }

    private static CompressImagePerf a(long costTime, ImageInfo info, EncodeOptions options, EncodeResult result) {
        ImageInfo dstInfo;
        CompressImagePerf perf = new CompressImagePerf();
        perf.retCode = result.code;
        perf.totalTime = costTime;
        perf.width = info.correctWidth;
        perf.height = info.correctHeight;
        if (options != null) {
            perf.level = options.quality;
        }
        if (result.isSuccess()) {
            if (result.imageInfo != null) {
                dstInfo = result.imageInfo;
            } else if (TextUtils.isEmpty(result.encodeFilePath)) {
                dstInfo = ImageInfo.getImageInfo(result.encodeFilePath);
            } else {
                dstInfo = ImageInfo.getImageInfo(result.encodeData);
            }
            perf.destWidth = dstInfo.correctWidth;
            perf.destHeight = dstInfo.correctHeight;
        }
        return perf;
    }

    public String getCaseId() {
        return "UC-MM-C31";
    }

    public String getSeedId() {
        return "CompressImagePerf";
    }

    public String getParam1() {
        return String.valueOf(this.retCode);
    }

    public String getParam2() {
        return String.valueOf(this.size);
    }

    public String getParam3() {
        return String.valueOf(this.totalTime);
    }

    /* access modifiers changed from: protected */
    public void fillExtParams(Map<String, String> ext) {
        ext.put("w", String.valueOf(this.width));
        ext.put("h", String.valueOf(this.height));
        ext.put("dw", String.valueOf(this.destWidth));
        ext.put("dh", String.valueOf(this.destHeight));
        ext.put("cl", String.valueOf(this.level));
    }
}
