package com.amap.bundle.drivecommon.restrictedarea;

import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.map.db.model.Car;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"restrict_type", "plate"}, url = "ws/mapapi/navigation/auto/restrictedarea/?")
public final class RestrictedAreaParam implements ParamEntity {
    public String adcodes;
    public long endroad;
    public String plate;
    public int restrict_type = 1;
    public String ruleids;
    public long startroad;
    public float truck_height;
    public float truck_load;
    public int vehicle_type;
    public String via_points;

    private RestrictedAreaParam() {
    }

    public static final RestrictedAreaParam buildCityListParam(int i) {
        Car car;
        RestrictedAreaParam restrictedAreaParam = new RestrictedAreaParam();
        restrictedAreaParam.restrict_type = 8;
        restrictedAreaParam.adcodes = "110000";
        restrictedAreaParam.plate = "高A0000";
        if (i == 1) {
            car = DriveUtil.getCarTruckInfo();
        } else {
            car = DriveUtil.getCarInfo();
        }
        restrictedAreaParam.vehicle_type = DriveUtil.getVtype(car, i);
        return restrictedAreaParam;
    }

    public static final RestrictedAreaParam buildCityRestrictPolicyParam(String str, int i) {
        return buildCityRestrictPolicyParam(str, null, i);
    }

    public static final RestrictedAreaParam buildCityRestrictPolicyParam(String str, String str2, int i) {
        Car car;
        RestrictedAreaParam restrictedAreaParam = new RestrictedAreaParam();
        restrictedAreaParam.restrict_type = 7;
        restrictedAreaParam.adcodes = str;
        restrictedAreaParam.plate = "高A0000";
        String carPlateNumber = DriveUtil.getCarPlateNumber();
        String truckCarPlateNumber = DriveUtil.getTruckCarPlateNumber();
        if (!TextUtils.isEmpty(str2)) {
            restrictedAreaParam.plate = str2;
        } else if (!TextUtils.isEmpty(carPlateNumber)) {
            restrictedAreaParam.plate = carPlateNumber;
        } else if (!TextUtils.isEmpty(truckCarPlateNumber)) {
            restrictedAreaParam.plate = truckCarPlateNumber;
        } else {
            restrictedAreaParam.plate = "高A0000";
        }
        if (i == 1) {
            car = DriveUtil.getCarTruckInfo();
        } else {
            car = DriveUtil.getCarInfo();
        }
        restrictedAreaParam.vehicle_type = DriveUtil.getVtype(car, i);
        return restrictedAreaParam;
    }
}
