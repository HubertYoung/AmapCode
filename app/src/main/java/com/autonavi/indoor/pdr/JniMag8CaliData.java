package com.autonavi.indoor.pdr;

import java.lang.reflect.Array;

public class JniMag8CaliData {
    public double cov_ = 0.0d;
    public double[] param_H_ = new double[3];
    public double[][] param_Sut_ = ((double[][]) Array.newInstance(double.class, new int[]{3, 3}));

    public JniMag8CaliData(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, double d13) {
        this.param_Sut_[0][0] = d;
        this.param_Sut_[0][1] = d2;
        this.param_Sut_[0][2] = d3;
        this.param_Sut_[1][0] = d4;
        this.param_Sut_[1][1] = d5;
        this.param_Sut_[1][2] = d6;
        this.param_Sut_[2][0] = d7;
        this.param_Sut_[2][1] = d8;
        this.param_Sut_[2][2] = d9;
        this.param_H_[0] = d10;
        this.param_H_[1] = d11;
        this.param_H_[2] = d12;
        this.cov_ = d13;
    }
}
