package com.alipay.mobileapp.biz.rpc.mdap;

import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobileapp.biz.rpc.mdap.vo.pb.UnifyCustomMdapReqPb;
import com.alipay.mobileapp.biz.rpc.mdap.vo.pb.UnifyCustomMdapResPb;

public interface UnifyCustomMdapFacade {
    @SignCheck
    @OperationType("ali.user.gw.unifyCustomMdap")
    UnifyCustomMdapResPb mobileUnifyRegister(UnifyCustomMdapReqPb unifyCustomMdapReqPb);
}
