package defpackage;

import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.internal.state.KoalaState;
import com.autonavi.minimap.offline.koala.intf.IKoalaStateContext;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData.ActionKind;

/* renamed from: dsi reason: default package */
/* compiled from: KoalaPendingState */
public final class dsi extends KoalaState {
    public dsi(IKoalaStateContext iKoalaStateContext) {
        super(iKoalaStateContext);
    }

    public final void execute(KoalaDownloadRoughData koalaDownloadRoughData) {
        if (koalaDownloadRoughData.getThrowable() != null) {
            this.mStateContext.setState(new dsg(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else if (koalaDownloadRoughData.getActionKind() == ActionKind.PAUSE) {
            this.mStateContext.setState(new dsh(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else {
            if (koalaDownloadRoughData.getActionKind() == ActionKind.START && this.mDownloadEntity.getTime() <= 0) {
                this.mDownloadEntity.setTime(koalaDownloadRoughData.getActionTime());
            }
            this.mDownloadEntity.setStatus(KoalaDownloadStatus.PENDING);
            this.mDownloadEntity.save();
            this.mDownloadListener.onPending(koalaDownloadRoughData.getId());
            this.mStateContext.setState(new dse(this.mStateContext));
        }
    }
}
