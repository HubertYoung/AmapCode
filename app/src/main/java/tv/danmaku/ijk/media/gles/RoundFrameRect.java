package tv.danmaku.ijk.media.gles;

import android.graphics.Point;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import tv.danmaku.ijk.media.gles.GLRoundedGeometry.GeometryArrays;

public class RoundFrameRect {
    private static final int FLOAT_SIZE_BYTES = 4;
    private static int GL_TEXTURE_EXTERNAL_OES = 36197;
    private static final int SHORT_SIZE_BYTES = 2;
    private static String TAG = "VideoRender";
    private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
    private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 20;
    private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
    private static final String mFragmentShader2d = "precision mediump float;\nvarying vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
    private final String mFragmentShader;
    private float[] mMVPMatrix;
    private int mProgram;
    private RectF mRoundRadius;
    private GLRoundedGeometry mRoundedGeometry;
    private boolean mThumb;
    private ShortBuffer mTriangleIndices;
    private short[] mTriangleIndicesData;
    private FloatBuffer mTriangleVertices;
    private float[] mTriangleVerticesData;
    private final String mVertexShader;
    private final RectF mViewPortGLBounds;
    private final Point mViewPortSize;
    private int maPositionHandle;
    private int maTextureHandle;
    private int muMVPMatrixHandle;
    private int muSTMatrixHandle;

    public RoundFrameRect(boolean thumb) {
        this(new GLRoundedGeometry(), new RectF(-1.0f, 1.0f, 1.0f, -1.0f), thumb);
    }

    public RoundFrameRect(GLRoundedGeometry roundedGeometry, RectF viewPortGLBounds, boolean thumb) {
        this.mVertexShader = "uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n";
        this.mFragmentShader = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
        this.mMVPMatrix = new float[16];
        this.mRoundRadius = new RectF();
        this.mViewPortSize = new Point();
        this.mRoundedGeometry = roundedGeometry;
        this.mViewPortGLBounds = viewPortGLBounds;
        this.mViewPortSize.set(1, 1);
        Matrix.setIdentityM(this.mMVPMatrix, 0);
        this.mThumb = thumb;
    }

    public void setBubbleEffect(int type) {
        this.mRoundedGeometry.setBubbleType(type);
    }

    public void setTriangleOffset(int offset) {
        this.mRoundedGeometry.setTriangleOffsetY(offset);
    }

    public void setCornerRadius(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        this.mRoundRadius.left = topLeft;
        this.mRoundRadius.top = topRight;
        this.mRoundRadius.right = bottomRight;
        this.mRoundRadius.bottom = bottomLeft;
        if (this.mViewPortSize.x > 1) {
            updateVertexData();
        }
    }

    private void updateVertexData() {
        GeometryArrays arrays = this.mRoundedGeometry.generateVertexData(this.mRoundRadius, this.mViewPortGLBounds, this.mViewPortSize);
        this.mTriangleVerticesData = arrays.triangleVertices;
        this.mTriangleIndicesData = arrays.triangleIndices;
        if (this.mTriangleVertices != null) {
            this.mTriangleVertices.clear();
        } else {
            this.mTriangleVertices = ByteBuffer.allocateDirect(this.mTriangleVerticesData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        }
        if (this.mTriangleIndices != null) {
            this.mTriangleIndices.clear();
        } else {
            this.mTriangleIndices = ByteBuffer.allocateDirect(this.mTriangleIndicesData.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        }
        this.mTriangleVertices.put(this.mTriangleVerticesData).position(0);
        this.mTriangleIndices.put(this.mTriangleIndicesData).position(0);
    }

    public void setupData(int width, int height) {
        this.mViewPortSize.set(width, height);
        updateVertexData();
        if (!this.mThumb) {
            this.mProgram = createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        } else {
            this.mProgram = createProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", mFragmentShader2d);
        }
        if (this.mProgram != 0) {
            this.maPositionHandle = GLES20.glGetAttribLocation(this.mProgram, "aPosition");
            checkGlError("glGetAttribLocation aPosition");
            if (this.maPositionHandle == -1) {
                throw new RuntimeException("Could not get attrib location for aPosition");
            }
            this.maTextureHandle = GLES20.glGetAttribLocation(this.mProgram, "aTextureCoord");
            checkGlError("glGetAttribLocation aTextureCoord");
            if (this.maTextureHandle == -1) {
                throw new RuntimeException("Could not get attrib location for aTextureCoord");
            }
            this.muMVPMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uMVPMatrix");
            checkGlError("glGetUniformLocation uMVPMatrix");
            if (this.muMVPMatrixHandle == -1) {
                throw new RuntimeException("Could not get attrib location for uMVPMatrix");
            }
            this.muSTMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uSTMatrix");
            checkGlError("glGetUniformLocation uSTMatrix");
            if (this.muSTMatrixHandle == -1) {
                throw new RuntimeException("Could not get attrib location for uSTMatrix");
            }
        }
    }

    public int createTextureObject() {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        int texId = textures[0];
        GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, texId);
        checkGlError("glBindTexture mTextureID");
        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES, 10241, 9729.0f);
        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES, 10240, 9729.0f);
        return texId;
    }

    public void drawFrame(int texId, float[] stmatrix, float[] mvpMatrix) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16640);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        GLES20.glUseProgram(this.mProgram);
        checkGlError("glUseProgram");
        GLES20.glActiveTexture(33984);
        if (this.mThumb) {
            GLES20.glBindTexture(3553, texId);
        } else {
            GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, texId);
        }
        this.mTriangleVertices.position(0);
        GLES20.glVertexAttribPointer(this.maPositionHandle, 3, 5126, false, 20, this.mTriangleVertices);
        checkGlError("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.maPositionHandle);
        checkGlError("glEnableVertexAttribArray maPositionHandle");
        this.mTriangleVertices.position(3);
        GLES20.glVertexAttribPointer(this.maTextureHandle, 3, 5126, false, 20, this.mTriangleVertices);
        checkGlError("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.maTextureHandle);
        checkGlError("glEnableVertexAttribArray maTextureHandle");
        if (!this.mThumb) {
            Matrix.scaleM(mvpMatrix, 0, 1.0f, 1.0f, 1.0f);
        }
        GLES20.glUniformMatrix4fv(this.muMVPMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glUniformMatrix4fv(this.muSTMatrixHandle, 1, false, stmatrix, 0);
        GLES20.glDrawElements(4, this.mTriangleIndicesData.length, 5123, this.mTriangleIndices);
        checkGlError("glDrawElements");
        GLES20.glFinish();
    }

    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader == 0) {
            return shader;
        }
        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, 35713, compiled, 0);
        if (compiled[0] != 0) {
            return shader;
        }
        Logger.D(TAG, "Could not compile shader " + shaderType + ":", new Object[0]);
        Logger.D(TAG, GLES20.glGetShaderInfoLog(shader), new Object[0]);
        GLES20.glDeleteShader(shader);
        return 0;
    }

    private int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(35633, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
        int pixelShader = loadShader(35632, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }
        int program = GLES20.glCreateProgram();
        if (program == 0) {
            return program;
        }
        GLES20.glAttachShader(program, vertexShader);
        checkGlError("glAttachShader");
        GLES20.glAttachShader(program, pixelShader);
        checkGlError("glAttachShader");
        GLES20.glLinkProgram(program);
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
        if (linkStatus[0] == 1) {
            return program;
        }
        Logger.D(TAG, "Could not link program: ", new Object[0]);
        Logger.D(TAG, GLES20.glGetProgramInfoLog(program), new Object[0]);
        GLES20.glDeleteProgram(program);
        return 0;
    }

    private void checkGlError(String op) {
        int error = GLES20.glGetError();
        if (error != 0) {
            Logger.D(TAG, op + ": glError " + error, new Object[0]);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
}
