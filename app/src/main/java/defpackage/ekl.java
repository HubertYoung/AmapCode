package defpackage;

import com.autonavi.minimap.route.voice.IRouteVoice;

/* renamed from: ekl reason: default package */
/* compiled from: RouteVoiceManager */
public final class ekl {
    public ekn a = this;
    private IRouteVoice b;

    /* renamed from: ekl$a */
    /* compiled from: RouteVoiceManager */
    public static class a {
        public static ekl a = new ekl(0);
    }

    /* synthetic */ ekl(byte b2) {
        this();
    }

    private ekl() {
        this.b = new ekk();
    }

    public final IRouteVoice a() {
        if (this.b == null) {
            this.b = new ekk();
        }
        return this.b;
    }
}
