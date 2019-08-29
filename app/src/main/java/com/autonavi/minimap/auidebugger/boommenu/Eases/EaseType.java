package com.autonavi.minimap.auidebugger.boommenu.Eases;

public enum EaseType {
    EaseInSine(cnf.class),
    EaseOutSine(cnp.class),
    EaseInOutSine(cnb.class),
    EaseInQuad(cnc.class),
    EaseOutQuad(cnm.class),
    EaseInOutQuad(cmy.class),
    EaseInCubic(cmp.class),
    EaseOutCubic(cnj.class),
    EaseInOutCubic(cmv.class),
    EaseInQuart(cnd.class),
    EaseOutQuart(cnn.class),
    EaseInOutQuart(cmz.class),
    EaseInQuint(cne.class),
    EaseOutQuint(cno.class),
    EaseInOutQuint(cna.class),
    EaseInExpo(cmr.class),
    EaseOutExpo(cnl.class),
    EaseInOutExpo(cmx.class),
    EaseInCirc(cmo.class),
    EaseOutCirc(cni.class),
    EaseInOutCirc(cmu.class),
    EaseInBack(cmm.class),
    EaseOutBack(cng.class),
    EaseInOutBack(cms.class),
    EaseInElastic(cmq.class),
    EaseOutElastic(cnk.class),
    EaseInOutElastic(cmw.class),
    EaseInBounce(cmn.class),
    EaseOutBounce(cnh.class),
    EaseInOutBounce(cmt.class),
    Linear(cnq.class);
    
    private Class easingType;

    private EaseType(Class cls) {
        this.easingType = cls;
    }

    public final float getOffset(float f) {
        try {
            return ((cml) this.easingType.getConstructor(new Class[0]).newInstance(new Object[0])).a(f);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("CubicBezier init error.");
        }
    }
}
