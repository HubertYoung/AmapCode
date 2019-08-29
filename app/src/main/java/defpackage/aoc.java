package defpackage;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.ali.auth.third.login.LoginConstants;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aoc reason: default package */
/* compiled from: UserUnbindAction */
public class aoc extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a = a();
        if (a != null && jSONObject != null) {
            JSONArray optJSONArray = jSONObject.optJSONArray("unbundling");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                LinkedList linkedList = new LinkedList();
                for (int i = 0; i < length; i++) {
                    linkedList.add(optJSONArray.optString(i));
                }
                a(linkedList);
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("_action", waVar.b);
            jSONObject2.put("status", 1);
            a.callJs(waVar.a, jSONObject2.toString());
        }
    }

    private static void a(List<String> list) {
        if (!list.isEmpty()) {
            for (String next : list) {
                if (!TextUtils.isEmpty(next)) {
                    AccountType accountType = null;
                    char c = 65535;
                    switch (next.hashCode()) {
                        case -1414960566:
                            if (next.equals(BehavorReporter.PROVIDE_BY_ALIPAY)) {
                                c = 3;
                                break;
                            }
                            break;
                        case -881000146:
                            if (next.equals(LoginConstants.TAOBAO_LOGIN)) {
                                c = 2;
                                break;
                            }
                            break;
                        case -791575966:
                            if (next.equals("weixin")) {
                                c = 4;
                                break;
                            }
                            break;
                        case 3616:
                            if (next.equals("qq")) {
                                c = 0;
                                break;
                            }
                            break;
                        case 96619420:
                            if (next.equals(NotificationCompat.CATEGORY_EMAIL)) {
                                c = 6;
                                break;
                            }
                            break;
                        case 106642798:
                            if (next.equals("phone")) {
                                c = 5;
                                break;
                            }
                            break;
                        case 113011944:
                            if (next.equals("weibo")) {
                                c = 1;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            accountType = AccountType.QQ;
                            break;
                        case 1:
                            accountType = AccountType.Sina;
                            break;
                        case 2:
                            accountType = AccountType.Taobao;
                            break;
                        case 3:
                            accountType = AccountType.Alipay;
                            break;
                        case 4:
                            accountType = AccountType.Weixin;
                            break;
                        case 5:
                            accountType = AccountType.Mobile;
                            break;
                        case 6:
                            accountType = AccountType.Email;
                            break;
                    }
                    aoz.a(accountType);
                }
            }
        }
    }
}
