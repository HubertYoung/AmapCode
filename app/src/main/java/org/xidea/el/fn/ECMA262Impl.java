package org.xidea.el.fn;

import java.util.List;
import org.xidea.el.impl.ExpressionFactoryImpl;

public abstract class ECMA262Impl {
    private static final Class<?>[] a = {List.class, Object[].class, int[].class, float[].class, double[].class, long[].class, short[].class, byte[].class, char[].class};

    public static void a(ExpressionFactoryImpl expressionFactoryImpl) {
        a(expressionFactoryImpl, JSArray.class, a);
        a(expressionFactoryImpl, JSNumber.class, Number.class);
        a(expressionFactoryImpl, JSString.class, String.class);
        JSGlobal.a(expressionFactoryImpl);
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x002d */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0031 A[Catch:{ Exception -> 0x0041 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(org.xidea.el.impl.ExpressionFactoryImpl r10, java.lang.Class<? extends org.xidea.el.fn.JSObject> r11, java.lang.Class<?>... r12) {
        /*
            java.lang.reflect.Method[] r0 = r11.getMethods()     // Catch:{ Exception -> 0x0041 }
            int r1 = r0.length     // Catch:{ Exception -> 0x0041 }
            r2 = 0
            r3 = 0
        L_0x0007:
            if (r3 >= r1) goto L_0x0040
            r4 = r0[r3]     // Catch:{ Exception -> 0x0041 }
            java.lang.Class r5 = r4.getDeclaringClass()     // Catch:{ Exception -> 0x0041 }
            if (r5 != r11) goto L_0x003d
            java.lang.Object r5 = r11.newInstance()     // Catch:{ Exception -> 0x0041 }
            org.xidea.el.fn.JSObject r5 = (org.xidea.el.fn.JSObject) r5     // Catch:{ Exception -> 0x0041 }
            java.lang.Class[] r6 = r4.getParameterTypes()     // Catch:{ Exception -> 0x0041 }
            r5.a = r4     // Catch:{ Exception -> 0x0041 }
            int r7 = r6.length     // Catch:{ Exception -> 0x0041 }
            r8 = 2
            r9 = 1
            if (r7 != r8) goto L_0x0028
            r7 = r6[r9]     // Catch:{ Exception -> 0x0041 }
            java.lang.Class<java.lang.Object[]> r8 = java.lang.Object[].class
            if (r7 == r8) goto L_0x002a
        L_0x0028:
            r5.b = r6     // Catch:{ Exception -> 0x0041 }
        L_0x002a:
            r4.setAccessible(r9)     // Catch:{ Exception -> 0x002d }
        L_0x002d:
            int r6 = r12.length     // Catch:{ Exception -> 0x0041 }
            r7 = 0
        L_0x002f:
            if (r7 >= r6) goto L_0x003d
            r8 = r12[r7]     // Catch:{ Exception -> 0x0041 }
            java.lang.String r9 = r4.getName()     // Catch:{ Exception -> 0x0041 }
            r10.a(r8, r9, r5)     // Catch:{ Exception -> 0x0041 }
            int r7 = r7 + 1
            goto L_0x002f
        L_0x003d:
            int r3 = r3 + 1
            goto L_0x0007
        L_0x0040:
            return
        L_0x0041:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xidea.el.fn.ECMA262Impl.a(org.xidea.el.impl.ExpressionFactoryImpl, java.lang.Class, java.lang.Class[]):void");
    }

    private static Number a(String str, int i) {
        try {
            return Integer.valueOf(Integer.parseInt(str, i));
        } catch (Exception unused) {
            return Long.valueOf(Long.parseLong(str, i));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0066 A[Catch:{ NumberFormatException -> 0x009d }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x007f A[Catch:{ NumberFormatException -> 0x009d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Number a(java.lang.Object r6) {
        /*
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            java.lang.Object r6 = a(r6, r0)
            r0 = 0
            if (r6 != 0) goto L_0x000e
            java.lang.Integer r6 = java.lang.Integer.valueOf(r0)
            return r6
        L_0x000e:
            boolean r1 = r6 instanceof java.lang.Boolean
            if (r1 == 0) goto L_0x001d
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            return r6
        L_0x001d:
            boolean r1 = r6 instanceof java.lang.Number
            if (r1 == 0) goto L_0x0024
            java.lang.Number r6 = (java.lang.Number) r6
            return r6
        L_0x0024:
            java.lang.String r6 = (java.lang.String) r6
            r1 = 46
            int r1 = r6.indexOf(r1)     // Catch:{ NumberFormatException -> 0x009d }
            if (r1 < 0) goto L_0x0037
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ NumberFormatException -> 0x009d }
            java.lang.Float r6 = java.lang.Float.valueOf(r6)     // Catch:{ NumberFormatException -> 0x009d }
            return r6
        L_0x0037:
            int r1 = r6.length()     // Catch:{ NumberFormatException -> 0x009d }
            r2 = 10
            r3 = 1
            if (r1 <= r3) goto L_0x0098
            char r0 = r6.charAt(r0)     // Catch:{ NumberFormatException -> 0x009d }
            char r1 = r6.charAt(r3)     // Catch:{ NumberFormatException -> 0x009d }
            r3 = 43
            r4 = 2
            if (r0 == r3) goto L_0x0056
            r3 = 45
            if (r0 != r3) goto L_0x0052
            goto L_0x0056
        L_0x0052:
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x0062
        L_0x0056:
            int r0 = r6.length()     // Catch:{ NumberFormatException -> 0x009d }
            if (r0 <= r4) goto L_0x0061
            char r0 = r6.charAt(r4)     // Catch:{ NumberFormatException -> 0x009d }
            goto L_0x0062
        L_0x0061:
            r0 = r1
        L_0x0062:
            r3 = 48
            if (r1 != r3) goto L_0x007f
            r1 = 120(0x78, float:1.68E-43)
            if (r0 == r1) goto L_0x0074
            r1 = 88
            if (r0 != r1) goto L_0x006f
            goto L_0x0074
        L_0x006f:
            java.lang.Number r6 = a(r6, r2)     // Catch:{ NumberFormatException -> 0x009d }
            return r6
        L_0x0074:
            java.lang.String r6 = r6.substring(r4)     // Catch:{ NumberFormatException -> 0x009d }
            r0 = 16
            java.lang.Number r6 = a(r6, r0)     // Catch:{ NumberFormatException -> 0x009d }
            return r6
        L_0x007f:
            r0 = 69
            int r0 = r6.indexOf(r0)     // Catch:{ NumberFormatException -> 0x009d }
            if (r0 > 0) goto L_0x008f
            r0 = 101(0x65, float:1.42E-43)
            int r0 = r6.indexOf(r0)     // Catch:{ NumberFormatException -> 0x009d }
            if (r0 <= 0) goto L_0x0098
        L_0x008f:
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ NumberFormatException -> 0x009d }
            java.lang.Float r6 = java.lang.Float.valueOf(r6)     // Catch:{ NumberFormatException -> 0x009d }
            return r6
        L_0x0098:
            java.lang.Number r6 = a(r6, r2)     // Catch:{ NumberFormatException -> 0x009d }
            return r6
        L_0x009d:
            r0 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            java.lang.Double r6 = java.lang.Double.valueOf(r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xidea.el.fn.ECMA262Impl.a(java.lang.Object):java.lang.Number");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0011, code lost:
        if ((r3 instanceof java.util.Date) == false) goto L_0x0013;
     */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0017  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0015  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object a(java.lang.Object r3, java.lang.Class<?> r4) {
        /*
            java.lang.Class<java.lang.Number> r0 = java.lang.Number.class
            r1 = 1
            r2 = 0
            if (r4 != r0) goto L_0x0008
        L_0x0006:
            r1 = 0
            goto L_0x0013
        L_0x0008:
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            if (r4 != r0) goto L_0x000d
            goto L_0x0013
        L_0x000d:
            if (r4 != 0) goto L_0x0042
            boolean r4 = r3 instanceof java.util.Date
            if (r4 != 0) goto L_0x0006
        L_0x0013:
            if (r3 != 0) goto L_0x0017
            r3 = 0
            return r3
        L_0x0017:
            boolean r4 = r3 instanceof java.lang.Boolean
            if (r4 == 0) goto L_0x001c
            return r3
        L_0x001c:
            boolean r4 = r3 instanceof java.lang.Number
            if (r4 == 0) goto L_0x0021
            return r3
        L_0x0021:
            boolean r4 = r3 instanceof java.lang.String
            if (r4 == 0) goto L_0x0026
            return r3
        L_0x0026:
            if (r1 == 0) goto L_0x002d
            java.lang.String r3 = java.lang.String.valueOf(r3)
            return r3
        L_0x002d:
            boolean r4 = r3 instanceof java.util.Date
            if (r4 == 0) goto L_0x003d
            java.lang.Long r4 = new java.lang.Long
            java.util.Date r3 = (java.util.Date) r3
            long r0 = r3.getTime()
            r4.<init>(r0)
            return r4
        L_0x003d:
            java.lang.String r3 = java.lang.String.valueOf(r3)
            return r3
        L_0x0042:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "expectedType 只能是 Number或者String"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xidea.el.fn.ECMA262Impl.a(java.lang.Object, java.lang.Class):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0078, code lost:
        if (java.lang.Float.isNaN(r2) == false) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0085, code lost:
        if (((java.lang.String) r2).length() <= 0) goto L_0x0094;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object b(java.lang.Object r2, java.lang.Class<?> r3) {
        /*
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            if (r3 != r0) goto L_0x000d
            if (r2 != 0) goto L_0x0008
            r2 = 0
            return r2
        L_0x0008:
            java.lang.String r2 = r2.toString()
            return r2
        L_0x000d:
            java.lang.Class<java.lang.Character> r0 = java.lang.Character.class
            r1 = 0
            if (r3 != r0) goto L_0x0045
            if (r2 != 0) goto L_0x0019
            java.lang.Character r2 = java.lang.Character.valueOf(r1)
            return r2
        L_0x0019:
            java.lang.Class<java.lang.String> r3 = java.lang.String.class
            java.lang.Object r2 = a(r2, r3)
            boolean r3 = r2 instanceof java.lang.Number
            if (r3 == 0) goto L_0x002f
            java.lang.Number r2 = (java.lang.Number) r2
            int r2 = r2.intValue()
            char r2 = (char) r2
            java.lang.Character r2 = java.lang.Character.valueOf(r2)
            return r2
        L_0x002f:
            java.lang.String r2 = (java.lang.String) r2
            int r3 = r2.length()
            if (r3 <= 0) goto L_0x0040
            char r2 = r2.charAt(r1)
            java.lang.Character r2 = java.lang.Character.valueOf(r2)
            return r2
        L_0x0040:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r1)
            return r2
        L_0x0045:
            java.lang.Class r3 = org.xidea.el.impl.ReflectUtil.e(r3)
            java.lang.Class<java.lang.Number> r0 = java.lang.Number.class
            boolean r0 = r0.isAssignableFrom(r3)
            if (r0 == 0) goto L_0x005a
            java.lang.Number r2 = a(r2)
            java.lang.Number r2 = org.xidea.el.impl.ReflectUtil.a(r2, r3)
            return r2
        L_0x005a:
            java.lang.Class<java.lang.Boolean> r0 = java.lang.Boolean.class
            if (r3 != r0) goto L_0x0099
            java.lang.Object r2 = a(r2, r3)
            if (r2 != 0) goto L_0x0065
            goto L_0x0094
        L_0x0065:
            boolean r3 = r2 instanceof java.lang.Number
            if (r3 == 0) goto L_0x007b
            java.lang.Number r2 = (java.lang.Number) r2
            float r2 = r2.floatValue()
            r3 = 0
            int r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x0094
            boolean r2 = java.lang.Float.isNaN(r2)
            if (r2 == 0) goto L_0x0093
            goto L_0x0094
        L_0x007b:
            boolean r3 = r2 instanceof java.lang.String
            if (r3 == 0) goto L_0x0088
            java.lang.String r2 = (java.lang.String) r2
            int r2 = r2.length()
            if (r2 > 0) goto L_0x0093
            goto L_0x0094
        L_0x0088:
            boolean r3 = r2 instanceof java.lang.Boolean
            if (r3 == 0) goto L_0x0093
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r1 = r2.booleanValue()
            goto L_0x0094
        L_0x0093:
            r1 = 1
        L_0x0094:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r1)
            return r2
        L_0x0099:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xidea.el.fn.ECMA262Impl.b(java.lang.Object, java.lang.Class):java.lang.Object");
    }

    public static String b(Object obj) {
        Object a2 = a(obj, String.class);
        if (!(a2 instanceof Number)) {
            return String.valueOf(a2);
        }
        Number number = (Number) a2;
        if (!(number instanceof Double) && !(number instanceof Float)) {
            return Long.toString(number.longValue(), 10);
        }
        double doubleValue = number.doubleValue();
        if (Double.isNaN(doubleValue)) {
            return "NaN";
        }
        if (Double.isInfinite(doubleValue)) {
            return doubleValue > 0.0d ? "Infinity" : "-Infinity";
        }
        if (doubleValue == 0.0d) {
            return "0";
        }
        String d = Double.toString(doubleValue);
        if (d.endsWith(".0")) {
            d = d.substring(0, d.length() - 2);
        }
        return d;
    }
}
