package com.alipay.mobile.common.transport.iprank.mng.score;

import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.mng.score.plugin.FeedbackSuccNumPlugin;
import com.alipay.mobile.common.transport.iprank.mng.score.plugin.SpeedTestPlugin;
import com.alipay.mobile.common.transport.iprank.mng.score.plugin.SuccessNumPlugin;
import com.alipay.mobile.common.transport.iprank.mng.score.plugin.SuccessTimePlugin;
import java.util.ArrayList;
import java.util.Iterator;

public class PlugInManager {
    public static float ErrNumPluginNum = 10.0f;
    public static float FeedbackSuccNumPluginNum = 30.0f;
    public static float SpeedTestPluginNum = 70.0f;
    public static float SuccessNumPluginNum = 10.0f;
    public static float SuccessTimePluginNum = 10.0f;
    public ArrayList<IPlugIn> plugIn = new ArrayList<>(4);

    public PlugInManager() {
        this.plugIn.add(new SpeedTestPlugin());
        this.plugIn.add(new FeedbackSuccNumPlugin());
        this.plugIn.add(new SuccessNumPlugin());
        this.plugIn.add(new SuccessTimePlugin());
    }

    public void run(ArrayList<IpRankModel> list) {
        if (list != null && list.size() != 0) {
            Iterator<IpRankModel> it = list.iterator();
            while (it.hasNext()) {
                IpRankModel temp = it.next();
                if (temp != null) {
                    temp.grade = 0.0f;
                } else {
                    return;
                }
            }
            Iterator<IPlugIn> it2 = this.plugIn.iterator();
            while (it2.hasNext()) {
                IPlugIn plug = it2.next();
                if (plug.isActivated()) {
                    plug.run(list);
                }
            }
        }
    }
}
