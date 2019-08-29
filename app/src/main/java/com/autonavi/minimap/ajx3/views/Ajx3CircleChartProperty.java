package com.autonavi.minimap.ajx3.views;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;

public class Ajx3CircleChartProperty extends BaseProperty<Ajx3CircleChart> {
    private static final int DEFAULT_BACKGROUND_COLOR = 13421772;
    private static final int DEFAULT_FONT = 28;
    private static final float DEFAULT_PERCENT = 0.0f;
    private static final int DEFAULT_PROCESS_COLOR = 4362737;
    private static final int DEFAULT_RING_WIDTH = 10;
    private static final String DEFAULT_TEXT = "";
    private static final int DEFAULT_TEXT_COLOR = 16777215;
    private static final String PROPERTY_BACKGROUND_COLOR = "backgroundcolor";
    private static final String PROPERTY_FONT = "font";
    private static final String PROPERTY_HAS_START_POINT = "hasstartpoint";
    private static final String PROPERTY_PERCENT = "percent";
    private static final String PROPERTY_PROCESS_COLOR = "processcolor";
    private static final String PROPERTY_RING_WIDTH = "ringwidth";
    private static final String PROPERTY_START_POINT_COLOR = "startpointcolor";
    private static final String PROPERTY_START_POINT_RADIUS = "startpointradius";
    private static final String PROPERTY_TEXT = "text";
    private static final String PROPERTY_TEXT_COLOR = "textcolor";

    public Ajx3CircleChartProperty(@NonNull Ajx3CircleChart ajx3CircleChart, @NonNull IAjxContext iAjxContext) {
        super(ajx3CircleChart, iAjxContext);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r2, java.lang.Object r3) {
        /*
            r1 = this;
            int r0 = r2.hashCode()
            switch(r0) {
                case -1762762624: goto L_0x0068;
                case -1034019242: goto L_0x005d;
                case -678927291: goto L_0x0053;
                case 3148879: goto L_0x0049;
                case 3556653: goto L_0x003e;
                case 323199382: goto L_0x0033;
                case 1176634805: goto L_0x0027;
                case 1316677365: goto L_0x001d;
                case 1433376904: goto L_0x0013;
                case 1996930644: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0074
        L_0x0009:
            java.lang.String r0 = "processcolor"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0074
            r0 = 2
            goto L_0x0075
        L_0x0013:
            java.lang.String r0 = "hasstartpoint"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0074
            r0 = 7
            goto L_0x0075
        L_0x001d:
            java.lang.String r0 = "backgroundcolor"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0074
            r0 = 3
            goto L_0x0075
        L_0x0027:
            java.lang.String r0 = "startpointcolor"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0074
            r0 = 9
            goto L_0x0075
        L_0x0033:
            java.lang.String r0 = "ringwidth"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0074
            r0 = 6
            goto L_0x0075
        L_0x003e:
            java.lang.String r0 = "text"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0074
            r0 = 0
            goto L_0x0075
        L_0x0049:
            java.lang.String r0 = "font"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0074
            r0 = 5
            goto L_0x0075
        L_0x0053:
            java.lang.String r0 = "percent"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0074
            r0 = 1
            goto L_0x0075
        L_0x005d:
            java.lang.String r0 = "textcolor"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0074
            r0 = 4
            goto L_0x0075
        L_0x0068:
            java.lang.String r0 = "startpointradius"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0074
            r0 = 8
            goto L_0x0075
        L_0x0074:
            r0 = -1
        L_0x0075:
            switch(r0) {
                case 0: goto L_0x00a0;
                case 1: goto L_0x009c;
                case 2: goto L_0x0098;
                case 3: goto L_0x0094;
                case 4: goto L_0x0090;
                case 5: goto L_0x008c;
                case 6: goto L_0x0088;
                case 7: goto L_0x0084;
                case 8: goto L_0x0080;
                case 9: goto L_0x007c;
                default: goto L_0x0078;
            }
        L_0x0078:
            super.updateAttribute(r2, r3)
            return
        L_0x007c:
            r1.updateStartPointColor(r3)
            return
        L_0x0080:
            r1.updateStartPointRadius(r3)
            return
        L_0x0084:
            r1.updateHasStartPoint(r3)
            return
        L_0x0088:
            r1.updateRingWidth(r3)
            return
        L_0x008c:
            r1.updateFont(r3)
            return
        L_0x0090:
            r1.updateTextColor(r3)
            return
        L_0x0094:
            r1.updateBackgroundColor(r3)
            return
        L_0x0098:
            r1.updateProcessColor(r3)
            return
        L_0x009c:
            r1.updatePercent(r3)
            return
        L_0x00a0:
            r1.updateText(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3CircleChartProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateText(Object obj) {
        if (obj == null) {
            ((Ajx3CircleChart) this.mView).setText("");
            return;
        }
        ((Ajx3CircleChart) this.mView).setText((String) obj);
    }

    private void updatePercent(Object obj) {
        if (obj == null) {
            ((Ajx3CircleChart) this.mView).setPercent(0.0f);
            return;
        }
        ((Ajx3CircleChart) this.mView).setPercent(StringUtils.parseFloat((String) obj));
    }

    private void updateProcessColor(Object obj) {
        if (obj == null) {
            ((Ajx3CircleChart) this.mView).setCircleColor(DEFAULT_PROCESS_COLOR);
            return;
        }
        ((Ajx3CircleChart) this.mView).setCircleColor(StringUtils.parseColor((String) obj));
    }

    private void updateBackgroundColor(Object obj) {
        if (obj == null) {
            ((Ajx3CircleChart) this.mView).setCircleBgColor(DEFAULT_BACKGROUND_COLOR);
            return;
        }
        ((Ajx3CircleChart) this.mView).setCircleBgColor(StringUtils.parseColor((String) obj));
    }

    private void updateTextColor(Object obj) {
        if (obj == null) {
            ((Ajx3CircleChart) this.mView).setTextColor(16777215);
            return;
        }
        ((Ajx3CircleChart) this.mView).setTextColor(StringUtils.parseColor((String) obj));
    }

    private void updateFont(Object obj) {
        ((Ajx3CircleChart) this.mView).setTextSize((float) StringUtils.parseStandUnit2Px(((Ajx3CircleChart) this.mView).getContext(), (String) obj, 28));
    }

    private void updateRingWidth(Object obj) {
        ((Ajx3CircleChart) this.mView).setStrokeWidth(StringUtils.parseStandUnit2Px(((Ajx3CircleChart) this.mView).getContext(), (String) obj, 10));
    }

    private void updateHasStartPoint(Object obj) {
        if (obj != null) {
            ((Ajx3CircleChart) this.mView).setHasStartPoint(StringUtils.parseBoolean((String) obj));
        }
    }

    private void updateStartPointRadius(Object obj) {
        if (obj != null) {
            ((Ajx3CircleChart) this.mView).setStartPointRadius(StringUtils.parseStandUnit2Px(((Ajx3CircleChart) this.mView).getContext(), (String) obj));
        }
    }

    private void updateStartPointColor(Object obj) {
        if (obj != null) {
            ((Ajx3CircleChart) this.mView).setStartPointColor(StringUtils.parseColor((String) obj));
        }
    }
}
