package com.autonavi.minimap.ajx3.modules;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.acanvas.ACanvasImage;
import com.autonavi.minimap.acanvas.ACanvasLog;
import com.autonavi.minimap.acanvas.IACanvasBridge;
import com.autonavi.minimap.acanvas.IACanvasFpsUpdater;
import com.autonavi.minimap.acanvas.IACanvasImageLoaderCallback;
import com.autonavi.minimap.ajx3.acanvas.AjxCanvasBridgeManager;
import com.autonavi.minimap.ajx3.acanvas.AjxCanvasImageLoader;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.mapcanvas.AmapCanvasBridge;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule(isInUiThread = false, value = "mapcanvas")
public class ModuleAmapCanvas extends AbstractModule {
    private List<JsFunctionCallback> callbacks = new ArrayList();
    /* access modifiers changed from: private */
    public final IACanvasBridge mACanvasBridge;
    /* access modifiers changed from: private */
    public Set<IACanvasImageLoaderCallback> mImageLoadCallback = new HashSet();
    private int mMapFps;
    private MapPageLifeCycleListener mMapPageLifeCycleListener;
    private bty mMapView;

    public static class MapPageLifeCycleListener implements d {
        HashSet<JsFunctionCallback> callbacks = new HashSet<>();
        private Class lastPageClazz;
        private Class mainPageClazz;

        public void addListener(JsFunctionCallback jsFunctionCallback) {
            this.callbacks.add(jsFunctionCallback);
        }

        public void clear() {
            this.callbacks.clear();
        }

        private void notify(String str) {
            Iterator<JsFunctionCallback> it = this.callbacks.iterator();
            while (it.hasNext()) {
                it.next().callback(str);
            }
        }

        public void onPageLifeResumed(@NonNull WeakReference<AbstractBasePage> weakReference) {
            if (this.mainPageClazz == null && AMapPageUtil.isHomePage()) {
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    this.mainPageClazz = pageContext.getClass();
                }
            }
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null && abstractBasePage.getClass() == this.mainPageClazz) {
                notify("pageShow");
            }
        }

        public void onPageLifePaused(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null) {
                this.lastPageClazz = abstractBasePage.getClass();
                if (this.mainPageClazz == null || this.lastPageClazz == this.mainPageClazz) {
                    notify("pageHide");
                }
            }
        }
    }

    public ModuleAmapCanvas(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.mACanvasBridge = new AmapCanvasBridge(new AjxCanvasImageLoader(iAjxContext));
    }

    @AjxMethod(invokeMode = "sync", value = "createContext2D")
    public void createContext2D(String str, int i, int i2, float f) {
        this.mACanvasBridge.createContext2D(str, DimensionUtils.standardUnitToPixel((float) i), DimensionUtils.standardUnitToPixel((float) i2), f / 2.0f);
    }

    @AjxMethod(invokeMode = "sync", value = "renderCommand")
    public void renderCommand(String str, String str2) {
        this.mACanvasBridge.renderCommand(str, str2);
        if (this.mMapFps > 0) {
            if (this.mMapView == null) {
                this.mMapView = DoNotUseTool.getMapView();
            }
            if (this.mMapView != null) {
                this.mMapView.c(this.mMapFps);
                this.mMapView.b(this.mMapFps);
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "measureText")
    public JSONObject measureText(String str, String str2, String str3) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("width", (double) this.mACanvasBridge.measureText(str, str2, str3));
        return jSONObject;
    }

    @AjxMethod(invokeMode = "sync", value = "createImage")
    public void createImage(String str, final JsFunctionCallback jsFunctionCallback) {
        AnonymousClass1 r0 = new IACanvasImageLoaderCallback() {
            public void onLoaded(@NonNull ACanvasImage aCanvasImage, @NonNull Bitmap bitmap) {
                ModuleAmapCanvas.this.mImageLoadCallback.remove(this);
                jsFunctionCallback.callback(Integer.valueOf(aCanvasImage.getId()), Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()));
            }

            public void onFailed() {
                ModuleAmapCanvas.this.mImageLoadCallback.remove(this);
                jsFunctionCallback.callback(Integer.valueOf(0));
            }
        };
        this.mImageLoadCallback.add(r0);
        this.mACanvasBridge.loadImage(getNativeContext(), str, false, r0);
    }

    @AjxMethod(invokeMode = "sync", value = "bindImageTexture")
    public void bindImageTexture(final String str, String str2, int i) {
        AnonymousClass2 r5 = new IACanvasImageLoaderCallback() {
            public void onLoaded(@NonNull ACanvasImage aCanvasImage, @NonNull Bitmap bitmap) {
                ModuleAmapCanvas.this.mImageLoadCallback.remove(this);
                ModuleAmapCanvas.this.mACanvasBridge.bindImageTexture(str, aCanvasImage.getId(), bitmap);
            }

            public void onFailed() {
                ModuleAmapCanvas.this.mImageLoadCallback.remove(this);
            }
        };
        this.mImageLoadCallback.add(r5);
        this.mACanvasBridge.loadImage(getNativeContext(), str2, true, r5);
    }

    @AjxMethod(invokeMode = "sync", value = "unBindImageTexture")
    public void unBindImageTexture(String str, String str2, int i) {
        this.mACanvasBridge.releaseImageTexture(str, str2);
    }

    @AjxMethod(invokeMode = "sync", value = "addFpsUpdater")
    public void addFpsUpdater(String str, float f, final JsFunctionCallback jsFunctionCallback) {
        this.mACanvasBridge.addFpsUpdater(str, f, new IACanvasFpsUpdater() {
            public void updateFps(float f) {
                jsFunctionCallback.callback(Float.valueOf(f));
            }
        });
    }

    @AjxMethod(invokeMode = "sync", value = "setCanvasSize")
    public void setCanvasSize(String str, int i, int i2) {
        this.mACanvasBridge.setCanvasSize(str, DimensionUtils.standardUnitToPixel((float) i), DimensionUtils.standardUnitToPixel((float) i2));
    }

    @AjxMethod(invokeMode = "sync", value = "setCanvasScaleFactor")
    public void setCanvasScale(String str, float f) {
        this.mACanvasBridge.setCanvasScale(str, f);
    }

    @AjxMethod(invokeMode = "sync", value = "setLogLevel")
    public void setLogLevel(String str) {
        ACanvasLog.setLevel(str);
    }

    @AjxMethod("setMapFps")
    public void setMapFps(int i) {
        this.mMapFps = i;
    }

    @AjxMethod("addMapPageLifeCycleListener")
    public void addMapPageLifeCycleListener(JsFunctionCallback jsFunctionCallback) {
        if (this.mMapPageLifeCycleListener == null) {
            this.mMapPageLifeCycleListener = new MapPageLifeCycleListener();
            drm.a((c) this.mMapPageLifeCycleListener);
        }
        MapPageVirtualApplication.addListener(jsFunctionCallback);
        this.mMapPageLifeCycleListener.addListener(jsFunctionCallback);
        this.callbacks.add(jsFunctionCallback);
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        if (this.mMapPageLifeCycleListener != null) {
            this.mMapPageLifeCycleListener.clear();
            drm.b((c) this.mMapPageLifeCycleListener);
            this.mMapPageLifeCycleListener = null;
        }
        if (this.callbacks.size() > 0) {
            for (JsFunctionCallback removeListener : this.callbacks) {
                MapPageVirtualApplication.removeListener(removeListener);
            }
            this.callbacks.clear();
        }
        AjxCanvasBridgeManager.getSingleton().removeBridge(getContext());
        this.mACanvasBridge.release();
        this.mImageLoadCallback.clear();
    }
}
