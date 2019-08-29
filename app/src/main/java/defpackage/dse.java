package defpackage;

import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.internal.state.KoalaState;
import com.autonavi.minimap.offline.koala.intf.IKoalaStateContext;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData.ActionKind;

/* renamed from: dse reason: default package */
/* compiled from: KoalaConnectedState */
final class dse extends KoalaState {
    public dse(IKoalaStateContext iKoalaStateContext) {
        super(iKoalaStateContext);
    }

    public final void execute(KoalaDownloadRoughData koalaDownloadRoughData) {
        if (koalaDownloadRoughData.getThrowable() != null) {
            this.mStateContext.setState(new dsg(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else if (koalaDownloadRoughData.getActionKind() == ActionKind.PAUSE) {
            this.mStateContext.setState(new dsh(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else if (this.mDownloadEntity.getStatus() != KoalaDownloadStatus.PENDING) {
            throw new IllegalStateException(KoalaUtils.formatString("download[%d] state error: connected state only from pending state!", Integer.valueOf(koalaDownloadRoughData.getId())));
        } else {
            this.mDownloadEntity.setStatus(KoalaDownloadStatus.CONNECTED);
            this.mDownloadEntity.save();
            this.mDownloadListener.onConnected(koalaDownloadRoughData.getId(), koalaDownloadRoughData.getUrl());
            this.mStateContext.setState(new dsf(this.mStateContext));
        }
    }
}
