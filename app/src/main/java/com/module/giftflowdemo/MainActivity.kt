package com.module.giftflowdemo

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_btn.setOnClickListener {
            view_flow.startAnimation(getDrawable(R.mipmap.ic_launcher),getLocation(iv_start),getLocation(iv_end))
        }
        iv_start.setOnTouchListener(TouchSlipListener())
        iv_end.setOnTouchListener(TouchSlipListener())
    }

    private fun getLocation(view:View) : IntArray{
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val width = view.width
        val height = view.height
        location[0] = location[0] + width / 2
        location[1] = location[1] + height / 2
        return location
    }



    inner class TouchSlipListener:View.OnTouchListener {
        private  var lastX:Int = 0
        private var lastY:Int = 0

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when(event?.action) {
                MotionEvent.ACTION_DOWN ->{
                    //获取触摸事件的x,y坐标。
                    lastX = event.rawX.toInt()
                    lastY = event.rawY.toInt()
                }
                MotionEvent.ACTION_MOVE ->{
                    val dx = event.rawX.toInt() - lastX
                    val dy = event.rawY.toInt() - lastY
                    val l = v?.left?.plus(dx)?.toInt()
//                    val r = v?.right?.plus(dx)?.toInt()
//                    val b = v?.bottom?.plus(dy)?.toInt()
                    val t = v?.top?.plus(dy)?.toInt()

//                    if (l != null && t != null && r != null && b != null) {
//                        v.layout(l, t, r, b)
//                    }
//                    v?.postInvalidate()
                    val layoutParams = v?.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.topMargin = t!!
                    layoutParams.leftMargin = l!!
                    v.layoutParams = layoutParams
                    lastX = event.rawX.toInt()
                    lastY = event.rawY.toInt()

                }
            }
            return true
        }
    }
}
