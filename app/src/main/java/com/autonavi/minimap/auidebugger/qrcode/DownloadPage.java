package com.autonavi.minimap.auidebugger.qrcode;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;

public class DownloadPage extends AbstractBasePage<cnv> {
    public View a;
    public ProgressBar b;
    public TextView c;
    public TextView d;
    public TextView e;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.ajxdebug_download_layout);
    }

    public final void a(String str, int i) {
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            mapView.d(0);
        }
        this.a.setVisibility(0);
        this.b.setProgress(i);
        this.c.setText(str);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cnv(this);
    }
}
