package com.alipay.mobile.embedview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebResourceResponse;
import android.widget.FrameLayout.LayoutParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.zoloz.R;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.apmap.AdapterAMap;
import com.alipay.mobile.apmap.AdapterAMap.OnAdapterCameraChangeListener;
import com.alipay.mobile.apmap.AdapterAMap.OnAdapterInfoWindowClickListener;
import com.alipay.mobile.apmap.AdapterAMap.OnAdapterMapClickListener;
import com.alipay.mobile.apmap.AdapterAMap.OnAdapterMapScreenShotListener;
import com.alipay.mobile.apmap.AdapterAMap.OnAdapterMarkerClickListener;
import com.alipay.mobile.apmap.AdapterCameraUpdateFactory;
import com.alipay.mobile.apmap.AdapterLocationSource;
import com.alipay.mobile.apmap.AdapterLocationSource.OnAdapterLocationChangedListener;
import com.alipay.mobile.apmap.AdapterTextureMapView;
import com.alipay.mobile.apmap.AdapterUiSettings;
import com.alipay.mobile.apmap.model.AdapterBitmapDescriptorFactory;
import com.alipay.mobile.apmap.model.AdapterCameraPosition;
import com.alipay.mobile.apmap.model.AdapterCircleOptions;
import com.alipay.mobile.apmap.model.AdapterLatLng;
import com.alipay.mobile.apmap.model.AdapterLatLngBounds;
import com.alipay.mobile.apmap.model.AdapterMarker;
import com.alipay.mobile.apmap.model.AdapterMarkerOptions;
import com.alipay.mobile.apmap.model.AdapterPolygonOptions;
import com.alipay.mobile.apmap.model.AdapterPolylineOptions;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5plugin.H5LocationPlugin;
import com.alipay.mobile.monitor.track.agent.DefaultTrackAgent;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.IH5EmbedView;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class H5EmbedMapView implements AdapterLocationSource, IH5EmbedView, AMapLocationListener {
    private static DisplayMetrics w;
    ConcurrentHashMap<String, Marker> a = new ConcurrentHashMap<>();
    ConcurrentSkipListSet<Integer> b = new ConcurrentSkipListSet<>();
    SparseArray<Control> c = new SparseArray<>();
    OnAdapterLocationChangedListener d;
    AMapLocationClient e;
    AMapLocationClientOption f;
    AMapLocation g;
    boolean h;
    AdapterMarker i;
    OnAdapterMarkerClickListener j = new OnAdapterMarkerClickListener() {
        public boolean onMarkerClick(AdapterMarker aMarker) {
            Marker marker = H5EmbedMapView.this.a.get(aMarker.getId());
            if (marker == null) {
                return false;
            }
            H5Page h5Page = (H5Page) H5EmbedMapView.this.q.get();
            if (h5Page == null) {
                return false;
            }
            H5Bridge h5Bridge = h5Page.getBridge();
            if (h5Bridge == null) {
                return false;
            }
            LoggerFactory.getTraceLogger().info("H5EmbedMapView", "onMarkerClick " + marker.id);
            JSONObject event = new JSONObject();
            JSONObject data = new JSONObject();
            data.put((String) "markerId", (Object) Integer.valueOf(marker.id));
            data.put((String) "element", (Object) H5Utils.getString(H5Utils.parseObject(H5EmbedMapView.this.s), (String) "element"));
            event.put((String) "data", (Object) data);
            h5Bridge.sendToWeb("nbcomponent.map.bindmarkertap", event, null);
            if (!(marker.title == null && (marker.callout == null || marker.callout.content == null))) {
                aMarker.showInfoWindow();
            }
            return true;
        }
    };
    OnAdapterInfoWindowClickListener k = new OnAdapterInfoWindowClickListener() {
        public void onInfoWindowClick(AdapterMarker aMarker) {
            Marker marker = H5EmbedMapView.this.a.get(aMarker.getId());
            if (marker != null) {
                H5Page h5Page = (H5Page) H5EmbedMapView.this.q.get();
                if (h5Page != null) {
                    H5Bridge h5Bridge = h5Page.getBridge();
                    if (h5Bridge != null) {
                        LoggerFactory.getTraceLogger().info("H5EmbedMapView", "onInfoWindowClick " + marker.id);
                        JSONObject event = new JSONObject();
                        JSONObject data = new JSONObject();
                        data.put((String) "markerId", (Object) Integer.valueOf(marker.id));
                        data.put((String) "element", (Object) H5Utils.getString(H5Utils.parseObject(H5EmbedMapView.this.s), (String) "element"));
                        event.put((String) "data", (Object) data);
                        h5Bridge.sendToWeb("nbcomponent.map.bindcallouttap", event, null);
                    }
                }
            }
        }
    };
    OnClickListener l = new OnClickListener() {
        public void onClick(View v) {
            if (v.getTag() instanceof Integer) {
                Control control = H5EmbedMapView.this.c.get(((Integer) v.getTag()).intValue());
                H5Page h5Page = (H5Page) H5EmbedMapView.this.q.get();
                if (h5Page != null && control != null) {
                    H5Bridge h5Bridge = h5Page.getBridge();
                    if (h5Bridge != null) {
                        LoggerFactory.getTraceLogger().info("H5EmbedMapView", "onControlClick " + control.id);
                        JSONObject event = new JSONObject();
                        JSONObject data = new JSONObject();
                        data.put((String) DefaultTrackAgent.PARAM_CONTROLID, (Object) Integer.valueOf(control.id));
                        data.put((String) "element", (Object) H5Utils.getString(H5Utils.parseObject(H5EmbedMapView.this.s), (String) "element"));
                        event.put((String) "data", (Object) data);
                        h5Bridge.sendToWeb("nbcomponent.map.bindcontroltap", event, null);
                    }
                }
            }
        }
    };
    OnAdapterMapClickListener m = new OnAdapterMapClickListener() {
        public void onMapClick(AdapterLatLng latLng) {
            H5Page h5Page = (H5Page) H5EmbedMapView.this.q.get();
            if (h5Page != null) {
                H5Bridge h5Bridge = h5Page.getBridge();
                if (h5Bridge != null) {
                    LoggerFactory.getTraceLogger().info("H5EmbedMapView", "onTapClick");
                    JSONObject event = new JSONObject();
                    JSONObject data = new JSONObject();
                    data.put((String) "element", (Object) H5Utils.getString(H5Utils.parseObject(H5EmbedMapView.this.s), (String) "element"));
                    event.put((String) "data", (Object) data);
                    h5Bridge.sendToWeb("nbcomponent.map.bindtap", event, null);
                }
            }
        }
    };
    OnAdapterCameraChangeListener n = new OnAdapterCameraChangeListener() {
        boolean a = false;

        public void onCameraChange(AdapterCameraPosition cameraPosition) {
            H5EmbedMapView.this.a(0.0f);
            H5EmbedMapView.this.a(0.0d, 0.0d);
            H5Page h5Page = (H5Page) H5EmbedMapView.this.q.get();
            if (h5Page == null) {
                this.a = true;
                return;
            }
            H5Bridge h5Bridge = h5Page.getBridge();
            if (h5Bridge != null && !this.a) {
                JSONObject event = new JSONObject();
                JSONObject data = new JSONObject();
                data.put((String) "regionChangedType", (Object) "begin");
                data.put((String) "latitude", (Object) Double.valueOf(cameraPosition.target.getLatitude()));
                data.put((String) "longitude", (Object) Double.valueOf(cameraPosition.target.getLongitude()));
                data.put((String) WidgetType.SCALE, (Object) Float.valueOf(cameraPosition.zoom));
                data.put((String) "element", (Object) H5Utils.getString(H5Utils.parseObject(H5EmbedMapView.this.s), (String) "element"));
                event.put((String) "data", (Object) data);
                h5Bridge.sendToWeb("nbcomponent.map.bindregionchange", event, null);
            }
            this.a = true;
        }

        public void onCameraChangeFinish(AdapterCameraPosition cameraPosition) {
            this.a = false;
            H5Page h5Page = (H5Page) H5EmbedMapView.this.q.get();
            if (h5Page != null) {
                H5Bridge h5Bridge = h5Page.getBridge();
                if (h5Bridge != null) {
                    JSONObject event = new JSONObject();
                    JSONObject data = new JSONObject();
                    data.put((String) "regionChangedType", (Object) "end");
                    data.put((String) "latitude", (Object) Double.valueOf(cameraPosition.target.getLatitude()));
                    data.put((String) "longitude", (Object) Double.valueOf(cameraPosition.target.getLongitude()));
                    data.put((String) WidgetType.SCALE, (Object) Float.valueOf(cameraPosition.zoom));
                    data.put((String) "element", (Object) H5Utils.getString(H5Utils.parseObject(H5EmbedMapView.this.s), (String) "element"));
                    event.put((String) "data", (Object) data);
                    h5Bridge.sendToWeb("nbcomponent.map.bindregionchange", event, null);
                }
                H5EmbedMapView.this.a(cameraPosition.zoom);
                H5EmbedMapView.this.a(cameraPosition.target.getLatitude(), cameraPosition.target.getLongitude());
            }
        }
    };
    private AdapterTextureMapView o;
    /* access modifiers changed from: private */
    public WeakReference<Context> p;
    /* access modifiers changed from: private */
    public WeakReference<H5Page> q;
    /* access modifiers changed from: private */
    public Bitmap r;
    /* access modifiers changed from: private */
    public String s;
    private float t;
    private double u;
    private double v;

    public class Callout implements Serializable {
        public String content;
    }

    public class Circle implements Serializable {
        public String color;
        public String fillColor;
        public double latitude;
        public double longitude;
        public double radius;
        public double strokeWidth;
    }

    public class Control implements Serializable {
        public boolean clickable = false;
        public String iconPath;
        public int id;
        public Position position;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Control control = (Control) o;
            if (this.id != control.id) {
                return false;
            }
            if (this.clickable != control.clickable) {
                return false;
            }
            if (this.position == null ? control.position != null : !this.position.equals(control.position)) {
                return false;
            }
            if (this.iconPath != null) {
                return this.iconPath.equals(control.iconPath);
            }
            if (control.iconPath != null) {
                return false;
            }
            return true;
        }
    }

    class MapData implements Serializable {
        public List<Circle> circles;
        public List<Control> controls;
        @JSONField(name = "include-points")
        public List<Point> includePoints;
        public List<Marker> markers;
        public List<Polygon> polygon;
        public List<Polyline> polyline;

        MapData() {
        }
    }

    public class MapProps implements Serializable {
        public double latitude;
        public double longitude;
        public float scale = 16.0f;
        @JSONField(name = "show-location")
        public boolean showLocation;
    }

    public class Marker implements Serializable {
        public double alpha = 1.0d;
        public double anchorX = 0.5d;
        public double anchorY = 1.0d;
        public Callout callout;
        public int height;
        public String iconPath;
        public int id;
        public double latitude;
        public double longitude;
        public int rotate = 0;
        public String title;
        public int width;
    }

    public class Point implements Serializable {
        public AdapterLatLng latLng;
        public double latitude;
        public double longitude;

        /* access modifiers changed from: 0000 */
        public final AdapterLatLng a() {
            if (this.latLng == null) {
                this.latLng = new AdapterLatLng(this.latitude, this.longitude);
            }
            return this.latLng;
        }
    }

    public class Polygon implements Serializable {
        public String color;
        public String fillColor;
        public List<Point> points;
        public double width;
    }

    public class Polyline implements Serializable {
        public String color;
        public boolean dottedLine = false;
        public List<Point> points;
        public double width;
    }

    public class Position implements Serializable {
        public double height;
        public double left = 0.0d;
        public double top = 0.0d;
        public double width;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position) o;
            if (Double.compare(position.left, this.left) != 0) {
                return false;
            }
            if (Double.compare(position.top, this.top) != 0) {
                return false;
            }
            if (Double.compare(position.width, this.width) != 0) {
                return false;
            }
            if (Double.compare(position.height, this.height) != 0) {
                return false;
            }
            return true;
        }
    }

    public void create(int width, int height) {
        AdapterTextureMapView mapView = new AdapterTextureMapView((Context) this.p.get());
        mapView.setMinimumWidth(width);
        mapView.setMinimumHeight(height);
        AdapterUiSettings uiSettings = mapView.getMap().getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setCompassEnabled(true);
        mapView.onCreate(new Bundle());
        mapView.onResume();
        this.o = mapView;
    }

    public void destory() {
        if (this.o != null) {
            this.o.onDestroy();
            this.o = null;
        }
        if (this.c != null) {
            this.c.clear();
        }
        a(0.0f);
        a(0.0d, 0.0d);
    }

    public void onCreate(Context context, H5Page h5Page) {
        this.p = new WeakReference<>(context);
        this.q = new WeakReference<>(h5Page);
    }

    public View getView(int width, int height, String viewId, String mType, Map<String, String> params) {
        LoggerFactory.getTraceLogger().info("H5EmbedMapView", "getView width " + width + " height " + height + " viewId " + viewId);
        if (this.o == null) {
            create(width, height);
            H5Page h5Page = (H5Page) this.q.get();
            if (h5Page != null) {
                H5Bridge h5Bridge = h5Page.getBridge();
                if (h5Bridge != null) {
                    h5Bridge.sendToWeb("nbcomponent.map.afterrender", null, null);
                }
            }
        }
        return this.o;
    }

    public void onEmbedViewAttachedToWebView(int width, int height, String viewId, String mType, Map<String, String> params) {
        H5Log.d("H5EmbedMapView", "H5EmbedMapView onEmbedViewAttachedToWebView width " + width + " height " + height + " viewId " + viewId);
    }

    public void onEmbedViewDetachedFromWebView(int width, int height, String viewId, String mType, Map<String, String> params) {
        H5Log.d("H5EmbedMapView", "H5EmbedMapView onEmbedViewDetachedFromWebView width " + width + " height " + height + " viewId " + viewId);
    }

    public void onEmbedViewDestory(int width, int height, String viewId, String mType, Map<String, String> params) {
        H5Log.d("H5EmbedMapView", "H5EmbedMapView onEmbedViewDestory width " + width + " height " + height + " viewId " + viewId);
    }

    public void onEmbedViewParamChanged(int width, int height, String viewId, String mType, Map<String, String> params, String[] name, String[] value) {
        H5Log.d("H5EmbedMapView", "H5EmbedMapView onEmbedViewParamChanged width " + width + " height " + height + " viewId " + viewId);
    }

    public void onEmbedViewVisibilityChanged(int width, int height, String viewId, String mType, Map<String, String> params, int reason) {
        H5Log.d("H5EmbedMapView", "H5EmbedMapView onEmbedViewVisibilityChanged width " + width + " height " + height + " viewId " + viewId);
    }

    public void onWebViewResume() {
        H5Log.d("H5EmbedMapView", "H5EmbedMapView onWebViewResume");
        if (this.h && this.e != null) {
            LoggerFactory.getTraceLogger().info("H5EmbedMapView", "onWebViewResume startLocation");
            this.e.startLocation();
        }
    }

    public void onWebViewPause() {
        H5Log.d("H5EmbedMapView", "H5EmbedMapView onWebViewPause");
        if (this.e != null) {
            LoggerFactory.getTraceLogger().info("H5EmbedMapView", "onWebViewPause stopLocation");
            this.e.stopLocation();
        }
    }

    public void onWebViewDestory() {
        H5Log.d("H5EmbedMapView", "H5EmbedMapView onWebViewDestory");
    }

    public View getSpecialRestoreView(int width, int height, String viewId, String mType, Map<String, String> params) {
        LoggerFactory.getTraceLogger().info("H5EmbedMapView", "getSpecialRestoreView width " + width + " height " + height + " viewId " + viewId);
        destory();
        create(width, height);
        a(this.s);
        return this.o;
    }

    public Bitmap getSnapshot(int width, int height, String viewId, String mType, Map<String, String> params) {
        LoggerFactory.getTraceLogger().info("H5EmbedMapView", "triggerPreSnapshot onMapScreenShot");
        return this.r;
    }

    public void triggerPreSnapshot() {
        if (this.o != null) {
            this.o.getMap().getMapScreenShot(new OnAdapterMapScreenShotListener() {
                public void onMapScreenShot(Bitmap bitmap) {
                    H5EmbedMapView.this.r = bitmap;
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance((Context) H5EmbedMapView.this.p.get());
                    Intent intent = new Intent();
                    intent.setAction("embedview.snapshot.complete");
                    localBroadcastManager.sendBroadcast(intent);
                    LoggerFactory.getTraceLogger().info("H5EmbedMapView", "triggerPreSnapshot onMapScreenShot");
                }
            });
        }
    }

    public void onReceivedMessage(String actionType, JSONObject data, H5BridgeContext bridgeContext) {
        if (actionType.equals("getCenterLocation")) {
            a(bridgeContext);
        } else if (actionType.equals("moveToLocation")) {
            moveToLocation(data, bridgeContext);
        }
    }

    private void a(H5BridgeContext bridgeContext) {
        if (this.o != null) {
            AdapterCameraPosition cameraPosition = this.o.getMap().getCameraPosition();
            if (cameraPosition != null && cameraPosition.target != null) {
                JSONObject args = new JSONObject();
                args.put((String) "longitude", (Object) Double.valueOf(cameraPosition.target.getLongitude()));
                args.put((String) "latitude", (Object) Double.valueOf(cameraPosition.target.getLatitude()));
                bridgeContext.sendBridgeResult(args);
                LoggerFactory.getTraceLogger().info("H5EmbedMapView", "getCenterLocation " + args.toJSONString());
            }
        }
    }

    public void moveToLocation(JSONObject data, H5BridgeContext bridgeContext) {
        if (this.h && this.g != null && this.o != null) {
            this.o.getMap().animateCamera(AdapterCameraUpdateFactory.changeLatLng(new AdapterLatLng(this.g.getLatitude(), this.g.getLongitude())));
            LoggerFactory.getTraceLogger().info("H5EmbedMapView", "moveToLocation " + this.g.getLatitude() + Token.SEPARATOR + this.g.getLongitude());
        }
    }

    public void onReceivedRender(JSONObject data, H5BridgeContext bridgeContext) {
        String dataJson = data != null ? data.toJSONString() : null;
        if (TextUtils.equals(dataJson, this.s)) {
            b(bridgeContext);
            return;
        }
        this.s = dataJson;
        a(dataJson);
        b(bridgeContext);
    }

    private void b(H5BridgeContext bridgeContext) {
        H5Page h5Page = (H5Page) this.q.get();
        if (h5Page != null) {
            H5Bridge h5Bridge = h5Page.getBridge();
            if (h5Bridge != null) {
                h5Bridge.sendToWeb("nbcomponent.map.afterrender", null, null);
            }
        }
        bridgeContext.sendSuccess();
    }

    private synchronized void a() {
        if (!(this.o == null || this.o.getMap() == null)) {
            this.o.getMap().clear();
            this.i = null;
        }
        this.a.clear();
        this.b.clear();
        LoggerFactory.getTraceLogger().info("H5EmbedMapView", "clear");
    }

    private void a(String dataJson) {
        MapProps mapProps = null;
        MapData mapData = null;
        if (dataJson != null) {
            try {
                mapProps = (MapProps) JSON.parseObject(dataJson, MapProps.class);
            } catch (Throwable t2) {
                LoggerFactory.getTraceLogger().error((String) "H5EmbedMapView", Log.getStackTraceString(t2));
            }
        } else {
            mapProps = null;
        }
        if (dataJson != null) {
            mapData = (MapData) JSON.parseObject(dataJson, MapData.class);
        } else {
            mapData = null;
        }
        if (mapProps != null) {
            a();
            double latitude = mapProps.latitude;
            double longitude = mapProps.longitude;
            float scale = mapProps.scale;
            this.h = mapProps.showLocation;
            LoggerFactory.getTraceLogger().info("H5EmbedMapView", "render latitude " + latitude + " longitude " + longitude + " scale " + scale);
            AdapterAMap aMap = this.o.getMap();
            if (!a(scale, latitude, longitude)) {
                aMap.moveCamera(AdapterCameraUpdateFactory.newLatLngZoom(new AdapterLatLng(latitude, longitude), scale));
            }
            aMap.setOnMapClickListener(this.m);
            aMap.setOnCameraChangeListener(this.n);
            if (this.h && (this.e == null || this.g == null)) {
                a(aMap);
            } else if (this.h && this.g != null) {
                b();
            } else if (!this.h) {
                LoggerFactory.getTraceLogger().info("H5EmbedMapView", "closeLocation");
                deactivate();
            }
            if (mapData != null) {
                a(aMap, mapData.markers);
                a(mapData.controls);
                b(aMap, mapData.polyline);
                c(aMap, mapData.circles);
                d(aMap, mapData.includePoints);
                e(aMap, mapData.polygon);
            }
            a(0.0f);
            a(0.0d, 0.0d);
            return;
        }
        LoggerFactory.getTraceLogger().info("H5EmbedMapView", "mapProps = null");
    }

    private void a(AdapterAMap aMap) {
        LoggerFactory.getTraceLogger().info("H5EmbedMapView", H5LocationPlugin.OPEN_LOCATION);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AdapterAMap.getLocationTypeLocate());
    }

    private void a(AdapterAMap aMap, List<Marker> markers) {
        String str;
        if (markers != null) {
            Context context = (Context) this.p.get();
            if (context != null) {
                for (final Marker marker : markers) {
                    final int markerWidth = (int) convertDp((double) marker.width);
                    final int markerHeight = (int) convertDp((double) marker.height);
                    AdapterMarkerOptions title = new AdapterMarkerOptions().title(marker.title);
                    if (marker.callout == null) {
                        str = "";
                    } else {
                        str = marker.callout.content;
                    }
                    final AdapterMarkerOptions markerOptions = title.snippet(str).position(new AdapterLatLng(marker.latitude, marker.longitude)).anchor(convertAnchor(marker.anchorX), convertAnchor(marker.anchorY));
                    this.b.add(Integer.valueOf(markerOptions.hashCode()));
                    if (!TextUtils.isEmpty(marker.iconPath)) {
                        final AdapterAMap adapterAMap = aMap;
                        a(marker.iconPath, (ResponseListen) new ResponseListen() {
                            Marker a = marker;
                            int b = markerWidth;
                            int c = markerHeight;

                            public void onGetResponse(final WebResourceResponse webResourceResponse) {
                                H5Utils.runOnMain(new Runnable() {
                                    public void run() {
                                        if (webResourceResponse != null) {
                                            H5EmbedMapView.this.a(adapterAMap, markerOptions, AnonymousClass2.this.a, AnonymousClass2.this.b, AnonymousClass2.this.c, BitmapFactory.decodeStream(webResourceResponse.getData()));
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        a(aMap, markerOptions, marker, markerWidth, markerHeight, BitmapFactory.decodeResource(context.getResources(), R.drawable.marker));
                    }
                }
                aMap.setOnMarkerClickListener(this.j);
                aMap.setOnInfoWindowClickListener(this.k);
                LoggerFactory.getTraceLogger().info("H5EmbedMapView", "setMarkers");
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(AdapterAMap aMap, AdapterMarkerOptions markerOptions, Marker marker, int markerWidth, int markerHeight, Bitmap bitmap) {
        if (!(bitmap == null || markerOptions == null)) {
            if (this.b.contains(Integer.valueOf(markerOptions.hashCode()))) {
                if (markerWidth > 0 && markerHeight > 0) {
                    bitmap = resizeBitmap(bitmap, markerWidth, markerHeight);
                }
                if (marker.rotate != 0) {
                    bitmap = rotateBitmap(bitmap, (float) marker.rotate);
                }
                if (marker.alpha != 1.0d) {
                    bitmap = alphaBitmap(bitmap, (int) (marker.alpha * 255.0d));
                }
                markerOptions.icon(AdapterBitmapDescriptorFactory.fromBitmap(bitmap));
                this.a.put(aMap.addMarker(markerOptions).getId(), marker);
            }
        }
        LoggerFactory.getTraceLogger().info("H5EmbedMapView", "handleMarkerIcon fail bmp = " + bitmap);
    }

    private void a(List<Control> controls) {
        if (controls != null) {
            final Context context = (Context) this.p.get();
            if (context != null) {
                List<View> removedView = new ArrayList<>();
                for (int i2 = 0; i2 < this.o.getAdapterChildCount(); i2++) {
                    View v2 = this.o.getAdapterChildAt(i2);
                    if ((v2.getTag() instanceof Integer) && this.c.get(((Integer) v2.getTag()).intValue()) != null) {
                        Control control = this.c.get(((Integer) v2.getTag()).intValue());
                        boolean canRemove = true;
                        Iterator<Control> it = controls.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (it.next().equals(control)) {
                                    canRemove = false;
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        if (canRemove) {
                            removedView.add(v2);
                        }
                    }
                }
                LoggerFactory.getTraceLogger().info("H5EmbedMapView", "before remove controlArray " + this.c);
                for (View v3 : removedView) {
                    this.o.removeAdapterView(v3);
                    this.c.remove(((Integer) v3.getTag()).intValue());
                }
                LoggerFactory.getTraceLogger().info("H5EmbedMapView", "after remove controlArray " + this.c);
                for (Control control2 : controls) {
                    if (!TextUtils.isEmpty(control2.iconPath) && control2.position != null && this.c.get(control2.id) == null) {
                        final View view = new View(context);
                        view.setTag(Integer.valueOf(control2.id));
                        this.c.put(control2.id, control2);
                        if (control2.clickable) {
                            view.setOnClickListener(this.l);
                        }
                        LayoutParams params = new LayoutParams((int) convertDp(control2.position.width), (int) convertDp(control2.position.height));
                        params.leftMargin = (int) convertDp(control2.position.left);
                        params.topMargin = (int) convertDp(control2.position.top);
                        a(control2.iconPath, (ResponseListen) new ResponseListen() {
                            View a = view;

                            public void onGetResponse(WebResourceResponse webResourceResponse) {
                                if (webResourceResponse != null) {
                                    H5EmbedMapView.this.a(webResourceResponse.getData(), this.a, context);
                                }
                            }
                        });
                        this.o.addAdapterView(view, params);
                    }
                }
                LoggerFactory.getTraceLogger().info("H5EmbedMapView", "setControls");
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(InputStream inputStream, final View v2, final Context context) {
        try {
            final Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            if (bmp == null || !(v2.getTag() instanceof Integer) || this.c.get(((Integer) v2.getTag()).intValue()) == null) {
                LoggerFactory.getTraceLogger().info("H5EmbedMapView", "handleControlIcon fail bmp = " + bmp + " v = " + v2);
            } else {
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        H5EmbedMapView.this.setViewDrawable(v2, new BitmapDrawable(context.getResources(), bmp));
                    }
                });
            }
        } catch (Throwable throwable) {
            H5Log.e((String) "H5EmbedMapView", throwable);
        }
        return;
    }

    private void b(AdapterAMap aMap, List<Polyline> polylines) {
        if (polylines != null) {
            for (Polyline polyline : polylines) {
                if (polyline.points != null) {
                    List latLngs = new ArrayList();
                    for (Point point : polyline.points) {
                        latLngs.add(point.a());
                    }
                    AdapterPolylineOptions polylineOptions = new AdapterPolylineOptions().addAll(latLngs);
                    if (polyline.width != -1.0d) {
                        polylineOptions.width((float) convertDp(polyline.width));
                    }
                    polylineOptions.color(b(polyline.color));
                    polylineOptions.setDottedLine(polyline.dottedLine);
                    aMap.addPolyline(polylineOptions);
                }
            }
            LoggerFactory.getTraceLogger().info("H5EmbedMapView", "setPolyline");
        }
    }

    private void c(AdapterAMap aMap, List<Circle> circles) {
        if (circles != null) {
            for (Circle circle : circles) {
                AdapterCircleOptions circleOptions = new AdapterCircleOptions().center(new AdapterLatLng(circle.latitude, circle.longitude));
                circleOptions.strokeColor(b(circle.color));
                circleOptions.fillColor(b(circle.fillColor));
                if (circle.strokeWidth != -1.0d) {
                    circleOptions.strokeWidth((float) convertDp(circle.strokeWidth));
                }
                circleOptions.radius(circle.radius);
                aMap.addCircle(circleOptions);
            }
            LoggerFactory.getTraceLogger().info("H5EmbedMapView", "setCircles");
        }
    }

    private void d(AdapterAMap aMap, List<Point> includePoints) {
        if (includePoints != null && includePoints.size() != 0) {
            if (includePoints.size() == 1) {
                aMap.moveCamera(AdapterCameraUpdateFactory.changeLatLng(includePoints.get(0).a()));
            } else {
                AdapterLatLngBounds builder = new AdapterLatLngBounds();
                for (Point point : includePoints) {
                    builder.include(point.a());
                }
                aMap.moveCamera(AdapterCameraUpdateFactory.newLatLngBounds(builder.build(), (int) convertDp(48.0d)));
            }
            LoggerFactory.getTraceLogger().info("H5EmbedMapView", "setInculdePoints");
        }
    }

    private void e(AdapterAMap aMap, List<Polygon> polygons) {
        if (polygons != null && polygons.size() != 0) {
            for (Polygon polygon : polygons) {
                AdapterPolygonOptions polygonOptions = new AdapterPolygonOptions();
                for (Point point : polygon.points) {
                    polygonOptions.add(point.a());
                }
                if (!TextUtils.isEmpty(polygon.color)) {
                    polygonOptions.strokeColor(b(polygon.color));
                }
                if (polygon.width > 0.0d) {
                    polygonOptions.strokeWidth((float) convertDp(polygon.width));
                }
                if (!TextUtils.isEmpty(polygon.fillColor)) {
                    polygonOptions.fillColor(b(polygon.fillColor));
                }
                aMap.addPolygon(polygonOptions);
            }
            LoggerFactory.getTraceLogger().info("H5EmbedMapView", "setPolygons");
        }
    }

    public void activate(OnAdapterLocationChangedListener onLocationChangedListener) {
        LoggerFactory.getTraceLogger().info("H5EmbedMapView", "activate");
        this.d = onLocationChangedListener;
        Context context = (Context) this.p.get();
        if (context != null && this.e == null) {
            this.e = new AMapLocationClient(context);
            this.f = new AMapLocationClientOption();
            this.e.setLocationListener(this);
            this.f.setLocationMode(AMapLocationMode.Battery_Saving);
            this.e.setLocationOption(this.f);
            this.e.startLocation();
        }
    }

    public void deactivate() {
        LoggerFactory.getTraceLogger().info("H5EmbedMapView", "deactivate");
        this.d = null;
        if (this.e != null) {
            this.e.stopLocation();
            this.e.onDestroy();
        }
        this.e = null;
        if (this.i != null) {
            this.i.remove();
            this.i.destroy();
            this.i = null;
        }
        this.g = null;
    }

    public void onLocationChanged(AMapLocation amapLocation) {
        if (this.d != null && amapLocation != null && ((Context) this.p.get()) != null) {
            if (amapLocation == null || amapLocation.getErrorCode() != 0) {
                LoggerFactory.getTraceLogger().error((String) "H5EmbedMapView", "location failed," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo());
            } else if (this.g == null || this.g.getLatitude() != amapLocation.getLatitude() || this.g.getLongitude() != amapLocation.getLongitude()) {
                this.g = amapLocation;
                b();
            }
        }
    }

    private void b() {
        if (this.g != null) {
            LoggerFactory.getTraceLogger().info("H5EmbedMapView", "setLocation");
            if (this.i == null) {
                AdapterMarkerOptions markerOptions = new AdapterMarkerOptions();
                markerOptions.icon(AdapterBitmapDescriptorFactory.fromResource(R.drawable.location));
                this.i = this.o.getMap().addMarker(markerOptions);
                LoggerFactory.getTraceLogger().info("H5EmbedMapView", "add locationMarker");
            }
            this.i.setPosition(new AdapterLatLng(this.g.getLatitude(), this.g.getLongitude()));
        }
    }

    public void execJavaScript(String s2, IH5EmbedViewJSCallback ih5EmbedViewJSCallback) {
    }

    public void getComponentResourceDataWithUrl(String url, ResponseListen listener, H5Page h5Page) {
    }

    private void a(String path, ResponseListen listener) {
        H5Log.d("H5EmbedMapView", "getImgFromPkg path " + path);
        H5Page h5Page = (H5Page) this.q.get();
        if (h5Page != null) {
            String realPath = a(path, h5Page.getParams());
            H5Log.d("H5EmbedMapView", "getImgFromPkg realPath " + realPath);
            H5Log.d("H5EmbedMapView", "getImgFromPkg offlinepkg1");
            H5Session h5Session = h5Page.getSession();
            if (h5Session != null) {
                H5Log.d("H5EmbedMapView", "getImgFromPkg offlinepkg2");
                H5ContentProvider h5ContentProvider = h5Session.getWebProvider();
                if (h5ContentProvider != null) {
                    H5Log.d("H5EmbedMapView", "getImgFromPkg offlinepkg3");
                    h5ContentProvider.getContentOnUi(realPath, listener);
                    return;
                }
                listener.onGetResponse(null);
                return;
            }
            listener.onGetResponse(null);
        }
    }

    private static String a(String oriUrl, Bundle startParams) {
        String entryUrl = H5Utils.getString(startParams, (String) "url");
        if (!TextUtils.isEmpty(entryUrl)) {
            return H5Utils.getAbsoluteUrlV2(entryUrl, oriUrl, null);
        }
        return null;
    }

    private static int b(String ori) {
        return a(Color.parseColor(ori));
    }

    private static int a(int rgba) {
        return ((rgba & 255) << 24) | ((rgba >> 8) & ViewCompat.MEASURED_SIZE_MASK);
    }

    public static Bitmap rotateBitmap(Bitmap src, float angle) {
        try {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        } catch (Throwable t2) {
            LoggerFactory.getTraceLogger().error((String) "H5EmbedMapView", Log.getStackTraceString(t2));
            return src;
        }
    }

    public Bitmap alphaBitmap(Bitmap src, int alpha) {
        try {
            Bitmap transBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(transBitmap);
            canvas.drawARGB(0, 0, 0, 0);
            Paint paint = new Paint();
            paint.setAlpha(alpha);
            canvas.drawBitmap(src, 0.0f, 0.0f, paint);
            return transBitmap;
        } catch (Throwable t2) {
            LoggerFactory.getTraceLogger().error((String) "H5EmbedMapView", Log.getStackTraceString(t2));
            return src;
        }
    }

    public static Bitmap resizeBitmap(Bitmap realImage, int width, int height) {
        try {
            return Bitmap.createScaledBitmap(realImage, width, height, true);
        } catch (Throwable t2) {
            LoggerFactory.getTraceLogger().error((String) "H5EmbedMapView", Log.getStackTraceString(t2));
            return realImage;
        }
    }

    public void setViewDrawable(View v2, Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            v2.setBackground(drawable);
        } else {
            v2.setBackgroundDrawable(drawable);
        }
    }

    public double convertDp(double dp) {
        return (double) DensityUtil.dip2px((Context) this.p.get(), (float) dp);
    }

    public double convertRpx2Px(double rpx) {
        Context context = (Context) this.p.get();
        if (context == null) {
            return 0.0d;
        }
        return rpx * (((double) getScreenWidth(context)) / 750.0d);
    }

    public float convertAnchor(double anchor) {
        if (anchor < 0.0d) {
            anchor = 0.0d;
        } else if (anchor > 1.0d) {
            anchor = 1.0d;
        }
        return (float) anchor;
    }

    public static int getScreenWidth(Context context) {
        return getMetrics(context).widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return getMetrics(context).heightPixels;
    }

    public static DisplayMetrics getMetrics(Context ctx) {
        if (w == null) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((WindowManager) ctx.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(metrics);
            w = metrics;
        }
        return w;
    }

    private boolean a(float scale, double latitude, double longitude) {
        boolean isScaleEqual;
        boolean isLatitudeEqual;
        boolean isLongitudeEqual;
        if (((double) Math.abs(this.t - scale)) < 1.0E-6d) {
            isScaleEqual = true;
        } else {
            isScaleEqual = false;
        }
        if (Math.abs(this.u - latitude) < 1.0E-6d) {
            isLatitudeEqual = true;
        } else {
            isLatitudeEqual = false;
        }
        if (Math.abs(this.v - longitude) < 1.0E-6d) {
            isLongitudeEqual = true;
        } else {
            isLongitudeEqual = false;
        }
        if (!isScaleEqual || !isLatitudeEqual || !isLongitudeEqual) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void a(float scale) {
        this.t = scale;
    }

    /* access modifiers changed from: private */
    public void a(double latitude, double longitude) {
        this.u = latitude;
        this.v = longitude;
    }
}
