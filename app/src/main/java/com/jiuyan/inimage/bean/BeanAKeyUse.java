package com.jiuyan.inimage.bean;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.List;

public class BeanAKeyUse extends BaseBean {
    public BeanData data;

    public class BeanData {
        public List<PasterItem> paster;

        public BeanData() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public class PasterItem {
        public String from;
        public String id;
        public BeanAKeyUseLocation location;
        public String name;
        public String url;

        public PasterItem() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public BeanAKeyUse() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
