package org.altbeacon.beacon.distance;

import org.altbeacon.beacon.b.d;

/* compiled from: CurveFittedDistanceCalculator */
public final class b implements c {
    private double a;
    private double b;
    private double c;

    public b(double coefficient1, double coefficient2, double coefficient3) {
        this.a = coefficient1;
        this.b = coefficient2;
        this.c = coefficient3;
    }

    public final double a(int txPower, double rssi) {
        double distance;
        if (rssi == 0.0d) {
            return -1.0d;
        }
        d.a("CurveFittedDistanceCalculator", "calculating distance based on mRssi of %s and txPower of %s", Double.valueOf(rssi), Integer.valueOf(txPower));
        double ratio = (1.0d * rssi) / ((double) txPower);
        if (ratio < 1.0d) {
            distance = Math.pow(ratio, 10.0d);
        } else {
            distance = (this.a * Math.pow(ratio, this.b)) + this.c;
        }
        d.a("CurveFittedDistanceCalculator", "avg mRssi: %s distance: %s", Double.valueOf(rssi), Double.valueOf(distance));
        return distance;
    }
}
