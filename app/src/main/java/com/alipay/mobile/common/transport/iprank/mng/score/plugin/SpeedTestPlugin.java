package com.alipay.mobile.common.transport.iprank.mng.score.plugin;

import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.mng.score.IPlugIn;
import com.alipay.mobile.common.transport.iprank.mng.score.PlugInManager;
import java.util.ArrayList;
import java.util.Iterator;

public class SpeedTestPlugin implements IPlugIn {
    public void run(ArrayList<IpRankModel> list) {
        float MAX_SPEED = 0.0f;
        Iterator<IpRankModel> it = list.iterator();
        while (it.hasNext()) {
            IpRankModel temp = it.next();
            if (temp.rtt != 9999) {
                MAX_SPEED = Math.max(MAX_SPEED, (float) temp.rtt);
            }
        }
        if (MAX_SPEED != 0.0f) {
            float bi = getWeight() / MAX_SPEED;
            Iterator<IpRankModel> it2 = list.iterator();
            while (it2.hasNext()) {
                IpRankModel temp2 = it2.next();
                if (temp2.rtt == 9999) {
                    temp2.grade -= getWeight();
                } else {
                    int finallySpeed = temp2.rtt;
                    if (finallySpeed > 0) {
                        temp2.grade += (getWeight() - (((float) finallySpeed) * bi)) + 5.0f;
                    }
                }
            }
        }
    }

    public float getWeight() {
        return PlugInManager.SpeedTestPluginNum;
    }

    public boolean isActivated() {
        return true;
    }
}
