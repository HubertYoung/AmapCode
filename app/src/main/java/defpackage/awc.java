package defpackage;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;
import java.util.ArrayList;
import java.util.List;

/* renamed from: awc reason: default package */
/* compiled from: MainMapEventAdapter */
public class awc implements MainMapEventListener {
    public static final String TAG = "awc";

    public void afterDrawFrame(GLMapState gLMapState) {
    }

    public void beforeDrawFrame(GLMapState gLMapState) {
    }

    public boolean onBlankClick() {
        return false;
    }

    public void onDoubleTap(MotionEvent motionEvent) {
    }

    public void onEngineActionGesture(alg alg) {
    }

    public void onEngineVisible(int i, boolean z) {
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onHorizontalMove(float f) {
    }

    public void onHorizontalMoveEnd() {
    }

    public void onHoveBegin(MotionEvent motionEvent) {
    }

    public void onLineOverlayClick(GLAmapFocusHits gLAmapFocusHits) {
    }

    public void onLongPress(MotionEvent motionEvent) {
    }

    public void onMapAnimationFinished(int i) {
    }

    public void onMapAnimationFinished(aln aln) {
    }

    public void onMapLevelChange(boolean z) {
    }

    public void onMapRenderCompleted() {
    }

    public void onMapSizeChange() {
    }

    public void onMapTipClear() {
    }

    public void onMapTipInfo(String str) {
    }

    public void onMotionFinished(int i) {
    }

    public void onMoveBegin(MotionEvent motionEvent) {
    }

    public boolean onNoBlankClick() {
        return false;
    }

    public void onNoFeatureClick() {
    }

    public void onOfflineMap(String str, int i) {
    }

    public void onPointOverlayClick(GLAmapFocusHits gLAmapFocusHits) {
    }

    public void onRealCityAnimateFinish() {
    }

    public void onScaleRotateBegin(MotionEvent motionEvent) {
    }

    public void onScreenShotFinished(int i, long j) {
    }

    public void onScreenShotFinished(int i, Bitmap bitmap) {
    }

    public void onScreenShotFinished(int i, String str) {
    }

    public void onSelectSubWayActive(byte[] bArr) {
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public void onUserMapTouchEvent(MotionEvent motionEvent) {
    }

    public void onZoomOutTap(MotionEvent motionEvent) {
    }

    public static List<Long> parseSubWayActiveIds(byte[] bArr) {
        int i;
        ArrayList arrayList = new ArrayList();
        if (bArr != null && bArr.length > 0) {
            int i2 = 0;
            try {
                if (4 <= bArr.length) {
                    i = ahg.a(bArr, 0);
                    i2 = 4;
                } else {
                    i = 0;
                }
                while (true) {
                    int i3 = i2 + 8;
                    try {
                        if (i3 > bArr.length) {
                            break;
                        }
                        arrayList.add(Long.valueOf((255 & ((long) bArr[i2])) | (65280 & (((long) bArr[i2 + 1]) << 8)) | (16711680 & (((long) bArr[i2 + 2]) << 16)) | (4278190080L & (((long) bArr[i2 + 3]) << 24)) | (1095216660480L & (((long) bArr[i2 + 4]) << 32)) | (280375465082880L & (((long) bArr[i2 + 5]) << 40)) | (71776119061217280L & (((long) bArr[i2 + 6]) << 48)) | (-72057594037927936L & (((long) bArr[i2 + 7]) << 56))));
                        i2 = i3;
                    } catch (Throwable unused) {
                    }
                }
            } catch (Throwable unused2) {
                i = 0;
            }
            if (i != arrayList.size()) {
                arrayList.clear();
            }
        }
        return arrayList;
    }
}
