package com.autonavi.minimap.search.inter;

import android.view.View;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.search.fragment.BaseCQDetailOwner;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface ICQDetailPageController {
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

    String getState();

    void hideCQLayer();

    void init(BaseCQDetailOwner baseCQDetailOwner);

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

    void onResume(boolean z);

    void onTipRefreshCallback(POI poi);

    void recoverFocusCenter();

    void resetHeaderTranslation();

    void setLayerVisibility(boolean z);

    void showCQLayer(POI poi, Object obj, boolean z, boolean z2);

    void showLayer();
}
