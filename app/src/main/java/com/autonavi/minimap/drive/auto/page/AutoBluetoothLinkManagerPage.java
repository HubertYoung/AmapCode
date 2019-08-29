package com.autonavi.minimap.drive.auto.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;

public class AutoBluetoothLinkManagerPage extends DriveBasePage<den> {
    public Button a;
    public ProgressDlg b;
    public OnClickListener c = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.auto_retry_connect_button) {
                AutoBluetoothLinkManagerPage.a(AutoBluetoothLinkManagerPage.this, AutoBluetoothLinkManagerPage.this.getContext().getString(R.string.auto_car_connecting));
                AutoBluetoothLinkManagerPage.this.mPresenter;
                IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
                if (iAutoRemoteController != null) {
                    iAutoRemoteController.checkNeedStartBluetoothServer();
                }
            }
        }
    };

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.remote_control_reconnection_manager);
    }

    public final void a() {
        if (this.b != null) {
            this.b.dismiss();
            this.b = null;
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new den(this);
    }

    static /* synthetic */ void a(AutoBluetoothLinkManagerPage autoBluetoothLinkManagerPage, String str) {
        autoBluetoothLinkManagerPage.b = new ProgressDlg(autoBluetoothLinkManagerPage.getActivity(), str);
        autoBluetoothLinkManagerPage.b.setCancelable(true);
        autoBluetoothLinkManagerPage.b.show();
    }
}
