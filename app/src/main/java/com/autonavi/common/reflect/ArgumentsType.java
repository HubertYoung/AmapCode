package com.autonavi.common.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class ArgumentsType implements ParameterizedType {
    protected Type[] mArguments;
    protected Type mOwnerType;
    protected Type mRawType;

    public ArgumentsType() {
    }

    public ArgumentsType(Type[] typeArr, Type type, Type type2) {
        this.mArguments = typeArr;
        this.mOwnerType = type;
        this.mRawType = type2;
    }

    public Type[] getActualTypeArguments() {
        return this.mArguments;
    }

    public void setActualTypeArguments(Type[] typeArr) {
        this.mArguments = typeArr;
    }

    public void setActualTypeArguments(List<Type> list) {
        this.mArguments = (Type[]) list.toArray();
    }

    public Type getOwnerType() {
        return this.mOwnerType;
    }

    public void setOwnerType(Type type) {
        this.mOwnerType = type;
    }

    public Type getRawType() {
        return this.mRawType;
    }

    public void setRawType(Type type) {
        this.mRawType = type;
    }

    public void clear() {
        this.mOwnerType = null;
        this.mRawType = null;
        if (this.mArguments != null) {
            Arrays.fill(this.mArguments, null);
            this.mArguments = null;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ParameterizedType) || this.mOwnerType == null || this.mRawType == null || this.mArguments == null) {
            return false;
        }
        ParameterizedType parameterizedType = (ParameterizedType) obj;
        return this.mOwnerType.equals(parameterizedType.getOwnerType()) && this.mRawType.equals(parameterizedType.getRawType()) && Arrays.equals(this.mArguments, parameterizedType.getActualTypeArguments());
    }
}
