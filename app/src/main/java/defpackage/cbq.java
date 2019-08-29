package defpackage;

import android.content.res.Resources;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiDynButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.minimap.search.utils.SearchUtils;
import java.util.HashMap;
import java.util.Map;

/* renamed from: cbq reason: default package */
/* compiled from: TemplateHelper */
public final class cbq {
    private static final HashMap<String, String> a = new HashMap<>();
    private static final HashMap<String, Integer> b = new HashMap<>();
    private static final HashMap<String, Integer> c;

    /* renamed from: cbq$a */
    /* compiled from: TemplateHelper */
    public interface a {
        CharSequence a(PoiLayoutTemplate poiLayoutTemplate);
    }

    static {
        HashMap<String, Integer> hashMap = new HashMap<>();
        c = hashMap;
        hashMap.put("poi_queue", Integer.valueOf(R.drawable.poi_queue));
        c.put("poi_room", Integer.valueOf(R.drawable.poi_room));
        c.put("poi_yikuaiqu_order", Integer.valueOf(R.drawable.poi_yikuaiqu_order));
        c.put("poi_bank", Integer.valueOf(R.drawable.poi_bank));
        c.put("poi_atm", Integer.valueOf(R.drawable.poi_atm));
        c.put("poi_ticket", Integer.valueOf(R.drawable.poi_ticket));
        c.put("poi_group", Integer.valueOf(R.drawable.poi_group));
        c.put("poi_favorable", Integer.valueOf(R.drawable.poi_favorable));
        c.put("poi_diandian", Integer.valueOf(R.drawable.poi_diandian));
        c.put("poi_booking", Integer.valueOf(R.drawable.poi_booking));
        c.put("poi_hospital", Integer.valueOf(R.drawable.poi_hospital));
        c.put("poi_taobao", Integer.valueOf(R.drawable.poi_taobao));
        c.put("poi_tmall", Integer.valueOf(R.drawable.poi_tmall));
        c.put("gaode_claim", Integer.valueOf(R.drawable.gaode_claim));
        c.put("is_overbooked", Integer.valueOf(R.drawable.is_overbooked));
        c.put("credit_r", Integer.valueOf(R.drawable.credit_r));
        c.put("is_overbooked_left", Integer.valueOf(R.drawable.is_overbooked_left));
        c.put("credit_r_left", Integer.valueOf(R.drawable.credit_r_left));
        c.put("poi_nearest", Integer.valueOf(R.drawable.poi_nearest));
        c.put(PoiLayoutTemplate.PARKING_DEFAULT_BG, Integer.valueOf(R.drawable.poi_parking_default));
        c.put(PoiLayoutTemplate.PARKING_ENOUGH_BG, Integer.valueOf(R.drawable.poi_parking_enough));
        c.put(PoiLayoutTemplate.PARKING_SHORTAGE_BG, Integer.valueOf(R.drawable.poi_parking_shortage));
        c.put(PoiLayoutTemplate.CHONGDIANZHAN_BG, Integer.valueOf(R.drawable.poi_charge));
        c.put("poi_creflag", Integer.valueOf(R.drawable.poi_creflag));
        c.put("poi_meeting_booking", Integer.valueOf(R.drawable.poi_meeting_booking));
        c.put("poi_room_booking", Integer.valueOf(R.drawable.poi_room_booking));
        c.put("poi_scenic_booking", Integer.valueOf(R.drawable.poi_scenic_booking));
        c.put("poi_live", Integer.valueOf(R.drawable.poi_live));
        c.put("poi_cinemazuo_booking", Integer.valueOf(R.drawable.poi_cinemazuo_booking));
        c.put("poi_alipay", Integer.valueOf(R.drawable.poi_alipay));
        c.put("poi_chengxintong", Integer.valueOf(R.drawable.poi_chengxintong));
        c.put("free_parking_flag", Integer.valueOf(R.drawable.free_parking_flag_new));
        c.put("wifi_flag", Integer.valueOf(R.drawable.wifi_flag_new));
        c.put("hotel_dining_flag", Integer.valueOf(R.drawable.hotel_dining_flag));
        c.put("hotel_meeting_flag", Integer.valueOf(R.drawable.hotel_meeting_flag));
        c.put("hotel_gym_flag", Integer.valueOf(R.drawable.hotel_gym_flag));
        c.put("hotel_wakeup_flag", Integer.valueOf(R.drawable.hotel_wakeup_flag));
        c.put("picking_up_flag", Integer.valueOf(R.drawable.picking_up_flag));
        c.put("hotel_swimming_flag", Integer.valueOf(R.drawable.hotel_swimming_flag));
        c.put("num_bus", Integer.valueOf(R.drawable.num_bus));
        c.put("num_subway", Integer.valueOf(R.drawable.num_subway));
        c.put("clock_red", Integer.valueOf(R.drawable.poi_clock_red));
        c.put("clock_gray", Integer.valueOf(R.drawable.poi_clock_gray));
        c.put("poi_selfsupport", Integer.valueOf(R.drawable.poi_ziying));
        c.put("poi_joinin", Integer.valueOf(R.drawable.poi_jiameng));
        c.put("poi_geek", Integer.valueOf(R.drawable.poi_geek));
        c.put("poi_chain", Integer.valueOf(R.drawable.poi_chain));
        c.put("poi_flagship", Integer.valueOf(R.drawable.poi_flagship));
        c.put("poi_official", Integer.valueOf(R.drawable.poi_official));
        c.put("open_time", Integer.valueOf(R.drawable.open_time));
        c.put("open_time_red", Integer.valueOf(R.drawable.open_time_red));
        c.put("eta_time", Integer.valueOf(R.drawable.eta_time));
        c.put("poi_event", Integer.valueOf(R.drawable.poi_event));
        c.put("poi_intro", Integer.valueOf(R.drawable.poi_intro));
        c.put("poi_direct", Integer.valueOf(R.drawable.poi_direct));
        c.put("poi_landmark", Integer.valueOf(R.drawable.poi_landmark));
        c.put("poi_imp_tevent", Integer.valueOf(R.drawable.poi_imp_tevent));
        c.put("poi_koubei", Integer.valueOf(R.drawable.poi_koubei));
        c.put("poi_swimming", Integer.valueOf(R.drawable.poi_swimming));
        c.put("poi_water_quality", Integer.valueOf(R.drawable.poi_water_quality));
        c.put("main_campus", Integer.valueOf(R.drawable.main_campus));
        c.put("sub_campus", Integer.valueOf(R.drawable.sub_campus));
        c.put("apple_authorization", Integer.valueOf(R.drawable.apple_authorization));
        c.put("xiaomi_authorization", Integer.valueOf(R.drawable.xiaomi_authorization));
        c.put("asus_authorization", Integer.valueOf(R.drawable.asus_authorization));
        c.put("dell_authorization", Integer.valueOf(R.drawable.dell_authorization));
        c.put("huawei_authorization", Integer.valueOf(R.drawable.huawei_authorization));
        c.put("lenovo_authorization", Integer.valueOf(R.drawable.lenovo_authorization));
        c.put("meizu_authorization", Integer.valueOf(R.drawable.meizu_authorization));
        c.put("oppo_authorization", Integer.valueOf(R.drawable.oppo_authorization));
        c.put("vivo_authorization", Integer.valueOf(R.drawable.vivo_authorization));
        c.put("yha_association", Integer.valueOf(R.drawable.yha_association));
        Resources resources = AMapAppGlobal.getApplication().getResources();
        a.put("indoor_flag", resources.getString(R.string.abstract_poi_view_indoor));
        a.put("hotel_flag", resources.getString(R.string.abstract_poi_view_hotel));
        a.put("takeout_flag", resources.getString(R.string.abstract_poi_view_takeout));
        a.put("sc_book_flag", resources.getString(R.string.abstract_poi_view_ticket));
        a.put("cinemazuo_flag", resources.getString(R.string.abstract_poi_view_seat));
        a.put("scenic_route", resources.getString(R.string.abstract_poi_view_voice_tour_guide));
        a.put("tel", resources.getString(R.string.abstract_poi_view_phone));
        a.put("share", resources.getString(R.string.abstrct_poi_view_share));
        a.put(WidgetType.INDOOR_GUIDE, resources.getString(R.string.abstrct_poi_view_shopping_mall));
        a.put("feedback", resources.getString(R.string.text_tips_feedback));
        a.put(FunctionSupportConfiger.TAXI_TAG, resources.getString(R.string.text_tips_taxi));
        b.put("indoor_flag", Integer.valueOf(R.drawable.mbox_icon_indoor_normal_new));
        b.put("hotel_flag", Integer.valueOf(R.drawable.search_result_hotel_new));
        b.put("takeout_flag", Integer.valueOf(R.drawable.search_result_waimai_new));
        b.put("sc_book_flag", Integer.valueOf(R.drawable.search_result_dingpiao_new));
        b.put("cinemazuo_flag", Integer.valueOf(R.drawable.search_result_movie_new));
        b.put("scenic_route", Integer.valueOf(R.drawable.scenic_route_new));
        b.put("tel", Integer.valueOf(R.drawable.tips_tel_new));
        b.put("share", Integer.valueOf(R.drawable.ic_tip_share_new));
        b.put(WidgetType.INDOOR_GUIDE, Integer.valueOf(R.drawable.daolan_new));
        b.put("add_poi", Integer.valueOf(R.drawable.funicon_add_tab_new));
        b.put("feedback", Integer.valueOf(R.drawable.ic_tips_feedback));
        b.put(FunctionSupportConfiger.TAXI_TAG, Integer.valueOf(R.drawable.tips_chuzuche));
    }

    public static void a(TextView textView, PoiLayoutTemplate poiLayoutTemplate, a aVar) {
        if (poiLayoutTemplate.getId() == 2001) {
            textView.setVisibility(poiLayoutTemplate.isShown() == 0 ? 0 : 8);
            textView.setText(aVar.a(poiLayoutTemplate));
        }
    }

    public static void a(int i, View view, PoiLayoutTemplate poiLayoutTemplate) {
        if (poiLayoutTemplate.getId() == i) {
            view.setVisibility(poiLayoutTemplate.getId() == i ? 0 : 8);
        }
    }

    public static void a(ViewGroup viewGroup, boolean z) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setVisibility(8);
            if ((childAt instanceof ViewGroup) && z) {
                a((ViewGroup) childAt, true);
            }
        }
    }

    public static void a(ViewGroup viewGroup) {
        boolean z;
        int childCount = viewGroup.getChildCount();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                z = false;
                break;
            } else if (viewGroup.getChildAt(i2).getVisibility() == 0) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (!z) {
            i = 8;
        }
        viewGroup.setVisibility(i);
    }

    public static PoiLayoutTemplate a(ISearchPoiData iSearchPoiData) {
        Map templateDataMap = iSearchPoiData.getTemplateDataMap();
        if (templateDataMap == null || templateDataMap.size() == 0) {
            return null;
        }
        return (PoiLayoutTemplate) templateDataMap.get(Integer.valueOf(1012));
    }

    public static String a(PoiLayoutTemplate poiLayoutTemplate) {
        if (poiLayoutTemplate == null) {
            return "";
        }
        if (poiLayoutTemplate instanceof PoiButtonTemplate) {
            return ((PoiButtonTemplate) poiLayoutTemplate).getAction();
        }
        return poiLayoutTemplate instanceof PoiDynButtonTemplate ? ((PoiDynButtonTemplate) poiLayoutTemplate).getAction() : "";
    }

    public static a a() {
        return new a() {
            public final CharSequence a(PoiLayoutTemplate poiLayoutTemplate) {
                if (poiLayoutTemplate instanceof PoiHtmlTemplate) {
                    return ((PoiHtmlTemplate) poiLayoutTemplate).getSpanned();
                }
                if (poiLayoutTemplate instanceof PoiTextTemplate) {
                    PoiTextTemplate poiTextTemplate = (PoiTextTemplate) poiLayoutTemplate;
                    if (!TextUtils.isEmpty(poiTextTemplate.getColor())) {
                        String value = poiTextTemplate.getValue();
                        SpannableString spannableString = new SpannableString(value);
                        spannableString.setSpan(new ForegroundColorSpan(SearchUtils.safeParseColor(poiTextTemplate.getColor(), (String) "#999999")), 0, value.length(), 17);
                        return spannableString;
                    }
                }
                return poiLayoutTemplate.getValue();
            }
        };
    }
}
