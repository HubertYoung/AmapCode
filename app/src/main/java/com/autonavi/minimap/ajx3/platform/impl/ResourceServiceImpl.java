package com.autonavi.minimap.ajx3.platform.impl;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.AjxLoaderManager;
import com.autonavi.minimap.ajx3.loader.IAjxImageLoader;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.platform.ackor.IAjxFileReadListener;
import com.autonavi.minimap.ajx3.platform.ackor.IResourceService;

public class ResourceServiceImpl implements IResourceService {
    private AjxFileInfo mAjxFileInfo;
    private AjxLoaderManager mAjxLoaderManager;
    private Context mContext;
    private IAjxFileReadListener mFileReadListener;

    public ResourceServiceImpl(Context context, AjxLoaderManager ajxLoaderManager, @NonNull AjxFileInfo ajxFileInfo, IAjxFileReadListener iAjxFileReadListener) {
        this.mContext = context;
        this.mAjxLoaderManager = ajxLoaderManager;
        this.mAjxFileInfo = ajxFileInfo;
        this.mFileReadListener = iAjxFileReadListener;
    }

    public AssetManager getAssets() {
        return this.mContext.getAssets();
    }

    public String onPreOpenFileStream(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        IAjxImageLoader lookupLoader = this.mAjxLoaderManager.lookupLoader(str);
        if (lookupLoader == null) {
            return null;
        }
        PictureParams pictureParams = new PictureParams();
        pictureParams.url = str;
        return lookupLoader.processingPath(pictureParams);
    }

    public AjxFileInfo getAjxFileInfo() {
        return this.mAjxFileInfo;
    }

    public IAjxFileReadListener getAjxFileReadListener() {
        return this.mFileReadListener;
    }
}
