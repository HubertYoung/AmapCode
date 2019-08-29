package com.autonavi.minimap.account.base.model;

import android.support.v4.app.NotificationCompat;
import com.alipay.mobile.mrtc.api.wwj.StreamerConstants;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.sdk.packet.d;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.taobao.wireless.security.sdk.indiekit.IndieKitDefine;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfile implements Serializable {
    public String adcode = null;
    public String avatar = null;
    public String birthday = null;
    public AccountProfileCar car = new AccountProfileCar();
    public int carLoginFlag = -1;
    public AccountProfileCarLogo carLogo = null;
    public int checkinCount = 0;
    public String cityname = null;
    public String des = null;
    public AccountProfileDevice device = new AccountProfileDevice();
    public String email = null;
    public int gender = 0;
    public int hasLoginAuto = 0;
    public int level = 0;
    public int memberLevel = 0;
    public String mobile = null;
    public String nickname = null;
    public AccountProfilePayment payment = new AccountProfilePayment();
    public ArrayList<AccountProfileProvider> providers = null;
    public String repwd = null;
    public String signature = null;
    public String source = null;
    public AccountProfileThirdCredit thirdCredit = new AccountProfileThirdCredit();
    public AccountProfileThirdDl thirdDl = new AccountProfileThirdDl();
    public String uid = null;
    public String username = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.uid = jSONObject.optString(Oauth2AccessToken.KEY_UID);
            this.username = jSONObject.optString(IndieKitDefine.SG_KEY_INDIE_KIT_USERNAME);
            this.nickname = jSONObject.optString("nickname");
            this.avatar = jSONObject.optString("avatar");
            this.gender = jSONObject.optInt("gender");
            this.birthday = jSONObject.optString("birthday");
            this.des = jSONObject.optString("description");
            this.mobile = jSONObject.optString("mobile");
            this.source = jSONObject.optString("source");
            this.email = jSONObject.optString(NotificationCompat.CATEGORY_EMAIL);
            this.adcode = jSONObject.optString(AutoJsonUtils.JSON_ADCODE);
            this.cityname = jSONObject.optString("cityname");
            this.signature = jSONObject.optString(StreamerConstants.DEFAULT_SIGNATURE);
            this.repwd = jSONObject.optString("repwd");
            JSONArray optJSONArray = jSONObject.optJSONArray("providers");
            if (optJSONArray != null) {
                this.providers = new ArrayList<>();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        AccountProfileProvider accountProfileProvider = new AccountProfileProvider();
                        accountProfileProvider.fromJson(optJSONObject);
                        this.providers.add(accountProfileProvider);
                    }
                }
            }
            this.level = jSONObject.optInt(H5PermissionManager.level);
            this.memberLevel = jSONObject.optInt("member_level");
            this.checkinCount = jSONObject.optInt("checkin_count");
            JSONObject optJSONObject2 = jSONObject.optJSONObject("car");
            if (optJSONObject2 != null) {
                AccountProfileCar accountProfileCar = new AccountProfileCar();
                accountProfileCar.fromJson(optJSONObject2);
                this.car = accountProfileCar;
            }
            JSONObject optJSONObject3 = jSONObject.optJSONObject("payment");
            if (optJSONObject3 != null) {
                AccountProfilePayment accountProfilePayment = new AccountProfilePayment();
                accountProfilePayment.fromJson(optJSONObject3);
                this.payment = accountProfilePayment;
            }
            JSONObject optJSONObject4 = jSONObject.optJSONObject("car_logo");
            if (optJSONObject4 != null) {
                this.carLogo = new AccountProfileCarLogo();
                AccountProfileCarLogo accountProfileCarLogo = new AccountProfileCarLogo();
                accountProfileCarLogo.fromJson(optJSONObject4);
                this.carLogo = accountProfileCarLogo;
            }
            JSONObject optJSONObject5 = jSONObject.optJSONObject(d.n);
            if (optJSONObject5 != null) {
                AccountProfileDevice accountProfileDevice = new AccountProfileDevice();
                accountProfileDevice.fromJson(optJSONObject5);
                this.device = accountProfileDevice;
            }
            this.hasLoginAuto = jSONObject.optInt("has_login_auto");
            JSONObject optJSONObject6 = jSONObject.optJSONObject("third_credit");
            if (optJSONObject6 != null) {
                AccountProfileThirdCredit accountProfileThirdCredit = new AccountProfileThirdCredit();
                accountProfileThirdCredit.fromJson(optJSONObject6);
                this.thirdCredit = accountProfileThirdCredit;
            }
            JSONObject optJSONObject7 = jSONObject.optJSONObject("third_dl");
            if (optJSONObject7 != null) {
                AccountProfileThirdDl accountProfileThirdDl = new AccountProfileThirdDl();
                accountProfileThirdDl.fromJson(optJSONObject7);
                this.thirdDl = accountProfileThirdDl;
            }
            if (jSONObject.has("car_login_flag")) {
                this.carLoginFlag = jSONObject.optInt("car_login_flag");
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(Oauth2AccessToken.KEY_UID, this.uid);
        jSONObject.put(IndieKitDefine.SG_KEY_INDIE_KIT_USERNAME, this.username);
        jSONObject.put("nickname", this.nickname);
        jSONObject.put("avatar", this.avatar);
        jSONObject.put("gender", this.gender);
        jSONObject.put("birthday", this.birthday);
        jSONObject.put("description", this.des);
        jSONObject.put("mobile", this.mobile);
        jSONObject.put("source", this.source);
        jSONObject.put(NotificationCompat.CATEGORY_EMAIL, this.email);
        jSONObject.put(AutoJsonUtils.JSON_ADCODE, this.adcode);
        jSONObject.put("cityname", this.cityname);
        jSONObject.put(StreamerConstants.DEFAULT_SIGNATURE, this.signature);
        jSONObject.put("repwd", this.repwd);
        JSONArray jSONArray = new JSONArray();
        if (this.providers != null && this.providers.size() > 0) {
            for (int i = 0; i < this.providers.size(); i++) {
                jSONArray.put(this.providers.get(i).toJson());
            }
        }
        jSONObject.put("providers", jSONArray);
        jSONObject.put(H5PermissionManager.level, this.level);
        jSONObject.put("member_level", this.memberLevel);
        jSONObject.put("checkin_count", this.checkinCount);
        jSONObject.put("car", this.car.toJson());
        jSONObject.put("payment", this.payment.toJson());
        if (this.carLogo != null) {
            jSONObject.put("car_logo", this.carLogo.toJson());
        }
        jSONObject.put(d.n, this.device.toJson());
        jSONObject.put("has_login_auto", this.hasLoginAuto);
        jSONObject.put("third_credit", this.thirdCredit.toJson());
        jSONObject.put("third_dl", this.thirdDl.toJson());
        if (this.carLoginFlag != -1) {
            jSONObject.put("car_login_flag", this.carLoginFlag);
        }
        return jSONObject;
    }
}
