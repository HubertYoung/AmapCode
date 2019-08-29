package com.alipay.android.phone.wallet.minizxing;

final class j {
    private final i a;
    private final int[] b;

    j(i field, int[] coefficients) {
        if (coefficients.length == 0) {
            throw new IllegalArgumentException();
        }
        this.a = field;
        int coefficientsLength = coefficients.length;
        if (coefficientsLength <= 1 || coefficients[0] != 0) {
            this.b = coefficients;
            return;
        }
        int firstNonZero = 1;
        while (firstNonZero < coefficientsLength && coefficients[firstNonZero] == 0) {
            firstNonZero++;
        }
        if (firstNonZero == coefficientsLength) {
            this.b = new int[]{0};
            return;
        }
        this.b = new int[(coefficientsLength - firstNonZero)];
        System.arraycopy(coefficients, firstNonZero, this.b, 0, this.b.length);
    }

    /* access modifiers changed from: 0000 */
    public final int[] a() {
        return this.b;
    }

    private int b() {
        return this.b.length - 1;
    }

    private boolean c() {
        return this.b[0] == 0;
    }

    private int a(int degree) {
        return this.b[(this.b.length - 1) - degree];
    }

    private j c(j other) {
        if (!this.a.equals(other.a)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        } else if (c()) {
            return other;
        } else {
            if (other.c()) {
                return this;
            }
            int[] smallerCoefficients = this.b;
            int[] largerCoefficients = other.b;
            if (smallerCoefficients.length > largerCoefficients.length) {
                int[] temp = smallerCoefficients;
                smallerCoefficients = largerCoefficients;
                largerCoefficients = temp;
            }
            int[] sumDiff = new int[largerCoefficients.length];
            int lengthDiff = largerCoefficients.length - smallerCoefficients.length;
            System.arraycopy(largerCoefficients, 0, sumDiff, 0, lengthDiff);
            for (int i = lengthDiff; i < largerCoefficients.length; i++) {
                sumDiff[i] = i.b(smallerCoefficients[i - lengthDiff], largerCoefficients[i]);
            }
            return new j(this.a, sumDiff);
        }
    }

    /* access modifiers changed from: 0000 */
    public final j a(j other) {
        if (!this.a.equals(other.a)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        } else if (c() || other.c()) {
            return this.a.a();
        } else {
            int[] aCoefficients = this.b;
            int aLength = aCoefficients.length;
            int[] bCoefficients = other.b;
            int bLength = bCoefficients.length;
            int[] product = new int[((aLength + bLength) - 1)];
            for (int i = 0; i < aLength; i++) {
                int aCoeff = aCoefficients[i];
                for (int j = 0; j < bLength; j++) {
                    product[i + j] = i.b(product[i + j], this.a.c(aCoeff, bCoefficients[j]));
                }
            }
            return new j(this.a, product);
        }
    }

    /* access modifiers changed from: 0000 */
    public final j a(int degree, int coefficient) {
        if (degree < 0) {
            throw new IllegalArgumentException();
        } else if (coefficient == 0) {
            return this.a.a();
        } else {
            int size = this.b.length;
            int[] product = new int[(size + degree)];
            for (int i = 0; i < size; i++) {
                product[i] = this.a.c(this.b[i], coefficient);
            }
            return new j(this.a, product);
        }
    }

    /* access modifiers changed from: 0000 */
    public final j[] b(j other) {
        if (!this.a.equals(other.a)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        } else if (other.c()) {
            throw new IllegalArgumentException("Divide by 0");
        } else {
            j quotient = this.a.a();
            j remainder = this;
            int inverseDenominatorLeadingTerm = this.a.c(other.a(other.b()));
            while (remainder.b() >= other.b() && !remainder.c()) {
                int degreeDifference = remainder.b() - other.b();
                int scale = this.a.c(remainder.a(remainder.b()), inverseDenominatorLeadingTerm);
                j term = other.a(degreeDifference, scale);
                quotient = quotient.c(this.a.a(degreeDifference, scale));
                remainder = remainder.c(term);
            }
            return new j[]{quotient, remainder};
        }
    }

    public final String toString() {
        StringBuilder result = new StringBuilder(b() * 8);
        for (int degree = b(); degree >= 0; degree--) {
            int coefficient = a(degree);
            if (coefficient != 0) {
                if (coefficient < 0) {
                    result.append(" - ");
                    coefficient = -coefficient;
                } else if (result.length() > 0) {
                    result.append(" + ");
                }
                if (degree == 0 || coefficient != 1) {
                    int alphaPower = this.a.b(coefficient);
                    if (alphaPower == 0) {
                        result.append('1');
                    } else if (alphaPower == 1) {
                        result.append('a');
                    } else {
                        result.append("a^");
                        result.append(alphaPower);
                    }
                }
                if (degree != 0) {
                    if (degree == 1) {
                        result.append('x');
                    } else {
                        result.append("x^");
                        result.append(degree);
                    }
                }
            }
        }
        return result.toString();
    }
}
