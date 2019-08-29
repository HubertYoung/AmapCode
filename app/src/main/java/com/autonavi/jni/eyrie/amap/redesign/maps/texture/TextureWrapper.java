package com.autonavi.jni.eyrie.amap.redesign.maps.texture;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Rect;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TextureWrapper {
    public float anchorX;
    public float anchorY;
    public List<Area> clickAreas = new ArrayList();
    public byte[] data = null;
    public int engineId = 0;
    public float height = 0.0f;
    boolean isAnchorSetted = false;
    public float scale = 1.0f;
    public List<Area> transparentAreas = new ArrayList();
    public int type = 0;
    public float width = 0.0f;

    public static class Area {
        public int areaId = 0;
        public Rect rect = new Rect();
    }

    private TextureWrapper() {
    }

    public static TextureWrapper make(IVPageContext iVPageContext, int i) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeResource(iVPageContext.getContext().getResources(), i);
        } catch (Throwable th) {
            th.printStackTrace();
            bitmap = null;
        }
        return make(iVPageContext.getEngineID(), bitmap, null, null);
    }

    public static TextureWrapper make(int i, Bitmap bitmap, List<Area> list, List<Area> list2) {
        TextureWrapper textureWrapper = new TextureWrapper();
        if (list != null) {
            textureWrapper.clickAreas.addAll(list);
        }
        if (list2 != null) {
            textureWrapper.transparentAreas.addAll(list2);
        }
        textureWrapper.width = (float) bitmap.getWidth();
        textureWrapper.height = (float) bitmap.getHeight();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
        textureWrapper.data = byteArrayOutputStream.toByteArray();
        textureWrapper.type = 2;
        textureWrapper.scale = 1.0f;
        textureWrapper.engineId = i;
        return textureWrapper;
    }

    public static TextureWrapper make(int i, Bitmap bitmap, List<Area> list, List<Area> list2, float f, float f2) {
        TextureWrapper make = make(i, bitmap, list, list2);
        make.anchorX = f;
        make.anchorY = f2;
        make.isAnchorSetted = true;
        return make;
    }
}
