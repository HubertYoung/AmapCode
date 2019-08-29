package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.ae.AEUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;

public class AjxFileUtils {
    private static final int LEN_SCHEMA_SPLIT = 3;

    private static Bitmap getImage(Context context, String str, int i) {
        FileInputStream fileInputStream;
        if (AEUtil.isAjx3Debug) {
            File externalFilesDir = FileUtil.getExternalFilesDir(context);
            StringBuilder sb = new StringBuilder("js/");
            sb.append(PathUtils.getNoSchemeUrl(str));
            String absolutePath = FileUtil.getAbsolutePath(externalFilesDir, sb.toString());
            File file = new File(ImageSizeUtils.getImagePathBySize(absolutePath, ImageSizeUtils.getSizeNameInFile(context, absolutePath)));
            if (file.exists()) {
                try {
                    fileInputStream = new FileInputStream(file);
                    try {
                        Bitmap decodeStream = BitmapFactory.decodeStream(fileInputStream);
                        ahe.a((Closeable) fileInputStream);
                        return decodeStream;
                    } catch (Exception e) {
                        e = e;
                        try {
                            e.printStackTrace();
                            ahe.a((Closeable) fileInputStream);
                            return null;
                        } catch (Throwable th) {
                            th = th;
                            ahe.a((Closeable) fileInputStream);
                            throw th;
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    fileInputStream = null;
                    e.printStackTrace();
                    ahe.a((Closeable) fileInputStream);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = null;
                    ahe.a((Closeable) fileInputStream);
                    throw th;
                }
            }
        } else {
            String ajxUrl = getAjxUrl(str);
            byte[] fileDataByPath = AjxFileInfo.getFileDataByPath(ImageSizeUtils.getImagePathBySize(ajxUrl, ImageSizeUtils.getSizeNameFromAjxFile(context.getApplicationContext(), ajxUrl, i)), i);
            if (fileDataByPath != null) {
                return BitmapFactory.decodeByteArray(fileDataByPath, 0, fileDataByPath.length);
            }
        }
        return null;
    }

    public static Bitmap getImage(IAjxContext iAjxContext, String str) {
        String scheme = Uri.parse(str).getScheme();
        String jsPath = iAjxContext.getJsPath();
        if (TextUtils.isEmpty(scheme) && !TextUtils.isEmpty(jsPath)) {
            str = PathUtils.processPath(jsPath.substring(0, jsPath.lastIndexOf("/") + 1), str);
        }
        return getImage(iAjxContext.getNativeContext(), str, iAjxContext.getPatchIndex(AjxFileInfo.getBundleName(str)));
    }

    public static String getAjxUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String scheme = Uri.parse(str).getScheme();
        return !TextUtils.isEmpty(scheme) ? str.substring(scheme.length() + LEN_SCHEMA_SPLIT) : str;
    }
}
