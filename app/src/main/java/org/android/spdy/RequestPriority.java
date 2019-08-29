package org.android.spdy;

public enum RequestPriority {
    HIGHEST(0),
    HIGH(1),
    MEDIUM(2),
    LOW(3),
    LOWEST(4),
    IDLE(5),
    DEFAULT_PRIORITY(1);
    
    private int priority;

    private RequestPriority(int i) {
        this.priority = i;
    }

    /* access modifiers changed from: 0000 */
    public final int getPriorityInt() {
        return this.priority;
    }
}
