package defpackage;

import com.autonavi.annotation.Router;

@Router({"setting", "settings"})
/* renamed from: bda reason: default package */
/* compiled from: SettingRouter */
public class bda extends esk {
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004d A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean start(defpackage.ese r7) {
        /*
            r6 = this;
            boolean r0 = r7.c
            r1 = 0
            if (r0 == 0) goto L_0x00c5
            android.net.Uri r0 = r7.a
            java.lang.String r2 = "featureName"
            java.lang.String r2 = r0.getQueryParameter(r2)
            java.lang.String r3 = "Settings"
            boolean r3 = r3.equalsIgnoreCase(r2)
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x004e
            java.lang.String r3 = "pageid"
            java.lang.String r0 = r0.getQueryParameter(r3)
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 == 0) goto L_0x0036
            esb r0 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.minimap.bundle.setting.api.ISettingService> r3 = com.autonavi.minimap.bundle.setting.api.ISettingService.class
            esc r0 = r0.a(r3)
            com.autonavi.minimap.bundle.setting.api.ISettingService r0 = (com.autonavi.minimap.bundle.setting.api.ISettingService) r0
            if (r0 == 0) goto L_0x004a
            r0.a()
        L_0x0034:
            r0 = 1
            goto L_0x004b
        L_0x0036:
            java.lang.String r3 = "about"
            boolean r0 = r0.equalsIgnoreCase(r3)
            if (r0 == 0) goto L_0x004a
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r0 == 0) goto L_0x004a
            java.lang.String r3 = "amap.basemap.action.sys_about_page"
            r0.startPage(r3, r4)
            goto L_0x0034
        L_0x004a:
            r0 = 0
        L_0x004b:
            if (r0 == 0) goto L_0x004e
            return r5
        L_0x004e:
            java.lang.String r0 = "SetMap"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x006d
            esb r0 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.minimap.bundle.setting.api.ISettingService> r3 = com.autonavi.minimap.bundle.setting.api.ISettingService.class
            esc r0 = r0.a(r3)
            com.autonavi.minimap.bundle.setting.api.ISettingService r0 = (com.autonavi.minimap.bundle.setting.api.ISettingService) r0
            if (r0 == 0) goto L_0x0069
            r0.c()
            r0 = 1
            goto L_0x006a
        L_0x0069:
            r0 = 0
        L_0x006a:
            if (r0 == 0) goto L_0x006d
            return r5
        L_0x006d:
            java.lang.String r0 = "SetNavi"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0095
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            java.lang.String r2 = "plugin.minimap.quicknavi.navisetting"
            java.lang.String r3 = "com.autonavi.minimap"
            r0.<init>(r2, r3)
            java.lang.String r2 = "from"
            r3 = 2
            r0.putInt(r2, r3)
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r2 == 0) goto L_0x0091
            java.lang.String r3 = "amap.drive.action.navigation.prefer"
            r2.startPage(r3, r0)
            r0 = 1
            goto L_0x0092
        L_0x0091:
            r0 = 0
        L_0x0092:
            if (r0 == 0) goto L_0x0095
            return r5
        L_0x0095:
            java.lang.String r7 = r7.a(r1)
            java.lang.String r0 = "switch2internal"
            boolean r0 = r0.equals(r7)
            if (r0 == 0) goto L_0x00a5
            defpackage.bdc.a()
            return r5
        L_0x00a5:
            java.lang.String r0 = "switch2publish"
            boolean r0 = r0.equals(r7)
            if (r0 == 0) goto L_0x00b1
            defpackage.bdc.b()
            return r5
        L_0x00b1:
            java.lang.String r0 = "eggpage"
            boolean r7 = r0.equals(r7)
            if (r7 == 0) goto L_0x00c5
            bid r7 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r7 == 0) goto L_0x00c4
            java.lang.Class<com.autonavi.mine.page.setting.sysabout.page.ConfigPage> r0 = com.autonavi.mine.page.setting.sysabout.page.ConfigPage.class
            r7.startPage(r0, r4)
        L_0x00c4:
            return r5
        L_0x00c5:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bda.start(ese):boolean");
    }
}
