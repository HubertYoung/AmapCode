package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import com.autonavi.minimap.ajx3.AjxConfig;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.ImageCache.Image;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.action.AjxAssetLoadAction;
import com.autonavi.minimap.ajx3.loader.action.AjxFileLoadAction;
import com.autonavi.minimap.ajx3.loader.action.AjxMemoryLoadAction;
import com.autonavi.minimap.ajx3.loader.action.AjxResLoadAction;
import com.autonavi.minimap.ajx3.loader.action.AjxResourceLoadAction;
import com.autonavi.minimap.ajx3.loader.action.AjxSVGLoadAction;
import com.autonavi.minimap.ajx3.loader.action.AjxWebLoadAction;
import com.autonavi.minimap.ajx3.loader.picasso.Cache;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.Builder;
import com.autonavi.minimap.ajx3.loader.picasso.Utils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.util.HashMap;
import java.util.Map;

public class AjxLoaderManager {
    public static final String LOADER_TYPE_AJX = "ajx";
    public static final String LOADER_TYPE_ASSET = "asset";
    public static final String LOADER_TYPE_FILE = "file";
    public static final String LOADER_TYPE_HTTP = "http";
    public static final String LOADER_TYPE_HTTPS = "https";
    public static final String LOADER_TYPE_MEMORY = "memory";
    public static final String LOADER_TYPE_NULL = AjxNoSchemeLoader.SCHEME_NULL;
    public static final String LOADER_TYPE_PATH = "path";
    public static final String LOADER_TYPE_RES = "res";
    public static final String LOADER_TYPE_SVG = "svg";
    public static final String LOADER_TYPE_WEB = ".web.";
    public static final int LOAD_ACTION_AJX = 0;
    public static final int LOAD_ACTION_ASSET = 3;
    public static final int LOAD_ACTION_FILE = 2;
    public static final int LOAD_ACTION_HTTP = 1;
    public static final int LOAD_ACTION_MEMORY = 5;
    public static final int LOAD_ACTION_RES = 4;
    public static final int LOAD_ACTION_SVG = 7;
    public static final int LOAD_ACTION_WEB = 6;
    private final IAjxImageLoader IDLE_LOADER = new IAjxImageLoader() {
        public byte[] loadImage(@NonNull PictureParams pictureParams) {
            return null;
        }

        public String processingPath(@NonNull PictureParams pictureParams) {
            return pictureParams.url;
        }

        public float[] readImageSize(@NonNull PictureParams pictureParams) {
            return new float[]{0.0f, 0.0f};
        }

        public void loadImage(@NonNull View view, @NonNull IAjxContext iAjxContext, @NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
            imageCallback.onBitmapFailed(null);
        }

        public void preLoadImage(@NonNull PictureParams pictureParams, @NonNull ImageCallback imageCallback) {
            imageCallback.onBitmapFailed(null);
        }
    };
    private Map<String, IAjxImageLoader> mLoaders = new HashMap();

    public AjxLoaderManager(@NonNull Context context, @NonNull AjxConfig ajxConfig) {
        Cache<Image> lruCache = ajxConfig.getLruCache();
        if (lruCache != null) {
            Picasso.setSingletonInstance(new Builder(context).memoryCache(lruCache).build());
        }
        SparseArray sparseArray = new SparseArray(5);
        sparseArray.put(0, new AjxResourceLoadAction());
        sparseArray.put(1, ajxConfig.getHttpLoadAction());
        sparseArray.put(2, new AjxFileLoadAction(ajxConfig.getImageLoader()));
        sparseArray.put(3, new AjxAssetLoadAction(ajxConfig.getImageLoader()));
        sparseArray.put(4, new AjxResLoadAction(ajxConfig.getImageLoader()));
        sparseArray.put(5, new AjxMemoryLoadAction(ajxConfig.getImageLoader()));
        sparseArray.put(6, new AjxWebLoadAction(ajxConfig.getImageLoader()));
        sparseArray.put(7, new AjxSVGLoadAction(ajxConfig.getImageLoader()));
        AjxHttpLoader ajxHttpLoader = new AjxHttpLoader(context, sparseArray);
        this.mLoaders.put("http", ajxHttpLoader);
        this.mLoaders.put("https", ajxHttpLoader);
        this.mLoaders.put("asset", new AjxAssetLoader(context, sparseArray));
        this.mLoaders.put("file", new AjxFileLoader(context, sparseArray));
        this.mLoaders.put("res", new AjxResLoader(context, sparseArray));
        this.mLoaders.put("ajx", new Ajx3ResourceLoader(context, sparseArray));
        this.mLoaders.put("path", new AjxPathLoader(context, sparseArray));
        this.mLoaders.put("memory", new AjxMemoryLoader(context, sparseArray));
        this.mLoaders.put(".web.", new AjxWebLoader(context, sparseArray));
        this.mLoaders.put(LOADER_TYPE_NULL, new AjxNoSchemeLoader(context, sparseArray));
        this.mLoaders.put("svg", new AjxSVGLoader(context, sparseArray));
    }

    public IAjxImageLoader lookupLoader(@NonNull String str) {
        LogHelper.d("AjxLoaderManager: lookupLoader for url = ".concat(String.valueOf(str)));
        IAjxImageLoader iAjxImageLoader = "svg".equals(str) ? this.mLoaders.get("svg") : null;
        if (iAjxImageLoader == null && Utils.isWebResource(str)) {
            iAjxImageLoader = this.mLoaders.get(".web.");
        }
        if (iAjxImageLoader == null) {
            iAjxImageLoader = this.mLoaders.get(Uri.parse(str).getScheme());
        }
        if (iAjxImageLoader != null) {
            return iAjxImageLoader;
        }
        StringBuilder sb = new StringBuilder("无法处理 url = ");
        sb.append(str);
        sb.append(" 类型的请求,\n    请检查url参数是否正常\n    如果有必要请找Android支撑同学添加相应的Loader");
        LogHelper.showErrorMsg2(sb.toString());
        return this.IDLE_LOADER;
    }
}
