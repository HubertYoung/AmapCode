package com.alipay.mobile.nebula.appcenter.util;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5BaseFile;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import java.io.File;

public class H5ZExternalFile extends H5BaseFile {
    protected static final String ExtDataTunnel = "ExtDataTunnel";
    private static final String Tag = "H5ZExternalFile";
    private static final long serialVersionUID = -3489082633723468737L;

    public H5ZExternalFile(Context context, String groupId, String name, String subPath) {
        super(buildPath(context, groupId, name, subPath));
    }

    public H5ZExternalFile(Context context, String groupId, String name) {
        super(buildPath(context, groupId, name, null));
    }

    private static String buildPath(Context context, String groupId, String name, String subPath) {
        if (context == null || TextUtils.isEmpty(groupId) || TextUtils.isEmpty(name)) {
            return "";
        }
        File externalFilesDir = null;
        try {
            externalFilesDir = context.getExternalFilesDir(groupId);
        } catch (Exception e) {
            H5Log.e((String) Tag, (Throwable) e);
        }
        if (externalFilesDir == null) {
            String sdcard = H5FileUtil.getSDPath();
            if (TextUtils.isEmpty(sdcard)) {
                return "";
            }
            externalFilesDir = new File(sdcard + File.separator + "ExtDataTunnel" + File.separator + AutoJsonUtils.JSON_FILES + File.separator + groupId);
        }
        String dir = externalFilesDir.getAbsolutePath() + formatPath(subPath);
        File fileDir = new File(dir);
        if (!fileDir.isDirectory() || !fileDir.exists()) {
            fileDir.mkdirs();
        }
        return dir + name;
    }
}
