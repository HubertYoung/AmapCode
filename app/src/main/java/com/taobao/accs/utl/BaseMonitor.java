package com.taobao.accs.utl;

import anet.channel.statist.StatObject;
import com.autonavi.minimap.ajx3.util.Constants;

public abstract class BaseMonitor extends StatObject {
    public static final String ALARM_MSG_ROUTING_RATE = "ele_routing_rate";
    public static final String ALARM_POINT_AUTH = "auth";
    public static final String ALARM_POINT_CONNECT = "connect";
    public static final String ALARM_POINT_REQUEST = "Request_Success_Rate";
    public static final String ALARM_POINT_REQ_ERROR = "send_fail";
    public static final String COUNT_ACK = "ack";
    public static final String COUNT_AGOO_ACK = "agoo_ack";
    public static final String COUNT_AGOO_ARRIVE = "agoo_arrive";
    public static final String COUNT_AGOO_ARRIVE_ID = "agoo_arrive_id";
    public static final String COUNT_AGOO_ARRIVE_REAL_ID = "agoo_arrive_real_id";
    public static final String COUNT_AGOO_CLICK = "agoo_click";
    public static final String COUNT_AGOO_FAIL_ACK = "agoo_fail_ack";
    public static final String COUNT_AGOO_REPORT_ID = "agoo_report_id";
    public static final String COUNT_AGOO_SUCCESS_ACK = "agoo_success_ack";
    public static final String COUNT_BUSINESS_ACK_FAIL = "bizAckFail";
    public static final String COUNT_BUSINESS_ACK_SUCC = "bizAckSucc";
    public static final String COUNT_ERROR = "error";
    public static final String COUNT_POINT_DNS = "dns";
    public static final String COUNT_POINT_RESEND = "resend";
    public static final String COUNT_POINT_SOFAIL = "sofail";
    public static final String COUNT_POINT_TO_BUSS = "to_buss";
    public static final String COUNT_SERVICE_ALIVE = "service_alive";
    public static final String MODULE = "accs";
    private boolean isCommitted = false;

    public String checkString(String str) {
        return str == null ? Constants.ANIMATOR_NONE : str;
    }

    public boolean beforeCommit() {
        if (this.isCommitted) {
            return false;
        }
        this.isCommitted = true;
        return true;
    }
}
