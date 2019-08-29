package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import com.autonavi.ae.ajx.map.AjxMapViewEventReceiver;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;
import com.autonavi.map.fragmentcontainer.page.IAMapHomePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.LinkedHashSet;
import java.util.Set;

@BundleInterface(awb.class)
/* renamed from: xk reason: default package */
/* compiled from: MapEventServiceImpl */
public class xk extends esi implements awb, bie {
    private xj a;
    private xl b;
    private int c;

    public final void a(Context context, int i) {
        this.a = new xj(i);
        this.b = new xl(i, context);
        this.c = i;
        akq.b().a((amk) this);
        akq.b().a((aml) this);
    }

    public final void a(int i, MainMapEventListener mainMapEventListener) {
        xj xjVar = this.a;
        if (i > 2 || i < 0) {
            i = 1;
        }
        Set set = xjVar.a.get(i);
        if (set == null) {
            set = new LinkedHashSet();
            xjVar.a.put(i, set);
        }
        set.add(mainMapEventListener);
    }

    public final void a(MainMapEventListener mainMapEventListener) {
        xj xjVar = this.a;
        Set set = xjVar.a.get(1);
        if (set == null) {
            set = new LinkedHashSet();
            xjVar.a.put(1, set);
        }
        set.add(mainMapEventListener);
    }

    public final void b(MainMapEventListener mainMapEventListener) {
        xj xjVar = this.a;
        for (int i = 0; i < xjVar.a.size(); i++) {
            xjVar.a.get(xjVar.a.keyAt(i)).remove(mainMapEventListener);
        }
    }

    public final void a() {
        akq.b().b((amk) this);
        akq.b().b((aml) this);
        xl.a(this.c).b().setMapGestureListener(null);
    }

    public final void a(btw btw) {
        this.b.a.remove(btw);
    }

    public final void b(btw btw) {
        this.b.a.add(btw);
    }

    public final void a(bts bts) {
        this.b.b.add(bts);
    }

    public final void b(bts bts) {
        this.b.b.remove(bts);
    }

    public final void a(int i, btv btv) {
        xl xlVar = this.b;
        if (btv == null) {
            xlVar.c.remove(Integer.valueOf(i));
        } else {
            xlVar.c.put(Integer.valueOf(i), btv);
        }
    }

    public final void a(@Nullable amj amj, @NonNull aky aky) {
        aky.setMapGestureListener(this);
        this.b.d = amj;
    }

    public final void b() {
        for (btw onGpsBtnClick : this.b.a) {
            onGpsBtnClick.onGpsBtnClick();
        }
    }

    public void onMapTipInfo(int i, String str) {
        if (!c()) {
            this.b.onMapTipInfo(i, str);
        }
        this.a.onMapTipInfo(i, str);
    }

    public void onMapTipClear(int i) {
        if (!c()) {
            this.b.onMapTipClear(i);
        }
        this.a.onMapTipClear(i);
    }

    public void onUserMapTouchEvent(int i, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 1) {
            AjxMapViewEventReceiver.onMapTouchEventS(i, motionEvent.getX(), motionEvent.getY(), motionEvent.getAction());
        }
        if (!c()) {
            this.b.onUserMapTouchEvent(i, motionEvent);
        }
        this.a.onUserMapTouchEvent(i, motionEvent);
    }

    public void onShowPress(int i, MotionEvent motionEvent) {
        if (!c()) {
            this.b.onShowPress(i, motionEvent);
        }
        this.a.onShowPress(i, motionEvent);
    }

    public void onLongPress(int i, MotionEvent motionEvent) {
        AjxMapViewEventReceiver.onLongPressS(i, motionEvent.getX(), motionEvent.getY());
        if (!c()) {
            this.b.onLongPress(i, motionEvent);
        }
        this.a.onLongPress(i, motionEvent);
    }

    public void onDoubleTap(int i, MotionEvent motionEvent) {
        if (!c()) {
            this.b.onDoubleTap(i, motionEvent);
        }
        this.a.onDoubleTap(i, motionEvent);
    }

    public void onMoveBegin(int i, MotionEvent motionEvent) {
        AjxMapViewEventReceiver.onEngineActionGestureS(i, 3, 1, false);
        if (!c()) {
            this.b.onMoveBegin(i, motionEvent);
        }
        this.a.onMoveBegin(i, motionEvent);
    }

    public void onZoomOutTap(int i, MotionEvent motionEvent) {
        if (!c()) {
            this.b.onZoomOutTap(i, motionEvent);
        }
        this.a.onZoomOutTap(i, motionEvent);
    }

    public void onScaleRotateBegin(int i, MotionEvent motionEvent) {
        if (!c()) {
            this.b.onScaleRotateBegin(i, motionEvent);
        }
        this.a.onScaleRotateBegin(i, motionEvent);
    }

    public void onHoveBegin(int i, MotionEvent motionEvent) {
        if (!c()) {
            this.b.onHoveBegin(i, motionEvent);
        }
        this.a.onHoveBegin(i, motionEvent);
    }

    public boolean onSingleTapUp(int i, MotionEvent motionEvent) {
        AjxMapViewEventReceiver.onSingleTapUpS(i, motionEvent.getX(), motionEvent.getY());
        boolean onSingleTapUp = !c() ? this.b.onSingleTapUp(i, motionEvent) : false;
        if (this.a.onSingleTapUp(i, motionEvent) || onSingleTapUp) {
            return true;
        }
        return false;
    }

    public boolean onFling(int i, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        boolean onFling = !c() ? this.b.onFling(i, motionEvent, motionEvent2, f, f2) : false;
        if (this.b.onFling(i, motionEvent, motionEvent2, f, f2) || onFling) {
            return true;
        }
        return false;
    }

    public void onMapSizeChange(int i) {
        if (!c()) {
            this.b.onMapSizeChange(i);
        }
        this.a.onMapSizeChange(i);
    }

    public void onMapLevelChange(int i, boolean z) {
        AjxMapViewEventReceiver.onMapLevelChangeS(i, z);
        if (!c()) {
            this.b.onMapLevelChange(i, z);
        }
        this.a.onMapLevelChange(i, z);
    }

    public void onEngineVisible(int i, boolean z) {
        if (!c()) {
            this.b.onEngineVisible(i, z);
        }
        this.a.onEngineVisible(i, z);
    }

    public void onMotionFinished(int i, int i2) {
        AjxMapViewEventReceiver.onMotionFinishedS(i, i2);
        this.b.onMotionFinished(i, i2);
        this.a.onMotionFinished(i, i2);
    }

    public void onHorizontalMove(int i, float f) {
        if (!c()) {
            this.b.onHorizontalMove(i, f);
        }
        this.a.onHorizontalMove(i, f);
    }

    public void onHorizontalMoveEnd(int i) {
        if (!c()) {
            this.b.onHorizontalMoveEnd(i);
        }
        this.a.onHorizontalMoveEnd(i);
    }

    public void onOfflineMap(int i, String str, int i2) {
        if (!c()) {
            this.b.onOfflineMap(i, str, i2);
        }
        this.a.onOfflineMap(i, str, i2);
    }

    public void onRealCityAnimateFinish(int i) {
        if (!c()) {
            this.b.onRealCityAnimateFinish(i);
        }
        this.a.onRealCityAnimateFinish(i);
    }

    public void beforeDrawFrame(int i, GLMapState gLMapState) {
        if (!c()) {
            this.b.beforeDrawFrame(i, gLMapState);
        }
        this.a.beforeDrawFrame(i, gLMapState);
    }

    public void afterDrawFrame(int i, GLMapState gLMapState) {
        if (!c()) {
            this.b.afterDrawFrame(i, gLMapState);
        }
        this.a.afterDrawFrame(i, gLMapState);
    }

    public void onMapAnimationFinished(int i, int i2) {
        if (!c()) {
            this.b.onMapAnimationFinished(i, i2);
        }
        this.a.onMapAnimationFinished(i, i2);
    }

    public void onMapAnimationFinished(int i, aln aln) {
        AjxMapViewEventReceiver.onMapAnimationFinishedS(i, aln.a, aln.b, aln.c);
        if (!c()) {
            this.b.onMapAnimationFinished(i, aln);
        }
        this.a.onMapAnimationFinished(i, aln);
    }

    public void onSelectSubWayActive(int i, byte[] bArr) {
        if (!c()) {
            this.b.onSelectSubWayActive(i, bArr);
        }
        this.a.onSelectSubWayActive(i, bArr);
    }

    public void onMapRenderCompleted(int i) {
        if (!c()) {
            this.b.onMapRenderCompleted(i);
        }
        this.a.onMapRenderCompleted(i);
    }

    public void onScreenShotFinished(int i, long j) {
        if (!c()) {
            this.b.onScreenShotFinished(i, j);
        }
        this.a.onScreenShotFinished(i, j);
    }

    public void onScreenShotFinished(int i, Bitmap bitmap) {
        if (!c()) {
            this.b.onScreenShotFinished(i, bitmap);
        }
        this.a.onScreenShotFinished(i, bitmap);
    }

    public void onScreenShotFinished(int i, String str) {
        if (!c()) {
            this.b.onScreenShotFinished(i, str);
        }
        this.a.onScreenShotFinished(i, str);
    }

    public void onPointOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits) {
        this.b.onPointOverlayClick(i, gLAmapFocusHits);
        this.a.onPointOverlayClick(i, gLAmapFocusHits);
    }

    public void onLineOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits) {
        this.b.onLineOverlayClick(i, gLAmapFocusHits);
        this.a.onLineOverlayClick(i, gLAmapFocusHits);
    }

    public void onNoFeatureClick(int i) {
        if (!c()) {
            this.b.onNoFeatureClick(i);
        }
        this.a.onNoFeatureClick(i);
    }

    public boolean onBlankClick(int i) {
        boolean onBlankClick = !c() ? this.b.onBlankClick(i) : false;
        if (this.a.onBlankClick(i) || onBlankClick) {
            return true;
        }
        return false;
    }

    public boolean onNoBlankClick(int i) {
        boolean onNoBlankClick = !c() ? this.b.onNoBlankClick(i) : false;
        if (this.a.onNoBlankClick(i) || onNoBlankClick) {
            return true;
        }
        return false;
    }

    private static boolean c() {
        return AMapPageUtil.getPageContext() instanceof IAMapHomePage;
    }

    public boolean onClick(int i, float f, float f2) {
        AjxMapViewEventReceiver.onPointOverlayClickS(i, f, f2);
        return this.b.onClick(i, f, f2);
    }

    public boolean onDoubleClick(int i, float f, float f2) {
        return this.b.onDoubleClick(i, f, f2);
    }

    public boolean onLongPress(int i, float f, float f2) {
        return this.b.onLongPress(i, f, f2);
    }

    public boolean onMontionStart(int i, float f, float f2) {
        return this.b.onMontionStart(i, f, f2);
    }

    public boolean onMontionFinish(int i) {
        return this.b.onMontionFinish(i);
    }

    public boolean onTouchEvent(int i, MotionEvent motionEvent) {
        return this.b.onTouchEvent(i, motionEvent);
    }

    public void onEngineActionGesture(int i, alg alg) {
        int i2;
        switch (alg.a) {
            case 1:
            case 3:
                i2 = 1;
                break;
            case 2:
            case 4:
                i2 = 2;
                break;
            case 5:
                i2 = 3;
                break;
            default:
                i2 = -1;
                break;
        }
        AjxMapViewEventReceiver.onEngineActionGestureS(i, i2, 3, alg.c);
        if (!c()) {
            this.b.onEngineActionGesture(i, alg);
        }
        this.a.onEngineActionGesture(i, alg);
    }
}
