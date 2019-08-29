package defpackage;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.StatFs;
import android.provider.Settings.System;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/* renamed from: bkh reason: default package */
/* compiled from: Utils */
public final class bkh {
    static final StringBuilder a = new StringBuilder();

    /* renamed from: bkh$a */
    /* compiled from: Utils */
    static class a extends Thread {
        public a(Runnable runnable) {
            super(runnable);
        }

        public final void run() {
            Process.setThreadPriority(10);
            super.run();
        }
    }

    /* renamed from: bkh$b */
    /* compiled from: Utils */
    static class b implements ThreadFactory {
        b() {
        }

        public final Thread newThread(Runnable runnable) {
            return new a(runnable);
        }
    }

    static int a(Bitmap bitmap) {
        int i;
        if (VERSION.SDK_INT >= 12) {
            i = bitmap.getByteCount();
        } else {
            i = bitmap.getRowBytes() * bitmap.getHeight();
        }
        if (i >= 0) {
            return i;
        }
        throw new IllegalStateException("Negative size: ".concat(String.valueOf(bitmap)));
    }

    static <T> T a(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static String a(bjk bjk) {
        return a(bjk, (String) "");
    }

    public static String a(bjk bjk, String str) {
        StringBuilder sb = new StringBuilder(str);
        bji bji = bjk.k;
        if (bji != null) {
            sb.append(bji.b.a());
        }
        List<bji> list = bjk.l;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (i > 0 || bji != null) {
                    sb.append(", ");
                }
                sb.append(list.get(i).b.a());
            }
        }
        return sb.toString();
    }

    public static void a(String str, String str2, String str3) {
        a(str, str2, str3, "");
    }

    public static void a(String str, String str2, String str3, String str4) {
        String.format("%1$-11s %2$-12s %3$s %4$s", new Object[]{str, str2, str3, str4});
    }

    public static String a(bjz bjz) {
        StringBuilder sb = a;
        if (bjz.f != null) {
            sb.ensureCapacity(bjz.f.length() + 50);
            sb.append(bjz.f);
        } else if (bjz.d != null) {
            String uri = bjz.d.toString();
            sb.ensureCapacity(uri.length() + 50);
            sb.append(uri);
        } else {
            sb.ensureCapacity(50);
            sb.append(bjz.e);
        }
        sb.append(10);
        if (bjz.m != 0.0f) {
            sb.append("rotation:");
            sb.append(bjz.m);
            if (bjz.p) {
                sb.append('@');
                sb.append(bjz.n);
                sb.append('x');
                sb.append(bjz.o);
            }
            sb.append(10);
        }
        if (bjz.c()) {
            sb.append("resize:");
            sb.append(bjz.h);
            sb.append('x');
            sb.append(bjz.i);
            sb.append(10);
        }
        if (bjz.j) {
            sb.append("centerCrop\n");
        } else if (bjz.k) {
            sb.append("centerInside\n");
        }
        String sb2 = sb.toString();
        a.setLength(0);
        return sb2;
    }

    static long a(File file) {
        long j;
        try {
            StatFs statFs = new StatFs(file.getAbsolutePath());
            j = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 50;
        } catch (IllegalArgumentException unused) {
            j = 5242880;
        }
        return Math.max(Math.min(j, 52428800), 5242880);
    }

    public static boolean b(Context context) {
        try {
            if (System.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0) {
                return true;
            }
            return false;
        } catch (NullPointerException unused) {
            return false;
        } catch (SecurityException unused2) {
            return false;
        }
    }

    public static <T> T a(Context context, String str) {
        return context.getSystemService(str);
    }

    public static boolean b(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    static byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    static boolean b(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[12];
        if (inputStream.read(bArr, 0, 12) != 12 || !"RIFF".equals(new String(bArr, 0, 4, "US-ASCII")) || !"WEBP".equals(new String(bArr, 8, 4, "US-ASCII"))) {
            return false;
        }
        return true;
    }

    static int a(Resources resources, bjz bjz) throws FileNotFoundException {
        int i;
        if (bjz.e != 0 || bjz.d == null) {
            return bjz.e;
        }
        String authority = bjz.d.getAuthority();
        if (authority == null) {
            StringBuilder sb = new StringBuilder("No package provided: ");
            sb.append(bjz.d);
            throw new FileNotFoundException(sb.toString());
        }
        List<String> pathSegments = bjz.d.getPathSegments();
        if (pathSegments == null || pathSegments.isEmpty()) {
            StringBuilder sb2 = new StringBuilder("No path segments: ");
            sb2.append(bjz.d);
            throw new FileNotFoundException(sb2.toString());
        }
        if (pathSegments.size() == 1) {
            try {
                i = Integer.parseInt(pathSegments.get(0));
            } catch (NumberFormatException unused) {
                StringBuilder sb3 = new StringBuilder("Last path segment is not a resource ID: ");
                sb3.append(bjz.d);
                throw new FileNotFoundException(sb3.toString());
            }
        } else if (pathSegments.size() == 2) {
            i = resources.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
        } else {
            StringBuilder sb4 = new StringBuilder("More than two path segments: ");
            sb4.append(bjz.d);
            throw new FileNotFoundException(sb4.toString());
        }
        return i;
    }

    static Resources a(Context context, bjz bjz) throws FileNotFoundException {
        if (bjz.e != 0 || bjz.d == null) {
            return context.getResources();
        }
        String authority = bjz.d.getAuthority();
        if (authority == null) {
            StringBuilder sb = new StringBuilder("No package provided: ");
            sb.append(bjz.d);
            throw new FileNotFoundException(sb.toString());
        }
        try {
            return context.getPackageManager().getResourcesForApplication(authority);
        } catch (NameNotFoundException unused) {
            StringBuilder sb2 = new StringBuilder("Unable to obtain resources for package: ");
            sb2.append(bjz.d);
            throw new FileNotFoundException(sb2.toString());
        }
    }

    public static void a(Looper looper) {
        AnonymousClass1 r0 = new Handler(looper) {
            public final void handleMessage(Message message) {
                sendMessageDelayed(obtainMessage(), 1000);
            }
        };
        r0.sendMessageDelayed(r0.obtainMessage(), 1000);
    }

    public static String a(String str) {
        return new BigInteger(a(str.getBytes())).abs().toString(36);
    }

    private static byte[] a(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static void a() {
        if (!(Looper.getMainLooper().getThread() == Thread.currentThread())) {
            throw new IllegalStateException("Method call should happen from the main thread.");
        }
    }

    static int a(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
        boolean z = (context.getApplicationInfo().flags & 1048576) != 0;
        int memoryClass = activityManager.getMemoryClass();
        if (z && VERSION.SDK_INT >= 11) {
            memoryClass = activityManager.getLargeMemoryClass();
        }
        return (memoryClass * 1048576) / 7;
    }
}
