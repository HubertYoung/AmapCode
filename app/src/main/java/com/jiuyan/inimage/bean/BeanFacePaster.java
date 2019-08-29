package com.jiuyan.inimage.bean;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.Serializable;
import java.util.HashMap;

public class BeanFacePaster implements Serializable {
    public String author;
    public HashMap<String, String> face_param;
    public String from;
    public String id;
    public String name;
    public String url;

    public BeanFacePaster() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean equals(Object obj) {
        if (this != obj && !this.id.equals(((BeanFacePaster) obj).id)) {
            return false;
        }
        return true;
    }
}
