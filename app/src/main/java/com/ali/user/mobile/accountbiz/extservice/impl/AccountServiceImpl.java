package com.ali.user.mobile.accountbiz.extservice.impl;

import android.content.Context;
import android.text.TextUtils;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.accountbiz.extservice.AccountService;
import com.ali.user.mobile.accountbiz.extservice.base.BaseExtService;
import com.ali.user.mobile.accountbiz.sp.SecurityShareStore;
import com.ali.user.mobile.db.UserInfoDaoHelper;
import com.ali.user.mobile.log.AliUserLog;
import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl extends BaseExtService implements AccountService {
    private static AccountService mAccountService;
    final String TAG = "AccountServiceImpl";

    private AccountServiceImpl(Context context) {
        super(context);
        getCurrentLoginUserId();
    }

    public static AccountService getInstance(Context context) {
        if (mAccountService == null) {
            synchronized (AccountServiceImpl.class) {
                try {
                    if (mAccountService == null) {
                        mAccountService = new AccountServiceImpl(context);
                    }
                }
            }
        }
        return mAccountService;
    }

    public UserInfo queryAccountDetailInfoByUserId(String str) {
        try {
            return UserInfoDaoHelper.a(this.mContext).b(str);
        } catch (Exception e) {
            AliUserLog.a((String) "AccountServiceImpl", (Throwable) e);
            return null;
        }
    }

    public List<UserInfo> queryAccountList() {
        try {
            return UserInfoDaoHelper.a(this.mContext).a();
        } catch (Exception e) {
            AliUserLog.a((String) "AccountServiceImpl", (Throwable) e);
            return null;
        }
    }

    public boolean addUserInfo(UserInfo userInfo) {
        try {
            return UserInfoDaoHelper.a(this.mContext).a(userInfo);
        } catch (Exception e) {
            AliUserLog.a((String) "AccountServiceImpl", (Throwable) e);
            return false;
        }
    }

    public boolean cleanLocalAccountByUserId(String str) {
        AliUserLog.c("AccountServiceImpl", "删除本地用户信息");
        try {
            return UserInfoDaoHelper.a(this.mContext).a(str);
        } catch (Exception e) {
            AliUserLog.a((String) "AccountServiceImpl", (Throwable) e);
            return false;
        }
    }

    public boolean getCurrentLoginState() {
        String a = SecurityShareStore.a(this.mContext, "currentUserLoginState");
        return a != null && "true".equals(a);
    }

    public String getCurrentLoginUserId() {
        return SecurityShareStore.a(this.mContext, "currentUserId");
    }

    public String getCurrentLoginLogonId() {
        return SecurityShareStore.a(this.mContext, "currentLogonId");
    }

    public void setCurrentLoginState(String str) {
        String valueOf = String.valueOf(getCurrentLoginState());
        AliUserLog.c("AccountServiceImpl", String.format("new state: %s , old state: %s", new Object[]{str, valueOf}));
        if (!valueOf.equalsIgnoreCase(str)) {
            SecurityShareStore.a(this.mContext, (String) "currentUserLoginState", str);
        }
    }

    public void setCurrentLoginUserId(String str) {
        SecurityShareStore.a(this.mContext, (String) "currentUserId", str);
    }

    public void setCurrentLoginLogonId(String str) {
        SecurityShareStore.a(this.mContext, (String) "currentLogonId", str);
    }

    public List<UserInfo> getLoginedAlipayUser() {
        List<UserInfo> queryAccountList = queryAccountList();
        if (queryAccountList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (UserInfo next : queryAccountList) {
            if (!TextUtils.isEmpty(next.getLoginTime())) {
                arrayList.add(next);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList;
    }

    public List<UserInfo> getAutoLoginAlipayUser() {
        List<UserInfo> loginedAlipayUser = getLoginedAlipayUser();
        if (loginedAlipayUser == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (UserInfo next : loginedAlipayUser) {
            if (next.isAutoLogin()) {
                arrayList.add(next);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList;
    }

    public UserInfo getUserInfoBySql(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            return UserInfoDaoHelper.a(this.mContext).a(null, str2);
        }
        if (!TextUtils.isEmpty(str)) {
            return UserInfoDaoHelper.a(this.mContext).a(str, null);
        }
        String currentLoginUserId = getCurrentLoginUserId();
        if (TextUtils.isEmpty(currentLoginUserId)) {
            return null;
        }
        return UserInfoDaoHelper.a(this.mContext).a(currentLoginUserId, null);
    }
}
