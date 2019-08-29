package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager;

public class ViewPagerProperty extends BaseProperty<AjxViewPager> {
    public boolean canSupportBorderClip() {
        return false;
    }

    public ViewPagerProperty(@NonNull AjxViewPager ajxViewPager, @NonNull IAjxContext iAjxContext) {
        super(ajxViewPager, iAjxContext);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0088  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r5, java.lang.Object r6) {
        /*
            r4 = this;
            int r0 = r5.hashCode()
            r1 = 66669991(0x3f94da7, float:1.4652733E-36)
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x0039
            r1 = 536676945(0x1ffd0a51, float:1.07166735E-19)
            if (r0 == r1) goto L_0x002f
            r1 = 601108392(0x23d42fa8, float:2.300527E-17)
            if (r0 == r1) goto L_0x0025
            r1 = 1278524054(0x4c34ba96, float:4.7376984E7)
            if (r0 == r1) goto L_0x001b
            goto L_0x0044
        L_0x001b:
            java.lang.String r0 = "currentPageWithAnimation"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0044
            r0 = 1
            goto L_0x0045
        L_0x0025:
            java.lang.String r0 = "currentPage"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0044
            r0 = 0
            goto L_0x0045
        L_0x002f:
            java.lang.String r0 = "offscreenpagelimit"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0044
            r0 = 2
            goto L_0x0045
        L_0x0039:
            java.lang.String r0 = "scrollable"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0044
            r0 = 3
            goto L_0x0045
        L_0x0044:
            r0 = -1
        L_0x0045:
            switch(r0) {
                case 0: goto L_0x0088;
                case 1: goto L_0x007b;
                case 2: goto L_0x005e;
                case 3: goto L_0x004c;
                default: goto L_0x0048;
            }
        L_0x0048:
            super.updateAttribute(r5, r6)
            return
        L_0x004c:
            boolean r5 = r6 instanceof java.lang.String
            if (r5 == 0) goto L_0x005d
            java.lang.String r6 = (java.lang.String) r6
            boolean r5 = java.lang.Boolean.parseBoolean(r6)
            android.view.View r6 = r4.mView
            com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager r6 = (com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager) r6
            r6.setScrollable(r5)
        L_0x005d:
            return
        L_0x005e:
            boolean r5 = r6 instanceof java.lang.String
            if (r5 == 0) goto L_0x0069
            java.lang.String r6 = (java.lang.String) r6
            int r3 = java.lang.Integer.parseInt(r6)
            goto L_0x0073
        L_0x0069:
            boolean r5 = r6 instanceof java.lang.Integer
            if (r5 == 0) goto L_0x0073
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r3 = r6.intValue()
        L_0x0073:
            android.view.View r5 = r4.mView
            com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager r5 = (com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager) r5
            r5.setOffscreenPageLimit(r3)
            return
        L_0x007b:
            r4.updateCurrentPage(r6, r3)
            com.autonavi.minimap.ajx3.dom.AjxDomNode r5 = r4.getNode()
            java.lang.String r0 = "currentPage"
            r5.setAttribute(r0, r6)
            return
        L_0x0088:
            r4.updateCurrentPage(r6, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.ViewPagerProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    public Object getAttribute(String str) {
        if (((str.hashCode() == 66047092 && str.equals(HorizontalScrollerProperty.SCROLL_LEFT)) ? (char) 0 : 65535) != 0) {
            return super.getAttribute(str);
        }
        return Float.valueOf(DimensionUtils.pixelToStandardUnit((float) ((AjxViewPager) this.mView).getScrollLeft()));
    }

    private void updateCurrentPage(Object obj, boolean z) {
        if (obj instanceof String) {
            int parseInt = StringUtils.parseInt((String) obj);
            if (parseInt != -1) {
                ((AjxViewPager) this.mView).setCurrentItem(parseInt, z);
            }
        }
    }
}
