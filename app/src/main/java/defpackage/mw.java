package defpackage;

import com.amap.bundle.drive.common.dialog.DriveDlgBaseManager;

/* renamed from: mw reason: default package */
/* compiled from: NaviDlgManager */
public final class mw extends DriveDlgBaseManager {
    public String e;

    public mw(bid bid, String str) {
        super(bid);
        this.e = str;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(final com.amap.bundle.drive.common.dialog.DriveDlgBaseManager.DialogId r6, java.lang.Object... r7) {
        /*
            r5 = this;
            int[] r0 = defpackage.mw.AnonymousClass4.a
            int r1 = r6.ordinal()
            r0 = r0[r1]
            r1 = 1
            r2 = 0
            switch(r0) {
                case 1: goto L_0x004e;
                case 2: goto L_0x000f;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x008e
        L_0x000f:
            r0 = r7[r2]
            java.lang.String r0 = (java.lang.String) r0
            r7 = r7[r1]
            android.content.DialogInterface$OnCancelListener r7 = (android.content.DialogInterface.OnCancelListener) r7
            boolean r3 = r5.a(r6)
            if (r3 != 0) goto L_0x008e
            com.autonavi.map.widget.ProgressDlg r3 = new com.autonavi.map.widget.ProgressDlg
            bid r4 = r5.a
            android.app.Activity r4 = r4.getActivity()
            r3.<init>(r4)
            r3.setCancelable(r1)
            r3.setCanceledOnTouchOutside(r2)
            android.view.Window r1 = r3.getWindow()
            if (r1 == 0) goto L_0x003c
            android.view.Window r1 = r3.getWindow()
            r2 = 3
            r1.setVolumeControlStream(r2)
        L_0x003c:
            r3.setOnCancelListener(r7)
            mw$3 r7 = new mw$3
            r7.<init>(r6)
            r3.setOnDismissListener(r7)
            r3.setMessage(r0)
            r3.show()
            goto L_0x008f
        L_0x004e:
            r0 = r7[r2]
            com.amap.bundle.drive.common.dialog.DriveDlgBaseManager$a r0 = (com.amap.bundle.drive.common.dialog.DriveDlgBaseManager.a) r0
            r7 = r7[r1]
            com.amap.bundle.drive.common.dialog.DriveDlgBaseManager$a r7 = (com.amap.bundle.drive.common.dialog.DriveDlgBaseManager.a) r7
            java.lang.String r1 = r5.e
            java.lang.String r2 = "motorbike"
            boolean r1 = android.text.TextUtils.equals(r1, r2)
            if (r1 == 0) goto L_0x0063
            int r1 = com.autonavi.minimap.R.layout.navi_motorbike_declare
            goto L_0x0072
        L_0x0063:
            java.lang.String r1 = r5.e
            java.lang.String r2 = "truck"
            boolean r1 = android.text.TextUtils.equals(r1, r2)
            if (r1 == 0) goto L_0x0070
            int r1 = com.autonavi.minimap.R.layout.navi_truck_declare
            goto L_0x0072
        L_0x0070:
            int r1 = com.autonavi.minimap.R.layout.navi_declare
        L_0x0072:
            com.autonavi.minimap.widget.ConfirmDlg r3 = new com.autonavi.minimap.widget.ConfirmDlg
            bid r2 = r5.a
            android.app.Activity r2 = r2.getActivity()
            mw$1 r4 = new mw$1
            r4.<init>(r7, r0)
            r3.<init>(r2, r4, r1)
            mw$2 r7 = new mw$2
            r7.<init>(r6)
            r3.setOnDismissListener(r7)
            r3.show()
            goto L_0x008f
        L_0x008e:
            r3 = 0
        L_0x008f:
            if (r3 == 0) goto L_0x00ba
            java.util.Map r7 = r5.c
            r7.put(r6, r3)
            ku r7 = defpackage.ku.a()
            java.lang.String r0 = "NaviMonitor"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "[DriveDialogManager]#showDialog#dialogPool.put("
            r1.<init>(r2)
            r1.append(r6)
            java.lang.String r6 = ","
            r1.append(r6)
            r1.append(r3)
            java.lang.String r6 = ")"
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            r7.c(r0, r6)
        L_0x00ba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.mw.a(com.amap.bundle.drive.common.dialog.DriveDlgBaseManager$DialogId, java.lang.Object[]):void");
    }
}
