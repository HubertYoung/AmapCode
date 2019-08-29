package com.autonavi.bundle.uitemplate.mapwidget.widget.ajxtemplate;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.autonavi.bundle.uitemplate.api.IAJXWidgetProperty;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.ajxtemplate.AJXTemplateContainer.OnDataChangeListener;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AJXTemplateMapWidget extends AbstractMapWidget<AJXTemplateWidgetPresenter> {
    /* access modifiers changed from: private */
    public String lastDataChangeCallbackStr;
    private OnDataChangeListener mOnDataChangeListener;
    private OnItemClickListener mOnItemClickListener;

    public AJXTemplateMapWidget(Context context, IWidgetProperty iWidgetProperty) {
        super(context, iWidgetProperty);
    }

    public View createContentView(Context context) {
        if (this.mWidgetProperty == null || !(this.mWidgetProperty instanceof IAJXWidgetProperty)) {
            return null;
        }
        if (this.mContentView != null) {
            refreshState();
            return this.mContentView;
        }
        AJXTemplateContainer aJXTemplateContainer = new AJXTemplateContainer(context);
        aJXTemplateContainer.setData((IAJXWidgetProperty) this.mWidgetProperty);
        aJXTemplateContainer.setItemClickListener(getItemClickListener());
        aJXTemplateContainer.setDataChangeListener(getDataChangeListener());
        this.mWidgetProperty.setOffsetParams(aJXTemplateContainer.getLayoutOffsetParams());
        return aJXTemplateContainer;
    }

    public void refreshState() {
        if (this.mContentView != null && this.mWidgetProperty != null && (this.mWidgetProperty instanceof IAJXWidgetProperty)) {
            this.lastDataChangeCallbackStr = null;
            AJXTemplateContainer aJXTemplateContainer = (AJXTemplateContainer) this.mContentView;
            aJXTemplateContainer.setData((IAJXWidgetProperty) this.mWidgetProperty);
            aJXTemplateContainer.setItemClickListener(getItemClickListener());
            this.mWidgetProperty.setOffsetParams(aJXTemplateContainer.getLayoutOffsetParams());
            aJXTemplateContainer.setDataChangeListener(getDataChangeListener());
        }
    }

    private OnItemClickListener getItemClickListener() {
        if (this.mOnItemClickListener == null) {
            this.mOnItemClickListener = new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    if (AJXTemplateMapWidget.this.mWidgetProperty != null && (AJXTemplateMapWidget.this.mWidgetProperty instanceof IAJXWidgetProperty) && AJXTemplateMapWidget.this.mWidgetProperty.getJsFunctionCallback() != null) {
                        int[] iArr = new int[2];
                        view.getLocationInWindow(iArr);
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("event", "onClick");
                            jSONObject.put("widgetTag", AJXTemplateMapWidget.this.mWidgetProperty.getWidgetType());
                            jSONObject.put("itemTag", ((IAJXWidgetProperty) AJXTemplateMapWidget.this.mWidgetProperty).getWidgetBeans().get(i).a);
                            jSONObject.put("top", (double) DimensionUtils.pixelToStandardUnit((float) iArr[1]));
                            jSONObject.put("bottom", (double) DimensionUtils.pixelToStandardUnit((float) (iArr[1] + view.getMeasuredHeight())));
                            jSONObject.put("left", (double) DimensionUtils.pixelToStandardUnit((float) iArr[0]));
                            jSONObject.put("right", (double) DimensionUtils.pixelToStandardUnit((float) (iArr[0] + view.getMeasuredWidth())));
                        } catch (JSONException unused) {
                        }
                        AJXTemplateMapWidget.this.mWidgetProperty.getJsFunctionCallback().callback(jSONObject.toString());
                    }
                }
            };
        }
        return this.mOnItemClickListener;
    }

    private OnDataChangeListener getDataChangeListener() {
        if (this.mOnDataChangeListener == null) {
            this.mOnDataChangeListener = new OnDataChangeListener() {
                public void onDataChange(List<bel> list) {
                    if (AJXTemplateMapWidget.this.mWidgetProperty != null && (AJXTemplateMapWidget.this.mWidgetProperty instanceof IAJXWidgetProperty) && AJXTemplateMapWidget.this.mWidgetProperty.getJsFunctionCallback() != null && list != null && list.size() != 0) {
                        JSONObject jSONObject = new JSONObject();
                        JSONArray jSONArray = new JSONArray();
                        JSONArray jSONArray2 = new JSONArray();
                        try {
                            jSONObject.put("event", "onItemChange");
                            for (bel next : list) {
                                if (next.h) {
                                    jSONArray.put(next.a);
                                } else {
                                    jSONArray2.put(next.a);
                                }
                            }
                            jSONObject.put("enable", jSONArray);
                            jSONObject.put("unable", jSONArray2);
                        } catch (JSONException unused) {
                        }
                        String jSONObject2 = jSONObject.toString();
                        if (!jSONObject2.equals(AJXTemplateMapWidget.this.lastDataChangeCallbackStr)) {
                            AJXTemplateMapWidget.this.lastDataChangeCallbackStr = jSONObject2;
                            AJXTemplateMapWidget.this.mWidgetProperty.getJsFunctionCallback().callback(jSONObject2);
                        }
                    }
                }
            };
        }
        return this.mOnDataChangeListener;
    }
}
