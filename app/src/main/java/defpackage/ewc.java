package defpackage;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ewc reason: default package */
/* compiled from: TaskDispatch */
public class ewc {
    public static String b = "";
    /* access modifiers changed from: private */
    public static final String c = "ewc";
    public ConcurrentHashMap<String, String> a;

    /* renamed from: ewc$a */
    /* compiled from: TaskDispatch */
    static class a {
        public static final ewc a = new ewc(0);
    }

    /* synthetic */ ewc(byte b2) {
        this();
    }

    public static ewc a() {
        return a.a;
    }

    private ewc() {
        this.a = new ConcurrentHashMap<>();
    }

    /* access modifiers changed from: private */
    public Boolean a(Context context, JSONObject jSONObject) {
        if (!euw.J(context)) {
            return Boolean.FALSE;
        }
        String optString = jSONObject.optString("checkroot");
        if (!TextUtils.isEmpty(optString)) {
            if (optString.equals("1") && euw.e()) {
                return Boolean.TRUE;
            }
        } else if (euw.e()) {
            return Boolean.TRUE;
        }
        String optString2 = jSONObject.optString("cnd");
        if (TextUtils.isEmpty(optString2)) {
            return Boolean.TRUE;
        }
        String b2 = ewf.b("mobileanalytics", optString2);
        if (TextUtils.isEmpty(b2)) {
            return Boolean.TRUE;
        }
        try {
            HashMap<String, HashMap<String, ArrayList<String>>> c2 = c(new JSONObject(b2));
            if (c2.isEmpty()) {
                return Boolean.TRUE;
            }
            if (!a(c2.get("includeMap"))) {
                return Boolean.TRUE;
            }
            return null;
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(th.getMessage());
            euw.a(sb.toString());
            return Boolean.FALSE;
        }
    }

    /* access modifiers changed from: private */
    public boolean a(HashMap<String, ArrayList<String>> hashMap) {
        if (hashMap.isEmpty()) {
            return false;
        }
        for (Entry next : hashMap.entrySet()) {
            String str = (String) next.getKey();
            ArrayList arrayList = (ArrayList) next.getValue();
            if (arrayList == null || arrayList.size() == 0) {
                return false;
            }
            String str2 = this.a.get(str);
            if (TextUtils.isEmpty(str2)) {
                return false;
            }
            if (!arrayList.contains(str2)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static HashMap<String, HashMap<String, ArrayList<String>>> c(JSONObject jSONObject) {
        HashMap<String, HashMap<String, ArrayList<String>>> hashMap = new HashMap<>();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            int i = 0;
            if (next.endsWith("_block")) {
                JSONArray optJSONArray = jSONObject.optJSONArray(next);
                if (optJSONArray != null) {
                    int length = optJSONArray.length();
                    if (length != 0) {
                        ArrayList arrayList = new ArrayList();
                        while (i < length) {
                            String optString = optJSONArray.optString(i);
                            if (!TextUtils.isEmpty(optString)) {
                                arrayList.add(optString);
                            }
                            i++;
                        }
                        hashMap3.put(next, arrayList);
                    }
                }
            } else {
                JSONArray optJSONArray2 = jSONObject.optJSONArray(next);
                if (optJSONArray2 != null) {
                    int length2 = optJSONArray2.length();
                    if (length2 != 0) {
                        ArrayList arrayList2 = new ArrayList();
                        while (i < length2) {
                            String optString2 = optJSONArray2.optString(i);
                            if (!TextUtils.isEmpty(optString2)) {
                                arrayList2.add(optString2);
                            }
                            i++;
                        }
                        hashMap2.put(next, arrayList2);
                    } else {
                        hashMap2.put(next, new ArrayList());
                    }
                }
            }
        }
        hashMap.put("includeMap", hashMap2);
        hashMap.put("excludeMap", hashMap3);
        return hashMap;
    }

    static /* synthetic */ String a(ewc ewc, JSONObject jSONObject, String str, String str2) throws UnsupportedEncodingException, JSONException, MalformedURLException, URISyntaxException {
        String[] strArr;
        JSONArray optJSONArray = jSONObject.optJSONArray("params");
        if (optJSONArray == null) {
            strArr = new String[0];
        } else {
            String[] strArr2 = new String[optJSONArray.length()];
            for (int i = 0; i < optJSONArray.length(); i++) {
                strArr2[i] = optJSONArray.optString(i);
            }
            strArr = strArr2;
        }
        for (String str3 : strArr) {
            String str4 = ewc.a.get(str3);
            if (str4 != null) {
                if (str2.equals("hvtstart") || str2.equals("mapping")) {
                    StringBuilder sb = new StringBuilder("{");
                    sb.append(str3);
                    sb.append(h.d);
                    str = str.replace(sb.toString(), Uri.encode(str4));
                } else {
                    StringBuilder sb2 = new StringBuilder("{");
                    sb2.append(str3);
                    sb2.append(h.d);
                    str = str.replace(sb2.toString(), str4);
                }
            }
        }
        return str;
    }

    static /* synthetic */ ArrayList b(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("wake_pkg");
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); i++) {
                String optString = optJSONArray.optString(i);
                if (!TextUtils.isEmpty(optString)) {
                    arrayList.add(optString);
                }
            }
        }
        return arrayList;
    }
}
