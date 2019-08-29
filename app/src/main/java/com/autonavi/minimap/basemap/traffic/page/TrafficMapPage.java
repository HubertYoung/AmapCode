package com.autonavi.minimap.basemap.traffic.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.zoom.ZoomView;
import com.autonavi.minimap.R;

@PageAction("page_action_trafficmap")
public class TrafficMapPage extends AbstractBaseMapPage<csq> {
    private static final String a = "com.autonavi.minimap.basemap.traffic.page.TrafficMapPage";
    private a b;
    private ImageButton c;
    private TextView d;
    private View e;

    static class a extends ccx {
        public final void c() {
        }

        public final void e() {
        }

        public a(IMapPage iMapPage) {
            super(iMapPage);
        }

        public final void d() {
            View b = this.e.b(false);
            LayoutParams layoutParams = new LayoutParams(this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size), this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
            layoutParams.rightMargin = agn.a(this.a.getContext(), 5.0f);
            c(b, layoutParams);
            c(this.e.c(false), this.e.k());
        }

        public final void b() {
            View a = this.e.a(false);
            a.setContentDescription("指南针");
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.leftMargin = agn.a(this.b, 8.0f);
            layoutParams.topMargin = agn.a(this.b, 4.0f);
            a(a, layoutParams);
        }

        public final void f() {
            ZoomView l = this.e.l();
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.rightMargin = agn.a(this.b, 5.0f);
            layoutParams.topMargin = agn.a(this.b, 4.0f);
            d(l, layoutParams);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public csq createPresenter() {
        return new csq(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.sina_traffic_map_view);
        this.b = new a(this);
        this.c = (ImageButton) findViewById(R.id.title_btn_left);
        this.d = (TextView) findViewById(R.id.title_text_name);
        this.e = findViewById(R.id.title_btn_right);
        this.c.setOnClickListener((OnClickListener) this.mPresenter);
        this.e.setOnClickListener((OnClickListener) this.mPresenter);
        this.d.setText(R.string.oper_surrounding_roads);
    }

    public View getMapSuspendView() {
        return this.b.a();
    }
}
