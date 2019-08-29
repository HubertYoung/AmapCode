package defpackage;

import android.view.MotionEvent;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.search.fragment.SearchResultBasePage;
import java.util.List;

/* renamed from: cbe reason: default package */
/* compiled from: SearchResultMapBasePresenter */
public final class cbe extends ann<SearchResultBasePage> implements bgo {
    public bxk a;

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public cbe(SearchResultBasePage searchResultBasePage) {
        super(searchResultBasePage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        this.a.a(pageBundle);
    }

    public final void onStart() {
        this.a.j();
        super.onStart();
        this.a.k();
    }

    public final void onStop() {
        super.onStop();
        this.a.p();
    }

    public final void a() {
        ((SearchResultBasePage) this.mPage).onPageAppear();
        this.a.l();
    }

    public final void b() {
        ((SearchResultBasePage) this.mPage).onPageCover();
        this.a.m();
    }

    public final void c() {
        this.a.v();
    }

    public final void d() {
        this.a.w();
    }

    public final void onDestroy() {
        super.onDestroy();
        this.a.r();
    }

    public final boolean onFocusClear() {
        this.a.onNonFeatureClick();
        return super.onFocusClear();
    }

    public final ON_BACK_TYPE onBackPressed() {
        return this.a.q();
    }

    public final void onMapSurfaceCreated() {
        super.onMapSurfaceCreated();
        this.a.n();
    }

    public final void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
        this.a.o();
    }

    public final boolean onLabelClick(List<als> list) {
        super.onLabelClick(list);
        return this.a.onLabelClick(list);
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        super.onMapTouchEvent(motionEvent);
        return this.a.onMapTouchEvent(motionEvent);
    }

    public final boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        super.onMapLongPress(motionEvent, geoPoint);
        return this.a.onMapLongPress(motionEvent, geoPoint);
    }

    public final boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        super.onMapSingleClick(motionEvent, geoPoint);
        return this.a.onMapSingleClick(motionEvent, geoPoint);
    }

    public final boolean onMapLevelChange(boolean z) {
        super.onMapLevelChange(z);
        return this.a.onMapLevelChange(z);
    }

    public final boolean onMapMotionStop() {
        super.onMapMotionStop();
        return this.a.onMapMotionStop();
    }

    public final boolean onPointOverlayClick(long j, int i) {
        super.onPointOverlayClick(j, i);
        return this.a.onPointOverlayClick(j, i);
    }

    public final boolean onLineOverlayClick(long j) {
        super.onLineOverlayClick(j);
        return this.a.onLineOverlayClick(j);
    }

    public final boolean onBlankClick() {
        super.onBlankClick();
        return this.a.onBlankClick();
    }

    public final boolean onNoBlankClick() {
        super.onNoBlankClick();
        return this.a.onNoBlankClick();
    }

    public final void onMapAnimationFinished(int i) {
        super.onMapAnimationFinished(i);
        this.a.onMapAnimationFinished(i);
    }

    public final void onDoubleTap() {
        this.a.onMapTouch();
    }

    public final void onMoveBegin() {
        this.a.onMapTouch();
    }

    public final void onZoomOutTap() {
        this.a.onMapTouch();
    }

    public final void onScaleRotateBegin() {
        this.a.onMapTouch();
    }

    public final void onHoveBegin() {
        this.a.onMapTouch();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        this.a.a(i, resultType, pageBundle);
    }

    public final boolean onShowGpsTipView(int i, btt btt) {
        if (this.a.s()) {
            return super.onShowGpsTipView(i, btt);
        }
        return true;
    }
}
