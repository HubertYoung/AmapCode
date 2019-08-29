package com.alipay.multimedia.gles;

import android.graphics.Bitmap;
import android.hardware.Camera.Size;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class FullFrameRect {
    private final Drawable2d a = new Drawable2d();
    private Texture2dProgram b;
    private FloatBuffer c;
    private FloatBuffer d = null;

    public FullFrameRect(Texture2dProgram program) {
        this.b = program;
    }

    public void release(boolean doEglCleanup) {
        if (this.b != null) {
            if (doEglCleanup) {
                this.b.release();
            }
            this.b = null;
        }
    }

    public Texture2dProgram getProgram() {
        return this.b;
    }

    public void changeProgram(Texture2dProgram program) {
        this.b.release();
        this.b = program;
    }

    public int createTextureObject() {
        return this.b.createTextureObject();
    }

    public int createImageTexture(Bitmap data, GL10 gl) {
        return this.b.createImageTexture(data, gl);
    }

    public void freeImageTexture(int id) {
        this.b.freeImageTexture(id);
    }

    public void drawFrame(int textureId, float[] texMatrix) {
        this.b.draw(GlUtil.IDENTITY_MATRIX, this.a.getVertexArray(), 0, this.a.getVertexCount(), this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, this.a.getTexCoordArray(), textureId, this.a.getTexCoordStride());
    }

    public void drawFrame(int textureId, float[] texMatrix, FloatBuffer texCoord) {
        this.b.draw(GlUtil.IDENTITY_MATRIX, this.a.getVertexArray(), 0, this.a.getVertexCount(), this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, texCoord, textureId, this.a.getTexCoordStride());
    }

    public void drawFrame(int textureId, float[] texMatrix, float[] mvpMatrix) {
        this.b.draw(mvpMatrix, this.a.getVertexArray(), 0, this.a.getVertexCount(), this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, this.a.getTexCoordArray(), textureId, this.a.getTexCoordStride());
    }

    public void drawFrameClearBack(int textureId, float[] texMatrix, float[] mvpMatrix, float smooth, float thresholdSensitivity, int color, int replace, int replaceColor) {
        this.b.draw(mvpMatrix, this.a.getVertexArray(), 0, this.a.getVertexCount(), this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, this.a.getTexCoordArray(), textureId, this.a.getTexCoordStride(), smooth, thresholdSensitivity, color, replace, replaceColor);
    }

    public void drawFrameTransparent(int textureId, float[] texMatrix, int video_w, int video_h, int view_w, int view_h) {
        drawCroppedFrame(textureId, texMatrix, video_w, video_h, view_w, view_h, true);
    }

    public void drawFrameTransparent(int textureId, float[] texMatrix, float[] mvpMatrix) {
        if (this.c == null) {
            ByteBuffer bb = ByteBuffer.allocateDirect(32);
            bb.order(ByteOrder.nativeOrder());
            this.c = bb.asFloatBuffer();
            this.c.put(new float[]{0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f});
            this.c.position(0);
        }
        this.b.draw(mvpMatrix, this.a.getVertexArray(), 0, this.a.getVertexCount(), this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, this.c, textureId, this.a.getTexCoordStride());
    }

    public void drawFrame(int textureId, float[] texMatrix, FloatBuffer vetexArray, int vetexCount) {
        this.b.draw(GlUtil.IDENTITY_MATRIX, vetexArray, 0, vetexCount, this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, this.a.getTexCoordArray(), textureId, this.a.getTexCoordStride());
    }

    private void a(int image_w, int image_h) {
        FloatBuffer buffer0 = this.a.getTexCoordArray();
        int count = buffer0.capacity();
        if (this.d == null) {
            ByteBuffer bb = ByteBuffer.allocateDirect(count * 4);
            bb.order(ByteOrder.nativeOrder());
            this.d = bb.asFloatBuffer();
            if (image_w * 9 <= image_h * 16) {
                float delta = ((float) (image_h - ((image_w * 9) / 16))) / 2.0f;
                for (int i = 0; i < count; i++) {
                    float fl = buffer0.get(i);
                    if (i == 0 || i == 4) {
                        fl = delta / ((float) image_h);
                    }
                    if (i == 2 || i == 6) {
                        fl = (((float) image_h) - delta) / ((float) image_h);
                    }
                    this.d.put(i, fl);
                }
                return;
            }
            float delta2 = ((float) (image_w - ((image_h * 16) / 9))) / 2.0f;
            for (int i2 = 0; i2 < count; i2++) {
                float fl2 = buffer0.get(i2);
                if (i2 == 1 || i2 == 3) {
                    fl2 = delta2 / ((float) image_w);
                }
                if (i2 == 5 || i2 == 7) {
                    fl2 = (((float) image_w) - delta2) / ((float) image_w);
                }
                this.d.put(i2, fl2);
            }
        }
    }

    public void drawCroppedFrame(int textureId, float[] texMatrix, Size size) {
        a(size.width, size.height);
        this.b.draw(GlUtil.IDENTITY_MATRIX, this.a.getVertexArray(), 0, this.a.getVertexCount(), this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, this.d, textureId, this.a.getTexCoordStride());
    }

    public void drawCroppedFrame2(int textureId, int texture1Id, float[] texMatrix, float[] texMatrix1, Size size) {
        a(size.width, size.height);
        this.b.draw2(GlUtil.IDENTITY_MATRIX, this.a.getVertexArray(), 0, this.a.getVertexCount(), this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, texMatrix1, this.d, textureId, texture1Id, this.a.getTexCoordStride());
    }

    public void drawCroppedFrame3(int textureId, int texture1Id, int texture2Id, float[] texMatrix, float[] texMatrix1, float[] texMatrix2, Size size) {
        a(size.width, size.height);
        this.b.draw3(GlUtil.IDENTITY_MATRIX, this.a.getVertexArray(), 0, this.a.getVertexCount(), this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, texMatrix1, texMatrix2, this.d, textureId, texture1Id, texture2Id, this.a.getTexCoordStride());
    }

    public void drawCroppedFrame3_view(int textureId, int texture1Id, int texture2Id, float[] texMatrix, float[] texMatrix1, float[] texMatrix2, Size size) {
        a(size.width, size.height);
        this.b.draw3_view(GlUtil.IDENTITY_MATRIX, this.a.getVertexArray(), 0, this.a.getVertexCount(), this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, texMatrix1, texMatrix2, this.d, textureId, texture1Id, texture2Id, this.a.getTexCoordStride());
    }

    public void drawCroppedFrame(int textureId, float[] texMatrix, int videoWidth, int videoHeight, int displayWidth, int displayHeight) {
        drawCroppedFrame(textureId, texMatrix, videoWidth, videoHeight, displayWidth, displayHeight, false);
    }

    public void drawCroppedFrame(int textureId, float[] texMatrix, int videoWidth, int videoHeight, int displayWidth, int displayHeight, boolean half) {
        FloatBuffer buffer0 = this.a.getTexCoordArray();
        int count = buffer0.capacity();
        if (this.d == null) {
            ByteBuffer bb = ByteBuffer.allocateDirect(count * 4);
            bb.order(ByteOrder.nativeOrder());
            this.d = bb.asFloatBuffer();
            if (videoHeight * displayWidth < displayHeight * videoWidth) {
                float delta = ((float) (videoWidth - ((videoHeight * displayWidth) / displayHeight))) / 2.0f;
                for (int i = 0; i < count; i++) {
                    float fl = buffer0.get(i);
                    if (i == 0 || i == 4) {
                        fl = delta / ((float) videoWidth);
                    }
                    if (i == 2 || i == 6) {
                        fl = (((float) videoWidth) - delta) / ((float) videoWidth);
                    }
                    this.d.put(i, fl);
                }
            } else {
                float delta2 = ((float) (videoHeight - ((videoWidth * displayHeight) / displayWidth))) / 2.0f;
                for (int i2 = 0; i2 < count; i2++) {
                    float fl2 = buffer0.get(i2);
                    if (i2 == 1 || i2 == 3) {
                        fl2 = delta2 / ((float) videoHeight);
                    }
                    if (i2 == 5 || i2 == 7) {
                        fl2 = (((float) videoHeight) - delta2) / ((float) videoHeight);
                    }
                    this.d.put(i2, fl2);
                }
            }
            if (half) {
                for (int i3 = 0; i3 < count; i3++) {
                    float fl3 = this.d.get(i3);
                    if (i3 == 2) {
                        fl3 = (this.d.get(0) + fl3) / 2.0f;
                    }
                    if (i3 == 6) {
                        fl3 = (this.d.get(4) + fl3) / 2.0f;
                    }
                    this.d.put(i3, fl3);
                }
            }
        }
        this.b.draw(GlUtil.IDENTITY_MATRIX, this.a.getVertexArray(), 0, this.a.getVertexCount(), this.a.getCoordsPerVertex(), this.a.getVertexStride(), texMatrix, this.d, textureId, this.a.getTexCoordStride());
    }
}
