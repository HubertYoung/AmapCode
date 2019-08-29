package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.ajx.ModuleSlideContainer;
import com.autonavi.bundle.uitemplate.mapwidget.common.MapWidgetTip;
import com.autonavi.bundle.uitemplate.mapwidget.inter.LogVersionType;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.util.List;

/* renamed from: aqo reason: default package */
/* compiled from: RedesignTipLayer */
public final class aqo implements IViewLayer {
    public ModuleSlideContainer a;
    public Runnable b;
    public boolean c;
    /* access modifiers changed from: private */
    public bid d;
    private View e = LayoutInflater.from(this.f).inflate(R.layout.redesign_pull_tip, null);
    private Context f;
    private MapWidgetTip g = ((MapWidgetTip) this.e.findViewById(R.id.redesign_pull_tip));
    private Runnable h;

    /* renamed from: aqo$a */
    /* compiled from: RedesignTipLayer */
    class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(aqo aqo, byte b) {
            this();
        }

        public final void run() {
            if (aqo.this.d != null && aqo.this.d.isAlive()) {
                aqo.this.b();
            }
        }
    }

    /* renamed from: aqo$b */
    /* compiled from: RedesignTipLayer */
    public class b implements Runnable {
        private b() {
        }

        public /* synthetic */ b(aqo aqo, byte b) {
            this();
        }

        public final void run() {
            aqo.a(aqo.this);
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void showBackground(boolean z) {
    }

    public aqo(bid bid) {
        this.d = bid;
        this.f = bid.getContext();
        this.g.setTipPadding(DimensionUtils.dipToPixel(10.0f), DimensionUtils.dipToPixel(10.0f), DimensionUtils.dipToPixel(10.0f), DimensionUtils.dipToPixel(10.0f));
        this.g.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                aqo.this.b();
                return true;
            }
        });
    }

    public final void a(boolean z) {
        if (this.a != null) {
            this.a.tipStateChange(z ? "1" : "0");
        }
    }

    public final void a(int i) {
        this.g.setTranslationY((float) ((-i) + DimensionUtils.dipToPixel(10.0f)));
    }

    public static boolean a() {
        Object item = Ajx.getInstance().getMemoryStorage("toolbox").getItem("vuiShowing");
        if (item != null && "1".equals(item.toString())) {
            return false;
        }
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences(LogVersionType.REDESIGN, 0);
        if (sharedPreferences == null || sharedPreferences.getInt("pull_tip_show_times", 0) >= 3) {
            return false;
        }
        if ((System.currentTimeMillis() - sharedPreferences.getLong("pull_tip_last_show", -1)) / 86400000 < 2) {
            return false;
        }
        return true;
    }

    public final View getView() {
        return this.e;
    }

    public final boolean onBackPressed() {
        b();
        return false;
    }

    public final void b() {
        if (this.c && this.d != null && this.d.isAlive()) {
            aho.b(this.h);
            this.c = false;
            if (this.e != null && this.e.getVisibility() == 0) {
                this.e.setVisibility(8);
            }
            this.d.dismissViewLayer(this);
            a(false);
        }
    }

    static /* synthetic */ void a(aqo aqo) {
        if (!aqo.c && aqo.d != null && aqo.d.isAlive()) {
            List<IViewLayer> layerStack = aqo.d.getLayerStack();
            if ((layerStack != null && layerStack.size() == 1 && (layerStack.get(0) instanceof aqv)) || !aqo.d.hasViewLayer()) {
                aqo.c = true;
                aqo.d.showViewLayer(aqo);
                if (aqo.h == null) {
                    aqo.h = new a(aqo, 0);
                }
                aho.a(aqo.h, 5000);
                aqo.a(true);
            }
        }
    }
}
