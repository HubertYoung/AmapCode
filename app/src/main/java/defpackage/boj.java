package defpackage;

import java.io.Closeable;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Stack;

/* renamed from: boj reason: default package */
/* compiled from: Util */
public final class boj {
    static final Charset a = Charset.forName("US-ASCII");
    static final Charset b = Charset.forName("UTF-8");

    public static int a(File file) {
        Stack stack = new Stack();
        stack.push(file);
        int i = 0;
        while (stack.size() > 0) {
            File file2 = (File) stack.pop();
            File[] listFiles = file2.listFiles();
            if (listFiles != null) {
                if (listFiles.length != 0) {
                    stack.push(file2);
                    int i2 = i;
                    int i3 = 0;
                    while (i3 < listFiles.length) {
                        try {
                            if (listFiles[i3].isDirectory()) {
                                stack.push(listFiles[i3]);
                            } else {
                                listFiles[i3].delete();
                                i2++;
                            }
                            i3++;
                        } catch (Exception unused) {
                            return i2;
                        }
                    }
                    i = i2;
                }
            }
            try {
                file2.delete();
                i++;
            } catch (Exception unused2) {
                return i;
            }
        }
        return i;
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }
}
