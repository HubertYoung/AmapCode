package com.alipay.android.phone.inside.offlinecode.model;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.rpc.response.base.OfflineDataInfo;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;

public class CardData {
    public BehaviorRecord behaviorRecord;
    public String cardConfig;
    public String cardData;
    public String cardDetail;
    public String cardIdentify;

    public CardData() {
    }

    public CardData(String str) {
        this.cardIdentify = str;
    }

    public boolean isIllegal() {
        return TextUtils.isEmpty(this.cardDetail) || TextUtils.isEmpty(this.cardData);
    }

    public static CardData parse(String str) {
        CardData cardData2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("cardIdentify", "");
            String optString2 = jSONObject.optString("cardDetail", "");
            String optString3 = jSONObject.optString("cardData", "");
            String optString4 = jSONObject.optString("cardConfig", "");
            String optString5 = jSONObject.optString("behaviorRecord", "");
            BehaviorRecord parse = !TextUtils.isEmpty(optString5) ? BehaviorRecord.parse(optString5) : null;
            cardData2 = new CardData();
            try {
                cardData2.cardIdentify = optString;
                cardData2.cardDetail = optString2;
                cardData2.cardData = optString3;
                cardData2.cardConfig = optString4;
                cardData2.behaviorRecord = parse;
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            cardData2 = null;
            LoggerFactory.f().c((String) "inside", th);
            return cardData2;
        }
        return cardData2;
    }

    public String getCardType() {
        if (TextUtils.isEmpty(this.cardDetail)) {
            return "";
        }
        try {
            return new JSONObject(this.cardDetail).optString("cardType", "");
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return "";
        }
    }

    public String getCardNo() {
        if (TextUtils.isEmpty(this.cardDetail)) {
            return "";
        }
        try {
            JSONArray optJSONArray = new JSONObject(this.cardDetail).optJSONArray("cardModels");
            if (optJSONArray == null || optJSONArray.length() <= 0) {
                return "";
            }
            return ((JSONObject) optJSONArray.get(0)).optString("cardNo", "");
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return "";
        }
    }

    public void refreshUpdateTime() {
        getBehaviorRecord().modifyTime = System.currentTimeMillis();
    }

    public void resetMgTimes() {
        getBehaviorRecord().generateMgTimes = 0;
        getBehaviorRecord().checkMgTime = System.currentTimeMillis();
    }

    public void resetOgTimes() {
        getBehaviorRecord().generateOgTimes = 0;
        getBehaviorRecord().checkOgTime = System.currentTimeMillis();
    }

    public void incGenerateTimes() {
        BehaviorRecord behaviorRecord2 = getBehaviorRecord();
        behaviorRecord2.generateMgTimes++;
        BehaviorRecord behaviorRecord3 = getBehaviorRecord();
        behaviorRecord3.generateOgTimes++;
    }

    public String serializeJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("cardIdentify", this.cardIdentify);
            jSONObject.put("cardDetail", this.cardDetail);
            jSONObject.put("cardData", this.cardData);
            jSONObject.put("cardConfig", this.cardConfig);
            if (this.behaviorRecord != null) {
                jSONObject.put("behaviorRecord", this.behaviorRecord.serializeJson());
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject.toString();
    }

    public BehaviorRecord getBehaviorRecord() {
        if (this.behaviorRecord == null) {
            this.behaviorRecord = new BehaviorRecord();
        }
        return this.behaviorRecord;
    }

    public JSONObject getJSONCardDetail() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(this.cardDetail)) {
                return new JSONObject(this.cardDetail);
            }
            return jSONObject;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return jSONObject;
        }
    }

    public JSONObject getJSONCardConfig() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(this.cardConfig)) {
                return new JSONObject(this.cardConfig);
            }
            return jSONObject;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return jSONObject;
        }
    }

    public void setCardConfig(String str) {
        this.cardConfig = str;
    }

    public int getCardConfig(String str) {
        try {
            return new JSONObject(this.cardConfig).optInt(str, -1);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return -1;
        }
    }

    public void setCardData(String str) {
        this.cardData = str;
    }

    public OfflineDataInfo getOfflineData() {
        return OfflineDataInfo.parse(this.cardData);
    }

    public long getExpireTime() {
        OfflineDataInfo offlineData = getOfflineData();
        if (offlineData != null) {
            return offlineData.expireTime;
        }
        return -1;
    }

    public String getJumpWalletUrl() {
        String format = String.format("/www/offline_qrcode.html?cardType=%s&source=ALIPAY_INSIDE", new Object[]{getCardType()});
        try {
            StringBuilder sb = new StringBuilder("alipays://platformapi/startapp?appId=60000098&url=");
            sb.append(URLEncoder.encode(format, "utf-8"));
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "BusJumpWalletUrlEx", (Throwable) e);
            r0 = "";
            return "";
        }
    }
}
