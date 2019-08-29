package com.autonavi.minimap.route.train.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;

public class TrainDataPage extends AbstractBasePage<ejk> {
    public View a;
    public ExtendedWebView b;
    public JsAdapter c;
    private Context d;
    private LayoutInflater e;

    public /* synthetic */ IPresenter createPresenter() {
        this.d = getContext();
        if (this.d != null) {
            this.e = LayoutInflater.from(this.d);
            this.a = this.e.inflate(R.layout.route_train_ticket_data_selected, null);
        }
        if (this.a != null) {
            setContentView(this.a);
        }
        return new ejk(this);
    }
}
