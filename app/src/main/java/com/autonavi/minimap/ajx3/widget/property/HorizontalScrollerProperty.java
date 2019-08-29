package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxScrollerDomNode;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.view.HorizontalScroller;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollBeginListener;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollEndListener;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollInertiaEnd;

public class HorizontalScrollerProperty extends BaseProperty<HorizontalScroller> {
    public static final String ATTR_DECELERATE = "decelerate";
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
    public static final String ATTR_SCROLL_LEFT = "_SCROLL_LEFT";
    private static final String ATTR_VIEW_TYPE = "viewtype";
    public static final String SCROLL_LEFT = "scrollLeft";
    /* access modifiers changed from: private */
    public boolean mScrollease = false;

    public boolean canSupportBorderClip() {
        return false;
    }

    public HorizontalScrollerProperty(@NonNull HorizontalScroller horizontalScroller, @NonNull IAjxContext iAjxContext) {
        super(horizontalScroller, iAjxContext);
    }

    /* access modifiers changed from: protected */
    public void updateStyle(int i, Object obj, boolean z) {
        if (i != 1056964687) {
            super.updateStyle(i, obj, z);
        } else {
            updateScrollMode(obj);
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
                case -671184274: goto L_0x0070;
                case -669283206: goto L_0x0066;
                case -27294425: goto L_0x005b;
                case 66047092: goto L_0x0050;
                case 66669991: goto L_0x0044;
                case 66788411: goto L_0x0039;
                case 417766094: goto L_0x002e;
                case 1196814175: goto L_0x0022;
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
            r0 = 6
            goto L_0x009d
        L_0x0022:
            java.lang.String r0 = "viewtype"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 0
            goto L_0x009d
        L_0x002e:
            java.lang.String r0 = "scrollEnd"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 7
            goto L_0x009d
        L_0x0039:
            java.lang.String r0 = "scrollease"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 5
            goto L_0x009d
        L_0x0044:
            java.lang.String r0 = "scrollable"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 12
            goto L_0x009d
        L_0x0050:
            java.lang.String r0 = "scrollLeft"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 3
            goto L_0x009d
        L_0x005b:
            java.lang.String r0 = "leavebound"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 11
            goto L_0x009d
        L_0x0066:
            java.lang.String r0 = "_SCROLL_LEFT"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 2
            goto L_0x009d
        L_0x0070:
            java.lang.String r0 = "scrollInertiaEnd"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 8
            goto L_0x009d
        L_0x007c:
            java.lang.String r0 = "indicator"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x009c
            r0 = 4
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
            r0 = 1
            goto L_0x009d
        L_0x009c:
            r0 = -1
        L_0x009d:
            switch(r0) {
                case 0: goto L_0x00c9;
                case 1: goto L_0x00c8;
                case 2: goto L_0x00c4;
                case 3: goto L_0x00c4;
                case 4: goto L_0x00c0;
                case 5: goto L_0x00bc;
                case 6: goto L_0x00b8;
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
            r1.updateScrollInertiaEnd(r3)
            return
        L_0x00b4:
            r1.updateScrollEnd(r3)
            return
        L_0x00b8:
            r1.updateScrollBegin(r3)
            return
        L_0x00bc:
            r1.updateScrollease(r3)
            return
        L_0x00c0:
            r1.updateIndicator(r3)
            return
        L_0x00c4:
            r1.updateScrollLeft(r3)
            return
        L_0x00c8:
            return
        L_0x00c9:
            r1.replaceViewPager(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.HorizontalScrollerProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateScrollable(Object obj) {
        if (obj != null) {
            ((HorizontalScroller) this.mView).updateScrollable(Boolean.parseBoolean((String) obj));
        }
    }

    private void updateScrollInertiaEnd(Object obj) {
        if (obj == null) {
            ((HorizontalScroller) this.mView).setScrollInertiaEnd(null);
        } else {
            ((HorizontalScroller) this.mView).setScrollInertiaEnd(new ScrollInertiaEnd() {
                public void onScrollInertiaEnd() {
                    HorizontalScrollerProperty.this.invoke(HorizontalScrollerProperty.ATTR_SCROLL_INERTIA_END, null, null);
                }
            });
        }
    }

    private void updateScrollEnd(Object obj) {
        if (obj == null) {
            ((HorizontalScroller) this.mView).setScrollEndListener(null);
        } else {
            ((HorizontalScroller) this.mView).setScrollEndListener(new ScrollEndListener() {
                public void onScrollEnd() {
                    HorizontalScrollerProperty.this.invoke(HorizontalScrollerProperty.ATTR_SCROLL_END, null, null);
                }
            });
        }
    }

    private void updateScrollBegin(Object obj) {
        if (obj == null) {
            ((HorizontalScroller) this.mView).setScrollBeginListener(null);
        } else {
            ((HorizontalScroller) this.mView).setScrollBeginListener(new ScrollBeginListener() {
                public void onDragBegin() {
                    HorizontalScrollerProperty.this.invoke(HorizontalScrollerProperty.ATTR_SCROLL_BEGIN, null, null);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invoke(String str, Parcel parcel, Parcel parcel2) {
        this.mAjxContext.invokeJsEvent(new Builder().setEventName(str).setNodeId(this.mAjxContext.getDomTree().getNodeId(this.mView)).setAttribute(parcel).setTouch(parcel2).build());
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

    private void updateScrollLeft(Object obj) {
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
            f = -1.0f;
        }
        this.mAjxContext.getDomTree().getRootView().post(new Runnable() {
            public void run() {
                if (HorizontalScrollerProperty.this.mScrollease) {
                    ((HorizontalScroller) HorizontalScrollerProperty.this.mView).setFingerDrag(true);
                    ((HorizontalScroller) HorizontalScrollerProperty.this.mView).smoothScrollTo(DimensionUtils.standardUnitToPixel(f), 0);
                    return;
                }
                ((HorizontalScroller) HorizontalScrollerProperty.this.mView).setFingerDrag(false);
                ((HorizontalScroller) HorizontalScrollerProperty.this.mView).scrollTo(DimensionUtils.standardUnitToPixel(f), 0);
            }
        });
    }

    private void replaceViewPager(Object obj) {
        if ((obj instanceof String) && TextUtils.equals("viewpager", (String) obj) && this.mNode != null && (this.mNode instanceof AjxScrollerDomNode)) {
            ((AjxScrollerDomNode) this.mNode).replaceViewPager();
        }
    }

    private void updateScrollAccuracy(Object obj) {
        try {
            ((HorizontalScroller) this.mView).setScrollAccuracy(Float.parseFloat(String.valueOf(obj)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void updateIndicator(Object obj) {
        if (obj instanceof Boolean) {
            ((HorizontalScroller) this.mView).setHorizontalScrollBarEnabled(((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof String) {
            ((HorizontalScroller) this.mView).setHorizontalScrollBarEnabled(StringUtils.parseBoolean((String) obj));
        }
    }

    private void updateNeedScrollPosition(Object obj) {
        if (obj == null) {
            ((HorizontalScroller) this.mView).setNeedScrollPosition(false);
        } else {
            ((HorizontalScroller) this.mView).setNeedScrollPosition(true);
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
            com.autonavi.minimap.ajx3.widget.view.HorizontalScroller r7 = (com.autonavi.minimap.ajx3.widget.view.HorizontalScroller) r7
            r7.setOverScrollMode(r0)
            return
        L_0x000b:
            boolean r1 = r7 instanceof java.lang.String
            if (r1 == 0) goto L_0x007c
            java.lang.String r7 = (java.lang.String) r7
            r1 = -1
            int r2 = r7.hashCode()
            r3 = 3029889(0x2e3b81, float:4.245779E-39)
            r4 = 2
            r5 = 3
            if (r2 == r3) goto L_0x004c
            r3 = 3317767(0x32a007, float:4.649182E-39)
            if (r2 == r3) goto L_0x0042
            r3 = 3387192(0x33af38, float:4.746467E-39)
            if (r2 == r3) goto L_0x0038
            r3 = 108511772(0x677c21c, float:4.6598146E-35)
            if (r2 == r3) goto L_0x002d
            goto L_0x0056
        L_0x002d:
            java.lang.String r2 = "right"
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L_0x0056
            r7 = 3
            goto L_0x0057
        L_0x0038:
            java.lang.String r2 = "none"
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L_0x0056
            r7 = 1
            goto L_0x0057
        L_0x0042:
            java.lang.String r2 = "left"
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L_0x0056
            r7 = 2
            goto L_0x0057
        L_0x004c:
            java.lang.String r2 = "both"
            boolean r7 = r7.equals(r2)
            if (r7 == 0) goto L_0x0056
            r7 = 0
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
            com.autonavi.minimap.ajx3.widget.view.HorizontalScroller r7 = (com.autonavi.minimap.ajx3.widget.view.HorizontalScroller) r7
            r0 = 4
            r7.setOverScrollMode(r0)
            goto L_0x007c
        L_0x0064:
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.view.HorizontalScroller r7 = (com.autonavi.minimap.ajx3.widget.view.HorizontalScroller) r7
            r7.setOverScrollMode(r5)
            return
        L_0x006c:
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.view.HorizontalScroller r7 = (com.autonavi.minimap.ajx3.widget.view.HorizontalScroller) r7
            r7.setOverScrollMode(r4)
            return
        L_0x0074:
            android.view.View r7 = r6.mView
            com.autonavi.minimap.ajx3.widget.view.HorizontalScroller r7 = (com.autonavi.minimap.ajx3.widget.view.HorizontalScroller) r7
            r7.setOverScrollMode(r0)
            return
        L_0x007c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.HorizontalScrollerProperty.updateScrollMode(java.lang.Object):void");
    }
}
