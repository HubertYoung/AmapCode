package com.alipay.mobile.nebulacore.dev.sampler;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractSampler {
    protected final List<SampleCallback> a = new ArrayList();
    protected AtomicBoolean b = new AtomicBoolean(false);
    protected long c;
    /* access modifiers changed from: private */
    public Runnable d = new Runnable() {
        public void run() {
            JSONObject result = AbstractSampler.this.a();
            for (SampleCallback onSample : AbstractSampler.this.a) {
                if (!onSample.onSample(result)) {
                    AbstractSampler.this.b.set(false);
                }
            }
            if (AbstractSampler.this.b.get()) {
                HandlerThreadFactory.getTimerThreadHandler().postDelayed(AbstractSampler.this.d, AbstractSampler.this.c);
            }
        }
    };

    public interface SampleCallback {
        boolean onSample(JSONObject jSONObject);
    }

    /* access modifiers changed from: 0000 */
    public abstract JSONObject a();

    public AbstractSampler(long sampleInterval) {
        this.c = 0 == sampleInterval ? 300 : sampleInterval;
    }

    public void start() {
        if (!this.b.get()) {
            this.b.set(true);
            HandlerThreadFactory.getTimerThreadHandler().removeCallbacks(this.d);
            HandlerThreadFactory.getTimerThreadHandler().postDelayed(this.d, (long) ReportConfig.getInstance().getSampleDelay());
        }
    }

    public void stop() {
        if (this.b.get()) {
            this.b.set(false);
            HandlerThreadFactory.getTimerThreadHandler().removeCallbacks(this.d);
        }
    }

    public void registerCallback(SampleCallback callback) {
        synchronized (this.a) {
            this.a.add(callback);
        }
    }

    public void unRegisterCallback(SampleCallback callback) {
        synchronized (this.a) {
            this.a.remove(callback);
        }
    }
}
