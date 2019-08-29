package mtopsdk.mtop.deviceid.domain;

import mtopsdk.mtop.domain.BaseOutDo;

public class MtopSysNewDeviceIdResponse extends BaseOutDo {
    private MtopSysNewDeviceIdResponseData data;

    public MtopSysNewDeviceIdResponseData getData() {
        return this.data;
    }

    public void setData(MtopSysNewDeviceIdResponseData mtopSysNewDeviceIdResponseData) {
        this.data = mtopSysNewDeviceIdResponseData;
    }
}
