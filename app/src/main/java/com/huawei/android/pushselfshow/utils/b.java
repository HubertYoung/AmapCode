package com.huawei.android.pushselfshow.utils;

import android.content.Context;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.huawei.android.pushagent.a.a.c;
import java.lang.reflect.InvocationTargetException;

final class b implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;

    b(Context context, String str, String str2, String str3) {
        this.a = context;
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    public final void run() {
        String str;
        String str2;
        try {
            if (!a.m(this.a)) {
                c.a("PushSelfShowLog", "not allowed to sendHiAnalytics!");
                return;
            }
            StringBuffer stringBuffer = new StringBuffer(String.valueOf(a.a()));
            stringBuffer.append("|PS|");
            stringBuffer.append(a.b(this.a));
            stringBuffer.append(MergeUtil.SEPARATOR_KV);
            stringBuffer.append(this.b);
            stringBuffer.append(MergeUtil.SEPARATOR_KV);
            stringBuffer.append(this.c);
            stringBuffer.append(MergeUtil.SEPARATOR_KV);
            stringBuffer.append(a.a(this.a));
            stringBuffer.append(MergeUtil.SEPARATOR_KV);
            stringBuffer.append(this.d);
            String stringBuffer2 = stringBuffer.toString();
            if (this.a != null) {
                c.b("PushSelfShowLog", "run normal sendHiAnalytics");
                Class<?> cls = Class.forName("com.hianalytics.android.v1.HiAnalytics");
                cls.getMethod("onEvent", new Class[]{Context.class, String.class, String.class}).invoke(cls, new Object[]{this.a, "PUSH_PS", stringBuffer2});
                cls.getMethod("onReport", new Class[]{Context.class}).invoke(cls, new Object[]{this.a});
                StringBuilder sb = new StringBuilder("send HiAnalytics msg, report cmd =");
                sb.append(this.d);
                sb.append(", msgid = ");
                sb.append(this.b);
                sb.append(", eventId = ");
                sb.append(this.c);
                c.a("PushSelfShowLog", sb.toString());
                return;
            }
            StringBuilder sb2 = new StringBuilder("send HiAnalytics msg, report cmd =");
            sb2.append(this.d);
            sb2.append(",context = ");
            sb2.append(this.a);
            c.a("PushSelfShowLog", sb2.toString());
        } catch (IllegalAccessException e) {
            e = e;
            str = "PushSelfShowLog";
            str2 = "sendHiAnalytics IllegalAccessException ";
            c.d(str, str2, e);
        } catch (IllegalArgumentException e2) {
            e = e2;
            str = "PushSelfShowLog";
            str2 = "sendHiAnalytics IllegalArgumentException ";
            c.d(str, str2, e);
        } catch (InvocationTargetException e3) {
            e = e3;
            str = "PushSelfShowLog";
            str2 = "sendHiAnalytics InvocationTargetException";
            c.d(str, str2, e);
        } catch (NoSuchMethodException e4) {
            e = e4;
            str = "PushSelfShowLog";
            str2 = "sendHiAnalytics NoSuchMethodException";
            c.d(str, str2, e);
        } catch (ClassNotFoundException e5) {
            e = e5;
            str = "PushSelfShowLog";
            str2 = "sendHiAnalytics ClassNotFoundException";
            c.d(str, str2, e);
        }
    }
}
