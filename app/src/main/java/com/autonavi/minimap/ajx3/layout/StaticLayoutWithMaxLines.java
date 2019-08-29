package com.autonavi.minimap.ajx3.layout;

import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import java.lang.reflect.Constructor;

public class StaticLayoutWithMaxLines {
    private static final String LOGTAG = "StaticLayoutWithMaxLines";
    private static final String TEXT_DIRS_CLASS = "android.text.TextDirectionHeuristics";
    private static final String TEXT_DIR_CLASS = "android.text.TextDirectionHeuristic";
    private static final String TEXT_DIR_FIRSTSTRONG_LTR = "FIRSTSTRONG_LTR";
    private static Constructor<StaticLayout> sConstructor;
    private static Object[] sConstructorArgs;
    private static boolean sInitialized;
    private static Object sTextDirection;

    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0098 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x009c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x00a0 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void ensureInitialized() {
        /*
            java.lang.Class<com.autonavi.minimap.ajx3.layout.StaticLayoutWithMaxLines> r0 = com.autonavi.minimap.ajx3.layout.StaticLayoutWithMaxLines.class
            monitor-enter(r0)
            boolean r1 = sInitialized     // Catch:{ all -> 0x00a4 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)
            return
        L_0x0009:
            r1 = 1
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r3 = 18
            if (r2 < r3) goto L_0x0017
            java.lang.Class<android.text.TextDirectionHeuristic> r2 = android.text.TextDirectionHeuristic.class
            android.text.TextDirectionHeuristic r3 = android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            sTextDirection = r3     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            goto L_0x0036
        L_0x0017:
            java.lang.Class<com.autonavi.minimap.ajx3.layout.StaticLayoutWithMaxLines> r2 = com.autonavi.minimap.ajx3.layout.StaticLayoutWithMaxLines.class
            java.lang.ClassLoader r2 = r2.getClassLoader()     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            java.lang.String r3 = "android.text.TextDirectionHeuristic"
            java.lang.Class r3 = r2.loadClass(r3)     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            java.lang.String r4 = "android.text.TextDirectionHeuristics"
            java.lang.Class r2 = r2.loadClass(r4)     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            java.lang.String r4 = "FIRSTSTRONG_LTR"
            java.lang.reflect.Field r4 = r2.getField(r4)     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            java.lang.Object r2 = r4.get(r2)     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            sTextDirection = r2     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r2 = r3
        L_0x0036:
            r3 = 13
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r5 = 0
            java.lang.Class<java.lang.CharSequence> r6 = java.lang.CharSequence.class
            r4[r5] = r6     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r4[r1] = r5     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r5 = 2
            java.lang.Class r6 = java.lang.Integer.TYPE     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r4[r5] = r6     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r5 = 3
            java.lang.Class<android.text.TextPaint> r6 = android.text.TextPaint.class
            r4[r5] = r6     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r5 = 4
            java.lang.Class r6 = java.lang.Integer.TYPE     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r4[r5] = r6     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r5 = 5
            java.lang.Class<android.text.Layout$Alignment> r6 = android.text.Layout.Alignment.class
            r4[r5] = r6     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r5 = 6
            r4[r5] = r2     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r2 = 7
            java.lang.Class r5 = java.lang.Float.TYPE     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r4[r2] = r5     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r2 = 8
            java.lang.Class r5 = java.lang.Float.TYPE     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r4[r2] = r5     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r2 = 9
            java.lang.Class r5 = java.lang.Boolean.TYPE     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r4[r2] = r5     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r2 = 10
            java.lang.Class<android.text.TextUtils$TruncateAt> r5 = android.text.TextUtils.TruncateAt.class
            r4[r2] = r5     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r2 = 11
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r4[r2] = r5     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r2 = 12
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r4[r2] = r5     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            java.lang.Class<android.text.StaticLayout> r2 = android.text.StaticLayout.class
            java.lang.reflect.Constructor r2 = r2.getDeclaredConstructor(r4)     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            sConstructor = r2     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            r2.setAccessible(r1)     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            sConstructorArgs = r2     // Catch:{ NoSuchMethodException -> 0x00a0, ClassNotFoundException -> 0x009c, NoSuchFieldException -> 0x0098, IllegalAccessException -> 0x0094, all -> 0x0090 }
            sInitialized = r1     // Catch:{ all -> 0x00a4 }
            monitor-exit(r0)
            return
        L_0x0090:
            r2 = move-exception
            sInitialized = r1     // Catch:{ all -> 0x00a4 }
            throw r2     // Catch:{ all -> 0x00a4 }
        L_0x0094:
            sInitialized = r1     // Catch:{ all -> 0x00a4 }
            monitor-exit(r0)
            return
        L_0x0098:
            sInitialized = r1     // Catch:{ all -> 0x00a4 }
            monitor-exit(r0)
            return
        L_0x009c:
            sInitialized = r1     // Catch:{ all -> 0x00a4 }
            monitor-exit(r0)
            return
        L_0x00a0:
            sInitialized = r1     // Catch:{ all -> 0x00a4 }
            monitor-exit(r0)
            return
        L_0x00a4:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.layout.StaticLayoutWithMaxLines.ensureInitialized():void");
    }

    static boolean isSupported() {
        if (VERSION.SDK_INT < 14) {
            return false;
        }
        ensureInitialized();
        if (sConstructor != null) {
            return true;
        }
        return false;
    }

    public static synchronized StaticLayout create(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, Alignment alignment, float f, float f2, boolean z, TruncateAt truncateAt, int i4, int i5) {
        StaticLayout create;
        synchronized (StaticLayoutWithMaxLines.class) {
            try {
                create = AjxStaticLayout.create(charSequence, i, i2, textPaint, i3, alignment, (TextDirectionHeuristic) sTextDirection, f, f2, z, truncateAt, i4, i5);
            } catch (Exception e) {
                throw new IllegalStateException("Error creating StaticLayout with max lines: ".concat(String.valueOf(e)));
            } catch (Throwable th) {
                throw th;
            }
        }
        return create;
    }
}
