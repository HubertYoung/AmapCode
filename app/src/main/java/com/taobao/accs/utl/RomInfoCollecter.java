package com.taobao.accs.utl;

public abstract class RomInfoCollecter {
    protected RomInfoCollecter mNextCollecter;

    public abstract String collect();

    public static RomInfoCollecter getCollecter() {
        return new HuaWeiRomCollecter();
    }
}
