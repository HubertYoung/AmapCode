package defpackage;

import android.content.Context;
import com.amap.bundle.drive.result.tip.view.TipsView;
import com.autonavi.minimap.R;

/* renamed from: py reason: default package */
/* compiled from: TipsFactory */
public final class py {
    public static TipsView a(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_high_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_1);
        tipsView.setCancelColor(R.color.f_c_1_d);
        tipsView.setDividerColor(R.color.c_1_g);
        return tipsView;
    }

    public static TipsView a(Context context, boolean z, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_high_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_1);
        tipsView.setCancelColor(R.color.f_c_1_d);
        if (!z) {
            tipsView.setDividerColor(R.color.c_1_g);
        } else if (!(pxVar == null || pxVar.a == 10)) {
            tipsView.hideDivider();
        }
        if (!(pxVar == null || pxVar.a == 10)) {
            tipsView.dismissConfirmButton();
        }
        return tipsView;
    }

    public static TipsView a(Context context, px pxVar, boolean z) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_middle_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_10);
        tipsView.setCancelColor(R.color.f_c_10_a);
        tipsView.setDividerColor(R.color.c_7);
        if (!z) {
            tipsView.dismissConfirmButton();
        }
        return tipsView;
    }

    public static TipsView b(Context context, boolean z, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_middle_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_10);
        tipsView.setCancelColor(R.color.f_c_10_a);
        if (!z) {
            tipsView.setDividerColor(R.color.c_7);
        } else {
            tipsView.hideDivider();
        }
        tipsView.dismissConfirmButton();
        return tipsView;
    }

    public static TipsView b(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_high_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_1);
        tipsView.setCancelColor(R.color.f_c_1_d);
        tipsView.setDividerColor(R.color.c_1_g);
        if (rt.a()) {
            tipsView.setConfirmText(R.string.tip_see_route);
        }
        return tipsView;
    }

    public static TipsView b(Context context, px pxVar, boolean z) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_middle_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_10);
        tipsView.setCancelColor(R.color.f_c_10_a);
        tipsView.setDividerColor(R.color.c_7);
        if (rt.a()) {
            tipsView.setConfirmText(R.string.tip_see_route);
        }
        if (!z) {
            tipsView.dismissConfirmButton();
        }
        return tipsView;
    }

    public static TipsView c(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_middle_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_10);
        tipsView.setCancelColor(R.color.f_c_10_a);
        tipsView.setDividerColor(R.color.c_7);
        return tipsView;
    }

    public static TipsView d(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_high_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_1);
        tipsView.setCancelColor(R.color.f_c_1_d);
        tipsView.setDividerColor(R.color.c_1_g);
        return tipsView;
    }

    public static TipsView e(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_middle_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_10);
        tipsView.setCancelColor(R.color.f_c_10_a);
        tipsView.setDividerColor(R.color.c_7);
        return tipsView;
    }

    public static TipsView f(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setConfirmText(R.string.tip_avoid_restrict);
        tipsView.setTipBackGround(R.drawable.route_result_high_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_1);
        tipsView.setCancelColor(R.color.f_c_1_d);
        tipsView.setDividerColor(R.color.c_1_g);
        return tipsView;
    }

    public static TipsView c(Context context, boolean z, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setConfirmText(R.string.tip_avoid_restrict);
        tipsView.setTipBackGround(R.drawable.route_result_high_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_1);
        tipsView.setCancelColor(R.color.f_c_1_d);
        if (!z) {
            tipsView.setDividerColor(R.color.c_1_g);
        } else {
            tipsView.hideDivider();
        }
        return tipsView;
    }

    public static TipsView g(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_middle_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_10);
        tipsView.setCancelColor(R.color.f_c_10_a);
        tipsView.setDividerColor(R.color.c_7);
        return tipsView;
    }

    public static TipsView h(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_middle_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_10);
        tipsView.setCancelColor(R.color.f_c_10_a);
        tipsView.setDividerColor(R.color.c_7);
        tipsView.dismissConfirmButton();
        return tipsView;
    }

    public static TipsView i(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        pxVar.b = pxVar.b;
        tipsView.setTipsInfo(pxVar);
        tipsView.setConfirmText(R.string.tip_set_plate);
        tipsView.setTipBackGround(R.drawable.route_result_high_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_1);
        tipsView.setCancelColor(R.color.f_c_1_d);
        tipsView.setDividerColor(R.color.c_1_g);
        return tipsView;
    }

    public static TipsView c(Context context, px pxVar, boolean z) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setConfirmText(R.string.tip_using_online);
        tipsView.setTipBackGround(R.drawable.route_result_low_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_2);
        tipsView.setCancelColor(R.color.f_c_3);
        tipsView.setDividerColor(R.color.c_26);
        if (!z) {
            tipsView.dismissConfirmButton();
        } else {
            tipsView.setConfirmColor(R.color.f_c_6);
        }
        return tipsView;
    }

    public static TipsView j(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setConfirmText(R.string.navi_switch_to_online);
        tipsView.setTipBackGround(R.drawable.route_result_high_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_1);
        tipsView.setCancelColor(R.color.f_c_1_d);
        tipsView.setDividerColor(R.color.c_1_g);
        return tipsView;
    }

    public static TipsView k(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_middle_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_10);
        tipsView.setCancelColor(R.color.f_c_10_a);
        tipsView.setDividerColor(R.color.c_7);
        return tipsView;
    }

    public static TipsView l(Context context, px pxVar) {
        if (pxVar == null) {
            return null;
        }
        TipsView tipsView = new TipsView(context);
        tipsView.setTipsInfo(pxVar);
        tipsView.setTipBackGround(R.drawable.route_result_middle_priority_tip_bg);
        tipsView.setTextColor(R.color.f_c_10);
        tipsView.setCancelColor(R.color.f_c_10_a);
        tipsView.setDividerColor(R.color.c_7);
        return tipsView;
    }
}
