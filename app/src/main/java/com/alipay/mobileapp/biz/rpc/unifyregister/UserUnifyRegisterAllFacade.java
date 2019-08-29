package com.alipay.mobileapp.biz.rpc.unifyregister;

import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllReqPb;
import com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllResPb;

public interface UserUnifyRegisterAllFacade {
    @SignCheck
    @OperationType("ali.user.gw.mobileUnifyRegister")
    UnifyRegisterAllResPb mobileUnifyRegister(UnifyRegisterAllReqPb unifyRegisterAllReqPb);
}
