package com.squareup.leakcanary;

import android.content.Context;
import com.squareup.leakcanary.HeapDump.Listener;
import com.squareup.leakcanary.internal.HeapAnalyzerService;
import com.squareup.leakcanary.internal.LeakCanaryInternals;

public final class ServiceHeapDumpListener implements Listener {
    private final Context context;
    private final Class<? extends AbstractAnalysisResultService> listenerServiceClass;

    public ServiceHeapDumpListener(Context context2, Class<? extends AbstractAnalysisResultService> cls) {
        LeakCanaryInternals.setEnabled(context2, cls, true);
        LeakCanaryInternals.setEnabled(context2, HeapAnalyzerService.class, true);
        this.listenerServiceClass = (Class) Preconditions.checkNotNull(cls, "listenerServiceClass");
        this.context = ((Context) Preconditions.checkNotNull(context2, "context")).getApplicationContext();
    }

    public final void analyze(HeapDump heapDump) {
        Preconditions.checkNotNull(heapDump, "heapDump");
        HeapAnalyzerService.runAnalysis(this.context, heapDump, this.listenerServiceClass);
    }
}
