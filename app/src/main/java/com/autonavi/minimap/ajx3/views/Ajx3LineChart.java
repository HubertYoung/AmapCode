package com.autonavi.minimap.ajx3.views;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import com.amap.bundle.commonui.linechart.LineChart;
import com.amap.bundle.commonui.linechart.LineChart.b;
import com.amap.bundle.commonui.linechart.LineChart.c;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"ViewConstructor"})
public class Ajx3LineChart extends LineChart implements ViewExtension {
    private boolean isDataChanged = false;
    /* access modifiers changed from: private */
    public IAjxContext mAjxContext;
    private b mPopAdapter;
    protected final BaseProperty mProperty;
    private List<c> mValueEntities;
    private List<String> mXAxis;
    private List<String> mYAxis;
    private List<Float> mYAxisValue;

    public Ajx3LineChart(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        this.mProperty = new Ajx3LineChartProperty(this, iAjxContext);
        setOnTabListener();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    public void setXAxis(String str) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (jSONArray.length() > 0) {
                if (this.mXAxis == null) {
                    this.mXAxis = new ArrayList();
                }
                this.mXAxis.clear();
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.mXAxis.add(jSONArray.optString(i));
                }
                this.isDataChanged = true;
                syncData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            syncData();
        }
    }

    public void setYAxis(String str) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (jSONArray.length() > 0) {
                if (this.mYAxis == null) {
                    this.mYAxis = new ArrayList();
                }
                if (this.mYAxisValue == null) {
                    this.mYAxisValue = new ArrayList();
                }
                this.mYAxis.clear();
                this.mYAxisValue.clear();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    this.mYAxis.add(optJSONObject.optString("label"));
                    this.mYAxisValue.add(Float.valueOf((float) optJSONObject.optDouble("val")));
                }
                this.isDataChanged = true;
                syncData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            syncData();
        }
    }

    public void setData(String str) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (jSONArray.length() > 0) {
                if (this.mValueEntities == null) {
                    this.mValueEntities = new ArrayList();
                }
                this.mValueEntities.clear();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    int parseColor = StringUtils.parseColor(optJSONObject.optString("lineColor"));
                    int standardUnitToPixel = DimensionUtils.standardUnitToPixel((float) optJSONObject.optInt(MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH));
                    String optString = optJSONObject.optString("label");
                    JSONArray optJSONArray = optJSONObject.optJSONArray("data");
                    ArrayList arrayList = new ArrayList();
                    int i2 = 0;
                    while (optJSONArray != null && i2 < optJSONArray.length()) {
                        arrayList.add(Float.valueOf((float) optJSONArray.optDouble(i2)));
                        i2++;
                    }
                    this.mValueEntities.add(new c(parseColor, standardUnitToPixel, optString, arrayList));
                }
                this.isDataChanged = true;
                syncData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            syncData();
        }
    }

    public void setOnTabListener() {
        this.mPopAdapter = new b() {
            public String getPopString(int i, int i2) {
                long nodeId = Ajx3LineChart.this.mAjxContext.getDomTree().getNodeId(Ajx3LineChart.this);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW, false);
                    jSONObject.put("lineindex", i);
                    jSONObject.put("pointindex", i2);
                    jSONObject.put("detailstr", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Parcel parcel = new Parcel();
                parcel.writeInt(2);
                parcel.writeString("detail");
                parcel.writeString(jSONObject.toString());
                Ajx3LineChart.this.mProperty.updateAttribute("detail", jSONObject.toString(), false, true, false, false);
                Ajx3LineChart.this.mAjxContext.invokeJsEvent("onTab", nodeId, parcel, null);
                return "";
            }
        };
        this.isDataChanged = true;
        syncData();
    }

    private void syncData() {
        if (this.isDataChanged && isDataValid()) {
            setData(this.mXAxis, this.mYAxis, this.mYAxisValue, this.mValueEntities, this.mPopAdapter);
            this.isDataChanged = false;
        }
    }

    private boolean isDataValid() {
        return this.mXAxis != null && !this.mXAxis.isEmpty() && this.mYAxis != null && !this.mYAxis.isEmpty() && this.mYAxisValue != null && !this.mYAxisValue.isEmpty() && this.mValueEntities != null && !this.mValueEntities.isEmpty();
    }
}
