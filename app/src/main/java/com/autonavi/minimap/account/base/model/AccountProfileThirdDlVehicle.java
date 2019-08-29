package com.autonavi.minimap.account.base.model;

import com.autonavi.miniapp.plugin.carowner.CarOwnerHelper;
import com.taobao.accs.common.Constants;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfileThirdDlVehicle implements Serializable {
    public String encodedImgMain = null;
    public String encodedImgVice = null;
    public String engineNo = null;
    public String issueDate = null;
    public String model = null;
    public String owner = null;
    public String plateNo = null;
    public String registerDate = null;
    public String vin = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.plateNo = jSONObject.optString("plate_no");
            this.owner = jSONObject.optString("owner");
            this.model = jSONObject.optString(Constants.KEY_MODEL);
            this.vin = jSONObject.optString(CarOwnerHelper.OPTIONAL_ITEM_VIN);
            this.engineNo = jSONObject.optString("engine_no");
            this.registerDate = jSONObject.optString("register_date");
            this.issueDate = jSONObject.optString("issue_date");
            this.encodedImgMain = jSONObject.optString("encoded_img_main");
            this.encodedImgVice = jSONObject.optString("encoded_img_vice");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("plate_no", this.plateNo);
        jSONObject.put("owner", this.owner);
        jSONObject.put(Constants.KEY_MODEL, this.model);
        jSONObject.put(CarOwnerHelper.OPTIONAL_ITEM_VIN, this.vin);
        jSONObject.put("engine_no", this.engineNo);
        jSONObject.put("register_date", this.registerDate);
        jSONObject.put("issue_date", this.issueDate);
        jSONObject.put("encoded_img_main", this.encodedImgMain);
        jSONObject.put("encoded_img_vice", this.encodedImgVice);
        return jSONObject;
    }
}
