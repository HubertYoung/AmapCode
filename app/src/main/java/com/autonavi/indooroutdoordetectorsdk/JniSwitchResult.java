package com.autonavi.indooroutdoordetectorsdk;

public class JniSwitchResult {
    public double confidence;
    double indoor_prob;
    int is_call_net;

    public JniSwitchResult(double d, double d2, int i) {
        this.indoor_prob = d;
        this.confidence = d2;
        this.is_call_net = i;
    }

    public String toString() {
        String str = this.confidence < 0.0d ? "Invalide" : this.confidence <= 0.3d ? "INDOOR" : this.confidence < 0.5d ? "UNKNOW-I" : this.confidence < 0.7d ? "UNKNOW+O" : "OUTDOOR";
        StringBuilder sb = new StringBuilder("confidence=");
        sb.append(GeoFenceHelper.round(this.confidence, 2));
        sb.append(",");
        sb.append(str);
        return sb.toString();
    }
}
