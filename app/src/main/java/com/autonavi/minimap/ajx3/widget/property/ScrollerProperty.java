package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxScrollerDomNode;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.view.Scroller;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollBeginListener;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollEndListener;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollInertiaEnd;

public class ScrollerProperty extends BaseProperty<Scroller> {
    private static final String ATTR_INDICATOR = "indicator";
    private static final String ATTR_LEAVE_BOUND = "leavebound";
    private static final String ATTR_ORIENTATION = "orientation";
    private static final String ATTR_SCROLLEASE = "scrollease";
    private static final String ATTR_SCROLL_ABLE = "scrollable";
    private static final String ATTR_SCROLL_ACCURACY = "scrollaccuracy";
    private static final String ATTR_SCROLL_BEGIN = "scrollBegin";
    private static final String ATTR_SCROLL_BOUND = "scrollbound";
    private static final String ATTR_SCROLL_END = "scrollEnd";
    private static final String ATTR_SCROLL_INERTIA_END = "scrollInertiaEnd";
    public static final String ATTR_SCROLL_TOP = "_SCROLL_TOP";
    private static final String ATTR_VIEW_TYPE = "viewtype";
    public static final String SCROLL_TOP = "scrollTop";
    /* access modifiers changed from: private */
    public boolean mScrollease = false;

    public boolean canSupportBorderClip() {
        return false;
    }

    public ScrollerProperty(@NonNull Scroller scroller, @NonNull IAjxContext iAjxContext) {
        super(scroller, iAjxContext);
    }

    /* access modifiers changed from: protected */
    public void updateStyle(int i, Object obj, boolean z) {
        switch (i) {
            case Property.NODE_PROPERTY_ORIENTATION /*1056964686*/:
                replaceHorizontalScroller(obj);
                return;
            case Property.NODE_PROPERTY_BOUNCE /*1056964687*/:
                updateScrollMode(obj);
                return;
            default:
                super.updateStyle(i, obj, z);
                return;
        }
    }

    /* access modifiers changed from: protected */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r2, java.lang.Object r3) {
        /*
            r1 = this;
            int r0 = r2.hashCode()
            switch(r0) {
                case -1439500848: goto L_0x0092;
                case -866286906: goto L_0x0086;
                case -711999985: goto L_0x007c;
                case -671184274: goto L_0x0071;
                case -27294425: goto L_0x0066;
                case 66669991: goto L_0x005a;
                case 66788411: goto L_0x004e;
                case 417766094: goto L_0x0043;
                case 417780552: goto L_0x0038;
                case 1196814175: goto L_0x002d;
                case 1640986210: goto L_0x0022;
                case 2038225372: goto L_0x0016;
                case 2068089553: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x009c
        L_0x0009:
            java.lang.String r0 = "scrollbound"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 10
            goto L_0x009d
        L_0x0016:
            java.lang.String r0 = "scrollBegin"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 0
            goto L_0x009d
        L_0x0022:
            java.lang.String r0 = "_SCROLL_TOP"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 6
            goto L_0x009d
        L_0x002d:
            java.lang.String r0 = "viewtype"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 3
            goto L_0x009d
        L_0x0038:
            java.lang.String r0 = "scrollTop"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 7
            goto L_0x009d
        L_0x0043:
            java.lang.String r0 = "scrollEnd"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 1
            goto L_0x009d
        L_0x004e:
            java.lang.String r0 = "scrollease"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 8
            goto L_0x009d
        L_0x005a:
            java.lang.String r0 = "scrollable"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 12
            goto L_0x009d
        L_0x0066:
            java.lang.String r0 = "leavebound"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 11
            goto L_0x009d
        L_0x0071:
            java.lang.String r0 = "scrollInertiaEnd"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 2
            goto L_0x009d
        L_0x007c:
            java.lang.String r0 = "indicator"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 5
            goto L_0x009d
        L_0x0086:
            java.lang.String r0 = "scrollaccuracy"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 9
            goto L_0x009d
        L_0x0092:
            java.lang.String r0 = "orientation"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 4
            goto L_0x009d
        L_0x009c:
            r0 = -1
        L_0x009d:
            switch(r0) {
                case 0: goto L_0x00cc;
                case 1: goto L_0x00c8;
                case 2: goto L_0x00c4;
                case 3: goto L_0x00c0;
                case 4: goto L_0x00bc;
                case 5: goto L_0x00b8;
                case 6: goto L_0x00b4;
                case 7: goto L_0x00b4;
                case 8: goto L_0x00b0;
                case 9: goto L_0x00ac;
                case 10: goto L_0x00a8;
                case 11: goto L_0x00a8;
                case 12: goto L_0x00a4;
                default: goto L_0x00a0;
            }
        L_0x00a0:
            super.updateAttribute(r2, r3)
            return
        L_0x00a4:
            r1.updateScrollable(r3)
            return
        L_0x00a8:
            r1.updateNeedScrollPosition(r3)
            return
        L_0x00ac:
            r1.updateScrollAccuracy(r3)
            return
        L_0x00b0:
            r1.updateScrollease(r3)
            return
        L_0x00b4:
            r1.updateScrollTop(r3)
            return
        L_0x00b8:
            r1.updateIndicator(r3)
            return
        L_0x00bc:
            r1.replaceHorizontalScroller(r3)
            return
        L_0x00c0:
            r1.replaceViewPager(r3)
            return
        L_0x00c4:
            r1.updateScrollInertiaEnd(r3)
            return
        L_0x00c8:
            r1.updateScrollEnd(r3)
            return
        L_0x00cc:
            r1.updateScrollBegin(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.ScrollerProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateScrollable(Object obj) {
        if (obj != null) {
            ((Scroller) this.mView).updateScrollable(Boolean.parseBoolean((String) obj));
        }
    }

    private void updateScrollInertiaEnd(Object obj) {
        if (obj == null) {
            ((Scroller) this.mView).setScrollInertiaEnd(null);
        } else {
            ((Scroller) this.mView).setScrollInertiaEnd(new ScrollInertiaEnd() {
                public void onScrollInertiaEnd() {
                    ScrollerProperty.this.invoke(ScrollerProperty.ATTR_SCROLL_INERTIA_END, null, null);
                }
            });
        }
    }

    private void updateScrollEnd(Object obj) {
        if (obj == null) {
            ((Scroller) this.mView).setScrollEndListener(null);
        } else {
            ((Scroller) this.mView).setScrollEndListener(new ScrollEndListener() {
                public void onScrollEnd() {
                    ScrollerProperty.this.invoke(ScrollerProperty.ATTR_SCROLL_END, null, null);
                }
            });
        }
    }

    private void updateScrollBegin(Object obj) {
        if (obj == null) {
            ((Scroller) this.mView).setScrollBeginListener(null);
        } else {
            ((Scroller) this.mView).setScrollBeginListener(new ScrollBeginListener() {
                public void onDragBegin() {
                    ScrollerProperty.this.invoke(ScrollerProperty.ATTR_SCROLL_BEGIN, null, null);
                }
            });
        }
    }

    private void updateScrollease(Object obj) {
        if (obj instanceof Boolean) {
            this.mScrollease = ((Boolean) obj).booleanValue();
            return;
        }
        if (obj instanceof String) {
            this.mScrollease = StringUtils.parseBoolean((String) obj);
        }
    }

    private void replaceHorizontalScroller(Object obj) {
        if ((obj instanceof Integer) && 1056964743 == ((Integer) obj).intValue() && !TextUtils.equals("viewpager", (String) getNode().getAttributeValue(ATTR_VIEW_TYPE)) && this.mNode != null && (this.mNode instanceof AjxScrollerDomNode)) {
            ((AjxScrollerDomNode) this.mNode).replaceHorizontalScroller();
        }
    }

    private void replaceViewPager(Object obj) {
        if ((obj instanceof String) && TextUtils.equals("viewpager", (String) obj) && this.mNode != null && (this.mNode instanceof AjxScrollerDomNode)) {
            ((AjxScrollerDomNode) this.mNode).replaceViewPager();
        }
    }

    private void updateIndicator(Object obj) {
        if (obj instanceof Boolean) {
            ((Scroller) this.mView).setVerticalScrollBarEnabled(((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof String) {
            ((Scroller) this.mView).setVerticalScrollBarEnabled(StringUtils.parseBoolean((String) obj));
        }
    }

    private void updateScrollTop(Object obj) {
        final float f;
        if (obj instanceof Float) {
            f = ((Float) obj).floatValue();
        } else if (obj instanceof String) {
            String str = (String) obj;
            int indexOf = str.indexOf(Params.UNIT_PX);
            if (indexOf != -1) {
                str = str.substring(0, indexOf);
            }
            f = Float.valueOf(str).floatValue();
        } else {
            f = 0.0f;
        }
        ((Scroller) this.mView).post(new Runnable() {
            public void run() {
                if (ScrollerProperty.this.mScrollease) {
                    ((Scroller) ScrollerProperty.this.mView).smoothScrollTo(0, DimensionUtils.standardUnitToPixel(f));
                } else {
                    ((Scroller) ScrollerProperty.this.mView).scrollTo(0, DimensionUtils.standardUnitToPixel(f));
                }
            }
        });
    }

    private void updateScrollAccuracy(Object obj) {
        try {
            ((Scroller) this.mView).setScrollAccuracy(Float.parseFloat(String.valueOf(obj)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void invoke(String str, Parcel parcel, Parcel parcel2) {
        this.mAjxContext.invokeJsEvent(new Builder().setEventName(str).setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mView)).setAttribute(parcel).setTouch(parcel2).build());
    }

    private void updateNeedScrollPosition(Object obj) {
        if (obj == null) {
            ((Scroller) this.mView).setNeedScrollPosition(false);
        } else {
            ((Scroller) this.mView).setNeedScrollPosition(true);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0074  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateScrollMode(java.lang.Object r7) {
        /*
            r6 = this;
            r0 = 0
            if (r7 != 0) goto L_0x000b
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.view.Scroller r7 = (com.autonavi.minimap.ajx3.widget.view.Scroller) r7
            r7.setOverScrollMode(r0)
            return
        L_0x000b:
            boolean r1 = r7 instanceof java.lang.String
            if (r1 == 0) goto L_0x007c
            java.lang.String r7 = (java.lang.String) r7
            r1 = -1
            int r2 = r7.hashCode()
            r3 = -1383228885(0xffffffffad8d9a2b, float:-1.6098308E-11)
            r4 = 3
            r5 = 2
            if (r2 == r3) goto L_0x004c
            r3 = 115029(0x1c155, float:1.6119E-40)
            if (r2 == r3) goto L_0x0041
            r3 = 3029889(0x2e3b81, float:4.245779E-39)
            if (r2 == r3) goto L_0x0037
            r3 = 3387192(0x33af38, float:4.746467E-39)
            if (r2 == r3) goto L_0x002d
            goto L_0x0056
        L_0x002d:
            java.lang.String r2 = "none"
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L_0x0056
            r7 = 1
            goto L_0x0057
        L_0x0037:
            java.lang.String r2 = "both"
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L_0x0056
            r7 = 0
            goto L_0x0057
        L_0x0041:
            java.lang.String r2 = "top"
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L_0x0056
            r7 = 2
            goto L_0x0057
        L_0x004c:
            java.lang.String r2 = "bottom"
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L_0x0056
            r7 = 3
            goto L_0x0057
        L_0x0056:
            r7 = -1
        L_0x0057:
            switch(r7) {
                case 0: goto L_0x0074;
                case 1: goto L_0x006c;
                case 2: goto L_0x0064;
                case 3: goto L_0x005b;
                default: goto L_0x005a;
            }
        L_0x005a:
            goto L_0x007c
        L_0x005b:
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.view.Scroller r7 = (com.autonavi.minimap.ajx3.widget.view.Scroller) r7
            r0 = 4
            r7.setOverScrollMode(r0)
            goto L_0x007c
        L_0x0064:
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.view.Scroller r7 = (com.autonavi.minimap.ajx3.widget.view.Scroller) r7
            r7.setOverScrollMode(r4)
            return
        L_0x006c:
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.view.Scroller r7 = (com.autonavi.minimap.ajx3.widget.view.Scroller) r7
            r7.setOverScrollMode(r5)
            return
        L_0x0074:
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.view.Scroller r7 = (com.autonavi.minimap.ajx3.widget.view.Scroller) r7
            r7.setOverScrollMode(r0)
            return
        L_0x007c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.ScrollerProperty.updateScrollMode(java.lang.Object):void");
    }
}
