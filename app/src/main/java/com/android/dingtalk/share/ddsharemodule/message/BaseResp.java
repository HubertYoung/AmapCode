package com.android.dingtalk.share.ddsharemodule.message;

import android.os.Bundle;
import com.android.dingtalk.share.ddsharemodule.ShareConstant;

public abstract class BaseResp {
    public int mErrCode;
    public String mErrStr;
    public String mTransaction;

    public interface ErrCode {
        public static final int ERR_AUTH_DENIED = -4;
        public static final int ERR_COMM = -1;
        public static final int ERR_OK = 0;
        public static final int ERR_SENT_FAILED = -3;
        public static final int ERR_UNSUPPORT = -5;
        public static final int ERR_USER_CANCEL = -2;
    }

    /* access modifiers changed from: 0000 */
    public abstract boolean checkArgs();

    public abstract int getType();

    public void toBundle(Bundle bundle) {
        bundle.putInt(ShareConstant.EXTRA_COMMAND_TYPE, getType());
        bundle.putInt(ShareConstant.EXTRA_BASEREQ_ERROR_CODE, this.mErrCode);
        bundle.putString(ShareConstant.EXTRA_BASEREQ_ERROR_STRING, this.mErrStr);
        bundle.putString(ShareConstant.EXTRA_BASEREQ_TRANSACTION, this.mTransaction);
    }

    public void fromBundle(Bundle bundle) {
        this.mErrCode = bundle.getInt(ShareConstant.EXTRA_BASEREQ_ERROR_CODE);
        this.mErrStr = bundle.getString(ShareConstant.EXTRA_BASEREQ_ERROR_STRING);
        this.mTransaction = bundle.getString(ShareConstant.EXTRA_BASEREQ_TRANSACTION);
    }
}
