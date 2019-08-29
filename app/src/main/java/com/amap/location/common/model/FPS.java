package com.amap.location.common.model;

public class FPS {
    public double accuracy;
    public CellStatus cellStatus = new CellStatus();
    public double latitude;
    public double longitude;
    public byte provider;
    public WifiStatus wifiStatus = new WifiStatus();

    public FPS clone() {
        FPS fps = new FPS();
        if (this.cellStatus != null) {
            fps.cellStatus = this.cellStatus.clone();
        }
        if (this.wifiStatus != null) {
            fps.wifiStatus = this.wifiStatus.clone();
        }
        return fps;
    }

    public String toString() {
        return super.toString();
    }

    public String toStringSimple() {
        return toStr(true);
    }

    private String toStr(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("FPS[");
        if (this.cellStatus == null) {
            sb.append("cellStatus:null");
        } else if (z) {
            sb.append(this.cellStatus.toStringSimple());
        } else {
            sb.append(this.cellStatus.toString());
        }
        sb.append(";");
        if (this.wifiStatus == null) {
            sb.append("wifiScan:null");
        } else if (z) {
            sb.append(this.wifiStatus.toStringSimple());
        } else {
            sb.append(this.wifiStatus.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
