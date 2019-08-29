package com.amap.bundle.webview.page;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.widget.ui.TitleBar;

public final class WebErrorPage extends AbstractBasePage<aji> implements OnClickListener {
    Button a;
    Uri b;

    public final void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.web_error_fragment);
        TitleBar titleBar = (TitleBar) findViewById(R.id.title);
        titleBar.setWidgetVisibility(33, 8);
        titleBar.setWidgetVisibility(2, 0);
        titleBar.setOnBackClickListener((OnClickListener) this);
        titleBar.setOnExBackClickListener(this);
        this.a = (Button) findViewById(R.id.refresh_btn);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.b = (Uri) arguments.getObject("url");
            this.a.setOnClickListener(this);
            return;
        }
        this.a.setVisibility(8);
    }

    public final void onClick(View view) {
        if (view.getId() != R.id.refresh_btn) {
            finish();
        } else if (!aaw.c(getContext()) || this.b == null) {
            ToastHelper.showToast(getString(R.string.net_error_message));
        } else {
            finish();
            Intent intent = new Intent();
            intent.setData(this.b);
            intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
            getPageContext().startScheme(intent);
        }
    }

    public final /* synthetic */ IPresenter createPresenter() {
        return new aji();
    }
}
