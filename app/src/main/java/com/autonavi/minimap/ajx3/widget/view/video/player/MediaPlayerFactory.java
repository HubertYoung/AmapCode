package com.autonavi.minimap.ajx3.widget.view.video.player;

public class MediaPlayerFactory implements IPlayerFactory {
    public AbstractPlayer create() {
        return new VideoMediaPlayer();
    }
}
