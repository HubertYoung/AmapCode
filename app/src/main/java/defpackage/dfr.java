package defpackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;
import java.io.File;
import java.lang.ref.WeakReference;

/* renamed from: dfr reason: default package */
/* compiled from: PPHelperController */
public final class dfr {

    /* renamed from: dfr$a */
    /* compiled from: PPHelperController */
    public static class a extends defpackage.dgw.a {
        private WeakReference<OfflineNaviTtsFragment> a;
        private WeakReference<Context> b;
        private WeakReference<dgl> c;
        private String d;
        private String e;
        private String f;

        public a(OfflineNaviTtsFragment offlineNaviTtsFragment, Context context, dgl dgl, String str, String str2) {
            this.a = new WeakReference<>(offlineNaviTtsFragment);
            this.b = new WeakReference<>(context);
            this.c = new WeakReference<>(dgl);
            this.d = str;
            this.e = dgl.a();
            this.f = str2;
        }

        public final Object doBackground() throws Exception {
            if (((Context) this.b.get()) == null) {
                return null;
            }
            File file = new File(this.e);
            dfr.a(this.d, file);
            if (file.exists()) {
                dgl dgl = (dgl) this.c.get();
                if (dgl == null) {
                    dgl = dfs.a(this.f);
                }
                if (dgl == null) {
                    return null;
                }
                if (TextUtils.isEmpty(dgl.a.i) || !dgl.a(this.e, dgl.a.i)) {
                    file.delete();
                    return null;
                }
                File file2 = new File(this.d);
                if (file2.exists()) {
                    file2.delete();
                }
                dgl.a(dgl.a.g);
                dgl.a(4);
                dgm.a().a(true);
                final OfflineNaviTtsFragment offlineNaviTtsFragment = (OfflineNaviTtsFragment) this.a.get();
                if (offlineNaviTtsFragment != null) {
                    offlineNaviTtsFragment.getActivity().runOnUiThread(new Runnable() {
                        public final void run() {
                            offlineNaviTtsFragment.refreshListView();
                        }
                    });
                }
            }
            return null;
        }
    }

    public static void a(Context context, dgl dgl) {
        if (dgl != null) {
            if (!dfp.a(dgl)) {
                ToastHelper.showToast(context.getApplicationContext().getString(R.string.offline_storage_noenough));
                return;
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setPackage("com.pp.assistant");
            intent.setData(Uri.parse("pp://www.25pp.com/highspeed/"));
            intent.putExtra("ex_event", 1002);
            StringBuilder sb = new StringBuilder(dgl.a.d);
            sb.append("?md5=");
            sb.append(dgl.a.i);
            sb.append("&size=");
            sb.append(dgl.a.g);
            intent.putExtra("ex_url", sb.toString());
            String str = dgl.a.l;
            if (TextUtils.isEmpty(str)) {
                str = dgl.a.c;
            }
            intent.putExtra("ex_fname", str);
            intent.putExtra("ex_task_id", dgl.a.i);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                ToastHelper.showLongToast("高速下载失败，请尝试普通下载");
            }
        }
    }

    /* JADX WARNING: type inference failed for: r6v2 */
    /* JADX WARNING: type inference failed for: r6v3, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r6v5, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r1v6, types: [java.io.Closeable, java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v10, types: [java.io.OutputStream, java.io.Closeable, java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r6v7 */
    /* JADX WARNING: type inference failed for: r7v14, types: [java.io.Closeable, java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r6v10 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r6v13 */
    /* JADX WARNING: type inference failed for: r6v14 */
    /* JADX WARNING: type inference failed for: r6v15 */
    /* JADX WARNING: type inference failed for: r6v16 */
    /* JADX WARNING: type inference failed for: r6v17 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r2v16 */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v1
      assigns: []
      uses: []
      mth insns count: 84
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
    /* JADX WARNING: Unknown variable types count: 17 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(java.lang.String r6, java.io.File r7) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r6)
            boolean r6 = r7.exists()
            if (r6 == 0) goto L_0x000e
            r7.delete()
        L_0x000e:
            r6 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0068, all -> 0x0063 }
            r1.<init>(r0)     // Catch:{ Exception -> 0x0068, all -> 0x0063 }
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x005d, all -> 0x0059 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x005d, all -> 0x0059 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
            r2.<init>(r7)     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
            java.io.BufferedOutputStream r7 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x004e, all -> 0x004c }
            r7.<init>(r2)     // Catch:{ Exception -> 0x004e, all -> 0x004c }
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]     // Catch:{ Exception -> 0x0046, all -> 0x0042 }
        L_0x0027:
            int r3 = r0.read(r6)     // Catch:{ Exception -> 0x0046, all -> 0x0042 }
            if (r3 <= 0) goto L_0x0035
            r4 = 0
            r7.write(r6, r4, r3)     // Catch:{ Exception -> 0x0046, all -> 0x0042 }
            r7.flush()     // Catch:{ Exception -> 0x0046, all -> 0x0042 }
            goto L_0x0027
        L_0x0035:
            defpackage.ahe.a(r1)
            defpackage.ahe.a(r0)
            defpackage.ahe.a(r2)
            defpackage.ahe.a(r7)
            return
        L_0x0042:
            r6 = move-exception
            r5 = r7
            r7 = r6
            goto L_0x007f
        L_0x0046:
            r6 = move-exception
            r5 = r7
            r7 = r6
            r6 = r1
            r1 = r5
            goto L_0x006c
        L_0x004c:
            r7 = move-exception
            goto L_0x0080
        L_0x004e:
            r7 = move-exception
            r5 = r1
            r1 = r6
            r6 = r5
            goto L_0x006c
        L_0x0053:
            r7 = move-exception
            r2 = r6
            goto L_0x0080
        L_0x0056:
            r7 = move-exception
            r2 = r6
            goto L_0x0060
        L_0x0059:
            r7 = move-exception
            r0 = r6
            r2 = r0
            goto L_0x0080
        L_0x005d:
            r7 = move-exception
            r0 = r6
            r2 = r0
        L_0x0060:
            r6 = r1
            r1 = r2
            goto L_0x006c
        L_0x0063:
            r7 = move-exception
            r0 = r6
            r1 = r0
            r2 = r1
            goto L_0x0080
        L_0x0068:
            r7 = move-exception
            r0 = r6
            r1 = r0
            r2 = r1
        L_0x006c:
            r7.printStackTrace()     // Catch:{ all -> 0x007c }
            defpackage.ahe.a(r6)
            defpackage.ahe.a(r0)
            defpackage.ahe.a(r2)
            defpackage.ahe.a(r1)
            return
        L_0x007c:
            r7 = move-exception
            r5 = r1
            r1 = r6
        L_0x007f:
            r6 = r5
        L_0x0080:
            defpackage.ahe.a(r1)
            defpackage.ahe.a(r0)
            defpackage.ahe.a(r2)
            defpackage.ahe.a(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dfr.a(java.lang.String, java.io.File):void");
    }
}
