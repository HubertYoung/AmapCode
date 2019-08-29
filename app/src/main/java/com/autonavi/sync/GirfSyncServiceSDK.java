package com.autonavi.sync;

import android.text.TextUtils;
import com.autonavi.sync.GrifSyncSDK.GirfSync;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GirfSyncServiceSDK {

    public class GirfSyncService {
        /* access modifiers changed from: private */
        public GirfSyncServiceJni mJni;

        public GirfSyncService(GirfSyncServiceJni girfSyncServiceJni) {
            this.mJni = girfSyncServiceJni;
        }

        public String getCar(String str) {
            return this.mJni.getCar(str);
        }

        public int addCar(String str, String str2, int i) {
            return this.mJni.addCar(str, str2, i);
        }

        public int updateCar(String str, String str2, String str3, String str4, int i) {
            return this.mJni.updateCar(str, str2, str3, str4, i);
        }

        public int deleteCar(String str, String str2, int i, int i2) {
            return this.mJni.deleteCar(str, str2, i, i2);
        }

        public List<String> getCarList(int i) {
            ArrayList arrayList = new ArrayList();
            if (this.mJni == null) {
                return arrayList;
            }
            String carList = this.mJni.getCarList(i);
            if (carList == null || carList.length() == 0) {
                return arrayList;
            }
            try {
                JSONArray jSONArray = new JSONArray(carList);
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    arrayList.add(jSONArray.getString(i2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        public int setOftenUsedCar(String str, String str2, int i) {
            return this.mJni.setOftenUsedCar(str, str2, i);
        }

        public String getOftenUsedCar(int i) {
            return this.mJni.getOftenUsedCar(i);
        }

        public int setFrequentAddress(String str) {
            return this.mJni.setFrequentAddress(str);
        }

        public String getFrequentAddress() {
            String frequentAddress = this.mJni.getFrequentAddress();
            if (!TextUtils.isEmpty(frequentAddress)) {
                try {
                    JSONObject jSONObject = new JSONObject(frequentAddress);
                    if (jSONObject.getInt("ret") == 0) {
                        JSONObject optJSONObject = jSONObject.optJSONObject("datas");
                        if (optJSONObject != null) {
                            JSONObject optJSONObject2 = optJSONObject.optJSONObject("106");
                            if (optJSONObject2 != null) {
                                return optJSONObject2.optString("601", null);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        public int removeFrequentAddress() {
            return this.mJni.removeFrequentAddress();
        }
    }

    public GirfSyncService createInstance(GirfSync girfSync, ICallback iCallback) {
        GirfSyncServiceJni createSyncInstance = GirfSyncServiceJni.createSyncInstance(girfSync.getJni(), iCallback);
        if (createSyncInstance == null) {
            return null;
        }
        return new GirfSyncService(createSyncInstance);
    }

    public void destroySyncInstance(GirfSyncService girfSyncService) {
        if (girfSyncService != null) {
            GirfSyncServiceJni access$0 = girfSyncService.mJni;
            if (access$0 != null) {
                GirfSyncServiceJni.destroySyncInstance(access$0);
            }
        }
    }
}
