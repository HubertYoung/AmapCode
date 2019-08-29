package com.autonavi.minimap.ajx3.widget.view.video.player;

public class Message {
    private int mHash;
    private String mVideoUrl;

    public Message(int i, String str) {
        this.mHash = i;
        this.mVideoUrl = str;
    }

    public int getHash() {
        return this.mHash;
    }

    public String getVideoUrl() {
        return this.mVideoUrl;
    }
}
