package com.autonavi.carowner.roadcamera.page;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;

public class RdCameraApplyResultFragment extends DriveBasePage<bhu> {
    public String a;
    public String b;
    public String c;
    public String d;
    public TextView e;
    public TextView f;
    public TextView g;
    public View h;
    public TextView i;
    public Button j;
    public Button k;
    public View l;
    public Button m;
    public View n;
    public View o;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.rd_camera_apply_result_fragment);
    }

    public final void a() {
        this.e.setText(R.string.rd_camera_apply_result_apply_result);
        this.f.setText(R.string.rd_camera_apply_result_apply_successfully);
        this.g.setText(Html.fromHtml(getString(R.string.rd_camera_apply_result_verified_info_for_money_in_html, this.a)));
        this.l.setVisibility(8);
        this.h.setVisibility(0);
        this.n.setVisibility(0);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bhu(this);
    }
}
