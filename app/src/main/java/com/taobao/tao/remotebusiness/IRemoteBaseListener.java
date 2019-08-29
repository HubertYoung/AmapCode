package com.taobao.tao.remotebusiness;

import mtopsdk.mtop.domain.MtopResponse;

public interface IRemoteBaseListener extends IRemoteListener {
    void onSystemError(int i, MtopResponse mtopResponse, Object obj);
}
