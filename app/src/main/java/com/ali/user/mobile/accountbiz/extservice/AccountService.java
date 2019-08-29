package com.ali.user.mobile.accountbiz.extservice;

import com.ali.user.mobile.account.bean.UserInfo;
import java.util.List;

public interface AccountService {
    boolean addUserInfo(UserInfo userInfo);

    boolean cleanLocalAccountByUserId(String str);

    List<UserInfo> getAutoLoginAlipayUser();

    String getCurrentLoginLogonId();

    boolean getCurrentLoginState();

    String getCurrentLoginUserId();

    List<UserInfo> getLoginedAlipayUser();

    UserInfo getUserInfoBySql(String str, String str2);

    UserInfo queryAccountDetailInfoByUserId(String str);

    List<UserInfo> queryAccountList();

    void setCurrentLoginLogonId(String str);

    void setCurrentLoginState(String str);

    void setCurrentLoginUserId(String str);
}
