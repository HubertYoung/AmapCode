package com.autonavi.minimap.account.base.model;

import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfilePayment implements Serializable {
    public ArrayList<AccountProfilePaymentTaxi> taxi = new ArrayList<>();

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            JSONArray optJSONArray = jSONObject.optJSONArray(FunctionSupportConfiger.TAXI_TAG);
            if (optJSONArray != null && optJSONArray.length() > 0) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        AccountProfilePaymentTaxi accountProfilePaymentTaxi = new AccountProfilePaymentTaxi();
                        accountProfilePaymentTaxi.fromJson(optJSONObject);
                        this.taxi.add(accountProfilePaymentTaxi);
                    }
                }
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        if (this.taxi != null && this.taxi.size() > 0) {
            for (int i = 0; i < this.taxi.size(); i++) {
                jSONArray.put(this.taxi.get(i).toJson());
            }
        }
        jSONObject.put(FunctionSupportConfiger.TAXI_TAG, jSONArray);
        return jSONObject;
    }
}
