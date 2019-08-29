package defpackage;

import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.internal.state.KoalaState;
import com.autonavi.minimap.offline.koala.intf.IKoalaStateContext;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData.ActionKind;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadSpecialData;

/* renamed from: dsc reason: default package */
/* compiled from: KoalaBlockCompleteState */
final class dsc extends KoalaState {
    public dsc(IKoalaStateContext iKoalaStateContext) {
        super(iKoalaStateContext);
    }

    public final void execute(KoalaDownloadRoughData koalaDownloadRoughData) {
        if (koalaDownloadRoughData.getThrowable() != null) {
            this.mStateContext.setState(new dsg(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else if (koalaDownloadRoughData.getActionKind() == ActionKind.PAUSE) {
            this.mStateContext.setState(new dsh(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else if (this.mDownloadEntity.getStatus() != KoalaDownloadStatus.DOWNLOADING) {
            throw new IllegalStateException(KoalaUtils.formatString("download[%d] state error: block complete state only from downloading state!", Integer.valueOf(koalaDownloadRoughData.getId())));
        } else {
            KoalaDownloadSpecialData findSpecialData = this.mDownloadEntity.findSpecialData(koalaDownloadRoughData.getUrl());
            if (findSpecialData == null) {
                throw new IllegalArgumentException(KoalaUtils.formatString("can't not find the special data with url is %s in download task!", koalaDownloadRoughData.getUrl()));
            } else if (findSpecialData.getDownloadBytes() != findSpecialData.getTotalBytes()) {
                throw new IllegalArgumentException(KoalaUtils.formatString("download[%d] state error: url[%s] download bytes not equal total bytes!", Integer.valueOf(koalaDownloadRoughData.getId()), koalaDownloadRoughData.getUrl()));
            } else {
                this.mDownloadEntity.setStatus(KoalaDownloadStatus.BLOCK_COMPLETE);
                this.mDownloadEntity.save();
                this.mDownloadListener.onBlockComplete(koalaDownloadRoughData.getId(), koalaDownloadRoughData.getUrl());
                if (this.mDownloadEntity.isDownloadComplete()) {
                    this.mStateContext.setState(new dsd(this.mStateContext));
                    this.mStateContext.execute(koalaDownloadRoughData);
                    return;
                }
                this.mStateContext.setState(new dsi(this.mStateContext));
            }
        }
    }
}
