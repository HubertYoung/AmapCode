package com.alipay.mobile.common.transport.utils;

import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.net.Socket;

public class TcpInfoManager {
    private static boolean a;

    public native String getTcpInfo(int i);

    static {
        a = false;
        try {
            Class.forName("com.alipay.mobile.common.utils.load.LibraryLoadUtils").getMethod("loadLibrary", new Class[]{String.class, Boolean.TYPE}).invoke(null, new Object[]{"tcpinfo", Boolean.valueOf(false)});
            a = true;
            LogCatUtil.info("TcpInfoManager", "LibraryLoadUtils libtcpinfo.so success.");
        } catch (Throwable e2) {
            a = false;
            LogCatUtil.warn((String) "TcpInfoManager", "System.loadLibrary load fail. " + e2.toString());
        }
    }

    private TcpInfoManager() {
    }

    public static TcpInfo getTcpInfoSync(int fd) {
        String result = getTcpInfoStrSync(fd);
        if (TextUtils.isEmpty(result)) {
            return null;
        }
        return TcpInfo.convert(result);
    }

    public static String getTcpInfoStrSync(int fd) {
        if (fd < 0) {
            LogCatUtil.warn((String) "TcpInfoManager", "fd is smaller than 0, fd=" + fd);
            return null;
        }
        String result = new TcpInfoManager().a(fd);
        LogCatUtil.info("TcpInfoManager", "fd=" + fd + ", result=" + result);
        return result;
    }

    public static int getFdFromSocket(Socket socket) {
        if (socket != null) {
            try {
                if (!socket.isClosed()) {
                    int fd = ParcelFileDescriptor.fromSocket(socket).getFd();
                    LogCatUtil.info("TcpInfoManager", "socket fd=" + fd);
                    return fd;
                }
            } catch (Throwable e) {
                LogCatUtil.warn((String) "TcpInfoManager", "getFdFromSocket error. " + e.toString());
                return -1;
            }
        }
        LogCatUtil.warn((String) "TcpInfoManager", (String) "getFdFromSocket error, socket is null or closed. ");
        return -1;
    }

    public static TcpInfo getTcpInfoSync(Socket socket) {
        return getTcpInfoSync(getFdFromSocket(socket));
    }

    public static String getTcpInfoStrSync(Socket socket) {
        return getTcpInfoStrSync(getFdFromSocket(socket));
    }

    public static void reportTcpInfoAsync(Socket socket, String networkType, String networkName, String subType) {
        try {
            final int fd = getFdFromSocket(socket);
            if (fd > 0) {
                final String localPort = Integer.toString(socket.getLocalPort());
                final String remotePort = Integer.toString(socket.getPort());
                final String remoteIp = socket.getInetAddress().getHostAddress();
                final String localIp = socket.getLocalAddress().getHostAddress();
                final String str = networkType;
                final String str2 = networkName;
                final String str3 = subType;
                NetworkAsyncTaskExecutor.execute(new Runnable() {
                    public final void run() {
                        try {
                            if (fd <= 0) {
                                LogCatUtil.warn((String) "TcpInfoManager", (String) "fd is invalid.");
                                return;
                            }
                            String infoStr = TcpInfoManager.getTcpInfoStrSync(fd);
                            if (TcpInfo.convert(infoStr) == null) {
                                LogCatUtil.info("TcpInfoManager", "convert: null.");
                            } else {
                                TcpInfoManager.a(infoStr, remoteIp, remotePort, localIp, localPort, str, str2, str3);
                            }
                        } catch (Throwable e) {
                            LogCatUtil.warn((String) "TcpInfoManager", "reportTcpInfoAsync thread error. " + e.toString());
                        }
                    }
                });
            }
        } catch (Throwable e1) {
            LogCatUtil.warn((String) "TcpInfoManager", "reportTcpInfoAsync error. " + e1.toString());
        }
    }

    public static void reportTcpInfoAsync(int fd, String remoteIp, String remotePort, String localIp, String localPort, String networkType, String networkName, String subType) {
        final int i = fd;
        final String str = remoteIp;
        final String str2 = remotePort;
        final String str3 = localIp;
        final String str4 = localPort;
        final String str5 = networkType;
        final String str6 = networkName;
        final String str7 = subType;
        NetworkAsyncTaskExecutor.execute(new Runnable() {
            public final void run() {
                try {
                    if (i <= 0) {
                        LogCatUtil.warn((String) "TcpInfoManager", (String) "fd is invalue.");
                        return;
                    }
                    String infoStr = TcpInfoManager.getTcpInfoStrSync(i);
                    if (TcpInfo.convert(infoStr) == null) {
                        LogCatUtil.info("TcpInfoManager", "convert: null.");
                    } else {
                        TcpInfoManager.a(infoStr, str, str2, str3, str4, str5, str6, str7);
                    }
                } catch (Throwable e) {
                    LogCatUtil.warn((String) "TcpInfoManager", "reportTcpInfoAsync error. " + e.toString());
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void a(String infoStr, String remoteIp, String remotePort, String localIp, String localPort, String networkType, String networkName, String subType) {
        try {
            if (TextUtils.isEmpty(subType)) {
                subType = "mmtp_timeout";
            }
            Performance pf = new TransportPerformance();
            pf.setSubType("TCP_STACK");
            pf.setParam1(MonitorLoggerUtils.getLogBizType("TCP_STACK"));
            pf.setParam2("INFO");
            pf.setParam3(subType);
            pf.getExtPramas().put("TCPINFO", infoStr);
            pf.getExtPramas().put("IR", remoteIp);
            pf.getExtPramas().put("PR", remotePort);
            pf.getExtPramas().put("IL", localIp);
            pf.getExtPramas().put("PL", localPort);
            pf.getExtPramas().put("network_extra", networkName);
            pf.getExtPramas().put("network", networkType);
            MonitorLoggerUtils.uploadPerfLog(pf);
            LogCatUtil.info("TcpInfoManager", "tcpinfo:" + pf.toString());
        } catch (Throwable e) {
            LogCatUtil.warn((String) "TcpInfoManager", "upload error. " + e.toString());
        }
    }

    private String a(int fd) {
        if (!a) {
            return null;
        }
        try {
            String itemValue = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.TCPINFO_LOG_UPLOAD);
            if (TextUtils.isEmpty(itemValue)) {
                LogCatUtil.warn((String) "TcpInfoManager", (String) "The Tcpinfo switch value is null.");
                return null;
            } else if (MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), itemValue)) {
                return getTcpInfo(fd);
            } else {
                LogCatUtil.warn((String) "TcpInfoManager", "The Tcpinfo switch is off. value = " + itemValue);
                return null;
            }
        } catch (Throwable e) {
            LogCatUtil.warn((String) "TcpInfoManager", "getTcpinfoStr. " + e.toString());
            return null;
        }
    }
}
