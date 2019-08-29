package defpackage;

import java.util.HashMap;
import java.util.Map.Entry;

/* renamed from: avw reason: default package */
/* compiled from: ActivityEntity */
public final class avw {
    public HashMap<String, String> a;

    public final String toString() {
        if (this.a == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Entry next : this.a.entrySet()) {
            String str = (String) next.getKey();
            String str2 = (String) next.getValue();
            if (stringBuffer.length() != 0) {
                stringBuffer.append("+");
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("=");
            sb.append(str2);
            stringBuffer.append(sb.toString());
        }
        return stringBuffer.toString();
    }
}
