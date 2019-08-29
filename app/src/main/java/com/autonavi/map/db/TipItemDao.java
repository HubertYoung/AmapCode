package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.common.logging.util.avail.ExceptionData;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sync.beans.GirfFavoritePoint;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.Date;

public class TipItemDao extends AbstractDao<TipItem, Long> {
    public static final String TABLENAME = "tipitem";

    public static class Properties {
        public static final Property A;
        public static final Property a;
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
            Property property = new Property(0, Long.class, "id", true, AutoJsonUtils.JSON_ID);
            a = property;
            Property property2 = new Property(1, Integer.TYPE, "type", false, ExceptionData.E_TYPE);
            b = property2;
            Property property3 = new Property(2, Integer.TYPE, "dataType", false, "DATA_TYPE");
            c = property3;
            Property property4 = new Property(3, String.class, "name", false, "NAME");
            d = property4;
            Property property5 = new Property(4, String.class, AutoJsonUtils.JSON_ADCODE, false, "ADCODE");
            e = property5;
            Property property6 = new Property(5, String.class, "district", false, "DISTRICT");
            f = property6;
            Property property7 = new Property(6, String.class, LocationInstrument.LOCATION_EXTRAS_KEY_POIID, false, "POIID");
            g = property7;
            Property property8 = new Property(7, String.class, "addr", false, "ADDR");
            h = property8;
            Property property9 = new Property(8, Double.TYPE, DictionaryKeys.CTRLXY_X, false, "X");
            i = property9;
            Property property10 = new Property(9, Double.TYPE, DictionaryKeys.CTRLXY_Y, false, "Y");
            j = property10;
            Property property11 = new Property(10, String.class, "poiTag", false, "POI_TAG");
            k = property11;
            Property property12 = new Property(11, String.class, "funcText", false, "FUNC_TEXT");
            l = property12;
            Property property13 = new Property(12, String.class, "shortname", false, "SHORTNAME");
            m = property13;
            Property property14 = new Property(13, String.class, "displayInfo", false, "DISPLAY_INFO");
            n = property14;
            Property property15 = new Property(14, Integer.TYPE, "iconinfo", false, "ICONINFO");
            o = property15;
            Property property16 = new Property(15, String.class, "searchQuery", false, "SEARCH_QUERY");
            p = property16;
            Property property17 = new Property(16, String.class, "terminals", false, "TERMINALS");
            q = property17;
            Property property18 = new Property(17, Integer.TYPE, "ignoreDistrict", false, "IGNORE_DISTRICT");
            r = property18;
            Property property19 = new Property(18, String.class, "searchTag", false, "SEARCH_TAG");
            s = property19;
            Property property20 = new Property(19, Date.class, "time", false, SecureSignatureDefine.SG_KEY_SIGN_TIME);
            t = property20;
            Property property21 = new Property(20, Integer.TYPE, "historyType", false, "HISTORY_TYPE");
            u = property21;
            Property property22 = new Property(21, String.class, "richRating", false, "RICH_RATING");
            v = property22;
            Property property23 = new Property(22, String.class, "numReview", false, "NUM_REVIEW");
            w = property23;
            Property property24 = new Property(23, String.class, GirfFavoritePoint.JSON_FIELD_POI_NEW_TYPE, false, "NEW_TYPE");
            x = property24;
            Property property25 = new Property(24, Double.TYPE, "x_entr", false, "X_ENTR");
            y = property25;
            Property property26 = new Property(25, Double.TYPE, "y_entr", false, "Y_ENTR");
            z = property26;
            Property property27 = new Property(26, String.class, "super_address", false, "SUPER_ADDRESS");
            A = property27;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        TipItem tipItem = (TipItem) obj;
        sQLiteStatement.clearBindings();
        Long l = tipItem.id;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        sQLiteStatement.bindLong(2, (long) tipItem.type);
        sQLiteStatement.bindLong(3, (long) tipItem.dataType);
        String str = tipItem.name;
        if (str != null) {
            sQLiteStatement.bindString(4, str);
        }
        String str2 = tipItem.adcode;
        if (str2 != null) {
            sQLiteStatement.bindString(5, str2);
        }
        String str3 = tipItem.district;
        if (str3 != null) {
            sQLiteStatement.bindString(6, str3);
        }
        String str4 = tipItem.poiid;
        if (str4 != null) {
            sQLiteStatement.bindString(7, str4);
        }
        String str5 = tipItem.addr;
        if (str5 != null) {
            sQLiteStatement.bindString(8, str5);
        }
        sQLiteStatement.bindDouble(9, tipItem.x);
        sQLiteStatement.bindDouble(10, tipItem.y);
        String str6 = tipItem.poiTag;
        if (str6 != null) {
            sQLiteStatement.bindString(11, str6);
        }
        String str7 = tipItem.funcText;
        if (str7 != null) {
            sQLiteStatement.bindString(12, str7);
        }
        String str8 = tipItem.shortname;
        if (str8 != null) {
            sQLiteStatement.bindString(13, str8);
        }
        String str9 = tipItem.displayInfo;
        if (str9 != null) {
            sQLiteStatement.bindString(14, str9);
        }
        sQLiteStatement.bindLong(15, (long) tipItem.iconinfo);
        String str10 = tipItem.searchQuery;
        if (str10 != null) {
            sQLiteStatement.bindString(16, str10);
        }
        String str11 = tipItem.terminals;
        if (str11 != null) {
            sQLiteStatement.bindString(17, str11);
        }
        sQLiteStatement.bindLong(18, (long) tipItem.ignoreDistrict);
        String str12 = tipItem.searchTag;
        if (str12 != null) {
            sQLiteStatement.bindString(19, str12);
        }
        sQLiteStatement.bindLong(20, tipItem.time.getTime());
        sQLiteStatement.bindLong(21, (long) tipItem.historyType);
        String str13 = tipItem.richRating;
        if (str13 != null) {
            sQLiteStatement.bindString(22, str13);
        }
        String str14 = tipItem.numReview;
        if (str14 != null) {
            sQLiteStatement.bindString(23, str14);
        }
        String str15 = tipItem.newType;
        if (str15 != null) {
            sQLiteStatement.bindString(24, str15);
        }
        sQLiteStatement.bindDouble(25, tipItem.x_entr);
        sQLiteStatement.bindDouble(26, tipItem.y_entr);
        String str16 = tipItem.super_address;
        if (str16 != null) {
            sQLiteStatement.bindString(27, str16);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        TipItem tipItem = (TipItem) obj;
        if (tipItem != null) {
            return tipItem.id;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        TipItem tipItem = (TipItem) obj;
        int i2 = i + 0;
        String str = null;
        tipItem.id = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        tipItem.type = cursor.getInt(i + 1);
        tipItem.dataType = cursor.getInt(i + 2);
        int i3 = i + 3;
        tipItem.name = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 4;
        tipItem.adcode = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 5;
        tipItem.district = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 6;
        tipItem.poiid = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 7;
        tipItem.addr = cursor.isNull(i7) ? null : cursor.getString(i7);
        tipItem.x = cursor.getDouble(i + 8);
        tipItem.y = cursor.getDouble(i + 9);
        int i8 = i + 10;
        tipItem.poiTag = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 11;
        tipItem.funcText = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 12;
        tipItem.shortname = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 13;
        tipItem.displayInfo = cursor.isNull(i11) ? null : cursor.getString(i11);
        tipItem.iconinfo = cursor.getInt(i + 14);
        int i12 = i + 15;
        tipItem.searchQuery = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = i + 16;
        tipItem.terminals = cursor.isNull(i13) ? null : cursor.getString(i13);
        tipItem.ignoreDistrict = cursor.getInt(i + 17);
        int i14 = i + 18;
        tipItem.searchTag = cursor.isNull(i14) ? null : cursor.getString(i14);
        tipItem.time = new Date(cursor.getLong(i + 19));
        tipItem.historyType = cursor.getInt(i + 20);
        int i15 = i + 21;
        tipItem.richRating = cursor.isNull(i15) ? null : cursor.getString(i15);
        int i16 = i + 22;
        tipItem.numReview = cursor.isNull(i16) ? null : cursor.getString(i16);
        int i17 = i + 23;
        tipItem.newType = cursor.isNull(i17) ? null : cursor.getString(i17);
        tipItem.x_entr = cursor.getDouble(i + 24);
        tipItem.y_entr = cursor.getDouble(i + 25);
        int i18 = i + 26;
        if (!cursor.isNull(i18)) {
            str = cursor.getString(i18);
        }
        tipItem.super_address = str;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((TipItem) obj).id = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public TipItemDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"tipitem\" (\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"TYPE\" INTEGER NOT NULL ,\"DATA_TYPE\" INTEGER NOT NULL ,\"NAME\" TEXT,\"ADCODE\" TEXT,\"DISTRICT\" TEXT,\"POIID\" TEXT,\"ADDR\" TEXT,\"X\" REAL NOT NULL ,\"Y\" REAL NOT NULL ,\"POI_TAG\" TEXT,\"FUNC_TEXT\" TEXT,\"SHORTNAME\" TEXT,\"DISPLAY_INFO\" TEXT,\"ICONINFO\" INTEGER NOT NULL ,\"SEARCH_QUERY\" TEXT,\"TERMINALS\" TEXT,\"IGNORE_DISTRICT\" INTEGER NOT NULL ,\"SEARCH_TAG\" TEXT,\"TIME\" INTEGER NOT NULL ,\"HISTORY_TYPE\" INTEGER NOT NULL ,\"RICH_RATING\" TEXT,\"NUM_REVIEW\" TEXT,\"NEW_TYPE\" TEXT,\"X_ENTR\" REAL NOT NULL DEFAULT 0 ,\"Y_ENTR\" REAL NOT NULL DEFAULT 0 ,\"SUPER_ADDRESS\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"tipitem\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        TipItem tipItem = new TipItem();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            tipItem.id = Long.valueOf(cursor.getLong(i2));
        }
        tipItem.type = cursor.getInt(i + 1);
        tipItem.dataType = cursor.getInt(i + 2);
        int i3 = i + 3;
        if (!cursor.isNull(i3)) {
            tipItem.name = cursor.getString(i3);
        }
        int i4 = i + 4;
        if (!cursor.isNull(i4)) {
            tipItem.adcode = cursor.getString(i4);
        }
        int i5 = i + 5;
        if (!cursor.isNull(i5)) {
            tipItem.district = cursor.getString(i5);
        }
        int i6 = i + 6;
        if (!cursor.isNull(i6)) {
            tipItem.poiid = cursor.getString(i6);
        }
        int i7 = i + 7;
        if (!cursor.isNull(i7)) {
            tipItem.addr = cursor.getString(i7);
        }
        tipItem.x = cursor.getDouble(i + 8);
        tipItem.y = cursor.getDouble(i + 9);
        int i8 = i + 10;
        if (!cursor.isNull(i8)) {
            tipItem.poiTag = cursor.getString(i8);
        }
        int i9 = i + 11;
        if (!cursor.isNull(i9)) {
            tipItem.funcText = cursor.getString(i9);
        }
        int i10 = i + 12;
        if (!cursor.isNull(i10)) {
            tipItem.shortname = cursor.getString(i10);
        }
        int i11 = i + 13;
        if (!cursor.isNull(i11)) {
            tipItem.displayInfo = cursor.getString(i11);
        }
        tipItem.iconinfo = cursor.getInt(i + 14);
        int i12 = i + 15;
        if (!cursor.isNull(i12)) {
            tipItem.searchQuery = cursor.getString(i12);
        }
        int i13 = i + 16;
        if (!cursor.isNull(i13)) {
            tipItem.terminals = cursor.getString(i13);
        }
        tipItem.ignoreDistrict = cursor.getInt(i + 17);
        int i14 = i + 18;
        if (!cursor.isNull(i14)) {
            tipItem.searchTag = cursor.getString(i14);
        }
        tipItem.time = new Date(cursor.getLong(i + 19));
        tipItem.historyType = cursor.getInt(i + 20);
        int i15 = i + 21;
        if (!cursor.isNull(i15)) {
            tipItem.richRating = cursor.getString(i15);
        }
        int i16 = i + 22;
        if (!cursor.isNull(i16)) {
            tipItem.numReview = cursor.getString(i16);
        }
        int i17 = i + 23;
        if (!cursor.isNull(i17)) {
            tipItem.newType = cursor.getString(i17);
        }
        tipItem.x_entr = cursor.getDouble(i + 24);
        tipItem.y_entr = cursor.getDouble(i + 25);
        int i18 = i + 26;
        if (!cursor.isNull(i18)) {
            tipItem.super_address = cursor.getString(i18);
        }
        return tipItem;
    }
}
