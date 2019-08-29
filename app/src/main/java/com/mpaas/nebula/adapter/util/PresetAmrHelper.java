package com.mpaas.nebula.adapter.util;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5PreSetPkgInfo;
import com.alipay.mobile.nebula.util.H5Log;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PresetAmrHelper {
    private static final String a = PresetAmrHelper.class.getSimpleName();

    public static List<H5PreSetPkgInfo> listArmFiles(Context context, String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            String[] results = context.getAssets().list(path);
            if (results == null) {
                return null;
            }
            List packageInfos = new ArrayList();
            for (String filename : results) {
                int lastDotPos = filename.lastIndexOf(".amr");
                if (lastDotPos != -1 && filename.contains("_")) {
                    int pos = filename.lastIndexOf("_");
                    if (pos != -1) {
                        String appId = filename.substring(0, pos);
                        String version = filename.substring(pos + 1, lastDotPos);
                        H5Log.d(a, "parse preset package filename = " + filename + ", appId = " + appId + ", version = " + version);
                        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(version)) {
                            packageInfos.add(new H5PreSetPkgInfo(appId, version, context.getAssets().open(path + File.separator + filename), false));
                        }
                    }
                }
            }
            return packageInfos;
        } catch (IOException e) {
            H5Log.e(a, (Throwable) e);
            return null;
        }
    }

    public static InputStream getAmrFile(Context context, String path, String targetAppId, String targetVersion) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            String[] results = context.getAssets().list(path);
            if (results == null) {
                return null;
            }
            for (String filename : results) {
                int lastDotPos = filename.lastIndexOf(".amr");
                if (lastDotPos != -1 && filename.contains("_")) {
                    int pos = filename.lastIndexOf("_");
                    if (pos != -1) {
                        String appId = filename.substring(0, pos);
                        String version = filename.substring(pos + 1, lastDotPos);
                        H5Log.d(a, "parse preset package filename = " + filename + ", appId = " + appId + ", version = " + version);
                        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(version) && appId.equals(targetAppId) && version.equals(targetVersion)) {
                            return context.getAssets().open(path + File.separator + filename);
                        }
                    } else {
                        continue;
                    }
                }
            }
            return null;
        } catch (IOException e) {
            H5Log.e(a, (Throwable) e);
        }
    }
}
