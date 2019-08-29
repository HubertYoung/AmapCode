package com.alipay.mobile.common.transport.iprank.mng.score.plugin;

import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.mng.score.IPlugIn;
import com.alipay.mobile.common.transport.iprank.mng.score.PlugInManager;
import java.util.ArrayList;
import java.util.Iterator;

public class SuccessTimePlugin implements IPlugIn {
    public void run(ArrayList<IpRankModel> list) {
        float bi = getWeight() / 1440.0f;
        Iterator<IpRankModel> it = list.iterator();
        while (it.hasNext()) {
            IpRankModel temp = it.next();
            long offTime = ((System.currentTimeMillis() - temp.lastSuccTime) / 1000) / 60;
            if (((float) offTime) <= 1440.0f) {
                temp.grade = (float) (((double) temp.grade) + ((double) (getWeight() - (((float) offTime) * bi))) + 0.1d);
            }
        }
    }

    public float getWeight() {
        return PlugInManager.SuccessTimePluginNum;
    }

    public boolean isActivated() {
        return true;
    }
}
