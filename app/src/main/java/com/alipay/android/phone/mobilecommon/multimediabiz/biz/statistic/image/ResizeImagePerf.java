package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.BaseStatistics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP1;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP2;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP3;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SPExt;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.ST;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.decode.CropOptions;
import com.alipay.multimedia.img.decode.DecodeOptions;
import com.alipay.multimedia.img.decode.DecodeOptions.FitRectMode;
import com.alipay.multimedia.img.decode.DecodeOptions.MaxLenMode;
import com.alipay.multimedia.img.decode.DecodeOptions.MinLenMode;
import com.alipay.multimedia.img.decode.DecodeResult;
import java.io.File;
import java.util.Map;

@ST(caseId = "UC-MM-C32", seedId = "ResizeImagePerf")
public class ResizeImagePerf extends BaseStatistics {
    @SPExt(name = "rh")
    public int destHeight = -1;
    @SPExt(name = "rw")
    public int destWidth = -1;
    public String filePath;
    @SPExt(name = "oh")
    public int height;
    @SP1
    public int retCode;
    @SP2
    public long size = -1;
    @SP3
    public long totalTime;
    @SPExt(name = "tp")
    public int type;
    @SPExt(name = "ow")
    public int width;
    @SPExt(name = "zh")
    public int zoomHeight;
    @SPExt(name = "zw")
    public int zoomWidth;

    public static ResizeImagePerf createFrom(long start, File file, DecodeOptions options, DecodeResult result) {
        return a(System.currentTimeMillis() - start, file.getAbsolutePath(), options, result);
    }

    public static ResizeImagePerf createFrom(long start, byte[] data, DecodeOptions options, DecodeResult result) {
        return a(System.currentTimeMillis() - start, ImageInfo.getImageInfo(data), options, result);
    }

    public static ResizeImagePerf createFrom(long start, File file, CropOptions options, DecodeResult result) {
        return a(System.currentTimeMillis() - start, file.getAbsolutePath(), options, result);
    }

    public static ResizeImagePerf createFrom(long start, byte[] data, CropOptions options, DecodeResult result) {
        return a(System.currentTimeMillis() - start, ImageInfo.getImageInfo(data), options, result);
    }

    private static ResizeImagePerf a(long costTime, ImageInfo info, DecodeOptions options, DecodeResult result) {
        ResizeImagePerf perf = new ResizeImagePerf();
        perf.retCode = result.code;
        perf.totalTime = costTime;
        perf.width = info.correctWidth;
        perf.height = info.correctHeight;
        if (options.mode instanceof MaxLenMode) {
            int intValue = ((MaxLenMode) options.mode).len.intValue();
            perf.zoomHeight = intValue;
            perf.zoomWidth = intValue;
        } else if (options.mode instanceof MinLenMode) {
            int intValue2 = ((MinLenMode) options.mode).len.intValue();
            perf.zoomHeight = intValue2;
            perf.zoomWidth = intValue2;
        } else if (options.mode instanceof FitRectMode) {
            perf.zoomWidth = ((FitRectMode) options.mode).rectWidth;
            perf.zoomHeight = ((FitRectMode) options.mode).rectHeight;
        }
        if (result.isSuccess() && result.bitmap != null) {
            perf.destWidth = result.bitmap.getWidth();
            perf.destHeight = result.bitmap.getHeight();
        }
        perf.type = 0;
        return perf;
    }

    private static ResizeImagePerf a(long costTime, String path, DecodeOptions options, DecodeResult result) {
        ResizeImagePerf perf = new ResizeImagePerf();
        perf.retCode = result.code;
        perf.totalTime = costTime;
        perf.filePath = path;
        if (options.mode instanceof MaxLenMode) {
            int intValue = ((MaxLenMode) options.mode).len.intValue();
            perf.zoomHeight = intValue;
            perf.zoomWidth = intValue;
        } else if (options.mode instanceof MinLenMode) {
            int intValue2 = ((MinLenMode) options.mode).len.intValue();
            perf.zoomHeight = intValue2;
            perf.zoomWidth = intValue2;
        } else if (options.mode instanceof FitRectMode) {
            perf.zoomWidth = ((FitRectMode) options.mode).rectWidth;
            perf.zoomHeight = ((FitRectMode) options.mode).rectHeight;
        }
        if (result.isSuccess() && result.bitmap != null) {
            perf.destWidth = result.bitmap.getWidth();
            perf.destHeight = result.bitmap.getHeight();
        }
        perf.type = 0;
        return perf;
    }

    private static ResizeImagePerf a(long costTime, ImageInfo info, CropOptions options, DecodeResult result) {
        ResizeImagePerf perf = new ResizeImagePerf();
        perf.retCode = result.code;
        perf.totalTime = costTime;
        perf.width = info.correctWidth;
        perf.height = info.correctHeight;
        perf.type = ImageStType.getType(options.cropMode);
        perf.zoomWidth = options.cutSize.width;
        perf.zoomHeight = options.cutSize.height;
        if (result.isSuccess() && result.bitmap != null) {
            perf.destWidth = result.bitmap.getWidth();
            perf.destHeight = result.bitmap.getHeight();
        }
        return perf;
    }

    private static ResizeImagePerf a(long costTime, String filePath2, CropOptions options, DecodeResult result) {
        ResizeImagePerf perf = new ResizeImagePerf();
        perf.retCode = result.code;
        perf.totalTime = costTime;
        perf.filePath = filePath2;
        perf.type = ImageStType.getType(options.cropMode);
        perf.zoomWidth = options.cutSize.width;
        perf.zoomHeight = options.cutSize.height;
        if (result.isSuccess() && result.bitmap != null) {
            perf.destWidth = result.bitmap.getWidth();
            perf.destHeight = result.bitmap.getHeight();
        }
        return perf;
    }

    public String getCaseId() {
        return "UC-MM-C32";
    }

    public String getSeedId() {
        return "ResizeImagePerf";
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
        ext.put("ow", String.valueOf(this.width));
        ext.put("oh", String.valueOf(this.height));
        ext.put("rw", String.valueOf(this.destWidth));
        ext.put("rh", String.valueOf(this.destHeight));
        ext.put("zw", String.valueOf(this.zoomWidth));
        ext.put("zh", String.valueOf(this.zoomHeight));
        ext.put("tp", String.valueOf(this.type));
    }

    public void submitRemote() {
    }
}
