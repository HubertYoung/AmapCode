package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImageInfo;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImagePlaceHolderOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImagePlaceHolderRect;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APCropOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.effect.APCalcColorResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APCenterCropMode;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APMaxLenMode;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APMinLenMode;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APSpecificCropMode;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.ImageSize;
import com.alipay.multimedia.img.decode.CropOptions;
import com.alipay.multimedia.img.decode.DecodeOptions;
import com.alipay.multimedia.img.decode.DecodeResult;
import com.alipay.multimedia.img.encode.EncodeOptions;
import com.alipay.multimedia.img.encode.EncodeResult;
import com.alipay.multimedia.img.encode.mode.CenterCropMode;
import com.alipay.multimedia.img.encode.mode.MaxLenMode;
import com.alipay.multimedia.img.encode.mode.MinLenMode;
import com.alipay.multimedia.img.encode.mode.NoneScaleMode;
import com.alipay.multimedia.img.encode.mode.SpecificCropMode;
import com.alipay.multimedia.img.utils.ImageAssist.ImagePlaceHolderOptions;
import com.alipay.multimedia.img.utils.ImageAssist.ImagePlaceHolderRect;
import com.alipay.multimedia.img.utils.ImageFileType;
import com.alipay.streammedia.mmengine.filter.CalcColorResult;

public class AlipayImageParamConverter {
    public static EncodeOptions from(APEncodeOptions options) {
        EncodeOptions opts = new EncodeOptions();
        opts.quality = options.quality;
        opts.requireOutputInfo = options.requireOutputInfo;
        opts.outFormat = options.outFormat;
        opts.outputFile = options.outputFile;
        opts.autoRotate = options.autoRotate;
        opts.forceRotate = options.forceRotate;
        switch (options.mode.type) {
            case 0:
                opts.mode = new MaxLenMode(((APMaxLenMode) options.mode).len);
                break;
            case 1:
                opts.mode = new MinLenMode(((APMinLenMode) options.mode).len);
                break;
            case 2:
                APCenterCropMode centerCropMode = (APCenterCropMode) options.mode;
                opts.mode = new CenterCropMode(centerCropMode.width, centerCropMode.height);
                break;
            case 3:
                APSpecificCropMode specificCropMode = (APSpecificCropMode) options.mode;
                opts.mode = new SpecificCropMode(specificCropMode.x, specificCropMode.y, specificCropMode.width, specificCropMode.height);
                break;
            case 4:
                opts.mode = new NoneScaleMode();
                break;
        }
        return opts;
    }

    public static APEncodeResult from(EncodeResult result) {
        APEncodeResult ret = new APEncodeResult();
        ret.code = result.code;
        ret.encodeData = result.encodeData;
        ret.extra = result.extra;
        ret.encodeFilePath = result.encodeFilePath;
        if (result.imageInfo != null) {
            ret.imageInfo = new APImageInfo(result.imageInfo.width, result.imageInfo.height, result.imageInfo.orientation);
        }
        return ret;
    }

    public static CropOptions from(APCropOptions options) {
        CropOptions opts = new CropOptions();
        opts.cutSize = new ImageSize(options.cutSize.getWidth(), options.cutSize.getHeight());
        opts.autoUseAshmem = options.autoUseAshmem;
        opts.startPoint = options.startPoint;
        opts.autoRotate = options.autoRotate;
        opts.inPreferredConfig = options.inPreferredConfig;
        opts.smartFaceCut = options.smartFaceCut;
        opts.usePreferSizeInstead = options.usePreferSizeInstead;
        opts.inPreferQualityOverSpeed = options.inPreferQualityOverSpeed;
        opts.scaleType = options.scaleType;
        opts.inPerformance = options.inPerformance;
        opts.forceRotate = options.forceRotate;
        opts.canUseJpgThumbnailData = options.canUseJpgThumbnailData;
        return opts;
    }

    public static DecodeOptions from(APDecodeOptions options) {
        DecodeOptions opts = new DecodeOptions();
        opts.inPreferQualityOverSpeed = options.inPreferQualityOverSpeed;
        opts.inPreferredConfig = options.inPreferredConfig;
        opts.autoUseAshmem = options.autoUseAshmem;
        opts.autoRotate = options.autoRotate;
        opts.forceRotate = options.forceRotate;
        if (options.mode instanceof APDecodeOptions.MaxLenMode) {
            opts.mode = new DecodeOptions.MaxLenMode(((APDecodeOptions.MaxLenMode) options.mode).len);
        } else if (options.mode instanceof APDecodeOptions.MinLenMode) {
            opts.mode = new DecodeOptions.MinLenMode(((APDecodeOptions.MinLenMode) options.mode).len);
        }
        return opts;
    }

    public static APDecodeResult from(DecodeResult result) {
        APDecodeResult ret = new APDecodeResult();
        ret.bitmap = result.bitmap;
        ret.code = result.code;
        ret.extra = result.extra;
        if (result.srcInfo != null) {
            ret.srcInfo = new APImageInfo(result.srcInfo.width, result.srcInfo.height, result.srcInfo.orientation);
        }
        return ret;
    }

    public static ImagePlaceHolderOptions from(APImagePlaceHolderOptions options) {
        ImagePlaceHolderOptions opts = new ImagePlaceHolderOptions();
        opts.jpgFile = options.jpgFile;
        opts.srcWidth = options.srcWidth;
        opts.srcHeight = options.srcHeight;
        opts.dstWidth = options.dstWidth;
        opts.dstHeight = options.dstHeight;
        opts.maxDimension = options.maxDimension;
        opts.minDimension = options.minDimension;
        opts.cropX = options.cropX;
        opts.cropY = options.cropY;
        opts.cropMode = options.cropMode;
        opts.rotate = options.rotate;
        opts.needMirror = options.needMirror;
        return opts;
    }

    public static APImagePlaceHolderRect from(ImagePlaceHolderRect result) {
        APImagePlaceHolderRect rect = new APImagePlaceHolderRect();
        rect.cropLeft = result.cropLeft;
        rect.cropTop = result.cropTop;
        rect.dstHeight = result.dstHeight;
        rect.dstWidth = result.dstWidth;
        rect.retCode = result.retCode;
        rect.srcWidth = result.srcWidth;
        rect.srcHeight = result.srcHeight;
        return rect;
    }

    public static APImageInfo from(ImageInfo info) {
        if (info == null) {
            return null;
        }
        APImageInfo out = new APImageInfo(info.width, info.height, info.orientation);
        out.type = ImageFileType.getSuffixByType(info.format.intValue());
        return out;
    }

    public static APCalcColorResult from(CalcColorResult colorResult) {
        if (colorResult == null) {
            return null;
        }
        APCalcColorResult result = new APCalcColorResult();
        result.black = colorResult.black;
        result.blue = colorResult.blue;
        result.green = colorResult.green;
        result.red = colorResult.red;
        return result;
    }
}
