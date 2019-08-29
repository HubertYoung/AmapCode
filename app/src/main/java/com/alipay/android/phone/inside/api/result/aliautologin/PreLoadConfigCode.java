package com.alipay.android.phone.inside.api.result.aliautologin;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class PreLoadConfigCode extends ResultCode {
    public static final PreLoadConfigCode FAILED = new PreLoadConfigCode("aal_pre_load_config_8000", "preloadfailed");
    public static final PreLoadConfigCode SUCCESS = new PreLoadConfigCode("aal_pre_load_config_9000", "preloadsuccess");
    private static final List<PreLoadConfigCode> mCodeList;

    protected PreLoadConfigCode(String str, String str2) {
        super(str, str2);
    }

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    public static PreLoadConfigCode parse(String str) {
        for (PreLoadConfigCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
