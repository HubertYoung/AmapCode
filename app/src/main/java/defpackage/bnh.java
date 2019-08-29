package defpackage;

import com.autonavi.common.tool.CrashLogUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bnh reason: default package */
/* compiled from: UploadFileManager */
public class bnh {
    private static bnh a;
    private Thread b;
    /* access modifiers changed from: private */
    public bmt c;

    /* renamed from: bnh$a */
    /* compiled from: UploadFileManager */
    interface a {
        void a();
    }

    private bnh(bmt bmt) {
        this.c = bmt;
    }

    public static bnh a(bmt bmt) {
        if (a == null) {
            synchronized (bnh.class) {
                try {
                    a = new bnh(bmt);
                }
            }
        }
        return a;
    }

    public final void a() {
        a((a) new a() {
            public final void a() {
                bnh.this.d();
            }
        });
    }

    public final void b() {
        a((a) new a() {
            public final void a() {
                int i;
                File[] listFiles;
                bng bng = new bng(bnh.this.c.s());
                File[] a2 = bng.a();
                while (true) {
                    if ((a2 == null || a2.length == 0) || !bnh.this.d()) {
                        bnc bnc = new bnc(bnh.this.c.G());
                        ArrayList arrayList = new ArrayList();
                    } else {
                        a2 = bng.a();
                    }
                }
                bnc bnc2 = new bnc(bnh.this.c.G());
                ArrayList arrayList2 = new ArrayList();
                for (File file : bnc2.e.listFiles()) {
                    String name = file.getName();
                    File file2 = new File(file, bnc.b);
                    if (!bnc2.d.equals(name)) {
                        if (bnc2.d.length() != name.length() || !file2.exists() || !bnc2.a(name)) {
                            bmu.a(file);
                        } else {
                            arrayList2.add(file);
                        }
                    }
                }
                if (arrayList2.size() > 0) {
                    new bnd(bnh.this.c).a((List<File>) arrayList2);
                }
            }
        });
    }

    private synchronized void a(final a aVar) {
        if (this.b == null || !this.b.isAlive()) {
            this.b = new Thread(new Runnable() {
                public final void run() {
                    try {
                        if (aVar != null) {
                            aVar.a();
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            }, "DP-UP");
            this.b.start();
        }
    }

    public final synchronized void c() {
        if (this.b != null) {
            try {
                this.b.join(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean d() {
        if (!this.c.a()) {
            return false;
        }
        try {
            File s = this.c.s();
            StringBuilder sb = new StringBuilder();
            sb.append(s);
            sb.append("/upload.zip");
            String sb2 = sb.toString();
            ArrayList arrayList = new ArrayList();
            File a2 = new bng(s).a(arrayList, sb2);
            if (!arrayList.isEmpty() && a2 != null && a2.exists()) {
                if (a2.length() != 0) {
                    new StringBuilder("upload.zip size = ").append(a2.length());
                    File[] fileArr = (File[]) arrayList.toArray(new File[0]);
                    CrashLogUtil.appendUploadFlag(fileArr);
                    new bnl();
                    bnj a3 = bnl.a(a2, fileArr, this.c);
                    if (a3 == null) {
                        return false;
                    }
                    a3.a();
                    return a3.c();
                }
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }
}
