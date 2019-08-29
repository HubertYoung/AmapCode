package com.alipay.mobile.common.transport.config;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData;
import com.autonavi.common.SuperId;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.taobao.accs.utl.BaseMonitor;

public enum TransportConfigureItem implements ConfigureItem {
    ALLOW_SPDY_GLOBAL_ERROR_TIMES("allowSpdyErrorTimes", "1000"),
    ONCE_SPDY_ERROR_TIMES("onceSpdyErrorTimes", "5"),
    CONN_FAIL_MAX_TIMES("cfmt", "100"),
    ERR_TIMES_OF_DELETE_IP("errTimesOfDeleteIp", "2"),
    CONN_TIME_OUT("cTimeout", H5AppPrepareData.PREPARE_FAIL),
    HANDSHAK_TIMEOUT("hsto", "15000"),
    SECOND_GEN_READ_TIMEOUT("srt", "30000"),
    THIRD_GEN_READ_TIMEOUT("trt", "20000"),
    WIFI_READ_TIMEOUT("wrt", "15000"),
    SPDY_TIME_TO_ASLEEP("sttal", "90000"),
    SPDY_SHORT_TIME_OUT("ssto", "6000"),
    OPERATION_TYPE_LIST("operationTypeList", "ALL1"),
    SPDY_SWITCH("ss", "F"),
    SPDY_LONGLINK_SWITCH("slls", "T"),
    SYNC_SPDY_SWITCH("sss", "T"),
    H5_SPDY_SWITCH("h5ss", "T"),
    SMALL_SPDY_SWITCH("smlss", "T"),
    SUB_PROC_SPDY_SWITCH("spss", "T"),
    NO_DOWN_HTTPS("nhd", "1-1"),
    SPDY_BLACK_LIST_PHONE_BRAND("sblpb", ""),
    SPDY_BLACK_LIST_PHONE_MODEL("sblpm", ""),
    SPDY_BLACK_LIST_CPU_MODEL("sblcm", ""),
    SPDY_DISABLED_NET_KEY("snetKey", ""),
    SPDY_DISABLED_SDK_VERSION("ssdkVer", ""),
    AMNET_BLACK_LIST_PHONE_BRAND("ablpb", ""),
    AMNET_BLACK_LIST_PHONE_MODEL("ablpm", ""),
    AMNET_BLACK_LIST_CPU_MODEL("ablcm", ""),
    AMNET_DISABLED_NET_KEY("anetKey", ""),
    AMNET_DISABLED_SDK_VERSION("asdkVer", ""),
    COOKIE_CACHE_SWITCH("ccs", "T"),
    REPLACE_SPANNER_COOKIE_SWITCH("rscs", "F"),
    MOBILEGW_URL("mgwurl", ""),
    PING_DEFAULT_INTERVAL("defi", "30000"),
    PING_TIME_OUT("pto", "15000"),
    RECONN_MAX_COUNT("rcmc", "10"),
    SECOND_GEN_PING_INTERVAL("sgi", "15000"),
    THIRD_GEN_PING_INTERVAL("tgi", "15000"),
    WIFI_PING_INTERVAL("wifii", "30000"),
    MDAP_SEED1("mdapSeed1", "0"),
    MDAP_SEED2("mdapSeed2", "0"),
    SPDY_URL("spdyUrl", "https://mobilegw.alipay.com/mgw.htm"),
    MMTP_URL("murl", "mygw.alipay.com:443"),
    AMNET_HS("mhs", "T"),
    VERSION(LogItem.MM_C43_K4_VIDEO_FPS, "0"),
    VERSION2("vs", "0"),
    GZIP_SWITCH("gzip", "T"),
    DNS_SWITCH(BaseMonitor.COUNT_POINT_DNS, "T"),
    DNS_SWITCH_DEBUG("dnsd", "F"),
    DNS_PRE_LOAD("dnspload", "T"),
    DNS_USE_SIGN("dnssign", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    DNS_FEEDBACK("dnsfeed", "T"),
    DOWNGRADE_TLS_SWITCH("dgts", "T"),
    VERIFY_ALTS_MODE("vam", "T"),
    ENABLED_CACHE_ADDRESS("eca", "T"),
    CONN_KEEP_ALIVE_DURATION("ckad", "600000"),
    RPC_DIRECT_ADDR_SWITCH("rdas", "F"),
    MOBILEGW_PRE_SET_IPS("mpsi", ""),
    GET_ALL_BY_NAME_TIME_OUT("gabnto", "10"),
    ERROR_LOG_LEVEL("ell", "1"),
    BLACK_LIST_DNS_HOST_NAME("bldhn", "bogon"),
    BG_2G_THREAD_COUNT("b2tc", "2"),
    BG_3G_THREAD_COUNT("b3tc", "3"),
    IMG_4G_THREAD_COUNT("i4tc", "7"),
    IMG_2G_THREAD_COUNT("i2tc", "3"),
    IMG_3G_THREAD_COUNT("i3tc", "4"),
    NEW_DATA_STRUCT("nds", "F"),
    AMNET_SWITCH("as", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    ANDROID_5_MMTP_SWITCH("a5ms", "T"),
    RPCV2_SWITCH("rpcv2", "T"),
    QUIC_HOST("qh", ""),
    QUIC_PORT("qp", "44300"),
    QUIC_SWITCH("qs", "0-1"),
    QUIC_DOWN_URL("qdu", ""),
    QUIC_MD5("qmd5", ""),
    QUIC_ZIP_SIZE("qzs", "0"),
    HTTPDNS_REQUEST_INTERVAL("hdri", "600000"),
    HTTPDNS_REQUEST_STRONG_INTERVAL("hdrsi", "300000"),
    HTTPDNS_RETRY_COUNT("hdrc", "3"),
    HTTP_DNS_V2("hdv", "T"),
    HTTP_DNS_AMDC_IP("amdc_ip", "110.75.244.151"),
    RPC_WHITELIST_INTERVAL("rpcwi", "0"),
    IPRANK_MODEL_SWITCH("ipmdsw", "T"),
    IPRANK_AB_SWITCH("iprabs", "0"),
    IPRANK_SPEEDTEST_SWITCH("ipsts", "T"),
    IPRANK_H5_SWITCH("iprh5", "F"),
    IPRANK_TTL("iprt", "5"),
    IPRANK_MAX_SIZE("ipmsz", "600"),
    IPRANK_DELETE_THRESHOLD("ipdt", "0.3"),
    CDN_URL("cdnu", "https://promotion.alipay.com/mgw.htm"),
    CDN_APIS("cdna", ""),
    AMNET_RESEND_APIS("ara", "alipay.mobile.bill.queryBillListPB,alipay.mobile.transfer.checkCertify,alipay.client.getRSAKey"),
    REQ_DC_INTERVAL("rdi", "7200000"),
    AMNET_SMART_HEARTBEAT("ashb", "1-1"),
    AMNET_ORTT("ortt", "1-1"),
    AMNET_DELAY_HANDSHAKE("adhs", "1-1"),
    SWITCH_TAG_LOG("stl1", "def"),
    SWITCH_TAG_LOG1("stl1", "def"),
    SWITCH_TAG_LOG2("stl2", "def"),
    SYNC_ONLY_SPDY("sos", "1-1"),
    USE_URGENT_RPC_POOL("uurp", "T"),
    ALLOW_AMNET_DOWNGRADE("aad", "T"),
    AMNET_DOWNGRADE_RPC_TRIGGER_COUNT("adrtc", "30"),
    AMNET_DOWNGRADE_RPC_TRIGGER_TIME("adrtt", "15"),
    AMNET_DOWNGRADE_BIND_TRIGGER_COUNT("adbtc", "4"),
    AMNET_DOWNGRADE_BIND_TRIGGER_TIME("adbtt", "30"),
    ADVANCED_NET_PERF_PROFILING("anpp", "T"),
    ALIPAY_CLIENT_VERSION("cv", "T"),
    ALIPAY_USER_ID(Oauth2AccessToken.KEY_UID, "F"),
    AMENT_INTLGNT_HB_PERF("aihp", "T"),
    RPC_TOTAL_TIMEOUT("RTTO", "180000"),
    AMENT_USE_RTC_WAKEUP("aurw", "T"),
    ALARM_LOG_UPLOAD("alu", "0,0"),
    DJG_SWITCH("djgswh", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    NBNET_DL_SWITCH("nbdls4", "-1"),
    NBNET_DL_OVERSEA_SWITCH("nbdlos", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    NBNET_UP_SWITCH("nbups4", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    NETSERVICE_REPORT_PERIOD("nsrp", "300000"),
    NETSERVICE_REPORT_ERRCOUNT("nsrerrc", "5"),
    NETSERVICE_RPC_LIST("nsrpcl", "alipay.msp.cashier.dispatch.pb.v3"),
    NETSERVICE_UPERR_REPORT("massup", "0"),
    WHITE_LIST_USER("wlu", "F"),
    UPLOAD_ATONCE("upla", "T"),
    LOG_UPLOAD_SIZE("lgus", "10"),
    NETWORK_DIAGNOSE_TRACEROUTE_SYS("ndts", "0,0"),
    NETWORK_DIAGNOSE_TRACEROUTE_USR("ndtu", "0,0"),
    NETWORK_DIAGNOSE_TRACEROUTE_THRESHOLD("ndtt", "1,1000"),
    NETWORK_DIAGNOSE_TRACEROUTE_AUTO("ndta", "-1"),
    RPC_DIAGNOSE_UPLOAD_TIME("rdut", "300"),
    FORCE_RESET_COOKIE("frc", "T"),
    SHORTLINK_RPCLIST("slrl", ""),
    SIGN_ATLAS_OPEN("sao", "F"),
    AFTER_LOGIN_GO_EXT("alge", "T"),
    BGRPC_GO_SPDY("bgrgs", "T"),
    BGRPC_GO_SPDY_GRAY("bgrspg", "0,0"),
    RETRY_CAPTAIN("rtycap", "T"),
    DOWNLOAD_DOWNGRADE("dwndg", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    DOWNLOADERR_RETRY("dwerrty", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    DOWNLOAD_EXT_TIMEOUT("dwnextto", "T"),
    DOWNGRADE_HOSTS("dwnhosts", "tfs.alipayobjects.com,pic.alipayobjects.com,oalipay-dl-django.alicdn.com,api-mayi.django.t.taobao.com"),
    DOWN_TFS_HOST("tfshost", "tfs.alipayobjects.com"),
    DOWN_PIC_HOST("pichost", "pic.alipayobjects.com"),
    DOWN_APIDJG_HOST("apihost", "api-mayi.django.t.taobao.com"),
    DOWN_DLDJG_HOST("dlhost", "oalipay-dl-django.alicdn.com"),
    DOWN_CHECK_SD_PERMISSION("dcsp", "T"),
    EASTEREGGS("eggs", "F"),
    LOG_PRIO_SWITCH("lps", "3"),
    INIT_MERGE_CMD("imc", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    ABTAG("ab_tag", ""),
    ENHANCED_FRONT_JUDGE("enfrj", "F"),
    RSRC_WITH_CACHE("rsrcwc", "F"),
    RSRC_RETRY_WITH_RANGE("rsrcrange", "T"),
    SWITCH_FROM_ORIGINAL("sfo", "0,0"),
    PROC_CRASH_HANDLE_SWITCH("pchs", "T"),
    SINGAL_STATE_SWITCH("ssts", "T"),
    SET_SND_TIMEOUT_SWITCH("ssndts", "T"),
    NET_QOS_SWITCH("nqos", "T"),
    RTO_BOUND_A("rtoa", "1200"),
    RTO_BOUND_B("rtob", "2500"),
    RTO_BOUND_C("rtoc", "3500"),
    SPEED_BOUND_A("sba", "42"),
    SPEED_BOUND_B("sbb", "3.6"),
    SPEED_BOUND_C("sbc", "0.384"),
    NET_QOS_INTERFER("qosif", "F"),
    GET_VPN_INTER_SWITCH("gvis", "T"),
    TCPINFO_LOG_UPLOAD("tlu", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    EXPORT_RES_DATA_LIMIT("erdl", "2048"),
    HTTP_WRITE_TIMEOUT_SWITCH("hwts", "T"),
    DOWNLOAD_GZIP_CHECK("dwgc", "T"),
    DJG_RESPONSE_PRECHECK("djgpc", "T"),
    GO_SPDY_APIS("gospa", ""),
    DB_STORAGE_SWITCH("dbss", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    DEBUG_HW_FIRST_USE_PROXY("dhwfup", "T"),
    ZSTD_GRAY("zg", "0,0"),
    ZSTD_SPANNER_SWITCH("zss", "1"),
    ZSTD_WHITE_LIST("zw", ""),
    ZSTD_WHITE_LIST_SWITCH("zs", "1"),
    ZSTD_DOWNGRADE_PERIOD("zdp", "7200"),
    ZSTD_BLACK_LIST("zb", ""),
    ZSTD_TOPLIMIT("zt", "2147483647"),
    IGNORE_NETWORK_STATE("ignnetst", "T"),
    LBS_DUMP("lbsdump", "0"),
    LBS_LEVEL("lbslvl", "1"),
    RPC_REQSIZE_LIMIT("reqlim", "200000"),
    RPC_RESSIZE_LIMIT("reslim", "200000"),
    ADD_WUA_HEADER("wua", "T"),
    LOGIN_INTERCEPTOR("loginter3", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    LOGIN_REFRESH_SWITCH("lrs", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    GO_URLCONNECTION_SWITCH("gourlconnect2", "0"),
    URLCONNECTION_HOST_LIST("urlhostlist", "alipayobjects,alicdn,http2.akamai.com"),
    RPC_GO_H2_SWITCH("rpcgoh2", "0"),
    RPC_H2_LIST("rpch2list", "alipay.cdp.space.queryBySpaceCode4Pb,ant.abtest.configlite"),
    H2_DOWNGRADE_SWITCH("h2downsw", "T"),
    H2_RPC_GWHOST("h2gwhost", "mobilegwspdy.alipay.com"),
    MDAP_APIS("mdapapi", "alipay.loggw.log.reallog"),
    LOW_VERSION_ENABLE_SSL("lowenabletls", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    USE_BIFROST("am_ub", "0,0"),
    BIFROST_BLACK_LIST_BRAND("am_bbb", ""),
    BIFROST_BLACK_LIST_MODEL("am_bbm", ""),
    BIFROST_BLACK_LIST_CPU("am_bbc", ""),
    BIFROST_BLACK_LIST_SDK("am_bbs", ""),
    SHORTLINK_ONLY_RPCLIST("b_sorl", ""),
    BIFROST_USE_H2("buh2", "0,0"),
    BIFROST_H2_URL("h2url", "myh2.alipay.com:443"),
    BIFROST_DISABLSE_RPC_DOWNGRADE("bdrd", "0,0"),
    SUPPORT_ONLY_PUSH_SWITCH("sops", "0"),
    SUPPORT_AUTO_UPGRADE_SWITCH("saus", SuperId.BIT_2_REALTIMEBUS_BUSSTATION),
    DURATION_PROCESS_SURVIVAL("durProcSur", "180000"),
    UPGRADLE_INTERVAL("upgInt", "1200000"),
    DOWNGRADE_SCEN_TRIG_UPGRADE_INTERVAL("dstui", "900000"),
    SYSTEM_LOGGER_ENABLE("sle", "F"),
    RPC_SELF_ENCTYPT("rpcencrypt", "T"),
    SM4_ENCRYPT("sm4encrypt", "T"),
    LAST_ITEM("$k", "$v");
    
    private String a;
    private String b;

    private TransportConfigureItem(String configKey, String configValue) {
        this.b = configValue;
        this.a = configKey;
    }

    public final String getConfigName() {
        return this.a;
    }

    public final String getStringValue() {
        return this.b;
    }

    public final long getLongValue() {
        try {
            return Long.parseLong(this.b);
        } catch (Exception e) {
            LogCatUtil.warn((String) "SpdyConfigItem", (Throwable) e);
            return -1;
        }
    }

    public final int getIntValue() {
        try {
            return Integer.parseInt(this.b);
        } catch (Exception e) {
            LogCatUtil.warn((String) "SpdyConfigItem", (Throwable) e);
            return -1;
        }
    }

    public final double getDoubleValue() {
        try {
            return Double.parseDouble(this.b);
        } catch (Throwable ex) {
            LogCatUtil.error((String) "TransportConfigureItem", ex);
            return -1.0d;
        }
    }

    public final void setValue(String value) {
        this.b = value;
    }
}
