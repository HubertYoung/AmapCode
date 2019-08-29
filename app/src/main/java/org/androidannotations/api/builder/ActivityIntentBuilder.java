package org.androidannotations.api.builder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.androidannotations.api.builder.ActivityIntentBuilder;

public abstract class ActivityIntentBuilder<I extends ActivityIntentBuilder<I>> extends IntentBuilder<I> implements ActivityStarter {
    protected Bundle lastOptions;

    public abstract PostActivityStarter startForResult(int i);

    public ActivityIntentBuilder(Context context, Class<?> clazz) {
        super(context, clazz);
    }

    public ActivityIntentBuilder(Context context, Intent intent) {
        super(context, intent);
    }

    public final PostActivityStarter start() {
        startForResult(-1);
        return new PostActivityStarter(this.context);
    }

    public ActivityStarter withOptions(Bundle options) {
        this.lastOptions = options;
        return this;
    }
}
