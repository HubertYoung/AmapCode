package com.autonavi.bundle.amaphome.widget.header;

import android.view.View;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.MiniSearchFramePresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.user.UserCenterWidgetPresenter;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.Map;

public class OldHomePageHeaderPresenter extends BaseMapWidgetPresenter<OldHomePageHeader> {
    public void initialize(OldHomePageHeader oldHomePageHeader) {
        super.initialize(oldHomePageHeader);
        apr apr = (apr) a.a.a(apr.class);
        if (apr != null) {
            apr.a((aps) new aps() {
                public void callback(CharSequence charSequence, int i) {
                    if (OldHomePageHeaderPresenter.this.isWidgetNotNull()) {
                        ((MiniSearchFramePresenter) ((OldHomePageHeader) OldHomePageHeaderPresenter.this.mBindWidget).getMiniSearchMapWidget().getPresenter()).updateText(charSequence, i);
                    }
                }
            });
        }
    }

    public void bindListener() {
        if (isWidgetNotNull()) {
            onBindListener(((OldHomePageHeader) this.mBindWidget).getVoiceBtn(), ((OldHomePageHeader) this.mBindWidget).getScanViewBtn(), ((OldHomePageHeader) this.mBindWidget).getContentView());
        }
    }

    public void internalClickListener(View view) {
        if (((OldHomePageHeader) this.mBindWidget).getContentView() == view) {
            ((OldHomePageHeader) this.mBindWidget).getMiniSearchMapWidget().getContentView().performClick();
            return;
        }
        if (R.id.btn_qrscan == view.getId()) {
            startQRscanPage();
        }
    }

    public void pageResume(bid bid) {
        if (isWidgetNotNull() && ((OldHomePageHeader) this.mBindWidget).getUserCenterMapWidget() != null) {
            ((UserCenterWidgetPresenter) ((OldHomePageHeader) this.mBindWidget).getUserCenterMapWidget().getPresenter()).onPageResume(bid);
        }
    }

    private void startQRscanPage() {
        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_SHARE_BIKE_ENTRY_CLICK);
        awt awt = (awt) a.a.a(awt.class);
        if (awt != null) {
            awt.a((String) "main");
        }
    }

    public void pageDestroy(bid bid) {
        ((OldHomePageHeader) this.mBindWidget).getVUIGuideTipViewLayer().b();
    }

    public void dismissVUIGuideTip() {
        ((OldHomePageHeader) this.mBindWidget).getVUIGuideTipViewLayer().a();
    }

    public void addVoiceClickLog() {
        LogManager.actionLogV2("P00001", "B003", getLogVersionStateParam());
        kd.a((String) "amap.P00001.0.B003", (Map<String, String>) null);
    }
}
