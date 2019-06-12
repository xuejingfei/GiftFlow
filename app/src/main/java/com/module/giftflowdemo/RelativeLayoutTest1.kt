package com.module.giftflowdemo

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-05-27 23:02
 */
class RelativeLayoutTest1(context: Context,arrs: AttributeSet):RelativeLayout(context,arrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action) {
            MotionEvent.ACTION_DOWN ->{
                Log.d("xjf", "RelativeLayoutTest1_dispatchTouchEvent" + "ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE ->{
                Log.d("xjf", "RelativeLayoutTest1_dispatchTouchEvent" + "ACTION_MOVE")
            }
            MotionEvent.ACTION_UP ->{
                Log.d("xjf", "RelativeLayoutTest1_dispatchTouchEvent" + "ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL ->{
                Log.d("xjf", "RelativeLayoutTest1_dispatchTouchEvent" + "ACTION_CANCEL")
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action) {
            MotionEvent.ACTION_DOWN ->{
                Log.d("xjf", "RelativeLayoutTest1_onInterceptTouchEvent" + "ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE ->{
                Log.d("xjf", "RelativeLayoutTest1_onInterceptTouchEvent" + "ACTION_MOVE")
            }
            MotionEvent.ACTION_UP ->{
                Log.d("xjf", "RelativeLayoutTest1_onInterceptTouchEvent" + "ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL ->{
                Log.d("xjf", "RelativeLayoutTest1_onInterceptTouchEvent" + "ACTION_CANCEL")
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN ->{
                Log.d("xjf", "RelativeLayoutTest1_onTouchEvent" + "ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE ->{
                Log.d("xjf", "RelativeLayoutTest1_onTouchEvent" + "ACTION_MOVE")
            }
            MotionEvent.ACTION_UP ->{
                Log.d("xjf", "RelativeLayoutTest1_onTouchEvent" + "ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL ->{
                Log.d("xjf", "RelativeLayoutTest1_onTouchEvent" + "ACTION_CANCEL")
            }
        }
        return super.onTouchEvent(event)
    }

}