package com.example.greatinternetofthings.customview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.greatinternetofthings.constant.ConstantAssemble;
import com.example.greatinternetofthings.R;
import com.example.greatinternetofthings.adapter.DragGridAdapter;
import com.example.greatinternetofthings.constant.NativeResource;

public class DragGridView extends GridView implements AdapterView.OnItemLongClickListener {
    View touchedView, dragView;
    WindowManager windowManager;
    WindowManager.LayoutParams layoutParams;
    int dragMode, originalPosition,touchedPosition, tempPosition;
    float originalX, originalY, itemHalfWidth, itemHalfHeight;

    public DragGridView(Context context) {
        super(context, null);
        Init(context);
    }

    public DragGridView(Context context, AttributeSet attr) {
        super(context, attr, 0);
        Init(context);
    }

    public DragGridView(Context context, AttributeSet attr, int defStyleAttr) {
        super(context, attr, defStyleAttr);
        Init(context);
    }

    void Init(Context context){
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        dragMode=ConstantAssemble.DRAG_MODE_NORMAL;
        setOnItemLongClickListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                originalX = ev.getRawX();
                originalY = ev.getRawY();
                Log.d("TAG","intercept ACTION:--- down  rawX:---  "+originalX+"  rawY:---  "+originalY);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                Log.d("TAG","intercept ACTION:--- up");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        Log.d("TAG","long click beign,mode:--- "+dragMode);
        if (dragMode == ConstantAssemble.DRAG_MODE_MOVE) {
            return false;
        }
        touchedView = view;
        originalPosition=position;
        touchedPosition = position;
        tempPosition = position;
        Log.d("TAG","tempPosition:--- "+tempPosition);
        itemHalfWidth = originalX - touchedView.getLeft() - this.getLeft();
        itemHalfHeight = touchedView.getHeight();
        CreateDragView();
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("TAG","touch ACTION:--- down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("TAG","touch ACTION:--- move, dragmode:--- "+dragMode);
                if (dragMode == ConstantAssemble.DRAG_MODE_MOVE) {
                    move(ev);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("TAG","touch ACTION:--- up");
                    CloseWindow();
                break;
        }
        return super.onTouchEvent(ev);
    }

    void CreateDragView() {
        if (dragView == null) {
            dragView = View.inflate(getContext(), R.layout.item_drag_grid, null);
            ImageView ivItemIcon = dragView.findViewById(R.id.iv_item_icon);
            TextView tvName = dragView.findViewById(R.id.tv_name);
            ivItemIcon.setImageResource(NativeResource.menuImage[touchedPosition]);
            tvName.setText(ConstantAssemble.itemNames[touchedPosition]);
        }
        if (layoutParams == null) {
            layoutParams = new WindowManager.LayoutParams();
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            else{
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            }
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            layoutParams.width = touchedView.getWidth();
            layoutParams.height = touchedView.getHeight();
            layoutParams.x = touchedView.getLeft() + this.getLeft();
            layoutParams.y = (int)(originalY-itemHalfHeight);
//            layoutParams.y = (int)(originalY);
            Log.d("TAG","touchedView top:--- "+touchedView.getTop()+"  gridview top:--- "+getTop()+"  gridview y:---  "+getY());
            touchedView.setVisibility(INVISIBLE);
        }
        windowManager.addView(dragView, layoutParams);
        dragMode = ConstantAssemble.DRAG_MODE_MOVE;
    }

    void move(MotionEvent ev) {
        if (dragMode == ConstantAssemble.DRAG_MODE_MOVE) {
            float x = ev.getRawX() - itemHalfWidth;
            float y = ev.getRawY() - itemHalfHeight;
            if (layoutParams != null) {
                layoutParams.x = (int) x;
                layoutParams.y = (int) y;
                windowManager.updateViewLayout(dragView, layoutParams);
            }
            float mX = ev.getX();
            float mY = ev.getY();
            int dropPosition = pointToPosition((int) mX, (int) mY);
            if (dropPosition == tempPosition || dropPosition == GridView.INVALID_POSITION) {
                return;
            }
            ItemMove(dropPosition);
        }
    }

    void ItemMove(int dropPosition) {
        TranslateAnimation translateAnimation;
        if (dropPosition < tempPosition) {
            for (int i = dropPosition; i < tempPosition; i++) {
                View currentView = getChildAt(i);
                View nextView = getChildAt(i + 1);
                float x = (nextView.getLeft() - currentView.getLeft()) * 1f / currentView.getWidth();
                float y = (nextView.getTop() - currentView.getTop()) * 1f / currentView.getHeight();
                translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, x, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, y);
                translateAnimation.setInterpolator(new LinearInterpolator());
                translateAnimation.setDuration(300);
                if (i == tempPosition - 1) {
                    translateAnimation.setAnimationListener(listener);
                }
                currentView.startAnimation(translateAnimation);
            }
        } else {
            for (int i = tempPosition+1; i <= dropPosition; i++) {
                View currentView = getChildAt(i);
                View prevView = getChildAt(i - 1);
                float x = (prevView.getLeft() - currentView.getLeft()) * 1f / currentView.getWidth();
                float y = (prevView.getTop() - currentView.getTop()) * 1f / currentView.getHeight();
                translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, x, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, y);
                translateAnimation.setInterpolator(new LinearInterpolator());
                translateAnimation.setDuration(300);
                if (i == dropPosition) {
                    translateAnimation.setAnimationListener(listener);
                }
                currentView.startAnimation(translateAnimation);
            }
        }
        tempPosition = dropPosition;
    }

    Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            ListAdapter adapter = getAdapter();
            if (dragView != null&&adapter != null && adapter instanceof DragGridAdapter) {
                Log.d("TAG","exchange item:touchedPosition:--- "+touchedPosition+"  tempPosition:--- "+tempPosition);
                ((DragGridAdapter) adapter).ExchangePosition(touchedPosition, tempPosition, true);
            }
            touchedPosition = tempPosition;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    void CloseWindow() {
        if (dragView != null) {
            windowManager.removeView(dragView);
            dragView = null;
            layoutParams = null;
        }
        if (tempPosition == touchedPosition || tempPosition == GridView.INVALID_POSITION) {
            Log.d("TAG","change visible:touchedPosition:--- "+touchedPosition+"  tempPosition:--- "+tempPosition);
            getChildAt(touchedPosition).setVisibility(VISIBLE);
        } else {
            Log.d("TAG","close window and exchange item:touchedPosition:--- "+touchedPosition+"  tempPosition:--- "+tempPosition);
            ListAdapter adapter = getAdapter();
            if (adapter != null && adapter instanceof DragGridAdapter) {
                ((DragGridAdapter) adapter).ExchangePosition(touchedPosition, tempPosition, false);
            }
        }
        dragMode = ConstantAssemble.DRAG_MODE_NORMAL;
    }
}
