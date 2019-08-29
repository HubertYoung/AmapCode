package com.alipay.mobile.aspect;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class FrameworkPointCutManager {
    private static FrameworkPointCutManager a;
    private Map<String, List<Advice>> b = new HashMap();

    private FrameworkPointCutManager() {
    }

    public static FrameworkPointCutManager getInstance() {
        if (a == null) {
            synchronized (FrameworkPointCutManager.class) {
                try {
                    if (a == null) {
                        a = new FrameworkPointCutManager();
                    }
                }
            }
        }
        return a;
    }

    public synchronized void registerPointCutAdvice(String pointCut, Advice advice) {
        if (!TextUtils.isEmpty(pointCut) && advice != null) {
            List advices = this.b.get(pointCut);
            if (advices == null) {
                List advices2 = new CopyOnWriteArrayList();
                advices2.add(advice);
                this.b.put(pointCut, advices2);
            } else {
                advices.add(advice);
            }
        }
    }

    public synchronized void unRegisterPointCutAdvice(Advice advice) {
        for (String pointCut : this.b.keySet()) {
            List advices = this.b.get(pointCut);
            if (advice != null) {
                for (int i = advices.size() - 1; i >= 0; i--) {
                    Advice curAdvice = (Advice) advices.get(i);
                    if (curAdvice == advice) {
                        advices.remove(curAdvice);
                    }
                }
            }
        }
    }

    public void registerPointCutAdvice(String[] pointCuts, Advice advice) {
        if (pointCuts != null) {
            for (String string : pointCuts) {
                registerPointCutAdvice(string, advice);
            }
        }
    }

    public List<Advice> getAdviceOnPointCut(String pointCut) {
        return this.b.get(pointCut);
    }
}
