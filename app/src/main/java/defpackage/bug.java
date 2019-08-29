package defpackage;

import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.msgbox.db.MessageCategoryDao;
import com.autonavi.map.msgbox.db.MsgboxDao;

@MultipleImpl(xy.class)
/* renamed from: bug reason: default package */
/* compiled from: MsgBoxDbOpenHelper */
public class bug implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        AMapLog.e("MsgBoxDbOpenHelper", sb.toString());
        if (i <= 23) {
            MessageCategoryDao.a(sQLiteDatabase);
        }
        boolean z = true;
        if (i <= 22) {
            MsgboxDao.b(sQLiteDatabase);
            MsgboxDao.a(sQLiteDatabase);
            MessageCategoryDao.b(sQLiteDatabase);
            MessageCategoryDao.a(sQLiteDatabase);
        } else if (i <= 23) {
            z = a(sQLiteDatabase);
        } else if (i <= 34) {
            z = b(sQLiteDatabase);
        } else if (i <= 35) {
            z = c(sQLiteDatabase);
        } else if (i <= 40) {
            z = d(sQLiteDatabase);
        } else if (i != 51 && i <= 56) {
            z = d(sQLiteDatabase);
        }
        if (!z) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS MSGBOX_temp");
            MsgboxDao.b(sQLiteDatabase);
            MsgboxDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS MESSAGE_CATEGORY_temp");
            MessageCategoryDao.b(sQLiteDatabase);
            MessageCategoryDao.a(sQLiteDatabase);
        }
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase) {
        try {
            MessageCategoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE MESSAGE_CATEGORY RENAME TO MESSAGE_CATEGORY_temp");
            MessageCategoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into MESSAGE_CATEGORY(ID, NAME, PATTERN)select ID, NAME, PATTERN from MESSAGE_CATEGORY_temp");
            sQLiteDatabase.execSQL("DROP TABLE MESSAGE_CATEGORY_temp");
            sQLiteDatabase.execSQL("ALTER TABLE MSGBOX RENAME TO MSGBOX_temp");
            MsgboxDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into MSGBOX(ID, VERSION, JSON, CREATED_TIME, EXPIRE_AT, FEATURES, ACTION_URI, CREATOR, TRACK_ID, EXTRA, TITLE, DESC_MESSAGE, PRIORITY, IS_UN_READ, SHOW_ON_MAP, RESIDE, BARICON, MSG_IMG_URI, MSG_IMG_URI_V2,LABEL, COUNTDOWN_ENDTIME, IS_ENABLE, PARENT_ID, WORD_STATUS, SHOW_BODY, NICK_NAME, PUSH_MSG_ID,GOLD_NUM, TOTAL_GOLD_NUM, GOLD_IMAGE1, GOLD_IMAGE2, EXT_DATA_GJ_NAME_ARRAY, EXT_DATA_GJ_TYPE, IS_NEW_COMING, HAS_SHOWN, OPE, TYPE, TAG, ADCODE, SHORT_NAME_CITY, SOURCE, SHOULD_FORMAT, BANNER_UPDATED, PAGE, LOCATION, IMG_URL, ACTIONS, HAS_SUB, SUB_IMG_URL, SUB_TITLE, SUB_PAGE, SUB_LOCATION, SUB_UNREAD) select CATEGORY, VERSION, JSON, CREATED_TIME, EXPIRE_AT, FEATURES, ACTION_URI, CREATOR, TRACK_ID, EXTRA, TITLE, DESC_MESSAGE, PRIORITY, IS_UN_READ, SHOW_ON_MAP, RESIDE, BARICON, MSG_IMG_URI, MSG_IMG_URI_V2,LABEL, COUNTDOWN_ENDTIME, IS_ENABLE, PARENT_ID, WORD_STATUS, SHOW_BODY, NICK_NAME, PUSH_MSG_ID,GOLD_NUM, TOTAL_GOLD_NUM, GOLD_IMAGE1, GOLD_IMAGE2, EXT_DATA_GJ_NAME_ARRAY, EXT_DATA_GJ_TYPE, NEW_COMING_INDICATOR_1, 1, OPE, TYPE, TAG, ADCODE, SHORT_NAME_CITY, SOURCE, SHOULD_FORMAT, BANNER_UPDATED, PAGE, LOCATION, IMG_URL, ACTIONS, HAS_SUB, SUB_IMG_URL, SUB_TITLE, SUB_PAGE, SUB_LOCATION, SUB_UNREAD from MSGBOX_temp");
            sQLiteDatabase.execSQL("DROP TABLE MSGBOX_temp");
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean b(SQLiteDatabase sQLiteDatabase) {
        try {
            MessageCategoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE MESSAGE_CATEGORY RENAME TO MESSAGE_CATEGORY_temp");
            MessageCategoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into MESSAGE_CATEGORY(ID, NAME, PATTERN)select ID, NAME, PATTERN from MESSAGE_CATEGORY_temp");
            sQLiteDatabase.execSQL("DROP TABLE MESSAGE_CATEGORY_temp");
            sQLiteDatabase.execSQL("ALTER TABLE MSGBOX RENAME TO MSGBOX_temp");
            MsgboxDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into MSGBOX(ID, VERSION, JSON, CATEGORY, CREATED_TIME, EXPIRE_AT, FEATURES, ACTION_URI, CREATOR, TRACK_ID, EXTRA, TITLE, DESC_MESSAGE, PRIORITY, IS_UN_READ, SHOW_ON_MAP, RESIDE, BARICON, MSG_IMG_URI, MSG_IMG_URI_V2,LABEL, COUNTDOWN_ENDTIME, IS_ENABLE, PARENT_ID, WORD_STATUS, SHOW_BODY, NICK_NAME, PUSH_MSG_ID,GOLD_NUM, TOTAL_GOLD_NUM, GOLD_IMAGE1, GOLD_IMAGE2, EXT_DATA_GJ_NAME_ARRAY, EXT_DATA_GJ_TYPE, IS_NEW_COMING, HAS_SHOWN, OPE, TYPE, TAG, ADCODE, SHORT_NAME_CITY, SOURCE, SHOULD_FORMAT, BANNER_UPDATED, PAGE, LOCATION, IMG_URL, ACTIONS, HAS_SUB, SUB_IMG_URL, SUB_TITLE, SUB_PAGE, SUB_LOCATION, SUB_UNREAD) select ID, VERSION, JSON, CATEGORY,CREATED_TIME, EXPIRE_AT, FEATURES, ACTION_URI, CREATOR, TRACK_ID, EXTRA, TITLE, DESC_MESSAGE, PRIORITY, IS_UN_READ, SHOW_ON_MAP, RESIDE, BARICON, MSG_IMG_URI, MSG_IMG_URI_V2,LABEL, COUNTDOWN_ENDTIME, IS_ENABLE, PARENT_ID, WORD_STATUS, SHOW_BODY, NICK_NAME, PUSH_MSG_ID,GOLD_NUM, TOTAL_GOLD_NUM, GOLD_IMAGE1, GOLD_IMAGE2, EXT_DATA_GJ_NAME_ARRAY, EXT_DATA_GJ_TYPE, IS_NEW_COMING, HAS_SHOWN, OPE, TYPE, TAG, ADCODE, SHORT_NAME_CITY, SOURCE, SHOULD_FORMAT, BANNER_UPDATED, PAGE, LOCATION, IMG_URL, ACTIONS, HAS_SUB, SUB_IMG_URL, SUB_TITLE, SUB_PAGE, SUB_LOCATION, SUB_UNREAD from MSGBOX_temp");
            sQLiteDatabase.execSQL("DROP TABLE MSGBOX_temp");
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean c(SQLiteDatabase sQLiteDatabase) {
        try {
            MessageCategoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE MESSAGE_CATEGORY RENAME TO MESSAGE_CATEGORY_temp");
            MessageCategoryDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into MESSAGE_CATEGORY(ID, NAME, PATTERN)select ID, NAME, PATTERN from MESSAGE_CATEGORY_temp");
            sQLiteDatabase.execSQL("DROP TABLE MESSAGE_CATEGORY_temp");
            sQLiteDatabase.execSQL("ALTER TABLE MSGBOX RENAME TO MSGBOX_temp");
            MsgboxDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into MSGBOX(ID, VERSION, JSON, CATEGORY, CREATED_TIME, EXPIRE_AT, FEATURES, ACTION_URI, CREATOR, TRACK_ID, EXTRA, TITLE, DESC_MESSAGE, PRIORITY, IS_UN_READ, SHOW_ON_MAP, RESIDE, BARICON, MSG_IMG_URI,MSG_IMG_URI_V2, LABEL, COUNTDOWN_ENDTIME, IS_ENABLE, PARENT_ID, WORD_STATUS, SHOW_BODY, NICK_NAME, PUSH_MSG_ID,GOLD_NUM, TOTAL_GOLD_NUM, GOLD_IMAGE1, GOLD_IMAGE2, EXT_DATA_GJ_NAME_ARRAY, EXT_DATA_GJ_TYPE, IS_NEW_COMING, HAS_SHOWN, OPE, TYPE, TAG, ADCODE, SHORT_NAME_CITY, SOURCE, SHOULD_FORMAT, BANNER_UPDATED, PAGE, LOCATION, IMG_URL, ACTIONS, HAS_SUB, SUB_IMG_URL, SUB_TITLE, SUB_PAGE, SUB_LOCATION, SUB_UNREAD, MSG_TYPE, MES_DISPLAY, BOX_DISPLAY) select ID, VERSION, JSON, CATEGORY, CREATED_TIME, EXPIRE_AT, FEATURES, ACTION_URI, CREATOR, TRACK_ID, EXTRA, TITLE, DESC_MESSAGE, PRIORITY, IS_UN_READ, SHOW_ON_MAP, RESIDE, BARICON, MSG_IMG_URI, MSG_IMG_URI_V2,LABEL, COUNTDOWN_ENDTIME, IS_ENABLE, PARENT_ID, WORD_STATUS, SHOW_BODY, NICK_NAME, PUSH_MSG_ID,GOLD_NUM, TOTAL_GOLD_NUM, GOLD_IMAGE1, GOLD_IMAGE2, EXT_DATA_GJ_NAME_ARRAY, EXT_DATA_GJ_TYPE, IS_NEW_COMING, HAS_SHOWN, OPE, TYPE, TAG, ADCODE, SHORT_NAME_CITY, SOURCE, SHOULD_FORMAT, BANNER_UPDATED, PAGE, LOCATION, IMG_URL, ACTIONS, HAS_SUB, SUB_IMG_URL, SUB_TITLE, SUB_PAGE, SUB_LOCATION, SUB_UNREAD, MSG_TYPE, MES_DISPLAY, BOX_DISPLAY from MSGBOX_temp");
            sQLiteDatabase.execSQL("DROP TABLE MSGBOX_temp");
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean d(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("ALTER TABLE MSGBOX RENAME TO MSGBOX_temp");
            MsgboxDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into MSGBOX(ID, VERSION, JSON, CATEGORY, CREATED_TIME, EXPIRE_AT, FEATURES, ACTION_URI, CREATOR, TRACK_ID, EXTRA, TITLE, DESC_MESSAGE, PRIORITY, IS_UN_READ, SHOW_ON_MAP, RESIDE, BARICON, MSG_IMG_URI, LABEL, COUNTDOWN_ENDTIME, IS_ENABLE, PARENT_ID, WORD_STATUS, SHOW_BODY, NICK_NAME, PUSH_MSG_ID,GOLD_NUM, TOTAL_GOLD_NUM, GOLD_IMAGE1, GOLD_IMAGE2, EXT_DATA_GJ_NAME_ARRAY, EXT_DATA_GJ_TYPE, IS_NEW_COMING, HAS_SHOWN, OPE, TYPE, TAG, ADCODE, SHORT_NAME_CITY, SOURCE, SHOULD_FORMAT, BANNER_UPDATED, PAGE, LOCATION, IMG_URL, ACTIONS, HAS_SUB, SUB_IMG_URL, SUB_TITLE, SUB_PAGE, SUB_LOCATION, SUB_UNREAD, MSG_TYPE, MES_DISPLAY, BOX_DISPLAY, SHOW_TYPE, LABEL_COLOR, UPDATE_TIME) select ID, VERSION, JSON, CATEGORY, CREATED_TIME, EXPIRE_AT, FEATURES, ACTION_URI, CREATOR, TRACK_ID, EXTRA, TITLE, DESC_MESSAGE, PRIORITY, IS_UN_READ, SHOW_ON_MAP, RESIDE, BARICON, MSG_IMG_URI, LABEL, COUNTDOWN_ENDTIME, IS_ENABLE, PARENT_ID, WORD_STATUS, SHOW_BODY, NICK_NAME, PUSH_MSG_ID,GOLD_NUM, TOTAL_GOLD_NUM, GOLD_IMAGE1, GOLD_IMAGE2, EXT_DATA_GJ_NAME_ARRAY, EXT_DATA_GJ_TYPE, IS_NEW_COMING, HAS_SHOWN, OPE, TYPE, TAG, ADCODE, SHORT_NAME_CITY, SOURCE, SHOULD_FORMAT, BANNER_UPDATED, PAGE, LOCATION, IMG_URL, ACTIONS, HAS_SUB, SUB_IMG_URL, SUB_TITLE, SUB_PAGE, SUB_LOCATION, SUB_UNREAD, MSG_TYPE, MES_DISPLAY, BOX_DISPLAY, SHOW_TYPE, LABEL_COLOR, UPDATE_TIME from MSGBOX_temp");
            sQLiteDatabase.execSQL("DROP TABLE MSGBOX_temp");
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}
