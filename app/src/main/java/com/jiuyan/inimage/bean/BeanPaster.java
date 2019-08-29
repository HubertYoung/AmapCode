package com.jiuyan.inimage.bean;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.Serializable;

public class BeanPaster implements Serializable {
    public static final String PASTER_TYPE_CUSTOM_PASTER = "diy";
    public static final String PASTER_TYPE_TEXT = "text";
    public String fromWhere;
    public String id;
    public BeanAKeyUseLocation location;
    public String name;
    public String type;
    public String url;

    public BeanPaster() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
