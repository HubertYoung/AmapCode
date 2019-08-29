package com.alipay.android.phone.mobilesdk.storage.file;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import java.io.File;

public class ZExternalFile extends BaseFile {
    protected static final String ExtDataTunnel = "ExtDataTunnel";
    private static final String Tag = "ZExternalFile";
    private static final long serialVersionUID = -3489082633723468737L;

    public ZExternalFile(Context context, String groupId, String name, String subPath) {
        super(buildPath(context, groupId, name, subPath));
    }

    public ZExternalFile(Context context, String groupId, String name) {
        super(buildPath(context, groupId, name, null));
    }

    private static String buildPath(Context context, String groupId, String name, String subPath) {
        if (context == null || TextUtils.isEmpty(groupId) || TextUtils.isEmpty(name)) {
            return "";
        }
        File externalFilesDir = null;
        try {
            externalFilesDir = context.getExternalFilesDir(groupId);
        } catch (Throwable t) {
            Log.w(Tag, "context.getExternalFilesDir(" + groupId + ") failed!", t);
        }
        if (externalFilesDir == null) {
            Log.w(Tag, "externalFilesDir is null");
            String sdcard = FileUtils.getSDPath();
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
