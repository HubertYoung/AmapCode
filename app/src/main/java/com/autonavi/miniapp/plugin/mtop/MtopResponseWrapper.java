package com.autonavi.miniapp.plugin.mtop;

public class MtopResponseWrapper<OutputDO> {
    OutputDO outputDO;
    Status responseStatus;

    public enum Status {
        SUCCESS,
        SESSIONINVALID,
        SYSTEMERROR,
        BUSSINESSERROR
    }

    public Status getResponseStatus() {
        return this.responseStatus;
    }

    public void setResponseStatus(Status status) {
        this.responseStatus = status;
    }

    public OutputDO getOutputDO() {
        return this.outputDO;
    }

    public void setOutputDO(OutputDO outputdo) {
        this.outputDO = outputdo;
    }
}
