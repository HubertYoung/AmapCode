package com.autonavi.minimap.agroup.widget;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import com.autonavi.widget.ui.AlertView.a;
import java.lang.ref.WeakReference;

public final class AGroupAlertFactory {

    public @interface AlertType {
    }

    public static cjx a(Context context, TeamStatus teamStatus, a aVar, a aVar2) {
        a aVar3 = new a(context);
        switch (teamStatus) {
            case STATUS_USER_NOT_LOGIN:
                aVar3.a(R.string.agroup_team_status_alert_title_not_login).b(R.string.agroup_team_status_alert_message_not_login).b(R.string.Cancel, aVar).a(R.string.agroup_login, aVar2);
                break;
            case STATUS_USER_IN_THIS_TEAM:
                aVar3.a(R.string.agroup_team_status_alert_title_in_team).b(R.string.Cancel, aVar).a(R.string.agroup_team_status_positive_show_team, aVar2);
                break;
            case STATUS_TEAM_DISMISS:
                aVar3.a(R.string.agroup_team_status_alert_title_dismiss).a(R.string.i_know, aVar2);
                break;
            case STATUS_TEAM_NOT_EXISTS:
                aVar3.a(R.string.agroup_team_status_alert_title_not_exists).a(R.string.i_know, aVar2);
                break;
            case STATUS_TEAM_MEMBER_LIMITED:
                aVar3.a(R.string.agroup_team_status_alert_title_member_limited).a(R.string.i_know, aVar2);
                break;
            case STATUS_USER_IN_OTHER_TEAM:
                String string = DoNotUseTool.getContext().getResources().getString(R.string.agroup_team_status_positive_exit_and_join_team);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(DoNotUseTool.getContext().getResources().getColor(R.color.f_c_8)), 0, string.length(), 33);
                aVar3.a(R.string.agroup_team_status_alert_title_in_other_tem).b(R.string.Cancel, aVar).a((CharSequence) spannableStringBuilder, aVar2);
                break;
            case STATUS_LEADER_IN_OTHER_TEAM:
                String string2 = DoNotUseTool.getContext().getResources().getString(R.string.agroup_team_status_positive_disband_and_join_team);
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(string2);
                spannableStringBuilder2.setSpan(new ForegroundColorSpan(DoNotUseTool.getContext().getResources().getColor(R.color.f_c_8)), 0, string2.length(), 33);
                aVar3.a(R.string.agroup_team_status_alert_title_in_leader_team).b(R.string.Cancel, aVar).a((CharSequence) spannableStringBuilder2, aVar2);
                break;
            default:
                return null;
        }
        aVar3.a(true);
        cjx cjx = new cjx(aVar3.a());
        cjx.a = false;
        cjr.a = new WeakReference<>(cjx);
        return cjx;
    }
}
