package defpackage;

import android.os.Build.VERSION;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import java.lang.reflect.Field;

/* renamed from: cvq reason: default package */
/* compiled from: MessageQueueProxy */
public final class cvq {
    private MessageQueue a;
    private Field b;
    private boolean c = false;

    public cvq() {
        try {
            this.b = MessageQueue.class.getDeclaredField("mMessages");
            this.b.setAccessible(true);
            this.c = true;
        } catch (Exception unused) {
        }
        if (this.c) {
            if (VERSION.SDK_INT >= 23) {
                this.a = Looper.getMainLooper().getQueue();
                return;
            }
            try {
                Field declaredField = Looper.class.getDeclaredField("mQueue");
                declaredField.setAccessible(true);
                this.a = (MessageQueue) declaredField.get(Looper.getMainLooper());
            } catch (Exception unused2) {
                this.c = false;
            }
        }
    }

    public final Message a() {
        try {
            if (this.b != null) {
                return (Message) this.b.get(this.a);
            }
        } catch (Exception unused) {
        }
        return null;
    }
}
