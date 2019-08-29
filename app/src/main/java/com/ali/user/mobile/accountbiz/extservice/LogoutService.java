package com.ali.user.mobile.accountbiz.extservice;

import android.app.Activity;
import com.ali.user.mobile.login.CommonNotifyCaller;

public interface LogoutService {
    void logout(CommonNotifyCaller commonNotifyCaller);

    void showChangeAccountDialog(Activity activity);
}
