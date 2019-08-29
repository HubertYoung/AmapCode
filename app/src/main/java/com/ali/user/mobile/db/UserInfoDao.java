package com.ali.user.mobile.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.account.dao.IUserInfoDao;
import com.ali.user.mobile.account.dao.UserInfoCache;
import com.ali.user.mobile.log.AliUserLog;
import java.util.List;

public class UserInfoDao implements IUserInfoDao {
    static final String c = "com.ali.user.mobile.db.UserInfoDao";
    public static SQLiteOpenHelper d;
    private static UserInfoDao e;
    private Context f;
    private ContextWrapper g;

    private UserInfoDao(Context context) {
        this.f = context;
        d = UserInfoDBHelper.a(context);
    }

    public static IUserInfoDao a(Context context) {
        if (e == null) {
            synchronized (UserInfoDao.class) {
                try {
                    if (e == null) {
                        e = new UserInfoDao(context);
                    }
                }
            }
        }
        return e;
    }

    private ContextWrapper b(Context context) {
        if (this.g == null) {
            this.g = new ContextWrapper(context);
        }
        return this.g;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0052  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.ali.user.mobile.account.bean.UserInfo d(java.lang.String r8) {
        /*
            r7 = this;
            android.database.sqlite.SQLiteOpenHelper r0 = d
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()
            r1 = 0
            java.lang.String r2 = "userId"
            java.lang.String[] r2 = new java.lang.String[]{r2}     // Catch:{ Exception -> 0x0048 }
            java.lang.String r2 = com.ali.user.mobile.db.UserInfoSqlHelper.a(r2)     // Catch:{ Exception -> 0x0048 }
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x0048 }
            r4 = 0
            android.content.Context r5 = r7.f     // Catch:{ Exception -> 0x0048 }
            android.content.ContextWrapper r5 = r7.b(r5)     // Catch:{ Exception -> 0x0048 }
            java.lang.String r8 = com.ali.user.mobile.encryption.DataEncryptor.a(r5, r8)     // Catch:{ Exception -> 0x0048 }
            r3[r4] = r8     // Catch:{ Exception -> 0x0048 }
            android.database.Cursor r8 = r0.rawQuery(r2, r3)     // Catch:{ Exception -> 0x0048 }
            if (r8 == 0) goto L_0x0050
            boolean r2 = r8.moveToFirst()     // Catch:{ Exception -> 0x0043 }
            if (r2 == 0) goto L_0x0050
            com.ali.user.mobile.account.bean.UserInfo r2 = com.ali.user.mobile.db.UserInfoSqlHelper.a(r8)     // Catch:{ Exception -> 0x0043 }
            android.content.Context r1 = r7.f     // Catch:{ Exception -> 0x003d }
            android.content.ContextWrapper r1 = r7.b(r1)     // Catch:{ Exception -> 0x003d }
            com.ali.user.mobile.account.bean.UserInfo r1 = com.ali.user.mobile.db.UserInfoEncrypter.b(r2, r1)     // Catch:{ Exception -> 0x003d }
            goto L_0x0050
        L_0x003d:
            r1 = move-exception
            r6 = r2
            r2 = r8
            r8 = r1
            r1 = r6
            goto L_0x004a
        L_0x0043:
            r2 = move-exception
            r6 = r2
            r2 = r8
            r8 = r6
            goto L_0x004a
        L_0x0048:
            r8 = move-exception
            r2 = r1
        L_0x004a:
            java.lang.String r3 = c
            com.ali.user.mobile.log.AliUserLog.a(r3, r8)
            r8 = r2
        L_0x0050:
            if (r8 == 0) goto L_0x0055
            r8.close()
        L_0x0055:
            r0.close()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.db.UserInfoDao.d(java.lang.String):com.ali.user.mobile.account.bean.UserInfo");
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r1v2, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v10, types: [com.ali.user.mobile.account.bean.UserInfo] */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0090, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0091, code lost:
        r7 = r1;
        r1 = r9;
        r9 = r7;
        r10 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0095, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0096, code lost:
        r1 = r9;
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00bc, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c1, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00c7, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00cc, code lost:
        r0.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0095 A[ExcHandler: all (r10v7 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:19:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00cc  */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.ali.user.mobile.account.bean.UserInfo a(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            android.database.sqlite.SQLiteOpenHelper r0 = d
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()
            r1 = 0
            boolean r2 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x00b3 }
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x0026
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x00b3 }
            if (r2 == 0) goto L_0x0026
            java.lang.String r9 = "SELECT * FROM UserInfo WHERE logonId = ?"
            java.lang.String[] r2 = new java.lang.String[r4]     // Catch:{ Exception -> 0x00b3 }
            android.content.Context r5 = r8.f     // Catch:{ Exception -> 0x00b3 }
            android.content.ContextWrapper r5 = r8.b(r5)     // Catch:{ Exception -> 0x00b3 }
            java.lang.String r10 = com.ali.user.mobile.encryption.DataEncryptor.a(r5, r10)     // Catch:{ Exception -> 0x00b3 }
            r2[r3] = r10     // Catch:{ Exception -> 0x00b3 }
            goto L_0x006f
        L_0x0026:
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x00b3 }
            if (r2 != 0) goto L_0x0044
            boolean r2 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x00b3 }
            if (r2 == 0) goto L_0x0044
            java.lang.String r10 = "SELECT * FROM UserInfo WHERE userId = ?"
            java.lang.String[] r2 = new java.lang.String[r4]     // Catch:{ Exception -> 0x00b3 }
            android.content.Context r5 = r8.f     // Catch:{ Exception -> 0x00b3 }
            android.content.ContextWrapper r5 = r8.b(r5)     // Catch:{ Exception -> 0x00b3 }
            java.lang.String r9 = com.ali.user.mobile.encryption.DataEncryptor.a(r5, r9)     // Catch:{ Exception -> 0x00b3 }
            r2[r3] = r9     // Catch:{ Exception -> 0x00b3 }
            r9 = r10
            goto L_0x006f
        L_0x0044:
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x00b3 }
            if (r2 != 0) goto L_0x00ab
            boolean r2 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x00b3 }
            if (r2 != 0) goto L_0x00ab
            java.lang.String r2 = "SELECT * FROM UserInfo WHERE userId = ? and logonId = ?"
            r5 = 2
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ Exception -> 0x00b3 }
            android.content.Context r6 = r8.f     // Catch:{ Exception -> 0x00b3 }
            android.content.ContextWrapper r6 = r8.b(r6)     // Catch:{ Exception -> 0x00b3 }
            java.lang.String r9 = com.ali.user.mobile.encryption.DataEncryptor.a(r6, r9)     // Catch:{ Exception -> 0x00b3 }
            r5[r3] = r9     // Catch:{ Exception -> 0x00b3 }
            android.content.Context r9 = r8.f     // Catch:{ Exception -> 0x00b3 }
            android.content.ContextWrapper r9 = r8.b(r9)     // Catch:{ Exception -> 0x00b3 }
            java.lang.String r9 = com.ali.user.mobile.encryption.DataEncryptor.a(r9, r10)     // Catch:{ Exception -> 0x00b3 }
            r5[r4] = r9     // Catch:{ Exception -> 0x00b3 }
            r9 = r2
            r2 = r5
        L_0x006f:
            android.database.Cursor r9 = r0.rawQuery(r9, r2)     // Catch:{ Exception -> 0x00b3 }
            if (r9 == 0) goto L_0x009f
            int r10 = r9.getCount()     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            if (r10 != r4) goto L_0x009f
            boolean r10 = r9.moveToFirst()     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            if (r10 == 0) goto L_0x009f
            com.ali.user.mobile.account.bean.UserInfo r10 = com.ali.user.mobile.db.UserInfoSqlHelper.a(r9)     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            android.content.Context r1 = r8.f     // Catch:{ Exception -> 0x0090, all -> 0x0095 }
            android.content.ContextWrapper r1 = r8.b(r1)     // Catch:{ Exception -> 0x0090, all -> 0x0095 }
            com.ali.user.mobile.account.bean.UserInfo r1 = com.ali.user.mobile.db.UserInfoEncrypter.b(r10, r1)     // Catch:{ Exception -> 0x0090, all -> 0x0095 }
            goto L_0x009f
        L_0x0090:
            r1 = move-exception
            r7 = r1
            r1 = r9
            r9 = r7
            goto L_0x00b5
        L_0x0095:
            r10 = move-exception
            r1 = r9
            r9 = r10
            goto L_0x00c5
        L_0x0099:
            r10 = move-exception
            r7 = r1
            r1 = r9
            r9 = r10
            r10 = r7
            goto L_0x00b5
        L_0x009f:
            if (r9 == 0) goto L_0x00a4
            r9.close()
        L_0x00a4:
            if (r0 == 0) goto L_0x00a9
            r0.close()
        L_0x00a9:
            r10 = r1
            goto L_0x00c4
        L_0x00ab:
            if (r0 == 0) goto L_0x00b0
            r0.close()
        L_0x00b0:
            return r1
        L_0x00b1:
            r9 = move-exception
            goto L_0x00c5
        L_0x00b3:
            r9 = move-exception
            r10 = r1
        L_0x00b5:
            java.lang.String r2 = c     // Catch:{ all -> 0x00b1 }
            com.ali.user.mobile.log.AliUserLog.a(r2, r9)     // Catch:{ all -> 0x00b1 }
            if (r1 == 0) goto L_0x00bf
            r1.close()
        L_0x00bf:
            if (r0 == 0) goto L_0x00c4
            r0.close()
        L_0x00c4:
            return r10
        L_0x00c5:
            if (r1 == 0) goto L_0x00ca
            r1.close()
        L_0x00ca:
            if (r0 == 0) goto L_0x00cf
            r0.close()
        L_0x00cf:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.db.UserInfoDao.a(java.lang.String, java.lang.String):com.ali.user.mobile.account.bean.UserInfo");
    }

    public final void a(UserInfo userInfo) {
        if (userInfo == null) {
            AliUserLog.d(c, "addOrUpdateUserLogin userInfo is null");
            return;
        }
        AliUserLog.c(c, "save user info");
        SQLiteDatabase writableDatabase = d.getWritableDatabase();
        try {
            String userId = userInfo.getUserId();
            writableDatabase.execSQL(UserInfoSqlHelper.a(UserInfoEncrypter.a(userInfo, b(this.f))));
            AliUserLog.c(c, "添加或者更新用户信息成功，清除用户缓存数据");
            if (UserInfoCache.a != null) {
                UserInfoCache.a.remove(userId);
            }
        } catch (Exception e2) {
            AliUserLog.a(c, "添加或者更新用户信息失败", e2);
        }
        writableDatabase.close();
    }

    public final List<UserInfo> a() {
        return a(-1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0055  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.ali.user.mobile.account.bean.UserInfo> a(int r6) {
        /*
            r5 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            android.database.sqlite.SQLiteOpenHelper r1 = d
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()
            r2 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x004c }
            java.lang.String r4 = com.ali.user.mobile.db.UserInfoSqlHelper.a(r2)     // Catch:{ Exception -> 0x004c }
            r3.<init>(r4)     // Catch:{ Exception -> 0x004c }
            java.lang.String r4 = " ORDER BY loginTime DESC"
            r3.append(r4)     // Catch:{ Exception -> 0x004c }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x004c }
            android.database.Cursor r3 = r1.rawQuery(r3, r2)     // Catch:{ Exception -> 0x004c }
            if (r3 == 0) goto L_0x0053
            boolean r2 = r3.moveToFirst()     // Catch:{ Exception -> 0x004a }
            if (r2 == 0) goto L_0x0053
        L_0x002a:
            com.ali.user.mobile.account.bean.UserInfo r2 = com.ali.user.mobile.db.UserInfoSqlHelper.a(r3)     // Catch:{ Exception -> 0x004a }
            android.content.Context r4 = r5.f     // Catch:{ Exception -> 0x004a }
            android.content.ContextWrapper r4 = r5.b(r4)     // Catch:{ Exception -> 0x004a }
            com.ali.user.mobile.account.bean.UserInfo r2 = com.ali.user.mobile.db.UserInfoEncrypter.b(r2, r4)     // Catch:{ Exception -> 0x004a }
            r0.add(r2)     // Catch:{ Exception -> 0x004a }
            if (r6 <= 0) goto L_0x0043
            int r2 = r0.size()     // Catch:{ Exception -> 0x004a }
            if (r2 == r6) goto L_0x0053
        L_0x0043:
            boolean r2 = r3.moveToNext()     // Catch:{ Exception -> 0x004a }
            if (r2 != 0) goto L_0x002a
            goto L_0x0053
        L_0x004a:
            r6 = move-exception
            goto L_0x004e
        L_0x004c:
            r6 = move-exception
            r3 = r2
        L_0x004e:
            java.lang.String r2 = c
            com.ali.user.mobile.log.AliUserLog.a(r2, r6)
        L_0x0053:
            if (r3 == 0) goto L_0x0058
            r3.close()
        L_0x0058:
            r1.close()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.db.UserInfoDao.a(int):java.util.List");
    }

    public final boolean c(String str) {
        boolean z = false;
        if (str == null) {
            AliUserLog.c(c, "delete fail no userInfo");
            return false;
        }
        SQLiteDatabase writableDatabase = d.getWritableDatabase();
        try {
            writableDatabase.execSQL(UserInfoSqlHelper.a(UserInfoEncrypter.a(str, this.g)));
            z = true;
            UserInfoCache.a = null;
            AliUserLog.c(c, "清楚本地账户数据");
        } catch (Exception e2) {
            AliUserLog.a(c, (Throwable) e2);
        }
        writableDatabase.close();
        return z;
    }

    public final boolean b(String str) {
        boolean z = true;
        try {
            if (TextUtils.isEmpty(str)) {
                AliUserLog.c(c, "updateUserAutoLoginFlag userId is empty");
                return false;
            }
            SQLiteDatabase writableDatabase = d.getWritableDatabase();
            StringBuilder sb = new StringBuilder("'");
            sb.append(UserInfoEncrypter.a((String) "false", b(this.f)));
            sb.append("'");
            String a = UserInfoSqlHelper.a("autoLogin", sb.toString(), new String[]{"userId"});
            StringBuilder sb2 = new StringBuilder("'");
            sb2.append(UserInfoEncrypter.a(str, b(this.f)));
            sb2.append("'");
            writableDatabase.rawQuery(a, new String[]{sb2.toString()});
            try {
                UserInfoCache.a = null;
                AliUserLog.c(c, "设置当前账户免登标识为false");
            } catch (Exception e2) {
                e = e2;
            }
            return z;
        } catch (Exception e3) {
            e = e3;
            z = false;
            AliUserLog.a(c, (Throwable) e);
            return z;
        }
    }

    public final boolean a(String str) {
        boolean z = true;
        try {
            if (TextUtils.isEmpty(str)) {
                AliUserLog.c(c, "updateUserAutoLoginFlagByLogonId logonId is empty");
                return false;
            }
            SQLiteDatabase writableDatabase = d.getWritableDatabase();
            StringBuilder sb = new StringBuilder("'");
            sb.append(UserInfoEncrypter.a((String) "false", b(this.f)));
            sb.append("'");
            String a = UserInfoSqlHelper.a("autoLogin", sb.toString(), new String[]{"logonId"});
            StringBuilder sb2 = new StringBuilder("'");
            sb2.append(UserInfoEncrypter.a(str, b(this.f)));
            sb2.append("'");
            writableDatabase.rawQuery(a, new String[]{sb2.toString()});
            try {
                UserInfoCache.a = null;
                AliUserLog.c(c, "设置当前账户免登标识为false");
            } catch (Exception e2) {
                e = e2;
            }
            return z;
        } catch (Exception e3) {
            e = e3;
            z = false;
            AliUserLog.a(c, (Throwable) e);
            return z;
        }
    }

    public final boolean b() {
        boolean z = false;
        try {
            SQLiteDatabase writableDatabase = d.getWritableDatabase();
            StringBuilder sb = new StringBuilder("'");
            sb.append(UserInfoEncrypter.a((String) "false", b(this.f)));
            sb.append("'");
            writableDatabase.rawQuery(UserInfoSqlHelper.a("autoLogin", sb.toString()), null);
            z = true;
            UserInfoCache.a = null;
            AliUserLog.c(c, "设置当前账户免登标识为false");
            return true;
        } catch (Exception e2) {
            AliUserLog.a(c, (Throwable) e2);
            return z;
        }
    }
}
