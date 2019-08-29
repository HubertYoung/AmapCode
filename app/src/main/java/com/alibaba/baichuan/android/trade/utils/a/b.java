package com.alibaba.baichuan.android.trade.utils.a;

import android.content.Context;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.constants.MessageConstants;
import com.alibaba.baichuan.android.trade.utils.i;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class b {
    private static Map a = new HashMap();
    private static ReentrantReadWriteLock b = new ReentrantReadWriteLock();
    private static final a c;
    private static final a d;
    private static volatile a e;
    private static volatile a f;
    private static final Object g = new Object();

    static {
        a aVar = new a();
        c = aVar;
        aVar.a = 1;
        c.c = "未在消息文件中找到 id 为 {0} 的消息";
        c.d = "请检查所依赖的 SDK 项目，或若是手动拷贝 SDK 至当前开发应用所在项目，请检查是否漏拷文件 res/values 下文件";
        c.b = "E";
        a aVar2 = new a();
        d = aVar2;
        aVar2.a = 2;
        d.c = "检索消息时发生如下错误 {0}";
        d.d = "请检查所依赖的 SDK 项目，或若是手动拷贝 SDK 至当前开发应用所在项目，请检查是否漏拷文件 res/values 下文件";
        d.b = "E";
    }

    private static a a(int i) {
        if (e == null) {
            synchronized (g) {
                if (e == null) {
                    a b2 = b(1);
                    e = b2;
                    if (b2 == null) {
                        e = c;
                    }
                }
            }
        }
        try {
            a aVar = (a) e.clone();
            aVar.c = MessageFormat.format(aVar.c, new Object[]{String.valueOf(i)});
            return aVar;
        } catch (CloneNotSupportedException unused) {
            return e;
        }
    }

    public static a a(int i, Object... objArr) {
        try {
            b.readLock().lock();
            a aVar = (a) a.get(Integer.valueOf(i));
            if (aVar == null) {
                b.readLock().unlock();
                b.writeLock().lock();
                aVar = b(i);
                if (aVar != null) {
                    a.put(Integer.valueOf(i), aVar);
                }
                b.readLock().lock();
                b.writeLock().unlock();
            }
            if (aVar == null) {
                a a2 = a(i);
                b.readLock().unlock();
                return a2;
            } else if (objArr.length == 0) {
                b.readLock().unlock();
                return aVar;
            } else {
                a aVar2 = (a) aVar.clone();
                aVar2.c = MessageFormat.format(aVar2.c, objArr);
                b.readLock().unlock();
                return aVar2;
            }
        } catch (Exception e2) {
            AlibcLogger.e("AlibcMessageUtils", e2.toString());
            return b(e2.getMessage());
        } catch (Throwable th) {
            b.writeLock().unlock();
            throw th;
        }
    }

    public static a a(String str) {
        int i = MessageConstants.PAY_COMMON_ERROR;
        try {
            int parseInt = Integer.parseInt(str);
            if (parseInt == 4000) {
                i = 805;
            } else if (parseInt == 8000) {
                i = 804;
            } else if (parseInt != 10002) {
                switch (parseInt) {
                    case 6001:
                        i = 806;
                        break;
                    case 6002:
                        i = MessageConstants.PAY_NETWORK_FAILED;
                        break;
                }
            } else {
                i = MessageConstants.PAY_SDK_FAILED;
            }
        } catch (Exception unused) {
            AlibcLogger.e("AlibcMessageUtils", "fail to parse the response code ".concat(String.valueOf(str)));
        }
        return a.a(i, str);
    }

    private static a b(int i) {
        try {
            Context context = AlibcContext.context;
            StringBuilder sb = new StringBuilder("alisdk_message_");
            sb.append(i);
            sb.append("_message");
            int a2 = i.a(context, ResUtils.STRING, sb.toString());
            if (a2 == 0) {
                return null;
            }
            a aVar = new a();
            aVar.a = i;
            aVar.c = AlibcContext.context.getResources().getString(a2);
            Context context2 = AlibcContext.context;
            StringBuilder sb2 = new StringBuilder("alisdk_message_");
            sb2.append(i);
            sb2.append("_action");
            int a3 = i.a(context2, ResUtils.STRING, sb2.toString());
            aVar.d = a3 != 0 ? AlibcContext.context.getResources().getString(a3) : "";
            Context context3 = AlibcContext.context;
            StringBuilder sb3 = new StringBuilder("alisdk_message_");
            sb3.append(i);
            sb3.append("_type");
            int a4 = i.a(context3, ResUtils.STRING, sb3.toString());
            aVar.b = a4 != 0 ? AlibcContext.context.getResources().getString(a4) : LogHelper.DEFAULT_LEVEL;
            return aVar;
        } catch (Exception e2) {
            StringBuilder sb4 = new StringBuilder("Fail to get message of the code ");
            sb4.append(i);
            sb4.append(", the error message is ");
            sb4.append(e2.getMessage());
            AlibcLogger.e("kernel", sb4.toString());
            return null;
        }
    }

    private static a b(String str) {
        if (f == null) {
            synchronized (g) {
                if (f == null) {
                    a b2 = b(2);
                    f = b2;
                    if (b2 == null) {
                        f = d;
                    }
                }
            }
        }
        try {
            a aVar = (a) f.clone();
            aVar.c = MessageFormat.format(aVar.c, new Object[]{str});
            return aVar;
        } catch (CloneNotSupportedException unused) {
            return f;
        }
    }
}
