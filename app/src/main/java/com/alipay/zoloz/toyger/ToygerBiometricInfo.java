package com.alipay.zoloz.toyger;

import com.alipay.zoloz.toyger.ToygerAttr;
import com.alipay.zoloz.toyger.algorithm.TGFrame;

public abstract class ToygerBiometricInfo<Attr extends ToygerAttr> {
    public Attr attr;
    public TGFrame frame;
}
