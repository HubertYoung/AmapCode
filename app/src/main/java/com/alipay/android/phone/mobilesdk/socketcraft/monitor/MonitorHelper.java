package com.alipay.android.phone.mobilesdk.socketcraft.monitor;

import com.alipay.android.phone.mobilesdk.socketcraft.api.DefaultWebSocketClient;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.monitor.MonitorPrinterFactory;
import com.alipay.android.phone.mobilesdk.socketcraft.util.StringUtils;

public class MonitorHelper {
    private SimpleStatistical simpleStatistical;
    private DefaultWebSocketClient webSocketClient;

    public MonitorHelper(DefaultWebSocketClient defaultWebSocketClient) {
        this.webSocketClient = defaultWebSocketClient;
    }

    public void printConnMonitorLog() {
        getSimpleStatistical().endConnAllTime = System.currentTimeMillis();
        MonitorModel monitorModel = createMonitorModel(MonitorItemConstants.WS_MONITOR_TITLE_CONN);
        monitorModel.appendDnsTime(String.valueOf(getSimpleStatistical().dnsTime));
        monitorModel.appendTcpTime(String.valueOf(getSimpleStatistical().tcpTime));
        monitorModel.appendSSLTime(String.valueOf(getSimpleStatistical().sslTime));
        monitorModel.appendWsHsTime(String.valueOf(getSimpleStatistical().wsHsTime));
        monitorModel.appendTargetHost(getSimpleStatistical().targetHost);
        monitorModel.appendAllTime(String.valueOf(getSimpleStatistical().getConnAllTime()));
        monitorModel.appendResult(true);
        MonitorPrinterFactory.getInstance().print(monitorModel);
    }

    public void printDisconnMonitorLog() {
        getSimpleStatistical().disconnectedTime = System.currentTimeMillis();
        MonitorModel monitorModel = createMonitorModel(MonitorItemConstants.WS_MONITOR_TITLE_DISCONN);
        monitorModel.appendDownMsgCount(String.valueOf(getSimpleStatistical().recvMsgCount));
        monitorModel.appendDownMsgLens(String.valueOf(getSimpleStatistical().recvMsgLenArray));
        monitorModel.appendUpMsgCount(String.valueOf(getSimpleStatistical().sendMsgCount));
        monitorModel.appendUpMsgLens(String.valueOf(getSimpleStatistical().sendMsgLenArray));
        monitorModel.appendLiveTime(String.valueOf(getSimpleStatistical().getLinkLiveTime()));
        MonitorPrinterFactory.getInstance().print(monitorModel);
        this.simpleStatistical = null;
    }

    public void printErrorMonitorLog(String code, String errMsg) {
        MonitorModel monitorModel = createMonitorModel("error");
        monitorModel.appendCode(code);
        monitorModel.appendErrMsg(errMsg);
        MonitorPrinterFactory.getInstance().print(monitorModel);
    }

    public MonitorModel createMonitorModel(String title) {
        MonitorModel monitorModel = new MonitorModel();
        monitorModel.logTitle = title;
        monitorModel.appendAppId(this.webSocketClient.getBizUniqId());
        monitorModel.appendUrl(this.webSocketClient.getUrl());
        return monitorModel;
    }

    public void recordMonitorOfRecvMsg(int len) {
        SimpleStatistical simpleStatistical2 = getSimpleStatistical();
        simpleStatistical2.recvMsgCount++;
        if (simpleStatistical2.recvMsgLenArray == null || simpleStatistical2.recvMsgLenArray.length() <= 0) {
            simpleStatistical2.recvMsgLenArray = String.valueOf(len);
        } else {
            simpleStatistical2.recvMsgLenArray += "_" + len;
        }
    }

    public void recordMonitorOfSndMsg(int len) {
        SimpleStatistical simpleStatistical2 = getSimpleStatistical();
        simpleStatistical2.sendMsgCount++;
        if (simpleStatistical2.sendMsgLenArray == null || simpleStatistical2.sendMsgLenArray.length() <= 0) {
            simpleStatistical2.sendMsgLenArray = String.valueOf(len);
        } else {
            simpleStatistical2.sendMsgLenArray += "_" + len;
        }
    }

    public SimpleStatistical getSimpleStatistical() {
        if (this.simpleStatistical != null) {
            return this.simpleStatistical;
        }
        this.simpleStatistical = new SimpleStatistical();
        return this.simpleStatistical;
    }

    public void recordStartConnAllTime() {
        getSimpleStatistical().startConnAllTime = System.currentTimeMillis();
    }

    public void recordConnectedTime() {
        getSimpleStatistical().connectedTime = System.currentTimeMillis();
    }

    public void recordDnsTime(long dnsTime) {
        if (dnsTime > 0) {
            getSimpleStatistical().dnsTime = dnsTime;
        }
    }

    public void recordTargetHost(String targetHost) {
        if (!StringUtils.isEmpty(targetHost) && !"null".equals(targetHost)) {
            getSimpleStatistical().targetHost = targetHost;
        }
    }

    public void recordTcpTime(long tcpTime) {
        if (tcpTime > 0) {
            getSimpleStatistical().tcpTime = tcpTime;
        }
    }

    public void recordSSLTime(long sslTime) {
        if (sslTime > 0) {
            getSimpleStatistical().sslTime = sslTime;
        }
    }

    public void recordWsHsTime(long wsHsTime) {
        if (wsHsTime > 0) {
            getSimpleStatistical().wsHsTime = wsHsTime;
        }
    }

    public final void noteTraficConsume(DataflowMonitorModel dataflowMonitorModel) {
        MonitorPrinterFactory.getInstance().noteTraficConsume(dataflowMonitorModel);
    }
}
