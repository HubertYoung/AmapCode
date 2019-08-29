package com.alipay.streammedia.mmengine.picture.jpg;

public class PictureJpegProgAttribute {

    public enum ProgLayers {
        LAYERS4(4, "4层"),
        LAYERS10(10, "10层");
        
        private int index;
        private String name;

        private ProgLayers(int index2, String name2) {
            this.index = index2;
            this.name = name2;
        }

        public final int getIndex() {
            return this.index;
        }

        public final void setIndex(int index2) {
            this.index = index2;
        }

        public final String getName() {
            return this.name;
        }

        public final void setName(String name2) {
            this.name = name2;
        }

        public static String getNameByIndex(int index2) {
            ProgLayers[] values;
            for (ProgLayers t : values()) {
                if (t.getIndex() == index2) {
                    return t.getName();
                }
            }
            return "Unknown Mode";
        }
    }
}
