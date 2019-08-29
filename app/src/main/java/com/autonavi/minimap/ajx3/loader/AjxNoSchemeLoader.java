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

public class AjxNoSchemeLoader extends AbstractAjxLoader {
    private static final String BASE_FILE = "base.js";
    public static final String DOMAIN = null;
    private static final String DOMAIN_AJX = "ajx://";
    public static final String SCHEME_NULL = null;
    private File mDataRoot;
    private File mFileRoot;
    private boolean mSDCardEnable;

    public AjxNoSchemeLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray) {
        this(context, sparseArray, null, null, true);
    }

    public AjxNoSchemeLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray, String str, String str2, boolean z) {
        super(context, sparseArray);
        this.mSDCardEnable = true;
        str = str == null ? "js/" : str;
        str2 = str2 == null ? "js/" : str2;
        this.mFileRoot = new File(FileUtil.getExternalFilesDir(context), str);
        this.mDataRoot = new File(FileUtil.getFilesDir(context), str2);
        this.mSDCardEnable = z;
    }

    public String processingPath(@NonNull PictureParams pictureParams) {
        if (!AjxFileInfo.isDebug) {
            return getAjxUrl(this.mContext, pictureParams);
        }
        return debugProcessingPath(this.mContext, pictureParams);
    }

    public float[] readImageSize(@NonNull PictureParams pictureParams) {
        pictureParams.imageSize = 0.0f;
        String ajxUrl = getAjxUrl(this.mContext, pictureParams);
        pictureParams.realUrl = getAjxPath(ajxUrl);
        return lookupAction(ajxUrl).readImageSize(this.mContext, pictureParams);
    }

    public void loadImage(@Nullable View view, @Nullable IAjxContext iAjxContext, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.imageSize = 0.0f;
        String ajxUrl = getAjxUrl(this.mContext, pictureParams);
        pictureParams.realUrl = getAjxPath(ajxUrl);
        lookupAction(ajxUrl).loadImage(this.mContext, pictureParams, imageCallback, view, iAjxContext);
    }

    public byte[] loadImage(@NonNull PictureParams pictureParams) {
        pictureParams.imageSize = 0.0f;
        String ajxUrl = getAjxUrl(this.mContext, pictureParams);
        pictureParams.realUrl = getAjxPath(ajxUrl);
        return lookupAction(ajxUrl).loadImage(this.mContext, pictureParams);
    }

    public void preLoadImage(@NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.imageSize = 0.0f;
        String ajxUrl = getAjxUrl(this.mContext, pictureParams);
        pictureParams.realUrl = getAjxPath(ajxUrl);
        lookupAction(ajxUrl).loadImage(this.mContext, pictureParams, imageCallback);
    }

    private IAjxImageLoadAction lookupAction(String str) {
        if (str.startsWith(AjxAssetLoader.DOMAIN_ASSET)) {
            return lookupAction(3);
        }
        if (str.startsWith("file://")) {
            return lookupAction(2);
        }
        return lookupAction(0);
    }

    private String getAjxPath(String str) {
        if (str.startsWith(AjxAssetLoader.DOMAIN_ASSET)) {
            return str.substring(22);
        }
        return PathUtils.getNoSchemeUrl(str);
    }

    private String getAjxUrl(@NonNull Context context, PictureParams pictureParams) {
        String handleNoSchemeUrl = PathUtils.handleNoSchemeUrl(context, pictureParams.jsPath, pictureParams.url);
        if (!handleNoSchemeUrl.startsWith(AjxPathLoader.DOMAIN)) {
            return handleNoSchemeUrl;
        }
        if (!AjxFileInfo.isDebug || AjxFileInfo.isReadFromAjxFile) {
            StringBuilder sb = new StringBuilder("ajx://");
            sb.append(PathUtils.getNoSchemeUrl(handleNoSchemeUrl));
            return sb.toString();
        }
        return PathUtils.dispatchImageUrl(context, handleNoSchemeUrl, pictureParams, this.mFileRoot, this.mDataRoot, this.mSDCardEnable, true, true);
    }

    private String debugProcessingPath(@NonNull Context context, PictureParams pictureParams) {
        if (!AjxFileInfo.isReadFromAjxFile || !pictureParams.url.endsWith(BASE_FILE)) {
            return getAjxUrl(context, pictureParams);
        }
        return BASE_FILE;
    }
}
