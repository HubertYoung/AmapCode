package com.autonavi.map.search.photo.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.alibaba.baichuan.android.trade.constants.AppLinkConstants;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.map.search.page.AbstractSearchBasePage;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;

public class CommonDialog extends AbstractSearchBasePage<cab> implements OnClickListener, Transparent {
    private int a;
    private TextView b;
    private TextView c;
    private TextView d;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.comment_cancel_edit_dialog);
        View contentView = getContentView();
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.a = arguments.getInt(AppLinkConstants.REQUESTCODE);
        } else {
            this.a = 150;
        }
        this.b = (TextView) contentView.findViewById(R.id.result_title);
        this.c = (TextView) contentView.findViewById(R.id.btn_drop);
        this.c.setOnClickListener(this);
        this.d = (TextView) contentView.findViewById(R.id.btn_goon);
        this.d.setOnClickListener(this);
        if (this.a == 150) {
            this.b.setText(getActivity().getString(R.string.poi_photo_dialog_title));
            this.c.setText(getActivity().getString(R.string.poi_photo_dialog_drop));
            this.d.setText(getActivity().getString(R.string.poi_photo_dialog_gono));
            return;
        }
        if (this.a == 151) {
            this.b.setText(getActivity().getString(R.string.poi_photo_dialog_fail_title));
            this.c.setText(getActivity().getString(R.string.poi_photo_dialog_cancel));
            this.d.setText(getActivity().getString(R.string.poi_photo_dialog_again));
        }
    }

    public void onClick(View view) {
        PageBundle pageBundle = new PageBundle();
        if (view.getId() == R.id.btn_drop) {
            if (this.a == 150) {
                pageBundle.putBoolean("photo_cancel", true);
            }
        } else if (view.getId() == R.id.btn_goon) {
            if (this.a == 150) {
                pageBundle.putBoolean("photo_cancel", false);
            } else {
                startPageForResult(PublishPhotoDialog.class, getArguments(), (int) MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
            }
        }
        finish();
        setResult(ResultType.OK, pageBundle);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cab(this);
    }
}
