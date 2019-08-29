package com.autonavi.jni.eyrie.amap.redesign.maps.overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.MeasureSpec;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay.OverlayType;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.IOverlayTextureLoader;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureInfo;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper.Area;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.VMapPage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class BaseLayer implements IOverlayTextureLoader {
    protected IVPageContext mContext;
    protected IDataSource mDataSource;
    protected GPSOverlay mGPSOverlay = null;
    protected List<LineOverlay> mLineOverlays = new ArrayList();
    protected List<PointOverlay> mPointOverlays = new ArrayList();
    protected List<PolygonOverlay> mPolygonOverlays = new ArrayList();
    private final Map<String, Object> mTextureDataMap = new Hashtable();

    interface IOverlayTransval {
        void handle(BaseOverlay baseOverlay);
    }

    /* access modifiers changed from: protected */
    public TextureWrapper loadTexture(OverlayTextureParam overlayTextureParam) {
        return null;
    }

    public BaseLayer(IVPageContext iVPageContext) {
        this.mContext = iVPageContext;
        this.mContext.addLayer(this);
        ((VMapPage) this.mContext).addTextureLoader(this);
    }

    public void setDataSource(IDataSource iDataSource) {
        this.mDataSource = iDataSource;
    }

    public void show() {
        doTransval(new IOverlayTransval() {
            public void handle(BaseOverlay baseOverlay) {
                baseOverlay.setVisible(true);
            }
        });
    }

    public void hide() {
        doTransval(new IOverlayTransval() {
            public void handle(BaseOverlay baseOverlay) {
                baseOverlay.setVisible(false);
            }
        });
    }

    public void clearFocus() {
        for (PointOverlay clearFocus : this.mPointOverlays) {
            clearFocus.clearFocus();
        }
    }

    public void clear() {
        doTransval(new IOverlayTransval() {
            public void handle(BaseOverlay baseOverlay) {
                if (baseOverlay != null) {
                    baseOverlay.clear();
                }
            }
        });
    }

    public void onDestroy() {
        doTransval(new IOverlayTransval() {
            public void handle(BaseOverlay baseOverlay) {
                baseOverlay.onDestroy();
            }
        });
        this.mPointOverlays.clear();
        this.mLineOverlays.clear();
        this.mPolygonOverlays.clear();
        this.mGPSOverlay = null;
        this.mTextureDataMap.clear();
        ((VMapPage) this.mContext).removeTextureLoader(this);
    }

    public void destroyTexture(String str) {
        this.mContext.destroyTexture(str);
    }

    private void doTransval(IOverlayTransval iOverlayTransval) {
        for (PointOverlay handle : this.mPointOverlays) {
            iOverlayTransval.handle(handle);
        }
        for (LineOverlay handle2 : this.mLineOverlays) {
            iOverlayTransval.handle(handle2);
        }
        for (PolygonOverlay handle3 : this.mPolygonOverlays) {
            iOverlayTransval.handle(handle3);
        }
        if (this.mGPSOverlay != null) {
            iOverlayTransval.handle(this.mGPSOverlay);
        }
    }

    /* access modifiers changed from: 0000 */
    public void addPointOverlay(PointOverlay pointOverlay) {
        this.mPointOverlays.add(pointOverlay);
    }

    /* access modifiers changed from: 0000 */
    public void addLineOverlay(LineOverlay lineOverlay) {
        this.mLineOverlays.add(lineOverlay);
    }

    /* access modifiers changed from: 0000 */
    public void addPolygonOverlay(PolygonOverlay polygonOverlay) {
        this.mPolygonOverlays.add(polygonOverlay);
    }

    /* access modifiers changed from: 0000 */
    public void setGPSOverlay(GPSOverlay gPSOverlay) {
        this.mGPSOverlay = gPSOverlay;
    }

    /* access modifiers changed from: 0000 */
    public long createNativeOverlay(OverlayType overlayType, String str) {
        if (this.mContext instanceof VMapPage) {
            return ((VMapPage) this.mContext).createNativeOverlay(overlayType, str);
        }
        return 0;
    }

    public TextureInfo createTextureGetInfo(OverlayTextureParam overlayTextureParam) {
        return this.mContext.createTextureGetInfo(overlayTextureParam);
    }

    public TextureWrapper loadTextureData(OverlayTextureParam overlayTextureParam) {
        overlayTextureParam.data = this.mTextureDataMap.get(overlayTextureParam.uri);
        return loadTexture(overlayTextureParam);
    }

    public OverlayTextureParam makeTextureParam(String str, float f, float f2, Object obj) {
        this.mTextureDataMap.put(str, obj);
        return OverlayTextureParam.make(this.mContext.getPageID(), str, f, f2, false, null);
    }

    public OverlayTextureParam makeTextureParam(int i, float f, float f2) {
        StringBuilder sb = new StringBuilder(OverlayTextureParam.STATIC_TEXTURE_URI_PREFIX);
        sb.append(String.valueOf(i));
        return OverlayTextureParam.make(this.mContext.getPageID(), sb.toString(), f, f2, false, null);
    }

    public OverlayTextureParam makeTextureParam(String str, float f, float f2) {
        return makeTextureParam(str, f, f2, "");
    }

    public OverlayTextureParam makeCustomTextureParam(String str, float f, float f2, Object obj) {
        this.mTextureDataMap.put(str, obj);
        return OverlayTextureParam.make(this.mContext.getPageID(), str, f, f2, true, null);
    }

    public OverlayTextureParam makeCustomTextureParam(String str, float f, float f2) {
        return makeCustomTextureParam(str, f, f2, "");
    }

    public TextureWrapper makeTextureWrapper(int i) {
        return TextureWrapper.make(this.mContext.getEngineID(), BitmapFactory.decodeStream(this.mContext.getContext().getResources().openRawResource(i)), null, null);
    }

    public TextureWrapper makeTextureWrapper(Bitmap bitmap) {
        return TextureWrapper.make(this.mContext.getEngineID(), bitmap, null, null);
    }

    public TextureWrapper makeTextureWrapper(IVPageContext iVPageContext, Bitmap bitmap, List<Area> list) {
        return TextureWrapper.make(this.mContext.getEngineID(), bitmap, list, null);
    }

    private Bitmap viewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public TextureWrapper makeTextureWrapper(View view, List<Area> list) {
        return TextureWrapper.make(this.mContext.getEngineID(), viewToBitmap(view), list, null);
    }

    public TextureWrapper makeTextureWrapper(View view, List<Area> list, float f, float f2) {
        return TextureWrapper.make(this.mContext.getEngineID(), viewToBitmap(view), list, null, f, f2);
    }

    public TextureWrapper makeTextureWrapper(View view) {
        return makeTextureWrapper(view, null);
    }

    public TextureWrapper makeTextureWrapper(View view, float f, float f2) {
        return makeTextureWrapper(view, null, f, f2);
    }
}
