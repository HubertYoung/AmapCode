package com.autonavi.minimap.drive.auto.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;

public class AliCarLinkManagerPage extends DriveBasePage<dem> implements afw {
    private static String p = "Xcar_";
    private static String q = "IOV";
    public ImageView a;
    public Button b;
    public Button c;
    public Button d;
    public LinearLayout e;
    public LinearLayout f;
    public LinearLayout g;
    public LinearLayout h;
    public LinearLayout i;
    public fbl j;
    public ProgressDlg k;
    public dee l;
    public OnClickListener m = new OnClickListener() {
        public final void onClick(View view) {
            if (agb.b()) {
                AliCarLinkManagerPage.a(AliCarLinkManagerPage.this);
                LogManager.actionLogV2("P00249", "B002");
                return;
            }
            AliCarLinkManagerPage.this.t = false;
            int id = view.getId();
            if (id == R.id.connect_button) {
                LogManager.actionLogV2("P00249", "B002");
                AliCarLinkManagerPage.b(AliCarLinkManagerPage.this);
            } else if (id == R.id.retry_connect_button) {
                AliCarLinkManagerPage.b(AliCarLinkManagerPage.this);
            } else {
                if (id == R.id.delete_connect_button) {
                    agb.a((String) "");
                    agb.a(Boolean.TRUE);
                    IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) defpackage.esb.a.a.a(IAutoRemoteController.class);
                    if (iAutoRemoteController != null) {
                        iAutoRemoteController.stopALinkBt();
                        iAutoRemoteController.stopALinkWifi();
                    }
                    agb.a(false);
                    agb.c(false);
                    AliCarLinkManagerPage.this.j.c.clear();
                    AliCarLinkManagerPage.this.j.d.clear();
                    AliCarLinkManagerPage.this.j.e.clear();
                    AliCarLinkManagerPage.this.j.g();
                    AliCarLinkManagerPage.this.a(CONNECTION_BUTTON_ACTION.TO_CONNECT);
                }
            }
        }
    };
    public fbn n = new fbn() {
        public final void a() {
            AliCarLinkManagerPage.this.j.a(AliCarLinkManagerPage.this.n);
        }

        public final void b() {
            agb.c(false);
        }

        public final void c() {
            AliCarLinkManagerPage.this.j.a(AliCarLinkManagerPage.this.o);
            AliCarLinkManagerPage.this.j.f();
            del.a().b();
        }
    };
    public fbm o = new fbm() {
        public final void a(String str, int i) {
            if (AliCarLinkManagerPage.a(str)) {
                AliCarLinkManagerPage.this.b();
                if (i == 1) {
                    agb.a((String) "ali_auto_wifi");
                    agb.a(true);
                    agb.c(true);
                    AliCarLinkManagerPage.this.a(CONNECTION_BUTTON_ACTION.DISCONNECTION);
                    ded.a(AliCarLinkManagerPage.this.getContext()).a(AliCarLinkManagerPage.this.j);
                    AliCarLinkManagerPage.this.c();
                    return;
                }
                agb.c(false);
                if (agb.a()) {
                    AliCarLinkManagerPage.this.a(CONNECTION_BUTTON_ACTION.RETRY_CONNECT);
                } else {
                    AliCarLinkManagerPage.this.a(CONNECTION_BUTTON_ACTION.TO_CONNECT);
                }
            }
        }
    };
    private CONNECTION_BUTTON_ACTION r = CONNECTION_BUTTON_ACTION.TO_CONNECT;
    private boolean s = false;
    /* access modifiers changed from: private */
    public boolean t = false;
    private Handler u = new a(this);

    public enum CONNECTION_BUTTON_ACTION {
        TO_CONNECT,
        CONNECTTING,
        DISCONNECTION,
        RETRY_CONNECT
    }

    static class a extends Handler {
        WeakReference<AliCarLinkManagerPage> a = null;

        a(@NonNull AliCarLinkManagerPage aliCarLinkManagerPage) {
            this.a = new WeakReference<>(aliCarLinkManagerPage);
        }

        public final void handleMessage(Message message) {
            AliCarLinkManagerPage aliCarLinkManagerPage = (AliCarLinkManagerPage) this.a.get();
            if (aliCarLinkManagerPage != null && aliCarLinkManagerPage.isAlive() && message.what == 100) {
                aliCarLinkManagerPage.b();
                IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) defpackage.esb.a.a.a(IAutoRemoteController.class);
                if (iAutoRemoteController != null) {
                    iAutoRemoteController.stopALinkBt();
                    iAutoRemoteController.stopALinkWifi();
                }
                try {
                    aliCarLinkManagerPage.j.c();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                aliCarLinkManagerPage.a(CONNECTION_BUTTON_ACTION.RETRY_CONNECT);
                String d = agb.d();
                if (("amap_bluetooth".equals(d) || "amap_bluetooth_20".equals(d)) && iAutoRemoteController != null) {
                    iAutoRemoteController.checkNeedStartBluetoothServer();
                }
                LogManager.actionLogV2("P00261", "B001");
            }
            return;
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.car_link_manager);
        requestScreenOrientation(1);
        LogManager.actionLogV2("P00249", "B001");
        this.j = fbl.a(AMapPageUtil.getAppContext());
    }

    public final void b() {
        this.u.removeMessages(100);
        if (this.k != null) {
            this.k.dismiss();
            this.k = null;
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        del.a().b();
        if (isAlive() && isStarted()) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putBoolean("firstConnected", true);
            setResult(ResultType.OK, new PageBundle());
            startPageForResult(AutoConnectionManagerFragment.class, pageBundle, 1000);
        }
    }

    public final void a(CONNECTION_BUTTON_ACTION connection_button_action) {
        this.r = connection_button_action;
        switch (connection_button_action) {
            case TO_CONNECT:
                b(true);
                c(false);
                d(false);
                return;
            case RETRY_CONNECT:
                c(true);
                b(false);
                d(false);
                return;
            case DISCONNECTION:
                d(true);
                b(false);
                c(false);
                break;
        }
    }

    private void b(boolean z) {
        int i2 = 8;
        this.b.setVisibility(z ? 0 : 8);
        LinearLayout linearLayout = this.e;
        if (z) {
            i2 = 0;
        }
        linearLayout.setVisibility(i2);
        if (z) {
            this.a.setImageResource(R.drawable.connected);
        }
    }

    private void c(boolean z) {
        int i2 = 8;
        this.c.setVisibility(z ? 0 : 8);
        this.f.setVisibility(z ? 0 : 8);
        LinearLayout linearLayout = this.g;
        if (z) {
            i2 = 0;
        }
        linearLayout.setVisibility(i2);
        if (z) {
            this.a.setImageResource(R.drawable.connection_failed);
        }
    }

    private void d(boolean z) {
        int i2 = 8;
        this.d.setVisibility(z ? 0 : 8);
        this.h.setVisibility(z ? 0 : 8);
        LinearLayout linearLayout = this.i;
        if (z) {
            i2 = 0;
        }
        linearLayout.setVisibility(i2);
        if (z) {
            this.a.setImageResource(R.drawable.connected);
        }
    }

    public final void a(boolean z) {
        b();
        if (z) {
            a(CONNECTION_BUTTON_ACTION.DISCONNECTION);
            this.s = true;
            c();
            return;
        }
        if (!this.s) {
            a(CONNECTION_BUTTON_ACTION.RETRY_CONNECT);
        }
    }

    public final void a() {
        c();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dem(this);
    }

    static /* synthetic */ void a(AliCarLinkManagerPage aliCarLinkManagerPage) {
        aliCarLinkManagerPage.l = new dee(aliCarLinkManagerPage.getActivity());
        aliCarLinkManagerPage.l.a();
        aliCarLinkManagerPage.l.b = new ss() {
            public final void b() {
                agb.a(Boolean.FALSE);
                AliCarLinkManagerPage.this.t = true;
                AliCarLinkManagerPage.b(AliCarLinkManagerPage.this);
                AliCarLinkManagerPage.this.dismissAllViewLayers();
            }

            public final void a() {
                agb.a(Boolean.TRUE);
                AliCarLinkManagerPage.this.t = false;
                AliCarLinkManagerPage.this.dismissAllViewLayers();
            }
        };
        aliCarLinkManagerPage.showViewLayer(aliCarLinkManagerPage.l);
    }

    static /* synthetic */ void b(AliCarLinkManagerPage aliCarLinkManagerPage) {
        aliCarLinkManagerPage.k = new ProgressDlg(AMapAppGlobal.getTopActivity(), aliCarLinkManagerPage.getString(R.string.connecting_ali_auto));
        aliCarLinkManagerPage.k.setCancelable(true);
        aliCarLinkManagerPage.k.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                AliCarLinkManagerPage.this.b();
            }
        });
        aliCarLinkManagerPage.k.show();
        aliCarLinkManagerPage.u.sendEmptyMessageDelayed(100, 10000);
        try {
            aliCarLinkManagerPage.j.a(aliCarLinkManagerPage.n);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) defpackage.esb.a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            String d2 = agb.d();
            if ("amap_bluetooth".equals(d2) || "amap_bluetooth_20".equals(d2)) {
                iAutoRemoteController.stopALinkBt();
            }
            iAutoRemoteController.startAlinkWifi(aliCarLinkManagerPage);
        }
    }

    static /* synthetic */ boolean a(String str) {
        return !TextUtils.isEmpty(str) && (str.startsWith(p) || str.startsWith(q));
    }
}
