package defpackage;

import com.autonavi.minimap.ajx3.scheme.IRedesignPageLoader;

/* renamed from: bem reason: default package */
/* compiled from: RedesignAjxPageLoader */
public class bem implements IRedesignPageLoader {
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0050, code lost:
        if (r1.equals("Page") != false) goto L_0x005e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0094, code lost:
        if (r8.equals("fullpage") == false) goto L_0x00ab;
     */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00b6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Class<? extends com.autonavi.minimap.ajx3.Ajx3Page> loadPage(android.net.Uri r8) {
        /*
            r7 = this;
            r0 = 0
            if (r8 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r1 = "style"
            java.lang.String r1 = r8.getQueryParameter(r1)
            java.lang.String r2 = "transition_mode"
            java.lang.String r8 = r8.getQueryParameter(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            r3 = 0
            r4 = 2
            r5 = 1
            r6 = -1
            if (r2 != 0) goto L_0x0074
            int r8 = r1.hashCode()
            switch(r8) {
                case -1830685983: goto L_0x0053;
                case 2479791: goto L_0x004a;
                case 442900938: goto L_0x0040;
                case 961428876: goto L_0x0036;
                case 1047469524: goto L_0x002c;
                case 1856580871: goto L_0x0022;
                default: goto L_0x0021;
            }
        L_0x0021:
            goto L_0x005d
        L_0x0022:
            java.lang.String r8 = "PageWithMap"
            boolean r8 = r1.equals(r8)
            if (r8 == 0) goto L_0x005d
            r3 = 1
            goto L_0x005e
        L_0x002c:
            java.lang.String r8 = "BottomSheet"
            boolean r8 = r1.equals(r8)
            if (r8 == 0) goto L_0x005d
            r3 = 4
            goto L_0x005e
        L_0x0036:
            java.lang.String r8 = "PresentPageWithMap"
            boolean r8 = r1.equals(r8)
            if (r8 == 0) goto L_0x005d
            r3 = 3
            goto L_0x005e
        L_0x0040:
            java.lang.String r8 = "PresentPage"
            boolean r8 = r1.equals(r8)
            if (r8 == 0) goto L_0x005d
            r3 = 2
            goto L_0x005e
        L_0x004a:
            java.lang.String r8 = "Page"
            boolean r8 = r1.equals(r8)
            if (r8 == 0) goto L_0x005d
            goto L_0x005e
        L_0x0053:
            java.lang.String r8 = "TransparentPage"
            boolean r8 = r1.equals(r8)
            if (r8 == 0) goto L_0x005d
            r3 = 5
            goto L_0x005e
        L_0x005d:
            r3 = -1
        L_0x005e:
            switch(r3) {
                case 0: goto L_0x0071;
                case 1: goto L_0x006e;
                case 2: goto L_0x006b;
                case 3: goto L_0x0068;
                case 4: goto L_0x0065;
                case 5: goto L_0x0062;
                default: goto L_0x0061;
            }
        L_0x0061:
            goto L_0x00b9
        L_0x0062:
            java.lang.Class<com.autonavi.bundle.uitemplate.page.redesign.TransparentPage> r8 = com.autonavi.bundle.uitemplate.page.redesign.TransparentPage.class
            return r8
        L_0x0065:
            java.lang.Class<com.autonavi.bundle.uitemplate.page.redesign.BottomSheet> r8 = com.autonavi.bundle.uitemplate.page.redesign.BottomSheet.class
            return r8
        L_0x0068:
            java.lang.Class<com.autonavi.bundle.uitemplate.page.redesign.PresenterPageWithMap> r8 = com.autonavi.bundle.uitemplate.page.redesign.PresenterPageWithMap.class
            return r8
        L_0x006b:
            java.lang.Class<com.autonavi.bundle.uitemplate.page.redesign.PresentPage> r8 = com.autonavi.bundle.uitemplate.page.redesign.PresentPage.class
            return r8
        L_0x006e:
            java.lang.Class<com.autonavi.bundle.uitemplate.page.redesign.PageWithMap> r8 = com.autonavi.bundle.uitemplate.page.redesign.PageWithMap.class
            return r8
        L_0x0071:
            java.lang.Class<com.autonavi.bundle.uitemplate.page.redesign.Page> r8 = com.autonavi.bundle.uitemplate.page.redesign.Page.class
            return r8
        L_0x0074:
            boolean r1 = android.text.TextUtils.isEmpty(r8)
            if (r1 != 0) goto L_0x00b9
            int r1 = r8.hashCode()
            r2 = 217955430(0xcfdbc66, float:3.909421E-31)
            if (r1 == r2) goto L_0x00a1
            r2 = 837548523(0x31ebf9eb, float:6.8678125E-9)
            if (r1 == r2) goto L_0x0097
            r2 = 1331864990(0x4f62a59e, float:3.8025047E9)
            if (r1 == r2) goto L_0x008e
            goto L_0x00ab
        L_0x008e:
            java.lang.String r1 = "fullpage"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x00ab
            goto L_0x00ac
        L_0x0097:
            java.lang.String r1 = "mappage"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x00ab
            r3 = 2
            goto L_0x00ac
        L_0x00a1:
            java.lang.String r1 = "sidepage"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x00ab
            r3 = 1
            goto L_0x00ac
        L_0x00ab:
            r3 = -1
        L_0x00ac:
            switch(r3) {
                case 0: goto L_0x00b6;
                case 1: goto L_0x00b3;
                case 2: goto L_0x00b0;
                default: goto L_0x00af;
            }
        L_0x00af:
            goto L_0x00b9
        L_0x00b0:
            java.lang.Class<com.autonavi.bundle.uitemplate.page.redesign.PageWithMap> r8 = com.autonavi.bundle.uitemplate.page.redesign.PageWithMap.class
            return r8
        L_0x00b3:
            java.lang.Class<com.autonavi.bundle.uitemplate.page.redesign.PresentPage> r8 = com.autonavi.bundle.uitemplate.page.redesign.PresentPage.class
            return r8
        L_0x00b6:
            java.lang.Class<com.autonavi.bundle.uitemplate.page.redesign.Page> r8 = com.autonavi.bundle.uitemplate.page.redesign.Page.class
            return r8
        L_0x00b9:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bem.loadPage(android.net.Uri):java.lang.Class");
    }
}
