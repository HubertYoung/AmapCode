package com.ali.user.mobile.db;

import android.content.Context;
import android.text.TextUtils;
import com.ali.user.mobile.account.AuthUtil;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.account.dao.IUserInfoDao;
import com.ali.user.mobile.account.dao.UserInfoCache;
import com.ali.user.mobile.log.AliUserLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserInfoDaoHelper {
    private static UserInfoDaoHelper a;
    private IUserInfoDao b;

    private UserInfoDaoHelper(Context context) {
        this.b = UserInfoDao.a(context);
    }

    public static synchronized UserInfoDaoHelper a(Context context) {
        UserInfoDaoHelper userInfoDaoHelper;
        synchronized (UserInfoDaoHelper.class) {
            try {
                if (a == null) {
                    a = new UserInfoDaoHelper(context);
                }
                userInfoDaoHelper = a;
            }
        }
        return userInfoDaoHelper;
    }

    public final synchronized UserInfo a(String str, String str2) {
        UserInfo userInfo;
        UserInfo userInfo2 = null;
        if (!TextUtils.isEmpty(str) && UserInfoCache.a != null) {
            userInfo2 = UserInfoCache.a.get(str);
            if (userInfo2 != null) {
                AliUserLog.c("SecurityDbHelper", "从缓存获取用户信息");
                return userInfo2;
            }
        }
        try {
            userInfo = this.b.a(str, str2);
            try {
                if (UserInfoCache.a == null) {
                    UserInfoCache.a = new HashMap<>();
                }
                if (userInfo != null) {
                    UserInfoCache.a.put(str, userInfo);
                }
            } catch (Throwable th) {
                th = th;
                AliUserLog.a((String) "SecurityDbHelper", th);
                return userInfo;
            }
        } catch (Throwable th2) {
            th = th2;
            userInfo = userInfo2;
            AliUserLog.a((String) "SecurityDbHelper", th);
            return userInfo;
        }
    }

    public final synchronized boolean a(String str) {
        boolean c;
        AliUserLog.c("SecurityDbHelper", "删除用户信息");
        try {
            c = this.b.c(str);
            AliUserLog.c("SecurityDbHelper", String.format("删除用户信息成功  userId=%s, retState=%s", new Object[]{str, Boolean.valueOf(c)}));
            if (UserInfoCache.a == null) {
                UserInfoCache.a = new HashMap<>();
            }
            UserInfoCache.a.remove(str);
        } catch (Throwable th) {
            AliUserLog.a((String) "SecurityDbHelper", th);
            return false;
        }
        return c;
    }

    public final synchronized boolean a(UserInfo userInfo) {
        boolean z;
        z = false;
        try {
            this.b.a(userInfo);
            AliUserLog.c("SecurityDbHelper", "addOrUpdateUserLogin success");
            z = true;
        } catch (Throwable th) {
            AliUserLog.a((String) "SecurityDbHelper", th);
        }
        return z;
    }

    public final synchronized List<UserInfo> a() {
        List<UserInfo> arrayList;
        arrayList = new ArrayList<>();
        try {
            AliUserLog.c("SecurityDbHelper", "查询所有本地用户列表");
            arrayList = this.b.a();
        } catch (Throwable th) {
            AliUserLog.a((String) "SecurityDbHelper", th);
        }
        return arrayList;
    }

    public final synchronized List<UserInfo> a(int i) {
        List<UserInfo> list;
        List<UserInfo> arrayList = new ArrayList<>();
        try {
            AliUserLog.c("SecurityDbHelper", "查询出本地用户列表");
            list = this.b.a(i);
        } catch (Throwable th) {
            AliUserLog.a((String) "SecurityDbHelper", th);
            list = arrayList;
        }
        return list;
    }

    public final synchronized UserInfo b(String str) {
        UserInfo userInfo;
        UserInfo userInfo2 = null;
        if (UserInfoCache.a != null) {
            userInfo2 = UserInfoCache.a.get(str);
            if (userInfo2 != null) {
                AliUserLog.c("SecurityDbHelper", "从缓存获取用户信息");
                return userInfo2;
            }
        }
        try {
            AliUserLog.c("SecurityDbHelper", "查询出本地用户详细信息");
            userInfo = this.b.d(str);
            try {
                if (UserInfoCache.a == null) {
                    UserInfoCache.a = new HashMap<>();
                }
                if (userInfo != null) {
                    UserInfoCache.a.put(str, userInfo);
                }
            } catch (Throwable th) {
                th = th;
                AliUserLog.a((String) "SecurityDbHelper", th);
                return userInfo;
            }
        } catch (Throwable th2) {
            th = th2;
            userInfo = userInfo2;
            AliUserLog.a((String) "SecurityDbHelper", th);
            return userInfo;
        }
    }

    public final synchronized boolean c(String str) {
        boolean b2;
        try {
            AuthUtil.a("SecurityDbHelper", "updateUserAutoLoginFlag");
            b2 = this.b.b(str);
            if (b2) {
                AliUserLog.c("SecurityDbHelper", "修改当前用户登录状态为未登录 成功");
            } else {
                AliUserLog.c("SecurityDbHelper", "修改当前用户登录状态为未登录 失败");
            }
        } catch (Throwable th) {
            AliUserLog.a((String) "SecurityDbHelper", th);
            return false;
        }
        return b2;
    }

    public final synchronized boolean d(String str) {
        boolean a2;
        try {
            AuthUtil.a("SecurityDbHelper", "updateUserAutoLoginFlagByLogonId");
            a2 = this.b.a(str);
            if (a2) {
                AliUserLog.c("SecurityDbHelper", "修改当前用户登录状态为未登录 成功");
            } else {
                AliUserLog.c("SecurityDbHelper", "修改当前用户登录状态为未登录 失败");
            }
        } catch (Throwable th) {
            AliUserLog.a((String) "SecurityDbHelper", th);
            return false;
        }
        return a2;
    }

    public final synchronized boolean b() {
        boolean b2;
        try {
            AuthUtil.a("SecurityDbHelper", "updateUserAutoLoginFlagByLogonId");
            b2 = this.b.b();
            if (b2) {
                AliUserLog.c("SecurityDbHelper", "修改所有用户登录状态为未登录 成功");
            } else {
                AliUserLog.c("SecurityDbHelper", "修改所有用户登录状态为未登录 失败");
            }
        } catch (Throwable th) {
            AliUserLog.a((String) "SecurityDbHelper", th);
            return false;
        }
        return b2;
    }
}
