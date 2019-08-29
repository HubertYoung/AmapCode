package com.jiuyan.inimage.bean;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.Serializable;

public class BeanDataPaster implements Serializable {
    public String author;
    public String from;
    public String id;
    public String name;
    public String thumb_url;
    public String url;

    public BeanDataPaster() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean equals(Object obj) {
        if (this != obj && !this.id.equals(((BeanDataPaster) obj).id)) {
            return false;
        }
        return true;
    }
}
