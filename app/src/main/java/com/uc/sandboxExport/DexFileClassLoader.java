package com.uc.sandboxExport;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexFile;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

@Api
/* compiled from: ProGuard */
public class DexFileClassLoader extends BaseDexClassLoader {
    private static final boolean a = Switches.a;
    private static long b = 0;
    private static long c = 0;
    private static long d = 0;
    private DexFile e;
    private Constructor<?> f;

    public DexFileClassLoader(String str, String str2, String str3, ClassLoader classLoader, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, String str4, DexFile dexFile, boolean z, boolean z2) {
        super(parcelFileDescriptor != null ? str4 : str, parcelFileDescriptor == null ? new File(str2) : null, str3, classLoader);
        b = System.currentTimeMillis();
        if (parcelFileDescriptor != null) {
            long currentTimeMillis = System.currentTimeMillis();
            this.e = a(parcelFileDescriptor, parcelFileDescriptor2, str4, dexFile, z, z2);
            c = System.currentTimeMillis() - currentTimeMillis;
        }
    }

    static long a(long j) {
        return b - j;
    }

    static long a() {
        return c;
    }

    static long b() {
        return d;
    }

    private DexFile a(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, String str, DexFile dexFile, boolean z, boolean z2) {
        DexFile dexFile2;
        Object obj;
        DexFile dexFile3 = null;
        if (z) {
            try {
                if (this.f == null) {
                    this.f = DexFile.class.getDeclaredConstructor(new Class[]{ByteBuffer.class});
                    this.f.setAccessible(true);
                }
                FileChannel channel = new FileInputStream(parcelFileDescriptor.getFileDescriptor()).getChannel();
                MappedByteBuffer map = channel.map(MapMode.READ_ONLY, 0, channel.size());
                dexFile3 = (DexFile) this.f.newInstance(new Object[]{map});
            } catch (Throwable th) {
                new StringBuilder("makeDexFile, use dexfile(buffer) error:").append(th);
                dexFile3 = null;
            }
        }
        if (dexFile2 == null) {
            long j = 0;
            try {
                if (a) {
                    j = System.currentTimeMillis();
                }
                if (dexFile == null) {
                    try {
                        dexFile2 = new DexFile(str);
                    } catch (Exception e2) {
                        e = e2;
                        dexFile2 = dexFile;
                        SandboxedProcessService.ProcessFatalError(e);
                        return dexFile2;
                    }
                } else {
                    dexFile2 = dexFile;
                }
                if (a) {
                    new StringBuilder("makeDexFile, new fakeDexFile end[time]").append(System.currentTimeMillis() - j);
                    j = System.currentTimeMillis();
                }
                if (VERSION.SDK_INT < 23) {
                    obj = Long.valueOf(DexFileResolver.loadDexByFdOnL(parcelFileDescriptor.getFd()));
                } else {
                    if (parcelFileDescriptor2 != null) {
                        DexFileResolver.setNeedVerifyRawDex(z2);
                        obj = DexFileResolver.loadDexByFdOnLAbove(parcelFileDescriptor2.detachFd());
                    } else {
                        obj = null;
                    }
                    if (obj == null) {
                        obj = DexFileResolver.loadDexByFdOnLAbove(parcelFileDescriptor.getFd());
                    }
                }
                if (a) {
                    new StringBuilder("makeDexFile, cookie ").append(obj).append("[time]").append(System.currentTimeMillis() - j);
                }
                d = DexFileResolver.getLoadDextime();
                if (obj != null) {
                    Field declaredField = dexFile2.getClass().getDeclaredField("mCookie");
                    declaredField.setAccessible(true);
                    declaredField.set(dexFile2, obj);
                }
            } catch (Exception e3) {
                e = e3;
                SandboxedProcessService.ProcessFatalError(e);
                return dexFile2;
            }
        }
        return dexFile2;
    }

    public String findLibrary(String str) {
        String findLibrary = super.findLibrary(str);
        if (findLibrary != null || !(getParent() instanceof BaseDexClassLoader)) {
            return findLibrary;
        }
        return ((BaseDexClassLoader) getParent()).findLibrary(str);
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public Class<?> findClass(String str) {
        Class<?> cls = null;
        if (this.e != null) {
            cls = this.e.loadClass(str, this);
        }
        try {
            cls = super.findClass(str);
        } catch (ClassNotFoundException e2) {
        }
        if (cls == null) {
            cls = findLoadedClass(str);
        }
        if (cls == null) {
            return getParent().loadClass(str);
        }
        return cls;
    }

    /* access modifiers changed from: protected */
    public Class<?> loadClass(String str, boolean z) {
        if (!str.startsWith("com.uc.") && !str.startsWith("org.chromium.")) {
            return super.loadClass(str, z);
        }
        Class<?> findLoadedClass = findLoadedClass(str);
        if (findLoadedClass == null) {
            return findClass(str);
        }
        return findLoadedClass;
    }
}
