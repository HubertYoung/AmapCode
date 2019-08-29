package com.autonavi.miniapp.biz.network.response;

import com.autonavi.miniapp.biz.network.ResultDTO;
import mtopsdk.mtop.domain.BaseOutDo;

public class AbsResponse<T> extends BaseOutDo {
    private ResultDTO<T> data;

    public ResultDTO<T> getData() {
        return this.data;
    }

    public void setData(ResultDTO<T> resultDTO) {
        this.data = resultDTO;
    }
}
