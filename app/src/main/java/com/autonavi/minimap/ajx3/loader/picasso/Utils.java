package com.autonavi.minimap.ajx3.loader.picasso;

import android.annotation.TargetApi;
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
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadFactory;

public final class Utils {
    static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 15000;
    static final int DEFAULT_READ_TIMEOUT_MILLIS = 20000;
    static final int DEFAULT_WRITE_TIMEOUT_MILLIS = 20000;
    private static final String GIF_FILE_HEADER_GIF = "GIF";
    private static final int GIF_FILE_HEADER_SIZE = 6;
    private static final String GIF_FILE_HEADER_VER1 = "87a";
    private static final String GIF_FILE_HEADER_VER2 = "89a";
    private static final String HASH_ALGORITHM = "MD5";
    private static final int KEY_PADDING = 50;
    public static final char KEY_SEPARATOR = '\n';
    static final StringBuilder MAIN_THREAD_KEY_BUILDER = new StringBuilder();
    private static final int MAX_DISK_CACHE_SIZE = 104857600;
    private static final int MIN_DISK_CACHE_SIZE = 20971520;
    static final String OWNER_DISPATCHER = "Dispatcher";
    static final String OWNER_HUNTER = "Hunter";
    static final String OWNER_MAIN = "Main";
    private static final String PICASSO_CACHE = "picasso-cache";
    private static final int RADIX = 36;
    static final String THREAD_IDLE_NAME = "Picasso-Idle";
    static final int THREAD_LEAK_CLEANING_MS = 1000;
    static final String THREAD_PREFIX = "Picasso-";
    static final Charset US_ASCII = Charset.forName("US-ASCII");
    static final Charset UTF_8 = Charset.forName("UTF-8");
    static final String VERB_BATCHED = "batched";
    static final String VERB_CACHE_DISK = "disk_cache";
    static final String VERB_CANCELED = "canceled";
    static final String VERB_CHANGED = "changed";
    static final String VERB_COMPLETED = "completed";
    static final String VERB_CREATED = "created";
    static final String VERB_DECODED = "decoded";
    static final String VERB_DELIVERED = "delivered";
    static final String VERB_ENQUEUED = "enqueued";
    static final String VERB_ERRORED = "errored";
    static final String VERB_EXECUTING = "executing";
    static final String VERB_FAST = "fastcompleted";
    static final String VERB_IGNORED = "ignored";
    static final String VERB_JOINED = "joined";
    static final String VERB_PAUSED = "paused";
    static final String VERB_REMOVED = "removed";
    static final String VERB_REPLAYING = "replaying";
    static final String VERB_RESUMED = "resumed";
    static final String VERB_RETRYING = "retrying";
    static final String VERB_TRANSFORMED = "transformed";
    private static final String WEBP_FILE_HEADER_RIFF = "RIFF";
    private static final int WEBP_FILE_HEADER_SIZE = 12;
    private static final String WEBP_FILE_HEADER_WEBP = "WEBP";

    @TargetApi(11)
    static class ActivityManagerHoneycomb {
        private ActivityManagerHoneycomb() {
        }

        static int getLargeMemoryClass(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    @TargetApi(12)
    static class BitmapHoneycombMR1 {
        private BitmapHoneycombMR1() {
        }

        static int getByteCount(Bitmap bitmap) {
            return bitmap.getByteCount();
        }
    }

    static class PicassoThread extends Thread {
        public PicassoThread(Runnable runnable) {
            super(runnable);
        }

        public void run() {
            Process.setThreadPriority(10);
            super.run();
        }
    }

    static class PicassoThreadFactory implements ThreadFactory {
        PicassoThreadFactory() {
        }

        public Thread newThread(Runnable runnable) {
            return new PicassoThread(runnable);
        }
    }

    private Utils() {
    }

    public static int getBitmapBytes(Bitmap bitmap) {
        int i;
        if (VERSION.SDK_INT >= 12) {
            i = BitmapHoneycombMR1.getByteCount(bitmap);
        } else {
            i = bitmap.getRowBytes() * bitmap.getHeight();
        }
        if (i >= 0) {
            return i;
        }
        throw new IllegalStateException("Negative size: ".concat(String.valueOf(bitmap)));
    }

    static <T> T checkNotNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    static void checkNotMain() {
        if (isMain()) {
            throw new IllegalStateException("Method call should not happen from the main thread.");
        }
    }

    static void checkMain() {
        if (!isMain()) {
            throw new IllegalStateException("Method call should happen from the main thread.");
        }
    }

    static boolean isMain() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    static String getLogIdsForHunter(ImageHunter imageHunter) {
        return getLogIdsForHunter(imageHunter, "");
    }

    static String getLogIdsForHunter(ImageHunter imageHunter, String str) {
        StringBuilder sb = new StringBuilder(str);
        Action action = imageHunter.getAction();
        if (action != null) {
            sb.append(action.request.logId());
        }
        List<Action> actions = imageHunter.getActions();
        if (actions != null) {
            int size = actions.size();
            for (int i = 0; i < size; i++) {
                if (i > 0 || action != null) {
                    sb.append(", ");
                }
                sb.append(actions.get(i).request.logId());
            }
        }
        return sb.toString();
    }

    static void log(String str, String str2, String str3) {
        log(str, str2, str3, "");
    }

    static void log(String str, String str2, String str3, String str4) {
        if (Picasso.singleton.loggingEnabled) {
            String.format("%1$-11s %2$-12s %3$s %4$s", new Object[]{str, str2, str3, str4});
        }
    }

    static String createKey(Request request) {
        String createKey = createKey(request, MAIN_THREAD_KEY_BUILDER);
        MAIN_THREAD_KEY_BUILDER.setLength(0);
        return createKey;
    }

    static String createKey(Request request, StringBuilder sb) {
        if (request.stableKey != null) {
            sb.ensureCapacity(request.stableKey.length() + 50);
            sb.append(request.stableKey);
        } else if (request.uri != null) {
            String uri = request.uri.toString();
            sb.ensureCapacity(uri.length() + 50);
            sb.append(uri);
        } else {
            sb.ensureCapacity(50);
            sb.append(request.resourceId);
        }
        sb.append(10);
        if (request.rotationDegrees != 0.0f) {
            sb.append("rotation:");
            sb.append(request.rotationDegrees);
            if (request.hasRotationPivot) {
                sb.append('@');
                sb.append(request.rotationPivotX);
                sb.append('x');
                sb.append(request.rotationPivotY);
            }
            sb.append(10);
        }
        if (request.hasSize()) {
            sb.append("resize:");
            sb.append(request.targetWidth);
            sb.append('x');
            sb.append(request.targetHeight);
            sb.append(10);
        }
        if (request.centerCrop) {
            sb.append("centerCrop\n");
        } else if (request.centerInside) {
            sb.append("centerInside\n");
        }
        if (request.transformations != null) {
            int size = request.transformations.size();
            for (int i = 0; i < size; i++) {
                sb.append(request.transformations.get(i).key());
                sb.append(10);
            }
        }
        return sb.toString();
    }

    static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    static boolean parseResponseSourceHeader(String str) {
        if (str == null) {
            return false;
        }
        String[] split = str.split(Token.SEPARATOR, 2);
        if ("CACHE".equals(split[0])) {
            return true;
        }
        if (split.length == 1) {
            return false;
        }
        try {
            if (!"CONDITIONAL_CACHE".equals(split[0]) || Integer.parseInt(split[1]) != 304) {
                return false;
            }
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    static File createDefaultCacheDir(Context context) {
        File file = new File(context.getApplicationContext().getCacheDir(), PICASSO_CACHE);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    static long calculateDiskCacheSize(File file, float f) {
        long j;
        try {
            StatFs statFs = new StatFs(file.getAbsolutePath());
            j = (long) (((float) (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()))) * f);
        } catch (IllegalArgumentException unused) {
            j = 20971520;
        }
        return Math.max(Math.min(j, 104857600), 20971520);
    }

    static int calculateMemoryCacheSize(Context context) {
        ActivityManager activityManager = (ActivityManager) getService(context, WidgetType.ACTIVITY);
        boolean z = (context.getApplicationInfo().flags & 1048576) != 0;
        int memoryClass = activityManager.getMemoryClass();
        if (z && VERSION.SDK_INT >= 11) {
            memoryClass = ActivityManagerHoneycomb.getLargeMemoryClass(activityManager);
        }
        return (memoryClass * 1048576) / 10;
    }

    static boolean isAirplaneModeOn(Context context) {
        try {
            if (System.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0) {
                return true;
            }
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
    }

    static <T> T getService(Context context, String str) {
        return context.getSystemService(str);
    }

    static boolean hasPermission(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    static byte[] toByteArray(InputStream inputStream) throws IOException {
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

    static boolean isWebPFile(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[12];
        if (inputStream.read(bArr, 0, 12) != 12 || !WEBP_FILE_HEADER_RIFF.equals(new String(bArr, 0, 4, "US-ASCII")) || !WEBP_FILE_HEADER_WEBP.equals(new String(bArr, 8, 4, "US-ASCII"))) {
            return false;
        }
        return true;
    }

    static boolean isGifFile(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[6];
        if (inputStream.read(bArr, 0, 6) != 6 || !GIF_FILE_HEADER_GIF.equals(new String(bArr, 0, 3, "US-ASCII"))) {
            return false;
        }
        if (GIF_FILE_HEADER_VER1.equals(new String(bArr, 3, 3)) || GIF_FILE_HEADER_VER2.equals(new String(bArr, 3, 3))) {
            return true;
        }
        return false;
    }

    static int getResourceId(Resources resources, Request request) throws FileNotFoundException {
        int i;
        if (request.resourceId != 0 || request.uri == null) {
            return request.resourceId;
        }
        String authority = request.uri.getAuthority();
        if (authority == null) {
            StringBuilder sb = new StringBuilder("No package provided: ");
            sb.append(request.uri);
            throw new FileNotFoundException(sb.toString());
        }
        List<String> pathSegments = request.uri.getPathSegments();
        if (pathSegments == null || pathSegments.isEmpty()) {
            StringBuilder sb2 = new StringBuilder("No path segments: ");
            sb2.append(request.uri);
            throw new FileNotFoundException(sb2.toString());
        }
        if (pathSegments.size() == 1) {
            try {
                i = Integer.parseInt(pathSegments.get(0));
            } catch (NumberFormatException unused) {
                StringBuilder sb3 = new StringBuilder("Last path segment is not a resource ID: ");
                sb3.append(request.uri);
                throw new FileNotFoundException(sb3.toString());
            }
        } else if (pathSegments.size() == 2) {
            String str = pathSegments.get(0);
            String str2 = pathSegments.get(1);
            int indexOf = str2.indexOf(46);
            if (indexOf >= 0) {
                str2 = str2.substring(0, indexOf);
            }
            i = resources.getIdentifier(str2, str, authority);
        } else {
            StringBuilder sb4 = new StringBuilder("More than two path segments: ");
            sb4.append(request.uri);
            throw new FileNotFoundException(sb4.toString());
        }
        return i;
    }

    static Resources getResources(Context context, Request request) throws FileNotFoundException {
        if (request.resourceId != 0 || request.uri == null) {
            return context.getResources();
        }
        String authority = request.uri.getAuthority();
        if (authority == null) {
            StringBuilder sb = new StringBuilder("No package provided: ");
            sb.append(request.uri);
            throw new FileNotFoundException(sb.toString());
        }
        try {
            return context.getPackageManager().getResourcesForApplication(authority);
        } catch (NameNotFoundException unused) {
            StringBuilder sb2 = new StringBuilder("Unable to obtain resources for package: ");
            sb2.append(request.uri);
            throw new FileNotFoundException(sb2.toString());
        }
    }

    static void flushStackLocalLeaks(Looper looper) {
        AnonymousClass1 r0 = new Handler(looper) {
            public final void handleMessage(Message message) {
                sendMessageDelayed(obtainMessage(), 1000);
            }
        };
        r0.sendMessageDelayed(r0.obtainMessage(), 1000);
    }

    public static String generateImageName(String str) {
        return new BigInteger(getMD5(str.getBytes())).abs().toString(36);
    }

    private static byte[] getMD5(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static int deleteContents(File file) {
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

    static String readFully(Reader reader) throws IOException {
        try {
            StringWriter stringWriter = new StringWriter();
            char[] cArr = new char[1024];
            while (true) {
                int read = reader.read(cArr);
                if (read == -1) {
                    return stringWriter.toString();
                }
                stringWriter.write(cArr, 0, read);
            }
        } finally {
            reader.close();
        }
    }

    public static boolean isWebResource(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (!str.startsWith(AjxHttpLoader.DOMAIN_HTTP) && !str.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
            return false;
        }
        int lastIndexOf = str.lastIndexOf(47);
        int lastIndexOf2 = str.lastIndexOf(".web.");
        if (lastIndexOf2 < 0 || lastIndexOf < 0 || lastIndexOf >= lastIndexOf2) {
            return false;
        }
        return true;
    }
}
