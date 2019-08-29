package com.taobao.tao.remotebusiness.handler;

import com.taobao.tao.remotebusiness.MtopBusiness;
import java.io.Serializable;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.MtopResponse;

public class HandlerParam implements Serializable {
    private static final long serialVersionUID = 9196408638670689787L;
    public fet event;
    public few listener;
    public MtopBusiness mtopBusiness;
    public MtopResponse mtopResponse;
    public BaseOutDo pojo;

    public HandlerParam(few few, fet fet, MtopBusiness mtopBusiness2) {
        this.listener = few;
        this.event = fet;
        this.mtopBusiness = mtopBusiness2;
    }
}
