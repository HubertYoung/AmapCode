package com.autonavi.minimap.ajx3.widget.view.video.player;

public class DurationMessage extends Message {
    private long mDuration;

    public DurationMessage(int i, String str, long j) {
        super(i, str);
        this.mDuration = j;
    }

    public long getDuration() {
        return this.mDuration;
    }
}
