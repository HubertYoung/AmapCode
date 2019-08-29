package com.autonavi.minimap.ajx3.views;

import android.support.annotation.NonNull;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;

class Ajx3SwitchProperty extends BaseProperty<Ajx3Switch> {
    private static final String EVENT_SWITCH = "switch";
    private static final String PROPERTY_SWITCH = "switch";
    private static final String PROPERTY_SWITCH_ON = "on";
    private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            Ajx3SwitchProperty.this.updateAttribute("on", Boolean.valueOf(z), false, true, false, false);
            long nodeId = Ajx3SwitchProperty.this.mAjxContext.getDomTree().getNodeId(Ajx3SwitchProperty.this.mView);
            Parcel parcel = new Parcel();
            parcel.writeInt(2);
            parcel.writeString("on");
            parcel.writeString(String.valueOf(z));
            Ajx3SwitchProperty.this.mAjxContext.invokeJsEvent(FunctionSupportConfiger.SWITCH_TAG, nodeId, parcel, null);
        }
    };
    private boolean mTrackChecked;

    public Ajx3SwitchProperty(@NonNull Ajx3Switch ajx3Switch, @NonNull IAjxContext iAjxContext) {
        super(ajx3Switch, iAjxContext);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0046  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r5, java.lang.Object r6) {
        /*
            r4 = this;
            int r0 = r5.hashCode()
            r1 = -889473228(0xffffffffcafbb734, float:-8248218.0)
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L_0x001a
            r1 = 3551(0xddf, float:4.976E-42)
            if (r0 == r1) goto L_0x0010
            goto L_0x0025
        L_0x0010:
            java.lang.String r0 = "on"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0025
            r0 = 0
            goto L_0x0026
        L_0x001a:
            java.lang.String r0 = "switch"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0025
            r0 = 1
            goto L_0x0026
        L_0x0025:
            r0 = -1
        L_0x0026:
            switch(r0) {
                case 0: goto L_0x0046;
                case 1: goto L_0x002d;
                default: goto L_0x0029;
            }
        L_0x0029:
            super.updateAttribute(r5, r6)
            return
        L_0x002d:
            if (r6 == 0) goto L_0x003b
            android.view.View r5 = r4.mView
            com.autonavi.minimap.ajx3.views.Ajx3Switch r5 = (com.autonavi.minimap.ajx3.views.Ajx3Switch) r5
            android.widget.CompoundButton$OnCheckedChangeListener r6 = r4.mOnCheckedChangeListener
            r5.setOnCheckedChangeListener(r6)
            r4.mTrackChecked = r2
            return
        L_0x003b:
            android.view.View r5 = r4.mView
            com.autonavi.minimap.ajx3.views.Ajx3Switch r5 = (com.autonavi.minimap.ajx3.views.Ajx3Switch) r5
            r6 = 0
            r5.setOnCheckedChangeListener(r6)
            r4.mTrackChecked = r3
            return
        L_0x0046:
            r4.updateSwitchStatus(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3SwitchProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateSwitchStatus(Object obj) {
        ((Ajx3Switch) this.mView).setOnCheckedChangeListener(null);
        boolean isChecked = ((Ajx3Switch) this.mView).isChecked();
        if (obj instanceof Boolean) {
            isChecked = ((Boolean) obj).booleanValue();
        } else if (obj instanceof String) {
            isChecked = Boolean.valueOf((String) obj).booleanValue();
        }
        if (((Ajx3Switch) this.mView).isChecked() != isChecked) {
            ((Ajx3Switch) this.mView).setChecked(isChecked);
        }
        if (this.mTrackChecked) {
            ((Ajx3Switch) this.mView).setOnCheckedChangeListener(this.mOnCheckedChangeListener);
        }
    }
}
