package defpackage;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.Router;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;

@Router({"dialect"})
/* renamed from: dfn reason: default package */
/* compiled from: DialectRouter */
public class dfn extends esk {
    private Context a = AMapAppGlobal.getApplication();

    /* access modifiers changed from: private */
    public boolean a(boolean z) {
        if (!FileUtil.iSHasSdcardPath(this.a)) {
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.offline_data_unavalable));
            return false;
        }
        if (!z) {
            String a2 = PathManager.a().a(DirType.DRIVE_VOICE);
            if (!FileUtil.iSHasSdcardPath(this.a)) {
                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.offline_data_unavalable));
                return false;
            } else if (!FileUtil.getPathIsCanWrite(a2)) {
                if (!TextUtils.isEmpty(FileUtil.canWritePath(this.a))) {
                    dfp.a((a) new a() {
                        public final void a() {
                            dfn.this.a(true);
                        }
                    });
                    return true;
                }
                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.offline_data_unavalable));
                return false;
            }
        }
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager == null) {
            return false;
        }
        Intent intent = new Intent();
        intent.putExtra(IVoicePackageManager.ENTRANCE_VOICE_SQUARE, true);
        intent.putExtra(IVoicePackageManager.SHOW_TTS_FROM_KEY, 0);
        iVoicePackageManager.deal(AMapPageUtil.getPageContext(), intent);
        return true;
    }

    public boolean start(ese ese) {
        if (ese == null || ese.a == null) {
            return false;
        }
        String b = ese.b();
        if (TextUtils.isEmpty(b)) {
            return false;
        }
        char c = 65535;
        if (b.hashCode() == 46613902 && b.equals("/home")) {
            c = 0;
        }
        if (c != 0) {
            return false;
        }
        return a(false);
    }
}
