package com.autonavi.miniapp.plugin.map.overlay;

import android.graphics.Bitmap;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLMapItemMarkerInfo;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureCacheManager;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureFactory;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureIdManager;
import com.autonavi.miniapp.plugin.map.util.TextureIdGenerator;
import com.autonavi.minimap.base.overlay.Marker;
import java.util.ArrayList;
import java.util.List;

public class MiniAppPointOverlay extends BasePointMiniAppOverlay {
    private static final int CACHE_MARKER = 1;
    private static final String TAG = "MiniAppPointOverlay";
    private List<MiniAppPointOverlayItem> fixedItems = new ArrayList();
    private MiniAppPointOverlayItem mItemWithCallout;
    private MiniAppTextureCacheManager mTextureCacheManager;
    private MiniAppTextureIdManager mTextureIdManager;

    public MiniAppPointOverlay(bty bty, MiniAppTextureIdManager miniAppTextureIdManager, MiniAppTextureCacheManager miniAppTextureCacheManager) {
        super(bty);
        this.mTextureIdManager = miniAppTextureIdManager;
        this.mTextureCacheManager = miniAppTextureCacheManager;
    }

    private void resetPointOverlayItems() {
        if (this.mItemWithCallout != null) {
            removeItem(this.mItemWithCallout);
            this.mItemWithCallout = null;
        }
    }

    public boolean isTextureCacheValid(Object obj) {
        return this.mTextureCacheManager.getTextureCache(this.mMapView, 1, obj) != null;
    }

    public void clearCalloutItem() {
        clearCalloutItem(null);
    }

    public void clearCalloutItem(Long l) {
        if (this.mItemWithCallout == null) {
            return;
        }
        if (l == null || this.mItemWithCallout.mItemId == l.longValue()) {
            ((GLPointOverlay) this.mGLOverlay).clearPointItemMarker(this.mItemWithCallout.mItemId);
            this.mItemWithCallout.mClickCalloutMarkerIndex = -1;
            this.mItemWithCallout = null;
        }
    }

    public void showClickCallout(MiniAppPointOverlayItem miniAppPointOverlayItem, Bitmap bitmap) {
        if (miniAppPointOverlayItem != null && miniAppPointOverlayItem.mDefaultMarker != null && miniAppPointOverlayItem.mMiniAppMarker != null && bitmap != null) {
            clearCalloutItem();
            MiniAppTextureFactory.createMarker(9999, bitmap, 9, 0.5f, 1.0f, this.mMapView);
            GLMapItemMarkerInfo gLMapItemMarkerInfo = new GLMapItemMarkerInfo();
            gLMapItemMarkerInfo.markerId = 9999;
            gLMapItemMarkerInfo.isClickable = true;
            float f = miniAppPointOverlayItem.mMiniAppMarker.anchorX;
            float f2 = miniAppPointOverlayItem.mMiniAppMarker.anchorY;
            gLMapItemMarkerInfo.relativeCenterX = (-(f - 0.5f)) * ((float) miniAppPointOverlayItem.mDefaultMarker.mWidth);
            gLMapItemMarkerInfo.relativeCenterY = (-f2) * ((float) miniAppPointOverlayItem.mDefaultMarker.mHeight);
            addItemMarker(miniAppPointOverlayItem, gLMapItemMarkerInfo);
            this.mItemWithCallout = miniAppPointOverlayItem;
            miniAppPointOverlayItem.mClickCalloutMarkerIndex = getItemMarkerCount(miniAppPointOverlayItem) - 1;
        }
    }

    public void showAlwaysCallout(TextureIdGenerator textureIdGenerator, MiniAppPointOverlayItem miniAppPointOverlayItem, Bitmap bitmap, Object obj) {
        Marker marker = (Marker) this.mTextureCacheManager.getTextureCache(this.mMapView, 1, obj);
        if (marker == null) {
            int generateMarkerId = this.mTextureIdManager.generateMarkerId(textureIdGenerator.generate(), getClass());
            if (generateMarkerId < 0) {
                AMapLog.debug("infoservice.miniapp", TAG, "showAlwaysCallout abort, textureId < 0");
                return;
            } else if (bitmap == null) {
                AMapLog.debug("infoservice.miniapp", TAG, "showAlwaysCallout abort, no texture cache and bitmap is null");
                return;
            } else {
                marker = MiniAppTextureFactory.createMarker(generateMarkerId, bitmap, 9, 0.5f, 1.0f, this.mMapView);
                this.mTextureCacheManager.addTextureCache(this.mMapView, 1, obj, marker);
            }
        }
        GLMapItemMarkerInfo gLMapItemMarkerInfo = new GLMapItemMarkerInfo();
        gLMapItemMarkerInfo.markerId = marker.mID;
        gLMapItemMarkerInfo.isClickable = true;
        float f = miniAppPointOverlayItem.mMiniAppMarker.anchorX;
        float f2 = miniAppPointOverlayItem.mMiniAppMarker.anchorY;
        gLMapItemMarkerInfo.relativeCenterX = (-(f - 0.5f)) * ((float) miniAppPointOverlayItem.mDefaultMarker.mWidth);
        gLMapItemMarkerInfo.relativeCenterY = (-f2) * ((float) miniAppPointOverlayItem.mDefaultMarker.mHeight);
        addItemMarker(miniAppPointOverlayItem, gLMapItemMarkerInfo);
    }

    public boolean clear() {
        if (this.fixedItems != null) {
            this.fixedItems.clear();
        }
        resetPointOverlayItems();
        this.mTextureCacheManager.clearTextureCache(this.mMapView, 1);
        return super.clear();
    }

    public void renderFixedPoint() {
        for (MiniAppPointOverlayItem next : this.fixedItems) {
            GeoPoint geoPoint = new GeoPoint();
            try {
                if (!(next.mMiniAppMarker == null || next.mMiniAppMarker.fixedPoint == null)) {
                    GeoPointHD geoPointHD = new GeoPointHD(this.mMapView.c(next.mMiniAppMarker.fixedPoint.originX, next.mMiniAppMarker.fixedPoint.originY));
                    geoPoint.x = geoPointHD.x;
                    geoPoint.y = geoPointHD.y;
                    updateItem(next, geoPoint, next.mAngle);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public MiniAppPointOverlayItem addMarker(TextureIdGenerator textureIdGenerator, MarkerPropertyProcessor.Marker marker, Bitmap bitmap, Object obj) {
        MiniAppPointOverlayItem miniAppPointOverlayItem = new MiniAppPointOverlayItem(new GeoPoint(marker.longitude, marker.latitude));
        if (marker.fixedPoint != null) {
            miniAppPointOverlayItem.mIsFixPoint = true;
            this.fixedItems.add(miniAppPointOverlayItem);
        }
        Marker marker2 = (Marker) this.mTextureCacheManager.getTextureCache(this.mMapView, 1, obj);
        if (marker2 != null) {
            miniAppPointOverlayItem.mDefaultMarker = marker2;
        } else {
            int generateMarkerId = this.mTextureIdManager.generateMarkerId(textureIdGenerator.generate(), getClass());
            if (generateMarkerId < 0) {
                AMapLog.debug("infoservice.miniapp", TAG, "addMarker abort, textureId < 0");
                return null;
            } else if (bitmap == null) {
                AMapLog.debug("infoservice.miniapp", TAG, "addMarker abort, no texture cache and bitmap is null");
                return null;
            } else {
                Marker createMarker = MiniAppTextureFactory.createMarker(generateMarkerId, bitmap, 9, marker.anchorX, marker.anchorY, this.mMapView);
                miniAppPointOverlayItem.mDefaultMarker = createMarker;
                this.mTextureCacheManager.addTextureCache(this.mMapView, 1, obj, createMarker);
            }
        }
        miniAppPointOverlayItem.mMiniAppMarker = marker;
        miniAppPointOverlayItem.mAngleMode = 0;
        addItem(miniAppPointOverlayItem);
        return miniAppPointOverlayItem;
    }

    public MiniAppPointOverlayItem addMiniAppViewMarker(int i, GeoPoint geoPoint) {
        MiniAppPointOverlayItem miniAppPointOverlayItem = new MiniAppPointOverlayItem(geoPoint);
        miniAppPointOverlayItem.mDefaultMarker = MiniAppTextureFactory.createMarker(i, 9, 0.5f, 1.0f, this.mMapView);
        miniAppPointOverlayItem.mMiniAppMarker = new MarkerPropertyProcessor.Marker();
        miniAppPointOverlayItem.mMiniAppMarker.anchorX = 0.5f;
        miniAppPointOverlayItem.mMiniAppMarker.anchorY = 1.0f;
        addItem(miniAppPointOverlayItem);
        return miniAppPointOverlayItem;
    }

    public MiniAppPointOverlayItem preAddItem(MarkerPropertyProcessor.Marker marker) {
        MiniAppPointOverlayItem miniAppPointOverlayItem = new MiniAppPointOverlayItem(new GeoPoint(marker.longitude, marker.latitude));
        if (marker.fixedPoint != null) {
            miniAppPointOverlayItem.mIsFixPoint = true;
            this.fixedItems.add(miniAppPointOverlayItem);
        }
        miniAppPointOverlayItem.mDefaultMarker = new Marker(-999, 4, 0, 0);
        miniAppPointOverlayItem.mMiniAppMarker = marker;
        miniAppPointOverlayItem.mAngleMode = 0;
        addItem(miniAppPointOverlayItem);
        return miniAppPointOverlayItem;
    }

    public MiniAppPointOverlayItem updatePreAddedItem(MiniAppPointOverlayItem miniAppPointOverlayItem, TextureIdGenerator textureIdGenerator, MarkerPropertyProcessor.Marker marker, Bitmap bitmap, Object obj) {
        Marker marker2 = (Marker) this.mTextureCacheManager.getTextureCache(this.mMapView, 1, obj);
        if (marker2 != null) {
            miniAppPointOverlayItem.mDefaultMarker = marker2;
        } else {
            int generateMarkerId = this.mTextureIdManager.generateMarkerId(textureIdGenerator.generate(), getClass());
            if (generateMarkerId < 0) {
                AMapLog.debug("infoservice.miniapp", TAG, "updatePreAddedItem abort, textureId < 0");
                return null;
            } else if (bitmap == null) {
                AMapLog.debug("infoservice.miniapp", TAG, "updatePreAddedItem abort, no texture cache and bitmap is null");
                return null;
            } else {
                Marker createMarker = MiniAppTextureFactory.createMarker(generateMarkerId, bitmap, 9, marker.anchorX, marker.anchorY, this.mMapView);
                miniAppPointOverlayItem.mDefaultMarker = createMarker;
                this.mTextureCacheManager.addTextureCache(this.mMapView, 1, obj, createMarker);
            }
        }
        updateItem(miniAppPointOverlayItem, miniAppPointOverlayItem.mDefaultMarker.mID);
        this.mMapView.R();
        return miniAppPointOverlayItem;
    }
}
