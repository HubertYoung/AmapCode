package com.alipay.mobile.common.transport.iprank.mng.score;

import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import java.util.ArrayList;

public interface IPlugIn {
    float getWeight();

    boolean isActivated();

    void run(ArrayList<IpRankModel> arrayList);
}
