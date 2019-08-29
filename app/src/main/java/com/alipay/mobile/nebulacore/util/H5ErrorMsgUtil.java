package com.alipay.mobile.nebulacore.util;

import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5ErrorMsgUtil {
    public static final int H5_NETWORK_AUTH_ERROR = 8;
    public static final int H5_NETWORK_CANCEL_ERROR = 13;
    public static final int H5_NETWORK_CONNECTION_EXCEPTION = 3;
    public static final int H5_NETWORK_DNS_ERROR = 9;
    public static final int H5_NETWORK_IO_EXCEPTION = 6;
    public static final int H5_NETWORK_SCHEDULE_ERROR = 7;
    public static final int H5_NETWORK_SERVER_EXCEPTION = 5;
    public static final int H5_NETWORK_SOCKET_EXCEPTION = 4;
    public static final int H5_NETWORK_SSL_EXCEPTION = 2;
    public static final int H5_NETWORK_TRAFIC_BEYOND_LIMIT = 11;
    public static final int H5_NETWORK_UNAVAILABLE = 1;
    public static final int H5_NETWORK_UNKNOWN_ERROR = 0;
    public static final int H5_UC_NETWORK_UNAVAILABLE = -80;
    public static final int H5_URL_ERROR = 10;

    public static String getErrorMsg(int statusCode, boolean isSubtitle) {
        if (statusCode == -6 || statusCode == -7 || statusCode == -80 || statusCode == 1) {
            return H5Environment.getResources().getString(R.string.h5_network_unavailable);
        }
        if (statusCode == -2 || statusCode == 9) {
            return H5Environment.getResources().getString(R.string.h5_DNS_resolution_failed_new);
        }
        if (statusCode == -4 || statusCode == -12 || statusCode == -5 || statusCode == -10 || statusCode == -11 || statusCode == 10 || statusCode == 8 || statusCode == 7) {
            return H5Environment.getResources().getString(R.string.h5_URL_error_new);
        }
        if (statusCode == -8 || statusCode == 3 || statusCode == 4) {
            return H5Environment.getResources().getString(R.string.h5_error_timeout_new);
        }
        if (statusCode == -9) {
            return H5Environment.getResources().getString(R.string.h5_redirect_loop);
        }
        if (statusCode == -1 || statusCode == -3 || statusCode == -13 || statusCode == -14 || statusCode == -15 || statusCode == 0 || statusCode == 5 || statusCode == 6 || statusCode == 11 || statusCode == 13 || statusCode == 2) {
            return H5Environment.getResources().getString(R.string.h5_network_error_new);
        }
        if (statusCode == 400 || statusCode == 503 || statusCode == 401 || statusCode == 409 || statusCode == 410 || statusCode == 411 || statusCode == 412 || statusCode == 500 || statusCode == 501 || statusCode == 502) {
            return H5Environment.getResources().getString(R.string.h5_server_error_new);
        }
        return H5Environment.getResources().getString(R.string.h5_unknown_error);
    }
}
