package com.autonavi.bundle.buscard.page;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import com.ali.auth.third.core.context.KernelContext;
import com.alipay.mobile.tinyappcustom.api.MiniProgramAuthService;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

public class BusCardFullPage extends Ajx3Page {
    private BroadcastReceiver a = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), MiniProgramAuthService.LOGIN_TOKEN_INVALID)) {
                asl.a().a(7, "", "");
            }
        }
    };

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
        LocalBroadcastManager.getInstance(KernelContext.getApplicationContext()).unregisterReceiver(this.a);
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
            arguments.putString("url", "path://amap_bundle_buscard/src/BusCardFull.page.js");
        } else {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "path://amap_bundle_buscard/src/BusCardFull.page.js");
            setArguments(pageBundle);
        }
        super.onCreate(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MiniProgramAuthService.LOGIN_TOKEN_INVALID);
        LocalBroadcastManager.getInstance(KernelContext.getApplicationContext()).registerReceiver(this.a, intentFilter);
    }
}
