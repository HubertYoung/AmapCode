package com.uc.webview.export.cyclone;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import dalvik.system.DexClassLoader;
import java.io.File;

@Constant
/* compiled from: ProGuard */
public class UCLoader extends DexClassLoader {
    private static final boolean ENABLE_SPEEDUP_LOAD = true;
    private static final String TAG = "UCLoader";
    private static int sToken = UCLogger.createToken("v", TAG);

    private static String doCheckOdexFile(String str, String str2, boolean z) {
        if (VERSION.SDK_INT == 21) {
            for (String optimizedFileFor : str.split(":")) {
                File optimizedFileFor2 = UCCyclone.optimizedFileFor(optimizedFileFor, str2);
                if (optimizedFileFor2.exists()) {
                    File file = new File(str2, UCCyclone.getDecompressFileHash(optimizedFileFor2));
                    if (!file.exists()) {
                        if (z) {
                            try {
                                optimizedFileFor2.delete();
                                int i = sToken;
                                StringBuilder sb = new StringBuilder("File [");
                                sb.append(optimizedFileFor2);
                                sb.append("] deleted.");
                                UCLogger.print(i, sb.toString(), new Throwable[0]);
                            } catch (Throwable th) {
                                int i2 = sToken;
                                StringBuilder sb2 = new StringBuilder("File [");
                                sb2.append(optimizedFileFor2);
                                sb2.append("] delete but exception.");
                                UCLogger.print(i2, sb2.toString(), th);
                            }
                        } else {
                            try {
                                file.createNewFile();
                                int i3 = sToken;
                                StringBuilder sb3 = new StringBuilder("File [");
                                sb3.append(file);
                                sb3.append("] created.");
                                UCLogger.print(i3, sb3.toString(), new Throwable[0]);
                            } catch (Throwable th2) {
                                int i4 = sToken;
                                StringBuilder sb4 = new StringBuilder("File [");
                                sb4.append(file);
                                sb4.append("] create but exception.");
                                UCLogger.print(i4, sb4.toString(), th2);
                            }
                        }
                    }
                }
            }
        }
        return str;
    }

    public UCLoader(String str, String str2, String str3, ClassLoader classLoader) {
        super(doCheckOdexFile(str, str2, true), str2, str3, classLoader);
        doCheckOdexFile(str, str2, false);
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public Class<?> findClass(String str) throws ClassNotFoundException {
        Class<?> cls;
        try {
            cls = super.findClass(str);
        } catch (ClassNotFoundException unused) {
            cls = null;
        }
        if (cls == null) {
            cls = findLoadedClass(str);
        }
        return cls == null ? getParent().loadClass(str) : cls;
    }

    /* access modifiers changed from: protected */
    public Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
        if (!str.startsWith("com.uc.") && !str.startsWith("org.chromium.")) {
            return super.loadClass(str, z);
        }
        Class findLoadedClass = findLoadedClass(str);
        return findLoadedClass == null ? findClass(str) : findLoadedClass;
    }
}
