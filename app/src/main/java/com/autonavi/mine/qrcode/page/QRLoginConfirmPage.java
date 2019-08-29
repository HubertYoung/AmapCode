package com.autonavi.mine.qrcode.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.mine.qrcode.presenter.QRLoginConfirmPresenter;
import com.autonavi.minimap.R;

@PageAction("amap.basemap.action.qr_login_page")
public class QRLoginConfirmPage extends AbstractBasePage<QRLoginConfirmPresenter> implements OnClickListener {
    private Button a;
    private Button b;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.fragment_qr_login);
        View contentView = getContentView();
        this.a = (Button) contentView.findViewById(R.id.action_confirm_login);
        this.a.setOnClickListener(this);
        this.b = (Button) contentView.findViewById(R.id.action_confirm_login_cancel);
        this.b.setOnClickListener(this);
        requestScreenOrientation(1);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.action_confirm_login) {
            QRLoginConfirmPresenter qRLoginConfirmPresenter = (QRLoginConfirmPresenter) this.mPresenter;
            if (TextUtils.isEmpty(qRLoginConfirmPresenter.a)) {
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.qr_relogin));
                ((QRLoginConfirmPage) qRLoginConfirmPresenter.mPage).finish();
                return;
            }
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                if (iAccountService.a()) {
                    qRLoginConfirmPresenter.a();
                } else if (NetworkReachability.b()) {
                    iAccountService.a(((QRLoginConfirmPage) qRLoginConfirmPresenter.mPage).getPageContext(), (anq) new anq() {
                        public final void loginOrBindCancel() {
                        }

                        public final void onComplete(boolean z) {
                            if (z) {
                                QRLoginConfirmPresenter.this.a();
                            }
                        }
                    });
                    return;
                } else {
                    ToastHelper.showToast(((QRLoginConfirmPage) qRLoginConfirmPresenter.mPage).getString(R.string.network_error_msg));
                    return;
                }
            }
            return;
        }
        if (view.getId() == R.id.action_confirm_login_cancel) {
            finish();
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new QRLoginConfirmPresenter(this);
    }
}
