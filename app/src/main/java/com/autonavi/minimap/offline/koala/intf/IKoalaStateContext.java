package com.autonavi.minimap.offline.koala.intf;

import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.internal.state.KoalaState;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadEntity;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;

public interface IKoalaStateContext {

    public interface StateChangedListener {
        void onStateChanged(KoalaDownloadStatus koalaDownloadStatus);
    }

    void execute(KoalaDownloadRoughData koalaDownloadRoughData);

    KoalaDownloadEntity getDownloadEntity();

    IKoalaDownloadListener getDownloadListener();

    void setState(KoalaState koalaState);

    void setStateChangedListener(StateChangedListener stateChangedListener);
}
