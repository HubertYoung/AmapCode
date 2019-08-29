package com.autonavi.carowner.roadcamera.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewAnimator;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.carowner.roadcamera.model.RdCameraPaymentListModel;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.ui.TitleBar;

@PageAction("amap.drive.action.road.camera")
public class RdCameraPaymentListPage extends DriveBasePage<bhv> implements launchModeSingleTask {
    public TitleBar a;
    public RadioButton b;
    public RadioButton c;
    public ViewAnimator d;
    public TextView e;
    public View f;
    public TextView g;
    public PullToRefreshListView h;
    public TextView i;
    public Button j;
    public OnClickListener k = new OnClickListener() {
        public final void onClick(View view) {
            bhv bhv = (bhv) RdCameraPaymentListPage.this.mPresenter;
            aja aja = new aja(ConfigerHelper.getInstance().getRdcameraPaymentKnowMoreActivitiesUrl());
            aja.b = new ajf() {
                public final a l_() {
                    return new a() {
                        public final String a() {
                            return AMapAppGlobal.getApplication().getString(R.string.refresh);
                        }

                        public final boolean b() {
                            if (AnonymousClass1.this.a != null) {
                                AnonymousClass1.this.a.a().reload();
                            }
                            return true;
                        }
                    };
                }
            };
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a((bid) bhv.mPage, aja);
            }
        }
    };
    private ProgressDlg l;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.rd_camera_payment_fragment_layout);
    }

    public final void a() {
        this.h.setHeaderText(getContext().getString(R.string.rd_camera_payment_pull_to_refresh), getContext().getString(R.string.rd_camera_payment_release_to_refresh), getContext().getString(R.string.rd_camera_payment_refreshing));
        if (((bhv) this.mPresenter).e()) {
            this.h.setFooterText(getContext().getString(R.string.rd_camera_payment_pull_to_more), getContext().getString(R.string.rd_camera_payment_release_to_more), getContext().getString(R.string.rd_camera_payment_loading));
            this.h.setMode(Mode.BOTH);
            this.h.changeFooter().setVisibility(0);
            this.h.showImageFoot();
        } else if (((bhv) this.mPresenter).e == 0) {
            if (e()) {
                this.h.setMode(Mode.PULL_FROM_START);
            } else {
                this.h.setMode(Mode.DISABLED);
            }
            this.h.changeFooter().setVisibility(8);
        } else {
            this.h.setMode(Mode.PULL_FROM_START);
            this.h.setFooterText(getContext().getString(R.string.rd_camera_payment_no_more), getContext().getString(R.string.rd_camera_payment_no_more), getContext().getString(R.string.rd_camera_payment_no_more));
            this.h.changeFooter().setVisibility(0);
            this.h.hideImageFoot();
        }
    }

    public final void a(int i2) {
        if (i2 != 1) {
            if (this.b.isChecked()) {
                this.a.setActionTextVisibility(bhv.a(((RdCameraPaymentListModel) ((bhv) this.mPresenter).b).c()));
            }
        } else if (this.c.isChecked()) {
            this.a.setActionTextVisibility(bhv.a(((bhv) this.mPresenter).c()));
        }
    }

    public final void b() {
        if (e()) {
            this.i.setText(getString(R.string.rd_camera_payment_apply_already_list_empty_info_sign_in));
            this.j.setVisibility(8);
            return;
        }
        this.i.setText(getString(R.string.rd_camera_payment_apply_already_list_empty_info_sign_out));
        this.j.setVisibility(0);
    }

    public final void a(int i2, int i3) {
        TextView textView;
        if (i2 == 1) {
            textView = this.e;
        } else {
            textView = this.g;
        }
        textView.setText(Html.fromHtml(getContext().getString(R.string.rd_camera_payment_apply_already_count, new Object[]{Integer.valueOf(i3)})));
    }

    public final void a(final AosRequest aosRequest, String str) {
        c();
        this.l = new ProgressDlg(getActivity());
        this.l.setMessage(str);
        this.l.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (aosRequest != null) {
                    aosRequest.cancel();
                }
            }
        });
        this.l.show();
    }

    public final void c() {
        if (this.l != null) {
            this.l.dismiss();
            this.l = null;
        }
    }

    /* access modifiers changed from: private */
    public static boolean e() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bhv(this);
    }
}
