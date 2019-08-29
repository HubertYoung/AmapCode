package com.autonavi.miniapp.plugin.carowner;

import java.io.Serializable;

public class MiniAppVehicle implements Serializable {
    public String engineNo;
    public String plateNo;
    public Integer powerType;
    public Integer vehicleType;
    public String vin;

    public String toString() {
        StringBuilder sb = new StringBuilder("MiniAppVehicle{plateNo='");
        sb.append(this.plateNo);
        sb.append('\'');
        sb.append(", vehicleType=");
        sb.append(this.vehicleType);
        sb.append(", powerType=");
        sb.append(this.powerType);
        sb.append(", engineNo='");
        sb.append(this.engineNo);
        sb.append('\'');
        sb.append(", vin='");
        sb.append(this.vin);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MiniAppVehicle miniAppVehicle = (MiniAppVehicle) obj;
        if (this.plateNo == null ? miniAppVehicle.plateNo != null : !this.plateNo.equals(miniAppVehicle.plateNo)) {
            return false;
        }
        if (this.vehicleType == null ? miniAppVehicle.vehicleType != null : !this.vehicleType.equals(miniAppVehicle.vehicleType)) {
            return false;
        }
        if (this.powerType == null ? miniAppVehicle.powerType != null : !this.powerType.equals(miniAppVehicle.powerType)) {
            return false;
        }
        if (this.engineNo == null ? miniAppVehicle.engineNo != null : !this.engineNo.equals(miniAppVehicle.engineNo)) {
            return false;
        }
        if (this.vin != null) {
            return this.vin.equals(miniAppVehicle.vin);
        }
        return miniAppVehicle.vin == null;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (((((((this.plateNo != null ? this.plateNo.hashCode() : 0) * 31) + (this.vehicleType != null ? this.vehicleType.hashCode() : 0)) * 31) + (this.powerType != null ? this.powerType.hashCode() : 0)) * 31) + (this.engineNo != null ? this.engineNo.hashCode() : 0)) * 31;
        if (this.vin != null) {
            i = this.vin.hashCode();
        }
        return hashCode + i;
    }
}
