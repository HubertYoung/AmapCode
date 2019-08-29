package com.alipay.mobile.common.transport.iprank.mng.score.plugin;

import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.mng.score.IPlugIn;
import com.alipay.mobile.common.transport.iprank.mng.score.PlugInManager;
import java.util.ArrayList;
import java.util.Iterator;

public class ErrNumPlugin implements IPlugIn {
    public void run(ArrayList<IpRankModel> list) {
        float MAX_ERRNUM = 0.0f;
        Iterator<IpRankModel> it = list.iterator();
        while (it.hasNext()) {
            MAX_ERRNUM = Math.max(MAX_ERRNUM, (float) it.next().failCount);
        }
        if (MAX_ERRNUM != 0.0f) {
            float bi = getWeight() / MAX_ERRNUM;
            Iterator<IpRankModel> it2 = list.iterator();
            while (it2.hasNext()) {
                IpRankModel temp = it2.next();
                temp.grade += getWeight() - (((float) temp.failCount) * bi);
            }
        }
    }

    public float getWeight() {
        return PlugInManager.ErrNumPluginNum;
    }

    public boolean isActivated() {
        return false;
    }
}
