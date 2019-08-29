package com.alipay.android.phone.mobilecommon.multimedia.graphics.load;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APConstants;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APDisplayer;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.BaseOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.BaseReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public final class DisplayImageOptions extends BaseReq {
    /* access modifiers changed from: private */
    public final boolean cacheInMem;
    /* access modifiers changed from: private */
    public final String caller;
    /* access modifiers changed from: private */
    public final boolean considerExifParams;
    /* access modifiers changed from: private */
    public final int delayBeforeLoading;
    /* access modifiers changed from: private */
    public final APDisplayer displayer;
    /* access modifiers changed from: private */
    public final DrawableDecoder drawableDecoder;
    private final WeakReference<Drawable> imageOnLoading;
    /* access modifiers changed from: private */
    public final boolean isSyncLoading;
    /* access modifiers changed from: private */
    public final ExecutorService netloadExecutorService;
    /* access modifiers changed from: private */
    public final boolean returnStatusWhileInMem;
    /* access modifiers changed from: private */
    public final boolean setNullDefaultDrawable;
    /* access modifiers changed from: private */
    public final Float viewW2HRatio;
    /* access modifiers changed from: private */
    public final boolean withImageDataInCallback;

    public static class Builder {
        /* access modifiers changed from: private */
        public String aliasPath;
        /* access modifiers changed from: private */
        public boolean bHttps = false;
        /* access modifiers changed from: private */
        public boolean bProgressive = false;
        /* access modifiers changed from: private */
        public Base64Optimization base64Optimization = Base64Optimization.createDefault();
        public BaseOptions baseOptions;
        /* access modifiers changed from: private */
        public String bizType;
        public Bundle bundle;
        /* access modifiers changed from: private */
        public String businessId;
        /* access modifiers changed from: private */
        public boolean cacheInMem = true;
        /* access modifiers changed from: private */
        public String caller;
        /* access modifiers changed from: private */
        public boolean considerExifParams = false;
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public CutScaleType cutScaleType;
        /* access modifiers changed from: private */
        public int delayBeforeLoading = 0;
        /* access modifiers changed from: private */
        public boolean detectedGif = false;
        /* access modifiers changed from: private */
        public APDisplayer displayer = null;
        DrawableDecoder drawableDecoder;
        /* access modifiers changed from: private */
        public boolean enableSaliency = false;
        /* access modifiers changed from: private */
        public long expiredTime = Long.MAX_VALUE;
        /* access modifiers changed from: private */
        public Map<String, String> extInfo;
        /* access modifiers changed from: private */
        public Integer height = null;
        /* access modifiers changed from: private */
        public APImageMarkRequest imageMarkRequest;
        /* access modifiers changed from: private */
        public Drawable imageOnLoading = null;
        /* access modifiers changed from: private */
        public boolean isSyncLoading = false;
        /* access modifiers changed from: private */
        public String md5 = null;
        /* access modifiers changed from: private */
        public ExecutorService netloadExecutorService;
        /* access modifiers changed from: private */
        public Size originalSize;
        /* access modifiers changed from: private */
        public int priority = 5;
        /* access modifiers changed from: private */
        public ImageWorkerPlugin processor = null;
        /* access modifiers changed from: private */
        public int quality = -1;
        /* access modifiers changed from: private */
        public boolean returnStatusWhileInMem = false;
        /* access modifiers changed from: private */
        public Float scale = Float.valueOf(0.5f);
        /* access modifiers changed from: private */
        public CutScaleType secondaryCutScaleType;
        /* access modifiers changed from: private */
        public boolean setNullDefaultDrawable = true;
        /* access modifiers changed from: private */
        public boolean shareGifMemCache = true;
        /* access modifiers changed from: private */
        public int timeout = -1;
        /* access modifiers changed from: private */
        public boolean usingSourceType = false;
        /* access modifiers changed from: private */
        public Float viewW2HRatio;
        /* access modifiers changed from: private */
        public Integer width = null;
        /* access modifiers changed from: private */
        public boolean withImageDataInCallback = false;

        public Builder setBundle(Bundle bundle2) {
            this.bundle = bundle2;
            return this;
        }

        public Builder showImageOnLoading(Drawable drawable) {
            this.imageOnLoading = drawable;
            return this;
        }

        public Builder imageScaleType(CutScaleType cutScaleType2) {
            this.cutScaleType = cutScaleType2;
            return this;
        }

        public Builder setProcessor(ImageWorkerPlugin processor2) {
            this.processor = processor2;
            return this;
        }

        public Builder displayer(APDisplayer displayer2) {
            this.displayer = displayer2;
            return this;
        }

        public Builder syncLoading(boolean isSyncLoading2) {
            this.isSyncLoading = isSyncLoading2;
            return this;
        }

        public Builder originalSize(Size originalSize2) {
            this.originalSize = originalSize2;
            return this;
        }

        public Builder width(Integer width2) {
            if (BaseReq.isSuperSize(width2)) {
                this.width = Integer.valueOf(16000);
            } else {
                this.width = width2;
            }
            return this;
        }

        public Builder height(Integer height2) {
            if (BaseReq.isSuperSize(height2)) {
                this.height = Integer.valueOf(16000);
            } else {
                this.height = height2;
            }
            return this;
        }

        public Builder scale(Float scale2) {
            this.scale = Float.valueOf(scale2 == null ? 0.5f : scale2.floatValue());
            return this;
        }

        public Builder netloadExecutorService(ExecutorService service) {
            this.netloadExecutorService = service;
            return this;
        }

        public Builder imageMarkRequest(APImageMarkRequest imageMarkRequest2) {
            this.imageMarkRequest = imageMarkRequest2;
            return this;
        }

        public Builder setDrawableDecoder(DrawableDecoder drawableDecoder2) {
            this.drawableDecoder = drawableDecoder2;
            return this;
        }

        public Builder caller(String caller2) {
            this.caller = caller2;
            return this;
        }

        public Builder business(String businessId2) {
            this.businessId = businessId2;
            return this;
        }

        public Builder quality(int quality2) {
            this.quality = quality2;
            return this;
        }

        public Builder priority(int priority2) {
            this.priority = priority2;
            return this;
        }

        public Builder https(boolean bHttps2) {
            this.bHttps = bHttps2;
            return this;
        }

        public Builder md5(String md52) {
            this.md5 = md52;
            return this;
        }

        public Builder progressive(boolean bProgressive2) {
            this.bProgressive = bProgressive2;
            return this;
        }

        public Builder returnStatusWhileInMem(boolean returnStatus) {
            this.returnStatusWhileInMem = returnStatus;
            return this;
        }

        public Builder viewW2HRatio(float ratio) {
            this.viewW2HRatio = Float.valueOf(ratio);
            return this;
        }

        public Builder withImageDataInCallback(boolean with) {
            this.withImageDataInCallback = with;
            return this;
        }

        public Builder aliasPath(String aliasPath2) {
            this.aliasPath = aliasPath2;
            return this;
        }

        public Builder base64Optimization(Base64Optimization optimization) {
            this.base64Optimization = optimization;
            return this;
        }

        public Builder cacheInMem(boolean cache) {
            this.cacheInMem = cache;
            return this;
        }

        public Builder usingSourceType(boolean usingSourceType2) {
            this.usingSourceType = usingSourceType2;
            return this;
        }

        public Builder setContext(Context context2) {
            this.context = context2;
            return this;
        }

        public Builder setBizType(String bizType2) {
            this.bizType = bizType2;
            return this;
        }

        public Builder setNullDefaultDrawable(boolean set) {
            this.setNullDefaultDrawable = set;
            return this;
        }

        public Builder setSecondaryCutScaleType(CutScaleType secondaryCutScaleType2) {
            this.secondaryCutScaleType = secondaryCutScaleType2;
            return this;
        }

        public Builder setExtInfo(Map<String, String> extInfo2) {
            this.extInfo = extInfo2;
            return this;
        }

        public Builder detectedGif(boolean detected) {
            this.detectedGif = detected;
            return this;
        }

        public Builder shareGifMemCache(boolean bShare) {
            this.shareGifMemCache = bShare;
            return this;
        }

        public Builder enableSaliency(boolean enable) {
            this.enableSaliency = enable;
            return this;
        }

        public Builder expiredTime(long expiredTime2) {
            this.expiredTime = expiredTime2;
            return this;
        }

        public Builder timeout(int timeout2) {
            this.timeout = timeout2;
            return this;
        }

        public Builder baseOptions(BaseOptions baseOptions2) {
            this.baseOptions = baseOptions2;
            return this;
        }

        public Builder cloneFrom(DisplayImageOptions options) {
            this.imageOnLoading = options.getImageOnLoading();
            this.cutScaleType = options.cutScaleType;
            this.delayBeforeLoading = options.delayBeforeLoading;
            this.considerExifParams = options.considerExifParams;
            this.processor = options.plugin;
            this.displayer = options.displayer;
            this.isSyncLoading = options.isSyncLoading;
            this.originalSize = options.srcSize;
            if (options.width == null || options.width.intValue() <= 16000 || options.width.intValue() == Integer.MAX_VALUE) {
                this.width = options.width;
            } else {
                this.width = Integer.valueOf(16000);
            }
            if (options.height == null || options.height.intValue() <= 16000 || options.height.intValue() == Integer.MAX_VALUE) {
                this.height = options.height;
            } else {
                this.height = Integer.valueOf(16000);
            }
            this.scale = options.scale;
            this.netloadExecutorService = options.netloadExecutorService;
            this.imageMarkRequest = options.imageMarkRequest;
            this.drawableDecoder = options.drawableDecoder;
            this.caller = options.caller;
            this.businessId = options.businessId;
            this.quality = options.getQuality();
            this.priority = options.getPriority();
            this.bProgressive = options.isProgressive();
            this.returnStatusWhileInMem = options.returnStatusWhileInMem;
            this.viewW2HRatio = options.viewW2HRatio;
            this.withImageDataInCallback = options.withImageDataInCallback;
            this.aliasPath = options.aliasPath;
            this.base64Optimization = options.base64Optimization;
            this.cacheInMem = options.cacheInMem;
            this.usingSourceType = options.usingSourceType;
            this.context = options.getContext();
            if (!APConstants.DEFAULT_BUSINESS.equals(options.getBizType())) {
                this.bizType = options.getBizType();
            }
            this.setNullDefaultDrawable = options.setNullDefaultDrawable;
            this.secondaryCutScaleType = options.secondaryCutScaleType;
            this.extInfo = options.extInfo;
            this.detectedGif = options.detectedGif;
            this.shareGifMemCache = options.shareGifMemCache;
            this.enableSaliency = options.enableSaliency;
            this.expiredTime = options.getExpiredTime();
            this.timeout = options.getTimeout();
            this.bundle = options.bundle;
            this.baseOptions = options.baseOptions;
            return this;
        }

        public DisplayImageOptions build() {
            return new DisplayImageOptions(this);
        }
    }

    public interface DrawableDecoder {
        Drawable decode(InputStream inputStream);
    }

    private DisplayImageOptions(Builder builder) {
        WeakReference<Drawable> weakReference;
        if (builder.imageOnLoading == null) {
            weakReference = null;
        } else {
            weakReference = new WeakReference<>(builder.imageOnLoading);
        }
        this.imageOnLoading = weakReference;
        this.delayBeforeLoading = builder.delayBeforeLoading;
        this.considerExifParams = builder.considerExifParams;
        this.plugin = builder.processor;
        this.displayer = builder.displayer;
        this.isSyncLoading = builder.isSyncLoading;
        this.srcSize = builder.originalSize;
        this.width = builder.width;
        this.height = builder.height;
        this.scale = builder.scale;
        if (builder.cutScaleType != null) {
            this.cutScaleType = builder.cutScaleType;
        } else {
            this.cutScaleType = CutScaleType.KEEP_RATIO;
        }
        this.netloadExecutorService = builder.netloadExecutorService;
        this.imageMarkRequest = builder.imageMarkRequest;
        this.drawableDecoder = builder.drawableDecoder;
        this.caller = builder.caller;
        this.businessId = TextUtils.isEmpty(builder.businessId) ? APConstants.DEFAULT_BUSINESS : builder.businessId;
        setQuality(builder.quality);
        setPriority(builder.priority);
        setMd5(builder.md5);
        setHttps(builder.bHttps);
        setProgressive(builder.bProgressive);
        setExpiredTime(builder.expiredTime);
        this.returnStatusWhileInMem = builder.returnStatusWhileInMem;
        this.viewW2HRatio = builder.viewW2HRatio;
        this.withImageDataInCallback = builder.withImageDataInCallback;
        this.aliasPath = builder.aliasPath;
        this.base64Optimization = builder.base64Optimization;
        this.cacheInMem = builder.cacheInMem;
        this.usingSourceType = builder.usingSourceType;
        setContext(builder.context);
        setBizType(builder.bizType);
        this.setNullDefaultDrawable = builder.setNullDefaultDrawable;
        this.secondaryCutScaleType = builder.secondaryCutScaleType;
        this.extInfo = builder.extInfo;
        this.detectedGif = builder.detectedGif;
        this.shareGifMemCache = builder.shareGifMemCache;
        setEnableSaliency(builder.enableSaliency);
        setTimeout(builder.timeout);
        this.bundle = builder.bundle;
        if (this.bundle != null) {
            this.fileKey = this.bundle.getString("filekey");
        }
        this.baseOptions = builder.baseOptions;
        if (this.detectedGif && this.usingSourceType) {
            throw new IllegalArgumentException("detectedGif and usingSourceType is both true. Only one should be true");
        }
    }

    public final boolean shouldProcess() {
        return this.plugin != null;
    }

    public final Drawable getImageOnLoading() {
        if (this.imageOnLoading == null) {
            return null;
        }
        return (Drawable) this.imageOnLoading.get();
    }

    public final CutScaleType getCutScaleType() {
        return this.cutScaleType;
    }

    public final ImageWorkerPlugin getProcessor() {
        return this.plugin;
    }

    public final APDisplayer getDisplayer() {
        return this.displayer;
    }

    public final boolean isSyncLoading() {
        return this.isSyncLoading;
    }

    public final Integer getHeight() {
        if (CutScaleType.NONE.equals(this.cutScaleType)) {
            return Integer.valueOf(Integer.MAX_VALUE);
        }
        return this.height;
    }

    public final Integer getWidth() {
        if (CutScaleType.NONE.equals(this.cutScaleType)) {
            return Integer.valueOf(Integer.MAX_VALUE);
        }
        return this.width;
    }

    public final Float getScale() {
        return this.scale;
    }

    public final Size getOriginalSize() {
        return this.srcSize;
    }

    public final boolean hasNetloadExecutorService() {
        return this.netloadExecutorService != null;
    }

    public final ExecutorService getNetloadExecutorService() {
        return this.netloadExecutorService;
    }

    public final APImageMarkRequest getImageMarkRequest() {
        return this.imageMarkRequest;
    }

    public final DrawableDecoder getDrawableDecoder() {
        return this.drawableDecoder;
    }

    public final String getCaller() {
        return this.caller;
    }

    public final String getBusinessId() {
        return this.businessId;
    }

    public final boolean isReturnStatusWhileInMem() {
        return this.returnStatusWhileInMem;
    }

    public final Float viewW2HRatio() {
        return this.viewW2HRatio;
    }

    public final boolean isWithImageDataInCallback() {
        return this.withImageDataInCallback;
    }

    public final String getAliasPath() {
        return this.aliasPath;
    }

    public final Base64Optimization getBase64Optimization() {
        return this.base64Optimization;
    }

    public final boolean isCacheInMem() {
        return this.cacheInMem;
    }

    public final boolean isUsingSourceType() {
        return this.usingSourceType;
    }

    public final boolean isSetNullDefaultDrawable() {
        return this.setNullDefaultDrawable;
    }

    public final boolean isDetectedGif() {
        return this.detectedGif;
    }

    public final boolean isEnableSaliency() {
        return this.enableSaliency;
    }

    public static DisplayImageOptions createSimple() {
        return new Builder().build();
    }

    public final String toString() {
        return "DisplayImageOptions{imageOnLoading=" + this.imageOnLoading + ", delayBeforeLoading=" + this.delayBeforeLoading + ", considerExifParams=" + this.considerExifParams + ", displayer=" + this.displayer + ", isSyncLoading=" + this.isSyncLoading + ", netloadExecutorService=" + this.netloadExecutorService + ", caller='" + this.caller + '\'' + ", viewW2HRatio=" + this.viewW2HRatio + ", drawableDecoder=" + this.drawableDecoder + ", returnStatusWhileInMem=" + this.returnStatusWhileInMem + ", withImageDataInCallback=" + this.withImageDataInCallback + "}\t" + super.toString();
    }
}
