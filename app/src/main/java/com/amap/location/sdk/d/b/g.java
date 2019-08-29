package com.amap.location.sdk.d.b;

import android.content.Context;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.location.e.c.a;
import com.amap.location.e.c.b;
import com.amap.location.offline.c;
import com.amap.location.protocol.d;
import com.amap.location.sdk.BuildConfig;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.SuperId;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: SdkConfigHelper */
public class g {
    public static void a(JSONObject jSONObject, a aVar) {
        try {
            if (a(jSONObject, (String) "multi")) {
                aVar.a = "1".equals(jSONObject.getString("multi"));
            }
            if (a(jSONObject, (String) "reversegeo")) {
                aVar.b = "1".equals(jSONObject.getString("reversegeo"));
            }
            if (a(jSONObject, (String) LocationParams.PARA_OVERSEAS) && jSONObject.optInt(LocationParams.PARA_OVERSEAS, 0) == 1) {
                com.amap.location.protocol.b.a.a = true;
            }
        } catch (Exception e) {
            com.amap.location.common.d.a.a((Throwable) e);
        }
    }

    public static b a(JSONObject jSONObject, boolean z) {
        b bVar = new b();
        bVar.a = a(jSONObject);
        return bVar;
    }

    private static d a(JSONObject jSONObject) {
        HashMap hashMap;
        Throwable th;
        String str;
        String str2;
        Object obj;
        String str3;
        JSONObject jSONObject2 = jSONObject;
        final HashMap hashMap2 = new HashMap(100);
        HashMap hashMap3 = new HashMap(100);
        String str4 = "Unknown";
        String str5 = "";
        String str6 = "";
        try {
            String[] strArr = {SuperId.BIT_1_MAIN_VOICE_ASSISTANT, "v", "e", "d", SuperId.BIT_1_NEARBY_SEARCH, H5Param.URL, "u2", "u3"};
            String[] strArr2 = {"channel", LocationParams.PARA_COMMON_CIFA, "from"};
            if (a(jSONObject2, (String) "session")) {
                try {
                    str = jSONObject2.getString("session");
                } catch (Exception e) {
                    th = e;
                    hashMap = hashMap3;
                    com.amap.location.common.d.a.a(th);
                    final HashMap hashMap4 = hashMap;
                    return new d() {
                        public final String a(String str) {
                            return b.a(str);
                        }

                        public final byte[] a(byte[] bArr) {
                            return b.a(bArr);
                        }

                        public final String a() {
                            return (String) hashMap2.get("session");
                        }

                        public final String b() {
                            return (String) hashMap2.get("spm");
                        }

                        public final String c() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_DIP);
                        }

                        public final String d() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_DIV);
                        }

                        public final String g() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_DIBV);
                        }

                        public final String h() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_DIE);
                        }

                        public final String i() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_DID);
                        }

                        public final String j() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_DIC);
                        }

                        public final String k() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU);
                        }

                        public final String l() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU2);
                        }

                        public final String m() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU3);
                        }

                        public final String n() {
                            return (String) hashMap2.get("channel");
                        }

                        public final String o() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_CIFA);
                        }

                        public final String p() {
                            return (String) hashMap2.get("from");
                        }

                        public final String q() {
                            return (String) hashMap2.get("tid");
                        }

                        public final String r() {
                            return (String) hashMap2.get("stepid");
                        }

                        public final String s() {
                            return (String) hashMap2.get("appkey");
                        }

                        public final String t() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_LOC_SCENE);
                        }

                        public final Map<String, String> u() {
                            return hashMap4;
                        }

                        public final String f() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_ADIU);
                        }

                        public final String e() {
                            return (String) hashMap2.get(LocationParams.PARA_COMMON_AUTODIV);
                        }
                    };
                }
            } else {
                str = a.a();
            }
            String b = a.b();
            if (a(jSONObject2, (String) "stepid")) {
                str4 = jSONObject2.getString("stepid");
            }
            if (a(jSONObject2, (String) "tid")) {
                str5 = jSONObject2.getString("tid");
            }
            if (a(jSONObject2, (String) "aosextra")) {
                JSONObject jSONObject3 = jSONObject2.getJSONObject("aosextra");
                for (int i = 0; i < 8; i++) {
                    String concat = "di".concat(String.valueOf(strArr[i]));
                    if (a(jSONObject3, concat)) {
                        jSONObject2.put(concat, jSONObject3.get(concat));
                        jSONObject3.remove(concat);
                    }
                }
                for (int i2 = 0; i2 < 3; i2++) {
                    String str7 = strArr2[i2];
                    if (a(jSONObject3, str7)) {
                        jSONObject2.put(str7, jSONObject3.get(str7));
                        jSONObject3.remove(str7);
                    }
                }
                Iterator<String> keys = jSONObject3.keys();
                while (keys != null && keys.hasNext()) {
                    String next = keys.next();
                    hashMap3.put(next, jSONObject3.getString(next));
                }
                if (jSONObject3.has("tid")) {
                    str5 = jSONObject3.optString("tid");
                }
                str6 = jSONObject3.optString("appkey");
            }
            String optString = jSONObject2.optString(LocationParams.PARA_COMMON_DIP);
            String optString2 = jSONObject2.optString(LocationParams.PARA_COMMON_DIV);
            String optString3 = jSONObject2.optString(LocationParams.PARA_COMMON_DIE);
            String optString4 = jSONObject2.optString(LocationParams.PARA_COMMON_DID);
            String optString5 = jSONObject2.optString(LocationParams.PARA_COMMON_DIC);
            String optString6 = jSONObject2.optString(LocationParams.PARA_COMMON_DIU);
            r16 = "";
            String optString7 = jSONObject2.optString(LocationParams.PARA_COMMON_DIU2);
            hashMap = hashMap3;
            try {
                String optString8 = jSONObject2.optString(LocationParams.PARA_COMMON_DIU3);
                String str8 = str6;
                String optString9 = jSONObject2.optString("channel");
                String str9 = str4;
                String optString10 = jSONObject2.optString(LocationParams.PARA_COMMON_CIFA);
                String str10 = str5;
                String optString11 = jSONObject2.optString("from");
                if (jSONObject2.has(LocationParams.PARA_COMMON_DIBV)) {
                    obj = jSONObject2.optString(LocationParams.PARA_COMMON_DIBV);
                    str2 = optString10;
                } else {
                    str2 = optString10;
                    obj = "";
                }
                if (jSONObject2.has(LocationParams.PARA_COMMON_LOC_SCENE)) {
                    str3 = optString9;
                    LocationParams.AMAP_LOC_SCENE = jSONObject2.optString(LocationParams.PARA_COMMON_LOC_SCENE, "0");
                } else {
                    str3 = optString9;
                }
                if (jSONObject2.has(LocationParams.PARA_COMMON_DRIVE_MODE)) {
                    LocationParams.AMAP_DRIVE_MODE = jSONObject2.optString(LocationParams.PARA_COMMON_DRIVE_MODE, "-1");
                }
                hashMap2.put("session", str);
                hashMap2.put("spm", b);
                hashMap2.put(LocationParams.PARA_COMMON_DIP, optString);
                hashMap2.put(LocationParams.PARA_COMMON_DIV, optString2);
                hashMap2.put(LocationParams.PARA_COMMON_DIBV, obj);
                hashMap2.put(LocationParams.PARA_COMMON_DIE, optString3);
                hashMap2.put(LocationParams.PARA_COMMON_DID, optString4);
                hashMap2.put(LocationParams.PARA_COMMON_DIC, optString5);
                hashMap2.put(LocationParams.PARA_COMMON_DIU, optString6);
                hashMap2.put(LocationParams.PARA_COMMON_DIU2, optString7);
                hashMap2.put(LocationParams.PARA_COMMON_DIU3, optString8);
                hashMap2.put("channel", str3);
                hashMap2.put(LocationParams.PARA_COMMON_CIFA, str2);
                hashMap2.put("from", optString11);
                hashMap2.put("tid", str10);
                hashMap2.put("stepid", str9);
                hashMap2.put("appkey", str8);
                hashMap2.put(LocationParams.PARA_COMMON_LOC_SCENE, LocationParams.AMAP_LOC_SCENE);
                hashMap2.put(LocationParams.PARA_COMMON_ADIU, jSONObject2.optString(LocationParams.PARA_COMMON_ADIU));
                hashMap2.put(LocationParams.PARA_COMMON_AUTODIV, jSONObject2.optString(LocationParams.PARA_COMMON_AUTODIV));
            } catch (Exception e2) {
                e = e2;
                th = e;
                com.amap.location.common.d.a.a(th);
                final HashMap hashMap42 = hashMap;
                return new d() {
                    public final String a(String str) {
                        return b.a(str);
                    }

                    public final byte[] a(byte[] bArr) {
                        return b.a(bArr);
                    }

                    public final String a() {
                        return (String) hashMap2.get("session");
                    }

                    public final String b() {
                        return (String) hashMap2.get("spm");
                    }

                    public final String c() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_DIP);
                    }

                    public final String d() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_DIV);
                    }

                    public final String g() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_DIBV);
                    }

                    public final String h() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_DIE);
                    }

                    public final String i() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_DID);
                    }

                    public final String j() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_DIC);
                    }

                    public final String k() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU);
                    }

                    public final String l() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU2);
                    }

                    public final String m() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU3);
                    }

                    public final String n() {
                        return (String) hashMap2.get("channel");
                    }

                    public final String o() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_CIFA);
                    }

                    public final String p() {
                        return (String) hashMap2.get("from");
                    }

                    public final String q() {
                        return (String) hashMap2.get("tid");
                    }

                    public final String r() {
                        return (String) hashMap2.get("stepid");
                    }

                    public final String s() {
                        return (String) hashMap2.get("appkey");
                    }

                    public final String t() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_LOC_SCENE);
                    }

                    public final Map<String, String> u() {
                        return hashMap42;
                    }

                    public final String f() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_ADIU);
                    }

                    public final String e() {
                        return (String) hashMap2.get(LocationParams.PARA_COMMON_AUTODIV);
                    }
                };
            }
        } catch (Exception e3) {
            e = e3;
            hashMap = hashMap3;
            th = e;
            com.amap.location.common.d.a.a(th);
            final HashMap hashMap422 = hashMap;
            return new d() {
                public final String a(String str) {
                    return b.a(str);
                }

                public final byte[] a(byte[] bArr) {
                    return b.a(bArr);
                }

                public final String a() {
                    return (String) hashMap2.get("session");
                }

                public final String b() {
                    return (String) hashMap2.get("spm");
                }

                public final String c() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_DIP);
                }

                public final String d() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_DIV);
                }

                public final String g() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_DIBV);
                }

                public final String h() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_DIE);
                }

                public final String i() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_DID);
                }

                public final String j() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_DIC);
                }

                public final String k() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU);
                }

                public final String l() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU2);
                }

                public final String m() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU3);
                }

                public final String n() {
                    return (String) hashMap2.get("channel");
                }

                public final String o() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_CIFA);
                }

                public final String p() {
                    return (String) hashMap2.get("from");
                }

                public final String q() {
                    return (String) hashMap2.get("tid");
                }

                public final String r() {
                    return (String) hashMap2.get("stepid");
                }

                public final String s() {
                    return (String) hashMap2.get("appkey");
                }

                public final String t() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_LOC_SCENE);
                }

                public final Map<String, String> u() {
                    return hashMap422;
                }

                public final String f() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_ADIU);
                }

                public final String e() {
                    return (String) hashMap2.get(LocationParams.PARA_COMMON_AUTODIV);
                }
            };
        }
        final HashMap hashMap4222 = hashMap;
        return new d() {
            public final String a(String str) {
                return b.a(str);
            }

            public final byte[] a(byte[] bArr) {
                return b.a(bArr);
            }

            public final String a() {
                return (String) hashMap2.get("session");
            }

            public final String b() {
                return (String) hashMap2.get("spm");
            }

            public final String c() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_DIP);
            }

            public final String d() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_DIV);
            }

            public final String g() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_DIBV);
            }

            public final String h() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_DIE);
            }

            public final String i() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_DID);
            }

            public final String j() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_DIC);
            }

            public final String k() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU);
            }

            public final String l() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU2);
            }

            public final String m() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_DIU3);
            }

            public final String n() {
                return (String) hashMap2.get("channel");
            }

            public final String o() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_CIFA);
            }

            public final String p() {
                return (String) hashMap2.get("from");
            }

            public final String q() {
                return (String) hashMap2.get("tid");
            }

            public final String r() {
                return (String) hashMap2.get("stepid");
            }

            public final String s() {
                return (String) hashMap2.get("appkey");
            }

            public final String t() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_LOC_SCENE);
            }

            public final Map<String, String> u() {
                return hashMap4222;
            }

            public final String f() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_ADIU);
            }

            public final String e() {
                return (String) hashMap2.get(LocationParams.PARA_COMMON_AUTODIV);
            }
        };
    }

    public static c a(Context context) {
        c cVar = new c();
        cVar.e = com.amap.location.common.a.a();
        cVar.f = com.amap.location.common.a.a(context);
        cVar.g = com.amap.location.common.a.d(context);
        cVar.h = com.amap.location.common.a.b(context);
        cVar.b = 0;
        cVar.d = BuildConfig.VERSION_NAME;
        cVar.i = "ABKLWEH8H9LH09NLB5CCAGHK78BYZ89";
        cVar.l = new String[]{"com.amap.location.offline.flp.LocationProvider", "com.amap.location.offline.nlp.LocationProvider"};
        cVar.k = true;
        cVar.m = new c.b() {
            public final void a(byte[] bArr) {
                com.amap.location.sdk.b.a.d.b(4, bArr);
            }
        };
        cVar.n = com.amap.location.sdk.b.d.a();
        return cVar;
    }

    private static boolean a(JSONObject jSONObject, String str) {
        return jSONObject != null && jSONObject.has(str);
    }
}
