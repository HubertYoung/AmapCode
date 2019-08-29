package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.content.ContentValues;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule(isInUiThread = false, value = "ajx.db")
public class AjxModuleDB extends AbstractModule {
    private static final String AJX_DB_BEGIN = "AJXDB_";
    private static final String AMAP_DB_BEGIN = "AMAP/";
    private static final int CLOSE = 1029;
    private static final int DELETE = 1027;
    private static final int EXEC_SQL = 1028;
    private static int ID = 0;
    private static final int INSERT = 1025;
    public static final String MODULE_NAME = "ajx.db";
    private static final int QUERY = 1024;
    private static final int UPDATE = 1026;
    private static HandlerThread mHandleThread;
    private static Handler mHandler;
    private static OnDatabaseOpListener onDatabaseOpListener;
    private final Map<String, DBHelper> mDBHelperMap = new ConcurrentHashMap();

    static class AjxDbModel {
        @Nullable
        JsFunctionCallback mCallback;
        String mColumnsJson;
        DBHelper mDbHelper;
        String mSelection;
        String mSelectionArgsJson;
        String mSql;
        String mTable;

        AjxDbModel(int i, DBHelper dBHelper, String str, String str2, String str3, String str4, @Nullable JsFunctionCallback jsFunctionCallback) {
            this.mDbHelper = dBHelper;
            this.mTable = str;
            this.mColumnsJson = str2;
            this.mSelection = str3;
            this.mSelectionArgsJson = str4;
            this.mCallback = jsFunctionCallback;
        }

        AjxDbModel(int i, DBHelper dBHelper, String str, @Nullable JsFunctionCallback jsFunctionCallback) {
            this.mDbHelper = dBHelper;
            this.mSql = str;
            this.mCallback = jsFunctionCallback;
        }
    }

    public interface OnDatabaseOpListener {
        void onDatabaseOpend(String str, String str2);
    }

    public AjxModuleDB(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "openDatabase")
    public String openDatabase(String str, @Nullable Integer num, @Nullable String str2) {
        if (str == null) {
            return null;
        }
        if (num == null) {
            num = Integer.valueOf(1);
        }
        DBHelper dBHelper = new DBHelper(getNativeContext(), !str.startsWith(AMAP_DB_BEGIN) ? AJX_DB_BEGIN.concat(String.valueOf(str)) : str, num.intValue(), str2);
        StringBuilder sb = new StringBuilder();
        int i = ID + 1;
        ID = i;
        sb.append(i);
        String sb2 = sb.toString();
        this.mDBHelperMap.put(sb2, dBHelper);
        if (this.mDBHelperMap.size() == 1) {
            initHandleThread();
        }
        if (LogManager.logOpen && onDatabaseOpListener != null) {
            onDatabaseOpListener.onDatabaseOpend(str, sb2);
        }
        return sb2;
    }

    private void initHandleThread() {
        HandlerThread handlerThread = new HandlerThread("moduleDB");
        mHandleThread = handlerThread;
        handlerThread.start();
        mHandler = new Handler(mHandleThread.getLooper()) {
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1024:
                        AjxModuleDB.this.query((AjxDbModel) message.obj);
                        return;
                    case 1025:
                        AjxModuleDB.this.insert((AjxDbModel) message.obj);
                        return;
                    case AjxModuleDB.UPDATE /*1026*/:
                        AjxModuleDB.this.update((AjxDbModel) message.obj);
                        return;
                    case AjxModuleDB.DELETE /*1027*/:
                        AjxModuleDB.this.delete((AjxDbModel) message.obj);
                        return;
                    case AjxModuleDB.EXEC_SQL /*1028*/:
                        AjxModuleDB.this.execSQL((AjxDbModel) message.obj);
                        return;
                    case AjxModuleDB.CLOSE /*1029*/:
                        AjxModuleDB.this.closeDB((String) message.obj);
                        break;
                }
            }
        };
    }

    @AjxMethod(invokeMode = "sync", value = "query")
    public void query(String str, String str2, String str3, String str4, String str5, JsFunctionCallback jsFunctionCallback) {
        if (str2 != null) {
            DBHelper dBHelper = this.mDBHelperMap.get(str);
            if (dBHelper != null) {
                Message obtain = Message.obtain();
                obtain.what = 1024;
                AjxDbModel ajxDbModel = new AjxDbModel(1024, dBHelper, str2, str3, str4, str5, jsFunctionCallback);
                obtain.obj = ajxDbModel;
                mHandler.sendMessage(obtain);
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "insert")
    public void insert(String str, String str2, String str3, JsFunctionCallback jsFunctionCallback) {
        if (str2 != null && str3 != null) {
            DBHelper dBHelper = this.mDBHelperMap.get(str);
            if (dBHelper != null) {
                Message obtain = Message.obtain();
                obtain.what = 1025;
                AjxDbModel ajxDbModel = new AjxDbModel(1025, dBHelper, str2, str3, null, null, jsFunctionCallback);
                obtain.obj = ajxDbModel;
                mHandler.sendMessage(obtain);
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "update")
    public void update(String str, String str2, String str3, String str4, String str5, JsFunctionCallback jsFunctionCallback) {
        if (str2 != null) {
            DBHelper dBHelper = this.mDBHelperMap.get(str);
            if (dBHelper != null) {
                Message obtain = Message.obtain();
                obtain.what = UPDATE;
                AjxDbModel ajxDbModel = new AjxDbModel(UPDATE, dBHelper, str2, str3, str4, str5, jsFunctionCallback);
                obtain.obj = ajxDbModel;
                mHandler.sendMessage(obtain);
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "delete")
    public void delete(String str, String str2, String str3, String str4, JsFunctionCallback jsFunctionCallback) {
        if (str2 != null) {
            DBHelper dBHelper = this.mDBHelperMap.get(str);
            if (dBHelper != null) {
                Message obtain = Message.obtain();
                obtain.what = DELETE;
                AjxDbModel ajxDbModel = new AjxDbModel(DELETE, dBHelper, str2, null, str3, str4, jsFunctionCallback);
                obtain.obj = ajxDbModel;
                mHandler.sendMessage(obtain);
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "execSQL")
    public void execSQL(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str2)) {
            DBHelper dBHelper = this.mDBHelperMap.get(str);
            if (dBHelper != null) {
                Message obtain = Message.obtain();
                obtain.what = EXEC_SQL;
                obtain.obj = new AjxDbModel(EXEC_SQL, dBHelper, str2, jsFunctionCallback);
                mHandler.sendMessage(obtain);
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "close")
    public void close(String str) {
        if (!TextUtils.isEmpty(str) && this.mDBHelperMap.get(str) != null) {
            Message obtain = Message.obtain();
            obtain.what = CLOSE;
            obtain.obj = str;
            mHandler.sendMessage(obtain);
        }
    }

    /* access modifiers changed from: private */
    public void closeDB(String str) {
        DBHelper dBHelper = this.mDBHelperMap.get(str);
        if (dBHelper != null) {
            dBHelper.close();
        }
        if (this.mDBHelperMap.isEmpty()) {
            mHandleThread.quit();
        }
    }

    public void destroy() {
        for (Entry<String, DBHelper> value : this.mDBHelperMap.entrySet()) {
            ((DBHelper) value.getValue()).close();
        }
    }

    public static synchronized void destroyHandleThread() {
        synchronized (AjxModuleDB.class) {
            mHandler = null;
            if (mHandleThread != null) {
                mHandleThread.quit();
                mHandleThread = null;
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void query(@android.support.annotation.NonNull com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleDB.AjxDbModel r14) {
        /*
            r13 = this;
            r0 = 0
            r1 = 0
            java.lang.String r2 = r14.mColumnsJson     // Catch:{ JSONException -> 0x004a }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ JSONException -> 0x004a }
            if (r2 != 0) goto L_0x0027
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ JSONException -> 0x004a }
            java.lang.String r3 = r14.mColumnsJson     // Catch:{ JSONException -> 0x004a }
            r2.<init>(r3)     // Catch:{ JSONException -> 0x004a }
            int r3 = r2.length()     // Catch:{ JSONException -> 0x004a }
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ JSONException -> 0x004a }
            r4 = 0
        L_0x0018:
            int r5 = r2.length()     // Catch:{ JSONException -> 0x0047 }
            if (r4 >= r5) goto L_0x0028
            java.lang.String r5 = r2.optString(r4)     // Catch:{ JSONException -> 0x0047 }
            r3[r4] = r5     // Catch:{ JSONException -> 0x0047 }
            int r4 = r4 + 1
            goto L_0x0018
        L_0x0027:
            r3 = r0
        L_0x0028:
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ JSONException -> 0x0047 }
            java.lang.String r4 = r14.mSelectionArgsJson     // Catch:{ JSONException -> 0x0047 }
            r2.<init>(r4)     // Catch:{ JSONException -> 0x0047 }
            int r4 = r2.length()     // Catch:{ JSONException -> 0x0047 }
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ JSONException -> 0x0047 }
            r0 = 0
        L_0x0036:
            int r5 = r2.length()     // Catch:{ JSONException -> 0x0045 }
            if (r0 >= r5) goto L_0x0050
            java.lang.String r5 = r2.optString(r0)     // Catch:{ JSONException -> 0x0045 }
            r4[r0] = r5     // Catch:{ JSONException -> 0x0045 }
            int r0 = r0 + 1
            goto L_0x0036
        L_0x0045:
            r2 = move-exception
            goto L_0x004d
        L_0x0047:
            r2 = move-exception
            r4 = r0
            goto L_0x004d
        L_0x004a:
            r2 = move-exception
            r3 = r0
            r4 = r3
        L_0x004d:
            r2.printStackTrace()
        L_0x0050:
            r7 = r3
            r9 = r4
            com.autonavi.minimap.ajx3.modules.internalmodules.DBHelper r5 = r14.mDbHelper
            java.lang.String r6 = r14.mTable
            java.lang.String r8 = r14.mSelection
            r10 = 0
            r11 = 0
            r12 = 0
            java.lang.String r0 = r5.query(r6, r7, r8, r9, r10, r11, r12)
            com.autonavi.minimap.ajx3.core.JsFunctionCallback r2 = r14.mCallback
            if (r2 == 0) goto L_0x0077
            com.autonavi.minimap.ajx3.core.JsFunctionCallback r14 = r14.mCallback
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r1] = r0
            r3 = 1
            if (r0 == 0) goto L_0x006e
            r1 = 1
        L_0x006e:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r1)
            r2[r3] = r0
            r14.callback(r2)
        L_0x0077:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleDB.query(com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleDB$AjxDbModel):void");
    }

    /* access modifiers changed from: private */
    public void insert(@NonNull AjxDbModel ajxDbModel) {
        try {
            ContentValues contentValues = new ContentValues();
            JSONObject jSONObject = new JSONObject(ajxDbModel.mColumnsJson);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String obj = keys.next().toString();
                if (!TextUtils.isEmpty(obj)) {
                    contentValues.put(obj, jSONObject.getString(obj));
                }
            }
            long insert = ajxDbModel.mDbHelper.insert(ajxDbModel.mTable, contentValues);
            if (ajxDbModel.mCallback != null) {
                JsFunctionCallback jsFunctionCallback = ajxDbModel.mCallback;
                boolean z = true;
                Object[] objArr = new Object[1];
                if (insert == -1) {
                    z = false;
                }
                objArr[0] = Boolean.valueOf(z);
                jsFunctionCallback.callback(objArr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void update(@NonNull AjxDbModel ajxDbModel) {
        try {
            JSONArray jSONArray = new JSONArray(ajxDbModel.mSelectionArgsJson);
            String[] strArr = new String[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                strArr[i] = jSONArray.optString(i);
            }
            JSONObject jSONObject = new JSONObject(ajxDbModel.mColumnsJson);
            ContentValues contentValues = new ContentValues();
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String obj = keys.next().toString();
                if (!TextUtils.isEmpty(obj)) {
                    contentValues.put(obj, jSONObject.getString(obj));
                }
            }
            int update = ajxDbModel.mDbHelper.update(ajxDbModel.mTable, contentValues, ajxDbModel.mSelection, strArr);
            if (ajxDbModel.mCallback != null) {
                JsFunctionCallback jsFunctionCallback = ajxDbModel.mCallback;
                boolean z = true;
                Object[] objArr = new Object[1];
                if (update == 0) {
                    z = false;
                }
                objArr[0] = Boolean.valueOf(z);
                jsFunctionCallback.callback(objArr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void delete(@NonNull AjxDbModel ajxDbModel) {
        try {
            JSONArray jSONArray = new JSONArray(ajxDbModel.mSelectionArgsJson);
            String[] strArr = new String[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                strArr[i] = jSONArray.optString(i);
            }
            int delete = ajxDbModel.mDbHelper.delete(ajxDbModel.mTable, ajxDbModel.mSelection, strArr);
            if (ajxDbModel.mCallback != null) {
                JsFunctionCallback jsFunctionCallback = ajxDbModel.mCallback;
                boolean z = true;
                Object[] objArr = new Object[1];
                if (delete == 0) {
                    z = false;
                }
                objArr[0] = Boolean.valueOf(z);
                jsFunctionCallback.callback(objArr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void execSQL(@NonNull AjxDbModel ajxDbModel) {
        boolean execSQL = ajxDbModel.mDbHelper.execSQL(ajxDbModel.mSql);
        if (ajxDbModel.mCallback != null) {
            ajxDbModel.mCallback.callback(Boolean.valueOf(execSQL));
        }
    }

    public static void setOnDatabaseOpListener(OnDatabaseOpListener onDatabaseOpListener2) {
        onDatabaseOpListener = onDatabaseOpListener2;
    }
}
