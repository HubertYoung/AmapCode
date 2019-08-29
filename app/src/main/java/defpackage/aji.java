package defpackage;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;

/* renamed from: aji reason: default package */
/* compiled from: NonePresenter */
public final class aji implements IPresenter {
    public final void onActivityResult(int i, int i2, Intent intent) {
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void onDestroy() {
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        return false;
    }

    public final void onNewIntent(PageBundle pageBundle) {
    }

    public final void onPageCreated() {
    }

    public final void onPageCreated(int i, int i2) {
    }

    public final void onPause() {
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
    }

    public final void onResume() {
    }

    public final void onStart() {
    }

    public final void onStop() {
    }

    public final void onWindowFocusChanged(boolean z) {
    }

    public final ON_BACK_TYPE onBackPressed() {
        return ON_BACK_TYPE.TYPE_NORMAL;
    }
}
