package com.autonavi.minimap.acanvas;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.view.Surface;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ACanvasJNI {
    private static native void addFallbackFontFamily(String[] strArr);

    private static native void addFontFamily(String[] strArr, String[] strArr2);

    protected static native void bindImageTexture(long j, int i, Bitmap bitmap);

    protected static native long createContext2D(String str, int i, int i2, float f, IACanvasFpsListener iACanvasFpsListener);

    protected static native long createGlyphLoader();

    protected static native void destroyContext2D(long j);

    protected static native void destroyGlyphLoader(long j);

    public static native String getMagicMirrorVersion();

    public static native void mapBindImageTexture(long j, int i, Bitmap bitmap);

    public static native long mapCreateContext2D(long j, int i, int i2, float f, IACanvasFpsListener iACanvasFpsListener);

    public static native void mapDestroyContext2D(long j);

    public static native float mapMeasureText(long j, String str, String str2);

    public static native void mapReleaseImageTexture(long j, int i);

    public static native void mapRenderCommand(long j, String str);

    protected static native float measureText(long j, String str, String str2);

    protected static native void onCanvasScaleChanged(long j, float f);

    public static native void onMapCanvasScaleChanged(long j, float f);

    public static native void onMapSizeChanged(long j, int i, int i2);

    protected static native void onSizeChanged(long j, int i, int i2);

    protected static native void onSurfaceChanged(long j, Surface surface);

    protected static native void redraw(long j);

    public static native void registerFont(AssetManager assetManager, String str, String str2);

    protected static native void releaseImageTexture(long j, int i);

    protected static native void renderCommand(long j, String str);

    private static native void setFallbackFont(String str, String str2);

    public static native void setLogLevel(int i);

    static {
        try {
            setFontFamilies();
        } catch (Throwable th) {
            ACanvasLog.e("ACanvasJNI", "error when setFontFamilies", th);
        }
    }

    private static void setFontFamilies() {
        FontConfigParser fontConfigParser = new FontConfigParser();
        setFallbackFont(fontConfigParser.getFallbackFont(), fontConfigParser.getSystemFontLocation());
        HashMap<List<String>, List<String>> fontFamilies = fontConfigParser.getFontFamilies();
        if (fontFamilies != null) {
            for (Entry next : fontFamilies.entrySet()) {
                List list = (List) next.getKey();
                List list2 = (List) next.getValue();
                if (!(list == null || list2 == null)) {
                    int size = list.size();
                    String[] strArr = new String[size];
                    for (int i = 0; i < size; i++) {
                        strArr[i] = (String) list.get(i);
                    }
                    int size2 = list2.size();
                    String[] strArr2 = new String[size2];
                    for (int i2 = 0; i2 < size2; i2++) {
                        strArr2[i2] = (String) list2.get(i2);
                    }
                    addFontFamily(strArr, strArr2);
                }
            }
        }
        List<String> fallbackFontsList = fontConfigParser.getFallbackFontsList();
        if (fallbackFontsList != null) {
            int size3 = fallbackFontsList.size();
            String[] strArr3 = new String[size3];
            for (int i3 = 0; i3 < size3; i3++) {
                strArr3[i3] = fallbackFontsList.get(i3);
            }
            addFallbackFontFamily(strArr3);
        }
    }
}
