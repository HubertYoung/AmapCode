package com.alipay.android.phone.wallet.minizxing;

final class k {
    static int a(d matrix) {
        return a(matrix, true) + a(matrix, false);
    }

    static int b(d matrix) {
        int penalty = 0;
        byte[][] array = matrix.c();
        int width = matrix.b();
        int height = matrix.a();
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width - 1; x++) {
                byte value = array[y][x];
                if (value == array[y][x + 1] && value == array[y + 1][x] && value == array[y + 1][x + 1]) {
                    penalty++;
                }
            }
        }
        return penalty * 3;
    }

    static int c(d matrix) {
        int numPenalties = 0;
        byte[][] array = matrix.c();
        int width = matrix.b();
        int height = matrix.a();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                byte[] arrayY = array[y];
                if (x + 6 < width && arrayY[x] == 1 && arrayY[x + 1] == 0 && arrayY[x + 2] == 1 && arrayY[x + 3] == 1 && arrayY[x + 4] == 1 && arrayY[x + 5] == 0 && arrayY[x + 6] == 1 && (a(arrayY, x - 4, x) || a(arrayY, x + 7, x + 11))) {
                    numPenalties++;
                }
                if (y + 6 < height && array[y][x] == 1 && array[y + 1][x] == 0 && array[y + 2][x] == 1 && array[y + 3][x] == 1 && array[y + 4][x] == 1 && array[y + 5][x] == 0 && array[y + 6][x] == 1 && (a(array, x, y - 4, y) || a(array, x, y + 7, y + 11))) {
                    numPenalties++;
                }
            }
        }
        return numPenalties * 40;
    }

    private static boolean a(byte[] rowArray, int from, int to) {
        for (int i = from; i < to; i++) {
            if (i >= 0 && i < rowArray.length && rowArray[i] == 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(byte[][] array, int col, int from, int to) {
        for (int i = from; i < to; i++) {
            if (i >= 0 && i < array.length && array[i][col] == 1) {
                return false;
            }
        }
        return true;
    }

    static int d(d matrix) {
        int numDarkCells = 0;
        byte[][] array = matrix.c();
        int width = matrix.b();
        int height = matrix.a();
        for (int y = 0; y < height; y++) {
            byte[] arrayY = array[y];
            for (int x = 0; x < width; x++) {
                if (arrayY[x] == 1) {
                    numDarkCells++;
                }
            }
        }
        int numTotalCells = matrix.a() * matrix.b();
        return ((Math.abs((numDarkCells * 2) - numTotalCells) * 10) / numTotalCells) * 10;
    }

    static boolean a(int maskPattern, int x, int y) {
        int intermediate;
        switch (maskPattern) {
            case 0:
                intermediate = (y + x) & 1;
                break;
            case 1:
                intermediate = y & 1;
                break;
            case 2:
                intermediate = x % 3;
                break;
            case 3:
                intermediate = (y + x) % 3;
                break;
            case 4:
                intermediate = ((y / 2) + (x / 3)) & 1;
                break;
            case 5:
                int temp = y * x;
                intermediate = (temp & 1) + (temp % 3);
                break;
            case 6:
                int temp2 = y * x;
                intermediate = ((temp2 & 1) + (temp2 % 3)) & 1;
                break;
            case 7:
                intermediate = (((y * x) % 3) + ((y + x) & 1)) & 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid mask pattern: " + maskPattern);
        }
        if (intermediate == 0) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v0, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v2, types: [byte] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int a(com.alipay.android.phone.wallet.minizxing.d r11, boolean r12) {
        /*
            r10 = 5
            r7 = 0
            if (r12 == 0) goto L_0x0027
            int r3 = r11.a()
        L_0x0008:
            if (r12 == 0) goto L_0x002c
            int r5 = r11.b()
        L_0x000e:
            byte[][] r0 = r11.c()
            r2 = 0
        L_0x0013:
            if (r2 >= r3) goto L_0x004a
            r6 = 0
            r8 = -1
            r4 = 0
        L_0x0018:
            if (r4 >= r5) goto L_0x0040
            if (r12 == 0) goto L_0x0031
            r9 = r0[r2]
            byte r1 = r9[r4]
        L_0x0020:
            if (r1 != r8) goto L_0x0036
            int r6 = r6 + 1
        L_0x0024:
            int r4 = r4 + 1
            goto L_0x0018
        L_0x0027:
            int r3 = r11.b()
            goto L_0x0008
        L_0x002c:
            int r5 = r11.a()
            goto L_0x000e
        L_0x0031:
            r9 = r0[r4]
            byte r1 = r9[r2]
            goto L_0x0020
        L_0x0036:
            if (r6 < r10) goto L_0x003d
            int r9 = r6 + -5
            int r9 = r9 + 3
            int r7 = r7 + r9
        L_0x003d:
            r6 = 1
            r8 = r1
            goto L_0x0024
        L_0x0040:
            if (r6 < r10) goto L_0x0047
            int r9 = r6 + -5
            int r9 = r9 + 3
            int r7 = r7 + r9
        L_0x0047:
            int r2 = r2 + 1
            goto L_0x0013
        L_0x004a:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.wallet.minizxing.k.a(com.alipay.android.phone.wallet.minizxing.d, boolean):int");
    }
}
