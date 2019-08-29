package defpackage;

import android.content.Context;
import android.widget.FrameLayout;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.MainMapInvokePriority;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.suspend.SuspendPartitionView.LayoutParams;
import com.autonavi.map.suspend.refactor.scenic.ISmartScenicController;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dkz reason: default package */
/* compiled from: MainMapScenicManager */
public class dkz implements IPageStateListener, czr, czu, czy {
    private IMainMapService a;
    private Context b;
    private cde c;
    private FrameLayout d;
    private boolean e = false;
    private boolean f = false;
    private cet g = new cet() {
    };

    public void onAppear() {
    }

    @MainMapInvokePriority(4.0f)
    public void onCreate() {
        this.a = (IMainMapService) ank.a(IMainMapService.class);
        this.b = this.a.e().getContext();
        this.c = this.a.b();
        this.c.a((ISmartScenicController) ank.a(ISmartScenicController.class));
        this.d = new FrameLayout(this.b);
        this.d.setVisibility(8);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.leftMargin = this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
        layoutParams.d = 2.5f;
        layoutParams.b = 3.0f;
        czc czc = (czc) this.a.a(czc.class);
        if (czc != null) {
            czc.a(this.d, layoutParams);
        }
    }

    public void onPause() {
        if (this.c != null) {
        }
    }

    @MainMapInvokePriority(4.0f)
    public void onDestroy() {
        if (this.c == null || this.c.h() != null) {
        }
    }

    public void onIndoor(boolean z) {
        this.f = z;
        a();
    }

    public void onScenic(boolean z) {
        this.e = z;
        a();
        if (z) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", "1");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00001", LogConstant.SMART_SCENIC_WIDGET_VIEW, jSONObject);
        }
    }

    public void onCover() {
        if (this.c != null) {
        }
    }

    private void a() {
        this.a.a(czh.class);
    }

    public void onResume() {
        if (!(this.c == null || this.c.h() == null)) {
            this.a.a(czh.class);
        }
        if (this.c != null) {
            this.f = this.c.c().c();
            if (this.c.h() != null) {
                this.e = this.c.h().a();
            }
        }
        a();
    }
}
