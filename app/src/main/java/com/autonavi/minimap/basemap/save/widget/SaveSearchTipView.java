package com.autonavi.minimap.basemap.save.widget;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.constant.ConstantCompat.SaveSearchResultMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;

public class SaveSearchTipView extends AbstractPoiDetailView implements OnClickListener {
    private View btnConfirm;
    private TextView mSetText;
    private AbstractBasePage nodeFragment;
    private POI poi;
    private View rootView;
    private TextView tvMainTitle;
    private TextView tvViceTitle;
    private View viewTips;

    public SaveSearchTipView(AbstractBasePage abstractBasePage) {
        super(abstractBasePage.getContext());
        this.nodeFragment = abstractBasePage;
        addViews();
    }

    private void addViews() {
        this.rootView = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.save_search_tip_layout, null);
        this.viewTips = this.rootView.findViewById(R.id.useful_address_tip);
        this.tvMainTitle = (TextView) this.rootView.findViewById(R.id.useful_addresss_tip_name);
        this.tvViceTitle = (TextView) this.rootView.findViewById(R.id.useful_addresss_tip_addr);
        this.btnConfirm = this.rootView.findViewById(R.id.useful_address_tip_confirm);
        this.mSetText = (TextView) this.rootView.findViewById(R.id.set_favorite_text);
        this.viewTips.setOnClickListener(this);
        this.btnConfirm.setOnClickListener(this);
        addView(this.rootView, new LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.poi_detail_view_height)));
    }

    public void setFavoriteTyep(String str) {
        if (str.equals(crt.b)) {
            this.mSetText.setText(AMapAppGlobal.getApplication().getString(R.string.save_set_home));
            Drawable drawable = AMapAppGlobal.getApplication().getResources().getDrawable(R.drawable.commute_helper_home);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.mSetText.setCompoundDrawables(drawable, null, null, null);
            return;
        }
        if (str.equals(crt.c)) {
            this.mSetText.setText(AMapAppGlobal.getApplication().getString(R.string.save_set_company));
            Drawable drawable2 = AMapAppGlobal.getApplication().getResources().getDrawable(R.drawable.commute_helper_company);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            this.mSetText.setCompoundDrawables(drawable2, null, null, null);
        }
    }

    public void setPoi(POI poi2) {
        this.poi = poi2.as(POI.class);
    }

    public POI getPoi() {
        return this.poi;
    }

    public void setViceTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            this.tvViceTitle.setVisibility(8);
            return;
        }
        this.tvViceTitle.setVisibility(0);
        this.tvViceTitle.setText(str);
    }

    public void onClick(View view) {
        if (view == this.btnConfirm) {
            if (this.poi == null) {
                setPointResult(false);
            } else if (cpf.b(cpm.b().a()).c(this.poi)) {
                final bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext == null) {
                    setPointResult(true);
                    return;
                }
                a aVar = new a(getContext());
                aVar.a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.dulicate_save_point_modify));
                AnonymousClass1 r2 = new a() {
                    public final void onClick(AlertView alertView, int i) {
                        pageContext.dismissViewLayer(alertView);
                    }
                };
                aVar.b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.cancel), (a) r2);
                aVar.c = r2;
                aVar.a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.modify_confirm), (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        pageContext.dismissViewLayer(alertView);
                        SaveSearchTipView.this.setPointResult(true);
                    }
                });
                aVar.b(true);
                pageContext.showViewLayer(aVar.a());
            } else {
                setPointResult(false);
            }
        }
    }

    public void setPointResult(boolean z) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("result_poi", this.poi);
        pageBundle.putBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY, z);
        this.nodeFragment.setResult(ResultType.OK, pageBundle);
        this.nodeFragment.finish();
    }

    public void reset() {
        this.poi = null;
        this.tvMainTitle.setText("");
        this.tvViceTitle.setText("");
    }

    public void setMainTitle(String str) {
        this.tvMainTitle.setText(str);
    }

    public void adjustMargin() {
        LayoutParams layoutParams = (LayoutParams) this.rootView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.gravity = 1;
            this.rootView.setLayoutParams(layoutParams);
        }
    }
}
