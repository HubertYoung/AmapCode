package com.alipay.mobile.nebula.tiny;

import android.text.TextUtils;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5TinyFallBackData {
    private static final String TAG = "H5TinyFallBackData";
    public static Map<String, Map<String, byte[]>> apiPermissionByte = new ConcurrentHashMap();
    public static Map<String, Map<String, byte[]>> appConfigByte = new ConcurrentHashMap();

    public static byte[] removeApiPermission(String appId, String version) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(version)) {
            return null;
        }
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService == null) {
                return null;
            }
            try {
                H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                if (h5IpcServer == null) {
                    return null;
                }
                String data = h5IpcServer.removeApiPermission(appId, version);
                if (!TextUtils.isEmpty(data)) {
                    return data.getBytes();
                }
                return null;
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
                return null;
            }
        } else {
            Map api = apiPermissionByte.remove(appId);
            if (api != null) {
                return (byte[]) api.get(version);
            }
            return null;
        }
    }

    public static byte[] removeAppConfigByte(String appId, String version) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(version)) {
            return null;
        }
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService == null) {
                return null;
            }
            try {
                H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                if (h5IpcServer == null) {
                    return null;
                }
                String data = h5IpcServer.removeAppConfigByte(appId, version);
                if (!TextUtils.isEmpty(data)) {
                    return data.getBytes();
                }
                return null;
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
                return null;
            }
        } else {
            Map api = appConfigByte.remove(appId);
            if (api != null) {
                return (byte[]) api.get(version);
            }
            return null;
        }
    }
}
