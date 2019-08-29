package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.amap.bundle.drive.common.basepage.AjxRouteCarNaviBasePage;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;

/* renamed from: mu reason: default package */
/* compiled from: AjxRouteCarNaviBasePresenter */
public class mu<Page extends AjxRouteCarNaviBasePage> extends Ajx3PagePresenter {
    protected Page a;

    public mu(Page page) {
        super(page);
        this.a = page;
    }

    public void onPageCreated() {
        super.onPageCreated();
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    public boolean onMapTouchEvent(MotionEvent motionEvent) {
        return super.onMapTouchEvent(motionEvent);
    }

    public boolean onMapLevelChange(boolean z) {
        return super.onMapLevelChange(z);
    }

    public void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
    }

    public void onMapSurfaceCreated() {
        super.onMapSurfaceCreated();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.a.configurationChanged(configuration);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 24 && !ro.f()) {
            rw.a(AMapAppGlobal.getApplication()).b();
            tv.a((Context) AMapAppGlobal.getApplication()).a(true);
            return true;
        } else if (i != 25 || ro.f()) {
            return super.onKeyDown(i, keyEvent);
        } else {
            rw.a(AMapAppGlobal.getApplication()).c();
            tv.a((Context) AMapAppGlobal.getApplication()).b(true);
            return true;
        }
    }
}
