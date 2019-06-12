package com.module.giftflowdemo;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class GiftFlowView extends FrameLayout {
    private static final String TAG = GiftFlowView.class.getName();
    private float a = 0f, b = 0f, c = 0f;
    private int count = 120;
    private ViewGroup viewGroup;
    private Context mContext;
    private int viewSizeY;


    public GiftFlowView(@NonNull Context context) {
        super(context);
        mContext = context;
        viewGroup = createAnimLayout();
    }


    public GiftFlowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        viewGroup = createAnimLayout();
    }

    public GiftFlowView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        viewGroup = createAnimLayout();
    }

    /**
     * 开始动画，注意开始之前必须设置上下文
     *
     * @param startLocation 开始的位置
     * @param endLocation   结束的位置
     */
    public void startAnimation(Drawable flowName, int[] startLocation, int[] endLocation) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable)flowName;
        Bitmap target = bitmapDrawable.getBitmap();
        int viewSizeX = target.getWidth();
        viewSizeY = target.getHeight();
        View imageView = new View(mContext);
        imageView.setBackgroundDrawable(flowName);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(viewSizeX, viewSizeY);
        imageView.setLayoutParams(layoutParams);
        viewGroup.addView(imageView);

        // 把要移动的控件添加到动画层
        int[] location = new int[2];
        viewGroup.getLocationOnScreen(location);
        int startX = startLocation[0] - viewSizeX / 2;
        int startY = startLocation[1] - viewSizeY / 2 - location[1];
        int endX = endLocation[0] - viewSizeX / 2;
        int endY = endLocation[1] - viewSizeY / 2 - location[1];

        Point startPoint = new Point(startX, startY);
        Point endPoint = new Point(endX, endY);

        if (startPoint.x == endPoint.x) {//一条直线
            startLineAnimation(imageView, startPoint, endPoint);
            return;
        }
        Point highPoints = getHighPoints(startPoint, endPoint);
        setMatrix(startPoint, highPoints, endPoint);
        if (startPoint.x > DensityUtil.getScreenHeight(mContext) / 2) {
            startRightAnimation(imageView, endPoint);
        } else {
            startLeftAnimation(imageView, startPoint);
        }
    }


    /**
     * 创建动画层
     *
     * @return
     */
    private ViewGroup createAnimLayout() {
        RelativeLayout animLayout = new RelativeLayout(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        this.addView(animLayout);
        return animLayout;
    }


    public Point getHighPoints(Point startPoint, Point endPoint) {
        Point highPoint = new Point();//动态计算抛物线最高点
        if (startPoint.x == endPoint.x) {
            highPoint.set(startPoint.x, (startPoint.y < endPoint.y ? startPoint.y : endPoint.y) - viewSizeY);
        } else if (startPoint.y == endPoint.y) {
            highPoint.set((startPoint.x < endPoint.x ? startPoint.x : endPoint.x) + Math.abs(endPoint.x - startPoint.x) / 2, (int) (startPoint.y - viewSizeY));
        } else if ((startPoint.x < endPoint.x && startPoint.y < endPoint.y)
                || (startPoint.x > endPoint.x && startPoint.y > endPoint.y)) {
            int x = (Math.abs(endPoint.x - startPoint.x)) / 3 + (startPoint.x < endPoint.x ? startPoint.x : endPoint.x);
            int y = (startPoint.y < endPoint.y ? startPoint.y : endPoint.y) - viewSizeY;
            highPoint.set(x, y);
        } else {
            int x = (startPoint.x > endPoint.x ? startPoint.x : endPoint.x) - (Math.abs(endPoint.x - startPoint.x)) / 3;
            int y = (startPoint.y < endPoint.y ? startPoint.y : endPoint.y) - viewSizeY;
            highPoint.set(x, y);
        }
        return highPoint;
    }

    public void setMatrix(Point startPoint, Point highPoint, Point endPoint) {
        final float[][] points = {{(float) startPoint.x, (float) startPoint.y},
                {(float) highPoint.x, (float) highPoint.y},
                {(float) endPoint.x, (float) endPoint.y}};
        float[] value = ParabolaAlgorithm.calculate(points);
        a = value[0];
        b = value[1];
        c = value[2];
        count = (int) Math.abs(points[2][0] - points[0][0]);
    }

    /**
     * 垂直动画
     *
     * @param imageView
     */
    private void startLineAnimation(final View imageView, Point startPoint, Point endPoint) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("translationX", startPoint.x, endPoint.x);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", startPoint.y, endPoint.y);
        final ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(imageView, pvhX, pvhY).setDuration(700);
        if (startPoint.y < endPoint.y) {
            yxBouncer.setInterpolator(new AnticipateInterpolator());
        } else {
            yxBouncer.setInterpolator(new OvershootInterpolator());
        }
        yxBouncer.start();
        yxBouncer.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewGroup.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }



    /**
     * 左侧抛物线动画
     *
     * @param imageView
     */
    private void startLeftAnimation(final View imageView, Point startLocation) {
        if (count <= 0) {
            Log.e(TAG, "please set Point!");
            return;
        }
        Keyframe[] keyframes = new Keyframe[count];
        final float keyStep = 1f / (float) count;
        float key = keyStep;
        for (int i = 0; i < count; ++i) {
            keyframes[i] = Keyframe.ofFloat(key, startLocation.x + i + 1);
            key += keyStep;
        }

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe("translationX", keyframes);
        key = keyStep;
        for (int i = 0; i < count; ++i) {
            keyframes[i] = Keyframe.ofFloat(key, getY(startLocation.x + i + 1));
            key += keyStep;
        }

        PropertyValuesHolder pvhY = PropertyValuesHolder.ofKeyframe("translationY", keyframes);
        ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(imageView, pvhY, pvhX).setDuration(500);
        yxBouncer.setInterpolator(new LinearInterpolator());
        yxBouncer.start();
        yxBouncer.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewGroup.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 右侧抛物线动画
     *
     * @param imageView
     */
    private void startRightAnimation(final View imageView, Point endLocation) {
        if (count <= 0) {
            Log.e(TAG, "please set Point!");
            return;
        }
        Keyframe[] keyframes = new Keyframe[count];
        final float keyStep = 1f / (float) count;
        float key = keyStep;
        for (int i = count - 1; i >= 0; --i) {
            keyframes[count - 1 - i] = Keyframe.ofFloat(key, endLocation.x + i - 1);
            key += keyStep;
        }

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe("translationX", keyframes);
        key = keyStep;
        for (int i = count - 1; i >= 0; --i) {
            keyframes[count - 1 - i] = Keyframe.ofFloat(key, getY(endLocation.x + i - 1));
            key += keyStep;
        }

        PropertyValuesHolder pvhY = PropertyValuesHolder.ofKeyframe("translationY", keyframes);
        ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(imageView, pvhY, pvhX).setDuration(500);
        yxBouncer.setInterpolator(new LinearInterpolator());
        yxBouncer.start();
        yxBouncer.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewGroup.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 这里是根据三个坐标点{（0,0），（300,0），（150,300）}计算出来的抛物线方程
     *
     * @param x
     * @return
     */
    private float getY(float x) {
        return a * x * x + b * x + c;
    }


}
