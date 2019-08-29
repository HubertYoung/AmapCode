package com.huawei.android.pushselfshow.utils.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.huawei.android.pushselfshow.richpush.a.b;
import com.huawei.android.pushselfshow.richpush.favorites.e;
import com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider.a;
import java.util.ArrayList;

public class c {
    public static ArrayList a(Context context, String str) {
        String str2;
        String[] strArr;
        Cursor cursor;
        ArrayList arrayList = new ArrayList();
        if (str == null) {
            str2 = "SELECT pushmsg._id,pushmsg.msg,pushmsg.token,pushmsg.url,notify.bmp  FROM pushmsg LEFT OUTER JOIN notify ON pushmsg.url = notify.url order by pushmsg._id desc limit 1000;";
            strArr = null;
        } else {
            strArr = new String[]{str};
            str2 = "SELECT pushmsg._id,pushmsg.msg,pushmsg.token,pushmsg.url,notify.bmp  FROM pushmsg LEFT OUTER JOIN notify ON pushmsg.url = notify.url and pushmsg.url = ? order by pushmsg._id desc";
        }
        try {
            cursor = b.a().a(context, a.f, str2, strArr);
        } catch (Exception e) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", e.toString(), e);
            cursor = null;
        }
        if (cursor == null) {
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "cursor is null.");
            return arrayList;
        }
        while (cursor.moveToNext()) {
            try {
                int i = cursor.getInt(0);
                byte[] blob = cursor.getBlob(1);
                if (blob == null) {
                    com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "msg is null");
                } else {
                    com.huawei.android.pushselfshow.b.a aVar = new com.huawei.android.pushselfshow.b.a(blob, Token.SEPARATOR.getBytes("UTF-8"));
                    if (!aVar.b()) {
                        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "parseMessage failed");
                    }
                    String string = cursor.getString(3);
                    e eVar = new e();
                    eVar.a(i);
                    eVar.a(string);
                    eVar.a(aVar);
                    arrayList.add(eVar);
                }
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder("query favo error ");
                sb.append(e2.toString());
                com.huawei.android.pushagent.a.a.c.c(RPCDataItems.SWITCH_TAG_LOG, sb.toString(), e2);
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        }
        cursor.close();
        StringBuilder sb2 = new StringBuilder("query favo size is ");
        sb2.append(arrayList.size());
        com.huawei.android.pushagent.a.a.c.e("PushSelfShowLog", sb2.toString());
        return arrayList;
    }

    public static void a(Context context, int i) {
        try {
            b.a().a(context, a.g, "pushmsg", "_id = ?", new String[]{String.valueOf(i)});
        } catch (Exception e) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    public static boolean a(Context context, String str, com.huawei.android.pushselfshow.b.a aVar) {
        if (context == null || str == null || aVar == null) {
            com.huawei.android.pushagent.a.a.c.e("PushSelfShowLog", "insertPushMsginfo ilegle param");
            return false;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("url", str);
            contentValues.put("msg", aVar.c());
            contentValues.put("token", Token.SEPARATOR.getBytes("UTF-8"));
            com.huawei.android.pushagent.a.a.c.a((String) "PushSelfShowLog", (String) "insertPushMsginfo select url is %s ,rpl is %s", str, aVar.D);
            ArrayList a = a(context, str);
            String str2 = aVar.D;
            int i = 0;
            while (i < a.size()) {
                if (((e) a.get(i)).b() == null || !str2.equals(((e) a.get(i)).b().D)) {
                    i++;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(" already exist");
                    com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb.toString());
                    return true;
                }
            }
            StringBuilder sb2 = new StringBuilder("insertPushMsginfo ");
            sb2.append(contentValues.toString());
            com.huawei.android.pushagent.a.a.c.e("PushSelfShowLog", sb2.toString());
            b.a().a(context, a.e, (String) "pushmsg", contentValues);
            return true;
        } catch (Exception e) {
            com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "insertBmpinfo error", e);
            return false;
        }
    }
}
