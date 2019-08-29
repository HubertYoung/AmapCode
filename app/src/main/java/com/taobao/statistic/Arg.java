package com.taobao.statistic;

@Deprecated
public class Arg {
    private Object mArg1;
    private Object mArg2;
    private Object mArg3;
    private String[] mArgs;

    public Arg(Object obj, Object obj2, Object obj3, String... strArr) {
        this.mArg1 = obj;
        this.mArg2 = obj2;
        this.mArg3 = obj3;
        this.mArgs = strArr;
    }

    public Object getArg1() {
        return this.mArg1;
    }

    public void setArg1(Object obj) {
        this.mArg1 = obj;
    }

    public Object getArg2() {
        return this.mArg2;
    }

    public void setArg2(Object obj) {
        this.mArg2 = obj;
    }

    public Object getArg3() {
        return this.mArg3;
    }

    public void setArg3(Object obj) {
        this.mArg3 = obj;
    }

    public String[] getArgs() {
        return this.mArgs;
    }

    public void setArgs(String... strArr) {
        this.mArgs = strArr;
    }
}
