package com.ali.user.mobile.db;

import android.content.ContextWrapper;
import android.text.TextUtils;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.encryption.DataEncryptor;
import com.ali.user.mobile.log.AliUserLog;

public class UserInfoEncrypter {
    public static String a(String str, ContextWrapper contextWrapper) {
        if (str == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            String a = DataEncryptor.a(contextWrapper, str);
            AliUserLog.c("UserInfoEncrypter", "encrypt = ".concat(String.valueOf(a)));
            return a;
        } catch (Exception e) {
            AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e);
            return null;
        }
    }

    public static UserInfo a(UserInfo userInfo, ContextWrapper contextWrapper) {
        if (userInfo == null) {
            return null;
        }
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setLoginTime(userInfo.getLoginTime());
        String taobaoSid = userInfo.getTaobaoSid();
        if (!TextUtils.isEmpty(taobaoSid)) {
            try {
                String a = DataEncryptor.a(contextWrapper, taobaoSid);
                AliUserLog.c("UserInfoEncrypter", String.format("src.taobaoSid=%s, enc=%s", new Object[]{taobaoSid, a}));
                userInfo2.setTaobaoSid(a);
            } catch (Exception e) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e);
            }
        }
        String userAvatar = userInfo.getUserAvatar();
        if (!TextUtils.isEmpty(userAvatar)) {
            try {
                String a2 = DataEncryptor.a(contextWrapper, userAvatar);
                AliUserLog.c("UserInfoEncrypter", String.format("src.userAvatar=%s, enc=%s", new Object[]{userAvatar, a2}));
                userInfo2.setUserAvatar(a2);
            } catch (Exception e2) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e2);
            }
        }
        String userType = userInfo.getUserType();
        if (!TextUtils.isEmpty(userType)) {
            try {
                String a3 = DataEncryptor.a(contextWrapper, userType);
                AliUserLog.c("UserInfoEncrypter", String.format("src.userType=%s, enc=%s", new Object[]{userType, a3}));
                userInfo2.setUserType(a3);
            } catch (Exception e3) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e3);
            }
        }
        String loginMobile = userInfo.getLoginMobile();
        if (!TextUtils.isEmpty(loginMobile)) {
            try {
                String a4 = DataEncryptor.a(contextWrapper, loginMobile);
                AliUserLog.c("UserInfoEncrypter", String.format("src.loginMobile=%s, enc=%s", new Object[]{loginMobile, a4}));
                userInfo2.setLoginMobile(a4);
            } catch (Exception e4) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e4);
            }
        }
        String isBindCardStr = userInfo.getIsBindCardStr();
        if (!TextUtils.isEmpty(isBindCardStr)) {
            try {
                String a5 = DataEncryptor.a(contextWrapper, isBindCardStr);
                AliUserLog.c("UserInfoEncrypter", String.format("src.isBindCard=%s, enc=%s", new Object[]{isBindCardStr, a5}));
                userInfo2.setIsBindCardStr(a5);
            } catch (Exception e5) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e5);
            }
        }
        String loginToken = userInfo.getLoginToken();
        if (!TextUtils.isEmpty(loginToken)) {
            try {
                String a6 = DataEncryptor.a(contextWrapper, loginToken);
                AliUserLog.c("UserInfoEncrypter", String.format("src.loginToken=%s, enc=%s", new Object[]{loginToken, a6}));
                userInfo2.setLoginToken(a6);
            } catch (Exception e6) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e6);
            }
        }
        String isWirelessUserStr = userInfo.getIsWirelessUserStr();
        if (!TextUtils.isEmpty(isWirelessUserStr)) {
            try {
                String a7 = DataEncryptor.a(contextWrapper, isWirelessUserStr);
                AliUserLog.c("UserInfoEncrypter", String.format("src.isWirelessUser=%s, enc=%s", new Object[]{isWirelessUserStr, a7}));
                userInfo2.setIsWirelessUserStr(a7);
            } catch (Exception e7) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e7);
            }
        }
        String loginEmail = userInfo.getLoginEmail();
        if (!TextUtils.isEmpty(loginEmail)) {
            try {
                String a8 = DataEncryptor.a(contextWrapper, loginEmail);
                AliUserLog.c("UserInfoEncrypter", String.format("src.loginEmail=%s, enc=%s", new Object[]{loginEmail, a8}));
                userInfo2.setLoginEmail(a8);
            } catch (Exception e8) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e8);
            }
        }
        String taobaoNick = userInfo.getTaobaoNick();
        if (!TextUtils.isEmpty(taobaoNick)) {
            try {
                String a9 = DataEncryptor.a(contextWrapper, taobaoNick);
                AliUserLog.c("UserInfoEncrypter", String.format("src.taobaoNick=%s, enc=%s", new Object[]{taobaoNick, a9}));
                userInfo2.setTaobaoNick(a9);
            } catch (Exception e9) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e9);
            }
        }
        String userId = userInfo.getUserId();
        if (!TextUtils.isEmpty(userId)) {
            try {
                String a10 = DataEncryptor.a(contextWrapper, userId);
                AliUserLog.c("UserInfoEncrypter", String.format("src.userId=%s, enc=%s", new Object[]{userId, a10}));
                userInfo2.setUserId(a10);
            } catch (Exception e10) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e10);
            }
        }
        String memberGrade = userInfo.getMemberGrade();
        if (!TextUtils.isEmpty(memberGrade)) {
            try {
                String a11 = DataEncryptor.a(contextWrapper, memberGrade);
                AliUserLog.c("UserInfoEncrypter", String.format("src.memberGrade=%s, enc=%s", new Object[]{memberGrade, a11}));
                userInfo2.setMemberGrade(a11);
            } catch (Exception e11) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e11);
            }
        }
        String gender = userInfo.getGender();
        if (!TextUtils.isEmpty(gender)) {
            try {
                String a12 = DataEncryptor.a(contextWrapper, gender);
                AliUserLog.c("UserInfoEncrypter", String.format("src.gender=%s, enc=%s", new Object[]{gender, a12}));
                userInfo2.setGender(a12);
            } catch (Exception e12) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e12);
            }
        }
        String userName = userInfo.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            try {
                String a13 = DataEncryptor.a(contextWrapper, userName);
                AliUserLog.c("UserInfoEncrypter", String.format("src.userName=%s, enc=%s", new Object[]{userName, a13}));
                userInfo2.setUserName(a13);
            } catch (Exception e13) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e13);
            }
        }
        String realNamed = userInfo.getRealNamed();
        if (!TextUtils.isEmpty(realNamed)) {
            try {
                String a14 = DataEncryptor.a(contextWrapper, realNamed);
                AliUserLog.c("UserInfoEncrypter", String.format("src.realNamed=%s, enc=%s", new Object[]{realNamed, a14}));
                userInfo2.setRealNamed(a14);
            } catch (Exception e14) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e14);
            }
        }
        String studentCertify = userInfo.getStudentCertify();
        if (!TextUtils.isEmpty(studentCertify)) {
            try {
                String a15 = DataEncryptor.a(contextWrapper, studentCertify);
                AliUserLog.c("UserInfoEncrypter", String.format("src.studentCertify=%s, enc=%s", new Object[]{studentCertify, a15}));
                userInfo2.setStudentCertify(a15);
            } catch (Exception e15) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e15);
            }
        }
        String externToken = userInfo.getExternToken();
        if (!TextUtils.isEmpty(externToken)) {
            try {
                String a16 = DataEncryptor.a(contextWrapper, externToken);
                AliUserLog.c("UserInfoEncrypter", String.format("src.externToken=%s, enc=%s", new Object[]{externToken, a16}));
                userInfo2.setExternToken(a16);
            } catch (Exception e16) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e16);
            }
        }
        String customerType = userInfo.getCustomerType();
        if (!TextUtils.isEmpty(customerType)) {
            try {
                String a17 = DataEncryptor.a(contextWrapper, customerType);
                AliUserLog.c("UserInfoEncrypter", String.format("src.customerType=%s, enc=%s", new Object[]{customerType, a17}));
                userInfo2.setCustomerType(a17);
            } catch (Exception e17) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e17);
            }
        }
        String isCertified = userInfo.getIsCertified();
        if (!TextUtils.isEmpty(isCertified)) {
            try {
                String a18 = DataEncryptor.a(contextWrapper, isCertified);
                AliUserLog.c("UserInfoEncrypter", String.format("src.isCertified=%s, enc=%s", new Object[]{isCertified, a18}));
                userInfo2.setIsCertified(a18);
            } catch (Exception e18) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e18);
            }
        }
        String havanaId = userInfo.getHavanaId();
        if (!TextUtils.isEmpty(havanaId)) {
            try {
                String a19 = DataEncryptor.a(contextWrapper, havanaId);
                AliUserLog.c("UserInfoEncrypter", String.format("src.havanaId=%s, enc=%s", new Object[]{havanaId, a19}));
                userInfo2.setHavanaId(a19);
            } catch (Exception e19) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e19);
            }
        }
        String nick = userInfo.getNick();
        if (!TextUtils.isEmpty(nick)) {
            try {
                String a20 = DataEncryptor.a(contextWrapper, nick);
                AliUserLog.c("UserInfoEncrypter", String.format("src.nick=%s, enc=%s", new Object[]{nick, a20}));
                userInfo2.setNick(a20);
            } catch (Exception e20) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e20);
            }
        }
        String sessionId = userInfo.getSessionId();
        if (!TextUtils.isEmpty(sessionId)) {
            try {
                String a21 = DataEncryptor.a(contextWrapper, sessionId);
                AliUserLog.c("UserInfoEncrypter", String.format("src.sessionId=%s, enc=%s", new Object[]{sessionId, a21}));
                userInfo2.setSessionId(a21);
            } catch (Exception e21) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e21);
            }
        }
        String isAutoLoginStr = userInfo.getIsAutoLoginStr();
        if (!TextUtils.isEmpty(isAutoLoginStr)) {
            try {
                String a22 = DataEncryptor.a(contextWrapper, isAutoLoginStr);
                AliUserLog.c("UserInfoEncrypter", String.format("src.autoLogin=%s, enc=%s", new Object[]{isAutoLoginStr, a22}));
                userInfo2.setIsAutoLoginStr(a22);
            } catch (Exception e22) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e22);
            }
        }
        String realName = userInfo.getRealName();
        if (!TextUtils.isEmpty(realName)) {
            try {
                String a23 = DataEncryptor.a(contextWrapper, realName);
                AliUserLog.c("UserInfoEncrypter", String.format("src.realName=%s, enc=%s", new Object[]{realName, a23}));
                userInfo2.setRealName(a23);
            } catch (Exception e23) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e23);
            }
        }
        String mobileNumber = userInfo.getMobileNumber();
        if (!TextUtils.isEmpty(mobileNumber)) {
            try {
                String a24 = DataEncryptor.a(contextWrapper, mobileNumber);
                AliUserLog.c("UserInfoEncrypter", String.format("src.mobileNumber=%s, enc=%s", new Object[]{mobileNumber, a24}));
                userInfo2.setMobileNumber(a24);
            } catch (Exception e24) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e24);
            }
        }
        String logonId = userInfo.getLogonId();
        if (!TextUtils.isEmpty(logonId)) {
            try {
                String a25 = DataEncryptor.a(contextWrapper, logonId);
                AliUserLog.c("UserInfoEncrypter", String.format("src.logonId=%s, enc=%s", new Object[]{logonId, a25}));
                userInfo2.setLogonId(a25);
            } catch (Exception e25) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e25);
            }
        }
        String isNewUserStr = userInfo.getIsNewUserStr();
        if (!TextUtils.isEmpty(isNewUserStr)) {
            try {
                String a26 = DataEncryptor.a(contextWrapper, isNewUserStr);
                AliUserLog.c("UserInfoEncrypter", String.format("src.isNewUser=%s, enc=%s", new Object[]{isNewUserStr, a26}));
                userInfo2.setIsNewUserStr(a26);
            } catch (Exception e26) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e26);
            }
        }
        String otherLoginId = userInfo.getOtherLoginId();
        if (!TextUtils.isEmpty(otherLoginId)) {
            try {
                String a27 = DataEncryptor.a(contextWrapper, otherLoginId);
                AliUserLog.c("UserInfoEncrypter", String.format("src.otherLoginId=%s, enc=%s", new Object[]{otherLoginId, a27}));
                userInfo2.setOtherLoginId(a27);
            } catch (Exception e27) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e27);
            }
        }
        return userInfo2;
    }

    public static UserInfo b(UserInfo userInfo, ContextWrapper contextWrapper) {
        if (userInfo == null) {
            return null;
        }
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setLoginTime(userInfo.getLoginTime());
        String taobaoSid = userInfo.getTaobaoSid();
        if (!TextUtils.isEmpty(taobaoSid)) {
            try {
                String b = DataEncryptor.b(contextWrapper, taobaoSid);
                AliUserLog.c("UserInfoEncrypter", String.format("src.taobaoSid=%s, dec=%s", new Object[]{taobaoSid, b}));
                userInfo2.setTaobaoSid(b);
            } catch (Exception e) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e);
            }
        }
        String noPayPwd = userInfo.getNoPayPwd();
        if (!TextUtils.isEmpty(noPayPwd)) {
            try {
                String b2 = DataEncryptor.b(contextWrapper, noPayPwd);
                AliUserLog.c("UserInfoEncrypter", String.format("src.noPayPwd=%s, dec=%s", new Object[]{noPayPwd, b2}));
                userInfo2.setNoPayPwd(b2);
            } catch (Exception e2) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e2);
            }
        }
        String userAvatar = userInfo.getUserAvatar();
        if (!TextUtils.isEmpty(userAvatar)) {
            try {
                String b3 = DataEncryptor.b(contextWrapper, userAvatar);
                AliUserLog.c("UserInfoEncrypter", String.format("src.userAvatar=%s, dec=%s", new Object[]{userAvatar, b3}));
                userInfo2.setUserAvatar(b3);
            } catch (Exception e3) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e3);
            }
        }
        String userType = userInfo.getUserType();
        if (!TextUtils.isEmpty(userType)) {
            try {
                String b4 = DataEncryptor.b(contextWrapper, userType);
                AliUserLog.c("UserInfoEncrypter", String.format("src.userType=%s, dec=%s", new Object[]{userType, b4}));
                userInfo2.setUserType(b4);
            } catch (Exception e4) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e4);
            }
        }
        String loginMobile = userInfo.getLoginMobile();
        if (!TextUtils.isEmpty(loginMobile)) {
            try {
                String b5 = DataEncryptor.b(contextWrapper, loginMobile);
                AliUserLog.c("UserInfoEncrypter", String.format("src.loginMobile=%s, dec=%s", new Object[]{loginMobile, b5}));
                userInfo2.setLoginMobile(b5);
            } catch (Exception e5) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e5);
            }
        }
        String isBindCardStr = userInfo.getIsBindCardStr();
        if (!TextUtils.isEmpty(isBindCardStr)) {
            try {
                String b6 = DataEncryptor.b(contextWrapper, isBindCardStr);
                AliUserLog.c("UserInfoEncrypter", String.format("src.isBindCard=%s, dec=%s", new Object[]{isBindCardStr, b6}));
                userInfo2.setIsBindCardStr(b6);
            } catch (Exception e6) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e6);
            }
        }
        String loginToken = userInfo.getLoginToken();
        if (!TextUtils.isEmpty(loginToken)) {
            try {
                String b7 = DataEncryptor.b(contextWrapper, loginToken);
                AliUserLog.c("UserInfoEncrypter", String.format("src.loginToken=%s, dec=%s", new Object[]{loginToken, b7}));
                userInfo2.setLoginToken(b7);
            } catch (Exception e7) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e7);
            }
        }
        String isWirelessUserStr = userInfo.getIsWirelessUserStr();
        if (!TextUtils.isEmpty(isWirelessUserStr)) {
            try {
                String b8 = DataEncryptor.b(contextWrapper, isWirelessUserStr);
                AliUserLog.c("UserInfoEncrypter", String.format("src.isWirelessUser=%s, dec=%s", new Object[]{isWirelessUserStr, b8}));
                userInfo2.setIsWirelessUserStr(b8);
            } catch (Exception e8) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e8);
            }
        }
        String loginEmail = userInfo.getLoginEmail();
        if (!TextUtils.isEmpty(loginEmail)) {
            try {
                String b9 = DataEncryptor.b(contextWrapper, loginEmail);
                AliUserLog.c("UserInfoEncrypter", String.format("src.loginEmail=%s, dec=%s", new Object[]{loginEmail, b9}));
                userInfo2.setLoginEmail(b9);
            } catch (Exception e9) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e9);
            }
        }
        String taobaoNick = userInfo.getTaobaoNick();
        if (!TextUtils.isEmpty(taobaoNick)) {
            try {
                String b10 = DataEncryptor.b(contextWrapper, taobaoNick);
                AliUserLog.c("UserInfoEncrypter", String.format("src.taobaoNick=%s, dec=%s", new Object[]{taobaoNick, b10}));
                userInfo2.setTaobaoNick(b10);
            } catch (Exception e10) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e10);
            }
        }
        String userId = userInfo.getUserId();
        if (!TextUtils.isEmpty(userId)) {
            try {
                String b11 = DataEncryptor.b(contextWrapper, userId);
                AliUserLog.c("UserInfoEncrypter", String.format("src.userId=%s, dec=%s", new Object[]{userId, b11}));
                userInfo2.setUserId(b11);
            } catch (Exception e11) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e11);
            }
        }
        String memberGrade = userInfo.getMemberGrade();
        if (!TextUtils.isEmpty(memberGrade)) {
            try {
                String b12 = DataEncryptor.b(contextWrapper, memberGrade);
                AliUserLog.c("UserInfoEncrypter", String.format("src.memberGrade=%s, dec=%s", new Object[]{memberGrade, b12}));
                userInfo2.setMemberGrade(b12);
            } catch (Exception e12) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e12);
            }
        }
        String gender = userInfo.getGender();
        if (!TextUtils.isEmpty(gender)) {
            try {
                String b13 = DataEncryptor.b(contextWrapper, gender);
                AliUserLog.c("UserInfoEncrypter", String.format("src.gender=%s, dec=%s", new Object[]{gender, b13}));
                userInfo2.setGender(b13);
            } catch (Exception e13) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e13);
            }
        }
        String userName = userInfo.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            try {
                String b14 = DataEncryptor.b(contextWrapper, userName);
                AliUserLog.c("UserInfoEncrypter", String.format("src.userName=%s, dec=%s", new Object[]{userName, b14}));
                userInfo2.setUserName(b14);
            } catch (Exception e14) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e14);
            }
        }
        String realNamed = userInfo.getRealNamed();
        if (!TextUtils.isEmpty(realNamed)) {
            try {
                String b15 = DataEncryptor.b(contextWrapper, realNamed);
                AliUserLog.c("UserInfoEncrypter", String.format("src.realNamed=%s, dec=%s", new Object[]{realNamed, b15}));
                userInfo2.setRealNamed(b15);
            } catch (Exception e15) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e15);
            }
        }
        String studentCertify = userInfo.getStudentCertify();
        if (!TextUtils.isEmpty(studentCertify)) {
            try {
                String b16 = DataEncryptor.b(contextWrapper, studentCertify);
                AliUserLog.c("UserInfoEncrypter", String.format("src.studentCertify=%s, dec=%s", new Object[]{studentCertify, b16}));
                userInfo2.setStudentCertify(b16);
            } catch (Exception e16) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e16);
            }
        }
        String externToken = userInfo.getExternToken();
        if (!TextUtils.isEmpty(externToken)) {
            try {
                String b17 = DataEncryptor.b(contextWrapper, externToken);
                AliUserLog.c("UserInfoEncrypter", String.format("src.externToken=%s, dec=%s", new Object[]{externToken, b17}));
                userInfo2.setExternToken(b17);
            } catch (Exception e17) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e17);
            }
        }
        String customerType = userInfo.getCustomerType();
        if (!TextUtils.isEmpty(customerType)) {
            try {
                String b18 = DataEncryptor.b(contextWrapper, customerType);
                AliUserLog.c("UserInfoEncrypter", String.format("src.customerType=%s, dec=%s", new Object[]{customerType, b18}));
                userInfo2.setCustomerType(b18);
            } catch (Exception e18) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e18);
            }
        }
        String isCertified = userInfo.getIsCertified();
        if (!TextUtils.isEmpty(isCertified)) {
            try {
                String b19 = DataEncryptor.b(contextWrapper, isCertified);
                AliUserLog.c("UserInfoEncrypter", String.format("src.isCertified=%s, dec=%s", new Object[]{isCertified, b19}));
                userInfo2.setIsCertified(b19);
            } catch (Exception e19) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e19);
            }
        }
        String havanaId = userInfo.getHavanaId();
        if (!TextUtils.isEmpty(havanaId)) {
            try {
                String b20 = DataEncryptor.b(contextWrapper, havanaId);
                AliUserLog.c("UserInfoEncrypter", String.format("src.havanaId=%s, dec=%s", new Object[]{havanaId, b20}));
                userInfo2.setHavanaId(b20);
            } catch (Exception e20) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e20);
            }
        }
        String nick = userInfo.getNick();
        if (!TextUtils.isEmpty(nick)) {
            try {
                String b21 = DataEncryptor.b(contextWrapper, nick);
                AliUserLog.c("UserInfoEncrypter", String.format("src.nick=%s, dec=%s", new Object[]{nick, b21}));
                userInfo2.setNick(b21);
            } catch (Exception e21) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e21);
            }
        }
        String sessionId = userInfo.getSessionId();
        if (!TextUtils.isEmpty(sessionId)) {
            try {
                String b22 = DataEncryptor.b(contextWrapper, sessionId);
                AliUserLog.c("UserInfoEncrypter", String.format("src.sessionId=%s, dec=%s", new Object[]{sessionId, b22}));
                userInfo2.setSessionId(b22);
            } catch (Exception e22) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e22);
            }
        }
        String isAutoLoginStr = userInfo.getIsAutoLoginStr();
        if (!TextUtils.isEmpty(isAutoLoginStr)) {
            try {
                String b23 = DataEncryptor.b(contextWrapper, isAutoLoginStr);
                AliUserLog.c("UserInfoEncrypter", String.format("src.autoLogin=%s, dec=%s", new Object[]{isAutoLoginStr, b23}));
                userInfo2.setIsAutoLoginStr(b23);
            } catch (Exception e23) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e23);
            }
        }
        String realName = userInfo.getRealName();
        if (!TextUtils.isEmpty(realName)) {
            try {
                String b24 = DataEncryptor.b(contextWrapper, realName);
                AliUserLog.c("UserInfoEncrypter", String.format("src.realName=%s, dec=%s", new Object[]{realName, b24}));
                userInfo2.setRealName(b24);
            } catch (Exception e24) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e24);
            }
        }
        String mobileNumber = userInfo.getMobileNumber();
        if (!TextUtils.isEmpty(mobileNumber)) {
            try {
                String b25 = DataEncryptor.b(contextWrapper, mobileNumber);
                AliUserLog.c("UserInfoEncrypter", String.format("src.mobileNumber=%s, dec=%s", new Object[]{mobileNumber, b25}));
                userInfo2.setMobileNumber(b25);
            } catch (Exception e25) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e25);
            }
        }
        String logonId = userInfo.getLogonId();
        if (!TextUtils.isEmpty(logonId)) {
            try {
                String b26 = DataEncryptor.b(contextWrapper, logonId);
                AliUserLog.c("UserInfoEncrypter", String.format("src.logonId=%s, dec=%s", new Object[]{logonId, b26}));
                userInfo2.setLogonId(b26);
            } catch (Exception e26) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e26);
            }
        }
        String isNewUserStr = userInfo.getIsNewUserStr();
        if (!TextUtils.isEmpty(isNewUserStr)) {
            try {
                String b27 = DataEncryptor.b(contextWrapper, isNewUserStr);
                AliUserLog.c("UserInfoEncrypter", String.format("src.isNewUser=%s, dec=%s", new Object[]{isNewUserStr, b27}));
                userInfo2.setIsNewUserStr(b27);
            } catch (Exception e27) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e27);
            }
        }
        String otherLoginId = userInfo.getOtherLoginId();
        if (!TextUtils.isEmpty(otherLoginId)) {
            try {
                String b28 = DataEncryptor.b(contextWrapper, otherLoginId);
                AliUserLog.c("UserInfoEncrypter", String.format("src.otherLoginId=%s, dec=%s", new Object[]{otherLoginId, b28}));
                userInfo2.setOtherLoginId(b28);
            } catch (Exception e28) {
                AliUserLog.b((String) "UserInfoEncrypter", (Throwable) e28);
            }
        }
        return userInfo2;
    }
}
