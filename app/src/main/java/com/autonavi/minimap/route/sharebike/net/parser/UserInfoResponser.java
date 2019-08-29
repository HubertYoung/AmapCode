package com.autonavi.minimap.route.sharebike.net.parser;

import android.text.TextUtils;
import com.autonavi.minimap.route.sharebike.model.UserInfo;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import com.autonavi.server.aos.serverkey;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoResponser extends BaseResponser {
    public UserInfoResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        egt egt;
        JSONObject parseHeader = parseHeader(bArr);
        UserInfo userInfo = new UserInfo();
        userInfo.result = this.result;
        userInfo.errorCode = this.errorCode;
        if (parseHeader != null) {
            userInfo.resStr = parseHeader.optString("data");
            String str = "";
            if (!TextUtils.isEmpty(userInfo.resStr)) {
                str = serverkey.amapDecode(userInfo.resStr);
                if (!TextUtils.isEmpty(str)) {
                    JSONObject jSONObject = new JSONObject(str);
                    Iterator<String> keys = jSONObject.keys();
                    if (keys != null) {
                        while (keys.hasNext()) {
                            String next = keys.next();
                            JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                            if (TextUtils.isEmpty(next) || jSONObject2 == null) {
                                egt = null;
                            } else {
                                egt = new egt();
                                egt.a = next;
                                egt.d = jSONObject2.optString("appkey");
                                egt.b = jSONObject2.optString("userid");
                                egt.c = jSONObject2.optString("token");
                                String jSONObject3 = jSONObject2.toString();
                                if (!TextUtils.isEmpty(jSONObject3)) {
                                    egt.e = jSONObject3;
                                }
                            }
                            if (egt != null) {
                                userInfo.items.add(egt);
                            }
                        }
                    }
                }
            }
            eao.e(getClass().getName(), "after decode ---> ".concat(String.valueOf(str)));
        }
        setResult(userInfo);
    }
}
