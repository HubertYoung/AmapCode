package defpackage;

import android.content.res.Resources;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;

/* renamed from: edq reason: default package */
/* compiled from: RouteDestPlanUtil */
public final class edq {

    /* renamed from: edq$a */
    /* compiled from: RouteDestPlanUtil */
    public interface a {
    }

    public static String a(int i) {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        switch (i) {
            case 2:
                return resources.getString(R.string.navigation_turn_left);
            case 3:
                return resources.getString(R.string.navigation_turn_right);
            case 4:
                return resources.getString(R.string.walk_straight_along_left);
            case 5:
                return resources.getString(R.string.walk_straight_along_right);
            case 6:
                return resources.getString(R.string.walk_left_backward);
            case 7:
                return resources.getString(R.string.walk_right_backward);
            case 8:
                return resources.getString(R.string.walk_turn_around);
            case 9:
                return resources.getString(R.string.walk_straight);
            case 15:
                return resources.getString(R.string.foot_facility_action_arrive_destination);
            case 16:
                return resources.getString(R.string.foot_facility_action_pass_tunnel);
            case 17:
                return resources.getString(R.string.foot_facility_action_pass_cross_walk);
            case 18:
                return resources.getString(R.string.foot_facility_action_pass_over_street);
            case 19:
                return resources.getString(R.string.foot_facility_action_pass_under_ground);
            case 20:
                return resources.getString(R.string.foot_facility_action_pass_square);
            case 21:
                return resources.getString(R.string.foot_facility_action_pass_park);
            case 22:
                return resources.getString(R.string.foot_facility_action_pass_staircase);
            case 23:
                return resources.getString(R.string.foot_facility_action_pass_elevator);
            case 24:
                return resources.getString(R.string.foot_facility_action_pass_rope_way);
            case 25:
                return resources.getString(R.string.foot_facility_action_pass_over_pass_way);
            case 26:
                return resources.getString(R.string.foot_facility_action_pass_building_pass_way);
            case 27:
                return resources.getString(R.string.foot_facility_action_pass_walkman_road);
            case 28:
                return resources.getString(R.string.foot_facility_action_pass_marina);
            case 29:
                return resources.getString(R.string.foot_facility_action_arrive_sightseeing_car);
            case 30:
                return resources.getString(R.string.foot_facility_action_arrive_slide);
            case 31:
                return resources.getString(R.string.foot_facility_action_pass_stair);
            case 32:
                return resources.getString(R.string.foot_facility_action_pass_slope);
            case 33:
                return resources.getString(R.string.foot_facility_action_pass_bridge);
            case 34:
                return resources.getString(R.string.foot_facility_action_pass_ferry);
            case 35:
                return resources.getString(R.string.foot_facility_action_pass_subway_channel);
            case 37:
                return resources.getString(R.string.exit_gate);
            case 38:
                return resources.getString(R.string.foot_facility_action_change_floor_lift);
            case 39:
                return resources.getString(R.string.foot_facility_action_change_floor_stair);
            case 40:
                return resources.getString(R.string.foot_facility_action_change_floor_escalator);
            default:
                return "";
        }
    }

    public static String b(int i) {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        switch (i) {
            case 16:
            case 19:
            case 25:
            case 26:
            case 35:
            case 36:
                return resources.getString(R.string.enter);
            case 17:
            case 27:
            case 31:
            case 32:
                return resources.getString(R.string.pass);
            case 18:
                return resources.getString(R.string.up);
            case 20:
            case 21:
                return resources.getString(R.string.cross);
            case 22:
            case 23:
            case 24:
            case 28:
            case 29:
            case 30:
            case 34:
            case 38:
            case 40:
                return resources.getString(R.string.sit);
            case 33:
                return resources.getString(R.string.guo);
            case 37:
                return resources.getString(R.string.leave);
            case 39:
                return resources.getString(R.string.walk);
            default:
                return "";
        }
    }

    public static String c(int i) {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        switch (i) {
            case 17:
                return resources.getString(R.string.crosswalk);
            case 18:
                return resources.getString(R.string.over_street);
            case 19:
                return resources.getString(R.string.underground);
            case 20:
                return resources.getString(R.string.pass_yard);
            case 21:
                return resources.getString(R.string.pass_park);
            case 22:
                return resources.getString(R.string.staircase);
            case 23:
                return resources.getString(R.string.elevator);
            case 24:
                return resources.getString(R.string.rope_way);
            case 25:
                return resources.getString(R.string.over_passway);
            case 26:
                return resources.getString(R.string.passway);
            case 27:
                return resources.getString(R.string.walk_street);
            case 28:
                return resources.getString(R.string.boat);
            case 29:
                return resources.getString(R.string.sightseeing_car);
            case 30:
                return resources.getString(R.string.skidway);
            case 31:
                return resources.getString(R.string.stair);
            case 32:
                return resources.getString(R.string.slope);
            case 33:
                return resources.getString(R.string.bridge);
            case 34:
                return resources.getString(R.string.ferry);
            case 35:
                return resources.getString(R.string.subway_tunnel);
            case 36:
                return resources.getString(R.string.enter_building);
            case 37:
                return resources.getString(R.string.exit_building);
            case 38:
                return resources.getString(R.string.elevator_floor);
            case 39:
                return resources.getString(R.string.stairway_floor);
            case 40:
                return resources.getString(R.string.escalator_floor);
            default:
                return "";
        }
    }

    public static boolean a(final bid bid, GeoPoint geoPoint, GeoPoint geoPoint2, final String str) {
        if (cfe.a(geoPoint, geoPoint2) < 1000000.0f) {
            return true;
        }
        aho.a(new Runnable() {
            final /* synthetic */ a c = null;

            public final void run() {
                if (bid != null && bid.isAlive() && bid.getContext() != null) {
                    com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(bid.getContext());
                    aVar.a((CharSequence) str).a(R.string.i_know, (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            if (bid != null) {
                                bid.dismissViewLayer(alertView);
                            }
                        }
                    });
                    aVar.a(true);
                    bid.showViewLayer(aVar.a());
                }
            }
        }, 0);
        return false;
    }
}
