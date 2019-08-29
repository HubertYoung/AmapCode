package com.autonavi.minimap.auidebugger.qrcode;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;

public class QRCodePage extends AbstractBasePage<cnw> implements Callback {
    public dty a;
    public boolean b;
    public View c;
    public ProgressBar d;
    public TextView e;

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.ajxdebug_qrcode_layout);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!this.b) {
            this.b = true;
            ((cnw) this.mPresenter).a(surfaceHolder);
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.b = false;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cnw(this);
    }
}
