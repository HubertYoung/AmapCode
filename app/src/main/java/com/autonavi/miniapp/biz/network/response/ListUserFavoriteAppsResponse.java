package com.autonavi.miniapp.biz.network.response;

import com.autonavi.miniapp.biz.network.ResultDTO;
import com.autonavi.miniapp.biz.network.dataobject.MiniAppDO;
import java.util.List;

public class ListUserFavoriteAppsResponse extends AbsResponse<List<MiniAppDO>> {
    private ResultDTO<List<MiniAppDO>> data;

    public ResultDTO<List<MiniAppDO>> getData() {
        return this.data;
    }

    public void setData(ResultDTO<List<MiniAppDO>> resultDTO) {
        this.data = resultDTO;
    }
}
