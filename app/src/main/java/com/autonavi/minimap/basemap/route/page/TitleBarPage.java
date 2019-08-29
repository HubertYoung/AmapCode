package com.autonavi.minimap.basemap.route.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import defpackage.cqv;

public abstract class TitleBarPage<Presenter extends cqv> extends AbstractBasePage<Presenter> {
    protected TitleBar l;

    public abstract void a(PageBundle pageBundle);

    /* access modifiers changed from: protected */
    public abstract int b();

    /* access modifiers changed from: protected */
    public abstract void g();

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        getActivity();
        setContentView(b());
        this.l = (TitleBar) findViewById(R.id.titlebar);
        if (this.l != null) {
            this.l.setOnBackClickListener((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    TitleBarPage.this.g();
                }
            });
            this.l.setOnActionClickListener(new OnClickListener() {
                public final void onClick(View view) {
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public final void a(String str) {
        if (this.l != null) {
            this.l.setTitle(str);
        }
    }
}
