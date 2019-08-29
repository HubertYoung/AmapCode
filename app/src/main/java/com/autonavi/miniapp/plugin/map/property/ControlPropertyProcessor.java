package com.autonavi.miniapp.plugin.map.property;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.LruCache;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.monitor.track.agent.DefaultTrackAgent;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.MapJsonObj;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.util.ElementProvider;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils.ImgCallback;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ControlPropertyProcessor extends BasePropertyProcessor {
    /* access modifiers changed from: private */
    public SparseArray<Control> controlArray = new SparseArray<>();
    /* access modifiers changed from: private */
    public ElementProvider mElementProvider;
    /* access modifiers changed from: private */
    public LruCache<String, Bitmap> mIconCache = new LruCache<>(10);
    private OnClickListener onControlClickListener = new OnClickListener() {
        public void onClick(View view) {
            if (view.getTag() instanceof Integer) {
                Control control = (Control) ControlPropertyProcessor.this.controlArray.get(((Integer) view.getTag()).intValue());
                H5Page h5Page = (H5Page) ControlPropertyProcessor.this.mPage.get();
                if (h5Page != null && control != null) {
                    H5Bridge bridge = h5Page.getBridge();
                    if (bridge != null) {
                        StringBuilder sb = new StringBuilder("onControlClick ");
                        sb.append(control.id);
                        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb.toString());
                        JSONObject jSONObject = new JSONObject();
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put((String) DefaultTrackAgent.PARAM_CONTROLID, (Object) Integer.valueOf(control.id));
                        jSONObject2.put((String) "element", (Object) ControlPropertyProcessor.this.mElementProvider.getElement());
                        jSONObject.put((String) "data", (Object) jSONObject2);
                        bridge.sendToWeb("nbcomponent.map.bindcontroltap", jSONObject, null);
                    }
                }
            }
        }
    };

    public static class Control implements Serializable {
        public Setting amapSetting;
        public boolean clickable = false;
        public String iconPath;
        public int id;
        public Position position;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Control control = (Control) obj;
            if (this.id != control.id || this.clickable != control.clickable) {
                return false;
            }
            if (this.position == null ? control.position != null : !this.position.equals(control.position)) {
                return false;
            }
            if (this.iconPath != null) {
                return this.iconPath.equals(control.iconPath);
            }
            return control.iconPath == null;
        }
    }

    public static class Setting implements Serializable {
        public boolean lockCameraDegree;
        public LogoPosition logoPosition;
        public int mapMode;
        public int showLogo;
        public int showScale;
    }

    /* access modifiers changed from: protected */
    public void doClear() {
    }

    /* access modifiers changed from: protected */
    public void doDestroy() {
    }

    public ControlPropertyProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView, ElementProvider elementProvider) {
        super(weakReference, weakReference2, adapterTextureMapView);
        this.mElementProvider = elementProvider;
    }

    /* access modifiers changed from: protected */
    public void doProcess(MapJsonObj mapJsonObj) {
        List<Control> list = mapJsonObj.controls;
        if (list != null) {
            final Context context = (Context) this.mContext.get();
            if (context != null) {
                doClear();
                ArrayList<View> arrayList = new ArrayList<>();
                int i = 0;
                while (true) {
                    boolean z = true;
                    if (i >= this.mRealView.getAdapterChildCount()) {
                        break;
                    }
                    View adapterChildAt = this.mRealView.getAdapterChildAt(i);
                    if ((adapterChildAt.getTag() instanceof Integer) && this.controlArray.get(((Integer) adapterChildAt.getTag()).intValue()) != null) {
                        Control control = this.controlArray.get(((Integer) adapterChildAt.getTag()).intValue());
                        Iterator<Control> it = list.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (it.next().equals(control)) {
                                    z = false;
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        if (z) {
                            arrayList.add(adapterChildAt);
                        }
                    }
                    i++;
                }
                StringBuilder sb = new StringBuilder("before remove controlArray ");
                sb.append(this.controlArray);
                AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb.toString());
                for (View view : arrayList) {
                    this.mRealView.removeAdapterView(view);
                    this.controlArray.remove(((Integer) view.getTag()).intValue());
                }
                StringBuilder sb2 = new StringBuilder("after remove controlArray ");
                sb2.append(this.controlArray);
                AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb2.toString());
                for (final Control next : list) {
                    if (next.amapSetting != null) {
                        if (next.amapSetting.logoPosition != null) {
                            this.mRealView.setLogoPosition(convertDp((double) next.amapSetting.logoPosition.centerX), convertDp((double) next.amapSetting.logoPosition.centerY));
                        }
                        this.mRealView.setShowLogo(next.amapSetting.showLogo != 0);
                        this.mRealView.setShowScaleView(next.amapSetting.showScale != 0);
                        this.mRealView.setMapMode(next.amapSetting.mapMode);
                        this.mRealView.setLockCameraDegree(next.amapSetting.lockCameraDegree);
                    } else if (!TextUtils.isEmpty(next.iconPath) && next.position != null && this.controlArray.get(next.id) == null) {
                        final View view2 = new View(context);
                        view2.setTag(Integer.valueOf(next.id));
                        this.controlArray.put(next.id, next);
                        if (next.clickable) {
                            view2.setOnClickListener(this.onControlClickListener);
                        }
                        LayoutParams layoutParams = new LayoutParams(convertDp(next.position.width), convertDp(next.position.height));
                        layoutParams.leftMargin = convertDp(next.position.left);
                        layoutParams.topMargin = convertDp(next.position.top);
                        Bitmap bitmap = this.mIconCache.get(next.iconPath);
                        if (bitmap != null) {
                            handleControlIcon(bitmap, view2, context);
                        } else {
                            H5MapUtils.getImgFromPkg((H5Page) this.mPage.get(), next.iconPath, new ImgCallback() {
                                View tmpView = view2;

                                public void onLoadImage(Bitmap bitmap) {
                                    if (bitmap != null) {
                                        ControlPropertyProcessor.this.mIconCache.put(next.iconPath, bitmap);
                                        ControlPropertyProcessor.this.handleControlIcon(bitmap, this.tmpView, context);
                                    }
                                }
                            });
                        }
                        this.mRealView.addAdapterView(view2, layoutParams);
                    }
                }
                AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "setControls");
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleControlIcon(final Bitmap bitmap, final View view, final Context context) {
        if (bitmap != null) {
            try {
                if ((view.getTag() instanceof Integer) && this.controlArray.get(((Integer) view.getTag()).intValue()) != null) {
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            ControlPropertyProcessor.this.setViewDrawable(view, new BitmapDrawable(context.getResources(), bitmap));
                        }
                    });
                    return;
                }
            } catch (Throwable th) {
                AMapLog.error("infoservice.miniapp", AMapH5EmbedMapView.TAG, th.toString());
                return;
            }
        }
        StringBuilder sb = new StringBuilder("handleControlIcon fail bmp = ");
        sb.append(bitmap);
        sb.append(" v = ");
        sb.append(view);
        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb.toString());
    }

    public void setViewDrawable(View view, Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
