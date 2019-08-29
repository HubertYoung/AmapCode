package com.autonavi.minimap.bundle.agroup.api;

public interface IDataService extends bie {

    public enum TeamStatus {
        STATUS_NONE,
        STATUS_SUCCESS,
        STATUS_TEAM_NOT_EXISTS,
        STATUS_TEAM_DISMISS,
        STATUS_TEAM_MEMBER_LIMITED,
        STATUS_USER_NOT_LOGIN,
        STATUS_USER_IN_TEAM,
        STATUS_USER_NOT_JOIN_IN_TEAM,
        STATUS_USER_IN_THIS_TEAM,
        STATUS_USER_IN_OTHER_TEAM,
        STATUS_LEADER_IN_OTHER_TEAM,
        STATUS_GENERIC_ERROR,
        STATUS_UNKNOWN
    }

    public interface a {
        void onMemberBaseInfoChanged();

        void onMemberLocationInfoChanged();

        void onSuperGroupInfoChanged();

        void onTeamInfoChanged();

        void onTeamStatusChanged(TeamStatus teamStatus);
    }

    void a(a aVar);

    void a(String str, String str2, String str3);

    String b(int i);

    void b(a aVar);

    int h();

    int i();

    int j();

    boolean l();

    boolean m();
}
