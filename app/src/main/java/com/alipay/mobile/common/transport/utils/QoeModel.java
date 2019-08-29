package com.alipay.mobile.common.transport.utils;

public class QoeModel {
    public double rtt_d = 0.0d;
    public double rtt_o = 500.0d;
    public double rtt_s = 0.0d;

    public void reset() {
        this.rtt_o = 0.0d;
        this.rtt_d = 0.0d;
        this.rtt_s = 0.0d;
    }

    public void estimate(double rtt) {
        double d = 500.0d;
        if (this.rtt_s == 0.0d) {
            this.rtt_s = rtt;
            this.rtt_d = 0.25d * rtt;
            if (this.rtt_d > 500.0d) {
                d = this.rtt_d;
            }
            this.rtt_d = d;
        } else {
            this.rtt_s += 0.1d * (rtt - this.rtt_s);
            double rtt2 = rtt - this.rtt_s;
            if (rtt2 < 0.0d) {
                rtt2 = -rtt2;
            }
            this.rtt_d = (0.75d * this.rtt_d) + (0.25d * rtt2);
        }
        this.rtt_o = (1.0d * this.rtt_s) + (4.0d * this.rtt_d);
    }
}
