package com.alipay.mobile.beehive.rpc.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FollowAction implements Serializable {
    public Map<String, String> extInfo;
    public List<FollowAction> triggerActions;
    public String triggerType;
    public String type;
}
