package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.widget.view.PointTipView;

public class PointTipViewProperty extends BaseProperty<PointTipView> {
    private boolean mHasClickTip = false;

    public PointTipViewProperty(@NonNull PointTipView pointTipView, @NonNull IAjxContext iAjxContext) {
        super(pointTipView, iAjxContext);
    }

    /* access modifiers changed from: protected */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r4, java.lang.Object r5) {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = 0
            r2 = 1
            switch(r0) {
                case -1224443999: goto L_0x006a;
                case -1216244702: goto L_0x0060;
                case -389150394: goto L_0x0056;
                case 3560141: goto L_0x004b;
                case 3575610: goto L_0x0040;
                case 110327241: goto L_0x0035;
                case 113082767: goto L_0x002a;
                case 747790508: goto L_0x0020;
                case 951530617: goto L_0x0016;
                case 2067279966: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x0075
        L_0x000b:
            java.lang.String r0 = "showTip"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 4
            goto L_0x0076
        L_0x0016:
            java.lang.String r0 = "content"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 3
            goto L_0x0076
        L_0x0020:
            java.lang.String r0 = "onTipClick"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 6
            goto L_0x0076
        L_0x002a:
            java.lang.String r0 = "hasArrow"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 9
            goto L_0x0076
        L_0x0035:
            java.lang.String r0 = "theme"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 7
            goto L_0x0076
        L_0x0040:
            java.lang.String r0 = "type"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 0
            goto L_0x0076
        L_0x004b:
            java.lang.String r0 = "time"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 1
            goto L_0x0076
        L_0x0056:
            java.lang.String r0 = "contentText"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 2
            goto L_0x0076
        L_0x0060:
            java.lang.String r0 = "playJumpAnimation"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 5
            goto L_0x0076
        L_0x006a:
            java.lang.String r0 = "hasTip"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0075
            r0 = 8
            goto L_0x0076
        L_0x0075:
            r0 = -1
        L_0x0076:
            switch(r0) {
                case 0: goto L_0x010c;
                case 1: goto L_0x00fd;
                case 2: goto L_0x00ee;
                case 3: goto L_0x00ee;
                case 4: goto L_0x00ce;
                case 5: goto L_0x00b6;
                case 6: goto L_0x00b2;
                case 7: goto L_0x00a9;
                case 8: goto L_0x0092;
                case 9: goto L_0x007b;
                default: goto L_0x0079;
            }
        L_0x0079:
            goto L_0x012b
        L_0x007b:
            boolean r0 = r5 instanceof java.lang.String
            if (r0 == 0) goto L_0x0089
            r0 = r5
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r0)
            if (r0 == 0) goto L_0x0089
            r1 = 1
        L_0x0089:
            android.view.View r0 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.PointTipView r0 = (com.autonavi.minimap.ajx3.widget.view.PointTipView) r0
            r0.updateArrow(r1)
            goto L_0x012b
        L_0x0092:
            boolean r0 = r5 instanceof java.lang.String
            if (r0 == 0) goto L_0x00a0
            r0 = r5
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r0)
            if (r0 == 0) goto L_0x00a0
            r1 = 1
        L_0x00a0:
            android.view.View r0 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.PointTipView r0 = (com.autonavi.minimap.ajx3.widget.view.PointTipView) r0
            r0.updateHasTip(r1)
            goto L_0x012b
        L_0x00a9:
            android.view.View r0 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.PointTipView r0 = (com.autonavi.minimap.ajx3.widget.view.PointTipView) r0
            r0.updateTheme(r5)
            goto L_0x012b
        L_0x00b2:
            r3.mHasClickTip = r2
            goto L_0x012b
        L_0x00b6:
            boolean r0 = r5 instanceof java.lang.String
            if (r0 == 0) goto L_0x00c4
            r0 = r5
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r0)
            if (r0 == 0) goto L_0x00c4
            r1 = 1
        L_0x00c4:
            if (r1 == 0) goto L_0x012b
            android.view.View r0 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.PointTipView r0 = (com.autonavi.minimap.ajx3.widget.view.PointTipView) r0
            r0.playTipPinDownAnimator(r2)
            goto L_0x012b
        L_0x00ce:
            boolean r0 = r5 instanceof java.lang.String
            if (r0 == 0) goto L_0x00dc
            r0 = r5
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r0)
            if (r0 == 0) goto L_0x00dc
            r1 = 1
        L_0x00dc:
            if (r1 != 0) goto L_0x00e6
            android.view.View r0 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.PointTipView r0 = (com.autonavi.minimap.ajx3.widget.view.PointTipView) r0
            r0.hideTipWithAnimation()
            goto L_0x012b
        L_0x00e6:
            android.view.View r0 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.PointTipView r0 = (com.autonavi.minimap.ajx3.widget.view.PointTipView) r0
            r0.showTipWithAnimation()
            goto L_0x012b
        L_0x00ee:
            boolean r0 = r5 instanceof java.lang.String
            if (r0 == 0) goto L_0x012b
            android.view.View r0 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.PointTipView r0 = (com.autonavi.minimap.ajx3.widget.view.PointTipView) r0
            r1 = r5
            java.lang.String r1 = (java.lang.String) r1
            r0.updateContent(r1)
            goto L_0x012b
        L_0x00fd:
            boolean r0 = r5 instanceof java.lang.String
            if (r0 == 0) goto L_0x012b
            android.view.View r0 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.PointTipView r0 = (com.autonavi.minimap.ajx3.widget.view.PointTipView) r0
            r1 = r5
            java.lang.String r1 = (java.lang.String) r1
            r0.updateTipTime(r1)
            goto L_0x012b
        L_0x010c:
            boolean r0 = r5 instanceof java.lang.String
            if (r0 == 0) goto L_0x012b
            java.lang.String r0 = "2"
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x0122
            android.view.View r0 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.PointTipView r0 = (com.autonavi.minimap.ajx3.widget.view.PointTipView) r0
            int r1 = com.autonavi.minimap.ajx3.widget.view.PointTipView.TYPE_HAS_TIME
            r0.setType(r1)
            goto L_0x012b
        L_0x0122:
            android.view.View r0 = r3.mView
            com.autonavi.minimap.ajx3.widget.view.PointTipView r0 = (com.autonavi.minimap.ajx3.widget.view.PointTipView) r0
            int r1 = com.autonavi.minimap.ajx3.widget.view.PointTipView.TYPE_NO_TIME
            r0.setType(r1)
        L_0x012b:
            super.updateAttribute(r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.PointTipViewProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    public boolean hasClickTip() {
        return this.mHasClickTip;
    }

    public void invokeClickTip() {
        this.mAjxContext.invokeJsEvent(new Builder().setEventName("onTipClick").setNodeId(getNodeId()).build());
    }
}
