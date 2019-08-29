package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;

/* renamed from: cyg reason: default package */
/* compiled from: IFrequentLocationPresenter */
public interface cyg {
    void bindView(cyn cyn);

    void cancelLoadData();

    void destroy();

    void editLocation(int i);

    void loadData();

    void onAddPoi();

    void onEditModeChanged(boolean z);

    void planRoute(POI poi);

    void refreshFooter();

    void search();

    void selectPoiCallback(PageBundle pageBundle);

    void updateData(int i, PageBundle pageBundle);
}
