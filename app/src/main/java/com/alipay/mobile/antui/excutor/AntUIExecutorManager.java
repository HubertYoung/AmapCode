package com.alipay.mobile.antui.excutor;

import com.alipay.mobile.antui.excutor.impl.BaseLoggerExecutorImpl;
import com.alipay.mobile.antui.excutor.impl.BaseLottieExecutorImpl;

public class AntUIExecutorManager {
    private static AntUIExecutorManager sInstance;
    private ConfigExecutor configExecutor;
    private FileLoadExecutor fileLoadExecutor;
    private LoggerExecutor loggerExecutor = new BaseLoggerExecutorImpl();
    private LottieViewExecutor lottieViewExecutor = new BaseLottieExecutorImpl();

    private AntUIExecutorManager() {
    }

    public static AntUIExecutorManager getInstance() {
        if (sInstance == null) {
            synchronized (AntUIExecutorManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new AntUIExecutorManager();
                    }
                }
            }
        }
        return sInstance;
    }

    public void setLoggerExecutor(LoggerExecutor loggerExecutor2) {
        this.loggerExecutor = loggerExecutor2;
    }

    public LoggerExecutor getLoggerExecutor() {
        return this.loggerExecutor;
    }

    public void setLottieViewExecutor(LottieViewExecutor lottieViewExecutor2) {
        this.lottieViewExecutor = lottieViewExecutor2;
    }

    public LottieViewExecutor getLottieViewExecutor() {
        return this.lottieViewExecutor;
    }

    public void setConfigExecutor(ConfigExecutor configExecutor2) {
        this.configExecutor = configExecutor2;
    }

    public ConfigExecutor getConfigExecutor() {
        return this.configExecutor;
    }

    public void setFileLoadExecutor(FileLoadExecutor fileLoadExecutor2) {
        this.fileLoadExecutor = fileLoadExecutor2;
    }

    public FileLoadExecutor getFileLoadExecutor() {
        return this.fileLoadExecutor;
    }
}
