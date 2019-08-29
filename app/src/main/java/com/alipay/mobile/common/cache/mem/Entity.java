package com.alipay.mobile.common.cache.mem;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class Entity<T> {
    protected String mGroup;
    protected String mOwner;
    protected T mValue;

    public Entity(String owner, String group, T value) {
        if (owner == null) {
            this.mOwner = "-";
        } else {
            this.mOwner = owner;
        }
        this.mGroup = group;
        this.mValue = value;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
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

    public boolean authenticate(String owner) {
        if (this.mOwner.equalsIgnoreCase("-")) {
            return true;
        }
        if (owner == null) {
            LoggerFactory.getTraceLogger().warn((String) "Entity", (String) "authenticate: owner is null");
            return false;
        } else if (!owner.contains(this.mOwner)) {
            return false;
        } else {
            return true;
        }
    }

    public String toString() {
        return String.format("value: %s", new Object[]{this.mValue.toString()});
    }
}
