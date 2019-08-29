package com.jiuyan.inimage.bean;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.List;

public class BeanRecRelation extends BaseBean {
    public List<DataEntity> data;

    public class DataEntity {
        public String cate_id;
        public String cate_name;
        public List<BeanDataPaster> list;

        public DataEntity() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public BeanRecRelation() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
