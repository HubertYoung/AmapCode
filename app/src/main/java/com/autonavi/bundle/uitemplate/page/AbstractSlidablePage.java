package com.autonavi.bundle.uitemplate.page;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.autonavi.bundle.uitemplate.container.SlideContainer;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.SlideMode;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;

public abstract class AbstractSlidablePage<Presenter extends AbstractBasePresenter> extends AbstractBaseMapPage implements a {
    public SlideContainer r;
    protected ViewGroup s;
    public PanelState t = null;

    public @interface PageSlideMode {
    }

    public @interface PageState {
    }

    /* access modifiers changed from: protected */
    @Nullable
    public abstract View a(Context context);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract View b(Context context);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract View c(Context context);

    public void onCreate(Context context) {
        super.onCreate(context);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new LayoutParams(-1, -1));
        relativeLayout.setBackground(null);
        setContentView((View) relativeLayout);
        this.r = new SlideContainer(context);
        this.r.setLayoutParams(new LayoutParams(-1, -1));
        this.r.setHeadView(a(context));
        this.r.setPreloadView(b(context));
        this.r.setContentView(c(context));
        this.r.setContentBackgroundColor(Color.parseColor("#FFFFFF"));
        relativeLayout.addView(this.r);
        this.s = new FrameLayout(context);
        this.s.setLayoutParams(new LayoutParams(-1, -1));
        d(context);
    }

    public void d(Context context) {
        a(bet.a(context, 80), bet.a(context, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST));
    }

    public final void a(int i, int i2) {
        SlideMode slideMode = SlideMode.SLIDE;
        this.r.setSlideMode(SlideMode.SLIDE);
        PanelState panelState = PanelState.COLLAPSED;
        PanelState panelState2 = PanelState.ANCHORED;
        this.t = panelState2;
        this.r.setPanelState(panelState2, false);
        this.r.setMinHeight(i);
        this.r.setAnchorHeight(i2);
    }
}
