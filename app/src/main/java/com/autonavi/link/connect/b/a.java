package com.autonavi.link.connect.b;

import android.os.Build;
import android.os.Build.VERSION;
import android.util.JsonReader;
import com.autonavi.link.LinkSDK;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.connect.model.b;
import com.autonavi.link.connect.wifi.ShareNetManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: JsonUtils */
public class a {
    public static synchronized b a(InputStream inputStream) throws IOException, JSONException {
        b bVar;
        synchronized (a.class) {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            bVar = new b();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if (nextName.equals("isAndroid")) {
                    bVar.a = "isAndroid".endsWith(jsonReader.nextString());
                } else if (nextName.equals("SDKVersion")) {
                    bVar.b = jsonReader.nextString();
                } else if (nextName.equals("httpPort")) {
                    bVar.g = jsonReader.nextString();
                } else if (nextName.equals("httpIP")) {
                    bVar.f = jsonReader.nextString();
                } else if (nextName.equals("socketPort")) {
                    bVar.h = jsonReader.nextString();
                } else if (nextName.equals("deviceName")) {
                    bVar.i = jsonReader.nextString();
                } else if (nextName.equals("appId")) {
                    bVar.j = jsonReader.nextString();
                } else if (nextName.equals("appName")) {
                    bVar.k = jsonReader.nextString();
                } else if (nextName.equals("appVersion")) {
                    bVar.l = jsonReader.nextString();
                } else if (nextName.equals("connectionId")) {
                    bVar.m = jsonReader.nextString();
                } else {
                    int i = 0;
                    if (nextName.equals("Bluetooth")) {
                        ArrayList arrayList = new ArrayList();
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            arrayList.add(jsonReader.nextString());
                        }
                        jsonReader.endArray();
                        String[] strArr = new String[arrayList.size()];
                        while (i < strArr.length) {
                            strArr[i] = (String) arrayList.get(i);
                            i++;
                        }
                        bVar.c = strArr;
                    } else if (nextName.equals("WIFI")) {
                        ArrayList arrayList2 = new ArrayList();
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            arrayList2.add(jsonReader.nextString());
                        }
                        jsonReader.endArray();
                        String[] strArr2 = new String[arrayList2.size()];
                        while (i < strArr2.length) {
                            strArr2[i] = (String) arrayList2.get(i);
                            i++;
                        }
                        bVar.d = strArr2;
                    } else if (nextName.equals("HTTP")) {
                        ArrayList arrayList3 = new ArrayList();
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            arrayList3.add(jsonReader.nextString());
                        }
                        jsonReader.endArray();
                        String[] strArr3 = new String[arrayList3.size()];
                        while (i < strArr3.length) {
                            strArr3[i] = (String) arrayList3.get(i);
                            i++;
                        }
                        bVar.e = strArr3;
                    } else if (nextName.equals("deviceInfo")) {
                        bVar.n = a(jsonReader);
                    } else {
                        jsonReader.skipValue();
                    }
                }
            }
            jsonReader.endObject();
        }
        return bVar;
    }

    private static com.autonavi.link.connect.model.a a(JsonReader jsonReader) throws IOException {
        com.autonavi.link.connect.model.a aVar = new com.autonavi.link.connect.model.a();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("deviceId")) {
                aVar.a = jsonReader.nextString();
            } else if (nextName.equals("deviceName")) {
                aVar.b = jsonReader.nextString();
            } else if (nextName.equals("factoryName")) {
                aVar.c = jsonReader.nextString();
            } else if (nextName.equals("osVersion")) {
                aVar.d = jsonReader.nextString();
            } else if (nextName.equals("isRoot")) {
                aVar.e = jsonReader.nextString();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return aVar;
    }

    public static byte[] a(String str, String str2, String str3) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (str == null) {
            str = Build.MODEL;
        }
        jSONObject.put("deviceName", str);
        if (str2 == null) {
            str2 = "";
        }
        jSONObject.put("appId", str2);
        if (str3 == null) {
            str3 = "5656";
        }
        jSONObject.put("socketPort", str3);
        return jSONObject.toString().getBytes();
    }

    public static DiscoverInfo b(InputStream inputStream) throws IOException {
        DiscoverInfo discoverInfo = new DiscoverInfo();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "utf-8"));
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("socketPort")) {
                jsonReader.nextString();
            } else if (nextName.equals("deviceName")) {
                discoverInfo.deviceName = jsonReader.nextString();
            } else if (nextName.equals("appId")) {
                discoverInfo.appId = jsonReader.nextString();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return discoverInfo;
    }

    public static synchronized byte[] b(String str, String str2, String str3) throws IOException, JSONException {
        byte[] bytes;
        synchronized (a.class) {
            b bVar = new b();
            bVar.b = LinkSDK.getInstance().getSdkVersion();
            String[] strArr = {"1. point to point", "2. transfer small file"};
            String[] strArr2 = {"1. point to point"};
            String[] strArr3 = {"1.1"};
            if (str2 == null) {
                str2 = "";
            }
            bVar.g = str2;
            if (str == null) {
                str = "";
            }
            bVar.f = str;
            if (str3 == null) {
                str3 = "";
            }
            bVar.h = str3;
            bVar.i = Build.MODEL;
            bVar.j = ShareNetManager.getInstance().getAppPackageName();
            bVar.k = ShareNetManager.getInstance().getAppName();
            bVar.l = ShareNetManager.getInstance().getAppVersion();
            bVar.c = strArr;
            bVar.d = strArr2;
            bVar.e = strArr3;
            bVar.a(Build.ID, Build.MODEL, Build.BOARD, VERSION.RELEASE, false);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("isAndroid", "isAndroid");
            jSONObject.put("SDKVersion", bVar.b);
            jSONObject.put("httpPort", bVar.g);
            jSONObject.put("httpIP", bVar.f);
            jSONObject.put("socketPort", bVar.h);
            jSONObject.put("deviceName", bVar.i);
            jSONObject.put("appId", bVar.j);
            jSONObject.put("appName", bVar.k);
            jSONObject.put("appVersion", bVar.l);
            jSONObject.put("connectionId", com.autonavi.link.connect.a.a.a().b());
            if (VERSION.SDK_INT > 19) {
                jSONObject.put("Bluetooth", new JSONArray(bVar.c));
                jSONObject.put("WIFI", new JSONArray(bVar.d));
                jSONObject.put("HTTP", new JSONArray(bVar.e));
            } else {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < 2; i++) {
                    arrayList.add(strArr[i]);
                }
                jSONObject.put("Bluetooth", new JSONArray(arrayList));
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < 2; i2++) {
                    arrayList2.add(strArr[i2]);
                }
                jSONObject.put("WIFI", new JSONArray(arrayList2));
                ArrayList arrayList3 = new ArrayList();
                for (int i3 = 0; i3 < 2; i3++) {
                    arrayList3.add(strArr[i3]);
                }
                jSONObject.put("HTTP", new JSONArray(arrayList3));
            }
            HashMap hashMap = new HashMap();
            hashMap.put("deviceId", bVar.n.a);
            hashMap.put("deviceName", bVar.n.b);
            hashMap.put("factoryName", bVar.n.c);
            hashMap.put("osVersion", bVar.n.d);
            hashMap.put("isRoot", bVar.n.e);
            jSONObject.put("deviceInfo", new JSONObject(hashMap));
            bytes = jSONObject.toString().getBytes();
        }
        return bytes;
    }
}
