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

public final class AjxPathLoader extends AbstractAjxLoader {
    public static final String DOMAIN = "path://";
    private static final String DOMAIN_AJX = "ajx://";
    private static final String DOMAIN_FILE = "file://";
    public static final String SCHEME_PATH = "path";
    private boolean loadFromSDCard;
    private File mDataRoot;
    private File mFileRoot;

    public AjxPathLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray) {
        this(context, sparseArray, null, null, true);
    }

    public AjxPathLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray, String str, String str2, boolean z) {
        super(context, sparseArray);
        this.loadFromSDCard = true;
        str = str == null ? "js/" : str;
        str2 = str2 == null ? "js/" : str2;
        this.mFileRoot = new File(FileUtil.getExternalFilesDir(context), str);
        this.mDataRoot = new File(FileUtil.getFilesDir(context), str2);
        this.loadFromSDCard = z;
    }

    public final String processingPath(@NonNull PictureParams pictureParams) {
        if (!AjxFileInfo.isDebug || AjxFileInfo.isReadFromAjxFile) {
            return PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url);
        }
        return PathUtils.processAssetPath(PathUtils.dispatchImageUrl(this.mContext, pictureParams.url, pictureParams, this.mFileRoot, this.mDataRoot, this.loadFromSDCard, true, true));
    }

    public final float[] readImageSize(@NonNull PictureParams pictureParams) {
        pictureParams.imageSize = 0.0f;
        String ajxUrl = getAjxUrl(this.mContext, pictureParams);
        pictureParams.realUrl = getAjxPath(ajxUrl);
        return lookupAction(ajxUrl).readImageSize(this.mContext, pictureParams);
    }

    public final void loadImage(@Nullable View view, @Nullable IAjxContext iAjxContext, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.imageSize = 0.0f;
        String ajxUrl = getAjxUrl(this.mContext, pictureParams);
        pictureParams.realUrl = getAjxPath(ajxUrl);
        lookupAction(ajxUrl).loadImage(this.mContext, pictureParams, imageCallback, view, iAjxContext);
    }

    public final byte[] loadImage(@NonNull PictureParams pictureParams) {
        pictureParams.imageSize = 0.0f;
        String ajxUrl = getAjxUrl(this.mContext, pictureParams);
        pictureParams.realUrl = getAjxPath(ajxUrl);
        return lookupAction(ajxUrl).loadImage(this.mContext, pictureParams);
    }

    public final void preLoadImage(@NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
        pictureParams.imageSize = 0.0f;
        String ajxUrl = getAjxUrl(this.mContext, pictureParams);
        pictureParams.realUrl = getAjxPath(ajxUrl);
        lookupAction(ajxUrl).loadImage(this.mContext, pictureParams, imageCallback);
    }

    private String getAjxPath(String str) {
        if (str.startsWith(AjxAssetLoader.DOMAIN_ASSET)) {
            return str.substring(22);
        }
        return PathUtils.getNoSchemeUrl(str);
    }

    private String getAjxUrl(@NonNull Context context, PictureParams pictureParams) {
        if (!AjxFileInfo.isDebug || AjxFileInfo.isReadFromAjxFile) {
            StringBuilder sb = new StringBuilder("ajx://");
            sb.append(PathUtils.processPathWithJsPath(pictureParams.jsPath, pictureParams.url));
            return sb.toString();
        }
        return PathUtils.dispatchImageUrl(context, pictureParams.url, pictureParams, this.mFileRoot, this.mDataRoot, true, true, true);
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
}
