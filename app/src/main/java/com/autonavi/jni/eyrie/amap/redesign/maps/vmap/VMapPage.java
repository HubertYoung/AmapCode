package com.autonavi.jni.eyrie.amap.redesign.maps.vmap;

import android.content.Context;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay.OverlayType;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.IOverlayTextureLoader;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureInfo;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureLoaderDispatcher;
import java.util.ArrayList;
import java.util.List;

public class VMapPage implements IVPageContext {
    private final Context mContext;
    private final int mEngineID;
    private final List<BaseLayer> mLayers = new ArrayList();
    long mNativePtr;
    private final int mPageID;
    private final TextureLoaderDispatcher mTextureLoaderDispatcher = new TextureLoaderDispatcher(this);

    private static native void nativeAddTextureLoader(long j, TextureLoaderDispatcher textureLoaderDispatcher);

    private static native void nativeClearFocus(long j);

    private static native long nativeCreateOverlay(long j, int i, String str);

    private static native TextureInfo nativeCreateTextureGetInfo(long j, OverlayTextureParam overlayTextureParam);

    private static native void nativeDestroyTexture(long j, int i, String str);

    private static native int nativeGetPageID(long j);

    private static native void nativeOnHide(long j);

    private static native void nativeOnShow(long j);

    private static native void nativeRemoveTextureLoader(long j, TextureLoaderDispatcher textureLoaderDispatcher);

    VMapPage(long j, int i, Context context) {
        this.mNativePtr = j;
        this.mContext = context;
        nativeAddTextureLoader(this.mNativePtr, this.mTextureLoaderDispatcher);
        this.mPageID = nativeGetPageID(this.mNativePtr);
        this.mEngineID = i;
    }

    public void onShow() {
        nativeOnShow(this.mNativePtr);
    }

    public void onHide() {
        nativeOnHide(this.mNativePtr);
    }

    /* access modifiers changed from: 0000 */
    public void onDestroy() {
        for (BaseLayer onDestroy : this.mLayers) {
            onDestroy.onDestroy();
        }
        this.mLayers.clear();
        nativeRemoveTextureLoader(this.mNativePtr, this.mTextureLoaderDispatcher);
        this.mTextureLoaderDispatcher.release();
    }

    public void clearFocus() {
        nativeClearFocus(this.mNativePtr);
    }

    public long createNativeOverlay(OverlayType overlayType, String str) {
        return nativeCreateOverlay(this.mNativePtr, overlayType.getValue(), str);
    }

    public void addLayer(BaseLayer baseLayer) {
        this.mLayers.add(baseLayer);
    }

    public void addTextureLoader(IOverlayTextureLoader iOverlayTextureLoader, int i) {
        this.mTextureLoaderDispatcher.addTextureLoader(iOverlayTextureLoader, i);
    }

    public void addTextureLoader(IOverlayTextureLoader iOverlayTextureLoader) {
        addTextureLoader(iOverlayTextureLoader, 0);
    }

    public void removeTextureLoader(IOverlayTextureLoader iOverlayTextureLoader) {
        this.mTextureLoaderDispatcher.removeTextureLoader(iOverlayTextureLoader);
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getPageID() {
        return this.mPageID;
    }

    public int getEngineID() {
        return this.mEngineID;
    }

    public void destroyTexture(String str) {
        nativeDestroyTexture(this.mNativePtr, this.mEngineID, str);
    }

    public TextureInfo createTextureGetInfo(OverlayTextureParam overlayTextureParam) {
        return nativeCreateTextureGetInfo(this.mNativePtr, overlayTextureParam);
    }
}
