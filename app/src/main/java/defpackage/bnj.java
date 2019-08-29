package defpackage;

import java.io.File;
import java.io.FileWriter;

/* renamed from: bnj reason: default package */
/* compiled from: BaseUploadFile */
public abstract class bnj {
    protected File a;
    protected File[] b;
    protected bmt c;
    protected boolean d = false;

    public abstract void a();

    protected bnj(File file, File[] fileArr, bmt bmt) {
        this.a = file;
        this.b = fileArr;
        this.c = bmt;
    }

    /* access modifiers changed from: protected */
    public final boolean b() {
        return (this.a == null || !this.a.exists() || this.b == null || this.b.length == 0 || this.c == null) ? false : true;
    }

    public final boolean c() {
        return this.d;
    }

    protected static void a(File[] fileArr) {
        if (fileArr != null && fileArr.length != 0) {
            for (File file : fileArr) {
                try {
                    file.delete();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                if (file.exists()) {
                    try {
                        new FileWriter(file, false).close();
                    } catch (Throwable th2) {
                        th2.printStackTrace();
                    }
                }
            }
        }
    }
}
