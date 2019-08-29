package com.alipay.mobile.common.transport.iprank.mng.score;

import android.content.Context;
import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import java.util.ArrayList;

public class ScoreManager implements IScore {
    public static final String TAG = "IPR_ScoreManager";
    private static ScoreManager a = null;
    private PlugInManager b;

    public static ScoreManager getInstance(Context context) {
        if (a != null) {
            return a;
        }
        synchronized (ScoreManager.class) {
            try {
                if (a == null) {
                    a = new ScoreManager();
                }
            }
        }
        return a;
    }

    private ScoreManager() {
        this.b = null;
        this.b = new PlugInManager();
    }

    public void computeIpScore(ArrayList<IpRankModel> list) {
        if (list.size() > 1) {
            this.b.run(list);
        }
    }
}
