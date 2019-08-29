package com.autonavi.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import com.alipay.mobile.quinox.bundle.IBundle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public final class PageBundle {
    public static final PageBundle EMPTY;
    public static final int FLAG_LAUNCH_MODE_SINGLE_INSTANCE = 16;
    public static final int FLAG_LAUNCH_MODE_SINGLE_INSTANCE_WITHOUT_REUSE = 32;
    public static final int FLAG_LAUNCH_MODE_SINGLE_TASK = 4;
    public static final int FLAG_LAUNCH_MODE_SINGLE_TASK_WITHOUT_REUSE = 8;
    public static final int FLAG_LAUNCH_MODE_SINGLE_TOP = 2;
    public static final int FLAG_LAUNCH_MODE_SINGLE_TOP_ALLOW_DUPLICATE = 64;
    ArrayMap<String, Object> a;
    private String b;
    private String c;
    private boolean d;
    private boolean e;
    private boolean f;
    private int g;
    private int h;
    private String i;
    private ClassLoader j;

    static {
        PageBundle pageBundle = new PageBundle();
        EMPTY = pageBundle;
        pageBundle.a = new ArrayMap<>();
    }

    public PageBundle() {
        this.a = null;
        this.d = false;
        this.e = true;
        this.f = true;
        this.g = 0;
        this.h = 0;
        this.a = new ArrayMap<>();
        this.j = getClass().getClassLoader();
    }

    public PageBundle(String str, String str2) {
        this();
        this.b = str;
        this.c = str2;
    }

    public PageBundle(ClassLoader classLoader) {
        this.a = null;
        this.d = false;
        this.e = true;
        this.f = true;
        this.g = 0;
        this.h = 0;
        this.a = new ArrayMap<>();
        this.j = classLoader;
    }

    public PageBundle(int i2) {
        this.a = null;
        this.d = false;
        this.e = true;
        this.f = true;
        this.g = 0;
        this.h = 0;
        this.a = new ArrayMap<>(i2);
        this.j = getClass().getClassLoader();
    }

    public PageBundle(PageBundle pageBundle) {
        this.a = null;
        this.d = false;
        this.e = true;
        this.f = true;
        this.g = 0;
        this.h = 0;
        if (pageBundle.a != null) {
            this.a = new ArrayMap<>((SimpleArrayMap) pageBundle.a);
        } else {
            this.a = null;
        }
        this.d = pageBundle.d;
        this.e = pageBundle.e;
        this.j = pageBundle.j;
        this.b = pageBundle.getAction();
        this.c = pageBundle.getPackageName();
    }

    public PageBundle(@NonNull String str, @NonNull String str2, PageBundle pageBundle) {
        this.a = null;
        this.d = false;
        this.e = true;
        this.f = true;
        this.g = 0;
        this.h = 0;
        if (pageBundle.a != null) {
            this.a = new ArrayMap<>((SimpleArrayMap) pageBundle.a);
        } else {
            this.a = null;
        }
        this.d = pageBundle.d;
        this.e = pageBundle.e;
        this.j = pageBundle.j;
        this.b = str;
        this.c = str2;
    }

    public PageBundle(Intent intent) {
        this.a = null;
        this.d = false;
        this.e = true;
        this.f = true;
        this.g = 0;
        this.h = 0;
        this.b = intent.getAction();
        this.c = intent.getPackage();
        this.a = new ArrayMap<>();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Set<String> keySet = extras.keySet();
            if (keySet != null) {
                for (String str : keySet) {
                    this.a.put(str, extras.get(str));
                }
            }
        }
    }

    public static PageBundle forPair(String str, String str2) {
        PageBundle pageBundle = new PageBundle(1);
        pageBundle.putString(str, str2);
        return pageBundle;
    }

    public final String getPairValue() {
        if (this.a.size() == 0) {
            return null;
        }
        Object valueAt = this.a.valueAt(0);
        try {
            return (String) valueAt;
        } catch (ClassCastException unused) {
            a("getPairValue()", valueAt, "String");
            return null;
        }
    }

    public final void setClassLoader(ClassLoader classLoader) {
        this.j = classLoader;
    }

    public final ClassLoader getClassLoader() {
        return this.j;
    }

    public final boolean setAllowFds(boolean z) {
        boolean z2 = this.f;
        this.f = z;
        return z2;
    }

    public final Object clone() {
        return new PageBundle(this);
    }

    public final int size() {
        return this.a.size();
    }

    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    public final void clear() {
        this.a.clear();
        this.d = false;
        this.e = true;
    }

    public final boolean containsKey(String str) {
        return this.a.containsKey(str);
    }

    public final Object get(String str) {
        return this.a.get(str);
    }

    public final void remove(String str) {
        this.a.remove(str);
    }

    public final Set<String> keySet() {
        return this.a.keySet();
    }

    public final void putBoolean(String str, boolean z) {
        this.a.put(str, Boolean.valueOf(z));
    }

    public final void putByte(String str, byte b2) {
        this.a.put(str, Byte.valueOf(b2));
    }

    public final void putChar(String str, char c2) {
        this.a.put(str, Character.valueOf(c2));
    }

    public final void putShort(String str, short s) {
        this.a.put(str, Short.valueOf(s));
    }

    public final void putInt(String str, int i2) {
        this.a.put(str, Integer.valueOf(i2));
    }

    public final void putLong(String str, long j2) {
        this.a.put(str, Long.valueOf(j2));
    }

    public final void putFloat(String str, float f2) {
        this.a.put(str, Float.valueOf(f2));
    }

    public final void putDouble(String str, double d2) {
        this.a.put(str, Double.valueOf(d2));
    }

    public final void putString(String str, String str2) {
        this.a.put(str, str2);
    }

    public final void putCharSequence(String str, CharSequence charSequence) {
        this.a.put(str, charSequence);
    }

    public final void putIntegerArrayList(String str, ArrayList<Integer> arrayList) {
        this.a.put(str, arrayList);
    }

    public final void putStringArrayList(String str, ArrayList<String> arrayList) {
        this.a.put(str, arrayList);
    }

    public final void putCharSequenceArrayList(String str, ArrayList<CharSequence> arrayList) {
        this.a.put(str, arrayList);
    }

    public final void putSerializable(String str, Serializable serializable) {
        this.a.put(str, serializable);
    }

    public final void putBooleanArray(String str, boolean[] zArr) {
        this.a.put(str, zArr);
    }

    public final void putByteArray(String str, byte[] bArr) {
        this.a.put(str, bArr);
    }

    public final void putShortArray(String str, short[] sArr) {
        this.a.put(str, sArr);
    }

    public final void putCharArray(String str, char[] cArr) {
        this.a.put(str, cArr);
    }

    public final void putIntArray(String str, int[] iArr) {
        this.a.put(str, iArr);
    }

    public final void putLongArray(String str, long[] jArr) {
        this.a.put(str, jArr);
    }

    public final void putFloatArray(String str, float[] fArr) {
        this.a.put(str, fArr);
    }

    public final void putDoubleArray(String str, double[] dArr) {
        this.a.put(str, dArr);
    }

    public final void putStringArray(String str, String[] strArr) {
        this.a.put(str, strArr);
    }

    public final void putCharSequenceArray(String str, CharSequence[] charSequenceArr) {
        this.a.put(str, charSequenceArr);
    }

    public final void putBundle(String str, PageBundle pageBundle) {
        this.a.put(str, pageBundle);
    }

    public final boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    private static void a(String str, Object obj, String str2, Object obj2) {
        StringBuilder sb = new StringBuilder();
        sb.append("Key ");
        sb.append(str);
        sb.append(" expected ");
        sb.append(str2);
        sb.append(" but value was a ");
        sb.append(obj.getClass().getName());
        sb.append(".  The default value ");
        sb.append(obj2);
        sb.append(" was returned.");
    }

    private static void a(String str, Object obj, String str2) {
        a(str, obj, str2, "<null>");
    }

    public final boolean getBoolean(String str, boolean z) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return z;
        }
        try {
            return ((Boolean) obj).booleanValue();
        } catch (ClassCastException unused) {
            a(str, obj, "Boolean", Boolean.valueOf(z));
            return z;
        }
    }

    public final byte getByte(String str) {
        return getByte(str, 0).byteValue();
    }

    public final Byte getByte(String str, byte b2) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return Byte.valueOf(b2);
        }
        try {
            return (Byte) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "Byte", Byte.valueOf(b2));
            return Byte.valueOf(b2);
        }
    }

    public final char getChar(String str) {
        return getChar(str, 0);
    }

    public final char getChar(String str, char c2) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return c2;
        }
        try {
            return ((Character) obj).charValue();
        } catch (ClassCastException unused) {
            a(str, obj, "Character", Character.valueOf(c2));
            return c2;
        }
    }

    public final short getShort(String str) {
        return getShort(str, 0);
    }

    public final short getShort(String str, short s) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return s;
        }
        try {
            return ((Short) obj).shortValue();
        } catch (ClassCastException unused) {
            a(str, obj, "Short", Short.valueOf(s));
            return s;
        }
    }

    public final int getInt(String str) {
        return getInt(str, 0);
    }

    public final int getInt(String str, int i2) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return i2;
        }
        try {
            return ((Integer) obj).intValue();
        } catch (ClassCastException unused) {
            a(str, obj, "Integer", Integer.valueOf(i2));
            return i2;
        }
    }

    public final long getLong(String str) {
        return getLong(str, 0);
    }

    public final long getLong(String str, long j2) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return j2;
        }
        try {
            return ((Long) obj).longValue();
        } catch (ClassCastException unused) {
            a(str, obj, "Long", Long.valueOf(j2));
            return j2;
        }
    }

    public final float getFloat(String str) {
        return getFloat(str, 0.0f);
    }

    public final float getFloat(String str, float f2) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return f2;
        }
        try {
            return ((Float) obj).floatValue();
        } catch (ClassCastException unused) {
            a(str, obj, "Float", Float.valueOf(f2));
            return f2;
        }
    }

    public final double getDouble(String str) {
        return getDouble(str, 0.0d);
    }

    public final double getDouble(String str, double d2) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return d2;
        }
        try {
            return ((Double) obj).doubleValue();
        } catch (ClassCastException unused) {
            a(str, obj, "Double", Double.valueOf(d2));
            return d2;
        }
    }

    public final String getString(String str) {
        Object obj = this.a.get(str);
        try {
            return (String) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "String");
            return null;
        }
    }

    public final String getString(String str, String str2) {
        String string = getString(str);
        return string == null ? str2 : string;
    }

    public final CharSequence getCharSequence(String str) {
        Object obj = this.a.get(str);
        try {
            return (CharSequence) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "CharSequence");
            return null;
        }
    }

    public final CharSequence getCharSequence(String str, CharSequence charSequence) {
        CharSequence charSequence2 = getCharSequence(str);
        return charSequence2 == null ? charSequence : charSequence2;
    }

    public final PageBundle getBundle(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (PageBundle) obj;
        } catch (ClassCastException unused) {
            a(str, obj, IBundle.TAG);
            return null;
        }
    }

    public final Serializable getSerializable(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (Serializable) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "Serializable");
            return null;
        }
    }

    public final ArrayList<Integer> getIntegerArrayList(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "ArrayList<Integer>");
            return null;
        }
    }

    public final ArrayList<String> getStringArrayList(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "ArrayList<String>");
            return null;
        }
    }

    public final ArrayList<CharSequence> getCharSequenceArrayList(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (ArrayList) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "ArrayList<CharSequence>");
            return null;
        }
    }

    public final boolean[] getBooleanArray(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (boolean[]) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "byte[]");
            return null;
        }
    }

    public final byte[] getByteArray(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (byte[]) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "byte[]");
            return null;
        }
    }

    public final short[] getShortArray(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (short[]) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "short[]");
            return null;
        }
    }

    public final char[] getCharArray(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (char[]) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "char[]");
            return null;
        }
    }

    public final int[] getIntArray(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (int[]) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "int[]");
            return null;
        }
    }

    public final long[] getLongArray(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (long[]) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "long[]");
            return null;
        }
    }

    public final float[] getFloatArray(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (float[]) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "float[]");
            return null;
        }
    }

    public final double[] getDoubleArray(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (double[]) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "double[]");
            return null;
        }
    }

    public final String[] getStringArray(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (String[]) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "String[]");
            return null;
        }
    }

    public final CharSequence[] getCharSequenceArray(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (CharSequence[]) obj;
        } catch (ClassCastException unused) {
            a(str, obj, "CharSequence[]");
            return null;
        }
    }

    public final void putObject(String str, Object obj) {
        this.a.put(str, obj);
    }

    public final Object getObject(String str) {
        return this.a.get(str);
    }

    public final String getAction() {
        return this.b;
    }

    public final String getPackageName() {
        return this.c;
    }

    public final void setFlags(int i2) {
        this.g = i2;
    }

    public final int getFlags() {
        return this.g;
    }

    public final void setFlags(int i2, int i3) {
        this.g = i2;
        this.h = i3;
    }

    public final int getPageCnt() {
        return this.h;
    }

    public final String getUniqueID() {
        return this.i;
    }

    public final void setUniqueID(String str) {
        this.i = str;
    }
}
