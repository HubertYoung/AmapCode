package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alipay.mobile.beehive.video.views.OriVideoPreviewCon;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.misc.c;
import com.xiaomi.channel.commonutils.misc.h;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.push.service.an;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.g;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class aq {
    public static void a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        long j = sharedPreferences.getLong("last_sync_info", -1);
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long a = (long) an.a(context).a(g.SyncInfoFrequency.a(), 1209600);
        if (j == -1) {
            sharedPreferences.edit().putLong("last_sync_info", currentTimeMillis).commit();
            return;
        }
        if (Math.abs(currentTimeMillis - j) > a) {
            a(context, true);
            sharedPreferences.edit().putLong("last_sync_info", currentTimeMillis).commit();
        }
    }

    public static void a(Context context, ai aiVar) {
        StringBuilder sb = new StringBuilder("need to update local info with: ");
        sb.append(aiVar.i());
        b.a(sb.toString());
        String str = aiVar.i().get(Constants.EXTRA_KEY_ACCEPT_TIME);
        if (str != null) {
            MiPushClient.removeAcceptTime(context);
            String[] split = str.split("-");
            if (split.length == 2) {
                MiPushClient.addAcceptTime(context, split[0], split[1]);
                if (!OriVideoPreviewCon.ZERO_DURATION.equals(split[0]) || !OriVideoPreviewCon.ZERO_DURATION.equals(split[1])) {
                    c.a(context).a(false);
                } else {
                    c.a(context).a(true);
                }
            }
        }
        String str2 = aiVar.i().get(Constants.EXTRA_KEY_ALIASES);
        if (str2 != null) {
            MiPushClient.removeAllAliases(context);
            if (!"".equals(str2)) {
                for (String addAlias : str2.split(",")) {
                    MiPushClient.addAlias(context, addAlias);
                }
            }
        }
        String str3 = aiVar.i().get(Constants.EXTRA_KEY_TOPICS);
        if (str3 != null) {
            MiPushClient.removeAllTopics(context);
            if (!"".equals(str3)) {
                for (String addTopic : str3.split(",")) {
                    MiPushClient.addTopic(context, addTopic);
                }
            }
        }
        String str4 = aiVar.i().get(Constants.EXTRA_KEY_ACCOUNTS);
        if (str4 != null) {
            MiPushClient.removeAllAccounts(context);
            if (!"".equals(str4)) {
                for (String addAccount : str4.split(",")) {
                    MiPushClient.addAccount(context, addAccount);
                }
            }
        }
    }

    public static void a(Context context, boolean z) {
        h.a(context).a((Runnable) new ar(context, z));
    }

    /* access modifiers changed from: private */
    public static String c(List<String> list) {
        String a = d.a(d(list));
        return (TextUtils.isEmpty(a) || a.length() <= 4) ? "" : a.substring(0, 4).toLowerCase();
    }

    /* access modifiers changed from: private */
    public static String d(List<String> list) {
        if (c.a(list)) {
            return "";
        }
        ArrayList<String> arrayList = new ArrayList<>(list);
        Collections.sort(arrayList, Collator.getInstance(Locale.CHINA));
        String str = "";
        for (String str2 : arrayList) {
            if (!TextUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(",");
                str = sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(str2);
            str = sb2.toString();
        }
        return str;
    }
}
