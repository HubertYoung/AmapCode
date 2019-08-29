package com.autonavi.minimap.offline.koala.internal.state;

import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.intf.IKoalaCustomAction;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadListener;
import com.autonavi.minimap.offline.koala.intf.IKoalaStateContext;
import com.autonavi.minimap.offline.koala.intf.IKoalaStateContext.StateChangedListener;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadEntity;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;
import java.util.ArrayList;
import java.util.List;

public class KoalaStateContext implements IKoalaStateContext {
    private List<IKoalaCustomAction> mCustomActionList = new ArrayList();
    private KoalaDownloadEntity mDownloadEntity;
    private IKoalaDownloadListener mDownloadListener;
    private KoalaState mState;
    private StateChangedListener mStateChangedListener;

    public KoalaStateContext(KoalaDownloadEntity koalaDownloadEntity, IKoalaDownloadListener iKoalaDownloadListener) {
        this.mDownloadEntity = koalaDownloadEntity;
        this.mDownloadListener = iKoalaDownloadListener;
        init();
    }

    public void setCustomActions(List<IKoalaCustomAction> list) {
        if (list != null) {
            this.mCustomActionList = new ArrayList(list);
        }
    }

    private void init() {
        switch (this.mDownloadEntity.getStatus()) {
            case PENDING:
                this.mState = new dsi(this);
                return;
            case CONNECTED:
            case DOWNLOADING:
            case BLOCK_COMPLETE:
            case PAUSE:
                this.mState = new dsh(this);
                return;
            case COMPLETE:
                this.mState = new dsd(this);
                return;
            case ERROR:
                this.mState = new dsg(this);
                return;
            default:
                this.mState = new dsi(this);
                return;
        }
    }

    public void setState(KoalaState koalaState) {
        this.mState = koalaState;
        if (this.mStateChangedListener != null) {
            this.mStateChangedListener.onStateChanged(this.mDownloadEntity.getStatus());
        }
        if (this.mDownloadEntity.getStatus() != KoalaDownloadStatus.ERROR) {
            try {
                for (IKoalaCustomAction execute : this.mCustomActionList) {
                    this.mDownloadListener.onBeforeAction(this.mDownloadEntity.getId());
                    this.mDownloadListener.onAfterAction(this.mDownloadEntity.getId(), execute.execute(this.mDownloadEntity.snapshot()));
                }
            } catch (Exception e) {
                execute(new KoalaDownloadRoughData().setId(this.mDownloadEntity.getId()).setThrowable(e));
            }
        }
    }

    public KoalaDownloadEntity getDownloadEntity() {
        return this.mDownloadEntity;
    }

    public IKoalaDownloadListener getDownloadListener() {
        return this.mDownloadListener;
    }

    public synchronized void execute(KoalaDownloadRoughData koalaDownloadRoughData) {
        this.mState.execute(koalaDownloadRoughData);
    }

    public void setStateChangedListener(StateChangedListener stateChangedListener) {
        this.mStateChangedListener = stateChangedListener;
    }
}
