package com.autonavi.minimap.drive.auto.page;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.tripgroup.api.IAutoRemoteController.ConnectionType;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.drive.auto.AutoConnectionTypeEnum;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.drive.action.alicar.manage")
public class AutoConnectionManagerFragment extends DriveBasePage<deo> implements launchModeSingleTask {
    private String A;
    private String B;
    public RelativeLayout a;
    public RelativeLayout b;
    public RelativeLayout c;
    public RelativeLayout d;
    public RelativeLayout e;
    public TextView f;
    public TextView g;
    public AutoConnectionTypeEnum h = AutoConnectionTypeEnum.NONE;
    public boolean i = false;
    public ImageView j;
    public deg k;
    public def l;
    public LinearLayout m;
    public TextView n;
    public TextView o;
    public TextView p;
    public TextView q;
    public TextView r;
    public TextView s;
    public ImageView t;
    public ImageView u;
    public ImageView v;
    public TitleBar w;
    public AlertView x;
    public aga y = new aga() {
        public final void a(ConnectionType connectionType) {
            AutoConnectionManagerFragment.this.i = true;
            AutoConnectionManagerFragment.this.a(true);
            AutoConnectionManagerFragment.a(AutoConnectionManagerFragment.this, connectionType);
            ((dej) ((deo) AutoConnectionManagerFragment.this.mPresenter).b).b();
        }

        public final void b(ConnectionType connectionType) {
            AutoConnectionManagerFragment.this.i = false;
            AutoConnectionManagerFragment.this.a(false);
            AutoConnectionManagerFragment.a(AutoConnectionManagerFragment.this, connectionType);
        }
    };
    public OnClickListener z = new OnClickListener() {
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.bluetooth_function_limition) {
                AutoConnectionManagerFragment.e(AutoConnectionManagerFragment.this);
            } else if (id == R.id.auto_send_route_to_auto) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putBoolean("isWifiConnection", AutoConnectionManagerFragment.f(AutoConnectionManagerFragment.this));
                AutoConnectionManagerFragment.this.startPage(RemoteControlFragment.class, pageBundle);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", ((deo) AutoConnectionManagerFragment.this.mPresenter).c() ? 1 : 2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00250", "B001", jSONObject);
            } else if (id == R.id.auto_send_apk_to_auto) {
                AutoConnectionManagerFragment.h(AutoConnectionManagerFragment.this);
                AutoConnectionManagerFragment.a(AutoConnectionManagerFragment.this, (String) "B003");
            } else if (id == R.id.auto_send_mapdata_to_auto) {
                AutoConnectionManagerFragment.i(AutoConnectionManagerFragment.this);
                AutoConnectionManagerFragment.a(AutoConnectionManagerFragment.this, (String) "B002");
            } else {
                if (id == R.id.mapdata_function_limition) {
                    AutoConnectionManagerFragment.j(AutoConnectionManagerFragment.this);
                }
            }
        }
    };

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.auto_connection_manager);
    }

    private void b(boolean z2) {
        this.q.setEnabled(z2);
        this.t.setEnabled(z2);
        this.c.setEnabled(z2);
    }

    public final void a() {
        this.r.setEnabled(false);
        this.g.setEnabled(false);
        this.u.setEnabled(false);
        this.e.setEnabled(false);
    }

    private void c(boolean z2) {
        this.s.setEnabled(z2);
        this.f.setEnabled(z2);
        this.v.setEnabled(z2);
        this.d.setEnabled(z2);
    }

    public final void a(boolean z2) {
        if (z2) {
            this.m.setVisibility(0);
            this.n.setVisibility(8);
            this.j.setEnabled(true);
            return;
        }
        this.m.setVisibility(8);
        this.n.setVisibility(0);
        this.j.setEnabled(false);
        this.o.setText(getResources().getString(R.string.disconnection_info_text));
        this.a.setVisibility(0);
    }

    public final void a(AutoConnectionTypeEnum autoConnectionTypeEnum) {
        b(true);
        a();
        c(true);
        switch (autoConnectionTypeEnum) {
            case AMAP_WIFI_20:
                this.c.setVisibility(0);
                this.d.setVisibility(0);
                this.e.setVisibility(0);
                this.a.setVisibility(8);
                a(R.drawable.auto_connection_state_icon_wifi);
                this.p.setText(getString(R.string.auto_connection_style, getString(R.string.wifi)));
                if (!this.i) {
                    a(R.drawable.auto_connection_state_icon_disconnection);
                    this.a.setVisibility(0);
                    b(false);
                    break;
                }
                break;
            case AMAP_WIFI_10:
                this.c.setVisibility(0);
                this.d.setVisibility(8);
                this.e.setVisibility(8);
                this.a.setVisibility(8);
                a(R.drawable.auto_connection_state_icon_wifi);
                this.p.setText(getString(R.string.auto_connection_style, getString(R.string.wifi)));
                if (!this.i) {
                    a(R.drawable.auto_connection_state_icon_disconnection);
                    b(false);
                    break;
                }
                break;
            case AMAP_BLUETOOTH_20:
                this.c.setVisibility(0);
                this.d.setVisibility(0);
                this.e.setVisibility(0);
                this.a.setVisibility(0);
                a(R.drawable.auto_connection_state_icon_bluetooth);
                this.p.setText(getString(R.string.auto_connection_style, getString(R.string.blue_tooth)));
                if (!this.i) {
                    b(false);
                    a();
                    c(true);
                    this.m.setVisibility(8);
                    this.n.setVisibility(0);
                    this.o.setText(getResources().getString(R.string.disconnection_info_text));
                    a(R.drawable.auto_connection_state_icon_disconnection);
                    break;
                } else {
                    b(true);
                    a();
                    c(false);
                    this.m.setVisibility(0);
                    this.n.setVisibility(8);
                    this.o.setText(getResources().getString(R.string.auto_bluetooth_tips));
                    break;
                }
            case AMAP_BLUETOOTH_10:
                if (!this.i) {
                    this.c.setVisibility(0);
                    this.d.setVisibility(8);
                    this.e.setVisibility(8);
                    this.a.setVisibility(8);
                    b(false);
                    break;
                } else {
                    this.c.setVisibility(0);
                    this.d.setVisibility(8);
                    this.e.setVisibility(8);
                    this.a.setVisibility(8);
                    a(R.drawable.auto_connection_state_icon_bluetooth);
                    this.p.setText(getString(R.string.auto_connection_style, getString(R.string.blue_tooth)));
                    break;
                }
            case ALI_AUTO:
                this.c.setVisibility(0);
                this.d.setVisibility(8);
                this.e.setVisibility(8);
                this.a.setVisibility(8);
                a(R.drawable.auto_connection_state_icon_wifi);
                this.p.setText(getString(R.string.auto_connection_style, getString(R.string.wifi)));
                this.i = ((deo) this.mPresenter).e();
                if (!this.i) {
                    b(false);
                    this.c.setClickable(false);
                    this.m.setVisibility(8);
                    this.n.setVisibility(0);
                    break;
                } else {
                    b(true);
                    this.c.setClickable(true);
                    this.m.setVisibility(0);
                    this.n.setVisibility(8);
                    break;
                }
        }
        if (!this.i) {
            this.j.setEnabled(false);
            a(R.drawable.auto_connection_state_icon_disconnection);
            return;
        }
        this.j.setEnabled(true);
    }

    private void a(@DrawableRes int i2) {
        StringBuilder sb = new StringBuilder("     ");
        sb.append(getString(R.string.auto_title));
        sb.append("     ");
        SpannableString spannableString = new SpannableString(sb.toString());
        Drawable drawable = getResources().getDrawable(i2);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannableString.setSpan(new ImageSpan(drawable, 1), 12, 13, 17);
        this.w.setTitle(spannableString);
    }

    private static boolean b() {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            return iAutoRemoteController.isNewAmapSDK();
        }
        return false;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new deo(this);
    }

    static /* synthetic */ void a(AutoConnectionManagerFragment autoConnectionManagerFragment, ConnectionType connectionType) {
        if (connectionType == ConnectionType.WIFI) {
            if (b()) {
                autoConnectionManagerFragment.a(AutoConnectionTypeEnum.AMAP_WIFI_20);
                autoConnectionManagerFragment.h = AutoConnectionTypeEnum.AMAP_WIFI_20;
            } else {
                autoConnectionManagerFragment.a(AutoConnectionTypeEnum.AMAP_WIFI_10);
                autoConnectionManagerFragment.h = AutoConnectionTypeEnum.AMAP_WIFI_10;
            }
        }
        if (connectionType == ConnectionType.BLUETOOTH) {
            if (b()) {
                autoConnectionManagerFragment.a(AutoConnectionTypeEnum.AMAP_BLUETOOTH_20);
                autoConnectionManagerFragment.h = AutoConnectionTypeEnum.AMAP_BLUETOOTH_20;
                return;
            }
            autoConnectionManagerFragment.a(AutoConnectionTypeEnum.AMAP_BLUETOOTH_10);
            autoConnectionManagerFragment.h = AutoConnectionTypeEnum.AMAP_BLUETOOTH_10;
        }
    }

    static /* synthetic */ void e(AutoConnectionManagerFragment autoConnectionManagerFragment) {
        if (!autoConnectionManagerFragment.i) {
            autoConnectionManagerFragment.l = new def(autoConnectionManagerFragment.getActivity());
            autoConnectionManagerFragment.l.b();
            autoConnectionManagerFragment.l.c = new ss() {
                public final void a() {
                    AutoConnectionManagerFragment.this.dismissAllViewLayers();
                }
            };
            autoConnectionManagerFragment.showViewLayer(autoConnectionManagerFragment.l);
            return;
        }
        if (autoConnectionManagerFragment.h == AutoConnectionTypeEnum.AMAP_BLUETOOTH_20) {
            autoConnectionManagerFragment.k = new deg(autoConnectionManagerFragment.getActivity());
            autoConnectionManagerFragment.k.a();
            autoConnectionManagerFragment.k.c = new ss() {
                public final void a() {
                    AutoConnectionManagerFragment.this.dismissAllViewLayers();
                }
            };
            autoConnectionManagerFragment.showViewLayer(autoConnectionManagerFragment.k);
        }
    }

    static /* synthetic */ boolean f(AutoConnectionManagerFragment autoConnectionManagerFragment) {
        return (autoConnectionManagerFragment.h == AutoConnectionTypeEnum.AMAP_BLUETOOTH_10 || autoConnectionManagerFragment.h == AutoConnectionTypeEnum.AMAP_BLUETOOTH_20) ? false : true;
    }

    static /* synthetic */ void h(AutoConnectionManagerFragment autoConnectionManagerFragment) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_offline/src/auto/lib/sendcarapk/index.jsx.js");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("auto", autoConnectionManagerFragment.A);
            jSONObject.put("aos", autoConnectionManagerFragment.B);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        pageBundle.putString("jsData", jSONObject.toString());
        autoConnectionManagerFragment.startPage(Ajx3Page.class, pageBundle);
    }

    static /* synthetic */ void a(AutoConnectionManagerFragment autoConnectionManagerFragment, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!autoConnectionManagerFragment.i) {
                jSONObject.put("status", 0);
                LogManager.actionLogV2("P00250", str, jSONObject);
                return;
            }
            if (!((deo) autoConnectionManagerFragment.mPresenter).c()) {
                jSONObject.put("status", 2);
                LogManager.actionLogV2("P00250", str, jSONObject);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    static /* synthetic */ void i(AutoConnectionManagerFragment autoConnectionManagerFragment) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_offline/src/auto/index.jsx.js");
        pageBundle.putLong("jsData", System.currentTimeMillis());
        autoConnectionManagerFragment.startPage(Ajx3Page.class, pageBundle);
    }

    static /* synthetic */ void j(AutoConnectionManagerFragment autoConnectionManagerFragment) {
        if (autoConnectionManagerFragment.x != null && autoConnectionManagerFragment.isViewLayerShowing(autoConnectionManagerFragment.x)) {
            autoConnectionManagerFragment.dismissViewLayer(autoConnectionManagerFragment.x);
            autoConnectionManagerFragment.x = null;
        }
        a aVar = new a(autoConnectionManagerFragment.getContext());
        aVar.a(R.string.auto_send_map_title);
        aVar.b(R.string.auto_send_map_message);
        int i2 = R.string.auto_sure;
        AnonymousClass8 r2 = new a() {
            public final void onClick(AlertView alertView, int i) {
                AutoConnectionManagerFragment.this.dismissViewLayer(AutoConnectionManagerFragment.this.x);
                AutoConnectionManagerFragment.this.x = null;
            }
        };
        aVar.a.m = aVar.a.a.getText(i2);
        aVar.a.n = r2;
        autoConnectionManagerFragment.x = aVar.a();
        autoConnectionManagerFragment.showViewLayer(autoConnectionManagerFragment.x);
        autoConnectionManagerFragment.x.startAnimation();
    }
}
