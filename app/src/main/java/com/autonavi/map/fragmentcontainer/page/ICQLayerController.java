package com.autonavi.map.fragmentcontainer.page;

import android.view.View;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface ICQLayerController {
    public static final boolean IS_TESTIING_CQ = false;

    public enum DetailLayerState {
        EXPAND,
        COLLAPSED
    }

    void collapseCQLayer();

    boolean dismissCQLayer(boolean z);

    void doDispatchSlidedEvent();

    void expendCQLayer();

    int getCQTopOffset();

    POI getCurPoi();

    DetailLayerState getDetailLayerState();

    View getMapMiddleView();

    View getMapPointTipView();

    void hideCQLayer();

    void init(BaseCQLayerOwner baseCQLayerOwner);

    boolean isFavWhenShow();

    boolean isLayerShowing();

    boolean isMapMiddleViewHidden();

    boolean isMapPointRequestOutter();

    boolean isScenic();

    boolean isShowing();

    ON_BACK_TYPE onBackPressed();

    void onDestroy();

    void onFragmentResult(int i, ResultType resultType, PageBundle pageBundle);

    void onMapPointRequestReturnNull();

    void onMapSurfaceChanged(int i, int i2);

    void onPageAppear();

    void onPageCover();

    void onPause();

    void onResume();

    void onTipRefreshCallback(POI poi);

    void recoverFocusCenter();

    void resetHeaderTranslation();

    void setLayerVisibility(boolean z);

    void showCQLayer(POI poi, Object obj, boolean z);

    void showLayer();
}
