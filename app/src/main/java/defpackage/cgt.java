package defpackage;

import android.os.Handler;
import android.os.Looper;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.mine.page.setting.sysmapset.page.SysMapSettingPage;

/* renamed from: cgt reason: default package */
/* compiled from: SysMapSettingPresenter */
public final class cgt extends AbstractBasePresenter<SysMapSettingPage> {
    public cgt(SysMapSettingPage sysMapSettingPage) {
        super(sysMapSettingPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        bim.aa().z();
        bim.aa().a((biw) new biw() {
            public final void a() {
                cgt.this.a();
            }
        });
        a();
    }

    public final void onStop() {
        ((SysMapSettingPage) this.mPage).refreshUI_onPause();
        super.onStop();
    }

    /* access modifiers changed from: private */
    public void a() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                ((SysMapSettingPage) cgt.this.mPage).updateUIWithCloudData();
            }
        });
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((SysMapSettingPage) this.mPage).isClearFrequentLocationShown()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }
}
