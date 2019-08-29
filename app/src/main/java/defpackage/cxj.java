package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cxj reason: default package */
/* compiled from: EvaluateHelper */
public final class cxj {

    /* renamed from: cxj$a */
    /* compiled from: EvaluateHelper */
    static class a {
        static String a(String str) {
            if (str == null) {
                return "<n>";
            }
            try {
                char[] charArray = str.toCharArray();
                for (int length = charArray.length - 1; length >= 0; length--) {
                    if (charArray[length] >= 'A' && charArray[length] <= 'Z') {
                        charArray[length] = (char) (charArray[length] + 3);
                        if (charArray[length] > 'Z') {
                            charArray[length] = (char) (((charArray[length] - 'Z') + 65) - 1);
                        } else if (charArray[length] < 'A') {
                            charArray[length] = (char) ((90 - ('A' - charArray[length])) + 1);
                        }
                    } else if (charArray[length] >= 'a' && charArray[length] <= 'z') {
                        charArray[length] = (char) (charArray[length] + 3);
                        if (charArray[length] > 'z') {
                            charArray[length] = (char) (((charArray[length] - 'z') + 97) - 1);
                        } else if (charArray[length] < 'a') {
                            charArray[length] = (char) ((122 - ('a' - charArray[length])) + 1);
                        }
                    }
                }
                for (int i = 0; i < charArray.length; i++) {
                    charArray[i] = (char) (charArray[i] ^ 5);
                }
                return new String(charArray);
            } catch (Exception unused) {
                return str;
            }
        }
    }

    /* renamed from: cxj$b */
    /* compiled from: EvaluateHelper */
    public static class b {
        static String a(String str) {
            if (str == null) {
                return null;
            }
            return new cwp().a(str.getBytes());
        }
    }

    /* renamed from: cxj$c */
    /* compiled from: EvaluateHelper */
    public static class c {
        public static void a(@NonNull JSONObject jSONObject, @NonNull String str, @Nullable String str2) {
            if (str2 != null) {
                try {
                    jSONObject.put(str, str2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
