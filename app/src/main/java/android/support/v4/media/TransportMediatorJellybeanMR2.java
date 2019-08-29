package android.support.v4.media;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.OnGetPlaybackPositionListener;
import android.media.RemoteControlClient.OnPlaybackPositionUpdateListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnWindowAttachListener;
import android.view.ViewTreeObserver.OnWindowFocusChangeListener;

class TransportMediatorJellybeanMR2 {
    final Context a;
    final AudioManager b;
    final View c;
    final TransportMediatorCallback d;
    final String e;
    final IntentFilter f;
    final Intent g;
    final OnWindowAttachListener h = new OnWindowAttachListener() {
        public void onWindowAttached() {
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = TransportMediatorJellybeanMR2.this;
            transportMediatorJellybeanMR2.a.registerReceiver(transportMediatorJellybeanMR2.j, transportMediatorJellybeanMR2.f);
            transportMediatorJellybeanMR2.n = PendingIntent.getBroadcast(transportMediatorJellybeanMR2.a, 0, transportMediatorJellybeanMR2.g, 268435456);
            transportMediatorJellybeanMR2.o = new RemoteControlClient(transportMediatorJellybeanMR2.n);
            transportMediatorJellybeanMR2.o.setOnGetPlaybackPositionListener(transportMediatorJellybeanMR2.l);
            transportMediatorJellybeanMR2.o.setPlaybackPositionUpdateListener(transportMediatorJellybeanMR2.m);
        }

        public void onWindowDetached() {
            TransportMediatorJellybeanMR2.this.d();
        }
    };
    final OnWindowFocusChangeListener i = new OnWindowFocusChangeListener() {
        public void onWindowFocusChanged(boolean z) {
            if (z) {
                TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = TransportMediatorJellybeanMR2.this;
                if (!transportMediatorJellybeanMR2.p) {
                    transportMediatorJellybeanMR2.p = true;
                    transportMediatorJellybeanMR2.b.registerMediaButtonEventReceiver(transportMediatorJellybeanMR2.n);
                    transportMediatorJellybeanMR2.b.registerRemoteControlClient(transportMediatorJellybeanMR2.o);
                    if (transportMediatorJellybeanMR2.q == 3) {
                        transportMediatorJellybeanMR2.a();
                    }
                }
                return;
            }
            TransportMediatorJellybeanMR2.this.c();
        }
    };
    final BroadcastReceiver j = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {
                TransportMediatorJellybeanMR2.this.d.a((KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT"));
            } catch (ClassCastException unused) {
            }
        }
    };
    OnAudioFocusChangeListener k = new OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int i) {
            TransportMediatorJellybeanMR2.this.d.a(i);
        }
    };
    final OnGetPlaybackPositionListener l = new OnGetPlaybackPositionListener() {
        public long onGetPlaybackPosition() {
            return TransportMediatorJellybeanMR2.this.d.a();
        }
    };
    final OnPlaybackPositionUpdateListener m = new OnPlaybackPositionUpdateListener() {
        public void onPlaybackPositionUpdate(long j) {
            TransportMediatorJellybeanMR2.this.d.a(j);
        }
    };
    PendingIntent n;
    RemoteControlClient o;
    boolean p;
    int q = 0;
    boolean r;

    public TransportMediatorJellybeanMR2(Context context, AudioManager audioManager, View view, TransportMediatorCallback transportMediatorCallback) {
        this.a = context;
        this.b = audioManager;
        this.c = view;
        this.d = transportMediatorCallback;
        StringBuilder sb = new StringBuilder();
        sb.append(context.getPackageName());
        sb.append(":transport:");
        sb.append(System.identityHashCode(this));
        this.e = sb.toString();
        this.g = new Intent(this.e);
        this.g.setPackage(context.getPackageName());
        this.f = new IntentFilter();
        this.f.addAction(this.e);
        this.c.getViewTreeObserver().addOnWindowAttachListener(this.h);
        this.c.getViewTreeObserver().addOnWindowFocusChangeListener(this.i);
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        if (!this.r) {
            this.r = true;
            this.b.requestAudioFocus(this.k, 3, 1);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        if (this.r) {
            this.r = false;
            this.b.abandonAudioFocus(this.k);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        b();
        if (this.p) {
            this.p = false;
            this.b.unregisterRemoteControlClient(this.o);
            this.b.unregisterMediaButtonEventReceiver(this.n);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void d() {
        c();
        if (this.n != null) {
            this.a.unregisterReceiver(this.j);
            this.n.cancel();
            this.n = null;
            this.o = null;
        }
    }
}
