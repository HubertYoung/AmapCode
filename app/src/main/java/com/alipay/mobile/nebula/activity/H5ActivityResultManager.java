package com.alipay.mobile.nebula.activity;

import android.content.Intent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class H5ActivityResultManager {
    private static H5ActivityResultManager instance;
    private List<OnH5ActivityResult> list = Collections.synchronizedList(new ArrayList());

    public static synchronized H5ActivityResultManager getInstance() {
        H5ActivityResultManager h5ActivityResultManager;
        synchronized (H5ActivityResultManager.class) {
            try {
                if (instance == null) {
                    instance = new H5ActivityResultManager();
                }
                h5ActivityResultManager = instance;
            }
        }
        return h5ActivityResultManager;
    }

    private H5ActivityResultManager() {
    }

    public void put(OnH5ActivityResult onH5ActivityResult) {
        this.list.add(onH5ActivityResult);
    }

    public void remove(OnH5ActivityResult onH5ActivityResult) {
        this.list.remove(onH5ActivityResult);
    }

    public void sendResult(int requestCode, int resultCode, Intent dat) {
        if (!this.list.isEmpty()) {
            for (OnH5ActivityResult onGetResult : this.list) {
                onGetResult.onGetResult(requestCode, resultCode, dat);
            }
        }
    }
}
