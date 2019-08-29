package com.autonavi.minimap.route.bus.realtimebus.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.SearchEdit;

public class RTBusSearchPage extends RTBusSearchBasePage<dyr> {
    /* access modifiers changed from: private */
    public SearchEdit c;

    public void onCreate(Context context) {
        super.onCreate(context);
    }

    /* access modifiers changed from: protected */
    public final void a() {
        setContentView(R.layout.route_realtime_real_time_bus_search);
        View contentView = getContentView();
        this.c = (SearchEdit) contentView.findViewById(R.id.search_search_layout);
        this.a = new dwp(this.b);
        this.a.a((bid) this, contentView);
        ((LinearLayout) contentView.findViewById(R.id.busline_search_header)).setBackgroundResource(R.color.realbus_search_header);
        NoDBClickUtil.a(contentView.findViewById(R.id.btn_search_back), (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RTBusSearchPage.this.finish();
            }
        });
        aho.a(new Runnable() {
            public final void run() {
                if (RTBusSearchPage.this.c != null) {
                    RTBusSearchPage.this.c.getEditText().clearFocus();
                }
            }
        });
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dyr(this);
    }
}
