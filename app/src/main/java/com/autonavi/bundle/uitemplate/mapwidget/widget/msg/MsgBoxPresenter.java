package com.autonavi.bundle.uitemplate.mapwidget.widget.msg;

import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;

public class MsgBoxPresenter {
    private IMsgBoxView mMsgBoxWidget;

    public void detachView() {
    }

    public void update(AmapMessage amapMessage) {
        this.mMsgBoxWidget.update(amapMessage);
    }

    public void attachView(IMsgBoxView iMsgBoxView) {
        this.mMsgBoxWidget = iMsgBoxView;
    }
}
