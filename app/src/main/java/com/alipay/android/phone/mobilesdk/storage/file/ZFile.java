package com.alipay.android.phone.mobilesdk.storage.file;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;

public class ZFile extends BaseFile {
    private static final long serialVersionUID = -1952946196402763362L;

    public ZFile(Context context, String groupId, String name, String subPath) {
        super(buildPath(context, groupId, name, subPath));
    }

    public ZFile(Context context, String groupId, String name) {
        super(buildPath(context, groupId, name, null));
    }

    private static String buildPath(Context context, String groupId, String name, String subPath) {
        if (context == null || TextUtils.isEmpty(groupId) || TextUtils.isEmpty(name)) {
            return "";
        }
        if (TextUtils.isEmpty(context.getFilesDir().getAbsolutePath())) {
            return "";
        }
        String dir = context.getFilesDir() + File.separator + groupId + formatPath(subPath);
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return dir + name;
    }
}
