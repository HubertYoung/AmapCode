package com.alipay.multimedia.img.encode;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.decode.DecodeOptions;
import com.alipay.multimedia.img.decode.DecodeOptions.MaxLenMode;
import com.alipay.multimedia.img.decode.DecodeOptions.MinLenMode;
import com.alipay.multimedia.img.decode.DecodeResult;
import com.alipay.multimedia.img.decode.SystemImageDecoder;
import com.alipay.multimedia.img.utils.LogUtils;
import com.alipay.multimedia.io.FileUtils;
import com.alipay.multimedia.io.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SystemImageEncoder {
    private static final String TAG = "SystemImageEncoder";

    public static EncodeResult compress(File file, EncodeOptions options) {
        long start = System.currentTimeMillis();
        EncodeResult encodeResult = new EncodeResult();
        if (FileUtils.checkFile(file)) {
            ImageInfo info = ImageInfo.getImageInfo(file.getAbsolutePath());
            processEncode(SystemImageDecoder.decodeBitmap(file, createDecodeOptions(info, options)), options, info, encodeResult);
        }
        LogUtils.d(TAG, "compress file: " + file + ", options: " + options + ", result: " + encodeResult + ", cost: " + (System.currentTimeMillis() - start));
        return encodeResult;
    }

    public static EncodeResult compress(byte[] data, EncodeOptions options) {
        long start = System.currentTimeMillis();
        EncodeResult encodeResult = new EncodeResult();
        if (data != null && data.length > 0) {
            ImageInfo info = ImageInfo.getImageInfo(data);
            processEncode(SystemImageDecoder.decodeBitmap(data, createDecodeOptions(info, options)), options, info, encodeResult);
        }
        LogUtils.d(TAG, "compress data: " + data + ", options: " + options + ", result: " + encodeResult + ", cost: " + (System.currentTimeMillis() - start));
        return encodeResult;
    }

    public static EncodeResult compress(InputStream in, EncodeOptions options) {
        return compress(in == null ? null : IOUtils.getBytes(in), options);
    }

    public static EncodeResult compress(Bitmap bitmap, EncodeOptions options) {
        long start = System.currentTimeMillis();
        EncodeResult encodeResult = new EncodeResult();
        if (bitmap != null && !bitmap.isRecycled()) {
            DecodeResult result = new DecodeResult();
            result.code = 0;
            result.bitmap = bitmap;
            processEncode(result, options, ImageInfo.getImageInfo(bitmap, 0), encodeResult);
        }
        LogUtils.d(TAG, "compress bitmap: " + bitmap + ", options: " + options + ", result: " + encodeResult + ", cost: " + (System.currentTimeMillis() - start));
        return encodeResult;
    }

    private static DecodeOptions createDecodeOptions(ImageInfo info, EncodeOptions options) {
        DecodeOptions opts = new DecodeOptions();
        switch (options.mode.type) {
            case 0:
                opts.mode = new MaxLenMode(Integer.valueOf(((com.alipay.multimedia.img.encode.mode.MaxLenMode) options.mode).len));
                break;
            case 1:
                opts.mode = new MinLenMode(Integer.valueOf(((com.alipay.multimedia.img.encode.mode.MinLenMode) options.mode).len));
                break;
            case 4:
                opts.mode = new MaxLenMode(Integer.valueOf(Math.max(info.width, info.height)));
                break;
            default:
                opts.mode = new MaxLenMode(Integer.valueOf(1280));
                break;
        }
        return opts;
    }

    private static void processEncode(DecodeResult result, EncodeOptions options, ImageInfo info, EncodeResult encodeResult) {
        OutputStream outputStream;
        LogUtils.d(TAG, "processEncode start decodeResult: " + result + ", opts: " + options + ", info: " + info + ", encodeResult: " + encodeResult + ", flag: " + result.isSuccess());
        if (result.isSuccess()) {
            OutputStream outputStream2 = null;
            try {
                LogUtils.d(TAG, "processEncode start, options: " + options);
                if (!TextUtils.isEmpty(options.outputFile)) {
                    File outFile = new File(options.outputFile);
                    outFile.getParentFile().mkdirs();
                    outputStream = new FileOutputStream(outFile);
                } else {
                    outputStream = new ByteArrayOutputStream();
                }
                CompressFormat format = CompressFormat.JPEG;
                if (options.outFormat == 1) {
                    format = CompressFormat.PNG;
                }
                int quality = 80;
                if (options.quality == 1) {
                    quality = 90;
                }
                result.bitmap.compress(format, quality, outputStream2);
                encodeResult.code = 0;
                encodeResult.encodeFilePath = options.outputFile;
                encodeResult.encodeData = outputStream2 instanceof ByteArrayOutputStream ? ((ByteArrayOutputStream) outputStream2).toByteArray() : null;
                encodeResult.imageInfo = info;
            } catch (Throwable t) {
                LogUtils.e(TAG, "processEncode error", t);
            } finally {
                IOUtils.closeQuietly(outputStream2);
            }
        } else {
            encodeResult.code = -1;
        }
        LogUtils.d(TAG, "processEncode decodeResult: " + result + ", opts: " + options + ", info: " + info + ", encodeResult: " + encodeResult);
    }
}
