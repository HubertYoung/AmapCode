package com.xiaomi.mipush.sdk;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageHandleService extends IntentService {
    private static ConcurrentLinkedQueue<a> a = new ConcurrentLinkedQueue<>();

    public static class a {
        private PushMessageReceiver a;
        private Intent b;

        public a(Intent intent, PushMessageReceiver pushMessageReceiver) {
            this.a = pushMessageReceiver;
            this.b = intent;
        }

        public PushMessageReceiver a() {
            return this.a;
        }

        public Intent b() {
            return this.b;
        }
    }

    public MessageHandleService() {
        super("MessageHandleThread");
    }

    protected static void a(Service service, Intent intent) {
        if (intent != null) {
            try {
                a poll = a.poll();
                if (poll != null) {
                    PushMessageReceiver a2 = poll.a();
                    Intent b = poll.b();
                    int intExtra = b.getIntExtra(PushMessageHelper.MESSAGE_TYPE, 1);
                    if (intExtra != 1) {
                        switch (intExtra) {
                            case 3:
                                MiPushCommandMessage miPushCommandMessage = (MiPushCommandMessage) b.getSerializableExtra(PushMessageHelper.KEY_COMMAND);
                                a2.onCommandResult(service, miPushCommandMessage);
                                if (TextUtils.equals(miPushCommandMessage.getCommand(), "register")) {
                                    a2.onReceiveRegisterResult(service, miPushCommandMessage);
                                    if (miPushCommandMessage.getResultCode() == 0) {
                                        g.c(service);
                                        return;
                                    }
                                }
                                break;
                            case 4:
                                return;
                            default:
                                return;
                        }
                    } else {
                        a a3 = ah.a((Context) service).a(b);
                        if (a3 != null) {
                            if (a3 instanceof MiPushMessage) {
                                MiPushMessage miPushMessage = (MiPushMessage) a3;
                                if (!miPushMessage.isArrivedMessage()) {
                                    a2.onReceiveMessage(service, miPushMessage);
                                }
                                if (miPushMessage.getPassThrough() == 1) {
                                    a2.onReceivePassThroughMessage(service, miPushMessage);
                                } else if (miPushMessage.isNotified()) {
                                    a2.onNotificationMessageClicked(service, miPushMessage);
                                } else {
                                    a2.onNotificationMessageArrived(service, miPushMessage);
                                }
                            } else if (a3 instanceof MiPushCommandMessage) {
                                MiPushCommandMessage miPushCommandMessage2 = (MiPushCommandMessage) a3;
                                a2.onCommandResult(service, miPushCommandMessage2);
                                if (TextUtils.equals(miPushCommandMessage2.getCommand(), "register")) {
                                    a2.onReceiveRegisterResult(service, miPushCommandMessage2);
                                    if (miPushCommandMessage2.getResultCode() == 0) {
                                        g.c(service);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (RuntimeException e) {
                b.a((Throwable) e);
            }
        }
    }

    public static void addJob(a aVar) {
        if (aVar != null) {
            a.add(aVar);
        }
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        a(this, intent);
    }
}
