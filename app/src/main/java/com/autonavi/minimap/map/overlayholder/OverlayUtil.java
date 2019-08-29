package com.autonavi.minimap.map.overlayholder;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayPage.VisiblePolicy;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class OverlayUtil {
    private static final String FAVORITE = "favorite";
    private static final String GPS = "gps";
    private static final String VISIBLE = "visible";
    private static OverlayPageProperty defaultProperty;

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public static void setOverlayProperty(xq xqVar, OvProperty ovProperty) {
        if (xqVar != null && ovProperty != null) {
            xqVar.setVisible(ovProperty.visible());
            xqVar.setClickable(ovProperty.clickable());
            if (PointOverlay.class.isAssignableFrom(xqVar.getClass())) {
                ((PointOverlay) xqVar).setMoveToFocus(ovProperty.moveToFocus());
            }
        }
    }

    public static synchronized OverlayPageProperty getDefaultPageProperty() {
        OverlayPageProperty overlayPageProperty;
        synchronized (OverlayUtil.class) {
            try {
                if (defaultProperty == null) {
                    defaultProperty = generateDefaultProperty();
                }
                overlayPageProperty = defaultProperty;
            }
        }
        return overlayPageProperty;
    }

    private static OverlayPageProperty generateDefaultProperty() {
        return new OverlayPageProperty() {
            public final Class<? extends Annotation> annotationType() {
                return OverlayPageProperty.class;
            }

            public final OvProperty[] overlays() {
                UvOverlay[] values = UvOverlay.values();
                OvProperty[] ovPropertyArr = new OvProperty[values.length];
                for (int i = 0; i < values.length; i++) {
                    ovPropertyArr[i] = OverlayUtil.getOverlayProperty(values[i], values[i] == UvOverlay.GpsOverlay, false, false);
                }
                return ovPropertyArr;
            }
        };
    }

    public static synchronized OverlayPageProperty getAjxPageProperty(String str) {
        synchronized (OverlayUtil.class) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                final JSONObject jSONObject = new JSONObject(str);
                if (!jSONObject.has("gps") && !jSONObject.has(FAVORITE)) {
                    return null;
                }
                AnonymousClass2 r3 = new OverlayPageProperty() {
                    public final Class<? extends Annotation> annotationType() {
                        return OverlayPageProperty.class;
                    }

                    public final OvProperty[] overlays() {
                        ArrayList arrayList = new ArrayList();
                        if (jSONObject.has("gps")) {
                            JSONObject optJSONObject = jSONObject.optJSONObject("gps");
                            if (optJSONObject != null) {
                                arrayList.add(OverlayUtil.getOverlayProperty(UvOverlay.GpsOverlay, optJSONObject.optBoolean(OverlayUtil.VISIBLE, true), false, false));
                            }
                        }
                        if (jSONObject.has(OverlayUtil.FAVORITE)) {
                            JSONObject optJSONObject2 = jSONObject.optJSONObject(OverlayUtil.FAVORITE);
                            if (optJSONObject2 != null) {
                                arrayList.add(OverlayUtil.getOverlayProperty(UvOverlay.SaveOverlay, optJSONObject2.optBoolean(OverlayUtil.VISIBLE, false), false, false));
                            }
                        }
                        return (OvProperty[]) arrayList.toArray(new OvProperty[arrayList.size()]);
                    }
                };
                return r3;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /* access modifiers changed from: private */
    public static OvProperty getOverlayProperty(final UvOverlay uvOverlay, final boolean z, final boolean z2, final boolean z3) {
        return new OvProperty() {
            public final Class<? extends Annotation> annotationType() {
                return OvProperty.class;
            }

            public final boolean visible() {
                return z;
            }

            public final UvOverlay overlay() {
                return uvOverlay;
            }

            public final boolean moveToFocus() {
                return z3;
            }

            public final boolean clickable() {
                return z2;
            }

            public final VisiblePolicy visiblePolicy() {
                return VisiblePolicy.CareConfig;
            }
        };
    }
}
