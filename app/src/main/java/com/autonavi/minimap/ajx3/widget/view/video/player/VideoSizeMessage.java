package com.autonavi.minimap.ajx3.widget.view.video.player;

public class VideoSizeMessage extends Message {
    private int height;
    private int width;

    public VideoSizeMessage(int i, String str, int i2, int i3) {
        super(i, str);
        this.width = i2;
        this.height = i3;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
