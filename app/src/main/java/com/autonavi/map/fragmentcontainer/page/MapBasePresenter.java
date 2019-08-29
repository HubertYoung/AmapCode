package com.autonavi.map.fragmentcontainer.page;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.MapBasePage.POI_DETAIL_TYPE;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import de.greenrobot.event.EventBus;
import java.util.List;

public class MapBasePresenter<Page extends MapBasePage> extends AbstractBaseMapPagePresenter<Page> {
    a baseReleatedTrafficEventHandler;
    d mTrafficEventOwner;

    public MapBasePresenter(Page page) {
        super(page);
    }

    public void onPageCreated() {
        super.onPageCreated();
        ((MapBasePage) this.mPage).onPageCreated();
        ((MapBasePage) this.mPage).initCQLayerController();
        ((MapBasePage) this.mPage).initCQLayerViews();
        if (this.mPage instanceof d) {
            this.mTrafficEventOwner = (d) this.mPage;
            this.baseReleatedTrafficEventHandler = this.mTrafficEventOwner.getReleatedTrafficEventHandler();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((MapBasePage) this.mPage).onPageConfigurationChanged(configuration);
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        ((MapBasePage) this.mPage).onPageWindowFocusChanged(z);
    }

    public void onStart() {
        ((MapBasePage) this.mPage).onPageHiddenChanged(false);
        ((MapBasePage) this.mPage).onPageStart();
        ((MapBasePage) this.mPage).onPageResume();
        if (((MapBasePage) this.mPage).getBottomTipsContainer() != null) {
            ((MapBasePage) this.mPage).getMapManager().getOverlayManager().setPoiDetailDelegate(((MapBasePage) this.mPage).getPoiDetailDelegate());
        }
        ((MapBasePage) this.mPage).resumeCQLayerController();
        if (((MapBasePage) this.mPage).getPoiDetailType() == POI_DETAIL_TYPE.CQ_VIEW) {
            ((MapBasePage) this.mPage).requestScreenOrientation(1);
        }
        super.onStart();
    }

    public void onResume() {
        ((MapBasePage) this.mPage).onPageResumePost();
        if (this.baseReleatedTrafficEventHandler != null) {
            a aVar = this.baseReleatedTrafficEventHandler;
            if (aVar.c != null) {
                brp affectAreaOverlayManager = aVar.c.getAffectAreaOverlayManager();
                if (affectAreaOverlayManager != null) {
                    affectAreaOverlayManager.a(true);
                }
            }
        }
        super.onResume();
    }

    public void onStop() {
        ((MapBasePage) this.mPage).onPageHiddenChanged(true);
        ((MapBasePage) this.mPage).onPagePause();
        ((MapBasePage) this.mPage).onPageStop();
        ((MapBasePage) this.mPage).pauseCQLayerController();
        super.onStop();
    }

    public void onPause() {
        ((MapBasePage) this.mPage).onPagePausePost();
        if (this.baseReleatedTrafficEventHandler != null) {
            a aVar = this.baseReleatedTrafficEventHandler;
            if (aVar.c != null) {
                brp affectAreaOverlayManager = aVar.c.getAffectAreaOverlayManager();
                if (affectAreaOverlayManager != null) {
                    affectAreaOverlayManager.a(false);
                }
            }
        }
        super.onPause();
    }

    public void onDestroy() {
        ((MapBasePage) this.mPage).onPageDestroyView();
        ((MapBasePage) this.mPage).onPageDestroy();
        if (((MapBasePage) this.mPage).getBottomTipsContainer() != null) {
            ((MapBasePage) this.mPage).getMapManager().getOverlayManager().setPoiDetailDelegate(null);
        }
        ((MapBasePage) this.mPage).destroyCQLayerController();
        if (this.baseReleatedTrafficEventHandler != null) {
            EventBus.getDefault().unregister(this.baseReleatedTrafficEventHandler);
        }
        AMapPageUtil.removePageStateListener((bid) this.mPage);
        super.onDestroy();
    }

    public ON_BACK_TYPE onBackPressed() {
        ON_BACK_TYPE onBackPressed = super.onBackPressed();
        if (onBackPressed != null && onBackPressed != ON_BACK_TYPE.TYPE_NORMAL) {
            return onBackPressed;
        }
        ON_BACK_TYPE onBackPressCQLayerController = ((MapBasePage) this.mPage).onBackPressCQLayerController();
        if (onBackPressCQLayerController != null && onBackPressCQLayerController != ON_BACK_TYPE.TYPE_NORMAL) {
            return onBackPressCQLayerController;
        }
        if (this.baseReleatedTrafficEventHandler == null || !this.baseReleatedTrafficEventHandler.g()) {
            return ((MapBasePage) this.mPage).onPageBackPressed();
        }
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((MapBasePage) this.mPage).onPageNewNodeFragmentBundle(pageBundle);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ((MapBasePage) this.mPage).onPageActivityResult(i, i2, intent);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return ((MapBasePage) this.mPage).onPageKeyDown(i, keyEvent) || super.onKeyDown(i, keyEvent);
    }

    public boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        return ((MapBasePage) this.mPage).onPageMapLongPress(motionEvent, geoPoint) || super.onMapLongPress(motionEvent, geoPoint);
    }

    public boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        boolean onPageMapSingleClick = ((MapBasePage) this.mPage).onPageMapSingleClick(motionEvent, geoPoint);
        boolean onMapSingleClick = super.onMapSingleClick(motionEvent, geoPoint);
        if (onPageMapSingleClick) {
            return true;
        }
        return onMapSingleClick;
    }

    public boolean onLabelClick(List<als> list) {
        boolean onPageLabelClick = ((MapBasePage) this.mPage).onPageLabelClick(list);
        boolean onLabelClick = super.onLabelClick(list);
        if (onPageLabelClick) {
            return true;
        }
        return onLabelClick;
    }

    public boolean onBlankClick() {
        boolean onPageBlankClick = ((MapBasePage) this.mPage).onPageBlankClick();
        boolean onBlankClick = super.onBlankClick();
        if (onPageBlankClick) {
            return true;
        }
        return onBlankClick;
    }

    public boolean onNoBlankClick() {
        boolean onPageNoBlankClick = ((MapBasePage) this.mPage).onPageNoBlankClick();
        boolean onNoBlankClick = super.onNoBlankClick();
        if (onPageNoBlankClick) {
            return true;
        }
        return onNoBlankClick;
    }

    public void onMapAnimationFinished(int i) {
        ((MapBasePage) this.mPage).onPageMapAnimationFinished(i);
        super.onMapAnimationFinished(i);
    }

    public boolean onPointOverlayClick(long j, int i) {
        boolean onPagePointOverlayClick = ((MapBasePage) this.mPage).onPagePointOverlayClick(j, i);
        boolean onPointOverlayClick = super.onPointOverlayClick(j, i);
        if (onPagePointOverlayClick) {
            return true;
        }
        return onPointOverlayClick;
    }

    public boolean onMapLevelChange(boolean z) {
        boolean onPageMapLevelChange = ((MapBasePage) this.mPage).onPageMapLevelChange(z);
        boolean onMapLevelChange = super.onMapLevelChange(z);
        if (onPageMapLevelChange) {
            return true;
        }
        return onMapLevelChange;
    }

    public boolean onMapMotionStop() {
        boolean onPageMapMotionStop = ((MapBasePage) this.mPage).onPageMapMotionStop();
        boolean onMapMotionStop = super.onMapMotionStop();
        if (onPageMapMotionStop) {
            return true;
        }
        return onMapMotionStop;
    }

    public boolean onLineOverlayClick(long j) {
        boolean onPageLineOverlayClick = ((MapBasePage) this.mPage).onPageLineOverlayClick(j);
        boolean onLineOverlayClick = super.onLineOverlayClick(j);
        if (onPageLineOverlayClick) {
            return true;
        }
        return onLineOverlayClick;
    }

    public boolean onFocusClear() {
        boolean onPageNonFeatureClick = ((MapBasePage) this.mPage).onPageNonFeatureClick();
        boolean onFocusClear = super.onFocusClear();
        if (onPageNonFeatureClick) {
            return true;
        }
        return onFocusClear;
    }

    public void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
        ((MapBasePage) this.mPage).onPageMapSurfaceChanged(i, i2);
        ((MapBasePage) this.mPage).onMapSurfaceChangedCQLayerController(i, i2);
        if (this.baseReleatedTrafficEventHandler != null) {
            this.baseReleatedTrafficEventHandler.d();
        }
    }

    public void onMapSurfaceCreated() {
        super.onMapSurfaceCreated();
        ((MapBasePage) this.mPage).onPageMapSurfaceCreated();
    }

    public void onMapSurfaceDestroy() {
        ((MapBasePage) this.mPage).onPageMapSurfaceDestroy();
        super.onMapSurfaceDestroy();
    }

    public void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        ((MapBasePage) this.mPage).onPageResult(i, resultType, pageBundle);
        ((MapBasePage) this.mPage).onFragmentResultCQLayerController(i, resultType, pageBundle);
    }

    public boolean onMapTouchEvent(MotionEvent motionEvent) {
        boolean onPageMapTouchEvent = ((MapBasePage) this.mPage).onPageMapTouchEvent(motionEvent);
        boolean onMapTouchEvent = super.onMapTouchEvent(motionEvent);
        if (onPageMapTouchEvent) {
            return true;
        }
        return onMapTouchEvent;
    }
}
