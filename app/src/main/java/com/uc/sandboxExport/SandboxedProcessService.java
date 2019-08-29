package com.uc.sandboxExport;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.uc.sandboxExport.IChildProcessSetup.Stub;
import com.uc.sandboxExport.a.a;
import dalvik.system.DexFile;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Api
/* compiled from: ProGuard */
public class SandboxedProcessService extends Service {
    /* access modifiers changed from: private */
    public static final boolean a = Switches.a;
    private boolean b;
    private Constructor<?> c;
    private Method d;
    private Method e;
    private Method f;
    /* access modifiers changed from: private */
    public IBinder g;
    private Object h;
    /* access modifiers changed from: private */
    public Intent i;
    private final Stub j = new Stub() {
        public IBinder preSetupConnection(Bundle bundle) {
            ParcelFileDescriptor parcelFileDescriptor;
            ParcelFileDescriptor[] parcelFileDescriptorArr;
            boolean z = true;
            ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) bundle.getParcelable("dex.fd");
            Parcelable[] parcelableArray = bundle.getParcelableArray("lib.fd");
            ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) bundle.getParcelable("crash.fd");
            boolean z2 = bundle.getBoolean("verifyRawDex", false);
            boolean z3 = bundle.getBoolean("createDexInJava", false);
            if (SandboxedProcessService.this.shouldOptimizeDexLoad()) {
                parcelFileDescriptor = (ParcelFileDescriptor) bundle.getParcelable("rawDex.fd");
            } else {
                parcelFileDescriptor = null;
            }
            if (SandboxedProcessService.a) {
                StringBuilder append = new StringBuilder("setupConnection, fd: ").append(parcelFileDescriptor2 != null).append("[lib fd]").append(parcelFileDescriptor2 != null).append("[rawdex]");
                if (parcelFileDescriptor == null) {
                    z = false;
                }
                append.append(z).append("[verifyRawDex]").append(z2);
            }
            if (parcelableArray != null) {
                parcelFileDescriptorArr = new ParcelFileDescriptor[parcelableArray.length];
                System.arraycopy(parcelableArray, 0, parcelFileDescriptorArr, 0, parcelableArray.length);
            } else {
                parcelFileDescriptorArr = null;
            }
            SandboxedProcessService.this.init(SandboxedProcessService.this.i, parcelFileDescriptor2, parcelFileDescriptorArr, parcelFileDescriptor3, parcelFileDescriptor, z3, z2);
            return SandboxedProcessService.this.g;
        }
    };

    private static Class<?> a(String str, ClassLoader classLoader) {
        if (classLoader == null) {
            return Class.forName(str);
        }
        try {
            return Class.forName(str, false, classLoader);
        } catch (Exception e2) {
            ProcessFatalError(e2);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void init(Intent intent, ParcelFileDescriptor parcelFileDescriptor, Parcelable[] parcelableArr, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, boolean z, boolean z2) {
        DexFileClassLoader dexFileClassLoader;
        DexFile dexFile;
        if (!this.b) {
            if (parcelFileDescriptor2 != null && a.a()) {
                try {
                    Class<?> cls = Class.forName("com.uc.crashsdk.export.CrashApi");
                    if (cls != null) {
                        Object invoke = cls.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                        if (invoke != null) {
                            cls.getDeclaredMethod("setIsolatedHostFd", new Class[]{ParcelFileDescriptor.class}).invoke(invoke, new Object[]{parcelFileDescriptor2});
                        }
                    }
                } catch (Throwable th) {
                }
            }
            String stringExtra = intent.getStringExtra("dex.path");
            String stringExtra2 = intent.getStringExtra("odex.path");
            String stringExtra3 = intent.getStringExtra("lib.path");
            String stringExtra4 = intent.getStringExtra("source.dir");
            String str = shouldOptimizeDexLoad() ? intent.getStringExtra("source.dir.prior") : "";
            if (a) {
                new StringBuilder("sandboxed-renderer: init, dexPath: ").append(stringExtra).append(", odexPath: ").append(stringExtra2).append(", libPath: ").append(stringExtra3).append("sourcedir: ").append(stringExtra4).append("classloader").append(getClass().getClassLoader());
            }
            if (stringExtra == null || stringExtra.length() == 0) {
                dexFileClassLoader = null;
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                try {
                    dexFile = (!a.a() || str == null || !new File(str).exists()) ? null : new DexFile(str);
                } catch (Throwable th2) {
                    dexFile = null;
                }
                if (dexFile != null) {
                    stringExtra4 = str;
                }
                dexFileClassLoader = new DexFileClassLoader(stringExtra, stringExtra2, stringExtra3, getClass().getClassLoader(), parcelFileDescriptor, parcelFileDescriptor3, stringExtra4, dexFile, z, z2);
                if (parcelFileDescriptor != null) {
                    this.i.putExtra("isolate", true);
                    this.i.putExtra("dexLoadTimes", new long[]{System.currentTimeMillis() - currentTimeMillis, DexFileClassLoader.a(currentTimeMillis), DexFileClassLoader.a(), DexFileClassLoader.b()});
                }
            }
            Class<?> a2 = a("org.chromium.content.app.SandboxedProcessService0", dexFileClassLoader);
            if (a2 == null) {
                a2 = a("org.chromium.content.app.SandboxedProcessService0", dexFileClassLoader);
            }
            if (a2 == null) {
                ProcessFatalError(new Exception("org.chromium.content.app.SandboxedProcessService0get null"));
                return;
            }
            try {
                this.c = a2.getDeclaredConstructor(new Class[0]);
                this.c.setAccessible(true);
            } catch (Throwable th3) {
                ProcessFatalError(th3);
            }
            try {
                this.d = a2.getMethod("onDestroy", new Class[0]);
                this.d.setAccessible(true);
            } catch (Throwable th4) {
                ProcessFatalError(th4);
            }
            try {
                this.f = a2.getDeclaredMethod("initializeEngine", new Class[]{Class.forName("[Landroid.os.ParcelFileDescriptor;")});
                this.f.setAccessible(true);
            } catch (Throwable th5) {
                ProcessFatalError(th5);
            }
            try {
                this.e = a2.getDeclaredMethod("onBind", new Class[]{Class.forName("android.content.Intent")});
                this.e.setAccessible(true);
            } catch (Throwable th6) {
                ProcessFatalError(th6);
            }
            if (this.c != null) {
                try {
                    this.h = this.c.newInstance(new Object[0]);
                } catch (Exception e2) {
                    ProcessFatalError(e2);
                }
            }
            try {
                Class<?> cls2 = Class.forName("android.app.Service");
                Field declaredField = cls2.getDeclaredField("mThread");
                declaredField.setAccessible(true);
                Field declaredField2 = cls2.getDeclaredField("mClassName");
                declaredField2.setAccessible(true);
                Field declaredField3 = cls2.getDeclaredField("mToken");
                declaredField3.setAccessible(true);
                Field declaredField4 = cls2.getDeclaredField("mApplication");
                declaredField4.setAccessible(true);
                Field declaredField5 = cls2.getDeclaredField("mActivityManager");
                declaredField5.setAccessible(true);
                Field declaredField6 = Class.forName("android.content.ContextWrapper").getDeclaredField("mBase");
                declaredField6.setAccessible(true);
                cls2.getDeclaredMethod("attach", new Class[]{Class.forName("android.content.Context"), Class.forName("android.app.ActivityThread"), Class.forName("java.lang.String"), Class.forName("android.os.IBinder"), Class.forName("android.app.Application"), Class.forName("java.lang.Object")}).invoke(this.h, new Object[]{declaredField6.get(this), declaredField.get(this), declaredField2.get(this), declaredField3.get(this), declaredField4.get(this), declaredField5.get(this)});
            } catch (Exception e3) {
                ProcessFatalError(e3);
            }
            if (!(this.h == null || this.e == null)) {
                try {
                    this.g = (IBinder) this.e.invoke(this.h, new Object[]{this.i});
                } catch (Exception e4) {
                    ProcessFatalError(e4);
                }
            }
            if (!(this.h == null || this.f == null)) {
                try {
                    this.f.invoke(this.h, new Object[]{parcelableArr});
                } catch (Exception e5) {
                    ProcessFatalError(e5);
                }
            }
            this.b = true;
        }
    }

    public boolean shouldOptimizeDexLoad() {
        return true;
    }

    public static void ProcessFatalError(Throwable th) {
        throw new Error("render process faltal error" + (th == null ? "" : th.toString()));
    }

    public IBinder onBind(Intent intent) {
        this.i = intent;
        return this.j;
    }

    public void onDestroy() {
        if (this.g != null) {
            if (this.d != null) {
                try {
                    this.d.invoke(this.h, new Object[0]);
                } catch (Throwable th) {
                }
            }
            this.h = null;
            this.g = null;
        }
    }
}
