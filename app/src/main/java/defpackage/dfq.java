package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/* renamed from: dfq reason: default package */
/* compiled from: PPApkDownloadCallback */
public final class dfq implements bjf {
    private WeakReference<OfflineNaviTtsFragment> a;
    private dgl b;

    public dfq(OfflineNaviTtsFragment offlineNaviTtsFragment, dgl dgl) {
        this.a = new WeakReference<>(offlineNaviTtsFragment);
        this.b = dgl;
    }

    public final void onStart(long j, Map<String, List<String>> map, int i) {
        OfflineNaviTtsFragment offlineNaviTtsFragment = (OfflineNaviTtsFragment) this.a.get();
        if (offlineNaviTtsFragment != null && this.b != null) {
            String str = this.b.a.l;
            if (TextUtils.isEmpty(str)) {
                str = this.b.a.c;
            }
            offlineNaviTtsFragment.showPPHelperApkDownloadDialog(str);
        }
    }

    public final void onProgressUpdate(long j, long j2) {
        OfflineNaviTtsFragment offlineNaviTtsFragment = (OfflineNaviTtsFragment) this.a.get();
        if (offlineNaviTtsFragment != null && j2 != 0) {
            offlineNaviTtsFragment.updatePPHelperApkDownloadDialogProgress((int) ((j * 100) / j2));
        }
    }

    public final void onFinish(bpk bpk) {
        OfflineNaviTtsFragment offlineNaviTtsFragment = (OfflineNaviTtsFragment) this.a.get();
        if (offlineNaviTtsFragment != null) {
            offlineNaviTtsFragment.closePPHelperApkDownloadDialog(false, this.b);
        }
    }

    public final void onError(int i, int i2) {
        OfflineNaviTtsFragment offlineNaviTtsFragment = (OfflineNaviTtsFragment) this.a.get();
        if (offlineNaviTtsFragment != null) {
            offlineNaviTtsFragment.closePPHelperApkDownloadDialog(true, this.b);
        }
    }
}
