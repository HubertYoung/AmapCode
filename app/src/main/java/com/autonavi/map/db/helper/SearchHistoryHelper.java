package com.autonavi.map.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.map.db.TipItemDao;
import com.autonavi.map.db.TipItemDao.Properties;
import de.greenrobot.dao.query.WhereCondition;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchHistoryHelper {
    public static final int DATATYPE_POI_OFFLINE = 3;
    public static final int POIINFO_COLOR = -40960;
    public static final String S = "%##%";
    public static final String SP_KEY_merge_search_history_cloud = "merge_search_history_cloud";
    public static final String SP_NAME_search_history = "search_history";
    public static final int TYPE_BUSLINE_HISTORY = 1;
    public static final int TYPE_CINEMA_HISTORY = 5;
    public static final int TYPE_GOLF_HISTORY = 4;
    public static final int TYPE_GROUP_BUY_HISTORY = 6;
    public static final int TYPE_HIS = 0;
    public static final int TYPE_HOTEL_HISTORY = 2;
    public static final int TYPE_KEYWORD_HISTORY = 0;
    public static final int TYPE_REAL_TIME_BUS_HISTORY = 7;
    public static final int TYPE_RQBXY_SEARCH = 3;
    public static final int TYPE_TIP = 1;
    private static SearchHistoryHelper a;
    private TipItemDao b = ((TipItemDao) xv.b().a(TipItemDao.class));
    private xs c = xv.a();
    private boolean d = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HistoryType {
    }

    private SearchHistoryHelper() {
    }

    public static synchronized SearchHistoryHelper getInstance(Context context) {
        SearchHistoryHelper instance;
        synchronized (SearchHistoryHelper.class) {
            try {
                instance = getInstance();
            }
        }
        return instance;
    }

    public static synchronized SearchHistoryHelper getInstance() {
        SearchHistoryHelper searchHistoryHelper;
        synchronized (SearchHistoryHelper.class) {
            if (a == null) {
                a = new SearchHistoryHelper();
            }
            searchHistoryHelper = a;
        }
        return searchHistoryHelper;
    }

    public List<TipItem> getTipItems(int i) {
        List<TipItem> list;
        if (i != 0) {
            list = this.b.queryBuilder().where(Properties.u.eq(Integer.valueOf(i)), new WhereCondition[0]).orderDesc(Properties.t).list();
            if (list == null || list.isEmpty()) {
                return null;
            }
            for (TipItem b2 : list) {
                b(b2, false);
            }
        } else {
            List<String> P = bin.a.P();
            if (P == null || P.isEmpty()) {
                return null;
            }
            list = a(P);
        }
        return list;
    }

    public List<TipItem> getTipItems(String str, int i) {
        if (i == 0) {
            return getTipItems(i);
        }
        List<TipItem> list = this.b.queryBuilder().where(Properties.u.eq(Integer.valueOf(i)), Properties.e.eq(str)).orderDesc(Properties.t).list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (TipItem b2 : list) {
            b(b2, false);
        }
        return list;
    }

    public void saveTipItem(TipItem tipItem) {
        if (tipItem != null) {
            tipItem.funcText = "";
            a(tipItem, false);
            if (tipItem.historyType != 0) {
                try {
                    if (this.c != null) {
                        SQLiteDatabase database = this.c.getDatabase();
                        if (database != null && database.isReadOnly()) {
                            return;
                        }
                    }
                    this.b.insertOrReplace(tipItem);
                    if (this.b.count() > 20) {
                        List list = this.b.queryBuilder().where(Properties.u.eq(Integer.valueOf(tipItem.historyType)), new WhereCondition[0]).orderDesc(Properties.t).list();
                        while (list.size() > 20) {
                            this.b.delete(list.remove(list.size() - 1));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                a(tipItem);
            }
        }
    }

    public void saveTipItemFromRoute(TipItem tipItem) {
        if (tipItem != null) {
            tipItem.funcText = "";
            a(tipItem, true);
            if (this.d) {
                if (tipItem.historyType != 0) {
                    this.b.insertOrReplace(tipItem);
                    return;
                }
                a(tipItem);
            }
        }
    }

    private void a(TipItem tipItem, boolean z) {
        tipItem.inputs.clear();
        String a2 = a(tipItem.name, tipItem.historyType, z);
        if (!TextUtils.isEmpty(a2)) {
            tipItem.searchTag = a2;
            b(tipItem, false);
        }
        if (!TextUtils.isEmpty(tipItem.userInput)) {
            tipItem.addInputs(tipItem.userInput);
        }
        b(tipItem, true);
    }

    public static boolean isUserfulPoi(TipItem tipItem) {
        return !TextUtils.isEmpty(tipItem.poiid) && (tipItem.dataType == 0 || tipItem.dataType == 3) && tipItem.x > 0.0d && tipItem.y > 0.0d;
    }

    public static int idType(TipItem tipItem) {
        return tipItem.dataType;
    }

    public void deleteAll() {
        if (this.b != null) {
            this.b.deleteAll();
        }
    }

    public void deleteItem(TipItem tipItem) {
        if (tipItem != null) {
            if (tipItem.historyType != 0) {
                this.b.delete(tipItem);
                return;
            }
            String a2 = agy.a(tipItem.name);
            if (!TextUtils.isEmpty(a2)) {
                bin.a.c("301", a2, 0);
            }
        }
    }

    public void deleteRecordByHistoryType(int i) {
        if (i == 0) {
            bin.a.c("301", "", 1);
        } else if (this.b != null) {
            this.b.queryBuilder().where(Properties.u.eq(Integer.valueOf(i)), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }

    public void deleteRecordByHistoryTypeAndAdCode(String str, int i) {
        if (i == 0) {
            deleteRecordByHistoryType(i);
        } else if (this.b != null) {
            this.b.queryBuilder().where(Properties.u.eq(Integer.valueOf(i)), Properties.e.eq(str)).buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }

    private String a(String str, int i, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        List arrayList = new ArrayList();
        if (i != 0) {
            arrayList = this.b.queryBuilder().where(Properties.d.eq(str), Properties.u.eq(Integer.valueOf(i))).build().list();
        } else {
            String d2 = bin.a.d((String) "301", agy.a(str));
            try {
                if (!TextUtils.isEmpty(d2)) {
                    arrayList.add(parseItemCloudJSON(new JSONObject(d2)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (arrayList == null || arrayList.size() <= 0) {
            this.d = true;
            return null;
        }
        TipItem tipItem = (TipItem) arrayList.get(0);
        if (!z || tipItem.iconinfo <= 0) {
            if (i == 0) {
                String a2 = agy.a(tipItem.name);
                if (!TextUtils.isEmpty(a2)) {
                    bin.a.c("301", a2, 0);
                }
            } else if ("mounted".equals(Environment.getExternalStorageState())) {
                this.b.delete(tipItem);
                this.d = true;
            }
            this.d = true;
        } else {
            this.d = false;
        }
        return tipItem.searchTag;
    }

    private static void b(TipItem tipItem, boolean z) {
        if (tipItem != null) {
            int i = 0;
            if (z) {
                if (tipItem.inputs != null && !tipItem.inputs.isEmpty()) {
                    int size = tipItem.inputs.size();
                    StringBuilder sb = new StringBuilder();
                    while (i < size) {
                        sb.append(tipItem.inputs.get(i));
                        if (i < size) {
                            sb.append(S);
                        }
                        i++;
                    }
                    tipItem.searchTag = sb.toString();
                }
            } else if (!TextUtils.isEmpty(tipItem.searchTag)) {
                String str = tipItem.searchTag;
                if (str.contains(S)) {
                    String[] split = str.split(S);
                    int length = split.length;
                    while (i < length) {
                        tipItem.inputs.add(i, split[i]);
                        i++;
                    }
                    return;
                }
                tipItem.inputs.add(0, str);
            }
        }
    }

    private List<TipItem> a(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            for (String next : list) {
                if (!TextUtils.isEmpty(next)) {
                    TipItem parseItemCloudJSON = parseItemCloudJSON(new JSONObject(next));
                    if (parseItemCloudJSON != null) {
                        arrayList.add(parseItemCloudJSON);
                    }
                }
            }
        } catch (JSONException unused) {
        }
        return arrayList;
    }

    public TipItem parseItemCloudJSON(JSONObject jSONObject) {
        return bxs.b(jSONObject);
    }

    public void tryMergeOldDataAsync() {
        MapSharePreference mapSharePreference = new MapSharePreference((String) "search_history");
        if (!mapSharePreference.getBooleanValue(SP_KEY_merge_search_history_cloud, false)) {
            try {
                List<TipItem> list = this.b.queryBuilder().where(Properties.u.eq(Integer.valueOf(0)), new WhereCondition[0]).orderDesc(Properties.t).list();
                mapSharePreference.putBooleanValue(SP_KEY_merge_search_history_cloud, true);
                if (list != null) {
                    if (!list.isEmpty()) {
                        this.b.queryBuilder().where(Properties.u.eq(Integer.valueOf(0)), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
                        for (TipItem a2 : list) {
                            a(a2);
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    private static void a(TipItem tipItem) {
        if (tipItem != null) {
            JSONObject a2 = bxs.a(tipItem);
            if (a2 != null) {
                bin.a.e(agy.a(tipItem.name), a2.toString());
            }
        }
    }
}
