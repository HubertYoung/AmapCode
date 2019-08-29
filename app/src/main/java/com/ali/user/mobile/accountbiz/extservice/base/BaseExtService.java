package com.ali.user.mobile.accountbiz.extservice.base;

import android.content.Context;
import com.ali.user.mobile.AliUserInit;

public abstract class BaseExtService {
    protected Context mContext;

    protected BaseExtService(Context context) {
        this.mContext = context == null ? AliUserInit.b() : context;
    }
}
