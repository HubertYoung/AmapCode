package com.alipay.mobile.common.transport.iprank.mng.score;

import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import java.util.ArrayList;

public interface IScore {
    void computeIpScore(ArrayList<IpRankModel> arrayList);
}
