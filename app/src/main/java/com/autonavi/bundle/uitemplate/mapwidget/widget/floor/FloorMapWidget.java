package com.autonavi.bundle.uitemplate.mapwidget.widget.floor;

import android.content.Context;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;

public class FloorMapWidget extends AbstractMapWidget<FloorMapWidgetPresenter> {
    public FloorMapWidget(Context context, IWidgetProperty iWidgetProperty) {
        super(context, iWidgetProperty);
    }

    /* JADX WARNING: type inference failed for: r0v3, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r0v4, types: [com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView] */
    /* JADX WARNING: type inference failed for: r0v5, types: [com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView2] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v4, types: [com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView]
      assigns: [com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView, com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView2]
      uses: [com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView, android.view.View, com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView2]
      mth insns count: 36
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View createContentView(android.content.Context r4) {
        /*
            r3 = this;
            android.view.View r0 = r3.mContentView
            if (r0 == 0) goto L_0x0007
            android.view.View r4 = r3.mContentView
            return r4
        L_0x0007:
            com.autonavi.bundle.uitemplate.api.IWidgetProperty r0 = r3.getWidgetProperty()
            boolean r0 = r0.isLoadNewWidgetStyle()
            r1 = 8
            if (r0 == 0) goto L_0x0037
            com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView2 r0 = new com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView2
            r0.<init>(r4)
            java.lang.Class<bec> r4 = defpackage.bec.class
            java.lang.Object r4 = defpackage.ank.a(r4)
            bec r4 = (defpackage.bec) r4
            if (r4 == 0) goto L_0x0068
            bea r4 = r4.a()
            if (r4 == 0) goto L_0x0068
            com.autonavi.bundle.uitemplate.indoor.RedesignFloorWidgetView r2 = r0.getFloorWidgetView()
            r2.setVisibility(r1)
            com.autonavi.bundle.uitemplate.indoor.RedesignFloorWidgetView r1 = r0.getFloorWidgetView()
            r4.a(r1)
            goto L_0x0068
        L_0x0037:
            com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView r0 = new com.autonavi.bundle.uitemplate.indoor.RedesignFloorCompositeView
            r0.<init>(r4)
            java.lang.Class<bec> r4 = defpackage.bec.class
            java.lang.Object r4 = defpackage.ank.a(r4)
            bec r4 = (defpackage.bec) r4
            if (r4 == 0) goto L_0x0068
            bea r4 = r4.a()
            if (r4 == 0) goto L_0x0068
            com.autonavi.bundle.uitemplate.indoor.RedesignFloorWidgetView r2 = r0.getFloorWidgetView()
            r2.setVisibility(r1)
            com.autonavi.bundle.uitemplate.indoor.RedesignFloorWidgetView r2 = r0.getFloorWidgetView()
            r4.a(r2)
            com.autonavi.bundle.uitemplate.indoor.RedesignMapGuideView r2 = r0.getMapGuideView()
            r2.setVisibility(r1)
            com.autonavi.bundle.uitemplate.indoor.RedesignMapGuideView r1 = r0.getMapGuideView()
            r4.a(r1)
        L_0x0068:
            r3.mContentView = r0
            android.view.View r4 = r3.mContentView
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.uitemplate.mapwidget.widget.floor.FloorMapWidget.createContentView(android.content.Context):android.view.View");
    }
}
