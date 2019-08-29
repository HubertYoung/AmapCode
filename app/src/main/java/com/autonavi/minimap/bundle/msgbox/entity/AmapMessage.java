package com.autonavi.minimap.bundle.msgbox.entity;

import android.text.TextUtils;
import com.autonavi.map.db.model.Msgbox;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class AmapMessage extends AmapMessageModel implements Cloneable {
    public static final int INVALID_TAG_VAL = -1;
    public static final int PRIORITY_A = 500;
    public static final int PRIORITY_A0 = 100;
    public static final int PRIORITY_A1 = 300;
    public static final int PRIORITY_A2 = 500;
    public static final String TOKEN_CLOUD_SYNC_DIALOG = "cloud_sync_dialog";
    public static final String TOKEN_DOWNLOAD_SEAR_MAP = "downloadSeatMap";
    public static final String TOKEN_OFFLINE_DOWN_CITY = "offlineDownCity";
    public static final String TOKEN_REAL3D = "real3d";
    public static final String TOKEN_TAOBAO_LOGIN = "taobaoLogin";
    public static final String TOKEN_TRAVEL = "travel";
    public static final String TOKEN_UPDATE_APP = "appUpdate";
    public static final String TOKEN_UPDATE_OFFLINE_MAP = "updateOfflineMap";
    public static final String TYPE_ACTIVITY = "type_activity";
    public static final String TYPE_MSG = "type_msg";
    public static final int TYPE_MSG_TAG_DEFAULT = 0;
    public String adcode;
    public boolean bannerUpdated = true;
    public String baricon = "";
    public String creator;
    public String displayTime;
    public long expireAt = 0;
    public String[] extData_gj_name_array;
    public String extData_gj_type = "";
    public String extra = "";
    public String features;
    public boolean hasSub = false;
    public String[] imgUrl;
    public String isEnable = "";
    public boolean isNewComing = true;
    public int location = -1;
    public String ope;
    public int page = -1;
    public String parentId = "";
    public int priority;
    public String pushMsgId = "";
    public String reside = "1";
    public String shortNameCity;
    public boolean shouldFormat = false;
    public String showBody = "";
    public boolean showOnMap = true;
    public int source = -1;
    public String[] subImgUrl;
    public String subTitle;
    public int sub_location = -1;
    public int sub_page = -1;
    public boolean sub_unread = true;
    public String trackId;
    public String type = TYPE_MSG;
    public String version;

    public static AmapMessage convertToAmapMessage(Msgbox msgbox) {
        JSONArray jSONArray;
        JSONArray jSONArray2;
        JSONArray jSONArray3;
        JSONArray jSONArray4 = null;
        if (msgbox == null) {
            return null;
        }
        AmapMessage amapMessage = new AmapMessage();
        amapMessage.id = msgbox.id;
        amapMessage.version = msgbox.version;
        amapMessage.category = msgbox.category;
        if (TextUtils.isEmpty(amapMessage.category)) {
            amapMessage.category = "1";
        }
        amapMessage.createdTime = msgbox.createdTime;
        if (amapMessage.createdTime <= 0) {
            amapMessage.createdTime = System.currentTimeMillis();
        }
        amapMessage.expireAt = msgbox.expireAt;
        amapMessage.features = msgbox.features;
        amapMessage.actionUri = msgbox.actionUri;
        amapMessage.creator = msgbox.creator;
        amapMessage.trackId = msgbox.trackId;
        amapMessage.extra = msgbox.extra;
        amapMessage.title = msgbox.title;
        amapMessage.descMessage = msgbox.descMessage;
        amapMessage.priority = msgbox.priority;
        amapMessage.isUnRead = msgbox.isUnRead;
        amapMessage.showOnMap = msgbox.showOnMap;
        amapMessage.reside = msgbox.reside;
        amapMessage.baricon = msgbox.baricon;
        amapMessage.msgImgUri = msgbox.msgImgUri;
        amapMessage.msgImgUriV2 = msgbox.msgImgUriV2;
        amapMessage.label = msgbox.label;
        amapMessage.labelColor = msgbox.labelColor;
        amapMessage.countdownEndtime = msgbox.countdownEndtime;
        amapMessage.isEnable = msgbox.isEnable;
        amapMessage.parentId = msgbox.parentId;
        amapMessage.wordStatus = msgbox.wordStatus;
        amapMessage.showBody = msgbox.showBody;
        amapMessage.nickName = msgbox.nickName;
        amapMessage.pushMsgId = msgbox.pushMsgId;
        amapMessage.goldNum = msgbox.goldNum;
        amapMessage.totalGoldNum = msgbox.totalGoldNum;
        amapMessage.goldImage1 = msgbox.goldImage1;
        amapMessage.goldImage2 = msgbox.goldImage2;
        if (!TextUtils.isEmpty(msgbox.extData_gj_name_array)) {
            try {
                jSONArray3 = new JSONArray(msgbox.extData_gj_name_array);
            } catch (JSONException e) {
                e.printStackTrace();
                jSONArray3 = null;
            }
            if (jSONArray3 != null && jSONArray3.length() > 0) {
                amapMessage.extData_gj_name_array = new String[jSONArray3.length()];
                for (int i = 0; i < jSONArray3.length(); i++) {
                    amapMessage.extData_gj_name_array[i] = jSONArray3.optString(i, "");
                }
            }
        }
        amapMessage.extData_gj_type = msgbox.extData_gj_type;
        amapMessage.isNewComing = msgbox.isNewComing;
        amapMessage.hasShown = msgbox.hasShown;
        amapMessage.ope = msgbox.ope;
        amapMessage.type = msgbox.type;
        amapMessage.tag = msgbox.tag;
        amapMessage.adcode = msgbox.adcode;
        amapMessage.shortNameCity = msgbox.shortNameCity;
        amapMessage.source = msgbox.source;
        amapMessage.shouldFormat = msgbox.shouldFormat;
        amapMessage.bannerUpdated = msgbox.bannerUpdated;
        amapMessage.page = msgbox.page;
        amapMessage.location = msgbox.location;
        if (!TextUtils.isEmpty(msgbox.imgUrl)) {
            try {
                jSONArray2 = new JSONArray(msgbox.imgUrl);
            } catch (JSONException e2) {
                e2.printStackTrace();
                jSONArray2 = null;
            }
            if (jSONArray2 != null && jSONArray2.length() > 0) {
                amapMessage.imgUrl = new String[jSONArray2.length()];
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    amapMessage.imgUrl[i2] = jSONArray2.optString(i2, "");
                }
            }
        }
        if (!TextUtils.isEmpty(msgbox.actions)) {
            try {
                jSONArray = new JSONArray(msgbox.actions);
            } catch (JSONException e3) {
                e3.printStackTrace();
                jSONArray = null;
            }
            if (jSONArray != null && jSONArray.length() > 0) {
                amapMessage.amapMessageBtnActions = new AmapMessageBtnAction[jSONArray.length()];
                for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i3);
                    if (optJSONObject != null) {
                        amapMessage.amapMessageBtnActions[i3] = new AmapMessageBtnAction();
                        amapMessage.amapMessageBtnActions[i3].actionKey = optJSONObject.optString("actionKey", "");
                        amapMessage.amapMessageBtnActions[i3].actionUri = optJSONObject.optString("actionUri", "");
                    }
                }
            }
        }
        amapMessage.hasSub = msgbox.hasSub;
        if (amapMessage.hasSub) {
            if (!TextUtils.isEmpty(msgbox.subImgUrl)) {
                try {
                    jSONArray4 = new JSONArray(msgbox.subImgUrl);
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
                if (jSONArray4 != null && jSONArray4.length() > 0) {
                    amapMessage.subImgUrl = new String[jSONArray4.length()];
                    for (int i4 = 0; i4 < jSONArray4.length(); i4++) {
                        amapMessage.subImgUrl[i4] = jSONArray4.optString(i4, "");
                    }
                }
            }
            amapMessage.subTitle = msgbox.subTitle;
            amapMessage.sub_page = msgbox.sub_page;
            amapMessage.sub_location = msgbox.sub_location;
            amapMessage.sub_unread = msgbox.sub_unread;
        }
        amapMessage.msgType = msgbox.msgType;
        amapMessage.barDisplay = msgbox.mesDisplay;
        amapMessage.boxDisplay = msgbox.boxDisplay;
        amapMessage.showType = msgbox.showType;
        amapMessage.updateTime = msgbox.updateTime;
        amapMessage.impression = msgbox.impression;
        return amapMessage;
    }

    public Msgbox convertToMsgbox() {
        Msgbox msgbox = new Msgbox();
        msgbox.id = this.id;
        msgbox.version = this.version;
        msgbox.category = this.category;
        msgbox.createdTime = this.createdTime;
        msgbox.expireAt = this.expireAt;
        msgbox.features = this.features;
        msgbox.actionUri = this.actionUri;
        msgbox.creator = this.creator;
        msgbox.trackId = this.trackId;
        msgbox.extra = this.extra;
        msgbox.title = this.title;
        msgbox.descMessage = this.descMessage;
        msgbox.priority = this.priority;
        msgbox.isUnRead = this.isUnRead;
        msgbox.showOnMap = this.showOnMap;
        msgbox.type = this.type;
        msgbox.isNewComing = this.isNewComing;
        msgbox.hasShown = this.hasShown;
        msgbox.tag = this.tag;
        msgbox.adcode = this.adcode;
        msgbox.shortNameCity = this.shortNameCity;
        msgbox.source = this.source;
        msgbox.shouldFormat = this.shouldFormat;
        msgbox.bannerUpdated = this.bannerUpdated;
        msgbox.page = this.page;
        msgbox.location = this.location;
        msgbox.pushMsgId = this.pushMsgId;
        msgbox.goldNum = this.goldNum;
        msgbox.totalGoldNum = this.totalGoldNum;
        msgbox.goldImage1 = this.goldImage1;
        msgbox.goldImage2 = this.goldImage2;
        if (this.extData_gj_name_array != null && this.extData_gj_name_array.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < this.extData_gj_name_array.length; i++) {
                try {
                    jSONArray.put(i, this.extData_gj_name_array[i]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            msgbox.extData_gj_name_array = jSONArray.toString();
        }
        msgbox.extData_gj_type = this.extData_gj_type;
        msgbox.baricon = this.baricon;
        msgbox.msgImgUri = this.msgImgUri;
        msgbox.msgImgUriV2 = this.msgImgUriV2;
        msgbox.label = this.label;
        msgbox.labelColor = this.labelColor;
        msgbox.countdownEndtime = this.countdownEndtime;
        msgbox.isEnable = this.isEnable;
        msgbox.parentId = this.parentId;
        msgbox.wordStatus = this.wordStatus;
        msgbox.showBody = this.showBody;
        msgbox.nickName = this.nickName;
        msgbox.reside = TextUtils.isEmpty(this.reside) ? "1" : this.reside;
        if (this.imgUrl != null) {
            JSONArray jSONArray2 = new JSONArray();
            for (int i2 = 0; i2 < this.imgUrl.length; i2++) {
                try {
                    jSONArray2.put(i2, this.imgUrl[i2]);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            msgbox.imgUrl = jSONArray2.toString();
        }
        if (this.amapMessageBtnActions != null && this.amapMessageBtnActions.length > 0) {
            JSONArray jSONArray3 = new JSONArray();
            for (int i3 = 0; i3 < this.amapMessageBtnActions.length; i3++) {
                if (this.amapMessageBtnActions[i3] != null) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("actionKey", this.amapMessageBtnActions[i3].actionKey);
                        jSONObject.put("actionUri", this.amapMessageBtnActions[i3].actionUri);
                        jSONArray3.put(i3, jSONObject);
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
            }
            if (jSONArray3.length() > 0) {
                msgbox.actions = jSONArray3.toString();
            }
        }
        msgbox.hasSub = this.hasSub;
        if (this.hasSub) {
            if (this.subImgUrl != null) {
                JSONArray jSONArray4 = new JSONArray();
                for (int i4 = 0; i4 < this.subImgUrl.length; i4++) {
                    try {
                        jSONArray4.put(i4, this.subImgUrl[i4]);
                    } catch (JSONException e4) {
                        e4.printStackTrace();
                    }
                }
                msgbox.subImgUrl = jSONArray4.toString();
            }
            msgbox.subTitle = this.subTitle;
            msgbox.sub_page = this.sub_page;
            msgbox.sub_location = this.sub_location;
            msgbox.sub_unread = this.sub_unread;
        }
        msgbox.msgType = this.msgType;
        msgbox.mesDisplay = this.barDisplay;
        msgbox.boxDisplay = this.boxDisplay;
        msgbox.showType = this.showType;
        msgbox.updateTime = this.updateTime;
        msgbox.impression = this.impression;
        return msgbox;
    }

    @SuppressFBWarnings({"CN_IDIOM_NO_SUPER_CALL"})
    public AmapMessage clone() {
        AmapMessage amapMessage = new AmapMessage();
        amapMessage.id = this.id;
        amapMessage.category = this.category;
        amapMessage.createdTime = this.createdTime;
        amapMessage.expireAt = this.expireAt;
        amapMessage.features = this.features;
        amapMessage.actionUri = this.actionUri;
        amapMessage.creator = this.creator;
        amapMessage.trackId = this.trackId;
        amapMessage.extra = this.extra;
        amapMessage.title = this.title;
        amapMessage.descMessage = this.descMessage;
        amapMessage.priority = this.priority;
        amapMessage.isUnRead = this.isUnRead;
        amapMessage.showOnMap = this.showOnMap;
        amapMessage.isNewComing = this.isNewComing;
        amapMessage.hasShown = this.hasShown;
        amapMessage.version = this.version;
        amapMessage.type = this.type;
        amapMessage.tag = this.tag;
        amapMessage.adcode = this.adcode;
        amapMessage.shortNameCity = this.shortNameCity;
        amapMessage.source = this.source;
        amapMessage.shouldFormat = this.shouldFormat;
        amapMessage.bannerUpdated = this.bannerUpdated;
        amapMessage.page = this.page;
        amapMessage.location = this.location;
        amapMessage.baricon = this.baricon;
        amapMessage.reside = this.reside;
        amapMessage.msgImgUri = this.msgImgUri;
        amapMessage.msgImgUriV2 = this.msgImgUriV2;
        amapMessage.label = this.label;
        amapMessage.labelColor = this.labelColor;
        amapMessage.countdownEndtime = this.countdownEndtime;
        amapMessage.isEnable = this.isEnable;
        amapMessage.parentId = this.parentId;
        amapMessage.wordStatus = this.wordStatus;
        amapMessage.showBody = this.showBody;
        amapMessage.nickName = this.nickName;
        amapMessage.pushMsgId = this.pushMsgId;
        amapMessage.impression = this.impression;
        amapMessage.goldNum = this.goldNum;
        amapMessage.totalGoldNum = this.totalGoldNum;
        amapMessage.goldImage1 = this.goldImage1;
        amapMessage.goldImage2 = this.goldImage2;
        if (this.extData_gj_name_array == null || this.extData_gj_name_array.length <= 0) {
            amapMessage.extData_gj_name_array = null;
        } else {
            String[] strArr = new String[this.extData_gj_name_array.length];
            for (int i = 0; i < this.extData_gj_name_array.length; i++) {
                strArr[i] = this.extData_gj_name_array[i];
            }
            amapMessage.extData_gj_name_array = strArr;
        }
        amapMessage.extData_gj_type = this.extData_gj_type;
        if (this.imgUrl != null) {
            String[] strArr2 = new String[this.imgUrl.length];
            for (int i2 = 0; i2 < this.imgUrl.length; i2++) {
                strArr2[i2] = this.imgUrl[i2];
            }
            amapMessage.imgUrl = strArr2;
        } else {
            amapMessage.imgUrl = null;
        }
        if (this.amapMessageBtnActions == null || this.amapMessageBtnActions.length <= 0) {
            amapMessage.amapMessageBtnActions = null;
        } else {
            AmapMessageBtnAction[] amapMessageBtnActionArr = new AmapMessageBtnAction[this.amapMessageBtnActions.length];
            for (int i3 = 0; i3 < this.amapMessageBtnActions.length; i3++) {
                if (this.amapMessageBtnActions[i3] != null) {
                    amapMessageBtnActionArr[i3] = new AmapMessageBtnAction();
                    amapMessageBtnActionArr[i3].actionKey = this.amapMessageBtnActions[i3].actionKey;
                    amapMessageBtnActionArr[i3].actionUri = this.amapMessageBtnActions[i3].actionUri;
                }
            }
            amapMessage.amapMessageBtnActions = amapMessageBtnActionArr;
        }
        amapMessage.hasSub = this.hasSub;
        if (this.hasSub) {
            if (this.subImgUrl != null) {
                String[] strArr3 = new String[this.subImgUrl.length];
                for (int i4 = 0; i4 < this.subImgUrl.length; i4++) {
                    strArr3[i4] = this.subImgUrl[i4];
                }
                amapMessage.subImgUrl = strArr3;
            } else {
                amapMessage.subImgUrl = null;
            }
            amapMessage.subTitle = this.subTitle;
            amapMessage.sub_page = this.sub_page;
            amapMessage.sub_location = this.sub_location;
            amapMessage.sub_unread = this.sub_unread;
        }
        amapMessage.msgType = this.msgType;
        amapMessage.barDisplay = this.barDisplay;
        amapMessage.boxDisplay = this.boxDisplay;
        amapMessage.showType = this.showType;
        amapMessage.updateTime = this.updateTime;
        return amapMessage;
    }

    public boolean isToastTips() {
        return !TextUtils.isEmpty(this.reside) && "2".equals(this.reside);
    }

    public boolean isADMsg() {
        return this.msgType == 1;
    }

    public boolean isEmergencyNews() {
        return this.msgType == 2;
    }

    public boolean isADDisplay() {
        return this.barDisplay;
    }
}
