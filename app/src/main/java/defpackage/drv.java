package defpackage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.mapscreenshot.MapScreenshotPage;
import com.autonavi.widget.ui.LoadingViewBL;

/* renamed from: drv reason: default package */
/* compiled from: MapScreenshotPresenter */
public final class drv extends AbstractBaseMapPagePresenter<MapScreenshotPage> implements a {
    public drv(MapScreenshotPage mapScreenshotPage) {
        super(mapScreenshotPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        PageBundle arguments = ((MapScreenshotPage) this.mPage).getArguments();
        if (arguments != null) {
            String string = arguments.getString("title");
            if (!TextUtils.isEmpty(string)) {
                ((MapScreenshotPage) this.mPage).a.setTitle(string);
            }
        }
    }

    public final void onPrepare() {
        MapScreenshotPage mapScreenshotPage = (MapScreenshotPage) this.mPage;
        mapScreenshotPage.b = new LoadingViewBL(mapScreenshotPage.getContext(), 3);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        mapScreenshotPage.b.setLayoutParams(layoutParams);
        mapScreenshotPage.b.setCancelable(false);
        mapScreenshotPage.b.setLoadingText(mapScreenshotPage.getString(R.string.progress_screenshot_is_ongoing));
        mapScreenshotPage.b.setCloseIconVisibility(8);
        mapScreenshotPage.b.setOnCloseClickListener(new OnClickListener() {
            public final void onClick(View view) {
                MapScreenshotPage.this.a();
            }
        });
        if (mapScreenshotPage.getPageContext() != null && mapScreenshotPage.b != null) {
            mapScreenshotPage.getPageContext().showViewLayer(mapScreenshotPage.b);
        }
    }

    public final void onFailure() {
        ((MapScreenshotPage) this.mPage).getActivity().runOnUiThread(new Runnable() {
            public final void run() {
                ((MapScreenshotPage) drv.this.mPage).a();
                ToastHelper.showToast("截图失败！");
            }
        });
    }

    public final void onScreenShotFinish(final String str) {
        ((MapScreenshotPage) this.mPage).getActivity().runOnUiThread(new Runnable() {
            public final void run() {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("screenshot_filepath", str);
                ((MapScreenshotPage) drv.this.mPage).setResult(ResultType.OK, pageBundle);
                ((MapScreenshotPage) drv.this.mPage).finish();
                ((MapScreenshotPage) drv.this.mPage).a();
            }
        });
    }
}
