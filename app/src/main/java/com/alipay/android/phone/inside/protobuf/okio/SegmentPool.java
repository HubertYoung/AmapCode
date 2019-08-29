package com.alipay.android.phone.inside.protobuf.okio;

import tv.danmaku.ijk.media.player.IjkMediaMeta;

final class SegmentPool {
    static final SegmentPool a = new SegmentPool();
    long b;
    private Segment c;

    private SegmentPool() {
    }

    /* access modifiers changed from: 0000 */
    public final Segment a() {
        synchronized (this) {
            try {
                if (this.c == null) {
                    return new Segment();
                }
                Segment segment = this.c;
                this.c = segment.d;
                segment.d = null;
                this.b -= 2048;
                return segment;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Segment segment) {
        if (segment.d == null && segment.e == null) {
            synchronized (this) {
                if (this.b + 2048 <= IjkMediaMeta.AV_CH_TOP_BACK_CENTER) {
                    this.b += 2048;
                    segment.d = this.c;
                    segment.c = 0;
                    segment.b = 0;
                    this.c = segment;
                    return;
                }
                return;
            }
        }
        throw new IllegalArgumentException();
    }
}
