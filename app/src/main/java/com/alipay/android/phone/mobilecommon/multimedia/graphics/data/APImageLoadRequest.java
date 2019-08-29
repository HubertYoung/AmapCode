package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APDisplayer;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.Base64Optimization;

public class APImageLoadRequest extends BaseReq {
    public static final int DEFAULT_LOAD_H = 240;
    public static final int DEFAULT_LOAD_W = 240;
    public static final int ORIGINAL_WH = Integer.MAX_VALUE;
    public static final int TYPE_ASSET = 4;
    public static final int TYPE_DATA = 2;
    public static final int TYPE_DJANGO = 1;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_ORIGINAL = 3;
    public Bundle bundle;
    public APImageDownLoadCallback callback;
    public String caller;
    public byte[] data;
    public Bitmap defaultBitmap;
    public Drawable defaultDrawable;
    public APDisplayer displayer;
    public int height;
    public int imageId;
    public Size imageSize;
    public ImageView imageView;
    @Deprecated
    public boolean isBackground;
    public int loadType;
    public String path;
    public ImageWorkerPlugin plugin;
    public String thumbPath;
    public int width;
    public boolean withImageDataInCallback;

    public APImageLoadRequest() {
        this.width = 240;
        this.height = 240;
        this.withImageDataInCallback = false;
        this.base64Optimization = Base64Optimization.createDefault();
    }
}
