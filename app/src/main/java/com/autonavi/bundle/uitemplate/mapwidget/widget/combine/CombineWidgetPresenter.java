package com.autonavi.bundle.uitemplate.mapwidget.widget.combine;

import android.text.TextUtils;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

public class CombineWidgetPresenter extends BaseMapWidgetPresenter<CombineMapWidget> {
    public <T extends BaseMapWidgetPresenter> T getCombinedPresenter(String str) {
        if (isWidgetNotNull() && !TextUtils.isEmpty(str)) {
            IMapWidget[] combineWidgets = ((CombineMapWidget) this.mBindWidget).getCombineWidgets();
            if (combineWidgets != null) {
                for (IMapWidget iMapWidget : combineWidgets) {
                    if (str.equals(iMapWidget.getWidgetProperty().getWidgetType())) {
                        return (BaseMapWidgetPresenter) iMapWidget.getPresenter();
                    }
                }
            }
        }
        return null;
    }

    public void pageResume(bid bid) {
        processMethod("onPageResume", bid.class, bid);
    }

    public void dispatchAJXEventIfNeeded(int i, View view) {
        if (this.mBindWidget != null && ((CombineMapWidget) this.mBindWidget).getWidgetProperty() != null) {
            JsFunctionCallback jsFunctionCallback = ((CombineMapWidget) this.mBindWidget).getWidgetProperty().getJsFunctionCallback();
            if (jsFunctionCallback != null && !TextUtils.isEmpty(((CombineMapWidget) this.mBindWidget).getWidgetProperty().getWidgetType())) {
                IMapWidget[] combineWidgets = ((CombineMapWidget) this.mBindWidget).getCombineWidgets();
                int[] iArr = new int[2];
                view.getLocationInWindow(iArr);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("event", "onClick");
                    jSONObject.put("widgetTag", ((CombineMapWidget) this.mBindWidget).getWidgetProperty().getWidgetType());
                    jSONObject.put("itemTag", combineWidgets[i].getWidgetProperty().getWidgetType());
                    jSONObject.put("top", (double) DimensionUtils.pixelToStandardUnit((float) iArr[1]));
                    jSONObject.put("bottom", (double) DimensionUtils.pixelToStandardUnit((float) (iArr[1] + view.getMeasuredHeight())));
                    jSONObject.put("left", (double) DimensionUtils.pixelToStandardUnit((float) iArr[0]));
                    jSONObject.put("right", (double) DimensionUtils.pixelToStandardUnit((float) (iArr[0] + view.getMeasuredWidth())));
                } catch (JSONException unused) {
                }
                jsFunctionCallback.callback(jSONObject.toString());
            }
        }
    }

    private void processMethod(String str, Class<?> cls, Object obj) {
        IMapWidget[] combineWidgets;
        if (isWidgetNotNull() && !TextUtils.isEmpty(str) && cls != null && obj != null) {
            for (IMapWidget iMapWidget : ((CombineMapWidget) getWidget()).getCombineWidgets()) {
                if (!(iMapWidget == null || iMapWidget.getPresenter() == null)) {
                    try {
                        Method method = iMapWidget.getPresenter().getClass().getMethod(str, new Class[]{cls});
                        if (method != null) {
                            method.invoke(iMapWidget.getPresenter(), new Object[]{obj});
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
