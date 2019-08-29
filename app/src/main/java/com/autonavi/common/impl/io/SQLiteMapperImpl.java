package com.autonavi.common.impl.io;

import android.content.Context;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SQLiteMapperImpl<T> extends bkk<T> {
    private static ExecutorService c = Executors.newSingleThreadScheduledExecutor();

    enum COMMAND {
        SAVE,
        QUERY,
        UPDATE,
        UPDATE_CONTENT,
        REMOVE,
        SAVE_BAT,
        GET,
        GET_BY_KEY
    }

    public SQLiteMapperImpl(Context context, Class<T> cls) {
        super(context, cls);
    }
}
