package defpackage;

import android.app.Application;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* renamed from: me reason: default package */
/* compiled from: CloudResModel */
final class me {
    ArrayList<a> a = new ArrayList<>();

    /* renamed from: me$a */
    /* compiled from: CloudResModel */
    static class a {
        String a;
        String b;
        String c;
        int d;
        int e;
        String f;
        String g;
        int h = -1;

        a(String str, String str2, String str3, int i, int i2) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = i;
            this.e = i2;
            StringBuilder sb = new StringBuilder();
            sb.append(mf.a().a);
            sb.append(File.separator);
            sb.append(str);
            this.f = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(mf.a().a);
            sb2.append(File.separator);
            sb2.append(str);
            sb2.append(i2);
            sb2.append(FilePathHelper.SUFFIX_DOT_ZIP);
            this.g = sb2.toString();
            c();
        }

        a(String str, String str2, String str3, int i, int i2, String str4) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = i;
            this.e = i2;
            this.f = str4;
            StringBuilder sb = new StringBuilder();
            sb.append(mf.a().a);
            sb.append(File.separator);
            sb.append(str);
            sb.append(i2);
            sb.append(FilePathHelper.SUFFIX_DOT_ZIP);
            this.g = sb.toString();
            c();
        }

        private void c() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.f);
            sb.append(File.separator);
            sb.append("item.cloudVersion");
            String readData = FileUtil.readData(sb.toString());
            if (!TextUtils.isEmpty(readData)) {
                try {
                    this.h = Integer.parseInt(readData);
                } catch (Exception unused) {
                }
            }
        }

        /* renamed from: a */
        public final a clone() {
            a aVar = new a(this.a, this.b, this.c, this.d, this.e, this.f);
            return aVar;
        }

        /* access modifiers changed from: 0000 */
        public final boolean b() {
            File file = new File(this.f);
            if (!file.exists() || !file.isDirectory()) {
                return false;
            }
            String[] list = file.list();
            if (list == null || list.length <= 0) {
                return false;
            }
            return true;
        }
    }

    me(String str) {
        b(str);
    }

    private void b(String str) {
        JSONObject parseObject = JSON.parseObject(str);
        if (parseObject != null) {
            Set<String> keySet = parseObject.keySet();
            if (keySet != null) {
                for (String next : keySet) {
                    JSONObject jSONObject = parseObject.getJSONObject(next);
                    if (jSONObject != null) {
                        int intValue = jSONObject.getIntValue("type");
                        int intValue2 = jSONObject.getIntValue("version");
                        JSONObject jSONObject2 = jSONObject.getJSONObject("file");
                        if (jSONObject2 != null) {
                            String string = jSONObject2.getString("url");
                            String string2 = jSONObject2.getString("md5");
                            if ("h5_template".equalsIgnoreCase(next)) {
                                ArrayList<a> arrayList = this.a;
                                Application application = AMapAppGlobal.getApplication();
                                File dir = application.getDir("webtemplate", 0);
                                if (dir == null) {
                                    StringBuilder sb = new StringBuilder("/data/data/");
                                    sb.append(application.getPackageName());
                                    sb.append("/app_webtemplate");
                                    dir = new File(sb.toString());
                                }
                                a aVar = new a(next, string, string2, intValue, intValue2, new File(dir, "websets").getAbsolutePath());
                                arrayList.add(aVar);
                            } else {
                                ArrayList<a> arrayList2 = this.a;
                                a aVar2 = new a(next, string, string2, intValue, intValue2);
                                arrayList2.add(aVar2);
                            }
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final a a(String str) {
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.a.equalsIgnoreCase(str)) {
                return next;
            }
        }
        return null;
    }
}
