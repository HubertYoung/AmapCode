package defpackage;

/* renamed from: beu reason: default package */
/* compiled from: DistanceCalculator */
public final class beu {
    public static void a(double d, double d2, double d3, double d4, float[] fArr) {
        double d5;
        double d6 = (0.017453292519943295d * d4) - (d2 * 0.017453292519943295d);
        double atan = Math.atan(Math.tan(d * 0.017453292519943295d) * 0.996647189328169d);
        double atan2 = Math.atan(Math.tan(d3 * 0.017453292519943295d) * 0.996647189328169d);
        double cos = Math.cos(atan);
        double cos2 = Math.cos(atan2);
        double sin = Math.sin(atan);
        double sin2 = Math.sin(atan2);
        double d7 = cos * cos2;
        double d8 = sin * sin2;
        double d9 = d6;
        int i = 0;
        double d10 = 0.0d;
        double d11 = 0.0d;
        double d12 = 0.0d;
        while (true) {
            if (i >= 20) {
                d5 = d10;
                break;
            }
            double cos3 = Math.cos(d6);
            double sin3 = Math.sin(d6);
            double d13 = cos2 * sin3;
            double d14 = (cos * sin2) - ((sin * cos2) * cos3);
            double d15 = sin;
            double sqrt = Math.sqrt((d13 * d13) + (d14 * d14));
            double d16 = sin2;
            double d17 = d8 + (cos3 * d7);
            d11 = Math.atan2(sqrt, d17);
            double d18 = sqrt == 0.0d ? 0.0d : (sin3 * d7) / sqrt;
            double d19 = 1.0d - (d18 * d18);
            double d20 = d19 == 0.0d ? 0.0d : d17 - ((d8 * 2.0d) / d19);
            double d21 = 0.006739496756586903d * d19;
            d5 = ((d21 / 16384.0d) * (((((320.0d - (175.0d * d21)) * d21) - 0.005859375d) * d21) + 4096.0d)) + 1.0d;
            double d22 = (d21 / 1024.0d) * ((d21 * (((74.0d - (47.0d * d21)) * d21) - 0.03125d)) + 256.0d);
            double d23 = 2.0955066698943685E-4d * d19 * (((4.0d - (d19 * 3.0d)) * 0.0033528106718309896d) + 4.0d);
            double d24 = d20 * d20;
            d12 = d22 * sqrt * (d20 + ((d22 / 4.0d) * ((((d24 * 2.0d) - 4.0d) * d17) - ((((d22 / 6.0d) * d20) * (((sqrt * 4.0d) * sqrt) - 1.5d)) * ((d24 * 4.0d) - 1.5d)))));
            double d25 = d9 + ((1.0d - d23) * 0.0033528106718309896d * d18 * (d11 + (sqrt * d23 * (d20 + (d23 * d17 * (((2.0d * d20) * d20) - 4.0d))))));
            if (Math.abs((d25 - d6) / d25) < 1.0E-12d) {
                break;
            }
            i++;
            d6 = d25;
            sin = d15;
            sin2 = d16;
            d10 = d5;
        }
        fArr[0] = (float) (d5 * 6356752.3142d * (d11 - d12));
    }
}
