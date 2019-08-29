package com.alipay.android.phone.inside.api.model.accountopenauth;

public enum MCAccountStatusEnum {
    MC_LOGOUT("mcLogout"),
    MC_SWITCH_USER("mcSwitchUser"),
    MC_UNBIND_ALIPAY("mcUnBindAlipay"),
    MC_CHANGE_BIND_ALIPAY("mcChangeBindAlipay");
    
    private String actionName;

    private MCAccountStatusEnum(String str) {
        this.actionName = str;
    }

    public final String getActionName() {
        return this.actionName;
    }
}
