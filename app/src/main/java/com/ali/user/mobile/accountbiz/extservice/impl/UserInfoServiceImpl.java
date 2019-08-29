package com.ali.user.mobile.accountbiz.extservice.impl;

import android.content.Context;
import com.ali.user.mobile.account.SecurityCache;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.account.bean.UserMode;
import com.ali.user.mobile.accountbiz.extservice.UserInfoService;
import com.ali.user.mobile.accountbiz.extservice.base.BaseExtService;
import com.ali.user.mobile.db.UserInfoDaoHelper;
import com.ali.user.mobile.log.AliUserLog;

public class UserInfoServiceImpl extends BaseExtService implements UserInfoService {
    private static UserInfoService mUserInfoService;
    final String TAG = "UserInfoServiceImpl";

    private UserInfoServiceImpl(Context context) {
        super(context);
    }

    public static UserInfoService getInstance(Context context) {
        if (mUserInfoService == null) {
            synchronized (SecurityInitServiceImpl.class) {
                try {
                    if (mUserInfoService == null) {
                        mUserInfoService = new UserInfoServiceImpl(context);
                    }
                }
            }
        }
        return mUserInfoService;
    }

    public UserMode queryUserInfo(String str) {
        UserInfo userInfo;
        UserMode userMode = new UserMode();
        try {
            userInfo = UserInfoDaoHelper.a(this.mContext).b(str);
        } catch (Exception e) {
            AliUserLog.a((String) "StackTrace", (Throwable) e);
            userInfo = null;
        }
        AliUserLog.c("UserInfoServiceImpl", "用户信息查询完成");
        userMode.setUserInfo(userInfo);
        userMode.setUserExtInfo(SecurityCache.a);
        return userMode;
    }
}
