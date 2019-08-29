package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import java.io.File;

public class CacheUtils {
    public static final String CACHE_KEY_SEPARATOR = "##";
    public static final String CACHE_MARK_PREFIX = "mark@@";
    public static final String CACHE_MARK_SEPARATOR = "@@";
    public static final String CACHE_QUALITY_CHAR = "q";
    public static final String CACHE_QUALITY_KEY = "##q";
    public static final int VIDEO_THUMB_HEIGHT = 640;
    public static final int VIDEO_THUMB_WIDTH = 640;

    public static String makeImageCacheKey(ImageWorkerPlugin plugin, String path, int width, int height, CutScaleType type, APImageMarkRequest imageMarkRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(path).append("##").append(width).append("##").append(height).append("##").append(type);
        if (plugin != null && !TextUtils.isEmpty(plugin.getPluginKey())) {
            sb.append("##").append(plugin.getPluginKey());
        }
        if (imageMarkRequest != null && MarkUtil.isValidMarkRequest(imageMarkRequest)) {
            if (plugin == null || TextUtils.isEmpty(plugin.getPluginKey())) {
                sb.append("##no_plugin");
            }
            sb.append("##mark@@").append(imageMarkRequest.getMarkId()).append("@@").append(imageMarkRequest.getPosition()).append("@@").append(imageMarkRequest.getTransparency()).append("@@").append(imageMarkRequest.getMarkWidth()).append("@@").append(imageMarkRequest.getMarkHeight()).append("@@").append(imageMarkRequest.getPaddingX()).append("@@").append(imageMarkRequest.getPaddingY()).append("@@").append(imageMarkRequest.getPercent());
        }
        return sb.toString();
    }

    public static boolean qualityCachekeyCheck(int quality) {
        return quality >= 40 && quality < 80;
    }

    public static String makeVideoThumbCacheKey(String thumbId, int width, int height) {
        return makeImageCacheKey(null, thumbId, width, height, CutScaleType.KEEP_RATIO, null);
    }

    public static boolean checkCacheFile(File file) {
        return FileUtils.checkFile(file);
    }

    public static String[] splitCacheKey(String input) {
        String[] out = null;
        if (!TextUtils.isEmpty(input)) {
            out = new String[7];
            String[] tmp = input.split("##", 7);
            int i = 0;
            while (i < tmp.length && i < 7) {
                if (i != tmp.length - 1 || !tmp[i].startsWith("q")) {
                    out[i] = tmp[i];
                } else {
                    out[6] = tmp[i];
                }
                i++;
            }
        }
        return out;
    }

    public static boolean isDiskCacheExpired(String key, File cacheFile) {
        if (cacheFile == null) {
            return true;
        }
        String[] params = splitCacheKey(key);
        if (params != null && params.length > 0 && PathUtils.isLocalFile(params[0])) {
            File originFile = new File(params[0]);
            if (!originFile.exists()) {
                Logger.D("CacheUtils", "disk cache[" + key + "] expired! " + params[0] + " already not exist", new Object[0]);
                return true;
            }
            long originLastModify = originFile.lastModified();
            long cacheTime = cacheFile.lastModified();
            if (originLastModify > 0 && cacheTime > 0 && originLastModify > cacheTime) {
                Logger.D("CacheUtils", "disk cache [" + key + "] expired! origin  " + originFile + " originLastModify " + originLastModify + " cache  " + cacheFile + " cacheTime " + cacheTime, new Object[0]);
                return true;
            }
        }
        return false;
    }
}
