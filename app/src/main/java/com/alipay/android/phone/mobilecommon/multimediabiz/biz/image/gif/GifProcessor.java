package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.gif;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.OriginalBitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.mobile.common.utils.MD5Util;
import com.alipay.multimedia.img.base.SoLibLoader;
import com.alipay.multimedia.img.decode.DecodeOptions;
import com.alipay.multimedia.img.decode.DecodeResult;
import com.alipay.multimedia.img.decode.GifDecoderWrapper;
import com.alipay.multimedia.img.utils.LogUtils;
import com.alipay.streammedia.mmengine.picture.gif.GifCompress;
import com.alipay.streammedia.mmengine.picture.gif.GifDecoder;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class GifProcessor {
    private static ConcurrentHashMap<String, String> a = new ConcurrentHashMap<>(20);
    private static ConcurrentHashMap<String, String> b = new ConcurrentHashMap<>(40);
    private static GifCompress c = new GifCompress();

    static {
        try {
            GifCompress.loadLibrariesOnce(new SoLibLoader());
        } catch (Throwable t) {
            Logger.E((String) "GifProcessor", t, (String) "load library error", new Object[0]);
        }
    }

    public static APGifInfo compressGif(String path, String business, Bundle extra) {
        boolean z;
        Logger.D("GifProcessor", "compressGif input path:" + path + ", business: " + business, new Object[0]);
        long ts = System.currentTimeMillis();
        boolean ret = false;
        if (LocalIdTool.isLocalIdRes(path)) {
            path = LocalIdTool.get().decodeToPath(path);
        }
        APGifInfo compressInfo = new APGifInfo();
        try {
            String path2 = PathUtils.extractFile(path).getAbsolutePath();
            if (!FileUtils.checkFile(path2)) {
                Logger.D("GifProcessor", "compressGif file not exist path:" + path2 + ", business: " + business, new Object[0]);
            } else {
                String fileMd5 = MD5Util.getFileMD5String(new File(path2));
                String md5 = MD5Util.getMD5String(fileMd5 + path2);
                String destPath = GifFileManager.getInstance().generateGifPath(md5);
                Logger.D("GifProcessor", "compressGif path=" + path2 + ", md5=" + md5 + ";fileMd5=" + fileMd5 + ";destPath=" + destPath, new Object[0]);
                String localGifId = LocalIdTool.get().encodeToLocalId(destPath);
                if (!FileUtils.checkFile(destPath)) {
                    try {
                        GifDecoder gifDecoder = new GifDecoder();
                        gifDecoder.init(path2, 4096, 1);
                        gifDecoder.release();
                        int srcWidth = gifDecoder.getWidth();
                        int srcHeight = gifDecoder.getHeight();
                        int maxSide = extra == null ? 800 : extra.getInt("maxSide", 800);
                        if (srcWidth > 0 && srcHeight > 0) {
                            maxSide = Math.min(maxSide, Math.max(srcWidth, srcHeight));
                        }
                        ret = c.optimalCompress(path2, destPath, 20, maxSide) == 0;
                        if (ret) {
                            GifFileManager.getInstance().insertRecord(new OriginalBitmapCacheKey(destPath), destPath, 128, business, -1702967296);
                        }
                    } catch (Exception e) {
                        LogUtils.e("GifProcessor", "CompressImage exp =", e);
                    }
                } else {
                    ret = true;
                }
                if (extra == null) {
                    z = false;
                } else {
                    z = extra.getBoolean("needFrame", false);
                }
                if (z) {
                    String localGifFrameId = localGifId + "_frame";
                    if (!FileUtils.checkFile(new File(GifFileManager.getInstance().generateGifPath(localGifId + "_frame")))) {
                        extractGifFrame(destPath, 0);
                    }
                    Logger.D("GifProcessor", "compressGif localGifFrameId=" + localGifFrameId, new Object[0]);
                }
                compressInfo.mPath = destPath;
                compressInfo.mId = localGifId;
                compressInfo.mSize = new File(destPath).length();
                compressInfo.mSuccess = ret;
                if (ret) {
                    a(destPath, path2);
                }
                Logger.D("GifProcessor", "compressGif compressInfo=" + compressInfo.toString() + ";coastTime=" + (System.currentTimeMillis() - ts), new Object[0]);
            }
        } catch (Exception e2) {
            Logger.E((String) "GifProcessor", (Throwable) e2, (String) "compressGif exp!!!", new Object[0]);
        } finally {
            r6 = "GifProcessor";
            r8 = "compressGif compressInfo=";
            StringBuilder append = new StringBuilder(r8).append(compressInfo.toString());
            r8 = ";coastTime=";
            Logger.D(r6, append.append(r8).append(System.currentTimeMillis() - ts).toString(), new Object[0]);
        }
        return compressInfo;
    }

    public static Bitmap extractGifFrame(String gifPath, int index) {
        DecodeResult result = null;
        try {
            File gifFile = PathUtils.extractFile(gifPath);
            DecodeOptions options = new DecodeOptions();
            options.frameIndex = index;
            options.frameCheck = false;
            result = GifDecoderWrapper.decode(gifFile, options);
        } catch (Throwable t) {
            Logger.E((String) "GifProcessor", t, "extractGifFrame error, path: " + gifPath + ", index: " + index, new Object[0]);
        }
        if (result != null) {
            return result.bitmap;
        }
        return null;
    }

    public static void relateCloudidToLocalgif(String gifCloudId, String gifFrameId, String localGifPath) {
        if (!TextUtils.isEmpty(localGifPath)) {
            if (!TextUtils.isEmpty(gifCloudId)) {
                b.put(gifCloudId, localGifPath);
            }
            if (!TextUtils.isEmpty(gifFrameId)) {
                b.put(gifFrameId, localGifPath);
            }
        }
    }

    private static void a(String compressPath, String sourcePath) {
        if (!TextUtils.isEmpty(compressPath) && !TextUtils.isEmpty(sourcePath)) {
            a.put(compressPath, sourcePath);
        }
    }

    public static String getRelateGifSourcePath(String compressPath) {
        return a.get(compressPath);
    }

    public static String getRelateCloudIdGifPath(String gifGloudId) {
        return b.get(gifGloudId);
    }
}
