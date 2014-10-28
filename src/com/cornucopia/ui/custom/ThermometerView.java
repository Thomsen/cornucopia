package com.cornucopia.ui.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.cornucopia.R;

// step 1 extend view

public class ThermometerView extends View {

    // rim 轮廓

    private RectF rimRect;
    private Paint rimPaint;
    private Paint rimCirclePaint;
    private Bitmap background;
    private Paint backgroundPaint;
    private RectF faceRect;
    private Paint facePaint;
    private Paint rimShadowPaint;
    
    // step 2 implement constructor
    
    public ThermometerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ThermometerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ThermometerView(Context context) {
        this(context, null);
    }
    
    private void init() {
        initDrawingTools();
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
        
        backgroundPaint = new Paint();
        backgroundPaint.setFilterBitmap(true); // 绘画是过滤对bitmap的优化操作，加快显示速度
    }
    
    // step 3 measure

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
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
       
        background = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888); // 创建背景图
        Canvas backgroundCanvas = new Canvas(background);  // up and line, Avoid object allocations during draw/layout
                                                           //   operations (preallocate and reuse instead)
        float backgroundScale = getWidth();
        backgroundCanvas.scale(backgroundScale, backgroundScale);
        
        drawRim(backgroundCanvas);
        drawFace(backgroundCanvas);
        
        canvas.drawBitmap(background, 0, 0, backgroundPaint);
        
        float scale = getWidth();
        canvas.save(Canvas.MATRIX_SAVE_FLAG); // 矩阵需要还原
        canvas.scale(scale, scale); // 缩放变换
        
        canvas.restore();
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
    }
    
    // step 6 mechanics
    
    // step 7 state saving & geting the temperature
    
}
