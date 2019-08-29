package com.autonavi.map.core.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.Real3DManager;
import com.autonavi.map.core.Real3DManager.ActionLogFromEnum;
import com.autonavi.map.core.Real3DManager.ActionLogStateEnum;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.util.Iterator;

public class Real3DSwitchView extends LinearLayout implements OnClickListener, OnCheckedChangeListener {
    private CheckBox cbReal3DSwitch;
    private boolean mIgnoreCheckChanged = false;
    /* access modifiers changed from: private */
    public bsg mPresenter;

    public Real3DSwitchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.real3d_switch_view, this);
        setOrientation(1);
        this.cbReal3DSwitch = (CheckBox) findViewById(R.id.cbReal3DSwitch);
        this.cbReal3DSwitch.setOnCheckedChangeListener(this);
    }

    public void setPresenter(bsg bsg) {
        this.mPresenter = bsg;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        boolean z2;
        if (this.mPresenter != null && !this.mIgnoreCheckChanged) {
            if (z) {
                bsg bsg = this.mPresenter;
                Real3DManager real3DManager = bsg.d;
                GeoPoint o = bsg.b.getMapView().o();
                if (real3DManager.a(false)) {
                    lj b = li.a().b(o.getLongitude(), o.getLatitude());
                    if (b != null && b.j != 0) {
                        String valueOf = String.valueOf(b.j);
                        Iterator<String> it = real3DManager.d.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (valueOf.equals(it.next())) {
                                    z2 = true;
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
                z2 = false;
                if (z2) {
                    bsg.a(false);
                    return;
                }
                bsg.c();
                bsg.a.showConfirmDialog();
                return;
            }
            bsg bsg2 = this.mPresenter;
            bsg2.d.c(false);
            Real3DManager.b(bsg2.b);
            ActionLogFromEnum actionLogFromEnum = ActionLogFromEnum.SWITCH;
            ActionLogStateEnum actionLogStateEnum = ActionLogStateEnum.CLOSE;
        }
    }

    public void setSwitchChecked(boolean z) {
        this.mIgnoreCheckChanged = true;
        this.cbReal3DSwitch.setChecked(z);
        this.mIgnoreCheckChanged = false;
    }

    public void showConfirmDialog() {
        final bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            bsg bsg = this.mPresenter;
            if (bsg.c != null) {
                bsg.c.d();
            }
            this.mPresenter.c();
            a aVar = new a(getContext());
            aVar.b(R.string.real3d_confirm_tip).a(R.string.real3d_not_suport_poi_confirm_dialog_yes, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    Real3DSwitchView.this.mPresenter.a(true);
                    if (Real3DSwitchView.this.mPresenter.d != null && AMapPageUtil.isHomePage()) {
                        DoNotUseTool.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(Real3DManager.c)));
                    }
                    pageContext.dismissViewLayer(alertView);
                }
            }).b(R.string.cancel, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    Real3DSwitchView.this.setSwitchChecked(false);
                    pageContext.dismissViewLayer(alertView);
                }
            });
            pageContext.showViewLayer(aVar.a());
        }
    }

    public void onClick(View view) {
        clearAnimation();
    }
}
