package com.alipay.mobile.tinyappcommon.remotedebug.state;

import android.app.Activity;
import android.content.Context;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.remotedebug.view.RemoteDebugInfoPanelView;
import java.lang.ref.WeakReference;

/* compiled from: RemoteDebugStateManager */
public class b implements a {
    /* access modifiers changed from: private */
    public static final String a = b.class.getSimpleName();
    private RemoteDebugState b = RemoteDebugState.STATE_NON_REMOTE_DEBUG;
    /* access modifiers changed from: private */
    public WeakReference<Activity> c;
    /* access modifiers changed from: private */
    public com.alipay.mobile.tinyappcommon.remotedebug.view.a d;
    /* access modifiers changed from: private */
    public RemoteDebugInfoPanelView e;
    private a f;

    /* compiled from: RemoteDebugStateManager */
    public interface a {
        void exitDebugMode();
    }

    public final void a(a listener) {
        this.f = listener;
    }

    public final void a(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity cannot be null!");
        }
        this.c = new WeakReference<>(activity);
    }

    public final void a(RemoteDebugState state) {
        H5Log.d(a, "notifyStateChanged...state:" + state + ", old state: " + this.b);
        if (this.b != state) {
            this.b = state;
            switch (state) {
                case STATE_CONNECTING:
                    d();
                    return;
                case STATE_CONNECTED:
                    e();
                    return;
                case STATE_CONNECT_FAILED:
                    f();
                    return;
                case STATE_NETWORK_UNAVAILABLE:
                    k();
                    return;
                case STATE_REMOTE_DISCONNECTED:
                    g();
                    return;
                case STATE_HIT_BREAKPOINT:
                    h();
                    return;
                case STATE_RELEASE_BREAKPOINT:
                    i();
                    return;
                case STATE_EXITED:
                    j();
                    return;
                default:
                    return;
            }
        }
    }

    private void d() {
        if (this.b != RemoteDebugState.STATE_CONNECTING) {
            H5Log.d(a, "remoteDebugConnecting...state error");
        } else {
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    if (b.this.m()) {
                        if (b.this.e == null) {
                            b.this.e = new RemoteDebugInfoPanelView((Context) b.this.c.get());
                            b.this.e.setActionEventListener(b.this);
                        }
                        b.this.e.setStateConnecting();
                    }
                }
            });
        }
    }

    private void e() {
        if (this.b != RemoteDebugState.STATE_CONNECTED) {
            H5Log.d(a, "remoteDebugConnected...state error");
        } else {
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    if (b.this.m()) {
                        if (b.this.e == null) {
                            b.this.e = new RemoteDebugInfoPanelView((Context) b.this.c.get());
                            b.this.e.setActionEventListener(b.this);
                        }
                        b.this.e.setStateConnected();
                    }
                }
            });
        }
    }

    private void f() {
        if (this.b != RemoteDebugState.STATE_CONNECT_FAILED) {
            H5Log.d(a, "remoteDebugConnectFailed...state error");
        } else {
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    if (b.this.m()) {
                        if (b.this.d == null) {
                            b.this.d = new com.alipay.mobile.tinyappcommon.remotedebug.view.a((Context) b.this.c.get());
                            b.this.d.a((a) b.this);
                        }
                        b.this.d.a((String) "连接中断");
                        b.this.d.c();
                        if (b.this.e == null) {
                            b.this.e = new RemoteDebugInfoPanelView((Context) b.this.c.get());
                            b.this.e.setActionEventListener(b.this);
                        }
                        b.this.e.setStateConnectFailed();
                    }
                }
            });
        }
    }

    private void g() {
        if (this.b != RemoteDebugState.STATE_REMOTE_DISCONNECTED) {
            H5Log.d(a, "remoteDisconnected...state error");
        } else {
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    H5Log.d(b.a, "in disconnect");
                    if (b.this.m()) {
                        if (b.this.d == null) {
                            b.this.d = new com.alipay.mobile.tinyappcommon.remotedebug.view.a((Context) b.this.c.get());
                            b.this.d.a((a) b.this);
                        }
                        b.this.d.a((String) "连接中断");
                        b.this.d.c();
                    }
                }
            });
        }
    }

    private void h() {
        if (this.b != RemoteDebugState.STATE_HIT_BREAKPOINT) {
            H5Log.d(a, "hitBreakpoint...state error");
        } else {
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    if (b.this.m()) {
                        if (b.this.d == null) {
                            b.this.d = new com.alipay.mobile.tinyappcommon.remotedebug.view.a((Context) b.this.c.get());
                            b.this.d.a((a) b.this);
                        }
                        b.this.d.a((String) "命中断点...");
                        b.this.d.c();
                    }
                }
            });
        }
    }

    private void i() {
        if (this.b != RemoteDebugState.STATE_RELEASE_BREAKPOINT) {
            H5Log.d(a, "releaseBreakpoint...state error");
        } else {
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    if (b.this.d != null) {
                        b.this.d.dismiss();
                    }
                }
            });
        }
    }

    private void j() {
        if (this.b != RemoteDebugState.STATE_EXITED) {
            H5Log.d(a, "exitDebugMode...state error");
        } else if (this.f != null) {
            this.f.exitDebugMode();
        }
    }

    private void k() {
        if (this.b != RemoteDebugState.STATE_NETWORK_UNAVAILABLE) {
            H5Log.d(a, "handleNetworkUnavailable...state error");
        } else {
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    if (b.this.m()) {
                        if (b.this.d == null) {
                            b.this.d = new com.alipay.mobile.tinyappcommon.remotedebug.view.a((Context) b.this.c.get());
                            b.this.d.a((a) b.this);
                        }
                        b.this.d.a((String) "网络不可用...");
                        b.this.d.c();
                    }
                }
            });
        }
    }

    public final void a() {
        l();
    }

    private void l() {
        if (m()) {
            AUNoticeDialog dialog = new AUNoticeDialog((Context) this.c.get(), null, "确定退出远程调试?", "确定", "取消");
            dialog.setPositiveListener(new OnClickPositiveListener() {
                public final void onClick() {
                    b.this.a(RemoteDebugState.STATE_EXITED);
                }
            });
            dialog.show();
        }
    }

    /* access modifiers changed from: private */
    public boolean m() {
        Activity activity = (Activity) this.c.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            return true;
        }
        H5Log.w(a, "activity reference is null, release remote debug views.");
        b();
        return false;
    }

    public final void b() {
        H5Log.d(a, "in release view");
        if (this.e != null) {
            this.e = null;
        }
        if (this.d != null) {
            this.d.dismiss();
            this.d = null;
        }
    }
}
