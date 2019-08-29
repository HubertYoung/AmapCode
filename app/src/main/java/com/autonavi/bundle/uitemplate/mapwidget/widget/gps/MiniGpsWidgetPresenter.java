package com.autonavi.bundle.uitemplate.mapwidget.widget.gps;

import android.text.TextUtils;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MiniGpsWidgetPresenter extends BaseMapWidgetPresenter<MiniGpsMapWidget> {
    private String gpsStateToAjx(int i) {
        switch (i) {
            case 11:
                return Constants.ANIMATOR_NONE;
            case 12:
                return "follow";
            case 13:
                return "followWithHeading";
            default:
                return "";
        }
    }

    public void initialize(MiniGpsMapWidget miniGpsMapWidget) {
        super.initialize(miniGpsMapWidget);
        DoNotUseTool.getSuspendManager().d().a((ced) getGpsMapView());
    }

    public void dispatchAJXEventIfNeeded(int i, View view) {
        if (this.mBindWidget != null && ((MiniGpsMapWidget) this.mBindWidget).getWidgetProperty() != null) {
            JsFunctionCallback jsFunctionCallback = ((MiniGpsMapWidget) this.mBindWidget).getWidgetProperty().getJsFunctionCallback();
            if (jsFunctionCallback != null && !TextUtils.isEmpty(((MiniGpsMapWidget) this.mBindWidget).getWidgetProperty().getWidgetType())) {
                int[] iArr = new int[2];
                view.getLocationInWindow(iArr);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("event", "onClick");
                    jSONObject.put("widgetTag", ((MiniGpsMapWidget) this.mBindWidget).getWidgetProperty().getWidgetType());
                    jSONObject.put("itemTag", ((MiniGpsMapWidget) this.mBindWidget).getWidgetProperty().getWidgetType());
                    jSONObject.put("top", (double) DimensionUtils.pixelToStandardUnit((float) iArr[1]));
                    jSONObject.put("bottom", (double) DimensionUtils.pixelToStandardUnit((float) (iArr[1] + view.getMeasuredHeight())));
                    jSONObject.put("left", (double) DimensionUtils.pixelToStandardUnit((float) iArr[0]));
                    jSONObject.put("right", (double) DimensionUtils.pixelToStandardUnit((float) (iArr[0] + view.getMeasuredWidth())));
                    GpsMapView gpsMapView = getGpsMapView();
                    if (gpsMapView != null) {
                        String gpsStateToAjx = gpsStateToAjx(gpsMapView.getCurGPSBtnState());
                        bez.b("MiniGpsCallback", "gpsState", new bew("gpsSate", gpsStateToAjx));
                        jSONObject.put("state", gpsStateToAjx);
                    }
                } catch (JSONException unused) {
                }
                jsFunctionCallback.callback(jSONObject.toString());
            }
        }
    }

    private GpsMapView getGpsMapView() {
        if (this.mBindWidget != null) {
            return (GpsMapView) ((MiniGpsMapWidget) this.mBindWidget).getContentView();
        }
        return null;
    }
}
