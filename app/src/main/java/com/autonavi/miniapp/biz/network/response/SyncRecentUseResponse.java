package com.autonavi.miniapp.biz.network.response;

import com.autonavi.miniapp.biz.network.ResultDTO;

public class SyncRecentUseResponse extends AbsResponse<Object> {
    private ResultDTO<Object> data;

    public ResultDTO<Object> getData() {
        return this.data;
    }

    public void setData(ResultDTO<Object> resultDTO) {
        this.data = resultDTO;
    }
}
