package com.alipay.android.phone.inside.offlinecode.rpc.response.card;

import com.alipay.android.phone.inside.offlinecode.rpc.response.base.BaseRPCResponseInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.response.base.VirtualCardSubScene;
import java.util.ArrayList;
import java.util.List;

public class VirtualCardQuerySubSceneResponse {
    public BaseRPCResponseInfo baseRPCResponseInfo;
    public List<VirtualCardSubScene> subScenes = new ArrayList();
}
