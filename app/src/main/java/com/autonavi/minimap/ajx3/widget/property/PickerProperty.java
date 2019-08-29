package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import com.alipay.mobile.framework.locale.LocaleHelper;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.widget.view.timepicker.Picker;

public class PickerProperty extends BaseProperty {
    private boolean mHasAnimate = true;
    private boolean mHasChangeListener = false;

    public PickerProperty(@NonNull Picker picker, @NonNull IAjxContext iAjxContext) {
        super(picker, iAjxContext);
    }

    /* access modifiers changed from: protected */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r4, java.lang.Object r5) {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = 1
            r2 = 0
            switch(r0) {
                case -1361636432: goto L_0x0034;
                case -856935711: goto L_0x002a;
                case -838846263: goto L_0x001f;
                case 3076010: goto L_0x0015;
                case 111972721: goto L_0x000a;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x003e
        L_0x000a:
            java.lang.String r0 = "value"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x003e
            r0 = 2
            goto L_0x003f
        L_0x0015:
            java.lang.String r0 = "data"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x003e
            r0 = 0
            goto L_0x003f
        L_0x001f:
            java.lang.String r0 = "update"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x003e
            r0 = 1
            goto L_0x003f
        L_0x002a:
            java.lang.String r0 = "animate"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x003e
            r0 = 4
            goto L_0x003f
        L_0x0034:
            java.lang.String r0 = "change"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x003e
            r0 = 3
            goto L_0x003f
        L_0x003e:
            r0 = -1
        L_0x003f:
            switch(r0) {
                case 0: goto L_0x0078;
                case 1: goto L_0x0068;
                case 2: goto L_0x0058;
                case 3: goto L_0x0051;
                case 4: goto L_0x0043;
                default: goto L_0x0042;
            }
        L_0x0042:
            goto L_0x0088
        L_0x0043:
            boolean r0 = r5 instanceof java.lang.String
            if (r0 == 0) goto L_0x0088
            r0 = r5
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r0)
            r3.mHasAnimate = r0
            goto L_0x0088
        L_0x0051:
            if (r5 == 0) goto L_0x0054
            goto L_0x0055
        L_0x0054:
            r1 = 0
        L_0x0055:
            r3.mHasChangeListener = r1
            return
        L_0x0058:
            if (r5 == 0) goto L_0x0067
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x0067
            android.view.View r4 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.timepicker.Picker r4 = (com.autonavi.minimap.ajx3.widget.view.timepicker.Picker) r4
            java.lang.String r5 = (java.lang.String) r5
            r4.updateSelect(r5)
        L_0x0067:
            return
        L_0x0068:
            if (r5 == 0) goto L_0x0077
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x0077
            android.view.View r4 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.timepicker.Picker r4 = (com.autonavi.minimap.ajx3.widget.view.timepicker.Picker) r4
            java.lang.String r5 = (java.lang.String) r5
            r4.updateData(r5)
        L_0x0077:
            return
        L_0x0078:
            if (r5 == 0) goto L_0x0087
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x0087
            android.view.View r4 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.timepicker.Picker r4 = (com.autonavi.minimap.ajx3.widget.view.timepicker.Picker) r4
            java.lang.String r5 = (java.lang.String) r5
            r4.initPickerData(r5)
        L_0x0087:
            return
        L_0x0088:
            super.updateAttribute(r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.PickerProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    public boolean hasChangeAnimation() {
        return this.mHasAnimate;
    }

    public boolean hasChangeListener() {
        return this.mHasChangeListener;
    }

    public void invokeJSChangeEvent() {
        long nodeId = this.mAjxContext.getDomTree().getNodeId(this.mView);
        if (nodeId != -1) {
            this.mAjxContext.invokeJsEvent(new Builder().setEventName(LocaleHelper.SPKEY_CHANGE_FLAG).setNodeId(nodeId).build());
        }
    }
}
