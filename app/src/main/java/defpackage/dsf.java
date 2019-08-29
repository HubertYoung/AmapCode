package defpackage;

import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.internal.state.KoalaState;
import com.autonavi.minimap.offline.koala.intf.IKoalaStateContext;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadRoughData.ActionKind;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadSpecialData;

/* renamed from: dsf reason: default package */
/* compiled from: KoalaDownloadingState */
final class dsf extends KoalaState {
    public dsf(IKoalaStateContext iKoalaStateContext) {
        super(iKoalaStateContext);
    }

    public final void execute(KoalaDownloadRoughData koalaDownloadRoughData) {
        if (koalaDownloadRoughData.getThrowable() != null) {
            this.mStateContext.setState(new dsg(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else if (koalaDownloadRoughData.getActionKind() == ActionKind.PAUSE) {
            this.mStateContext.setState(new dsh(this.mStateContext));
            this.mStateContext.execute(koalaDownloadRoughData);
        } else if (this.mDownloadEntity.getStatus() == KoalaDownloadStatus.CONNECTED || this.mDownloadEntity.getStatus() == KoalaDownloadStatus.DOWNLOADING) {
            KoalaDownloadSpecialData findSpecialData = this.mDownloadEntity.findSpecialData(koalaDownloadRoughData.getUrl());
            if (findSpecialData == null) {
                throw new IllegalArgumentException(KoalaUtils.formatString("can't not find the special data with url is %s in download task!", koalaDownloadRoughData.getUrl()));
            }
            findSpecialData.setTotalBytes(koalaDownloadRoughData.getTotalBytes());
            findSpecialData.setDownloadBytes(koalaDownloadRoughData.getDownloadBytes());
            if (!findSpecialData.isDownloadComplete()) {
                if (this.mDownloadEntity.getStatus() != KoalaDownloadStatus.DOWNLOADING) {
                    this.mDownloadEntity.setStatus(KoalaDownloadStatus.DOWNLOADING);
                    this.mDownloadEntity.save();
                }
                this.mDownloadListener.onProgress(koalaDownloadRoughData.getId(), koalaDownloadRoughData.getUrl(), this.mDownloadEntity.getTotalBytes(), this.mDownloadEntity.getDownloadBytes(), findSpecialData.getTotalBytes(), findSpecialData.getDownloadBytes());
                return;
            }
            this.mDownloadEntity.save();
            this.mDownloadListener.onProgress(koalaDownloadRoughData.getId(), koalaDownloadRoughData.getUrl(), this.mDownloadEntity.getTotalBytes(), this.mDownloadEntity.getDownloadBytes(), findSpecialData.getTotalBytes(), findSpecialData.getDownloadBytes());
            this.mStateContext.setState(new dsc(this.mStateContext));
        } else {
            throw new IllegalStateException(KoalaUtils.formatString("download[%d] state error: downloading state only from connected state or downloading state!", Integer.valueOf(koalaDownloadRoughData.getId())));
        }
    }
}
