package com.autonavi.bundle.uitemplate.mapwidget.widget.msg;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class MsgBoxIconPresenter implements OnClickListener {
    private MsgBoxIconView msgBoxIconView;
    private ImageView msgboxIconTips;

    public void attachView(MsgBoxIconView msgBoxIconView2) {
        this.msgBoxIconView = msgBoxIconView2;
        this.msgBoxIconView.setMsgboxIconClickListener(this);
        this.msgboxIconTips = this.msgBoxIconView.getMsgboxIconTips();
    }

    public ImageView getMsgboxIconTips() {
        return this.msgboxIconTips;
    }

    public void detachView() {
        this.msgBoxIconView.setMsgboxIconClickListener(null);
        this.msgBoxIconView = null;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.relat_container) {
            startMsgBoxPage();
        }
    }

    private void startMsgBoxPage() {
        if (this.msgboxIconTips != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("time", lf.b());
                jSONObject.put("status", this.msgboxIconTips.getVisibility() == 0 ? 1 : 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", LogConstant.MAIN_MSGBOX_MAIN_MAP_ENTRANCE, jSONObject);
            this.msgboxIconTips.setVisibility(8);
        }
        czj czj = (czj) ((IMainMapService) ank.a(IMainMapService.class)).a(czj.class);
        if (czj != null) {
            czj.b();
        }
        IMsgboxService iMsgboxService = (IMsgboxService) a.a.a(IMsgboxService.class);
        if (iMsgboxService != null) {
            iMsgboxService.jumpToMainPage();
            a.k();
        }
        MsgGroupWidgetPresenter msgGroupWidgetPresenter = (MsgGroupWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.MSG_BOX);
        if (msgGroupWidgetPresenter != null) {
            IMsgGroupEventDelegate iMsgGroupEventDelegate = (IMsgGroupEventDelegate) msgGroupWidgetPresenter.getEventDelegate();
            if (iMsgGroupEventDelegate != null) {
                iMsgGroupEventDelegate.clearMainLauncher();
            }
        }
    }
}
