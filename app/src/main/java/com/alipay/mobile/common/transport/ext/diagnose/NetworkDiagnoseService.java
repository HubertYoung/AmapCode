package com.alipay.mobile.common.transport.ext.diagnose;

public interface NetworkDiagnoseService {
    public static final int STATE_CONNECTING_SERVER = 1;
    public static final int STATE_FAIL = 5;
    public static final int STATE_SUCCESS = 4;
    public static final int STATE_TRACE_ROUTING = 2;
    public static final int STATE_UPLOADING = 3;

    void addDiagnoseListener(NetworkDiagnoseListener networkDiagnoseListener);

    void cancel();

    boolean isCanDiagnose();

    void removeDiagnoseListener(NetworkDiagnoseListener networkDiagnoseListener);

    void startDiagnose();
}
