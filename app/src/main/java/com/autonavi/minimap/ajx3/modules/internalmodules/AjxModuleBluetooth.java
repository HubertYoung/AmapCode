package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.alibaba.wireless.security.SecExceptionCode;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import com.iflytek.tts.TtsService.Tts;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajx.bluetooth")
public class AjxModuleBluetooth extends AbstractModule {
    /* access modifiers changed from: private */
    public JsFunctionCallback mConnectCallback;
    /* access modifiers changed from: private */
    public JsFunctionCallback mDisConnectCallback;
    private IntentFilter mFilter = new IntentFilter();
    private BluetoothConnectionReceiver mReceiver;

    public class BluetoothConnectionReceiver extends BroadcastReceiver {
        public BluetoothConnectionReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null) {
                JsFunctionCallback jsFunctionCallback = null;
                String action = intent.getAction();
                char c = 65535;
                int hashCode = action.hashCode();
                if (hashCode != -301431627) {
                    if (hashCode == 1821585647 && action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                        c = 1;
                    }
                } else if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                    c = 0;
                }
                switch (c) {
                    case 0:
                        jsFunctionCallback = AjxModuleBluetooth.this.mConnectCallback;
                        break;
                    case 1:
                        jsFunctionCallback = AjxModuleBluetooth.this.mDisConnectCallback;
                        break;
                }
                try {
                    JSONObject bluetoothDeviceInfo = AjxModuleBluetooth.getBluetoothDeviceInfo((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(bluetoothDeviceInfo);
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    public AjxModuleBluetooth(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.mFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        this.mFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
    }

    @AjxMethod(invokeMode = "sync", value = "isConnected")
    public String isBluetoothConnected() {
        return checkBluetoothConnectedInternal() ? "1" : "0";
    }

    @AjxMethod("onConnect")
    public void onConnect(JsFunctionCallback jsFunctionCallback) {
        if (this.mReceiver == null) {
            this.mReceiver = new BluetoothConnectionReceiver();
            getNativeContext().registerReceiver(this.mReceiver, this.mFilter);
        }
        this.mConnectCallback = jsFunctionCallback;
    }

    @AjxMethod("onDisconnect")
    public void onDisconnect(JsFunctionCallback jsFunctionCallback) {
        if (this.mReceiver == null) {
            this.mReceiver = new BluetoothConnectionReceiver();
            getNativeContext().registerReceiver(this.mReceiver, this.mFilter);
        }
        this.mDisConnectCallback = jsFunctionCallback;
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        if (this.mReceiver != null) {
            getNativeContext().unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    public static boolean checkBluetoothConnectedInternal() {
        if (BluetoothAdapter.getDefaultAdapter().getState() == 12 && getBluetoothState() != -1) {
            return true;
        }
        return false;
    }

    public static int getBluetoothState() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        int profileConnectionState = defaultAdapter.getProfileConnectionState(2);
        int profileConnectionState2 = defaultAdapter.getProfileConnectionState(1);
        int profileConnectionState3 = defaultAdapter.getProfileConnectionState(3);
        if (profileConnectionState == 2) {
            return profileConnectionState;
        }
        if (profileConnectionState2 == 2) {
            return profileConnectionState2;
        }
        if (profileConnectionState3 == 2) {
            return profileConnectionState3;
        }
        return -1;
    }

    public static JSONObject getBluetoothDeviceInfo(BluetoothDevice bluetoothDevice) throws JSONException {
        String str;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", -1);
        jSONObject.put("typeName", "未知");
        jSONObject.put("name", "未知");
        if (bluetoothDevice == null || bluetoothDevice.getBluetoothClass() == null) {
            return jSONObject;
        }
        int deviceClass = bluetoothDevice.getBluetoothClass().getDeviceClass();
        switch (deviceClass) {
            case 256:
                str = "未知的电脑设备";
                break;
            case Tts.TTS_STATE_DESTROY /*260*/:
                str = "电脑桌面";
                break;
            case 264:
                str = "电脑服务";
                break;
            case 268:
                str = "便携式电脑";
                break;
            case LZMA_Base.kNumLenSymbols /*272*/:
                str = "手提电脑或Pad";
                break;
            case 276:
                str = "微型电脑";
                break;
            case 280:
                str = "可穿戴的电脑";
                break;
            case 512:
                str = "未知手机";
                break;
            case 516:
                str = "手机";
                break;
            case 520:
                str = "无线设备";
                break;
            case 524:
                str = "手机卫星";
                break;
            case 528:
                str = "手机调节器";
                break;
            case 532:
                str = "手机服务数据网";
                break;
            case 1024:
                str = "视频未知设备";
                break;
            case 1028:
                str = "可穿戴耳机";
                break;
            case 1032:
                str = "蓝牙耳机";
                break;
            case TrafficTopic.MOOD /*1040*/:
                str = "麦克风";
                break;
            case 1044:
                str = "扬声器";
                break;
            case 1048:
                str = "头戴式受话器";
                break;
            case 1052:
                str = "打印机";
                break;
            case 1056:
                str = "车载设备";
                break;
            case TrafficTopic.CONTROL_ICON /*1060*/:
                str = "BOX";
                break;
            case 1064:
                str = "高保真音频设备";
                break;
            case 1068:
                str = "录像机";
                break;
            case 1072:
                str = "照相机录像机";
                break;
            case 1076:
                str = "录像机";
                break;
            case TrafficTopic.NOTIFY /*1080*/:
                str = "可穿戴设备";
                break;
            case 1084:
                str = "显示器和扬声器";
                break;
            case 1088:
                str = "视频会议";
                break;
            case 1096:
                str = "游戏";
                break;
            case 1792:
                str = "未知的可穿戴设备";
                break;
            case 1796:
                str = "手腕监听设备";
                break;
            case 1800:
                str = "客串点寻呼机";
                break;
            case 1804:
                str = "可穿戴上衣";
                break;
            case 1808:
                str = "可穿戴头盔";
                break;
            case 1812:
                str = "可穿戴眼镜";
                break;
            case 2048:
                str = "玩具未知设备";
                break;
            case 2052:
                str = "玩具遥控器";
                break;
            case 2056:
                str = "玩具车辆";
                break;
            case 2060:
                str = "玩具";
                break;
            case 2064:
                str = "可穿戴设备";
                break;
            case 2068:
                str = "游戏";
                break;
            case SecExceptionCode.SEC_ERROR_MIDDLE_TIER_UNSUPPORT_BINARY_DATA_YET /*2304*/:
                str = "未知健康状态设备";
                break;
            case 2308:
                str = "健康状态-血压";
                break;
            case 2312:
                str = "健康状态体温计";
                break;
            case 2316:
                str = "健康状态体重";
                break;
            case 2320:
                str = "健康状态葡萄糖";
                break;
            case 2324:
                str = "健康状态脉搏血氧计";
                break;
            case 2328:
                str = "健康状态脉搏速来";
                break;
            case 2332:
                str = "健康状态数据";
                break;
            default:
                str = "未知";
                break;
        }
        jSONObject.put("type", deviceClass);
        jSONObject.put("typeName", str);
        jSONObject.put("name", bluetoothDevice.getName());
        return jSONObject;
    }
}
