package com.exzi.coincase.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CustomSeekBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var progress = 0
    private val max = 100
    private val step = 25
    private val paint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 3f // Çizgi kalınlığını da biraz küçülttüm
    }
    private val progressPaint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 3f
    }
    private val circlePaint = Paint().apply {
        color = Color.parseColor("#0E111B")
        style = Paint.Style.FILL
    }
    private val circleStrokePaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }
    private val progressCirclePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val circleRadius = 10f // Dairelerin yarıçapını küçülttüm
    private val padding = circleRadius * 2

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val width = width.toFloat() - padding * 2
        val height = height.toFloat()
        val stepWidth = width / (max / step)

        // Tüm ilerleme çizgisini gri çiz
        canvas.drawLine(padding, height / 2, width + padding, height / 2, paint)

        // Çizgiler ve çemberler için başlangıç ve bitiş noktalarını belirleme
        for (i in 0..max step step) {
            val x = i * stepWidth / step + padding
            canvas.drawCircle(x, height / 2, circleRadius, circlePaint)
            canvas.drawCircle(x, height / 2, circleRadius, circleStrokePaint)
        }

        val progressX = progress * stepWidth / step + padding

        // Beyaz ilerleme çizgisi
        canvas.drawLine(padding, height / 2, progressX, height / 2, progressPaint)

        // İlerleme çemberi
        canvas.drawCircle(progressX, height / 2, circleRadius, progressCirclePaint)
        canvas.drawCircle(progressX, height / 2, circleRadius, circleStrokePaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        if (event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val width = width.toFloat() - padding * 2
            val stepWidth = width / (max / step)

            progress = (((x - padding) / stepWidth).toInt() * step).coerceIn(0, max)
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }

    fun getProgress(): Int {
        return progress
    }
}
