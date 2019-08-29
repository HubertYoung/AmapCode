package com.alipay.android.phone.inside.offlinecode.storage;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.offlinecode.model.CardData;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class CardDataStorage {
    static final String TAG = "inside";
    private static CardDataStorage instance;
    private Map<String, JSONObject> cardDataByUser = null;
    private IDynamicDataStoreComponent mDynamicStore;

    private String getStoreKeyOfSelectedCardIdentify() {
        return "selectedCardIdentify";
    }

    private IDynamicDataStoreComponent getDynamicStore(Context context) {
        if (this.mDynamicStore != null) {
            LoggerFactory.f().b((String) TAG, (String) "CardDataStorage:: 000");
            return this.mDynamicStore;
        }
        LoggerFactory.f().b((String) TAG, (String) "CardDataStorage:: 001");
        try {
            this.mDynamicStore = SecurityGuardManager.getInstance(context).getDynamicDataStoreComp();
        } catch (Throwable th) {
            LoggerFactory.f().c((String) TAG, th);
        }
        LoggerFactory.f().b((String) TAG, (String) "CardDataStorage:: 002");
        return this.mDynamicStore;
    }

    public static CardDataStorage getInstance() {
        if (instance == null) {
            instance = new CardDataStorage();
        }
        return instance;
    }

    private CardDataStorage() {
    }

    public CardData getCardData(Context context, String str) {
        CardData parse = CardData.parse(getCardDataByCard(context, str));
        if (parse == null || parse.isIllegal()) {
            return null;
        }
        return parse;
    }

    public void saveCardData(Context context, CardData cardData) {
        saveCardDataByCard(context, getCardIdentify(cardData.getCardType(), cardData.getCardNo()), cardData.serializeJson());
    }

    private void saveCardDataByCard(Context context, String str, String str2) {
        JSONObject cardMapByUser = getCardMapByUser(context);
        if (cardMapByUser == null) {
            cardMapByUser = new JSONObject();
        }
        try {
            cardMapByUser.put(str, str2);
            cardMapByUser.put(getStoreKeyOfSelectedCardIdentify(), str);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) TAG, th);
        }
        saveCardMapByUser(context, cardMapByUser);
    }

    private String getCardDataByCard(Context context, String str) {
        JSONObject cardMapByUser = getCardMapByUser(context);
        if (cardMapByUser != null) {
            try {
                String optString = TextUtils.isEmpty(str) ? cardMapByUser.optString(getStoreKeyOfSelectedCardIdentify(), "") : str;
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("getCardDataByCard:");
                sb.append(str);
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(optString);
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(cardMapByUser);
                f.b((String) "cting", sb.toString());
                return cardMapByUser.optString(optString, null);
            } catch (Throwable th) {
                LoggerFactory.f().c((String) TAG, th);
            }
        }
        return null;
    }

    private void saveCardMapByUser(Context context, JSONObject jSONObject) {
        String storeKeyByUser = getStoreKeyByUser();
        if (this.cardDataByUser == null) {
            this.cardDataByUser = new HashMap();
        }
        this.cardDataByUser.put(storeKeyByUser, jSONObject);
        try {
            getDynamicStore(context).putString(storeKeyByUser, jSONObject.toString());
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "busCode", (String) "SaveCardByUserEx", th);
        }
    }

    public void clearCardByUser(Context context) {
        String storeKeyByUser = getStoreKeyByUser();
        if (this.cardDataByUser != null) {
            this.cardDataByUser.remove(storeKeyByUser);
        }
        try {
            getDynamicStore(context).removeString(storeKeyByUser);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) TAG, th);
        }
    }

    public JSONObject getCardMapByUser(Context context) {
        if (this.cardDataByUser == null) {
            this.cardDataByUser = new HashMap();
        }
        String storeKeyByUser = getStoreKeyByUser();
        if (!this.cardDataByUser.containsKey(storeKeyByUser)) {
            try {
                String string = getDynamicStore(context).getString(storeKeyByUser);
                if (!TextUtils.isEmpty(string)) {
                    this.cardDataByUser.put(storeKeyByUser, new JSONObject(string));
                }
            } catch (Throwable th) {
                LoggerFactory.f().c((String) TAG, th);
            }
        }
        return this.cardDataByUser.get(storeKeyByUser);
    }

    private String getStoreKeyByUser() {
        String e = RunningConfig.e();
        if (TextUtils.isEmpty(e)) {
            LoggerFactory.f().b((String) TAG, (String) "CardDataStorage::getDefautlCodeKey > userid empty!");
        }
        return "buscode_card_data_".concat(String.valueOf(e));
    }

    public static String getCardIdentify(String str, String str2) {
        return (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) ? str : "";
    }
}
