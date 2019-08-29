package defpackage;

import android.content.Context;
import com.autonavi.minimap.R;

/* renamed from: ri reason: default package */
/* compiled from: MotorTipsUtil */
public final class ri {
    public static String a(Context context, int i) {
        if (context == null) {
            return "";
        }
        switch (i) {
            case 0:
                return context.getString(R.string.tip_type_unavoid_restriction_desc);
            case 1:
                return context.getString(R.string.tip_type_avoid_restriction_desc);
            case 2:
                return context.getString(R.string.tip_type_unavoid_incident_desc);
            case 3:
                return context.getString(R.string.tip_type_avoid_incident_desc);
            case 4:
                return context.getString(R.string.tip_type_remind_open_restriction_desc);
            case 5:
                return context.getString(R.string.tip_type_avoid_jam_desc);
            case 6:
                return context.getString(R.string.tip_type_remind_open_car_plate_desc);
            case 7:
                return context.getString(R.string.tip_type_offline_to_online_desc);
            case 8:
                return context.getString(R.string.tip_soon_effect_avoid_restrict_title);
            case 9:
                return context.getString(R.string.tip_soon_end_cross_restrict_title);
            case 10:
                return context.getString(R.string.tip_type_unavoid_desc);
            case 11:
                return context.getString(R.string.tip_type_unavoid_desc);
            case 12:
                return context.getString(R.string.tip_type_unavoid_desc);
            case 13:
                return context.getString(R.string.tip_type_unavoid_desc);
            case 14:
                return context.getString(R.string.tip_type_remind_offline_online_desc);
            case 15:
                return context.getString(R.string.tip_type_avoid_desc);
            case 16:
                return context.getString(R.string.tip_type_avoid_desc);
            case 17:
                return context.getString(R.string.tip_type_avoid_desc);
            case 18:
                return context.getString(R.string.tip_type_avoid_desc);
            default:
                return "";
        }
    }
}
