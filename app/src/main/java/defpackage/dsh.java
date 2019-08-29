package defpackage;

import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.internal.state.KoalaState;
import com.autonavi.minimap.offline.koala.intf.IKoalaStateContext;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData.ActionKind;

/* renamed from: dsh reason: default package */
/* compiled from: KoalaPauseState */
public final class dsh extends KoalaState {
    public dsh(IKoalaStateContext iKoalaStateContext) {
        super(iKoalaStateContext);
    }

    public final void execute(KoalaDownloadRoughData koalaDownloadRoughData) {
        if (koalaDownloadRoughData.getThrowable() != null) {
            this.mStateContext.setState(new dsg(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else if (koalaDownloadRoughData.getActionKind() != ActionKind.RESUME) {
            if (this.mDownloadEntity.getStatus() != KoalaDownloadStatus.PAUSE) {
                this.mDownloadEntity.setStatus(KoalaDownloadStatus.PAUSE);
                this.mDownloadEntity.save();
                this.mDownloadListener.onPause(koalaDownloadRoughData.getId());
            }
        } else if (this.mDownloadEntity.isDownloadComplete()) {
            throw new IllegalStateException(KoalaUtils.formatString("download[%d] is complete, so can't be resumed!", Integer.valueOf(koalaDownloadRoughData.getId())));
        } else {
            this.mDownloadListener.onResume(koalaDownloadRoughData.getId());
            this.mStateContext.setState(new dsi(this.mStateContext));
        }
    }
}
