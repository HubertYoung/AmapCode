package com.autonavi.jni.eyrie.amap.redesign.maps.texture;

import android.os.Handler;
import android.os.Looper;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import java.util.ArrayList;
import java.util.Iterator;

public class TextureLoaderDispatcher {
    private final IVPageContext mContext;
    /* access modifiers changed from: private */
    public final ArrayList<LoaderWrapper> mLoaders = new ArrayList<>();
    private long mNativePtr = nativeCreateInstance(this);
    private final Handler mUIHandler = new Handler(Looper.getMainLooper());

    static class LoaderWrapper {
        final IOverlayTextureLoader mLoader;
        final int mPriority;

        LoaderWrapper(int i, IOverlayTextureLoader iOverlayTextureLoader) {
            this.mPriority = i;
            this.mLoader = iOverlayTextureLoader;
        }
    }

    private static native long nativeCreateInstance(TextureLoaderDispatcher textureLoaderDispatcher);

    private static native void nativeDestroyInstance(long j);

    public TextureLoaderDispatcher(IVPageContext iVPageContext) {
        this.mContext = iVPageContext;
    }

    public synchronized void release() {
        this.mLoaders.clear();
        nativeDestroyInstance(this.mNativePtr);
        this.mNativePtr = 0;
    }

    private TextureWrapper loadTexture(OverlayTextureParam overlayTextureParam) {
        int resID = getResID(overlayTextureParam.uri);
        if (resID != 0) {
            return TextureWrapper.make(this.mContext, resID);
        }
        return null;
    }

    private static int getResID(String str) {
        int i;
        if (str.indexOf(OverlayTextureParam.STATIC_TEXTURE_URI_PREFIX) != 0) {
            return 0;
        }
        try {
            i = Integer.valueOf(str.substring(26)).intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            i = 0;
        }
        return i;
    }

    private boolean loadCustomTexture(final OverlayTextureParam overlayTextureParam, final ICustomTextureObserver iCustomTextureObserver) {
        if (overlayTextureParam.getPageId() != this.mContext.getPageID()) {
            return false;
        }
        this.mUIHandler.post(new Runnable() {
            public void run() {
                synchronized (TextureLoaderDispatcher.this) {
                    Iterator it = TextureLoaderDispatcher.this.mLoaders.iterator();
                    while (it.hasNext()) {
                        LoaderWrapper loaderWrapper = (LoaderWrapper) it.next();
                        if (loaderWrapper.mLoader != null) {
                            TextureWrapper loadTextureData = loaderWrapper.mLoader.loadTextureData(overlayTextureParam);
                            if (loadTextureData != null) {
                                iCustomTextureObserver.onCustomTextureLoaded(loadTextureData);
                                return;
                            }
                        }
                    }
                }
            }
        });
        return true;
    }

    public synchronized void addTextureLoader(IOverlayTextureLoader iOverlayTextureLoader, int i) {
        this.mLoaders.add(new LoaderWrapper(i, iOverlayTextureLoader));
    }

    public synchronized void removeTextureLoader(IOverlayTextureLoader iOverlayTextureLoader) {
        LoaderWrapper loaderWrapper = null;
        Iterator<LoaderWrapper> it = this.mLoaders.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            LoaderWrapper next = it.next();
            if (next.mLoader == iOverlayTextureLoader) {
                loaderWrapper = next;
                break;
            }
        }
        this.mLoaders.remove(loaderWrapper);
    }
}
