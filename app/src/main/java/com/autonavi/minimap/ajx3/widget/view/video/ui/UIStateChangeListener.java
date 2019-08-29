package com.autonavi.minimap.ajx3.widget.view.video.ui;

public interface UIStateChangeListener {
    void onChangeUIBufferCompletedState(int i);

    void onChangeUIBufferingState(int i);

    void onChangeUICompleteState(int i);

    void onChangeUIErrorState(int i);

    void onChangeUIFirstFrameRendering(int i);

    void onChangeUILoadingState(int i);

    void onChangeUINormalState(int i);

    void onChangeUIPauseState(int i);

    void onChangeUIPlayingState(int i);
}
