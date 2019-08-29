package com.autonavi.miniapp.plugin.map.property;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.AnimationStatusType;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.OverlayAnimationEvent;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.OverlayAnimationListener;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.ComponentJsonObj;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.MapJsonObj;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.MarkerAnimator;
import com.autonavi.miniapp.plugin.map.action.TranslateMarkerActionProcessor;
import com.autonavi.miniapp.plugin.map.markerstyle.BaseMarkerStyle;
import com.autonavi.miniapp.plugin.map.markerstyle.BaseMarkerStyle.Callback;
import com.autonavi.miniapp.plugin.map.overlay.BasePointMiniAppOverlay.OnItemClickListener;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppPointOverlay;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppPointOverlayItem;
import com.autonavi.miniapp.plugin.map.util.ElementProvider;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils.ImgCallback;
import com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper;
import com.autonavi.miniapp.plugin.map.util.TextureIdGenerator;
import com.autonavi.miniapp.plugin.util.MiniAppHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.DPoint;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MarkerPropertyProcessor extends BasePropertyProcessor {
    private boolean forceCheckHide;
    private Bitmap mDefaultIcon;
    /* access modifiers changed from: private */
    public ElementProvider mElementProvider;
    private SparseArray<HideOnScaleItemsBucket> mHideOnScaleBucketMap = new SparseArray<>();
    private int mLastMapViewHeight;
    private int mLastMapViewWidth;
    /* access modifiers changed from: private */
    public Map<Object, String> mLatestMarkerStatusMap = Collections.synchronizedMap(new HashMap());
    private MarkerCalloutLayoutHelper mMarkerCalloutLayoutHelper = new MarkerCalloutLayoutHelper();
    private OnItemClickListener mOnMarkerClickListener = new OnItemClickListener() {
        public void onItemClick(MiniAppPointOverlayItem miniAppPointOverlayItem, int i) {
            double d;
            double d2;
            if (MarkerPropertyProcessor.this.mRealView != null && MarkerPropertyProcessor.this.mRealView.getMap() != null) {
                H5Page h5Page = (H5Page) MarkerPropertyProcessor.this.mPage.get();
                if (h5Page != null) {
                    H5Bridge bridge = h5Page.getBridge();
                    if (bridge != null) {
                        if (i < 0 || miniAppPointOverlayItem.mClickCalloutMarkerIndex != i) {
                            StringBuilder sb = new StringBuilder("onMarkerClick ");
                            sb.append(miniAppPointOverlayItem.mMiniAppMarker.id);
                            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb.toString());
                            JSONObject jSONObject = new JSONObject();
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put((String) "markerId", miniAppPointOverlayItem.mMiniAppMarker.id);
                            jSONObject2.put((String) "element", (Object) MarkerPropertyProcessor.this.mElementProvider.getElement());
                            if (miniAppPointOverlayItem.mIsFixPoint) {
                                GeoPointHD geoPointHD = new GeoPointHD(MarkerPropertyProcessor.this.mRealView.getMap().c(miniAppPointOverlayItem.mMiniAppMarker.fixedPoint.originX, miniAppPointOverlayItem.mMiniAppMarker.fixedPoint.originY));
                                DPoint a = cfg.a((long) geoPointHD.x, (long) geoPointHD.y);
                                d2 = a.y;
                                d = a.x;
                            } else {
                                d2 = miniAppPointOverlayItem.mGeoPoint.getLatitude();
                                d = miniAppPointOverlayItem.mGeoPoint.getLongitude();
                            }
                            jSONObject2.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(d2)));
                            jSONObject2.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(d)));
                            StringBuilder sb2 = new StringBuilder("bindmarkertp, data.latitude=");
                            sb2.append(d2);
                            sb2.append(",data.longitude=");
                            sb2.append(d);
                            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb2.toString());
                            jSONObject.put((String) "data", (Object) jSONObject2);
                            bridge.sendToWeb("nbcomponent.map.bindmarkertap", jSONObject, null);
                            MarkerPropertyProcessor.this.showClickCallout(miniAppPointOverlayItem.mMiniAppMarker, MarkerPropertyProcessor.this.mRealView.getPointOverlay(), miniAppPointOverlayItem);
                        } else {
                            StringBuilder sb3 = new StringBuilder("onInfoWindowClick ");
                            sb3.append(miniAppPointOverlayItem.mMiniAppMarker.id);
                            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb3.toString());
                            JSONObject jSONObject3 = new JSONObject();
                            JSONObject jSONObject4 = new JSONObject();
                            jSONObject4.put((String) "markerId", miniAppPointOverlayItem.mMiniAppMarker.id);
                            jSONObject4.put((String) "element", (Object) MarkerPropertyProcessor.this.mElementProvider.getElement());
                            jSONObject4.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(miniAppPointOverlayItem.mMiniAppMarker.latitude)));
                            jSONObject4.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(miniAppPointOverlayItem.mMiniAppMarker.longitude)));
                            jSONObject3.put((String) "data", (Object) jSONObject4);
                            bridge.sendToWeb("nbcomponent.map.bindcallouttap", jSONObject3, null);
                        }
                    }
                }
            }
        }
    };
    private TextureIdGenerator mTextureIdGenerator = new TextureIdGenerator();
    /* access modifiers changed from: private */
    public int renderId = 0;

    public static class Callout implements Serializable {
        public static final String CALLOUT_DISPLAY_ALWAYS = "ALWAYS";
        public static final String CALLOUT_DISPLAY_BYCLICK = "BYCLICK";
        public static final String CALLOUT_TEXT_ALIGN_CENTER = "center";
        public static final String CALLOUT_TEXT_ALIGN_LEFT = "left";
        public static final String CALLOUT_TEXT_ALIGN_RIGHT = "right";
        public String bgColor;
        public String borderColor;
        public float borderRadius = 6.0f;
        public int borderWidth;
        public String color;
        public String content;
        public String display;
        public int fontSize = -1;
        public transient String innerTitle;
        public int padding = -1;
        public String textAlign;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Callout callout = (Callout) obj;
            if (this.fontSize != callout.fontSize || Float.compare(callout.borderRadius, this.borderRadius) != 0 || this.borderWidth != callout.borderWidth || this.padding != callout.padding) {
                return false;
            }
            if (this.content == null ? callout.content != null : !this.content.equals(callout.content)) {
                return false;
            }
            if (this.color == null ? callout.color != null : !this.color.equals(callout.color)) {
                return false;
            }
            if (this.borderColor == null ? callout.borderColor != null : !this.borderColor.equals(callout.borderColor)) {
                return false;
            }
            if (this.bgColor == null ? callout.bgColor != null : !this.bgColor.equals(callout.bgColor)) {
                return false;
            }
            if (this.display == null ? callout.display != null : !this.display.equals(callout.display)) {
                return false;
            }
            if (this.textAlign == null ? callout.textAlign != null : !this.textAlign.equals(callout.textAlign)) {
                return false;
            }
            if (this.innerTitle != null) {
                return this.innerTitle.equals(callout.innerTitle);
            }
            return callout.innerTitle == null;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = (((((((((((((((((((this.content != null ? this.content.hashCode() : 0) * 31) + (this.color != null ? this.color.hashCode() : 0)) * 31) + this.fontSize) * 31) + (this.borderRadius != 0.0f ? Float.floatToIntBits(this.borderRadius) : 0)) * 31) + this.borderWidth) * 31) + (this.borderColor != null ? this.borderColor.hashCode() : 0)) * 31) + (this.bgColor != null ? this.bgColor.hashCode() : 0)) * 31) + this.padding) * 31) + (this.display != null ? this.display.hashCode() : 0)) * 31) + (this.textAlign != null ? this.textAlign.hashCode() : 0)) * 31;
            if (this.innerTitle != null) {
                i = this.innerTitle.hashCode();
            }
            return hashCode + i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Callout{content='");
            sb.append(this.content);
            sb.append('\'');
            sb.append(", color='");
            sb.append(this.color);
            sb.append('\'');
            sb.append(", fontSize=");
            sb.append(this.fontSize);
            sb.append(", borderRadius=");
            sb.append(this.borderRadius);
            sb.append(", borderWidth=");
            sb.append(this.borderWidth);
            sb.append(", borderColor='");
            sb.append(this.borderColor);
            sb.append('\'');
            sb.append(", bgColor='");
            sb.append(this.bgColor);
            sb.append('\'');
            sb.append(", padding=");
            sb.append(this.padding);
            sb.append(", display='");
            sb.append(this.display);
            sb.append('\'');
            sb.append(", textAlign='");
            sb.append(this.textAlign);
            sb.append('\'');
            sb.append(", innerTitle='");
            sb.append(this.innerTitle);
            sb.append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public static class Command implements Serializable {
        public List<MarkerAnimItem> markerAnim;
    }

    public static class CustomCallout implements Serializable {
        public List<RichTextInfo> descList;
        public int isShow = 1;
        public String time;
        public int type;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            CustomCallout customCallout = (CustomCallout) obj;
            if (this.type != customCallout.type || this.isShow != customCallout.isShow) {
                return false;
            }
            if (this.time == null ? customCallout.time != null : !this.time.equals(customCallout.time)) {
                return false;
            }
            if (this.descList != null) {
                return this.descList.equals(customCallout.descList);
            }
            return customCallout.descList == null;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((((this.type * 31) + this.isShow) * 31) + (this.time != null ? this.time.hashCode() : 0)) * 31;
            if (this.descList != null) {
                i = this.descList.hashCode();
            }
            return hashCode + i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("CustomCallout{type=");
            sb.append(this.type);
            sb.append(", isShow=");
            sb.append(this.isShow);
            sb.append(", time='");
            sb.append(this.time);
            sb.append('\'');
            sb.append(", descList=");
            sb.append(this.descList);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class FixedPoint implements Serializable {
        public int originX;
        public int originY;
    }

    public static class HideOnScaleItemsBucket {
        public List<MiniAppPointOverlayItem> items = new LinkedList();
        public boolean visible = true;
    }

    public static class Label implements Serializable {
        public String bgColor = "#FFFFFF";
        public int borderRadius = 5;
        public String color = "#000000";
        public String content;
        public int fontSize = 14;
        public int padding = 10;

        public boolean isValid() {
            return !TextUtils.isEmpty(this.content);
        }

        public Callout createCallout() {
            Callout callout = new Callout();
            callout.color = this.color;
            callout.bgColor = this.bgColor;
            callout.fontSize = this.fontSize;
            callout.borderRadius = (float) this.borderRadius;
            callout.padding = this.padding;
            callout.content = this.content;
            callout.display = Callout.CALLOUT_DISPLAY_ALWAYS;
            return callout;
        }
    }

    public static class Marker implements Serializable {
        public double alpha = 1.0d;
        public float anchorX = 0.5f;
        public float anchorY = 1.0f;
        public Callout callout;
        public CustomCallout customCallout;
        public FixedPoint fixedPoint;
        public int height;
        public int hideOnScale;
        public String iconPath;
        public Object id;
        public Label label;
        public double latitude;
        public double longitude;
        public int markerLevel;
        public int rotate = 0;
        public JSONObject style;
        public String title;
        public int width;

        public boolean isHasLabel() {
            return this.label != null && this.label.isValid();
        }

        public String getMarkerCacheKey() {
            StringBuilder sb = new StringBuilder("m|");
            sb.append(this.iconPath);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(this.width);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(this.height);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(this.anchorX);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(this.anchorY);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(this.alpha);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(this.rotate);
            return sb.toString();
        }
    }

    public static class MarkerAnimItem implements Serializable {
        public Object markerId;
        public int type = -1;
    }

    public static class RichTextInfo implements Serializable {
        public String desc;
        public String descColor;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            RichTextInfo richTextInfo = (RichTextInfo) obj;
            if (this.desc == null ? richTextInfo.desc != null : !this.desc.equals(richTextInfo.desc)) {
                return false;
            }
            if (this.descColor != null) {
                return this.descColor.equals(richTextInfo.descColor);
            }
            return richTextInfo.descColor == null;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = (this.desc != null ? this.desc.hashCode() : 0) * 31;
            if (this.descColor != null) {
                i = this.descColor.hashCode();
            }
            return hashCode + i;
        }
    }

    public MarkerPropertyProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView, ElementProvider elementProvider) {
        super(weakReference, weakReference2, adapterTextureMapView);
        this.mElementProvider = elementProvider;
        this.mRealView.getPointOverlay().addOnItemClickListener(this.mOnMarkerClickListener);
    }

    public void checkHideOnScale(boolean z) {
        int w = this.mRealView.getMap().w();
        if (this.forceCheckHide || z) {
            for (int i = 0; i < this.mHideOnScaleBucketMap.size(); i++) {
                int keyAt = this.mHideOnScaleBucketMap.keyAt(i);
                HideOnScaleItemsBucket hideOnScaleItemsBucket = this.mHideOnScaleBucketMap.get(keyAt);
                if (w < keyAt) {
                    if (hideOnScaleItemsBucket.visible) {
                        for (MiniAppPointOverlayItem pointItemVisible : hideOnScaleItemsBucket.items) {
                            this.mRealView.getPointOverlay().setPointItemVisible(pointItemVisible, false);
                        }
                        hideOnScaleItemsBucket.visible = false;
                    }
                } else if (!hideOnScaleItemsBucket.visible) {
                    for (MiniAppPointOverlayItem pointItemVisible2 : hideOnScaleItemsBucket.items) {
                        this.mRealView.getPointOverlay().setPointItemVisible(pointItemVisible2, true);
                    }
                    hideOnScaleItemsBucket.visible = true;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doProcess(MapJsonObj mapJsonObj) {
        innerProcess(mapJsonObj.markers);
    }

    /* access modifiers changed from: protected */
    public void doProcess(ComponentJsonObj componentJsonObj) {
        innerProcess(componentJsonObj.markers);
        handleCommond(componentJsonObj.command);
    }

    private void innerProcess(List<Marker> list) {
        if (list != null) {
            Context context = (Context) this.mContext.get();
            if (context != null && this.mRealView != null) {
                this.renderId++;
                doClear();
                Collections.sort(list, new Comparator<Marker>() {
                    public int compare(Marker marker, Marker marker2) {
                        if (marker.markerLevel > marker2.markerLevel) {
                            return 1;
                        }
                        return marker.markerLevel < marker2.markerLevel ? -1 : 0;
                    }
                });
                initCalloutTitle(list);
                initCalloutByLabel(list);
                final int i = this.renderId;
                for (final Marker next : list) {
                    int convertDp = convertDp((double) next.width);
                    int convertDp2 = convertDp((double) next.height);
                    if (next.style != null) {
                        final JSONObject jSONObject = next.style;
                        BaseMarkerStyle fromJSONObject = BaseMarkerStyle.fromJSONObject(next.style, (H5Page) this.mPage.get(), (Context) this.mContext.get());
                        if (fromJSONObject != null) {
                            fromJSONObject.getBitmap(new Callback() {
                                public void call(Bitmap bitmap, int i) {
                                    if (i == MarkerPropertyProcessor.this.renderId && bitmap != null) {
                                        MarkerPropertyProcessor.this.handleMarkerIcon(MarkerPropertyProcessor.this.mRealView.getPointOverlay(), next, bitmap.getWidth(), bitmap.getHeight(), bitmap, jSONObject);
                                    }
                                }
                            });
                        }
                    } else if (!TextUtils.isEmpty(next.iconPath)) {
                        MiniAppPointOverlayItem miniAppPointOverlayItem = null;
                        if (next.iconPath.startsWith("http")) {
                            miniAppPointOverlayItem = this.mRealView.getPointOverlay().preAddItem(next);
                        }
                        MiniAppPointOverlayItem miniAppPointOverlayItem2 = miniAppPointOverlayItem;
                        String markerCacheKey = next.getMarkerCacheKey();
                        if (this.mRealView.getPointOverlay().isTextureCacheValid(markerCacheKey)) {
                            handleMarkerIcon(this.mRealView.getPointOverlay(), next, convertDp, convertDp2, null, markerCacheKey, miniAppPointOverlayItem2);
                        } else {
                            String str = next.iconPath;
                            final Marker marker = next;
                            final int i2 = convertDp;
                            final int i3 = convertDp2;
                            final int i4 = i;
                            final String str2 = markerCacheKey;
                            final MiniAppPointOverlayItem miniAppPointOverlayItem3 = miniAppPointOverlayItem2;
                            AnonymousClass3 r2 = new ImgCallback() {
                                int tmpHeight = i3;
                                Marker tmpMarker = marker;
                                int tmpWidth = i2;

                                public void onLoadImage(Bitmap bitmap) {
                                    if (i4 == MarkerPropertyProcessor.this.renderId && bitmap != null) {
                                        MarkerPropertyProcessor.this.handleMarkerIcon(MarkerPropertyProcessor.this.mRealView.getPointOverlay(), this.tmpMarker, this.tmpWidth, this.tmpHeight, MarkerPropertyProcessor.this.processMarkerIcon(bitmap, this.tmpMarker, this.tmpWidth, this.tmpHeight), str2, miniAppPointOverlayItem3);
                                    }
                                }
                            };
                            H5MapUtils.getImgFromPkg((H5Page) this.mPage.get(), str, r2);
                        }
                    } else {
                        String markerCacheKey2 = next.getMarkerCacheKey();
                        if (this.mRealView.getPointOverlay().isTextureCacheValid(markerCacheKey2)) {
                            handleMarkerIcon(this.mRealView.getPointOverlay(), next, convertDp, convertDp2, null, markerCacheKey2);
                        } else {
                            if (this.mDefaultIcon == null) {
                                this.mDefaultIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.miniapp_default_marker);
                            }
                            handleMarkerIcon(this.mRealView.getPointOverlay(), next, convertDp, convertDp2, processMarkerIcon(this.mDefaultIcon, next, convertDp, convertDp2), markerCacheKey2);
                        }
                    }
                }
            }
        }
    }

    private void handleCommond(Command command) {
        if (command != null && command.markerAnim != null) {
            boolean z = false;
            MarkerAnimator markerAnimator = new MarkerAnimator(this.mRealView.getPointOverlay());
            for (MarkerAnimItem next : command.markerAnim) {
                if (next != null) {
                    final Object obj = next.markerId;
                    if (this.mLatestMarkerStatusMap == null) {
                        this.mLatestMarkerStatusMap = Collections.synchronizedMap(new HashMap());
                    }
                    String str = this.mLatestMarkerStatusMap.get(obj);
                    if (str == null || str.equals(AnimationStatusType.AnimationStatusTypeNormalEnd.name())) {
                        markerAnimator.processAnimItem(next, new OverlayAnimationListener() {
                            public void onProcessOverlayAnimationEvent(OverlayAnimationEvent overlayAnimationEvent) {
                                if (overlayAnimationEvent != null && overlayAnimationEvent.mStatus != null) {
                                    if (overlayAnimationEvent.mStatus == AnimationStatusType.AnimationStatusTypeForceEnd || overlayAnimationEvent.mStatus == AnimationStatusType.AnimationStatusTypeNormalEnd) {
                                        String name = overlayAnimationEvent.mStatus.name();
                                        if (MarkerPropertyProcessor.this.mLatestMarkerStatusMap == null) {
                                            MarkerPropertyProcessor.this.mLatestMarkerStatusMap = Collections.synchronizedMap(new HashMap());
                                        }
                                        MarkerPropertyProcessor.this.mLatestMarkerStatusMap.put(obj, name);
                                    }
                                }
                            }
                        });
                        z = true;
                    }
                }
            }
            if (z) {
                AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "update animation type, refresh render.");
                this.mRealView.refreshRender();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doClear() {
        this.mTextureIdGenerator.reset();
        this.mRealView.getPointOverlay().clear();
        this.mHideOnScaleBucketMap.clear();
        if (TranslateMarkerActionProcessor.sTranslateMarkerAnimStatusMap != null) {
            TranslateMarkerActionProcessor.sTranslateMarkerAnimStatusMap.clear();
        }
    }

    /* access modifiers changed from: protected */
    public void doDestroy() {
        this.mHideOnScaleBucketMap.clear();
        if (TranslateMarkerActionProcessor.sTranslateMarkerAnimStatusMap != null) {
            TranslateMarkerActionProcessor.sTranslateMarkerAnimStatusMap.clear();
        }
    }

    private void addItemToScaleMonitor(int i, MiniAppPointOverlayItem miniAppPointOverlayItem) {
        this.mRealView.getPointOverlay().setPointItemVisible(miniAppPointOverlayItem, false);
        HideOnScaleItemsBucket hideOnScaleItemsBucket = this.mHideOnScaleBucketMap.get(i);
        if (hideOnScaleItemsBucket == null) {
            hideOnScaleItemsBucket = new HideOnScaleItemsBucket();
            this.mHideOnScaleBucketMap.put(i, hideOnScaleItemsBucket);
        }
        hideOnScaleItemsBucket.items.add(miniAppPointOverlayItem);
        hideOnScaleItemsBucket.visible = false;
        this.forceCheckHide = true;
    }

    private void initCalloutByLabel(List<Marker> list) {
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Marker marker = list.get(i);
                if (marker.isHasLabel()) {
                    marker.callout = marker.label.createCallout();
                    marker.title = null;
                    marker.callout.innerTitle = null;
                }
            }
        }
    }

    private void initCalloutTitle(List<Marker> list) {
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Marker marker = list.get(i);
                if (marker.callout != null && !TextUtils.isEmpty(marker.title)) {
                    marker.callout.innerTitle = marker.title;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public Bitmap processMarkerIcon(Bitmap bitmap, Marker marker, int i, int i2) {
        if (i > 0 && i2 > 0) {
            bitmap = resizeBitmap(bitmap, i, i2);
        }
        if (BigDecimal.valueOf(1).compareTo(BigDecimal.valueOf(marker.alpha)) == 0) {
            return bitmap;
        }
        return alphaBitmap(bitmap, (int) (marker.alpha * 255.0d));
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int i, int i2) {
        try {
            return Bitmap.createScaledBitmap(bitmap, i, i2, true);
        } catch (Throwable th) {
            AMapLog.error("infoservice.miniapp", AMapH5EmbedMapView.TAG, Log.getStackTraceString(th));
            return bitmap;
        }
    }

    private Bitmap alphaBitmap(Bitmap bitmap, int i) {
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            canvas.drawARGB(0, 0, 0, 0);
            Paint paint = new Paint();
            paint.setAlpha(i);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
            return createBitmap;
        } catch (Throwable th) {
            AMapLog.error("infoservice.miniapp", AMapH5EmbedMapView.TAG, Log.getStackTraceString(th));
            return bitmap;
        }
    }

    /* access modifiers changed from: private */
    public void handleMarkerIcon(MiniAppPointOverlay miniAppPointOverlay, Marker marker, int i, int i2, Bitmap bitmap, Object obj) {
        handleMarkerIcon(miniAppPointOverlay, marker, i, i2, bitmap, obj, null);
    }

    /* access modifiers changed from: private */
    public void handleMarkerIcon(MiniAppPointOverlay miniAppPointOverlay, Marker marker, int i, int i2, Bitmap bitmap, Object obj, MiniAppPointOverlayItem miniAppPointOverlayItem) {
        MiniAppPointOverlayItem miniAppPointOverlayItem2;
        Context context = (Context) this.mContext.get();
        if (!(context == null || marker == null || miniAppPointOverlay == null)) {
            handleMarkerFixedPoint(marker, i, i2);
            Bitmap bitmap2 = null;
            if (bitmap != null) {
                bitmap2 = MiniAppHelper.rotateBitmap(bitmap, (float) marker.rotate);
            }
            Bitmap bitmap3 = bitmap2;
            if (miniAppPointOverlayItem != null) {
                miniAppPointOverlayItem2 = miniAppPointOverlay.updatePreAddedItem(miniAppPointOverlayItem, this.mTextureIdGenerator, marker, bitmap3, obj);
            } else {
                miniAppPointOverlayItem2 = miniAppPointOverlay.addMarker(this.mTextureIdGenerator, marker, bitmap3, obj);
            }
            if (miniAppPointOverlayItem2 != null) {
                if (marker.hideOnScale > 0) {
                    addItemToScaleMonitor(marker.hideOnScale, miniAppPointOverlayItem2);
                }
                if (marker.isHasLabel()) {
                    handleAlwaysCallout(context, marker, miniAppPointOverlayItem2);
                    return;
                }
                boolean z = true;
                if (marker.customCallout != null && marker.customCallout.isShow == 1 && marker.customCallout.descList != null && marker.customCallout.descList.size() > 0) {
                    handleCustomCallout(context, marker, miniAppPointOverlayItem2);
                } else if (marker.callout != null && Callout.CALLOUT_DISPLAY_ALWAYS.equals(marker.callout.display)) {
                    if (TextUtils.isEmpty(marker.title) && TextUtils.isEmpty(marker.callout.content)) {
                        z = false;
                    }
                    if (z) {
                        handleAlwaysCallout(context, marker, miniAppPointOverlayItem2);
                    }
                }
            }
        }
    }

    private void handleCustomCallout(Context context, Marker marker, MiniAppPointOverlayItem miniAppPointOverlayItem) {
        CustomCallout customCallout = marker.customCallout;
        if (this.mRealView.getPointOverlay().isTextureCacheValid(customCallout)) {
            this.mRealView.getPointOverlay().showAlwaysCallout(this.mTextureIdGenerator, miniAppPointOverlayItem, null, customCallout);
            return;
        }
        this.mRealView.getPointOverlay().showAlwaysCallout(this.mTextureIdGenerator, miniAppPointOverlayItem, this.mMarkerCalloutLayoutHelper.getMarkerCustomCallout(context, marker), customCallout);
    }

    private void handleAlwaysCallout(Context context, Marker marker, MiniAppPointOverlayItem miniAppPointOverlayItem) {
        Callout callout = marker.callout;
        if (this.mRealView.getPointOverlay().isTextureCacheValid(callout)) {
            this.mRealView.getPointOverlay().showAlwaysCallout(this.mTextureIdGenerator, miniAppPointOverlayItem, null, callout);
            return;
        }
        this.mRealView.getPointOverlay().showAlwaysCallout(this.mTextureIdGenerator, miniAppPointOverlayItem, this.mMarkerCalloutLayoutHelper.getMarkerCallout(context, marker), callout);
    }

    private void handleMarkerFixedPoint(Marker marker, int i, int i2) {
        if (marker != null && marker.fixedPoint != null) {
            int i3 = marker.fixedPoint.originX;
            int i4 = marker.fixedPoint.originY;
            marker.fixedPoint.originX = convertDp((double) i3);
            marker.fixedPoint.originY = convertDp((double) i4);
            clipFixPoint(marker.fixedPoint, (int) (((float) i) * marker.anchorX), (int) (((float) i2) * marker.anchorY));
            GeoPointHD geoPointHD = new GeoPointHD(this.mRealView.getMap().c(marker.fixedPoint.originX, marker.fixedPoint.originY));
            DPoint a = cfg.a((long) geoPointHD.x, (long) geoPointHD.y);
            marker.latitude = a.y;
            marker.longitude = a.x;
        }
    }

    private void clipFixPoint(FixedPoint fixedPoint, int i, int i2) {
        StringBuilder sb = new StringBuilder("clipFixPoint originX = ");
        sb.append(fixedPoint.originX);
        sb.append(" originY =");
        sb.append(fixedPoint.originY);
        sb.append(" markerWidth = ");
        sb.append(i);
        sb.append(" markerHeight");
        sb.append(i2);
        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb.toString());
        if (fixedPoint.originX <= i) {
            fixedPoint.originX = i;
        }
        if (fixedPoint.originY <= i2) {
            fixedPoint.originY = i2;
        }
        int measuredWidth = this.mRealView.getMeasuredWidth();
        int measuredHeight = this.mRealView.getMeasuredHeight();
        StringBuilder sb2 = new StringBuilder("clipFixPoint viewWidth = ");
        sb2.append(measuredWidth);
        sb2.append(" viewHeight =");
        sb2.append(measuredHeight);
        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb2.toString());
        if (!(measuredWidth == 0 || measuredHeight == 0)) {
            this.mLastMapViewWidth = measuredWidth;
            this.mLastMapViewHeight = measuredHeight;
        }
        if (fixedPoint.originX >= this.mLastMapViewWidth) {
            fixedPoint.originX = this.mLastMapViewWidth - i;
        }
        if (fixedPoint.originY >= this.mLastMapViewHeight) {
            fixedPoint.originY = this.mLastMapViewHeight - 5;
        }
    }

    /* access modifiers changed from: private */
    public void showClickCallout(Marker marker, MiniAppPointOverlay miniAppPointOverlay, MiniAppPointOverlayItem miniAppPointOverlayItem) {
        if (marker != null) {
            if (TranslateMarkerActionProcessor.sTranslateMarkerAnimStatusMap == null || miniAppPointOverlayItem == null || TranslateMarkerActionProcessor.sTranslateMarkerAnimStatusMap.get(Long.valueOf(miniAppPointOverlayItem.mItemId)) == null) {
                if (marker.callout == null || TextUtils.isEmpty(marker.callout.display) || Callout.CALLOUT_DISPLAY_BYCLICK.equals(marker.callout.display) || !Callout.CALLOUT_DISPLAY_ALWAYS.equals(marker.callout.display)) {
                    showCallout(marker, miniAppPointOverlay, miniAppPointOverlayItem);
                }
            }
        }
    }

    private void showCallout(Marker marker, MiniAppPointOverlay miniAppPointOverlay, MiniAppPointOverlayItem miniAppPointOverlayItem) {
        if (marker != null && miniAppPointOverlay != null && miniAppPointOverlayItem != null) {
            Context context = (Context) this.mContext.get();
            if (context != null) {
                if (!TextUtils.isEmpty(marker.title) || (marker.callout != null && !TextUtils.isEmpty(marker.callout.content))) {
                    CustomCallout customCallout = marker.customCallout;
                    if ((customCallout == null || customCallout.isShow == 0) && marker.label == null) {
                        miniAppPointOverlay.showClickCallout(miniAppPointOverlayItem, this.mMarkerCalloutLayoutHelper.getMarkerCallout(context, marker));
                    }
                }
            }
        }
    }
}
