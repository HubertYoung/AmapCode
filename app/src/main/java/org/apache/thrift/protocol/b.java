package org.apache.thrift.protocol;

import com.j256.ormlite.stmt.query.SimpleComparison;

public class b {
    public final String a;
    public final byte b;
    public final short c;

    public b() {
        this("", 0, 0);
    }

    public b(String str, byte b2, short s) {
        this.a = str;
        this.b = b2;
        this.c = s;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("<TField name:'");
        sb.append(this.a);
        sb.append("' type:");
        sb.append(this.b);
        sb.append(" field-id:");
        sb.append(this.c);
        sb.append(SimpleComparison.GREATER_THAN_OPERATION);
        return sb.toString();
    }
}
