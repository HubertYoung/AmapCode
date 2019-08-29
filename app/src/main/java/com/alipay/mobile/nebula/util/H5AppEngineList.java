package com.alipay.mobile.nebula.util;

import com.alipay.mobile.liteprocess.Const;
import java.util.ArrayList;
import java.util.List;

public class H5AppEngineList {
    public static List<String> appEngineList;

    static {
        ArrayList arrayList = new ArrayList();
        appEngineList = arrayList;
        arrayList.add("H5App");
        appEngineList.add(Const.TYPE_RN);
        appEngineList.add("BNApp");
    }
}
