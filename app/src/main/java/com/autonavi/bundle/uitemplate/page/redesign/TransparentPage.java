package com.autonavi.bundle.uitemplate.page.redesign;

import android.content.Context;
import android.view.View;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

public class TransparentPage extends Ajx3Page implements Transparent {
    public void onCreate(Context context) {
        super.onCreate(context);
        this.mAjxView.setBackgroundColor(0);
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        amapAjxView.setBackgroundColor(0);
        return super.onCreateView(amapAjxView);
    }

    public void pageCreated() {
        super.pageCreated();
        this.mAjxView.setBackgroundColor(0);
    }

    public void resume() {
        super.resume();
        this.mAjxView.setBackgroundColor(0);
    }
}
