package com.autonavi.minimap.account.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfileThirdDl implements Serializable {
    public AccountProfileThirdDlDriving drivingLiscense = new AccountProfileThirdDlDriving();
    public ArrayList<AccountProfileThirdDlVehicle> vehicleLiscenseList = new ArrayList<>();

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            JSONObject optJSONObject = jSONObject.optJSONObject("driving_liscense");
            if (optJSONObject != null) {
                AccountProfileThirdDlDriving accountProfileThirdDlDriving = new AccountProfileThirdDlDriving();
                accountProfileThirdDlDriving.fromJson(optJSONObject);
                this.drivingLiscense = accountProfileThirdDlDriving;
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("vehicle_liscense_list");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                    if (optJSONObject2 != null) {
                        AccountProfileThirdDlVehicle accountProfileThirdDlVehicle = new AccountProfileThirdDlVehicle();
                        accountProfileThirdDlVehicle.fromJson(optJSONObject2);
                        this.vehicleLiscenseList.add(accountProfileThirdDlVehicle);
                    }
                }
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("driving_liscense", this.drivingLiscense.toJson());
        JSONArray jSONArray = new JSONArray();
        if (this.vehicleLiscenseList != null && this.vehicleLiscenseList.size() > 0) {
            for (int i = 0; i < this.vehicleLiscenseList.size(); i++) {
                jSONArray.put(this.vehicleLiscenseList.get(i).toJson());
            }
        }
        jSONObject.put("vehicle_liscense_list", jSONArray);
        return jSONObject;
    }
}
