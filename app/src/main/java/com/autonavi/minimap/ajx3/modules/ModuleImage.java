package com.autonavi.minimap.ajx3.modules;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.image.ImageCache;

@AjxModule("img")
public class ModuleImage extends AbstractModule {
    public static final String MODULE_NAME = "img";

    public ModuleImage(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("preload")
    public void preloadimage(String[] strArr, JsFunctionCallback jsFunctionCallback) {
        if (getNativeContext() != null) {
            ImageCache.getInstance(getNativeContext()).preLoad(getContext(), strArr, jsFunctionCallback);
        }
    }

    @AjxMethod("evictCache")
    public void evictCache(String[] strArr) {
        if (getNativeContext() != null) {
            ImageCache.getInstance(getNativeContext()).evictCache(getContext(), strArr);
        }
    }
}
