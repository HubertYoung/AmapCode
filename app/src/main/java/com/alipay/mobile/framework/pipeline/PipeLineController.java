package com.alipay.mobile.framework.pipeline;

public class PipeLineController {
    private static PipeLineController a;
    private Pausable b;

    public interface Pausable {
        void pause();

        void resume();
    }

    public static synchronized void createInstance(Pausable pausable) {
        synchronized (PipeLineController.class) {
            if (a == null) {
                a = new PipeLineController(pausable);
            }
        }
    }

    public static synchronized PipeLineController getInstance() {
        PipeLineController pipeLineController;
        synchronized (PipeLineController.class) {
            pipeLineController = a;
        }
        return pipeLineController;
    }

    private PipeLineController(Pausable pausable) {
        this.b = pausable;
    }

    public void pausePipeline() {
        if (this.b != null) {
            this.b.pause();
        }
    }

    public void resumePipeline() {
        if (this.b != null) {
            this.b.resume();
        }
    }
}
