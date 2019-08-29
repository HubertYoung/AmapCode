package com.autonavi.minimap.route.sharebike.net.parser;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.minimap.route.sharebike.model.UserTagData;
import com.autonavi.minimap.route.sharebike.model.UserTagDataFees;
import com.autonavi.minimap.route.sharebike.model.UserTagDataInfo;
import com.autonavi.minimap.route.sharebike.model.UserTagDataTips;
import com.autonavi.minimap.route.sharebike.model.UserTagInfo;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserTagResponser extends BaseResponser {
    public UserTagResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        UserTagDataFees userTagDataFees;
        if (bArr != null && bArr.length != 0) {
            parseHeader(bArr);
            UserTagInfo userTagInfo = new UserTagInfo();
            userTagInfo.result = this.result;
            userTagInfo.errorCode = this.errorCode;
            if (this.result && this.errorCode == 1) {
                String str = new String(bArr);
                userTagInfo.setAllInfoJson(str);
                JSONObject optJSONObject = new JSONObject(str).optJSONObject("data");
                if (optJSONObject != null) {
                    UserTagData userTagData = new UserTagData();
                    String optString = optJSONObject.optString("tips_md5");
                    if (TextUtils.isEmpty(optString)) {
                        optString = "";
                    }
                    userTagData.setTipsMd5(optString);
                    userTagData.setTaginfo(a(optJSONObject.optJSONObject("taginfo")));
                    userTagData.setTips(a(optJSONObject.optJSONArray(ModulePoi.TIPS)));
                    JSONObject optJSONObject2 = optJSONObject.optJSONObject("fees");
                    if (optJSONObject2 != null) {
                        userTagDataFees = new UserTagDataFees();
                        userTagDataFees.setMobike(a("mobike", optJSONObject2));
                        userTagDataFees.setOfo(a("ofo", optJSONObject2));
                    } else {
                        userTagDataFees = null;
                    }
                    userTagData.setFees(userTagDataFees);
                    userTagInfo.setData(userTagData);
                }
            }
            setResult(userTagInfo);
        }
    }

    private static UserTagDataInfo a(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        UserTagDataInfo userTagDataInfo = new UserTagDataInfo();
        JSONObject optJSONObject = jSONObject.optJSONObject(H5Param.MENU_ICON);
        if (optJSONObject != null) {
            UserTagDataInfo.a aVar = new UserTagDataInfo.a();
            String optString = optJSONObject.optString("front");
            if (TextUtils.isEmpty(optString)) {
                optString = "";
            }
            aVar.a = optString;
            String optString2 = optJSONObject.optString("tail");
            if (TextUtils.isEmpty(optString2)) {
                optString2 = "";
            }
            aVar.b = optString2;
            userTagDataInfo.setIcon(aVar);
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("tags");
        if (optJSONArray != null) {
            ArrayList arrayList = new ArrayList();
            int length = optJSONArray.length();
            for (int i = 0; i < length; i++) {
                String optString3 = optJSONArray.optString(i);
                if (TextUtils.isEmpty(optString3)) {
                    optString3 = "";
                }
                arrayList.add(optString3);
            }
            userTagDataInfo.setTags(arrayList);
        }
        return userTagDataInfo;
    }

    private static UserTagDataTips a(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() <= 0) {
            return null;
        }
        UserTagDataTips userTagDataTips = new UserTagDataTips();
        int length = jSONArray.length();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < length; i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                UserTagDataTips.a aVar = new UserTagDataTips.a();
                String optString = optJSONObject.optString("desc");
                if (TextUtils.isEmpty(optString)) {
                    optString = "";
                }
                aVar.a = optString;
                String optString2 = optJSONObject.optString(H5Param.MENU_ICON);
                if (TextUtils.isEmpty(optString2)) {
                    optString2 = "";
                }
                aVar.c = optString2;
                String optString3 = optJSONObject.optString("url");
                if (TextUtils.isEmpty(optString3)) {
                    optString3 = "";
                }
                aVar.b = optString3;
                arrayList.add(aVar);
            }
        }
        userTagDataTips.setTips(arrayList);
        return userTagDataTips;
    }

    private static UserTagDataFees.a a(String str, JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        if (optJSONObject == null) {
            return null;
        }
        UserTagDataFees.a aVar = new UserTagDataFees.a();
        String optString = optJSONObject.optString("tag_desc");
        if (TextUtils.isEmpty(optString)) {
            optString = "";
        }
        aVar.a = optString;
        String optString2 = optJSONObject.optString("fees");
        if (TextUtils.isEmpty(optString2)) {
            optString2 = "";
        }
        aVar.b = optString2;
        return aVar;
    }
}
