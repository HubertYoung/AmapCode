package com.autonavi.minimap.basemap.traffic.page;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.presenter.AbstractTrafficSubmitPresenter;

public abstract class AbstractTrafficSubmitPage<Presenter extends AbstractTrafficSubmitPresenter> extends AbstractBasePage<Presenter> {
    public LinearLayout a;
    private View b;
    private View c;
    private View d;
    private TextView e;

    public void a(boolean z) {
        this.b = findViewById(R.id.title_btn_left);
        this.b.setOnClickListener((OnClickListener) this.mPresenter);
        this.d = findViewById(R.id.btn_pick_poi);
        this.d.setOnClickListener((OnClickListener) this.mPresenter);
        this.e = (TextView) findViewById(R.id.poi_name);
        this.a = (LinearLayout) findViewById(R.id.pic_container);
        this.c = findViewById(R.id.image_plus);
        this.c.setOnClickListener((OnClickListener) this.mPresenter);
    }

    public final void a(String str) {
        this.e.setText(str);
    }

    public final void a(View view) {
        this.a.removeView(view);
        if (this.a.getChildCount() == 0) {
            this.a.setVisibility(8);
        }
        a();
    }

    public final void a() {
        this.c.setVisibility(this.a.getChildCount() <= 0 ? 0 : 8);
    }

    public final void b() {
        if (this.a != null) {
            this.a.removeAllViews();
        }
    }
}
