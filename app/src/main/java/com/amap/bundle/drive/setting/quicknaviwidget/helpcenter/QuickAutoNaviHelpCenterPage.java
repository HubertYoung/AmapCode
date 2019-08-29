package com.amap.bundle.drive.setting.quicknaviwidget.helpcenter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

public class QuickAutoNaviHelpCenterPage extends Ajx3Page {
    public Ajx3PagePresenter createPresenter() {
        return super.createPresenter();
    }

    public void pageCreated() {
        super.pageCreated();
    }

    public View getMapSuspendView() {
        return super.getMapSuspendView();
    }

    public void onAjxViewCreated(AmapAjxView amapAjxView) {
        super.onAjxViewCreated(amapAjxView);
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        return super.onCreateView(amapAjxView);
    }

    public AmapAjxView getAjxView() {
        return super.getAjxView();
    }

    public void loadJs() {
        super.loadJs();
    }

    public void resume() {
        super.resume();
    }

    public void pause() {
        super.pause();
    }

    public void start() {
        super.start();
    }

    public void stop() {
        super.stop();
    }

    public void destroy() {
        super.destroy();
    }

    public boolean backPressed() {
        return super.backPressed();
    }

    @Nullable
    public String getAjx3Url() {
        return super.getAjx3Url();
    }

    public String getAjxPageId() {
        return super.getAjxPageId();
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            arguments.putString("url", "path://amap_bundle_drive/src/car/vui/vui_help_center.page.js");
        } else {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "path://amap_bundle_drive/src/car/vui/vui_help_center.page.js");
            setArguments(pageBundle);
        }
        super.onCreate(context);
    }
}
