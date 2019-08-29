package com.alipay.mobile.beehive.plugins.utils;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.APMToolService;
import com.alipay.mobile.beehive.plugins.Constant;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PathToLocalUtil {
    public static final String TAG = "PathToLocalUtil";
    private static H5PLogger sLogger = H5PLogger.getLogger((String) TAG);

    public static Map<String, String> mapImageFilePathToLocalIds(JSONObject jObject, List<String> photoPaths, boolean compactFix) {
        Map ret = new LinkedHashMap();
        List localIds = new LinkedList();
        for (String s : photoPaths) {
            String id = GeneralUtils.covertPathToLocalId(s);
            if (!TextUtils.isEmpty(id)) {
                String idAfterPattern = H5ResourceHandlerUtil.localIdToUrl(id, "image");
                localIds.add(idAfterPattern);
                ret.put(s, idAfterPattern);
            } else {
                BeeH5PluginLogger.w(TAG, s + "covert failed!");
            }
        }
        if (!localIds.isEmpty()) {
            if (compactFix) {
                jObject.put((String) Constant.AL_MEDIA_FILES, JSON.toJSON(localIds));
            } else {
                jObject.put((String) Constant.AL_MEDIA_FILES, (Object) JSONArray.toJSONString(localIds));
                jObject.put((String) Constant.AL_MEDIA_FILES_V2, JSONArray.toJSON(localIds));
            }
        }
        return ret;
    }

    public static String encodeToLocalId(String path) {
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService != null) {
                try {
                    H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                    if (h5IpcServer != null) {
                        return h5IpcServer.encodeToLocalId(path);
                    }
                } catch (Throwable throwable) {
                    sLogger.e(throwable);
                }
            }
        } else {
            APMToolService apmToolService = (APMToolService) MicroServiceUtil.getMicroService(APMToolService.class);
            if (apmToolService != null) {
                String localId = apmToolService.encodeToLocalId(path);
                sLogger.d("localId :" + localId + " path:" + path);
                return localId;
            }
            sLogger.e((String) "apmToolService ==null ");
        }
        return null;
    }

    public static String decodeToPath(String localId) {
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) MicroServiceUtil.getMicroService(H5EventHandlerService.class);
            if (h5EventHandlerService != null) {
                try {
                    H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                    if (h5IpcServer != null) {
                        return h5IpcServer.decodeToPath(localId);
                    }
                } catch (Throwable throwable) {
                    sLogger.e(throwable);
                }
            }
        } else {
            APMToolService apmToolService = (APMToolService) MicroServiceUtil.getMicroService(APMToolService.class);
            if (apmToolService != null) {
                String path = apmToolService.decodeToPath(localId);
                sLogger.d("localId :" + localId + " path:" + path);
                return path;
            }
            sLogger.e((String) "apmToolService ==null ");
        }
        return null;
    }

    public static boolean isAPFilePath(String path, String format) {
        if (TextUtils.isEmpty(path) || !path.startsWith("https://resource/") || !path.endsWith(format)) {
            return false;
        }
        return true;
    }

    public static String getLocalIdFromAPFilePath(String apFilePath, String suffix) {
        String ret = apFilePath;
        try {
            if (TextUtils.isEmpty(apFilePath) || !apFilePath.startsWith("https://resource/") || TextUtils.isEmpty(suffix) || !apFilePath.endsWith(suffix)) {
                return ret;
            }
            Uri uri = H5UrlHelper.parseUrl(apFilePath);
            if (uri == null || TextUtils.isEmpty(uri.getPath())) {
                return ret;
            }
            String[] details = uri.getPath().replace("/", "").split("\\.");
            if (details.length > 1) {
                return details[0];
            }
            return ret;
        } catch (Throwable tr) {
            sLogger.e(tr);
            return ret;
        }
    }

    public static String decodeAbsPath(String apFilePath, String suffix) {
        String ret = apFilePath;
        try {
            if (TextUtils.isEmpty(apFilePath) || !apFilePath.startsWith("https://resource/") || TextUtils.isEmpty(suffix) || !apFilePath.endsWith(suffix)) {
                return ret;
            }
            Uri uri = H5UrlHelper.parseUrl(apFilePath);
            if (uri == null || TextUtils.isEmpty(uri.getPath())) {
                return ret;
            }
            String[] details = uri.getPath().replace("/", "").split("\\.");
            if (details.length <= 1) {
                return ret;
            }
            String localId = details[0];
            if (!TextUtils.isEmpty(localId)) {
                return decodeToPath(localId);
            }
            return ret;
        } catch (Throwable tr) {
            sLogger.e(tr);
            return ret;
        }
    }
}
