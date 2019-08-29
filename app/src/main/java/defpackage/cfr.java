package defpackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: cfr reason: default package */
/* compiled from: BillStatementListRequestParser */
public final class cfr {
    public static List<cfn> a(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("tradelogs");
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                String optString = optJSONObject.optString("action_time");
                GregorianCalendar a = a(optString);
                a aVar = new a();
                aVar.a = optString;
                aVar.b = a;
                aVar.c = optJSONObject.optInt("type");
                String optString2 = optJSONObject.optString("type_display");
                if (optString2 != null) {
                    aVar.d = optString2;
                }
                aVar.e = optJSONObject.optDouble("action_amount");
                aVar.f = optJSONObject.optInt("status");
                String optString3 = optJSONObject.optString("status_display");
                if (optString3 != null) {
                    aVar.g = optString3;
                }
                cfn cfn = new cfn(aVar.b, aVar.a, aVar.c, aVar.d, aVar.e, aVar.f, aVar.g, 0);
                arrayList.add(cfn);
            }
        }
        return arrayList;
    }

    private static GregorianCalendar a(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(simpleDateFormat.parse(str));
            return gregorianCalendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
