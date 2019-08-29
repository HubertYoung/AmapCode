package com.autonavi.bundle.uitemplate.mapwidget.widget.msg;

import android.view.View.OnClickListener;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;

public interface IMsgBoxView extends brh {
    void setAlpha(float f);

    void setClearViewOnClickListener(OnClickListener onClickListener);

    void setOnClickListener(OnClickListener onClickListener);

    void setVisibility(int i);

    void update(AmapMessage amapMessage);
}
