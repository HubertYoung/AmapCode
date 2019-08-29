package com.taobao.agoo.control;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.common.SuperId;
import com.taobao.accs.client.ClientManager;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class AgooBindCache {
    private static final int BINDED = 2;
    private static final int BINDING = 1;
    public static final String SP_AGOO_BIND_FILE_NAME = "AGOO_BIND";
    private static final String SP_BIND_KEY = "bind_status";
    private static final String TAG = "AgooBindCache";
    private static final int UNBINDED = 4;
    private static final int UNBINDING = 3;
    private long agooLastFlushTime;
    private ConcurrentMap<String, Integer> mAgooBindStatus = new ConcurrentHashMap();
    private String mAgooBindedAlias;
    private Context mContext;

    public AgooBindCache(Context context) {
        if (context == null) {
            throw new RuntimeException("Context is null!!");
        }
        this.mContext = context.getApplicationContext();
    }

    public void onAgooRegister(String str) {
        Integer num = (Integer) this.mAgooBindStatus.get(str);
        if (num == null || num.intValue() != 2) {
            this.mAgooBindStatus.put(str, Integer.valueOf(2));
            ClientManager.saveClients(this.mContext, "AGOO_BIND", this.agooLastFlushTime, this.mAgooBindStatus);
        }
    }

    public boolean isAgooRegistered(String str) {
        if (this.mAgooBindStatus.isEmpty()) {
            restoreAgooClients();
        }
        Integer num = (Integer) this.mAgooBindStatus.get(str);
        ALog.i(TAG, "isAgooRegistered", "packageName", str, "appStatus", num, "agooBindStatus", this.mAgooBindStatus);
        return !UtilityImpl.utdidChanged("Agoo_AppStore", this.mContext) && num != null && num.intValue() == 2;
    }

    public void onAgooAliasBind(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mAgooBindedAlias = str;
        }
    }

    public void onAgooAliasUnBind() {
        this.mAgooBindedAlias = null;
    }

    public boolean isAgooAliasBinded(String str) {
        return this.mAgooBindedAlias != null && this.mAgooBindedAlias.equals(str);
    }

    private void restoreAgooClients() {
        try {
            String string = this.mContext.getSharedPreferences("AGOO_BIND", 0).getString(SP_BIND_KEY, null);
            if (TextUtils.isEmpty(string)) {
                ALog.w(TAG, "restoreAgooClients packs null return", new Object[0]);
                return;
            }
            JSONArray jSONArray = new JSONArray(string);
            this.agooLastFlushTime = jSONArray.getLong(0);
            if (System.currentTimeMillis() < this.agooLastFlushTime + 86400000) {
                for (int i = 1; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    this.mAgooBindStatus.put(jSONObject.getString(SuperId.BIT_1_MAIN_VOICE_ASSISTANT), Integer.valueOf(jSONObject.getInt("s")));
                }
                ALog.i(TAG, "restoreAgooClients", "mAgooBindStatus", this.mAgooBindStatus);
                return;
            }
            ALog.i(TAG, "restoreAgooClients expired", "agooLastFlushTime", Long.valueOf(this.agooLastFlushTime));
            this.agooLastFlushTime = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
