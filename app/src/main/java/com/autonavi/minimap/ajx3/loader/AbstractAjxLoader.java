package com.autonavi.minimap.ajx3.loader;

import android.content.Context;
import android.util.SparseArray;
import com.autonavi.minimap.ajx3.loader.action.IAjxImageLoadAction;

public abstract class AbstractAjxLoader implements IAjxImageLoader {
    protected SparseArray<IAjxImageLoadAction> mActions;
    protected Context mContext;

    public AbstractAjxLoader(Context context, SparseArray<IAjxImageLoadAction> sparseArray) {
        this.mActions = sparseArray;
        this.mContext = context.getApplicationContext();
    }

    public final IAjxImageLoadAction lookupAction(int i) {
        return this.mActions.get(i);
    }
}
