package com.squareup.leakcanary;

import java.io.Serializable;

public final class AnalysisResult implements Serializable {
    public final long analysisDurationMs;
    public final String className;
    public final boolean excludedLeak;
    public final Exception failure;
    public final boolean leakFound;
    public final LeakTrace leakTrace;

    public static AnalysisResult noLeak(long j) {
        AnalysisResult analysisResult = new AnalysisResult(false, false, null, null, null, j);
        return analysisResult;
    }

    public static AnalysisResult leakDetected(boolean z, String str, LeakTrace leakTrace2, long j) {
        AnalysisResult analysisResult = new AnalysisResult(true, z, str, leakTrace2, null, j);
        return analysisResult;
    }

    public static AnalysisResult failure(Exception exc, long j) {
        AnalysisResult analysisResult = new AnalysisResult(false, false, null, null, exc, j);
        return analysisResult;
    }

    private AnalysisResult(boolean z, boolean z2, String str, LeakTrace leakTrace2, Exception exc, long j) {
        this.leakFound = z;
        this.excludedLeak = z2;
        this.className = str;
        this.leakTrace = leakTrace2;
        this.failure = exc;
        this.analysisDurationMs = j;
    }
}
