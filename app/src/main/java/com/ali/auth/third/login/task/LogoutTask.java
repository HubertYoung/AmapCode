package com.ali.auth.third.login.task;

import android.app.Activity;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.task.InitWaitTask;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.login.callback.LogoutCallback;

public class LogoutTask extends InitWaitTask {
    public LogoutTask(Activity activity, LogoutCallback logoutCallback) {
        super(activity, logoutCallback, new e(logoutCallback), "E_LOGOUT");
    }

    public void doWhenException(Throwable th) {
        CommonUtils.onFailure(this.failureCallback, ResultCode.create(10010, th.getMessage()));
    }
}
