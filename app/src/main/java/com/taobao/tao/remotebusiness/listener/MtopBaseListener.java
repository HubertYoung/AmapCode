package com.taobao.tao.remotebusiness.listener;

import com.taobao.tao.remotebusiness.MtopBusiness;

abstract class MtopBaseListener {
    protected few listener = null;
    protected MtopBusiness mtopBusiness = null;

    protected MtopBaseListener(MtopBusiness mtopBusiness2, few few) {
        this.mtopBusiness = mtopBusiness2;
        this.listener = few;
    }
}
