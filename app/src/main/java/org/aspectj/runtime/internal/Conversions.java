package org.aspectj.runtime.internal;

public final class Conversions {
    private Conversions() {
    }

    public static Object intObject(int i) {
        return new Integer(i);
    }

    public static Object shortObject(short i) {
        return new Short(i);
    }

    public static Object byteObject(byte i) {
        return new Byte(i);
    }

    public static Object charObject(char i) {
        return new Character(i);
    }

    public static Object longObject(long i) {
        return new Long(i);
    }

    public static Object floatObject(float i) {
        return new Float(i);
    }

    public static Object doubleObject(double i) {
        return new Double(i);
    }

    public static Object booleanObject(boolean i) {
        return new Boolean(i);
    }

    public static Object voidObject() {
        return null;
    }

    public static int intValue(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Number) {
            return ((Number) o).intValue();
        }
        throw new ClassCastException(new StringBuffer().append(o.getClass().getName()).append(" can not be converted to int").toString());
    }

    public static long longValue(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        throw new ClassCastException(new StringBuffer().append(o.getClass().getName()).append(" can not be converted to long").toString());
    }

    public static float floatValue(Object o) {
        if (o == null) {
            return 0.0f;
        }
        if (o instanceof Number) {
            return ((Number) o).floatValue();
        }
        throw new ClassCastException(new StringBuffer().append(o.getClass().getName()).append(" can not be converted to float").toString());
    }

    public static double doubleValue(Object o) {
        if (o == null) {
            return 0.0d;
        }
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        throw new ClassCastException(new StringBuffer().append(o.getClass().getName()).append(" can not be converted to double").toString());
    }

    public static byte byteValue(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Number) {
            return ((Number) o).byteValue();
        }
        throw new ClassCastException(new StringBuffer().append(o.getClass().getName()).append(" can not be converted to byte").toString());
    }

    public static short shortValue(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Number) {
            return ((Number) o).shortValue();
        }
        throw new ClassCastException(new StringBuffer().append(o.getClass().getName()).append(" can not be converted to short").toString());
    }

    public static char charValue(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Character) {
            return ((Character) o).charValue();
        }
        throw new ClassCastException(new StringBuffer().append(o.getClass().getName()).append(" can not be converted to char").toString());
    }

    public static boolean booleanValue(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Boolean) {
            return ((Boolean) o).booleanValue();
        }
        throw new ClassCastException(new StringBuffer().append(o.getClass().getName()).append(" can not be converted to boolean").toString());
    }

    public static Object voidValue(Object o) {
        if (o == null) {
        }
        return o;
    }
}
