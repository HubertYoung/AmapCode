package com.alipay.android.phone.inside.bizadapter.ex;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.alipay.android.phone.inside.bizadapter.R;
import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.ex.ExceptionEnum;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.common.logging.api.LogContext;
import java.lang.Thread.UncaughtExceptionHandler;

public class InsideExceptionHandler implements UncaughtExceptionHandler {
    private static InsideExceptionHandler b;
    private static UncaughtExceptionHandler c;
    /* access modifiers changed from: private */
    public final Context a;

    private InsideExceptionHandler(Context context) {
        this.a = context;
    }

    public static synchronized InsideExceptionHandler a(Context context) {
        InsideExceptionHandler insideExceptionHandler;
        synchronized (InsideExceptionHandler.class) {
            try {
                if (b == null) {
                    b = new InsideExceptionHandler(context);
                }
                insideExceptionHandler = b;
            }
        }
        return insideExceptionHandler;
    }

    public final void a() {
        synchronized (InsideExceptionHandler.class) {
            c = Looper.getMainLooper().getThread().getUncaughtExceptionHandler();
            Looper.getMainLooper().getThread().setUncaughtExceptionHandler(this);
        }
    }

    public static void b() {
        synchronized (InsideExceptionHandler.class) {
            Looper.getMainLooper().getThread().setUncaughtExceptionHandler(c);
            c = null;
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        LoggerFactory.f().c((String) "inside_uncaught", th);
        if ((th instanceof RuntimeException) && th.getCause() != null && (th.getCause() instanceof RpcException)) {
            th = th.getCause();
        }
        if ((th instanceof RpcException) && !"main".equalsIgnoreCase(Thread.currentThread().getName())) {
            RpcException rpcException = (RpcException) th;
            if (rpcException != null) {
                int code = rpcException.getCode();
                String str = "";
                LoggerFactory.f().b((String) "rpcExceptionHandler", "error code = ".concat(String.valueOf(code)));
                if (AppInfo.a().c().equalsIgnoreCase("test") || AppInfo.a().c().equalsIgnoreCase("dev") || AppInfo.a().c().equalsIgnoreCase(LogContext.RELEASETYPE_RC)) {
                    StringBuilder sb = new StringBuilder("\n [");
                    sb.append(rpcException.getOperationType());
                    sb.append("] ErrorCode=");
                    sb.append(code);
                    str = sb.toString();
                }
                if (code != 1002) {
                    if (code != 6666) {
                        switch (code) {
                            case 0:
                            case 1:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 13:
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(this.a.getString(R.string.b));
                                sb2.append(str);
                                a(sb2.toString(), 1);
                                break;
                            case 2:
                                a(this.a.getString(R.string.a), 1);
                                break;
                            case 3:
                            case 11:
                            case 12:
                                break;
                            case 4:
                            case 5:
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(this.a.getString(R.string.d));
                                sb3.append(str);
                                a(sb3.toString(), 1);
                                break;
                            default:
                                switch (code) {
                                    case 15:
                                        break;
                                    case 16:
                                        StringBuilder sb4 = new StringBuilder();
                                        sb4.append(this.a.getString(R.string.c));
                                        sb4.append(str);
                                        a(sb4.toString(), 1);
                                        break;
                                    default:
                                        switch (code) {
                                            case 3000:
                                                break;
                                            case 3001:
                                                StringBuilder sb5 = new StringBuilder();
                                                sb5.append(this.a.getString(R.string.e));
                                                sb5.append(str);
                                                a(sb5.toString(), 1);
                                                break;
                                            default:
                                                switch (code) {
                                                    case 4001:
                                                    case 4002:
                                                    case 4003:
                                                        break;
                                                    default:
                                                        switch (code) {
                                                            case 6000:
                                                            case 6001:
                                                                break;
                                                            default:
                                                                if (code < 400 || code >= 500) {
                                                                    if (code >= 100 && code < 600) {
                                                                        StringBuilder sb6 = new StringBuilder();
                                                                        sb6.append(this.a.getString(R.string.c));
                                                                        sb6.append(str);
                                                                        a(sb6.toString(), 1);
                                                                        break;
                                                                    }
                                                                } else {
                                                                    StringBuilder sb7 = new StringBuilder();
                                                                    sb7.append(this.a.getString(R.string.a));
                                                                    sb7.append(str);
                                                                    a(sb7.toString(), 1);
                                                                    break;
                                                                }
                                                                break;
                                                        }
                                                }
                                                StringBuilder sb52 = new StringBuilder();
                                                sb52.append(this.a.getString(R.string.e));
                                                sb52.append(str);
                                                a(sb52.toString(), 1);
                                                break;
                                        }
                                }
                                a(this.a.getString(R.string.a), 1);
                                break;
                        }
                    }
                } else {
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(rpcException.getMsg());
                    sb8.append(str);
                    a(sb8.toString(), 1);
                }
            }
        }
        LoggerFactory.e().a(ExceptionEnum.CRASH, (String) "crash", (String) "UncaughtException", th);
    }

    private void a(final String str, final int i) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Toast.makeText(InsideExceptionHandler.this.a, str, i).show();
            }
        });
    }
}
