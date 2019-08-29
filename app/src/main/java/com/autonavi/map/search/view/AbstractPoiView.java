package com.autonavi.map.search.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.HashMap;

public abstract class AbstractPoiView extends FrameLayout implements ely<InfoliteResult> {
    static final HashMap<String, Integer> EXT_BTN_DRAWABLE = new HashMap<>();
    static final HashMap<String, String> EXT_BTN_TEXT = new HashMap<>();
    static final HashMap<String, Integer> EXT_IMAGE_SRC = new HashMap<>();
    public static final String PHONELIST_SPLITER = "$";
    public static final String VIEW_TYPE_TIP_ITEM = "TIP";
    protected InfoliteResult dataCenter;
    public boolean isSingle;
    protected Context mContext;
    protected String mFromSource;
    protected LayoutInflater mLayoutInflater;
    protected View mRootView;
    protected View parent;
    protected POI poi;
    protected int pos;
    protected SuperId superId;
    protected String viewType = "";

    public POI getPoi() {
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract void goToDetail(POI poi2, String str);

    /* access modifiers changed from: protected */
    public abstract void showTelPanel(POI poi2, int i, String str);

    static {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        EXT_BTN_TEXT.put("indoor_flag", resources.getString(R.string.abstract_poi_view_indoor));
        EXT_BTN_TEXT.put("hotel_flag", resources.getString(R.string.abstract_poi_view_hotel));
        EXT_BTN_TEXT.put("takeout_flag", resources.getString(R.string.abstract_poi_view_takeout));
        EXT_BTN_TEXT.put("sc_book_flag", resources.getString(R.string.abstract_poi_view_ticket));
        EXT_BTN_TEXT.put("cinemazuo_flag", resources.getString(R.string.abstract_poi_view_seat));
        EXT_BTN_TEXT.put("scenic_route", resources.getString(R.string.abstract_poi_view_voice_tour_guide));
        EXT_BTN_TEXT.put("tel", resources.getString(R.string.abstract_poi_view_phone));
        EXT_BTN_TEXT.put("share", resources.getString(R.string.abstrct_poi_view_share));
        EXT_BTN_TEXT.put(WidgetType.INDOOR_GUIDE, resources.getString(R.string.abstrct_poi_view_shopping_mall));
        EXT_BTN_DRAWABLE.put("indoor_flag", Integer.valueOf(R.drawable.mbox_icon_indoor_normal));
        EXT_BTN_DRAWABLE.put("hotel_flag", Integer.valueOf(R.drawable.search_result_hotle));
        EXT_BTN_DRAWABLE.put("takeout_flag", Integer.valueOf(R.drawable.search_result_waimai));
        EXT_BTN_DRAWABLE.put("sc_book_flag", Integer.valueOf(R.drawable.search_result_dingpiao));
        EXT_BTN_DRAWABLE.put("cinemazuo_flag", Integer.valueOf(R.drawable.search_result_movie));
        EXT_BTN_DRAWABLE.put("scenic_route", Integer.valueOf(R.drawable.scenic_route));
        EXT_BTN_DRAWABLE.put("tel", Integer.valueOf(R.drawable.mbox_btn_call_icon));
        EXT_BTN_DRAWABLE.put("share", Integer.valueOf(R.drawable.ic_tip_share));
        EXT_BTN_DRAWABLE.put(WidgetType.INDOOR_GUIDE, Integer.valueOf(R.drawable.daolan));
        EXT_BTN_DRAWABLE.put("add_poi", Integer.valueOf(R.drawable.funicon_add_tab));
        EXT_IMAGE_SRC.put("poi_queue", Integer.valueOf(R.drawable.poi_queue));
        EXT_IMAGE_SRC.put("free_parking_flag", Integer.valueOf(R.drawable.free_parking_flag));
        EXT_IMAGE_SRC.put("wifi_flag", Integer.valueOf(R.drawable.wifi_flag));
        EXT_IMAGE_SRC.put("poi_room", Integer.valueOf(R.drawable.poi_room));
        EXT_IMAGE_SRC.put("poi_yikuaiqu_order", Integer.valueOf(R.drawable.poi_yikuaiqu_order));
        EXT_IMAGE_SRC.put("poi_bank", Integer.valueOf(R.drawable.poi_bank));
        EXT_IMAGE_SRC.put("poi_atm", Integer.valueOf(R.drawable.poi_atm));
        EXT_IMAGE_SRC.put("poi_ticket", Integer.valueOf(R.drawable.poi_ticket));
        EXT_IMAGE_SRC.put("poi_group", Integer.valueOf(R.drawable.poi_group));
        EXT_IMAGE_SRC.put("poi_favorable", Integer.valueOf(R.drawable.poi_favorable));
        EXT_IMAGE_SRC.put("poi_diandian", Integer.valueOf(R.drawable.poi_diandian));
        EXT_IMAGE_SRC.put("poi_booking", Integer.valueOf(R.drawable.poi_booking));
        EXT_IMAGE_SRC.put("poi_hospital", Integer.valueOf(R.drawable.poi_hospital));
        EXT_IMAGE_SRC.put("poi_taobao", Integer.valueOf(R.drawable.poi_taobao));
        EXT_IMAGE_SRC.put("poi_tmall", Integer.valueOf(R.drawable.poi_tmall));
        EXT_IMAGE_SRC.put("gaode_claim", Integer.valueOf(R.drawable.gaode_claim));
        EXT_IMAGE_SRC.put("poi_nearest", Integer.valueOf(R.drawable.poi_nearest));
        EXT_IMAGE_SRC.put(PoiLayoutTemplate.PARKING_DEFAULT_BG, Integer.valueOf(R.drawable.poi_parking_default));
        EXT_IMAGE_SRC.put(PoiLayoutTemplate.PARKING_ENOUGH_BG, Integer.valueOf(R.drawable.poi_parking_enough));
        EXT_IMAGE_SRC.put(PoiLayoutTemplate.PARKING_SHORTAGE_BG, Integer.valueOf(R.drawable.poi_parking_shortage));
        EXT_IMAGE_SRC.put(PoiLayoutTemplate.CHONGDIANZHAN_BG, Integer.valueOf(R.drawable.poi_charge));
        EXT_IMAGE_SRC.put("poi_creflag", Integer.valueOf(R.drawable.poi_creflag));
        EXT_IMAGE_SRC.put("poi_room_booking", Integer.valueOf(R.drawable.poi_room_booking));
        EXT_IMAGE_SRC.put("poi_meeting_booking", Integer.valueOf(R.drawable.poi_meeting_booking));
        EXT_IMAGE_SRC.put("poi_scenic_booking", Integer.valueOf(R.drawable.poi_scenic_booking));
        EXT_IMAGE_SRC.put("poi_live", Integer.valueOf(R.drawable.poi_live));
        EXT_IMAGE_SRC.put("poi_cinemazuo_booking", Integer.valueOf(R.drawable.poi_cinemazuo_booking));
        EXT_IMAGE_SRC.put("poi_alipay", Integer.valueOf(R.drawable.poi_alipay));
        EXT_IMAGE_SRC.put("poi_chengxintong", Integer.valueOf(R.drawable.poi_chengxintong));
        EXT_IMAGE_SRC.put("poi_selfsupport", Integer.valueOf(R.drawable.poi_ziying));
        EXT_IMAGE_SRC.put("poi_joinin", Integer.valueOf(R.drawable.poi_jiameng));
        EXT_IMAGE_SRC.put("poi_geek", Integer.valueOf(R.drawable.poi_geek));
        EXT_IMAGE_SRC.put("poi_chain", Integer.valueOf(R.drawable.poi_chain));
        EXT_IMAGE_SRC.put("poi_flagship", Integer.valueOf(R.drawable.poi_flagship));
        EXT_IMAGE_SRC.put("poi_official", Integer.valueOf(R.drawable.poi_official));
    }

    public AbstractPoiView(Context context) {
        super(context);
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public void setSingle(boolean z) {
        this.isSingle = z;
    }

    public boolean isSingle() {
        return this.isSingle;
    }

    /* access modifiers changed from: protected */
    public int getResourceId(String str, String str2) {
        Integer num = EXT_IMAGE_SRC.get(str);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public void setSuperId(SuperId superId2) {
        this.superId = superId2;
    }

    /* access modifiers changed from: protected */
    public void resetPoiIvs(ImageView[] imageViewArr, String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            int resourceId = getResourceId(strArr[i], ResUtils.DRAWABLE);
            if (resourceId != 0) {
                int i2 = i % 3;
                imageViewArr[i2].setVisibility(0);
                imageViewArr[i2].setImageResource(resourceId);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setTextViewMaxWidth(int i, View view, ImageView[] imageViewArr, TextView textView, TextView textView2) {
        int i2;
        int i3;
        if (textView2 != null && view != null && imageViewArr != null && textView != null && i != 0) {
            textView2.getLayoutParams().width = -2;
            textView.measure(0, 0);
            int measuredWidth = textView.getMeasuredWidth();
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                if (imageViewArr[i5] != null) {
                    i4 += ((imageViewArr[i5].getDrawable() == null || imageViewArr[i5].getVisibility() != 0) ? 0 : imageViewArr[i5].getDrawable().getIntrinsicWidth()) + this.mContext.getResources().getDimensionPixelOffset(R.dimen.search_result_list_icon_divide);
                }
            }
            int i6 = this.mContext.getResources().getDisplayMetrics().widthPixels;
            if (i6 != 0) {
                if (this.isSingle) {
                    i2 = agn.a(this.mContext, 40.0f);
                    i3 = 0;
                } else {
                    i3 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_margin_10A);
                    i2 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.search_result_tip_detail_right_padding);
                }
                textView2.measure(0, 0);
                int i7 = (((i6 - measuredWidth) - i4) - i2) - i3;
                if (textView2.getMeasuredWidth() > i7) {
                    textView2.setMaxWidth(i7);
                }
            }
        }
    }

    public void setFromSource(String str) {
        this.mFromSource = str;
    }

    public void setViewType(String str) {
        setTag(str);
        this.viewType = str;
    }

    public String getViewType() {
        return this.viewType;
    }
}
