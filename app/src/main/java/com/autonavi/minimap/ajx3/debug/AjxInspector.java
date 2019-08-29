package com.autonavi.minimap.ajx3.debug;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;
import com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import com.autonavi.minimap.ajx3.util.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nanohttpd.protocols.http.HTTPSession;

public class AjxInspector {
    private static final String AJX_DB_BEGIN = "AJXDB_";
    private static final String AMAP_DB_BEGIN = "AMAP/";
    public static final String CMD_OPEN_URL = "ajx.debug.openURL";
    public static final String CMD_RELOAD_URL = "ajx.debug.reloadURL";
    public static final String DATABASE_ADD_DATABASE = "Database.addDatabase";
    public static final String DATABASE_ENABLE = "Database.enable";
    public static final String DATABASE_EXECUTESQL = "Database.executeSQL";
    public static final String DATABASE_GET_DATABASE_TABLE_NAMES = "Database.getDatabaseTableNames";
    public static final String DOMSTORAGE_GET_LOCAL_STORAGE_NAMESPACE = "DOMStorage.getLocalStorageNamespace";
    public static final String DOM_STORAGE_CLEAR = "DOMStorage.clear";
    public static final String DOM_STORAGE_DOM_STORAGE_ITEM_ADDED = "DOMStorage.domStorageItemAdded";
    public static final String DOM_STORAGE_DOM_STORAGE_ITEM_REMOVED = "DOMStorage.domStorageItemRemoved";
    public static final String DOM_STORAGE_DOM_STORAGE_ITEM_UPDATED = "DOMStorage.domStorageItemUpdated";
    public static final String DOM_STORAGE_GETDOM_STORAGE_ITEMS = "DOMStorage.getDOMStorageItems";
    public static final String DOM_STORAGE_NAMESPACE_ADDED = "DOMStorage.namespaceAdded";
    public static final String DOM_STORAGE_REMOVEDOM_STORAGE_ITEM = "DOMStorage.removeDOMStorageItem";
    public static final String DOM_STORAGE_SETDOM_STORAGE_ITEM = "DOMStorage.setDOMStorageItem";
    public static final String NETWORK_ENABLE = "Network.enable";
    public static final String NETWORK_GET_RESPONSE_BODY = "Network.getResponseBody";
    public static final String NET_CACHE_DIR;
    private static Map<String, String> contentMap = new HashMap();
    private static boolean databaseEnable = false;
    private static List<String> databaseList = new ArrayList();
    private static HashSet<String> dbNamesRegistered = new HashSet<>();
    private static Map<String, String[]> hashMap = new HashMap();
    private static Object mutex = new Object();
    private static boolean networkEnable = false;
    private static long sCurContextId = -1;
    private static final Executor singleExecutor;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().toString());
        sb.append("/autonavi/ajxhttpcache");
        NET_CACHE_DIR = sb.toString();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
        singleExecutor = threadPoolExecutor;
    }

    public static void setContextId(long j) {
        sCurContextId = j;
    }

    public static void setNetworkEnable(boolean z) {
        networkEnable = z;
        if (z) {
            sendCacheFiles();
        }
    }

    public static void setDatabaseEnable(boolean z) {
        databaseEnable = z;
        if (z) {
            synchronized (databaseList) {
                for (String sendInspectorMessage : databaseList) {
                    Ajx.getInstance().sendInspectorMessage(sCurContextId, sendInspectorMessage);
                }
                databaseList.clear();
            }
        }
    }

    private static void sendCacheFiles() {
        String[] strArr;
        synchronized (mutex) {
            for (Entry next : hashMap.entrySet()) {
                String str = (String) next.getKey();
                for (String str2 : (String[]) next.getValue()) {
                    Ajx.getInstance().sendInspectorMessage(sCurContextId, contentMap.get(str2));
                    StringBuilder sb = new StringBuilder("--AjxInspector.sendCacheFiles :key ");
                    sb.append(str);
                    sb.append(" ,value: ");
                    sb.append(contentMap.get(str2));
                }
            }
            hashMap.clear();
        }
    }

    public static boolean isDatabaseEnable() {
        return databaseEnable;
    }

    public static boolean isNetworkEnable() {
        return networkEnable;
    }

    public static void requestWillBeSend(String str, String str2, String str3, JSONObject jSONObject, String str4, String str5) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("type", "inspector");
            jSONObject2.put("method", "Network.requestWillBeSent");
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(OkApacheClient.REQUESTID, str);
            jSONObject3.put("documentURL", str2);
            jSONObject3.put("timestamp", ((double) System.currentTimeMillis()) / 1000000.0d);
            jSONObject3.put("type", str5);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("url", str2);
            jSONObject4.put("method", str3);
            if ("POST".equals(str3)) {
                jSONObject4.put("method", "POST");
                jSONObject4.put("hasPostData", true);
                jSONObject4.put(HTTPSession.POST_DATA, str4);
            }
            jSONObject4.put("headers", jSONObject);
            jSONObject3.put("request", jSONObject4);
            jSONObject2.put("params", jSONObject3);
        } catch (Exception unused) {
        }
        String jSONObject5 = jSONObject2.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("requestWillBeSend");
        String sb2 = sb.toString();
        synchronized (mutex) {
            contentMap.put(sb2, jSONObject5);
        }
    }

    public static void responseReceived(String str, String str2, int i, String str3, JSONObject jSONObject, JSONObject jSONObject2, String str4) {
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject3.put("type", "inspector");
            jSONObject3.put("method", "Network.responseReceived");
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put(OkApacheClient.REQUESTID, str);
            jSONObject4.put("timestamp", ((double) System.currentTimeMillis()) / 1000000.0d);
            jSONObject4.put("type", str4);
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put("status", i);
            jSONObject5.put("mimeType", "");
            jSONObject5.put("statusText", str3);
            jSONObject5.put("url", str2);
            if (jSONObject != null) {
                jSONObject5.put("headers", jSONObject2);
            }
            jSONObject5.put("requestHeaders", jSONObject);
            jSONObject4.put(ModuleLongLinkService.CALLBACK_KEY_RESPONSE, jSONObject5);
            jSONObject3.put("params", jSONObject4);
        } catch (Exception unused) {
        }
        String jSONObject6 = jSONObject3.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("responseReceived");
        String sb2 = sb.toString();
        synchronized (mutex) {
            contentMap.put(sb2, jSONObject6);
        }
    }

    public static void loadingFinished(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "inspector");
            jSONObject.put("method", "Network.loadingFinished");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(OkApacheClient.REQUESTID, str);
            jSONObject2.put("timestamp", ((double) System.currentTimeMillis()) / 1000000.0d);
            jSONObject.put("params", jSONObject2);
        } catch (Exception unused) {
        }
        String jSONObject3 = jSONObject.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("loadingFinished");
        contentMap.put(sb.toString(), jSONObject3);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("requestWillBeSend");
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append("responseReceived");
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append("loadingFinished");
        String[] strArr = {sb2.toString(), sb3.toString(), sb4.toString()};
        synchronized (mutex) {
            hashMap.put(str, strArr);
        }
        if (networkEnable) {
            sendCacheFiles();
        }
    }

    public static void sendRespToInspector(int i, String str) {
        StringBuilder sb = new StringBuilder("sendRespToInspector >> id: ");
        sb.append(i);
        sb.append(" reqId: ");
        sb.append(str);
        String fileContent = getFileContent(generateFileName(str));
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "inspector");
            jSONObject.put("id", i);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(Constants.BODY, fileContent);
            jSONObject2.put("base64Encoded", false);
            jSONObject.put("result", jSONObject2);
        } catch (JSONException unused) {
        }
        Ajx.getInstance().sendInspectorMessage(sCurContextId, jSONObject.toString());
    }

    public static String generateFileName(String str) {
        File file = new File(NET_CACHE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(NET_CACHE_DIR);
        sb.append("/");
        sb.append(str);
        return sb.toString();
    }

    public static void clearNetCacheDir() {
        clearDir(NET_CACHE_DIR);
        synchronized (hashMap) {
            hashMap.clear();
        }
        synchronized (contentMap) {
            contentMap.clear();
        }
        synchronized (databaseList) {
            databaseList.clear();
        }
        synchronized (dbNamesRegistered) {
            dbNamesRegistered.clear();
        }
        setNetworkEnable(false);
        setDatabaseEnable(false);
    }

    public static String getFileContent(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str));
            byte[] bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            String str2 = new String(bArr);
            try {
                fileInputStream.close();
                return str2;
            } catch (Exception unused) {
                return str2;
            }
        } catch (Exception unused2) {
            r2 = "";
            return "";
        }
    }

    public static void clearDir(final String str) {
        if (new File(str).exists()) {
            singleExecutor.execute(new Runnable() {
                public final void run() {
                    for (File delete : new File(str).listFiles()) {
                        delete.delete();
                    }
                }
            });
        }
    }

    public static void writeStrToFileByAppend(final String str, final String str2) {
        singleExecutor.execute(new Runnable() {
            public final void run() {
                try {
                    File file = new File(str);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(str2.getBytes());
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getLocalStorageNamespace(int i) {
        File[] listFiles = new File("/data/data/com.autonavi.minimap/shared_prefs").listFiles();
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < listFiles.length; i2++) {
            jSONArray.put(listFiles[i2].getName().substring(0, listFiles[i2].getName().lastIndexOf(".")));
        }
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("id", i);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("namespace", jSONArray);
            buildInspctorJSON.put("result", jSONObject);
            Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
        } catch (JSONException unused) {
        }
    }

    public static void getDOMStorageItems(int i, String str) {
        Map<String, ?> all = Ajx.getInstance().getSharedPreferences(str).getAll();
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("id", i);
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            for (Entry next : all.entrySet()) {
                JSONArray jSONArray2 = new JSONArray();
                jSONArray2.put(next.getKey()).put(String.valueOf(next.getValue()));
                jSONArray.put(jSONArray2);
            }
            jSONObject.put("entries", jSONArray);
            buildInspctorJSON.put("result", jSONObject);
            Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
        } catch (Exception unused) {
        }
    }

    public static void clearLocalStorage(int i, String str) {
        Ajx.getInstance().getSharedPreferences(str).edit().clear().apply();
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("id", i);
            buildInspctorJSON.put("result", new JSONObject());
            Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
        } catch (Exception unused) {
        }
    }

    public static void removeLocalStorage(int i, String str, String str2) {
        Ajx.getInstance().getSharedPreferences(str).edit().remove(str2).apply();
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("id", i);
            buildInspctorJSON.put("result", new JSONObject());
            Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
        } catch (Exception unused) {
        }
        domStorageItemRemoved(str, str2);
    }

    public static void setDOMStorageItem(int i, String str, String str2, String str3) {
        if (preferenceExist(str)) {
            domStorageItemUpdated(str, str2, Ajx.getInstance().getSharedPreferences(str).getString(str2, ""), str3);
        } else {
            domStorageItemAdded(str, str2, str3);
        }
        Ajx.getInstance().getSharedPreferences(str).edit().putString(str2, str3).apply();
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("id", i);
            buildInspctorJSON.put("result", new JSONObject());
            Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
        } catch (Exception unused) {
        }
    }

    private static boolean preferenceExist(String str) {
        StringBuilder sb = new StringBuilder("/data/data/");
        sb.append(Ajx.getInstance().getPackageName());
        sb.append("/shared_prefs/");
        sb.append(str);
        sb.append(".xml");
        return new File(sb.toString()).exists();
    }

    public static void namespaceAdded(String str) {
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("method", DOM_STORAGE_NAMESPACE_ADDED);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("key", str);
            buildInspctorJSON.put("params", jSONObject);
            Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
        } catch (Exception unused) {
        }
    }

    public static void domStorageItemRemoved(String str, String str2) {
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("method", DOM_STORAGE_DOM_STORAGE_ITEM_REMOVED);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("key", str2);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("securityOrigin", str);
            jSONObject2.put("isLocalStorage", true);
            jSONObject.put("storageId", jSONObject2);
            buildInspctorJSON.put("params", jSONObject);
            Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void domStorageItemUpdated(String str, String str2, String str3, String str4) {
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("method", DOM_STORAGE_DOM_STORAGE_ITEM_UPDATED);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("key", str2);
            jSONObject.put("oldValue", str3);
            jSONObject.put("newValue", str4);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("securityOrigin", str);
            jSONObject2.put("isLocalStorage", true);
            jSONObject.put("storageId", jSONObject2);
            buildInspctorJSON.put("params", jSONObject);
            Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void domStorageItemAdded(String str, String str2, String str3) {
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("method", DOM_STORAGE_DOM_STORAGE_ITEM_ADDED);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("key", str2);
            jSONObject.put("newValue", str3);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("securityOrigin", str);
            jSONObject2.put("isLocalStorage", true);
            jSONObject.put("storageId", jSONObject2);
            buildInspctorJSON.put("params", jSONObject);
            Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject buildInspctorJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", "inspector");
        return jSONObject;
    }

    public static boolean isLocalStorage(JSONObject jSONObject) {
        try {
            return jSONObject.getJSONObject("params").getJSONObject("storageId").getBoolean("isLocalStorage");
        } catch (Exception unused) {
            return false;
        }
    }

    public static String getKeyOfRemoveDOMStorageItem(JSONObject jSONObject) {
        try {
            return jSONObject.getJSONObject("params").getString("key");
        } catch (Exception unused) {
            r2 = "";
            return "";
        }
    }

    public static String getValueOfsetDOMStorageItem(JSONObject jSONObject) {
        try {
            return jSONObject.getJSONObject("params").getString("value");
        } catch (Exception unused) {
            r2 = "";
            return "";
        }
    }

    public static String getNamespace(JSONObject jSONObject) {
        try {
            return jSONObject.getJSONObject("params").getJSONObject("storageId").getString("securityOrigin");
        } catch (Exception unused) {
            r2 = "";
            return "";
        }
    }

    private static String getRealDbName(String str) {
        return (TextUtils.isEmpty(str) || str.startsWith(AMAP_DB_BEGIN)) ? str : AJX_DB_BEGIN.concat(String.valueOf(str));
    }

    public static void getDatabaseTableNames(int i, String str) {
        StringBuilder sb = new StringBuilder("/data/data/");
        sb.append(Ajx.getInstance().getPackageName());
        sb.append("/databases/");
        sb.append(getRealDbName(str));
        SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(sb.toString(), null, 0);
        JSONArray jSONArray = new JSONArray();
        Cursor rawQuery = openDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type=?", new String[]{"table"});
        while (rawQuery.moveToNext()) {
            jSONArray.put(rawQuery.getString(0));
        }
        openDatabase.close();
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("id", i);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("tableNames", jSONArray);
            buildInspctorJSON.put("result", jSONObject);
            Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void executeSQL(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder("/data/data/");
        sb.append(Ajx.getInstance().getPackageName());
        sb.append("/databases/");
        sb.append(getRealDbName(str));
        SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(sb.toString(), null, 1);
        Cursor rawQuery = openDatabase.rawQuery(str2, null);
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        for (String put : rawQuery.getColumnNames()) {
            jSONArray.put(put);
        }
        int columnCount = rawQuery.getColumnCount();
        for (int i2 = 0; i2 < 250 && rawQuery.moveToNext(); i2++) {
            for (int i3 = 0; i3 < columnCount; i3++) {
                int type = rawQuery.getType(i3);
                if (type != 4) {
                    switch (type) {
                        case 0:
                            jSONArray2.put(null);
                            break;
                        case 1:
                            jSONArray2.put(rawQuery.getLong(i3));
                            break;
                        case 2:
                            try {
                                jSONArray2.put((double) rawQuery.getFloat(i3));
                                break;
                            } catch (Exception unused) {
                                break;
                            }
                        default:
                            jSONArray2.put(rawQuery.getString(i3));
                            break;
                    }
                } else {
                    jSONArray2.put(rawQuery.getBlob(i3));
                }
            }
        }
        rawQuery.close();
        openDatabase.close();
        try {
            JSONObject buildInspctorJSON = buildInspctorJSON();
            buildInspctorJSON.put("id", i);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("columnNames", jSONArray);
            jSONObject.put("values", jSONArray2);
            buildInspctorJSON.put("result", jSONObject);
            if (databaseEnable) {
                Ajx.getInstance().sendInspectorMessage(sCurContextId, buildInspctorJSON.toString());
            } else {
                databaseList.add(buildInspctorJSON.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r0 = buildInspctorJSON();
        r0.put("method", DATABASE_ADD_DATABASE);
        r1 = new org.json.JSONObject();
        r0.put("params", r1);
        r2 = new org.json.JSONObject();
        r1.put("database", r2);
        r2.put("id", r4);
        r2.put("domain", "localhost");
        r2.put("name", r4);
        r2.put("version", "1.0");
        r4 = r0.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0052, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0053, code lost:
        r4.printStackTrace();
        r4 = "";
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void databaseAdd(java.lang.String r4, java.lang.String r5) {
        /*
            java.util.HashSet<java.lang.String> r5 = dbNamesRegistered
            monitor-enter(r5)
            java.util.HashSet<java.lang.String> r0 = dbNamesRegistered     // Catch:{ all -> 0x0072 }
            boolean r0 = r0.contains(r4)     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x000d
            monitor-exit(r5)     // Catch:{ all -> 0x0072 }
            return
        L_0x000d:
            java.util.HashSet<java.lang.String> r0 = dbNamesRegistered     // Catch:{ all -> 0x0072 }
            r0.add(r4)     // Catch:{ all -> 0x0072 }
            monitor-exit(r5)     // Catch:{ all -> 0x0072 }
            java.lang.String r5 = ""
            org.json.JSONObject r0 = buildInspctorJSON()     // Catch:{ JSONException -> 0x0052 }
            java.lang.String r1 = "method"
            java.lang.String r2 = "Database.addDatabase"
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0052 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0052 }
            r1.<init>()     // Catch:{ JSONException -> 0x0052 }
            java.lang.String r2 = "params"
            r0.put(r2, r1)     // Catch:{ JSONException -> 0x0052 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0052 }
            r2.<init>()     // Catch:{ JSONException -> 0x0052 }
            java.lang.String r3 = "database"
            r1.put(r3, r2)     // Catch:{ JSONException -> 0x0052 }
            java.lang.String r1 = "id"
            r2.put(r1, r4)     // Catch:{ JSONException -> 0x0052 }
            java.lang.String r1 = "domain"
            java.lang.String r3 = "localhost"
            r2.put(r1, r3)     // Catch:{ JSONException -> 0x0052 }
            java.lang.String r1 = "name"
            r2.put(r1, r4)     // Catch:{ JSONException -> 0x0052 }
            java.lang.String r4 = "version"
            java.lang.String r1 = "1.0"
            r2.put(r4, r1)     // Catch:{ JSONException -> 0x0052 }
            java.lang.String r4 = r0.toString()     // Catch:{ JSONException -> 0x0052 }
            goto L_0x0057
        L_0x0052:
            r4 = move-exception
            r4.printStackTrace()
            r4 = r5
        L_0x0057:
            boolean r5 = databaseEnable
            if (r5 == 0) goto L_0x0065
            com.autonavi.minimap.ajx3.Ajx r5 = com.autonavi.minimap.ajx3.Ajx.getInstance()
            long r0 = sCurContextId
            r5.sendInspectorMessage(r0, r4)
            return
        L_0x0065:
            java.util.List<java.lang.String> r0 = databaseList
            monitor-enter(r0)
            java.util.List<java.lang.String> r5 = databaseList     // Catch:{ all -> 0x006f }
            r5.add(r4)     // Catch:{ all -> 0x006f }
            monitor-exit(r0)     // Catch:{ all -> 0x006f }
            return
        L_0x006f:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x006f }
            throw r4
        L_0x0072:
            r4 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0072 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.debug.AjxInspector.databaseAdd(java.lang.String, java.lang.String):void");
    }
}
