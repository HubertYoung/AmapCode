package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: ya reason: default package */
/* compiled from: EncryptSharePreferences */
public final class ya implements SharedPreferences, Editor {
    public String a;
    public volatile int b = 0;
    public final Object c = new Object();
    /* access modifiers changed from: private */
    public SharedPreferences d;
    private Editor e;

    public final Editor edit() {
        return this;
    }

    public ya(Context context, String str) {
        this.a = str;
        this.d = context.getSharedPreferences(str, 0);
    }

    /* access modifiers changed from: private */
    public Editor a() {
        if (this.e == null) {
            this.e = this.d.edit();
        }
        return this.e;
    }

    private void b() {
        if (this.b == 1) {
            try {
                this.c.wait();
            } catch (InterruptedException e2) {
                if (bno.a) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public final Editor putString(String str, String str2) {
        synchronized (this.c) {
            b();
            if (this.b == 2) {
                a().putString(yb.a(str), yb.c(str2));
            } else {
                a().putString(str, str2);
            }
        }
        return this;
    }

    public final Editor putStringSet(String str, Set<String> set) {
        a().putStringSet(str, set);
        return this;
    }

    public final Editor putInt(String str, int i) {
        a().putInt(str, i);
        return this;
    }

    public final Editor putLong(String str, long j) {
        a().putLong(str, j);
        return this;
    }

    public final Editor putFloat(String str, float f) {
        a().putFloat(str, f);
        return this;
    }

    public final Editor putBoolean(String str, boolean z) {
        a().putBoolean(str, z);
        return this;
    }

    public final Editor remove(String str) {
        synchronized (this.c) {
            b();
            if (this.b == 2) {
                a().remove(str);
                str = yb.a(str);
            }
            a().remove(str);
        }
        return this;
    }

    public final Editor clear() {
        synchronized (this.c) {
            b();
            a().clear();
        }
        return this;
    }

    public final boolean commit() {
        boolean commit;
        synchronized (this.c) {
            b();
            commit = a().commit();
        }
        return commit;
    }

    public final void apply() {
        synchronized (this.c) {
            b();
            a().apply();
        }
    }

    public final Map<String, ?> getAll() {
        synchronized (this.c) {
            b();
            if (this.b == 2) {
                HashMap hashMap = new HashMap();
                Map<String, ?> all = this.d.getAll();
                if (all != null) {
                    if (!all.isEmpty()) {
                        for (Entry next : all.entrySet()) {
                            Object value = next.getValue();
                            if (value instanceof String) {
                                String str = (String) next.getKey();
                                if (!TextUtils.isEmpty(str) && str.startsWith("qplxzg")) {
                                    if (str.endsWith("gffdge")) {
                                        str = new String(Base64.decode(str.substring(6, str.length() - 6), 2));
                                    }
                                }
                                hashMap.put(str, yb.b(value.toString()));
                            } else {
                                hashMap.put(next.getKey(), value);
                            }
                        }
                        return hashMap;
                    }
                }
                return null;
            }
            Map<String, ?> all2 = this.d.getAll();
            return all2;
        }
    }

    @Nullable
    public final String getString(String str, String str2) {
        synchronized (this.c) {
            try {
                b();
                if (this.b != 0) {
                    if (this.b != 3) {
                        String string = this.d.getString(yb.a(str), str2);
                        if (TextUtils.equals(string, str2)) {
                            return str2;
                        }
                        String b2 = yb.b(string);
                        return b2;
                    }
                }
                String string2 = this.d.getString(str, str2);
                return string2;
            }
        }
    }

    @Nullable
    public final Set<String> getStringSet(String str, Set<String> set) {
        return this.d.getStringSet(str, set);
    }

    public final int getInt(String str, int i) {
        return this.d.getInt(str, i);
    }

    public final long getLong(String str, long j) {
        return this.d.getLong(str, j);
    }

    public final float getFloat(String str, float f) {
        return this.d.getFloat(str, f);
    }

    public final boolean getBoolean(String str, boolean z) {
        return this.d.getBoolean(str, z);
    }

    public final boolean contains(String str) {
        boolean contains;
        synchronized (this.c) {
            try {
                b();
                contains = this.d.contains(str);
                if (!contains) {
                    contains = this.d.contains(yb.a(str));
                }
            }
        }
        return contains;
    }

    public final void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.d.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public final void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.d.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }
}
