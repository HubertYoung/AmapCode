package com.autonavi.minimap.route.bus.realtimebus.page;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.adapter.RealTimeBusRepeateAdapter;
import com.autonavi.widget.ui.TitleBar;

public class RTBusDateSettingPage extends AbstractBasePage<dyn> {
    public TitleBar a;
    public ListView b;
    public RealTimeBusRepeateAdapter c;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.busline_attention_setting_repeate);
        View contentView = getContentView();
        this.a = (TitleBar) contentView.findViewById(R.id.title_bar);
        this.b = (ListView) contentView.findViewById(R.id.lv_route_realtime_settting_repeate);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dyn(this);
    }
}
