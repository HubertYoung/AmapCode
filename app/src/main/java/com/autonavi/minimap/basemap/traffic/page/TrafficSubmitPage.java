package com.autonavi.minimap.basemap.traffic.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.presenter.TrafficSubmitPresenter;
import com.autonavi.minimap.basemap.view.ToggleButtonList;

public class TrafficSubmitPage extends AbstractTrafficSubmitPage<TrafficSubmitPresenter> {
    public LayoutInflater b;
    public TextView c;
    public crw d = null;
    public GridView e;
    public ToggleButton f;
    private View g;
    private View h;
    private View i;
    private ToggleButton j;
    private ToggleButton k;
    private ToggleButton l;
    private ToggleButton m;
    private ToggleButton n;
    private ToggleButtonList o;
    private View p;
    private View q;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.traffic_report_submit_fragment);
    }

    public final void a(boolean z) {
        super.a(z);
        this.b = LayoutInflater.from(getContext());
        ((TextView) findViewById(R.id.title_text_name)).setText(((TrafficSubmitPresenter) this.mPresenter).j());
        this.c = (TextView) findViewById(R.id.traffic_report_desc);
        this.c.setOnClickListener((OnClickListener) this.mPresenter);
        this.g = findViewById(R.id.traffic_report_right_now);
        this.g.setOnClickListener((OnClickListener) this.mPresenter);
        this.e = (GridView) findViewById(R.id.label_list);
        this.h = findViewById(R.id.drive_way_list1);
        this.i = findViewById(R.id.drive_way_list2);
        this.j = (ToggleButton) findViewById(R.id.drive_way_diff);
        this.k = (ToggleButton) findViewById(R.id.drive_way_diff2);
        this.f = (ToggleButton) findViewById(R.id.drive_way_same);
        this.l = (ToggleButton) findViewById(R.id.drive_way_left);
        this.m = (ToggleButton) findViewById(R.id.drive_way_mid);
        this.n = (ToggleButton) findViewById(R.id.drive_way_right);
        this.o = new ToggleButtonList(new ToggleButton[]{this.j, this.k, this.f, this.l, this.m, this.n});
        this.p = findViewById(R.id.traffic_detail_label_layout);
        this.q = findViewById(R.id.traffic_report_poi_layout);
        if (z) {
            this.p.setVisibility(8);
            this.q.setVisibility(8);
        }
    }

    public final void a(int i2) {
        this.h.setVisibility(i2);
    }

    public final void b(int i2) {
        this.i.setVisibility(i2);
    }

    public final int c() {
        if (this.o == null) {
            return -1;
        }
        CompoundButton compoundButton = this.o.b;
        if (compoundButton == null) {
            return -1;
        }
        if (compoundButton == this.j || compoundButton == this.k) {
            return 1;
        }
        if (compoundButton == this.f) {
            return 0;
        }
        if (compoundButton == this.l) {
            return 2;
        }
        if (compoundButton == this.m) {
            return 3;
        }
        if (compoundButton == this.n) {
            return 4;
        }
        return -1;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new TrafficSubmitPresenter(this);
    }
}
