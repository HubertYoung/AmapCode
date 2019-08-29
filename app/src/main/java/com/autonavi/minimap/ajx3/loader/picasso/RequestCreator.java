package com.autonavi.minimap.ajx3.loader.picasso;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.autonavi.minimap.ajx3.image.ImageCache.Image;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.Priority;
import com.autonavi.minimap.ajx3.loader.picasso.Request.Builder;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import pl.droidsonroids.gif.GifDrawable;

public class RequestCreator {
    private static final AtomicInteger NEXT_ID = new AtomicInteger();
    private final Builder data;
    private boolean deferred;
    private Drawable errorDrawable;
    private int errorResId;
    private boolean fastMode;
    private int memoryPolicy;
    private int networkPolicy;
    private boolean noFade;
    private boolean noMerge;
    private final Picasso picasso;
    private Drawable placeholderDrawable;
    private int placeholderResId;
    private boolean setPlaceholder;
    private Object tag;

    RequestCreator(Picasso picasso2, Uri uri, int i) {
        this.setPlaceholder = true;
        this.fastMode = false;
        this.noMerge = false;
        if (picasso2.shutdown) {
            throw new IllegalStateException("Picasso instance already shut down. Cannot submit new requests.");
        }
        this.picasso = picasso2;
        this.data = new Builder(uri, i, picasso2.defaultBitmapConfig);
    }

    RequestCreator() {
        this.setPlaceholder = true;
        this.fastMode = false;
        this.noMerge = false;
        this.picasso = null;
        this.data = new Builder((Uri) null);
    }

    public RequestCreator noPlaceholder() {
        if (this.placeholderResId != 0) {
            throw new IllegalStateException("Placeholder resource already set.");
        } else if (this.placeholderDrawable != null) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.setPlaceholder = false;
            return this;
        }
    }

    public RequestCreator placeholder(int i) {
        if (!this.setPlaceholder) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        } else if (i == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        } else if (this.placeholderDrawable != null) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.placeholderResId = i;
            return this;
        }
    }

    public RequestCreator placeholder(Drawable drawable) {
        if (!this.setPlaceholder) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        } else if (this.placeholderResId != 0) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.placeholderDrawable = drawable;
            return this;
        }
    }

    public RequestCreator error(int i) {
        if (i == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        } else if (this.errorDrawable != null) {
            throw new IllegalStateException("Error image already set.");
        } else {
            this.errorResId = i;
            return this;
        }
    }

    public RequestCreator error(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Error image may not be null.");
        } else if (this.errorResId != 0) {
            throw new IllegalStateException("Error image already set.");
        } else {
            this.errorDrawable = drawable;
            return this;
        }
    }

    public RequestCreator tag(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Tag invalid.");
        } else if (this.tag != null) {
            throw new IllegalStateException("Tag already set.");
        } else {
            this.tag = obj;
            return this;
        }
    }

    public RequestCreator fastMode(boolean z) {
        this.fastMode = z;
        return this;
    }

    public RequestCreator noMerge(boolean z) {
        this.noMerge = z;
        return this;
    }

    public RequestCreator setSVGFlag(boolean z) {
        this.data.setSVGFlag(z);
        return this;
    }

    public RequestCreator fit() {
        this.deferred = true;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public RequestCreator unfit() {
        this.deferred = false;
        return this;
    }

    public RequestCreator resizeDimen(int i, int i2) {
        Resources resources = this.picasso.context.getResources();
        return resize(resources.getDimensionPixelSize(i), resources.getDimensionPixelSize(i2));
    }

    public RequestCreator resize(int i, int i2) {
        this.data.resize(i, i2);
        return this;
    }

    public RequestCreator centerCrop() {
        this.data.centerCrop();
        return this;
    }

    public RequestCreator centerInside() {
        this.data.centerInside();
        return this;
    }

    public RequestCreator onlyScaleDown() {
        this.data.onlyScaleDown();
        return this;
    }

    public RequestCreator rotate(float f) {
        this.data.rotate(f);
        return this;
    }

    public RequestCreator rotate(float f, float f2, float f3) {
        this.data.rotate(f, f2, f3);
        return this;
    }

    public RequestCreator config(Config config) {
        this.data.config(config);
        return this;
    }

    public RequestCreator stableKey(String str) {
        this.data.stableKey(str);
        return this;
    }

    public RequestCreator priority(Priority priority) {
        this.data.priority(priority);
        return this;
    }

    public RequestCreator transform(Transformation transformation) {
        this.data.transform(transformation);
        return this;
    }

    public RequestCreator transform(List<? extends Transformation> list) {
        this.data.transform(list);
        return this;
    }

    @Deprecated
    public RequestCreator skipMemoryCache() {
        return memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
    }

    public RequestCreator memoryPolicy(MemoryPolicy memoryPolicy2, MemoryPolicy... memoryPolicyArr) {
        if (memoryPolicy2 == null) {
            throw new IllegalArgumentException("Memory policy cannot be null.");
        }
        this.memoryPolicy = memoryPolicy2.index | this.memoryPolicy;
        if (memoryPolicyArr == null) {
            throw new IllegalArgumentException("Memory policy cannot be null.");
        }
        if (memoryPolicyArr.length > 0) {
            for (MemoryPolicy memoryPolicy3 : memoryPolicyArr) {
                if (memoryPolicy3 == null) {
                    throw new IllegalArgumentException("Memory policy cannot be null.");
                }
                this.memoryPolicy = memoryPolicy3.index | this.memoryPolicy;
            }
        }
        return this;
    }

    public RequestCreator networkPolicy(NetworkPolicy networkPolicy2, NetworkPolicy... networkPolicyArr) {
        if (networkPolicy2 == null) {
            throw new IllegalArgumentException("Network policy cannot be null.");
        }
        this.networkPolicy = networkPolicy2.index | this.networkPolicy;
        if (networkPolicyArr == null) {
            throw new IllegalArgumentException("Network policy cannot be null.");
        }
        if (networkPolicyArr.length > 0) {
            for (NetworkPolicy networkPolicy3 : networkPolicyArr) {
                if (networkPolicy3 == null) {
                    throw new IllegalArgumentException("Network policy cannot be null.");
                }
                this.networkPolicy = networkPolicy3.index | this.networkPolicy;
            }
        }
        return this;
    }

    public RequestCreator noFade() {
        this.noFade = true;
        return this;
    }

    public Bitmap getBitmap() throws IOException {
        long nanoTime = System.nanoTime();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with getBitmap.");
        } else if (!this.data.hasImage()) {
            return null;
        } else {
            Request createRequest = createRequest(nanoTime);
            GetAction getAction = new GetAction(this.picasso, createRequest, this.memoryPolicy, this.networkPolicy, this.tag, Utils.createKey(createRequest, new StringBuilder()), this.fastMode, this.noMerge);
            Image hunt = ImageHunter.forRequest(this.picasso, this.picasso.dispatcher, this.picasso.cache, this.picasso.stats, getAction).hunt();
            if (hunt != null) {
                return hunt.bitmap;
            }
            return null;
        }
    }

    public GifDrawable getGif() throws IOException {
        long nanoTime = System.nanoTime();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with getGif.");
        } else if (!this.data.hasImage()) {
            return null;
        } else {
            Request createRequest = createRequest(nanoTime);
            GetAction getAction = new GetAction(this.picasso, createRequest, this.memoryPolicy, this.networkPolicy, this.tag, Utils.createKey(createRequest, new StringBuilder()), this.fastMode, this.noMerge);
            Image hunt = ImageHunter.forRequest(this.picasso, this.picasso.dispatcher, this.picasso.cache, this.picasso.stats, getAction).hunt();
            if (hunt != null) {
                return hunt.gif;
            }
            return null;
        }
    }

    public void fetch() {
        fetch(null);
    }

    public void fetch(Callback callback) {
        long nanoTime = System.nanoTime();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with fetch.");
        }
        if (this.data.hasImage()) {
            if (!this.data.hasPriority()) {
                this.data.priority(Priority.LOW);
            }
            Request createRequest = createRequest(nanoTime);
            String createKey = Utils.createKey(createRequest, new StringBuilder());
            if (this.picasso.quickMemoryCacheCheck(createKey) != null) {
                if (this.picasso.loggingEnabled) {
                    String plainId = createRequest.plainId();
                    StringBuilder sb = new StringBuilder("from ");
                    sb.append(LoadedFrom.MEMORY);
                    Utils.log("Main", "completed", plainId, sb.toString());
                }
                if (callback != null) {
                    callback.onSuccess();
                }
            } else {
                FetchAction fetchAction = new FetchAction(this.picasso, createRequest, this.memoryPolicy, this.networkPolicy, this.tag, createKey, callback, this.fastMode, this.noMerge);
                this.picasso.submit(fetchAction);
            }
        }
    }

    public void into(Target target) {
        long nanoTime = System.nanoTime();
        Utils.checkMain();
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with a Target.");
        } else {
            Drawable drawable = null;
            if (!this.data.hasImage()) {
                this.picasso.cancelRequest(target);
                if (this.setPlaceholder) {
                    drawable = getPlaceholderDrawable();
                }
                target.onPrepareLoad(drawable);
                return;
            }
            Request createRequest = createRequest(nanoTime);
            String createKey = Utils.createKey(createRequest);
            if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
                Image quickMemoryCacheCheck = this.picasso.quickMemoryCacheCheck(createKey);
                if (quickMemoryCacheCheck != null && quickMemoryCacheCheck.gif != null) {
                    this.picasso.cancelRequest(target);
                    target.onGifDrawableLoaded(quickMemoryCacheCheck.gif, LoadedFrom.MEMORY);
                } else if (!(quickMemoryCacheCheck == null || quickMemoryCacheCheck.bitmap == null)) {
                    this.picasso.cancelRequest(target);
                    target.onBitmapLoaded(quickMemoryCacheCheck.bitmap, LoadedFrom.MEMORY);
                    return;
                }
            }
            if (this.setPlaceholder) {
                drawable = getPlaceholderDrawable();
            }
            target.onPrepareLoad(drawable);
            TargetAction targetAction = new TargetAction(this.picasso, target, createRequest, this.memoryPolicy, this.networkPolicy, this.errorDrawable, createKey, this.tag, this.errorResId, this.fastMode, this.noMerge);
            this.picasso.enqueueAndSubmit(targetAction);
        }
    }

    public void into(RemoteViews remoteViews, int i, int[] iArr) {
        long nanoTime = System.nanoTime();
        if (remoteViews == null) {
            throw new IllegalArgumentException("remoteViews must not be null.");
        } else if (iArr == null) {
            throw new IllegalArgumentException("appWidgetIds must not be null.");
        } else if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with remote views.");
        } else if (this.placeholderDrawable == null && this.placeholderResId == 0 && this.errorDrawable == null) {
            Request createRequest = createRequest(nanoTime);
            RemoteViews remoteViews2 = remoteViews;
            int i2 = i;
            int[] iArr2 = iArr;
            AppWidgetAction appWidgetAction = new AppWidgetAction(this.picasso, createRequest, remoteViews2, i2, iArr2, this.memoryPolicy, this.networkPolicy, Utils.createKey(createRequest, new StringBuilder()), this.tag, this.errorResId, this.fastMode, this.noMerge);
            performRemoteViewInto(appWidgetAction);
        } else {
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
    }

    public void into(ImageView imageView) {
        into(imageView, null);
    }

    public void into(ImageView imageView, Callback callback) {
        ImageView imageView2 = imageView;
        Callback callback2 = callback;
        long nanoTime = System.nanoTime();
        Utils.checkMain();
        if (imageView2 == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (!this.data.hasImage()) {
            this.picasso.cancelRequest(imageView2);
            if (this.setPlaceholder) {
                PicassoDrawable.setPlaceholder(imageView2, getPlaceholderDrawable());
            }
        } else {
            if (this.deferred) {
                if (this.data.hasSize()) {
                    throw new IllegalStateException("Fit cannot be used with resize.");
                }
                int width = imageView.getWidth();
                int height = imageView.getHeight();
                if (width == 0 || height == 0) {
                    if (this.setPlaceholder) {
                        PicassoDrawable.setPlaceholder(imageView2, getPlaceholderDrawable());
                    }
                    this.picasso.defer(imageView2, new DeferredRequestCreator(this, imageView2, callback2));
                    return;
                }
                this.data.resize(width, height);
            }
            Request createRequest = createRequest(nanoTime);
            String createKey = Utils.createKey(createRequest);
            if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
                Image quickMemoryCacheCheck = this.picasso.quickMemoryCacheCheck(createKey);
                if (quickMemoryCacheCheck != null && quickMemoryCacheCheck.gif != null) {
                    this.picasso.cancelRequest(imageView2);
                    PicassoDrawable.setGif(imageView2, this.picasso.context, quickMemoryCacheCheck.gif, LoadedFrom.MEMORY, this.noFade, this.picasso.indicatorsEnabled);
                    if (this.picasso.loggingEnabled) {
                        String plainId = createRequest.plainId();
                        StringBuilder sb = new StringBuilder("from ");
                        sb.append(LoadedFrom.MEMORY);
                        Utils.log("Main", "completed", plainId, sb.toString());
                    }
                    if (callback2 != null) {
                        callback.onSuccess();
                    }
                    return;
                } else if (!(quickMemoryCacheCheck == null || quickMemoryCacheCheck.bitmap == null)) {
                    this.picasso.cancelRequest(imageView2);
                    PicassoDrawable.setBitmap(imageView2, this.picasso.context, quickMemoryCacheCheck.bitmap, LoadedFrom.MEMORY, this.noFade, this.picasso.indicatorsEnabled);
                    if (this.picasso.loggingEnabled) {
                        String plainId2 = createRequest.plainId();
                        StringBuilder sb2 = new StringBuilder("from ");
                        sb2.append(LoadedFrom.MEMORY);
                        Utils.log("Main", "completed", plainId2, sb2.toString());
                    }
                    if (callback2 != null) {
                        callback.onSuccess();
                    }
                    return;
                }
            }
            if (this.setPlaceholder) {
                PicassoDrawable.setPlaceholder(imageView2, getPlaceholderDrawable());
            }
            ImageViewAction imageViewAction = new ImageViewAction(this.picasso, imageView2, createRequest, this.memoryPolicy, this.networkPolicy, this.errorResId, this.errorDrawable, createKey, this.tag, callback2, this.noFade, this.fastMode, this.noMerge);
            this.picasso.enqueueAndSubmit(imageViewAction);
        }
    }

    private Drawable getPlaceholderDrawable() {
        if (this.placeholderResId != 0) {
            return this.picasso.context.getResources().getDrawable(this.placeholderResId);
        }
        return this.placeholderDrawable;
    }

    private Request createRequest(long j) {
        int andIncrement = NEXT_ID.getAndIncrement();
        Request build = this.data.build();
        build.id = andIncrement;
        build.started = j;
        boolean z = this.picasso.loggingEnabled;
        if (z) {
            Utils.log("Main", "created", build.plainId(), build.toString());
        }
        Request transformRequest = this.picasso.transformRequest(build);
        if (transformRequest != build) {
            transformRequest.id = andIncrement;
            transformRequest.started = j;
            if (z) {
                Utils.log("Main", "changed", transformRequest.logId(), "into ".concat(String.valueOf(transformRequest)));
            }
        }
        return transformRequest;
    }

    private void performRemoteViewInto(RemoteViewsAction remoteViewsAction) {
        if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
            Image quickMemoryCacheCheck = this.picasso.quickMemoryCacheCheck(remoteViewsAction.getKey());
            if (quickMemoryCacheCheck != null) {
                remoteViewsAction.complete(quickMemoryCacheCheck, LoadedFrom.MEMORY);
                return;
            }
        }
        if (this.placeholderResId != 0) {
            remoteViewsAction.setImageResource(this.placeholderResId);
        }
        this.picasso.enqueueAndSubmit(remoteViewsAction);
    }
}
