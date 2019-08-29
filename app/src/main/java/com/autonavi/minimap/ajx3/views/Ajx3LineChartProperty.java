package com.autonavi.minimap.ajx3.views;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import org.json.JSONException;
import org.json.JSONObject;

public class Ajx3LineChartProperty extends BaseProperty<Ajx3LineChart> {
    private static final String DEFAULT_VALUE = "[]";
    private static final String EVENT_DETAIL = "detail";
    private static final String PROPERTY_DATA = "data";
    private static final String PROPERTY_X_AXIS = "xaxis";
    private static final String PROPERTY_Y_AXIS = "yaxis";
    private String mData;
    private String mXAxis;
    private String mYAxis;

    public Ajx3LineChartProperty(@NonNull Ajx3LineChart ajx3LineChart, @NonNull IAjxContext iAjxContext) {
        super(ajx3LineChart, iAjxContext);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r3, java.lang.Object r4) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -1335224239(0xffffffffb06a1851, float:-8.516326E-10)
            if (r0 == r1) goto L_0x0039
            r1 = 3076010(0x2eefaa, float:4.310408E-39)
            if (r0 == r1) goto L_0x002f
            r1 = 113830937(0x6c8ec19, float:7.557855E-35)
            if (r0 == r1) goto L_0x0024
            r1 = 114754458(0x6d7039a, float:8.0879303E-35)
            if (r0 == r1) goto L_0x0019
            goto L_0x0043
        L_0x0019:
            java.lang.String r0 = "yaxis"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 1
            goto L_0x0044
        L_0x0024:
            java.lang.String r0 = "xaxis"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 0
            goto L_0x0044
        L_0x002f:
            java.lang.String r0 = "data"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 2
            goto L_0x0044
        L_0x0039:
            java.lang.String r0 = "detail"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 3
            goto L_0x0044
        L_0x0043:
            r0 = -1
        L_0x0044:
            switch(r0) {
                case 0: goto L_0x005b;
                case 1: goto L_0x0055;
                case 2: goto L_0x004f;
                case 3: goto L_0x004b;
                default: goto L_0x0047;
            }
        L_0x0047:
            super.updateAttribute(r3, r4)
            return
        L_0x004b:
            r2.updateDetail(r4)
            return
        L_0x004f:
            java.lang.String r4 = (java.lang.String) r4
            r2.updateData(r4)
            return
        L_0x0055:
            java.lang.String r4 = (java.lang.String) r4
            r2.updateYAxis(r4)
            return
        L_0x005b:
            java.lang.String r4 = (java.lang.String) r4
            r2.updateXAxis(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3LineChartProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateDetail(Object obj) {
        if (obj != null) {
            try {
                JSONObject jSONObject = new JSONObject((String) obj);
                if (jSONObject.getBoolean(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW)) {
                    ((Ajx3LineChart) this.mView).showOverLayer(jSONObject.getString("detailstr"), jSONObject.getInt("lineindex"), jSONObject.getInt("pointindex"));
                    return;
                }
                ((Ajx3LineChart) this.mView).dismissOverLayer();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateXAxis(String str) {
        if (str == null) {
            if (!TextUtils.equals(this.mXAxis, DEFAULT_VALUE)) {
                ((Ajx3LineChart) this.mView).setXAxis(DEFAULT_VALUE);
            }
            this.mXAxis = DEFAULT_VALUE;
            return;
        }
        if (!TextUtils.equals(this.mXAxis, str)) {
            ((Ajx3LineChart) this.mView).setXAxis(str);
        }
        this.mXAxis = str;
    }

    private void updateYAxis(String str) {
        if (str == null) {
            if (!TextUtils.equals(this.mYAxis, DEFAULT_VALUE)) {
                ((Ajx3LineChart) this.mView).setYAxis(DEFAULT_VALUE);
            }
            this.mYAxis = DEFAULT_VALUE;
            return;
        }
        if (!TextUtils.equals(this.mYAxis, str)) {
            ((Ajx3LineChart) this.mView).setYAxis(str);
        }
        this.mYAxis = str;
    }

    private void updateData(String str) {
        if (str == null) {
            if (!TextUtils.equals(this.mData, DEFAULT_VALUE)) {
                ((Ajx3LineChart) this.mView).setData(DEFAULT_VALUE);
            }
            this.mData = DEFAULT_VALUE;
            return;
        }
        if (!TextUtils.equals(this.mData, str)) {
            ((Ajx3LineChart) this.mView).setData(str);
        }
        this.mData = str;
    }
}
