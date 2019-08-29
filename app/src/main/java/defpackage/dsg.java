package defpackage;

import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.internal.state.KoalaState;
import com.autonavi.minimap.offline.koala.intf.IKoalaStateContext;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData.ActionKind;

/* renamed from: dsg reason: default package */
/* compiled from: KoalaErrorState */
public final class dsg extends KoalaState {
    public dsg(IKoalaStateContext iKoalaStateContext) {
        super(iKoalaStateContext);
    }

    public final void execute(KoalaDownloadRoughData koalaDownloadRoughData) {
        if (koalaDownloadRoughData.getActionKind() == ActionKind.RESUME) {
            if (this.mDownloadEntity.isDownloadComplete()) {
                throw new IllegalStateException(KoalaUtils.formatString("download[%d] is complete, so can't be resumed!", Integer.valueOf(koalaDownloadRoughData.getId())));
            }
            this.mDownloadListener.onResume(koalaDownloadRoughData.getId());
            this.mStateContext.setState(new dsi(this.mStateContext));
        } else if (koalaDownloadRoughData.getActionKind() == ActionKind.PAUSE) {
            this.mStateContext.setState(new dsh(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else {
            if (koalaDownloadRoughData.getThrowable() != null) {
                String message = koalaDownloadRoughData.getThrowable().getMessage();
                this.mDownloadEntity.setStatus(KoalaDownloadStatus.ERROR);
                this.mDownloadEntity.setErrorCause(message);
                this.mDownloadEntity.save();
                this.mDownloadListener.onError(koalaDownloadRoughData.getId(), koalaDownloadRoughData.getThrowable());
            }
        }
    }
}
