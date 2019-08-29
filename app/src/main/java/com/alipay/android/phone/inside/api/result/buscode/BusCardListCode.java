package com.alipay.android.phone.inside.api.result.buscode;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class BusCardListCode extends ResultCode {
    public static final BusCardListCode FAILED = new BusCardListCode("bus_cardlist_8000", "获取失败。");
    public static final BusCardListCode PARAMS_ILLEGAL = new BusCardListCode("bus_cardlist_8001", "获取失败，参数非法。");
    public static final BusCardListCode SUCCESS = new BusCardListCode("bus_cardlist_9000", "成功。");
    public static final BusCardListCode UNAUTH = new BusCardListCode("bus_cardlist_7000", "未授权。");
    private static final List<BusCardListCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(UNAUTH);
    }

    protected BusCardListCode(String str, String str2) {
        super(str, str2);
    }

    public static BusCardListCode parse(String str) {
        for (BusCardListCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
