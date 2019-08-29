package com.autonavi.bundle.amaphome.msg;

import com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.msg.MsgGroupWidgetPresenter;
import com.autonavi.minimap.bundle.msgbox.dispatcher.AbsMsgBoxDispatcher;

public interface IMapHomeMsgDispatchService extends czi {

    public static class Sub {
        private static IMapHomeMsgDispatchService instance = ((IMapHomeMsgDispatchService) ank.a(IMapHomeMsgDispatchService.class));

        public static IMapHomeMsgDispatchService getInstance() {
            return instance;
        }
    }

    void bindUi(MsgGroupWidgetPresenter msgGroupWidgetPresenter, OperateActivityWidgetPresenter operateActivityWidgetPresenter);

    AbsMsgBoxDispatcher getMsgBoxDispatcher();
}
