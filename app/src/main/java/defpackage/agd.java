package defpackage;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: agd reason: default package */
/* compiled from: JsonHelper */
public final class agd {
    static String a = "null";

    public static void a(JSONObject jSONObject, String str, String str2) {
        if (jSONObject != null && str != null && str.length() != 0) {
            if (str2 == null || str2.length() == 0) {
                str2 = a;
            }
            try {
                jSONObject.put(str, str2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void a(JSONObject jSONObject, String str, String str2, String str3) {
        if (jSONObject != null && str.length() != 0) {
            if (TextUtils.isEmpty(str2)) {
                str2 = str3;
            }
            try {
                jSONObject.put(str, str2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void a(JSONObject jSONObject, String str, int i) {
        if (jSONObject != null && str.length() != 0) {
            try {
                jSONObject.put(str, String.valueOf(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void a(JSONObject jSONObject, String str, long j) {
        if (str.length() != 0) {
            try {
                jSONObject.put(str, String.valueOf(j));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void a(JSONObject jSONObject, String str, boolean z) {
        if (str.length() != 0) {
            try {
                jSONObject.put(str, z);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static int a(JSONObject jSONObject, String str) {
        if (jSONObject == null || str.length() == 0) {
            return -1;
        }
        try {
            String string = jSONObject.getString(str);
            if (string.equals(a)) {
                return -1;
            }
            try {
                return Integer.parseInt(string);
            } catch (Exception unused) {
                return 0;
            }
        } catch (JSONException unused2) {
            return -1;
        }
    }

    public static double b(JSONObject jSONObject, String str) {
        if (jSONObject == null || str.length() == 0) {
            return -1.0d;
        }
        try {
            String string = jSONObject.getString(str);
            if (string.equals(a)) {
                return -1.0d;
            }
            try {
                return Double.parseDouble(string);
            } catch (Exception unused) {
                return 0.0d;
            }
        } catch (JSONException unused2) {
            return -1.0d;
        }
    }

    public static long c(JSONObject jSONObject, String str) {
        if (jSONObject == null || str.length() == 0) {
            return -1;
        }
        try {
            String string = jSONObject.getString(str);
            if (string.equals(a)) {
                return -1;
            }
            try {
                return Long.parseLong(string);
            } catch (Exception unused) {
                return 0;
            }
        } catch (JSONException unused2) {
            return -1;
        }
    }

    public static boolean d(JSONObject jSONObject, String str) {
        Boolean bool;
        Boolean bool2 = Boolean.FALSE;
        if (jSONObject == null) {
            return bool2.booleanValue();
        }
        if (str.length() == 0) {
            return bool2.booleanValue();
        }
        try {
            bool = Boolean.valueOf(jSONObject.getBoolean(str));
            try {
                return bool.booleanValue();
            } catch (JSONException unused) {
                return bool.booleanValue();
            }
        } catch (JSONException unused2) {
            bool = bool2;
            return bool.booleanValue();
        }
    }

    public static String e(JSONObject jSONObject, String str) {
        String str2;
        if (jSONObject == null || str == null || str.length() == 0) {
            return null;
        }
        try {
            str2 = jSONObject.getString(str);
            try {
                return str2.equals(a) ? "" : str2;
            } catch (JSONException unused) {
                return str2;
            }
        } catch (JSONException unused2) {
            str2 = "";
            return str2;
        }
    }

    public static JSONArray f(JSONObject jSONObject, String str) {
        if (jSONObject == null || str.length() == 0) {
            return null;
        }
        try {
            return jSONObject.getJSONArray(str);
        } catch (JSONException unused) {
            return null;
        }
    }
}
