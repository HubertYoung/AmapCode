package com.amap.bundle.drivecommon.map.db.helper;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.map.db.RdCameraCityInfoDao;
import com.amap.bundle.drivecommon.map.db.RdCameraCityInfoDao.Properties;
import com.amap.bundle.drivecommon.map.db.model.RdCameraCityInfo;
import de.greenrobot.dao.query.WhereCondition;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RdCameraCityInfoDbHelper {
    private static RdCameraCityInfoDbHelper a;
    private static Set<String> c = new HashSet();
    private RdCameraCityInfoDao b = ((RdCameraCityInfoDao) xv.b().a(RdCameraCityInfoDao.class));

    private RdCameraCityInfoDbHelper() {
    }

    public static synchronized RdCameraCityInfoDbHelper getInstance(Context context) {
        RdCameraCityInfoDbHelper rdCameraCityInfoDbHelper;
        synchronized (RdCameraCityInfoDbHelper.class) {
            if (a == null) {
                a = new RdCameraCityInfoDbHelper();
                c.add("京");
                c.add("津");
                c.add("沪");
                c.add("渝");
            }
            rdCameraCityInfoDbHelper = a;
        }
        return rdCameraCityInfoDbHelper;
    }

    public List<RdCameraCityInfo> getAll() {
        return this.b.queryBuilder().list();
    }

    public void save(RdCameraCityInfo rdCameraCityInfo) {
        this.b.insertOrReplace(rdCameraCityInfo);
    }

    public void saveAll(List<RdCameraCityInfo> list) {
        if (list != null) {
            for (RdCameraCityInfo save : list) {
                save(save);
            }
        }
    }

    public void delete(RdCameraCityInfo rdCameraCityInfo) {
        if (rdCameraCityInfo != null) {
            this.b.delete(rdCameraCityInfo);
        }
    }

    public void deleteAll() {
        this.b.deleteAll();
    }

    public RdCameraCityInfo getCityInfoByCarNumberPrefix(String str) {
        List list = this.b.queryBuilder().where(Properties.d.eq(str), new WhereCondition[0]).limit(1).list();
        if (list == null || list.size() <= 0) {
            return null;
        }
        return (RdCameraCityInfo) list.get(0);
    }

    public boolean isInSingleWordSet(String str) {
        if (TextUtils.isEmpty(str) || str.length() != 1) {
            return false;
        }
        return c.contains(str);
    }

    public static void parseCityInfoAndSave(final JSONArray jSONArray, final Context context, final sh shVar) {
        if (jSONArray == null) {
            if (shVar != null) {
                shVar.a();
            }
            return;
        }
        ahm.a(new Runnable() {
            public final void run() {
                try {
                    RdCameraCityInfoDbHelper.getInstance(context).deleteAll();
                    RdCameraCityInfo rdCameraCityInfo = new RdCameraCityInfo();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONArray jSONArray = jSONArray.getJSONObject(i).getJSONArray("cities");
                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                            JSONObject jSONObject = jSONArray.getJSONObject(i2);
                            rdCameraCityInfo.carNumberPrefix = jSONObject.optString("carNumberPrefix");
                            rdCameraCityInfo.carCodeLen = Integer.valueOf(jSONObject.optInt("carCodeLen"));
                            rdCameraCityInfo.cityId = jSONObject.optString("cityId");
                            rdCameraCityInfo.name = jSONObject.optString("name");
                            rdCameraCityInfo.cityName = jSONObject.optString("cityName");
                            rdCameraCityInfo.carOwnerLen = Integer.valueOf(jSONObject.optInt("carOwnerLen"));
                            rdCameraCityInfo.proxyEnable = Integer.valueOf(jSONObject.optInt("proxyEnable"));
                            rdCameraCityInfo.carEngineLen = Integer.valueOf(jSONObject.optInt("carEngineLen"));
                            RdCameraCityInfoDbHelper.getInstance(context).save(rdCameraCityInfo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (shVar != null) {
                    shVar.a();
                }
            }
        });
    }
}
