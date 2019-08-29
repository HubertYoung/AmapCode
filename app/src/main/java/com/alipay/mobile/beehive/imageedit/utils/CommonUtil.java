package com.alipay.mobile.beehive.imageedit.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeOptions.MaxLenMode;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeResult;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.io.File;
import java.util.concurrent.ThreadPoolExecutor;

public class CommonUtil {
    private static final String TAG = "CommonUtil";

    public static File getDCIMDir() {
        File ret = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!ret.exists()) {
            ret.mkdirs();
        }
        return ret;
    }

    public static String url2AbsPath(String url) {
        if (TextUtils.isEmpty(url)) {
            ImageEditLogger.warn((String) "CommonUtil", (String) "url2AbsPath:url is empty.");
            return "";
        } else if (!url.startsWith(File.separator)) {
            return Uri.parse(url).getPath();
        } else {
            ImageEditLogger.warn((String) "CommonUtil", "url2AbsPath:url seems to be  abs path." + url);
            return url;
        }
    }

    public static String absPath2Url(String path) {
        if (TextUtils.isEmpty(path)) {
            ImageEditLogger.warn((String) "CommonUtil", (String) "absPath2Url:path is empty.");
            return "";
        }
        if (!path.startsWith(File.separator)) {
            ImageEditLogger.warn((String) "CommonUtil", (String) "absPath2Url:path is not start with File.separator.");
        }
        return "file://" + path;
    }

    public static ThreadPoolExecutor getExecutor(ScheduleType type) {
        return ((TaskScheduleService) MicroServiceUtil.getMicroService(TaskScheduleService.class)).acquireExecutor(type);
    }

    public static Bitmap decodePath(String path) {
        String filePath;
        try {
            MultimediaImageProcessor p = (MultimediaImageProcessor) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaImageProcessor.class.getName());
            APDecodeOptions option = new APDecodeOptions();
            option.mode = new MaxLenMode(Integer.valueOf(1280));
            if (path.startsWith("file://")) {
                filePath = path.substring("file://".length());
            } else {
                filePath = path;
            }
            APDecodeResult result = p.decodeBitmap(new File(filePath), option);
            if (result.isSuccess()) {
                return result.bitmap;
            }
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) "CommonUtil", (Throwable) ex);
        }
        return null;
    }
}
