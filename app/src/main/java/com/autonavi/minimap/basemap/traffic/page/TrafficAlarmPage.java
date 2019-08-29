package com.autonavi.minimap.basemap.traffic.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.presenter.TrafficAlarmPresenter;

public class TrafficAlarmPage extends AbstractTrafficSubmitPage<TrafficAlarmPresenter> {
    public EditText b;
    public ToggleButton c;
    private Button d;
    private View e;
    private View f;
    private TextView g;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.traffic_report_alarm_fragment);
    }

    public final void a(boolean z) {
        super.a(z);
        ((TextView) findViewById(R.id.title_text_name)).setText(R.string.traffic_report_alarm_title_122);
        this.d = (Button) findViewById(R.id.traffic_report_right_now);
        this.d.setOnClickListener((OnClickListener) this.mPresenter);
        this.e = findViewById(R.id.traffic_report_right_now_shield);
        this.e.setOnClickListener((OnClickListener) this.mPresenter);
        this.b = (EditText) findViewById(R.id.traffic_report_tel_number);
        this.c = (ToggleButton) findViewById(R.id.traffic_report_hurt);
        this.c.setOnClickListener((OnClickListener) this.mPresenter);
        this.f = findViewById(R.id.traffic_report_status_layout);
        this.g = (TextView) findViewById(R.id.traffic_report_status_text);
        this.f.setOnClickListener((OnClickListener) this.mPresenter);
    }

    public final void b(boolean z) {
        this.d.setEnabled(z);
    }

    public final void a(int i) {
        this.e.setVisibility(i);
    }

    public final void b(int i) {
        this.f.setVisibility(i);
    }

    public final void a(CharSequence charSequence) {
        this.g.setText(charSequence);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new TrafficAlarmPresenter(this);
    }
}
