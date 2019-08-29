package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.net.Uri;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.mobile.common.transport.utils.ZURLEncodedUtil;
import com.alipay.zoloz.toyger.bean.Config;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.iflytek.tts.TtsService.Tts;
import com.sina.weibo.sdk.utils.FileUtils;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;
import tv.danmaku.ijk.media.encode.VideoRecordParameters;

public class PathUtils {
    public static final String ASSET_SCHEMA = "file:///[asset]/";
    public static final String CONTENT_SCHEMA = "content://";
    public static final String PIPE_PATH_SCHEMA = "pipe:%d";
    public static final String RES_SCHEMA = "res://";
    private static final String a = (File.separator + "[asset]" + File.separator);
    private static int[][] b = {new int[]{40, 40}, new int[]{80, 80}, new int[]{160, 160}, new int[]{-1, -1}};
    private static final int[][] c = {new int[]{16, 16}, new int[]{24, 24}, new int[]{32, 32}, new int[]{40, 40}, new int[]{50, 50}, new int[]{60, 60}, new int[]{72, 72}, new int[]{80, 80}, new int[]{90, 90}, new int[]{100, 100}, new int[]{110, 110}, new int[]{MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_}, new int[]{130, 130}, new int[]{140, 140}, new int[]{150, 150}, new int[]{160, 160}, new int[]{170, 170}, new int[]{180, 180}, new int[]{190, 190}, new int[]{200, 200}, new int[]{AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST}, new int[]{240, 240}, new int[]{Callback.DEFAULT_SWIPE_ANIMATION_DURATION, Callback.DEFAULT_SWIPE_ANIMATION_DURATION}, new int[]{270, 270}, new int[]{290, 290}, new int[]{300, 300}, new int[]{SecExceptionCode.SEC_ERROR_STA_LOW_VERSION_DATA_FILE, SecExceptionCode.SEC_ERROR_STA_LOW_VERSION_DATA_FILE}, new int[]{320, 320}, new int[]{360, 360}, new int[]{375, 375}, new int[]{400, 400}, new int[]{430, 430}, new int[]{438, 438}, new int[]{460, 460}, new int[]{Config.HQ_IMAGE_WIDTH, Config.HQ_IMAGE_WIDTH}, new int[]{500, 500}, new int[]{VideoRecordParameters.HD_HEIGHT_16_9, VideoRecordParameters.HD_HEIGHT_16_9}, new int[]{560, 560}, new int[]{580, 580}, new int[]{600, 600}, new int[]{640, 640}, new int[]{670, 670}, new int[]{720, 720}, new int[]{760, 760}, new int[]{960, 960}, new int[]{1136, 1136}, new int[]{1280, 1280}, new int[]{320, Tts.TTS_STATE_DESTROY}, new int[]{240, DumpSegment.ANDROID_PRIMITIVE_ARRAY_NODATA_DUMP}, new int[]{160, 130}, new int[]{-1, -1}};
    private static final int[] d = {16, 24, 32, 40, 50, 60, 72, 80, 90, 100, 110, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 130, 140, 150, 160, 170, 180, 190, 200, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, 240, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 270, 290, 300, SecExceptionCode.SEC_ERROR_STA_LOW_VERSION_DATA_FILE, 320, 360, 375, 400, 430, 438, 460, Config.HQ_IMAGE_WIDTH, 500, VideoRecordParameters.HD_HEIGHT_16_9, 560, 580, 600, 640, 670, 720, 760, 960, 1136, 1280};
    private static final int[][] e = {new int[]{50, 50}, new int[]{64, 64}, new int[]{72, 72}, new int[]{80, 80}, new int[]{88, 88}, new int[]{90, 90}, new int[]{100, 100}, new int[]{110, 110}, new int[]{MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_}, new int[]{160, 160}, new int[]{170, 170}, new int[]{175, 175}, new int[]{180, 180}, new int[]{187, 187}, new int[]{200, 200}, new int[]{240, 240}, new int[]{Callback.DEFAULT_SWIPE_ANIMATION_DURATION, Callback.DEFAULT_SWIPE_ANIMATION_DURATION}, new int[]{290, 290}, new int[]{SecExceptionCode.SEC_ERROR_STA_INVALID_ENCRYPTED_DATA, SecExceptionCode.SEC_ERROR_STA_INVALID_ENCRYPTED_DATA}, new int[]{320, 320}, new int[]{360, 360}, new int[]{375, 375}, new int[]{430, 430}, new int[]{438, 438}, new int[]{460, 460}, new int[]{500, 500}, new int[]{640, 640}, new int[]{800, 800}, new int[]{1024, 1024}, new int[]{-1, -1}};

    public enum AuthTypeEnum {
        SIGN(0),
        PUBLIC(1),
        CUSTOM(2);
        
        byte v;

        private AuthTypeEnum(byte v2) {
            this.v = v2;
        }

        public final byte val() {
            return this.v;
        }

        public static AuthTypeEnum parseAuthType(byte v2) {
            AuthTypeEnum[] values;
            for (AuthTypeEnum e : values()) {
                if (e.v == v2) {
                    return e;
                }
            }
            return null;
        }
    }

    public static String extractPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return path;
        }
        switch (Scheme.ofUri(path)) {
            case FILE:
                String path2 = Scheme.FILE.crop(path);
                if (TextUtils.isEmpty(path2) || !path2.startsWith(a)) {
                    return path2;
                }
                return path2.substring(a.length());
            case HTTP:
            case HTTPS:
                return urlEncode(path);
            default:
                return path;
        }
    }

    public static File extractFile(String path) {
        File file = null;
        if (!TextUtils.isEmpty(path)) {
            try {
                Uri uri = Uri.parse(path);
                if (isLocalFile(uri)) {
                    file = new File(extractPath(path));
                } else if (!isHttp(uri)) {
                    file = new File(path);
                }
            } catch (Exception e2) {
                file = new File(path);
            }
        }
        if (file == null) {
            return file;
        }
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        return file;
    }

    public static boolean isLocalFile(String in) {
        return !TextUtils.isEmpty(in) && (in.startsWith("/") || isLocalFile(Uri.parse(in)));
    }

    public static boolean isLocalFile(Uri uri) {
        if (uri != null && "file".equalsIgnoreCase(uri.getScheme()) && !hasHost(uri)) {
            return true;
        }
        return false;
    }

    public static boolean isHttp(String in) {
        return !TextUtils.isEmpty(in) && isHttp(Uri.parse(in));
    }

    public static boolean isHttp(Uri uri) {
        if (uri == null) {
            return false;
        }
        String scheme = uri.getScheme();
        if (("https".equalsIgnoreCase(scheme) || "http".equalsIgnoreCase(scheme)) && hasHost(uri)) {
            return true;
        }
        return false;
    }

    public static boolean isRtmp(String in) {
        return !TextUtils.isEmpty(in) && isRtmp(Uri.parse(in));
    }

    public static boolean isRtmp(Uri uri) {
        if (uri != null && "rtmp".equalsIgnoreCase(uri.getScheme()) && hasHost(uri)) {
            return true;
        }
        return false;
    }

    public static boolean checkDjangoId(String filedId) {
        if (!TextUtils.isEmpty(filedId) && 32 == filedId.length()) {
            return true;
        }
        return false;
    }

    public static boolean checkAftId(String filedId) {
        if (ConfigManager.getInstance().getCommonConfigItem().checkAftsId == 0 || TextUtils.isEmpty(filedId) || 32 != filedId.length() || !filedId.startsWith("A*")) {
            return false;
        }
        return true;
    }

    public static boolean isDjangoPath(String path) {
        if (TextUtils.isEmpty(path) || 32 != path.length() || path.startsWith(File.separator) || path.startsWith("file") || path.startsWith("http")) {
            return false;
        }
        return true;
    }

    public static boolean checkIdForMdn(String id) {
        if (!isDjangoPath(id)) {
            return false;
        }
        if (id.startsWith("A*")) {
            return isFilePublic(id);
        }
        return true;
    }

    public static boolean hasHost(Uri uri) {
        String host = uri.getHost();
        return host != null && !"".equals(host);
    }

    public static boolean isAlipayAssetsFile(String input) {
        if (!TextUtils.isEmpty(input)) {
            return input.startsWith(ASSET_SCHEMA);
        }
        return false;
    }

    public static boolean isContentFile(String input) {
        if (!TextUtils.isEmpty(input)) {
            return input.startsWith("content://");
        }
        return false;
    }

    public static boolean isVideoFileUri(String uri) {
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uri));
        return mimeType != null && mimeType.startsWith(FileUtils.VIDEO_FILE_START);
    }

    public static String preferImageUrl(String orignUrl, int width, int height) {
        if (TextUtils.isEmpty(orignUrl)) {
            return orignUrl;
        }
        if (width == 0 || height == 0) {
            width = 1280;
            height = 1280;
        }
        String url = orignUrl;
        if (orignUrl.contains("[imgWidth]")) {
            url = b(orignUrl, width, height);
        } else if (orignUrl.contains("[pixelWidth]")) {
            url = a(orignUrl, width, height);
        }
        return url;
    }

    private static String a(String orignUrl, int width, int height) {
        if (width < 0) {
            Logger.W("PathUtils", "width<0", new Object[0]);
        }
        String url = orignUrl.replace("[pixelWidth]", String.valueOf(width));
        if (!orignUrl.contains("[pixelHeight]")) {
            return url;
        }
        if (height < 0) {
            Logger.W("PathUtils", "height<0", new Object[0]);
        }
        return url.replace("[pixelHeight]", String.valueOf(height));
    }

    private static String b(String orignUrl, int width, int height) {
        if (width < 0 || height < 0) {
            Logger.W("PathUtils", "width<0||height<0", new Object[0]);
        }
        Size size = getTfsNearestImageSize(new Size(width, height));
        return orignUrl.replace("[imgWidth]", size.getWidth()).replace("[imgHeight]", size.getHeight());
    }

    public static Size getTfsNearestImageSize(Size size) {
        return a(size, b);
    }

    public static Size getDjangoNearestImageSize(Size size) {
        if (ConfigManager.getInstance().getCommonConfigItem().newImageSizeSwitch != 1) {
            return a(size, c);
        }
        int i = a(d, Math.max(size.getWidth(), size.getHeight()));
        return new Size(d[i], d[i]);
    }

    public static Size getDjangoNearestCutImageSize(Size size) {
        return a(size, e);
    }

    private static Size a(Size size, int[][] sizeList) {
        int width = size.getWidth();
        int height = size.getHeight();
        int variancePre = Integer.MAX_VALUE;
        for (int index = 0; sizeList[index][0] > 0; index++) {
            int varianceCur = ((sizeList[index][0] - size.getWidth()) * (sizeList[index][0] - size.getWidth())) + ((sizeList[index][1] - size.getHeight()) * (sizeList[index][1] - size.getHeight()));
            if (varianceCur < variancePre) {
                variancePre = varianceCur;
                width = sizeList[index][0];
                height = sizeList[index][1];
            }
        }
        return new Size(width, height);
    }

    private static int a(int[] srcArray, int des) {
        int low = 0;
        int high = srcArray.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (des == srcArray[middle]) {
                return middle;
            }
            if (des < srcArray[middle]) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        if (high < 0) {
            return 0;
        }
        if (des < srcArray[high] && high - 1 >= 0) {
            high--;
        }
        return high;
    }

    public static boolean isBase64Image(String content) {
        return isBase64Image(content, null);
    }

    public static boolean isBase64Image(String content, AtomicInteger endIndex) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }
        String lowContentPre = content;
        if (content.length() > 150) {
            lowContentPre = content.substring(0, 150).toLowerCase();
        }
        int index = -1;
        if (lowContentPre.startsWith("data")) {
            index = lowContentPre.indexOf(";base64,");
            if (endIndex != null) {
                endIndex.set(index + 8);
            }
        }
        if (index >= 0) {
            return true;
        }
        return false;
    }

    public static String extractBase64(String content) {
        AtomicInteger endIndex = new AtomicInteger(-1);
        if (!isBase64Image(content, endIndex) || endIndex.get() <= 0) {
            return content;
        }
        return content.substring(endIndex.get());
    }

    public static boolean isNetResource(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        if (isHttp(path) || checkAftId(path) || checkDjangoId(path)) {
            return true;
        }
        return false;
    }

    public static boolean isPreloadNeedReport(String bizId, String fileId) {
        if (TextUtils.isEmpty(fileId) || checkIsResourcePreDownload(bizId)) {
            return false;
        }
        String[] config = ConfigManager.getInstance().getPreloadIdConfig();
        if (config == null) {
            return false;
        }
        int length = config.length;
        int i = 0;
        while (i < length) {
            if (!fileId.equals(config[i])) {
                i++;
            } else if (!UCLogUtil.isPreloadIdInSp(fileId)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean checkIsResourcePreDownload(String bizId) {
        return TextUtils.isEmpty(bizId) || "FRW.PREDL".equals(bizId);
    }

    public static int extraResId(String path) {
        try {
            if (!isResFile(path)) {
                return -1;
            }
            String[] parts = path.split("/");
            if (parts.length > 1) {
                return Integer.parseInt(parts[parts.length - 1]);
            }
            return -1;
        } catch (Exception e2) {
            Logger.E((String) "PathUtils", (Throwable) e2, "extraResId error, path: " + path, new Object[0]);
            return -1;
        }
    }

    public static boolean isResFile(String source) {
        return !TextUtils.isEmpty(source) && source.startsWith(RES_SCHEMA);
    }

    public static String extractDomain(String path) {
        try {
            return Uri.parse(path).getHost();
        } catch (Exception e2) {
            Logger.E((String) "PathUtils", (Throwable) e2, "extractDomain error, path: " + path, new Object[0]);
            return null;
        }
    }

    public static String extractDjangoFileIds(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Uri uri = Uri.parse(path);
        String url = uri.getQueryParameter("fileIds");
        if (TextUtils.isEmpty(url)) {
            return uri.getQueryParameter("fileid");
        }
        return url;
    }

    public static String extractDjangoZoomParams(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return Uri.parse(path).getQueryParameter("zoom");
    }

    public static String urlEncode(String url) {
        return ZURLEncodedUtil.urlEncode(url);
    }

    public static boolean isEncryptedMusic(String fileId) {
        if (!TextUtils.isEmpty(fileId) && fileId.startsWith("A*") && fileId.length() == 32 && ((Base64.decode(fileId.substring(2), 8)[21] >> 4) & 15) == 1) {
            return true;
        }
        return false;
    }

    public static AuthTypeEnum parseAuthType(String id) {
        if (id == null || id.length() != 32) {
            return null;
        }
        try {
            return AuthTypeEnum.parseAuthType((byte) (Base64.decode(id.substring(2), 8)[21] & 15));
        } catch (Exception e2) {
            Logger.E((String) "PathUtils", (Throwable) e2, "parseAuthType error, id: " + id, new Object[0]);
            return AuthTypeEnum.SIGN;
        }
    }

    public static boolean isAftsId(String id) {
        return id != null && id.length() == 32 && id.charAt(0) == 'A' && id.charAt(1) == '*';
    }

    public static boolean isFilePublic(String id) {
        return AuthTypeEnum.PUBLIC == parseAuthType(id);
    }

    public static String genPipePath(int fd) {
        return String.format(PIPE_PATH_SCHEMA, new Object[]{Integer.valueOf(fd)});
    }
}
