package com.alibaba.baichuan.android.trade.c.a.a.a;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.c.a.a.a.d.a;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class f implements b {
    private a a;
    private Pattern b;
    private String c;
    private String d;
    private boolean e;
    private String f;
    private String g;

    public f(a aVar) {
        String str = (String) aVar.c.get("regexp");
        if (str != null) {
            this.b = Pattern.compile(str);
        }
        this.c = (String) aVar.c.get("key");
        this.d = (String) aVar.c.get("value");
        this.g = (String) aVar.c.get(Constants.KEY_MODE);
        this.e = "true".equals(aVar.c.get("cacheable"));
        this.a = aVar;
    }

    private Map b(c cVar) {
        HashMap hashMap = new HashMap();
        if (this.a.c != null) {
            hashMap.putAll(this.a.c);
        }
        hashMap.putAll(cVar.a());
        return hashMap;
    }

    public boolean a(c cVar) {
        String str;
        String str2;
        if (this.c == null) {
            return false;
        }
        String a2 = cVar.a(this.c);
        if ((("addIfAbsent".equals(this.g) || "update".equals(this.g)) && a2 == null) || "append".equals(this.g)) {
            if (!"addAllParams".equals(this.c)) {
                if (!this.e || this.f == null || TextUtils.isEmpty(this.f)) {
                    str = com.alibaba.baichuan.android.trade.c.a.a.a.a(this.d, b(cVar));
                    if (this.e) {
                        this.f = str;
                    }
                } else {
                    str = this.f;
                }
                if (str != null) {
                    cVar.c(this.c, str);
                }
            } else if (cVar.a() != null) {
                for (String str3 : cVar.a().keySet()) {
                    if (!TextUtils.isEmpty((CharSequence) cVar.a().get(str3))) {
                        cVar.c(str3, (String) cVar.a().get(str3));
                    }
                }
            }
        } else if (("replace".equals(this.g) || "update".equals(this.g)) && a2 != null) {
            if (!this.e || this.f == null) {
                Map b2 = b(cVar);
                if (this.b != null) {
                    Matcher matcher = this.b.matcher(a2);
                    if (matcher.matches()) {
                        int groupCount = matcher.groupCount();
                        for (int i = 1; i <= groupCount; i++) {
                            b2.put("group_".concat(String.valueOf(i)), matcher.group(i));
                        }
                    }
                    b2.put("group_0", a2);
                }
                str2 = com.alibaba.baichuan.android.trade.c.a.a.a.a(this.d, b2);
                if (this.e) {
                    this.f = str2;
                }
            } else {
                str2 = this.f;
            }
            if (str2 != null) {
                cVar.b(this.c, str2);
                return true;
            }
        } else if ("delete".equals(this.g)) {
            cVar.e(this.c);
            return true;
        } else {
            StringBuilder sb = new StringBuilder("ignore the action ");
            sb.append(this.g);
            sb.append(" key ");
            sb.append(this.c);
            AlibcLogger.i("ui", sb.toString());
            return true;
        }
        return true;
    }
}
