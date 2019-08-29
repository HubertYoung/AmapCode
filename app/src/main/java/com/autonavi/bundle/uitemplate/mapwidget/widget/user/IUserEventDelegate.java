package com.autonavi.bundle.uitemplate.mapwidget.widget.user;

import com.autonavi.bundle.uitemplate.mapwidget.inter.IEventDelegate;

public interface IUserEventDelegate extends IEventDelegate {
    boolean getLaboratoryHeadRedShowFlag();

    String getPortraitUrl();

    String getUserLevel();

    void setLaboratoryHeadRedShowFlag(boolean z);

    void setMainHeaderRedFlag(boolean z);
}
