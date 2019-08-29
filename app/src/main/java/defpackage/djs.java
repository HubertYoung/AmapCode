package defpackage;

import android.content.Context;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import java.util.ArrayList;

/* renamed from: djs reason: default package */
/* compiled from: RouteLineErrorReportUtil */
public final class djs {
    public static String a(int i) {
        switch (i) {
            case 1:
                return "2003";
            case 2:
                return Result.RUBBISH_ACCOUNT;
            case 3:
                return "4001";
            case 4:
                return "5000";
            case 5:
                return "6001";
            case 6:
                return "6002";
            case 7:
                return "5008";
            case 8:
                return "4001";
            case 9:
                return "5012";
            case 10:
                return "5011";
            case 11:
                return "5000";
            case 12:
                return "6000";
            case 13:
                return "2003";
            default:
                return "4001";
        }
    }

    public static String b(int i) {
        switch (i) {
            case 1:
                return "2003";
            case 2:
                return "4012";
            case 3:
                return "4011";
            case 4:
                return "4013";
            case 5:
                return "6001";
            case 6:
                return "6002";
            case 7:
                return "5008";
            default:
                return "4011";
        }
    }

    public static String a(Context context, int i, boolean z) {
        switch (i) {
            case 1:
                return context.getString(R.string.car_error_edit_detail_hint_destination_error);
            case 2:
                return context.getString(R.string.car_error_edit_detail_hint_around_error);
            case 3:
                if (z) {
                    return context.getString(R.string.car_error_edit_detail_hint_restriction_info_error_resultpage);
                }
                return context.getString(R.string.car_error_edit_detail_hint_restriction_info_error);
            case 4:
                return context.getString(R.string.car_error_edit_detail_hint_other_error);
            case 5:
                return context.getString(R.string.car_error_edit_detail_hint_camera_error);
            case 6:
                return context.getString(R.string.car_error_edit_detail_hint_speed_info_error);
            case 7:
                return context.getString(R.string.car_error_edit_detail_hint_wrong_drive);
            case 8:
                return context.getString(R.string.car_error_edit_detail_hint_restriction_error);
            case 9:
                return context.getString(R.string.car_error_edit_detail_hint_around_error);
            case 10:
                return context.getString(R.string.car_error_edit_detail_hint_limit_height);
            case 11:
                return context.getString(R.string.car_error_edit_detail_hint_other_error);
            case 12:
                return context.getString(R.string.car_error_edit_detail_hint_road_speed_limit_error);
            case 13:
                return context.getString(R.string.car_error_edit_detail_hint_shop_removal_error);
            default:
                return "";
        }
    }

    public static String a(Context context, int i) {
        switch (i) {
            case 1:
                return context.getString(R.string.car_error_category_destination_error);
            case 2:
                return context.getString(R.string.car_error_category_around_error);
            case 3:
                return context.getString(R.string.car_error_category_restriction_info_error);
            case 4:
                return context.getString(R.string.car_error_category_other_error);
            case 5:
                return context.getString(R.string.car_error_category_camera_error);
            case 6:
                return context.getString(R.string.car_error_category_speed_info_error);
            case 7:
                return context.getString(R.string.car_error_category_wrong_drive);
            case 8:
                return context.getString(R.string.car_error_category_restriction_info_error);
            case 9:
                return context.getString(R.string.car_error_category_line_unreasonable);
            case 10:
                return context.getString(R.string.car_error_category_msg_error);
            case 11:
                return context.getString(R.string.car_error_category_other);
            case 12:
                return context.getString(R.string.car_error_category_camera_feedback);
            case 13:
                return context.getString(R.string.car_error_category_destination_error);
            default:
                return context.getString(R.string.car_error_category_destination_error);
        }
    }

    public static String b(Context context, int i) {
        switch (i) {
            case 1:
                return context.getString(R.string.car_error_tip_confirm_tip_destination_error);
            case 2:
                return context.getString(R.string.car_error_tip_confirm_tip_around_error);
            case 3:
                return context.getString(R.string.car_error_tip_confirm_tip_restriction_info_error);
            case 4:
                return context.getString(R.string.car_error_tip_confirm_tip_other_error);
            case 5:
                return context.getString(R.string.car_error_tip_confirm_tip_camera_error);
            case 6:
                return context.getString(R.string.car_error_tip_confirm_tip_speed_info_error);
            case 7:
                return context.getString(R.string.car_error_tip_confirm_tip_wrong_error);
            case 8:
                return context.getString(R.string.car_error_tip_confirm_tip_restriction_info_error);
            case 9:
                return context.getString(R.string.car_error_tip_confirm_tip_unreasonable_error);
            case 10:
                return context.getString(R.string.car_error_tip_confirm_tip_msg_hint_error);
            case 11:
                return context.getString(R.string.car_error_tip_confirm_tip_other_error);
            case 12:
                return context.getString(R.string.car_error_tip_confirm_tip_camera_error);
            case 13:
                return context.getString(R.string.car_error_tip_confirm_tip_destination_error);
            default:
                return context.getString(R.string.car_error_tip_confirm_tip_destination_error);
        }
    }

    public static String c(Context context, int i) {
        switch (i) {
            case 1:
                return context.getString(R.string.car_error_move_map_tip_destination_error);
            case 2:
                return context.getString(R.string.car_error_move_map_tip_around_error);
            case 3:
                return context.getString(R.string.car_error_move_map_tip_restriction_info_error);
            case 4:
                return context.getString(R.string.car_error_move_map_tip_other_error);
            case 5:
                return context.getString(R.string.car_error_move_map_tip_camera_error);
            case 6:
                return context.getString(R.string.car_error_move_map_tip_speed_info_error);
            case 7:
                return context.getString(R.string.car_error_move_map_tip_wrong_drive);
            case 8:
                return context.getString(R.string.car_error_move_map_tip_restriction_info_error);
            case 9:
                return context.getString(R.string.car_error_move_map_tip_unreasonable_error);
            case 10:
                return context.getString(R.string.car_error_move_map_tip_msg_error);
            case 11:
                return context.getString(R.string.car_error_move_map_tip_other_error);
            case 12:
                return context.getString(R.string.car_error_move_map_tip_camera_feedback_error);
            case 13:
                return context.getString(R.string.car_error_move_map_tip_destination_error);
            default:
                return context.getString(R.string.car_error_category_destination_error);
        }
    }

    public static String d(Context context, int i) {
        if (2 == i) {
            return context.getString(R.string.car_error_move_map_tip_unreasonable_error);
        }
        return c(context, i);
    }

    static /* synthetic */ ArrayList a(GeoPoint geoPoint, GeoPoint geoPoint2, ArrayList arrayList) {
        float a = cfe.a(geoPoint, geoPoint2);
        if (a >= 10.0f) {
            int i = ((int) (a / 10.0f)) + 1;
            int i2 = (geoPoint2.x - geoPoint.x) / i;
            int i3 = (geoPoint2.y - geoPoint.y) / i;
            for (int i4 = 1; i4 < i; i4++) {
                arrayList.add(new GeoPoint(geoPoint.x + (i2 * i4), geoPoint.y + (i3 * i4)));
            }
        }
        return arrayList;
    }
}
