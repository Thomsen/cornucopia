package com.cornucopia.ui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

import com.cornucopia.R;

import java.security.GeneralSecurityException;
import java.util.List;

// step 1 extend view

public class ThermometerView extends View implements SensorEventListener {

    // rim 轮廓

    private RectF rimRect;
    private Paint rimPaint;
    private Paint rimCirclePaint;
    
    private Bitmap background;
    private Paint backgroundPaint;
    
    private RectF faceRect;
    private Paint facePaint;
    private Paint rimShadowPaint;
    
    private Paint scalePaint;
    private RectF scaleRect;
    
    private Paint titlePaint;
    private Path titlePath;

    private Paint logoPaint;
    private Bitmap logoBitmap;
    private float logoScale;
    private Matrix logoMatrix;
    
    private Paint handPaint;
    private Path handPath;
    private Paint handScrewPaint;
    
    // scale configuration
    private static final int totalNicks = 100;
    private static final float degreesPerNick = 360.0f / totalNicks;    
    private static final int centerDegree = 40; // the one in the top center (12 o'clock)
    private static final int minDegrees = -30;
    private static final int maxDegrees = 110;

    // hand dynamics
    private float handPosition = centerDegree;
    private long lastHandMoveTime = -1L;
    private float handVelocity = 0.0f;
    private float handAcceleration = 0.0f;
    private float handTarget = centerDegree;
    private boolean handInitialized = false;
    private Handler handler;
    
    // step 2 implement constructor
    
    public ThermometerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public ThermometerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThermometerView(Context context) {
        super(context);
        init(null, 0);
    }
    
    @SuppressLint("NewApi") 
    private void init(AttributeSet attrs, int defStyle) {
        handler = new Handler();
        initDrawingTools();
        
        // solove android 4.1.2 bug, drawPath no effect. or manifest 
        // application node android:hardwareAccelerated="false"
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void initDrawingTools() {
        rimRect = new RectF(0.1f, 0.1f, 0.9f, 0.9f); // 矩形（左，上，右，下）
        
        rimPaint = new Paint(); // 画笔
        rimPaint.setFlags(Paint.ANTI_ALIAS_FLAG); // 抗锯齿
        rimPaint.setShader(new LinearGradient(0.4f, 0.0f, 0.6f, 1.0f, 
                Color.rgb(0xf0, 0xf5, 0xf0), 
                Color.rgb(0x30, 0x31, 0x30), Shader.TileMode.CLAMP));  // 颜色渲染
        
        rimCirclePaint = new Paint();
        rimCirclePaint.setAntiAlias(true); // 抗锯齿
        rimCirclePaint.setStyle(Paint.Style.STROKE); // 线的样式
        rimCirclePaint.setStrokeWidth(0.005f); // 粗细程度
        rimCirclePaint.setColor(Color.argb(0x4f, 0x33, 0x36, 0x33)); // 线的颜色
        
        float rimSize = 0.02f;
        faceRect = new RectF();
        faceRect.set(rimRect.left + rimSize, rimRect.top + rimSize,
                rimRect.right - rimSize, rimRect.bottom - rimSize);
        Bitmap faceTexture = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.plastic);
        BitmapShader paperShader = new BitmapShader(faceTexture, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        Matrix paperMatrix = new Matrix();
        paperMatrix.setScale((1.0f / faceTexture.getWidth()), (1.0f / faceTexture.getHeight()));
        paperShader.setLocalMatrix(paperMatrix);
        
        facePaint = new Paint();
        facePaint.setFilterBitmap(true);
        facePaint.setStyle(Paint.Style.FILL);
        facePaint.setShader(paperShader);
        
        rimShadowPaint = new Paint();
        rimShadowPaint.setShader(new RadialGradient(0.5f, 0.5f, faceRect.width() / 2.0f, 
                new int[] {0x00000000, 0x00000050, 0x50000050}, 
                new float[] {0.96f, 0.96f, 0.99f}, Shader.TileMode.MIRROR));
        rimShadowPaint.setStyle(Paint.Style.FILL);
        
        scalePaint = new Paint();
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setColor(0x9f004d0f);
        scalePaint.setStrokeWidth(0.005f);
        scalePaint.setAntiAlias(true);
        scalePaint.setTextSize(0.045f);
        scalePaint.setTypeface(Typeface.SANS_SERIF); // 字体
        scalePaint.setTextScaleX(0.8f);
        scalePaint.setTextAlign(Paint.Align.CENTER);
        
        float scalePosition = 0.10f;
        scaleRect = new RectF();
        scaleRect.set(faceRect.left + scalePosition, faceRect.top + scalePosition,
                      faceRect.right - scalePosition, faceRect.bottom - scalePosition); // 位置（左，上，右，下）
        
        titlePaint = new Paint();
        titlePaint.setColor(0xaf946109);
        titlePaint.setAntiAlias(true);
        titlePaint.setTypeface(Typeface.DEFAULT_BOLD);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTextSize(0.05f);
        titlePaint.setTextScaleX(0.8f);
        
        titlePath = new Path(); // 路径
        titlePath.addArc(new RectF(0.24f, 0.24f, 0.76f, 0.76f), -180.0f, -180.0f); // 圆弧
        
        logoPaint = new Paint();
        logoPaint.setFilterBitmap(true);
        logoBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher);
        logoMatrix = new Matrix();
        logoScale = (1.0f / logoBitmap.getWidth()) * 0.3f;;
        logoMatrix.setScale(logoScale, logoScale);

        handPaint = new Paint();
        handPaint.setAntiAlias(true);
        handPaint.setColor(0xff392f2c);     
        handPaint.setShadowLayer(0.01f, -0.005f, -0.005f, 0x7f000000);
        handPaint.setStyle(Paint.Style.FILL);   
        
        handPath = new Path();
        handPath.moveTo(0.5f, 0.5f + 0.2f); // 起始点
        handPath.lineTo(0.5f - 0.010f, 0.5f + 0.2f - 0.007f);
        handPath.lineTo(0.5f - 0.002f, 0.5f - 0.32f);
        handPath.lineTo(0.5f + 0.002f, 0.5f - 0.32f);
        handPath.lineTo(0.5f + 0.010f, 0.5f + 0.2f - 0.007f);
        handPath.lineTo(0.5f, 0.5f + 0.2f);
        handPath.addCircle(0.5f, 0.5f, 0.025f, Path.Direction.CW); // clockwise 顺时针
        
        handScrewPaint = new Paint();
        handScrewPaint.setAntiAlias(true);
        handScrewPaint.setColor(0xff493f3c);
        handScrewPaint.setStyle(Paint.Style.FILL);
        
        backgroundPaint = new Paint();
        backgroundPaint.setFilterBitmap(true); // 绘画是过滤对bitmap的优化操作，加快显示速度
    }
    
    // step 3 measure

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("thom", "Width spec: " + MeasureSpec.toString(widthMeasureSpec));
        Log.d("thom", "Height spec: " + MeasureSpec.toString(heightMeasureSpec));
        
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        
        int chosenWidth = chooseDimension(widthMode, widthSize);
        int chosenHeight = chooseDimension(heightMode, heightSize);
        
        int chosenDimension = Math.min(chosenWidth, chosenHeight);
        setMeasuredDimension(chosenDimension, chosenDimension);  // 设置当前view大小
    }

    private int chooseDimension(int mode, int size) {
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.EXACTLY) {
            return size;
        } else {  // MeasureSpce.UNSPECIFIED
            return getPreferredSize();
        }
    }
    
    private int getPreferredSize() {
        return 300;
    }
    
    // step 4 draw

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
       
        float scale = getWidth();
        // https://developer.android.com/sdk/api_diff/28/changes
//        canvas.save(Canvas.MATRIX_SAVE_FLAG); // 保存状态，矩阵变换
        canvas.scale(scale, scale); // 缩放变换preconcat，width x height变为（1.0 x 1.0）
        
        drawLogo(canvas);
        drawHand(canvas);

        canvas.restore(); // 恢复状态
        
        if (handNeedsToMove()) {
            moveHand();
        }
    }

    private void drawHand(Canvas canvas) {
        if (handInitialized) {
            float handAngle = degreeToAngle(handPosition);
//            canvas.save(Canvas.MATRIX_SAVE_FLAG);
            canvas.rotate(handAngle, 0.5f, 0.5f);
            canvas.drawPath(handPath, handPaint); // android:targetSdkVersion="16" 无效
            canvas.restore();
            
            canvas.drawCircle(0.5f, 0.5f, 0.01f, handScrewPaint);
        }
    }

    private float degreeToAngle(float degree) {
        return (degree - centerDegree) / 2.0f * degreesPerNick;
    }

    private void drawLogo(Canvas canvas) {
//        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        
        canvas.translate(0.5f - logoBitmap.getWidth() * logoScale / 2.0f,
                         0.5f - logoBitmap.getHeight() * logoScale / 2.0f);
        int color = 0x00000000;
        float position = getRelativeTemperaturePosition();
        if (position < 0) {
            color |= (int) ((0xf0) * (-position));  // blue
        } else {
            color |= ((int) ((0xf0) * (position))) << 16; // red
        }
        LightingColorFilter logoFilter = new LightingColorFilter(0xff338822, color);
        logoPaint.setColorFilter(logoFilter);
        
        canvas.drawBitmap(logoBitmap, logoMatrix, logoPaint);
        
        canvas.restore();
    }

    private void drawTitle(Canvas canvas) {
        String title = getTitle();
        canvas.drawTextOnPath(title, titlePath, 0.0f, 0.0f, titlePaint);
    }

    private String getTitle() {
        return "thom thermometer";
    }

    private void drawScale(Canvas canvas) {
        canvas.drawOval(scaleRect, scalePaint);
        
//        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        
        for (int i=0; i<totalNicks; ++i) {
            float y1 = scaleRect.top;
            float y2 = y1 - 0.02f;
            canvas.drawLine(0.5f, y1, 0.5f, y2, scalePaint);
            
            if (i % 5 == 0) {
                int value = nickToDegree(i);
                if (value >= minDegrees && value <= maxDegrees) {
                    String valueText = Integer.toString(value);
                    canvas.drawText(valueText, 0.5f, y2 - 0.015f, scalePaint);
                }
            }
            
            canvas.rotate(degreesPerNick, 0.5f, 0.5f);
        }
        
        canvas.restore(); // to save
    }

    private int nickToDegree(int nick) {
        int rawDegree = ((nick < totalNicks / 2) ? nick : (nick - totalNicks)) * 2;
        int shiftedDegree = rawDegree + centerDegree;
        return shiftedDegree;
    }

    private void drawFace(Canvas canvas) {
        canvas.drawOval(faceRect, facePaint);
        canvas.drawOval(faceRect, rimCirclePaint);
        canvas.drawOval(faceRect, rimShadowPaint);
        
    }

    private void drawRim(Canvas canvas) {
        // first, draw the metallic body
        canvas.drawOval(rimRect, rimPaint); // 椭圆
        // draw cicle
        canvas.drawOval(rimRect, rimCirclePaint);
    }

    private void drawBackground(Canvas canvas) {
        if (null == background) {
            Log.w("thom", "background not created");
        } else {
            canvas.drawBitmap(background, 0, 0, backgroundPaint);
        }
    }
    
    // step 5 optimization
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);  // onMeasure => onSizeChanged => onDraw
        Log.d("thom", "Size changed to " + w + "x" + h);
      
        generateBackground();
    }
    
    private void generateBackground() {
        if (null != background) {
            background.recycle();
        }
        background = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888); // 创建背景图
        Canvas backgroundCanvas = new Canvas(background);  // up and line, Avoid object allocations during draw/layout
                                                           //   operations (preallocate and reuse instead)
        float backgroundScale = getWidth();
        backgroundCanvas.scale(backgroundScale, backgroundScale);
        
        drawRim(backgroundCanvas);
        drawFace(backgroundCanvas);
        drawScale(backgroundCanvas);
        drawTitle(backgroundCanvas);
    }
    
    // step 6 mechanics

    private void moveHand() {
        if (! handNeedsToMove()) {
            return;
        }
        
        if (lastHandMoveTime != -1L) {
            long currentTime = System.currentTimeMillis();
            float delta = (currentTime - lastHandMoveTime) / 1000.0f;

            float direction = Math.signum(handVelocity);
            if (Math.abs(handVelocity) < 90.0f) {
                handAcceleration = 5.0f * (handTarget - handPosition);
            } else {
                handAcceleration = 0.0f;
            }
            handPosition += handVelocity * delta;
            handVelocity += handAcceleration * delta;
            if ((handTarget - handPosition) * direction < 0.01f * direction) {
                handPosition = handTarget;
                handVelocity = 0.0f;
                handAcceleration = 0.0f;
                lastHandMoveTime = -1L;
            } else {
                lastHandMoveTime = System.currentTimeMillis();              
            }
            invalidate();
        } else {
            lastHandMoveTime = System.currentTimeMillis();
            moveHand();
        }
        
    }

    private boolean handNeedsToMove() {
        return Math.abs(handPosition - handTarget) > 0.01f;
    }
    
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        attachToSensor();
    }
    
    @Override
    protected void onDetachedFromWindow() {
        detachFromSensor();
        super.onDetachedFromWindow();
    }
    
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values.length > 0) {
            float temperatureC = sensorEvent.values[0];
            Log.d("thom", "*** Temperature: " + temperatureC);
            
            float temperatureF = (9.0f / 5.0f) * temperatureC + 32.0f;
            setHandTarget(temperatureF);
        } else {
            Log.w("thom", "Empty sensor event received");
        }
    }
    
    private void setHandTarget(float temperature) {
        if (temperature < minDegrees) {
            temperature = minDegrees;
        } else if (temperature > maxDegrees) {
            temperature = maxDegrees;
        }
        handTarget = temperature;
        handInitialized = true;
        invalidate();
    }
    
    private float getRelativeTemperaturePosition() {
        if (handPosition < centerDegree) {
            return - (centerDegree - handPosition) / (float) (centerDegree - minDegrees);
        } else {
            return (handPosition - centerDegree) / (float) (maxDegrees - centerDegree);
        }
    }
    
    private SensorManager getSensorManager() {
        return (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);       
    }
    
    private void attachToSensor() {
        SensorManager sensorManager = getSensorManager();
        
        @SuppressWarnings("deprecation")
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_TEMPERATURE);
        if (sensors.size() > 0) {
            Sensor sensor = sensors.get(0);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST, handler);
        } else {
            Log.e("thom", "No temperature sensor found");
        }       
    }
    
    private void detachFromSensor() {
        SensorManager sensorManager = getSensorManager();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
    }
    
    // step 7 state saving & geting the temperature
    
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable savedState = super.onSaveInstanceState();
        
        Bundle bundle = new Bundle();
        bundle.putParcelable("savedState", savedState);
        bundle.putBoolean("handInitialized", handInitialized);
        bundle.putFloat("handPosition", handPosition);
        bundle.putFloat("handTarget", handTarget);
        bundle.putFloat("handVelocity", handVelocity);
        bundle.putFloat("handAcceleration", handAcceleration);
        bundle.putLong("lastHandMoveTime", lastHandMoveTime);
        
        return bundle;
    }
    
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        Parcelable savedState = bundle.getParcelable("savedState");
        super.onRestoreInstanceState(savedState);
        
        handInitialized = bundle.getBoolean("handInitialized");
        handPosition = bundle.getFloat("handPosition");
        handTarget = bundle.getFloat("handTarget");
        handVelocity = bundle.getFloat("handVelocity");
        handAcceleration = bundle.getFloat("handAcceleration");
        lastHandMoveTime = bundle.getLong("lastHandMoveTime");
    }
}
