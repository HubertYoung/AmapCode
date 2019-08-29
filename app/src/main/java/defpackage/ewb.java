package defpackage;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.amap.location.common.model.AmapLoc;
import java.io.File;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: ewb reason: default package */
/* compiled from: SoDispatcher */
public class ewb {
    private static final String a = "ewb";

    /* renamed from: ewb$a */
    /* compiled from: SoDispatcher */
    public interface a {
        void a(String str);
    }

    public static void a(Context context, String str, a aVar) {
        try {
            evw a2 = evh.a("https://m.irs01.com/hmt_pro/project/so.config");
            HashMap hashMap = new HashMap();
            boolean z = false;
            if (a2.a && !TextUtils.isEmpty(a2.b)) {
                JSONArray jSONArray = new JSONArray(a2.b);
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    String optString = optJSONObject.optString("abi");
                    String optString2 = optJSONObject.optString("url");
                    String optString3 = optJSONObject.optString("version");
                    String optString4 = optJSONObject.optString("lib_name");
                    if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString2) && !TextUtils.isEmpty(optString3)) {
                        hashMap.put(optString, new evr(optString, optString2, optString3, optString4));
                    }
                }
            }
            if (hashMap.size() == 0) {
                if (str.equals("wa")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ewc.b);
                    sb.append(AmapLoc.RESULT_TYPE_SKYHOOK);
                    ewc.b = sb.toString();
                }
                return;
            }
            if (str.equals("wa")) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(ewc.b);
                sb2.append("4");
                ewc.b = sb2.toString();
            }
            String str2 = "";
            if (VERSION.SDK_INT <= 21) {
                str2 = Build.CPU_ABI;
            } else if (Build.SUPPORTED_ABIS != null && Build.SUPPORTED_ABIS.length > 0) {
                str2 = Build.SUPPORTED_ABIS[0];
            }
            evr evr = null;
            if (!TextUtils.isEmpty(str2)) {
                if (hashMap.containsKey(str2)) {
                    evr = (evr) hashMap.get(str2);
                }
            }
            if (evr == null) {
                if (str.equals("wa")) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(ewc.b);
                    sb3.append(AmapLoc.RESULT_TYPE_STANDARD);
                    ewc.b = sb3.toString();
                }
                return;
            }
            if (str.equals("wa")) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(ewc.b);
                sb4.append("5");
                ewc.b = sb4.toString();
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append(context.getFilesDir().getAbsolutePath());
            sb5.append(File.separator);
            sb5.append(evr.a);
            sb5.append(File.separator);
            sb5.append(evr.d);
            File file = new File(sb5.toString());
            boolean exists = file.exists();
            boolean z2 = true;
            if (Integer.valueOf((String) ewp.b(context, "local_so_version", "0")).intValue() < Integer.valueOf(evr.c).intValue()) {
                z = true;
            }
            if (exists && z) {
                file.delete();
                if (str.equals("wa")) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(ewc.b);
                    sb6.append("6");
                    ewc.b = sb6.toString();
                }
            } else if (str.equals("wa")) {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(ewc.b);
                sb7.append("-6");
                ewc.b = sb7.toString();
            }
            StringBuilder sb8 = new StringBuilder();
            sb8.append(context.getFilesDir().getAbsolutePath());
            sb8.append(File.separator);
            sb8.append(evr.a);
            File file2 = new File(sb8.toString());
            if (!file2.exists()) {
                file2.mkdirs();
            }
            StringBuilder sb9 = new StringBuilder();
            sb9.append(context.getFilesDir().getAbsolutePath());
            sb9.append(File.separator);
            sb9.append(evr.a);
            sb9.append(File.separator);
            sb9.append(evr.d);
            File file3 = new File(sb9.toString());
            if (!file3.exists()) {
                if (str.equals("wa")) {
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append(ewc.b);
                    sb10.append("7");
                    ewc.b = sb10.toString();
                }
                z2 = evh.a(context, evr);
            }
            if (!file3.exists() || file3.length() <= 1 || !z2) {
                if (str.equals("wa")) {
                    StringBuilder sb11 = new StringBuilder();
                    sb11.append(ewc.b);
                    sb11.append("-8");
                    ewc.b = sb11.toString();
                }
                return;
            }
            if (str.equals("wa")) {
                StringBuilder sb12 = new StringBuilder();
                sb12.append(ewc.b);
                sb12.append("8");
                ewc.b = sb12.toString();
            }
            aVar.a(file3.getAbsolutePath());
        } catch (Throwable th) {
            StringBuilder sb13 = new StringBuilder("Collected:");
            sb13.append(th.getMessage());
            euw.a(sb13.toString());
        }
    }
}
