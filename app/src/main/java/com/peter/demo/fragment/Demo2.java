package com.peter.demo.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by Peter on 2018/9/27.

public class Demo2 extends View {
    private static final String TAG = "BackGroundView";
    private Path pathMid;
    private Path pathLow;
    private Path pathHight;
    private Paint paintHight, paintMid, paintLow,paintCircle;

    private int mWidth, mHeight;

    private float yScale, xScale;

    public Demo2(Context context, @Nullable AttributeSet attrs) {
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
            mHeight=minSize;
            mWidth=minSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, minSize);
        }
        xScale = mWidth / 1280f;
        yScale = mHeight / 800f;

        Log.d(TAG, "onMeasure: width : "+mWidth+ " height : "+mHeight);
        Log.d(TAG, "onMeasure: xScale : "+xScale+"yScale : "+yScale);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);

        drawValue(canvas,paintLow,pathLow);

        canvas.save();
        canvas.rotate(90, 20*xScale, 20*yScale);
        drawValue(canvas,paintMid,pathMid);
        canvas.restore();

        canvas.save();
        canvas.rotate(180, 0, 40*yScale);
        drawValue(canvas,paintHight,pathHight);
        canvas.restore();

        canvas.rotate(360 * fraction);
//        canvas.drawCircle(0,0,120*xScale,paintCircle);
    }

    private void drawValue(Canvas canvas,Paint paint,Path path) {
        path.moveTo(-200 * xScale, 200 * yScale);
        path.quadTo(40 * xScale, 480 * yScale, 200 * xScale, 200 * yScale);
        path.lineTo(200 * xScale, 200 * yScale);
        path.quadTo(430 * xScale, -320 * yScale, 0, -200 * yScale);
        path.lineTo(0, -200 * yScale);
        path.quadTo(-430 * xScale, -50 * yScale, -200 * xScale, 200 * yScale);
        path.close();

//        path.moveTo(-200 * xScale, 200 * xScale);
//        path.quadTo(40 * xScale, 480 * xScale, 200 * xScale, 200 * yScale);
//        path.lineTo(200 * xScale, 200 * yScale);
//        path.quadTo(430 * xScale, -320 * yScale, 0, -200 * yScale);
//        path.lineTo(0, -200 * yScale);
//        path.quadTo(-430 * xScale, -50 * yScale, -200 * xScale, 200 * yScale);
//        path.close();




        canvas.drawPath(path, paint);
    }

    private void init() {
        pathLow = new Path();
        paintLow = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLow.setStyle(Paint.Style.FILL);
        int startcolor = getResources().getColor(R.color.color1);
        int midcolor = getResources().getColor(R.color.color_low);
        LinearGradient backGradient = new LinearGradient(-200, 0, 0, 400, new int[]{midcolor, startcolor}, null, Shader.TileMode.CLAMP);
        paintLow.setShader(backGradient);
        paintLow.setAlpha(100);

        pathHight = new Path();
        paintHight = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHight.setStyle(Paint.Style.FILL);
        int startcolor1 = getResources().getColor(R.color.color1);
        int midcolor1 = getResources().getColor(R.color.color2);
        LinearGradient backGradient1 = new LinearGradient(-200, 0, 0, 400, new int[]{midcolor1, startcolor1}, null, Shader.TileMode.CLAMP);
        paintHight.setShader(backGradient1);
        paintHight.setAlpha(125);

        pathMid = new Path();
        paintMid = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintMid.setStyle(Paint.Style.FILL);
        int startcolor2 = getResources().getColor(R.color.color1);
        int midcolor2 = getResources().getColor(R.color.color2);
        LinearGradient backGradient2 = new LinearGradient(-200, 0, 0, 400, new int[]{midcolor2, startcolor2}, null, Shader.TileMode.CLAMP);
        paintMid.setShader(backGradient2);
        paintMid.setAlpha(85);

        paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setColor(Color.WHITE);

    }


    private float fraction = 0.0f;
    private ValueAnimator valueAnimator;

    public void startAnim() {

        post(new Runnable() {
            @Override
            public void run() {
                valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setDuration(5000);
                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                valueAnimator.setRepeatMode(ValueAnimator.RESTART);
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

    public void stopAnim(){
        if (valueAnimator != null) {
            valueAnimator.end();
            valueAnimator = null;
        }
    }
}
 */


/**
 * @param year
 * @param month
 * @param day
 * @return
 */
//public static String CaculateWeekDay(int y,int m,int d)
//        {
//
//        String strDate = y+"-"+m+"-"+d;// 定义日期字符串
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
//        Date date = null;
//        try {
//        date = format.parse(strDate);// 将字符串转换为日期
//        }catch (ParseException e) {
//        }
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        int dayForWeek = 0;
//        if(c.get(Calendar.DAY_OF_WEEK) == 1){
//        dayForWeek = 7;
//        }else{
//        dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
//        }
//        　　　　　　　　String day = "";
//        　　　　　　　　switch （dayFotWeek）
//        　　　　　　　　{
//        　　　　　　　　　　case 0:day = "周日";break;
//
//        　　　　　　　　　　case 1:day = "周一";break;　　
//        　　　　　　　　　　case 2:day = "周二";break;
//        　　　　　　　　　　case 3:day = "周三";break;
//        　　　　　　　　　　case 4:day = "周四";break;
//        　　　　　　　　　　case 5:day = "周五";break;
//
//        　　　　　　　　　　case 6:day = "周六";break;
//        　　　　　　　　　}
//        return day;
//
//        }