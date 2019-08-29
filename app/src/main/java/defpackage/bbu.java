package defpackage;

/* renamed from: bbu reason: default package */
/* compiled from: DistanceCalculator */
public final class bbu {
    public static double a(double d, double d2, double d3, double d4) {
        double d5;
        if (d2 <= 0.0d || d <= 0.0d || d4 <= 0.0d || d3 <= 0.0d) {
            return 0.0d;
        }
        double atan = Math.atan(Math.tan(d2 * 0.017453292519943295d) * 0.996647189328169d);
        double atan2 = Math.atan(Math.tan(d4 * 0.017453292519943295d) * 0.996647189328169d);
        double cos = Math.cos(atan);
        double cos2 = Math.cos(atan2);
        double sin = Math.sin(atan);
        double sin2 = Math.sin(atan2);
        double d6 = cos * cos2;
        double d7 = sin * sin2;
        double d8 = (d3 * 0.017453292519943295d) - (d * 0.017453292519943295d);
        double d9 = 0.0d;
        double d10 = 0.0d;
        double d11 = 0.0d;
        float[] fArr = new float[1];
        int i = 0;
        double d12 = d8;
        while (true) {
            if (i >= 20) {
                d5 = d9;
                break;
            }
            double cos3 = Math.cos(d12);
            double sin3 = Math.sin(d12);
            double d13 = cos2 * sin3;
            double d14 = (cos * sin2) - ((sin * cos2) * cos3);
            double d15 = sin;
            double sqrt = Math.sqrt((d13 * d13) + (d14 * d14));
            double d16 = (cos3 * d6) + d7;
            d10 = Math.atan2(sqrt, d16);
            double d17 = sqrt == 0.0d ? 0.0d : (sin3 * d6) / sqrt;
            double d18 = 1.0d - (d17 * d17);
            double d19 = d18 == 0.0d ? 0.0d : d16 - ((d7 * 2.0d) / d18);
            double d20 = 0.006739496756586903d * d18;
            d5 = ((d20 / 16384.0d) * (((((320.0d - (175.0d * d20)) * d20) - 0.005859375d) * d20) + 4096.0d)) + 1.0d;
            double d21 = (d20 / 1024.0d) * ((d20 * (((74.0d - (47.0d * d20)) * d20) - 0.03125d)) + 256.0d);
            double d22 = 2.0955066698943685E-4d * d18 * (((4.0d - (d18 * 3.0d)) * 0.0033528106718309896d) + 4.0d);
            double d23 = d19 * d19;
            d11 = d21 * sqrt * (d19 + ((d21 / 4.0d) * ((((d23 * 2.0d) - 4.0d) * d16) - ((((d21 / 6.0d) * d19) * (((sqrt * 4.0d) * sqrt) - 1.5d)) * ((d23 * 4.0d) - 1.5d)))));
            double d24 = d8 + ((1.0d - d22) * 0.0033528106718309896d * d17 * (d10 + (sqrt * d22 * (d19 + (d22 * d16 * (((2.0d * d19) * d19) - 4.0d))))));
            if (Math.abs((d24 - d12) / d24) < 1.0E-12d) {
                break;
            }
            i++;
            d12 = d24;
            sin = d15;
            d9 = d5;
        }
        fArr[0] = (float) (d5 * 6356752.3142d * (d10 - d11));
        return (double) fArr[0];
    }
}
