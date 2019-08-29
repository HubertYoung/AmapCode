package com.autonavi.minimap.life.inter;

import android.support.annotation.Nullable;
import com.autonavi.common.PageBundle;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IOpenLifeFragment {
    void a(@Nullable bid bid, int i, @Nullable PageBundle pageBundle);
}
