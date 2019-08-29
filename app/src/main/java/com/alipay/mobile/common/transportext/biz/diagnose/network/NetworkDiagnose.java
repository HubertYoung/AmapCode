package com.alipay.mobile.common.transportext.biz.diagnose.network;

import com.alipay.mobile.common.amnet.api.AmnetNetworkDiagnoseListener;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.amnet.Storage;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.DetectInf;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Traceroute.PingInf;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;

public class NetworkDiagnose {
    static final String TAG = "DIAGNOSE-NETWORKDIAGNOSE";
    private static NetworkDiagnose singleton;
    private AmnetNetworkDiagnoseListener callback = null;
    private DetectInf[] detectInfs = null;
    private PingInf[] pingInfs = null;
    private Storage storage = null;
    private long timeFlag = 0;
    private boolean tracerouteAllowed = false;
    private int typeFlag = 0;

    public static final NetworkDiagnose instance() {
        if (singleton == null) {
            singleton = new NetworkDiagnose();
        }
        return singleton;
    }

    public void register(Storage storage2) {
        this.storage = storage2;
    }

    public void register(AmnetNetworkDiagnoseListener callback2) {
        this.callback = callback2;
    }

    public void register(long timeFlag2, int typeFlag2) {
        this.timeFlag = timeFlag2;
        this.typeFlag = typeFlag2;
    }

    public void launch() {
        NetworkDiagnoseManager networkDiagnoseManager;
        LogCatUtil.info(TAG, "[launch]begin.");
        try {
            initialize();
            if (this.detectInfs == null || this.detectInfs[0] == null) {
                LogCatUtil.info(TAG, "[launch]the configuration is null, now get default address.");
                this.detectInfs = new DetectInf[1];
                this.detectInfs[0] = new DetectInf();
                this.detectInfs[0].domain = "www.taobao.com";
                this.detectInfs[0].protocol = 0;
                this.detectInfs[0].request = "HEAD / HTTP/1.1\r\nHost: www.taobao.com\r\nContent-Length: 0\r\n\r\n";
                this.detectInfs[0].response = "HTTP/1.1 200 ";
                this.detectInfs[0].waiting = 30;
                this.detectInfs[0].trying = 1;
                this.pingInfs = new PingInf[1];
                this.pingInfs[0] = new PingInf();
                this.pingInfs[0].domain = "www.taobao.com";
                this.pingInfs[0].type = 3;
                this.pingInfs[0].threshold = 6000;
                this.pingInfs[0].waiting = 5;
                if (1 == this.typeFlag) {
                    this.pingInfs[0].timeoutNum = 8;
                }
            }
            if (2 == this.typeFlag) {
                this.tracerouteAllowed = ExtTransportStrategy.isEnableDiagnoseBySystem(DeviceInfo.createInstance(ExtTransportEnv.getAppContext()).getmDid());
            } else if (1 == this.typeFlag) {
                this.tracerouteAllowed = ExtTransportStrategy.isEnableDiagnoseByUser(DeviceInfo.createInstance(ExtTransportEnv.getAppContext()).getmDid());
            }
            LogCatUtil.info(TAG, "flag=" + this.typeFlag + ", tracerouteAllow=" + this.tracerouteAllowed);
            if (this.tracerouteAllowed) {
                networkDiagnoseManager = new NetworkDiagnoseManager(this.detectInfs, this.pingInfs);
            } else {
                networkDiagnoseManager = new NetworkDiagnoseManager(this.detectInfs, null);
            }
            networkDiagnoseManager.register(this.callback);
            networkDiagnoseManager.register(this.typeFlag, this.timeFlag);
            networkDiagnoseManager.start();
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, "[launch]" + e.toString());
        }
    }

    private void initialize() {
        if (this.storage != null) {
            StorageManager storageManager = new StorageManager(this.storage);
            int count = 0;
            Integer num = storageManager.getInt("/detect/information", true);
            if (num != null) {
                count = num.intValue();
                if (count < 0) {
                    count = 0;
                }
            }
            if (count <= 0) {
                LogCatUtil.warn((String) TAG, (String) "[initialize] num is <= 0.");
                return;
            }
            DetectInf[] src = new DetectInf[count];
            int count2 = 0;
            for (int i = 0; i < src.length; i++) {
                String tmp = "/detect/information" + "/" + i;
                DetectInf d = new DetectInf();
                boolean keep = true;
                d.domain = storageManager.getStr(tmp + Configuration.VAL_DOMAIN, true);
                if (d.domain != null) {
                    d.domain = d.domain.trim();
                    if (d.domain.isEmpty()) {
                        d.domain = null;
                    }
                }
                d.ip = storageManager.getStr(tmp + Configuration.VAL_IP, true);
                if (d.ip != null) {
                    d.ip = d.ip.trim();
                    if (d.ip.isEmpty()) {
                        d.ip = null;
                    }
                }
                if (d.domain == null && d.ip == null) {
                    keep = false;
                } else {
                    Integer num2 = storageManager.getInt(tmp + Configuration.VAL_PORT, true);
                    if (num2 != null) {
                        int val = num2.intValue();
                        if (val < 0 || val > 65535) {
                            keep = false;
                        } else {
                            d.port = val;
                        }
                    }
                }
                if (keep) {
                    Integer num3 = storageManager.getInt(tmp + Configuration.VAL_PROTO, true);
                    if (num3 == null) {
                        keep = false;
                    } else {
                        int val2 = num3.intValue();
                        if (val2 == 0 || val2 == 1) {
                            d.protocol = val2;
                        } else {
                            keep = false;
                        }
                    }
                }
                if (keep) {
                    d.request = storageManager.getStr(tmp + Configuration.VAL_REQ, true);
                    d.response = storageManager.getStr(tmp + Configuration.VAL_RSP, true);
                    if (d.request == null || d.response == null) {
                        keep = false;
                    }
                }
                if (keep) {
                    Integer num4 = storageManager.getInt(tmp + Configuration.VAL_WAITING, true);
                    if (num4 == null) {
                        keep = false;
                    } else {
                        int val3 = num4.intValue();
                        if (val3 <= 0 || val3 > 100) {
                            keep = false;
                        } else {
                            d.waiting = val3;
                        }
                    }
                }
                if (keep) {
                    Integer num5 = storageManager.getInt(tmp + Configuration.VAL_TRYING, true);
                    if (num5 == null) {
                        keep = false;
                    } else {
                        int val4 = num5.intValue();
                        if (val4 < 0 || val4 > 100) {
                            keep = false;
                        } else {
                            d.trying = val4;
                        }
                    }
                }
                if (keep) {
                    src[i] = d;
                    count2++;
                }
            }
            if (count2 <= 0) {
                LogCatUtil.warn((String) TAG, (String) "[initialize] count is <= 0.");
                return;
            }
            this.detectInfs = new DetectInf[count2];
            int count3 = 0;
            for (int i2 = 0; i2 < src.length; i2++) {
                if (src[i2] != null) {
                    this.detectInfs[count3] = src[i2];
                    count3++;
                }
            }
            this.pingInfs = new PingInf[count3];
            for (int i3 = 0; i3 < count3; i3++) {
                this.pingInfs[i3] = new PingInf();
                this.pingInfs[i3].domain = NetworkDiagnoseUtil.parse(this.detectInfs[i3].ip == null ? this.detectInfs[i3].domain : this.detectInfs[i3].ip);
                this.pingInfs[i3].type = 3;
                this.pingInfs[i3].threshold = 6000;
                this.pingInfs[i3].waiting = 5;
                if (1 == this.typeFlag) {
                    this.pingInfs[i3].timeoutNum = 8;
                }
            }
        }
    }
}
