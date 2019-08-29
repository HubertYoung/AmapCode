package defpackage;

import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.internal.state.KoalaState;
import com.autonavi.minimap.offline.koala.intf.IKoalaStateContext;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData.ActionKind;

/* renamed from: dsd reason: default package */
/* compiled from: KoalaCompleteState */
public final class dsd extends KoalaState {
    public dsd(IKoalaStateContext iKoalaStateContext) {
        super(iKoalaStateContext);
    }

    public final void execute(KoalaDownloadRoughData koalaDownloadRoughData) {
        if (koalaDownloadRoughData.getThrowable() != null) {
            this.mStateContext.setState(new dsg(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else if (koalaDownloadRoughData.getActionKind() == ActionKind.PAUSE) {
            this.mStateContext.setState(new dsh(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else if (this.mDownloadEntity.getStatus() != KoalaDownloadStatus.BLOCK_COMPLETE) {
            throw new IllegalStateException(KoalaUtils.formatString("download[%d] state error: complete state only from block complete state!", Integer.valueOf(koalaDownloadRoughData.getId())));
        } else {
            this.mDownloadEntity.setStatus(KoalaDownloadStatus.COMPLETE);
            this.mDownloadEntity.save();
            this.mDownloadListener.onComplete(koalaDownloadRoughData.getId());
        }
    }
}
