package com.autonavi.minimap.account.base.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfileThirdDlDriving implements Serializable {
    public String clazz = null;
    public String drivingLiscenseNo = null;
    public String encodedImgMain = null;
    public String encodedImgVice = null;
    public String expireDate = null;
    public String fileNo = null;
    public String name = null;
    public String validDate = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.drivingLiscenseNo = jSONObject.optString("driving_liscense_no");
            this.name = jSONObject.optString("name");
            this.clazz = jSONObject.optString("clazz");
            this.fileNo = jSONObject.optString("file_no");
            this.validDate = jSONObject.optString("valid_date");
            this.expireDate = jSONObject.optString("expire_date");
            this.encodedImgMain = jSONObject.optString("encoded_img_main");
            this.encodedImgVice = jSONObject.optString("encoded_img_vice");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("driving_liscense_no", this.drivingLiscenseNo);
        jSONObject.put("name", this.name);
        jSONObject.put("clazz", this.clazz);
        jSONObject.put("file_no", this.fileNo);
        jSONObject.put("valid_date", this.validDate);
        jSONObject.put("expire_date", this.expireDate);
        jSONObject.put("encoded_img_main", this.encodedImgMain);
        jSONObject.put("encoded_img_vice", this.encodedImgVice);
        return jSONObject;
    }
}
