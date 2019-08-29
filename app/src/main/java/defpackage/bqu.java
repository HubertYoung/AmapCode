package defpackage;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.internal.view.SupportMenu;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import com.autonavi.io.monitor.AccessError;
import com.autonavi.io.monitor.annotation.AccessManifest;
import com.autonavi.io.monitor.annotation.AccessMode;
import com.autonavi.io.monitor.annotation.AccessPath;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: bqu reason: default package */
/* compiled from: SDGuard */
public class bqu implements bqr {
    /* access modifiers changed from: private */
    public static final String c = "bqu";
    protected boolean a = true;
    protected int b = 0;
    private Context d;
    private Map<AccessMode, List<String>> e = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public bqq f;
    private long g = 5000;
    private HandlerThread h;
    private Handler i;
    /* access modifiers changed from: private */
    public boolean j;

    public bqu(Context context) {
        this.d = context;
        b();
        this.f = new bqq(this.d);
        this.h = new HandlerThread(c);
        this.h.start();
        this.i = new Handler(this.h.getLooper());
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.h != null) {
            this.h.quit();
        }
        super.finalize();
    }

    private void b() {
        AccessPath[] value;
        List list;
        AccessManifest accessManifest = (AccessManifest) getClass().getAnnotation(AccessManifest.class);
        if (accessManifest != null) {
            for (AccessPath accessPath : accessManifest.value()) {
                String value2 = accessPath.value();
                if (!value2.isEmpty()) {
                    if (!accessPath.isFile() && !value2.endsWith("/")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(value2);
                        sb.append("/");
                        value2 = sb.toString();
                    }
                    AccessMode[] mode = accessPath.mode();
                    if (mode.length == 0) {
                        mode = accessPath.isFile() ? AccessMode.defaultFileModes() : AccessMode.defaultModes();
                    } else {
                        int binarySearch = Arrays.binarySearch(mode, AccessMode.OPEN);
                        if (binarySearch != -1) {
                            AccessMode[] accessModeArr = new AccessMode[(mode.length + 1)];
                            System.arraycopy(mode, 0, accessModeArr, 0, mode.length);
                            accessModeArr[binarySearch] = AccessMode.OPEN_R;
                            accessModeArr[mode.length] = AccessMode.OPEN_W;
                        }
                    }
                    for (AccessMode accessMode : mode) {
                        if (!this.e.containsKey(accessMode)) {
                            list = new CopyOnWriteArrayList();
                            this.e.put(accessMode, list);
                        } else {
                            list = this.e.get(accessMode);
                        }
                        list.add(value2);
                    }
                }
            }
            this.a = !this.e.isEmpty();
        }
    }

    public final void b(String str, int i2) {
        if (!str.endsWith("/")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("/");
            str = sb.toString();
        }
        if (i2 == 0) {
            i2 = 31;
        }
        if ((i2 & 1) == 1) {
            a(str, AccessMode.MK_DIR);
        }
        if ((i2 & 2) == 2) {
            a(str, AccessMode.REMOVE);
        }
        if ((i2 & 4) == 4) {
            a(str, AccessMode.RENAME);
        }
        if ((i2 & 8) == 8) {
            a(str, AccessMode.OPEN_R);
        }
        if ((i2 & 16) == 16) {
            a(str, AccessMode.OPEN_W);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0037, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void a(java.lang.String r3, com.autonavi.io.monitor.annotation.AccessMode r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 == 0) goto L_0x0036
            boolean r0 = r3.isEmpty()     // Catch:{ all -> 0x0033 }
            if (r0 == 0) goto L_0x000a
            goto L_0x0036
        L_0x000a:
            java.util.Map<com.autonavi.io.monitor.annotation.AccessMode, java.util.List<java.lang.String>> r0 = r2.e     // Catch:{ all -> 0x0033 }
            boolean r0 = r0.containsKey(r4)     // Catch:{ all -> 0x0033 }
            if (r0 != 0) goto L_0x001d
            java.util.concurrent.CopyOnWriteArrayList r0 = new java.util.concurrent.CopyOnWriteArrayList     // Catch:{ all -> 0x0033 }
            r0.<init>()     // Catch:{ all -> 0x0033 }
            java.util.Map<com.autonavi.io.monitor.annotation.AccessMode, java.util.List<java.lang.String>> r1 = r2.e     // Catch:{ all -> 0x0033 }
            r1.put(r4, r0)     // Catch:{ all -> 0x0033 }
            goto L_0x002e
        L_0x001d:
            java.util.Map<com.autonavi.io.monitor.annotation.AccessMode, java.util.List<java.lang.String>> r0 = r2.e     // Catch:{ all -> 0x0033 }
            java.lang.Object r4 = r0.get(r4)     // Catch:{ all -> 0x0033 }
            r0 = r4
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x0033 }
            boolean r4 = r0.contains(r3)     // Catch:{ all -> 0x0033 }
            if (r4 == 0) goto L_0x002e
            monitor-exit(r2)
            return
        L_0x002e:
            r0.add(r3)     // Catch:{ all -> 0x0033 }
            monitor-exit(r2)
            return
        L_0x0033:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        L_0x0036:
            monitor-exit(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bqu.a(java.lang.String, com.autonavi.io.monitor.annotation.AccessMode):void");
    }

    public final void b(String str) {
        a(str, AccessMode.REMOVE);
        a(str, AccessMode.RENAME);
        a(str, AccessMode.OPEN_R);
        a(str, AccessMode.OPEN_W);
    }

    public final void a(final String str, final int i2) {
        final AccessError accessError = new AccessError(str, AccessMode.MK_DIR, null);
        this.i.post(new Runnable() {
            public final void run() {
                if (bqu.this.j) {
                    bqu.c;
                    StringBuilder sb = new StringBuilder("MkDir: [");
                    sb.append(i2);
                    sb.append("] ");
                    sb.append(str);
                }
                bqu.a(bqu.this, str, AccessMode.MK_DIR, accessError);
            }
        });
    }

    public final void a(final String str) {
        final AccessError accessError = new AccessError(str, AccessMode.REMOVE, null);
        this.i.post(new Runnable() {
            public final void run() {
                if (bqu.this.j) {
                    bqu.c;
                    new StringBuilder("Remove: ").append(str);
                }
                bqu.a(bqu.this, str, AccessMode.REMOVE, accessError);
            }
        });
    }

    public final void a(String str, String str2) {
        final AccessError accessError = new AccessError(str, AccessMode.RENAME, null);
        final AccessError accessError2 = new AccessError(str2, AccessMode.RENAME, null);
        Handler handler = this.i;
        final String str3 = str;
        final String str4 = str2;
        AnonymousClass3 r0 = new Runnable() {
            public final void run() {
                if (bqu.this.j) {
                    bqu.c;
                    StringBuilder sb = new StringBuilder("Rename: ");
                    sb.append(str3);
                    sb.append(" -> ");
                    sb.append(str4);
                }
                bqu.a(bqu.this, str3, AccessMode.RENAME, accessError);
                bqu.a(bqu.this, str4, AccessMode.RENAME, accessError2);
            }
        };
        handler.post(r0);
    }

    public final void a(String str, int i2, int i3) {
        final AccessError accessError = new AccessError(str, null, null);
        Handler handler = this.i;
        final int i4 = i2;
        final int i5 = i3;
        final String str2 = str;
        AnonymousClass4 r0 = new Runnable() {
            public final void run() {
                if (bqu.this.j) {
                    bqu.c;
                    StringBuilder sb = new StringBuilder("Open: [");
                    sb.append(i4);
                    sb.append("][");
                    sb.append(i5);
                    sb.append("] ");
                    sb.append(str2);
                }
                if (i5 == 0) {
                    accessError.setMode(AccessMode.OPEN_R);
                    bqu.a(bqu.this, str2, AccessMode.OPEN_R, accessError);
                }
                if (i5 == 384) {
                    accessError.setMode(AccessMode.OPEN_W);
                    bqu.a(bqu.this, str2, AccessMode.OPEN_W, accessError);
                }
            }
        };
        handler.post(r0);
    }

    private String c() {
        if (this.e.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < 87; i2++) {
            sb.append("*");
        }
        String str = null;
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory());
            sb2.append("/");
            str = sb2.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        TreeMap treeMap = new TreeMap(new bqv());
        treeMap.putAll(this.e);
        for (Entry entry : treeMap.entrySet()) {
            AccessMode accessMode = (AccessMode) entry.getKey();
            for (String str2 : (List) entry.getValue()) {
                sb.append("\n* [");
                sb.append(accessMode);
                sb.append("] ");
                if (str != null) {
                    sb.append(str);
                }
                sb.append(str2);
            }
        }
        sb.append("\n");
        for (int i3 = 0; i3 < 87; i3++) {
            sb.append("*");
        }
        return sb.toString();
    }

    static /* synthetic */ void a(bqu bqu, String str, AccessMode accessMode, AccessError accessError) {
        boolean z;
        if (bqu.a) {
            boolean z2 = true;
            if (str.startsWith("/") && !str.startsWith("/proc/") && !str.startsWith("/data/")) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                List list = bqu.e.get(accessMode);
                if (list != null && !str.isEmpty() && "mounted".equals(Environment.getExternalStorageState())) {
                    File externalStorageDirectory = Environment.getExternalStorageDirectory();
                    StringBuilder sb = new StringBuilder();
                    sb.append(externalStorageDirectory.getAbsolutePath());
                    sb.append("/");
                    String sb2 = sb.toString();
                    if (str.startsWith(sb2)) {
                        Iterator it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z2 = false;
                                break;
                            }
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(sb2);
                            sb3.append((String) it.next());
                            String sb4 = sb3.toString();
                            boolean z3 = sb4.charAt(sb4.length() - 1) != '/';
                            if (accessMode != AccessMode.MK_DIR) {
                                if (!z3) {
                                    if (str.length() > sb4.length() && str.startsWith(sb4)) {
                                        break;
                                    }
                                } else if (str.equals(sb4)) {
                                    break;
                                }
                            } else if (!z3) {
                                if (sb4.length() != str.length() + 1) {
                                    if (str.length() > sb4.length() && str.startsWith(sb4)) {
                                        break;
                                    }
                                } else if (sb4.startsWith(str)) {
                                    break;
                                }
                            } else {
                                continue;
                            }
                        }
                        if (!z2) {
                            if (bqu.b == 0) {
                                accessError.setMessage(bqu.c());
                                throw accessError;
                            }
                            if (!bqu.f.b && System.currentTimeMillis() - bqu.f.g > bqu.g) {
                                StringWriter stringWriter = new StringWriter();
                                PrintWriter printWriter = new PrintWriter(stringWriter);
                                accessError.printStackTrace(printWriter);
                                printWriter.flush();
                                printWriter.close();
                                final String stringWriter2 = stringWriter.toString();
                                final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                                spannableStringBuilder.append("SD卡访问被拒绝");
                                spannableStringBuilder.append("\n如需增加权限请联系：堇花 wenhua.ywh@alibaba-inc.com\n");
                                spannableStringBuilder.append(stringWriter2);
                                spannableStringBuilder.setSpan(new ForegroundColorSpan(SupportMenu.CATEGORY_MASK), spannableStringBuilder.length() - stringWriter2.length(), spannableStringBuilder.length(), 33);
                                bqu.f.e.post(new Runnable() {
                                    public final void run() {
                                        bqu.this.f.d = stringWriter2;
                                        bqq b2 = bqu.this.f;
                                        b2.c.setText(spannableStringBuilder);
                                        bqq b3 = bqu.this.f;
                                        if (!b3.b) {
                                            b3.b = true;
                                            b3.a();
                                            b3.e.post(b3.f);
                                        }
                                    }
                                });
                            }
                        } else if (bqu.j) {
                            StringBuilder sb5 = new StringBuilder("Authorize: [");
                            sb5.append(accessMode);
                            sb5.append("] ");
                            sb5.append(str);
                        }
                    }
                }
            }
        }
    }
}
