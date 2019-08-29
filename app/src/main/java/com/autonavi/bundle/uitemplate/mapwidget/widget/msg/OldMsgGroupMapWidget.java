package com.autonavi.bundle.uitemplate.mapwidget.widget.msg;

import android.content.Context;
import android.view.View;
import com.autonavi.minimap.R;

public class OldMsgGroupMapWidget extends MsgGroupMapWidget {
    /* access modifiers changed from: protected */
    public int getTipFrameLeftMargin() {
        return 0;
    }

    public OldMsgGroupMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        return loadLayoutRes(context, R.layout.old_map_widget_msg_group);
    }

    /* access modifiers changed from: protected */
    public int getTipTextViewLeftMargin() {
        return bet.a(getContext(), 20);
    }

    public Class<MsgGroupWidgetPresenter> getPresenterClass() {
        return MsgGroupWidgetPresenter.class;
    }
}
