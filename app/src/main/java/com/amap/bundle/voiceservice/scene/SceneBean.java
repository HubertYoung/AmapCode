package com.amap.bundle.voiceservice.scene;

import android.util.Pair;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class SceneBean {
    public static final int TASK_TYPE_DATA = 1;
    public static final int TASK_TYPE_JUMP = 2;
    public static final int TASK_TYPE_UI = 0;
    public boolean mBlockBool;
    public int mMethodType;
    public Pair<Class, String> mPair;
}
