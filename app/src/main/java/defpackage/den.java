package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.auto.page.AutoBluetoothLinkManagerPage;

/* renamed from: den reason: default package */
/* compiled from: AutoBluetoothLinkManagerPresenter */
public final class den extends sw<AutoBluetoothLinkManagerPage, dei> {
    public den(AutoBluetoothLinkManagerPage autoBluetoothLinkManagerPage) {
        super(autoBluetoothLinkManagerPage);
    }

    public final void onPageCreated() {
        AutoBluetoothLinkManagerPage autoBluetoothLinkManagerPage = (AutoBluetoothLinkManagerPage) this.mPage;
        ((TextView) autoBluetoothLinkManagerPage.getContentView().findViewById(R.id.title_text_name)).setText(R.string.auto_title);
        autoBluetoothLinkManagerPage.getContentView().findViewById(R.id.title_btn_left).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                AutoBluetoothLinkManagerPage.this.finish();
            }
        });
        autoBluetoothLinkManagerPage.a = (Button) autoBluetoothLinkManagerPage.getContentView().findViewById(R.id.auto_retry_connect_button);
        NoDBClickUtil.a((View) autoBluetoothLinkManagerPage.a, autoBluetoothLinkManagerPage.c);
        dei dei = (dei) this.b;
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.setRemoteControlConnectListener(dei.c);
        }
    }

    public final void onDestroy() {
        ((AutoBluetoothLinkManagerPage) this.mPage).a();
        dei dei = (dei) this.b;
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.removeRemoteControlConnectListener(dei.c);
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        AutoBluetoothLinkManagerPage autoBluetoothLinkManagerPage = (AutoBluetoothLinkManagerPage) this.mPage;
        if (autoBluetoothLinkManagerPage.b != null && autoBluetoothLinkManagerPage.b.isShowing()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public final /* synthetic */ su a() {
        return new dei(this);
    }
}
