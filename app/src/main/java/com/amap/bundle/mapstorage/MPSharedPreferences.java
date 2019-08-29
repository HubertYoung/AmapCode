package com.amap.bundle.mapstorage;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.UriMatcher;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amap.bundle.logs.AMapLog;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class MPSharedPreferences extends ContentProvider implements SharedPreferences {
    private static final Object e = new Object();
    private static String h;
    /* access modifiers changed from: private */
    public static volatile Uri i;
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public int c;
    /* access modifiers changed from: private */
    public boolean d;
    /* access modifiers changed from: private */
    public WeakHashMap<OnSharedPreferenceChangeListener, Object> f;
    private BroadcastReceiver g;
    private UriMatcher j;
    private HashMap<String, Integer> k;

    static final class a extends MatrixCursor {
        private Bundle a;

        public a(Bundle bundle) {
            super(new String[0], 0);
            this.a = bundle;
        }

        public final Bundle getExtras() {
            return this.a;
        }

        public final Bundle respond(Bundle bundle) {
            this.a = bundle;
            return this.a;
        }
    }

    public final class b implements Editor {
        private final Map<String, Object> b = new HashMap();
        private boolean c = false;

        public final Editor putStringSet(String str, Set<String> set) {
            return this;
        }

        public b() {
        }

        public final Editor putString(String str, String str2) {
            synchronized (this) {
                this.b.put(str, str2);
            }
            return this;
        }

        public final Editor putInt(String str, int i) {
            synchronized (this) {
                this.b.put(str, Integer.valueOf(i));
            }
            return this;
        }

        public final Editor putLong(String str, long j) {
            synchronized (this) {
                this.b.put(str, Long.valueOf(j));
            }
            return this;
        }

        public final Editor putFloat(String str, float f) {
            synchronized (this) {
                this.b.put(str, Float.valueOf(f));
            }
            return this;
        }

        public final Editor putBoolean(String str, boolean z) {
            synchronized (this) {
                this.b.put(str, Boolean.valueOf(z));
            }
            return this;
        }

        public final Editor remove(String str) {
            synchronized (this) {
                this.b.put(str, null);
            }
            return this;
        }

        public final Editor clear() {
            synchronized (this) {
                this.c = true;
            }
            return this;
        }

        public final void apply() {
            a("apply");
        }

        public final boolean commit() {
            return a("commit");
        }

        private boolean a(String str) {
            boolean z = false;
            if (!MPSharedPreferences.this.d && MPSharedPreferences.this.b(MPSharedPreferences.this.a)) {
                String[] strArr = {String.valueOf(MPSharedPreferences.this.c), String.valueOf(this.c)};
                synchronized (this) {
                    Uri withAppendedPath = Uri.withAppendedPath(Uri.withAppendedPath(MPSharedPreferences.i, MPSharedPreferences.this.b), str);
                    ContentValues a2 = c.a((HashMap) this.b);
                    if (a2 == null) {
                        return false;
                    }
                    try {
                        if (MPSharedPreferences.this.a.getContentResolver().update(withAppendedPath, a2, null, strArr) > 0) {
                            z = true;
                        }
                    } catch (IllegalArgumentException unused) {
                    } catch (RuntimeException e) {
                        if (!MPSharedPreferences.c((Throwable) e) && !MPSharedPreferences.d((Throwable) e)) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            return z;
            return z;
        }
    }

    static class c {
        public static ContentValues a(HashMap<String, Object> hashMap) {
            try {
                Constructor<ContentValues> declaredConstructor = ContentValues.class.getDeclaredConstructor(new Class[]{HashMap.class});
                declaredConstructor.setAccessible(true);
                return declaredConstructor.newInstance(new Object[]{hashMap});
            } catch (Throwable th) {
                StringBuilder sb = new StringBuilder("contentValuesNewInstance error: ");
                sb.append(th.getLocalizedMessage());
                AMapLog.error("paas.mapstorage", "MPSharedPreferences", sb.toString());
                return null;
            }
        }

        public static String a(ContentProvider contentProvider) {
            try {
                Field declaredField = ContentProvider.class.getDeclaredField("mAuthority");
                declaredField.setAccessible(true);
                return (String) declaredField.get(contentProvider);
            } catch (Throwable th) {
                StringBuilder sb = new StringBuilder("contentValuesNewInstance error: ");
                sb.append(th.getLocalizedMessage());
                AMapLog.error("paas.mapstorage", "MPSharedPreferences", sb.toString());
                return null;
            }
        }
    }

    @Nullable
    public Set<String> getStringSet(String str, @Nullable Set<String> set) {
        return null;
    }

    private static boolean a(Context context) {
        try {
            return context.getPackageManager().isSafeMode();
        } catch (RuntimeException e2) {
            if (c((Throwable) e2)) {
                return false;
            }
            throw e2;
        }
    }

    public static void a(String str) {
        h = str;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x004b A[Catch:{ NameNotFoundException -> 0x003e, RuntimeException -> 0x002f }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0084 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0063 A[EDGE_INSN: B:44:0x0063->B:33:0x0063 ?: BREAK  
    EDGE_INSN: B:44:0x0063->B:33:0x0063 ?: BREAK  
    EDGE_INSN: B:44:0x0063->B:33:0x0063 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0063 A[EDGE_INSN: B:44:0x0063->B:33:0x0063 ?: BREAK  
    EDGE_INSN: B:44:0x0063->B:33:0x0063 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean b(android.content.Context r7) {
        /*
            r6 = this;
            android.net.Uri r0 = i
            r1 = 0
            if (r0 != 0) goto L_0x007e
            monitor-enter(r6)
            android.net.Uri r0 = i     // Catch:{ all -> 0x007b }
            if (r0 != 0) goto L_0x0079
            java.lang.String r0 = h     // Catch:{ all -> 0x007b }
            if (r0 != 0) goto L_0x0063
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x007b }
            r2 = 21
            if (r0 < r2) goto L_0x001f
            boolean r0 = r6 instanceof android.content.ContentProvider     // Catch:{ all -> 0x007b }
            if (r0 == 0) goto L_0x001f
            java.lang.String r7 = com.amap.bundle.mapstorage.MPSharedPreferences.c.a(r6)     // Catch:{ all -> 0x007b }
            h = r7     // Catch:{ all -> 0x007b }
            goto L_0x0063
        L_0x001f:
            r0 = 0
            android.content.pm.PackageManager r2 = r7.getPackageManager()     // Catch:{ NameNotFoundException -> 0x003e, RuntimeException -> 0x002f }
            java.lang.String r7 = r7.getPackageName()     // Catch:{ NameNotFoundException -> 0x003e, RuntimeException -> 0x002f }
            r3 = 8
            android.content.pm.PackageInfo r7 = r2.getPackageInfo(r7, r3)     // Catch:{ NameNotFoundException -> 0x003e, RuntimeException -> 0x002f }
            goto L_0x003f
        L_0x002f:
            r7 = move-exception
            boolean r2 = c(r7)     // Catch:{ all -> 0x007b }
            if (r2 != 0) goto L_0x003e
            java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch:{ all -> 0x007b }
            java.lang.String r1 = "checkInitAuthority"
            r0.<init>(r1, r7)     // Catch:{ all -> 0x007b }
            throw r0     // Catch:{ all -> 0x007b }
        L_0x003e:
            r7 = r0
        L_0x003f:
            if (r7 == 0) goto L_0x0063
            android.content.pm.ProviderInfo[] r0 = r7.providers     // Catch:{ all -> 0x007b }
            if (r0 == 0) goto L_0x0063
            android.content.pm.ProviderInfo[] r7 = r7.providers     // Catch:{ all -> 0x007b }
            int r0 = r7.length     // Catch:{ all -> 0x007b }
            r2 = 0
        L_0x0049:
            if (r2 >= r0) goto L_0x0063
            r3 = r7[r2]     // Catch:{ all -> 0x007b }
            java.lang.String r4 = r3.name     // Catch:{ all -> 0x007b }
            java.lang.Class<com.amap.bundle.mapstorage.MPSharedPreferences> r5 = com.amap.bundle.mapstorage.MPSharedPreferences.class
            java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x007b }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x007b }
            if (r4 == 0) goto L_0x0060
            java.lang.String r7 = r3.authority     // Catch:{ all -> 0x007b }
            h = r7     // Catch:{ all -> 0x007b }
            goto L_0x0063
        L_0x0060:
            int r2 = r2 + 1
            goto L_0x0049
        L_0x0063:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x007b }
            java.lang.String r0 = "content://"
            r7.<init>(r0)     // Catch:{ all -> 0x007b }
            java.lang.String r0 = h     // Catch:{ all -> 0x007b }
            r7.append(r0)     // Catch:{ all -> 0x007b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x007b }
            android.net.Uri r7 = android.net.Uri.parse(r7)     // Catch:{ all -> 0x007b }
            i = r7     // Catch:{ all -> 0x007b }
        L_0x0079:
            monitor-exit(r6)     // Catch:{ all -> 0x007b }
            goto L_0x007e
        L_0x007b:
            r7 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x007b }
            throw r7
        L_0x007e:
            android.net.Uri r7 = i
            if (r7 == 0) goto L_0x0084
            r7 = 1
            return r7
        L_0x0084:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.mapstorage.MPSharedPreferences.b(android.content.Context):boolean");
    }

    /* access modifiers changed from: private */
    public static boolean c(Throwable th) {
        if ((th instanceof RuntimeException) && th.getMessage() != null && th.getMessage().contains("Package manager has died")) {
            Throwable e2 = e(th);
            if ((e2 instanceof DeadObjectException) || e2.getClass().getName().equals("android.os.TransactionTooLargeException")) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean d(Throwable th) {
        return (th instanceof RuntimeException) && th.getMessage() != null && th.getMessage().contains("unstableCount < 0: -1") && (e(th) instanceof IllegalStateException);
    }

    private static Throwable e(Throwable th) {
        Throwable th2;
        Throwable cause = th.getCause();
        Throwable th3 = null;
        while (true) {
            Throwable th4 = th3;
            th3 = cause;
            th2 = th4;
            if (th3 == null) {
                break;
            }
            cause = th3.getCause();
        }
        return th2 == null ? new Throwable() : th2;
    }

    public static SharedPreferences a(Context context, String str) {
        return new MPSharedPreferences(context, str, 0);
    }

    @Deprecated
    public MPSharedPreferences() {
    }

    private MPSharedPreferences(Context context, String str, int i2) {
        this.a = context;
        this.b = str;
        this.c = i2;
        this.d = a(this.a);
    }

    public Map<String, ?> getAll() {
        Map<String, ?> map = (Map) a("getAll", null, null);
        return map == null ? new HashMap() : map;
    }

    public String getString(String str, String str2) {
        return (String) a("getString", str, str2);
    }

    public int getInt(String str, int i2) {
        return ((Integer) a("getInt", str, Integer.valueOf(i2))).intValue();
    }

    public long getLong(String str, long j2) {
        return ((Long) a("getLong", str, Long.valueOf(j2))).longValue();
    }

    public float getFloat(String str, float f2) {
        return ((Float) a("getFloat", str, Float.valueOf(f2))).floatValue();
    }

    public boolean getBoolean(String str, boolean z) {
        return ((Boolean) a("getBoolean", str, Boolean.valueOf(z))).booleanValue();
    }

    public boolean contains(String str) {
        return ((Boolean) a("contains", str, Boolean.FALSE)).booleanValue();
    }

    public Editor edit() {
        return new b();
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (this) {
            if (this.f == null) {
                this.f = new WeakHashMap<>();
            }
            Boolean bool = (Boolean) a("registerOnSharedPreferenceChangeListener", null, Boolean.FALSE);
            if (bool != null && bool.booleanValue()) {
                this.f.put(onSharedPreferenceChangeListener, e);
                if (this.g == null) {
                    this.g = new BroadcastReceiver() {
                        public void onReceive(Context context, Intent intent) {
                            String stringExtra = intent.getStringExtra("name");
                            List list = (List) intent.getSerializableExtra("value");
                            if (MPSharedPreferences.this.b.equals(stringExtra) && list != null) {
                                HashSet<OnSharedPreferenceChangeListener> hashSet = new HashSet<>(MPSharedPreferences.this.f.keySet());
                                for (int size = list.size() - 1; size >= 0; size--) {
                                    String str = (String) list.get(size);
                                    for (OnSharedPreferenceChangeListener onSharedPreferenceChangeListener : hashSet) {
                                        if (onSharedPreferenceChangeListener != null) {
                                            onSharedPreferenceChangeListener.onSharedPreferenceChanged(MPSharedPreferences.this, str);
                                        }
                                    }
                                }
                            }
                        }
                    };
                    this.a.registerReceiver(this.g, new IntentFilter(b(this.b)));
                }
            }
        }
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (this) {
            a("unregisterOnSharedPreferenceChangeListener", null, Boolean.FALSE);
            if (this.f != null) {
                this.f.remove(onSharedPreferenceChangeListener);
                if (this.f.isEmpty() && this.g != null) {
                    this.a.unregisterReceiver(this.g);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0057 A[SYNTHETIC, Splitter:B:21:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x006f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0070 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.Object a(java.lang.String r9, java.lang.String r10, java.lang.Object r11) {
        /*
            r8 = this;
            boolean r0 = r8.d
            r1 = 0
            if (r0 != 0) goto L_0x006d
            android.content.Context r0 = r8.a
            boolean r0 = r8.b(r0)
            if (r0 == 0) goto L_0x006d
            android.net.Uri r0 = i
            java.lang.String r2 = r8.b
            android.net.Uri r0 = android.net.Uri.withAppendedPath(r0, r2)
            android.net.Uri r3 = android.net.Uri.withAppendedPath(r0, r9)
            r9 = 3
            java.lang.String[] r6 = new java.lang.String[r9]
            r9 = 0
            int r0 = r8.c
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r6[r9] = r0
            r9 = 1
            r6[r9] = r10
            r9 = 2
            if (r11 != 0) goto L_0x002d
            r10 = r1
            goto L_0x0031
        L_0x002d:
            java.lang.String r10 = java.lang.String.valueOf(r11)
        L_0x0031:
            r6[r9] = r10
            android.content.Context r9 = r8.a     // Catch:{ SecurityException -> 0x0054, RuntimeException -> 0x0041 }
            android.content.ContentResolver r2 = r9.getContentResolver()     // Catch:{ SecurityException -> 0x0054, RuntimeException -> 0x0041 }
            r4 = 0
            r5 = 0
            r7 = 0
            android.database.Cursor r9 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ SecurityException -> 0x0054, RuntimeException -> 0x0041 }
            goto L_0x0055
        L_0x0041:
            r9 = move-exception
            boolean r10 = c(r9)
            if (r10 != 0) goto L_0x0054
            boolean r10 = d(r9)
            if (r10 != 0) goto L_0x0054
            java.lang.RuntimeException r10 = new java.lang.RuntimeException
            r10.<init>(r9)
            throw r10
        L_0x0054:
            r9 = r1
        L_0x0055:
            if (r9 == 0) goto L_0x006d
            android.os.Bundle r10 = r9.getExtras()     // Catch:{ RuntimeException -> 0x005c }
            goto L_0x005d
        L_0x005c:
            r10 = r1
        L_0x005d:
            if (r10 == 0) goto L_0x006a
            java.lang.String r0 = "value"
            java.lang.Object r0 = r10.get(r0)
            r10.clear()
            r1 = r0
        L_0x006a:
            r9.close()
        L_0x006d:
            if (r1 != 0) goto L_0x0070
            return r11
        L_0x0070:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.mapstorage.MPSharedPreferences.a(java.lang.String, java.lang.String, java.lang.Object):java.lang.Object");
    }

    private static String b(String str) {
        return String.format("%1$s_%2$s", new Object[]{MPSharedPreferences.class.getName(), str});
    }

    public boolean onCreate() {
        if (!b(getContext())) {
            return false;
        }
        this.j = new UriMatcher(-1);
        this.j.addURI(h, "*/getAll", 1);
        this.j.addURI(h, "*/getString", 2);
        this.j.addURI(h, "*/getInt", 3);
        this.j.addURI(h, "*/getLong", 4);
        this.j.addURI(h, "*/getFloat", 5);
        this.j.addURI(h, "*/getBoolean", 6);
        this.j.addURI(h, "*/contains", 7);
        this.j.addURI(h, "*/apply", 8);
        this.j.addURI(h, "*/commit", 9);
        this.j.addURI(h, "*/registerOnSharedPreferenceChangeListener", 10);
        this.j.addURI(h, "*/unregisterOnSharedPreferenceChangeListener", 11);
        return true;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.database.Cursor query(@android.support.annotation.NonNull android.net.Uri r5, java.lang.String[] r6, java.lang.String r7, java.lang.String[] r8, java.lang.String r9) {
        /*
            r4 = this;
            java.util.List r6 = r5.getPathSegments()
            r7 = 0
            java.lang.Object r6 = r6.get(r7)
            java.lang.String r6 = (java.lang.String) r6
            r9 = r8[r7]
            int r9 = java.lang.Integer.parseInt(r9)
            r0 = 1
            r1 = r8[r0]
            r2 = 2
            r8 = r8[r2]
            android.os.Bundle r2 = new android.os.Bundle
            r2.<init>()
            android.content.UriMatcher r3 = r4.j
            int r5 = r3.match(r5)
            switch(r5) {
                case 1: goto L_0x0111;
                case 2: goto L_0x0102;
                case 3: goto L_0x00ef;
                case 4: goto L_0x00dc;
                case 5: goto L_0x00c9;
                case 6: goto L_0x00b5;
                case 7: goto L_0x00a9;
                case 8: goto L_0x0025;
                case 9: goto L_0x0025;
                case 10: goto L_0x0071;
                case 11: goto L_0x0027;
                default: goto L_0x0025;
            }
        L_0x0025:
            goto L_0x0121
        L_0x0027:
            r4.b()
            java.util.HashMap<java.lang.String, java.lang.Integer> r5 = r4.k
            java.lang.Object r5 = r5.get(r6)
            java.lang.Integer r5 = (java.lang.Integer) r5
            if (r5 != 0) goto L_0x0036
            r5 = 0
            goto L_0x003a
        L_0x0036:
            int r5 = r5.intValue()
        L_0x003a:
            int r5 = r5 - r0
            if (r5 > 0) goto L_0x0051
            java.util.HashMap<java.lang.String, java.lang.Integer> r5 = r4.k
            r5.remove(r6)
            java.lang.String r5 = "value"
            java.util.HashMap<java.lang.String, java.lang.Integer> r7 = r4.k
            boolean r6 = r7.containsKey(r6)
            r6 = r6 ^ r0
            r2.putBoolean(r5, r6)
            goto L_0x0121
        L_0x0051:
            java.util.HashMap<java.lang.String, java.lang.Integer> r8 = r4.k
            java.lang.Integer r9 = java.lang.Integer.valueOf(r5)
            r8.put(r6, r9)
            java.util.HashMap<java.lang.String, java.lang.Integer> r8 = r4.k
            java.lang.Object r6 = r8.get(r6)
            java.lang.Integer r6 = (java.lang.Integer) r6
            java.lang.String r8 = "value"
            if (r6 != 0) goto L_0x0069
            r6 = 0
            goto L_0x006d
        L_0x0069:
            int r6 = r6.intValue()
        L_0x006d:
            if (r5 != r6) goto L_0x00c5
            r7 = 1
            goto L_0x00c5
        L_0x0071:
            r4.b()
            java.util.HashMap<java.lang.String, java.lang.Integer> r5 = r4.k
            java.lang.Object r5 = r5.get(r6)
            java.lang.Integer r5 = (java.lang.Integer) r5
            if (r5 != 0) goto L_0x0080
            r5 = 0
            goto L_0x0084
        L_0x0080:
            int r5 = r5.intValue()
        L_0x0084:
            int r5 = r5 + r0
            java.util.HashMap<java.lang.String, java.lang.Integer> r8 = r4.k
            java.lang.Integer r9 = java.lang.Integer.valueOf(r5)
            r8.put(r6, r9)
            java.util.HashMap<java.lang.String, java.lang.Integer> r8 = r4.k
            java.lang.Object r6 = r8.get(r6)
            java.lang.Integer r6 = (java.lang.Integer) r6
            java.lang.String r8 = "value"
            if (r6 != 0) goto L_0x009d
            r6 = 0
            goto L_0x00a1
        L_0x009d:
            int r6 = r6.intValue()
        L_0x00a1:
            if (r5 != r6) goto L_0x00a4
            r7 = 1
        L_0x00a4:
            r2.putBoolean(r8, r7)
            goto L_0x0121
        L_0x00a9:
            java.lang.String r8 = "value"
            android.content.SharedPreferences r5 = r4.a(r6, r9)
            boolean r7 = r5.contains(r1)
            goto L_0x00c5
        L_0x00b5:
            java.lang.String r5 = "value"
            android.content.SharedPreferences r6 = r4.a(r6, r9)
            boolean r7 = java.lang.Boolean.parseBoolean(r8)
            boolean r7 = r6.getBoolean(r1, r7)
            r8 = r5
        L_0x00c5:
            r2.putBoolean(r8, r7)
            goto L_0x0121
        L_0x00c9:
            java.lang.String r5 = "value"
            android.content.SharedPreferences r6 = r4.a(r6, r9)
            float r7 = java.lang.Float.parseFloat(r8)
            float r6 = r6.getFloat(r1, r7)
            r2.putFloat(r5, r6)
            goto L_0x0121
        L_0x00dc:
            java.lang.String r5 = "value"
            android.content.SharedPreferences r6 = r4.a(r6, r9)
            long r7 = java.lang.Long.parseLong(r8)
            long r6 = r6.getLong(r1, r7)
            r2.putLong(r5, r6)
            goto L_0x0121
        L_0x00ef:
            java.lang.String r5 = "value"
            android.content.SharedPreferences r6 = r4.a(r6, r9)
            int r7 = java.lang.Integer.parseInt(r8)
            int r6 = r6.getInt(r1, r7)
            r2.putInt(r5, r6)
            goto L_0x0121
        L_0x0102:
            java.lang.String r5 = "value"
            android.content.SharedPreferences r6 = r4.a(r6, r9)
            java.lang.String r6 = r6.getString(r1, r8)
            r2.putString(r5, r6)
            goto L_0x0121
        L_0x0111:
            java.lang.String r5 = "value"
            android.content.SharedPreferences r6 = r4.a(r6, r9)
            java.util.Map r6 = r6.getAll()
            java.util.HashMap r6 = (java.util.HashMap) r6
            r2.putSerializable(r5, r6)
        L_0x0121:
            com.amap.bundle.mapstorage.MPSharedPreferences$a r5 = new com.amap.bundle.mapstorage.MPSharedPreferences$a
            r5.<init>(r2)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.mapstorage.MPSharedPreferences.query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String):android.database.Cursor");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int update(@android.support.annotation.NonNull android.net.Uri r12, android.content.ContentValues r13, java.lang.String r14, java.lang.String[] r15) {
        /*
            r11 = this;
            java.util.List r14 = r12.getPathSegments()
            r0 = 0
            java.lang.Object r14 = r14.get(r0)
            java.lang.String r14 = (java.lang.String) r14
            r1 = r15[r0]
            int r1 = java.lang.Integer.parseInt(r1)
            android.content.SharedPreferences r1 = r11.a(r14, r1)
            android.content.UriMatcher r2 = r11.j
            int r12 = r2.match(r12)
            switch(r12) {
                case 8: goto L_0x0020;
                case 9: goto L_0x0020;
                default: goto L_0x001e;
            }
        L_0x001e:
            goto L_0x0136
        L_0x0020:
            java.util.HashMap<java.lang.String, java.lang.Integer> r2 = r11.k
            r3 = 1
            if (r2 == 0) goto L_0x003d
            java.util.HashMap<java.lang.String, java.lang.Integer> r2 = r11.k
            java.lang.Object r2 = r2.get(r14)
            if (r2 == 0) goto L_0x003d
            java.util.HashMap<java.lang.String, java.lang.Integer> r2 = r11.k
            java.lang.Object r2 = r2.get(r14)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 <= 0) goto L_0x003d
            r2 = 1
            goto L_0x003e
        L_0x003d:
            r2 = 0
        L_0x003e:
            r4 = 0
            if (r2 == 0) goto L_0x004e
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Map r5 = r1.getAll()
            r10 = r5
            r5 = r4
            r4 = r10
            goto L_0x004f
        L_0x004e:
            r5 = r4
        L_0x004f:
            android.content.SharedPreferences$Editor r1 = r1.edit()
            r15 = r15[r3]
            boolean r15 = java.lang.Boolean.parseBoolean(r15)
            if (r15 == 0) goto L_0x0082
            if (r2 == 0) goto L_0x007f
            boolean r15 = r4.isEmpty()
            if (r15 != 0) goto L_0x007f
            java.util.Set r15 = r4.entrySet()
            java.util.Iterator r15 = r15.iterator()
        L_0x006b:
            boolean r6 = r15.hasNext()
            if (r6 == 0) goto L_0x007f
            java.lang.Object r6 = r15.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r6 = r6.getKey()
            r5.add(r6)
            goto L_0x006b
        L_0x007f:
            r1.clear()
        L_0x0082:
            java.util.Set r15 = r13.valueSet()
            java.util.Iterator r15 = r15.iterator()
        L_0x008a:
            boolean r6 = r15.hasNext()
            if (r6 == 0) goto L_0x0114
            java.lang.Object r6 = r15.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r7 = r6.getKey()
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r6 = r6.getValue()
            boolean r8 = r6 instanceof com.amap.bundle.mapstorage.MPSharedPreferences.b
            if (r8 != 0) goto L_0x00c3
            if (r6 != 0) goto L_0x00a7
            goto L_0x00c3
        L_0x00a7:
            if (r2 == 0) goto L_0x00d1
            boolean r8 = r4.containsKey(r7)
            if (r8 == 0) goto L_0x00bf
            boolean r8 = r4.containsKey(r7)
            if (r8 == 0) goto L_0x00d1
            java.lang.Object r8 = r4.get(r7)
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x00d1
        L_0x00bf:
            r5.add(r7)
            goto L_0x00d1
        L_0x00c3:
            r1.remove(r7)
            if (r2 == 0) goto L_0x00d1
            boolean r8 = r4.containsKey(r7)
            if (r8 == 0) goto L_0x00d1
            r5.add(r7)
        L_0x00d1:
            boolean r8 = r6 instanceof java.lang.String
            if (r8 == 0) goto L_0x00db
            java.lang.String r6 = (java.lang.String) r6
            r1.putString(r7, r6)
            goto L_0x008a
        L_0x00db:
            boolean r8 = r6 instanceof java.lang.Integer
            if (r8 == 0) goto L_0x00e9
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            r1.putInt(r7, r6)
            goto L_0x008a
        L_0x00e9:
            boolean r8 = r6 instanceof java.lang.Long
            if (r8 == 0) goto L_0x00f7
            java.lang.Long r6 = (java.lang.Long) r6
            long r8 = r6.longValue()
            r1.putLong(r7, r8)
            goto L_0x008a
        L_0x00f7:
            boolean r8 = r6 instanceof java.lang.Float
            if (r8 == 0) goto L_0x0105
            java.lang.Float r6 = (java.lang.Float) r6
            float r6 = r6.floatValue()
            r1.putFloat(r7, r6)
            goto L_0x008a
        L_0x0105:
            boolean r8 = r6 instanceof java.lang.Boolean
            if (r8 == 0) goto L_0x008a
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            r1.putBoolean(r7, r6)
            goto L_0x008a
        L_0x0114:
            if (r2 == 0) goto L_0x011e
            boolean r15 = r5.isEmpty()
            if (r15 == 0) goto L_0x011e
        L_0x011c:
            r0 = 1
            goto L_0x0133
        L_0x011e:
            switch(r12) {
                case 8: goto L_0x012c;
                case 9: goto L_0x0122;
                default: goto L_0x0121;
            }
        L_0x0121:
            goto L_0x0133
        L_0x0122:
            boolean r12 = r1.commit()
            if (r12 == 0) goto L_0x0133
            r11.a(r14, r5)
            goto L_0x011c
        L_0x012c:
            r1.apply()
            r11.a(r14, r5)
            goto L_0x011c
        L_0x0133:
            r13.clear()
        L_0x0136:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.mapstorage.MPSharedPreferences.update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[]):int");
    }

    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("No external call");
    }

    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No external insert");
    }

    public int delete(@NonNull Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("No external delete");
    }

    private SharedPreferences a(String str, int i2) {
        return getContext().getSharedPreferences(str, i2);
    }

    private void b() {
        if (this.k == null) {
            this.k = new HashMap<>();
        }
    }

    private void a(String str, ArrayList<String> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            Intent intent = new Intent();
            intent.setAction(b(str));
            intent.setPackage(getContext().getPackageName());
            intent.putExtra("name", str);
            intent.putExtra("value", arrayList);
            getContext().sendBroadcast(intent);
        }
    }
}
