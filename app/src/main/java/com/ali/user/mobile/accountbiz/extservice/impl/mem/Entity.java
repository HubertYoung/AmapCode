package com.ali.user.mobile.accountbiz.extservice.impl.mem;

import com.ali.user.mobile.log.AliUserLog;

public class Entity<T> {
    protected String mGroup;
    protected String mOwner;
    protected T mValue;

    public Entity(String str, String str2, T t) {
        if (str == null) {
            this.mOwner = "-";
        } else {
            this.mOwner = str;
        }
        this.mGroup = str2;
        this.mValue = t;
    }

    public String getOwner() {
        return this.mOwner;
    }

    public String getGroup() {
        return this.mGroup;
    }

    public T getValue() {
        return this.mValue;
    }

    public boolean authenticate(String str) {
        if (this.mOwner.equalsIgnoreCase("-")) {
            return true;
        }
        if (str != null) {
            return str.contains(this.mOwner);
        }
        AliUserLog.d("Entity", "authenticate: owner is null");
        return false;
    }

    public String toString() {
        return String.format("value: %s", new Object[]{this.mValue.toString()});
    }
}
