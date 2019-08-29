package defpackage;

import android.content.Intent;
import android.support.annotation.UiThread;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.offline.storage.StorageService.IStorageCallback;
import com.autonavi.minimap.offline.uiutils.UiController;

/* renamed from: dsj reason: default package */
/* compiled from: UiControllerStorageCallback */
public final class dsj implements IStorageCallback {
    public bid a;
    public ProgressDlg b;
    private Intent c;

    public final void onProgress(int i) {
    }

    public dsj(bid bid, Intent intent) {
        this.a = bid;
        this.c = intent;
    }

    public final void onStatusChanged(int i) {
        switch (i) {
            case 5:
                a();
                if (!(this.c == null || this.a == null)) {
                    UiController.gotoFragment(this.c, this.a);
                    break;
                }
            case 6:
                a();
                break;
        }
        b();
    }

    public final void onError(int i, String str, String str2) {
        a();
        b();
    }

    @UiThread
    private void a() {
        if (this.b != null && this.b.isShowing()) {
            this.b.dismiss();
        }
    }

    private void b() {
        this.a = null;
        this.b = null;
    }
}
