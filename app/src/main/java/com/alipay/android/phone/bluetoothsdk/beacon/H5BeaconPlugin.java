package com.alipay.android.phone.bluetoothsdk.beacon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.bluetoothsdk.MonitorHelper;
import com.alipay.android.phone.bluetoothsdk.better.ble.ErrorConstants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import java.util.ArrayList;
import java.util.List;

public class H5BeaconPlugin extends H5SimplePlugin {
    private static final String EVENT_BEACON_SERVICE_CHANGE = "beaconServiceChange";
    private static final String EVENT_BEACON_UPDATE = "beaconUpdate";
    private static final String FUNC_GET_BEACONS = "getBeacons";
    private static final String FUNC_START_BEACON_DISCOVERY = "startBeaconDiscovery";
    private static final String FUNC_STOP_BEACON_DISCOVERY = "stopBeaconDiscovery";
    private static final String KEY_AVAILABLE = "available";
    private static final String KEY_BEACONS = "beacons";
    private static final String KEY_DATA = "data";
    private static final String KEY_DISCOVERING = "discovering";
    private static final String KEY_ERROR = "error";
    private static final String KEY_ERROR_MESSAGE = "errorMessage";
    private static final String KEY_UUIDS = "uuids";
    private static final String TAG = "H5BeaconPlugin";
    private static List<String> list;
    private H5Page h5Page;
    private H5Service h5Service;
    private MyBeaconListener myBeaconListener = new MyBeaconListener() {
        public void onBeaconUpdate(List<MyBeacon> myBeacons) {
            JSONObject param = new JSONObject();
            JSONObject data = new JSONObject();
            data.put((String) H5BeaconPlugin.KEY_BEACONS, JSON.toJSON(myBeacons));
            param.put((String) "data", (Object) data);
            H5Page h5Page = H5BeaconPlugin.this.getTopH5Page();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BeaconPlugin.EVENT_BEACON_UPDATE, param, null);
            }
        }

        public void onBeaconServiceChange(boolean available, boolean discovering) {
            JSONObject param = new JSONObject();
            JSONObject data = new JSONObject();
            data.put((String) H5BeaconPlugin.KEY_AVAILABLE, (Object) String.valueOf(available));
            data.put((String) H5BeaconPlugin.KEY_DISCOVERING, (Object) Boolean.valueOf(discovering));
            param.put((String) "data", (Object) data);
            H5Page h5Page = H5BeaconPlugin.this.getTopH5Page();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BeaconPlugin.EVENT_BEACON_SERVICE_CHANGE, param, null);
            }
        }
    };
    private MyBeaconService myBeaconService;

    static {
        ArrayList arrayList = new ArrayList();
        list = arrayList;
        arrayList.add(FUNC_START_BEACON_DISCOVERY);
        list.add(FUNC_STOP_BEACON_DISCOVERY);
        list.add(FUNC_GET_BEACONS);
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.setEventsList(list);
        this.h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
        this.myBeaconService = (MyBeaconService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MyBeaconService.class.getName());
        this.myBeaconService.setMyBeaconListener(this.myBeaconListener);
    }

    public void onRelease() {
        super.onRelease();
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        LoggerFactory.getTraceLogger().debug(TAG, "handleEvent, event:" + event.getAction());
        this.h5Page = event.getH5page();
        if (FUNC_START_BEACON_DISCOVERY.equals(event.getAction())) {
            startBeaconDiscovery(event, context);
        } else if (FUNC_STOP_BEACON_DISCOVERY.equals(event.getAction())) {
            stopBeaconDiscovery(event, context);
        } else if (FUNC_GET_BEACONS.equals(event.getAction())) {
            getBeacons(event, context);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public H5Page getTopH5Page() {
        if (this.h5Page != null && this.h5Page.getBridge() != null) {
            return this.h5Page;
        }
        if (this.h5Service != null) {
            H5BaseFragment h5BaseFragment = this.h5Service.getTopH5BaseFragment();
            if (h5BaseFragment != null) {
                return h5BaseFragment.getH5Page();
            }
        }
        return null;
    }

    private void startBeaconDiscovery(H5Event event, H5BridgeContext context) {
        if (this.myBeaconService == null) {
            sendDefaultErrorBridgeResult(context);
        } else if (event.getParam() == null || !event.getParam().containsKey(KEY_UUIDS)) {
            sendUUIDEmptyErrorBidgeResult(context);
        } else {
            List serviceUUIDList = JSON.parseArray(event.getParam().getString(KEY_UUIDS), String.class);
            if (serviceUUIDList == null || serviceUUIDList.isEmpty()) {
                sendUUIDEmptyErrorBidgeResult(context);
            } else {
                String[] serviceUUIDs = new String[serviceUUIDList.size()];
                serviceUUIDList.toArray(serviceUUIDs);
                sendBridgeResult(context, this.myBeaconService.startBeaconDiscovery(serviceUUIDs));
            }
        }
        MonitorHelper.logStartBeaconDiscovery();
    }

    private void stopBeaconDiscovery(H5Event event, H5BridgeContext context) {
        if (this.myBeaconService != null) {
            sendBridgeResult(context, this.myBeaconService.stopBeaconDiscovery());
        } else {
            sendDefaultErrorBridgeResult(context);
        }
        MonitorHelper.logStopBeaconDiscovery();
    }

    private void getBeacons(H5Event event, H5BridgeContext context) {
        if (this.myBeaconService != null) {
            BeaconResult beaconResult = this.myBeaconService.getBeacons();
            if (beaconResult.success) {
                List myBeaconList = (List) beaconResult.obj;
                JSONObject result = new JSONObject();
                if (myBeaconList != null) {
                    result.put((String) KEY_BEACONS, JSON.toJSON(myBeaconList));
                }
                context.sendBridgeResult(result);
            } else {
                sendBridgeResult(context, beaconResult);
            }
        } else {
            sendDefaultErrorBridgeResult(context);
        }
        MonitorHelper.logGetBeacons();
    }

    private void sendBridgeResult(H5BridgeContext context, BeaconResult beaconResult) {
        JSONObject result = new JSONObject();
        if (!beaconResult.success) {
            result.put((String) "error", (Object) beaconResult.getErrorCode());
            result.put((String) "errorMessage", (Object) beaconResult.getErrorMessage());
        }
        context.sendBridgeResult(result);
    }

    private void sendUUIDEmptyErrorBidgeResult(H5BridgeContext context) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) ErrorConstants.ERROR_BEACON_UUID_EMPTY[0]);
        result.put((String) "errorMessage", (Object) ErrorConstants.ERROR_BEACON_UUID_EMPTY[1]);
        context.sendBridgeResult(result);
    }

    private void sendLackingParamErrorBridgeResult(H5BridgeContext context) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) ErrorConstants.ERROR_BEACON_LACK_PARAMS[0]);
        result.put((String) "errorMessage", (Object) ErrorConstants.ERROR_BEACON_LACK_PARAMS[1]);
        context.sendBridgeResult(result);
    }

    private void sendDefaultErrorBridgeResult(H5BridgeContext context) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) ErrorConstants.ERROR_BEACON_SYSTEM_ERROR[0]);
        result.put((String) "errorMessage", (Object) ErrorConstants.ERROR_BEACON_SYSTEM_ERROR[1]);
        context.sendBridgeResult(result);
    }
}
