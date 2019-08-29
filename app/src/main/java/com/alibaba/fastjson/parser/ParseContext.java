package com.alibaba.fastjson.parser;

import java.lang.reflect.Type;

public class ParseContext {
    public final Object fieldName;
    public Object object;
    public final ParseContext parent;
    private transient String path;
    public Type type;

    public ParseContext(ParseContext parseContext, Object obj, Object obj2) {
        this.parent = parseContext;
        this.object = obj;
        this.fieldName = obj2;
    }

    public String toString() {
        if (this.path == null) {
            if (this.parent == null) {
                this.path = "$";
            } else if (this.fieldName instanceof Integer) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.parent.toString());
                sb.append("[");
                sb.append(this.fieldName);
                sb.append("]");
                this.path = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.parent.toString());
                sb2.append(".");
                sb2.append(this.fieldName);
                this.path = sb2.toString();
            }
        }
        return this.path;
    }
}
