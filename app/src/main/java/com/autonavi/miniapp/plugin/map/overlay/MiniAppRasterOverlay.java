package com.autonavi.miniapp.plugin.map.overlay;

import android.graphics.Bitmap;
import com.autonavi.jni.ae.gmap.gloverlay.GLRasterOverlay.GLRasterOverlayParam;
import com.autonavi.miniapp.plugin.map.property.GroundOverlaysPropertyProcessor.GroundOverlay;
import com.autonavi.miniapp.plugin.map.property.Point;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureCacheManager;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureFactory;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureIdManager;
import com.autonavi.miniapp.plugin.map.util.TextureIdGenerator;

public class MiniAppRasterOverlay extends BaseRasterMiniAppOverlay {
    public static final int OVERLAY_DRAWLAYER_PRIORITY_AFTER_BUILDING = 80;
    private bty mMapView;
    private MiniAppTextureCacheManager mTextureCacheManager;
    private MiniAppTextureIdManager mTextureIdManager;

    public MiniAppRasterOverlay(bty bty, MiniAppTextureIdManager miniAppTextureIdManager, MiniAppTextureCacheManager miniAppTextureCacheManager) {
        super(bty);
        this.mMapView = bty;
        this.mTextureIdManager = miniAppTextureIdManager;
        this.mTextureCacheManager = miniAppTextureCacheManager;
    }

    public void addRasterOverlay(TextureIdGenerator textureIdGenerator, Bitmap bitmap, GroundOverlay groundOverlay) {
        if (bitmap != null && groundOverlay != null) {
            GLRasterOverlayParam gLRasterOverlayParam = new GLRasterOverlayParam();
            gLRasterOverlayParam.mAlpha = groundOverlay.alpha;
            gLRasterOverlayParam.mVisible = true;
            Point point = groundOverlay.includePoints.get(1);
            Point point2 = groundOverlay.includePoints.get(0);
            gLRasterOverlayParam.mLeftBottom = new alp(point.longitude, point.latitude);
            gLRasterOverlayParam.mRightTop = new alp(point2.longitude, point2.latitude);
            Integer num = (Integer) this.mTextureCacheManager.getTextureCache(this.mMapView, 3, groundOverlay.image);
            if (num != null) {
                gLRasterOverlayParam.mResourceID = num.intValue();
            } else {
                int generateMarkerId = this.mTextureIdManager.generateMarkerId(textureIdGenerator.generate(), getClass());
                if (generateMarkerId >= 0) {
                    gLRasterOverlayParam.mResourceID = generateMarkerId;
                    MiniAppTextureFactory.createRasterTexture(this.mMapView, gLRasterOverlayParam.mResourceID, bitmap);
                    this.mTextureCacheManager.addTextureCache(this.mMapView, 3, groundOverlay.image, Integer.valueOf(gLRasterOverlayParam.mResourceID));
                } else {
                    return;
                }
            }
            addItem(gLRasterOverlayParam);
        }
    }

    public boolean clear() {
        this.mTextureCacheManager.clearTextureCache(this.mMapView, 3);
        return super.clear();
    }
}
