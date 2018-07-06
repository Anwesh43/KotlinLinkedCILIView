package com.anwesh.uiprojects.linkedciliview

/**
 * Created by anweshmishra on 06/07/18.
 */

import android.content.Context
import android.view.View
import android.view.MotionEvent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

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

    data class LICIState(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f, var j : Int = 0) {

        fun update(stopcb : (Float) -> Unit) {
            scale += 0.1f * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(prevScale)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class LICIAnimator (var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class LICINode(var i : Int, val state : LICIState = LICIState()) {

        lateinit var next : LICINode

        lateinit var prev : LICINode

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < LI_CI_NODES - 1) {
                next = LICINode(i + 1)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val gap : Float = (w / LI_CI_NODES)
            val r : Float = gap / 2
            val sc1 : Float = Math.min(state.scale, 0.5f)
            val sc2 : Float = Math.max(state.scale - 0.5f, 0f)
            paint.strokeWidth = Math.min(w, h) / 60
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = Color.parseColor("#673AB7")
            canvas.save()
            canvas.translate(i * gap + r, h/2)
            canvas.drawArc(RectF(-r, -r, r, r), 180f, 180f + 180f * sc1, false, paint)
            canvas.drawLine(0f, -r, r * sc2, -r + r * sc2, paint)
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : LICINode {
            var curr : LICINode = prev
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }
}