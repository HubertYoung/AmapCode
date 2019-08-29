package com.alipay.mobile.common.nbnet.biz.upload;

import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetServerLimitFlowException;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.qoe.NetworkQoeManagerFactory;
import com.alipay.mobile.common.nbnet.biz.token.TokenManager;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetConfigUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.nbnet.biz.util.ServerLimitedFlowHelper;
import com.alipay.mobile.common.nbnet.biz.util.URLConfigUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;

public class UploadRetryHandler {
    private byte a = 0;
    private byte b = 0;
    private byte c = 0;
    private byte d = 0;
    private byte e = 0;
    private byte f = 0;
    private byte g = 0;

    public final boolean a(Throwable throwable, UploadActionSession uplodaAction) {
        boolean isRetry = b(throwable, uplodaAction);
        NBNetLogCat.a((String) "UploadRetryHandler", "retryUpload.  isRetry=" + isRetry);
        return isRetry;
    }

    private boolean b(Throwable throwable, UploadActionSession uplodaAction) {
        if (throwable == null) {
            return false;
        }
        NBNetLogCat.a((String) "UploadRetryHandler", "retryUpload. exception: " + throwable.toString() + ", sumExecuteCount: " + this.a);
        this.a = (byte) (this.a + 1);
        if (!a(this.a, NBNetConfigUtil.j())) {
            return false;
        }
        Throwable rootCause = MiscUtils.getRootCause(throwable);
        if (rootCause instanceof NBNetException) {
            NBNetException nbNetException = (NBNetException) rootCause;
            if (nbNetException.getErrorCode() > 0) {
                return a(uplodaAction, nbNetException);
            }
            if (nbNetException.getErrorCode() == -7) {
                NBNetLogCat.a((String) "UploadRetryHandler", (String) "SC_FUSING_ERROR, no retry");
                return false;
            } else if (nbNetException.getErrorCode() == -8) {
                NBNetLogCat.a((String) "UploadRetryHandler", (String) "SC_MANUAL_CANCEL_ERROR, no retry");
                return false;
            } else if (nbNetException.getErrorCode() == -3 || nbNetException.getErrorCode() == -11) {
                return a(uplodaAction, nbNetException.getErrorCode());
            }
        } else if (a(rootCause)) {
            return b();
        } else {
            if (NBNetCommonUtil.a(rootCause)) {
                return a();
            }
        }
        return c();
    }

    private boolean a() {
        NetworkQoeManagerFactory.a().a();
        int uploadNetworkExecCount = NBNetConfigUtil.k();
        this.b = (byte) (this.b + 1);
        if (!a(this.b, uploadNetworkExecCount)) {
            NBNetLogCat.d("UploadRetryHandler", "processNetworkException.  networkExecuteCount greater than " + uploadNetworkExecCount);
            return false;
        }
        NBNetLogCat.a((String) "UploadRetryHandler", "networkExecuteCount: " + this.b);
        return true;
    }

    private boolean b() {
        int uploadFileExecCount = NBNetConfigUtil.l();
        this.c = (byte) (this.c + 1);
        if (!a(this.c, uploadFileExecCount)) {
            NBNetLogCat.d("UploadRetryHandler", "processFileException.  fileExecuteCount greater than " + uploadFileExecCount);
            return false;
        }
        NBNetLogCat.a((String) "UploadRetryHandler", "fileExecuteCount: " + this.c);
        return true;
    }

    private boolean c() {
        int uploadUnknowExecCount = NBNetConfigUtil.m();
        this.d = (byte) (this.d + 1);
        if (!a(this.d, uploadUnknowExecCount)) {
            NBNetLogCat.d("UploadRetryHandler", "processUnknowException.  unknowExecuteCount greater than " + uploadUnknowExecCount);
            return false;
        }
        NBNetLogCat.a((String) "UploadRetryHandler", "unknowExecuteCount: " + this.d);
        return true;
    }

    private boolean a(UploadActionSession uploadActionSesession, NBNetException nbNetException) {
        if (nbNetException instanceof NBNetServerLimitFlowException) {
            NBNetServerLimitFlowException serverLimitFlowException = (NBNetServerLimitFlowException) nbNetException;
            if (serverLimitFlowException.getSleep() > 0) {
                ServerLimitedFlowHelper.a(serverLimitFlowException);
            }
            NBNetLogCat.d("UploadRetryHandler", "processServerException. Upload server limited.");
            return false;
        }
        int uploadServerExecCount = NBNetConfigUtil.n();
        a(uploadActionSesession);
        this.e = (byte) (this.e + 1);
        if (!a(this.e, uploadServerExecCount)) {
            NBNetLogCat.d("UploadRetryHandler", "processServerException.  serverExecuteCount greater than " + uploadServerExecCount);
            return false;
        }
        NBNetLogCat.a((String) "UploadRetryHandler", "serverExecuteCount: " + this.e);
        if (this.e > 5) {
            b(uploadActionSesession);
        }
        if (nbNetException.getErrorCode() == 5002) {
            this.g = (byte) (this.g + 1);
            d();
        }
        return true;
    }

    private boolean a(UploadActionSession actionSession, int errorCode) {
        int uploadProtocolExecCount = NBNetConfigUtil.o();
        this.f = (byte) (this.f + 1);
        a(actionSession);
        if (!a(this.f, uploadProtocolExecCount)) {
            NBNetLogCat.d("UploadRetryHandler", "processServerException.  serverExecuteCount greater than " + uploadProtocolExecCount);
            return false;
        }
        NBNetLogCat.a((String) "UploadRetryHandler", "protocolExecuteCount: " + this.f);
        if (this.f >= 5 || errorCode == -11) {
            b(actionSession);
        }
        return true;
    }

    private static boolean a(Throwable rootCause) {
        return MiscUtils.getRootCause(rootCause).getClass().getName().contains("File");
    }

    private static boolean a(byte executeCount, int maxExecuteCount) {
        NBNetLogCat.a((String) "UploadRetryHandler", "calcExecuteCount. executeCount=[" + executeCount + "] maxExecuteCount=[" + maxExecuteCount + "]");
        return executeCount <= maxExecuteCount;
    }

    private void d() {
        TokenManager tokenManager = TokenManager.a();
        if (this.g <= 2) {
            TokenManager.e();
        } else {
            TokenManager.d();
        }
        tokenManager.c();
    }

    private static void a(UploadActionSession actionSession) {
        try {
            if (MiscUtils.isDebugger(NBNetEnvUtils.a()) && URLConfigUtil.b().getHost().contains("alipay.net")) {
                LogCatUtil.warn((String) "UploadRetryHandler", (String) "[enableUseSecureUpload] Don't use secure upload. debuggable and *.alipay.net");
                return;
            }
        } catch (Throwable e2) {
            LogCatUtil.error("UploadRetryHandler", "[enableUseSecureUpload] getUploadServerURL Exception: " + e2.toString(), e2);
        }
        actionSession.d = true;
    }

    private static void b(UploadActionSession actionSession) {
        if (actionSession.c()) {
            NBNetLogCat.a((String) "UploadRetryHandler", "switchUploadActionForRetry. update action: " + actionSession.a + " -> 1");
            actionSession.h();
            actionSession.b = null;
            return;
        }
        NBNetLogCat.a((String) "UploadRetryHandler", "switchUploadActionForRetry. update action: " + actionSession.a + " -> 0");
        actionSession.g();
        actionSession.b = null;
    }
}
