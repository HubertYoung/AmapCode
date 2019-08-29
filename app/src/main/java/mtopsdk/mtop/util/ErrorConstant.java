package mtopsdk.mtop.util;

import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

public final class ErrorConstant {
    static HashMap<String, String> a = new HashMap<>(128);
    static HashMap<String, String> b = new HashMap<>(24);
    static HashMap<String, String> c;

    public interface ErrorMappingType {

        @Retention(RetentionPolicy.SOURCE)
        public @interface Definition {
        }
    }

    public interface MappingMsg {

        @Retention(RetentionPolicy.SOURCE)
        public @interface Definition {
        }
    }

    static {
        HashMap<String, String> hashMap = new HashMap<>(64);
        c = hashMap;
        hashMap.put("FAIL_SYS_API_STOP_SERVICE", "ES10000");
        c.put("FAIL_SYS_SM_ODD_REQUEST", "ES10001");
        c.put("FAIL_SYS_API_NOT_FOUNDED", "ES10002");
        c.put("FAIL_SYS_SESSION_EXPIRED", "ES10003");
        c.put("FAIL_SYS_SYSTEM_BUSY_ERROR", "ES10004");
        c.put("FAIL_SYS_SERVLET_ASYNC_START_FAIL", "ES10005");
        c.put("FAIL_SYS_FLOWLIMIT", "ES10006");
        c.put("FAIL_SYS_API_UNAUTHORIZED", "ES10007");
        c.put("FAIL_SYS_PROTOPARAM_MISSED", "ES10008");
        c.put("FAIL_SYS_PROTOVER_MISSED", "ES10009");
        c.put("FAIL_SYS_REQUEST_EXPIRED", "ES10010");
        c.put("FAIL_SYS_ILEGEL_SIGN", "ES10011");
        c.put("FAIL_SYS_INVALID_HTTP_METHOD", "ES10012");
        c.put("FAIL_SYS_BADARGUMENT_T", "ES10013");
        c.put("FAIL_SYS_UNKNOWN_APP", "ES10014");
        c.put("FAIL_SYS_INTERNAL_FAULT", "ES10015");
        c.put("FAIL_SYS_TRAFFIC_LIMIT", "ES10016");
        c.put("FAIL_SYS_BIZPARAM_TYPE_ERROR", "ES10017");
        c.put("FAIL_SYS_BIZPARAM_MISSED", "ES10018");
        c.put("FAIL_SYS_TOPAUTHPARAM_MISSED", "ES10019");
        c.put("FAIL_SYS_TOPAUTH_FAILED", "ES10020");
        c.put("FAIL_SYS_TOPAUTH_ACCESSTOKENEXPIRED_ERROR", "ES10021");
        c.put("FAIL_SYS_TOPAUTH_TRAFFICLIMIT_ERROR", "ES10022");
        c.put("FAIL_SYS_TOPUNAUTHAPI_ERROR", "ES10023");
        c.put("FAIL_SYS_TOPAUTH_FAULT", "ES10024");
        c.put("FAIL_SYS_RETMISSED_ERROR", "ES10025");
        c.put("FAIL_SYS_PARAMINVALID_ERROR", "ES10026");
        c.put("SYSTEM_ERROR", "ES10027");
        c.put("FAIL_SYS_UNAUTHORIZED_ENTRANCE", "ES10028");
        c.put("FAIL_SYS_SESSION_ERROR", "ES10029");
        c.put("FAIL_SYS_MT_ODD_REQUEST", "ES10030");
        c.put("FAIL_SYS_EXPIRED_REQUEST", "ES10031");
        c.put("FAIL_SYS_PORTOCOLPARAM_INVALID", "ES10032");
        c.put("FAIL_SYS_INVALID_PROTOCOLVERSION", "ES10033");
        c.put("FAIL_SYS_ILLEGAL_ARGUMENT_TTID", "ES10034");
        c.put("FAIL_SYS_PARAM_MISSING", "ES10035");
        c.put("FAIL_SYS_PARAM_FORMAT_ERROR", "ES10036");
        c.put("FAIL_SYS_ILLEGAL_ARGUMENT_TTID", "ES10034");
        c.put("FAIL_SYS_ILLEGAL_ACCESS_TOKEN", "ES10037");
        c.put("FAIL_SYS_ACCESS_TOKEN_STOP_SERVICE", "ES10038");
        c.put("FAIL_SYS_ACCESS_TOKEN_INTERNAL_FAULT", "ES10039");
        c.put("FAIL_SYS_ACCESS_TOKEN_TRAFFIC_LIMIT", "ES10040");
        c.put("FAIL_SYS_ACCESS_TOKEN_EXPIRED", "ES10041");
        c.put("FAIL_SYS_ACCESS_TOKEN_PARAM_INVALID", "ES10042");
        c.put("FAIL_SYS_ACCESS_TOKEN_UNKNOWN_ERROR", "ES10043");
        c.put("FAIL_SYS_REQUEST_QUEUED", "ES10044");
        c.put("FAIL_SYS_SERVICE_NOT_EXIST", "ES20000");
        c.put("FAIL_SYS_SERVICE_TIMEOUT", "ES20001");
        c.put("FAIL_SYS_SERVICE_FAULT", "ES20002");
        c.put("FAIL_SYS_HTTP_QUERYIP_ERROR", "ES30000");
        c.put("FAIL_SYS_HTTP_REQUESTSUBMIT_FAILED", "ES30001");
        c.put("FAIL_SYS_HTTP_INVOKE_ERROR", "ES30002");
        c.put("FAIL_SYS_HTTP_RESPONSE_TIMEOUT", "ES30003");
        c.put("FAIL_SYS_HTTP_CONNECT_TIMEOUT", "ES30004");
        c.put("UNKNOWN_FAIL_CODE", "ES40000");
        c.put("FAIL_SYS_HSF_THROWN_EXCEPTION", "ES40001");
        c.put("FAIL_SYS_SERVICE_INNER_FAULT", "ES40002");
        c.put("FAIL_SYS_HTTP_RESULT_FIELDMISSED", "ES40003");
        c.put("FAIL_SYS_SERVICE_INNER_FAULT", "ES40002");
        b.put("ANDROID_SYS_NO_NETWORK", "EC10000");
        b.put("ANDROID_SYS_NETWORK_ERROR", "EC10001");
        b.put("ANDROID_SYS_JSONDATA_BLANK", "EC30000");
        b.put("ANDROID_SYS_JSONDATA_PARSE_ERROR", "EC30001");
        b.put("ANDROID_SYS_MTOPSDK_INIT_ERROR", "EC40000");
        b.put("ANDROID_SYS_MTOPCONTEXT_INIT_ERROR", "EC40001");
        b.put("ANDROID_SYS_GENERATE_MTOP_SIGN_ERROR", "EC40002");
        b.put("ANDROID_SYS_NETWORK_REQUEST_CONVERT_ERROR", "EC40003");
        b.put("ANDROID_SYS_API_FLOW_LIMIT_LOCKED", "EC20000");
        b.put("ANDROID_SYS_API_41X_ANTI_ATTACK", "EC20001");
        b.put("ANDROID_SYS_MTOP_APICALL_ASYNC_TIMEOUT", "EC40004");
        b.put("ANDROID_SYS_INIT_MTOP_ISIGN_ERROR", "EC40005");
        b.put("ANDROID_SYS_MTOP_MISS_CALL_FACTORY", "EC40006");
        b.put("ANDROID_SYS_LOGIN_FAIL", "EC40007");
        b.put("ANDROID_SYS_LOGIN_CANCEL", "EC40008");
        b.put("ANDROID_SYS_ILLEGAL_JSPARAM_ERROR", "EC40009");
        b.put("ANDROID_SYS_PARSE_JSPARAM_ERROR", "EC40010");
        b.put("ANDROID_SYS_BUILD_PROTOCOL_PARAMS_ERROR", "EC40011");
        a.putAll(c);
        a.putAll(b);
        a.put(GenBusCodeService.CODE_SUCESS, GenBusCodeService.CODE_SUCESS);
    }

    public static String a(String str) {
        return a.get(str);
    }

    public static boolean b(String str) {
        return c.containsKey(str);
    }

    public static boolean c(String str) {
        return fdd.b(str) || b.containsKey(str);
    }

    public static boolean d(String str) {
        return "ANDROID_SYS_NETWORK_ERROR".equals(str) || "ANDROID_SYS_NO_NETWORK".equals(str);
    }

    public static boolean e(String str) {
        return "ANDROID_SYS_NO_NETWORK".equals(str);
    }

    public static boolean f(String str) {
        return "FAIL_SYS_SESSION_EXPIRED".equals(str) || "ANDROID_SYS_LOGIN_FAIL".equals(str);
    }

    public static boolean g(String str) {
        return "FAIL_SYS_ILEGEL_SIGN".equals(str);
    }

    public static boolean h(String str) {
        return GenBusCodeService.CODE_SUCESS.equals(str);
    }

    public static boolean i(String str) {
        return "ANDROID_SYS_API_41X_ANTI_ATTACK".equals(str);
    }

    public static boolean j(String str) {
        return "ANDROID_SYS_API_FLOW_LIMIT_LOCKED".equals(str);
    }

    public static boolean k(String str) {
        return "FAIL_SYS_EXPIRED_REQUEST".equals(str) || "FAIL_SYS_REQUEST_EXPIRED".equals(str);
    }

    public static boolean l(String str) {
        return str != null && (c.containsKey(str) || str.startsWith("FAIL_SYS_"));
    }

    public static String a(int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append("A");
        sb.append(str);
        return sb.toString();
    }
}
