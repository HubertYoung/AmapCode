package defpackage;

import android.os.Environment;
import java.io.File;
import java.util.Comparator;

/* renamed from: eua reason: default package */
/* compiled from: Files */
public final class eua {

    /* renamed from: eua$a */
    /* compiled from: Files */
    static final class a implements Comparator<File> {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final /* synthetic */ int compare(Object obj, Object obj2) {
            int i = (((File) obj).lastModified() > ((File) obj2).lastModified() ? 1 : (((File) obj).lastModified() == ((File) obj2).lastModified() ? 0 : -1));
            if (i < 0) {
                return -1;
            }
            return i == 0 ? 0 : 1;
        }
    }

    public static boolean a() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            try {
                return Environment.getExternalStorageDirectory().canWrite();
            } catch (Exception unused) {
            }
        }
        return false;
    }
}
