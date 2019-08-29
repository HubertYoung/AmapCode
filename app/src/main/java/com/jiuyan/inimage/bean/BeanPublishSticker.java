package com.jiuyan.inimage.bean;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.Serializable;

public class BeanPublishSticker implements Serializable {
    private static final long serialVersionUID = 1;
    public String f;
    public String from;
    public String fromWhere = "0";
    public String h;
    public String id;
    public String r;
    public String s;
    public String sort;
    public String text;
    public int textColor;
    public String url;
    public String w;
    public String x;
    public String y;

    public BeanPublishSticker() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
