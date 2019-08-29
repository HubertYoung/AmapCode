package com.alipay.multimedia.gles;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.text.TextUtils;
import com.alipay.alipaylogger.Log;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Texture2dProgram {
    public static final int KERNEL_SIZE = 9;
    private static int p = 36197;
    private ProgramType a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private float[] m = new float[9];
    private float[] n;
    private float o;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;

    public enum ProgramType {
        TEXTURE_2D,
        TEXTURE_EXT,
        TEXTURE_EXT_BW,
        TEXTURE_EXT_FILT,
        TEXTURE_EXT_MERGE_2,
        TEXTURE_EXT_MERGE_2_OES,
        TEXTURE_EXT_MERGE_3,
        TEXTURE_EXT_MERGE_VIEW_3,
        TEXTURE_EXT_CLEAR_BACK,
        TEXTURE_EXT_TRANSPARENT
    }

    public Texture2dProgram(ProgramType programType) {
        this.a = programType;
        switch (programType) {
            case TEXTURE_2D:
                this.l = 3553;
                this.b = GlUtil.createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "precision mediump float;\nvarying vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
                break;
            case TEXTURE_EXT:
                this.l = p;
                this.b = GlUtil.createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
                break;
            case TEXTURE_EXT_BW:
                this.l = p;
                this.b = GlUtil.createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    vec4 tc = texture2D(sTexture, vTextureCoord);\n    float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;\n    gl_FragColor = vec4(color, color, color, 1.0);\n}\n");
                break;
            case TEXTURE_EXT_FILT:
                this.l = p;
                this.b = GlUtil.createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\n#define KERNEL_SIZE 9\nprecision highp float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nuniform float uKernel[KERNEL_SIZE];\nuniform vec2 uTexOffset[KERNEL_SIZE];\nuniform float uColorAdjust;\nvoid main() {\n    int i = 0;\n    vec4 sum = vec4(0.0);\n    if (vTextureCoord.x < vTextureCoord.y - 0.005) {\n        for (i = 0; i < KERNEL_SIZE; i++) {\n            vec4 texc = texture2D(sTexture, vTextureCoord + uTexOffset[i]);\n            sum += texc * uKernel[i];\n        }\n    sum += uColorAdjust;\n    } else if (vTextureCoord.x > vTextureCoord.y + 0.005) {\n        sum = texture2D(sTexture, vTextureCoord);\n    } else {\n        sum.r = 1.0;\n    }\n    gl_FragColor = sum;\n}\n");
                break;
            case TEXTURE_EXT_MERGE_2:
                this.l = p;
                this.b = GlUtil.createProgram("uniform highp mat4 uMVPMatrix;\nuniform highp mat4 uTexMatrix;\nuniform highp mat4 uTexMatrix1;\nattribute highp vec4 aPosition;\nattribute highp vec4 aTextureCoord;\nvarying highp vec2 vTextureCoord;\nvarying highp vec2 vTextureCoord1;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n    vTextureCoord1 = (uTexMatrix1 * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nvarying highp vec2 vTextureCoord; \nvarying highp vec2 vTextureCoord1; \n\nuniform highp samplerExternalOES sTexture; \nuniform highp sampler2D sTexture1; \n\nvoid main() \n{ \n  highp vec4 textureColor = texture2D(sTexture, vTextureCoord); \n  highp vec4 textureColor2 = texture2D(sTexture1, vTextureCoord1); \n  gl_FragColor = textureColor * (1.0-textureColor2.a) + textureColor2; \n} ");
                break;
            case TEXTURE_EXT_MERGE_2_OES:
                this.l = p;
                this.b = GlUtil.createProgram("uniform highp mat4 uMVPMatrix;\nuniform highp mat4 uTexMatrix;\nuniform highp mat4 uTexMatrix1;\nattribute highp vec4 aPosition;\nattribute highp vec4 aTextureCoord;\nvarying highp vec2 vTextureCoord;\nvarying highp vec2 vTextureCoord1;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n    vTextureCoord1 = (uTexMatrix1 * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nvarying highp vec2 vTextureCoord; \nvarying highp vec2 vTextureCoord1; \n\nuniform highp samplerExternalOES sTexture; \nuniform highp samplerExternalOES sTexture1; \n\nvoid main() \n{ \n  highp vec4 textureColor = texture2D(sTexture, vTextureCoord); \n  highp vec4 textureColor2 = texture2D(sTexture1, vTextureCoord1); \n  gl_FragColor = textureColor * (1.0-textureColor2.a) + textureColor2; \n} ");
                break;
            case TEXTURE_EXT_MERGE_3:
                this.l = p;
                this.b = GlUtil.createProgram("uniform highp mat4 uMVPMatrix;\nuniform highp mat4 uTexMatrix;\nuniform highp mat4 uTexMatrix1;\nuniform highp mat4 uTexMatrix2;\nattribute highp vec4 aPosition;\nattribute highp vec4 aTextureCoord;\nvarying highp vec2 vTextureCoord;\nvarying highp vec2 vTextureCoord1;\nvarying highp vec2 vTextureCoord2;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n    vTextureCoord1 = (uTexMatrix1 * aTextureCoord).xy;\n    vTextureCoord2 = (uTexMatrix2 * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nvarying highp vec2 vTextureCoord; \nvarying highp vec2 vTextureCoord1; \nvarying highp vec2 vTextureCoord2; \n\nuniform highp samplerExternalOES sTexture; \nuniform highp samplerExternalOES sTexture1; \nuniform highp sampler2D sTexture2; \n\nvoid main() \n{ \n  highp vec4 textureColor = texture2D(sTexture, vTextureCoord); \n  highp vec4 textureColor1 = texture2D(sTexture1, vTextureCoord1); \n  highp vec4 textureColor2 = texture2D(sTexture2, vTextureCoord2); \n  highp vec4 textureColortmp = textureColor * (1.0-textureColor1.a) + textureColor1;\n  gl_FragColor = textureColortmp * (1.0-textureColor2.a) + textureColor2;\n} ");
                break;
            case TEXTURE_EXT_MERGE_VIEW_3:
                this.l = p;
                this.b = GlUtil.createProgram("uniform highp mat4 uMVPMatrix;\nuniform highp mat4 uTexMatrix;\nuniform highp mat4 uTexMatrix1;\nuniform highp mat4 uTexMatrix2;\nattribute highp vec4 aPosition;\nattribute highp vec4 aTextureCoord;\nvarying highp vec2 vTextureCoord;\nvarying highp vec2 vTextureCoord1;\nvarying highp vec2 vTextureCoord2;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n    vTextureCoord1 = (uTexMatrix1 * aTextureCoord).xy;\n    vTextureCoord2 = (uTexMatrix2 * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nvarying highp vec2 vTextureCoord; \nvarying highp vec2 vTextureCoord1; \nvarying highp vec2 vTextureCoord2; \n\nuniform highp samplerExternalOES sTexture; \nuniform highp sampler2D sTexture1; \nuniform highp sampler2D sTexture2; \n\nvoid main() \n{ \n  highp vec4 textureColor = texture2D(sTexture, vTextureCoord); \n  highp vec4 textureColor1 = texture2D(sTexture1, vTextureCoord1); \n  highp vec4 textureColor2 = texture2D(sTexture2, vTextureCoord2); \n  highp vec4 textureColortmp = textureColor * (1.0-textureColor1.a) + textureColor1; \n  gl_FragColor = textureColortmp * (1.0-textureColor2.a) + textureColor2; \n} ");
                break;
            case TEXTURE_EXT_CLEAR_BACK:
                this.l = p;
                this.b = GlUtil.createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision highp float;\nvarying highp vec2 vTextureCoord;\tuniform float thresholdSensitivity;\nuniform float smoothing;\nuniform int replaceForeground;\nuniform vec3 foregroundColor;\nuniform vec3 backgroundColor;\nuniform samplerExternalOES sTexture;\nvec2 convert(vec3 color) {\nfloat Y = 0.2989 * color.r + 0.5866 * color.g + 0.1145 * color.b;\nfloat Cr = 0.7132 * (color.r - Y);\nfloat Cb = 0.5647 * (color.b - Y);\nreturn vec2(Cr, Cb);\n}\nvoid main() {\nvec4 textureColor = texture2D(sTexture, vTextureCoord);\nvec2 background = convert(backgroundColor);\nvec2 target = convert(textureColor.rgb);\nfloat blendValue = smoothstep(thresholdSensitivity, thresholdSensitivity + smoothing, distance(target, background));\nvec3 color;\nif (replaceForeground != 0) {\ncolor = foregroundColor;}\nelse { color = textureColor.rgb; }\ngl_FragColor = vec4(color, blendValue);\n}\n");
                break;
            case TEXTURE_EXT_TRANSPARENT:
                this.l = p;
                this.b = GlUtil.createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision highp float;\nvarying highp vec2 vTextureCoord;\tuniform samplerExternalOES sTexture;\nvoid main() {\nvec4 textureColor = texture2D(sTexture, vTextureCoord);\nvec2 textureAlphaCoordinate = vec2(vTextureCoord.x + 0.5, vTextureCoord.y);\nvec4 textureAlpha = texture2D(sTexture, textureAlphaCoordinate);\ngl_FragColor = vec4(textureColor.rgb, textureAlpha.r);\n}\n");
                break;
            default:
                throw new RuntimeException("Unhandled type " + programType);
        }
        if (this.b == 0) {
            throw new RuntimeException("Unable to create program");
        }
        Log.d(GlUtil.TAG, "Created program " + this.b + " (" + programType + ")");
        this.j = GLES20.glGetAttribLocation(this.b, "aPosition");
        GlUtil.checkLocation(this.j, "aPosition");
        this.k = GLES20.glGetAttribLocation(this.b, "aTextureCoord");
        GlUtil.checkLocation(this.k, "aTextureCoord");
        this.c = GLES20.glGetUniformLocation(this.b, "uMVPMatrix");
        GlUtil.checkLocation(this.c, "uMVPMatrix");
        this.d = GLES20.glGetUniformLocation(this.b, "uTexMatrix");
        GlUtil.checkLocation(this.d, "uTexMatrix");
        this.e = GLES20.glGetUniformLocation(this.b, "uTexMatrix1");
        GlUtil.checkLocation(this.d, "uTexMatrix1");
        this.f = GLES20.glGetUniformLocation(this.b, "uTexMatrix2");
        GlUtil.checkLocation(this.d, "uTexMatrix2");
        if (this.a == ProgramType.TEXTURE_EXT_CLEAR_BACK) {
            this.r = GLES20.glGetUniformLocation(this.b, "smoothing");
            GlUtil.checkLocation(this.r, "smoothing");
            this.q = GLES20.glGetUniformLocation(this.b, "thresholdSensitivity");
            GlUtil.checkLocation(this.q, "thresholdSensitivity");
            this.s = GLES20.glGetUniformLocation(this.b, "backgroundColor");
            GlUtil.checkLocation(this.s, "backgroundColor");
            this.t = GLES20.glGetUniformLocation(this.b, "foregroundColor");
            GlUtil.checkLocation(this.t, "foregroundColor");
            this.u = GLES20.glGetUniformLocation(this.b, "replaceForeground");
            GlUtil.checkLocation(this.u, "replaceForeground");
        }
        this.g = GLES20.glGetUniformLocation(this.b, "uKernel");
        if (this.g < 0) {
            this.g = -1;
            this.h = -1;
            this.i = -1;
            return;
        }
        this.h = GLES20.glGetUniformLocation(this.b, "uTexOffset");
        GlUtil.checkLocation(this.h, "uTexOffset");
        this.i = GLES20.glGetUniformLocation(this.b, "uColorAdjust");
        GlUtil.checkLocation(this.i, "uColorAdjust");
        setKernel(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f}, 0.0f);
        setTexSize(256, 256);
    }

    public void release() {
        Log.d(GlUtil.TAG, "deleting program " + this.b);
        GLES20.glDeleteProgram(this.b);
        this.b = -1;
    }

    public ProgramType getProgramType() {
        return this.a;
    }

    public int getProgramHandler() {
        return this.b;
    }

    public int createTextureObject() {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        GlUtil.checkGlError("glGenTextures");
        int texId = textures[0];
        GLES20.glBindTexture(this.l, texId);
        GlUtil.checkGlError("glBindTexture " + texId);
        GLES20.glTexParameterf(p, 10241, 9729.0f);
        GLES20.glTexParameterf(p, 10240, 9729.0f);
        GLES20.glTexParameteri(p, 10242, 33071);
        GLES20.glTexParameteri(p, 10243, 33071);
        GlUtil.checkGlError("glTexParameter");
        return texId;
    }

    public int createImageTexture(Bitmap bitmap, GL10 gl) {
        int[] textureHandles = new int[1];
        GLES20.glGenTextures(1, textureHandles, 0);
        int textureHandle = textureHandles[0];
        GlUtil.checkGlError("glGenTextures");
        GLES20.glBindTexture(3553, textureHandle);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GlUtil.checkGlError("loadImageTexture");
        if (!a(gl)) {
            Drawable drawable = new BitmapDrawable(bitmap);
            int originalWidth = drawable.getIntrinsicWidth();
            int originalHeight = drawable.getIntrinsicHeight();
            int powWidth = a(originalWidth);
            int powHeight = a(originalHeight);
            drawable.setBounds(0, 0, powWidth, powHeight);
            Bitmap bitmapPO2 = Bitmap.createBitmap(powWidth, powHeight, Config.ARGB_4444);
            Canvas canvas = new Canvas(bitmapPO2);
            bitmapPO2.eraseColor(0);
            drawable.draw(canvas);
            GLUtils.texImage2D(3553, 0, bitmapPO2, 0);
            bitmapPO2.recycle();
        } else {
            GLUtils.texImage2D(3553, 0, bitmap, 0);
        }
        return textureHandle;
    }

    public void freeImageTexture(int id) {
        GLES20.glDeleteTextures(1, new int[]{id}, 0);
        GlUtil.checkGlError("glDeleteTextures");
    }

    public void setKernel(float[] values, float colorAdj) {
        if (values.length != 9) {
            throw new IllegalArgumentException("Kernel size is " + values.length + " vs. 9");
        }
        System.arraycopy(values, 0, this.m, 0, 9);
        this.o = colorAdj;
    }

    public void setTexSize(int width, int height) {
        float rw = 1.0f / ((float) width);
        float rh = 1.0f / ((float) height);
        this.n = new float[]{-rw, -rh, 0.0f, -rh, rw, -rh, -rw, 0.0f, 0.0f, 0.0f, rw, 0.0f, -rw, rh, 0.0f, rh, rw, rh};
    }

    public void draw(float[] mvpMatrix, FloatBuffer vertexBuffer, int firstVertex, int vertexCount, int coordsPerVertex, int vertexStride, float[] texMatrix, FloatBuffer texBuffer, int textureId, int texStride) {
        GlUtil.checkGlError("draw start");
        GLES20.glUseProgram(this.b);
        GlUtil.checkGlError("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(this.l, textureId);
        GLES20.glUniformMatrix4fv(this.c, 1, false, mvpMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.d, 1, false, texMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glEnableVertexAttribArray(this.j);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.j, coordsPerVertex, 5126, false, vertexStride, vertexBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(this.k);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.k, 2, 5126, false, texStride, texBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");
        if (this.g >= 0) {
            GLES20.glUniform1fv(this.g, 9, this.m, 0);
            GLES20.glUniform2fv(this.h, 9, this.n, 0);
            GLES20.glUniform1f(this.i, this.o);
        }
        GLES20.glDrawArrays(5, firstVertex, vertexCount);
        GlUtil.checkGlError("glDrawArrays");
        GLES20.glDisableVertexAttribArray(this.j);
        GLES20.glDisableVertexAttribArray(this.k);
        GLES20.glBindTexture(this.l, 0);
        GLES20.glUseProgram(0);
    }

    public void draw2(float[] mvpMatrix, FloatBuffer vertexBuffer, int firstVertex, int vertexCount, int coordsPerVertex, int vertexStride, float[] texMatrix, float[] texMatrix1, FloatBuffer texBuffer, int textureId, int texture1Id, int texStride) {
        GlUtil.checkGlError("draw2 start");
        GLES20.glUseProgram(this.b);
        GlUtil.checkGlError("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(this.l, textureId);
        int id = GLES20.glGetUniformLocation(this.b, "sTexture");
        GlUtil.checkLocation(id, "sTexture");
        GLES20.glUniform1i(id, 0);
        GLES20.glActiveTexture(33985);
        int secondTexTarget = 3553;
        if (this.a == ProgramType.TEXTURE_EXT_MERGE_2_OES) {
            secondTexTarget = p;
        }
        GLES20.glBindTexture(secondTexTarget, texture1Id);
        int id1 = GLES20.glGetUniformLocation(this.b, "sTexture1");
        GlUtil.checkLocation(id1, "sTexture1");
        GLES20.glUniform1i(id1, 1);
        GLES20.glUniformMatrix4fv(this.c, 1, false, mvpMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.d, 1, false, texMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.e, 1, false, texMatrix1, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glEnableVertexAttribArray(this.j);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.j, coordsPerVertex, 5126, false, vertexStride, vertexBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(this.k);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.k, 2, 5126, false, texStride, texBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glDrawArrays(5, firstVertex, vertexCount);
        GlUtil.checkGlError("glDrawArrays");
        GLES20.glDisableVertexAttribArray(this.j);
        GLES20.glDisableVertexAttribArray(this.k);
        GLES20.glBindTexture(this.l, 0);
        GLES20.glUseProgram(0);
    }

    public void draw3(float[] mvpMatrix, FloatBuffer vertexBuffer, int firstVertex, int vertexCount, int coordsPerVertex, int vertexStride, float[] texMatrix, float[] texMatrix1, float[] texMatrix2, FloatBuffer texBuffer, int textureId, int texture1Id, int texture2Id, int texStride) {
        GlUtil.checkGlError("draw3 start");
        GLES20.glUseProgram(this.b);
        GlUtil.checkGlError("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(this.l, textureId);
        int id = GLES20.glGetUniformLocation(this.b, "sTexture");
        GlUtil.checkLocation(id, "sTexture");
        GLES20.glUniform1i(id, 0);
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(this.l, texture1Id);
        int id1 = GLES20.glGetUniformLocation(this.b, "sTexture1");
        GlUtil.checkLocation(id1, "sTexture1");
        GLES20.glUniform1i(id1, 1);
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, texture2Id);
        int id2 = GLES20.glGetUniformLocation(this.b, "sTexture2");
        GlUtil.checkLocation(id2, "sTexture2");
        GLES20.glUniform1i(id2, 2);
        GLES20.glUniformMatrix4fv(this.c, 1, false, mvpMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.d, 1, false, texMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.e, 1, false, texMatrix1, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.f, 1, false, texMatrix2, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glEnableVertexAttribArray(this.j);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.j, coordsPerVertex, 5126, false, vertexStride, vertexBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(this.k);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.k, 2, 5126, false, texStride, texBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glDrawArrays(5, firstVertex, vertexCount);
        GlUtil.checkGlError("glDrawArrays");
        GLES20.glDisableVertexAttribArray(this.j);
        GLES20.glDisableVertexAttribArray(this.k);
        GLES20.glBindTexture(this.l, 0);
        GLES20.glUseProgram(0);
    }

    public void draw3_view(float[] mvpMatrix, FloatBuffer vertexBuffer, int firstVertex, int vertexCount, int coordsPerVertex, int vertexStride, float[] texMatrix, float[] texMatrix1, float[] texMatrix2, FloatBuffer texBuffer, int textureId, int texture1Id, int texture2Id, int texStride) {
        GlUtil.checkGlError("draw3_view start");
        GLES20.glUseProgram(this.b);
        GlUtil.checkGlError("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(this.l, textureId);
        int id = GLES20.glGetUniformLocation(this.b, "sTexture");
        GlUtil.checkLocation(id, "sTexture");
        GLES20.glUniform1i(id, 0);
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, texture1Id);
        int id1 = GLES20.glGetUniformLocation(this.b, "sTexture1");
        GlUtil.checkLocation(id1, "sTexture1");
        GLES20.glUniform1i(id1, 1);
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, texture2Id);
        int id2 = GLES20.glGetUniformLocation(this.b, "sTexture2");
        GlUtil.checkLocation(id2, "sTexture2");
        GLES20.glUniform1i(id2, 2);
        GLES20.glUniformMatrix4fv(this.c, 1, false, mvpMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.d, 1, false, texMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.e, 1, false, texMatrix1, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.f, 1, false, texMatrix2, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glEnableVertexAttribArray(this.j);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.j, coordsPerVertex, 5126, false, vertexStride, vertexBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(this.k);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.k, 2, 5126, false, texStride, texBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glDrawArrays(5, firstVertex, vertexCount);
        GlUtil.checkGlError("glDrawArrays");
        GLES20.glDisableVertexAttribArray(this.j);
        GLES20.glDisableVertexAttribArray(this.k);
        GLES20.glBindTexture(this.l, 0);
        GLES20.glUseProgram(0);
    }

    private static int a(int n2) {
        int n3 = n2 - 1;
        int i2 = (n3 >> 1) | n3;
        int i3 = i2 | (i2 >> 2);
        int i4 = i3 | (i3 >> 4);
        int i5 = i4 | (i4 >> 8);
        int i6 = i5 | (i5 >> 16);
        return (i6 | (i6 >> 32)) + 1;
    }

    private static boolean a(GL10 gl) {
        boolean supportsNPOT = true;
        if (gl == null) {
            return true;
        }
        String extensions = gl.glGetString(7939);
        if (TextUtils.isEmpty(extensions)) {
            return false;
        }
        if (extensions.indexOf("GL_OES_texture_npot") == -1) {
            supportsNPOT = false;
        }
        Log.d(GlUtil.TAG, "mSupportsNPOT: " + supportsNPOT);
        return supportsNPOT;
    }

    public void draw(float[] mvpMatrix, FloatBuffer vertexBuffer, int firstVertex, int vertexCount, int coordsPerVertex, int vertexStride, float[] texMatrix, FloatBuffer texBuffer, int textureId, int texStride, float smooth, float thresholdSensitivity, int color, int replace, int replaceColor) {
        float r2 = ((float) ((16711680 & color) >> 16)) / 255.0f;
        float g2 = ((float) ((65280 & color) >> 8)) / 255.0f;
        float b2 = ((float) (color & 255)) / 255.0f;
        Log.i(GlUtil.TAG, "r,g,b:" + r2 + "," + g2 + "," + b2);
        GlUtil.checkGlError("draw start");
        GLES20.glUseProgram(this.b);
        GlUtil.checkGlError("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(this.l, textureId);
        GLES20.glUniformMatrix4fv(this.c, 1, false, mvpMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.d, 1, false, texMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glUniform1f(this.r, smooth);
        GlUtil.checkGlError("glUniform1f");
        GLES20.glUniform1f(this.q, thresholdSensitivity);
        GlUtil.checkGlError("glUniform1f");
        GLES20.glUniform3f(this.s, r2, g2, b2);
        GlUtil.checkGlError("glUniform3f");
        GLES20.glUniform3f(this.t, ((float) ((16711680 & replaceColor) >> 16)) / 255.0f, ((float) ((65280 & replaceColor) >> 8)) / 255.0f, ((float) (replaceColor & 255)) / 255.0f);
        GlUtil.checkGlError("glUniform3f");
        GLES20.glUniform1i(this.u, replace);
        GlUtil.checkGlError("glUniform1i");
        GLES20.glEnableVertexAttribArray(this.j);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.j, coordsPerVertex, 5126, false, vertexStride, vertexBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(this.k);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.k, 2, 5126, false, texStride, texBuffer);
        GlUtil.checkGlError("glVertexAttribPointer");
        if (this.g >= 0) {
            GLES20.glUniform1fv(this.g, 9, this.m, 0);
            GLES20.glUniform2fv(this.h, 9, this.n, 0);
            GLES20.glUniform1f(this.i, this.o);
        }
        GLES20.glDrawArrays(5, firstVertex, vertexCount);
        GlUtil.checkGlError("glDrawArrays");
        GLES20.glDisableVertexAttribArray(this.j);
        GLES20.glDisableVertexAttribArray(this.k);
        GLES20.glBindTexture(this.l, 0);
        GLES20.glUseProgram(0);
    }
}
