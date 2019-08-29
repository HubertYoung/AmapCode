package com.alipay.multimedia.sound;

public interface SoundEffect {

    public static class Options {
        private boolean ignoreSystemSilent = false;

        public boolean isIgnoreSystemSilent() {
            return this.ignoreSystemSilent;
        }

        public Options setIgnoreSystemSilent(boolean ignoreSystemSilent2) {
            this.ignoreSystemSilent = ignoreSystemSilent2;
            return this;
        }

        public int hashCode() {
            return System.identityHashCode(Boolean.valueOf(this.ignoreSystemSilent));
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            if (this.ignoreSystemSilent != ((Options) o).ignoreSystemSilent) {
                return false;
            }
            return true;
        }

        public String getUniqueKey() {
            return String.valueOf(this.ignoreSystemSilent);
        }
    }

    Options getOptions();

    int getResId();

    void play();

    void release();

    void stop();
}
