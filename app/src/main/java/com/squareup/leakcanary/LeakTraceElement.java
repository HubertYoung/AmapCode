package com.squareup.leakcanary;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class LeakTraceElement implements Serializable {
    public final String className;
    public final String extra;
    public final List<String> fields;
    public final Holder holder;
    public final String referenceName;
    public final Type type;

    public enum Holder {
        OBJECT,
        CLASS,
        THREAD,
        ARRAY
    }

    public enum Type {
        INSTANCE_FIELD,
        STATIC_FIELD,
        LOCAL
    }

    LeakTraceElement(String str, Type type2, Holder holder2, String str2, String str3, List<String> list) {
        this.referenceName = str;
        this.type = type2;
        this.holder = holder2;
        this.className = str2;
        this.extra = str3;
        this.fields = Collections.unmodifiableList(new ArrayList(list));
    }

    public final String toString() {
        String str;
        String str2 = "";
        if (this.type == Type.STATIC_FIELD) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("static ");
            str2 = sb.toString();
        }
        if (this.holder == Holder.ARRAY || this.holder == Holder.THREAD) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(this.holder.name().toLowerCase(Locale.US));
            sb2.append(Token.SEPARATOR);
            str2 = sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append(this.className);
        String sb4 = sb3.toString();
        if (this.referenceName != null) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append(".");
            sb5.append(this.referenceName);
            str = sb5.toString();
        } else {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(sb4);
            sb6.append(" instance");
            str = sb6.toString();
        }
        if (this.extra == null) {
            return str;
        }
        StringBuilder sb7 = new StringBuilder();
        sb7.append(str);
        sb7.append(Token.SEPARATOR);
        sb7.append(this.extra);
        return sb7.toString();
    }

    public final String toDetailedString() {
        String str;
        if (this.holder == Holder.ARRAY) {
            StringBuilder sb = new StringBuilder();
            sb.append("* ");
            sb.append("Array of");
            str = sb.toString();
        } else if (this.holder == Holder.CLASS) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("* ");
            sb2.append("Class");
            str = sb2.toString();
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("* ");
            sb3.append("Instance of");
            str = sb3.toString();
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append(Token.SEPARATOR);
        sb4.append(this.className);
        sb4.append("\n");
        String sb5 = sb4.toString();
        for (String append : this.fields) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(sb5);
            sb6.append("|   ");
            sb6.append(append);
            sb6.append("\n");
            sb5 = sb6.toString();
        }
        return sb5;
    }
}
