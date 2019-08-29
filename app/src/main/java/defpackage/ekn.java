package defpackage;

/* renamed from: ekn reason: default package */
/* compiled from: VoiceStateListener */
public abstract class ekn {
    protected int b;
    protected boolean c;

    public abstract void a(int i);

    public final void b(int i) {
        this.b = i;
        this.c = true;
    }

    public final boolean a() {
        if (!this.c) {
            return false;
        }
        this.c = false;
        return true;
    }
}
