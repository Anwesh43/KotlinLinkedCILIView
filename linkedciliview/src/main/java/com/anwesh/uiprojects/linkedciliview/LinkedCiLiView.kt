package com.anwesh.uiprojects.linkedciliview

/**
 * Created by anweshmishra on 06/07/18.
 */

import android.content.Context
import android.view.View
import android.view.MotionEvent
import android.graphics.Canvas
import android.graphics.Paint

val LI_CI_NODES : Int = 5

class LinkedCiLiView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}