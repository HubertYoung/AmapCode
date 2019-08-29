package com.alipay.mobile.common.transport.iprank.mng.score.plugin;

import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.mng.score.IPlugIn;
import com.alipay.mobile.common.transport.iprank.mng.score.PlugInManager;
import java.util.ArrayList;
import java.util.Iterator;

public class SuccessNumPlugin implements IPlugIn {
    public void run(ArrayList<IpRankModel> list) {
        float MAX_SUCCESSNUM = 0.0f;
        Iterator<IpRankModel> it = list.iterator();
        while (it.hasNext()) {
            MAX_SUCCESSNUM = Math.max(MAX_SUCCESSNUM, (float) it.next().successCount);
        }
        if (MAX_SUCCESSNUM != 0.0f) {
            float bi = getWeight() / MAX_SUCCESSNUM;
            Iterator<IpRankModel> it2 = list.iterator();
            while (it2.hasNext()) {
                IpRankModel temp = it2.next();
                temp.grade += ((float) temp.successCount) * bi;
            }
        }
    }

    public float getWeight() {
        return PlugInManager.SuccessNumPluginNum;
    }

    public boolean isActivated() {
        return true;
    }
}
