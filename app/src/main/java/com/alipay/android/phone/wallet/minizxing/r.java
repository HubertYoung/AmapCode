package com.alipay.android.phone.wallet.minizxing;

import java.util.ArrayList;
import java.util.List;

public final class r {
    private final i a;
    private final List<j> b = new ArrayList();

    public r(i field) {
        this.a = field;
        this.b.add(new j(field, new int[]{1}));
    }

    private j a(int degree) {
        if (degree >= this.b.size()) {
            j lastGenerator = this.b.get(this.b.size() - 1);
            for (int d = this.b.size(); d <= degree; d++) {
                j nextGenerator = lastGenerator.a(new j(this.a, new int[]{1, this.a.a((d - 1) + this.a.b())}));
                this.b.add(nextGenerator);
                lastGenerator = nextGenerator;
            }
        }
        return this.b.get(degree);
    }

    public final void a(int[] toEncode, int ecBytes) {
        if (ecBytes == 0) {
            throw new IllegalArgumentException("No error correction bytes");
        }
        int dataBytes = toEncode.length - ecBytes;
        if (dataBytes <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
        }
        j generator = a(ecBytes);
        int[] infoCoefficients = new int[dataBytes];
        System.arraycopy(toEncode, 0, infoCoefficients, 0, dataBytes);
        int[] coefficients = new j(this.a, infoCoefficients).a(ecBytes, 1).b(generator)[1].a();
        int numZeroCoefficients = ecBytes - coefficients.length;
        for (int i = 0; i < numZeroCoefficients; i++) {
            toEncode[dataBytes + i] = 0;
        }
        System.arraycopy(coefficients, 0, toEncode, dataBytes + numZeroCoefficients, coefficients.length);
    }
}
