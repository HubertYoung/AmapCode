package com.ali.user.mobile.accountbiz.extservice.impl;

import android.content.Context;
import com.ali.user.mobile.accountbiz.extservice.DexInfoService;
import com.ali.user.mobile.accountbiz.extservice.base.BaseExtService;

public class DexInfoServiceImpl extends BaseExtService implements DexInfoService {
    private static DexInfoService mDexInfoService;

    private DexInfoServiceImpl(Context context) {
        super(context);
    }

    public static DexInfoService getInstance(Context context) {
        if (mDexInfoService == null) {
            synchronized (DexInfoServiceImpl.class) {
                try {
                    if (mDexInfoService == null) {
                        mDexInfoService = new DexInfoServiceImpl(context);
                    }
                }
            }
        }
        return mDexInfoService;
    }
}
