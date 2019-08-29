package com.autonavi.sync;

import android.text.TextUtils;
import com.autonavi.sync.beans.City;
import com.autonavi.sync.beans.GirfFavoritePoint;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.autonavi.sync.beans.JsonDataWithId;
import com.autonavi.sync.beans.JsonDatasWithType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GrifSyncSDK {

    public class GirfSync {
        /* access modifiers changed from: private */
        public GirfSyncJni mObj;

        public GirfSync(GirfSyncJni girfSyncJni) {
            this.mObj = girfSyncJni;
        }

        public int beginTransactionForChangeData() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.beginTransactionForChangeData();
        }

        public int endTransactionForChangeData() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.endTransactionForChangeData();
        }

        @Deprecated
        public List<String> getTypeIds(String str) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            String idsByType = girfSyncJni.getIdsByType(str);
            ArrayList arrayList = new ArrayList();
            try {
                JSONObject jSONObject = new JSONObject(idsByType);
                if (jSONObject.getInt("ret") == 0) {
                    JSONArray jSONArray = jSONObject.getJSONArray("datas");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        arrayList.add(jSONArray.getString(i));
                    }
                }
            } catch (Exception unused) {
            }
            return arrayList;
        }

        public int getDataCountByType(String str) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return -1;
            }
            return girfSyncJni.getDataItemCountByType(str);
        }

        private List<JsonDataWithId> generateDatasWithIdListFromJSON(JSONObject jSONObject) throws JSONException {
            if (jSONObject == null) {
                return null;
            }
            Iterator<String> keys = jSONObject.keys();
            if (!keys.hasNext()) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            while (keys.hasNext()) {
                String next = keys.next();
                arrayList.add(new JsonDataWithId(next, jSONObject.getString(next)));
            }
            return arrayList;
        }

        public JsonDatasWithType getDataItem(String str, String str2) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            String jsonData = girfSyncJni.getJsonData(str, str2);
            if (TextUtils.isEmpty(jsonData)) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(jsonData);
                if (jSONObject.getInt("ret") == 0) {
                    List<JsonDataWithId> generateDatasWithIdListFromJSON = generateDatasWithIdListFromJSON(jSONObject.getJSONObject("datas").getJSONObject(str));
                    if (generateDatasWithIdListFromJSON == null) {
                        return null;
                    }
                    return new JsonDatasWithType(str, generateDatasWithIdListFromJSON);
                }
            } catch (Exception unused) {
            }
            return null;
        }

        @Deprecated
        public List<JsonDatasWithType> getDatasByRegEx(String str, String str2) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            String jsondatasByRegEx = girfSyncJni.getJsondatasByRegEx(str, str2);
            if (TextUtils.isEmpty(jsondatasByRegEx)) {
                return null;
            }
            try {
                ArrayList arrayList = new ArrayList();
                JSONObject jSONObject = new JSONObject(jsondatasByRegEx);
                if (jSONObject.getInt("ret") == 0) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("datas");
                    Iterator<String> keys = jSONObject2.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        List<JsonDataWithId> generateDatasWithIdListFromJSON = generateDatasWithIdListFromJSON(jSONObject2.getJSONObject(next));
                        if (generateDatasWithIdListFromJSON != null) {
                            arrayList.add(new JsonDatasWithType(next, generateDatasWithIdListFromJSON));
                        }
                    }
                    return arrayList;
                }
            } catch (Exception unused) {
            }
            return null;
        }

        public int setData(String str, String str2, String str3, int i) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.setJsonData(str, str2, str3, i);
        }

        public int setDataForUser(String str, String str2, String str3, String str4) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.setJsonDataForUser(str, str2, str3, str4);
        }

        public int clearData(String str, String str2, int i) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.clearJsonData(str, str2, i);
        }

        @Deprecated
        public List<JsonDatasWithType> getDatas(List<String> list) throws JSONException {
            if (list == null || list.size() <= 0) {
                return null;
            }
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            JSONArray jSONArray = new JSONArray();
            for (String put : list) {
                jSONArray.put(put);
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("datas", jSONArray);
            try {
                JSONObject jSONObject2 = new JSONObject(girfSyncJni.getJsonDatas(jSONObject.toString()));
                if (jSONObject2.getInt("ret") == 0) {
                    ArrayList arrayList = new ArrayList();
                    JSONObject jSONObject3 = jSONObject2.getJSONObject("datas");
                    Iterator<String> keys = jSONObject3.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        List<JsonDataWithId> generateDatasWithIdListFromJSON = generateDatasWithIdListFromJSON(jSONObject3.getJSONObject(next));
                        if (generateDatasWithIdListFromJSON != null) {
                            arrayList.add(new JsonDatasWithType(next, generateDatasWithIdListFromJSON));
                        }
                    }
                    return arrayList;
                }
            } catch (Exception unused) {
            }
            return null;
        }

        public int startSync() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.doSync();
        }

        public int confirmMerge(boolean z) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.confirmMerge(z);
        }

        public int loginGuest() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.setGuestLogin();
        }

        public int loginUser(String str) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.setUserLogin(str);
        }

        public int loginGuestWithoutSync() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.setGuestLoginWithoutSync();
        }

        public int loginUserWithoutSync(String str) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.setUserLoginWithoutSync(str);
        }

        public int[] getFavoritePointIds() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return girfSyncJni.getSnapshotIdsByType("101", 0);
        }

        public GirfFavoritePoint getFavoritePointItemById(int i) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            String snaptshotItemById = girfSyncJni.getSnaptshotItemById("101", i);
            if (TextUtils.isEmpty(snaptshotItemById)) {
                return null;
            }
            return GirfFavoritePoint.makePoi(i, snaptshotItemById);
        }

        public int[] getRouteIds() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return girfSyncJni.getSnapshotIdsByType("102", 0);
        }

        public GirfFavoriteRoute getFavoriteRouteObject(int i) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            String snaptshotItemById = girfSyncJni.getSnaptshotItemById("102", i);
            if (TextUtils.isEmpty(snaptshotItemById)) {
                return null;
            }
            return GirfFavoriteRoute.makeRoute(i, snaptshotItemById);
        }

        public int[] getPoiIdsByLabel(String str) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return girfSyncJni.getPoiIdsByLabel(str);
        }

        public int[] getPoiIdsByCityCode(String str) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return girfSyncJni.getPoiIdsByCityCode(str);
        }

        public int[] getPoiIdsByCityName(String str) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return girfSyncJni.getPoiIdsByCityName(str);
        }

        public int[] getPoiIdsByClassification(String str) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return girfSyncJni.getPoiIdsByClassification(str);
        }

        public List<GirfFavoritePoint> getHomeList() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(girfSyncJni.getHomeList());
                if (jSONObject.getInt("ret") == 0) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("datas");
                    Iterator<String> keys = jSONObject2.keys();
                    ArrayList arrayList = new ArrayList();
                    boolean z = false;
                    while (keys.hasNext()) {
                        String next = keys.next();
                        arrayList.add(GirfFavoritePoint.makePoi(Integer.parseInt(next), jSONObject2.getString(next)));
                        z = true;
                    }
                    if (z) {
                        return arrayList;
                    }
                    return null;
                }
            } catch (Exception unused) {
            }
            return null;
        }

        public List<GirfFavoritePoint> getCompanyList() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(girfSyncJni.getCompanyList());
                if (jSONObject.getInt("ret") == 0) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("datas");
                    Iterator<String> keys = jSONObject2.keys();
                    ArrayList arrayList = new ArrayList();
                    boolean z = false;
                    while (keys.hasNext()) {
                        String next = keys.next();
                        arrayList.add(GirfFavoritePoint.makePoi(Integer.parseInt(next), jSONObject2.getString(next)));
                        z = true;
                    }
                    if (z) {
                        return arrayList;
                    }
                    return null;
                }
            } catch (Exception unused) {
            }
            return null;
        }

        public List<String> getCustomLabels() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return parseIdList(girfSyncJni.getCustomLabels());
        }

        public List<City> getCities() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return parseCityList(girfSyncJni.getCityCodes());
        }

        public List<String> getCityNames() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return parseCityNames(girfSyncJni.getCityNames());
        }

        public List<String> getClassifications() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return parseIdList(girfSyncJni.getClassifications());
        }

        private List<String> parseIdList(String str) {
            try {
                ArrayList arrayList = new ArrayList();
                JSONArray jSONArray = new JSONArray(str);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
                if (arrayList.size() > 0) {
                    return arrayList;
                }
                return null;
            } catch (Exception unused) {
                return null;
            }
        }

        private List<City> parseCityList(String str) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                ArrayList arrayList = new ArrayList();
                int i = 0;
                while (i < jSONArray.length()) {
                    int i2 = i + 1;
                    int i3 = i2 + 1;
                    arrayList.add(new City(jSONArray.getString(i), jSONArray.getString(i2)));
                    i = i3;
                }
                if (arrayList.size() > 0) {
                    return arrayList;
                }
                return null;
            } catch (Exception unused) {
                return null;
            }
        }

        private List<String> parseCityNames(String str) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                ArrayList arrayList = new ArrayList();
                int i = 0;
                while (i < jSONArray.length()) {
                    int i2 = i + 1;
                    arrayList.add(jSONArray.getString(i));
                    i = i2;
                }
                if (arrayList.size() > 0) {
                    return arrayList;
                }
                return null;
            } catch (Exception unused) {
                return null;
            }
        }

        public int parseIdCount(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getInt("ret") == 0) {
                    JSONArray jSONArray = jSONObject.getJSONArray("datas");
                    if (jSONArray == null || jSONArray.length() <= 0) {
                        return 0;
                    }
                    return Integer.valueOf(jSONArray.getString(0)).intValue();
                }
            } catch (Exception unused) {
            }
            return 0;
        }

        @Deprecated
        public boolean isSyncing() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return false;
            }
            return girfSyncJni.isSyncing();
        }

        @Deprecated
        public int[] getPoiIdsInScreen() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return girfSyncJni.getPoiIdsInScreen();
        }

        public List<JsonDataWithId> getDataByPoiid(String str) {
            ArrayList arrayList = new ArrayList();
            if (str == null || str.length() == 0) {
                return arrayList;
            }
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return arrayList;
            }
            String jsonDataByPoiid = girfSyncJni.getJsonDataByPoiid(str);
            if (jsonDataByPoiid == null || jsonDataByPoiid.length() == 0) {
                return arrayList;
            }
            try {
                JSONObject jSONObject = new JSONObject(jsonDataByPoiid);
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    String string = jSONObject.getString(next);
                    if (next != null && string != null && next.length() > 0 && string.length() > 0) {
                        arrayList.add(new JsonDataWithId(next, string));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        public List<String> getSearchHistory(int i) {
            ArrayList arrayList = new ArrayList();
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return arrayList;
            }
            String searchHistory = girfSyncJni.getSearchHistory(i);
            if (searchHistory == null || searchHistory.length() == 0) {
                return null;
            }
            try {
                JSONArray jSONArray = new JSONArray(searchHistory);
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    arrayList.add(jSONArray.getString(i2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        public List<String> getNaviSearchHistory(String str, int i) {
            ArrayList arrayList = new ArrayList();
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null || str == null) {
                return arrayList;
            }
            String naviSearchHistory = girfSyncJni.getNaviSearchHistory(str, i);
            if (naviSearchHistory == null || naviSearchHistory.length() == 0) {
                return null;
            }
            try {
                JSONArray jSONArray = new JSONArray(naviSearchHistory);
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    arrayList.add(jSONArray.getString(i2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        public void turnOnDebug() {
            if (this.mObj != null) {
                GirfSyncJni.turnOnDebug();
            }
        }

        public void turnOffDebug() {
            if (this.mObj != null) {
                GirfSyncJni.turnOffDebug();
            }
        }

        public String getVersion() {
            if (this.mObj == null) {
                return "";
            }
            return GirfSyncJni.getVersion();
        }

        public List<GirfFavoritePoint> getHomeListSorted() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            String homeListSorted = girfSyncJni.getHomeListSorted();
            try {
                ArrayList arrayList = new ArrayList();
                JSONArray jSONArray = new JSONArray(homeListSorted);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(GirfFavoritePoint.makePoi(i, jSONArray.getString(i)));
                }
                if (arrayList.size() > 0) {
                    return arrayList;
                }
                return null;
            } catch (Exception unused) {
            }
        }

        public List<GirfFavoritePoint> getCompanyListSorted() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            String companyListSorted = girfSyncJni.getCompanyListSorted();
            try {
                ArrayList arrayList = new ArrayList();
                JSONArray jSONArray = new JSONArray(companyListSorted);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(GirfFavoritePoint.makePoi(i, jSONArray.getString(i)));
                }
                if (arrayList.size() > 0) {
                    return arrayList;
                }
                return null;
            } catch (Exception unused) {
            }
        }

        public List<String> getCars(int i) {
            ArrayList arrayList = new ArrayList();
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return arrayList;
            }
            String cars = girfSyncJni.getCars(i);
            if (cars == null || cars.length() == 0) {
                return arrayList;
            }
            try {
                JSONArray jSONArray = new JSONArray(cars);
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    arrayList.add(jSONArray.getString(i2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        public GirfSyncJni getJni() {
            return this.mObj;
        }

        public String getPath(String str, String str2, int i) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return "";
            }
            return girfSyncJni.getPath(str, str2, i);
        }

        public int[] getTrailIds() {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            return girfSyncJni.getSnapshotIdsByType("402", 0);
        }

        public String getTrailItemById(int i) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return null;
            }
            String snaptshotItemById = girfSyncJni.getSnaptshotItemById("402", i);
            if (TextUtils.isEmpty(snaptshotItemById)) {
                return null;
            }
            return snaptshotItemById;
        }

        public int getTotalDistance(String str) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 0;
            }
            return girfSyncJni.getTotalDistance(str);
        }

        public int getTotalDuration(String str) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 0;
            }
            return girfSyncJni.getTotalDuration(str);
        }

        public int confirmMerge(String str, boolean z) {
            GirfSyncJni girfSyncJni = this.mObj;
            if (girfSyncJni == null) {
                return 1285;
            }
            return girfSyncJni.confirmMerge(str, z);
        }
    }

    static {
        GirfSyncModuleJni.emptyMethod();
    }

    public int init(String str) {
        return GirfSyncModuleJni.moduleInit(str);
    }

    public int uninit() {
        return GirfSyncModuleJni.moduleUninit();
    }

    public void test() {
        GirfSyncModuleJni.test();
    }

    public GirfSync createSyncInstance(String str, String str2, INetwork iNetwork, ICallback iCallback) {
        GirfSyncJni createSyncInstance = GirfSyncModuleJni.createSyncInstance(str, str2, iNetwork, iCallback);
        if (createSyncInstance == null) {
            return null;
        }
        return new GirfSync(createSyncInstance);
    }

    public void destroySyncInstance(GirfSync girfSync) {
        if (girfSync != null) {
            GirfSyncJni access$0 = girfSync.mObj;
            if (access$0 != null) {
                GirfSyncModuleJni.destroySyncInstance(access$0);
            }
        }
    }

    public static void turnOnDebug() {
        GirfSyncJni.turnOnDebug();
    }

    public static void turnOffDebug() {
        GirfSyncJni.turnOffDebug();
    }

    public static String getVersion() {
        return GirfSyncJni.getVersion();
    }
}
