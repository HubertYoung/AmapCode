package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;

public class StringUtils {
    public static final int WRONG_COLOR = -2;

    public static boolean isEqual(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return TextUtils.isEmpty(str2);
        }
        return str.equals(str2);
    }

    public static boolean parseBoolean(@Nullable String str) {
        return "true".equalsIgnoreCase(str) || "1".equals(str);
    }

    public static int parseInt(@Nullable String str) {
        return parseInt(str, 0);
    }

    public static int parseInt(@Nullable String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return i;
        }
        int[] iArr = new int[1];
        parseInt(str, 0, i, iArr);
        return iArr[0];
    }

    public static float parseFloat(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return 0.0f;
        }
        String trim = str.trim();
        try {
            return Float.parseFloat(trim);
        } catch (NumberFormatException unused) {
            int i = 0;
            int i2 = -1;
            while (true) {
                if (i >= trim.length()) {
                    i = 0;
                    break;
                }
                char charAt = trim.charAt(i);
                if (!Character.isDigit(charAt)) {
                    if (i2 >= 0 || !".".equals(String.valueOf(charAt))) {
                        break;
                    }
                    i2 = i;
                }
                i++;
            }
            if (i > 0) {
                try {
                    return Float.parseFloat(trim.substring(0, i));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return 0.0f;
                }
            }
            return 0.0f;
        }
    }

    public static int parseStandUnit2Px(Context context, String str) {
        return parseStandUnit2Px(context, str, 0);
    }

    public static int parseStandUnit2Px(Context context, String str, int i) {
        return DimensionUtils.standardUnitToPixel((float) parseInt(str, i));
    }

    public static int[] parse4StandUnits2Pxs(@NonNull Context context, float[] fArr) {
        int[] iArr = {0, 0, 0, 0};
        if (fArr == null || fArr.length != 4) {
            return iArr;
        }
        for (int i = 0; i < fArr.length; i++) {
            iArr[i] = DimensionUtils.standardUnitToPixel(fArr[i]);
        }
        return iArr;
    }

    public static int[] parse4Int(@NonNull String str, @NonNull char c, int i) {
        int[] iArr = new int[4];
        int[] iArr2 = new int[2];
        parseInt(str, 0, i, iArr2);
        int indexOf = str.indexOf(c, iArr2[1]) + 1;
        if (indexOf == -1 || indexOf >= str.length()) {
            for (int i2 = 0; i2 < 4; i2++) {
                iArr[i2] = iArr2[0];
            }
            return iArr;
        }
        iArr[0] = iArr2[0];
        int i3 = indexOf;
        for (int i4 = 1; i4 < 4; i4++) {
            parseInt(str, i3, i, iArr2);
            iArr[i4] = iArr2[0];
            i3 = str.indexOf(c, iArr2[1]) + 1;
            if (i3 == -1 || i3 >= str.length()) {
                break;
            }
        }
        return iArr;
    }

    @ColorInt
    public static int parseColor(String str) {
        return parseColor(str, -16777216);
    }

    public static int parseColor(String str, @ColorInt int i) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, "#00000000")) {
            return 0;
        }
        if (str.length() > 0 && str.charAt(0) != '#') {
            str = MetaRecord.LOG_SEPARATOR.concat(String.valueOf(str));
        }
        if (str.length() > 2 && str.charAt(1) == '0' && str.charAt(2) == 'x') {
            StringBuilder sb = new StringBuilder(MetaRecord.LOG_SEPARATOR);
            sb.append(str.substring(3));
            str = sb.toString();
        }
        if (str.length() == 9 && str.charAt(0) == '#') {
            StringBuilder sb2 = new StringBuilder(MetaRecord.LOG_SEPARATOR);
            sb2.append(str.substring(7));
            sb2.append(str.substring(1, 7));
            str = sb2.toString();
        }
        try {
            return Color.parseColor(str);
        } catch (Exception e) {
            e.printStackTrace();
            return i;
        }
    }

    public static int parseColorByAnimator(String str) {
        if (TextUtils.isEmpty(str)) {
            return -2;
        }
        if (str.length() == 4 && str.charAt(0) == '#') {
            StringBuilder sb = new StringBuilder();
            sb.append(MetaRecord.LOG_SEPARATOR);
            for (int i = 1; i < str.length(); i++) {
                sb.append(str.charAt(i));
                sb.append(str.charAt(i));
            }
            str = sb.toString();
        } else {
            if (str.length() > 0 && str.charAt(0) != '#') {
                str = MetaRecord.LOG_SEPARATOR.concat(String.valueOf(str));
            }
            if (str.length() > 2 && str.charAt(1) == '0' && str.charAt(2) == 'x') {
                StringBuilder sb2 = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                sb2.append(str.substring(3));
                str = sb2.toString();
            }
            if (str.length() == 9 && str.charAt(0) == '#') {
                StringBuilder sb3 = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                sb3.append(str.substring(7));
                sb3.append(str.substring(1, 7));
                str = sb3.toString();
            }
        }
        try {
            return Color.parseColor(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }
    }

    private static void parseInt(@NonNull String str, int i, int i2, int[] iArr) {
        parseInt(str, i, str.length(), i2, iArr);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0052, code lost:
        r2 = Integer.MIN_VALUE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void parseInt(@android.support.annotation.NonNull java.lang.String r6, int r7, int r8, int r9, int[] r10) {
        /*
            if (r10 == 0) goto L_0x0077
            int r0 = r10.length
            if (r0 > 0) goto L_0x0007
            goto L_0x0077
        L_0x0007:
            r0 = -1
            if (r7 >= r8) goto L_0x0016
            char r1 = r6.charAt(r7)
            r2 = 32
            if (r1 <= r2) goto L_0x0013
            goto L_0x0017
        L_0x0013:
            int r7 = r7 + 1
            goto L_0x0007
        L_0x0016:
            r7 = -1
        L_0x0017:
            if (r7 != r0) goto L_0x001d
            setResultsValue(r10, r9, r8)
            return
        L_0x001d:
            char r0 = r6.charAt(r7)
            r1 = 45
            if (r0 == r1) goto L_0x0029
            r2 = 43
            if (r0 != r2) goto L_0x002b
        L_0x0029:
            int r7 = r7 + 1
        L_0x002b:
            int r2 = r6.length()
            if (r7 == r2) goto L_0x0073
            char r2 = r6.charAt(r7)
            boolean r2 = java.lang.Character.isDigit(r2)
            if (r2 != 0) goto L_0x003c
            goto L_0x0073
        L_0x003c:
            r2 = 0
        L_0x003d:
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r7 >= r8) goto L_0x0060
            char r4 = r6.charAt(r7)
            r5 = 10
            int r4 = java.lang.Character.digit(r4, r5)
            if (r4 < 0) goto L_0x0060
            r5 = -214748364(0xfffffffff3333334, float:-1.4197688E31)
            if (r5 <= r2) goto L_0x0055
        L_0x0052:
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x0060
        L_0x0055:
            int r5 = r2 * 10
            int r4 = r5 - r4
            if (r4 <= r2) goto L_0x005c
            goto L_0x0052
        L_0x005c:
            int r7 = r7 + 1
            r2 = r4
            goto L_0x003d
        L_0x0060:
            if (r0 == r1) goto L_0x006e
            if (r2 != r3) goto L_0x0068
            r9 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x006f
        L_0x0068:
            int r6 = -r2
            if (r6 >= 0) goto L_0x006c
            goto L_0x006f
        L_0x006c:
            r9 = r6
            goto L_0x006f
        L_0x006e:
            r9 = r2
        L_0x006f:
            setResultsValue(r10, r9, r7)
            return
        L_0x0073:
            setResultsValue(r10, r9, r7)
            return
        L_0x0077:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = "results is null or has length < 1"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.StringUtils.parseInt(java.lang.String, int, int, int, int[]):void");
    }

    private static void setResultsValue(@NonNull int[] iArr, int i, int i2) {
        iArr[0] = i;
        if (iArr.length > 1) {
            iArr[1] = i2;
        }
    }

    public static String replaceBlank(String str) {
        return !TextUtils.isEmpty(str) ? str.replaceAll("[\\t\\n\\r\\s*]", "") : str;
    }
}
