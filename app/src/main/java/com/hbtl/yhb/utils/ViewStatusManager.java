package com.hbtl.yhb.utils;

import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewStatusManager {
    private static final float DISABLE_OPACITY = 0.5f;
    public final static float[] BT_SELECTED = new float[]{
            1, 0, 0, 0, -50,
            0, 1, 0, 0, -50,
            0, 0, 1, 0, -50,
            0, 0, 0, 1, 0};

    public final static float[] BT_NOT_SELECTED = new float[]{
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0};

    public final static OnFocusChangeListener buttonOnFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!v.isEnabled()) {
                return;
            }
            if (hasFocus) {
                updateBackgroundColorFilter(v, true);
            } else {
                updateBackgroundColorFilter(v, false);
            }
        }
    };

    public final static OnTouchListener buttonOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (!v.isEnabled()) {
                return false;
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                updateBackgroundColorFilter(v, true);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            } else {
                updateBackgroundColorFilter(v, false);
            }
            return false;
        }
    };

    private static void updateBackgroundColorFilter(View v, boolean select) {
        if (v == null) {
            return;
        }

        if (v instanceof ImageView) {
            ImageView img = (ImageView) v;
            if (img.getDrawable() != null) {
                img.getDrawable().setColorFilter(select ? new ColorMatrixColorFilter(BT_SELECTED) : new ColorMatrixColorFilter(BT_NOT_SELECTED));
                img.setImageDrawable(img.getDrawable());
            }

        } else if (v instanceof TextView) {
            TextView text = (TextView) v;
            ColorStateList color =  text.getTextColors();
            if (color != null) {
                text.setTextColor(select ? color.withAlpha(150) : color.withAlpha(255));
            }

        } else {
            if (v.getBackground() != null) {
                v.getBackground().setColorFilter(select ? new ColorMatrixColorFilter(BT_SELECTED) : new ColorMatrixColorFilter(BT_NOT_SELECTED));
                v.setBackgroundDrawable(v.getBackground());
            }
        }
    }

    private static void updateDrawableLeftColorFilter(View v, ColorFilter filter) {
        if (v == null || !(v instanceof TextView)) {
            return;
        }

        TextView tv = (TextView) v;
        Drawable[] drawables = tv.getCompoundDrawables();
        if (drawables == null || drawables.length != 4) {
            return;
        }

        Drawable left = drawables[0];
        if (left == null) {
            return;
        }

        left.setColorFilter(filter);
        tv.setCompoundDrawables(left, drawables[1], drawables[2], drawables[3]);
    }


    /**
     * 设置图片控件获取焦点改变状态
     */
    public final static void setViewFocusChanged(View inView) {
        inView.setOnTouchListener(buttonOnTouchListener);
        inView.setOnFocusChangeListener(buttonOnFocusChangeListener);
    }

    /**
     * 设置点击区域控制子区域的图片
     *
     * @param inView
     * @param clickParent
     */
    public final static void setViewFocusChangedWithParent(final View inView, final View clickParent) {
        if (inView == null || clickParent == null) {
            return;
        }

        clickParent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!v.isEnabled()) {
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    updateBackgroundColorFilter(inView, true);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                } else {
                    updateBackgroundColorFilter(inView, false);
                }
                return false;
            }
        });

        clickParent.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!inView.isEnabled()) {
                    return;
                }
                if (hasFocus) {
                    updateBackgroundColorFilter(inView, true);
                } else {
                    updateBackgroundColorFilter(inView, false);
                }
            }
        });
    }

    /**
     * 设置点击区域控制子区域的图片
     *
     * @param inViews
     * @param clickParent
     */
    public final static void setViewFocusChanged(final View clickParent, final View... inViews) {
        if (inViews == null || clickParent == null) {
            return;
        }

        clickParent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                for (View inView : inViews) {
                    if (!v.isEnabled()) {
                        return false;
                    }
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        setViewAlpha(inView, 0.7f);
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                    } else {
                        setViewAlpha(inView, 1f);
                    }
                }
                return false;
            }
        });

        clickParent.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                for (View inView : inViews) {
                    if (!inView.isEnabled()) {
                        return;
                    }
                    if (hasFocus) {
                        setViewAlpha(inView, 0.7f);
                    } else {
                        setViewAlpha(inView, 1f);
                    }
                }
            }
        });
    }

    /**
     * 设置点击区域控制子区域的图片
     *
     * @param inView
     */
    public final static void setViewFocusChangedOnDrawableLeft(final View inView) {
        if (inView == null) {
            return;
        }

        inView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!v.isEnabled()) {
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setViewAlpha(inView, 0.7f);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                } else {
                    setViewAlpha(inView, 1.0f);
                }
                return false;
            }
        });

        inView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!inView.isEnabled()) {
                    return;
                }
                if (hasFocus) {
                    setViewAlpha(inView, 0.7f);
                } else {
                    setViewAlpha(inView, 1.0f);
                }
            }
        });
    }

    /**
     * 取消图片控件获取焦点改变状态
     */
    public final static void CancelViewFocusChanged(View inView) {
        inView.setOnTouchListener(null);
        inView.setOnFocusChangeListener(null);
    }

    /**
     * 扩大控件可点区域
     *
     * @param view
     */
    public final static void expandTouchArea(final View view) {
        final View parent = (View) view.getParent();
        parent.post(new Runnable() {

            public void run() {
                final Rect r = new Rect();
                view.getHitRect(r);
                r.top -= 5;
                r.bottom += 5;
                r.left -= 5;
                r.right += 5;
                parent.setTouchDelegate(new TouchDelegate(r, view));
            }
        });
    }

    public final static void expandTouchArea(final View view, final int left,
                                             final int top, final int right, final int bottom) {
        final View parent = (View) view.getParent();
        parent.post(new Runnable() {

            public void run() {
                final Rect r = new Rect();
                view.getHitRect(r);
                r.top -= top;
                r.bottom += bottom;
                r.left -= left;
                r.right += right;
                parent.setTouchDelegate(new TouchDelegate(r, view));
            }
        });
    }

    public static void setViewAlpha(View v, float opacity) {
        if (v == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            v.setAlpha(opacity);
        } else {
            AlphaAnimation alphaDown = new AlphaAnimation(1.0f, opacity);
            alphaDown.setDuration(0);
            alphaDown.setFillAfter(true);
            v.startAnimation(alphaDown);
        }
    }

    /*
     * 禁用控件，并且设置透明度
     */
    public static void disableViewReduceOpacity(View v) {
        if (v == null) {
            return;
        }
        AlphaAnimation alphaDown = new AlphaAnimation(1.0f, DISABLE_OPACITY);
        alphaDown.setDuration(0);
        alphaDown.setFillAfter(true);
        v.startAnimation(alphaDown);
        v.setClickable(false);
        v.setEnabled(false);
    }

    /*
     * 回复控件的可用
     */
    public static void enableViewRestoreOpacity(View v) {
        if (v == null) {
            return;
        }
        AlphaAnimation alphaDown = new AlphaAnimation(1.0f, 1.0f);
        alphaDown.setDuration(0);
        alphaDown.setFillAfter(true);
        v.startAnimation(alphaDown);
        v.setClickable(true);
        v.setEnabled(true);
    }
}
