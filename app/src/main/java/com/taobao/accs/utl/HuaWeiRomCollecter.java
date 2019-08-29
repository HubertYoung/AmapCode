package com.taobao.accs.utl;

public class HuaWeiRomCollecter extends RomInfoCollecter {
    public String collect() {
        String emuiVersion = UtilityImpl.getEmuiVersion();
        return (emuiVersion != null || this.mNextCollecter == null) ? emuiVersion : this.mNextCollecter.collect();
    }
}
