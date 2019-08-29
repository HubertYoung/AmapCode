package com.autonavi.minimap.acanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class ACanvasBridge implements IACanvasBridge {
    private ConcurrentHashMap<String, ACanvasContext2D> mCanvasContext2D = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, ACanvasImage> mCanvasImages = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, WeakReference<ACanvasView>> mCanvasViews = new ConcurrentHashMap<>();
    private IACanvasImageLoader mImageLoader;

    public ACanvasBridge(@NonNull IACanvasImageLoader iACanvasImageLoader) {
        this.mImageLoader = iACanvasImageLoader;
    }

    private ACanvasImage getImage(String str) {
        ACanvasImage aCanvasImage = this.mCanvasImages.get(str);
        if (aCanvasImage == null) {
            aCanvasImage = new ACanvasImage(str);
            ACanvasImage putIfAbsent = this.mCanvasImages.putIfAbsent(str, aCanvasImage);
            if (putIfAbsent != null) {
                return putIfAbsent;
            }
        }
        return aCanvasImage;
    }

    public void bindContext2D(String str, ACanvasView aCanvasView) {
        if (!TextUtils.isEmpty(str)) {
            this.mCanvasViews.put(str, new WeakReference(aCanvasView));
            ACanvasContext2D findContext2D = findContext2D(str);
            if (findContext2D != null) {
                aCanvasView.bindContext2D(findContext2D);
            }
        }
    }

    public synchronized ACanvasContext2D findContext2D(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mCanvasContext2D.get(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0032, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void createContext2D(java.lang.String r2, int r3, int r4, float r5) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0033 }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r1)
            return
        L_0x0009:
            com.autonavi.minimap.acanvas.ACanvasContext2D r0 = r1.findContext2D(r2)     // Catch:{ all -> 0x0033 }
            if (r0 == 0) goto L_0x0012
            r0.destroy()     // Catch:{ all -> 0x0033 }
        L_0x0012:
            com.autonavi.minimap.acanvas.ACanvasContext2D r0 = new com.autonavi.minimap.acanvas.ACanvasContext2D     // Catch:{ all -> 0x0033 }
            r0.<init>(r2, r3, r4, r5)     // Catch:{ all -> 0x0033 }
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.autonavi.minimap.acanvas.ACanvasContext2D> r3 = r1.mCanvasContext2D     // Catch:{ all -> 0x0033 }
            r3.put(r2, r0)     // Catch:{ all -> 0x0033 }
            java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.ref.WeakReference<com.autonavi.minimap.acanvas.ACanvasView>> r3 = r1.mCanvasViews     // Catch:{ all -> 0x0033 }
            java.lang.Object r2 = r3.get(r2)     // Catch:{ all -> 0x0033 }
            java.lang.ref.WeakReference r2 = (java.lang.ref.WeakReference) r2     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x0031
            java.lang.Object r2 = r2.get()     // Catch:{ all -> 0x0033 }
            com.autonavi.minimap.acanvas.ACanvasView r2 = (com.autonavi.minimap.acanvas.ACanvasView) r2     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x0031
            r2.bindContext2D(r0)     // Catch:{ all -> 0x0033 }
        L_0x0031:
            monitor-exit(r1)
            return
        L_0x0033:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.acanvas.ACanvasBridge.createContext2D(java.lang.String, int, int, float):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void destroyContext2D(java.lang.String r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r1)
            return
        L_0x0009:
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.autonavi.minimap.acanvas.ACanvasContext2D> r0 = r1.mCanvasContext2D     // Catch:{ all -> 0x0018 }
            java.lang.Object r2 = r0.remove(r2)     // Catch:{ all -> 0x0018 }
            com.autonavi.minimap.acanvas.ACanvasContext2D r2 = (com.autonavi.minimap.acanvas.ACanvasContext2D) r2     // Catch:{ all -> 0x0018 }
            if (r2 == 0) goto L_0x0016
            r2.destroy()     // Catch:{ all -> 0x0018 }
        L_0x0016:
            monitor-exit(r1)
            return
        L_0x0018:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.acanvas.ACanvasBridge.destroyContext2D(java.lang.String):void");
    }

    public void renderCommand(String str, String str2) {
        ACanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.renderCommand(str2);
        }
    }

    public float measureText(String str, String str2, String str3) {
        ACanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            return findContext2D.measureText(str2, str3);
        }
        return 0.0f;
    }

    public void loadImage(Context context, String str, boolean z, IACanvasImageLoaderCallback iACanvasImageLoaderCallback) {
        this.mImageLoader.load(context, getImage(str), z, iACanvasImageLoaderCallback);
    }

    public void bindImageTexture(String str, int i, Bitmap bitmap) {
        ACanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.bindImageTexture(i, bitmap);
        }
    }

    public void releaseImageTexture(String str, String str2) {
        ACanvasImage image = getImage(str2);
        if (image != null) {
            ACanvasContext2D findContext2D = findContext2D(str);
            if (findContext2D != null) {
                findContext2D.releaseImageTexture(image.getId());
            }
        }
    }

    public void addFpsUpdater(String str, float f, IACanvasFpsUpdater iACanvasFpsUpdater) {
        ACanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.addFpsUpdater(f, iACanvasFpsUpdater);
        }
    }

    public void setCanvasSize(String str, int i, int i2) {
        ACanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.onSizeChanged(i, i2);
        }
    }

    public void setCanvasScale(String str, float f) {
        ACanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.onCanvasScaleChanged(f);
        }
    }

    public synchronized void release() {
        for (Entry<String, ACanvasContext2D> value : this.mCanvasContext2D.entrySet()) {
            ((ACanvasContext2D) value.getValue()).destroy();
        }
        this.mCanvasImages.clear();
        this.mCanvasContext2D.clear();
        this.mCanvasViews.clear();
    }
}
