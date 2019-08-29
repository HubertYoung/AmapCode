package org.xidea.el;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class JsonType implements ParameterizedType {
    private Type[] a;
    private Type b;

    public Type getOwnerType() {
        return null;
    }

    public JsonType(Type type, Type... typeArr) {
        this.a = typeArr;
        this.b = type;
    }

    public Type[] getActualTypeArguments() {
        return this.a;
    }

    public Type getRawType() {
        return this.b;
    }
}
