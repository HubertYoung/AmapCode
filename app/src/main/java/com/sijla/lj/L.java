package com.sijla.lj;

public class L {
    private static Class<?> a = Number.class;
    private static Class<?> b = Byte.class;
    private static Class<?> c = Short.class;
    private static Class<?> d = Integer.class;
    private static Class<?> e = Long.class;
    private static Class<?> f = Float.class;
    private static Class<?> g = Double.class;
    private long h;

    private native synchronized int _getGlobal(long j, String str);

    private native synchronized Object _getObjectFromUserdata(long j, int i);

    private native synchronized int _isBoolean(long j, int i);

    private native synchronized int _isFunction(long j, int i);

    private native synchronized boolean _isJavaFunction(long j, int i);

    private native synchronized int _isNil(long j, int i);

    private native synchronized int _isNumber(long j, int i);

    private native synchronized boolean _isObject(long j, int i);

    private native synchronized int _isString(long j, int i);

    private native synchronized int _isTable(long j, int i);

    private native synchronized int _isUserdata(long j, int i);

    private native synchronized int _pcall(long j, int i, int i2, int i3);

    private native synchronized void _pop(long j, int i);

    private native synchronized void _pushBoolean(long j, int i);

    private native synchronized void _pushInteger(long j, long j2);

    private native synchronized void _pushJavaFunction(long j, b bVar);

    private native synchronized void _pushJavaObject(long j, Object obj);

    private native synchronized void _pushNil(long j);

    private native synchronized void _pushNumber(long j, double d2);

    private native synchronized void _pushString(long j, String str);

    private native synchronized int _rawGetI(long j, int i, long j2);

    private native synchronized void _setGlobal(long j, String str);

    private native synchronized void _setTop(long j, int i);

    private native synchronized int _toBoolean(long j, int i);

    private native synchronized double _toNumber(long j, int i);

    private native synchronized String _toString(long j, int i);

    static {
        System.loadLibrary("Qt");
    }

    public long a() {
        return this.h;
    }

    public void a(int i) {
        _setTop(this.h, i);
    }

    public boolean b(int i) {
        return _isNumber(this.h, i) != 0;
    }

    public boolean c(int i) {
        return _isString(this.h, i) != 0;
    }

    public boolean d(int i) {
        return _isFunction(this.h, i) != 0;
    }

    public boolean e(int i) {
        return _isUserdata(this.h, i) != 0;
    }

    public boolean f(int i) {
        return _isTable(this.h, i) != 0;
    }

    public boolean g(int i) {
        return _isBoolean(this.h, i) != 0;
    }

    public boolean h(int i) {
        return _isNil(this.h, i) != 0;
    }

    public double i(int i) {
        return _toNumber(this.h, i);
    }

    public boolean j(int i) {
        return _toBoolean(this.h, i) != 0;
    }

    public String k(int i) {
        return _toString(this.h, i);
    }

    public void b() {
        _pushNil(this.h);
    }

    public void a(double d2) {
        _pushNumber(this.h, d2);
    }

    public void a(long j) {
        _pushInteger(this.h, j);
    }

    public void a(String str) {
        if (str == null) {
            _pushNil(this.h);
        } else {
            _pushString(this.h, str);
        }
    }

    public void a(boolean z) {
        _pushBoolean(this.h, z ? 1 : 0);
    }

    public int a(int i, long j) {
        return _rawGetI(this.h, i, j);
    }

    public int a(int i, int i2, int i3) {
        return _pcall(this.h, i, i2, i3);
    }

    public void l(int i) {
        _pop(this.h, i);
    }

    public synchronized int b(String str) {
        try {
        }
        return _getGlobal(this.h, str);
    }

    public synchronized void c(String str) {
        _setGlobal(this.h, str);
    }

    public Object m(int i) {
        return _getObjectFromUserdata(this.h, i);
    }

    public boolean n(int i) {
        return _isObject(this.h, i);
    }

    public void a(Object obj) {
        _pushJavaObject(this.h, obj);
    }

    public void a(b bVar) {
        _pushJavaFunction(this.h, bVar);
    }

    public boolean o(int i) {
        return _isJavaFunction(this.h, i);
    }

    public void b(Object obj) {
        if (obj == null) {
            b();
        } else if (obj instanceof Boolean) {
            a(((Boolean) obj).booleanValue());
        } else if (obj instanceof Long) {
            a(((Long) obj).longValue());
        } else if (obj instanceof Integer) {
            a((long) ((Integer) obj).intValue());
        } else if (obj instanceof Short) {
            a((long) ((Short) obj).shortValue());
        } else if (obj instanceof Character) {
            a((long) ((Character) obj).charValue());
        } else if (obj instanceof Byte) {
            a((long) ((Byte) obj).byteValue());
        } else if (obj instanceof Float) {
            a((double) ((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            a(((Double) obj).doubleValue());
        } else if (obj instanceof String) {
            a((String) obj);
        } else if (obj instanceof b) {
            a((b) obj);
        } else if (obj instanceof d) {
            d dVar = (d) obj;
            if (dVar.a() == this) {
                dVar.b();
            } else {
                a((Object) dVar);
            }
        } else {
            a(obj);
        }
    }
}
