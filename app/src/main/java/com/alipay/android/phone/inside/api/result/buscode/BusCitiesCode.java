package com.alipay.android.phone.inside.api.result.buscode;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class BusCitiesCode extends ResultCode {
    public static final BusCitiesCode FAILED = new BusCitiesCode("bus_cities_8000", "获取失败。");
    public static final BusCitiesCode PARAMS_ILLEGAL = new BusCitiesCode("bus_cities_8001", "参数非法。");
    public static final BusCitiesCode SUCCESS = new BusCitiesCode("bus_cities_9000", "获取成功。");
    private static final List<BusCitiesCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
    }

    protected BusCitiesCode(String str, String str2) {
        super(str, str2);
    }

    public static BusCitiesCode parse(String str) {
        for (BusCitiesCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
