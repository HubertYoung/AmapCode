package com.autonavi.minimap.ajx3.widget.view.video.player;

public class UIStateMessage extends Message {
    private int mState;

    public UIStateMessage(int i, String str, int i2) {
        super(i, str);
        this.mState = i2;
    }

    public int getState() {
        return this.mState;
    }
}
