package com.autonavi.map.msgbox.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.common.logging.util.avail.ExceptionData;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.autonavi.map.db.model.Msgbox;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import org.eclipse.mat.hprof.IHprofParserHandler;

public class MsgboxDao extends AbstractDao<Msgbox, String> {
    public static final String TABLENAME = "MSGBOX";

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
            Property property = new Property(0, String.class, "id", true, AutoJsonUtils.JSON_ID);
            a = property;
            Property property2 = new Property(1, String.class, "version", false, IHprofParserHandler.VERSION);
            b = property2;
            Property property3 = new Property(2, String.class, "json", false, "JSON");
            c = property3;
            Property property4 = new Property(3, String.class, "category", false, "CATEGORY");
            d = property4;
            Property property5 = new Property(4, Long.TYPE, "createdTime", false, "CREATED_TIME");
            e = property5;
            Property property6 = new Property(5, Long.TYPE, "expireAt", false, "EXPIRE_AT");
            f = property6;
            Property property7 = new Property(6, String.class, "features", false, "FEATURES");
            g = property7;
            Property property8 = new Property(7, String.class, "actionUri", false, "ACTION_URI");
            h = property8;
            Property property9 = new Property(8, String.class, "creator", false, "CREATOR");
            i = property9;
            Property property10 = new Property(9, String.class, "trackId", false, "TRACK_ID");
            j = property10;
            Property property11 = new Property(10, String.class, "extra", false, "EXTRA");
            k = property11;
            Property property12 = new Property(11, String.class, "title", false, "TITLE");
            l = property12;
            Property property13 = new Property(12, String.class, "descMessage", false, "DESC_MESSAGE");
            m = property13;
            Property property14 = new Property(13, Integer.TYPE, "priority", false, "PRIORITY");
            n = property14;
            Property property15 = new Property(14, Boolean.TYPE, "isUnRead", false, "IS_UN_READ");
            o = property15;
            Property property16 = new Property(15, Boolean.TYPE, "showOnMap", false, "SHOW_ON_MAP");
            p = property16;
            Property property17 = new Property(16, String.class, "reside", false, "RESIDE");
            q = property17;
            Property property18 = new Property(17, String.class, "baricon", false, "BARICON");
            r = property18;
            Property property19 = new Property(18, String.class, "msgImgUri", false, "MSG_IMG_URI");
            s = property19;
            Property property20 = new Property(19, String.class, "label", false, "LABEL");
            t = property20;
            Property property21 = new Property(20, String.class, "countdownEndtime", false, "COUNTDOWN_ENDTIME");
            u = property21;
            Property property22 = new Property(21, String.class, "isEnable", false, "IS_ENABLE");
            v = property22;
            Property property23 = new Property(22, String.class, "parentId", false, "PARENT_ID");
            w = property23;
            Property property24 = new Property(23, String.class, "wordStatus", false, "WORD_STATUS");
            x = property24;
            Property property25 = new Property(24, String.class, "showBody", false, "SHOW_BODY");
            y = property25;
            Property property26 = new Property(25, String.class, "nickName", false, "NICK_NAME");
            z = property26;
            Property property27 = new Property(26, String.class, "pushMsgId", false, "PUSH_MSG_ID");
            A = property27;
            Property property28 = new Property(27, Integer.TYPE, "goldNum", false, "GOLD_NUM");
            B = property28;
            Property property29 = new Property(28, Integer.TYPE, "totalGoldNum", false, "TOTAL_GOLD_NUM");
            C = property29;
            Property property30 = new Property(29, String.class, "goldImage1", false, "GOLD_IMAGE1");
            D = property30;
            Property property31 = new Property(30, String.class, "goldImage2", false, "GOLD_IMAGE2");
            E = property31;
            Property property32 = new Property(31, String.class, "extData_gj_name_array", false, "EXT_DATA_GJ_NAME_ARRAY");
            F = property32;
            Property property33 = new Property(32, String.class, "extData_gj_type", false, "EXT_DATA_GJ_TYPE");
            G = property33;
            Property property34 = new Property(33, Boolean.TYPE, "isNewComing", false, "IS_NEW_COMING");
            H = property34;
            Property property35 = new Property(34, Boolean.TYPE, "hasShown", false, "HAS_SHOWN");
            I = property35;
            Property property36 = new Property(35, String.class, "ope", false, "OPE");
            J = property36;
            Property property37 = new Property(36, String.class, "type", false, ExceptionData.E_TYPE);
            K = property37;
            Property property38 = new Property(37, Integer.TYPE, "tag", false, RPCDataItems.SWITCH_TAG_LOG);
            L = property38;
            Property property39 = new Property(38, String.class, AutoJsonUtils.JSON_ADCODE, false, "ADCODE");
            M = property39;
            Property property40 = new Property(39, String.class, "shortNameCity", false, "SHORT_NAME_CITY");
            N = property40;
            Property property41 = new Property(40, Integer.TYPE, "source", false, "SOURCE");
            O = property41;
            Property property42 = new Property(41, Boolean.TYPE, "shouldFormat", false, "SHOULD_FORMAT");
            P = property42;
            Property property43 = new Property(42, Boolean.TYPE, "bannerUpdated", false, "BANNER_UPDATED");
            Q = property43;
            Property property44 = new Property(43, Integer.TYPE, "page", false, "PAGE");
            R = property44;
            Property property45 = new Property(44, Integer.TYPE, "location", false, "LOCATION");
            S = property45;
            Property property46 = new Property(45, String.class, ActionConstant.IMG_URL, false, "IMG_URL");
            T = property46;
            Property property47 = new Property(46, String.class, "actions", false, "ACTIONS");
            U = property47;
            Property property48 = new Property(47, Boolean.TYPE, "hasSub", false, "HAS_SUB");
            V = property48;
            Property property49 = new Property(48, String.class, "subImgUrl", false, "SUB_IMG_URL");
            W = property49;
            Property property50 = new Property(49, String.class, "subTitle", false, "SUB_TITLE");
            X = property50;
            Property property51 = new Property(50, Integer.TYPE, "sub_page", false, "SUB_PAGE");
            Y = property51;
            Property property52 = new Property(51, Integer.TYPE, "sub_location", false, "SUB_LOCATION");
            Z = property52;
            Property property53 = new Property(52, Boolean.TYPE, "sub_unread", false, "SUB_UNREAD");
            aa = property53;
            Property property54 = new Property(53, Integer.TYPE, "msgType", false, "MSG_TYPE");
            ab = property54;
            Property property55 = new Property(54, Boolean.TYPE, "mesDisplay", false, "MES_DISPLAY");
            ac = property55;
            Property property56 = new Property(55, Boolean.TYPE, "boxDisplay", false, "BOX_DISPLAY");
            ad = property56;
            Property property57 = new Property(56, Integer.TYPE, RpcConstant.SHOW_TYPE, false, "SHOW_TYPE");
            ae = property57;
            Property property58 = new Property(57, String.class, "labelColor", false, "LABEL_COLOR");
            af = property58;
            Property property59 = new Property(58, Long.TYPE, "updateTime", false, "UPDATE_TIME");
            ag = property59;
            Property property60 = new Property(59, String.class, "impression", false, "IMPRESSION");
            ah = property60;
            Property property61 = new Property(60, String.class, "msgImgUriV2", false, "MSG_IMG_URI_V2");
            ai = property61;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        Msgbox msgbox = (Msgbox) obj;
        sQLiteStatement.clearBindings();
        String str = msgbox.id;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = msgbox.version;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = msgbox.json;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        String str4 = msgbox.category;
        if (str4 != null) {
            sQLiteStatement.bindString(4, str4);
        }
        sQLiteStatement.bindLong(5, msgbox.createdTime);
        sQLiteStatement.bindLong(6, msgbox.expireAt);
        String str5 = msgbox.features;
        if (str5 != null) {
            sQLiteStatement.bindString(7, str5);
        }
        String str6 = msgbox.actionUri;
        if (str6 != null) {
            sQLiteStatement.bindString(8, str6);
        }
        String str7 = msgbox.creator;
        if (str7 != null) {
            sQLiteStatement.bindString(9, str7);
        }
        String str8 = msgbox.trackId;
        if (str8 != null) {
            sQLiteStatement.bindString(10, str8);
        }
        String str9 = msgbox.extra;
        if (str9 != null) {
            sQLiteStatement.bindString(11, str9);
        }
        String str10 = msgbox.title;
        if (str10 != null) {
            sQLiteStatement.bindString(12, str10);
        }
        String str11 = msgbox.descMessage;
        if (str11 != null) {
            sQLiteStatement.bindString(13, str11);
        }
        sQLiteStatement.bindLong(14, (long) msgbox.priority);
        long j = 0;
        sQLiteStatement.bindLong(15, msgbox.isUnRead ? 1 : 0);
        sQLiteStatement.bindLong(16, msgbox.showOnMap ? 1 : 0);
        String str12 = msgbox.reside;
        if (str12 != null) {
            sQLiteStatement.bindString(17, str12);
        }
        String str13 = msgbox.baricon;
        if (str13 != null) {
            sQLiteStatement.bindString(18, str13);
        }
        String str14 = msgbox.msgImgUri;
        if (str14 != null) {
            sQLiteStatement.bindString(19, str14);
        }
        String str15 = msgbox.label;
        if (str15 != null) {
            sQLiteStatement.bindString(20, str15);
        }
        String str16 = msgbox.countdownEndtime;
        if (str16 != null) {
            sQLiteStatement.bindString(21, str16);
        }
        String str17 = msgbox.isEnable;
        if (str17 != null) {
            sQLiteStatement.bindString(22, str17);
        }
        String str18 = msgbox.parentId;
        if (str18 != null) {
            sQLiteStatement.bindString(23, str18);
        }
        String str19 = msgbox.wordStatus;
        if (str19 != null) {
            sQLiteStatement.bindString(24, str19);
        }
        String str20 = msgbox.showBody;
        if (str20 != null) {
            sQLiteStatement.bindString(25, str20);
        }
        String str21 = msgbox.nickName;
        if (str21 != null) {
            sQLiteStatement.bindString(26, str21);
        }
        String str22 = msgbox.pushMsgId;
        if (str22 != null) {
            sQLiteStatement.bindString(27, str22);
        }
        sQLiteStatement.bindLong(28, (long) msgbox.goldNum);
        sQLiteStatement.bindLong(29, (long) msgbox.totalGoldNum);
        String str23 = msgbox.goldImage1;
        if (str23 != null) {
            sQLiteStatement.bindString(30, str23);
        }
        String str24 = msgbox.goldImage2;
        if (str24 != null) {
            sQLiteStatement.bindString(31, str24);
        }
        String str25 = msgbox.extData_gj_name_array;
        if (str25 != null) {
            sQLiteStatement.bindString(32, str25);
        }
        String str26 = msgbox.extData_gj_type;
        if (str26 != null) {
            sQLiteStatement.bindString(33, str26);
        }
        sQLiteStatement.bindLong(34, msgbox.isNewComing ? 1 : 0);
        sQLiteStatement.bindLong(35, msgbox.hasShown ? 1 : 0);
        String str27 = msgbox.ope;
        if (str27 != null) {
            sQLiteStatement.bindString(36, str27);
        }
        String str28 = msgbox.type;
        if (str28 != null) {
            sQLiteStatement.bindString(37, str28);
        }
        sQLiteStatement.bindLong(38, (long) msgbox.tag);
        String str29 = msgbox.adcode;
        if (str29 != null) {
            sQLiteStatement.bindString(39, str29);
        }
        String str30 = msgbox.shortNameCity;
        if (str30 != null) {
            sQLiteStatement.bindString(40, str30);
        }
        sQLiteStatement.bindLong(41, (long) msgbox.source);
        sQLiteStatement.bindLong(42, msgbox.shouldFormat ? 1 : 0);
        sQLiteStatement.bindLong(43, msgbox.bannerUpdated ? 1 : 0);
        sQLiteStatement.bindLong(44, (long) msgbox.page);
        sQLiteStatement.bindLong(45, (long) msgbox.location);
        String str31 = msgbox.imgUrl;
        if (str31 != null) {
            sQLiteStatement.bindString(46, str31);
        }
        String str32 = msgbox.actions;
        if (str32 != null) {
            sQLiteStatement.bindString(47, str32);
        }
        sQLiteStatement.bindLong(48, msgbox.hasSub ? 1 : 0);
        String str33 = msgbox.subImgUrl;
        if (str33 != null) {
            sQLiteStatement.bindString(49, str33);
        }
        String str34 = msgbox.subTitle;
        if (str34 != null) {
            sQLiteStatement.bindString(50, str34);
        }
        sQLiteStatement.bindLong(51, (long) msgbox.sub_page);
        sQLiteStatement.bindLong(52, (long) msgbox.sub_location);
        sQLiteStatement.bindLong(53, msgbox.sub_unread ? 1 : 0);
        sQLiteStatement.bindLong(54, (long) msgbox.msgType);
        sQLiteStatement.bindLong(55, msgbox.mesDisplay ? 1 : 0);
        if (msgbox.boxDisplay) {
            j = 1;
        }
        sQLiteStatement.bindLong(56, j);
        sQLiteStatement.bindLong(57, (long) msgbox.showType);
        String str35 = msgbox.labelColor;
        if (str35 != null) {
            sQLiteStatement.bindString(58, str35);
        }
        sQLiteStatement.bindLong(59, msgbox.updateTime);
        if (msgbox.impression != null) {
            sQLiteStatement.bindString(60, msgbox.impression);
        }
        String str36 = msgbox.msgImgUriV2;
        if (str36 != null) {
            sQLiteStatement.bindString(61, str36);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        Msgbox msgbox = (Msgbox) obj;
        if (msgbox != null) {
            return msgbox.id;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        Msgbox msgbox = (Msgbox) obj;
        int i2 = i + 0;
        String str = null;
        msgbox.id = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        msgbox.version = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        msgbox.json = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        msgbox.category = cursor.isNull(i5) ? null : cursor.getString(i5);
        msgbox.createdTime = cursor.getLong(i + 4);
        msgbox.expireAt = cursor.getLong(i + 5);
        int i6 = i + 6;
        msgbox.features = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 7;
        msgbox.actionUri = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 8;
        msgbox.creator = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 9;
        msgbox.trackId = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 10;
        msgbox.extra = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 11;
        msgbox.title = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 12;
        msgbox.descMessage = cursor.isNull(i12) ? null : cursor.getString(i12);
        msgbox.priority = cursor.getInt(i + 13);
        boolean z = true;
        msgbox.isUnRead = cursor.getShort(i + 14) != 0;
        msgbox.showOnMap = cursor.getShort(i + 15) != 0;
        int i13 = i + 16;
        msgbox.reside = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = i + 17;
        msgbox.baricon = cursor.isNull(i14) ? null : cursor.getString(i14);
        int i15 = i + 18;
        msgbox.msgImgUri = cursor.isNull(i15) ? null : cursor.getString(i15);
        int i16 = i + 19;
        msgbox.label = cursor.isNull(i16) ? null : cursor.getString(i16);
        int i17 = i + 20;
        msgbox.countdownEndtime = cursor.isNull(i17) ? null : cursor.getString(i17);
        int i18 = i + 21;
        msgbox.isEnable = cursor.isNull(i18) ? null : cursor.getString(i18);
        int i19 = i + 22;
        msgbox.parentId = cursor.isNull(i19) ? null : cursor.getString(i19);
        int i20 = i + 23;
        msgbox.wordStatus = cursor.isNull(i20) ? null : cursor.getString(i20);
        int i21 = i + 24;
        msgbox.showBody = cursor.isNull(i21) ? null : cursor.getString(i21);
        int i22 = i + 25;
        msgbox.nickName = cursor.isNull(i22) ? null : cursor.getString(i22);
        int i23 = i + 26;
        msgbox.pushMsgId = cursor.isNull(i23) ? null : cursor.getString(i23);
        msgbox.goldNum = cursor.getInt(i + 27);
        msgbox.totalGoldNum = cursor.getInt(i + 28);
        int i24 = i + 29;
        msgbox.goldImage1 = cursor.isNull(i24) ? null : cursor.getString(i24);
        int i25 = i + 30;
        msgbox.goldImage2 = cursor.isNull(i25) ? null : cursor.getString(i25);
        int i26 = i + 31;
        msgbox.extData_gj_name_array = cursor.isNull(i26) ? null : cursor.getString(i26);
        int i27 = i + 32;
        msgbox.extData_gj_type = cursor.isNull(i27) ? null : cursor.getString(i27);
        msgbox.isNewComing = cursor.getShort(i + 33) != 0;
        msgbox.hasShown = cursor.getShort(i + 34) != 0;
        int i28 = i + 35;
        msgbox.ope = cursor.isNull(i28) ? null : cursor.getString(i28);
        int i29 = i + 36;
        msgbox.type = cursor.isNull(i29) ? null : cursor.getString(i29);
        msgbox.tag = cursor.getInt(i + 37);
        int i30 = i + 38;
        msgbox.adcode = cursor.isNull(i30) ? null : cursor.getString(i30);
        int i31 = i + 39;
        msgbox.shortNameCity = cursor.isNull(i31) ? null : cursor.getString(i31);
        msgbox.source = cursor.getInt(i + 40);
        msgbox.shouldFormat = cursor.getShort(i + 41) != 0;
        msgbox.bannerUpdated = cursor.getShort(i + 42) != 0;
        msgbox.page = cursor.getInt(i + 43);
        msgbox.location = cursor.getInt(i + 44);
        int i32 = i + 45;
        msgbox.imgUrl = cursor.isNull(i32) ? null : cursor.getString(i32);
        int i33 = i + 46;
        msgbox.actions = cursor.isNull(i33) ? null : cursor.getString(i33);
        msgbox.hasSub = cursor.getShort(i + 47) != 0;
        int i34 = i + 48;
        msgbox.subImgUrl = cursor.isNull(i34) ? null : cursor.getString(i34);
        int i35 = i + 49;
        msgbox.subTitle = cursor.isNull(i35) ? null : cursor.getString(i35);
        msgbox.sub_page = cursor.getInt(i + 50);
        msgbox.sub_location = cursor.getInt(i + 51);
        msgbox.sub_unread = cursor.getShort(i + 52) != 0;
        msgbox.msgType = cursor.getInt(i + 53);
        msgbox.mesDisplay = cursor.getShort(i + 54) != 0;
        if (cursor.getShort(i + 55) == 0) {
            z = false;
        }
        msgbox.boxDisplay = z;
        msgbox.showType = cursor.getInt(i + 56);
        int i36 = i + 57;
        msgbox.labelColor = cursor.isNull(i36) ? null : cursor.getString(i36);
        msgbox.updateTime = cursor.getLong(i + 58);
        int i37 = i + 59;
        msgbox.impression = cursor.isNull(i37) ? null : cursor.getString(i37);
        int i38 = i + 60;
        if (!cursor.isNull(i38)) {
            str = cursor.getString(i38);
        }
        msgbox.msgImgUriV2 = str;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((Msgbox) obj).id;
    }

    public MsgboxDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"MSGBOX\" (\"ID\" TEXT PRIMARY KEY NOT NULL ,\"VERSION\" TEXT,\"JSON\" TEXT,\"CATEGORY\" TEXT DEFAULT 1 ,\"CREATED_TIME\" INTEGER NOT NULL DEFAULT 0 ,\"EXPIRE_AT\" INTEGER NOT NULL DEFAULT 0 ,\"FEATURES\" TEXT,\"ACTION_URI\" TEXT,\"CREATOR\" TEXT,\"TRACK_ID\" TEXT,\"EXTRA\" TEXT,\"TITLE\" TEXT,\"DESC_MESSAGE\" TEXT,\"PRIORITY\" INTEGER NOT NULL DEFAULT 501 ,\"IS_UN_READ\" INTEGER NOT NULL DEFAULT true ,\"SHOW_ON_MAP\" INTEGER NOT NULL DEFAULT true ,\"RESIDE\" TEXT DEFAULT 1 ,\"BARICON\" TEXT,\"MSG_IMG_URI\" TEXT,\"LABEL\" TEXT,\"COUNTDOWN_ENDTIME\" TEXT,\"IS_ENABLE\" TEXT,\"PARENT_ID\" TEXT,\"WORD_STATUS\" TEXT,\"SHOW_BODY\" TEXT,\"NICK_NAME\" TEXT,\"PUSH_MSG_ID\" TEXT,\"GOLD_NUM\" INTEGER NOT NULL DEFAULT -1 ,\"TOTAL_GOLD_NUM\" INTEGER NOT NULL DEFAULT -1 ,\"GOLD_IMAGE1\" TEXT,\"GOLD_IMAGE2\" TEXT,\"EXT_DATA_GJ_NAME_ARRAY\" TEXT,\"EXT_DATA_GJ_TYPE\" TEXT,\"IS_NEW_COMING\" INTEGER NOT NULL DEFAULT true ,\"HAS_SHOWN\" INTEGER NOT NULL DEFAULT false ,\"OPE\" TEXT,\"TYPE\" TEXT,\"TAG\" INTEGER NOT NULL DEFAULT -1 ,\"ADCODE\" TEXT,\"SHORT_NAME_CITY\" TEXT,\"SOURCE\" INTEGER NOT NULL DEFAULT -1 ,\"SHOULD_FORMAT\" INTEGER NOT NULL DEFAULT false ,\"BANNER_UPDATED\" INTEGER NOT NULL DEFAULT true ,\"PAGE\" INTEGER NOT NULL DEFAULT -1 ,\"LOCATION\" INTEGER NOT NULL DEFAULT -1 ,\"IMG_URL\" TEXT,\"ACTIONS\" TEXT,\"HAS_SUB\" INTEGER NOT NULL DEFAULT false ,\"SUB_IMG_URL\" TEXT,\"SUB_TITLE\" TEXT,\"SUB_PAGE\" INTEGER NOT NULL DEFAULT -1 ,\"SUB_LOCATION\" INTEGER NOT NULL DEFAULT -1 ,\"SUB_UNREAD\" INTEGER NOT NULL DEFAULT true ,\"MSG_TYPE\" INTEGER NOT NULL DEFAULT 0 ,\"MES_DISPLAY\" INTEGER NOT NULL DEFAULT false ,\"BOX_DISPLAY\" INTEGER NOT NULL DEFAULT false ,\"SHOW_TYPE\" INTEGER NOT NULL DEFAULT 0,\"LABEL_COLOR\" TEXT,\"UPDATE_TIME\" INTEGER NOT NULL DEFAULT 0,\"IMPRESSION\" TEXT,\"MSG_IMG_URI_V2\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"MSGBOX\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        Msgbox msgbox = new Msgbox();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            msgbox.id = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            msgbox.version = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            msgbox.json = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            msgbox.category = cursor.getString(i5);
        }
        msgbox.createdTime = cursor.getLong(i + 4);
        msgbox.expireAt = cursor.getLong(i + 5);
        int i6 = i + 6;
        if (!cursor.isNull(i6)) {
            msgbox.features = cursor.getString(i6);
        }
        int i7 = i + 7;
        if (!cursor.isNull(i7)) {
            msgbox.actionUri = cursor.getString(i7);
        }
        int i8 = i + 8;
        if (!cursor.isNull(i8)) {
            msgbox.creator = cursor.getString(i8);
        }
        int i9 = i + 9;
        if (!cursor.isNull(i9)) {
            msgbox.trackId = cursor.getString(i9);
        }
        int i10 = i + 10;
        if (!cursor.isNull(i10)) {
            msgbox.extra = cursor.getString(i10);
        }
        int i11 = i + 11;
        if (!cursor.isNull(i11)) {
            msgbox.title = cursor.getString(i11);
        }
        int i12 = i + 12;
        if (!cursor.isNull(i12)) {
            msgbox.descMessage = cursor.getString(i12);
        }
        msgbox.priority = cursor.getInt(i + 13);
        boolean z = true;
        msgbox.isUnRead = cursor.getShort(i + 14) != 0;
        msgbox.showOnMap = cursor.getShort(i + 15) != 0;
        int i13 = i + 16;
        if (!cursor.isNull(i13)) {
            msgbox.reside = cursor.getString(i13);
        }
        int i14 = i + 17;
        if (!cursor.isNull(i14)) {
            msgbox.baricon = cursor.getString(i14);
        }
        int i15 = i + 18;
        if (!cursor.isNull(i15)) {
            msgbox.msgImgUri = cursor.getString(i15);
        }
        int i16 = i + 19;
        if (!cursor.isNull(i16)) {
            msgbox.label = cursor.getString(i16);
        }
        int i17 = i + 20;
        if (!cursor.isNull(i17)) {
            msgbox.countdownEndtime = cursor.getString(i17);
        }
        int i18 = i + 21;
        if (!cursor.isNull(i18)) {
            msgbox.isEnable = cursor.getString(i18);
        }
        int i19 = i + 22;
        if (!cursor.isNull(i19)) {
            msgbox.parentId = cursor.getString(i19);
        }
        int i20 = i + 23;
        if (!cursor.isNull(i20)) {
            msgbox.wordStatus = cursor.getString(i20);
        }
        int i21 = i + 24;
        if (!cursor.isNull(i21)) {
            msgbox.showBody = cursor.getString(i21);
        }
        int i22 = i + 25;
        if (!cursor.isNull(i22)) {
            msgbox.nickName = cursor.getString(i22);
        }
        int i23 = i + 26;
        if (!cursor.isNull(i23)) {
            msgbox.pushMsgId = cursor.getString(i23);
        }
        msgbox.goldNum = cursor.getInt(i + 27);
        msgbox.totalGoldNum = cursor.getInt(i + 28);
        int i24 = i + 29;
        if (!cursor.isNull(i24)) {
            msgbox.goldImage1 = cursor.getString(i24);
        }
        int i25 = i + 30;
        if (!cursor.isNull(i25)) {
            msgbox.goldImage2 = cursor.getString(i25);
        }
        int i26 = i + 31;
        if (!cursor.isNull(i26)) {
            msgbox.extData_gj_name_array = cursor.getString(i26);
        }
        int i27 = i + 32;
        if (!cursor.isNull(i27)) {
            msgbox.extData_gj_type = cursor.getString(i27);
        }
        msgbox.isNewComing = cursor.getShort(i + 33) != 0;
        msgbox.hasShown = cursor.getShort(i + 34) != 0;
        int i28 = i + 35;
        if (!cursor.isNull(i28)) {
            msgbox.ope = cursor.getString(i28);
        }
        int i29 = i + 36;
        if (!cursor.isNull(i29)) {
            msgbox.type = cursor.getString(i29);
        }
        msgbox.tag = cursor.getInt(i + 37);
        int i30 = i + 38;
        if (!cursor.isNull(i30)) {
            msgbox.adcode = cursor.getString(i30);
        }
        int i31 = i + 39;
        if (!cursor.isNull(i31)) {
            msgbox.shortNameCity = cursor.getString(i31);
        }
        msgbox.source = cursor.getInt(i + 40);
        msgbox.shouldFormat = cursor.getShort(i + 41) != 0;
        msgbox.bannerUpdated = cursor.getShort(i + 42) != 0;
        msgbox.page = cursor.getInt(i + 43);
        msgbox.location = cursor.getInt(i + 44);
        int i32 = i + 45;
        if (!cursor.isNull(i32)) {
            msgbox.imgUrl = cursor.getString(i32);
        }
        int i33 = i + 46;
        if (!cursor.isNull(i33)) {
            msgbox.actions = cursor.getString(i33);
        }
        msgbox.hasSub = cursor.getShort(i + 47) != 0;
        int i34 = i + 48;
        if (!cursor.isNull(i34)) {
            msgbox.subImgUrl = cursor.getString(i34);
        }
        int i35 = i + 49;
        if (!cursor.isNull(i35)) {
            msgbox.subTitle = cursor.getString(i35);
        }
        msgbox.sub_page = cursor.getInt(i + 50);
        msgbox.sub_location = cursor.getInt(i + 51);
        msgbox.sub_unread = cursor.getShort(i + 52) != 0;
        msgbox.msgType = cursor.getInt(i + 53);
        msgbox.mesDisplay = cursor.getShort(i + 54) != 0;
        if (cursor.getShort(i + 55) == 0) {
            z = false;
        }
        msgbox.boxDisplay = z;
        msgbox.showType = cursor.getInt(i + 56);
        int i36 = i + 57;
        if (!cursor.isNull(i36)) {
            msgbox.labelColor = cursor.getString(i36);
        }
        msgbox.updateTime = cursor.getLong(i + 58);
        int i37 = i + 59;
        if (!cursor.isNull(i37)) {
            msgbox.impression = cursor.getString(i37);
        }
        int i38 = i + 60;
        if (!cursor.isNull(i38)) {
            msgbox.msgImgUriV2 = cursor.getString(i38);
        }
        return msgbox;
    }
}
