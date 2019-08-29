package com.alipay.mobile.common.transport.iprank.mng.score.plugin;

import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.mng.score.IPlugIn;
import com.alipay.mobile.common.transport.iprank.mng.score.PlugInManager;
import java.util.ArrayList;
import java.util.Iterator;

public class FeedbackSuccNumPlugin implements IPlugIn {
    public void run(ArrayList<IpRankModel> list) {
        float MAX_FEEDBACKSUCCNUM = 0.0f;
        Iterator<IpRankModel> it = list.iterator();
        while (it.hasNext()) {
            MAX_FEEDBACKSUCCNUM = Math.max(MAX_FEEDBACKSUCCNUM, (float) it.next().feedbackSuccCount);
        }
        if (MAX_FEEDBACKSUCCNUM != 0.0f) {
            float bi = getWeight() / MAX_FEEDBACKSUCCNUM;
            Iterator<IpRankModel> it2 = list.iterator();
            while (it2.hasNext()) {
                IpRankModel temp = it2.next();
                temp.grade += ((float) temp.feedbackSuccCount) * bi;
            }
        }
    }

    public float getWeight() {
        return PlugInManager.FeedbackSuccNumPluginNum;
    }

    public boolean isActivated() {
        return true;
    }
}
