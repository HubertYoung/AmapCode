package com.autonavi.miniapp.biz.network.response;

import com.autonavi.miniapp.biz.network.ResultDTO;
import com.autonavi.miniapp.biz.network.dataobject.UserFavoriteDO;

public class QueryFavoriteStatusResponse extends AbsResponse<UserFavoriteDO> {
    private ResultDTO<UserFavoriteDO> data;

    public ResultDTO<UserFavoriteDO> getData() {
        return this.data;
    }

    public void setData(ResultDTO<UserFavoriteDO> resultDTO) {
        this.data = resultDTO;
    }
}
