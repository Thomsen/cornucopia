package com.cornucopia.graphics.opengl;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by thom on 26/12/2017.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {


    private Triangle mTriangle;

    private Square mSquare;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];  // 将 local space 坐标转换 world space 的变换矩阵

    private final float[] mViewMatrix = new float[16];  // 将 world space 坐标转换 view space 的变换矩阵

    private final float[] mProjectionMatrix = new float[16];  // 将 view space 坐标转换 clip space 的变换矩阵

    // Mclip = Mprojection * Mview  * Mmodel * Mlocal


    public volatile float mAngle;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        GLES31.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        mTriangle = new Triangle();

        mSquare = new Square();

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {

        GLES31.glViewport(0, 0, width, height);

        // camera

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    private float[] mRotationMatrix = new float[16];

    @Override
    public void onDrawFrame(GL10 gl10) {

        GLES31.glClear(GLES20.GL_COLOR_BUFFER_BIT);

//        mTriangle.draw();

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw shape
        mTriangle.draw(mMVPMatrix);

        float[] scratch = new float[16];

//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * time;

        float angle = mAngle;

        // // RENDERMODE_WHEN_DIRTY not always rotation
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        mTriangle.draw(scratch);
    }


    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);

        GLES20.glCompileShader(shader);

        return shader;
    }

    public void setAngle(float angle) {
        this.mAngle = angle;
    }

    public float getAngle() {
        return mAngle;
    }
}
