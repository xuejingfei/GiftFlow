package com.module.giftflowdemo

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView

/**
 * Author：xuejingfei
 *
 * Description：
 *
 * Date：2019-05-27 23:27
 */
class TextViewTest (context:Context,arrs: AttributeSet):TextView(context,arrs){

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN ->{
                Log.d("xjf", "TextViewTest_dispatchTouchEvent" + "ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE ->{
                Log.d("xjf", "TextViewTest_dispatchTouchEvent" + "ACTION_MOVE")
            }
            MotionEvent.ACTION_UP ->{
                Log.d("xjf", "TextViewTest_dispatchTouchEvent" + "ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL ->{
                Log.d("xjf", "TextViewTest_dispatchTouchEvent" + "ACTION_CANCEL")
            }
        }
        return super.dispatchTouchEvent(event)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN ->{
                Log.d("xjf", "TextViewTest_onTouchEvent" + "ACTION_DOWN")
//                parent.requestDisallowInterceptTouchEvent(true)
                return true
            }
            MotionEvent.ACTION_MOVE ->{
                Log.d("xjf", "TextViewTest_onTouchEvent" + "ACTION_MOVE")
            }
            MotionEvent.ACTION_UP ->{
                Log.d("xjf", "TextViewTest_onTouchEvent" + "ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL ->{
                Log.d("xjf", "TextViewTest_onTouchEvent" + "ACTION_CANCEL")
            }
        }
        return super.onTouchEvent(event)
    }



}