package com.google.gson;

import com.google.gson.b.a;
import com.google.gson.b.g;
import java.math.BigInteger;

/* compiled from: JsonPrimitive */
public final class o extends j {
    private static final Class<?>[] a = {Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object b;

    public o(Boolean bool) {
        a((Object) bool);
    }

    public o(Number number) {
        a((Object) number);
    }

    public o(String str) {
        a((Object) str);
    }

    /* access modifiers changed from: 0000 */
    public final void a(Object obj) {
        if (obj instanceof Character) {
            this.b = String.valueOf(((Character) obj).charValue());
            return;
        }
        a.a((obj instanceof Number) || b(obj));
        this.b = obj;
    }

    public final boolean o() {
        return this.b instanceof Boolean;
    }

    /* access modifiers changed from: 0000 */
    public final Boolean n() {
        return (Boolean) this.b;
    }

    public final boolean f() {
        if (o()) {
            return n().booleanValue();
        }
        return Boolean.parseBoolean(b());
    }

    public final boolean p() {
        return this.b instanceof Number;
    }

    public final Number a() {
        return this.b instanceof String ? new g((String) this.b) : (Number) this.b;
    }

    public final boolean q() {
        return this.b instanceof String;
    }

    public final String b() {
        if (p()) {
            return a().toString();
        }
        if (o()) {
            return n().toString();
        }
        return (String) this.b;
    }

    public final double c() {
        return p() ? a().doubleValue() : Double.parseDouble(b());
    }

    public final long d() {
        return p() ? a().longValue() : Long.parseLong(b());
    }

    public final int e() {
        return p() ? a().intValue() : Integer.parseInt(b());
    }

    private static boolean b(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        Class<?> cls = obj.getClass();
        for (Class<?> isAssignableFrom : a) {
            if (isAssignableFrom.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        if (this.b == null) {
            return 31;
        }
        if (a(this)) {
            long longValue = a().longValue();
            return (int) ((longValue >>> 32) ^ longValue);
        } else if (!(this.b instanceof Number)) {
            return this.b.hashCode();
        } else {
            long doubleToLongBits = Double.doubleToLongBits(a().doubleValue());
            return (int) ((doubleToLongBits >>> 32) ^ doubleToLongBits);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        o oVar = (o) obj;
        if (this.b == null) {
            return oVar.b == null;
        }
        if (a(this) && a(oVar)) {
            return a().longValue() == oVar.a().longValue();
        }
        if (!(this.b instanceof Number) || !(oVar.b instanceof Number)) {
            return this.b.equals(oVar.b);
        }
        double doubleValue = a().doubleValue();
        double doubleValue2 = oVar.a().doubleValue();
        return doubleValue == doubleValue2 || (Double.isNaN(doubleValue) && Double.isNaN(doubleValue2));
    }

    private static boolean a(o oVar) {
        if (!(oVar.b instanceof Number)) {
            return false;
        }
        Number number = (Number) oVar.b;
        if ((number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte)) {
            return true;
        }
        return false;
    }
}
