package defpackage;

import com.autonavi.minimap.bundle.share.entity.ShareParam;

/* renamed from: dcd reason: default package */
/* compiled from: ShareStatusCallback */
public abstract class dcd {
    public static final int SHARE_RESULT_FAILED = -1;
    public static final int SHARE_RESULT_OK = 0;

    public abstract ShareParam getShareDataByType(int i);

    public void onDismiss() {
    }

    public void onEntrySelected(int i) {
    }

    public void onFinish(int i, int i2) {
    }

    public void onShow() {
    }
}
