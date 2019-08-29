package defpackage;

import android.text.TextUtils;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.onekeycheck.action.ActionListener;
import com.autonavi.minimap.onekeycheck.action.BaseAction;
import com.autonavi.minimap.onekeycheck.module.TraceRouteInfo;
import com.autonavi.minimap.onekeycheck.netease.service.LDNetDiagnoListener;
import com.autonavi.minimap.onekeycheck.netease.service.LDNetDiagnoService;
import java.util.Timer;

/* renamed from: dsp reason: default package */
/* compiled from: TraceRouteInfosAction */
public final class dsp extends BaseAction implements LDNetDiagnoListener {
    private LDNetDiagnoService d;
    private String e;
    /* access modifiers changed from: private */
    public TraceRouteInfo f;
    private StringBuffer g;
    private int h;
    /* access modifiers changed from: private */
    public boolean i;
    /* access modifiers changed from: private */
    public boolean j;

    public dsp(ActionListener actionListener, String str) {
        super(actionListener);
        this.e = TextUtils.isEmpty(str) ? "m5.amap.com" : str;
        this.f = new TraceRouteInfo();
        this.g = new StringBuffer("\n开始traceroute...\n");
    }

    public final void start() {
        if (getState().getState() <= 0) {
            getState().update(1);
            dsy dsy = new dsy(AMapPageUtil.getAppContext(), new a() {
                public final void a(String str) {
                    dsp.this.f.putNode("net_speed", str);
                    dsp.this.i = true;
                    if (dsp.this.j) {
                        dsp.this.c();
                    }
                }
            });
            dsy.a = dsy.a();
            dsy.b = System.currentTimeMillis();
            new Timer().schedule(dsy.e, 2000);
            this.d = new LDNetDiagnoService(AMapPageUtil.getAppContext(), this.e, this);
            this.d.setIfUseJNICTrace(true);
            this.d.execute(new String[0]);
            getState().update(3);
        }
    }

    public final void stop() {
        a();
    }

    public final void finish() {
        super.finish();
        a();
    }

    private void a() {
        if (this.d != null) {
            this.d.stopNetDialogsis();
        }
    }

    public final void OnNetDiagnoFinished() {
        b();
    }

    private void b() {
        a();
        this.f.putNode("tracert", this.g.toString());
        this.j = true;
        if (this.i) {
            c();
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        finish();
        callbackOnResponse(this.f);
    }

    public final void OnNetDiagnoUpdated(dsr dsr) {
        if (dsr != null) {
            if (!dsr.c) {
                boolean z = dsr.b.contains("*****") || dsr.b.contains("timeout");
                if (TextUtils.isEmpty(dsr.b) || !z) {
                    this.h = 0;
                } else {
                    this.h++;
                }
                this.g.append(dsr.b);
                if (this.h >= 4) {
                    b();
                }
            } else if (!TextUtils.isEmpty(dsr.a)) {
                this.f.putNode(dsr.a, dsr.b);
            }
        }
    }
}
