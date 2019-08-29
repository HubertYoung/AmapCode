package com.taobao.accs.net;

import anet.channel.entity.ConnType;
import anet.channel.entity.ConnType.TypeLevel;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpDnsProvider {
    private static final String TAG = "HttpDnsProvider";
    private int mCurrStrategyPos = 0;
    private List<bo> mStrategys = new ArrayList();

    public HttpDnsProvider(String str) {
        b.a.a((a) new a() {
            public void onEvent(cd cdVar) {
                ThreadPoolExecutorFactory.schedule(new Runnable() {
                    public void run() {
                        bu.a().b();
                    }
                }, 2000, TimeUnit.MILLISECONDS);
            }
        });
        getAvailableStrategy(str);
    }

    public List<bo> getAvailableStrategy(String str) {
        TypeLevel typeLevel;
        if (this.mCurrStrategyPos == 0 || this.mStrategys.isEmpty()) {
            List<bo> a = bu.a().a(str);
            if (a != null && !a.isEmpty()) {
                this.mStrategys.clear();
                for (bo next : a) {
                    ConnType a2 = ConnType.a(next.e());
                    if (a2.b()) {
                        typeLevel = TypeLevel.HTTP;
                    } else {
                        typeLevel = TypeLevel.SPDY;
                    }
                    if (typeLevel == TypeLevel.SPDY && a2.c()) {
                        this.mStrategys.add(next);
                    }
                }
            }
        }
        return this.mStrategys;
    }

    public bo getStrategy() {
        return getStrategy(this.mStrategys);
    }

    public bo getStrategy(List<bo> list) {
        if (list == null || list.isEmpty()) {
            ALog.d(TAG, "strategys null or 0", new Object[0]);
            return null;
        }
        if (this.mCurrStrategyPos < 0 || this.mCurrStrategyPos >= list.size()) {
            this.mCurrStrategyPos = 0;
        }
        return list.get(this.mCurrStrategyPos);
    }

    public void updateStrategyPos() {
        this.mCurrStrategyPos++;
        if (ALog.isPrintLog(Level.D)) {
            StringBuilder sb = new StringBuilder("updateStrategyPos StrategyPos:");
            sb.append(this.mCurrStrategyPos);
            ALog.d(TAG, sb.toString(), new Object[0]);
        }
    }

    public int getStrategyPos() {
        return this.mCurrStrategyPos;
    }

    public void forceUpdateStrategy(String str) {
        bu.a().d(str);
    }
}
