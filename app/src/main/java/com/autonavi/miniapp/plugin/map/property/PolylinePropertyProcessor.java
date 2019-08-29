package com.autonavi.miniapp.plugin.map.property;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Page;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.ComponentJsonObj;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.MapJsonObj;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils.ImgCallback;
import com.autonavi.miniapp.plugin.map.util.TextureIdGenerator;
import com.autonavi.miniapp.plugin.util.MiniAppHelper;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

public class PolylinePropertyProcessor extends BasePropertyProcessor {
    /* access modifiers changed from: private */
    public TextureIdGenerator mTextureIdGenerator = new TextureIdGenerator();
    /* access modifiers changed from: private */
    public int renderId = 0;

    public static class Polyline implements Serializable {
        public String color;
        public List<String> colorList;
        public boolean dottedLine = false;
        public String iconPath;
        public double iconWidth;
        public List<Point> points;
        public double width;
        public int zIndex = -1;
    }

    /* access modifiers changed from: protected */
    public void doDestroy() {
    }

    public PolylinePropertyProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super(weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(MapJsonObj mapJsonObj) {
        innerProcess(mapJsonObj.polyline);
    }

    /* access modifiers changed from: protected */
    public void doProcess(ComponentJsonObj componentJsonObj) {
        innerProcess(componentJsonObj.polyline);
    }

    private void innerProcess(List<Polyline> list) {
        if (this.mRealView != null && list != null) {
            this.renderId++;
            doClear();
            this.mRealView.getLineOverlay().addMiniAppLine(this.mPage, list);
            parsePolylineIconPath(list);
        }
    }

    private void parsePolylineIconPath(List<Polyline> list) {
        final int i = this.renderId;
        for (final Polyline next : list) {
            if (!TextUtils.isEmpty(next.iconPath)) {
                H5MapUtils.getImgFromPkg((H5Page) this.mPage.get(), next.iconPath, new ImgCallback() {
                    public void onLoadImage(Bitmap bitmap) {
                        if (i == PolylinePropertyProcessor.this.renderId && bitmap != null) {
                            PolylinePropertyProcessor.this.mRealView.getLineOverlay().addMiniLineWithIconPath(PolylinePropertyProcessor.this.mTextureIdGenerator, next, MiniAppHelper.rotateBitmap(bitmap, 180.0f));
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doClear() {
        this.mTextureIdGenerator.reset();
        this.mRealView.getLineOverlay().clear();
    }
}
