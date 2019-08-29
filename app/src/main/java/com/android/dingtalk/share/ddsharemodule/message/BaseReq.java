package com.android.dingtalk.share.ddsharemodule.message;

import android.os.Bundle;
import com.android.dingtalk.share.ddsharemodule.ShareConstant;

public abstract class BaseReq {
    public String mTransaction;

    public abstract boolean checkArgs();

    public abstract int getType();

    public void toBundle(Bundle bundle) {
        bundle.putInt(ShareConstant.EXTRA_COMMAND_TYPE, getType());
        bundle.putString(ShareConstant.EXTRA_BASEREQ_TRANSACTION, this.mTransaction);
    }

    public void fromBundle(Bundle bundle) {
        this.mTransaction = bundle.getString(ShareConstant.EXTRA_BASEREQ_TRANSACTION);
    }
}
