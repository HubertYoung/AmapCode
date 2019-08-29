package com.autonavi.minimap.bundle.msgbox.dispatcher;

import android.os.Handler;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import java.util.List;

public interface IMsgDispatcherHost {
    void clearTipTimer();

    Handler getHandler();

    int getMarqueeIndex();

    void marqueeMessage(List<AmapMessage> list);

    void resumeTipTimer();

    void setMarqueeIndex(int i);

    void stopTipTimer();

    void tickMessage(AmapMessage amapMessage);
}
