package com.autonavi.minimap.ajx3.widget.property;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.css.value.FilterValue;
import com.autonavi.minimap.ajx3.image.PictureFactory;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.IAjxImageLoader;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.ArrayUtils;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.PathUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.util.ViewUtils;
import com.uc.webview.export.extension.UCCore;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import pl.droidsonroids.gif.GifDrawable;

public class PictureHelper {
    protected final boolean isSVG;
    /* access modifiers changed from: private */
    public final BaseProperty mAttribute;
    protected boolean mBackgroundIsChanged = false;
    private ImageCallback mBgCallback;
    protected boolean mBgIsChanged = false;
    protected final PictureParams mBgParams;
    protected Bitmap mBitmap;
    private BorderHelper mBorderHelper;
    private ColorMatrix mBrightnessMatrix;
    protected String mCurrentUrl;
    /* access modifiers changed from: private */
    public boolean mHasImageLoadError = false;
    private boolean mHasImageLoaded = false;
    protected boolean mHoverBgIsChanged = false;
    protected String mHoverBgUrl;
    protected Bitmap mHoverBitmap;
    private ImageCallback mHoverCallback;
    /* access modifiers changed from: private */
    public boolean mIsLoading = false;
    protected String mLoadUrl;
    private ColorMatrix mMatrix;
    /* access modifiers changed from: private */
    public boolean mNeedErrorEventCallback = false;
    private boolean mNeedLoadEventCallback = false;
    /* access modifiers changed from: private */
    public boolean mNeedReload = false;
    private Paint mPaint;
    private ImageCallback mSVGCallback;
    protected boolean mSVGIsChanged = false;
    private IAjxImageLoader mSVGLoader;
    protected PictureParams mSVGParams;
    private ColorMatrix mSaturateMatrix;
    private ShadowHelper mShadowHelper;
    private ImageCallback mSrcCallback;
    protected boolean mSrcIsChanged = false;
    protected final PictureParams mSrcParams;
    private boolean mSyncLoadSVG = false;
    /* access modifiers changed from: private */
    public final View mView;

    static class BgCallback implements ImageCallback {
        private WeakReference<PictureHelper> mPictureHelper;

        public BgCallback(PictureHelper pictureHelper) {
            this.mPictureHelper = new WeakReference<>(pictureHelper);
        }

        public void onGifLoaded(GifDrawable gifDrawable) {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                pictureHelper.mIsLoading = false;
                pictureHelper.mBitmap = null;
                pictureHelper.mCurrentUrl = pictureHelper.mLoadUrl;
                pictureHelper.mLoadUrl = null;
                pictureHelper.updatePicture(gifDrawable);
                loadFinish();
            }
        }

        public void onBitmapLoaded(Bitmap bitmap) {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                pictureHelper.mIsLoading = false;
                pictureHelper.mBitmap = bitmap;
                pictureHelper.mCurrentUrl = pictureHelper.mLoadUrl;
                pictureHelper.mLoadUrl = null;
                pictureHelper.updatePicture(PictureFactory.edit(pictureHelper.mView.getContext(), bitmap, pictureHelper.mBgParams));
                loadFinish();
            }
        }

        public void onBitmapFailed(Drawable drawable) {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                pictureHelper.mIsLoading = false;
                pictureHelper.mBitmap = null;
                pictureHelper.mLoadUrl = null;
                pictureHelper.mHasImageLoadError = true;
                if (pictureHelper.mNeedErrorEventCallback) {
                    pictureHelper.mHasImageLoadError = false;
                    pictureHelper.mAttribute.mAjxContext.invokeJsEvent(new Builder().setEventName("error").setNodeId(pictureHelper.getNodeId()).build());
                }
                loadFinish();
            }
        }

        public void onPrepareLoad(Drawable drawable) {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                pictureHelper.mCurrentUrl = null;
                if (drawable == null) {
                    drawable = PictureFactory.edit(pictureHelper.mView.getContext(), null, pictureHelper.mBgParams);
                }
                ViewUtils.setBackground(pictureHelper.mView, drawable);
            }
        }

        private void loadFinish() {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                if (pictureHelper.mNeedReload) {
                    pictureHelper.update();
                    return;
                }
                if (!TextUtils.isEmpty(pictureHelper.mSrcParams.url)) {
                    pictureHelper.updateSrc();
                }
            }
        }
    }

    static class HoverCallback implements ImageCallback {
        private WeakReference<PictureHelper> mPictureHelper;

        public void onGifLoaded(GifDrawable gifDrawable) {
        }

        public void onPrepareLoad(Drawable drawable) {
        }

        public HoverCallback(PictureHelper pictureHelper) {
            this.mPictureHelper = new WeakReference<>(pictureHelper);
        }

        public void onBitmapLoaded(Bitmap bitmap) {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                pictureHelper.mHoverBitmap = bitmap;
            }
        }

        public void onBitmapFailed(Drawable drawable) {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                pictureHelper.mHoverBitmap = null;
            }
        }
    }

    static class SVGCallback implements ImageCallback {
        private WeakReference<PictureHelper> mPictureHelper;

        public void onGifLoaded(GifDrawable gifDrawable) {
        }

        public void onPrepareLoad(Drawable drawable) {
        }

        public SVGCallback(PictureHelper pictureHelper) {
            this.mPictureHelper = new WeakReference<>(pictureHelper);
        }

        public void onBitmapLoaded(Bitmap bitmap) {
            loadFinish(bitmap);
        }

        public void onBitmapFailed(Drawable drawable) {
            loadFinish(null);
        }

        private void loadFinish(Bitmap bitmap) {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                View access$100 = pictureHelper.mView;
                PictureParams pictureParams = pictureHelper.mSVGParams;
                if (bitmap == null || (TextUtils.isEmpty(pictureParams.url) && TextUtils.isEmpty(pictureParams.SVGData))) {
                    access$100.setBackground(null);
                } else {
                    access$100.setBackground(new BitmapDrawable(access$100.getResources(), bitmap));
                }
            }
        }
    }

    static class SrcCallback implements ImageCallback {
        private WeakReference<PictureHelper> mPictureHelper;

        public void onPrepareLoad(Drawable drawable) {
        }

        public SrcCallback(PictureHelper pictureHelper) {
            this.mPictureHelper = new WeakReference<>(pictureHelper);
        }

        public void onGifLoaded(GifDrawable gifDrawable) {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                pictureHelper.mIsLoading = false;
                pictureHelper.mBitmap = null;
                pictureHelper.mCurrentUrl = pictureHelper.mLoadUrl;
                pictureHelper.mLoadUrl = null;
                pictureHelper.updatePicture(gifDrawable);
                loadFinish();
            }
        }

        public void onBitmapLoaded(Bitmap bitmap) {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                pictureHelper.mIsLoading = false;
                pictureHelper.mBitmap = bitmap;
                pictureHelper.mCurrentUrl = pictureHelper.mLoadUrl;
                pictureHelper.mLoadUrl = null;
                pictureHelper.updatePicture(PictureFactory.edit(pictureHelper.mView.getContext(), bitmap, pictureHelper.mSrcParams));
                loadFinish();
            }
        }

        public void onBitmapFailed(Drawable drawable) {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null) {
                pictureHelper.mIsLoading = false;
                pictureHelper.mBitmap = null;
                pictureHelper.mLoadUrl = null;
                pictureHelper.mHasImageLoadError = true;
                if (pictureHelper.mNeedErrorEventCallback) {
                    pictureHelper.mHasImageLoadError = false;
                    pictureHelper.mAttribute.mAjxContext.invokeJsEvent(new Builder().setEventName("error").setNodeId(pictureHelper.getNodeId()).build());
                }
                loadFinish();
            }
        }

        private void loadFinish() {
            PictureHelper pictureHelper = (PictureHelper) this.mPictureHelper.get();
            if (pictureHelper != null && pictureHelper.mNeedReload) {
                pictureHelper.update();
            }
        }
    }

    public PictureHelper(BaseProperty baseProperty) {
        this.mView = baseProperty.mView;
        this.mShadowHelper = baseProperty.mShadowHelper;
        this.mAttribute = baseProperty;
        this.mBgParams = new PictureParams();
        this.mSrcParams = new PictureParams();
        this.mBgCallback = new BgCallback(this);
        this.mHoverCallback = new HoverCallback(this);
        this.mSrcCallback = new SrcCallback(this);
        this.mBorderHelper = new BorderHelper(baseProperty, this.mBgParams);
        this.isSVG = baseProperty instanceof SVGProperty;
        if (this.isSVG) {
            this.mSVGParams = PictureParams.make(baseProperty.mAjxContext, null, this.mSyncLoadSVG);
            this.mSVGLoader = Ajx.getInstance().lookupLoader("svg");
            this.mSVGCallback = new SVGCallback(this);
        }
    }

    public void syncLoadSVGFlag(boolean z) {
        this.mSyncLoadSVG = z;
        if (this.mSVGParams != null) {
            this.mSVGParams.isSyncLoadImg = z;
        }
    }

    public void syncLoadFlag(boolean z) {
        this.mBgParams.isSyncLoadImg = z;
        this.mSrcParams.isSyncLoadImg = z;
    }

    public void volatileFlag(boolean z) {
        this.mBgParams.isVolatile = z;
        this.mSrcParams.isVolatile = z;
    }

    public void setClipChildren(boolean z) {
        this.mBgParams.clipChildren = z;
    }

    public boolean getClipChildren() {
        return this.mBgParams.clipChildren;
    }

    public boolean canSupportBorder() {
        return PictureFactory.hasBorder(this.mBgParams.borderWidth) || PictureFactory.hasCornerRadius(this.mBgParams.cornerRadius);
    }

    public void update() {
        if (this.isSVG) {
            updateSVGSize();
            if (this.mSVGIsChanged) {
                this.mSVGIsChanged = false;
                this.mView.setBackground(null);
                if (this.mSVGParams.width > 0 && this.mSVGParams.height > 0) {
                    boolean z = !TextUtils.isEmpty(this.mSVGParams.SVGData);
                    boolean isEmpty = true ^ TextUtils.isEmpty(this.mSVGParams.url);
                    this.mSVGParams.patchIndex = 0;
                    if (isEmpty) {
                        this.mSVGParams.match(this.mAttribute.mAjxContext, this.mSVGParams.url);
                    }
                    if (z || isEmpty) {
                        this.mSVGLoader.loadImage(this.mView, this.mAttribute.mAjxContext, this.mSVGParams, this.mSVGCallback);
                    }
                }
                return;
            }
            return;
        }
        if (this.mBackgroundIsChanged) {
            if (!TextUtils.isEmpty(this.mBgParams.background)) {
                updatePicture(PictureFactory.edit(this.mView.getContext(), null, this.mBgParams));
                return;
            }
            updatePicture(null);
        }
        if (this.mHoverBgIsChanged && !TextUtils.isEmpty(this.mHoverBgUrl)) {
            this.mHoverBgIsChanged = false;
            IAjxContext iAjxContext = this.mAttribute.mAjxContext;
            PictureParams copy = this.mBgParams.copy();
            copy.url = this.mHoverBgUrl;
            copy.width = DimensionUtils.standardUnitToPixel(this.mAttribute.mCurrentWidth);
            copy.height = DimensionUtils.standardUnitToPixel(this.mAttribute.mCurrentHeight);
            Ajx.getInstance().lookupLoader(this.mHoverBgUrl).loadImage(this.mView, iAjxContext, copy.match(iAjxContext, this.mHoverBgUrl), this.mHoverCallback);
        }
        if (this.mIsLoading) {
            this.mNeedReload = true;
            return;
        }
        this.mNeedReload = false;
        if (TextUtils.isEmpty(this.mSrcParams.url)) {
            if (this.mBgIsChanged || this.mSrcIsChanged) {
                updateBg();
            }
        } else if (TextUtils.equals(this.mCurrentUrl, this.mSrcParams.url)) {
            if (this.mSrcIsChanged) {
                updateSrc();
            }
        } else {
            updateBg();
        }
    }

    private void updateBg() {
        this.mSrcIsChanged = false;
        this.mBgIsChanged = false;
        this.mBgParams.canSupportBorderClip = this.mAttribute.canSupportBorderClip();
        if (TextUtils.isEmpty(this.mBgParams.url)) {
            this.mCurrentUrl = null;
            ViewUtils.setBackground(this.mView, PictureFactory.edit(this.mView.getContext(), null, this.mBgParams));
            if (!TextUtils.isEmpty(this.mSrcParams.url)) {
                updateSrc();
            }
        } else {
            this.mIsLoading = true;
            this.mLoadUrl = this.mBgParams.url;
            this.mBgParams.width = DimensionUtils.standardUnitToPixel(this.mAttribute.mCurrentWidth);
            this.mBgParams.height = DimensionUtils.standardUnitToPixel(this.mAttribute.mCurrentHeight);
            IAjxContext iAjxContext = this.mAttribute.mAjxContext;
            Ajx.getInstance().lookupLoader(this.mBgParams.url).loadImage(this.mView, iAjxContext, this.mBgParams.match(iAjxContext, this.mBgParams.url), this.mBgCallback);
        }
    }

    /* access modifiers changed from: private */
    public void updateSrc() {
        this.mSrcIsChanged = false;
        IAjxImageLoader lookupLoader = Ajx.getInstance().lookupLoader(this.mSrcParams.url);
        if (lookupLoader != null && this.mAttribute.mAjxContext.getNativeContext() != null) {
            this.mIsLoading = true;
            this.mLoadUrl = this.mSrcParams.url;
            this.mSrcParams.width = DimensionUtils.standardUnitToPixel(this.mAttribute.mCurrentWidth);
            this.mSrcParams.height = DimensionUtils.standardUnitToPixel(this.mAttribute.mCurrentHeight);
            lookupLoader.loadImage(this.mView, this.mAttribute.mAjxContext, this.mSrcParams.match(this.mAttribute.mAjxContext, this.mSrcParams.url), this.mSrcCallback);
        }
    }

    /* access modifiers changed from: private */
    public long getNodeId() {
        return this.mAttribute.mAjxContext.getDomTree().getNodeId(this.mView);
    }

    /* access modifiers changed from: protected */
    public void updatePicture(Drawable drawable) {
        ViewUtils.setBackground(this.mView, drawable);
        this.mHasImageLoaded = true;
        if (this.mNeedLoadEventCallback) {
            this.mHasImageLoaded = false;
            this.mAttribute.mAjxContext.invokeJsEvent(new Builder().setEventName(UCCore.OPTION_LOAD_KERNEL_TYPE).setNodeId(getNodeId()).build());
        }
    }

    public void addLoadListener(Object obj) {
        this.mNeedLoadEventCallback = obj != null;
        if (this.mNeedLoadEventCallback && this.mHasImageLoaded) {
            this.mHasImageLoaded = false;
            this.mAttribute.mAjxContext.invokeJsEvent(new Builder().setEventName(UCCore.OPTION_LOAD_KERNEL_TYPE).setNodeId(getNodeId()).build());
        }
    }

    public void addErrorListener(Object obj) {
        this.mNeedErrorEventCallback = obj != null;
        if (this.mNeedErrorEventCallback && this.mHasImageLoadError) {
            this.mHasImageLoadError = false;
            this.mAttribute.mAjxContext.invokeJsEvent(new Builder().setEventName("error").setNodeId(getNodeId()).build());
        }
    }

    public void updateHoverBackgroundImage(Object obj) {
        String str = obj instanceof String ? (String) obj : null;
        if (!TextUtils.equals(this.mHoverBgUrl, str)) {
            this.mHoverBitmap = null;
            this.mHoverBgIsChanged = true;
            this.mHoverBgUrl = str;
        }
    }

    public void updateBackgroundImage(Object obj) {
        String str;
        this.mHasImageLoaded = false;
        this.mHasImageLoadError = false;
        if (obj instanceof String) {
            str = (String) obj;
            String trim = str.trim();
            if (TextUtils.isEmpty(trim) || TextUtils.equals(trim, Constants.ANIMATOR_NONE)) {
                str = "";
            }
        } else {
            str = null;
        }
        if (!TextUtils.equals(this.mBgParams.url, str)) {
            this.mBgIsChanged = true;
            this.mBgParams.url = str;
        }
    }

    public void updateBackgroundSize(Object obj) {
        if (obj instanceof Integer) {
            int intValue = ((Integer) obj).intValue();
            int i = this.mBgParams.scaleMode;
            if (1056964746 == intValue) {
                this.mBgParams.scaleMode = 0;
            } else if (1056964744 == intValue) {
                this.mBgParams.scaleMode = 2;
            } else if (1056964745 == intValue) {
                this.mBgParams.scaleMode = 1;
            } else if (1056964747 == intValue) {
                this.mBgParams.scaleMode = 3;
            }
            if (i != this.mBgParams.scaleMode) {
                this.mBgIsChanged = true;
            }
        }
    }

    public void updateBackgroundStretch(Object obj) {
        if (obj == null || (obj instanceof float[])) {
            this.mBgIsChanged = true;
            this.mBgParams.stretch = ArrayUtils.floatArray2IntArray((float[]) obj);
        }
    }

    public void updateRadius(Object obj) {
        if (obj == null || (obj instanceof float[])) {
            this.mBgIsChanged = true;
            this.mSrcIsChanged = true;
            float[] fArr = (float[]) obj;
            int[] iArr = null;
            if (fArr != null && fArr.length == 4) {
                iArr = new int[]{DimensionUtils.standardUnitToPixel(fArr[0]), DimensionUtils.standardUnitToPixel(fArr[1]), DimensionUtils.standardUnitToPixel(fArr[2]), DimensionUtils.standardUnitToPixel(fArr[3])};
            }
            this.mBgParams.cornerRadius = iArr;
            this.mSrcParams.cornerRadius = this.mBgParams.cornerRadius;
            this.mBorderHelper.notifyRadiusInvalid();
        }
    }

    public void updateBorderWidth(Object obj) {
        if (obj == null || (obj instanceof float[])) {
            this.mBgIsChanged = true;
            this.mSrcIsChanged = true;
            float[] fArr = (float[]) obj;
            int[] iArr = null;
            if (fArr != null && fArr.length == 4) {
                iArr = new int[]{DimensionUtils.standardUnitToPixel(fArr[0]), DimensionUtils.standardUnitToPixel(fArr[1]), DimensionUtils.standardUnitToPixel(fArr[2]), DimensionUtils.standardUnitToPixel(fArr[3])};
            }
            this.mBgParams.borderWidth = iArr;
            this.mSrcParams.borderWidth = this.mBgParams.borderWidth;
            this.mBorderHelper.notifyBorderWidthInvalid();
        }
    }

    public void updateBorderColor(Object obj) {
        if (obj instanceof Float) {
            obj = Integer.valueOf((int) ((Float) obj).floatValue());
        }
        this.mBgIsChanged = true;
        this.mSrcIsChanged = true;
        this.mBgParams.borderColor = obj == null ? -16777216 : ((Integer) obj).intValue();
        this.mSrcParams.borderColor = this.mBgParams.borderColor;
        this.mBorderHelper.notifyBorderColorInvalid();
    }

    public void updateBgColor(Object obj) {
        if (obj instanceof Float) {
            obj = Integer.valueOf((int) ((Float) obj).floatValue());
        }
        this.mBgIsChanged = true;
        this.mSrcIsChanged = true;
        this.mBgParams.bgColor = obj == null ? 0 : ((Integer) obj).intValue();
        this.mSrcParams.bgColor = this.mBgParams.bgColor;
    }

    public void updateImageStretch(Object obj) {
        if (obj == null || (obj instanceof float[])) {
            this.mSrcIsChanged = true;
            this.mSrcParams.stretch = ArrayUtils.floatArray2IntArray((float[]) obj);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
        if (android.text.TextUtils.equals(r0, com.autonavi.minimap.ajx3.util.Constants.ANIMATOR_NONE) == false) goto L_0x001f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateSrc(java.lang.Object r3) {
        /*
            r2 = this;
            r0 = 0
            r2.mHasImageLoaded = r0
            r2.mHasImageLoadError = r0
            boolean r0 = r3 instanceof java.lang.String
            if (r0 == 0) goto L_0x001e
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r0 = r3.trim()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x001e
            java.lang.String r1 = "none"
            boolean r0 = android.text.TextUtils.equals(r0, r1)
            if (r0 != 0) goto L_0x001e
            goto L_0x001f
        L_0x001e:
            r3 = 0
        L_0x001f:
            com.autonavi.minimap.ajx3.image.PictureParams r0 = r2.mSrcParams
            java.lang.String r0 = r0.url
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 != 0) goto L_0x0030
            r0 = 1
            r2.mSrcIsChanged = r0
            com.autonavi.minimap.ajx3.image.PictureParams r0 = r2.mSrcParams
            r0.url = r3
        L_0x0030:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.PictureHelper.updateSrc(java.lang.Object):void");
    }

    public void updateFileMode(Object obj) {
        if (obj instanceof Integer) {
            int intValue = ((Integer) obj).intValue();
            int i = this.mBgParams.scaleMode;
            boolean z = true;
            if (1056964746 == intValue) {
                this.mSrcParams.scaleMode = 0;
            } else if (1056964744 == intValue) {
                this.mSrcParams.scaleMode = 2;
            } else if (1056964745 == intValue) {
                this.mSrcParams.scaleMode = 1;
            } else if (1056964747 == intValue) {
                this.mSrcParams.scaleMode = 3;
            }
            if (i == this.mSrcParams.scaleMode) {
                z = false;
            }
            this.mSrcIsChanged = z;
        }
    }

    public void updateFilter(Object obj) {
        if (obj instanceof FilterValue) {
            FilterValue filterValue = (FilterValue) obj;
            updateFilterBlur(Integer.valueOf(filterValue.blur));
            updateBrightness(Float.valueOf(filterValue.brightness));
            updateSaturate(Float.valueOf(filterValue.saturate));
            updateDropShadow(filterValue.shadowX, filterValue.shadowY, filterValue.shadowBlur, filterValue.shadowColor);
            return;
        }
        if (obj == null) {
            updateFilterBlur(null);
            updateBrightness(null);
            updateSaturate(null);
            updateDropShadow(0, 0, 0, 0);
        }
    }

    public void updateFilterBlur(Object obj) {
        float f = obj instanceof String ? StringUtils.parseFloat((String) obj) : obj instanceof Float ? ((Float) obj).floatValue() : obj instanceof Integer ? (float) ((Integer) obj).intValue() : 0.0f;
        if (f != this.mBgParams.blur) {
            this.mBgParams.blur = f;
            this.mSrcParams.blur = this.mBgParams.blur;
            this.mBgIsChanged = true;
            this.mSrcIsChanged = true;
        }
    }

    public void updateBrightness(Object obj) {
        this.mBgParams.brightness = 1.0f;
        if (obj instanceof String) {
            this.mBgParams.brightness = StringUtils.parseFloat((String) obj);
        } else if (obj instanceof Float) {
            this.mBgParams.brightness = ((Float) obj).floatValue();
        }
        if (this.mBgParams.brightness > 2.0f) {
            this.mBgParams.brightness = 2.0f;
        } else if (this.mBgParams.brightness < 0.0f) {
            this.mBgParams.brightness = 0.0f;
        }
        this.mView.invalidate();
    }

    public void updateSaturate(Object obj) {
        this.mBgParams.saturate = 1.0f;
        if (obj instanceof String) {
            this.mBgParams.saturate = StringUtils.parseFloat((String) obj);
        } else if (obj instanceof Float) {
            this.mBgParams.saturate = ((Float) obj).floatValue();
        }
        if (this.mBgParams.saturate > 2.0f) {
            this.mBgParams.saturate = 2.0f;
        } else if (this.mBgParams.saturate < 0.0f) {
            this.mBgParams.saturate = 0.0f;
        }
        this.mView.invalidate();
    }

    public void updateDropShadow(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = 0;
        if (i != 0) {
            i5 = DimensionUtils.standardUnitToPixel((float) Math.abs(i)) * (i / Math.abs(i));
        } else {
            i5 = 0;
        }
        if (i2 != 0) {
            i6 = DimensionUtils.standardUnitToPixel((float) Math.abs(i2)) * (i2 / Math.abs(i2));
        } else {
            i6 = 0;
        }
        if (i3 > 0) {
            i7 = DimensionUtils.standardUnitToPixel((float) i3);
        }
        if (this.mBgParams.shadowX != i5 || this.mBgParams.shadowY != i6 || this.mBgParams.shadowBlur != i7 || this.mBgParams.shadowColor != i4) {
            this.mBgParams.shadowX = i5;
            this.mBgParams.shadowY = i6;
            this.mBgParams.shadowBlur = i7;
            this.mBgParams.shadowColor = i4;
            this.mShadowHelper.updateDropShadow(this.mBgParams);
        }
    }

    public void beforeDraw(Canvas canvas, boolean z) {
        this.mBorderHelper.beforeDraw(canvas, z);
    }

    public void afterDraw(Canvas canvas, boolean z) {
        this.mBorderHelper.afterDraw(canvas, z);
    }

    public void onDraw(Canvas canvas) {
        boolean z = true;
        boolean z2 = this.mBgParams.saturate != 1.0f;
        if (this.mBgParams.brightness == 1.0f) {
            z = false;
        }
        if (z2 || z) {
            int width = this.mView.getWidth();
            int height = this.mView.getHeight();
            Drawable background = this.mView.getBackground();
            if (width > 0 && height > 0 && background != null) {
                Bitmap createBitmap = Bitmap.createBitmap(width, height, background.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
                background.setBounds(0, 0, width, height);
                background.draw(new Canvas(createBitmap));
                if (this.mPaint == null) {
                    this.mPaint = new Paint();
                }
                this.mPaint.reset();
                if (z2 && z) {
                    if (this.mMatrix == null) {
                        this.mMatrix = new ColorMatrix();
                    }
                    if (this.mSaturateMatrix == null) {
                        this.mSaturateMatrix = new ColorMatrix();
                    }
                    if (this.mBrightnessMatrix == null) {
                        this.mBrightnessMatrix = new ColorMatrix();
                    }
                    canvas.drawBitmap(createBitmap, 0.0f, 0.0f, this.mPaint);
                    float f = this.mBgParams.brightness;
                    this.mBrightnessMatrix.setScale(f, f, f, 1.0f);
                    this.mSaturateMatrix.setSaturation(this.mBgParams.saturate);
                    this.mMatrix.reset();
                    this.mMatrix.postConcat(this.mSaturateMatrix);
                    this.mMatrix.postConcat(this.mBrightnessMatrix);
                    this.mPaint.setColorFilter(new ColorMatrixColorFilter(this.mMatrix));
                    canvas.drawBitmap(createBitmap, 0.0f, 0.0f, this.mPaint);
                } else if (z2) {
                    if (this.mSaturateMatrix == null) {
                        this.mSaturateMatrix = new ColorMatrix();
                    }
                    canvas.drawBitmap(createBitmap, 0.0f, 0.0f, this.mPaint);
                    this.mSaturateMatrix.setSaturation(this.mBgParams.saturate);
                    this.mPaint.setColorFilter(new ColorMatrixColorFilter(this.mSaturateMatrix));
                    canvas.drawBitmap(createBitmap, 0.0f, 0.0f, this.mPaint);
                } else {
                    if (z) {
                        if (this.mBrightnessMatrix == null) {
                            this.mBrightnessMatrix = new ColorMatrix();
                        }
                        canvas.drawBitmap(createBitmap, 0.0f, 0.0f, this.mPaint);
                        float f2 = this.mBgParams.brightness;
                        this.mBrightnessMatrix.setScale(f2, f2, f2, 1.0f);
                        this.mPaint.setColorFilter(new ColorMatrixColorFilter(this.mBrightnessMatrix));
                        canvas.drawBitmap(createBitmap, 0.0f, 0.0f, this.mPaint);
                    }
                }
            }
        }
    }

    public void setBackground(Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (!TextUtils.equals(this.mBgParams.background, str)) {
                this.mBgParams.background = str;
                this.mBackgroundIsChanged = true;
            }
        } else if (this.mBgParams.background != null) {
            this.mBgParams.background = null;
            this.mBackgroundIsChanged = true;
        }
    }

    public static Shader getShader(String str, float f, float f2) {
        String[] parseGradientValues = parseGradientValues(str);
        if (parseGradientValues != null && parseGradientValues.length >= 2) {
            int[] parseGradientColor = parseGradientColor(parseGradientValues);
            if (parseGradientColor != null && parseGradientColor.length > 1) {
                float[] scalePoints = scalePoints(parseGradientDirection(parseGradientValues[0], f, f2));
                LinearGradient linearGradient = new LinearGradient(scalePoints[0], scalePoints[1], scalePoints[2], scalePoints[3], parseGradientColor, null, TileMode.CLAMP);
                if (!(scalePoints[4] == 1.0f && scalePoints[5] == 1.0f)) {
                    Matrix matrix = new Matrix();
                    matrix.setScale(scalePoints[4], scalePoints[5]);
                    linearGradient.setLocalMatrix(matrix);
                }
                return linearGradient;
            }
        }
        return null;
    }

    private static float[] scalePoints(float[] fArr) {
        float f;
        float f2 = fArr[0];
        float f3 = fArr[1];
        float f4 = fArr[2];
        float f5 = fArr[3];
        int i = (f2 > f3 ? 1 : (f2 == f3 ? 0 : -1));
        float f6 = 1.0f;
        if (i == 0 || f2 == 0.0f || f3 == 0.0f || f4 != 0.0f || f5 != 0.0f) {
            int i2 = (f2 > f5 ? 1 : (f2 == f5 ? 0 : -1));
            if (i2 == 0 || f2 == 0.0f || f3 != 0.0f || f4 != 0.0f || f5 == 0.0f) {
                int i3 = (f4 > f3 ? 1 : (f4 == f3 ? 0 : -1));
                if (i3 == 0 || f2 != 0.0f || f3 == 0.0f || f4 == 0.0f || f5 != 0.0f) {
                    int i4 = (f4 > f5 ? 1 : (f4 == f5 ? 0 : -1));
                    if (!(i4 == 0 || f2 != 0.0f || f3 != 0.0f || f4 == 0.0f || f5 == 0.0f)) {
                        if (i4 > 0) {
                            f6 = f4 / f5;
                            f = 1.0f;
                            f4 = f5;
                        } else {
                            f = f5 / f4;
                            f5 = f4;
                        }
                        return new float[]{f2, f3, f4, f5, f6, f};
                    }
                } else {
                    if (i3 > 0) {
                        f6 = f4 / f3;
                        f = 1.0f;
                        f4 = f3;
                    } else {
                        f = f3 / f4;
                        f3 = f4;
                    }
                    return new float[]{f2, f3, f4, f5, f6, f};
                }
            } else {
                if (i2 > 0) {
                    f6 = f2 / f5;
                    f = 1.0f;
                    f2 = f5;
                } else {
                    f = f5 / f2;
                    f5 = f2;
                }
                return new float[]{f2, f3, f4, f5, f6, f};
            }
        } else if (i > 0) {
            f6 = f2 / f3;
            f2 = f3;
        } else {
            f = f3 / f2;
            f3 = f2;
            return new float[]{f2, f3, f4, f5, f6, f};
        }
        f = 1.0f;
        return new float[]{f2, f3, f4, f5, f6, f};
    }

    private static String[] parseGradientValues(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String trim = str.trim();
        if (trim.startsWith("linear-gradient")) {
            int indexOf = trim.indexOf("(");
            int lastIndexOf = trim.lastIndexOf(")");
            if (trim.length() > indexOf && trim.length() >= lastIndexOf) {
                return trim.substring(indexOf + 1, lastIndexOf).split(",");
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ac, code lost:
        if (r8.equals("torightbottom") != false) goto L_0x012f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static float[] parseGradientDirection(java.lang.String r8, float r9, float r10) {
        /*
            r0 = 4
            float[] r1 = new float[r0]
            r1 = {0, 0, 0, 0} // fill-array
            java.lang.String r2 = "\\s*"
            java.lang.String r3 = ""
            java.lang.String r8 = r8.replaceAll(r2, r3)
            java.lang.String r8 = r8.toLowerCase()
            java.lang.String r2 = "deg"
            int r2 = r8.indexOf(r2)
            r3 = 0
            if (r2 < 0) goto L_0x0098
            r4 = 180(0xb4, float:2.52E-43)
            java.lang.String r8 = r8.substring(r3, r2)     // Catch:{ Throwable -> 0x002e }
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ Throwable -> 0x002e }
            int r2 = r8 % 360
            int r2 = r2 + 360
            int r2 = r2 % 360
            goto L_0x0030
        L_0x002c:
            r2 = r8
            goto L_0x0030
        L_0x002e:
            r2 = 180(0xb4, float:2.52E-43)
        L_0x0030:
            double r4 = (double) r2
            r6 = 4627026404658118656(0x4036800000000000, double:22.5)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 < 0) goto L_0x0095
            r6 = 4634450307168862208(0x4050e00000000000, double:67.5)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x0047
            java.lang.String r8 = "totopright"
            goto L_0x0098
        L_0x0047:
            r6 = 4637616900656857088(0x405c200000000000, double:112.5)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x0054
            java.lang.String r8 = "toright"
            goto L_0x0098
        L_0x0054:
            r6 = 4639745555168231424(0x4063b00000000000, double:157.5)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x0061
            java.lang.String r8 = "torightbottom"
            goto L_0x0098
        L_0x0061:
            r6 = 4641328851912228864(0x4069500000000000, double:202.5)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x006e
            java.lang.String r8 = "tobottom"
            goto L_0x0098
        L_0x006e:
            r6 = 4642912148656226304(0x406ef00000000000, double:247.5)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x007b
            java.lang.String r8 = "toleftbottom"
            goto L_0x0098
        L_0x007b:
            r6 = 4643853330609602560(0x4072480000000000, double:292.5)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x0088
            java.lang.String r8 = "toleft"
            goto L_0x0098
        L_0x0088:
            r6 = 4644644978981601280(0x4075180000000000, double:337.5)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x0095
            java.lang.String r8 = "tolefttop"
            goto L_0x0098
        L_0x0095:
            java.lang.String r8 = "totop"
        L_0x0098:
            r2 = -1
            int r4 = r8.hashCode()
            r5 = 3
            r6 = 1
            r7 = 2
            switch(r4) {
                case -1556330067: goto L_0x0122;
                case -1520771788: goto L_0x0116;
                case -1486250643: goto L_0x010b;
                case -1352032154: goto L_0x00ff;
                case -1137407871: goto L_0x00f4;
                case -1033506462: goto L_0x00e8;
                case -868157182: goto L_0x00dd;
                case -172068863: goto L_0x00d2;
                case 110550266: goto L_0x00c7;
                case 1022562579: goto L_0x00bc;
                case 1176531318: goto L_0x00b0;
                case 1782578668: goto L_0x00a5;
                default: goto L_0x00a3;
            }
        L_0x00a3:
            goto L_0x012e
        L_0x00a5:
            java.lang.String r4 = "torightbottom"
            boolean r8 = r8.equals(r4)
            if (r8 == 0) goto L_0x012e
            goto L_0x012f
        L_0x00b0:
            java.lang.String r0 = "tobottomright"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 3
            goto L_0x012f
        L_0x00bc:
            java.lang.String r0 = "tolefttop"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 6
            goto L_0x012f
        L_0x00c7:
            java.lang.String r0 = "totop"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 2
            goto L_0x012f
        L_0x00d2:
            java.lang.String r0 = "totopleft"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 5
            goto L_0x012f
        L_0x00dd:
            java.lang.String r0 = "toleft"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 1
            goto L_0x012f
        L_0x00e8:
            java.lang.String r0 = "totopright"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 9
            goto L_0x012f
        L_0x00f4:
            java.lang.String r0 = "toright"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 0
            goto L_0x012f
        L_0x00ff:
            java.lang.String r0 = "tobottom"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 11
            goto L_0x012f
        L_0x010b:
            java.lang.String r0 = "tobottomleft"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 7
            goto L_0x012f
        L_0x0116:
            java.lang.String r0 = "torighttop"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 10
            goto L_0x012f
        L_0x0122:
            java.lang.String r0 = "toleftbottom"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x012e
            r0 = 8
            goto L_0x012f
        L_0x012e:
            r0 = -1
        L_0x012f:
            switch(r0) {
                case 0: goto L_0x0149;
                case 1: goto L_0x0146;
                case 2: goto L_0x0143;
                case 3: goto L_0x0140;
                case 4: goto L_0x0140;
                case 5: goto L_0x013b;
                case 6: goto L_0x013b;
                case 7: goto L_0x0138;
                case 8: goto L_0x0138;
                case 9: goto L_0x0133;
                case 10: goto L_0x0133;
                default: goto L_0x0132;
            }
        L_0x0132:
            goto L_0x014c
        L_0x0133:
            r1[r6] = r10
            r1[r7] = r9
            goto L_0x014e
        L_0x0138:
            r1[r3] = r9
            goto L_0x014c
        L_0x013b:
            r1[r3] = r9
            r1[r6] = r10
            goto L_0x014e
        L_0x0140:
            r1[r7] = r9
            goto L_0x014c
        L_0x0143:
            r1[r6] = r10
            goto L_0x014e
        L_0x0146:
            r1[r3] = r9
            goto L_0x014e
        L_0x0149:
            r1[r7] = r9
            goto L_0x014e
        L_0x014c:
            r1[r5] = r10
        L_0x014e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.PictureHelper.parseGradientDirection(java.lang.String, float, float):float[]");
    }

    private static int[] parseGradientColor(String[] strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String trim : strArr) {
            int parseColorByAnimator = StringUtils.parseColorByAnimator(trim.trim());
            if (parseColorByAnimator != -2) {
                arrayList.add(Integer.valueOf(parseColorByAnimator));
            }
        }
        int size = arrayList.size();
        if (size <= 0) {
            return null;
        }
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        return iArr;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public Bitmap getHoverBitmap() {
        return this.mHoverBitmap;
    }

    public String getCurrentUrl() {
        return this.mCurrentUrl;
    }

    public String getHoverBgUrl() {
        return this.mHoverBgUrl;
    }

    public void updateSVGColor(String str) {
        int parseColor = StringUtils.parseColor(str, 0);
        if (parseColor != this.mSVGParams.SVGColor) {
            this.mSVGIsChanged = true;
            this.mSVGParams.SVGColor = parseColor;
        }
    }

    public void updateSVGSrc(String str) {
        if (!TextUtils.equals(this.mSVGParams.url, str)) {
            this.mSVGIsChanged = true;
            this.mSVGParams.url = str;
            this.mSVGParams.realUrl = PathUtils.processPath(this.mAttribute.mAjxContext, str);
        }
    }

    public void updateSVGData(String str) {
        if (!TextUtils.equals(this.mSVGParams.SVGData, str)) {
            this.mSVGIsChanged = true;
            this.mSVGParams.SVGData = str;
        }
    }

    public void updateSVGSize() {
        int standardUnitToPixel = DimensionUtils.standardUnitToPixel(this.mAttribute.getSize("width"));
        int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel(this.mAttribute.getSize("height"));
        if (this.mSVGParams.width != standardUnitToPixel || this.mSVGParams.height != standardUnitToPixel2) {
            this.mSVGIsChanged = true;
            this.mSVGParams.width = standardUnitToPixel;
            this.mSVGParams.height = standardUnitToPixel2;
        }
    }
}
