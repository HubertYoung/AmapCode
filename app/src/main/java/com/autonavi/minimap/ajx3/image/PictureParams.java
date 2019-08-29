package com.autonavi.minimap.ajx3.image;

import android.net.Uri;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.loader.AjxPathLoader;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;

public class PictureParams {
    @ColorInt
    public int SVGColor = 0;
    public String SVGData;
    public String background;
    @ColorInt
    public int bgColor;
    public int bitmapHeight = 0;
    public int bitmapWidth = 0;
    public float blur = 0.0f;
    @ColorInt
    public int borderColor = -16777216;
    public int[] borderWidth;
    public float brightness = 1.0f;
    public boolean canSupportBorderClip = true;
    public boolean clipChildren = true;
    public int[] cornerRadius;
    public int height;
    public float imageSize;
    public boolean isNeedScale = false;
    public boolean isPreLoad = false;
    public boolean isRunOnUI = false;
    public boolean isSyncLoadImg = false;
    public boolean isVolatile = false;
    public String jsPath;
    public int patchIndex;
    public String realUrl;
    public float saturate = 1.0f;
    public int scaleMode = 0;
    public int shadowBlur = 0;
    @ColorInt
    public int shadowColor = -16777216;
    public int shadowX = 0;
    public int shadowY = 0;
    public int[] stretch;
    public String url;
    public int width;

    public PictureParams copy() {
        PictureParams pictureParams = new PictureParams();
        pictureParams.url = this.url;
        pictureParams.realUrl = this.realUrl;
        pictureParams.jsPath = this.jsPath;
        pictureParams.patchIndex = this.patchIndex;
        pictureParams.bgColor = this.bgColor;
        pictureParams.cornerRadius = this.cornerRadius;
        pictureParams.borderWidth = this.borderWidth;
        pictureParams.borderColor = this.borderColor;
        pictureParams.stretch = this.stretch;
        pictureParams.imageSize = this.imageSize;
        pictureParams.scaleMode = this.scaleMode;
        pictureParams.width = this.width;
        pictureParams.height = this.height;
        pictureParams.saturate = this.saturate;
        pictureParams.brightness = this.brightness;
        pictureParams.shadowX = this.shadowX;
        pictureParams.shadowY = this.shadowY;
        pictureParams.shadowBlur = this.shadowBlur;
        pictureParams.shadowColor = this.shadowColor;
        pictureParams.isSyncLoadImg = this.isSyncLoadImg;
        pictureParams.isVolatile = this.isVolatile;
        pictureParams.isNeedScale = this.isNeedScale;
        pictureParams.clipChildren = this.clipChildren;
        pictureParams.blur = this.blur;
        pictureParams.brightness = this.brightness;
        pictureParams.saturate = this.saturate;
        pictureParams.background = this.background;
        pictureParams.isRunOnUI = this.isRunOnUI;
        pictureParams.isPreLoad = this.isPreLoad;
        pictureParams.bitmapWidth = this.bitmapWidth;
        pictureParams.bitmapHeight = this.bitmapHeight;
        pictureParams.SVGColor = this.SVGColor;
        return pictureParams;
    }

    public static PictureParams make(IAjxContext iAjxContext, String str, boolean z) {
        PictureParams pictureParams = new PictureParams();
        pictureParams.url = str;
        pictureParams.isSyncLoadImg = z;
        if (iAjxContext != null) {
            pictureParams.isRunOnUI = iAjxContext.getJsContext().isRunOnUI();
            pictureParams.jsPath = iAjxContext.getJsPath();
            pictureParams.patchIndex = getBundleIndex(iAjxContext, str);
        }
        return pictureParams;
    }

    public PictureParams match(IAjxContext iAjxContext, String str) {
        if (iAjxContext != null) {
            this.isRunOnUI = iAjxContext.getJsContext().isRunOnUI();
            this.jsPath = iAjxContext.getJsPath();
            this.patchIndex = getBundleIndex(iAjxContext, str);
        }
        return this;
    }

    private static int getBundleIndex(IAjxContext iAjxContext, String str) {
        if (TextUtils.isEmpty(str) || (!str.startsWith(AjxPathLoader.DOMAIN) && !TextUtils.isEmpty(Uri.parse(str).getScheme()))) {
            return 0;
        }
        return iAjxContext.getPatchIndex(AjxFileInfo.getBundleName(str));
    }
}
