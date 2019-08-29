package com.alipay.android.phone.inside.api.result.wallet;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class WalletPreloadCode extends ResultCode {
    public static final WalletPreloadCode FAILED = new WalletPreloadCode("wallet_preload_8000", "预加载失败");
    public static final WalletPreloadCode SUCCESS = new WalletPreloadCode("wallet_preload_9000", "预加载成功");
    private static final List<WalletPreloadCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    protected WalletPreloadCode(String str, String str2) {
        super(str, str2);
    }

    public static WalletPreloadCode parse(String str) {
        for (WalletPreloadCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
