package com.autonavi.bundle.buscard.page;

import android.content.Context;
import android.view.View;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

public class BusCardSimplePage extends Ajx3Page {
    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
    }

    public void onJsBack(Object obj, String str) {
        super.onJsBack(obj, str);
    }

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

    public void result(int i, ResultType resultType, PageBundle pageBundle) {
        super.result(i, resultType, pageBundle);
    }

    public boolean backPressed() {
        return super.backPressed();
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            arguments.putString("url", "path://amap_bundle_buscard/src/BusCardSimple.page.js");
        } else {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "path://amap_bundle_buscard/src/BusCardSimple.page.js");
            setArguments(pageBundle);
        }
        super.onCreate(context);
    }
}
