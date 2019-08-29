package com.alipay.mobile.h5plugin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.storage.database.tinyapp.EncryptOrmliteSqliteOpenHelper;
import com.alipay.android.phone.mobilesdk.storage.database.tinyapp.SqliteOpenHelperManager;
import com.alipay.android.phone.mobilesdk.storage.database.tinyapp.TinyAppCacheModel;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.provider.H5TinyDebugProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TinyAppStoragePlugin extends H5SimplePlugin {
    public static final String CLEAR_TINY_LOCAL_STORAGE = "clearTinyLocalStorage";
    public static final String GET_TINY_LOCAL_STORAGE = "getTinyLocalStorage";
    public static final String GET_TINY_LOCAL_STORAGE_INFO = "getTinyLocalStorageInfo";
    public static final String REMOVE_TINY_LOCAL_STORAGE = "removeTinyLocalStorage";
    public static final String SEND_TINY_LOCAL_STORAGE_TO_IDE = "sendTinyLocalStorageToIDE";
    public static final String SET_TINY_LOCAL_STORAGE = "setTinyLocalStorage";
    public static final String TAG = "TinyAppStoragePlugin";
    /* access modifiers changed from: private */
    public volatile H5Page h5Page;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(SET_TINY_LOCAL_STORAGE);
        filter.addAction(GET_TINY_LOCAL_STORAGE);
        filter.addAction(REMOVE_TINY_LOCAL_STORAGE);
        filter.addAction(CLEAR_TINY_LOCAL_STORAGE);
        filter.addAction(GET_TINY_LOCAL_STORAGE_INFO);
        filter.addAction(SEND_TINY_LOCAL_STORAGE_TO_IDE);
    }

    public void onRelease() {
        this.h5Page = null;
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return false;
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext context) {
        if (!initH5Page(event)) {
            H5Log.e((String) TAG, (String) "failed to init page info.");
            context.sendError(event, Error.INVALID_PARAM);
            return false;
        }
        String action = event.getAction();
        if (SET_TINY_LOCAL_STORAGE.equals(action)) {
            LoggerFactory.getTraceLogger().info(TAG, "invoke setTinyLocalStorage");
            if (H5Utils.isMain()) {
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        try {
                            TinyAppStoragePlugin.this.setTinyLocalStorage(event, context);
                        } catch (Throwable e) {
                            H5Log.e((String) TinyAppStoragePlugin.TAG, e);
                        }
                    }
                });
            } else {
                setTinyLocalStorage(event, context);
            }
        } else if (GET_TINY_LOCAL_STORAGE.equals(action)) {
            LoggerFactory.getTraceLogger().info(TAG, "invoke getTinyLocalStorage");
            if (H5Utils.isMain()) {
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        try {
                            TinyAppStoragePlugin.this.getTinyLocalStorage(event, context);
                        } catch (Throwable e) {
                            H5Log.e((String) TinyAppStoragePlugin.TAG, e);
                        }
                    }
                });
            } else {
                getTinyLocalStorage(event, context);
            }
        } else if (REMOVE_TINY_LOCAL_STORAGE.equals(action)) {
            LoggerFactory.getTraceLogger().info(TAG, "invoke removeTinyLocalStorage");
            if (H5Utils.isMain()) {
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        try {
                            TinyAppStoragePlugin.this.removeTinyLocalStorage(event, context);
                        } catch (Throwable e) {
                            H5Log.e((String) TinyAppStoragePlugin.TAG, e);
                        }
                    }
                });
            } else {
                removeTinyLocalStorage(event, context);
            }
        } else if (CLEAR_TINY_LOCAL_STORAGE.equals(action)) {
            LoggerFactory.getTraceLogger().info(TAG, "invoke clearTinyLocalStorage");
            if (H5Utils.isMain()) {
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        try {
                            TinyAppStoragePlugin.this.clearTinyLocalStorage(event, context);
                        } catch (Throwable e) {
                            H5Log.e((String) TinyAppStoragePlugin.TAG, e);
                        }
                    }
                });
            } else {
                clearTinyLocalStorage(event, context);
            }
        } else if (GET_TINY_LOCAL_STORAGE_INFO.equals(action)) {
            LoggerFactory.getTraceLogger().info(TAG, "invoke getTinyLocalStorageInfo");
            if (H5Utils.isMain()) {
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        try {
                            TinyAppStoragePlugin.this.getTinyLocalStorageInfo(event, context);
                        } catch (Throwable e) {
                            H5Log.e((String) TinyAppStoragePlugin.TAG, e);
                        }
                    }
                });
            } else {
                getTinyLocalStorageInfo(event, context);
            }
        } else if (!SEND_TINY_LOCAL_STORAGE_TO_IDE.equals(action)) {
            return false;
        } else {
            checkIfSendStorageMsgToRemoteDebug();
        }
        return true;
    }

    private boolean initH5Page(H5Event event) {
        if (!(event.getTarget() instanceof H5Page)) {
            H5Log.w(TAG, "target not page.");
            return false;
        }
        this.h5Page = (H5Page) event.getTarget();
        return true;
    }

    private String getTinyAppId(H5Event h5Event) {
        if (h5Event == null) {
            r1 = "";
            return "";
        }
        H5Page h5Page2 = h5Event.getH5page();
        if (h5Page2 == null) {
            r1 = "";
            return "";
        }
        Bundle startParam = h5Page2.getParams();
        String appId = H5Utils.getString(startParam, (String) "parentAppId");
        String embedId = H5Utils.getString(startParam, (String) "embed_webview_flag");
        if (TextUtils.isEmpty(embedId)) {
            embedId = H5Utils.getString(startParam, (String) "MINI-PROGRAM-WEB-VIEW-TAG");
        }
        if (appIsMiniService(h5Page2)) {
            String currentAppId = H5Utils.getString(startParam, (String) "appId");
            if (TextUtils.isEmpty(currentAppId)) {
                LoggerFactory.getTraceLogger().info(TAG, "currentAppId is null, " + appId);
                return appId;
            }
            appId = appId + "_" + currentAppId;
        } else if (!TextUtils.isEmpty(embedId)) {
            appId = embedId + "_embed";
        } else if (TextUtils.isEmpty(appId)) {
            appId = H5Utils.getString(startParam, (String) "appId");
        }
        LoggerFactory.getTraceLogger().info(TAG, "appId = " + appId);
        return appId;
    }

    /* access modifiers changed from: private */
    public String getUserId() {
        String userId = TinyAppService.get().getUserId();
        if (!TextUtils.isEmpty(userId)) {
            return userId;
        }
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider != null) {
            return h5LoginProvider.getUserId();
        }
        return userId;
    }

    private Pair<String, String> getAppUserId(H5Event h5Event, H5BridgeContext bridgeContext, String method, String key) {
        String tinyAppId = getTinyAppId(h5Event);
        if (TextUtils.isEmpty(tinyAppId)) {
            JSONObject result = new JSONObject();
            result.put((String) "success", (Object) Boolean.valueOf(false));
            result.put((String) "error", (Object) Integer.valueOf(2));
            result.put((String) "errorMessage", (Object) "必填参数为空");
            bridgeContext.sendBridgeResult(result);
            log("", method, key, false, 2, "invalid appId");
            return null;
        }
        String userId = getUserId();
        if (!TextUtils.isEmpty(userId)) {
            return new Pair<>(tinyAppId, userId);
        }
        JSONObject result2 = new JSONObject();
        result2.put((String) "success", (Object) Boolean.valueOf(false));
        result2.put((String) "error", (Object) Integer.valueOf(2));
        result2.put((String) "errorMessage", (Object) "必填参数为空");
        bridgeContext.sendBridgeResult(result2);
        log(tinyAppId, method, key, false, 2, "invalid userId");
        return null;
    }

    /* access modifiers changed from: private */
    public synchronized void setTinyLocalStorage(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param.containsKey("key")) {
            String key = H5Utils.getString(param, (String) "key", (String) "");
            if (TextUtils.isEmpty(key)) {
                JSONObject result = new JSONObject();
                result.put((String) "success", (Object) Boolean.valueOf(false));
                result.put((String) "error", (Object) Integer.valueOf(2));
                result.put((String) "errorMessage", (Object) "必填参数为空");
                bridgeContext.sendBridgeResult(result);
                log("", SET_TINY_LOCAL_STORAGE, key, false, 2, "invalid param key");
            } else if (param.containsKey("data")) {
                String data = H5Utils.getString(param, (String) "data", (String) "");
                if (TextUtils.isEmpty(data)) {
                    JSONObject result2 = new JSONObject();
                    result2.put((String) "success", (Object) Boolean.valueOf(false));
                    result2.put((String) "error", (Object) Integer.valueOf(2));
                    result2.put((String) "errorMessage", (Object) "必填参数为空");
                    bridgeContext.sendBridgeResult(result2);
                    log("", SET_TINY_LOCAL_STORAGE, data, false, 2, "invalid param data");
                }
                Pair appUserId = getAppUserId(event, bridgeContext, SET_TINY_LOCAL_STORAGE, key);
                if (appUserId != null) {
                    EncryptOrmliteSqliteOpenHelper helper = SqliteOpenHelperManager.getInstance(H5Utils.getContext()).getSqliteOpenHelper((String) appUserId.first, (String) appUserId.second);
                    if (data.length() > 204800) {
                        JSONObject result3 = new JSONObject();
                        result3.put((String) "success", (Object) Boolean.valueOf(false));
                        result3.put((String) "error", (Object) Integer.valueOf(14));
                        result3.put((String) "errorMessage", (Object) "data长度超过200k");
                        bridgeContext.sendBridgeResult(result3);
                        String str = "param data length over 200k, length = " + data.length();
                        log((String) appUserId.first, SET_TINY_LOCAL_STORAGE, key, false, 14, str);
                    } else {
                        int length = key.length() + data.length();
                        try {
                            Dao dao = helper.getDao();
                            List<TinyAppCacheModel> query = dao.query(dao.queryBuilder().where().eq("key", key).prepare());
                            if (query == null) {
                                JSONObject result4 = new JSONObject();
                                result4.put((String) "success", (Object) Boolean.valueOf(false));
                                result4.put((String) "error", (Object) Integer.valueOf(18));
                                result4.put((String) "errorMessage", (Object) "查询时native异常");
                                bridgeContext.sendBridgeResult(result4);
                                log((String) appUserId.first, SET_TINY_LOCAL_STORAGE, key, false, 18, "query result returns null");
                            } else if (query.size() == 1) {
                                TinyAppCacheModel queryModel = (TinyAppCacheModel) query.get(0);
                                int reduceLength = queryModel.getKey().length() + queryModel.getValue().length();
                                if ((helper.getCurrentSize() - reduceLength) + length > 10485760) {
                                    JSONObject result5 = new JSONObject();
                                    result5.put((String) "success", (Object) Boolean.valueOf(false));
                                    result5.put((String) "error", (Object) Integer.valueOf(12));
                                    result5.put((String) "errorMessage", (Object) "数据库存储达到上限");
                                    bridgeContext.sendBridgeResult(result5);
                                    log((String) appUserId.first, SET_TINY_LOCAL_STORAGE, key, false, 12, "database reach max size,do not allow update");
                                } else {
                                    dao.delete((Collection<T>) query);
                                    insertData(bridgeContext, key, data, appUserId, helper, length, dao, -reduceLength);
                                }
                            } else if (query.size() == 0) {
                                if (helper.getCurrentSize() + length >= 10485760) {
                                    JSONObject result6 = new JSONObject();
                                    result6.put((String) "success", (Object) Boolean.valueOf(false));
                                    result6.put((String) "error", (Object) Integer.valueOf(12));
                                    result6.put((String) "errorMessage", (Object) "数据库存储达到上限");
                                    bridgeContext.sendBridgeResult(result6);
                                    log((String) appUserId.first, SET_TINY_LOCAL_STORAGE, key, false, 12, "database reach max size,do not allow insert");
                                } else {
                                    insertData(bridgeContext, key, data, appUserId, helper, length, dao, 0);
                                }
                            } else if (query.size() > 1) {
                                int reduceLength2 = 0;
                                JSONObject result7 = new JSONObject();
                                for (TinyAppCacheModel aModel : query) {
                                    reduceLength2 += aModel.getKey().length() + aModel.getValue().length();
                                }
                                if ((helper.getCurrentSize() - reduceLength2) + length > 10485760) {
                                    result7.put((String) "success", (Object) Boolean.valueOf(false));
                                    result7.put((String) "error", (Object) Integer.valueOf(12));
                                    result7.put((String) "errorMessage", (Object) "数据库存储达到上限");
                                    bridgeContext.sendBridgeResult(result7);
                                    log((String) appUserId.first, SET_TINY_LOCAL_STORAGE, key, false, 12, "database reach max size,do not allow update");
                                } else {
                                    dao.delete((Collection<T>) query);
                                    insertData(bridgeContext, key, data, appUserId, helper, length, dao, -reduceLength2);
                                }
                            }
                        } catch (SQLException e) {
                            JSONObject result8 = new JSONObject();
                            result8.put((String) "success", (Object) Boolean.valueOf(false));
                            result8.put((String) "error", (Object) Integer.valueOf(13));
                            result8.put((String) "errorMessage", (Object) "存储时native异常");
                            bridgeContext.sendBridgeResult(result8);
                            logError((String) appUserId.first, SET_TINY_LOCAL_STORAGE, key, false, 13, "exception ", e);
                        }
                    }
                }
            } else {
                JSONObject result9 = new JSONObject();
                result9.put((String) "success", (Object) Boolean.valueOf(false));
                result9.put((String) "error", (Object) Integer.valueOf(2));
                result9.put((String) "errorMessage", (Object) "必填参数为空");
                bridgeContext.sendBridgeResult(result9);
                log("", SET_TINY_LOCAL_STORAGE, "", false, 2, "invalid param data");
            }
        } else {
            JSONObject result10 = new JSONObject();
            result10.put((String) "success", (Object) Boolean.valueOf(false));
            result10.put((String) "error", (Object) Integer.valueOf(2));
            result10.put((String) "errorMessage", (Object) "必填参数为空");
            bridgeContext.sendBridgeResult(result10);
            log("", SET_TINY_LOCAL_STORAGE, "", false, 2, "invalid param key");
        }
        return;
    }

    private void insertData(H5BridgeContext bridgeContext, String key, String data, Pair<String, String> appUserId, EncryptOrmliteSqliteOpenHelper helper, int length, Dao<TinyAppCacheModel, Integer> dao, int deltaSize) {
        if (dao.createOrUpdate(new TinyAppCacheModel(key, data)).isCreated()) {
            JSONObject result = new JSONObject();
            result.put((String) "success", (Object) Boolean.valueOf(true));
            result.put((String) "error", (Object) Integer.valueOf(0));
            bridgeContext.sendBridgeResult(result);
            String str = "key = " + key + " , data = " + data;
            log((String) appUserId.first, SET_TINY_LOCAL_STORAGE, key, true, 0, str);
            helper.updateCurrentSize(length + deltaSize);
            checkIfSendStorageMsgToRemoteDebug();
            return;
        }
        JSONObject result2 = new JSONObject();
        result2.put((String) "success", (Object) Boolean.valueOf(false));
        result2.put((String) "error", (Object) Integer.valueOf(13));
        result2.put((String) "errorMessage", (Object) "存储时native异常");
        bridgeContext.sendBridgeResult(result2);
        String str2 = "key = " + key + " , data = " + data + ", isCreate returns false";
        log((String) appUserId.first, SET_TINY_LOCAL_STORAGE, key, false, 13, str2);
    }

    /* access modifiers changed from: private */
    public synchronized void getTinyLocalStorage(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param.containsKey("key")) {
            String key = H5Utils.getString(param, (String) "key", (String) "");
            if (TextUtils.isEmpty(key)) {
                JSONObject result = new JSONObject();
                result.put((String) "success", (Object) Boolean.valueOf(false));
                result.put((String) "error", (Object) Integer.valueOf(2));
                result.put((String) "errorMessage", (Object) "必填参数为空");
                bridgeContext.sendBridgeResult(result);
                log("", GET_TINY_LOCAL_STORAGE, key, false, 2, "invalid param key");
            } else {
                Pair appUserId = getAppUserId(event, bridgeContext, GET_TINY_LOCAL_STORAGE, key);
                if (appUserId != null) {
                    EncryptOrmliteSqliteOpenHelper helper = SqliteOpenHelperManager.getInstance(H5Utils.getContext()).getSqliteOpenHelper((String) appUserId.first, (String) appUserId.second);
                    try {
                        Dao<TinyAppCacheModel, Integer> dao = helper.getDao();
                        List<TinyAppCacheModel> queryResult = dao.query(dao.queryBuilder().where().eq("key", key).prepare());
                        if (queryResult == null) {
                            JSONObject result2 = new JSONObject();
                            result2.put((String) "success", (Object) Boolean.valueOf(false));
                            result2.put((String) "error", (Object) Integer.valueOf(18));
                            result2.put((String) "errorMessage", (Object) "查询时native异常");
                            bridgeContext.sendBridgeResult(result2);
                            log((String) appUserId.first, GET_TINY_LOCAL_STORAGE, key, false, 18, "query result returns null");
                        } else if (queryResult.size() == 0) {
                            JSONObject result3 = new JSONObject();
                            result3.put((String) "success", (Object) Boolean.valueOf(false));
                            result3.put((String) "error", (Object) Integer.valueOf(11));
                            result3.put((String) "errorMessage", (Object) "查无此key");
                            bridgeContext.sendBridgeResult(result3);
                            log((String) appUserId.first, GET_TINY_LOCAL_STORAGE, key, false, 11, "there is no key");
                        } else if (queryResult.size() > 1) {
                            TinyAppCacheModel model = (TinyAppCacheModel) queryResult.remove(queryResult.size() - 1);
                            int reduceLength = 0;
                            for (TinyAppCacheModel aModel : queryResult) {
                                reduceLength += aModel.getKey().length() + aModel.getValue().length();
                            }
                            dao.delete((Collection<T>) queryResult);
                            JSONObject result4 = new JSONObject();
                            result4.put((String) "success", (Object) Boolean.valueOf(true));
                            result4.put((String) "error", (Object) Integer.valueOf(0));
                            result4.put((String) "data", (Object) model.getValue());
                            bridgeContext.sendBridgeResult(result4);
                            helper.updateCurrentSize(-reduceLength);
                            log((String) appUserId.first, GET_TINY_LOCAL_STORAGE, key, true, 0, "get data success, duplicate key, return new result, delete others");
                        } else {
                            JSONObject result5 = new JSONObject();
                            result5.put((String) "success", (Object) Boolean.valueOf(true));
                            result5.put((String) "error", (Object) Integer.valueOf(0));
                            result5.put((String) "data", (Object) ((TinyAppCacheModel) queryResult.get(0)).getValue());
                            bridgeContext.sendBridgeResult(result5);
                            log((String) appUserId.first, GET_TINY_LOCAL_STORAGE, key, true, 0, "get data success");
                        }
                    } catch (Exception e) {
                        JSONObject result6 = new JSONObject();
                        result6.put((String) "success", (Object) Boolean.valueOf(false));
                        result6.put((String) "error", (Object) Integer.valueOf(18));
                        result6.put((String) "errorMessage", (Object) "查询时native异常");
                        bridgeContext.sendBridgeResult(result6);
                        logError((String) appUserId.first, GET_TINY_LOCAL_STORAGE, key, false, 18, "exception ", e);
                    }
                }
            }
        } else {
            JSONObject result7 = new JSONObject();
            result7.put((String) "success", (Object) Boolean.valueOf(false));
            result7.put((String) "error", (Object) Integer.valueOf(2));
            result7.put((String) "errorMessage", (Object) "必填参数为空");
            bridgeContext.sendBridgeResult(result7);
            log("", GET_TINY_LOCAL_STORAGE, "", false, 2, "invalid param key");
        }
        return;
    }

    /* access modifiers changed from: private */
    public synchronized void removeTinyLocalStorage(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param.containsKey("key")) {
            String key = H5Utils.getString(param, (String) "key", (String) "");
            if (TextUtils.isEmpty(key)) {
                JSONObject result = new JSONObject();
                result.put((String) "success", (Object) Boolean.valueOf(false));
                result.put((String) "error", (Object) Integer.valueOf(2));
                result.put((String) "errorMessage", (Object) "必填参数为空");
                bridgeContext.sendBridgeResult(result);
                log("", REMOVE_TINY_LOCAL_STORAGE, key, false, 2, "invalid param key");
            } else {
                Pair appUserId = getAppUserId(event, bridgeContext, REMOVE_TINY_LOCAL_STORAGE, key);
                if (appUserId != null) {
                    EncryptOrmliteSqliteOpenHelper helper = SqliteOpenHelperManager.getInstance(H5Utils.getContext()).getSqliteOpenHelper((String) appUserId.first, (String) appUserId.second);
                    try {
                        Dao<TinyAppCacheModel, Integer> dao = helper.getDao();
                        List<TinyAppCacheModel> queryResult = dao.query(dao.queryBuilder().where().eq("key", key).prepare());
                        if (queryResult == null) {
                            JSONObject result2 = new JSONObject();
                            result2.put((String) "success", (Object) Boolean.valueOf(false));
                            result2.put((String) "error", (Object) Integer.valueOf(18));
                            result2.put((String) "errorMessage", (Object) "查询时native异常");
                            bridgeContext.sendBridgeResult(result2);
                            log((String) appUserId.first, REMOVE_TINY_LOCAL_STORAGE, key, false, 18, "query result returns null");
                        } else if (queryResult.size() == 0) {
                            JSONObject result3 = new JSONObject();
                            result3.put((String) "success", (Object) Boolean.valueOf(true));
                            result3.put((String) "error", (Object) Integer.valueOf(0));
                            bridgeContext.sendBridgeResult(result3);
                            log((String) appUserId.first, REMOVE_TINY_LOCAL_STORAGE, key, true, 0, "remove success");
                        } else if (queryResult.size() > 1) {
                            int reduceLength = 0;
                            for (TinyAppCacheModel aModel : queryResult) {
                                reduceLength += aModel.getKey().length() + aModel.getValue().length();
                            }
                            dao.delete((Collection<T>) queryResult);
                            JSONObject result4 = new JSONObject();
                            result4.put((String) "success", (Object) Boolean.valueOf(true));
                            result4.put((String) "error", (Object) Integer.valueOf(0));
                            bridgeContext.sendBridgeResult(result4);
                            helper.updateCurrentSize(-reduceLength);
                            log((String) appUserId.first, REMOVE_TINY_LOCAL_STORAGE, key, true, 0, "remove success");
                        } else {
                            TinyAppCacheModel model = (TinyAppCacheModel) queryResult.get(0);
                            dao.delete(model);
                            JSONObject result5 = new JSONObject();
                            result5.put((String) "success", (Object) Boolean.valueOf(true));
                            result5.put((String) "error", (Object) Integer.valueOf(0));
                            bridgeContext.sendBridgeResult(result5);
                            helper.updateCurrentSize(-(model.getKey().length() + model.getValue().length()));
                            checkIfSendStorageMsgToRemoteDebug();
                            log((String) appUserId.first, REMOVE_TINY_LOCAL_STORAGE, key, true, 0, "remove success");
                        }
                    } catch (Exception e) {
                        JSONObject result6 = new JSONObject();
                        result6.put((String) "success", (Object) Boolean.valueOf(false));
                        result6.put((String) "error", (Object) Integer.valueOf(15));
                        result6.put((String) "errorMessage", (Object) "删除时native异常");
                        bridgeContext.sendBridgeResult(result6);
                        logError((String) appUserId.first, REMOVE_TINY_LOCAL_STORAGE, key, false, 15, "exception ", e);
                    }
                }
            }
        } else {
            JSONObject result7 = new JSONObject();
            result7.put((String) "success", (Object) Boolean.valueOf(false));
            result7.put((String) "error", (Object) Integer.valueOf(2));
            result7.put((String) "errorMessage", (Object) "必填参数为空");
            bridgeContext.sendBridgeResult(result7);
            log("", REMOVE_TINY_LOCAL_STORAGE, "", false, 2, "invalid param key");
        }
        return;
    }

    /* access modifiers changed from: private */
    public synchronized void clearTinyLocalStorage(H5Event h5Event, H5BridgeContext bridgeContext) {
        Pair appUserId = getAppUserId(h5Event, bridgeContext, CLEAR_TINY_LOCAL_STORAGE, "");
        if (appUserId != null) {
            try {
                Dao dao = SqliteOpenHelperManager.getInstance(H5Utils.getContext()).getSqliteOpenHelper((String) appUserId.first, (String) appUserId.second).getDao();
                dao.delete((Collection<T>) dao.queryForAll());
                List removed = dao.queryForAll();
                if (removed != null) {
                    if (removed.size() > 0) {
                        dao.delete((Collection<T>) removed);
                        JSONObject result = new JSONObject();
                        result.put((String) "success", (Object) Boolean.valueOf(true));
                        result.put((String) "error", (Object) Integer.valueOf(0));
                        bridgeContext.sendBridgeResult(result);
                    } else {
                        JSONObject result2 = new JSONObject();
                        result2.put((String) "success", (Object) Boolean.valueOf(true));
                        result2.put((String) "error", (Object) Integer.valueOf(0));
                        bridgeContext.sendBridgeResult(result2);
                    }
                    log((String) appUserId.first, CLEAR_TINY_LOCAL_STORAGE, "", true, 0, "clear success");
                }
                checkIfSendStorageMsgToRemoteDebug();
            } catch (Exception e) {
                JSONObject result3 = new JSONObject();
                result3.put((String) "success", (Object) Boolean.valueOf(false));
                result3.put((String) "error", (Object) Integer.valueOf(16));
                result3.put((String) "errorMessage", (Object) "清除失败");
                bridgeContext.sendBridgeResult(result3);
                logError((String) appUserId.first, CLEAR_TINY_LOCAL_STORAGE, "", false, 16, "exception ", e);
                LoggerFactory.getTraceLogger().error(TAG, "clearTinyLocalStorage exception ", e);
            }
        }
        return;
    }

    /* access modifiers changed from: private */
    public synchronized void getTinyLocalStorageInfo(H5Event event, H5BridgeContext bridgeContext) {
        Pair appUserId = getAppUserId(event, bridgeContext, GET_TINY_LOCAL_STORAGE_INFO, "");
        if (appUserId != null) {
            EncryptOrmliteSqliteOpenHelper helper = SqliteOpenHelperManager.getInstance(H5Utils.getContext()).getSqliteOpenHelper((String) appUserId.first, (String) appUserId.second);
            try {
                List allModels = helper.getDao().queryForAll();
                List allKeys = new ArrayList();
                for (int i = 0; i < allModels.size(); i++) {
                    TinyAppCacheModel model = (TinyAppCacheModel) allModels.get(i);
                    if (!model.getKey().equals(helper.getCurrentSizeKey())) {
                        allKeys.add(model.getKey());
                    }
                }
                String[] keys = (String[]) allKeys.toArray(new String[allKeys.size()]);
                float currentSize = (float) helper.getCurrentSize();
                float sizeKb = ((float) Math.round((currentSize / 1024.0f) * 100.0f)) / 100.0f;
                JSONObject result = new JSONObject();
                result.put((String) "success", (Object) Boolean.valueOf(true));
                result.put((String) "error", (Object) Integer.valueOf(0));
                result.put((String) "keys", (Object) keys);
                if (currentSize != -1.0f) {
                    result.put((String) "currentSize", (Object) Float.valueOf(sizeKb));
                }
                result.put((String) "limitSize", (Object) Integer.valueOf(10240));
                bridgeContext.sendBridgeResult(result);
                String str = "all keys = " + Arrays.toString(keys) + ", current size = " + sizeKb + "kb, limit size = 10240kb";
                log((String) appUserId.first, GET_TINY_LOCAL_STORAGE_INFO, "", true, 0, str);
            } catch (Exception e) {
                JSONObject result2 = new JSONObject();
                result2.put((String) "success", (Object) Boolean.valueOf(false));
                result2.put((String) "error", (Object) Integer.valueOf(17));
                result2.put((String) "errorMessage", (Object) "获取信息失败");
                bridgeContext.sendBridgeResult(result2);
                logError((String) appUserId.first, GET_TINY_LOCAL_STORAGE_INFO, "", false, 17, "exception ", e);
            }
        }
        return;
    }

    public static int getCurrentStorageSize(String appId, String userId) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(userId)) {
            return -1;
        }
        try {
            return SqliteOpenHelperManager.getInstance(H5Utils.getContext()).getSqliteOpenHelper(appId, userId).getCurrentSize();
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error(TAG, "getCurrentStorageSize throws exception ", t);
            return -2;
        }
    }

    private void log(String appId, String method, String key, boolean success, int error, String errorMessage) {
        LoggerFactory.getTraceLogger().info(TAG, "appId = " + appId + ", method = " + method + ", key = " + key + ", success = " + success + ", error = " + error + ", errorMessage = " + errorMessage);
    }

    private void logError(String appId, String method, String key, boolean success, int error, String errorMessage, Exception e) {
        LoggerFactory.getTraceLogger().error(TAG, "appId = " + appId + ", method = " + method + ", key = " + key + ", success = " + success + ", error = " + error + ", errorMessage = " + errorMessage, e);
    }

    /* access modifiers changed from: 0000 */
    public String getErrorMessageString(int stringId) {
        return LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(stringId);
    }

    private boolean appIsMiniService(H5Page h5Page2) {
        if (h5Page2 == null) {
            return false;
        }
        Bundle params = h5Page2.getParams();
        String tinySource = H5Utils.getString(params, (String) "tinySource");
        if (params != null) {
            params.remove("tinySource");
        }
        return "miniservice".equals(tinySource);
    }

    private void checkIfSendStorageMsgToRemoteDebug() {
        final String appId = H5Utils.getString(this.h5Page.getParams(), (String) "appId");
        final H5TinyDebugProvider provider = (H5TinyDebugProvider) H5Utils.getProvider(H5TinyDebugProvider.class.getName());
        if (provider == null) {
            return;
        }
        if (provider.isRemoteDebugConnected(appId) || provider.isVConsolePanelOpened()) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    if (TinyAppStoragePlugin.this.h5Page != null) {
                        String userId = TinyAppStoragePlugin.this.getUserId();
                        if (!TextUtils.isEmpty(userId)) {
                            EncryptOrmliteSqliteOpenHelper helper = SqliteOpenHelperManager.getInstance(H5Utils.getContext()).getSqliteOpenHelper(appId, userId);
                            try {
                                JSONObject storageInfoList = new JSONObject();
                                List allModels = helper.getDao().queryForAll();
                                for (int i = 0; i < allModels.size(); i++) {
                                    TinyAppCacheModel model = (TinyAppCacheModel) allModels.get(i);
                                    if (!model.getKey().equals(helper.getCurrentSizeKey())) {
                                        storageInfoList.put(model.getKey(), (Object) model.getValue());
                                    }
                                }
                                JSONObject data = new JSONObject();
                                data.put((String) "data", (Object) storageInfoList);
                                if (provider != null && !TextUtils.isEmpty(appId)) {
                                    provider.sendMsgToRemoteWorkerOrVConsole(appId, "tinyAppRemoteDebug_storage", data.toJSONString());
                                }
                            } catch (Exception e) {
                                H5Log.d(TinyAppStoragePlugin.TAG, "sendStorageMsgToRemoteDebug exception " + e);
                            }
                        }
                    }
                }
            });
        }
    }
}
