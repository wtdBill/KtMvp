package ktmvp.yppcat.ktmvp.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import ktmvp.yppcat.ktmvp.utils.drawCircle

class WeatherView : View {

    private var mWidth: Int = 0

    private var mHeight: Int = 0

    private var firstEndX: Int = 0

    private var secondEndX: Int = 0

    private var startX: Int = 0

    private var startY: Int = 0

    private var mPaint: Paint = Paint()

//    private var paddingX : Int = 0
//
//    private var paddingY : Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = h
        setMeasuredDimension(w,w)
        mHeight = w
        firstEndX = w / 3
        secondEndX = firstEndX * 2
        startX = 0
        startY = height / 2

    }

    init {
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 3f
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(startX, startY, 3, mPaint)
        val path = Path()
        path.moveTo(startX.toFloat(), startY.toFloat())
        path.quadTo((mWidth / 6).toFloat(), startY - (mWidth / 4).toFloat(), (mWidth / 3).toFloat(), startY - (mWidth * 3 / 16).toFloat())
        path.quadTo((mWidth / 2).toFloat(), startY - (mWidth / 2).toFloat(), (mWidth * 2 / 3).toFloat(), startY - (mWidth * 3 / 16).toFloat())
        path.quadTo((mWidth * 5 /6).toFloat(),startY - (mWidth / 4).toFloat(), mWidth.toFloat(), startY.toFloat())
        path.close()
        canvas?.drawPath(path, mPaint)
    }
}