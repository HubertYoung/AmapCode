package com.alipay.android.phone.inside.api.result.buscode;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class BusAllCardListCode extends ResultCode {
    public static final BusAllCardListCode FAILED = new BusAllCardListCode("bus_all_cardlist_8000", "获取失败。");
    public static final BusAllCardListCode PARAMS_ILLEGAL = new BusAllCardListCode("bus_all_cardlist_8001", "获取失败，参数非法。");
    public static final BusAllCardListCode SUCCESS = new BusAllCardListCode("bus_all_cardlist_9000", "获取成功。");
    public static final BusAllCardListCode UNAUTH = new BusAllCardListCode("bus_all_cardlist_7000", "未授权。");
    private static final List<BusAllCardListCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(UNAUTH);
    }

    protected BusAllCardListCode(String str, String str2) {
        super(str, str2);
    }

    public static BusAllCardListCode parse(String str) {
        for (BusAllCardListCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
