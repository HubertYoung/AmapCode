package com.autonavi.map.db.model;

import android.text.TextUtils;
import java.util.List;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class Vehicles {
    public Long id;
    public String ocrRequestId;
    public String uid;
    public String vehicle_brandName;
    public Integer vehicle_checkReminder;
    public String vehicle_dischargeRate;
    public String vehicle_engineNum;
    public String vehicle_frameNum;
    public Long vehicle_id;
    public Integer vehicle_limitDateType;
    public Integer vehicle_limitReminder;
    public String vehicle_modificationTimes;
    public Integer vehicle_oftenUse;
    public String vehicle_plateNum;
    public String vehicle_remark;
    public String vehicle_restrictionMsg;
    public String vehicle_telephone;
    public String vehicle_validityPeriod;
    public String vehicle_vehicleLogo;
    public String vehicle_vehicleMsg;
    public String vehicle_vehicleStyle;
    public String vehicle_vehiclecode;
    public Integer vehicle_violationNum;
    public Integer vehicle_violationReminder;
    public String vehicle_violationUrl;
    public String vehicle_years;

    public static Vehicles createEmptyIns() {
        Vehicles vehicles = new Vehicles();
        vehicles.vehicle_id = Long.valueOf(-1);
        vehicles.vehicle_violationReminder = Integer.valueOf(0);
        vehicles.id = Long.valueOf(-1);
        vehicles.vehicle_checkReminder = Integer.valueOf(0);
        vehicles.vehicle_limitReminder = Integer.valueOf(0);
        vehicles.vehicle_oftenUse = Integer.valueOf(0);
        vehicles.vehicle_violationNum = Integer.valueOf(0);
        return vehicles;
    }

    public boolean isViolationCheckEnabled() {
        return !TextUtils.isEmpty(this.vehicle_frameNum) && !TextUtils.isEmpty(this.vehicle_engineNum);
    }

    public boolean isAnnualCheckEnabled() {
        return !TextUtils.isEmpty(this.vehicle_validityPeriod);
    }

    public boolean isViolationReminderTurnOn() {
        if (this.vehicle_violationReminder != null && this.vehicle_violationReminder.intValue() > 0) {
            return true;
        }
        return false;
    }

    public boolean isAnnualReminderTurnOn() {
        if (this.vehicle_checkReminder != null && this.vehicle_checkReminder.intValue() > 0 && !TextUtils.isEmpty(this.vehicle_validityPeriod)) {
            return true;
        }
        return false;
    }

    public boolean isTrafficRestrictionTurnOn() {
        if (this.vehicle_limitReminder != null && this.vehicle_limitReminder.intValue() == 1) {
            return true;
        }
        return false;
    }

    public boolean isRestrictedToday(List<String> list, boolean z) {
        if (TextUtils.isEmpty(this.vehicle_plateNum) || this.vehicle_plateNum.length() <= 0) {
            return false;
        }
        return (!z || !Character.isLetter(this.vehicle_plateNum.charAt(this.vehicle_plateNum.length() - 1))) ? list != null && list.contains(this.vehicle_plateNum.substring(this.vehicle_plateNum.length() - 1)) : list != null && list.contains("0");
    }

    public boolean isOftenUsed() {
        if (this.vehicle_oftenUse != null && this.vehicle_oftenUse.intValue() == 1) {
            return true;
        }
        return false;
    }

    public long getModificationTimes() {
        if (TextUtils.isEmpty(this.vehicle_modificationTimes)) {
            return -1;
        }
        return Long.parseLong(this.vehicle_modificationTimes);
    }

    public int getLimitDateType() {
        if (this.vehicle_limitDateType == null || this.vehicle_limitDateType.intValue() == 0) {
            return 0;
        }
        return this.vehicle_limitDateType.intValue();
    }
}
