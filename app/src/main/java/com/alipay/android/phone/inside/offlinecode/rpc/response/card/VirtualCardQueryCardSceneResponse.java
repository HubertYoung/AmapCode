package com.alipay.android.phone.inside.offlinecode.rpc.response.card;

import com.alipay.android.phone.inside.offlinecode.rpc.response.base.BaseRPCResponseInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.response.base.VirtualCardScene;
import java.util.ArrayList;
import java.util.List;

public class VirtualCardQueryCardSceneResponse {
    public BaseRPCResponseInfo baseRPCResponseInfo;
    public List<VirtualCardScene> cardScenes = new ArrayList();
}
