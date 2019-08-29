package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v4.app.NotificationCompat;
import com.autonavi.map.db.model.UserInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.taobao.wireless.security.sdk.indiekit.IndieKitDefine;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class UserInfoDao extends AbstractDao<UserInfo, Long> {
    public static final String TABLENAME = "USER_INFO";

    public static class Properties {
        public static final Property A;
        public static final Property B;
        public static final Property C;
        public static final Property D;
        public static final Property E;
        public static final Property F;
        public static final Property G;
        public static final Property H;
        public static final Property I;
        public static final Property J;
        public static final Property K;
        public static final Property L;
        public static final Property M;
        public static final Property N;
        public static final Property O;
        public static final Property P;
        public static final Property Q;
        public static final Property R;
        public static final Property S;
        public static final Property T;
        public static final Property U;
        public static final Property V;
        public static final Property W;
        public static final Property X;
        public static final Property Y;
        public static final Property Z;
        public static final Property a;
        public static final Property aa;
        public static final Property ab;
        public static final Property ac;
        public static final Property ad;
        public static final Property ae;
        public static final Property af;
        public static final Property ag;
        public static final Property ah;
        public static final Property ai;
        public static final Property aj;
        public static final Property ak;
        public static final Property al;
        public static final Property b;
        public static final Property c;
        public static final Property d;
        public static final Property e;
        public static final Property f;
        public static final Property g;
        public static final Property h;
        public static final Property i;
        public static final Property j;
        public static final Property k;
        public static final Property l;
        public static final Property m;
        public static final Property n;
        public static final Property o;
        public static final Property p;
        public static final Property q;
        public static final Property r;
        public static final Property s;
        public static final Property t;
        public static final Property u;
        public static final Property v;
        public static final Property w;
        public static final Property x;
        public static final Property y;
        public static final Property z;

        static {
            Property property = new Property(0, Long.class, "_id", true, "_ID");
            a = property;
            Property property2 = new Property(1, String.class, "sn", false, "SN");
            b = property2;
            Property property3 = new Property(2, String.class, Oauth2AccessToken.KEY_UID, false, "UID");
            c = property3;
            Property property4 = new Property(3, String.class, "avatar", false, "AVATAR");
            d = property4;
            Property property5 = new Property(4, String.class, IndieKitDefine.SG_KEY_INDIE_KIT_USERNAME, false, "USERNAME");
            e = property5;
            Property property6 = new Property(5, String.class, "birthday", false, "BIRTHDAY");
            f = property6;
            Property property7 = new Property(6, String.class, "nick", false, "NICK");
            g = property7;
            Property property8 = new Property(7, Integer.class, "sex", false, "SEX");
            h = property8;
            Property property9 = new Property(8, String.class, "largeiconurl", false, "LARGEICONURL");
            i = property9;
            Property property10 = new Property(9, String.class, "smalliconurl", false, "SMALLICONURL");
            j = property10;
            Property property11 = new Property(10, String.class, "midiconurl", false, "MIDICONURL");
            k = property11;
            Property property12 = new Property(11, String.class, NotificationCompat.CATEGORY_EMAIL, false, "EMAIL");
            l = property12;
            Property property13 = new Property(12, String.class, "bindingmobile", false, "BINDINGMOBILE");
            m = property13;
            Property property14 = new Property(13, String.class, "age", false, "AGE");
            n = property14;
            Property property15 = new Property(14, String.class, "sinatoken", false, "SINATOKEN");
            o = property15;
            Property property16 = new Property(15, String.class, "sinaname", false, "SINANAME");
            p = property16;
            Property property17 = new Property(16, String.class, "toptoken", false, "TOPTOKEN");
            q = property17;
            Property property18 = new Property(17, String.class, "taobaoname", false, "TAOBAONAME");
            r = property18;
            Property property19 = new Property(18, String.class, "taobaoid", false, "TAOBAOID");
            s = property19;
            Property property20 = new Property(19, String.class, "qqtoken", false, "QQTOKEN");
            t = property20;
            Property property21 = new Property(20, String.class, "qqname", false, "QQNAME");
            u = property21;
            Property property22 = new Property(21, String.class, "qqid", false, "QQID");
            v = property22;
            Property property23 = new Property(22, String.class, "wxtoken", false, "WXTOKEN");
            w = property23;
            Property property24 = new Property(23, String.class, "wxname", false, "WXNAME");
            x = property24;
            Property property25 = new Property(24, String.class, "wxid", false, "WXID");
            y = property25;
            Property property26 = new Property(25, String.class, "alipaytoken", false, "ALIPAYTOKEN");
            z = property26;
            Property property27 = new Property(26, String.class, "alipayname", false, "ALIPAYNAME");
            A = property27;
            Property property28 = new Property(27, String.class, "alipayid", false, "ALIPAYID");
            B = property28;
            Property property29 = new Property(28, String.class, "alipayuserid", false, "ALIPAYUSERID");
            C = property29;
            Property property30 = new Property(29, String.class, "meizuid", false, "MEIZUID");
            D = property30;
            Property property31 = new Property(30, String.class, "meizuname", false, "MEIZUNAME");
            E = property31;
            Property property32 = new Property(31, String.class, "meizutoken", false, "MEIZUTOKEN");
            F = property32;
            Property property33 = new Property(32, String.class, "source", false, "SOURCE");
            G = property33;
            Property property34 = new Property(33, Boolean.class, "repwd", false, "REPWD");
            H = property34;
            Property property35 = new Property(34, String.class, "logoid", false, "LOGOID");
            I = property35;
            Property property36 = new Property(35, String.class, "logonormal", false, "LOGONORMAL");
            J = property36;
            Property property37 = new Property(36, String.class, "logoweak", false, "LOGOWEAK");
            K = property37;
            Property property38 = new Property(37, String.class, "_sn", false, "_SN");
            L = property38;
            Property property39 = new Property(38, String.class, "_uid", false, "_UID");
            M = property39;
            Property property40 = new Property(39, String.class, "_username", false, "_USERNAME");
            N = property40;
            Property property41 = new Property(40, String.class, "_birthday", false, "_BIRTHDAY");
            O = property41;
            Property property42 = new Property(41, String.class, "_nick", false, "_NICK");
            P = property42;
            Property property43 = new Property(42, Integer.class, "_sex", false, "_SEX");
            Q = property43;
            Property property44 = new Property(43, String.class, "_email", false, "_EMAIL");
            R = property44;
            Property property45 = new Property(44, String.class, "_bindingmobile", false, "_BINDINGMOBILE");
            S = property45;
            Property property46 = new Property(45, String.class, "_age", false, "_AGE");
            T = property46;
            Property property47 = new Property(46, String.class, "_sinatoken", false, "_SINATOKEN");
            U = property47;
            Property property48 = new Property(47, String.class, "_sinaname", false, "_SINANAME");
            V = property48;
            Property property49 = new Property(48, String.class, "_toptoken", false, "_TOPTOKEN");
            W = property49;
            Property property50 = new Property(49, String.class, "_taobaoname", false, "_TAOBAONAME");
            X = property50;
            Property property51 = new Property(50, String.class, "_taobaoid", false, "_TAOBAOID");
            Y = property51;
            Property property52 = new Property(51, String.class, "_qqtoken", false, "_QQTOKEN");
            Z = property52;
            Property property53 = new Property(52, String.class, "_qqname", false, "_QQNAME");
            aa = property53;
            Property property54 = new Property(53, String.class, "_qqid", false, "_QQID");
            ab = property54;
            Property property55 = new Property(54, String.class, "_wxtoken", false, "_WXTOKEN");
            ac = property55;
            Property property56 = new Property(55, String.class, "_wxname", false, "_WXNAME");
            ad = property56;
            Property property57 = new Property(56, String.class, "_wxid", false, "_WXID");
            ae = property57;
            Property property58 = new Property(57, String.class, "_alipaytoken", false, "_ALIPAYTOKEN");
            af = property58;
            Property property59 = new Property(58, String.class, "_alipayname", false, "_ALIPAYNAME");
            ag = property59;
            Property property60 = new Property(59, String.class, "_alipayid", false, "_ALIPAYID");
            ah = property60;
            Property property61 = new Property(60, String.class, "_alipayuserid", false, "_ALIPAYUSERID");
            ai = property61;
            Property property62 = new Property(61, String.class, "_meizuid", false, "_MEIZUID");
            aj = property62;
            Property property63 = new Property(62, String.class, "_meizuname", false, "_MEIZUNAME");
            ak = property63;
            Property property64 = new Property(63, String.class, "_meizutoken", false, "_MEIZUTOKEN");
            al = property64;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        UserInfo userInfo = (UserInfo) obj;
        sQLiteStatement.clearBindings();
        Long l = userInfo._id;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String str = userInfo.avatar;
        if (str != null) {
            sQLiteStatement.bindString(4, str);
        }
        Integer num = userInfo.sex;
        if (num != null) {
            sQLiteStatement.bindLong(8, (long) num.intValue());
        }
        String str2 = userInfo.largeiconurl;
        if (str2 != null) {
            sQLiteStatement.bindString(9, str2);
        }
        String str3 = userInfo.smalliconurl;
        if (str3 != null) {
            sQLiteStatement.bindString(10, str3);
        }
        String str4 = userInfo.midiconurl;
        if (str4 != null) {
            sQLiteStatement.bindString(11, str4);
        }
        String str5 = userInfo.source;
        if (str5 != null) {
            sQLiteStatement.bindString(33, str5);
        }
        Boolean bool = userInfo.repwd;
        if (bool != null) {
            sQLiteStatement.bindLong(34, bool.booleanValue() ? 1 : 0);
        }
        String str6 = userInfo.logoid;
        if (str6 != null) {
            sQLiteStatement.bindString(35, str6);
        }
        String str7 = userInfo.logonormal;
        if (str7 != null) {
            sQLiteStatement.bindString(36, str7);
        }
        String str8 = userInfo.logoweak;
        if (str8 != null) {
            sQLiteStatement.bindString(37, str8);
        }
        String a = xu.a(userInfo.sn);
        if (a != null) {
            sQLiteStatement.bindString(38, a);
        }
        String a2 = xu.a(userInfo.uid);
        if (a2 != null) {
            sQLiteStatement.bindString(39, a2);
        }
        String a3 = xu.a(userInfo.username);
        if (a3 != null) {
            sQLiteStatement.bindString(40, a3);
        }
        String a4 = xu.a(userInfo.birthday);
        if (a4 != null) {
            sQLiteStatement.bindString(41, a4);
        }
        String a5 = xu.a(userInfo.nick);
        if (a5 != null) {
            sQLiteStatement.bindString(42, a5);
        }
        String a6 = xu.a(userInfo.email);
        if (a6 != null) {
            sQLiteStatement.bindString(44, a6);
        }
        String a7 = xu.a(userInfo.bindingmobile);
        if (a7 != null) {
            sQLiteStatement.bindString(45, a7);
        }
        String a8 = xu.a(userInfo.age);
        if (a8 != null) {
            sQLiteStatement.bindString(46, a8);
        }
        String a9 = xu.a(userInfo.sinatoken);
        if (a9 != null) {
            sQLiteStatement.bindString(47, a9);
        }
        String a10 = xu.a(userInfo.sinaname);
        if (a10 != null) {
            sQLiteStatement.bindString(48, a10);
        }
        String a11 = xu.a(userInfo.toptoken);
        if (a11 != null) {
            sQLiteStatement.bindString(49, a11);
        }
        String a12 = xu.a(userInfo.taobaoname);
        if (a12 != null) {
            sQLiteStatement.bindString(50, a12);
        }
        String a13 = xu.a(userInfo.taobaoid);
        if (a13 != null) {
            sQLiteStatement.bindString(51, a13);
        }
        String a14 = xu.a(userInfo.qqtoken);
        if (a14 != null) {
            sQLiteStatement.bindString(52, a14);
        }
        String a15 = xu.a(userInfo.qqname);
        if (a15 != null) {
            sQLiteStatement.bindString(53, a15);
        }
        String a16 = xu.a(userInfo.qqid);
        if (a16 != null) {
            sQLiteStatement.bindString(54, a16);
        }
        String a17 = xu.a(userInfo.wxtoken);
        if (a17 != null) {
            sQLiteStatement.bindString(55, a17);
        }
        String a18 = xu.a(userInfo.wxname);
        if (a18 != null) {
            sQLiteStatement.bindString(56, a18);
        }
        String a19 = xu.a(userInfo.wxid);
        if (a19 != null) {
            sQLiteStatement.bindString(57, a19);
        }
        String a20 = xu.a(userInfo.alipaytoken);
        if (a20 != null) {
            sQLiteStatement.bindString(58, a20);
        }
        String a21 = xu.a(userInfo.alipayname);
        if (a21 != null) {
            sQLiteStatement.bindString(59, a21);
        }
        String a22 = xu.a(userInfo.alipayid);
        if (a22 != null) {
            sQLiteStatement.bindString(60, a22);
        }
        String a23 = xu.a(userInfo.alipayuserid);
        if (a23 != null) {
            sQLiteStatement.bindString(61, a23);
        }
        String a24 = xu.a(userInfo.meizuid);
        if (a24 != null) {
            sQLiteStatement.bindString(62, a24);
        }
        String a25 = xu.a(userInfo.meizuname);
        if (a25 != null) {
            sQLiteStatement.bindString(63, a25);
        }
        String a26 = xu.a(userInfo.meizutoken);
        if (a26 != null) {
            sQLiteStatement.bindString(64, a26);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        UserInfo userInfo = (UserInfo) obj;
        if (userInfo != null) {
            return userInfo._id;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        Boolean bool;
        UserInfo userInfo = (UserInfo) obj;
        int i2 = i + 0;
        String str = null;
        userInfo._id = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 3;
        userInfo.avatar = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 7;
        userInfo.sex = cursor.isNull(i4) ? null : Integer.valueOf(cursor.getInt(i4));
        int i5 = i + 8;
        userInfo.largeiconurl = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 9;
        userInfo.smalliconurl = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 10;
        userInfo.midiconurl = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 32;
        userInfo.source = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 33;
        if (cursor.isNull(i9)) {
            bool = null;
        } else {
            bool = Boolean.valueOf(cursor.getShort(i9) != 0);
        }
        userInfo.repwd = bool;
        int i10 = i + 34;
        userInfo.logoid = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 35;
        userInfo.logonormal = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 36;
        userInfo.logoweak = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = i + 37;
        userInfo.sn = cursor.isNull(i13) ? null : xu.b(cursor.getString(i13));
        int i14 = i + 38;
        userInfo.uid = cursor.isNull(i14) ? null : xu.b(cursor.getString(i14));
        int i15 = i + 39;
        userInfo.username = cursor.isNull(i15) ? null : xu.b(cursor.getString(i15));
        int i16 = i + 40;
        userInfo.birthday = cursor.isNull(i16) ? null : xu.b(cursor.getString(i16));
        int i17 = i + 41;
        userInfo.nick = cursor.isNull(i17) ? null : xu.b(cursor.getString(i17));
        int i18 = i + 43;
        userInfo.email = cursor.isNull(i18) ? null : xu.b(cursor.getString(i18));
        int i19 = i + 44;
        userInfo.bindingmobile = cursor.isNull(i19) ? null : xu.b(cursor.getString(i19));
        int i20 = i + 45;
        userInfo.age = cursor.isNull(i20) ? null : xu.b(cursor.getString(i20));
        int i21 = i + 46;
        userInfo.sinatoken = cursor.isNull(i21) ? null : xu.b(cursor.getString(i21));
        int i22 = i + 47;
        userInfo.sinaname = cursor.isNull(i22) ? null : xu.b(cursor.getString(i22));
        int i23 = i + 48;
        userInfo.toptoken = cursor.isNull(i23) ? null : xu.b(cursor.getString(i23));
        int i24 = i + 49;
        userInfo.taobaoname = cursor.isNull(i24) ? null : xu.b(cursor.getString(i24));
        int i25 = i + 50;
        userInfo.taobaoid = cursor.isNull(i25) ? null : xu.b(cursor.getString(i25));
        int i26 = i + 51;
        userInfo.qqtoken = cursor.isNull(i26) ? null : xu.b(cursor.getString(i26));
        int i27 = i + 52;
        userInfo.qqname = cursor.isNull(i27) ? null : xu.b(cursor.getString(i27));
        int i28 = i + 53;
        userInfo.qqid = cursor.isNull(i28) ? null : xu.b(cursor.getString(i28));
        int i29 = i + 54;
        userInfo.wxtoken = cursor.isNull(i29) ? null : xu.b(cursor.getString(i29));
        int i30 = i + 55;
        userInfo.wxname = cursor.isNull(i30) ? null : xu.b(cursor.getString(i30));
        int i31 = i + 56;
        userInfo.wxid = cursor.isNull(i31) ? null : xu.b(cursor.getString(i31));
        int i32 = i + 57;
        userInfo.alipaytoken = cursor.isNull(i32) ? null : xu.b(cursor.getString(i32));
        int i33 = i + 58;
        userInfo.alipayname = cursor.isNull(i33) ? null : xu.b(cursor.getString(i33));
        int i34 = i + 59;
        userInfo.alipayid = cursor.isNull(i34) ? null : xu.b(cursor.getString(i34));
        int i35 = i + 60;
        userInfo.alipayuserid = cursor.isNull(i35) ? null : xu.b(cursor.getString(i35));
        int i36 = i + 61;
        userInfo.meizuid = cursor.isNull(i36) ? null : xu.b(cursor.getString(i36));
        int i37 = i + 62;
        userInfo.meizuname = cursor.isNull(i37) ? null : xu.b(cursor.getString(i37));
        int i38 = i + 63;
        if (!cursor.isNull(i38)) {
            str = xu.b(cursor.getString(i38));
        }
        userInfo.meizutoken = str;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((UserInfo) obj)._id = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public UserInfoDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"USER_INFO\" (\"_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"SN\" TEXT,\"UID\" TEXT,\"AVATAR\" TEXT,\"USERNAME\" TEXT,\"BIRTHDAY\" TEXT,\"NICK\" TEXT,\"SEX\" INTEGER,\"LARGEICONURL\" TEXT,\"SMALLICONURL\" TEXT,\"MIDICONURL\" TEXT,\"EMAIL\" TEXT,\"BINDINGMOBILE\" TEXT,\"AGE\" TEXT,\"SINATOKEN\" TEXT,\"SINANAME\" TEXT,\"TOPTOKEN\" TEXT,\"TAOBAONAME\" TEXT,\"TAOBAOID\" TEXT,\"QQTOKEN\" TEXT,\"QQNAME\" TEXT,\"QQID\" TEXT,\"WXTOKEN\" TEXT,\"WXNAME\" TEXT,\"WXID\" TEXT,\"ALIPAYTOKEN\" TEXT,\"ALIPAYNAME\" TEXT,\"ALIPAYID\" TEXT,\"ALIPAYUSERID\" TEXT,\"MEIZUID\" TEXT,\"MEIZUNAME\" TEXT,\"MEIZUTOKEN\" TEXT,\"SOURCE\" TEXT,\"REPWD\" INTEGER,\"LOGOID\" TEXT,\"LOGONORMAL\" TEXT,\"LOGOWEAK\" TEXT,\"_SN\" TEXT,\"_UID\" TEXT,\"_USERNAME\" TEXT,\"_BIRTHDAY\" TEXT,\"_NICK\" TEXT,\"_SEX\" INTEGER,\"_EMAIL\" TEXT,\"_BINDINGMOBILE\" TEXT,\"_AGE\" TEXT,\"_SINATOKEN\" TEXT,\"_SINANAME\" TEXT,\"_TOPTOKEN\" TEXT,\"_TAOBAONAME\" TEXT,\"_TAOBAOID\" TEXT,\"_QQTOKEN\" TEXT,\"_QQNAME\" TEXT,\"_QQID\" TEXT,\"_WXTOKEN\" TEXT,\"_WXNAME\" TEXT,\"_WXID\" TEXT,\"_ALIPAYTOKEN\" TEXT,\"_ALIPAYNAME\" TEXT,\"_ALIPAYID\" TEXT,\"_ALIPAYUSERID\" TEXT,\"_MEIZUID\" TEXT,\"_MEIZUNAME\" TEXT,\"_MEIZUTOKEN\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"USER_INFO\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        UserInfo userInfo = new UserInfo();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            userInfo._id = Long.valueOf(cursor.getLong(i2));
        }
        int i3 = i + 3;
        if (!cursor.isNull(i3)) {
            userInfo.avatar = cursor.getString(i3);
        }
        int i4 = i + 7;
        if (!cursor.isNull(i4)) {
            userInfo.sex = Integer.valueOf(cursor.getInt(i4));
        }
        int i5 = i + 8;
        if (!cursor.isNull(i5)) {
            userInfo.largeiconurl = cursor.getString(i5);
        }
        int i6 = i + 9;
        if (!cursor.isNull(i6)) {
            userInfo.smalliconurl = cursor.getString(i6);
        }
        int i7 = i + 10;
        if (!cursor.isNull(i7)) {
            userInfo.midiconurl = cursor.getString(i7);
        }
        int i8 = i + 32;
        if (!cursor.isNull(i8)) {
            userInfo.source = cursor.getString(i8);
        }
        int i9 = i + 33;
        if (!cursor.isNull(i9)) {
            userInfo.repwd = Boolean.valueOf(cursor.getShort(i9) != 0);
        }
        int i10 = i + 34;
        if (!cursor.isNull(i10)) {
            userInfo.logoid = cursor.getString(i10);
        }
        int i11 = i + 35;
        if (!cursor.isNull(i11)) {
            userInfo.logonormal = cursor.getString(i11);
        }
        int i12 = i + 36;
        if (!cursor.isNull(i12)) {
            userInfo.logoweak = cursor.getString(i12);
        }
        int i13 = i + 37;
        if (!cursor.isNull(i13)) {
            userInfo.sn = xu.b(cursor.getString(i13));
        }
        int i14 = i + 38;
        if (!cursor.isNull(i14)) {
            userInfo.uid = xu.b(cursor.getString(i14));
        }
        int i15 = i + 39;
        if (!cursor.isNull(i15)) {
            userInfo.username = xu.b(cursor.getString(i15));
        }
        int i16 = i + 40;
        if (!cursor.isNull(i16)) {
            userInfo.birthday = xu.b(cursor.getString(i16));
        }
        int i17 = i + 41;
        if (!cursor.isNull(i17)) {
            userInfo.nick = xu.b(cursor.getString(i17));
        }
        int i18 = i + 43;
        if (!cursor.isNull(i18)) {
            userInfo.email = xu.b(cursor.getString(i18));
        }
        int i19 = i + 44;
        if (!cursor.isNull(i19)) {
            userInfo.bindingmobile = xu.b(cursor.getString(i19));
        }
        int i20 = i + 45;
        if (!cursor.isNull(i20)) {
            userInfo.age = xu.b(cursor.getString(i20));
        }
        int i21 = i + 46;
        if (!cursor.isNull(i21)) {
            userInfo.sinatoken = xu.b(cursor.getString(i21));
        }
        int i22 = i + 47;
        if (!cursor.isNull(i22)) {
            userInfo.sinaname = xu.b(cursor.getString(i22));
        }
        int i23 = i + 48;
        if (!cursor.isNull(i23)) {
            userInfo.toptoken = xu.b(cursor.getString(i23));
        }
        int i24 = i + 49;
        if (!cursor.isNull(i24)) {
            userInfo.taobaoname = xu.b(cursor.getString(i24));
        }
        int i25 = i + 50;
        if (!cursor.isNull(i25)) {
            userInfo.taobaoid = xu.b(cursor.getString(i25));
        }
        int i26 = i + 51;
        if (!cursor.isNull(i26)) {
            userInfo.qqtoken = xu.b(cursor.getString(i26));
        }
        int i27 = i + 52;
        if (!cursor.isNull(i27)) {
            userInfo.qqname = xu.b(cursor.getString(i27));
        }
        int i28 = i + 53;
        if (!cursor.isNull(i28)) {
            userInfo.qqid = xu.b(cursor.getString(i28));
        }
        int i29 = i + 54;
        if (!cursor.isNull(i29)) {
            userInfo.wxtoken = xu.b(cursor.getString(i29));
        }
        int i30 = i + 55;
        if (!cursor.isNull(i30)) {
            userInfo.wxname = xu.b(cursor.getString(i30));
        }
        int i31 = i + 56;
        if (!cursor.isNull(i31)) {
            userInfo.wxid = xu.b(cursor.getString(i31));
        }
        int i32 = i + 57;
        if (!cursor.isNull(i32)) {
            userInfo.alipaytoken = xu.b(cursor.getString(i32));
        }
        int i33 = i + 58;
        if (!cursor.isNull(i33)) {
            userInfo.alipayname = xu.b(cursor.getString(i33));
        }
        int i34 = i + 59;
        if (!cursor.isNull(i34)) {
            userInfo.alipayid = xu.b(cursor.getString(i34));
        }
        int i35 = i + 60;
        if (!cursor.isNull(i35)) {
            userInfo.alipayuserid = xu.b(cursor.getString(i35));
        }
        int i36 = i + 61;
        if (!cursor.isNull(i36)) {
            userInfo.meizuid = xu.b(cursor.getString(i36));
        }
        int i37 = i + 62;
        if (!cursor.isNull(i37)) {
            userInfo.meizuname = xu.b(cursor.getString(i37));
        }
        int i38 = i + 63;
        if (!cursor.isNull(i38)) {
            userInfo.meizutoken = xu.b(cursor.getString(i38));
        }
        return userInfo;
    }
}
