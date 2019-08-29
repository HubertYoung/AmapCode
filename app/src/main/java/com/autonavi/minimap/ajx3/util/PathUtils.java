package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.debug.AjxDebugConstant;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.AjxAssetLoader;
import com.autonavi.minimap.ajx3.loader.AjxFileLoader;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import java.io.File;

public class PathUtils {
    private static final int LEN_SCHEMA_SPLIT = 3;

    public static String preProcessUrl(String str) {
        return str;
    }

    public static String processPath(@NonNull IAjxContext iAjxContext, String str) {
        if (TextUtils.isEmpty(Uri.parse(str).getScheme())) {
            String jsPath = iAjxContext.getJsPath();
            if (!TextUtils.isEmpty(jsPath)) {
                int lastIndexOf = jsPath.lastIndexOf(47);
                if (lastIndexOf > 0 && lastIndexOf < jsPath.length()) {
                    return processPath(jsPath.substring(0, lastIndexOf + 1), str);
                }
            }
        }
        return str;
    }

    public static String processPathWithJsPath(String str, @NonNull String str2) {
        if (!str2.startsWith("../")) {
            return getNoSchemeUrl(str2);
        }
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        String noSchemeUrl = getNoSchemeUrl(str);
        return processPath(noSchemeUrl.substring(0, noSchemeUrl.lastIndexOf("/") + 1), str2);
    }

    public static String processPathWithJsPath(String str, @NonNull String str2, File file) {
        if (!str2.startsWith("../")) {
            String noSchemeUrl = getNoSchemeUrl(str2);
            if (noSchemeUrl.startsWith(AjxFileLoader.AUI_ROOT)) {
                return FileUtil.getAbsolutePath(file, noSchemeUrl.substring(9));
            }
            if (noSchemeUrl.startsWith("/aui_root/")) {
                return FileUtil.getAbsolutePath(file, noSchemeUrl.substring(10));
            }
            return !noSchemeUrl.startsWith("/") ? "/".concat(String.valueOf(noSchemeUrl)) : noSchemeUrl;
        } else if (TextUtils.isEmpty(str)) {
            return str2;
        } else {
            String noSchemeUrl2 = getNoSchemeUrl(str);
            return processPath(noSchemeUrl2.substring(0, noSchemeUrl2.lastIndexOf("/") + 1), str2);
        }
    }

    public static int getPatchIndexByUrl(@NonNull String str) {
        String bundleName = AjxFileInfo.getBundleName(str);
        if (!TextUtils.isEmpty(bundleName)) {
            return AjxFileInfo.getLatestPatchIndex(bundleName);
        }
        return 0;
    }

    public static String dispatchImageUrl(@NonNull Context context, @NonNull String str, PictureParams pictureParams, File file, File file2, boolean z, boolean z2, boolean z3) {
        String str2;
        String str3;
        if (AjxDebugConstant.DEBUGGER_OPEN) {
            int lastIndexOf = str.lastIndexOf(".");
            if (lastIndexOf > 2) {
                String substring = str.substring(0, lastIndexOf);
                if (substring.endsWith("@3x") || substring.endsWith("@2x") || substring.endsWith("@1x")) {
                    StringBuilder sb = new StringBuilder("警告！！！\n    非法url = ");
                    sb.append(str);
                    sb.append("\n    请检查url参数是否正常，请勿调用ProcessingPath提前处理url 或者 直接在url中指定 @1x/@2x/@3x\n    如果有必要请找Android支撑同学了解正确用法");
                    LogHelper.showErrorMsg2(sb.toString());
                }
            }
        }
        String noSchemeUrl = getNoSchemeUrl(str);
        boolean startsWith = noSchemeUrl.startsWith("../");
        if (z && "mounted".equals(Environment.getExternalStorageState())) {
            if (startsWith) {
                str3 = processPath(file.getAbsolutePath(), noSchemeUrl);
            } else {
                str3 = FileUtil.getAbsolutePath(file, noSchemeUrl);
            }
            String sizeNameInFile = ImageSizeUtils.getSizeNameInFile(context, str3);
            String imagePathBySize = ImageSizeUtils.getImagePathBySize(str3, sizeNameInFile);
            int imageSizeByName = ImageSizeUtils.getImageSizeByName(sizeNameInFile);
            if (FileUtil.checkFileInvalid(imagePathBySize)) {
                if (pictureParams != null) {
                    pictureParams.realUrl = imagePathBySize;
                    pictureParams.imageSize = (float) imageSizeByName;
                }
                String concat = "file://".concat(String.valueOf(imagePathBySize));
                StringBuilder sb2 = new StringBuilder("AjxPathLoader: dispatch image url from ");
                sb2.append(str);
                sb2.append(" to ");
                sb2.append(concat);
                sb2.append(" !");
                LogHelper.d(sb2.toString());
                return concat;
            }
        }
        if (z2) {
            if (startsWith) {
                str2 = processPath(file2.getAbsolutePath(), noSchemeUrl);
            } else {
                str2 = FileUtil.getAbsolutePath(file2, noSchemeUrl);
            }
            String sizeNameInFile2 = ImageSizeUtils.getSizeNameInFile(context, str2);
            String imagePathBySize2 = ImageSizeUtils.getImagePathBySize(str2, sizeNameInFile2);
            int imageSizeByName2 = ImageSizeUtils.getImageSizeByName(sizeNameInFile2);
            if (FileUtil.checkFileInvalid(imagePathBySize2)) {
                if (pictureParams != null) {
                    pictureParams.realUrl = imagePathBySize2;
                    pictureParams.imageSize = (float) imageSizeByName2;
                }
                String concat2 = "file://".concat(String.valueOf(imagePathBySize2));
                StringBuilder sb3 = new StringBuilder("AjxPathLoader: dispatch image url from ");
                sb3.append(str);
                sb3.append(" to ");
                sb3.append(concat2);
                sb3.append(" !");
                LogHelper.d(sb3.toString());
                return concat2;
            }
        }
        if (!z3) {
            return str;
        }
        String concat3 = AjxAssetLoader.DOMAIN_ASSET.concat(String.valueOf(noSchemeUrl));
        String sizeNameInAsset = ImageSizeUtils.getSizeNameInAsset(context, concat3);
        String imagePathBySize3 = ImageSizeUtils.getImagePathBySize(concat3, sizeNameInAsset);
        int imageSizeByName3 = ImageSizeUtils.getImageSizeByName(sizeNameInAsset);
        if (pictureParams != null) {
            pictureParams.realUrl = imagePathBySize3;
            pictureParams.imageSize = (float) imageSizeByName3;
        }
        StringBuilder sb4 = new StringBuilder("AjxPathLoader: dispatch image url from ");
        sb4.append(str);
        sb4.append(" to ");
        sb4.append(imagePathBySize3);
        sb4.append(" !");
        LogHelper.d(sb4.toString());
        return imagePathBySize3;
    }

    public static String handleNoSchemeUrl(@NonNull Context context, String str, @NonNull String str2) {
        if (str2.startsWith(AjxFileLoader.AUI_ROOT)) {
            File file = new File(FileUtil.getExternalFilesDir(context), "js/");
            StringBuilder sb = new StringBuilder("file://");
            sb.append(FileUtil.getAbsolutePath(file, str2.substring(9)));
            return sb.toString();
        } else if (str2.startsWith("/")) {
            return "file://".concat(String.valueOf(str2));
        } else {
            if (TextUtils.isEmpty(str)) {
                return str2;
            }
            String noSchemeUrl = getNoSchemeUrl(str);
            String scheme = Uri.parse(str).getScheme();
            if (TextUtils.isEmpty(scheme)) {
                return str2;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(scheme);
            sb2.append("://");
            sb2.append(processPath(noSchemeUrl.substring(0, noSchemeUrl.lastIndexOf("/") + 1), str2));
            return sb2.toString();
        }
    }

    public static String rectifyFileScheme(@NonNull String str) {
        if (TextUtils.isEmpty(str) || !str.startsWith("file://")) {
            return str;
        }
        String noSchemeUrl = getNoSchemeUrl(str);
        boolean startsWith = noSchemeUrl.startsWith("/");
        StringBuilder sb = new StringBuilder("file://");
        sb.append(startsWith ? "" : "/");
        sb.append(noSchemeUrl);
        return sb.toString();
    }

    public static String processPath(@Nullable String str, @Nullable String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.equals(str2, Constants.ANIMATOR_NONE)) {
            return "";
        }
        if (!TextUtils.isEmpty(Uri.parse(str2).getScheme())) {
            return str2;
        }
        while (str2.startsWith("../")) {
            if (!TextUtils.isEmpty(str) && str.contains("/") && !str.endsWith("://")) {
                int lastIndexOf = str.lastIndexOf("/");
                if (lastIndexOf == str.length() - 1) {
                    str = str.substring(0, lastIndexOf);
                }
                int lastIndexOf2 = str.lastIndexOf("/");
                if (lastIndexOf2 >= 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str.substring(0, lastIndexOf2));
                    sb.append("/");
                    str = sb.toString();
                }
            }
            str2 = str2.substring(3);
        }
        StringBuilder sb2 = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb2.append(str);
        sb2.append(str2);
        return sb2.toString().replace("./", "");
    }

    public static String getNoSchemeUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String scheme = Uri.parse(str).getScheme();
        return !TextUtils.isEmpty(scheme) ? str.substring(scheme.length() + LEN_SCHEMA_SPLIT) : str;
    }

    public static String processAssetPath(String str) {
        if (!str.startsWith(AjxAssetLoader.DOMAIN_ASSET)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(AjxAssetLoader.DOMAIN);
        sb.append(str.substring(22));
        return sb.toString();
    }

    public static boolean isGif(String str) {
        return !TextUtils.isEmpty(str) && str.endsWith(".gif");
    }

    public static boolean isGif(Uri uri) {
        if (uri == null) {
            return false;
        }
        String path = uri.getPath();
        if (TextUtils.isEmpty(path) || !path.endsWith(".gif")) {
            return false;
        }
        return true;
    }
}
