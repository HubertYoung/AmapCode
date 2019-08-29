package defpackage;

import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.widget.ui.ActionSheet;

/* renamed from: dgi reason: default package */
/* compiled from: OfflineNaviTtsDialogHelper */
public final class dgi {
    public a a;
    public int b = -1;
    public ActionSheet c;

    public final void a() {
        if (this.c != null && this.c.isShown()) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.dismissViewLayer(this.c);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x0225  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x024b A[Catch:{ Throwable -> 0x0279 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(final com.amap.bundle.drivecommon.mvp.view.DriveBasePage r13, final defpackage.dgl r14, boolean r15) {
        /*
            r12 = this;
            tw r0 = r14.a
            java.lang.String r0 = r0.c
            tw r1 = r14.a
            java.lang.String r1 = r1.l
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r2 = r2.getApplicationContext()
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            int r5 = r14.g()
            r6 = 9
            r7 = 10
            if (r5 == r7) goto L_0x01ec
            r8 = 64
            r9 = -42150(0xffffffffffff5b5a, float:NaN)
            r10 = 8
            r11 = 1
            if (r5 == r8) goto L_0x01af
            r8 = 4
            switch(r5) {
                case 0: goto L_0x0114;
                case 1: goto L_0x00e7;
                case 2: goto L_0x00e7;
                case 3: goto L_0x01ec;
                case 4: goto L_0x0034;
                case 5: goto L_0x01ec;
                default: goto L_0x0032;
            }
        L_0x0032:
            goto L_0x0217
        L_0x0034:
            dfx r5 = defpackage.dfx.a()
            java.lang.String r5 = r5.b()
            tw r6 = r14.a
            java.lang.String r6 = r6.f
            if (r5 == 0) goto L_0x0051
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0051
            java.lang.String r5 = "linzhilingyuyin"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x00c0
            return
        L_0x0051:
            boolean r5 = r14.c()
            if (r5 == 0) goto L_0x00aa
            java.lang.String r5 = defpackage.dfo.g()
            boolean r5 = r0.equals(r5)
            if (r5 == 0) goto L_0x0077
            java.lang.Integer r5 = java.lang.Integer.valueOf(r8)
            r4.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r7 = com.autonavi.minimap.R.string.offline_navi_tts_record_cancle
            java.lang.String r7 = r2.getString(r7)
            r5.<init>(r7)
            r3.add(r5)
            goto L_0x008d
        L_0x0077:
            r5 = 5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r7 = com.autonavi.minimap.R.string.offline_navi_tts_record_use
            java.lang.String r7 = r2.getString(r7)
            r5.<init>(r7)
            r3.add(r5)
        L_0x008d:
            int r5 = r12.b
            r7 = 1001(0x3e9, float:1.403E-42)
            if (r5 >= r7) goto L_0x00c0
            r5 = 7
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r7 = com.autonavi.minimap.R.string.offline_navi_tts_edit
            java.lang.String r7 = r2.getString(r7)
            r5.<init>(r7)
            r3.add(r5)
            goto L_0x00c0
        L_0x00aa:
            r5 = 6
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r7 = com.autonavi.minimap.R.string.offline_navi_tts_voice_use
            java.lang.String r7 = r2.getString(r7)
            r5.<init>(r7)
            r3.add(r5)
        L_0x00c0:
            if (r15 != 0) goto L_0x0217
            java.lang.String r5 = "linzhilingyuyin"
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x0217
            java.lang.Integer r5 = java.lang.Integer.valueOf(r10)
            r4.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r6 = com.autonavi.minimap.R.string.offline_navi_tts_voice_delete
            java.lang.String r6 = r2.getString(r6)
            r5.<init>(r6)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r9)
            r5.c = r6
            r3.add(r5)
            goto L_0x0217
        L_0x00e7:
            r5 = 2
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4.add(r5)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r6)
            r4.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r6 = com.autonavi.minimap.R.string.offline_navi_tts_down_pause
            java.lang.String r6 = r2.getString(r6)
            r5.<init>(r6)
            r3.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r6 = com.autonavi.minimap.R.string.offline_navi_tts_down_cancle
            java.lang.String r6 = r2.getString(r6)
            r5.<init>(r6)
            r3.add(r5)
            goto L_0x0217
        L_0x0114:
            boolean r5 = defpackage.dfs.a()
            r6 = 0
            if (r5 == 0) goto L_0x0154
            boolean r5 = defpackage.dfs.a(r2)
            if (r5 == 0) goto L_0x0154
            java.lang.Integer r5 = java.lang.Integer.valueOf(r7)
            r4.add(r5)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r11)
            r4.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r7 = com.autonavi.minimap.R.string.offline_navi_tts_quick_download
            java.lang.String r7 = r2.getString(r7)
            r5.<init>(r7)
            r3.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r7 = com.autonavi.minimap.R.string.offline_navi_tts_normal_download
            java.lang.String r7 = r2.getString(r7)
            r5.<init>(r7)
            r3.add(r5)
            java.lang.String r5 = "P00067"
            java.lang.String r7 = "B027"
            com.amap.bundle.statistics.LogManager.actionLogV2(r5, r7, r6, r6)
            goto L_0x0217
        L_0x0154:
            boolean r5 = defpackage.dfs.a()
            if (r5 == 0) goto L_0x0199
            boolean r5 = defpackage.dfs.b(r2)
            if (r5 == 0) goto L_0x0199
            int r5 = defpackage.aaw.b(r2)
            if (r5 != r8) goto L_0x0199
            java.lang.Integer r5 = java.lang.Integer.valueOf(r7)
            r4.add(r5)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r11)
            r4.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r7 = com.autonavi.minimap.R.string.offline_navi_tts_normal_download_pre
            java.lang.String r7 = r2.getString(r7)
            r5.<init>(r7)
            r3.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r7 = com.autonavi.minimap.R.string.offline_navi_tts_normal_download
            java.lang.String r7 = r2.getString(r7)
            r5.<init>(r7)
            r3.add(r5)
            java.lang.String r5 = "P00067"
            java.lang.String r7 = "B023"
            com.amap.bundle.statistics.LogManager.actionLogV2(r5, r7, r6, r6)
            goto L_0x0217
        L_0x0199:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r11)
            r4.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r6 = com.autonavi.minimap.R.string.offline_navi_tts_down_voice
            java.lang.String r6 = r2.getString(r6)
            r5.<init>(r6)
            r3.add(r5)
            goto L_0x0217
        L_0x01af:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r11)
            r4.add(r5)
            java.lang.String r5 = "linzhilingyuyin"
            tw r6 = r14.a
            java.lang.String r6 = r6.f
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x01c9
            java.lang.Integer r5 = java.lang.Integer.valueOf(r10)
            r4.add(r5)
        L_0x01c9:
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r6 = com.autonavi.minimap.R.string.offline_navi_tts_voice_updata
            java.lang.String r6 = r2.getString(r6)
            r5.<init>(r6)
            r3.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r6 = com.autonavi.minimap.R.string.offline_navi_tts_voice_delete
            java.lang.String r6 = r2.getString(r6)
            r5.<init>(r6)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r9)
            r5.c = r6
            r3.add(r5)
            goto L_0x0217
        L_0x01ec:
            r5 = 3
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4.add(r5)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r6)
            r4.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r6 = com.autonavi.minimap.R.string.offline_navi_tts_down_continue
            java.lang.String r6 = r2.getString(r6)
            r5.<init>(r6)
            r3.add(r5)
            com.autonavi.widget.ui.ActionSheet$a r5 = new com.autonavi.widget.ui.ActionSheet$a
            int r6 = com.autonavi.minimap.R.string.offline_navi_tts_down_cancle
            java.lang.String r6 = r2.getString(r6)
            r5.<init>(r6)
            r3.add(r5)
        L_0x0217:
            com.autonavi.widget.ui.ActionSheet$b r5 = new com.autonavi.widget.ui.ActionSheet$b
            r5.<init>(r2)
            r5.c = r3
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 == 0) goto L_0x0225
            goto L_0x0226
        L_0x0225:
            r0 = r1
        L_0x0226:
            r5.b = r0
            int r0 = com.autonavi.minimap.R.string.cancle
            java.lang.String r0 = r2.getString(r0)
            r5.d = r0
            dgi$2 r0 = new dgi$2
            r0.<init>(r13)
            r5.f = r0
            dgi$1 r0 = new dgi$1
            r0.<init>(r13, r4, r14)
            r5.g = r0
            dgi$3 r0 = new dgi$3
            r0.<init>()
            r5.e = r0
            android.app.Activity r0 = r13.getActivity()     // Catch:{ Throwable -> 0x0279 }
            if (r0 == 0) goto L_0x0278
            boolean r0 = r0.isFinishing()     // Catch:{ Throwable -> 0x0279 }
            if (r0 == 0) goto L_0x0252
            goto L_0x0278
        L_0x0252:
            r12.a()     // Catch:{ Throwable -> 0x0279 }
            if (r15 == 0) goto L_0x026c
            dfx r15 = defpackage.dfx.a()     // Catch:{ Throwable -> 0x0279 }
            java.lang.String r15 = r15.b()     // Catch:{ Throwable -> 0x0279 }
            if (r15 == 0) goto L_0x026c
            tw r14 = r14.a     // Catch:{ Throwable -> 0x0279 }
            java.lang.String r14 = r14.f     // Catch:{ Throwable -> 0x0279 }
            boolean r14 = r15.equals(r14)     // Catch:{ Throwable -> 0x0279 }
            if (r14 == 0) goto L_0x026c
            return
        L_0x026c:
            com.autonavi.widget.ui.ActionSheet r14 = r5.a()     // Catch:{ Throwable -> 0x0279 }
            r12.c = r14     // Catch:{ Throwable -> 0x0279 }
            com.autonavi.widget.ui.ActionSheet r14 = r12.c     // Catch:{ Throwable -> 0x0279 }
            r13.showViewLayer(r14)     // Catch:{ Throwable -> 0x0279 }
            return
        L_0x0278:
            return
        L_0x0279:
            r13 = move-exception
            r13.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dgi.a(com.amap.bundle.drivecommon.mvp.view.DriveBasePage, dgl, boolean):void");
    }
}
