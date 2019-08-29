package defpackage;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.carownerservice.api.ISyncVehicles;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.map.db.model.Vehicles;
import com.autonavi.map.db.model.VehiclesGroup;
import com.autonavi.map.db.model.VehiclesToJson;
import com.autonavi.minimap.basemap.inter.impl.SyncVehiclesUtils$1;
import com.autonavi.minimap.ivs.IvsRequestHolder;
import com.autonavi.minimap.ivs.param.VehicleSyncRequest;
import java.util.ArrayList;
import java.util.List;

/* renamed from: cpu reason: default package */
/* compiled from: SyncVehiclesImpl */
public class cpu implements ISyncVehicles {
    public a pull(Callback<Boolean> callback) {
        return null;
    }

    public void syncLocalVehicles() {
        List list;
        VehiclesToJson vehiclesToJson;
        ato ato = (ato) a.a.a(ato.class);
        VehiclesGroup vehiclesGroup = null;
        if (ato != null) {
            AMapAppGlobal.getApplication();
            list = ato.b().a();
        } else {
            list = null;
        }
        if (list != null && list.size() > 0 && list != null && list.size() != 0) {
            if (!(list == null || list.size() == 0)) {
                int size = list.size();
                VehiclesGroup vehiclesGroup2 = new VehiclesGroup();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < size; i++) {
                    Vehicles vehicles = (Vehicles) list.get(i);
                    if (vehicles == null) {
                        vehiclesToJson = null;
                    } else {
                        vehiclesToJson = new VehiclesToJson();
                        if (!TextUtils.isEmpty(vehicles.vehicle_plateNum)) {
                            vehiclesToJson.setPlateNum(vehicles.vehicle_plateNum);
                        }
                        if (!TextUtils.isEmpty(vehicles.vehicle_frameNum)) {
                            vehiclesToJson.setFrameNum(vehicles.vehicle_frameNum);
                        }
                        if (!TextUtils.isEmpty(vehicles.vehicle_engineNum)) {
                            vehiclesToJson.setEngineNum(vehicles.vehicle_engineNum);
                        }
                        if (!TextUtils.isEmpty(vehicles.vehicle_validityPeriod)) {
                            vehiclesToJson.setValidityPeriod(vehicles.vehicle_validityPeriod);
                        }
                        if (!TextUtils.isEmpty(vehicles.vehicle_vehiclecode)) {
                            vehiclesToJson.setVehiclecode(vehicles.vehicle_vehiclecode);
                        }
                        if (!TextUtils.isEmpty(vehicles.vehicle_telephone)) {
                            vehiclesToJson.setTelphone(vehicles.vehicle_telephone);
                        }
                        if (vehicles.vehicle_oftenUse != null) {
                            vehiclesToJson.setOftenUse(String.valueOf(vehicles.vehicle_oftenUse));
                        }
                        if (vehicles.vehicle_checkReminder != null) {
                            vehiclesToJson.setCheckReminder(vehicles.vehicle_checkReminder.intValue());
                        }
                        if (vehicles.vehicle_violationReminder != null) {
                            vehiclesToJson.setViolationReminder(vehicles.vehicle_violationReminder.intValue());
                        }
                        if (vehicles.vehicle_limitReminder != null) {
                            vehiclesToJson.setLimitReminder(vehicles.vehicle_limitReminder.intValue());
                        }
                        if (vehicles.ocrRequestId != null) {
                            vehiclesToJson.setOcr_request_id(vehicles.ocrRequestId);
                        }
                        if (!TextUtils.isEmpty(vehicles.vehicle_modificationTimes)) {
                            vehiclesToJson.setSyncTime(vehicles.vehicle_modificationTimes);
                        }
                    }
                    arrayList.add(vehiclesToJson);
                }
                vehiclesGroup2.setVehicles(arrayList);
                vehiclesGroup = vehiclesGroup2;
            }
            String jSONString = JSON.toJSONString(vehiclesGroup);
            AMapLog.i("zyl", "json--->".concat(String.valueOf(jSONString)));
            VehicleSyncRequest vehicleSyncRequest = new VehicleSyncRequest();
            vehicleSyncRequest.b = jSONString;
            IvsRequestHolder.getInstance().sendVehicleSync(vehicleSyncRequest, new SyncVehiclesUtils$1());
        }
    }

    public int getLocalVehicleCount() {
        List<Vehicles> list;
        ato ato = (ato) a.a.a(ato.class);
        if (ato != null) {
            AMapAppGlobal.getApplication();
            list = ato.b().a();
        } else {
            list = null;
        }
        if (list == null || list.size() <= 0) {
            return 0;
        }
        return list.size();
    }
}
