package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import java.util.List;

/* renamed from: bid reason: default package */
/* compiled from: IPageContext */
public interface bid extends buj {
    void dismissAllViewLayers();

    void dismissViewLayer(IViewLayer iViewLayer);

    void finish();

    Activity getActivity();

    PageBundle getArguments();

    View getContentView();

    Context getContext();

    List<IViewLayer> getLayerStack();

    boolean hasViewLayer();

    boolean isAlive();

    boolean isViewLayerShowing(IViewLayer iViewLayer);

    void onAnimationFinished(boolean z);

    void onAnimationStarted(boolean z);

    void requestScreenOrientation(int i);

    void setArguments(PageBundle pageBundle);

    void setResult(ResultType resultType, PageBundle pageBundle);

    void showViewLayer(IViewLayer iViewLayer);

    void startActivity(Intent intent);

    void startAlertDialogPage(a aVar);

    void startPageForResult(Class<? extends bid> cls, PageBundle pageBundle, int i);

    void startPageForResult(String str, PageBundle pageBundle, int i);

    void startScheme(Intent intent);
}
