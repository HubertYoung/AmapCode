package com.autonavi.miniapp.plugin.map.property;

import android.content.Context;
import android.graphics.Bitmap;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.mobile.h5container.api.H5Page;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.MapJsonObj;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils.ImgCallback;
import com.autonavi.miniapp.plugin.map.util.TextureIdGenerator;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

public class GroundOverlaysPropertyProcessor extends BasePropertyProcessor {
    /* access modifiers changed from: private */
    public TextureIdGenerator mTextureIdGenerator = new TextureIdGenerator();
    /* access modifiers changed from: private */
    public int renderId = 0;

    public static class GroundOverlay implements Serializable {
        public float alpha;
        public String image;
        @JSONField(name = "include-points")
        public List<Point> includePoints;
        public int zIndex = -1;
    }

    /* access modifiers changed from: protected */
    public void doDestroy() {
    }

    public GroundOverlaysPropertyProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super(weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(MapJsonObj mapJsonObj) {
        List<GroundOverlay> list = mapJsonObj.groundOverlays;
        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "groundOverlays=".concat(String.valueOf(list)));
        if (list != null && this.mRealView != null) {
            this.renderId++;
            final int i = this.renderId;
            doClear();
            for (final GroundOverlay next : list) {
                if (!(next == null || next.image == null)) {
                    H5MapUtils.getImgFromPkg((H5Page) this.mPage.get(), next.image, new ImgCallback() {
                        public void onLoadImage(Bitmap bitmap) {
                            if (bitmap != null && i == GroundOverlaysPropertyProcessor.this.renderId) {
                                GroundOverlaysPropertyProcessor.this.mRealView.getRasterOverlay().addRasterOverlay(GroundOverlaysPropertyProcessor.this.mTextureIdGenerator, bitmap, next);
                            }
                        }
                    });
                }
            }
            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "setGroundOverlays");
        }
    }

    /* access modifiers changed from: protected */
    public void doClear() {
        this.mTextureIdGenerator.reset();
        this.mRealView.getRasterOverlay().clear();
    }
}
