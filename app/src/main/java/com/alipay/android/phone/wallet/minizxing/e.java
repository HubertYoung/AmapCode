package com.alipay.android.phone.wallet.minizxing;

import com.google.zxing.common.StringUtils;
import java.util.HashMap;
import java.util.Map;

public enum e {
    Cp437((String) new int[]{0, 2}, (int) new String[0]),
    ISO8859_1((String) new int[]{1, 3}, (int) new String[]{"ISO-8859-1"}),
    ISO8859_2((String) 4, (int) new String[]{"ISO-8859-2"}),
    ISO8859_3((String) 5, (int) new String[]{"ISO-8859-3"}),
    ISO8859_4((String) 6, (int) new String[]{"ISO-8859-4"}),
    ISO8859_5((String) 7, (int) new String[]{"ISO-8859-5"}),
    ISO8859_6((String) 8, (int) new String[]{"ISO-8859-6"}),
    ISO8859_7((String) 9, (int) new String[]{"ISO-8859-7"}),
    ISO8859_8((String) 10, (int) new String[]{"ISO-8859-8"}),
    ISO8859_9((String) 11, (int) new String[]{"ISO-8859-9"}),
    ISO8859_10((String) 12, (int) new String[]{"ISO-8859-10"}),
    ISO8859_11((String) 13, (int) new String[]{"ISO-8859-11"}),
    ISO8859_13((String) 15, (int) new String[]{"ISO-8859-13"}),
    ISO8859_14((String) 16, (int) new String[]{"ISO-8859-14"}),
    ISO8859_15((String) 17, (int) new String[]{"ISO-8859-15"}),
    ISO8859_16((String) 18, (int) new String[]{"ISO-8859-16"}),
    SJIS((String) 20, (int) new String[]{"Shift_JIS"}),
    Cp1250((String) 21, (int) new String[]{"windows-1250"}),
    Cp1251((String) 22, (int) new String[]{"windows-1251"}),
    Cp1252((String) 23, (int) new String[]{"windows-1252"}),
    Cp1256((String) 24, (int) new String[]{"windows-1256"}),
    UnicodeBigUnmarked((String) 25, (int) new String[]{"UTF-16BE", "UnicodeBig"}),
    UTF8((String) 26, (int) new String[]{"UTF-8"}),
    ASCII((String) new int[]{27, 170}, (int) new String[]{"US-ASCII"}),
    GB18030((String) 29, (int) new String[]{StringUtils.GB2312, "EUC_CN", "GBK"}),
    EUC_KR((String) 30, (int) new String[]{"EUC-KR"});
    
    private static final Map<Integer, e> B = null;
    private static final Map<String, e> C = null;
    private final int[] D;
    private final String[] E;

    static {
        e[] values;
        B = new HashMap();
        C = new HashMap();
        for (e eci : values()) {
            for (int value : eci.D) {
                B.put(Integer.valueOf(value), eci);
            }
            C.put(eci.name(), eci);
            for (String name : eci.E) {
                C.put(name, eci);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [int, java.lang.String] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private e(int r5) {
        /*
            r4 = this;
            r3 = 0
            r0 = 24
            r1 = 1
            int[] r1 = new int[r1]
            r2 = 28
            r1[r3] = r2
            java.lang.String[] r2 = new java.lang.String[r3]
            r4.<init>(r5, r0, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.wallet.minizxing.e.<init>(java.lang.String):void");
    }

    private e(int value, String... otherEncodingNames) {
        this.D = new int[]{value};
        this.E = otherEncodingNames;
    }

    private e(int[] values, String... otherEncodingNames) {
        this.D = values;
        this.E = otherEncodingNames;
    }

    public final int a() {
        return this.D[0];
    }

    public static e a(String name) {
        return C.get(name);
    }
}
