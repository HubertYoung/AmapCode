package com.jiuyan.inimage.bean;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.List;

public class BeanBaseOneKeyFacePaster extends BaseBean {
    public DataOneKeyFacePaster data;

    public class DataOneKeyFacePaster {
        public int curRowPos;
        public boolean is_show;
        public List<RowPaster> list;

        public DataOneKeyFacePaster() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public class ItemOneKeyPaster {
        public String pcid;
        public String pid;

        public ItemOneKeyPaster() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public class RowPaster {
        public List<ItemOneKeyPaster> get_list;
        public String id;
        public List<List<BeanFacePaster>> list;
        public String play_type;

        public RowPaster() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public BeanBaseOneKeyFacePaster() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
