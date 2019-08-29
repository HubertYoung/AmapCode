package com.autonavi.miniapp.plugin.carowner;

import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.carownerservice.ajx.ModuleCarOwner;
import com.autonavi.map.db.model.Car;
import com.autonavi.miniapp.plugin.BasePlugin;
import com.autonavi.miniapp.plugin.carowner.CarOwnerHelper.Callback;
import com.autonavi.miniapp.plugin.util.AMapUserInfoUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CarOwnerPlugin extends BasePlugin {
    private static final String ADD_VEHICLE = "amapAddVehicle";
    private static final String DELETE_VEHICLE = "amapDeleteVehicle";
    private static final String ERROR_MSG_MTOP_AUTH_FAIL = "授权失败";
    private static final String ERROR_MSG_MTOP_GET_AUTHED_FAIL = "获取授权信息失败";
    private static final String ERROR_MSG_NOT_AUTH = "该小程序未授权";
    private static final int ERROR_MTOP_AUTH_FAIL = 22;
    private static final int ERROR_MTOP_GET_AUTHED_FAIL = 21;
    private static final int ERROR_NOT_AUTH = 23;
    private static final String GET_AUTHED_VEHICLES = "amapGetAuthedVehicles";
    private static final String GET_VEHICLE_LIST = "amapGetVehicleList";
    private static final String GET_VEHICLE_MODEL_INFOS = "getVehicleModelInfos";
    private static final int PLATE_NO_LENGTH_ELECTRIC = 8;
    private static final String SYNC_VEHICLES = "amapSyncVehicles";
    public static final String TAG = "CarOwnerPlugin";
    private static final String UPDATE_VEHICLE = "amapUpdateVehicle";
    private static final int VIN_ENGINE_MIN_LENGTH = 4;
    private List<String> mActions = new ArrayList();

    public CarOwnerPlugin() {
        this.mActions.add(GET_VEHICLE_LIST);
        this.mActions.add(ADD_VEHICLE);
        this.mActions.add(DELETE_VEHICLE);
        this.mActions.add(UPDATE_VEHICLE);
        this.mActions.add(SYNC_VEHICLES);
        this.mActions.add(GET_AUTHED_VEHICLES);
    }

    public String getScope() {
        return SCOPE_PAGE;
    }

    public void onPrepare(H5EventFilter h5EventFilter) {
        if (this.mActions != null && h5EventFilter != null) {
            for (String addAction : this.mActions) {
                h5EventFilter.addAction(addAction);
            }
        }
    }

    public String getEvents() {
        return getEvents(this.mActions);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (h5Event == null) {
            LoggerFactory.getTraceLogger().info(TAG, "event == null");
            return false;
        }
        String action = h5Event.getAction();
        try {
            if (GET_VEHICLE_LIST.equals(action)) {
                getVehicle(h5Event, h5BridgeContext);
                return true;
            } else if (ADD_VEHICLE.equals(action)) {
                addVehicle(h5Event, h5BridgeContext);
                return true;
            } else if (DELETE_VEHICLE.equals(action)) {
                deleteVehicle(h5Event, h5BridgeContext);
                return true;
            } else if (UPDATE_VEHICLE.equals(action)) {
                updateVehicle(h5Event, h5BridgeContext);
                return true;
            } else if (SYNC_VEHICLES.equals(action)) {
                syncVehicles(h5Event, h5BridgeContext);
                return true;
            } else if (GET_AUTHED_VEHICLES.equals(action)) {
                getAuthedVehicles(h5Event, h5BridgeContext);
                return true;
            } else {
                if (GET_VEHICLE_MODEL_INFOS.equals(action)) {
                    getVehicleModelInfos(h5BridgeContext);
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            AMapLog.error("infoservice.miniapp", TAG, Log.getStackTraceString(e));
            h5BridgeContext.sendBridgeResult(buildCommonError(Error.INVALID_PARAM, "exception thrown"));
        }
    }

    private void syncVehicles(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        List<MiniAppVehicle> list;
        ant userInfo = AMapUserInfoUtil.getInstance().getUserInfo();
        if (userInfo == null || TextUtils.isEmpty(userInfo.u) || TextUtils.isEmpty(userInfo.a)) {
            h5BridgeContext.sendBridgeResult(buildBusinessError(23, ERROR_MSG_NOT_AUTH));
            return;
        }
        JSONObject param = h5Event.getParam();
        JSONArray jSONArray = param.getJSONArray("optionalItems");
        if (jSONArray == null) {
            jSONArray = new JSONArray();
        }
        JSONArray jSONArray2 = jSONArray;
        JSONArray jSONArray3 = param.getJSONArray("vehicles");
        List<Car> a = bsl.a().a(-1);
        if (a == null) {
            a = Collections.emptyList();
        }
        if (jSONArray3 == null || jSONArray3.size() <= 0) {
            list = Collections.emptyList();
        } else {
            list = jSONArray3.toJavaList(MiniAppVehicle.class);
        }
        LinkedList linkedList = new LinkedList();
        for (MiniAppVehicle next : list) {
            if (next.plateNo != null) {
                next.plateNo = next.plateNo.toUpperCase();
            }
            boolean z = false;
            Iterator<Car> it = a.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Car next2 = it.next();
                if (TextUtils.equals(next.plateNo, next2.plateNum)) {
                    linkedList.add(new Pair(next, next2));
                    z = true;
                    break;
                }
            }
            if (!z) {
                linkedList.add(new Pair(next, null));
            }
        }
        syncVehiclesSanityCheck(h5BridgeContext, linkedList);
        int syncVehiclesToCarOwner = syncVehiclesToCarOwner(linkedList);
        if (syncVehiclesToCarOwner != 0) {
            h5BridgeContext.sendBridgeResult(buildBusinessError(CarOwnerErrorCodeHelper.translateErrorCode(syncVehiclesToCarOwner)));
            return;
        }
        List<MiniAppVehicle> syncCheckAuthVehicles = syncCheckAuthVehicles(linkedList, jSONArray2);
        if (syncCheckAuthVehicles.isEmpty()) {
            syncVehiclesCheckResult(userInfo, h5Event, h5BridgeContext, jSONArray2);
            return;
        }
        final H5BridgeContext h5BridgeContext2 = h5BridgeContext;
        final ant ant = userInfo;
        final H5Event h5Event2 = h5Event;
        final JSONArray jSONArray4 = jSONArray2;
        AnonymousClass1 r1 = new Callback() {
            public void onResponse(JSONObject jSONObject) {
                AMapLog.debug("infoservice.miniapp", CarOwnerPlugin.TAG, "authVehicles onResponse, json: ".concat(String.valueOf(jSONObject)));
                if (jSONObject.getIntValue("code") != 1) {
                    h5BridgeContext2.sendBridgeResult(CarOwnerPlugin.this.buildBusinessError(22, CarOwnerPlugin.ERROR_MSG_MTOP_AUTH_FAIL));
                } else {
                    CarOwnerPlugin.this.syncVehiclesCheckResult(ant, h5Event2, h5BridgeContext2, jSONArray4);
                }
            }
        };
        CarOwnerHelper.authVehicles(userInfo, h5Event, syncCheckAuthVehicles, jSONArray2, r1);
    }

    private List<MiniAppVehicle> syncCheckAuthVehicles(List<Pair<MiniAppVehicle, Car>> list, JSONArray jSONArray) {
        LinkedList linkedList = new LinkedList();
        for (Pair<MiniAppVehicle, Car> pair : list) {
            boolean z = true;
            MiniAppVehicle miniAppVehicle = (MiniAppVehicle) pair.first;
            if (jSONArray.contains(CarOwnerHelper.OPTIONAL_ITEM_ENGINE_NO) && TextUtils.isEmpty(miniAppVehicle.engineNo)) {
                z = false;
            }
            if (jSONArray.contains(CarOwnerHelper.OPTIONAL_ITEM_VIN) && TextUtils.isEmpty(miniAppVehicle.vin)) {
                z = false;
            }
            if (z) {
                linkedList.add(miniAppVehicle);
            }
        }
        return linkedList;
    }

    /* access modifiers changed from: private */
    public void syncVehiclesCheckResult(ant ant, H5Event h5Event, final H5BridgeContext h5BridgeContext, JSONArray jSONArray) {
        List<Car> a = bsl.a().a(1);
        if (a == null || a.size() == 0) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "hasUnauthed", (Object) Boolean.FALSE);
            h5BridgeContext.sendBridgeResult(buildCommonSuccess(jSONObject));
            return;
        }
        final LinkedList linkedList = new LinkedList();
        for (Car transCarToMiniAppVehicle : a) {
            linkedList.add(CarOwnerHelper.transCarToMiniAppVehicle(transCarToMiniAppVehicle));
        }
        CarOwnerHelper.getVehicleAuthInfo(ant, h5Event, linkedList, jSONArray, new Callback() {
            public void onResponse(JSONObject jSONObject) {
                AMapLog.debug("infoservice.miniapp", CarOwnerPlugin.TAG, "getVehicleAuthInfo onResponse, json: ".concat(String.valueOf(jSONObject)));
                boolean z = true;
                if (jSONObject.getIntValue("code") != 1) {
                    h5BridgeContext.sendBridgeResult(CarOwnerPlugin.this.buildBusinessError(21, CarOwnerPlugin.ERROR_MSG_MTOP_GET_AUTHED_FAIL));
                    return;
                }
                if (jSONObject.getJSONObject("data").getJSONArray("carInfos").size() == linkedList.size()) {
                    z = false;
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put((String) "hasUnauthed", (Object) Boolean.valueOf(z));
                h5BridgeContext.sendBridgeResult(CarOwnerPlugin.this.buildCommonSuccess(jSONObject2));
            }
        });
    }

    private int syncVehiclesToCarOwner(List<Pair<MiniAppVehicle, Car>> list) {
        int i;
        for (Pair next : list) {
            MiniAppVehicle miniAppVehicle = (MiniAppVehicle) next.first;
            Car car = (Car) next.second;
            if (car != null) {
                Car car2 = new Car();
                CarOwnerHelper.mergeCarInfo(car, car2);
                CarOwnerHelper.transMiniAppVehicleToCar(miniAppVehicle, car2);
                i = bsl.a().a(car2, car2.plateNum);
                continue;
            } else {
                i = bsl.a().a(CarOwnerHelper.transMiniAppVehicleToCar(miniAppVehicle));
                continue;
            }
            if (i != 0) {
                return i;
            }
        }
        return 0;
    }

    private void syncVehiclesSanityCheck(H5BridgeContext h5BridgeContext, List<Pair<MiniAppVehicle, Car>> list) {
        LinkedList linkedList = new LinkedList();
        for (Pair next : list) {
            MiniAppVehicle miniAppVehicle = (MiniAppVehicle) next.first;
            Car car = (Car) next.second;
            toUpperCase(miniAppVehicle);
            if (TextUtils.isEmpty(miniAppVehicle.plateNo) || !CarOwnerHelper.verifyPlateNo(miniAppVehicle.plateNo)) {
                linkedList.add(next);
            } else if (car == null || car.vehicleType == 1) {
                if (miniAppVehicle.vehicleType == null) {
                    miniAppVehicle.vehicleType = Integer.valueOf(1);
                } else if (miniAppVehicle.vehicleType.intValue() != 1) {
                    linkedList.add(next);
                }
                if (miniAppVehicle.powerType == null || miniAppVehicle.powerType.intValue() < 0 || miniAppVehicle.powerType.intValue() > 2) {
                    if (car != null) {
                        miniAppVehicle.powerType = Integer.valueOf(car.vehiclePowerType);
                    } else {
                        miniAppVehicle.powerType = Integer.valueOf(0);
                    }
                }
                if (miniAppVehicle.plateNo.length() == 8 && miniAppVehicle.powerType.intValue() == 0) {
                    miniAppVehicle.powerType = Integer.valueOf(1);
                }
                miniAppVehicle.vin = getVinOrEngineNo(miniAppVehicle.vin, car == null ? "" : car.frameNum);
                miniAppVehicle.engineNo = getVinOrEngineNo(miniAppVehicle.engineNo, car == null ? "" : car.engineNum);
            } else {
                linkedList.add(next);
            }
        }
        list.removeAll(linkedList);
    }

    private void toUpperCase(MiniAppVehicle miniAppVehicle) {
        if (miniAppVehicle != null) {
            if (!TextUtils.isEmpty(miniAppVehicle.plateNo)) {
                miniAppVehicle.plateNo = miniAppVehicle.plateNo.toUpperCase();
            }
            if (!TextUtils.isEmpty(miniAppVehicle.vin)) {
                miniAppVehicle.vin = miniAppVehicle.vin.toUpperCase();
            }
            if (!TextUtils.isEmpty(miniAppVehicle.engineNo)) {
                miniAppVehicle.engineNo = miniAppVehicle.engineNo.toUpperCase();
            }
        }
    }

    private String getVinOrEngineNo(String str, String str2) {
        if (TextUtils.isEmpty(str) || str.length() < 4 || !CarOwnerHelper.verifyAlphaDigit(str)) {
            return null;
        }
        if (!TextUtils.isEmpty(str2) && str2.length() >= 4 && str2.length() > str.length()) {
            String upperCase = str2.toUpperCase();
            return (!upperCase.startsWith(str) && !upperCase.substring(upperCase.length() - str.length()).equals(str)) ? str : upperCase;
        }
    }

    private void getAuthedVehicles(H5Event h5Event, final H5BridgeContext h5BridgeContext) {
        ant userInfo = AMapUserInfoUtil.getInstance().getUserInfo();
        if (userInfo == null || TextUtils.isEmpty(userInfo.u) || TextUtils.isEmpty(userInfo.a)) {
            h5BridgeContext.sendBridgeResult(buildBusinessError(23, ERROR_MSG_NOT_AUTH));
            return;
        }
        final JSONArray jSONArray = h5Event.getParam().getJSONArray("optionalItems");
        List<Car> a = bsl.a().a(-1);
        if (a == null || a.size() == 0) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "vehicles", (Object) "[]");
            h5BridgeContext.sendBridgeResult(buildCommonSuccess(jSONObject));
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (Car transCarToMiniAppVehicle : a) {
            linkedList.add(CarOwnerHelper.transCarToMiniAppVehicle(transCarToMiniAppVehicle));
        }
        CarOwnerHelper.getVehicleAuthInfo(userInfo, h5Event, linkedList, jSONArray, new Callback() {
            public void onResponse(JSONObject jSONObject) {
                try {
                    if (jSONObject.getIntValue("code") != 1) {
                        h5BridgeContext.sendBridgeResult(CarOwnerPlugin.this.buildBusinessError(21, CarOwnerPlugin.ERROR_MSG_MTOP_GET_AUTHED_FAIL));
                        return;
                    }
                    JSONArray jSONArray = jSONObject.getJSONObject("data").getJSONArray("carInfos");
                    LinkedList linkedList = new LinkedList();
                    List<Car> a = bsl.a().a(-1);
                    if (a == null) {
                        a = Collections.emptyList();
                    }
                    LinkedList<MiniAppVehicle> linkedList2 = new LinkedList<>();
                    for (Car transCarToMiniAppVehicle : a) {
                        linkedList2.add(CarOwnerHelper.transCarToMiniAppVehicle(transCarToMiniAppVehicle));
                    }
                    for (int i = 0; i < jSONArray.size(); i++) {
                        String string = jSONArray.getString(i);
                        for (MiniAppVehicle miniAppVehicle : linkedList2) {
                            if (TextUtils.equals(miniAppVehicle.plateNo, string)) {
                                linkedList.add(miniAppVehicle);
                            }
                        }
                    }
                    JSONArray jSONArray2 = (JSONArray) JSON.toJSON(linkedList);
                    CarOwnerHelper.filterOptionalItems(jSONArray2, jSONArray);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put((String) "vehicles", (Object) jSONArray2.toJSONString());
                    h5BridgeContext.sendBridgeResult(CarOwnerPlugin.this.buildCommonSuccess(jSONObject2));
                } catch (Exception e) {
                    AMapLog.error("infoservice.miniapp", CarOwnerPlugin.TAG, Log.getStackTraceString(e));
                    h5BridgeContext.sendBridgeResult(CarOwnerPlugin.this.buildCommonError(Error.UNKNOWN_ERROR, "未知错误"));
                }
            }
        });
    }

    private void getVehicleModelInfos(H5BridgeContext h5BridgeContext) {
        List<Car> a = bsl.a().a(-1);
        if (a == null) {
            a = Collections.emptyList();
        }
        JSONArray jSONArray = new JSONArray();
        for (Car car : a) {
            jSONArray.add(car.vehicleMsg);
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "vehicles", JSON.toJSON(jSONArray));
        AMapLog.debug("infoservice.miniapp", TAG, "handleEvent, event: getVehicleModelInfos, result: ".concat(String.valueOf(jSONObject)));
        h5BridgeContext.sendBridgeResult(buildCommonSuccess(jSONObject));
    }

    private void getVehicle(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        JSONObject param = h5Event.getParam();
        List<Car> a = bsl.a().a(param.containsKey("type") ? param.getIntValue("type") : -1);
        if (a == null) {
            a = Collections.emptyList();
        }
        LinkedList linkedList = new LinkedList();
        for (Car transCarToMiniAppVehicle : a) {
            linkedList.add(CarOwnerHelper.transCarToMiniAppVehicle(transCarToMiniAppVehicle));
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "vehicles", JSON.toJSON(linkedList));
        AMapLog.debug("infoservice.miniapp", TAG, "handleEvent, event: amapGetVehicleList, result: ".concat(String.valueOf(jSONObject)));
        h5BridgeContext.sendBridgeResult(buildCommonSuccess(jSONObject));
    }

    private void addVehicle(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        JSONObject param = h5Event.getParam();
        JSONObject jSONObject = param.getJSONObject(ModuleCarOwner.KEY_VEHICLE);
        AMapLog.debug("infoservice.miniapp", TAG, "handleEvent, event: amapAddVehicle, params: ".concat(String.valueOf(param)));
        if (jSONObject == null) {
            h5BridgeContext.sendBridgeResult(buildCommonError(Error.UNKNOWN_ERROR, "can't find vehicle in params"));
            return;
        }
        int a = bsl.a().a(CarOwnerHelper.transMiniAppVehicleToCar((MiniAppVehicle) jSONObject.toJavaObject(MiniAppVehicle.class)));
        if (a == 0) {
            h5BridgeContext.sendBridgeResult(buildCommonSuccess());
        } else {
            h5BridgeContext.sendBridgeResult(buildBusinessError(CarOwnerErrorCodeHelper.translateErrorCode(a)));
        }
    }

    private void deleteVehicle(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        JSONObject param = h5Event.getParam();
        JSONObject jSONObject = param.getJSONObject(ModuleCarOwner.KEY_VEHICLE);
        AMapLog.debug("infoservice.miniapp", TAG, "handleEvent, event: amapDeleteVehicle, params: ".concat(String.valueOf(param)));
        if (jSONObject == null) {
            h5BridgeContext.sendBridgeResult(buildCommonError(Error.UNKNOWN_ERROR, "can't find vehicle in params"));
            return;
        }
        MiniAppVehicle miniAppVehicle = (MiniAppVehicle) jSONObject.toJavaObject(MiniAppVehicle.class);
        int a = bsl.a().a(miniAppVehicle.plateNo, CarOwnerHelper.transMiniAppTypeToCarTypeMajor(miniAppVehicle.vehicleType.intValue()));
        if (a == 0) {
            h5BridgeContext.sendBridgeResult(buildCommonSuccess());
        } else {
            h5BridgeContext.sendBridgeResult(buildBusinessError(CarOwnerErrorCodeHelper.translateErrorCode(a)));
        }
    }

    private void updateVehicle(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        JSONObject param = h5Event.getParam();
        JSONObject jSONObject = param.getJSONObject(ModuleCarOwner.KEY_VEHICLE);
        String string = param.getString("oldPlateNo");
        AMapLog.debug("infoservice.miniapp", TAG, "handleEvent, event: amapUpdateVehicle, params: ".concat(String.valueOf(param)));
        if (jSONObject == null) {
            h5BridgeContext.sendBridgeResult(buildCommonError(Error.UNKNOWN_ERROR, "can't find vehicle in params"));
            return;
        }
        int i = 6;
        if (!TextUtils.isEmpty(string)) {
            Car a = bsl.a().a(string);
            if (a != null) {
                Car car = new Car();
                CarOwnerHelper.mergeCarInfo(a, car);
                CarOwnerHelper.transMiniAppVehicleToCar((MiniAppVehicle) jSONObject.toJavaObject(MiniAppVehicle.class), car);
                i = bsl.a().a(car, string);
            }
        }
        if (i == 0) {
            h5BridgeContext.sendBridgeResult(buildCommonSuccess());
        } else {
            h5BridgeContext.sendBridgeResult(buildBusinessError(CarOwnerErrorCodeHelper.translateErrorCode(i)));
        }
    }

    private JSONObject buildBusinessError(Pair<Integer, String> pair) {
        return buildBusinessError(((Integer) pair.first).intValue(), (String) pair.second);
    }
}
