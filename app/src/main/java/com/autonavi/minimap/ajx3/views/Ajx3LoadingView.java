package com.autonavi.minimap.ajx3.views;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.widget.ui.LoadingViewBL;

@SuppressLint({"ViewConstructor"})
public class Ajx3LoadingView extends FrameLayout implements ViewExtension {
    private LoadingViewBL mInnerView;
    private BaseProperty mProperty;
    private int mStatus = -1;
    private int mStyle = -1;
    private boolean modelAvailable = false;

    static class LoadingViewStrategy {
        /* access modifiers changed from: private */
        public static boolean isAppIconAvailable(int i) {
            return i == 2 || i == 6;
        }

        /* access modifiers changed from: private */
        public static boolean isLoadingAnimationAvailable(int i) {
            return i == 1;
        }

        /* access modifiers changed from: private */
        public static boolean isLoadingDetailAvailable(int i) {
            return false;
        }

        /* access modifiers changed from: private */
        public static boolean isLoadingTextAvailable(int i) {
            return i == 0;
        }

        private LoadingViewStrategy() {
        }
    }

    public Ajx3LoadingView(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mProperty = new Ajx3LoadingViewProperty(this, iAjxContext);
        updateInnerView(2);
    }

    private void updateInnerView(int i) {
        if (this.mStyle != i) {
            this.mStyle = i;
            removeAllViews();
            this.mInnerView = new LoadingViewBL(getContext(), i);
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.gravity = 17;
            addView(this.mInnerView, layoutParams);
            if (this.mStyle == 1) {
                updateLoadingB();
            }
        }
    }

    private void updateLoadingB() {
        this.mInnerView.setExtraViewVisibility(8);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0070  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setModel(java.lang.String r11) {
        /*
            r10 = this;
            int r0 = r10.mStyle
            java.lang.String r11 = r11.toLowerCase()
            int r1 = r11.hashCode()
            r2 = 3119(0xc2f, float:4.37E-42)
            r3 = 3
            r4 = 0
            r5 = 2
            r6 = 4
            r7 = 5
            r8 = 6
            r9 = 1
            if (r1 == r2) goto L_0x0055
            switch(r1) {
                case 97: goto L_0x004b;
                case 98: goto L_0x0041;
                case 99: goto L_0x0037;
                case 100: goto L_0x002d;
                case 101: goto L_0x0023;
                case 102: goto L_0x0019;
                default: goto L_0x0018;
            }
        L_0x0018:
            goto L_0x005f
        L_0x0019:
            java.lang.String r1 = "f"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x005f
            r11 = 6
            goto L_0x0060
        L_0x0023:
            java.lang.String r1 = "e"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x005f
            r11 = 5
            goto L_0x0060
        L_0x002d:
            java.lang.String r1 = "d"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x005f
            r11 = 4
            goto L_0x0060
        L_0x0037:
            java.lang.String r1 = "c"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x005f
            r11 = 2
            goto L_0x0060
        L_0x0041:
            java.lang.String r1 = "b"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x005f
            r11 = 1
            goto L_0x0060
        L_0x004b:
            java.lang.String r1 = "a"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x005f
            r11 = 0
            goto L_0x0060
        L_0x0055:
            java.lang.String r1 = "c2"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x005f
            r11 = 3
            goto L_0x0060
        L_0x005f:
            r11 = -1
        L_0x0060:
            switch(r11) {
                case 0: goto L_0x0070;
                case 1: goto L_0x006e;
                case 2: goto L_0x006c;
                case 3: goto L_0x006a;
                case 4: goto L_0x0068;
                case 5: goto L_0x0066;
                case 6: goto L_0x0064;
                default: goto L_0x0063;
            }
        L_0x0063:
            goto L_0x0071
        L_0x0064:
            r0 = 5
            goto L_0x0071
        L_0x0066:
            r0 = 4
            goto L_0x0071
        L_0x0068:
            r0 = 3
            goto L_0x0071
        L_0x006a:
            r0 = 6
            goto L_0x0071
        L_0x006c:
            r0 = 2
            goto L_0x0071
        L_0x006e:
            r0 = 1
            goto L_0x0071
        L_0x0070:
            r0 = 0
        L_0x0071:
            r10.modelAvailable = r9
            r10.updateInnerView(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3LoadingView.setModel(java.lang.String):void");
    }

    public boolean isModelAvailable() {
        return this.modelAvailable;
    }

    public void setAppIconVisibility(int i) {
        if (this.mInnerView != null && LoadingViewStrategy.isAppIconAvailable(this.mStyle)) {
            this.mInnerView.setAppIconVisibility(i);
        }
    }

    public void updateLoadingStatus(int i) {
        if (this.mInnerView != null && LoadingViewStrategy.isLoadingAnimationAvailable(this.mStyle)) {
            this.mInnerView.startAnimationByState(i);
            this.mStatus = i;
        }
    }

    public void setLoadingText(String str) {
        if (this.mInnerView != null && LoadingViewStrategy.isLoadingTextAvailable(this.mStyle) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str.trim())) {
            this.mInnerView.setLoadingText(str.trim());
        }
    }

    public void setLoadingDetail(String str) {
        if (this.mInnerView != null && LoadingViewStrategy.isLoadingDetailAvailable(this.mStyle) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str.trim())) {
            this.mInnerView.setLoadingDetail(str.trim());
        }
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

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mStyle == 1 && this.mStatus == 1 && this.mInnerView != null) {
            this.mInnerView.startAnimationByState(this.mStatus);
        }
    }
}
