package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView;

public class TimePickerProperty extends BaseProperty<TimePickerView> {
    public TimePickerProperty(@NonNull TimePickerView timePickerView, @NonNull IAjxContext iAjxContext) {
        super(timePickerView, iAjxContext);
    }

    /* access modifiers changed from: protected */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r3, java.lang.Object r4) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            switch(r0) {
                case -1345878855: goto L_0x0033;
                case -1268779017: goto L_0x0029;
                case -462926048: goto L_0x001e;
                case 3575610: goto L_0x0013;
                case 111972721: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x003d
        L_0x0008:
            java.lang.String r0 = "value"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x003d
            r0 = 0
            goto L_0x003e
        L_0x0013:
            java.lang.String r0 = "type"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x003d
            r0 = 2
            goto L_0x003e
        L_0x001e:
            java.lang.String r0 = "yearrange"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x003d
            r0 = 1
            goto L_0x003e
        L_0x0029:
            java.lang.String r0 = "format"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x003d
            r0 = 3
            goto L_0x003e
        L_0x0033:
            java.lang.String r0 = "cyclic"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x003d
            r0 = 4
            goto L_0x003e
        L_0x003d:
            r0 = -1
        L_0x003e:
            switch(r0) {
                case 0: goto L_0x0075;
                case 1: goto L_0x0064;
                case 2: goto L_0x0053;
                case 3: goto L_0x0053;
                case 4: goto L_0x0042;
                default: goto L_0x0041;
            }
        L_0x0041:
            goto L_0x0085
        L_0x0042:
            if (r4 == 0) goto L_0x0085
            boolean r0 = r4 instanceof java.lang.String
            if (r0 == 0) goto L_0x0085
            android.view.View r0 = r2.mView
            com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView r0 = (com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView) r0
            r1 = r4
            java.lang.String r1 = (java.lang.String) r1
            r0.updateCyclic(r1)
            goto L_0x0085
        L_0x0053:
            if (r4 == 0) goto L_0x0085
            boolean r0 = r4 instanceof java.lang.String
            if (r0 == 0) goto L_0x0085
            android.view.View r0 = r2.mView
            com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView r0 = (com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView) r0
            r1 = r4
            java.lang.String r1 = (java.lang.String) r1
            r0.updateType(r1)
            goto L_0x0085
        L_0x0064:
            if (r4 == 0) goto L_0x0085
            boolean r0 = r4 instanceof java.lang.String
            if (r0 == 0) goto L_0x0085
            android.view.View r0 = r2.mView
            com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView r0 = (com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView) r0
            r1 = r4
            java.lang.String r1 = (java.lang.String) r1
            r0.updateYearRange(r1)
            goto L_0x0085
        L_0x0075:
            if (r4 == 0) goto L_0x0085
            boolean r0 = r4 instanceof java.lang.String
            if (r0 == 0) goto L_0x0085
            r0 = r4
            java.lang.String r0 = (java.lang.String) r0
            android.view.View r1 = r2.mView
            com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView r1 = (com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView) r1
            r1.updateDate(r0)
        L_0x0085:
            super.updateAttribute(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.TimePickerProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }
}
