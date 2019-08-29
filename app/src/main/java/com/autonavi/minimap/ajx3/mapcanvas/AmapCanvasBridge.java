package com.autonavi.minimap.ajx3.mapcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.autonavi.minimap.acanvas.ACanvasImage;
import com.autonavi.minimap.acanvas.ACanvasView;
import com.autonavi.minimap.acanvas.IACanvasBridge;
import com.autonavi.minimap.acanvas.IACanvasFpsUpdater;
import com.autonavi.minimap.acanvas.IACanvasImageLoader;
import com.autonavi.minimap.acanvas.IACanvasImageLoaderCallback;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class AmapCanvasBridge implements IACanvasBridge {
    private ConcurrentHashMap<String, AmapCanvasContext2D> mCanvasContext2D = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, ACanvasImage> mCanvasImages = new ConcurrentHashMap<>();
    private IACanvasImageLoader mImageLoader;

    public void bindContext2D(String str, ACanvasView aCanvasView) {
    }

    public AmapCanvasBridge(@NonNull IACanvasImageLoader iACanvasImageLoader) {
        this.mImageLoader = iACanvasImageLoader;
    }

    private ACanvasImage getImage(String str) {
        ACanvasImage aCanvasImage = this.mCanvasImages.get(str);
        if (aCanvasImage != null) {
            return aCanvasImage;
        }
        ACanvasImage aCanvasImage2 = new ACanvasImage(str);
        this.mCanvasImages.put(str, aCanvasImage2);
        return aCanvasImage2;
    }

    public synchronized AmapCanvasContext2D findContext2D(String str) {
        return this.mCanvasContext2D.get(str);
    }

    public synchronized void createContext2D(String str, int i, int i2, float f) {
        AmapCanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.destroy();
        }
        this.mCanvasContext2D.put(str, new AmapCanvasContext2D(str, i, i2, f));
    }

    public synchronized void destroyContext2D(String str) {
        AmapCanvasContext2D remove = this.mCanvasContext2D.remove(str);
        if (remove != null) {
            remove.destroy();
        }
    }

    public void renderCommand(String str, String str2) {
        AmapCanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.renderCommand(str2);
        }
    }

    public float measureText(String str, String str2, String str3) {
        AmapCanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            return findContext2D.measureText(str2, str3);
        }
        return 0.0f;
    }

    public void loadImage(Context context, String str, boolean z, IACanvasImageLoaderCallback iACanvasImageLoaderCallback) {
        this.mImageLoader.load(context, getImage(str), z, iACanvasImageLoaderCallback);
    }

    public void bindImageTexture(String str, int i, Bitmap bitmap) {
        AmapCanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.bindImageTexture(i, bitmap);
        }
    }

    public void releaseImageTexture(String str, String str2) {
        ACanvasImage image = getImage(str2);
        if (image != null) {
            AmapCanvasContext2D findContext2D = findContext2D(str);
            if (findContext2D != null) {
                findContext2D.releaseImageTexture(image.getId());
            }
        }
    }

    public void addFpsUpdater(String str, float f, IACanvasFpsUpdater iACanvasFpsUpdater) {
        AmapCanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.addFpsUpdater(f, iACanvasFpsUpdater);
        }
    }

    public void setCanvasSize(String str, int i, int i2) {
        AmapCanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.onSizeChanged(i, i2);
        }
    }

    public void setCanvasScale(String str, float f) {
        AmapCanvasContext2D findContext2D = findContext2D(str);
        if (findContext2D != null) {
            findContext2D.onCanvasScaleChanged(f);
        }
    }

    public synchronized void release() {
        for (Entry<String, AmapCanvasContext2D> value : this.mCanvasContext2D.entrySet()) {
            ((AmapCanvasContext2D) value.getValue()).destroy();
        }
        this.mCanvasImages.clear();
        this.mCanvasContext2D.clear();
    }
}
