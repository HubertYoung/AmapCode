package com.alipay.mobile.beehive.rpc.action;

import com.alipay.mobile.beehive.rpc.model.FollowAction;

public interface TriggerActionCallback {
    void onFollowActionTrigger(Object obj, FollowAction followAction, String str);
}
