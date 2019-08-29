package com.alipay.edge.rpc.gen;

import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface EdgePBService {
    @SignCheck
    @OperationType("alipay.security.edge.data.update.pb")
    EdgeResult updateData(EdgeRequest edgeRequest);
}
