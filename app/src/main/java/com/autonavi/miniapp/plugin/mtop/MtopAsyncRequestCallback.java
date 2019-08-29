package com.autonavi.miniapp.plugin.mtop;

public interface MtopAsyncRequestCallback<OutputDO> {
    void onFinished(MtopResponseWrapper<OutputDO> mtopResponseWrapper);
}
