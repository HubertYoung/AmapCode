package com.alipay.mobile.nebula.resourcehandler;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.nebula.provider.H5APMTool;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5ResourceHandlerUtil {
    public static final String AUDIO = "audio";
    public static final String IMAGE = "image";
    public static final String OTHER = "other";
    public static final String RESOURCE = "https://resource/";
    public static final String VIDEO = "video";

    public static String localIdToUrl(String localId, String type) {
        return "https://resource/" + localId + "." + type;
    }

    public static String apUrlToFilePath(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (!url.endsWith("image") && !url.endsWith("video") && !url.endsWith("audio") && !url.endsWith(OTHER)) {
            return url;
        }
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null || TextUtils.isEmpty(uri.getPath())) {
            return url;
        }
        String[] details = uri.getPath().replace("/", "").split("\\.");
        if (details == null || details.length <= 1) {
            return url;
        }
        String localId = details[0];
        try {
            if (TextUtils.isEmpty(localId)) {
                return url;
            }
            H5APMTool h5APMTool = (H5APMTool) H5Utils.getProvider(H5APMTool.class.getName());
            if (h5APMTool != null) {
                return h5APMTool.decodeToPath(localId);
            }
            return url;
        } catch (Exception e) {
            H5Log.e((String) "", (Throwable) e);
            return url;
        }
    }
}
