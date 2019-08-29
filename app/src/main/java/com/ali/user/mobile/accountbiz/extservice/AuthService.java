package com.ali.user.mobile.accountbiz.extservice;

import android.os.Bundle;
import com.ali.user.mobile.account.bean.UserInfo;

public interface AuthService {
    @Deprecated
    public static final boolean LOGIN_REALSTATE = false;

    boolean auth();

    boolean auth(Bundle bundle);

    Bundle autoAuth();

    void clearLoginUserInfo();

    UserInfo gestureGetUserInfo();

    UserInfo getLastLoginedUserInfo();

    UserInfo getLoginUserInfo();

    boolean getTaoBaoSsoFlag();

    UserInfo getUserInfo();

    boolean isLogin();

    void logLoginKey();

    void notifyUnlockGestureApp();

    void notifyUnlockLoginApp(boolean z, boolean z2);

    UserInfo queryLatelyLoginUser();

    boolean rpcAuth();

    boolean rpcAuth(boolean z);

    void setTaoBaoSsoFlag(boolean z);

    boolean showActivityLogin(Bundle bundle, UserInfo userInfo);
}
