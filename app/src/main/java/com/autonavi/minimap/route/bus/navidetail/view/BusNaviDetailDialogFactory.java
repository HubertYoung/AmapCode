package com.autonavi.minimap.route.bus.navidetail.view;

import com.autonavi.minimap.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

public final class BusNaviDetailDialogFactory {
    private static Map<String, Integer> a;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogType {
    }

    public interface OnDialogClickListener {

        @Retention(RetentionPolicy.SOURCE)
        public @interface ClickType {
        }

        void a(int i);
    }

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put("dialog_type_declare_bus", Integer.valueOf(R.layout.bus_declare));
        a.put("dialog_type_declare_foot", Integer.valueOf(R.layout.onfoot_declare));
        a.put("dialog_type_declare_dest", Integer.valueOf(R.layout.ondest_declare));
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.app.Dialog a(android.app.Activity r3, java.lang.String r4, final com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory.OnDialogClickListener r5) {
        /*
            int r0 = r4.hashCode()
            r1 = -997436923(0xffffffffc48c5205, float:-1122.5631)
            r2 = 1
            if (r0 == r1) goto L_0x0038
            r1 = -997367855(0xffffffffc48d5fd1, float:-1130.9943)
            if (r0 == r1) goto L_0x002e
            r1 = -724913475(0xffffffffd4cab2bd, float:-6.964657E12)
            if (r0 == r1) goto L_0x0024
            r1 = 620156635(0x24f6d6db, float:1.0704947E-16)
            if (r0 == r1) goto L_0x001a
            goto L_0x0042
        L_0x001a:
            java.lang.String r0 = "dialog_type_progress"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0042
            r0 = 0
            goto L_0x0043
        L_0x0024:
            java.lang.String r0 = "dialog_type_declare_bus"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0042
            r0 = 1
            goto L_0x0043
        L_0x002e:
            java.lang.String r0 = "dialog_type_declare_foot"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0042
            r0 = 2
            goto L_0x0043
        L_0x0038:
            java.lang.String r0 = "dialog_type_declare_dest"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0042
            r0 = 3
            goto L_0x0043
        L_0x0042:
            r0 = -1
        L_0x0043:
            switch(r0) {
                case 0: goto L_0x005f;
                case 1: goto L_0x0048;
                case 2: goto L_0x0048;
                case 3: goto L_0x0048;
                default: goto L_0x0046;
            }
        L_0x0046:
            r0 = 0
            goto L_0x0071
        L_0x0048:
            com.autonavi.minimap.widget.ConfirmDlg r0 = new com.autonavi.minimap.widget.ConfirmDlg
            com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory$2 r1 = new com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory$2
            r1.<init>(r5)
            java.util.Map<java.lang.String, java.lang.Integer> r5 = a
            java.lang.Object r4 = r5.get(r4)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            r0.<init>(r3, r1, r4)
            goto L_0x0071
        L_0x005f:
            com.autonavi.map.widget.ProgressDlg r0 = new com.autonavi.map.widget.ProgressDlg
            java.lang.String r4 = ""
            r0.<init>(r3, r4)
            r0.setCancelable(r2)
            com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory$1 r3 = new com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory$1
            r3.<init>(r5)
            r0.setOnCancelListener(r3)
        L_0x0071:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory.a(android.app.Activity, java.lang.String, com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory$OnDialogClickListener):android.app.Dialog");
    }
}
