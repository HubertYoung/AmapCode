package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.config.db.NetworkConfigDAO;
import com.alipay.mobile.common.transport.ext.ExtTransportOffice;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import org.json.JSONObject;

public class NwSharedSwitchUtil {
    public static final String KEY_NET_SWITCH_ONE = "netsdk_normal_switch";
    public static final String KEY_NET_SWITCH_TWO = "android_network_core";
    public static final String KEY_READ_CONFIG_FROM_DB = "readConfigFromDB";
    public static final String SHARED_FILE = "sdkSharedSwitch.xml";
    public static final String SHARED_FILE_NAME = "sdkSharedSwitch";
    private static boolean a = false;
    public static SwitchChangedObserble switchChangedObserble;

    class ProxySharedSwitchChangedListener implements Observer {
        private ProxySharedSwitchChangedListener() {
        }

        public void update(Observable observable, Object data) {
            if (data == null || !(data instanceof Map)) {
                LogCatUtil.error((String) "NwSharedSwitchUtil", (String) "data is null or not a map type");
                return;
            }
            Map switchMap = (Map) data;
            String normalSwitch = (String) switchMap.get("netsdk_normal_switch");
            String coreSwitch = (String) switchMap.get("android_network_core");
            JSONObject coreSwitchJSON = JSONUtil.convertJSONObject(coreSwitch);
            JSONObject normalSwitchJSON = JSONUtil.convertJSONObject(normalSwitch);
            Map switchIdMaps = new LinkedHashMap(2);
            SwitchMonitorLogUtil.putSwitchId(coreSwitchJSON, switchIdMaps, TransportConfigureItem.SWITCH_TAG_LOG1.getConfigName());
            SwitchMonitorLogUtil.putSwitchId(normalSwitchJSON, switchIdMaps, TransportConfigureItem.SWITCH_TAG_LOG2.getConfigName());
            NwSharedSwitchUtil.a(switchIdMaps);
            NwSharedSwitchUtil.b(normalSwitch, coreSwitch);
            NwSharedSwitchUtil.processSwitchOfSwitch(normalSwitchJSON);
            NwSharedSwitchUtil.a().notifyObservers(switchMap);
        }
    }

    class SwitchChangedObserble extends Observable {
        SwitchChangedObserble() {
        }

        public void notifyObservers(Object data) {
            setChanged();
            super.notifyObservers(data);
        }
    }

    public static final void init() {
        regSwitchChangedListener();
    }

    public static synchronized String getSharedSwitch(Context context, String key) {
        String config;
        synchronized (NwSharedSwitchUtil.class) {
            config = getConfigFromDB(key);
            if (TextUtils.isEmpty(config)) {
                config = context.getSharedPreferences("sdkSharedSwitch", 4).getString(key, null);
            }
        }
        return config;
    }

    public static void refreshSharedSwitch(Context context, String sharedPrefName, String key, String value) {
        try {
            Editor ed = context.getSharedPreferences(sharedPrefName, 4).edit();
            ed.putString(key, value);
            if (ed.commit()) {
                LogCatUtil.info("NwSharedSwitchUtil", "refreshSharedSwitch commit success!");
            } else {
                LogCatUtil.info("NwSharedSwitchUtil", "refreshSharedSwitch commit fail!");
            }
        } catch (Exception ex) {
            LogCatUtil.error("NwSharedSwitchUtil", "refreshSharedSwitch, sharedPrefName=[" + sharedPrefName + "], key=[" + key + "], value=[" + value + "]", ex);
        }
        NetworkConfigDAO.getInstance().saveOrUpdateConfig(key, value);
    }

    public static synchronized Map<String, String> mergeMapsharedSwitch(Context context, String sharedPrefName, String switchKey, Map<String, String> srcMap) {
        synchronized (NwSharedSwitchUtil.class) {
            String sharedSwitch = getSharedSwitch(context, sharedPrefName, switchKey);
            if (!TextUtils.isEmpty(sharedSwitch)) {
                try {
                    JSONObject jsonObject = new JSONObject(sharedSwitch);
                    if (jsonObject.length() > 0) {
                        Iterator it = jsonObject.keys();
                        while (it.hasNext()) {
                            String key = it.next();
                            if (key != null) {
                                String value = jsonObject.optString(key);
                                if (value != null && !srcMap.containsKey(key)) {
                                    srcMap.put(key, value);
                                }
                            }
                        }
                    }
                } catch (Throwable e) {
                    LogCatUtil.error("NwSharedSwitchUtil", "mergeMapsharedSwitch fail", e);
                }
            }
        }
        return srcMap;
    }

    public static synchronized String getSharedSwitch(Context context, String sharedPrefName, String key) {
        String config;
        synchronized (NwSharedSwitchUtil.class) {
            config = getConfigFromDB(key);
            if (TextUtils.isEmpty(config)) {
                String result = context.getSharedPreferences(sharedPrefName, 4).getString(key, null);
                if (!TextUtils.isEmpty(result)) {
                    LogCatUtil.info("NwSharedSwitchUtil", "getSharedSwitch.  Get config from sp. key:" + key);
                    config = result;
                } else {
                    config = a(sharedPrefName, key);
                }
            }
        }
        return config;
    }

    protected static String getConfigFromDB(String key) {
        if (!isReadConfigFromDB()) {
            return "";
        }
        String config = NetworkConfigDAO.getInstance().getConfig(key);
        if (MiscUtils.isEmpty(config)) {
            return "";
        }
        LogCatUtil.info("NwSharedSwitchUtil", "getSharedSwitch.  Get config from db. key:" + key);
        return config;
    }

    private static String a(final String sharedPrefName, final String key) {
        try {
            String switchValue = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SWITCH_FROM_ORIGINAL);
            LogCatUtil.debug("NwSharedSwitchUtil", "switch from original value=[" + switchValue + "]");
            if (!MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), switchValue)) {
                LogCatUtil.debug("NwSharedSwitchUtil", "switch from original value=[" + switchValue + "],not hit");
                return "";
            } else if (MiscUtils.isOtherProcess(TransportEnvUtil.getContext())) {
                LogCatUtil.debug("NwSharedSwitchUtil", "it's not main process,return");
                return "";
            } else {
                LogCatUtil.debug("NwSharedSwitchUtil", "getSharedSwitch,value is null,try get from original");
                final String value = ExtTransportOffice.getInstance().getSwitchFromOriginal(key);
                if (TextUtils.isEmpty(value)) {
                    return value;
                }
                LogCatUtil.debug("NwSharedSwitchUtil", "load config from original");
                NetworkAsyncTaskExecutor.execute(new Runnable() {
                    public final void run() {
                        NwSharedSwitchUtil.refreshSharedSwitch(TransportEnvUtil.getContext(), sharedPrefName, key, value);
                        NwSharedSwitchUtil.notifySwitchUpdate();
                    }
                });
                return value;
            }
        } catch (Throwable ex) {
            LogCatUtil.error("NwSharedSwitchUtil", "getSwitchExt exception", ex);
            return "";
        }
    }

    public static final void addSwitchChangedListener(Observer observer) {
        a().addObserver(observer);
    }

    /* access modifiers changed from: private */
    public static SwitchChangedObserble a() {
        if (switchChangedObserble == null) {
            switchChangedObserble = new SwitchChangedObserble();
        }
        return switchChangedObserble;
    }

    public static void notifySwitchUpdate() {
        a().notifyObservers();
    }

    public static synchronized void removeSwitch(final Context context) {
        synchronized (NwSharedSwitchUtil.class) {
            try {
                NetworkAsyncTaskExecutor.executeIO(new Runnable() {
                    public final void run() {
                        Editor ed = context.getSharedPreferences("sdkSharedSwitch", 4).edit();
                        ed.remove("netsdk_normal_switch");
                        ed.remove("android_network_core");
                        if (ed.commit()) {
                            LogCatUtil.info("NwSharedSwitchUtil", "removeSwitch commit success!");
                        } else {
                            LogCatUtil.info("NwSharedSwitchUtil", "removeSwitch commit fail!");
                        }
                        NetworkConfigDAO.getInstance().deleteConfig("netsdk_normal_switch");
                        NetworkConfigDAO.getInstance().deleteConfig("android_network_core");
                    }
                });
            } catch (Exception ex) {
                LogCatUtil.error("NwSharedSwitchUtil", "removeSwitch exception", ex);
            }
        }
        return;
    }

    public static void regSwitchChangedListener() {
        if (!a) {
            a = true;
            try {
                Class realSharedSwitchUtilClass = Class.forName("com.alipay.mobile.common.utils.SharedSwitchUtil", true, NwSharedSwitchUtil.class.getClassLoader());
                realSharedSwitchUtilClass.getMethod("addSwitchChangedListener", new Class[]{Observer.class}).invoke(realSharedSwitchUtilClass, new Object[]{new ProxySharedSwitchChangedListener()});
                LogCatUtil.info("NwSharedSwitchUtil", "regSwitchChangedListener success.");
            } catch (Throwable e) {
                LogCatUtil.warn("NwSharedSwitchUtil", "regSwitchChangedListener fail", e);
            }
        }
    }

    public static final boolean isReadConfigFromDB() {
        boolean readConfigFromDB = true;
        String resultStr = NetworkConfigDAO.getInstance().getConfig(KEY_READ_CONFIG_FROM_DB);
        if (MiscUtils.isEmpty(resultStr)) {
            return true;
        }
        try {
            readConfigFromDB = Boolean.parseBoolean(resultStr);
            LogCatUtil.info("NwSharedSwitchUtil", "isReadConfigFromDB.  Get config result is " + readConfigFromDB);
            return readConfigFromDB;
        } catch (Throwable e) {
            NumberFormatException numberFormatException = new NumberFormatException("parseBoolean error. value:" + resultStr);
            numberFormatException.initCause(e);
            MonitorErrorLogHelper.log("NwSharedSwitchUtil", numberFormatException);
            return readConfigFromDB;
        }
    }

    public static final void putSwitchSrc(String switchSrc) {
        NetworkConfigDAO.getInstance().saveOrUpdateConfig("keySwitchSrc", switchSrc);
    }

    public static final String getSwitchSrc() {
        try {
            String switchSrc = NetworkConfigDAO.getInstance().getConfig("keySwitchSrc");
            if (!MiscUtils.isEmpty(switchSrc)) {
                return switchSrc;
            }
        } catch (Throwable e) {
            LogCatUtil.error("NwSharedSwitchUtil", "getSwitchSrc error", e);
        }
        return "unknow";
    }

    public static final void setReadConfigFromDBIfNoExist() {
        if (MiscUtils.isEmpty(NetworkConfigDAO.getInstance().getConfig(KEY_READ_CONFIG_FROM_DB))) {
            NetworkConfigDAO.getInstance().saveOrUpdateConfig(KEY_READ_CONFIG_FROM_DB, "true");
            LogCatUtil.info("NwSharedSwitchUtil", "Enter setReadConfigFromDB()");
        }
    }

    /* access modifiers changed from: private */
    public static final void b(String normalSwitch, String coreSwitch) {
        boolean saved = false;
        if (!MiscUtils.isEmpty(normalSwitch)) {
            NetworkConfigDAO.getInstance().saveOrUpdateConfig("netsdk_normal_switch", normalSwitch);
            saved = true;
        }
        if (!MiscUtils.isEmpty(coreSwitch)) {
            NetworkConfigDAO.getInstance().saveOrUpdateConfig("android_network_core", coreSwitch);
            saved = true;
        }
        if (saved) {
            putSwitchSrc("rpc");
        }
    }

    /* access modifiers changed from: private */
    public static final void a(Map<String, String> switchIdMaps) {
        if (!switchIdMaps.isEmpty()) {
            SwitchMonitorLogUtil.monitorLog(TransportEnvUtil.getContext(), (String) SwitchMonitorLogUtil.SUB_TYPE_RECV, switchIdMaps, (String) "rpc");
        }
    }

    public static void processSwitchOfSwitch(JSONObject jsonObject) {
        if (jsonObject != null) {
            processSwitchOfSwitch(jsonObject.optString(TransportConfigureItem.DB_STORAGE_SWITCH.getConfigName()));
        }
    }

    public static void processSwitchOfSwitch(String dbssValue) {
        boolean readConfigFromDB;
        if (!MiscUtils.isEmpty(dbssValue)) {
            if (TextUtils.equals(dbssValue, "-1")) {
                readConfigFromDB = false;
            } else {
                readConfigFromDB = MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), dbssValue);
            }
            NetworkConfigDAO.getInstance().saveOrUpdateConfig(KEY_READ_CONFIG_FROM_DB, String.valueOf(readConfigFromDB));
            LogCatUtil.info("NwSharedSwitchUtil", "processSwitchOfSwitch. grayscaleUtdid readConfigFromDB:" + readConfigFromDB);
        }
    }
}
