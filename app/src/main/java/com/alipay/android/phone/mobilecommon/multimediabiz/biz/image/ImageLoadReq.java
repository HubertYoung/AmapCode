package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.APMGifDrawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APGifController;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APLoadStateListener;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions.Builder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.gif.GifWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.NBNetUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.mobile.common.utils.MD5Util;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageLoadReq {
    public static final int LOAD_STEP_DISKCACHE = 1;
    public static final int LOAD_STEP_LOCAL = 2;
    public static final int LOAD_STEP_MEM = 0;
    public static final int LOAD_STEP_NET = 3;
    public static final int SOURCE_TYPE_BASE64 = 3;
    public static final int SOURCE_TYPE_DATA = 2;
    public static final int SOURCE_TYPE_PATH = 1;
    public static final int TRANSPORT_DEFAULT = 1;
    public static final int TRANSPORT_MDN = 3;
    public static final int TRANSPORT_NBNET = 2;
    public static final int TYPE_CUSTOM = 1;
    public static final int TYPE_NORMAL = 0;
    private ViewWrapper<ImageView> a;
    public boolean aliasPathUpdate;
    private ViewWrapper<View> b;
    public boolean bAftsLink;
    public boolean bProgressive;
    private GifWrapper c;
    public BitmapCacheKey cacheKey;
    private String d;
    public byte[] data;
    public APImageDownLoadCallback downLoadCallback;
    public APImageDownloadRsp downloadRsp;
    private Integer e;
    private StateDrawableParam f;
    public String fileId;
    private LoadOptions g;
    public int imageId;
    public ImageLoadEngine loadEngine;
    public int mTimeout;
    public LoadImageFromNetworkPerf netPerf;
    public DisplayImageOptions options;
    public String path;
    public boolean skipDisplay;
    public String source;
    public int sourceType;
    public long startTime;
    public String taskId;
    public APMultimediaTaskModel taskModel;
    public String thumbPath;
    public int type;
    public boolean urlToDjango;
    public String zoom;

    public static class LoadOptions {
        public boolean ignoreGifAutoStart;
        public boolean ignoreNetTask;
        public boolean saveToDiskCache = true;
    }

    public static class StateDrawableParam {
        private String a = "";
        public Map<String, Integer> localPaths;

        public String getKey() {
            if (!TextUtils.isEmpty(this.a) || this.localPaths == null) {
                return this.a;
            }
            for (Entry entry : this.localPaths.entrySet()) {
                this.a += ((String) entry.getKey()) + entry.getValue() + MetaRecord.LOG_SEPARATOR;
            }
            this.a = MD5Util.getMD5String(this.a);
            Logger.D("StateDrawableParam", "loadCustomImage key=" + this.a, new Object[0]);
            return this.a;
        }
    }

    public ImageLoadReq() {
        this.skipDisplay = false;
        this.bProgressive = false;
        this.aliasPathUpdate = false;
        this.sourceType = 1;
        this.mTimeout = -1;
    }

    public ImageLoadReq(ImageLoadEngine engine, Map<String, Integer> localPaths, DisplayImageOptions options2, APImageDownLoadCallback callback) {
        this.skipDisplay = false;
        this.bProgressive = false;
        this.aliasPathUpdate = false;
        this.sourceType = 1;
        this.mTimeout = -1;
        this.loadEngine = engine;
        this.f = new StateDrawableParam();
        this.f.localPaths = localPaths;
        this.path = this.f.getKey();
        this.options = a(options2);
        this.cacheKey = makeCacheKey();
        this.options = options2;
        this.downLoadCallback = callback;
        if (this.mTimeout <= 0 && options2.getTimeout() > 0) {
            this.mTimeout = options2.getTimeout();
        }
    }

    public ImageLoadReq(ImageLoadEngine engine, String source2, View view, DisplayImageOptions options2, APImageDownLoadCallback downLoadCallback2) {
        this(engine, view, downLoadCallback2, options2);
        this.source = LocalIdTool.isLocalIdRes(source2) ? LocalIdTool.get().decodeToPath(source2) : source2;
        if (PathUtils.isBase64Image(source2)) {
            this.sourceType = 3;
            this.path = CommonUtils.calcBase64Key(source2, options2.getBase64Optimization());
        } else if (options2.getWidth() == null || options2.getHeight() == null) {
            this.path = PathUtils.extractPath(source2);
        } else {
            this.path = PathUtils.preferImageUrl(PathUtils.extractPath(source2), options2.getWidth().intValue(), options2.getHeight().intValue());
        }
        if (this.path != null) {
            this.path = this.path.trim();
        }
        this.cacheKey = makeCacheKey();
        this.taskId = this.path;
        this.downloadRsp.setCacheId(this.cacheKey.complexCacheKey());
        this.downloadRsp.setSourcePath(source2);
        this.type = 1;
    }

    public ImageLoadReq(ImageLoadEngine engine, String source2, ImageView imageView, APImageDownLoadCallback downLoadCallback2, DisplayImageOptions options2) {
        this(engine, source2, (View) imageView, options2, downLoadCallback2);
        if (PathUtils.isBase64Image(source2)) {
            this.sourceType = 3;
            this.path = CommonUtils.calcBase64Key(source2, options2.getBase64Optimization());
        } else if (PathUtils.isHttp(this.path) || CutScaleType.NONE.equals(options2.getCutScaleType())) {
            this.taskId = this.path;
        } else {
            this.taskId = this.cacheKey.complexCacheKey();
        }
        this.type = 0;
    }

    public ImageLoadReq(ImageLoadEngine engine, byte[] data2, ImageView imageView, APImageDownLoadCallback downLoadCallback2, DisplayImageOptions options2) {
        this(engine, (View) imageView, downLoadCallback2, options2);
        this.data = data2;
        this.path = PathUtils.preferImageUrl(PathUtils.extractPath(this.source), options2.getWidth().intValue(), options2.getHeight().intValue());
        this.cacheKey = makeCacheKey();
        this.downloadRsp.setCacheId(this.cacheKey.complexCacheKey());
        this.type = 0;
        this.sourceType = 2;
    }

    public ImageLoadReq(ImageLoadEngine engine, View view, APImageDownLoadCallback downLoadCallback2, DisplayImageOptions options2) {
        this.skipDisplay = false;
        this.bProgressive = false;
        this.aliasPathUpdate = false;
        this.sourceType = 1;
        this.mTimeout = -1;
        this.loadEngine = engine;
        this.b = new ViewWrapper<>(view);
        this.a = view instanceof ImageView ? new ViewWrapper<>((ImageView) view) : null;
        this.downLoadCallback = downLoadCallback2;
        this.options = a(options2);
        this.downloadRsp = new APImageDownloadRsp();
        this.type = 0;
        if (this.mTimeout <= 0 && options2.getTimeout() > 0) {
            this.mTimeout = options2.getTimeout();
        }
    }

    private static DisplayImageOptions a(DisplayImageOptions options2) {
        CutScaleType type2;
        if (!CompareUtils.in(options2.getCutScaleType(), CutScaleType.KEEP_RATIO, CutScaleType.AUTO_CUT_EXACTLY) || options2.getOriginalSize() == null) {
            return options2;
        }
        Builder b2 = new Builder().cloneFrom(options2);
        if (options2.getOriginalSize() == null) {
            type2 = options2.getCutScaleType();
        } else {
            type2 = ImageUtils.calcCutScaleType(options2.getOriginalSize(), options2.getScale().floatValue(), Math.max(options2.getWidth().intValue(), options2.getHeight().intValue()));
        }
        if (!CutScaleType.CENTER_CROP.equals(type2)) {
            type2 = CutScaleType.KEEP_RATIO;
        } else if (CutScaleType.AUTO_CUT_EXACTLY.equals(options2.getCutScaleType()) || options2.viewW2HRatio() != null) {
            type2 = CutScaleType.AUTO_CUT_EXACTLY;
        }
        return b2.imageScaleType(type2).build();
    }

    /* access modifiers changed from: protected */
    public BitmapCacheKey makeCacheKey() {
        return makeCacheKey(this.path);
    }

    public BitmapCacheKey makeCacheKey(String path2) {
        BitmapCacheKey key;
        int width = a(this.options.getWidth());
        int height = a(this.options.getHeight());
        if (this.options.usingSourceType) {
            key = new BitmapCacheKey(path2, width, height, this.options.getCutScaleType(), this.options.getProcessor(), this.options.getQuality(), this.options.getImageMarkRequest(), 1);
        } else {
            key = new BitmapCacheKey(path2, width, height, this.options.getCutScaleType(), this.options.getProcessor(), this.options.getQuality(), this.options.getImageMarkRequest());
        }
        key.updateAliasKey(this.options.getAliasPath());
        return key;
    }

    public int getTransportWay() {
        if (this.e == null) {
            if (ConfigManager.getInstance().getMdnBizConfig().checkBusiness(this.options.getBizType())) {
                this.e = Integer.valueOf(3);
                return this.e.intValue();
            }
            this.e = Integer.valueOf(NBNetUtils.getNBNetDLSwitch(this.options.getBusinessId()) ? 2 : 1);
        }
        return this.e.intValue();
    }

    public boolean isEnableSaliency() {
        return this.options.isEnableSaliency() && ConfigManager.getInstance().isEnableSaliency();
    }

    private static int a(Integer i) {
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return ((ImageLoadReq) o).a().equals(a());
    }

    public int hashCode() {
        int result;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = 0;
        if (this.loadEngine != null) {
            result = this.loadEngine.hashCode();
        } else {
            result = 0;
        }
        int i9 = result * 31;
        if (this.path != null) {
            i = this.path.hashCode();
        } else {
            i = 0;
        }
        int i10 = (i9 + i) * 31;
        if (this.source != null) {
            i2 = this.source.hashCode();
        } else {
            i2 = 0;
        }
        ImageView imageView = getTargetImageView();
        int i11 = (i10 + i2) * 31;
        if (imageView != null) {
            i3 = imageView.hashCode();
        } else {
            i3 = 0;
        }
        int i12 = (i11 + i3) * 31;
        if (this.downLoadCallback != null) {
            i4 = this.downLoadCallback.hashCode();
        } else {
            i4 = 0;
        }
        int i13 = (i12 + i4) * 31;
        if (this.options != null) {
            i5 = this.options.hashCode();
        } else {
            i5 = 0;
        }
        int i14 = (i13 + i5) * 31;
        if (this.cacheKey != null) {
            i6 = this.cacheKey.hashCode();
        } else {
            i6 = 0;
        }
        int i15 = (i14 + i6) * 31;
        if (this.taskModel != null) {
            i7 = this.taskModel.hashCode();
        } else {
            i7 = 0;
        }
        int i16 = (i15 + i7) * 31;
        if (this.downloadRsp != null) {
            i8 = this.downloadRsp.hashCode();
        }
        return i8 + i16;
    }

    private String a() {
        if (TextUtils.isEmpty(this.d)) {
            StringBuilder sb = new StringBuilder();
            ImageView imageView = getTargetImageView();
            if (imageView != null) {
                sb.append(System.identityHashCode(imageView));
            }
            sb.append("##").append(System.identityHashCode(this.downLoadCallback));
            sb.append("##").append(System.identityHashCode(this.cacheKey));
            this.d = sb.toString();
        }
        return this.d;
    }

    public ImageLoadReq updateDisplayOptions(DisplayImageOptions options2) {
        this.options = options2;
        this.cacheKey = makeCacheKey();
        this.downloadRsp.setCacheId(this.cacheKey.complexCacheKey());
        return this;
    }

    public ImageLoadReq clone() {
        ImageLoadReq loadReq = new ImageLoadReq();
        loadReq.source = this.source;
        loadReq.path = this.path;
        loadReq.options = this.options;
        loadReq.taskModel = this.taskModel;
        loadReq.cacheKey = this.cacheKey;
        loadReq.data = this.data;
        loadReq.downLoadCallback = this.downLoadCallback;
        loadReq.downloadRsp = this.downloadRsp;
        loadReq.a = this.a;
        loadReq.urlToDjango = this.urlToDjango;
        loadReq.fileId = this.fileId;
        loadReq.zoom = this.zoom;
        loadReq.mTimeout = this.mTimeout;
        return loadReq;
    }

    public boolean adjustToDjangoReq() {
        this.fileId = PathUtils.extractDjangoFileIds(this.path);
        if (TextUtils.isEmpty(this.fileId)) {
            this.urlToDjango = false;
            return this.urlToDjango;
        }
        this.zoom = PathUtils.extractDjangoZoomParams(this.path);
        if (this.zoom == null) {
            this.zoom = "";
        }
        boolean isTfsFormat = ZoomHelper.isTfsFormatZoom(this.zoom);
        int width = this.options.getWidth().intValue();
        int height = this.options.getHeight().intValue();
        if (width > 16000 || height > 16000) {
            width = 16000;
            height = 16000;
        }
        if (!(this.options.getCutScaleType() == CutScaleType.SCALE_AUTO_LIMIT || width == 0 || height == 0 || width == -1 || height == -1 || width == Integer.MAX_VALUE || height == Integer.MAX_VALUE)) {
            if (isTfsFormat) {
                Matcher matcher = Pattern.compile(ZoomHelper.TFS_WH_PATTERN).matcher(this.zoom);
                if (matcher.find()) {
                    this.zoom = matcher.replaceFirst(String.format("%dx%d", new Object[]{Integer.valueOf(width), Integer.valueOf(height)}));
                }
            } else if (TextUtils.isEmpty(this.zoom) || "original".equals(this.zoom)) {
                this.zoom = String.format("%dw_%dh_1l", new Object[]{Integer.valueOf(width), Integer.valueOf(height)});
                this.zoom = a(this.zoom);
            } else {
                Matcher matcher2 = Pattern.compile(ZoomHelper.OSS_WH_PATTERN).matcher(this.zoom);
                if (matcher2.find()) {
                    this.zoom = matcher2.replaceFirst(String.format("%dw_%dh", new Object[]{Integer.valueOf(width), Integer.valueOf(height)}));
                    this.zoom = a(this.zoom);
                }
            }
        }
        this.urlToDjango = true;
        return this.urlToDjango;
    }

    public String getAdjustedZoom() {
        return this.zoom;
    }

    public long getExpiredTime() {
        if (this.options == null) {
            return Long.MAX_VALUE;
        }
        return this.options.getExpiredTime();
    }

    public ImageView getTargetImageView() {
        if (this.a == null) {
            return null;
        }
        return (ImageView) this.a.getTargetView();
    }

    public boolean isEncryptRequest() {
        if (this.options == null || TextUtils.isEmpty(this.options.fileKey) || PathUtils.isLocalFile(this.path)) {
            return false;
        }
        return true;
    }

    public View getTargetView() {
        if (this.b == null) {
            return null;
        }
        return this.b.getTargetView();
    }

    public boolean isStateDrawableReq() {
        return (this.f == null || this.f.localPaths == null || this.f.localPaths.isEmpty()) ? false : true;
    }

    public StateDrawableParam getStateDrawableParam() {
        return this.f;
    }

    public void setGifWrapper(APGifController controller, APLoadStateListener listener) {
        if (this.c == null) {
            this.c = new GifWrapper(controller, listener);
            return;
        }
        this.c.setGifController(controller);
        this.c.setAPLoadStateListener(listener);
    }

    public GifWrapper getGifWrapper() {
        return this.c;
    }

    public APGifController getGifController() {
        if (this.c == null) {
            return null;
        }
        return this.c.getGifController();
    }

    public APLoadStateListener getAPLoadStateListener() {
        if (this.c == null) {
            return null;
        }
        return this.c.getAPLoadStateListener();
    }

    public void notifyGifController(APMGifDrawable gifDrawable) {
        APGifController controller = getGifController();
        if (controller != null) {
            controller.onGifDrawable(gifDrawable, null);
        }
    }

    public void notifyGifState(int step, boolean start, int code) {
        APLoadStateListener stateListener = getAPLoadStateListener();
        if (stateListener != null) {
            switch (step) {
                case 0:
                    stateListener.onMemLoadState(start, code, null);
                    return;
                case 1:
                    stateListener.onLocalLoadState(start, code, null);
                    return;
                case 2:
                    stateListener.onDiskCacheLoadState(start, code, null);
                    return;
                case 3:
                    stateListener.onNetLoadState(start, code, null);
                    return;
                default:
                    return;
            }
        }
    }

    public void setLoadOptions(LoadOptions options2) {
        this.g = options2;
    }

    public LoadOptions getLoadOptions() {
        if (this.g == null && this.options.baseOptions != null) {
            this.g = new LoadOptions();
            this.g.ignoreNetTask = this.options.baseOptions.ignoreNetTask;
            this.g.ignoreGifAutoStart = this.options.baseOptions.ignoreGifAutoStart;
            this.g.saveToDiskCache = this.options.baseOptions.saveToDiskCache;
        }
        return this.g;
    }

    private String a(String zoom2) {
        return ZoomHelper.formatOssZoomExtra(this, false, zoom2);
    }

    public String toString() {
        Object valueOf;
        ImageView view = getTargetImageView();
        StringBuilder append = new StringBuilder("ImageLoadReq{loadEngine=").append(this.loadEngine).append(", path='").append(this.path).append('\'').append(", source='").append(this.source).append('\'').append(", imageView=");
        if (view == null) {
            valueOf = "null";
        } else {
            valueOf = Integer.valueOf(System.identityHashCode(view));
        }
        return append.append(valueOf).append(", downLoadCallback=").append(this.downLoadCallback).append(", options=").append(this.options).append(", cacheKey='").append(this.cacheKey).append('\'').append('}').toString();
    }
}
