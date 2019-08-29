package com.alp.o.mzx.a;

public final class e {
    static int[] a = {0, 4, 7};
    static int[][] b = {new int[]{10, 9, 8, 8}, new int[]{12, 11, 16, 10}, new int[]{14, 13, 16, 12}};

    public static a a(int length, b encodeMode, int versionSlot) {
        switch (encodeMode.a()) {
            case 0:
                return new a(b[versionSlot][0] + 4 + ((length / 3) * 10) + a[length % 3], length % 3);
            case 1:
                return new a(b[versionSlot][1] + 4 + ((length / 2) * 11) + ((length % 2) * 6), length % 2);
            case 2:
                return new a(b[versionSlot][2] + 4 + (length * 8), 0);
            case 3:
                return new a(b[versionSlot][3] + 4 + (length * 13), 0);
            default:
                return null;
        }
    }

    public static a a(int bitCost, int remains, b encodeMode, int length) {
        int length2 = length + remains;
        switch (encodeMode.a()) {
            case 0:
                return new a((bitCost - a[remains % 3]) + ((length2 / 3) * 10) + a[length2 % 3], length2 % 3);
            case 1:
                return new a((bitCost - ((remains % 2) * 6)) + ((length2 / 2) * 11) + ((length2 % 2) * 6), length2 % 2);
            case 2:
                return new a((length2 * 8) + bitCost, 0);
            case 3:
                return new a((length2 * 13) + bitCost, 0);
            default:
                return null;
        }
    }

    public static int a(int version) {
        if (version <= 9) {
            return 0;
        }
        if (version <= 26) {
            return 1;
        }
        return 2;
    }
}
