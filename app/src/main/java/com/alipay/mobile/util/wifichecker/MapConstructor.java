package com.alipay.mobile.util.wifichecker;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.Serializable;

public class MapConstructor implements Serializable {
    private static final long serialVersionUID = -2342245045465028952L;
    private String key;
    private String value;

    public MapConstructor() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }
}
