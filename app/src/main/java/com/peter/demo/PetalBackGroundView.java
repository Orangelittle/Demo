package com.peter.demo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Peter on 2018/9/27.
 */
public class PetalBackGroundView extends View {
    private static final String TAG = "PetalBackGroundView";
    private Path pathLow, pathMid, pathHight;
    private Paint paintHight, paintMid, paintLow, paintCircle;
    private int mWidth, mHeight;
    private int radius;
    private float fraction = 0.0f;
    private ValueAnimator valueAnimator;
    private LinearInterpolator linearInterpolator = new LinearInterpolator();

    public PetalBackGroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mWidth = widthSize - getPaddingRight() - getPaddingLeft();
        mHeight = heightSize - getPaddingTop() - getPaddingBottom();


        // 在wrap_content的情况下默认长度为200dp
        int minSize = 200;

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, minSize);
            mHeight = minSize;
            mWidth = minSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, minSize);
        }
        radius = mWidth / 3;
        Log.d(TAG, "onMeasure: width : " + mWidth + " height : " + mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(360 * fraction);
        drawLValue(canvas, paintLow, pathLow);
        drawMValue(canvas, paintMid, pathMid);
        drawHValue(canvas, paintHight, pathHight);
        canvas.drawCircle(0, 0, radius * 0.65f, paintCircle);
    }

    /**
     * 绘制最上层
     *
     * @param canvas
     * @param paint
     * @param path
     */
    private void drawHValue(Canvas canvas, Paint paint, Path path) {
        canvas.save();
        path.moveTo(0, -radius);
        path.quadTo(-3 * radius * cos(40), -2 * radius * sin(60), -radius * cos(60), radius * sin(60));
        path.lineTo(-radius * cos(60), radius * sin(60));
        path.quadTo(radius * cos(40), -3 * radius * sin(40), radius * cos(60), radius * sin(60));
        path.lineTo(radius * cos(60), radius * sin(60));
        path.quadTo(2 * radius * cos(40), -4 * radius * sin(60), 0, -radius);
        path.close();
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    /**
     * 绘制中间层
     *
     * @param canvas
     * @param paint
     * @param path
     */
    private void drawMValue(Canvas canvas, Paint paint, Path path) {
        canvas.save();
        canvas.rotate(55);
        path.moveTo(0, -radius);
        path.quadTo(-0.8f * radius, -radius, -radius * cos(60), radius * sin(60));
        path.lineTo(-radius * cos(60), radius * sin(60));
        path.quadTo(-0.55f * radius, 2.5f * radius, radius * cos(60), radius * sin(60));
        path.lineTo(radius * cos(60), radius * sin(60));
        path.quadTo(radius, -0.95f * radius, 0, -radius);
        path.close();
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    /**
     * 绘制最下层
     *
     * @param canvas
     * @param paint
     * @param path
     */
    private void drawLValue(Canvas canvas, Paint paint, Path path) {
        canvas.save();
        canvas.translate(-radius / 8, 0);
        canvas.rotate(10);
        path.moveTo(0, -radius);
        path.quadTo(-radius, -0.65f * radius, -radius * cos(60), radius * sin(60));
        path.lineTo(-radius * cos(60), radius * sin(60));
        path.quadTo(-0.2f * radius, -3 * radius * sin(40), radius * cos(60), radius * sin(60));
        path.lineTo(radius * cos(60), radius * sin(60));
        path.quadTo(1.5f * radius, -1.85f * radius, 0, -radius);
        path.close();
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    private float cos(double angle) {
        return -((float) Math.cos(angle));
    }

    private float sin(double angle) {
        return -((float) Math.sin(angle));
    }

    private void init() {
        pathLow = new Path();
        paintLow = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLow.setStyle(Paint.Style.FILL);
        int lowcolorend = getResources().getColor(R.color.color_end);
        int lowcolorstart = getResources().getColor(R.color.color_low);
        LinearGradient backGradient = new LinearGradient(-200, 0, 0, 400, new int[]{lowcolorstart, lowcolorend}, null, Shader.TileMode.CLAMP);
        paintLow.setShader(backGradient);
        paintLow.setAlpha(120);

        pathHight = new Path();
        paintHight = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHight.setStyle(Paint.Style.FILL);
        int hightcolorend = getResources().getColor(R.color.color_end);
        int hightcolorstart = getResources().getColor(R.color.color_start);
        LinearGradient backGradient1 = new LinearGradient(-200, 0, 0, 400, new int[]{hightcolorstart, hightcolorend}, null, Shader.TileMode.CLAMP);
        paintHight.setShader(backGradient1);
        paintHight.setAlpha(165);

        pathMid = new Path();
        paintMid = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintMid.setStyle(Paint.Style.FILL);
        int midcolorstart = getResources().getColor(R.color.color_mid);
        int midcolorend = getResources().getColor(R.color.color_start);
        LinearGradient backGradient2 = new LinearGradient(-200, 0, 0, 400, new int[]{midcolorstart, midcolorend}, null, Shader.TileMode.CLAMP);
        paintMid.setShader(backGradient2);
        paintMid.setAlpha(95);

        paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setColor(Color.WHITE);

    }


    public void startAnim() {
        post(new Runnable() {
            @Override
            public void run() {
                valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
                valueAnimator.setInterpolator(linearInterpolator);
                valueAnimator.setDuration(3000);
                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        fraction = animation.getAnimatedFraction();
                        invalidate();
                    }
                });
                valueAnimator.start();
            }
        });
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            valueAnimator.end();
            valueAnimator = null;
        }
    }
}
