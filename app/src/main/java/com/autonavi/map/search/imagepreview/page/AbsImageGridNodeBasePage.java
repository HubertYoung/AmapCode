package com.autonavi.map.search.imagepreview.page;

import android.view.View.OnClickListener;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public abstract class AbsImageGridNodeBasePage extends AbstractBasePage<byg> implements OnClickListener {
    public abstract void destroy();

    public abstract void start();

    public abstract void stop();
}
