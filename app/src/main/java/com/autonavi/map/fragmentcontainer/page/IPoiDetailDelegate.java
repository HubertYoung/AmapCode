package com.autonavi.map.fragmentcontainer.page;

import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.dialog.TipContainer.OnTipChangedListener;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;

public interface IPoiDetailDelegate {
    void addOnTipChangedListener(OnTipChangedListener onTipChangedListener);

    void dimissFooter();

    void drawOverlay(POI poi);

    ely getIPoiTipView();

    MapBasePage getPage();

    AbstractGpsTipView getgpsTipView();

    AbstractPoiDetailView getpoiDetailView();

    boolean isFooterMapPointRequestOutter();

    boolean isGpsTipDisable();

    boolean isPoiDetailPageEnabled();

    boolean isPoiTipsShowing();

    boolean isTokenAvailable(int i);

    boolean isTrafficItemDialogShowing();

    void onConfigurationChanged();

    void onDestroy();

    void onMapPointRequestReturnNull();

    void onPause();

    void onResume();

    void refreshPoiFooter(PageBundle pageBundle, int i);

    void removeOnTipChangedListener(OnTipChangedListener onTipChangedListener);

    void resetTokenPage();

    void showPoiFooter(PageBundle pageBundle, int i, Callback<Integer> callback);
}
