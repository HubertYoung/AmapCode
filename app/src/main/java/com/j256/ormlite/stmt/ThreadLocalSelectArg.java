package com.j256.ormlite.stmt;

import com.j256.ormlite.field.SqlType;

public class ThreadLocalSelectArg extends BaseArgumentHolder implements ArgumentHolder {
    private ThreadLocal<ValueWrapper> threadValue = new ThreadLocal<>();

    class ValueWrapper {
        Object value;

        public ValueWrapper(Object value2) {
            this.value = value2;
        }
    }

    public ThreadLocalSelectArg() {
    }

    public ThreadLocalSelectArg(String columnName, Object value) {
        super(columnName);
        setValue(value);
    }

    public ThreadLocalSelectArg(SqlType sqlType, Object value) {
        super(sqlType);
        setValue(value);
    }

    public ThreadLocalSelectArg(Object value) {
        setValue(value);
    }

    /* access modifiers changed from: protected */
    public Object getValue() {
        ValueWrapper wrapper = this.threadValue.get();
        if (wrapper == null) {
            return null;
        }
        return wrapper.value;
    }

    public void setValue(Object value) {
        this.threadValue.set(new ValueWrapper(value));
    }

    /* access modifiers changed from: protected */
    public boolean isValueSet() {
        return this.threadValue.get() != null;
    }
}
