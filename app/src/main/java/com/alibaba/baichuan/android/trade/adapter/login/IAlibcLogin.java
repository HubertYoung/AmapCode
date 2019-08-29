package com.alibaba.baichuan.android.trade.adapter.login;

import android.app.Activity;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;

public interface IAlibcLogin {
    Session getSession();

    boolean isLogin();

    void logout(Activity activity, LogoutCallback logoutCallback);

    void showLogin(Activity activity, AlibcLoginCallback alibcLoginCallback);
}
