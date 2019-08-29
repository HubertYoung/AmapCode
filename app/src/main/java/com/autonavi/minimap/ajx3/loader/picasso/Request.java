package com.autonavi.minimap.ajx3.loader.picasso;

import android.graphics.Bitmap.Config;
import android.net.Uri;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.Priority;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class Request {
    private static final long TOO_LONG_LOG = TimeUnit.SECONDS.toNanos(5);
    public final boolean centerCrop;
    public final boolean centerInside;
    public final Config config;
    public final boolean hasRotationPivot;
    int id;
    public final boolean isSVG;
    int networkPolicy;
    public final boolean onlyScaleDown;
    public final Priority priority;
    public final int resourceId;
    public final float rotationDegrees;
    public final float rotationPivotX;
    public final float rotationPivotY;
    public final String stableKey;
    long started;
    public final int targetHeight;
    public final int targetWidth;
    public final List<Transformation> transformations;
    public final Uri uri;

    public static final class Builder {
        private boolean centerCrop;
        private boolean centerInside;
        private Config config;
        private boolean hasRotationPivot;
        private boolean isSVG;
        private boolean onlyScaleDown;
        private Priority priority;
        private int resourceId;
        private float rotationDegrees;
        private float rotationPivotX;
        private float rotationPivotY;
        private String stableKey;
        private int targetHeight;
        private int targetWidth;
        private List<Transformation> transformations;
        private Uri uri;

        public Builder(Uri uri2) {
            setUri(uri2);
        }

        public Builder(int i) {
            setResourceId(i);
        }

        Builder(Uri uri2, int i, Config config2) {
            this.uri = uri2;
            this.resourceId = i;
            this.config = config2;
        }

        public Builder(Request request) {
            this.uri = request.uri;
            this.resourceId = request.resourceId;
            this.stableKey = request.stableKey;
            this.targetWidth = request.targetWidth;
            this.targetHeight = request.targetHeight;
            this.centerCrop = request.centerCrop;
            this.centerInside = request.centerInside;
            this.rotationDegrees = request.rotationDegrees;
            this.rotationPivotX = request.rotationPivotX;
            this.rotationPivotY = request.rotationPivotY;
            this.hasRotationPivot = request.hasRotationPivot;
            this.onlyScaleDown = request.onlyScaleDown;
            if (request.transformations != null) {
                this.transformations = new ArrayList(request.transformations);
            }
            this.config = request.config;
            this.priority = request.priority;
        }

        /* access modifiers changed from: 0000 */
        public final boolean hasImage() {
            return (this.uri == null && this.resourceId == 0) ? false : true;
        }

        /* access modifiers changed from: 0000 */
        public final boolean hasSize() {
            return (this.targetWidth == 0 && this.targetHeight == 0) ? false : true;
        }

        /* access modifiers changed from: 0000 */
        public final boolean hasPriority() {
            return this.priority != null;
        }

        public final Builder setUri(Uri uri2) {
            if (uri2 == null) {
                throw new IllegalArgumentException("Image URI may not be null.");
            }
            this.uri = uri2;
            this.resourceId = 0;
            return this;
        }

        public final Builder setResourceId(int i) {
            if (i == 0) {
                throw new IllegalArgumentException("Image resource ID may not be 0.");
            }
            this.resourceId = i;
            this.uri = null;
            return this;
        }

        public final Builder stableKey(String str) {
            this.stableKey = str;
            return this;
        }

        public final Builder resize(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("Width must be positive number or 0.");
            } else if (i2 < 0) {
                throw new IllegalArgumentException("Height must be positive number or 0.");
            } else if (i2 == 0 && i == 0) {
                throw new IllegalArgumentException("At least one dimension has to be positive number.");
            } else {
                this.targetWidth = i;
                this.targetHeight = i2;
                return this;
            }
        }

        public final Builder clearResize() {
            this.targetWidth = 0;
            this.targetHeight = 0;
            this.centerCrop = false;
            this.centerInside = false;
            return this;
        }

        public final Builder centerCrop() {
            if (this.centerInside) {
                throw new IllegalStateException("Center crop can not be used after calling centerInside");
            }
            this.centerCrop = true;
            return this;
        }

        public final Builder clearCenterCrop() {
            this.centerCrop = false;
            return this;
        }

        public final Builder centerInside() {
            if (this.centerCrop) {
                throw new IllegalStateException("Center inside can not be used after calling centerCrop");
            }
            this.centerInside = true;
            return this;
        }

        public final Builder clearCenterInside() {
            this.centerInside = false;
            return this;
        }

        public final Builder onlyScaleDown() {
            if (this.targetHeight == 0 && this.targetWidth == 0) {
                throw new IllegalStateException("onlyScaleDown can not be applied without resize");
            }
            this.onlyScaleDown = true;
            return this;
        }

        public final Builder clearOnlyScaleDown() {
            this.onlyScaleDown = false;
            return this;
        }

        public final Builder rotate(float f) {
            this.rotationDegrees = f;
            return this;
        }

        public final Builder rotate(float f, float f2, float f3) {
            this.rotationDegrees = f;
            this.rotationPivotX = f2;
            this.rotationPivotY = f3;
            this.hasRotationPivot = true;
            return this;
        }

        public final Builder clearRotation() {
            this.rotationDegrees = 0.0f;
            this.rotationPivotX = 0.0f;
            this.rotationPivotY = 0.0f;
            this.hasRotationPivot = false;
            return this;
        }

        public final Builder config(Config config2) {
            this.config = config2;
            return this;
        }

        public final Builder priority(Priority priority2) {
            if (priority2 == null) {
                throw new IllegalArgumentException("Priority invalid.");
            } else if (this.priority != null) {
                throw new IllegalStateException("Priority already set.");
            } else {
                this.priority = priority2;
                return this;
            }
        }

        public final Builder transform(Transformation transformation) {
            if (transformation == null) {
                throw new IllegalArgumentException("Transformation must not be null.");
            } else if (transformation.key() == null) {
                throw new IllegalArgumentException("Transformation key must not be null.");
            } else {
                if (this.transformations == null) {
                    this.transformations = new ArrayList(2);
                }
                this.transformations.add(transformation);
                return this;
            }
        }

        public final Builder transform(List<? extends Transformation> list) {
            if (list == null) {
                throw new IllegalArgumentException("Transformation list must not be null.");
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                transform((Transformation) list.get(i));
            }
            return this;
        }

        public final Builder setSVGFlag(boolean z) {
            this.isSVG = z;
            return this;
        }

        public final Request build() {
            if (this.centerInside && this.centerCrop) {
                throw new IllegalStateException("Center crop and center inside can not be used together.");
            } else if (this.centerCrop && this.targetWidth == 0 && this.targetHeight == 0) {
                throw new IllegalStateException("Center crop requires calling resize with positive width and height.");
            } else if (this.centerInside && this.targetWidth == 0 && this.targetHeight == 0) {
                throw new IllegalStateException("Center inside requires calling resize with positive width and height.");
            } else {
                if (this.priority == null) {
                    this.priority = Priority.NORMAL;
                }
                Uri uri2 = this.uri;
                int i = this.resourceId;
                String str = this.stableKey;
                List<Transformation> list = this.transformations;
                int i2 = this.targetWidth;
                int i3 = this.targetHeight;
                boolean z = this.centerCrop;
                boolean z2 = this.centerInside;
                boolean z3 = this.onlyScaleDown;
                float f = this.rotationDegrees;
                float f2 = this.rotationPivotX;
                float f3 = this.rotationPivotY;
                boolean z4 = this.hasRotationPivot;
                boolean z5 = z4;
                Request request = new Request(uri2, i, str, list, i2, i3, z, z2, z3, f, f2, f3, z5, this.config, this.priority, this.isSVG);
                return request;
            }
        }
    }

    private Request(Uri uri2, int i, String str, List<Transformation> list, int i2, int i3, boolean z, boolean z2, boolean z3, float f, float f2, float f3, boolean z4, Config config2, Priority priority2, boolean z5) {
        this.uri = uri2;
        this.resourceId = i;
        this.stableKey = str;
        if (list == null) {
            this.transformations = null;
        } else {
            this.transformations = Collections.unmodifiableList(list);
        }
        this.targetWidth = i2;
        this.targetHeight = i3;
        this.centerCrop = z;
        this.centerInside = z2;
        this.onlyScaleDown = z3;
        this.rotationDegrees = f;
        this.rotationPivotX = f2;
        this.rotationPivotY = f3;
        this.hasRotationPivot = z4;
        this.config = config2;
        this.priority = priority2;
        this.isSVG = z5;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Request{");
        if (this.resourceId > 0) {
            sb.append(this.resourceId);
        } else {
            sb.append(this.uri);
        }
        if (this.transformations != null && !this.transformations.isEmpty()) {
            for (Transformation key : this.transformations) {
                sb.append(' ');
                sb.append(key.key());
            }
        }
        if (this.stableKey != null) {
            sb.append(" stableKey(");
            sb.append(this.stableKey);
            sb.append(')');
        }
        if (this.targetWidth > 0) {
            sb.append(" resize(");
            sb.append(this.targetWidth);
            sb.append(',');
            sb.append(this.targetHeight);
            sb.append(')');
        }
        if (this.centerCrop) {
            sb.append(" centerCrop");
        }
        if (this.centerInside) {
            sb.append(" centerInside");
        }
        if (this.rotationDegrees != 0.0f) {
            sb.append(" rotation(");
            sb.append(this.rotationDegrees);
            if (this.hasRotationPivot) {
                sb.append(" @ ");
                sb.append(this.rotationPivotX);
                sb.append(',');
                sb.append(this.rotationPivotY);
            }
            sb.append(')');
        }
        if (this.config != null) {
            sb.append(' ');
            sb.append(this.config);
        }
        sb.append('}');
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final String logId() {
        long nanoTime = System.nanoTime() - this.started;
        if (nanoTime > TOO_LONG_LOG) {
            StringBuilder sb = new StringBuilder();
            sb.append(plainId());
            sb.append('+');
            sb.append(TimeUnit.NANOSECONDS.toSeconds(nanoTime));
            sb.append('s');
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(plainId());
        sb2.append('+');
        sb2.append(TimeUnit.NANOSECONDS.toMillis(nanoTime));
        sb2.append(RPCDataParser.TIME_MS);
        return sb2.toString();
    }

    /* access modifiers changed from: 0000 */
    public final String plainId() {
        StringBuilder sb = new StringBuilder("[R");
        sb.append(this.id);
        sb.append(']');
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final String getName() {
        if (this.uri != null) {
            return String.valueOf(this.uri.getPath());
        }
        return Integer.toHexString(this.resourceId);
    }

    public final boolean hasSize() {
        return (this.targetWidth == 0 && this.targetHeight == 0) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public final boolean needsTransformation() {
        return needsMatrixTransform() || hasCustomTransformations();
    }

    /* access modifiers changed from: 0000 */
    public final boolean needsMatrixTransform() {
        return hasSize() || this.rotationDegrees != 0.0f;
    }

    /* access modifiers changed from: 0000 */
    public final boolean hasCustomTransformations() {
        return this.transformations != null;
    }

    public final Builder copy() {
        return new Builder(this);
    }
}
