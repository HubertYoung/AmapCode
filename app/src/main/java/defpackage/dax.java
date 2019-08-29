package defpackage;

import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: dax reason: default package */
/* compiled from: MimeUpdateUtil */
public final class dax {
    private static final Object b = new Object();
    private static volatile dax c;
    public a a = null;

    /* renamed from: dax$a */
    /* compiled from: MimeUpdateUtil */
    public static class a extends AsyncTask<Integer, Integer, Integer> {
        private int a = 0;
        private final ArrayList<com.autonavi.minimap.bundle.msgbox.api.IMsgboxService.a> b = new ArrayList<>();

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onPostExecute(Object obj) {
            Integer num = (Integer) obj;
            super.onPostExecute(num);
            if (this.b != null) {
                Iterator<com.autonavi.minimap.bundle.msgbox.api.IMsgboxService.a> it = this.b.iterator();
                while (it.hasNext()) {
                    if (it.next() != null) {
                        num.intValue();
                    }
                }
            }
            if (this.b != null) {
                this.b.clear();
            }
        }

        public a(int i) {
            this.a = i;
        }

        public final void a(com.autonavi.minimap.bundle.msgbox.api.IMsgboxService.a aVar) {
            if (this.b != null) {
                this.b.add(aVar);
            }
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x00a1  */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x00a7  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final /* synthetic */ java.lang.Object doInBackground(java.lang.Object[] r9) {
            /*
                r8 = this;
                com.amap.bundle.mapstorage.MapSharePreference r9 = new com.amap.bundle.mapstorage.MapSharePreference
                com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r0 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
                r9.<init>(r0)
                java.lang.Class<com.autonavi.minimap.offline.map.inter.IOfflineManager> r0 = com.autonavi.minimap.offline.map.inter.IOfflineManager.class
                java.lang.Object r0 = defpackage.ank.a(r0)
                com.autonavi.minimap.offline.map.inter.IOfflineManager r0 = (com.autonavi.minimap.offline.map.inter.IOfflineManager) r0
                r1 = 1
                r2 = 0
                if (r0 == 0) goto L_0x007c
                java.lang.String r3 = r0.getOfflineDateUpdateVer()
                boolean r4 = android.text.TextUtils.isEmpty(r3)
                if (r4 != 0) goto L_0x0032
                java.lang.String r4 = "MsgboxOfflineDataVer"
                java.lang.String r5 = ""
                java.lang.String r4 = r9.getStringValue(r4, r5)
                boolean r4 = r4.contentEquals(r3)
                if (r4 != 0) goto L_0x0032
                java.lang.String r4 = "MsgboxOfflineDataVer"
                r9.putStringValue(r4, r3)
                r3 = 1
                goto L_0x0033
            L_0x0032:
                r3 = 0
            L_0x0033:
                java.lang.String r0 = r0.getRouteEnlargeUpdateVer()
                boolean r4 = android.text.TextUtils.isEmpty(r0)
                if (r4 != 0) goto L_0x0052
                java.lang.String r4 = "MsgboxRouteEnlargeVer"
                java.lang.String r5 = ""
                java.lang.String r4 = r9.getStringValue(r4, r5)
                boolean r4 = r4.contentEquals(r0)
                if (r4 != 0) goto L_0x0052
                java.lang.String r4 = "MsgboxRouteEnlargeVer"
                r9.putStringValue(r4, r0)
                r0 = 1
                goto L_0x0053
            L_0x0052:
                r0 = 0
            L_0x0053:
                java.lang.Class<com.amap.bundle.tripgroup.api.IVoicePackageManager> r4 = com.amap.bundle.tripgroup.api.IVoicePackageManager.class
                java.lang.Object r4 = defpackage.ank.a(r4)
                com.amap.bundle.tripgroup.api.IVoicePackageManager r4 = (com.amap.bundle.tripgroup.api.IVoicePackageManager) r4
                if (r4 == 0) goto L_0x007e
                java.lang.String r4 = r4.getNaviTtsUpdateVer()
                boolean r5 = android.text.TextUtils.isEmpty(r4)
                if (r5 != 0) goto L_0x007e
                java.lang.String r5 = "MsgboxNaviTtsVer"
                java.lang.String r6 = ""
                java.lang.String r5 = r9.getStringValue(r5, r6)
                boolean r5 = r5.contentEquals(r4)
                if (r5 != 0) goto L_0x007e
                java.lang.String r5 = "MsgboxNaviTtsVer"
                r9.putStringValue(r5, r4)
                r4 = 1
                goto L_0x007f
            L_0x007c:
                r0 = 0
                r3 = 0
            L_0x007e:
                r4 = 0
            L_0x007f:
                java.lang.String r5 = "NewFeatureTriggerMainPage756"
                boolean r5 = r9.getBooleanValue(r5, r2)
                r5 = r5 ^ r1
                java.lang.String r6 = "map_skin_indicator_1"
                boolean r6 = r9.getBooleanValue(r6, r1)
                java.lang.String r7 = "map_skin_indicator_2"
                boolean r7 = r9.getBooleanValue(r7, r1)
                if (r6 == 0) goto L_0x0098
                if (r7 == 0) goto L_0x0098
                r6 = 1
                goto L_0x0099
            L_0x0098:
                r6 = 0
            L_0x0099:
                java.lang.String r7 = "main_header_red_flag"
                boolean r7 = r9.getBooleanValue(r7, r2)
                if (r7 == 0) goto L_0x00a7
                java.lang.String r7 = "main_header_red_flag"
                r9.putBooleanValue(r7, r2)
                goto L_0x00a8
            L_0x00a7:
                r1 = 0
            L_0x00a8:
                int r9 = r8.a
                int r9 = r9 + r3
                int r9 = r9 + r0
                int r9 = r9 + r4
                int r9 = r9 + r5
                int r9 = r9 + r6
                int r9 = r9 + r1
                java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.dax.a.doInBackground(java.lang.Object[]):java.lang.Object");
        }
    }

    private dax() {
    }

    public static dax a() {
        if (c == null) {
            synchronized (b) {
                try {
                    c = new dax();
                }
            }
        }
        return c;
    }
}
