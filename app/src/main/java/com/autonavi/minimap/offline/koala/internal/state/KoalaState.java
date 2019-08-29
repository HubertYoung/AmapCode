package com.autonavi.minimap.offline.koala.internal.state;

import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadListener;
import com.autonavi.minimap.offline.koala.intf.IKoalaStateContext;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadEntity;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;

public abstract class KoalaState {
    protected KoalaDownloadEntity mDownloadEntity = this.mStateContext.getDownloadEntity();
    protected IKoalaDownloadListener mDownloadListener = this.mStateContext.getDownloadListener();
    protected IKoalaStateContext mStateContext;

    public abstract void execute(KoalaDownloadRoughData koalaDownloadRoughData);

    public KoalaState(IKoalaStateContext iKoalaStateContext) {
        this.mStateContext = iKoalaStateContext;
    }
}
