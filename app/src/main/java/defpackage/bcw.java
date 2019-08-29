package defpackage;

/* renamed from: bcw reason: default package */
/* compiled from: DistanceCalculator */
public final class bcw {
    public static double a(double d, double d2, double d3, double d4) {
        if (d2 <= 0.0d || d <= 0.0d || d4 <= 0.0d || d3 <= 0.0d) {
            return 0.0d;
        }
        float[] fArr = new float[1];
        a(d2, d, d4, d3, fArr);
        return (double) fArr[0];
    }

    private static void a(double d, double d2, double d3, double d4, float[] fArr) {
        b(d, d2, d3, d4, fArr);
    }

    private static void b(double d, double d2, double d3, double d4, float[] fArr) {
        double d5;
        double d6;
        float[] fArr2 = fArr;
        double atan = Math.atan(Math.tan(d * 0.017453292519943295d) * 0.996647189328169d);
        double atan2 = Math.atan(Math.tan(d3 * 0.017453292519943295d) * 0.996647189328169d);
        double cos = Math.cos(atan);
        double cos2 = Math.cos(atan2);
        double sin = Math.sin(atan);
        double sin2 = Math.sin(atan2);
        double d7 = cos * cos2;
        double d8 = sin * sin2;
        double d9 = (0.017453292519943295d * d4) - (d2 * 0.017453292519943295d);
        double d10 = 0.0d;
        double d11 = 0.0d;
        double d12 = 0.0d;
        double d13 = 0.0d;
        double d14 = 0.0d;
        int i = 0;
        double d15 = d9;
        while (true) {
            if (i >= 20) {
                d5 = sin;
                d6 = sin2;
                break;
            }
            d10 = Math.cos(d15);
            d12 = Math.sin(d15);
            double d16 = cos2 * d12;
            double d17 = (cos * sin2) - ((sin * cos2) * d10);
            d5 = sin;
            double sqrt = Math.sqrt((d16 * d16) + (d17 * d17));
            d6 = sin2;
            double d18 = d8 + (d7 * d10);
            d13 = Math.atan2(sqrt, d18);
            double d19 = sqrt == 0.0d ? 0.0d : (d7 * d12) / sqrt;
            double d20 = 1.0d - (d19 * d19);
            double d21 = d20 == 0.0d ? 0.0d : d18 - ((d8 * 2.0d) / d20);
            double d22 = 0.006739496756586903d * d20;
            double d23 = ((d22 / 16384.0d) * (((((320.0d - (175.0d * d22)) * d22) - 0.005859375d) * d22) + 4096.0d)) + 1.0d;
            double d24 = (d22 / 1024.0d) * ((d22 * (((74.0d - (47.0d * d22)) * d22) - 0.03125d)) + 256.0d);
            double d25 = 2.0955066698943685E-4d * d20 * (((4.0d - (d20 * 3.0d)) * 0.0033528106718309896d) + 4.0d);
            double d26 = d21 * d21;
            d14 = d24 * sqrt * (d21 + ((d24 / 4.0d) * ((((d26 * 2.0d) - 4.0d) * d18) - ((((d24 / 6.0d) * d21) * (((sqrt * 4.0d) * sqrt) - 1.5d)) * ((d26 * 4.0d) - 1.5d)))));
            double d27 = d9 + ((1.0d - d25) * 0.0033528106718309896d * d19 * (d13 + (sqrt * d25 * (d21 + (d25 * d18 * (((2.0d * d21) * d21) - 4.0d))))));
            if (Math.abs((d27 - d15) / d27) < 1.0E-12d) {
                d11 = d23;
                break;
            }
            i++;
            d15 = d27;
            sin = d5;
            sin2 = d6;
            d11 = d23;
        }
        float[] fArr3 = fArr;
        fArr3[0] = (float) (d11 * 6356752.3142d * (d13 - d14));
        if (fArr3.length > 1) {
            double d28 = cos * d6;
            fArr3[1] = (float) (((double) ((float) Math.atan2(cos2 * d12, d28 - ((d5 * cos2) * d10)))) * 57.29577951308232d);
            if (fArr3.length > 2) {
                fArr3[2] = (float) (((double) ((float) Math.atan2(cos * d12, ((-d5) * cos2) + (d28 * d10)))) * 57.29577951308232d);
            }
        }
    }
}
