package com.alipay.mobile.common.nbnet.biz.download;

import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetProtocolException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetRetryException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetServerException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetVerifyException;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.platform.DeviceInfoManagerFactory;
import com.alipay.mobile.common.nbnet.biz.qoe.NetworkQoeManagerFactory;
import com.alipay.mobile.common.nbnet.biz.util.NBNetConfigUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.SSLException;

public class DownloadRetryPolicy {
    private static final String a = DownloadRetryPolicy.class.getSimpleName();
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private Throwable g;
    private final AtomicBoolean h = new AtomicBoolean(false);
    private boolean i = false;

    public final void a(boolean isTimeout) {
        this.h.set(true);
        this.i = isTimeout;
    }

    public final boolean a(Throwable exception) {
        NBNetLogCat.d(a, "logicErrorCount=" + this.d + ", bytesTransferred=" + this.b + ", networkErrorCount=" + this.c + ", exception=" + exception.getClass().getSimpleName() + ":" + exception.toString());
        this.g = exception;
        if (this.h.get()) {
            if (this.i) {
                throw new NBNetRetryException(-23, "Download time out", this.g);
            }
            throw new NBNetRetryException(-8, "retry is canceled", this.g);
        } else if (DeviceInfoManagerFactory.a().b() && !NetworkUtils.isNetworkAvailable(NBNetEnvUtils.a())) {
            NBNetLogCat.a(a, (String) "Background running, network is unreachable not retry !!");
            if (exception instanceof NBNetException) {
                throw ((NBNetException) this.g);
            }
            throw new NBNetException((String) "Unknow Error", this.g);
        } else if (Thread.currentThread().isInterrupted()) {
            throw new NBNetRetryException(-22, "Thread interrupted", this.g);
        } else if ((exception instanceof NBNetProtocolException) || (exception instanceof NBNetServerException)) {
            this.d++;
            a((NBNetException) exception);
            d();
            return false;
        } else if (exception instanceof NBNetVerifyException) {
            this.e++;
            c();
            return true;
        } else if ((exception instanceof SocketTimeoutException) || (exception instanceof ConnectException) || (exception instanceof SocketException) || (exception instanceof SSLException) || (exception instanceof IOException) || (exception instanceof EOFException)) {
            this.c++;
            if (exception instanceof InterruptedIOException) {
                this.b += ((InterruptedIOException) exception).bytesTransferred;
            }
            NetworkQoeManagerFactory.a().a();
            e();
            a();
            return false;
        } else {
            this.f++;
            b();
            return false;
        }
    }

    private static void a(NBNetException exception) {
        NBNetException nbNetException = exception;
        switch (exception.getErrorCode()) {
            case 404:
            case 429:
            case 901:
            case 903:
            case 1006:
            case 1007:
                throw nbNetException;
            default:
                return;
        }
    }

    private void a() {
        if (this.b >= NBNetConfigUtil.g()) {
            throw new NBNetRetryException(-7, "bytesTransferred reach " + NBNetConfigUtil.g());
        }
    }

    private void b() {
        if (this.f >= 3) {
            throw new NBNetRetryException("unknow exception:" + this.g, this.g);
        }
    }

    private void c() {
        if (this.e < NBNetConfigUtil.e()) {
            return;
        }
        if (this.g instanceof NBNetException) {
            throw ((NBNetException) this.g);
        }
        throw new NBNetRetryException("Verify error retry reach " + NBNetConfigUtil.d() + ", " + this.g.toString(), this.g);
    }

    private void d() {
        if (this.d < NBNetConfigUtil.d()) {
            return;
        }
        if (this.g instanceof NBNetException) {
            throw ((NBNetException) this.g);
        }
        throw new NBNetRetryException("logic error retry reach " + NBNetConfigUtil.d() + ", " + this.g.toString(), this.g);
    }

    private void e() {
        if (this.c < NBNetConfigUtil.f()) {
            return;
        }
        if (this.g instanceof NBNetException) {
            throw ((NBNetException) this.g);
        }
        throw new NBNetRetryException("network error retry reach " + NBNetConfigUtil.f() + ", " + this.g.toString(), this.g);
    }
}
