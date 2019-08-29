package com.autonavi.minimap.ajx3.acanvas.module;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.autonavi.minimap.acanvas.ACanvasImage;
import com.autonavi.minimap.acanvas.ACanvasLog;
import com.autonavi.minimap.acanvas.IACanvasBridge;
import com.autonavi.minimap.acanvas.IACanvasFpsUpdater;
import com.autonavi.minimap.acanvas.IACanvasImageLoaderCallback;
import com.autonavi.minimap.ajx3.acanvas.AjxCanvasBridgeManager;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule(isInUiThread = false, value = "ajx.canvas")
public class AjxModuleCanvas extends AbstractModule {
    /* access modifiers changed from: private */
    public final IACanvasBridge mACanvasBridge;
    /* access modifiers changed from: private */
    public Set<IACanvasImageLoaderCallback> mImageLoadCallback = new HashSet();

    public AjxModuleCanvas(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.mACanvasBridge = AjxCanvasBridgeManager.getSingleton().getBridge(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "createContext2D")
    public void createContext2D(String str, int i, int i2, float f) {
        this.mACanvasBridge.createContext2D(str, DimensionUtils.standardUnitToPixel((float) i), DimensionUtils.standardUnitToPixel((float) i2), f / 2.0f);
    }

    @AjxMethod(invokeMode = "sync", value = "renderCommand")
    public void renderCommand(String str, String str2) {
        this.mACanvasBridge.renderCommand(str, str2);
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
                AjxModuleCanvas.this.mImageLoadCallback.remove(this);
                jsFunctionCallback.callback(Integer.valueOf(aCanvasImage.getId()), Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()));
            }

            public void onFailed() {
                AjxModuleCanvas.this.mImageLoadCallback.remove(this);
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
                AjxModuleCanvas.this.mImageLoadCallback.remove(this);
                AjxModuleCanvas.this.mACanvasBridge.bindImageTexture(str, aCanvasImage.getId(), bitmap);
            }

            public void onFailed() {
                AjxModuleCanvas.this.mImageLoadCallback.remove(this);
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

    public void onModuleDestroy() {
        super.onModuleDestroy();
        AjxCanvasBridgeManager.getSingleton().removeBridge(getContext());
        this.mACanvasBridge.release();
        this.mImageLoadCallback.clear();
    }
}
