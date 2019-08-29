package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.action.IAjxImageLoadAction;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.FileUtil;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.io.File;

public final class AjxFileLoader extends AbstractAjxLoader {
    public static final String AUI_ROOT = "aui_root/";
    private static final String BASE_FILE = "base.js";
    public static final String DEFAULT_DATA_ROOT = "js/";
    public static final String DEFAULT_FILE_ROOT = "js/";
    public static final String DOMAIN_FILE = "file://";
    public static final String FILE_ROOT_DIR = "/";
    public static final String SCHEME_FILE = "file";
    private File mFileRoot;

    public AjxFileLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray) {
        this(context, sparseArray, null);
    }

    public AjxFileLoader(@NonNull Context context, SparseArray<IAjxImageLoadAction> sparseArray, String str) {
        super(context, sparseArray);
        this.mFileRoot = new File(FileUtil.getExternalFilesDir(context), str == null ? "js/" : str);
    }

    public final String processingPath(@NonNull PictureParams pictureParams) {
        if (AjxFileInfo.isDebug) {
            return debugProcessingPath(pictureParams.jsPath, pictureParams.url);
        }
        StringBuilder sb = new StringBuilder("file://");
        sb.append(PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url, this.mFileRoot));
        return sb.toString();
    }

    public final float[] readImageSize(@NonNull PictureParams pictureParams) {
        pictureParams.realUrl = PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url, this.mFileRoot);
        pictureParams.imageSize = 0.0f;
        return lookupAction(2).readImageSize(this.mContext, pictureParams);
    }

    public final void loadImage(@Nullable View view, @Nullable IAjxContext iAjxContext, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.realUrl = PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url, this.mFileRoot);
        pictureParams.imageSize = 0.0f;
        lookupAction(2).loadImage(this.mContext, pictureParams, imageCallback, view, iAjxContext);
    }

    public final byte[] loadImage(@NonNull PictureParams pictureParams) {
        pictureParams.realUrl = PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url, this.mFileRoot);
        pictureParams.imageSize = 0.0f;
        return lookupAction(2).loadImage(this.mContext, pictureParams);
    }

    public final void preLoadImage(@NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.realUrl = PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url, this.mFileRoot);
        pictureParams.imageSize = 0.0f;
        lookupAction(2).loadImage(this.mContext, pictureParams, imageCallback);
    }

    private String debugProcessingPath(String str, @NonNull String str2) {
        if (AjxFileInfo.isReadFromAjxFile && AjxFileInfo.initMode == AjxFileInfo.FILE_INIT_MODE && str2.startsWith("file://") && str2.endsWith(BASE_FILE)) {
            return BASE_FILE;
        }
        StringBuilder sb = new StringBuilder("file://");
        sb.append(PathUtils.processPathWithJsPath(str, str2, this.mFileRoot));
        return sb.toString();
    }
}
