package defpackage;

import com.autonavi.map.db.model.UserInfo;
import com.autonavi.server.aos.serverkey;

/* renamed from: apf reason: default package */
/* compiled from: UserInfoEncryptUtil */
public final class apf extends UserInfo {
    public static UserInfo a(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        UserInfo userInfo2 = new UserInfo();
        userInfo2._id = userInfo._id;
        userInfo2.sn = userInfo.sn;
        userInfo2.uid = a(userInfo.uid);
        userInfo2.avatar = userInfo.avatar;
        userInfo2.username = userInfo.username;
        userInfo2.birthday = a(userInfo.birthday);
        userInfo2.nick = userInfo.nick;
        userInfo2.sex = userInfo.sex;
        userInfo2.midiconurl = userInfo.midiconurl;
        userInfo2.email = a(userInfo.email);
        userInfo2.bindingmobile = a(userInfo.bindingmobile);
        userInfo2.age = a(userInfo.age);
        userInfo2.sinatoken = userInfo.sinatoken;
        userInfo2.sinaname = userInfo.sinaname;
        userInfo2.toptoken = userInfo.toptoken;
        userInfo2.taobaoname = userInfo.taobaoname;
        userInfo2.taobaoid = a(userInfo.taobaoid);
        userInfo2.qqtoken = userInfo.qqtoken;
        userInfo2.qqname = userInfo.qqname;
        userInfo2.qqid = a(userInfo.qqid);
        userInfo2.wxtoken = userInfo.wxtoken;
        userInfo2.wxname = userInfo.wxname;
        userInfo2.wxid = a(userInfo.wxid);
        userInfo2.source = userInfo.source;
        userInfo2.repwd = userInfo.repwd;
        userInfo2.alipaytoken = userInfo.alipaytoken;
        userInfo2.alipayname = a(userInfo.alipayname);
        userInfo2.alipayid = a(userInfo.alipayid);
        userInfo2.alipayuserid = a(userInfo.alipayuserid);
        userInfo2.meizutoken = userInfo.meizutoken;
        userInfo2.meizuname = userInfo.meizuname;
        userInfo2.meizuid = a(userInfo.meizuid);
        userInfo2.logoid = userInfo.logoid;
        userInfo2.logonormal = userInfo.logonormal;
        userInfo2.logoweak = userInfo.logoweak;
        return userInfo2;
    }

    private static String a(String str) {
        if (str == null) {
            return null;
        }
        return serverkey.amapDecode(str);
    }
}
