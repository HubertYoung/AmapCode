package com.ali.auth.third.core.message;

import android.content.Context;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.ResourceUtils;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MessageUtils {
    private static Map<Integer, Message> a = new HashMap();
    private static ReentrantReadWriteLock b = new ReentrantReadWriteLock();
    private static final Message c;
    private static final Message d;
    private static Message e;
    private static Message f;
    private static final Object g = new Object();

    static {
        Message message = new Message();
        c = message;
        message.code = 1;
        c.message = "未在消息文件中找到 id 为 {0} 的消息";
        c.action = "请检查所依赖的 SDK 项目，或若是手动拷贝 SDK 至当前开发应用所在项目，请检查是否漏拷文件 res/values 下文件";
        c.type = "E";
        Message message2 = new Message();
        d = message2;
        message2.code = 2;
        d.message = "检索消息时发生如下错误 {0}";
        d.action = "请检查所依赖的 SDK 项目，或若是手动拷贝 SDK 至当前开发应用所在项目，请检查是否漏拷文件 res/values 下文件";
        d.type = "E";
    }

    private static Message a(int i) {
        if (e == null) {
            synchronized (g) {
                if (e == null) {
                    Message b2 = b(1);
                    e = b2;
                    if (b2 == null) {
                        e = c;
                    }
                }
            }
        }
        try {
            Message message = (Message) e.clone();
            message.message = MessageFormat.format(message.message, new Object[]{String.valueOf(i)});
            return message;
        } catch (CloneNotSupportedException unused) {
            return e;
        }
    }

    private static Message a(String str) {
        if (f == null) {
            synchronized (g) {
                if (f == null) {
                    Message b2 = b(2);
                    f = b2;
                    if (b2 == null) {
                        f = d;
                    }
                }
            }
        }
        try {
            Message message = (Message) f.clone();
            message.message = MessageFormat.format(message.message, new Object[]{str});
            return message;
        } catch (CloneNotSupportedException unused) {
            return f;
        }
    }

    private static Message b(int i) {
        try {
            Context applicationContext = KernelContext.getApplicationContext();
            StringBuilder sb = new StringBuilder("auth_sdk_message_");
            sb.append(i);
            sb.append("_message");
            int identifier = ResourceUtils.getIdentifier(applicationContext, ResUtils.STRING, sb.toString());
            if (identifier == 0) {
                return null;
            }
            Message message = new Message();
            message.code = i;
            message.message = KernelContext.getApplicationContext().getResources().getString(identifier);
            Context applicationContext2 = KernelContext.getApplicationContext();
            StringBuilder sb2 = new StringBuilder("auth_sdk_message_");
            sb2.append(i);
            sb2.append("_action");
            int identifier2 = ResourceUtils.getIdentifier(applicationContext2, ResUtils.STRING, sb2.toString());
            message.action = identifier2 != 0 ? KernelContext.getApplicationContext().getResources().getString(identifier2) : "";
            Context applicationContext3 = KernelContext.getApplicationContext();
            StringBuilder sb3 = new StringBuilder("auth_sdk_message_");
            sb3.append(i);
            sb3.append("_type");
            int identifier3 = ResourceUtils.getIdentifier(applicationContext3, ResUtils.STRING, sb3.toString());
            message.type = identifier3 != 0 ? KernelContext.getApplicationContext().getResources().getString(identifier3) : LogHelper.DEFAULT_LEVEL;
            return message;
        } catch (Exception e2) {
            StringBuilder sb4 = new StringBuilder("Fail to get message of the code ");
            sb4.append(i);
            sb4.append(", the error message is ");
            sb4.append(e2.getMessage());
            SDKLogger.e("kernel", sb4.toString());
            return null;
        }
    }

    public static Message createMessage(int i, Object... objArr) {
        try {
            b.readLock().lock();
            Message message = a.get(Integer.valueOf(i));
            if (message == null) {
                b.readLock().unlock();
                b.writeLock().lock();
                message = b(i);
                if (message != null) {
                    a.put(Integer.valueOf(i), message);
                }
                b.readLock().lock();
                b.writeLock().unlock();
            }
            if (message == null) {
                Message a2 = a(i);
                b.readLock().unlock();
                return a2;
            } else if (objArr.length == 0) {
                b.readLock().unlock();
                return message;
            } else {
                Message message2 = (Message) message.clone();
                message2.message = MessageFormat.format(message.message, objArr);
                b.readLock().unlock();
                return message2;
            }
        } catch (Exception e2) {
            return a(e2.getMessage());
        } catch (Throwable th) {
            b.writeLock().unlock();
            throw th;
        }
    }

    public static String getMessageContent(int i, Object... objArr) {
        try {
            b.readLock().lock();
            Message message = a.get(Integer.valueOf(i));
            if (message == null) {
                b.readLock().unlock();
                b.writeLock().lock();
                message = b(i);
                if (message != null) {
                    a.put(Integer.valueOf(i), message);
                }
                b.readLock().lock();
                b.writeLock().unlock();
            }
            if (message == null) {
                String str = a(i).message;
                b.readLock().unlock();
                return str;
            }
            String format = MessageFormat.format(message.message, objArr);
            b.readLock().unlock();
            return format;
        } catch (Exception e2) {
            return a(e2.getMessage()).message;
        } catch (Throwable th) {
            b.writeLock().unlock();
            throw th;
        }
    }
}
