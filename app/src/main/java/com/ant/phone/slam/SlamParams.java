package com.ant.phone.slam;

import java.util.List;

public class SlamParams {
    public float cameraFocalLength;
    public int cameraFps = 20000;
    public float cameraHorizontalViewAngle;
    public int cameraPictureSizeHeight;
    public int cameraPictureSizeWidth;
    public List<PreviewSize> cameraSupportPreviewSizeList;
    public float cameraVerticalViewAngle;
    public float defCamDistance = -1.0f;
    public int viewHeight = -1;
    public int viewWidth = -1;

    public class PreviewSize {
        public int height;
        public int width;

        public PreviewSize(int w, int h) {
            this.width = w;
            this.height = h;
        }

        public String toString() {
            return "PreviewSize{width=" + this.width + ", height=" + this.height + '}';
        }
    }

    public String toString() {
        return "SlamParams{viewWidth=" + this.viewWidth + ", viewHeight=" + this.viewHeight + ", defCamDistance=" + this.defCamDistance + ", cameraFps=" + this.cameraFps + ", cameraHorizontalViewAngle=" + this.cameraHorizontalViewAngle + ", cameraVerticalViewAngle=" + this.cameraVerticalViewAngle + ", cameraFocalLength=" + this.cameraFocalLength + ", cameraPictureSizeWidth=" + this.cameraPictureSizeWidth + ", cameraPictureSizeHeight=" + this.cameraPictureSizeHeight + ", cameraSupportPreviewSizeList=" + this.cameraSupportPreviewSizeList + '}';
    }
}
