package com.autonavi.common.impl.io;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.util.Base64;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.KeyValueStorage.WebStorage;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DynamicStorageImpl implements WebStorage {
    private Editor editor;
    private final SharedPreferences storage;

    public DynamicStorageImpl beginTransaction() {
        return this;
    }

    public DynamicStorageImpl(String str) {
        Application application = AMapAppGlobal.getApplication();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("__v2__");
        this.storage = application.getSharedPreferences(sb.toString(), 0);
        bugfix700(str);
    }

    private void bugfix700(String str) {
        if (this.storage.getAll().isEmpty()) {
            SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences(str, 0);
            Map<String, ?> all = sharedPreferences.getAll();
            if (all.size() > 0) {
                Editor edit = this.storage.edit();
                for (Entry next : all.entrySet()) {
                    String obscureKey700 = obscureKey700((String) next.getKey());
                    Object value = next.getValue();
                    if (value instanceof String) {
                        edit.putString(encode(obscureKey700), encode(obscureKey700((String) value)));
                    }
                }
                apply(edit);
            }
            apply(sharedPreferences.edit().clear());
        }
    }

    @Deprecated
    private static String obscureKey700(String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char) ((charArray[i] ^ '^') & 65535);
        }
        return new String(charArray);
    }

    public void commit() {
        Editor editor2 = this.editor;
        if (editor2 != null) {
            apply(editor2);
        }
    }

    private void apply(Editor editor2) {
        if (VERSION.SDK_INT < 9) {
            editor2.commit();
        } else {
            editor2.apply();
        }
    }

    public DynamicStorageImpl reset() {
        boolean z = this.editor == null;
        Editor edit = z ? this.storage.edit() : this.editor;
        edit.clear();
        if (z) {
            apply(edit);
        }
        return this;
    }

    public WebStorage remove(String str) {
        return set(str, (String) null);
    }

    public DynamicStorageImpl set(String str, String str2) {
        boolean z = this.editor == null;
        Editor edit = z ? this.storage.edit() : this.editor;
        if (str2 == null) {
            edit.remove(encode(str));
        } else {
            edit.putString(encode(str), encode(str2));
        }
        if (z) {
            apply(edit);
        }
        return this;
    }

    public String get(String str) {
        return decode(this.storage.getString(encode(str), ""));
    }

    private static String encode(String str) {
        if (str == null || str.length() <= 0) {
            return str;
        }
        String obscureKey700 = obscureKey700(str);
        try {
            return Base64.encodeToString(obscureKey700.getBytes("UTF-8"), 2);
        } catch (UnsupportedEncodingException unused) {
            return obscureKey700;
        }
    }

    /* access modifiers changed from: private */
    public static String decode(String str) {
        if (str == null || str.length() <= 0) {
            return str;
        }
        try {
            String str2 = new String(Base64.decode(str, 2), "UTF-8");
            try {
                return obscureKey700(str2);
            } catch (UnsupportedEncodingException unused) {
                return str2;
            }
        } catch (UnsupportedEncodingException unused2) {
            return str;
        }
    }

    public Iterator<String> iterator() {
        final Iterator it = new HashSet(this.storage.getAll().keySet()).iterator();
        return new Iterator<String>() {
            private String c;

            public final void remove() {
                it.remove();
                DynamicStorageImpl.this.set(this.c, (String) null);
            }

            public final boolean hasNext() {
                return it.hasNext();
            }

            public final /* synthetic */ Object next() {
                this.c = DynamicStorageImpl.decode((String) it.next());
                return this.c;
            }
        };
    }

    public int size() {
        return this.storage.getAll().size();
    }
}
