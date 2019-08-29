package defpackage;

import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK;
import com.autonavi.widget.ui.AlertView;
import java.util.Iterator;

/* renamed from: dfp reason: default package */
/* compiled from: OfflineNaviTtsUtil */
public final class dfp {

    /* renamed from: dfp$a */
    /* compiled from: OfflineNaviTtsUtil */
    public interface a {
        void a();
    }

    public static boolean a(dgl dgl) {
        if (dgl == null) {
            return true;
        }
        DriveOfflineSDK.e();
        if (DriveOfflineSDK.a(dgl)) {
            return true;
        }
        float a2 = dhd.a((float) dgl.a.g);
        float h = (float) dhd.h();
        DriveOfflineSDK.e();
        Iterator<dgl> it = DriveOfflineSDK.l().iterator();
        float f = 0.0f;
        while (it.hasNext()) {
            dgl next = it.next();
            int g = next.g();
            if (!(g == 4 || g == 64 || g == 5 || g == 10)) {
                f += dhd.a((float) next.a.g);
            }
        }
        if ((h - f) - a2 > 0.0f) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r7v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v7, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r7v4 */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r5v1, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r1v9, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r7v5 */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r7v8 */
    /* JADX WARNING: type inference failed for: r7v9, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r5v5, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r7v12 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r7v13 */
    /* JADX WARNING: type inference failed for: r7v14 */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r5v9 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r7v4
      assigns: []
      uses: []
      mth insns count: 81
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
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0082 A[SYNTHETIC, Splitter:B:42:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x008c A[SYNTHETIC, Splitter:B:47:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x009a A[SYNTHETIC, Splitter:B:56:0x009a] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00a4 A[SYNTHETIC, Splitter:B:61:0x00a4] */
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r7) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            dgu r1 = defpackage.dgu.a()
            java.lang.String r1 = r1.d()
            r0.append(r1)
            java.lang.String r1 = java.io.File.separator
            r0.append(r1)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = 0
            if (r1 == 0) goto L_0x0024
            return r2
        L_0x0024:
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r1 = r1.exists()
            r3 = 1
            if (r1 == 0) goto L_0x0031
            return r3
        L_0x0031:
            r1 = 0
            android.content.Context r4 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()     // Catch:{ Exception -> 0x007b, all -> 0x0078 }
            if (r4 != 0) goto L_0x0039
            return r2
        L_0x0039:
            android.content.res.AssetManager r4 = r4.getAssets()     // Catch:{ Exception -> 0x007b, all -> 0x0078 }
            if (r4 != 0) goto L_0x0040
            return r2
        L_0x0040:
            java.io.InputStream r7 = r4.open(r7)     // Catch:{ Exception -> 0x007b, all -> 0x0078 }
            int r4 = r7.available()     // Catch:{ Exception -> 0x0074, all -> 0x0072 }
            byte[] r4 = new byte[r4]     // Catch:{ Exception -> 0x0074, all -> 0x0072 }
            r7.read(r4)     // Catch:{ Exception -> 0x0074, all -> 0x0072 }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0074, all -> 0x0072 }
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x0074, all -> 0x0072 }
            r6.<init>(r0)     // Catch:{ Exception -> 0x0074, all -> 0x0072 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0074, all -> 0x0072 }
            r5.write(r4)     // Catch:{ Exception -> 0x0070, all -> 0x006e }
            if (r7 == 0) goto L_0x0064
            r7.close()     // Catch:{ Exception -> 0x0060 }
            goto L_0x0064
        L_0x0060:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0064:
            r5.close()     // Catch:{ Exception -> 0x0068 }
            goto L_0x006c
        L_0x0068:
            r7 = move-exception
            r7.printStackTrace()
        L_0x006c:
            r2 = 1
            goto L_0x0094
        L_0x006e:
            r0 = move-exception
            goto L_0x0097
        L_0x0070:
            r0 = move-exception
            goto L_0x0076
        L_0x0072:
            r0 = move-exception
            goto L_0x0098
        L_0x0074:
            r0 = move-exception
            r5 = r1
        L_0x0076:
            r1 = r7
            goto L_0x007d
        L_0x0078:
            r0 = move-exception
            r7 = r1
            goto L_0x0098
        L_0x007b:
            r0 = move-exception
            r5 = r1
        L_0x007d:
            r0.printStackTrace()     // Catch:{ all -> 0x0095 }
            if (r1 == 0) goto L_0x008a
            r1.close()     // Catch:{ Exception -> 0x0086 }
            goto L_0x008a
        L_0x0086:
            r7 = move-exception
            r7.printStackTrace()
        L_0x008a:
            if (r5 == 0) goto L_0x0094
            r5.close()     // Catch:{ Exception -> 0x0090 }
            goto L_0x0094
        L_0x0090:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0094:
            return r2
        L_0x0095:
            r0 = move-exception
            r7 = r1
        L_0x0097:
            r1 = r5
        L_0x0098:
            if (r7 == 0) goto L_0x00a2
            r7.close()     // Catch:{ Exception -> 0x009e }
            goto L_0x00a2
        L_0x009e:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00a2:
            if (r1 == 0) goto L_0x00ac
            r1.close()     // Catch:{ Exception -> 0x00a8 }
            goto L_0x00ac
        L_0x00a8:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00ac:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dfp.a(java.lang.String):boolean");
    }

    public static void a(final a aVar) {
        final bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            com.autonavi.widget.ui.AlertView.a aVar2 = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
            aVar2.a(R.string.sd_unavailable);
            aVar2.a(R.string.switch_sd, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    FileUtil.setClearDataOperation(true);
                    Iterator<dgn> it = dgr.a().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        dgn next = it.next();
                        if (!next.d) {
                            PathManager.a().a(DirType.DRIVE_VOICE, next.a);
                            break;
                        }
                    }
                    dgu.a().c();
                    IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
                    if (iVoicePackageManager != null) {
                        iVoicePackageManager.restoreDefaultTTS();
                    }
                    pageContext.dismissViewLayer(alertView);
                    if (aVar != null) {
                        aVar.a();
                    }
                }
            });
            aVar2.b(R.string.cancel, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                }
            });
            aVar2.a(true);
            AlertView a2 = aVar2.a();
            pageContext.showViewLayer(a2);
            a2.startAnimation();
        }
    }
}
