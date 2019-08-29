package com.autonavi.bundle.uitemplate.mapwidget.widget.msg;

import com.autonavi.bundle.uitemplate.mapwidget.inter.IEventDelegate;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;

public interface IMsgGroupEventDelegate extends IEventDelegate {
    void clearMainLauncher();

    void executeAction(AmapMessage amapMessage);

    String getCurHourInterval();

    void setCurDispBubbleMsg();

    void setRead(AmapMessage amapMessage);

    void setShowOnMap(AmapMessage amapMessage);

    void setSubRead(String str);
}
