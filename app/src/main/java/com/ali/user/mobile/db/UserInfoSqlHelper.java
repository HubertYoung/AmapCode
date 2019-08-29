package com.ali.user.mobile.db;

import android.database.Cursor;
import android.text.TextUtils;
import com.ali.user.mobile.account.bean.UserInfo;
import com.j256.ormlite.stmt.query.ManyClause;
import java.util.Map;

public class UserInfoSqlHelper {
    public static String a() {
        return "CREATE TABLE IF NOT EXISTS UserInfo(taobaoSid TEXT ,loginTime TEXT ,noPayPwd TEXT ,userAvatar TEXT ,userType TEXT ,loginMobile TEXT ,isBindCard TEXT ,loginToken TEXT ,isWirelessUser TEXT ,loginEmail TEXT ,taobaoNick TEXT , memberGrade TEXT ,gender TEXT ,userName TEXT ,realNamed TEXT ,studentCertify TEXT ,externToken TEXT ,customerType TEXT ,isCertified TEXT ,havanaId TEXT ,nick TEXT ,sessionId TEXT ,autoLogin TEXT ,realName TEXT ,mobileNumber TEXT ,logonId TEXT ,isNewUser TEXT ,otherLoginId TEXT ,userId TEXT PRIMARY KEY);";
    }

    public static String a(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return "SELECT * FROM UserInfo";
        }
        StringBuilder sb = new StringBuilder("SELECT * FROM UserInfo WHERE ");
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                sb.append(" AND ");
            }
            sb.append(strArr[i]);
            sb.append("=?");
        }
        return sb.toString();
    }

    public static String a(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "SELECT * FROM UserInfo";
        }
        StringBuilder sb = new StringBuilder("SELECT * FROM UserInfo WHERE ");
        int i = 0;
        for (String next : map.keySet()) {
            i++;
            if (i > 1) {
                sb.append(ManyClause.AND_OPERATION);
            }
            sb.append(next);
            sb.append("='");
            sb.append(String.valueOf(map.get(next)));
            sb.append("'");
        }
        return sb.toString();
    }

    public static String a(String str, String str2, String[] strArr) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || strArr == null || strArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("UPDATE UserInfo SET ");
        sb.append(str);
        sb.append("=");
        sb.append(str2);
        sb.append(" WHERE ");
        StringBuilder sb2 = new StringBuilder(sb.toString());
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                sb2.append(" AND ");
            }
            sb2.append(strArr[i]);
            sb2.append("=?");
        }
        return sb2.toString();
    }

    public static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder("UPDATE UserInfo SET ");
        sb.append(str);
        sb.append("=");
        sb.append(str2);
        return new StringBuilder(sb.toString()).toString();
    }

    public static String a(UserInfo userInfo) {
        if (userInfo == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("REPLACE INTO UserInfo (taobaoSid, loginTime, noPayPwd, userAvatar, userType, loginMobile, isBindCard, loginToken, isWirelessUser, loginEmail, taobaoNick, userId, memberGrade, gender, userName, realNamed, studentCertify, externToken, customerType, isCertified, havanaId, nick, sessionId, autoLogin, realName, mobileNumber, logonId, isNewUser, otherLoginId) values (");
        sb.append("'");
        sb.append(userInfo.getTaobaoSid());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getLoginTime());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getNoPayPwd());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getUserAvatar());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getUserType());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getLoginMobile());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getIsBindCardStr());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getLoginToken());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getIsWirelessUserStr());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getLoginEmail());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getTaobaoNick());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getUserId());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getMemberGrade());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getGender());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getUserName());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getRealNamed());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getStudentCertify());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getExternToken());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getCustomerType());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getIsCertified());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getHavanaId());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getNick());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getSessionId());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getIsAutoLoginStr());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getRealName());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getMobileNumber());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getLogonId());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getIsNewUserStr());
        sb.append("', ");
        sb.append("'");
        sb.append(userInfo.getOtherLoginId());
        sb.append("');");
        return sb.toString();
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return String.format("DELETE FROM UserInfo WHERE userId='%s'", new Object[]{str});
    }

    public static UserInfo a(Cursor cursor) {
        UserInfo userInfo = new UserInfo();
        int columnIndex = cursor.getColumnIndex("taobaoSid");
        if (columnIndex != -1) {
            userInfo.setTaobaoSid(cursor.getString(columnIndex));
        }
        int columnIndex2 = cursor.getColumnIndex("loginTime");
        if (columnIndex2 != -1) {
            userInfo.setLoginTime(cursor.getString(columnIndex2));
        }
        int columnIndex3 = cursor.getColumnIndex("noPayPwd");
        if (columnIndex3 != -1) {
            userInfo.setNoPayPwd(cursor.getString(columnIndex3));
        }
        int columnIndex4 = cursor.getColumnIndex("userAvatar");
        if (columnIndex4 != -1) {
            userInfo.setUserAvatar(cursor.getString(columnIndex4));
        }
        int columnIndex5 = cursor.getColumnIndex("userType");
        if (columnIndex5 != -1) {
            userInfo.setUserType(cursor.getString(columnIndex5));
        }
        int columnIndex6 = cursor.getColumnIndex("loginMobile");
        if (columnIndex6 != -1) {
            userInfo.setLoginMobile(cursor.getString(columnIndex6));
        }
        int columnIndex7 = cursor.getColumnIndex("isBindCard");
        if (columnIndex7 != -1) {
            userInfo.setIsBindCardStr(cursor.getString(columnIndex7));
        }
        int columnIndex8 = cursor.getColumnIndex("loginToken");
        if (columnIndex8 != -1) {
            userInfo.setLoginToken(cursor.getString(columnIndex8));
        }
        int columnIndex9 = cursor.getColumnIndex("isWirelessUser");
        if (columnIndex9 != -1) {
            userInfo.setIsWirelessUserStr(cursor.getString(columnIndex9));
        }
        int columnIndex10 = cursor.getColumnIndex("loginEmail");
        if (columnIndex10 != -1) {
            userInfo.setLoginEmail(cursor.getString(columnIndex10));
        }
        int columnIndex11 = cursor.getColumnIndex("taobaoNick");
        if (columnIndex11 != -1) {
            userInfo.setTaobaoNick(cursor.getString(columnIndex11));
        }
        int columnIndex12 = cursor.getColumnIndex("userId");
        if (columnIndex12 != -1) {
            userInfo.setUserId(cursor.getString(columnIndex12));
        }
        int columnIndex13 = cursor.getColumnIndex("memberGrade");
        if (columnIndex13 != -1) {
            userInfo.setMemberGrade(cursor.getString(columnIndex13));
        }
        int columnIndex14 = cursor.getColumnIndex("gender");
        if (columnIndex14 != -1) {
            userInfo.setGender(cursor.getString(columnIndex14));
        }
        int columnIndex15 = cursor.getColumnIndex("userName");
        if (columnIndex15 != -1) {
            userInfo.setUserName(cursor.getString(columnIndex15));
        }
        int columnIndex16 = cursor.getColumnIndex("realNamed");
        if (columnIndex16 != -1) {
            userInfo.setRealNamed(cursor.getString(columnIndex16));
        }
        int columnIndex17 = cursor.getColumnIndex("studentCertify");
        if (columnIndex17 != -1) {
            userInfo.setStudentCertify(cursor.getString(columnIndex17));
        }
        int columnIndex18 = cursor.getColumnIndex("externToken");
        if (columnIndex18 != -1) {
            userInfo.setExternToken(cursor.getString(columnIndex18));
        }
        int columnIndex19 = cursor.getColumnIndex("customerType");
        if (columnIndex19 != -1) {
            userInfo.setCustomerType(cursor.getString(columnIndex19));
        }
        int columnIndex20 = cursor.getColumnIndex("isCertified");
        if (columnIndex20 != -1) {
            userInfo.setIsCertified(cursor.getString(columnIndex20));
        }
        int columnIndex21 = cursor.getColumnIndex("havanaId");
        if (columnIndex21 != -1) {
            userInfo.setHavanaId(cursor.getString(columnIndex21));
        }
        int columnIndex22 = cursor.getColumnIndex("nick");
        if (columnIndex22 != -1) {
            userInfo.setNick(cursor.getString(columnIndex22));
        }
        int columnIndex23 = cursor.getColumnIndex("sessionId");
        if (columnIndex23 != -1) {
            userInfo.setSessionId(cursor.getString(columnIndex23));
        }
        int columnIndex24 = cursor.getColumnIndex("autoLogin");
        if (columnIndex24 != -1) {
            userInfo.setIsAutoLoginStr(cursor.getString(columnIndex24));
        }
        int columnIndex25 = cursor.getColumnIndex("realName");
        if (columnIndex25 != -1) {
            userInfo.setRealName(cursor.getString(columnIndex25));
        }
        int columnIndex26 = cursor.getColumnIndex("mobileNumber");
        if (columnIndex26 != -1) {
            userInfo.setMobileNumber(cursor.getString(columnIndex26));
        }
        int columnIndex27 = cursor.getColumnIndex("logonId");
        if (columnIndex27 != -1) {
            userInfo.setLogonId(cursor.getString(columnIndex27));
        }
        int columnIndex28 = cursor.getColumnIndex("isNewUser");
        if (columnIndex28 != -1) {
            userInfo.setIsNewUserStr(cursor.getString(columnIndex28));
        }
        int columnIndex29 = cursor.getColumnIndex("otherLoginId");
        if (columnIndex29 != -1) {
            userInfo.setOtherLoginId(cursor.getString(columnIndex29));
        }
        return userInfo;
    }
}
