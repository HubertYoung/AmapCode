package defpackage;

/* renamed from: epo reason: default package */
/* compiled from: Constants */
public class epo {
    private static final String a = "epo";
    private static double[] b = new double[6];
    private static double[] c = new double[6];

    static {
        String[] split = "1.000000000000000,-4.512146416035126,8.165474333547810,-7.406605409621800,3.366848280064921,-0.613509130387218,0.0000019267990183092,0.0000096339950915458,0.0000192679901830917,0.0000192679901830917,0.0000096339950915458,0.0000019267990183092".split(",");
        if (split.length == 12) {
            for (int i = 0; i < split.length; i++) {
                if (i < 6) {
                    try {
                        b[i] = Double.parseDouble(split[i]);
                    } catch (NumberFormatException e) {
                        kf.a((Throwable) e);
                    }
                } else {
                    c[i - 6] = Double.parseDouble(split[i]);
                }
            }
        }
    }

    static double[] a() {
        return b;
    }

    static double[] b() {
        return c;
    }
}
