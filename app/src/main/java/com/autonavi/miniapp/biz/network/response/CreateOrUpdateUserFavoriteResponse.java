package com.autonavi.miniapp.biz.network.response;

import com.autonavi.miniapp.biz.network.ResultDTO;

public class CreateOrUpdateUserFavoriteResponse extends AbsResponse<Long> {
    private ResultDTO<Long> data;

    public ResultDTO<Long> getData() {
        return this.data;
    }

    public void setData(ResultDTO<Long> resultDTO) {
        this.data = resultDTO;
    }
}
