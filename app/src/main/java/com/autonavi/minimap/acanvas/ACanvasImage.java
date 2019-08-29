package com.autonavi.minimap.acanvas;

public class ACanvasImage {
    private final int mId = IdManager.createId();
    private final String mSrc;

    static final class IdManager {
        static int ID_START = 1000;

        private IdManager() {
        }

        static int createId() {
            int i = ID_START + 1;
            ID_START = i;
            if (i > Integer.MAX_VALUE) {
                ID_START = 1000;
            }
            return ID_START;
        }
    }

    public ACanvasImage(String str) {
        this.mSrc = str;
    }

    public int getId() {
        return this.mId;
    }

    public String getSrc() {
        return this.mSrc;
    }
}
